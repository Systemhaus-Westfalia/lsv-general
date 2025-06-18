/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.shw.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MStorage;
import org.compiere.model.MWarehouse;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
/**
 *	Generate Invoices
 *	
 *  @author SHW
 *  @version $Id: SHW_InvoiceGenerate.java,v 1.2 2015/01/24 00:51:01 mc Exp $
 */
public class SHW_InvoiceGenerateFromOrderLine extends SvrProcess
{
	/**	Date Invoiced			*/
	private Timestamp	p_DateInvoiced = null;
	/** Consolidate				*/
	private boolean		p_ConsolidateDocument = true;
	/** Invoice Document Action	*/
	private String		p_docAction = "";
	
	/**	The current Invoice	*/
	private MInvoice 	invoice = null;
	/**	The current Shipment	*/
	/**	Business Partner		*/
	protected List<MOrderLine> m_records = null;
	protected List<MInvoice> m_invoices = null;
	protected List<MOrder> ordersToInvoice = null;
	protected List<MInOut> shipments = null;
	protected MInOut shipment = null;
	private MOrder m_order = null;
	private int P_C_Invoice_ID = 0;
	private int errorcount = 0;
	private String error = "";
	private int p_C_Doctype_ID = 0;
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] parameters = getParameter();
		for (ProcessInfoParameter para : parameters) {
			String name = para.getParameterName();
			if (para.getParameter() == null)
				;
			else if (name.equals("ConsolidateDocument"))
				p_ConsolidateDocument = para.getParameterAsBoolean();
			else if (name.equals("DocAction"))
				p_docAction = para.getParameterAsString();
			else if (name.equals(MInvoice.COLUMNNAME_DateInvoiced))
				p_DateInvoiced = para.getParameterAsTimestamp();
			else if (name.equals(MInvoice.COLUMNNAME_C_Invoice_ID))
				P_C_Invoice_ID = para.getParameterAsInt();
			else if (name.equals(MDocType.COLUMNNAME_C_DocType_ID))
				p_C_Doctype_ID = para.getParameterAsInt();
			else
				
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}

		//	Login Date
		if (p_DateInvoiced == null)
			p_DateInvoiced = Env.getContextAsDate(getCtx(), "#Date");
		if (p_DateInvoiced == null)
			p_DateInvoiced = new Timestamp(System.currentTimeMillis());

	}	//	prepare

	/**
	 * 	Generate Invoices
	 *	@return info
	 *	@throws Exception
	 */
	protected String doIt () throws Exception
	{

		StringBuffer orderClause = new StringBuffer();
		if (!p_ConsolidateDocument)
			orderClause.append("C_BPartner_ID, C_Project_ID, c_doctypetarget_ID");
		else 
			orderClause.append("C_BPartner_ID");
		String whereClause = "(C_OrderLine_ID in " + getSelectionKeys().toString().replace('[','(').replace(']',')') + ")";		
		m_records = new Query(getCtx(), MOrderLine.Table_Name, whereClause, get_TrxName())
		.setOrderBy(orderClause.toString())
		.setClient_ID()
		.list();

		//setColumnsValues();
		
		m_invoices = new ArrayList<MInvoice>();
		ordersToInvoice = new ArrayList<MOrder>();
		shipments = new ArrayList<MInOut>();
		for (MOrderLine orderLine: m_records) 
		{	
			m_order = (MOrder)orderLine.getC_Order();
			Boolean isadded =  false;
			for (MOrder order:ordersToInvoice)
			{
				if (order.getC_Order_ID() ==  m_order.getC_Order_ID())
				{
					isadded = true;
					break;
				}
			}
			if (!isadded)
				ordersToInvoice.add(m_order);
		}
		for(MOrder order:ordersToInvoice)
		{
			try
			{
				generate(order);				
			}
			catch (Exception e)
			{
				errorcount = errorcount + 1;
				error = error + order.getDocumentNo();
				continue;
			}
		}
		completeInvoices();

		String result = "Fact. No";
		for (MInvoice inv:m_invoices)
		{
			Env.setContext(getCtx(), "@WhereClause@", whereClause);
			result = result + ", " + inv.getDocumentInfo();
		}
		return result;
	}	//	doIt
	
	
	/**
	 * 	Generate Shipments
	 * 	@param pstmt order query 
	 *	@return info
	 */
	private String generate (MOrder order) throws Exception
	{

		createShipment(order,null);
		Arrays.asList(order.getShipments())
		.stream()
		.filter(shipment -> shipment.isComplete() && !shipment.getDocStatus().equals(MInOut.DOCSTATUS_Reversed))
		.forEach(shipment -> {
			Arrays.asList(shipment.getLines(false))
				.stream()
				.filter(shipmentLine -> order.isOrderLine(shipmentLine.getC_OrderLine_ID()))
				.filter(shipmentLine -> !shipmentLine.isInvoiced())
				.forEach(shipmentLine -> {
					createLine (order, shipment, shipmentLine);
				});
		});
		return "";
	}	//	generate
	
	

	/**
	 * 	Create Invoice Line from Shipment
	 *	@param order order
	 *	@param ship shipment header
	 *	@param sLine shipment line
	 */
	private void createLine (MOrder order, MInOut ship, MInOutLine shipmentLine)
	{
		if (invoice == null || invoice.getC_BPartner_ID() != order.getC_BPartner_ID())
		{
			if (P_C_Invoice_ID != 0)
				invoice = new MInvoice(getCtx(), P_C_Invoice_ID, get_TrxName());
			else
			{
				invoice = new MInvoice (order, 0, p_DateInvoiced);
				invoice.setDescription("Generado desde ");
				if (order.getPaymentRule()!= "" || order.getPaymentRule() != null)
					invoice.setPaymentRule(order.getPaymentRule());
				if (order.getC_PaymentTerm_ID() > 0)
					invoice.setC_PaymentTerm_ID(order.getC_PaymentTerm_ID());
				if (p_C_Doctype_ID != 0)
					invoice.setC_DocTypeTarget_ID(p_C_Doctype_ID);
				else if (order.getC_DocType().getC_DocTypeInvoice_ID() > 0)
					invoice.setC_DocTypeTarget_ID(order.getC_DocType().getC_DocTypeInvoice_ID());
				invoice.saveEx();	
				m_invoices.add(invoice);		

			}
		}
		invoice.setDescription(invoice.getDescription()  + order.getDocumentNo() );


		BigDecimal qtyToInvoice = shipmentLine.getMovementQty();
		MInvoiceLine invoiceLine = new MInvoiceLine (invoice);
		invoiceLine.setShipLine(shipmentLine);
		//	Qty = Delivered	
		if (shipmentLine.sameOrderLineUOM())
			invoiceLine.setQtyEntered(qtyToInvoice);
		else
			invoiceLine.setQtyEntered(qtyToInvoice);
		invoiceLine.setQtyInvoiced(qtyToInvoice);
		invoiceLine.saveEx();
		//	Link
		log.fine(invoiceLine.toString());
		shipmentLine.setIsInvoiced(true);
		shipmentLine.saveEx();
	}	//	createLine

	
	/**
	 * 	Complete Invoice
	 */
	private void completeInvoices()
	{
		m_invoices.stream().forEach(invoice -> {
			invoice.processIt(p_docAction);
		});
		}
	


	/**
	 * 	Correct Invoice
	 */
	
	private void createShipment(MOrder order, Timestamp movementDate)
	{
		m_records
		.stream()
		.filter(orderLine -> order.isOrderLine(orderLine.getC_OrderLine_ID())
				&& getSelectionAsBigDecimal(orderLine.get_ID(), "ODT_QtyToDeliver").compareTo(Env.ZERO) !=0)
		.forEach(orderLine -> {
			if (shipment == null) {
				shipment=	new MInOut (order, order.getC_DocType().getC_DocTypeShipment_ID(), movementDate);
				shipment.saveEx();
			}
			BigDecimal movementqty = getSelectionAsBigDecimal(orderLine.get_ID(), "ODT_QtyToDeliver");

			
			MInOutLine inOutLine = new MInOutLine(shipment);
			int locatorID = MStorage.getM_Locator_ID (orderLine.getM_Warehouse_ID(), 
					orderLine.getM_Product_ID(), orderLine.getM_AttributeSetInstance_ID(), 
					movementqty, get_TrxName());
			if (locatorID== 0)		//	Get default Location
			{
				MWarehouse wh = MWarehouse.get(getCtx(), orderLine.getM_Warehouse_ID());
				locatorID = wh.getDefaultLocator().getM_Locator_ID();
			}
			inOutLine.setOrderLine(orderLine, locatorID, movementqty);
			inOutLine.setQty(movementqty);
			inOutLine.saveEx();

		});
		if (shipment != null) {
			shipment.processIt(MInOut.DOCACTION_Complete);
			shipment = null;
		}

	}	//	createShipment
	
	

	

	

	
}	//	InvoiceGenerate

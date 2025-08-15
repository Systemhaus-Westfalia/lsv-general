/******************************************************************************
 * Product: ADempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2006-2017 ADempiere Foundation, All Rights Reserved.         *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * or (at your option) any later version.                                     *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * or via info@adempiere.net                                                  *
 * or https://github.com/adempiere/adempiere/blob/develop/license.html        *
 *****************************************************************************/

package org.shw.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;

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
import org.compiere.process.DocAction;
import org.compiere.util.Env;

/** Generated Process for (shw_OrderCreateSplitInvoice)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.4
 */
public class shw_OrderCreateSplitInvoice extends shw_OrderCreateSplitInvoiceAbstract
{
	
	private MInvoice 	invoice = null;
	private MInOut		shipment;
	private MInvoice 	ndD = null;

	@Override
	protected void prepare()
	{
		super.prepare();
	}

	@Override
	protected String doIt() throws Exception
	{
		MOrder order  = new MOrder(getCtx(), getRecord_ID(), get_TrxName());
		if (!order.getC_DocType().getDocSubTypeSO().equals(MOrder.DocSubTypeSO_Standard))
			return  "NO es orden estandard";
		MDocType dt = MDocType.get(getCtx(), order.getC_DocType_ID());
		String DocSubTypeSO = dt.getDocSubTypeSO();
		MInOut shipment = null;
		Boolean delivered_Invoiced = false;

		for (MOrderLine line:order.getLines()) {
			if (line.getQtyDelivered().compareTo(Env.ZERO) != 0 || line.getQtyInvoiced().compareTo(Env.ZERO)!=0)
				delivered_Invoiced = true;
			break;
		}
		if (delivered_Invoiced)
			return "La orden ya fue facturada";
		createShipment (order,dt, null);
		createInvoice(order, dt, null);
		Boolean issplitInvoice = false;
		for (MOrderLine line:order.getLines()) {

			if (line.get_ValueAsBoolean("isSplitInvoice"))
			{
				issplitInvoice = true;
				break;
			}			
		}
		if (invoice != null)
		if (issplitInvoice)
			splitInvoice();

		invoice.processIt(DocAction.ACTION_Complete);
		invoice.saveEx(get_TrxName());		
		if (ndD != null)
			ndD.processIt(DocAction.ACTION_Complete);
		return "";
	}
	

	/**
	 * 	Create Shipment
	 *	@param dt order document type
	 *	@param movementDate optional movement date (default today)
	 *	@return shipment or null
	 */
	private MInOut createShipment(MOrder order, MDocType dt, Timestamp movementDate)
	{
		log.info("For " + dt);
		shipment = new MInOut (order, dt.getC_DocTypeShipment_ID(), movementDate);
		shipment.saveEx(get_TrxName());
		//
		MOrderLine[] oLines = order.getLines(true, null);
		for (int i = 0; i < oLines.length; i++)
		{
			MOrderLine oLine = oLines[i];
			//
			MInOutLine ioLine = new MInOutLine(shipment);
			//	Qty = Ordered - Delivered
			BigDecimal MovementQty = oLine.getQtyOrdered().subtract(oLine.getQtyDelivered()); 
			//	Location
			int M_Locator_ID = MStorage.getM_Locator_ID (oLine.getM_Warehouse_ID(), 
					oLine.getM_Product_ID(), oLine.getM_AttributeSetInstance_ID(), 
					MovementQty, get_TrxName());
			if (M_Locator_ID == 0)		//	Get default Location
			{
				MWarehouse wh = MWarehouse.get(getCtx(), oLine.getM_Warehouse_ID());
				M_Locator_ID = wh.getDefaultLocator().getM_Locator_ID();
			}
			//
			ioLine.setOrderLine(oLine, M_Locator_ID, MovementQty);
			ioLine.setQty(MovementQty);
			if (oLine.getQtyEntered().compareTo(oLine.getQtyOrdered()) != 0) {
				ioLine.setQtyEntered(MovementQty
						.multiply(oLine.getQtyEntered())
						.divide(oLine.getQtyOrdered(), 6, RoundingMode.HALF_UP));
			}
			ioLine.saveEx(get_TrxName());
		}
		//	Manually Process Shipment
		shipment.processIt(DocAction.ACTION_Complete);
		shipment.saveEx(get_TrxName());
		if (!MOrder.DOCSTATUS_Completed.equals(shipment.getDocStatus()))
		{
			//m_processMsg = "@M_InOut_ID@: " + shipment.getProcessMsg();
			return null;
		}
		return shipment;
	}	//	createShipment
	
	private MInvoice createInvoice (MOrder order, MDocType dt, Timestamp invoiceDate)
	{
		log.info(dt.toString());
		invoice = new MInvoice (order, dt.getC_DocTypeInvoice_ID(), invoiceDate);
		invoice.saveEx(get_TrxName());
		
		//	If we have a Shipment - use that as a base
		if (shipment != null)
		{	//
			MInOutLine[] sLines = shipment.getLines(false);
			for (int i = 0; i < sLines.length; i++)
			{
				MInOutLine sLine = sLines[i];
				//
				MInvoiceLine iLine = new MInvoiceLine(invoice);
				iLine.setShipLine(sLine);
				//	Qty = Delivered	
				if (sLine.sameOrderLineUOM())
					iLine.setQtyEntered(sLine.getQtyEntered());
				else
					iLine.setQtyEntered(sLine.getMovementQty());
				iLine.setQtyInvoiced(sLine.getMovementQty());
				iLine.saveEx(get_TrxName());
				//
				sLine.setIsInvoiced(true);
				sLine.saveEx(get_TrxName());
			}
		}
		return invoice;
	}	//	createInvoice
	
	private String splitInvoice()
	{ 
		MDocType dt = (MDocType)invoice.getC_DocType();
		if (dt.get_ValueAsBoolean("isSplitInvoice"))
			return"";
		int c_doctype_ID = new Query(getCtx(), MDocType.Table_Name, "isSplitInvoice ='Y'", get_TrxName())
			.setOnlyActiveRecords(true)
			.setClient_ID()
			.firstId();

		ndD = new MInvoice((MOrder)invoice.getC_Order(), c_doctype_ID, invoice.getDateInvoiced());
		ndD.saveEx();
		for (MInvoiceLine ivl:invoice.getLines(true))
		{
			if (ivl.getC_OrderLine_ID() <= 0)
				continue;
			MOrderLine oLine = (MOrderLine)ivl.getC_OrderLine();
			if (oLine.get_ValueAsBoolean("isSplitInvoice"))
			{
				ivl.setC_Invoice_ID(ndD.getC_Invoice_ID());
				ivl.saveEx();
			}
		}
		invoice.saveEx();
		invoice.renumberLines(10);
		ndD.saveEx();
		
		if (ndD.getLines(true).length ==0)
		{
			ndD.setC_Order_ID(0);
			ndD.delete(true);
		}
		else
		{
			ndD.renumberLines(10);			
		}
		return "";
	}

	


		
	
}
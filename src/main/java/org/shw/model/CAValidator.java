package org.shw.model;
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


import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.core.domains.models.X_C_Invoice;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MBankAccount;
import org.compiere.model.MBankStatement;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.MCharge;
import org.compiere.model.MClient;
import org.compiere.model.MCommissionAmt;
import org.compiere.model.MCommissionDetail;
import org.compiere.model.MCostDetail;
import org.compiere.model.MDocType;
import org.compiere.model.MElementValue;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInventory;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MInvoiceTax;
import org.compiere.model.MMatchInv;
import org.compiere.model.MMovement;
import org.compiere.model.MMovementLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MPaySelection;
import org.compiere.model.MPaySelectionCheck;
import org.compiere.model.MPaySelectionLine;
import org.compiere.model.MPayment;
import org.compiere.model.MPaymentTerm;
import org.compiere.model.MPriceList;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPO;
import org.compiere.model.MProduction;
import org.compiere.model.MProductionLine;
import org.compiere.model.MProject;
import org.compiere.model.MProjectIssue;
import org.compiere.model.MProjectLine;
import org.compiere.model.MTax;
import org.compiere.model.MTaxCategory;
import org.compiere.model.MTimeExpense;
import org.compiere.model.MTimeExpenseLine;
import org.compiere.model.MUOM;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Language;
import org.compiere.util.Msg;

/**
 *	Validator for Customization Central America
 *	
 *  @author Susanne Calderon Systemhaus Westfalia
 */
public class CAValidator implements ModelValidator

{
	public CAValidator ()
	{
		super ();
	}
	
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(CAValidator.class);
	/** Client			*/
	private int		m_AD_Client_ID = -1;
	
	/**
	 *	Initialize Validation
	 *	@param engine validation engine 
	 *	@param client client
	 */
	public void initialize (ModelValidationEngine engine, MClient client)
	{
		//client = null for global validator
		if (client != null) {	
			m_AD_Client_ID = client.getAD_Client_ID();
			log.info(client.toString());
		}
		else  {
			log.info("Initializing global validator: "+this.toString());
		}

		//	Tables to be monitored
		engine.addModelChange(MOrder.Table_Name, this);
		engine.addModelChange(MOrderLine.Table_Name, this);
		engine.addModelChange(MInvoice.Table_Name, this);
		engine.addModelChange(MInvoiceLine.Table_Name, this);
		engine.addModelChange(MCommissionDetail.Table_Name, this);
		engine.addModelChange(MTimeExpenseLine.Table_Name, this);
		engine.addModelChange(MPaySelectionLine.Table_Name, this);
		engine.addModelChange(MInvoiceTax.Table_Name, this);		
		engine.addModelChange(MPaymentTerm.Table_Name, this);
		engine.addModelChange(MTax.Table_Name, this);
		engine.addModelChange(MTaxCategory.Table_Name, this);
		engine.addModelChange(MUOM.Table_Name, this);
		engine.addModelChange(MProduct.Table_Name, this);
		engine.addModelChange(MCharge.Table_Name, this);
		engine.addModelChange(MElementValue.Table_Name, this);
		engine.addModelChange(MDocType.Table_Name, this);
		engine.addModelChange(MAllocationHdr.Table_Name, this);
		engine.addModelChange(MProductPO.Table_Name, this);
		engine.addModelChange(MCostDetail.Table_Name, this);
		engine.addModelChange(MPaySelectionLine.Table_Name, this);
		engine.addModelChange(MProductionLine.Table_Name, this);
		

		engine.addDocValidate(MPaySelection.Table_Name	, this);
		engine.addDocValidate(MBankStatement.Table_Name, this);
		engine.addDocValidate(MOrder.Table_Name, this);
		engine.addDocValidate(MPayment.Table_Name, this);
		engine.addDocValidate(MTimeExpense.Table_Name, this);
		engine.addDocValidate(MMovement.Table_Name, this);
		engine.addDocValidate(MInventory.Table_Name, this);
		engine.addDocValidate(MProjectIssue.Table_Name, this);
		engine.addDocValidate(MProduction.Table_Name, this);
		engine.addDocValidate(MAllocationHdr.Table_Name, this);
		engine.addDocValidate(MInOut.Table_Name, this);
		engine.addDocValidate(MInvoice.Table_Name, this);
		
		
		//	Documents to be monitored
	//	engine.addDocValidate(MInvoice.Table_Name, this);

	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	Called after PO.beforeSave/PO.beforeDelete
     *	when you called addModelChange for the table
     *	@param po persistent object
     *	@param type TYPE_
     *	@return error message or null
     *	@exception Exception if the recipient wishes the change to be not accept.
     */
	public String modelChange (PO po, int type) throws Exception
	{
		String error = "";
		log.info(po.get_TableName() + " Type: "+type);

		if (type == ModelValidator.TYPE_BEFORE_NEW || type == ModelValidator.TYPE_BEFORE_CHANGE ){
			if (po.get_TableName().equals(MOrder.Table_Name)) {
				error = User4Mandatory(po);
				if (po.is_ValueChanged(MOrder.COLUMNNAME_C_DocTypeTarget_ID) || po.is_ValueChanged(MOrder.COLUMNNAME_M_PriceList_ID))
					error = controlPriceListDocType(po);
				;

			}			

			if (po instanceof MProductPO) {
				error = productPOCheckCurrentVendor(po);
			}

		}

		if (po instanceof MOrderLine) 
		{
			if (type == ModelValidator.TYPE_AFTER_NEW) {
				error = updateCreditLimitControl(po);
			}
			else if (type == ModelValidator.TYPE_AFTER_CHANGE)				 
			{
				if ( (po.is_ValueChanged(MOrderLine.COLUMNNAME_M_Product_ID)
						|| po.is_ValueChanged(MOrderLine.COLUMNNAME_PriceEntered)
						||po.is_ValueChanged(MOrderLine.COLUMNNAME_QtyEntered))) {
					error = updateCreditLimitControl(po);
				}			 
			}		 
		}

		if (type == ModelValidator.TYPE_AFTER_CHANGE ){
			if (po.get_TableName().equals(MOrder.Table_Name))
				error = updateOrderLines(po);
			if(po.get_TableName().equals(MInvoice.Table_Name)) {
				error = updateInvoiceLines(po);	

			}
			if (po.get_TableName().equals(MCommissionDetail.Table_Name)) {
				error = commissionAmtUpdate(po);
			}
			if (po instanceof MProduct || po instanceof MCharge || po instanceof MDocType || po instanceof MUOM
					|| po instanceof MElementValue || po instanceof MTax || po instanceof MTaxCategory || po instanceof MPaymentTerm) {
				error = updateTranslation(po);
			}



		}
		if (type == ModelValidator.TYPE_BEFORE_NEW ) {
			if( po.get_TableName().equals(MOrder.Table_Name)) {
				error = UpdatePaymentRule(po);
				if (po.is_ValueChanged(MOrder.COLUMNNAME_DateOrdered))
					UpdateDatePromised(po);
			}
			if (po instanceof MPaySelectionLine)
				error = paySelectionLineUpdatebpartnerName(po);
		}

		if (type == ModelValidator.TYPE_BEFORE_CHANGE && po.get_TableName().equals(MProductionLine.Table_Name))
			if (po.is_ValueChanged(MProductionLine.COLUMNNAME_QtyUsed)){
				error = productionLineUpdateMovementQtyFromQtyUsed(po);
			}

		if (type == ModelValidator.TYPE_BEFORE_DELETE) {
			if (po instanceof MAllocationHdr)
				error = allocationHdrBeforeDelete(po);
		}

		if (type == ModelValidator.TYPE_BEFORE_CHANGE || type == ModelValidator.TYPE_BEFORE_NEW) {
			if ((po.get_ColumnIndex("C_BPartner_ID") > 0 && po.is_ValueChanged("C_BPartner_ID")) 
					&& po.get_ColumnIndex(X_C_Invoice.COLUMNNAME_User2_ID) > 0) {
				int C_BPartner_ID = po.get_ValueAsInt("C_BPartner_ID");
				ArrayList<Object> params = new ArrayList<>();
				params.add(C_BPartner_ID);
				params.add(Env.getAD_Client_ID(po.getCtx()));
				BigDecimal User2_ID = DB.getSQLValueBD(po.get_TrxName(), 
						"select getUser2_ID(?,?) ",
						params);
				po.set_ValueOfColumn(X_C_Invoice.COLUMNNAME_User2_ID, User2_ID);
			}
			if ((po.get_ColumnIndex("M_Product_ID") > 0
					&& po.is_ValueChanged("M_Product_ID")) && po.get_ColumnIndex(X_C_Invoice.COLUMNNAME_User1_ID) > 0) {
				int M_Product_ID = po.get_ValueAsInt("M_Product_ID");
				ArrayList<Object> params = new ArrayList<>();
				params.add(M_Product_ID);
				params.add(Env.getAD_Client_ID(po.getCtx()));
				BigDecimal User1_ID = DB.getSQLValueBD(po.get_TrxName(), 
						"select getUser1_ID(?,?) ", params);
				po.set_ValueOfColumn(X_C_Invoice.COLUMNNAME_User1_ID, User1_ID);
				BigDecimal User3_ID = DB.getSQLValueBD(po.get_TrxName(), 
						"Select getUser3_ID(?,?) ",params);
				po.set_ValueOfColumn(X_C_Invoice.COLUMNNAME_User3_ID, User3_ID);
			}


		}
		return error;
	}

	/**
	 * 
	 */private String productionLineUpdateMovementQtyFromQtyUsed(PO po){
		 MProductionLine productionLine = (MProductionLine)po;
			if ( productionLine.getM_Product().isBOM() && productionLine.getM_Product().isStocked() )
			{
				productionLine.setMovementQty(productionLine.getQtyUsed().negate());
			}
		 return "";
	 }	//	modelChange


	private String User4Mandatory(PO po) {
		MOrder order = (MOrder)po;
		if(order.getC_DocTypeTarget().isHasCharges() && order.getUser4_ID() <=0)
		{
			return "EL campo retaceo es obligatorio";
		}
		return "";
	}
	

	
	
	private String UpdatePaymentRule(PO po) {
		MOrder order = (MOrder)po;
		if(order.getC_DocTypeTarget().getDocSubTypeSO().equals(MDocType.DOCSUBTYPESO_POSOrder))
		{
			order.setPaymentRule(MOrder.PAYMENTRULE_Cash);
		}
		return "";
	}
	
	private String UpdateDatePromised(PO po) {
		MOrder order = (MOrder)po;
		if (!order.isSOTrx())
			return "";
		Timestamp datePromised = order.getDateOrdered();
		try {
			GregorianCalendar cal = new GregorianCalendar(Language.getLoginLanguage().getLocale());
				cal.setTimeInMillis(datePromised.getTime());
				cal.add(Calendar.DAY_OF_YEAR, +3);	//	next
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				datePromised =  new Timestamp (cal.getTimeInMillis());
				datePromised = nextBusinessDay(datePromised, order.get_TrxName());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		order.setDatePromised(datePromised);
		return "";
	}
	
	
	private String updateOrderLines(PO po) {   
		MOrder order = (MOrder)po;
		if(order.is_ValueChanged("User4_ID"))
		{
			for(MOrderLine orderLine:order.getLines()) 
			{
				orderLine.setUser4_ID(order.getUser4_ID());
				orderLine.saveEx();            
			}
		}

		if(order.is_ValueChanged(MOrder.COLUMNNAME_C_Project_ID))
		{
			for(MOrderLine orderLine:order.getLines()) 
			{
				orderLine.setC_Project_ID(order.getC_Project_ID());;
				orderLine.saveEx();            
			}
		}
		
		return "";
	}
	
	private String beforeCompleteIsControlLimitPrice(PO po) {   
		String result = "";
		MOrder order = (MOrder)po;
		if (!order.isSOTrx())
			return result;
		if (!order.get_ValueAsBoolean("isControlLimitPrice")) {			
			return "";
		}
		StringBuffer sqlRole = new StringBuffer();
		sqlRole.append("SELECT count(*) FROM AD_Role r  WHERE r.IsActive='Y' ");
		sqlRole.append("	AND EXISTS (SELECT * FROM AD_User_Roles ur");
		sqlRole.append("	WHERE r.AD_Role_ID=ur.AD_Role_ID  AND ur.AD_User_ID=?) ");
		sqlRole.append("	AND r.IsDiscountUptoLimitPrice = 'N' ");
		int noRole = DB.getSQLValueEx(order.get_TrxName(), sqlRole.toString(), Env.getAD_User_ID(order.getCtx()));
		if (noRole>0) {
			order.set_ValueOfColumn("isControlLimitPrice", false);
			order.set_ValueOfColumn("limitPriceApprovedBy", Env.getAD_User_ID(order.getCtx()));
			order.saveEx();
			return result;			
		}
		else {

			result = "Excede Limite de Precio";		
			}
		return result;
	}
	
	private String updateCreditLimitControl(PO po) {	
		MOrderLine orderLine = (MOrderLine)po;
		if (orderLine.getParent().isSOTrx()) {
			String sql = "";
			int no = 0;
			ArrayList<Object> params = new ArrayList<Object>();
			BigDecimal creditLimit = orderLine.getParent().getC_BPartner().getSO_CreditLimit();
			BigDecimal openBalance  = orderLine.getParent().getC_BPartner().getTotalOpenBalance();
			if (openBalance.compareTo(Env.ZERO) <= 0)
				return "";
			String rejectStatus = (creditLimit.compareTo(orderLine.getParent().getGrandTotal().add(openBalance)) < 0)
					&& (creditLimit.compareTo(Env.ZERO)) > 0?"CL":"NO";			
					params.add(rejectStatus);
					sql = "UPDATE C_Order i "
							+ " SET DocStatus_RejectStatus= ?"
							+ " WHERE C_Order_ID=" + orderLine.getC_Order_ID();				
					no = DB.executeUpdateEx(sql, params.toArray(), po.get_TrxName());
					if (!rejectStatus.equals("NO"))
						return "";
					sql = "SELECT 1 "
							+ "FROM C_Invoice i "
							+ "LEFT JOIN C_PaymentTerm pt ON(pt.C_PaymentTerm_ID = i.C_PaymentTerm_ID) "
							+ "WHERE i.IsSotrx = 'Y' "
							+ "AND i.C_BPartner_ID=? "
							+ "AND i.IsPaid = 'N' "
							+ "AND (paymenttermDueDays(i.C_PaymentTerm_ID, i.DateInvoiced, getDate()) - pt.GraceDays) > 0";
					int noInvoicesDue = DB.getSQLValueEx(null, sql, orderLine.getParent().getC_BPartner_ID());
					if (noInvoicesDue>0) {
						sql = "UPDATE C_Order i "
								+ " SET DocStatus_RejectStatus='DI' "
								+ "WHERE C_Order_ID=" + orderLine.getC_Order_ID();
						no = DB.executeUpdate(sql, po.get_TrxName());			
					}
		}
		return "";
	}
	
	private String beforeCompleteOrderControlCreditStop(PO po) {  
		MOrder order = (MOrder)po;
		String result = "";
		BigDecimal newAmt = order.getC_BPartner().getTotalOpenBalance().add(order.getGrandTotal());
		BigDecimal creditLimit = order.getC_BPartner().getSO_CreditLimit();
		if (!order.isSOTrx() 
        		|| order.getC_BPartner().getSO_CreditLimit() == Env.ZERO
        		|| order.getC_BPartner().getTotalOpenBalance().compareTo(Env.ZERO) <=0)
			return result;
		BigDecimal additionalAmt = order.getGrandTotal();
		StringBuffer sqlRole = new StringBuffer();
		sqlRole.append("SELECT count(*) FROM AD_Role r  WHERE r.IsActive='Y' ");
		sqlRole.append("	AND EXISTS (SELECT * FROM AD_User_Roles ur");
		sqlRole.append("	WHERE r.AD_Role_ID=ur.AD_Role_ID AND ur.AD_User_ID=?) ");
		sqlRole.append("	AND r.IsCanApproveCreditLimit = 'Y' ");
		int noRole = DB.getSQLValueEx(order.get_TrxName(), sqlRole.toString(), Env.getAD_User_ID(order.getCtx()));
		if (noRole>0) {
			//order.set_ValueOfColumn("DocStatus_RejectStatus", "NO");
			order.set_ValueOfColumn("creditApprovedBy", Env.getAD_User_ID(order.getCtx()));
			order.saveEx();
			return result;			
		}
		
		if (additionalAmt == null || additionalAmt.signum() == 0)
			return "";
		//
		//	Nothing to do
		if (creditLimit.compareTo(newAmt) < 0) {
			return "Venta exede Limite de Credito";
		}
		//	Above (reduced) Credit Limit
		
			int noInvoicesDue = 0;
			StringBuffer sql = new StringBuffer("SELECT 1 "
					+ "FROM C_Invoice i "
					+ "LEFT JOIN C_PaymentTerm pt ON(pt.C_PaymentTerm_ID = i.C_PaymentTerm_ID) "
					+ "WHERE i.IsSotrx = 'Y' "
					+ "AND i.C_BPartner_ID=? "
					+ "AND i.IsPaid = 'N' "
					+ "AND (paymenttermDueDays(i.C_PaymentTerm_ID, i.DateInvoiced, getDate()) - pt.GraceDays) > 0");
			if(order.getC_BPartner().getDunningGrace() != null) {
				sql.append(" AND paymenttermDueDate(i.C_PaymentTerm_ID, i.DateInvoiced) >= ?");
				noInvoicesDue = DB.getSQLValueEx(null, sql.toString(), order.getC_BPartner_ID(), order.getC_BPartner().getDunningGrace());
			} else {
				noInvoicesDue = DB.getSQLValueEx(null, sql.toString(), order.getC_BPartner_ID());
			}
			if (noInvoicesDue == 1) {
				order.set_ValueOfColumn("DocStatus_RejectStatus", "DI");
				order.saveEx();
				return "Cliente tiene facturas pendientes";
			}

		//	is OK
		order.saveEx();
		return "";
	
	}
	



	
	private String updateInvoiceLines(PO po) { 
		MInvoice invoice = (MInvoice)po;
		if(invoice.is_ValueChanged("User4_ID")) 
		{
			for(MInvoiceLine invoiceLine:invoice.getLines()) 
			{
				invoiceLine.setUser4_ID(invoice.getUser4_ID());
				invoiceLine.saveEx();            
			}

			if(invoice.is_ValueChanged(MInvoice.COLUMNNAME_C_Project_ID))
			{
				for(MInvoiceLine invoiceLine:invoice.getLines()) 
				{
					invoiceLine.setC_Project_ID(invoice.getC_Project_ID());
					invoiceLine.saveEx();            
				}
			}
		}
		return "";
	}
	

	

	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("CAValidator");
		return sb.toString ();
	}	//	toString
	

/**
 *	User Login.
	 *	Called when preferences are set
	 *	@param AD_Org_ID org
	 *	@param AD_Role_ID role
	 *	@param AD_User_ID user
	 *	@return error message or null
	 */
	public String login (int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		log.info("AD_User_ID=" + AD_User_ID);
		
		  StringBuffer sqlRole = new StringBuffer();
		  sqlRole.append("SELECT count(*) FROM AD_Role r  WHERE r.IsActive='Y' ");
		  sqlRole.append("	AND EXISTS (SELECT * FROM AD_User_Roles ur"); 
		  sqlRole.append("	WHERE r.AD_Role_ID=ur.AD_Role_ID AND ur.IsActive='Y' AND ur.AD_User_ID= " + AD_User_ID); 
		  sqlRole.append("	AND r.iscanapprovecreditlimit = 'Y' )"); 
		  int noRole = DB.getSQLValueEx(null, sqlRole.toString());
		  if (noRole>0) { Env.setContext(Env.getCtx(), "#CanApproveCreditlimit", true);
		  } else Env.setContext(Env.getCtx(), "#CanApproveCreditlimit", false);
		  
		  StringBuffer sqlRolePriceLimit = new StringBuffer(); 
		  sqlRolePriceLimit.append("SELECT count(*) FROM AD_Role r  WHERE r.IsActive='Y' ");
		  sqlRolePriceLimit.append("	AND EXISTS (SELECT * FROM AD_User_Roles ur");
		  sqlRolePriceLimit. append("	WHERE r.AD_Role_ID=ur.AD_Role_ID AND ur.IsActive='Y' AND ur.AD_User_ID= " + AD_User_ID); 
		  sqlRolePriceLimit.append("	AND r.OverwritePriceLimit = 'Y') "); 
		  noRole = DB.getSQLValueEx(null, sqlRolePriceLimit.toString()); 
		  if (noRole>0) {
		  Env.setContext(Env.getCtx(), "#CanApprovePricelimit", true); 
		  } 
		  else
		  Env.setContext(Env.getCtx(), "#CanApprovePricelimit", false);
		 
		
		return null;
	}	//	login

	
	/**
	 *	Get Client to be monitored
	 *	@return AD_Client_ID client
	 */
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}	//	getAD_Client_ID
	

	public String docValidate (PO po, int timing)
	{
		String error = "";
        if (ModelValidator.TIMING_AFTER_COMPLETE == timing) {
        	//if (po instanceof MPaySelection)
        	//	error = PayselectionGeneratePayment(po);
        	if (po instanceof MPayment)
        		error = PaymentAutoReconcile(po);
        	if (po instanceof MTimeExpense)
        		error = TimeExpenseReportCreateProjectIssue(po);
        	if (po instanceof MOrder)
        		error = updateUser4Order(po);
        	if (po instanceof MInOut)
        		error = updateUser4MaterialReceipt(po);
        }
        if (ModelValidator.TIMING_AFTER_PREPARE == timing) {
        	
        	if (po instanceof MOrder) {
        		error = OrderSetPrecision(po);
        	}
        	if (po instanceof MInvoice) {
        		error = InvoiceSetPrecision(po);
        	}
        }
        if (ModelValidator.TIMING_BEFORE_POST == timing) {
        	if (po.get_ColumnIndex("Posted") > 0)        		
        	//error = factAcct_UpdateDocumentNO(po)
        		;
        }
        if (ModelValidator.DOCTIMING_BEFORE_PREPARE == timing) {
        	if (po instanceof MInvoice) {

				error = UpdateCreditMemo(po);
        	}   	
        	if (po instanceof MMovement) {
        		error = movementBeforePrepare(po);        		
        	}
        	if (po instanceof MInOut) {
        		error = inoutLineSetOrderLine(po);
        	}
;        }
        if (ModelValidator.TIMING_BEFORE_VOID == timing) {
        	if (po instanceof MPaySelection)
        		error = voidIncludedPayments(po);
        }
        
        if (ModelValidator.TIMING_BEFORE_COMPLETE == timing) {
        	if (po instanceof MOrder) {
        		error = testQtyOnhand(po);
        		if (!error.equals("")) {
        			return error;
        		}	
        	    error = beforeCompleteIsControlLimitPrice(po);
        	    if (!error.equals("")) {
        			return error;
        		}	
                error = beforeCompleteOrderControlCreditStop(po);
        	}
        }
        
        if (ModelValidator.TIMING_AFTER_COMPLETE==timing) {        	
        	if (po instanceof MInvoice) {
        		error = reversal_CorrectMatches(po);
        		 if (!error.equals("")) {
         			return error;        		
        		}	
        	}
        	
        }
        
		return error;
	}

	/**
	 * @param po
	 */
	private String voidIncludedPayments(PO po) {
		List<MPaySelectionCheck> paySelectionChecks = MPaySelectionCheck.get(po.getCtx(), po.get_ID(), po.get_TrxName());
		for (MPaySelectionCheck paySelectionCheck:paySelectionChecks) {
			MPayment payment = (MPayment)paySelectionCheck.getC_Payment();
			try
			{
				payment.voidIt();
				payment.saveEx();
			}
			catch (NumberFormatException ex)
			{
				//logger.log(Level.SEVERE, "DocumentNo=" + paySelectionCheck.getDocumentNo(), ex);
			}
		}
		return "";
	}	//	docValidate
	


	private String commissionAmtUpdate(PO A_PO)
	{
		MCommissionDetail commissionDetail = (MCommissionDetail)A_PO;
		MCommissionAmt commissionAmt = new MCommissionAmt(A_PO.getCtx(), commissionDetail.getC_CommissionAmt_ID(),commissionDetail.get_TrxName());
		commissionAmt.updateCommissionAmount();
		commissionAmt.saveEx();
		return "";
	}

	private String OrderSetPrecision(PO A_PO) {
		MOrder order = (MOrder)A_PO;
		if (order.getGrandTotal().scale() > order.getC_Currency().getStdPrecision())
			order.setGrandTotal(order.getGrandTotal().setScale(order.getC_Currency().getStdPrecision(), BigDecimal.ROUND_HALF_UP));
		order.saveEx();
		return "";
	}
	


	private String InvoiceSetPrecision(PO A_PO) {
		MInvoice invoice = (MInvoice)A_PO;
		if (invoice.getGrandTotal().scale() > invoice.getC_Currency().getStdPrecision())
			invoice.setGrandTotal(invoice.getGrandTotal().setScale(invoice.getC_Currency().getStdPrecision(), BigDecimal.ROUND_HALF_UP));
		invoice.saveEx();
		return "";
	}
	
	private String PaymentAutoReconcile(PO A_PO) {
		MPayment payment = (MPayment)A_PO;
		//if (payment.getTenderType().equals(MPayment.TENDERTYPE_Cash))
		//	return "";
		MBankAccount bankAccount = (MBankAccount)payment.getC_BankAccount();
		Boolean isAutoReconciled = bankAccount.get_ValueAsBoolean("isAutoReconciled");
		if(isAutoReconciled) {
			int[] bsls = MBankStatementLine.getAllIDs(MBankStatementLine.Table_Name, "c_Payment_ID=" + payment.getC_Payment_ID(), payment.get_TrxName());
			MBankStatementLine bsl = null;
			if (bsls.length==0) {
				bsl = MBankStatement.addPayment(payment);
			}
			else
				bsl = new MBankStatementLine(payment.getCtx(), bsls[0], payment.get_TrxName());
			//if (bsl.getParent().getC_BankAccount().getC_Bank().getBankType().equals((MBank.BANKTYPE_CashJournal)))
			//		bsl.getParent().processIt("CO");
			//		bsl.getParent().saveEx();
			}
		return "";
	}
	

	private String TimeExpenseReportCreateProjectIssue(PO A_PO) {
		MTimeExpense timeExpense = (MTimeExpense)A_PO;
		for (MTimeExpenseLine expenseLine:timeExpense.getLines()) {
			createLine(expenseLine, expenseLine.getQty());
		}
		return "";
	}
	
	private void createLine(MTimeExpenseLine timeExpenseLine, BigDecimal movementQty) {
		//	Create Issue
		if (timeExpenseLine.getC_Project_ID() == 0)
			return;
		MProject project = (MProject)timeExpenseLine.getC_Project();
		MTimeExpense timeExpense = (MTimeExpense)timeExpenseLine.getS_TimeExpense();
		MProjectIssue projectIssue = new MProjectIssue(project);
		projectIssue.setMandatory(timeExpense.getM_Locator_ID(), timeExpenseLine.getM_Product_ID(), timeExpenseLine.getQty());
		
		projectIssue.setMovementDate(timeExpenseLine.getDateExpense());
		projectIssue.setDescription(timeExpenseLine.getDescription());
		projectIssue.setS_TimeExpenseLine_ID(timeExpenseLine.getS_TimeExpenseLine_ID());
		if (timeExpenseLine.getC_ProjectPhase_ID() != 0)
			projectIssue.set_ValueOfColumn(MInOutLine.COLUMNNAME_C_ProjectPhase_ID, timeExpenseLine.getC_ProjectPhase_ID());
		if (timeExpenseLine.getC_ProjectTask_ID()!=0)
			projectIssue.set_ValueOfColumn(MInOutLine.COLUMNNAME_C_ProjectTask_ID, timeExpenseLine.getC_ProjectTask_ID());
		projectIssue.saveEx();
		//projectIssue.completeIt();

		//	Find/Create Project Line
		//	Find/Create Project Line
		MProjectLine projectLine = new MProjectLine(project);
		projectLine.setMProjectIssue(projectIssue);		//	setIssueBigDecimal feeAmt = (BigDecimal)expenseLine.get_Value("FeeAmt");
		BigDecimal travelCost =  (BigDecimal)timeExpenseLine.get_Value("TravelCost");
		BigDecimal prepaymentAmt =  (BigDecimal)timeExpenseLine.get_Value("PrepaymentAmt");
		BigDecimal feeAmt = (BigDecimal)timeExpenseLine.get_Value("FeeAmt");
		//projectLine.setCommittedAmt(timeExpenseLine.getConvertedAmt().add(timeExpenseLine.getPriceReimbursed()));
		projectLine.setCommittedAmt(timeExpenseLine.getConvertedAmt().add(feeAmt).add(prepaymentAmt).add(travelCost));
		projectLine.setM_Product_ID(projectIssue.getM_Product_ID());
		projectLine.set_ValueOfColumn(MTimeExpenseLine.COLUMNNAME_S_TimeExpenseLine_ID, projectIssue.getS_TimeExpenseLine_ID());
		projectLine.saveEx();
		//return "@Created@ " + counter.get();		
	}
	


	private String UpdateCreditMemo(PO A_PO) {
		MInvoice invoice = (MInvoice)A_PO;
		if (!invoice.getC_DocTypeTarget().getDocBaseType().equals(MDocType.DOCBASETYPE_ARCreditMemo)
				|| invoice.getM_RMA_ID() ==0)
			return "";
		invoice.setPOReference(getInvoiceDocNo(invoice));
		return "";
	}
	
	private String getInvoiceDocNo(MInvoice invoice) {
		String docNoList = "";
		String dateList = "";
		
			String sql = "SELECT distinct iorg.documentno, iorg.dateInvoiced, iorg.docstatus FROM C_Invoice i " + 
					" INNER JOIN C_InvoiceLine ivl on i.c_INvoice_ID=ivl.c_Invoice_ID " + 
					" INNER JOIN C_OrderLine ol on ivl.C_OrderLine_ID=ol.c_OrderLine_ID " + 
					" LEFT JOIN c_InvoiceLine il on il.c_OrderLIne_ID=ol.c_OrderLine_ID and il.c_INvoiceline_ID <> ivl.c_INvoiceline_ID " + 
					" LEFT JOIN C_Invoice iorg on il.c_Invoice_ID=iorg.c_Invoice_ID\r\n" + 
					" WHERE i.C_Invoice_ID=? and iorg.docstatus in ('CO','CL', 'VO') order by iorg.docstatus ";
			PreparedStatement pstmt = null;
			try
			{
				pstmt = DB.prepareStatement(sql, invoice.get_TrxName());
				pstmt.setInt(1, invoice.getC_Invoice_ID());
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					docNoList = docNoList + rs.getString(1);
					Timestamp date = rs.getTimestamp(2);
					String stringDate = new SimpleDateFormat("dd/MM/yyyy").format(date); 
					dateList = dateList + " " + stringDate;
				}
				rs.close();
				pstmt.close();
				pstmt = null;
				if (docNoList == null)
					docNoList = "";
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, "getName", e);
			}
			finally
			{
				try
				{
					if (pstmt != null)
						pstmt.close ();
				}
				catch (Exception e)
				{}
				pstmt = null;
			}
		return docNoList + ";" + dateList;
	}

	private String testQtyOnhand(PO po) {
		MOrder order = (MOrder)po;
		if (!order.isSOTrx())
			return "";
		String error = "";
		Boolean nottest = (order.getC_DocType().getDocSubTypeSO().equals(MOrder.DocSubTypeSO_RMA) ||
				order.getC_DocType().getDocSubTypeSO().equals(MOrder.DocSubTypeSO_Standard) );
		if (nottest)
			return "";
		StringBuffer sql = new StringBuffer("SELECT COALESCE(SUM(s.QtyOnHand),0)")
				.append(" FROM M_Storage s")
				.append(" WHERE s.M_Product_ID=?");
		sql.append(" AND EXISTS (SELECT 1 FROM M_Locator l WHERE s.M_Locator_ID=l.M_Locator_ID AND l.M_Warehouse_ID=?)");
		ArrayList<Object> params = new ArrayList<Object>();

		for (MOrderLine orderLine:order.getLines())
		{
			if (orderLine.isDescription() || orderLine.getC_Charge_ID() != 0 || !orderLine.getM_Product().isStocked()
					|| orderLine.getQtyOrdered().signum()<0)
				continue;
			StringBuffer sqlFinal = new StringBuffer(sql);
			params.clear();
			params.add(orderLine.getM_Product_ID());
			// Warehouse level
			params.add(order.getM_Warehouse_ID());
			// With ASI
			if (orderLine.getM_AttributeSetInstance_ID() != 0) {
				sqlFinal.append(" AND s.M_AttributeSetInstance_ID=?");
				params.add(orderLine.getM_AttributeSetInstance_ID());
			}
			//
			BigDecimal qtyOnHand = DB.getSQLValueBD(order.get_TrxName(), sqlFinal.toString(), params);
			if (qtyOnHand.subtract( orderLine.getQtyOrdered()).signum()< 0)
				error = error + Msg.translate(order.getCtx(), "InsufficientQtyAvailable") + " " + orderLine.getM_Product().getName() + "; ";
		}

		return error;

	}

	private String updateTranslation(PO po) {
		String additionalSQL = " ";
		String tableName = po.get_TableName();
		Boolean ischange = tableName.equals("C_DocType")? po.is_ValueChanged("Name"):
			(po.is_ValueChanged("Name") || po.is_ValueChanged("Description"));
		if (!ischange)
			return "";
		String translationTableName = po.get_TableName().concat("_Trl");
		String columnName = po.get_TableName().concat("_ID");
		int ID = po.get_ValueAsInt(columnName);

		if (!tableName.equals("C_DocType")){
			additionalSQL = "', description ='" + po.get_ValueAsString("Description");
		}

		List<Object> params = new ArrayList<Object>();
		params.add(ID);
		params.add(Env.getAD_Language(po.getCtx()));
		String sql = "Update " + translationTableName + " set name ='" + po.get_Value("Name") + additionalSQL +
				"' where " + columnName +" =? AND AD_Language = ?";
		DB.executeUpdateEx(sql,params.toArray(new Object[params.size()]), po.get_TrxName());
		return ""; 
	}

	private String updateUser4Order(PO po) {
		MOrder order = (MOrder)po;
		if (order.isSOTrx())
			return "";
		if (order.getUser4_ID() > 0) {
			MElementValue user4 = (MElementValue)order.getUser4();
			user4.set_CustomColumn("ValidFrom", order.getDateOrdered());
			user4.saveEx();
		}
		return "";
	}

	private String updateUser4MaterialReceipt(PO po) {
		MInOut inOut = (MInOut)po;
		if (inOut.isSOTrx())
			return "";
		if (inOut.getUser4_ID() > 0) {
			MElementValue user4 = (MElementValue)inOut.getUser4();
			user4.set_CustomColumn("ValidTo", inOut.getMovementDate());
			user4.saveEx();
		}
		return "";
	}
	
	private String allocationHdrBeforeDelete(PO po) {
		MAllocationHdr allocationHdr = (MAllocationHdr)po;
		
		Arrays.stream(allocationHdr.getLines(false))
		.forEach( allocationLine -> {
			if (allocationLine.getC_Invoice_ID() != 0) {
				MInvoice invoice = (MInvoice)allocationLine.getC_Invoice();
				invoice.setIsPaid(false);
				invoice.saveEx();
			}
			if (allocationLine.getC_Payment_ID() !=0) {
				MPayment payment = (MPayment)allocationLine.getC_Payment();
				payment.setIsAllocated(false);
				payment.saveEx();
			}
		});
		return "";
	}
	
	private String productPOCheckCurrentVendor(PO po) {
		MProductPO productPO = (MProductPO)po;
		if (!productPO.isCurrentVendor())
			return "";
		String sql = "Select count(*) from m_Product_PO where m_Product_ID=? and iscurrentvendor = 'Y'";
		List<Object> params = new ArrayList<>();
		params.add(productPO.getM_Product_ID());
		int no = DB.getSQLValueEx(productPO.get_TrxName(), sql, params);
 
		return no==0? "": "Ya existe un proveedor predeterminado";
	}
	
	private String paySelectionLineUpdatebpartnerName(PO po) {
		MPaySelectionLine line = (MPaySelectionLine)po;
		if (line.get_ValueAsString("bpartnername") == "")
			line.set_CustomColumn("bpartnername", line.getC_BPartner().getName());
		return "";		
	}
	
	 public Timestamp nextBusinessDay (Timestamp day, String trxName) throws SQLException
	{
		if (day == null)
			day = new Timestamp(System.currentTimeMillis());
		//
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(day);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		//
		//begin Goodwill (www.goodwill.co.id)
		// get Holiday
		boolean isHoliday = true;
		do
		{
			int dow = cal.get(Calendar.DAY_OF_WEEK);
			if (dow == Calendar.SATURDAY)
				cal.add(Calendar.DAY_OF_YEAR, 2);
			else if (dow == Calendar.SUNDAY)
				cal.add(Calendar.DAY_OF_YEAR, 1);
			java.util.Date temp = cal.getTime();
			String sql = "SELECT Date1 FROM C_NonBusinessDay WHERE IsActive ='Y' AND Date1=?";
			//PreparedStatement pstmt = Adempiere.prepareStatement(sql);
			PreparedStatement pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setTimestamp(1,new Timestamp(temp.getTime()));
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
			{
				cal = new GregorianCalendar();
				cal.setTime(temp);
				cal.add(Calendar.DAY_OF_YEAR,1);
			}
			else 
				isHoliday = false;
			
			rs.close();
			pstmt.close();

		}
		while (isHoliday);
		// end Goodwill
		
		return new Timestamp (cal.getTimeInMillis());
	}	//	nextBusinessDay	
	 
	 private String controlPriceListDocType(PO po) {
		 String error = "";
		 MOrder order = (MOrder)po;
		 if (!order.isSOTrx() || order.getC_POS_ID() >0)
			 return "";
		 MDocType docType = (MDocType)order.getC_DocTypeTarget();
		 if (docType.getDocSubTypeSO().equals(MDocType.DOCSUBTYPESO_WarehouseOrder)
				 || docType.getDocSubTypeSO().equals(MDocType.DOCSUBTYPESO_ReturnMaterial))
			 return "";
		 Boolean isTaxIncluded = docType.get_ValueAsBoolean(MPriceList.COLUMNNAME_IsTaxIncluded);
		 if (isTaxIncluded != order.getM_PriceList().isTaxIncluded())
			 error = "Lista de Precio y Tipo de documento no coinciden ";
		 return error;
	 }
		public  BigDecimal getQtyOnHand (int M_Locator_ID, 
			int M_Product_ID, int M_AttributeSetInstance_ID, String trxName)
		{
			ArrayList<Object> params = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer("SELECT COALESCE(SUM(s.QtyOnHand),0)")
									.append(" FROM M_Storage s")
									.append(" WHERE s.M_Product_ID=?");
			params.add(M_Product_ID);
			// Warehouse level
			// Locator level
				sql.append(" AND s.M_Locator_ID=?");
				params.add(M_Locator_ID);
			// With ASI
			if (M_AttributeSetInstance_ID != 0) {
				sql.append(" AND s.M_AttributeSetInstance_ID=?");
				params.add(M_AttributeSetInstance_ID);
			}
			//
			BigDecimal retValue = DB.getSQLValueBD(trxName, sql.toString(), params);
			return retValue;
		}	//	getQtyOnHand
		
		private String movementBeforePrepare(PO po) {
			Hashtable<Integer, BigDecimal> storages = new Hashtable<Integer, BigDecimal>();
			MMovement movement = (MMovement)po;
			String errorMsg = "";
			BigDecimal qtyAvailable = Env.ZERO;
			for (MMovementLine movementLine: movement.getLines(true)) {
				int key = movementLine.getM_Product_ID() + movementLine.getM_AttributeSetInstance_ID() + movementLine.getM_Locator_ID();
				qtyAvailable = storages.get(key);			
				if(qtyAvailable == null) {
				qtyAvailable = getQtyOnHand( movementLine.getM_Locator_ID(),
							movementLine.getM_Product_ID(),movementLine.getM_AttributeSetInstance_ID(), po.get_TrxName());
					storages.put(key, qtyAvailable);
				}
				qtyAvailable = qtyAvailable.subtract(movementLine.getMovementQty());
				storages.replace(key, qtyAvailable);
				if (qtyAvailable.compareTo(Env.ZERO)<0) {					
					errorMsg = Msg.translate(po.getCtx(), "InsufficientQtyAvailable") + " " + movementLine.getM_Product().getName();
				break;
				}
			}			
			return errorMsg;
		}
		private String inoutLineSetOrderLine(PO po) {
			MInOut inOut = (MInOut)po;
			if (inOut.isSOTrx() || inOut.getC_Order_ID() <=0) {
				return "";
			}
			Arrays.stream(inOut.getLines(false)).filter(inoutLine -> 
					 inoutLine.getC_OrderLine_ID() <=0)
			.forEach( inOutLine -> {
				MOrder order = (MOrder)inOutLine.getParent().getC_Order();
				for (MOrderLine orderLine : order.getLines()) {
					if (orderLine.getM_Product_ID() == inOutLine.getM_Product_ID() && orderLine.getM_AttributeSetInstance_ID() <=0) {
						inOutLine.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
						inOutLine.saveEx();
						break;
					}
				}
			});
			
			return "";
			
		}
		

		private String reversal_CorrectMatches(PO po) {
			MInvoice invoice = (MInvoice)po;
			if (invoice.isSOTrx() ||
					invoice.getReversal_ID()==0 ||
					invoice.getReversal_ID()>invoice.getC_Invoice_ID())
				return "";
			List <MMatchInv> matchInvs = MMatchInv.getByInvoiceId(invoice.getCtx(), invoice.getReversal_ID(), invoice.get_TrxName());
			for (MMatchInv matchInv:matchInvs) {
				if (matchInv.getC_InvoiceLine().getReversalLine_ID() > matchInv.getC_InvoiceLine_ID())
					continue;
				matchInv.setC_InvoiceLine_ID(matchInv.getC_InvoiceLine().getReversalLine_ID());
				matchInv.saveEx();

			}
			return "";
		}
		
		
		
		

	
		
			
		

	
}	//	Validator
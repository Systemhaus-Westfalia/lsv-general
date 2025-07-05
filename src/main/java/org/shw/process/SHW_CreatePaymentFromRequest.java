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

import java.sql.Timestamp;
import java.util.List;

import org.compiere.model.MBankAccount;
import org.compiere.model.MCharge;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrder;
import org.compiere.model.MPayment;
import org.compiere.model.MProject;
import org.compiere.model.MRequest;
import org.compiere.model.MRequestType;
import org.compiere.model.Query;

/**
 *  Creates Payment from c_invoice, including Aging
 *
 *  @author Susanne Calderon
 */

public class SHW_CreatePaymentFromRequest  extends SHW_CreatePaymentFromRequestAbstract
{	
	
    
    @Override    
    protected void prepare()
    {    	
		super.prepare();
        
    }
    
      
    @Override
    protected String doIt() throws Exception
    {
    	MRequest req = new MRequest(getCtx(), getRecord_ID(), get_TrxName());
    	int defaultaccount = new Query(req.getCtx(), MBankAccount.Table_Name, "", req.get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.firstId(); 
    	MPayment pay = new MPayment(getCtx(), 0, get_TrxName());  	
    	pay.setC_BankAccount_ID(defaultaccount);
    	pay.setDateTrx(new Timestamp (System.currentTimeMillis()));
    	pay.setIsOverUnderPayment(true);
    	pay.setAD_Org_ID(req.getAD_Org_ID());
    	pay.setC_Project_ID(req.getC_Project_ID());
    	MRequestType rtype = (MRequestType)req.getR_RequestType();
    	
    	if (rtype.get_ValueAsInt(MPayment.COLUMNNAME_C_DocType_ID)!= 0)
    		pay.setC_DocType_ID(rtype.get_ValueAsInt(MPayment.COLUMNNAME_C_DocType_ID));
    	else
    		pay.setC_DocType_ID(false);
     	//pay.setIsReceipt(false);
    	int c_charge_ID = req.get_ValueAsInt(MCharge.COLUMNNAME_C_Charge_ID);
    	int c_invoice_ID = req.getC_Invoice_ID();
    	int c_order_PO_ID = req.get_ValueAsInt("c_order_ID");
    	if (c_charge_ID == 0 && c_invoice_ID==0 && c_order_PO_ID==0)
    		return"";
    	if (c_charge_ID != 0)
    	{
        	if (req.get_ValueAsInt("C_BPartnerVendor_ID") == 0)
        		return "Hay que definir el Proveedor";
        	pay.setC_BPartner_ID(req.get_ValueAsInt("C_BPartnerVendor_ID"));
    		pay.setC_Charge_ID(req.get_ValueAsInt(MCharge.COLUMNNAME_C_Charge_ID));
        	pay.setPayAmt(req.getRequestAmt());
    	}
    	else if (c_invoice_ID != 0)
    	{
    		pay.setC_BPartner_ID(req.getC_Invoice().getC_BPartner_ID());
    		pay.setC_Invoice_ID(c_invoice_ID);
    		pay.setPayAmt(req.getC_Invoice().getGrandTotal());
    	}

    	else if (c_order_PO_ID != 0)
    	{
    		MOrder order = new MOrder(getCtx(), c_order_PO_ID, get_TrxName());
    		pay.setC_BPartner_ID(order.getC_BPartner_ID());
    		pay.setC_Order_ID(order.getC_Order_ID());
    		pay.setPayAmt(order.getGrandTotal());
    	}
    	pay.setC_Currency_ID(req.get_ValueAsInt("C_Currency_ID"));
    	pay.setC_Activity_ID(req.getC_Activity_ID());
    	pay.setC_Campaign_ID(req.getC_Campaign_ID());
    	MProject prj = (MProject)req.getC_Project();
    	pay.setUser1_ID(prj.get_ValueAsInt("User1_ID"));
    	pay.set_ValueOfColumn(MRequest.COLUMNNAME_R_Request_ID, req.getR_Request_ID());
    	String whereClause = "c_bpartner_ID = ? and ispaid = 'N' and docstatus in ('CO','CL') and issotrx = 'N' ";
    	List<MInvoice> invoices = new Query(getCtx(), MInvoice.Table_Name, whereClause, get_TrxName())
    		.setParameters(pay.getC_BPartner_ID())
    		.list();
    	String description = "";
    	for (MInvoice inv:invoices)
    	{
    		if (!inv.getC_DocType().getDocBaseType().equals(MDocType.DOCBASETYPE_APCreditMemo))
    			continue;    		
    		description = description + " Nota de Credito: " + inv.getDocumentNo() + " " + inv.getGrandTotal().toString();    		
    	}
    	if (!description.equals(""))
    		pay.setDescription(" " + description);
    	pay.saveEx();
    	req.setC_Payment_ID(pay.getC_Payment_ID());
    	req.saveEx();		
    		
		
		
    	return "Pago " + pay.getDocumentInfo();
    }
    //MSession session = new MSession(getCtx(), Env.getContext(getCtx(), "), trxName)
    

	
    
}

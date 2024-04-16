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

package org.shw.model;

import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MBPartner;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceBatch;
import org.compiere.model.MInvoiceBatchLine;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MPayment;
import org.compiere.model.MProject;
import org.compiere.model.MWarehouse;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *
 * @author  Susanne Calderon
 */
public class Callout_SHW extends CalloutEngine
{

    /**
    *  docType - set document properties based on document type.
    *  @param ctx
    *  @param WindowNo
    *  @param mTab
    *  @param mField
    *  @param value
    *  @return error message or ""
    */
   public String invoiceBatch (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
   {
       Integer C_InvoiceBatch_ID = (Integer)value;
       if (C_InvoiceBatch_ID == 0)
    	   return "";
       MInvoiceBatch ib = new MInvoiceBatch(Env.getCtx(), C_InvoiceBatch_ID, null);
       mTab.setValue(MInvoiceBatch.COLUMNNAME_C_InvoiceBatch_ID, ib.getDocumentAmt());  
       
       return "";
   }

   public String callout (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
   {
       return "";
   }
   public String paymentDocType (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
   {
	   Properties A_Ctx = ctx;
       Integer C_DocType_ID = (Integer)value;
       if (C_DocType_ID == 0)
    	   return "";
       MDocType dt = new MDocType(A_Ctx, C_DocType_ID, null);
       Boolean isReceipt = dt.getDocBaseType().equals(MDocType.DOCBASETYPE_APPayment)? false:true;
       mTab.setValue(MPayment.COLUMNNAME_IsReceipt, isReceipt);
       if (isReceipt)
    	   mTab.setValue(MPayment.COLUMNNAME_TenderType, 'X');
       else
    	   mTab.setValue(MPayment.COLUMNNAME_TenderType, 'K');  
	   
       return "";
   }
   
   
   public String setProjectParameters(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

       Integer C_Project_ID = (Integer)value;
    if (C_Project_ID == null || C_Project_ID.intValue() == 0)
        return "";

        MProject project = new MProject(ctx, C_Project_ID, null);
        mTab.setValue("C_Activity_ID", project.get_ValueAsInt("C_Activity_ID"));
        mTab.setValue("User1_ID", project.get_ValueAsInt("User1_ID"));
        mTab.setValue("C_Campaign_ID", project.get_ValueAsInt("C_Campaign_ID"));
        mTab.setValue("LG_Route_ID", project.get_ValueAsInt("LG_Route_ID"));
 	   
        return "";
   }
   

   public String setHRProcessName(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

	   Integer HR_Period_ID = (Integer)value;
	   if (HR_Period_ID == 0 || HR_Period_ID == null)
		   return "";
	   
	   String perName = DB.getSQLValueStringEx(null, "SELECT Name from HR_Period where HR_Period_ID = ?", HR_Period_ID);
	   Timestamp DateAcct = DB.getSQLValueTSEx(null, "SELECT EndDate from HR_Period where HR_Period_ID = ?", HR_Period_ID);

	   mTab.setValue("Name", perName); 
	   mTab.setValue("DateAcct", DateAcct); 
	   return "";
   }
   
   public String setInvoiceBatchLineValues(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

       Integer C_Invoice_ID = (Integer)value;
       if (C_Invoice_ID == null || C_Invoice_ID == 0  )
           return "";
       MInvoice i = new MInvoice(Env.getCtx(), C_Invoice_ID, null);
       mTab.setValue(MInvoiceBatchLine.COLUMNNAME_LineTotalAmt, i.getOpenAmt ());  
       mTab.setValue(MInvoice.COLUMNNAME_C_BPartner_ID, i.getC_BPartner_ID());
       mTab.setValue(MInvoice.COLUMNNAME_DateInvoiced, i.getDateInvoiced());   
   

	   return "";
   }
   

   public String setInvoiceBatchValues(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {


       Integer C_InvoiceBatch_ID = (Integer)value;
       if (C_InvoiceBatch_ID == null   || C_InvoiceBatch_ID == 0)
           return "";
       MInvoiceBatch quedan = new MInvoiceBatch(Env.getCtx(), C_InvoiceBatch_ID, null);
       mTab.setValue(MInvoiceLine.COLUMNNAME_PriceEntered,  quedan.getDocumentAmt()); 
       mTab.setValue(MInvoiceLine.COLUMNNAME_PriceActual,  quedan.getControlAmt()); 
       return "";

	   
   }
   

   public String setOrderWarehouse(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

	   Integer AD_Org_ID = (Integer)value;
	   if (AD_Org_ID == null || AD_Org_ID.intValue() == 0)
		   return "";

	   MWarehouse[] whs = null;
	   MWarehouse wh = null;
	   whs = MWarehouse.getForOrg(ctx, AD_Org_ID);
	   if (whs == null || whs.length <= 0)
		   return "";

	   wh = whs[0];
	   mTab.setValue("M_Warehouse_ID", wh.getM_Warehouse_ID ());
	   return "";
   }
   
   public String setInvoiceBPartnerParameters(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

	   Integer C_BPartner_ID = (Integer)value;
	   if (C_BPartner_ID == null || C_BPartner_ID.intValue() == 0)
		   return "";

	   MBPartner bpartner = new MBPartner(ctx, C_BPartner_ID, null);
	   mTab.setValue("C_Activity_ID", bpartner.get_ValueAsInt("C_Activity_ID"));
	   mTab.setValue("User1_ID", bpartner.get_ValueAsInt("User1_ID"));
	   mTab.setValue("SalesRep_ID", bpartner.get_ValueAsInt("SalesRep_ID"));
	   if (bpartner.get_ValueAsInt("SuperVisor_Custom") != 0)
		   mTab.setValue("SuperVisor_Custom", bpartner.get_ValueAsInt("SuperVisor_Custom"));
	   if (bpartner.get_ValueAsInt("SuperVisor_Shipment") !=0)
		   mTab.setValue("SuperVisor_Shipment", bpartner.get_ValueAsInt("SuperVisor_Shipment"));

	   int c_doctype_ID = -1;
	   boolean IsSOTrx = "Y".equals(Env.getContext(ctx, WindowNo, "IsSOTrx"));
	   if (IsSOTrx)
		   c_doctype_ID = bpartner.get_ValueAsInt("C_DocType_ID");
	   else
		   c_doctype_ID = bpartner.get_ValueAsInt("C_DocTypePO_ID");
	   if (c_doctype_ID > 0)
		   mTab.setValue("C_DocTypeTarget_ID", c_doctype_ID);
	   return "";
   }
   
   public String setPaymentInvoiceParameters(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

	   Integer C_Invoice_ID = (Integer)value;
	   if (C_Invoice_ID == null || C_Invoice_ID.intValue() == 0)
		   return "";

	   MInvoice inv = new MInvoice(ctx, C_Invoice_ID, null);
	   mTab.setValue("C_Activity_ID", inv.get_ValueAsInt("C_Activity_ID"));
	   mTab.setValue("User1_ID", inv.get_ValueAsInt("User1_ID"));
	   mTab.setValue("C_Campaign_ID", inv.get_ValueAsInt("C_Campaign_ID"));

	   return "";

   }

   
   
}
   

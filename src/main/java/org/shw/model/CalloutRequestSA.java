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

import org.adempiere.core.domains.models.X_C_Invoice;
import org.adempiere.core.domains.models.X_C_Order;
import org.adempiere.core.domains.models.X_C_Project;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MRequestType;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

/**
 *	Request Callouts
 *	
 *  @author Jorg Janke
 *  @version $Id: CalloutRequest.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class CalloutRequestSA extends CalloutEngine
{
	/**
	 *  Request - Copy Mail Text - <b>Callout</b>
	 *
	 *  @param ctx      Context
	 *  @param WindowNo current Window No
	 *  @param mTab     Model Tab
	 *  @param mField   Model Field
	 *  @param value    The new value
	 *  @return Error message or ""
	 */
	
	 
	public String type (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		String colName = mField.getColumnName();
		log.info(colName + "=" + value);
		mTab.setValue("R_Status_ID", null);
		if (value == null)
			return "";
		int R_RequestType_ID = ((Integer)value).intValue();
		if (R_RequestType_ID == 0)
			return "";
		MRequestType rt = MRequestType.get(ctx, R_RequestType_ID);
		int R_Status_ID = rt.getDefaultR_Status_ID();
		if (R_Status_ID != 0)
			mTab.setValue("R_Status_ID", new Integer(R_Status_ID));
		
		String sql = " select ad_user_ID from shw_requestUser where r_requesttype_ID=? "
				+ " and isdefault='Y' ";
		int user_ID = DB.getSQLValueEx(null, sql, R_RequestType_ID);
		mTab.setValue("SalesRep_ID", user_ID);
		
		
		return "";
	}	//	type
	
	public String order (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{

		Integer C_Order_ID = (Integer)value;
		if (C_Order_ID == null || C_Order_ID.intValue() == 0)
			return "";
		X_C_Order ord = new X_C_Order(ctx, C_Order_ID, null);
		mTab.setValue("C_Project_ID", ord.getC_Project_ID());
		mTab.setValue("AD_Org_ID", ord.getAD_Org_ID());
		mTab.setValue("C_Activity_ID", ord.getC_Activity_ID());
		mTab.setValue("C_Campaign_ID", ord.getC_Campaign_ID());
		mTab.setValue("User1_ID", ord.getUser1_ID());
		mTab.setValue("AD_User_ID", Env.getContext(Env.getCtx(), "#AD_User_ID"));
		mTab.setValue("RequestAmt", ord.getGrandTotal());
		if (!ord.isSOTrx()) {
			mTab.setValue("C_BPartnerVendor_ID", ord.getC_BPartner_ID());
		}


		return "";
	}	//	type
	
	public String invoice (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer C_Invoice_ID = (Integer)value;
		if (C_Invoice_ID == null || C_Invoice_ID.intValue() == 0)
			return "";
		X_C_Invoice invoice = new X_C_Invoice(ctx, C_Invoice_ID, null);
		mTab.setValue("C_Project_ID", invoice.getC_Project_ID());
		mTab.setValue("AD_Org_ID", invoice.getAD_Org_ID());
		mTab.setValue("C_Activity_ID", invoice.getC_Activity_ID());
		mTab.setValue("C_Campaign_ID", invoice.getC_Campaign_ID());
		mTab.setValue("User1_ID", invoice.getUser1_ID());
		mTab.setValue("RequestAmt", invoice.getGrandTotal());
		mTab.setValue("AD_User_ID", Env.getContext(Env.getCtx(), "#AD_User_ID"));
		return "";
	}	//	type
	
	
	public String project (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{

		Integer C_Project_ID = (Integer)value;
		if (C_Project_ID == null || C_Project_ID.intValue() == 0)
			return "";
		X_C_Project project = new X_C_Project(ctx, C_Project_ID, null);
		mTab.setValue("AD_Org_ID", project.getAD_Org_ID());
		mTab.setValue("AD_User_ID", Env.getContext(Env.getCtx(), "#AD_User_ID"));
		return "";
	}	//	type
	
	
	

}	//	CalloutRequest



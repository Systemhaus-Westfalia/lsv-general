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


import java.sql.Timestamp;

import org.adempiere.core.domains.models.X_C_Project;
import org.adempiere.core.domains.models.X_R_Request;
import org.adempiere.core.domains.models.X_R_RequestType;
import org.compiere.model.MBankAccount;
import org.compiere.model.MCharge;
import org.compiere.model.MPayment;
import org.compiere.model.MPaymentAllocate;
import org.compiere.model.MRequest;
import org.compiere.model.Query;

/** Generated Process for (SHW_RequestsCreatePayment)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.3
 */
public class SHW_RequestsCreatePayment extends SHW_RequestsCreatePaymentAbstract
{
	
	private 	MPayment pay = null;
	@Override
	protected void prepare()
	{
		super.prepare();
	}

	@Override
	protected String doIt() throws Exception
	{
		String result = "";
		MRequest orgrequest = new MRequest(getCtx(), getRecord_ID(), get_TrxName());

		for(Integer key : getSelectionKeys()) {
			MRequest request = new MRequest(getCtx(), key, get_TrxName());
			if (pay == null)
			{
				pay = new MPayment(getCtx(), 0, get_TrxName());

				int defaultaccount = new Query(request.getCtx(), MBankAccount.Table_Name, "", request.get_TrxName())
						.setClient_ID()
						.setOnlyActiveRecords(true)
						.firstId(); 
				int c_bpartner_ID = request.get_ValueAsInt("C_BPartnerVendor_ID");
				if (getBPartnerId()!= 0)
					c_bpartner_ID = getBPartnerId();
				if (request.get_ValueAsInt("C_BPartnerVendor_ID") == 0)
					return "Hay que definir el Proveedor";
				pay.setC_BPartner_ID(c_bpartner_ID); 	
				pay.setC_BankAccount_ID(defaultaccount);
				pay.setDateTrx(new Timestamp (System.currentTimeMillis()));
				pay.setIsOverUnderPayment(true);
				pay.setAD_Org_ID(request.getAD_Org_ID());
				pay.setC_Project_ID(request.getC_Project_ID());
				X_R_RequestType rtype = (X_R_RequestType)request.getR_RequestType();		    	
				if (rtype.get_ValueAsInt(MPayment.COLUMNNAME_C_DocType_ID)!= 0)
					pay.setC_DocType_ID(rtype.get_ValueAsInt(MPayment.COLUMNNAME_C_DocType_ID));
				else
					pay.setC_DocType_ID(false);
				pay.setC_Currency_ID(100);
				pay.saveEx();
				result = "Pago: " + pay.getDocumentNo();
				request.setC_Payment_ID(pay.getC_Payment_ID());
				request.saveEx();		
			}
			MPaymentAllocate palloc = new MPaymentAllocate(getCtx(), 0,get_TrxName());
			palloc.setAD_Org_ID(pay.getAD_Org_ID());
			palloc.setC_Payment_ID(pay.getC_Payment_ID());
			palloc.set_ValueOfColumn(MPayment.COLUMNNAME_C_Charge_ID, request.get_ValueAsInt(MCharge.COLUMNNAME_C_Charge_ID));
			palloc.setAmount(request.getRequestAmt());
			palloc.set_ValueOfColumn("C_Activity_ID", request.getC_Activity_ID());
			palloc.set_ValueOfColumn(MRequest.COLUMNNAME_C_Campaign_ID, request.getC_Campaign_ID());
			X_C_Project prj = (X_C_Project)request.getC_Project();
			palloc.set_ValueOfColumn("User1_ID",prj.get_ValueAsInt("User1_ID"));
			palloc.set_ValueOfColumn("C_Project_ID",prj.getC_Project_ID());
			palloc.set_ValueOfColumn(MRequest.COLUMNNAME_R_Request_ID, request.getR_Request_ID());
			palloc.saveEx();
		}		

		orgrequest.setResult(orgrequest.getResult() + " Pago generado:" + result );
		orgrequest.setC_Payment_ID(pay.getC_Payment_ID());
		orgrequest.saveEx();
		return result;
	}
}
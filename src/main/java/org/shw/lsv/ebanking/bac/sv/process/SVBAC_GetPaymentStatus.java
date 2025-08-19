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

package org.shw.lsv.ebanking.bac.sv.process;

import java.sql.Timestamp;

import org.compiere.model.MBankAccount;
import org.compiere.model.MPayment;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.shw.lsv.einvoice.process.IGenerateAndPost;
import org.shw.lsv.util.support.provider.SVBACBalance;
import org.shw.lsv.util.support.provider.SVBACPaymentStatus;
import org.spin.model.MADAppRegistration;

/** Generated Process for (SVBAC_GetPaymentStatus)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.4
 */
public class SVBAC_GetPaymentStatus extends SVBAC_GetPaymentStatusAbstract
{
	@Override
	protected void prepare()
	{
		super.prepare();
	}

	@Override
	protected String doIt() throws Exception
	{
		MPayment payment = new MPayment(getCtx(), getRecord_ID(), get_TrxName());

		String result = "";

		StringBuffer errorMessages = new StringBuffer();
		int noCompletados = 0;
		String applicationType = IGenerateAndPost.getApplicationType();
		MADAppRegistration registration = null;
		Timestamp startdate = null;
		String errorMessage= "";

		System.out.println("\n" + "******************************************************");
		System.out.println("Process Banking GetBalance: started with Client");
		registration = new Query(getCtx(), MADAppRegistration.Table_Name, " AD_Client_ID=? AND EXISTS(SELECT 1 FROM AD_AppSupport s "
				+ "WHERE s.AD_AppSupport_ID = AD_AppRegistration.AD_AppSupport_ID "
				+ "AND s.ApplicationType = ?"
				+ "AND s.IsActive = 'Y'"
				+ "AND s.Classname = ?)", get_TrxName())
				.setParameters(Env.getAD_Client_ID(getCtx()),  applicationType, SVBACPaymentStatus.class.getName())
				.<MADAppRegistration>first();


		if(registration==null) {
			errorMessage = "Process EInvoiceGenerateAndPost : no registration for Application Type " + applicationType;
			errorMessages.append(errorMessage);
			System.out.println(errorMessage);
			return errorMessage.toString();
		}

		SVBACPaymentStatus svbacPaymentStatus  = new SVBACPaymentStatus();
		svbacPaymentStatus.setAppRegistrationId(registration.getAD_AppRegistration_ID() );
		svbacPaymentStatus.setPaymentID(getRecord_ID());
		svbacPaymentStatus.setTransactionName(get_TrxName());
		
		try {
			result = svbacPaymentStatus.getStatus();
		} catch (Exception e) {
			System.out.println("Mist " + e);
		}
		
		return result;
	
	}
}
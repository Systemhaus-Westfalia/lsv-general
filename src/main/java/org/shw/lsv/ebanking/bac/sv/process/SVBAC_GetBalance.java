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

import org.compiere.model.MBankAccount;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.shw.lsv.einvoice.process.IGenerateAndPost;
import org.shw.lsv.util.support.provider.SVBACBalance;
import org.spin.model.MADAppRegistration;

/** Generated Process for (SVBAC_GetBalance)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.4
 */
public class SVBAC_GetBalance extends SVBAC_GetBalanceAbstract
{
	MBankAccount bankAccount = null;
	@Override
	protected void prepare()
	{
		super.prepare();
	}

	@Override
	protected String doIt() throws Exception
	{
		bankAccount = new MBankAccount(getCtx(), getRecord_ID(), get_TrxName());

		String result = "";

		StringBuffer errorMessages = new StringBuffer();
		String applicationType = IGenerateAndPost.getApplicationType();
		MADAppRegistration registration = null;
		String errorMessage= "";

		System.out.println("\n" + "******************************************************");
		System.out.println("Process Banking GetBalance: started with Client");
		registration = new Query(getCtx(), MADAppRegistration.Table_Name, " AD_Client_ID=? AND EXISTS(SELECT 1 FROM AD_AppSupport s "
				+ "WHERE s.AD_AppSupport_ID = AD_AppRegistration.AD_AppSupport_ID "
				+ "AND s.ApplicationType = ? "
				+ "AND s.IsActive = 'Y' "
				+ "AND s.Classname = ? )", get_TrxName())
				.setParameters(Env.getAD_Client_ID(getCtx()),  applicationType, SVBACBalance.class.getName())
				.<MADAppRegistration>first();


		if(registration==null) {
			errorMessage = "Process EInvoiceGenerateAndPost : no registration for Application Type " + applicationType;
			errorMessages.append(errorMessage);
			System.out.println(errorMessage);
			return errorMessage.toString();
		}

		SVBACBalance svbacBalance = new SVBACBalance();
		svbacBalance.setAppRegistrationId(registration.getAD_AppRegistration_ID() );
		svbacBalance.setBankAccountID(getRecord_ID());
		svbacBalance.setTransactionName(get_TrxName());
		
		try {
			result = svbacBalance.getBalance();
		} catch (Exception e) {
			System.out.println("Mist " + e);
		}
		
		return result;
	}
	
	
}
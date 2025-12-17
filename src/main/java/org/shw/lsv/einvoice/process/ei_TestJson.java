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

package org.shw.lsv.einvoice.process;

import java.sql.Timestamp;

import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.shw.lsv.util.support.provider.SVMinHacienda;
import org.spin.model.MADAppRegistration;

/** Generated Process for (ei_TestJson)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.4
 */
public class ei_TestJson extends ei_TestJsonAbstract
{
	@Override
	protected void prepare()
	{
		super.prepare();
	}

	@Override
	protected String doIt() throws Exception
	{

		StringBuffer errorMessages = new StringBuffer();
		int noCompletados = 0;
		String applicationType = IGenerateAndPost.getApplicationType();
		MADAppRegistration registration = null;
		Timestamp startdate = null;
		String errorMessage= "";
		int clientID = Env.getAD_Client_ID(getCtx());
		MClient client = new MClient(getCtx(),clientID, get_TrxName());

		startdate = (Timestamp)(client.get_Value("ei_Startdate"));
		System.out.println("\n" + "******************************************************");
		System.out.println("Process EInvoiceGenerateAndPost: started with Client '" + client.getName() + "', ID: " + clientID);
		registration = new Query(getCtx(), MADAppRegistration.Table_Name, " AD_Client_ID=? AND EXISTS(SELECT 1 FROM AD_AppSupport s "
				+ "WHERE s.AD_AppSupport_ID = AD_AppRegistration.AD_AppSupport_ID "
				+ "AND s.ApplicationType = ? "
				+ "AND s.IsActive = 'Y' "
				+ "AND s.Classname = ? )"
				+ "AND AD_Client_ID=?", get_TrxName())
				.setParameters(clientID,  applicationType, SVMinHacienda.class.getName(), clientID)
				.<MADAppRegistration>first();

		if(registration==null) {
			errorMessage = "Process EInvoiceGenerateAndPost : no registration for Application Type " + applicationType;
			errorMessages.append(errorMessage);
			System.out.println(errorMessage);
			return errorMessage.toString();
		}

		MInvoice invoice = new MInvoice(getCtx(), getInvoiceId(), get_TrxName());
		SVMinHacienda sv_minhacienda = new SVMinHacienda();
		sv_minhacienda.setVoided(false);
		sv_minhacienda.setADClientID(client.getAD_Client_ID());
		sv_minhacienda.setAppRegistrationId(registration.getAD_AppRegistration_ID() );		
		sv_minhacienda.testJson(invoice);
		return "";
	}
}
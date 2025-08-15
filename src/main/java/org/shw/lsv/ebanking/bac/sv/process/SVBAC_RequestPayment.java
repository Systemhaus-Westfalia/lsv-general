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

import org.compiere.model.MPayment;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.util.support.provider.SVBACPaymentRequest;
import org.spin.model.MADAppRegistration;

/** Generated Process for (SVBAC_RequestPayment_BACtoBAC)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.4
 */
public class SVBAC_RequestPayment extends SVBAC_RequestPaymentAbstract
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
		String swiftCode = payment.getC_BankAccount().getC_Bank().getSwiftCode();
		if (!swiftCode.equals(EBankingConstants.SWIFTBAC)) {
			return "Este banco no ofrece online banking";
		}
		String evtCd = payment.get_ValueAsString("evtCd");
		if (evtCd.equals(EBankingConstants.REQUEST_RECIBIDO))
			return "El Pago ya fue mandado";

		String transfertype = "";
		String swiftCodeBPartner = payment.getC_BP_BankAccount().getC_Bank().getSwiftCode();
		String result = "";
		result = validateSwiftBP(swiftCodeBPartner);
		if (!result.equals(EBankingConstants.VALIDATIONOK) )
			return result;
		if (swiftCode.equals(swiftCodeBPartner))
			transfertype = EBankingConstants.TRANSFERBACTOBAC;
		else if (!swiftCode.equals(swiftCodeBPartner) && swiftCodeBPartner.substring(4, 6).equals("SV")) {
			transfertype = EBankingConstants.TRANSFERDOM;
		}
		else
			transfertype = EBankingConstants.TRANSFERINT;
		StringBuffer errorMessages = new StringBuffer();
		String applicationType = "LSV";
		MADAppRegistration registration = null;
		String errorMessage= "";

		System.out.println("\n" + "******************************************************");
		System.out.println("Process Banking GetBalance: started with Client");
		registration = new Query(getCtx(), MADAppRegistration.Table_Name, " AD_Client_ID=? AND EXISTS(SELECT 1 FROM AD_AppSupport s "
				+ "WHERE s.AD_AppSupport_ID = AD_AppRegistration.AD_AppSupport_ID "
				+ "AND s.ApplicationType = ?"
				+ "AND s.IsActive = 'Y'"
				+ "AND s.Classname = ?)", get_TrxName())
				.setParameters(Env.getAD_Client_ID(getCtx()),  applicationType, SVBACPaymentRequest.class.getName())
				.<MADAppRegistration>first();


		if(registration==null) {
			errorMessage = "Process EInvoiceGenerateAndPost : no registration for Application Type " + applicationType;
			errorMessages.append(errorMessage);
			System.out.println(errorMessage);
			return errorMessage.toString();
		}

		SVBACPaymentRequest svbacPaymentBAC = new SVBACPaymentRequest();
		svbacPaymentBAC.setAppRegistrationId(registration.getAD_AppRegistration_ID() );
		svbacPaymentBAC.setPaymentID(getRecord_ID());
		svbacPaymentBAC.setTransactionName(get_TrxName());
				
		try {
			if (transfertype.equals(EBankingConstants.TRANSFERBACTOBAC))
			svbacPaymentBAC.sendPaymentRequestBACtoBAC();
			else if (transfertype.equals(EBankingConstants.TRANSFERDOM))
				svbacPaymentBAC.sendPaymentRequestBACtoDOM();
		} catch (Exception e) {
			System.out.println("Mist " + e);
		}
		
		return "";
	}
	
	public String validateSwiftBP(String swift) {
		String result = EBankingConstants.VALIDATIONOK;
        boolean patternOK = (swift != null && !swift.isEmpty()) &&
        		swift.matches(EBankingConstants.PATTERN_SWIFT);
        if (!patternOK) {
        	result = "El SWFT de Receptor de Pago no es correcto";
        }
        return result;
    }
}
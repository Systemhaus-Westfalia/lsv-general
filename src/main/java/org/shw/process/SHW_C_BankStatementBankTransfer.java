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

import org.adempiere.core.domains.models.I_C_POS;
import org.compiere.model.MBankAccount;
import org.compiere.model.MBankStatement;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.MPayment;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

/** Generated Process for (Bank Transfer)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.4
 */
public class SHW_C_BankStatementBankTransfer extends SHW_C_BankStatementBankTransferAbstract
{
	@Override
	protected void prepare()
	{
		super.prepare();
	}

	@Override
	protected String doIt() throws Exception
	{
		return generateBankTransfer();
	}
	
	
	private String generateBankTransfer() {
		Timestamp statementDate = getStatementDate();
		Timestamp dateAcct = getDateAcct();
		String documentNoTo = getDocumentNoTo();
		if(documentNoTo == null
				|| documentNoTo.trim().length() == 0) {
			documentNoTo = getDocumentNo();
		}
		//	Login Date
		if (statementDate == null) {
			statementDate = Env.getContextAsDate(getCtx(), "#Date");
		}
		if (statementDate == null) {
			statementDate = new Timestamp(System.currentTimeMillis());			
		}
		//	
		if (dateAcct == null) {
			dateAcct = statementDate;
		}

		MBankAccount mBankFrom = MBankAccount.get(getCtx(), getCBankAccountId());
		MBankAccount mBankTo = MBankAccount.get(getCtx(), getBankAccountToId());
		
		MPayment paymentBankFrom = new MPayment(getCtx(), 0 ,  get_TrxName());
		paymentBankFrom.setC_BankAccount_ID(mBankFrom.getC_BankAccount_ID());
		paymentBankFrom.setDocumentNo(getDocumentNo());
		paymentBankFrom.setDateAcct(dateAcct);
		paymentBankFrom.setDateTrx(statementDate);
		paymentBankFrom.setTenderType(getTenderType());
		paymentBankFrom.setDescription(getDescription());
		paymentBankFrom.setC_BPartner_ID (getBPartnerId());
		paymentBankFrom.setC_Currency_ID(getCurrencyId());
		paymentBankFrom.setC_POS_ID(getPOSId());
		if(getConversionTypeId() > 0) {
			paymentBankFrom.setC_ConversionType_ID(getConversionTypeId());	
		}
		paymentBankFrom.setPayAmt(getAmount());
		paymentBankFrom.setOverUnderAmt(Env.ZERO);
		if(getWithdrawalDocumentTypeId() != 0) {
			paymentBankFrom.setC_DocType_ID(getWithdrawalDocumentTypeId());
		} else {
			paymentBankFrom.setC_DocType_ID(false);
		}
		paymentBankFrom.setC_Charge_ID(getChargeId());
		paymentBankFrom.saveEx();
		//	
		MPayment paymentBankTo = new MPayment(getCtx(), 0 ,  get_TrxName());
		paymentBankTo.setC_BankAccount_ID(mBankTo.getC_BankAccount_ID());
		paymentBankTo.setDocumentNo(documentNoTo);
		paymentBankTo.setDateAcct(dateAcct);
		paymentBankTo.setDateTrx(statementDate);
		paymentBankTo.setTenderType(getTenderType());
		paymentBankTo.setDescription(getDescription());
		paymentBankTo.setC_BPartner_ID (getBPartnerId());
		paymentBankTo.setC_Currency_ID(getCurrencyId());
		paymentBankTo.setC_POS_ID(getPOSId());
		if(getConversionTypeId() > 0) {
			paymentBankTo.setC_ConversionType_ID(getConversionTypeId());	
		}
		//	Support to cash opening
		if(getParameterAsInt(I_C_POS.COLUMNNAME_C_POS_ID) > 0) {
			paymentBankFrom.setC_POS_ID(getParameterAsInt(I_C_POS.COLUMNNAME_C_POS_ID));
			paymentBankTo.setC_POS_ID(getParameterAsInt(I_C_POS.COLUMNNAME_C_POS_ID));
		}
		paymentBankTo.setPayAmt(getAmount());
		paymentBankTo.setOverUnderAmt(Env.ZERO);
		if(getDepositDocumentTypeId() != 0) {
			paymentBankTo.setC_DocType_ID(getDepositDocumentTypeId());
		} else {
			paymentBankTo.setC_DocType_ID(true);
		}
		paymentBankTo.setC_Charge_ID(getChargeId());
		paymentBankTo.saveEx();

		paymentBankFrom.setRelatedPayment_ID(paymentBankTo.getC_Payment_ID());
		paymentBankFrom.saveEx();
		paymentBankFrom.processIt(MPayment.DOCACTION_Complete);
		paymentBankFrom.saveEx();
		addLog("@C_Payment_ID@ @IsReceipt@: ");
		//	Add to current bank statement for account
		if(isAutoReconciled()) {
			MBankStatementLine bsl = MBankStatement.addPayment(paymentBankFrom);
			if(bsl != null) {
				addLog("@C_Payment_ID@: " + paymentBankFrom.getDocumentNo()
						+ " @Added@ @to@ [@AccountNo@ " + paymentBankFrom.getC_BankAccount().getAccountNo()
						+ " @C_BankStatement_ID@ " + bsl.getC_BankStatement().getName() + "]");
			}
		}
		paymentBankTo.setRelatedPayment_ID(paymentBankFrom.getC_Payment_ID());
		paymentBankTo.saveEx();
		paymentBankTo.processIt(MPayment.DOCACTION_Complete);
		paymentBankTo.saveEx();
		//	Add to current bank statement for account
		if(isAutoReconciled()) {
			MBankStatementLine bsl = MBankStatement.addPayment(paymentBankTo);
			if(bsl != null) {
				addLog("@C_Payment_ID@: " + paymentBankTo.getDocumentNo() 
						+ " @Added@ @to@ [@AccountNo@ " + paymentBankTo.getC_BankAccount().getAccountNo() 
						+ " @C_BankStatement_ID@ " + bsl.getC_BankStatement().getName() + "]");
			}
		}
		//	Return
		return "@Created@ (1) @From@ " + mBankFrom.getAccountNo()+ " @To@ " + mBankTo.getAccountNo() + " @Amt@ " + DisplayType.getNumberFormat(DisplayType.Amount).format(getAmount());
	}  //  createCashLines
}
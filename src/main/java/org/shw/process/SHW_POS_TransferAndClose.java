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

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBankAccount;
import org.compiere.model.MBankStatement;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.MDocType;
import org.compiere.model.MPayment;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfo;
import org.compiere.util.Env;
import org.eevolution.services.dsl.ProcessBuilder;

/** Generated Process for (POS Bank Statement Close)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.4
 */
public class SHW_POS_TransferAndClose extends SHW_POS_TransferAndCloseAbstract
{

    protected LinkedHashMap<Integer, MBankStatement> baskStatements = null;
	@Override
	protected void prepare()
	{
		super.prepare();
	}

	@Override
	protected String doIt() throws Exception
	{
		if (!isCloseDocument()) {

			generateBankTransfer();
		}
		else 
			closeBankStatements();
		return "";
	}
	
	private String generateBankTransfer() {
		MBankAccount bankaccount = new MBankAccount(getCtx(), getBankAccountId(), get_TrxName());
		int bpartnerID = bankaccount.getC_BPartner_ID();
		String documentno = Env.getContext(getCtx(), "@#Date");
		int docTypeWithdrawelID = 0;
		int docTypeDepositID = 0;
        Optional<MDocType> doctypeOptional = Arrays.stream(MDocType.getOfDocBaseType(getCtx(), MDocType.DOCBASETYPE_APPayment))
        		.filter(docType -> docType.isBankTransfer())
        		.findFirst();
        if(doctypeOptional.isPresent()) {
        	docTypeWithdrawelID = doctypeOptional.get().getC_DocType_ID();
		}
        
        Optional<MDocType> doctypeOptional1 = Arrays.stream(MDocType.getOfDocBaseType(getCtx(), MDocType.DOCBASETYPE_ARReceipt))
        		.filter(docType -> docType.isBankTransfer())
        		.findFirst();
        if(doctypeOptional1.isPresent()) {
        	docTypeDepositID = doctypeOptional1.get().getC_DocType_ID();
		}
        
		
		ProcessInfo  processInfo =
			ProcessBuilder.create(getCtx())
			.process(SHW_C_BankStatementBankTransfer.getProcessId())
			.withTitle(SHW_C_BankStatementBankTransfer.getProcessName())
			.withParameter("C_BankAccountTo_ID", getBankAccountToId())
			.withParameter("From_C_BankAccount_ID", getBankAccountId())
			.withParameter("C_BPartner_ID", bpartnerID)
			.withParameter("C_Currency_ID", 100)
			.withParameter("C_Charge_ID", getChargeId())
			.withParameter("DocumentNo", "0001")
			.withParameter("Amount", getAmount())
			.withParameter("StatementDate", getDateTrx())
			.withParameter("DateAcct", getDateTrx())
			.withParameter("IsAutoReconciled", true)
			.withParameter("WithdrawalDocumentType_ID", docTypeWithdrawelID)
			.withParameter("DepositDocumentType_ID", docTypeDepositID)
			.withParameter(SHW_C_BankStatementBankTransfer.C_POS_ID, getPOSId())
			.withParameter(SHW_C_BankStatementBankTransfer.TENDERTYPE, getTenderType())
			.execute(get_TrxName()); 
	if (processInfo.isError())
		throw new AdempiereException(processInfo.getSummary());
		//	Return
		return "@Created@ (1) @From@ " ;
	}  //  createCashLines
	
	 private LinkedHashMap<Integer, MBankStatement> getBankStatements()
	    {
	        if (baskStatements != null && baskStatements.size() > 0)
	            return baskStatements;

	        baskStatements = new LinkedHashMap<Integer, MBankStatement>();
	        List<MPayment> payments = (List<MPayment>) getInstancesForSelection(get_TrxName());
	        payments.stream().forEach( payment -> {
	            Integer bankStatementLineId = getSelectionAsInt(payment.get_ID() , "BSL_C_BankStatementLine_ID");
	            if (bankStatementLineId != null && bankStatementLineId > 0)
	            {
	                MBankStatementLine bankStatementLine = new MBankStatementLine(getCtx() , bankStatementLineId ,  get_TrxName());
	                MBankStatement bankStatement = bankStatementLine.getParent();
	                if (!baskStatements.containsKey(bankStatement.get_ID()))
	                    baskStatements.put(bankStatement.get_ID() , bankStatement);
	            }
	        });
	        return baskStatements;
	    }
	 
	 private void closeBankStatements() {
	        getBankStatements().entrySet().stream().forEach( entry -> {
	            MBankStatement bankStatement =  entry.getValue();
	            bankStatement.processIt(DocAction.ACTION_Complete);
	            bankStatement.saveEx();
	        });
	    }

}
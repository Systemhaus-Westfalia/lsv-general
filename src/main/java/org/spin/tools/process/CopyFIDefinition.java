/******************************************************************************
 * Product: ADempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2006-2017 ADempiere Foundation, All Rights Reserved.         *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * or (at your option) any later version.										*
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * or via info@adempiere.net or http://www.adempiere.net/license.html         *
 *****************************************************************************/

package org.spin.tools.process;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.core.domains.models.I_C_Bank;
import org.adempiere.core.domains.models.I_C_BankStatementMatcher;
import org.compiere.model.MBank;
import org.compiere.model.MBankStatementMatcher;
import org.compiere.model.Query;
import org.adempiere.core.domains.models.I_C_BankMatcher;
import org.spin.model.MCBankMatcher;
import org.spin.tools.util.CopyContextUtil;
import org.spin.tools.util.CopyUtil;

/** Generated Process for (Copy Financial from Client)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.3
 */
public class CopyFIDefinition extends CopyFIDefinitionAbstract {
	/**	Copy Util	*/
	private CopyUtil copy;
	
	@Override
	protected void prepare() {
		super.prepare();
		if(getTemplateClientId() <= 0) {
			throw new AdempiereException("@ECA25_TemplateClient_ID@ @IsMandatory@");
		}
		int targetClientId = getTargetClientId();
		if(targetClientId == 0) {
			targetClientId = getAD_Client_ID();
		}
		if(targetClientId == 0) {
			throw new AdempiereException("@ECA25_TargetClient_ID@ @NotFound@");
		}
		copy = CopyUtil.newInstance().withTeplateClientId(getTemplateClientId()).withTargetClientId(targetClientId).withContext(getCtx()).withTransactionName(get_TrxName());
	}

	@Override
	protected String doIt() throws Exception {
		try {
			CopyContextUtil.newInstance().updateContextToNewClient(getCtx(), copy.getTargetClientId(), getProcessInfo().isBatch());
			//	Banks
			copy.getTemplateReferencesOnlyNewIds(I_C_Bank.Table_Name).forEach(currentBankId -> {
				MBank currentBank = new MBank(getCtx(), currentBankId, get_TrxName()); 
				MBank newBank = new MBank(getCtx(), 0, get_TrxName());
				copy.copyCurrentValues(currentBank, newBank);
				newBank.setAD_Org_ID(0);
				newBank.saveEx();
				copy.saveReference(currentBank, newBank);
			});
			//	Bank Statement Matcher
			copy.getTemplateReferencesOnlyNewIds(I_C_BankStatementMatcher.Table_Name).forEach(currentBankId -> {
				MBankStatementMatcher currentReference = new MBankStatementMatcher(getCtx(), currentBankId, get_TrxName()); 
				MBankStatementMatcher newReference = new MBankStatementMatcher(getCtx(), 0, get_TrxName());
				copy.copyCurrentValues(currentReference, newReference);
				newReference.setAD_Org_ID(0);
				newReference.saveEx();
				copy.saveReference(currentReference, newReference);
				new Query(getCtx(), I_C_BankMatcher.Table_Name, "C_BankStatementMatcher_ID = ?", get_TrxName())
				.setParameters(currentReference.getC_BankStatementMatcher_ID())
				.getIDsAsList().forEach(currentLineId -> {
					MCBankMatcher currentMatcher = new MCBankMatcher(getCtx(), currentLineId, get_TrxName()); 
					MCBankMatcher newMatcher = new MCBankMatcher(getCtx(), 0, get_TrxName());
					copy.copyCurrentValues(currentMatcher, newMatcher);
					newMatcher.setAD_Org_ID(0);
					newMatcher.setC_BankStatementMatcher_ID(currentReference.getC_BankStatementMatcher_ID());
					int newBankReferenceId = copy.getReferenceId(I_C_Bank.Table_Name, currentMatcher.getC_Bank_ID());
					if(newBankReferenceId > 0) {
						newMatcher.setC_Bank_ID(newBankReferenceId);
					}
					newMatcher.saveEx();
					copy.saveReference(currentMatcher, newMatcher);
				});
			});
		} catch (Exception e) {
			throw new AdempiereException(e);
		} finally {
			CopyContextUtil.newInstance().revertContextToCurrentClient(getCtx(), getProcessInfo().isBatch());
		}
		return "Ok";
	}
}
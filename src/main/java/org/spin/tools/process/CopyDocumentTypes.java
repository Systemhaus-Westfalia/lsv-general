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
import org.adempiere.core.domains.models.I_AD_Org;
import org.adempiere.core.domains.models.I_AD_Sequence;
import org.adempiere.core.domains.models.I_C_DocType;
import org.compiere.model.MDocType;
import org.compiere.model.MSequence;
import org.compiere.util.Env;
import org.spin.tools.util.CopyUtil;

/** Generated Process for (Copy Document Types)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.3
 */
public class CopyDocumentTypes extends CopyDocumentTypesAbstract {
	/**	Copy Util	*/
	private CopyUtil copy;
	/** WindowNo for this process */
	public static final int     WINDOW_THIS_PROCESS = 9999;
	
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
			Env.setContext(getCtx(), WINDOW_THIS_PROCESS, "AD_Client_ID", copy.getTargetClientId());
			Env.setContext(getCtx(), "#AD_Client_ID", copy.getTargetClientId());
			copy.getTemplateReferencesOnlyNewIds(I_C_DocType.Table_Name).forEach(currentDocumentTypeId -> {
				MDocType currentDocumentType = new MDocType(getCtx(), currentDocumentTypeId, get_TrxName());
				MDocType newDocumentType = new MDocType(getCtx(), 0, get_TrxName());
				copy.copyCurrentValues(currentDocumentType, newDocumentType);
				int newOrganizationId = copy.getReferenceId(I_AD_Org.Table_Name, currentDocumentType.getAD_Org_ID());
				if(newOrganizationId < 0) {
					newOrganizationId = 0;
				}
				newDocumentType.setAD_Org_ID(newOrganizationId);
				newDocumentType.saveEx();
				copy.saveReference(currentDocumentType, newDocumentType);
				if(currentDocumentType.getDocNoSequence_ID() > 0) {
					int sequenceId = copy.getReferenceId(I_AD_Sequence.Table_Name, currentDocumentType.getDocNoSequence_ID());
					if(sequenceId <= 0) {
						MSequence sequence = createSequence(currentDocumentType.getDocNoSequence_ID());
						sequenceId = sequence.getAD_Sequence_ID();
					}
					newDocumentType.setDocNoSequence_ID(sequenceId);
				}
				if(currentDocumentType.getDefiniteSequence_ID() > 0) {
					int sequenceId = copy.getReferenceId(I_AD_Sequence.Table_Name, currentDocumentType.getDefiniteSequence_ID());
					if(sequenceId <= 0) {
						MSequence sequence = createSequence(currentDocumentType.getDefiniteSequence_ID());
						sequenceId = sequence.getAD_Sequence_ID();
					}
					newDocumentType.setDefiniteSequence_ID(sequenceId);
				}
				if(currentDocumentType.get_ValueAsInt("ControlNoSequence_ID") > 0) {
					int sequenceId = copy.getReferenceId(I_AD_Sequence.Table_Name, currentDocumentType.get_ValueAsInt("ControlNoSequence_ID"));
					if(sequenceId <= 0) {
						MSequence sequence = createSequence(currentDocumentType.get_ValueAsInt("ControlNoSequence_ID"));
						sequenceId = sequence.getAD_Sequence_ID();
					}
					newDocumentType.set_ValueOfColumn("ControlNoSequence_ID", sequenceId);
				}
				newDocumentType.saveEx();
			});
		} catch (Exception e) {
			Env.setContext(getCtx(), WINDOW_THIS_PROCESS, "AD_Client_ID", copy.getTemplateClientId());
			Env.setContext(getCtx(), "#AD_Client_ID", copy.getTemplateClientId());
			throw new AdempiereException(e);
		} finally {
			Env.setContext(getCtx(), WINDOW_THIS_PROCESS, "AD_Client_ID", copy.getTemplateClientId());
			Env.setContext(getCtx(), "#AD_Client_ID", copy.getTemplateClientId());
		}
		return "Ok";
	}
	
	/**
	 * Create Sequence from old Sequence
	 * @param currentSequenceId
	 * @return
	 */
	private MSequence createSequence(int currentSequenceId) {
		MSequence currentSequence = new MSequence(getCtx(), currentSequenceId, get_TrxName());
		MSequence newSequence = new MSequence(getCtx(), 0, get_TrxName());
		copy.copyCurrentValues(currentSequence, newSequence);
		newSequence.setCurrentNext(currentSequence.getIncrementNo());
		copy.saveReference(currentSequence, newSequence);
		return newSequence;
	}
}
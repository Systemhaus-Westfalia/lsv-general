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
import org.adempiere.core.domains.models.I_ASP_ClientLevel;
import org.adempiere.core.domains.models.X_ASP_ClientLevel;
import org.spin.tools.util.CopyContextUtil;
import org.spin.tools.util.CopyUtil;

/** Generated Process for (Copy Material Definitions)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.3
 */
public class CopyMaterialDefinitions extends CopyMaterialDefinitionsAbstract {
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
			copy.getTemplateReferencesOnlyNewIds(I_ASP_ClientLevel.Table_Name).forEach(currentASPClientLevelId -> {
				X_ASP_ClientLevel currentClientLevel = new X_ASP_ClientLevel(getCtx(), currentASPClientLevelId, get_TrxName()); 
				X_ASP_ClientLevel newClientLevel = new X_ASP_ClientLevel(getCtx(), 0, get_TrxName());
				copy.copyCurrentValues(currentClientLevel, newClientLevel);
				copy.saveReference(currentClientLevel, newClientLevel);
			});
		} catch (Exception e) {
			throw new AdempiereException(e);
		} finally {
			CopyContextUtil.newInstance().revertContextToCurrentClient(getCtx(), getProcessInfo().isBatch());
		}
		return "Ok";
	}
}
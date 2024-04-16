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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.core.domains.models.I_AD_Org;
import org.adempiere.core.domains.models.I_AD_Role;
import org.adempiere.core.domains.models.I_AD_User;
import org.compiere.model.MClient;
import org.compiere.model.MRole;
import org.compiere.model.MRoleIncluded;
import org.compiere.model.MUserRoles;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.spin.tools.util.CopyContextUtil;
import org.spin.tools.util.CopyUtil;

/** Generated Process for (Copy Role from Client)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.3
 */
public class CopyRoleDefinition extends CopyRoleDefinitionAbstract {
	/**	Copy Util	*/
	private CopyUtil copy;
	/**	Current Client	*/
	private MClient currentClient;
	/**	New Client	*/
	private MClient newClient;
	
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
		setTargetClientId(targetClientId);
		if(targetClientId == 0) {
			throw new AdempiereException("@ECA25_TargetClient_ID@ @NotFound@");
		}
		currentClient = new MClient(getCtx(), getTemplateClientId(), get_TrxName());
		newClient = new MClient(getCtx(), getTargetClientId(), get_TrxName());
		copy = CopyUtil.newInstance().withTeplateClientId(getTemplateClientId()).withTargetClientId(targetClientId).withContext(getCtx()).withTransactionName(get_TrxName());
	}

	@Override
	protected String doIt() throws Exception {
		try {
			CopyContextUtil.newInstance().updateContextToNewClient(getCtx(), copy.getTargetClientId(), getProcessInfo().isBatch());
			//	Rols
			List<Integer> copiedRolesIds = new ArrayList<>();
			copy.getTemplateReferencesOnlyNewIds(I_AD_Role.Table_Name).forEach(currentRoleId -> {
				if(!copiedRolesIds.contains(currentRoleId)) {
					MRole currentRole = new MRole(getCtx(), currentRoleId, get_TrxName());
					MRole newRole = copyRole(currentRole);
					List<MRole> includedRoles = currentRole.getIncludedRoles(false);
					if(includedRoles != null) {
						includedRoles.stream().filter(currentIncludedRole -> !copiedRolesIds.contains(currentIncludedRole.getAD_Role_ID())).forEach(currentIncludedRole -> {
							MRole newIncludedRole = copyRole(currentIncludedRole);
							MRoleIncluded newIncludedRoleReference = new MRoleIncluded(getCtx(), 0, get_TrxName());
							newIncludedRoleReference.setAD_Role_ID(newRole.getAD_Role_ID());
							newIncludedRoleReference.setIncluded_Role_ID(newIncludedRole.getAD_Role_ID());
							newIncludedRoleReference.saveEx();
							copy.saveReference(newIncludedRoleReference, newIncludedRole);
						});
					}
				}
			});
		} catch (Exception e) {
			throw new AdempiereException(e);
		} finally {
			CopyContextUtil.newInstance().revertContextToCurrentClient(getCtx(), getProcessInfo().isBatch());
		}
		return "Ok";
	}
	
	/**
	 * Copy a Role
	 * @param sourceRole
	 * @return
	 */
	private MRole copyRole(MRole sourceRole) {
		MRole newRole = new MRole(getCtx(), 0, get_TrxName());
		copy.copyCurrentValues(sourceRole, newRole);
		String newName = newClient.getValue() + " > " + sourceRole.getName().replaceAll(currentClient.getValue() + " > ", "")
				.replaceAll(currentClient.getName() + " > ", "")
				.replaceAll(currentClient.getValue(), "")
				.replaceAll(currentClient.getName(), "");
		newRole.setName(newName);
		newRole.setIsDirectLoad(true);
		int newOrganizationId = copy.getReferenceId(I_AD_Org.Table_Name, sourceRole.getAD_Org_ID());
		if(newOrganizationId < 0) {
			newOrganizationId = 0;
		}
		newRole.setAD_Org_ID(newOrganizationId);
		copy.saveReference(sourceRole, newRole);
		//	Add users, only from system
		new Query(getCtx(), I_AD_User.Table_Name, "AD_Client_ID = 0 AND IsLoginUser = 'Y' AND IsInternalUser = 'Y'", get_TrxName())
			.setOnlyActiveRecords(true)
			.getIDsAsList()
			.forEach(userId -> {
			MUserRoles userRoles = new MUserRoles(getCtx(), userId, newRole.getAD_Role_ID(), get_TrxName());
			userRoles.saveEx();
		});
		//	For all entities of role
		String[] tables = new String[] {"AD_Window_Access", "AD_Process_Access", "AD_Form_Access",
				"AD_Workflow_Access", "AD_Task_Access", "AD_Document_Action_Access", "AD_Browse_Access"
		};
		String[] keycolumns = new String[] {"AD_Window_ID", "AD_Process_ID", "AD_Form_ID",
				"AD_Workflow_ID", "AD_Task_ID", "C_DocType_ID, AD_Ref_List_ID", "AD_Browse_ID"
		};
		
		int action = 0;
		for ( int i = 0; i < tables.length; i++ ) {
			String table = tables[i];
			String keycolumn = keycolumns[i];
			
			String sql = "DELETE FROM " + table + " WHERE AD_Role_ID = " + newRole.getAD_Role_ID();
			int no = DB.executeUpdateEx(sql, get_TrxName());
			addLog(action++, null, BigDecimal.valueOf(no), "Old records deleted from " + table );
			
			final boolean column_IsReadWrite =
				!table.equals("AD_Document_Action_Access");
			
			sql = "INSERT INTO " + table
			+   " (AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, " 
			+   "AD_Role_ID, " + keycolumn +", isActive";
			if (column_IsReadWrite)
				sql += ", isReadWrite) ";
			else
				sql +=  ") ";
			sql	+= "SELECT " + getTargetClientId()
			+	", "+ 0
			+	", getdate(), "+ getAD_User_ID()
			+	", getdate(), "+ getAD_User_ID()
			+	", " + newRole.getAD_Role_ID()
			+	", " + keycolumn
			+	", IsActive ";
			if (column_IsReadWrite)
				sql += ", isReadWrite ";
			sql += "FROM " + table + " WHERE AD_Role_ID = " + sourceRole.getAD_Role_ID();

			no = DB.executeUpdateEx (sql, get_TrxName());
		}
		String updateUserRole = "INSERT INTO AD_User_Roles (ad_user_id ,"
				+ "    ad_role_id ,"
				+ "    ad_client_id ,"
				+ "    ad_org_id ,"
				+ "    isactive ,"
				+ "    created ,"
				+ "    createdby ,"
				+ "    updated ,"
				+ "    updatedby ,"
				+ "    isdefault )"
				+ " VALUES(100,"+  newRole.getAD_Role_ID() +"," + getTargetClientId() 
				+ " , 0 , 'Y'" 
				+	", getdate(), "+ getAD_User_ID()
				+	", getdate(), "+ getAD_User_ID()
				+ ", 'N')";
		int no = DB.executeUpdateEx (updateUserRole, get_TrxName());
		addLog("@AD_Role_ID@: " + newRole.getName());
		return newRole;
	}
}
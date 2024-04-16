/*************************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                              *
 * This program is free software; you can redistribute it and/or modify it    		 *
 * under the terms version 2 or later of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope   		 *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied 		 *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           		 *
 * See the GNU General Public License for more details.                       		 *
 * You should have received a copy of the GNU General Public License along    		 *
 * with this program; if not, write to the Free Software Foundation, Inc.,    		 *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     		 *
 * For the text or an alternative of this public license, you may reach us    		 *
 * Copyright (C) 2012-2018 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpya.com				  		                 *
 *************************************************************************************/
package org.spin.tools.util;

import java.util.List;
import java.util.Properties;

import org.adempiere.core.domains.models.I_AD_Org;
import org.compiere.model.MMigration;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.process.MigrationApply;
import org.compiere.process.ProcessInfo;
import org.compiere.util.DB;
import org.compiere.util.Util;
import org.eevolution.services.dsl.ProcessBuilder;
import org.spin.tools.process.CopyGLDefinitions;
/**
 * Util class for copy entities
 * @author yamel
 *
 */
public class CopyUtil {
	/**	Context	*/
	private Properties context;
	/**	Transaction Name	*/
	private String transactionName;
	/**	Template or source ID	*/
	private int templateClientId;
	/**	Target ID	*/
	private int targetClientId;
	
	
	public static CopyUtil newInstance() {
		return new CopyUtil();
	}
	
	/**
	 * Run a process for copy
	 */
	public void runCopyProcess(Class<?> clazz) {
		ProcessBuilder.create(getContext())
		.process(clazz)
		.withParameter("ECA25_TemplateClient_ID", getTemplateClientId())
		.withParameter("ECA25_TargetClient_ID", getTargetClientId())
		.withBatchMode()
		.withoutTransactionClose()
		.execute(getTransactionName());	

		
	}
	
	
	
	
	/**
	 * Get reference Id
	 * @param tableName
	 * @param oldId
	 * @return
	 */
	public int getReferenceId(String tableName, int oldId) {
		return DB.getSQLValueEx(getTransactionName(), 
				"SELECT ECA25_Target_ID FROM T_ECA25_CopyReference WHERE ECA25_TemplateClient_ID = ? AND ECA25_TargetClient_ID = ? AND TableName = ? AND ECA25_Template_ID = ?", 
				getTemplateClientId(), getTargetClientId(), tableName, oldId);
	}
	
	public int getnewOrg_ID(int oldID) {
		int orgID=getReferenceId(I_AD_Org.Table_Name, oldID);
		return orgID;		
	}
	
	/**
	 * Put Reference Id
	 * @param tableName
	 * @param oldId
	 * @param newId
	 */
	public CopyUtil putReferenceId(String tableName, int oldId, int newId) {
		if(Util.isEmpty(tableName) || oldId <= 0 || newId <= 0) {
			return this;
		}
		DB.executeUpdateEx("INSERT INTO T_ECA25_CopyReference(ECA25_TemplateClient_ID, ECA25_TargetClient_ID, TableName, ECA25_Template_ID, ECA25_Target_ID) VALUES(?, ?, ?, ?, ?)", 
				new Object[]{getTemplateClientId(), getTargetClientId(), tableName, oldId, newId}, 
				getTransactionName());
		return this;
	}
	
	/**
	 * Copy current values
	 * @param source
	 * @param target
	 */
	public void copyCurrentValues(PO source, PO target) {
		PO.copyValues(source, target);
		target.setIsActive(source.isActive());
	}
	
	/**
	 * Save Reference
	 * @param source
	 * @param target
	 */
	public void saveReference(PO source, PO target) {
		target.set_ValueOfColumn("UUID", String.valueOf(source.get_ID()));
		target.setIsDirectLoad(true);
		target.saveEx();
		putReferenceId(source.get_TableName(), source.get_ID(), target.get_ID());
	}
	
	/**
	 * Get Template References IDs
	 * @param tableName
	 * @return
	 */
	public List<Integer> getTemplateReferencesOnlyNewIds(String tableName) {
		String keyColumn = MTable.get(getContext(), tableName).getKeyColumns()[0];
		return new Query(getContext(), tableName, "AD_Client_ID = ? AND IsActive = 'Y' "
				+ "AND NOT EXISTS(SELECT 1 FROM T_ECA25_CopyReference r WHERE r.ECA25_TemplateClient_ID = ? AND r.ECA25_TargetClient_ID = ? AND r.TableName = ? AND r.ECA25_Template_ID = " + tableName + "." + keyColumn + ")", getTransactionName())
		.setParameters(getTemplateClientId(), getTemplateClientId(), getTargetClientId(), tableName)
		.getIDsAsList();
	}
	
	/**
	 * Get Template References IDs and validate only new
	 * @param tableName
	 * @return
	 */
	public List<Integer> getTemplateReferencesIds(String tableName) {
		return new Query(getContext(), tableName, "AD_Client_ID = ?", getTransactionName())
				.setParameters(getTemplateClientId())
				.getIDsAsList();
	}
	
	/**
	 * Copy translation from PO
	 * @param source
	 * @param target
	 */
	public void copyTranslation(PO source, PO target) {
		String tableName = source.get_TableName() + "_Trl";
		MTable.get(getContext(), source.get_Table_ID()).getColumnsAsList().stream().filter(column -> column.isTranslated()).findAny().ifPresent(column -> {
			new Query(getContext(), tableName, source.get_KeyColumns()[0] + " = ?", getTransactionName())
				.setParameters(source.get_ID())
				.<PO>list()
				.forEach(sourceTranslation -> {
					new Query(getContext(), tableName, target.get_KeyColumns()[0] + " = ? AND AD_Language = ?", getTransactionName())
					.setParameters(target.get_ID(), sourceTranslation.get_ValueAsString("AD_Language"))
					.<PO>list()
					.forEach(targetTranslation -> {
						MTable.get(getContext(), source.get_Table_ID()).getColumnsAsList().stream().filter(translatedColumn -> translatedColumn.isTranslated()).forEach(translatedColumn -> {
							targetTranslation.set_ValueOfColumn(translatedColumn.getColumnName(), sourceTranslation.get_Value(translatedColumn.getColumnName()));
						});
						targetTranslation.saveEx();
					});
				});
		});
	}
	
	public Properties getContext() {
		return context;
	}
	
	public CopyUtil withContext(Properties context) {
		this.context = context;
		return this;
	}
	
	public String getTransactionName() {
		return transactionName;
	}
	
	public CopyUtil withTransactionName(String transactionName) {
		this.transactionName = transactionName;
		return this;
	}
	
	public int getTemplateClientId() {
		return templateClientId;
	}
	
	public int getTargetClientId() {
		return targetClientId;
	}
	
	public CopyUtil withTeplateClientId(int teplateClientId) {
		this.templateClientId = teplateClientId;
		return this;
	}
	
	public CopyUtil withTargetClientId(int targetClientId) {
		this.targetClientId = targetClientId;
		return this;
	}
}

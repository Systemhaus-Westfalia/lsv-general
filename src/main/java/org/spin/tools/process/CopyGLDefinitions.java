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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.core.domains.models.I_AD_Org;
import org.adempiere.core.domains.models.I_AD_Tree;
import org.adempiere.core.domains.models.I_C_AcctSchema;
import org.adempiere.core.domains.models.I_C_AcctSchema_Default;
import org.adempiere.core.domains.models.I_C_AcctSchema_Element;
import org.adempiere.core.domains.models.I_C_AcctSchema_GL;
import org.adempiere.core.domains.models.I_C_Activity;
import org.adempiere.core.domains.models.I_C_BPartner;
import org.adempiere.core.domains.models.I_C_Campaign;
import org.adempiere.core.domains.models.I_C_Element;
import org.adempiere.core.domains.models.I_C_ElementValue;
import org.adempiere.core.domains.models.I_C_Location;
import org.adempiere.core.domains.models.I_C_Project;
import org.adempiere.core.domains.models.I_C_SalesRegion;
import org.adempiere.core.domains.models.I_C_ValidCombination;
import org.adempiere.core.domains.models.I_M_Product;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MAcctSchemaDefault;
import org.compiere.model.MAcctSchemaElement;
import org.compiere.model.MAcctSchemaGL;
import org.compiere.model.MClient;
import org.compiere.model.MClientInfo;
import org.compiere.model.MCurrency;
import org.compiere.model.MElement;
import org.compiere.model.MElementValue;
import org.compiere.model.MRefList;
import org.compiere.model.MTable;
import org.compiere.model.MTree;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.DisplayType;
import org.spin.tools.util.CopyContextUtil;
import org.spin.tools.util.CopyUtil;

/** Generated Process for (Copy GL Definitions)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.3
 */
public class CopyGLDefinitions extends CopyGLDefinitionsAbstract {
	
	/**	Copy Util	*/
	private CopyUtil copy;
	/**	New Client	*/
	private MClient newClient;
	/**	New Client Info	*/
	private MClientInfo newClientInfo;

	/**	New Client Info	*/
	private MClientInfo currentClientInfo;
	
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
		newClient = new MClient(getCtx(), targetClientId, get_TrxName());
		newClientInfo = MClientInfo.get(getCtx(), newClient.getAD_Client_ID(), get_TrxName());
		currentClientInfo = MClientInfo.get(getCtx(), getTemplateClientId(), get_TrxName());
		copy = CopyUtil.newInstance().withTeplateClientId(getTemplateClientId()).withTargetClientId(targetClientId).withContext(getCtx()).withTransactionName(get_TrxName());
	}

	@Override
	protected String doIt() throws Exception {
		try {
			CopyContextUtil.newInstance().updateContextToNewClient(getCtx(), copy.getTargetClientId(), getProcessInfo().isBatch());
			copyAccountSchema();
		} catch (Exception e) {
			throw new AdempiereException(e);
		} finally {
			CopyContextUtil.newInstance().revertContextToCurrentClient(getCtx(), getProcessInfo().isBatch());
		}
		return "Ok";
	}
	
	/**
	 * Copy all account schema
	 */
	private void copyAccountSchema() {
		copy.getTemplateReferencesOnlyNewIds(I_C_AcctSchema.Table_Name).forEach(currentAccountSchemaId -> {
			MAcctSchema currentAccountSchema = new MAcctSchema(getCtx(), currentAccountSchemaId, get_TrxName());
			MCurrency currency = MCurrency.get(getCtx(), currentAccountSchema.getC_Currency_ID());
			MAcctSchema newAccountSchema = new MAcctSchema(getCtx(), 0, get_TrxName());
			copy.copyCurrentValues(currentAccountSchema, newAccountSchema);
			newAccountSchema.setName(newClient.getName() + " " + newAccountSchema.getGAAP() + "/" + currency.getISO_Code());
			newAccountSchema.set_ValueOfColumn(I_C_AcctSchema.COLUMNNAME_C_Period_ID, null);
			newAccountSchema.setAD_Org_ID(0);
			newAccountSchema.saveEx();
			copy.putReferenceId(I_C_AcctSchema.Table_Name, currentAccountSchemaId, newAccountSchema.getC_AcctSchema_ID());
		});
		newClientInfo.setC_AcctSchema1_ID(copy.getReferenceId(I_C_AcctSchema.Table_Name, currentClientInfo.getC_AcctSchema1_ID()));
		newClientInfo.saveEx(get_TrxName());
		//	Copy Elements
		copyAccountElements();
		copyElementValues();
		List<Integer> accountSchemaElement = new ArrayList<>();
		//	Account Schema Elements
		copy.getTemplateReferencesOnlyNewIds(I_C_AcctSchema_Element.Table_Name).forEach(currentAccountSchemaEmentId -> {
			MAcctSchemaElement currentAccountSchemaElement = new MAcctSchemaElement(getCtx(), currentAccountSchemaEmentId, get_TrxName());
			MAcctSchemaElement newAccountSchemaElement = new MAcctSchemaElement(getCtx(), 0, get_TrxName());
			copy.copyCurrentValues(currentAccountSchemaElement, newAccountSchemaElement);
			newAccountSchemaElement.setC_AcctSchema_ID(copy.getReferenceId(I_C_AcctSchema.Table_Name, currentAccountSchemaElement.getC_AcctSchema_ID()));
			//	Value
			int id = -1;
			if(currentAccountSchemaElement.getC_Activity_ID() > 0) {
				id = copy.getReferenceId(I_C_Activity.Table_Name, currentAccountSchemaElement.getC_Activity_ID());
				if(id > 0) {
					newAccountSchemaElement.setC_Activity_ID(id);
				}
			}
			if(currentAccountSchemaElement.getC_Location_ID() > 0) {
				id = copy.getReferenceId(I_C_Location.Table_Name, currentAccountSchemaElement.getC_Location_ID());
				if(id > 0) {
					newAccountSchemaElement.setC_Location_ID(id);
				}
			}
			if(currentAccountSchemaElement.getC_Project_ID() > 0) {
				id = copy.getReferenceId(I_C_Project.Table_Name, currentAccountSchemaElement.getC_Project_ID());
				if(id > 0) {
					newAccountSchemaElement.setC_Project_ID(id);
				}
			}
			if(currentAccountSchemaElement.getC_SalesRegion_ID() > 0) {
				id = copy.getReferenceId(I_C_SalesRegion.Table_Name, currentAccountSchemaElement.getC_SalesRegion_ID());
				if(id > 0) {
					newAccountSchemaElement.setC_SalesRegion_ID(id);
				}
			}
			if(currentAccountSchemaElement.getM_Product_ID() > 0) {
				id = copy.getReferenceId(I_M_Product.Table_Name, currentAccountSchemaElement.getM_Product_ID());
				if(id > 0) {
					newAccountSchemaElement.setM_Product_ID(id);
				}
			}
			if(currentAccountSchemaElement.getC_BPartner_ID() > 0) {
				id = copy.getReferenceId(I_C_BPartner.Table_Name, currentAccountSchemaElement.getC_BPartner_ID());
				if(id > 0) {
					newAccountSchemaElement.setC_BPartner_ID(id);
				}
			}
			if(currentAccountSchemaElement.getC_Campaign_ID() > 0) {
				id = copy.getReferenceId(I_C_Campaign.Table_Name, currentAccountSchemaElement.getC_Campaign_ID());
				if(id > 0) {
					newAccountSchemaElement.setC_Campaign_ID(id);
				}
			}
			if(currentAccountSchemaElement.getC_Element_ID() > 0) {
				id = copy.getReferenceId(I_C_Element.Table_Name, currentAccountSchemaElement.getC_Element_ID());
				if(id > 0) {
					newAccountSchemaElement.setC_Element_ID(id);
				}
			}
			if(currentAccountSchemaElement.getC_Activity_ID() > 0) {
				id = copy.getReferenceId(I_AD_Org.Table_Name, currentAccountSchemaElement.getOrg_ID());
				if(id > 0) {
					newAccountSchemaElement.setOrg_ID(id);
				}
			}
			//	
			newAccountSchemaElement.saveEx();
			copy.saveReference(currentAccountSchemaElement, newAccountSchemaElement);
			//	For element Value
			if(currentAccountSchemaElement.getC_ElementValue_ID() > 0) {
				accountSchemaElement.add(newAccountSchemaElement.getC_AcctSchema_Element_ID());
			}
			copy.putReferenceId(I_C_AcctSchema_Element.Table_Name, currentAccountSchemaEmentId, newAccountSchemaElement.getC_AcctSchema_Element_ID());
		});
		//	Copy all combinations
		copyValidCombinations();
		accountSchemaElement.stream().map(schemaElementId -> new MAcctSchemaElement(getCtx(), schemaElementId, get_TrxName())).forEach(schemaElement -> {
			int id = copy.getReferenceId(I_C_ElementValue.Table_Name, schemaElement.getC_ElementValue_ID());
			if(id > 0) {
				schemaElement.setC_ElementValue_ID(id);
				schemaElement.saveEx();
			}
		});
		//	Account Schema GL
		copy.getTemplateReferencesOnlyNewIds(I_C_AcctSchema_GL.Table_Name).forEach(currentSchemaGLId -> {
			MAcctSchemaGL currentSchemaGL = new MAcctSchemaGL(getCtx(), currentSchemaGLId, get_TrxName());
			MAcctSchemaGL newSchemaGL = new MAcctSchemaGL(getCtx(), 0, get_TrxName());
			copy.copyCurrentValues(currentSchemaGL, newSchemaGL);
			newSchemaGL.setC_AcctSchema_ID(copy.getReferenceId(I_C_AcctSchema.Table_Name, currentSchemaGL.getC_AcctSchema_ID()));
			MTable.get(getCtx(), I_C_AcctSchema_GL.Table_Name)
			.getColumnsAsList()
			.stream()
			.filter(column -> column.getAD_Reference_ID() == DisplayType.Account)
			.map(column -> column.getColumnName())
			.forEach(columnName -> {
				if(currentSchemaGL.get_ValueAsInt(columnName) > 0) {
					int id = copy.getReferenceId(I_C_ValidCombination.Table_Name, currentSchemaGL.get_ValueAsInt(columnName));
					newSchemaGL.set_ValueOfColumn(columnName, id);
				}
			});
			newSchemaGL.saveEx();
			copy.saveReference(currentSchemaGL, newSchemaGL);
		});
		copy.getTemplateReferencesOnlyNewIds(I_C_AcctSchema_Default.Table_Name).forEach(currentSchemaDefaultId -> {
			MAcctSchemaDefault currentSchemaDefault = new MAcctSchemaDefault(getCtx(), currentSchemaDefaultId, get_TrxName());
			MAcctSchemaDefault newSchemaDefault = new MAcctSchemaDefault(getCtx(), 0, get_TrxName());
			copy.copyCurrentValues(currentSchemaDefault, newSchemaDefault);
			newSchemaDefault.setC_AcctSchema_ID(copy.getReferenceId(I_C_AcctSchema.Table_Name, currentSchemaDefault.getC_AcctSchema_ID()));
			MTable.get(getCtx(), I_C_AcctSchema_Default.Table_Name)
			.getColumnsAsList()
			.stream()
			.filter(column -> column.getAD_Reference_ID() == DisplayType.Account)
			.map(column -> column.getColumnName())
			.forEach(columnName -> {
				if(currentSchemaDefault.get_ValueAsInt(columnName) > 0) {
					int id = copy.getReferenceId(I_C_ValidCombination.Table_Name, currentSchemaDefault.get_ValueAsInt(columnName));
					newSchemaDefault.set_ValueOfColumn(columnName, id);
				}
			});
			newSchemaDefault.saveEx();
			copy.saveReference(currentSchemaDefault, newSchemaDefault);
		});
	}
	
	/**
	 * Copy all element values
	 */
	private void copyAccountElements() {
		copy.getTemplateReferencesOnlyNewIds(I_C_Element.Table_Name).forEach(currentElementId -> {
			MElement currentElement = new MElement(getCtx(), currentElementId, get_TrxName());
			MElement newElement = new MElement(getCtx(), 0, get_TrxName());
			copy.copyCurrentValues(currentElement, newElement);
			newElement.setElementType(currentElement.getElementType());
			newElement.setName(newClient.getName() + " " + currentElement.getName());
			newElement.setAD_Tree_ID(copy.getReferenceId(I_AD_Tree.Table_Name, currentElement.getAD_Tree_ID()));
			newElement.saveEx();
			copy.saveReference(currentElement, newElement);
			copy.putReferenceId(I_C_Element.Table_Name, currentElementId, newElement.getC_Element_ID());
		});
	}
	
	/**
	 * Copy all elements values
	 */
	private void copyElementValues() {
		//	Element Values
		List<Integer> elementValuesToUpdateIds = new ArrayList<>();
		copy.getTemplateReferencesOnlyNewIds(I_C_ElementValue.Table_Name).forEach(currentElementValueId -> {
			MElementValue currentElementValue = new MElementValue(getCtx(), currentElementValueId, get_TrxName());
			MElementValue newElementValue = new MElementValue(getCtx(), 0, get_TrxName());
			copy.copyCurrentValues(currentElementValue, newElementValue);
			newElementValue.setC_Element_ID(copy.getReferenceId(I_C_Element.Table_Name, currentElementValue.getC_Element_ID()));
			//	No insert nodes
			newElementValue.setIsDirectLoad(true);
			copy.saveReference(currentElementValue, newElementValue);
			if(currentElementValue.getParentElementValue_ID() > 0) {
				elementValuesToUpdateIds.add(newElementValue.getC_ElementValue_ID());
			}
			copy.putReferenceId(I_C_ElementValue.Table_Name, currentElementValueId, newElementValue.getC_ElementValue_ID());
		});
		elementValuesToUpdateIds.stream().map(elementValueId -> new MElementValue(getCtx(), elementValueId, get_TrxName())).forEach(elementValue -> {
			elementValue.setParentElementValue_ID(copy.getReferenceId(I_C_ElementValue.Table_Name, elementValue.getParentElementValue_ID()));
			//	No insert nodes
			elementValue.setIsDirectLoad(true);
			elementValue.saveEx();
		});
		//	Copy all nodes
		copy.getTemplateReferencesIds(I_C_Element.Table_Name).forEach(currentElementId -> {
			MElement currentElement = new MElement(getCtx(), currentElementId, get_TrxName());
			MTree currentTree = MTree.get(getCtx(), currentElement.getAD_Tree_ID(), get_TrxName());
			AtomicReference<MTable> treeNodeTable = new AtomicReference<MTable>();
			treeNodeTable.set(MTable.get(getCtx(), MTree.getNodeTableName(currentTree.getTreeType())));
			if(treeNodeTable.get() != null) {
				new Query(getCtx(), treeNodeTable.get().getTableName(), "AD_Tree_ID = ?", get_TrxName()).setParameters(currentTree.getAD_Tree_ID())
				.list()
				.forEach(currentTreeNode -> {
					int nodeId = copy.getReferenceId(I_C_ElementValue.Table_Name, currentTreeNode.get_ValueAsInt("Node_ID"));
					if(nodeId > 0) {
						PO newTreeNode = treeNodeTable.get().getPO(0, get_TrxName());
						newTreeNode.setAD_Org_ID(0);
						newTreeNode.setIsActive(currentTreeNode.isActive());
						newTreeNode.set_ValueOfColumn("AD_Tree_ID", copy.getReferenceId(I_AD_Tree.Table_Name, currentTree.getAD_Tree_ID()));
						newTreeNode.set_ValueOfColumn("Node_ID", copy.getReferenceId(I_C_ElementValue.Table_Name, currentTreeNode.get_ValueAsInt("Node_ID")));
						int parentId = copy.getReferenceId(I_C_ElementValue.Table_Name, currentTreeNode.get_ValueAsInt("Parent_ID"));
						if(parentId < 0) {
							parentId = 0;
						}
						newTreeNode.set_ValueOfColumn("Parent_ID", parentId);
						newTreeNode.set_ValueOfColumn("SeqNo", currentTreeNode.get_ValueAsInt("SeqNo"));
						copy.saveReference(currentTreeNode, newTreeNode);
						newTreeNode.saveEx();
					}
				});
			}
		});
	}
	
	/**
	 * Copy all valid combinations
	 */
	private void copyValidCombinations() {
		copy.getTemplateReferencesOnlyNewIds(I_C_ValidCombination.Table_Name).forEach(currentValidCombinationId -> {
			MAccount currentAccount = new MAccount(getCtx(), currentValidCombinationId, get_TrxName());
			MAccount newAccount = new MAccount(getCtx(), 0, get_TrxName());
			copy.copyCurrentValues(currentAccount, newAccount);
			newAccount.setAccount_ID(copy.getReferenceId(I_C_ElementValue.Table_Name, currentAccount.getAccount_ID()));
			newAccount.setC_AcctSchema_ID(copy.getReferenceId(I_C_AcctSchema.Table_Name, currentAccount.getC_AcctSchema_ID()));
			int newOrgID = copy.getReferenceId(I_AD_Org.Table_Name, currentAccount.getAD_Org_ID());
			if (newOrgID<0)
				newOrgID = 0;
			newAccount.setAD_Org_ID(newOrgID);
			newAccount.saveEx();
			copy.saveReference(currentAccount, newAccount);
			copy.putReferenceId(I_C_ValidCombination.Table_Name, currentValidCombinationId, newAccount.getC_ValidCombination_ID());
		});
	}
}
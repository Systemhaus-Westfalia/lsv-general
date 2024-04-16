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

import java.util.Arrays;
import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.core.domains.models.I_AD_Document_Action_Access;
import org.adempiere.core.domains.models.I_AD_Org;
import org.adempiere.core.domains.models.I_AD_Record_Access;
import org.adempiere.core.domains.models.I_AD_Role_OrgAccess;
import org.adempiere.core.domains.models.I_AD_Sequence;
import org.adempiere.core.domains.models.I_AD_User_OrgAccess;
import org.adempiere.core.domains.models.I_C_BPartner;
import org.adempiere.core.domains.models.I_C_Bank;
import org.adempiere.core.domains.models.I_C_BankAccount;
import org.adempiere.core.domains.models.I_C_BankAccountDoc;
import org.adempiere.core.domains.models.I_C_CashBook;
import org.adempiere.core.domains.models.I_C_DocType;
import org.adempiere.core.domains.models.I_C_Element;
import org.adempiere.core.domains.models.I_C_POS;
import org.adempiere.core.domains.models.I_M_Warehouse;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MBank;
import org.compiere.model.MBankAccount;
import org.compiere.model.MCashBook;
import org.compiere.model.MCity;
import org.compiere.model.MDocType;
import org.compiere.model.MLocation;
import org.compiere.model.MLocator;
import org.compiere.model.MOrg;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MPOS;
import org.compiere.model.MRecordAccess;
import org.compiere.model.MRegion;
import org.compiere.model.MRole;
import org.compiere.model.MRoleOrgAccess;
import org.compiere.model.MSequence;
import org.compiere.model.MUserOrgAccess;
import org.compiere.model.MWarehouse;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.adempiere.core.domains.models.X_AD_Document_Action_Access;
import org.adempiere.core.domains.models.X_C_BankAccountDoc;
import org.compiere.util.Env;
//import org.erpya.lve.util.LVEUtil;
//import org.spin.model.I_WH_DefinitionLine;
//import org.spin.model.MWHDefinitionLine;
import org.spin.tools.util.CopyContextUtil;
import org.spin.tools.util.CopyUtil;

/** Generated Process for (Copy Organization from Client)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.3
 */
public class CopyOrganizationDefinition extends CopyOrganizationDefinitionAbstract {
	/** WindowNo for this process */
	public static final int     WINDOW_THIS_PROCESS = 9999;
	/**	Current Organization	*/
	private MOrg currentOrganization;
	/**	New Organization	*/
	private MOrg newOrganization;
	/**	New Organization Info	*/
	private MOrgInfo newOrganizationInfo;
	/**	Copy Util	*/
	private CopyUtil copy;
	/**	Old values for context	*/
	private int organizationId;
	private int organizationWithWindowId;
	
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
		organizationWithWindowId = Env.getContextAsInt(getCtx(), WINDOW_THIS_PROCESS, "#AD_Org_ID");
		organizationId = Env.getContextAsInt(getCtx(), "#AD_Org_ID");
		currentOrganization = MOrg.get(getCtx(), getOrgId());
	}

	@Override
	protected String doIt() throws Exception {
		try {
			CopyContextUtil.newInstance().updateContextToNewClient(getCtx(), copy.getTargetClientId(), getProcessInfo().isBatch());
			copyOrganization();
			copyAccesses();
			copyLinkedBusinessPartner();
			copyDocumentTypes();
			copyWarehouses();
			copyPointOfSales();
			copyBanks();
			copyCashAccounts();	
		} catch (Exception e) {
			throw new AdempiereException(e);
		} finally {
			Env.setContext(getCtx(), WINDOW_THIS_PROCESS, "AD_Org_ID", organizationWithWindowId);
			Env.setContext(getCtx(), "#AD_Org_ID", organizationId);
			CopyContextUtil.newInstance().revertContextToCurrentClient(getCtx(), getProcessInfo().isBatch());
		}
		copy.putReferenceId(I_AD_Org.Table_Name, organizationId, newOrganization.getAD_Org_ID());

		return newOrganization.getValue() + " - " + newOrganization.getName();
	}
	
	/**
	 * Copy Banks
	 */
	private void copyBanks() {
		//	Banks
		copy.getTemplateReferencesOnlyNewIds(I_C_Bank.Table_Name).forEach(currentBankId -> {
			MBank currentBank = new MBank(getCtx(), currentBankId, get_TrxName()); 
			MBank newBank = new MBank(getCtx(), 0, get_TrxName());
			copy.copyCurrentValues(currentBank, newBank);
			newBank.setAD_Org_ID(0);
			newBank.saveEx();
			copy.saveReference(currentBank, newBank);
		});
	}

	/**
	 * Copy Organization
	 */
	private void copyOrganization() {
		newOrganization = new MOrg(getCtx(), 0, get_TrxName());
		PO.copyValues(currentOrganization, newOrganization);
		newOrganization.setValue(getValue());
		newOrganization.setName(getName());
		newOrganization.saveEx(get_TrxName());
		copy.withTargetClientId(newOrganization.getAD_Client_ID());
		Env.setContext(getCtx(), WINDOW_THIS_PROCESS, "AD_Org_ID", newOrganization.getAD_Org_ID());
		Env.setContext(getCtx(), "#AD_Org_ID", newOrganization.getAD_Org_ID());
		//	Set Organization Info
		newOrganizationInfo = MOrgInfo.get(getCtx(), newOrganization.getAD_Org_ID(), get_TrxName());
		MCity city = MCity.get(getCtx(), getCityId());
//				Info
		int currentCountryId = getRegionId() > 0? MRegion.get(getCtx(), getRegionId()).getC_Country_ID()
						: city.getC_Country_ID();

		MLocation newLocation = new MLocation(getCtx(), currentCountryId, getRegionId(), city.getName(), get_TrxName());
		newLocation.setC_City_ID(getCityId());
		//	For address
		Optional.ofNullable(getAddress1()).ifPresent(address -> newLocation.setAddress1(address));
		Optional.ofNullable(getAddress2()).ifPresent(address -> newLocation.setAddress2(address));
		Optional.ofNullable(getAddress3()).ifPresent(address -> newLocation.setAddress3(address));
		Optional.ofNullable(getAddress4()).ifPresent(address -> newLocation.setAddress4(address));
		//	Save
		newLocation.saveEx(get_TrxName());
		//	Set
		newOrganizationInfo.setTaxID(getTaxID());
		newOrganizationInfo.setC_Location_ID(newLocation.getC_Location_ID());
		newOrganizationInfo.saveEx(get_TrxName());
		//	Add
		addLog("@AD_Org_ID@: " + newOrganization.getValue() + " - " + newOrganization.getName());
	}
	
	/**
	 * Copy all document types
	 */
	private void copyDocumentTypes() {
		new Query(getCtx(), I_C_DocType.Table_Name, I_C_DocType.COLUMNNAME_AD_Org_ID + " = ?", get_TrxName())
				.setParameters(getOrgId())
				.getIDsAsList().forEach(currentDocumentTypeId -> {
					createAndGetDocumentTypeId(currentDocumentTypeId);
		});
	}
	
	/**
	 * Copy Organization Business Partner
	 */
	private void copyLinkedBusinessPartner() {
		MBPartner currentLinkedBusinessPartner = new Query(getCtx(), I_C_BPartner.Table_Name, I_C_BPartner.COLUMNNAME_AD_OrgBP_ID + " = ?", get_TrxName())
				.setParameters(getOrgId())
				.first();
		//	Validate
		if(currentLinkedBusinessPartner != null
				&& currentLinkedBusinessPartner.getC_BPartner_ID() > 0) {
			MBPartner newLinkedBusinessPartner = new MBPartner(getCtx(), 0, get_TrxName());
			PO.copyValues(currentLinkedBusinessPartner, newLinkedBusinessPartner);
			newLinkedBusinessPartner.setAD_Org_ID(newOrganization.getAD_Org_ID());
			newLinkedBusinessPartner.setAD_OrgBP_ID(newOrganization.getAD_Org_ID());
			newLinkedBusinessPartner.setValue(newOrganization.getValue());
			newLinkedBusinessPartner.setName(newOrganization.getName());
			newLinkedBusinessPartner.setDescription(newOrganization.getDescription());
			newLinkedBusinessPartner.saveEx(get_TrxName());
			//	Info
			MCity city = MCity.get(getCtx(), getCityId());
			int currentCountryId = getRegionId() > 0? MRegion.get(getCtx(), getRegionId()).getC_Country_ID()
					: city.getC_Country_ID();
			MLocation newLocation = new MLocation(getCtx(), currentCountryId, getRegionId(), city.getName(), get_TrxName());
			newLocation.setC_City_ID(getCityId());
			//	For address
			Optional.ofNullable(getAddress1()).ifPresent(address -> newLocation.setAddress1(address));
			Optional.ofNullable(getAddress2()).ifPresent(address -> newLocation.setAddress2(address));
			Optional.ofNullable(getAddress3()).ifPresent(address -> newLocation.setAddress3(address));
			Optional.ofNullable(getAddress4()).ifPresent(address -> newLocation.setAddress4(address));
			//	Save
			newLocation.saveEx(get_TrxName());
			//	Set
			MBPartnerLocation newLinkedBusinessPartnerLocation = new MBPartnerLocation(newLinkedBusinessPartner);
			newLinkedBusinessPartnerLocation.setC_Location_ID(newLocation.getC_Location_ID());
			newLinkedBusinessPartnerLocation.saveEx(get_TrxName());
			//	Add
			addLog("@AD_OrgBP_ID@: " + newLinkedBusinessPartner.getValue() + " - " + newLinkedBusinessPartner.getName());
		}
	}
	
	
	/**
	 * Copy all Point of Sales
	 */
	private void copyPointOfSales() {
		new Query(getCtx(), I_C_POS.Table_Name, I_C_POS.COLUMNNAME_AD_Org_ID + " = ?", get_TrxName())
				.setParameters(getOrgId())
				.getIDsAsList().forEach(currentPointOfSalesId -> {
					createAndGetPointOfSales(currentPointOfSalesId);
		});
	}
	
	/**
	 * Copy all Cash Accounts
	 */
	private void copyCashAccounts() {
		new Query(getCtx(), I_C_BankAccount.Table_Name, I_C_BankAccount.COLUMNNAME_AD_Org_ID + " = ? "
				+ "AND EXISTS(SELECT 1 FROM C_Bank b WHERE b.C_Bank_ID = C_BankAccount.C_Bank_ID AND b.BankType = 'C')", get_TrxName())
				.setParameters(getOrgId())
				.getIDsAsList().forEach(currentCashAccountId -> {
					createAndCashAccount(currentCashAccountId);
		});
	}
	
	/**
	 * Copy All Warehouses
	 */
	private void copyWarehouses() {
		new Query(getCtx(), I_M_Warehouse.Table_Name, I_M_Warehouse.COLUMNNAME_AD_Org_ID + " = ?", get_TrxName())
				.setParameters(getOrgId())
				.getIDsAsList().forEach(currentWareId -> {
					createAndGetWarehouse(currentWareId);
		});
	}
	
	/**
	 * Copy All Organization Access
	 */
	private void copyAccesses() {
		//	For Role
		new Query(getCtx(), I_AD_Role_OrgAccess.Table_Name, I_AD_Role_OrgAccess.COLUMNNAME_AD_Org_ID + " = ? "
				+ "AND NOT EXISTS(SELECT 1 FROM AD_Role_OrgAccess ra WHERE ra.AD_Role_ID = AD_Role_OrgAccess.AD_Role_ID AND ra.AD_Org_ID = ?)", get_TrxName())
				.setParameters(getOrgId(), newOrganization.getAD_Org_ID())
				.<MRoleOrgAccess>list().stream()
				.forEach(currentOrganizationAccess -> {
					MRoleOrgAccess newOrganizationAccess = new MRoleOrgAccess((MRole) currentOrganizationAccess.getAD_Role(), newOrganization.getAD_Org_ID());
					newOrganizationAccess.setIsActive(currentOrganizationAccess.isActive());
					newOrganizationAccess.setIsReadOnly(currentOrganizationAccess.isReadOnly());
					newOrganizationAccess.saveEx(get_TrxName());
					//	Add
					addLog("@AD_Role_ID@ @Added@: " + newOrganizationAccess.getAD_Role().getName());
		});
		//	For User
		new Query(getCtx(), I_AD_User_OrgAccess.Table_Name, I_AD_User_OrgAccess.COLUMNNAME_AD_Org_ID + " = ? "
				+ "AND NOT EXISTS(SELECT 1 FROM AD_User_OrgAccess ra WHERE ra.AD_User_ID = AD_User_OrgAccess.AD_User_ID AND ra.AD_Org_ID = ?)", get_TrxName())
				.setParameters(getOrgId(), newOrganization.getAD_Org_ID())
				.<MUserOrgAccess>list().stream()
				.forEach(currentOrganizationAccess -> {
					MUserOrgAccess newOrganizationAccess = new MUserOrgAccess(getCtx(), 0, get_TrxName());
					newOrganizationAccess.setAD_Org_ID(newOrganization.getAD_Org_ID());
					newOrganizationAccess.setAD_User_ID(currentOrganizationAccess.getAD_User_ID());
					newOrganizationAccess.setIsActive(currentOrganizationAccess.isActive());
					newOrganizationAccess.setIsReadOnly(currentOrganizationAccess.isReadOnly());
					newOrganizationAccess.saveEx(get_TrxName());
					//	Add
					addLog("@AD_User_ID@ @Added@: " + newOrganizationAccess.getAD_User().getName());
		});
	}
	
	/**
	 * Create and get new document type
	 * @param currentDocumentTypeId
	 * @return
	 */
	private int createAndGetDocumentTypeId(int currentDocumentTypeId) {
		if(currentDocumentTypeId <= 0) {
			return -1;
		}
		MDocType currentDocumentType = new MDocType(getCtx(), currentDocumentTypeId, get_TrxName());
		int newDocumentTypeId = copy.getReferenceId(I_C_DocType.Table_Name, currentDocumentTypeId);
		if(newDocumentTypeId > 0) {
			return newDocumentTypeId;
		}
		MDocType newDocumentType = new MDocType(getCtx(), 0, get_TrxName());
		PO.copyValues(currentDocumentType, newDocumentType);
		//	Set Sequences
		newDocumentType.setDocNoSequence_ID(createAndGetSequenceId(currentDocumentType.getDocNoSequence_ID()));
		newDocumentType.setDefiniteSequence_ID(createAndGetSequenceId(currentDocumentType.getDefiniteSequence_ID()));
		//int sequenceId = createAndGetSequenceId(currentDocumentType.get_ValueAsInt(LVEUtil.COLUMNNAME_ControlNoSequence_ID));
		////if(sequenceId > 0) {
			//newDocumentType.set_ValueOfColumn(LVEUtil.COLUMNNAME_ControlNoSequence_ID, sequenceId);
		//}
		//	referenced document Types
		newDocumentType.setC_DocTypeShipment_ID(createAndGetDocumentTypeId(currentDocumentType.getC_DocTypeShipment_ID()));
		newDocumentType.setC_DocTypeProforma_ID(createAndGetDocumentTypeId(currentDocumentType.getC_DocTypeProforma_ID()));
		newDocumentType.setC_DocTypePayment_ID(createAndGetDocumentTypeId(currentDocumentType.getC_DocTypePayment_ID()));
		newDocumentType.setC_DocTypeInvoice_ID(createAndGetDocumentTypeId(currentDocumentType.getC_DocTypeInvoice_ID()));
		newDocumentType.setC_DocTypeDifference_ID(createAndGetDocumentTypeId(currentDocumentType.getC_DocTypeDifference_ID()));
		newDocumentType.setIsActive(currentDocumentType.isActive());
		//	Set new values
		newDocumentType.setAD_Org_ID(newOrganization.getAD_Org_ID());
		newDocumentType.setName(getName(currentDocumentType.getName()));
		//	Save
		newDocumentType.saveEx(get_TrxName());
		//	Copy Access
		copyDocumentTypeAccess(currentDocumentType.getC_DocType_ID(), newDocumentType.getC_DocType_ID());
		//	Copy Document Action Access
		copyDocumentTypeWithholding(currentDocumentType.getC_DocType_ID(), newDocumentType.getC_DocType_ID());
		//	Record Access
		copyDocumentTypeRecordAccess(currentDocumentType.getC_DocType_ID(), newDocumentType.getC_DocType_ID());
		//	Add
		addLog("@C_DocType_ID@: " + newDocumentType.getName());
		//	Add to map
		newDocumentTypeId = newDocumentType.getC_DocType_ID();
		copy.putReferenceId(I_C_DocType.Table_Name, currentDocumentTypeId, newDocumentTypeId);
		return newDocumentTypeId;
	}
	
	/**
	 * Copy document Type Access
	 * @param currentDocumentTypeId
	 * @param newDocumentTypeId
	 */
	private void copyDocumentTypeAccess(int currentDocumentTypeId, int newDocumentTypeId) {
		//	For Role
		new Query(getCtx(), I_AD_Document_Action_Access.Table_Name, I_AD_Document_Action_Access.COLUMNNAME_C_DocType_ID + " = ? "
				+ "AND NOT EXISTS(SELECT 1 FROM AD_Document_Action_Access ra WHERE ra.C_DocType_ID = ? "
				+ "AND ra.AD_Role_ID = AD_Document_Action_Access.AD_Role_ID "
				+ "AND ra.AD_Ref_List_ID = AD_Document_Action_Access.AD_Ref_List_ID)", get_TrxName())
				.setParameters(currentDocumentTypeId, newDocumentTypeId)
				.<X_AD_Document_Action_Access>list().stream()
				.forEach(currentDocumentActionAccess -> {
					X_AD_Document_Action_Access documentAccionAccess = new X_AD_Document_Action_Access(getCtx(), 0, get_TrxName());
			    	documentAccionAccess.setAD_Role_ID(currentDocumentActionAccess.getAD_Role_ID());
			    	documentAccionAccess.setC_DocType_ID(newDocumentTypeId);
			    	documentAccionAccess.setAD_Ref_List_ID(currentDocumentActionAccess.getAD_Ref_List_ID());
			    	documentAccionAccess.saveEx();
					//	Add
					addLog("@C_DocType_ID@ @Added@: " + documentAccionAccess.getC_DocType().getName());
		});
	}
	
	/**
	 * Copy record access from document
	 * @param currentDocumentTypeId
	 * @param newDocumentTypeId
	 */
	private void copyDocumentTypeRecordAccess(int currentDocumentTypeId, int newDocumentTypeId) {
		//	For Role
		new Query(getCtx(), I_AD_Record_Access.Table_Name, I_AD_Record_Access.COLUMNNAME_Record_ID + " = ?", get_TrxName())
				.setParameters(currentDocumentTypeId)
				.<MRecordAccess>list().stream()
				.forEach(currentDocumentActionAccess -> {
					MRecordAccess documentAccionAccess = new Query(getCtx(), I_AD_Record_Access.Table_Name, 
							"AD_Record_Access.AD_Table_ID = ? AND AD_Record_Access.Record_ID = ? ", get_TrxName())
							.setParameters(MDocType.Table_ID, newDocumentTypeId)
							.first();
					if(documentAccionAccess == null
							|| documentAccionAccess.getAD_Table_ID() <= 0) {
						documentAccionAccess = new MRecordAccess(getCtx(), currentDocumentActionAccess.getAD_Role_ID(), MDocType.Table_ID, newDocumentTypeId, get_TrxName());
						documentAccionAccess.setIsActive(currentDocumentActionAccess.isActive());
						documentAccionAccess.setIsDependentEntities(currentDocumentActionAccess.isDependentEntities());
						documentAccionAccess.setIsReadOnly(currentDocumentActionAccess.isReadOnly());
						documentAccionAccess.setIsExclude(currentDocumentActionAccess.isExclude());
				    	documentAccionAccess.saveEx();
				    	MDocType documentType = new MDocType(getCtx(), newDocumentTypeId, get_TrxName());
						//	Add
						addLog("@Access@ @Added@: " + documentType.getName());
					}
		});
	}
	
	/**
	 * Copy Document Type for Withholding
	 * @param currentDocumentTypeId
	 * @param newDocumentTypeId
	 */
	private void copyDocumentTypeWithholding(int currentDocumentTypeId,
			int newDocumentTypeId) {/*
									 * // For Role new Query(getCtx(), I_WH_DefinitionLine.Table_Name,
									 * I_WH_DefinitionLine.COLUMNNAME_C_DocType_ID + " = ?", get_TrxName())
									 * .setParameters(currentDocumentTypeId) .<MWHDefinitionLine>list().stream()
									 * .forEach(currentWithholdingDefinitionLine -> { MWHDefinitionLine
									 * newWithholdingDefinitionLine = new MWHDefinitionLine(getCtx(), 0,
									 * get_TrxName());
									 * newWithholdingDefinitionLine.setC_DocType_ID(newDocumentTypeId);
									 * newWithholdingDefinitionLine.setWH_Definition_ID(
									 * currentWithholdingDefinitionLine.getWH_Definition_ID());
									 * newWithholdingDefinitionLine.setIsActive(currentWithholdingDefinitionLine.
									 * isActive()); newWithholdingDefinitionLine.saveEx(); // Add
									 * addLog("@WH_DefinitionLine_ID@ @Added@: " +
									 * newWithholdingDefinitionLine.getC_DocType().getName()); });
									 */
	}
	
	/**
	 * Create a sequence and get it
	 * @param currentSequenceId
	 * @return
	 */
	private int createAndGetSequenceId(int currentSequenceId) {
		if(currentSequenceId <= 0) {
			return -1;
		}
		MSequence currentSequence = new MSequence(getCtx(), currentSequenceId, get_TrxName());
		String newName = getName(currentSequence.getName());
		int newSequenceId = copy.getReferenceId(I_AD_Sequence.Table_Name, currentSequenceId);
		if(newSequenceId > 0) {
			return newSequenceId;
		}
		//	Create
		MSequence newSequence = new MSequence(getCtx(), 0, get_TrxName());
		PO.copyValues(currentSequence, newSequence);
		newSequence.setAD_Org_ID(newOrganization.getAD_Org_ID());
		newSequence.setName(newName);
		newSequence.setCurrentNext(1);
		newSequence.setCurrentNextSys(1);
		newSequence.setIncrementNo(currentSequence.getIncrementNo());
		newSequence.setStartNo(currentSequence.getStartNo());
		newSequence.setPrefix(getValue() + "-" + currentSequence.getPrefix());
		newSequence.setIsActive(currentSequence.isActive());
		newSequence.saveEx(get_TrxName());
		//	Add
		addLog("@AD_Sequence_ID@: " + newSequence.getName());
		//	Add to map
		newSequenceId = newSequence.getAD_Sequence_ID();
		copy.putReferenceId(I_AD_Sequence.Table_Name, currentSequenceId, newSequenceId);
		return newSequenceId;
	}
	
	/**
	 * Create and get new Point of Sales
	 * @param currentPointOfSalesId
	 * @return
	 */
	private int createAndGetPointOfSales(int currentPointOfSalesId) {
		if(currentPointOfSalesId <= 0) {
			return -1;
		}
		MPOS currentPointOfSales = new MPOS(getCtx(), currentPointOfSalesId, get_TrxName());
		String newName = getName(currentPointOfSales.getName());
		//	Create
		MPOS newPointOfSales = new MPOS(getCtx(), 0, get_TrxName());
		PO.copyValues(currentPointOfSales, newPointOfSales);
		newPointOfSales.setAD_Org_ID(newOrganization.getAD_Org_ID());
		newPointOfSales.setName(newName);
		newPointOfSales.setM_Warehouse_ID(createAndGetWarehouse(currentPointOfSales.getM_Warehouse_ID()));
		newPointOfSales.setC_CashBook_ID(createAndGetCashBook(currentPointOfSales.getC_CashBook_ID()));
		newPointOfSales.setCashTransferBankAccount_ID(createAndCashAccount(currentPointOfSales.getCashTransferBankAccount_ID()));
		newPointOfSales.setC_BankAccount_ID(createAndCashAccount(currentPointOfSales.getC_BankAccount_ID()));
		newPointOfSales.setIsActive(currentPointOfSales.isActive());
		//	Document Types
		newPointOfSales.setC_DocType_ID(createAndGetDocumentTypeId(currentPointOfSales.getC_DocType_ID()));
		int documentTypeForRMAId = createAndGetDocumentTypeId(currentPointOfSales.get_ValueAsInt("C_DocTypeRMA_ID"));
		if(documentTypeForRMAId > 0) {
			newPointOfSales.set_ValueOfColumn("C_DocTypeRMA_ID", documentTypeForRMAId);
		}
		//	Save
		newPointOfSales.saveEx(get_TrxName());
		//	Add
		addLog("@C_POS_ID@: " + newPointOfSales.getName());
		//	Default
		return newPointOfSales.getC_POS_ID();
	}
	
	/**
	 * Create and get Cash account
	 * @param currentCashAccountId
	 * @return
	 */
	private int createAndCashAccount(int currentCashAccountId) {
		if(currentCashAccountId <= 0) {
			return -1;
		}
		MBankAccount currentCashAccount = new MBankAccount(getCtx(), currentCashAccountId, get_TrxName());
		String newName = getName(currentCashAccount.getAccountNo());
		int newCashAccountId = copy.getReferenceId(I_C_BankAccount.Table_Name, currentCashAccountId);
		if(newCashAccountId > 0) {
			return newCashAccountId;
		}
		//	Create
		MBankAccount newCashAccount = new MBankAccount(getCtx(), 0, get_TrxName());
		PO.copyValues(currentCashAccount, newCashAccount);
		newCashAccount.setAD_Org_ID(newOrganization.getAD_Org_ID());
		newCashAccount.setIsActive(currentCashAccount.isActive());
		newCashAccount.setAccountNo(newName);
		newCashAccount.setDescription(newName);
		newCashAccount.setCurrentBalance(Env.ZERO);
		//	Set bank reference
		newCashAccount.setC_Bank_ID(copy.getReferenceId(I_C_Bank.Table_Name, currentCashAccount.getC_Bank_ID()));
		//	Save
		newCashAccount.saveEx(get_TrxName());
		//	Copy Document by bank account
		copyCashAccountDocument(currentCashAccount.getC_BankAccount_ID(), newCashAccount.getC_BankAccount_ID());
		//	Add
		addLog("@C_BankAccount_ID@: " + newCashAccount.getAccountNo());
		//	Default
		newCashAccountId = newCashAccount.getC_BankAccount_ID();
		copy.putReferenceId(I_C_BankAccount.Table_Name, currentCashAccountId, newCashAccountId);
		return newCashAccountId;
	}
	
	/**
	 * Copy document by bank account
	 * @param currentCashAccountId
	 * @param newCashAccountId
	 */
	private void copyCashAccountDocument(int currentCashAccountId, int newCashAccountId) {
		//	For Role
		new Query(getCtx(), I_C_BankAccountDoc.Table_Name, I_C_BankAccountDoc.COLUMNNAME_C_BankAccount_ID + " = ?", get_TrxName())
				.setParameters(currentCashAccountId)
				.<X_C_BankAccountDoc>list().stream()
				.forEach(currentBankAccountDocument -> {
					X_C_BankAccountDoc bankAccountDocument = new X_C_BankAccountDoc(getCtx(), 0, get_TrxName());
					PO.copyValues(currentBankAccountDocument, bankAccountDocument, true);
					bankAccountDocument.setC_BankAccount_ID(newCashAccountId);
					bankAccountDocument.setIsActive(currentBankAccountDocument.isActive());
					bankAccountDocument.setCurrentNext(0);
					bankAccountDocument.saveEx();
					//	Add
					addLog("@C_BankAccountDoc_ID@ @Added@: " + bankAccountDocument.getName());
		});
	}
	
	/**
	 * Create and get Cash account
	 * @param currentCashBookId
	 * @return
	 */
	private int createAndGetCashBook(int currentCashBookId) {
		if(currentCashBookId <= 0) {
			return -1;
		}
		MCashBook currentCashBook = new MCashBook(getCtx(), currentCashBookId, get_TrxName());
		String newName = getName(currentCashBook.getName());
		int newCashBookId = copy.getReferenceId(I_C_CashBook.Table_Name, currentCashBookId);
		if(newCashBookId > 0) {
			return newCashBookId;
		}
		//	Create
		MCashBook newCashBook = new MCashBook(getCtx(), 0, get_TrxName());
		PO.copyValues(currentCashBook, newCashBook);
		newCashBook.setAD_Org_ID(newOrganization.getAD_Org_ID());
		newCashBook.setName(newName);
		//	Save
		newCashBook.saveEx(get_TrxName());
		//	Add
		addLog("@C_CashBook_ID@: " + newCashBook.getName());
		//	Default
		newCashBookId = newCashBook.getC_CashBook_ID();
		copy.putReferenceId(I_C_CashBook.Table_Name, currentCashBookId, newCashBookId);
		return newCashBookId;
	}
	
	/**
	 * Create and get new warehouse
	 * @param currentWarehouseId
	 * @return
	 */
	private int createAndGetWarehouse(int currentWarehouseId) {
		if(currentWarehouseId <= 0) {
			return -1;
		}
		MWarehouse currentWarehouse = new MWarehouse(getCtx(), currentWarehouseId, get_TrxName());
		String newName = getName(currentWarehouse.getName());
		int newWarehousetId = copy.getReferenceId(I_M_Warehouse.Table_Name, currentWarehouseId);
		if(newWarehousetId > 0) {
			return newWarehousetId;
		}
		//	Create
		MWarehouse newWarehouse = new MWarehouse(getCtx(), 0, get_TrxName());
		PO.copyValues(currentWarehouse, newWarehouse);
		newWarehouse.setAD_Org_ID(newOrganization.getAD_Org_ID());
		newWarehouse.setIsActive(currentWarehouse.isActive());
		newWarehouse.setValue(getName(currentWarehouse.getValue()));
		newWarehouse.setName(newName);
		newWarehouse.setC_Location_ID(newOrganizationInfo.getC_Location_ID());
		//	Save
		newWarehouse.saveEx(get_TrxName());
		copyLocators(currentWarehouse , newWarehouse);
		//	Add
		addLog("@M_Warehouse_ID@: " + newWarehouse.getName());
		//	Default
		newWarehousetId = newWarehouse.getM_Warehouse_ID();
		copy.putReferenceId(I_M_Warehouse.Table_Name, currentWarehouseId, newWarehousetId);
		return newWarehousetId;
	}
	
	/**
	 * Copy all locators from warehouse
	 * @param warehouse
	 */
	private void copyLocators(MWarehouse currentWarehouse, MWarehouse newWarehouse) {
		Arrays.asList(currentWarehouse.getLocators(true)).forEach(locator -> {
			MLocator newLocator = new MLocator(getCtx(), 0, get_TrxName());
			PO.copyValues(locator, newLocator);
			newLocator.setM_Warehouse_ID(newWarehouse.getM_Warehouse_ID());
			newLocator.setIsActive(locator.isActive());
			newLocator.setAD_Org_ID(newOrganization.getAD_Org_ID());
			newLocator.saveEx(get_TrxName());
		});
	}
	
	/**
	 * Get Document Type name
	 * @param name
	 * @return
	 */
	private String getName(String name) {
		return name + " (" + getValue() + ")";
	}
}
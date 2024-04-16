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

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.core.domains.models.I_AD_ModelValidator;
import org.adempiere.core.domains.models.I_AD_Org;
import org.adempiere.core.domains.models.I_AD_Tree;
import org.compiere.model.MCalendar;
import org.compiere.model.MCity;
import org.compiere.model.MClient;
import org.compiere.model.MClientInfo;
import org.compiere.model.MLocation;
import org.compiere.model.MOrg;
import org.compiere.model.MRegion;
import org.compiere.model.MSequence;
import org.compiere.model.MTree;
import org.compiere.model.MYear;
import org.adempiere.core.domains.models.X_AD_ModelValidator;
import org.adempiere.core.domains.models.X_AD_Tree;
import org.eevolution.services.dsl.ProcessBuilder;
import org.spin.tools.util.CopyContextUtil;
import org.spin.tools.util.CopyUtil;

/** 
 * 	Generated Process for (Copy Client Setup Process)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.3
 */
public class CopyClientSetup extends CopyClientSetupAbstract {
	/**	Current Client	*/
	private MClient currentClient;
	/**	New Client	*/
	private MClient newClient;
	/**	New Client Info	*/
	private MClientInfo newClientInfo;
	/**	New Location	*/
	private MLocation newLocation;
	/**	Copy Util	*/
	private CopyUtil copy;
	
	@Override
	protected void prepare() {
		super.prepare();
		if(getTemplateClientId() <= 0) {
			throw new AdempiereException("@ECA25_TemplateClient_ID@ @IsMandatory@");
		}
		currentClient = new MClient(getCtx(), getTemplateClientId(), get_TrxName());
		copy = CopyUtil.newInstance().withTeplateClientId(getTemplateClientId()).withContext(getCtx()).withTransactionName(get_TrxName());
	}

	@Override
	protected String doIt() throws Exception {
		try {
			copyClient();
			copyClientInfo();
			createCalendar();
			copyOrganizations();
			copy.runCopyProcess(CopyGLDefinitions.class);
			copy.runCopyProcess(CopyFIDefinition.class);
			copy.runCopyProcess(CopyRoleDefinition.class);
			copyModelValidators();
			copy.runCopyProcess(CopyDocumentTypes.class);
			copy.runCopyProcess(CopySODefinitions.class);
			copy.runCopyProcess(CopyPODefinitions.class);
			//copy.runCopyProcess(CopyFiscalDefinitions.class);
			copy.runCopyProcess(CopyASP.class);
		} catch (Exception e) {
			throw new AdempiereException(e);
		} finally {
			CopyContextUtil.newInstance().revertContextToCurrentClient(getCtx(), getProcessInfo().isBatch());
		}
		return newClient.getValue() + " - " + newClient.getName();
	}
	
	/**
	 * Copy Model Validators
	 */
	private void copyModelValidators() {
		copy.getTemplateReferencesOnlyNewIds(I_AD_ModelValidator.Table_Name).forEach(currentModelValidatorId -> {
			X_AD_ModelValidator currentModelValidator = new X_AD_ModelValidator(getCtx(), currentModelValidatorId, get_TrxName()); 
			X_AD_ModelValidator newModelValidator = new X_AD_ModelValidator(getCtx(), 0, get_TrxName());
			copy.copyCurrentValues(currentModelValidator, newModelValidator);
			newModelValidator.setAD_Org_ID(0);
			newModelValidator.saveEx();
			copy.saveReference(currentModelValidator, newModelValidator);
		});
	}

	/**
	 * Copy only client
	 */
	private void copyClient() {
		newClient = new MClient(getCtx(), 0, true, get_TrxName());
		copy.copyCurrentValues(currentClient, newClient);
		newClient.setValue(getValue());
		newClient.setName(getName());
		newClient.setIsUseBetaFunctions(currentClient.isUseBetaFunctions());
		newClient.setIsCostImmediate(currentClient.isCostImmediate());
		newClient.setAutoArchive(currentClient.getAutoArchive());
		newClient.saveEx(get_TrxName());
		copy.withTargetClientId(newClient.getAD_Client_ID());
		copy.saveReference(currentClient, newClient);
		CopyContextUtil.newInstance().updateContextToNewClient(getCtx(), newClient.getAD_Client_ID(), getProcessInfo().isBatch());
		//	Sequences
		if(!MSequence.checkClientSequences (getCtx(), newClient.getAD_Client_ID(), get_TrxName())) {
			throw new AdempiereException("@CreateSequenceError@");
		}
		//	Info
		
		MCity city = MCity.get(getCtx(), getCityId());
		int currentCountryId = city.getC_Country_ID();
		newLocation = new MLocation(getCtx(), currentCountryId, getRegionId(), city.getName(), get_TrxName());
		newLocation.setC_City_ID(getCityId());
		//	For address
		Optional.ofNullable(getAddress1()).ifPresent(address -> newLocation.setAddress1(address));
		Optional.ofNullable(getAddress2()).ifPresent(address -> newLocation.setAddress2(address));
		Optional.ofNullable(getAddress3()).ifPresent(address -> newLocation.setAddress3(address));
		Optional.ofNullable(getAddress4()).ifPresent(address -> newLocation.setAddress4(address));
		//	Save
		newLocation.saveEx(get_TrxName());
		//	Add
		addLog("@AD_Client_ID@: " + newClient.getValue() + " - " + newClient.getName());
	}
	
	/**
	 * Copy All organizations
	 */
	private void copyOrganizations() {
		AtomicInteger counter = new AtomicInteger(1);
		copy.getTemplateReferencesOnlyNewIds(I_AD_Org.Table_Name).forEach(currentRoleId -> {
			MOrg sourceOrganization = new MOrg(getCtx(), currentRoleId, get_TrxName());
			ProcessBuilder.create(getCtx())
			.process(CopyOrganizationDefinition.class)
			.withParameter(CopyOrganizationDefinition.ECA25_TEMPLATECLIENT_ID, getTemplateClientId())
			.withParameter(CopyOrganizationDefinition.ECA25_TARGETCLIENT_ID, newClient.getAD_Client_ID())
			.withParameter(CopyOrganizationDefinition.AD_ORG_ID, sourceOrganization.getAD_Org_ID())
			.withParameter(CopyOrganizationDefinition.VALUE, getValue() + "-" + counter.get())
			.withParameter(CopyOrganizationDefinition.NAME, getName() + "-" + counter.get())
			.withParameter(CopyOrganizationDefinition.TAXID, getTaxID())
			.withParameter(CopyOrganizationDefinition.C_REGION_ID, getRegionId())
			.withParameter(CopyOrganizationDefinition.C_CITY_ID, getCityId())
			.withParameter(CopyOrganizationDefinition.ADDRESS1, getAddress1())
			.withParameter(CopyOrganizationDefinition.ADDRESS2, getAddress2())
			.withParameter(CopyOrganizationDefinition.ADDRESS3, getAddress3())
			.withParameter(CopyOrganizationDefinition.ADDRESS4, getAddress4())
			.withBatchMode()
			.withoutTransactionClose()
			.execute(get_TrxName());
			counter.incrementAndGet();
		});
	}
	
	/**************************************************************************
	 * 	Create Trees and Setup Client Info
	 * 	@return true if created
	 */
	public void copyClientInfo() {
		//  Tree IDs
		AtomicInteger treeOrgId = new AtomicInteger();
		AtomicInteger treeBPartnerId = new AtomicInteger();
		AtomicInteger treeProjectId = new AtomicInteger();
		AtomicInteger treeSalesRegionId = new AtomicInteger();
		AtomicInteger treeProductId = new AtomicInteger();
		AtomicInteger treeCampaignId = new AtomicInteger();
		AtomicInteger treeActivityId = new AtomicInteger();
		AtomicInteger treeAccountId = new AtomicInteger();
		
		copy.getTemplateReferencesOnlyNewIds(I_AD_Tree.Table_Name).forEach(currentTreeId -> {
			MTree currentTree = MTree.get(getCtx(), currentTreeId, get_TrxName());
			MTree newTree = new MTree(newClient, currentTree.getName(), currentTree.getTreeType());
			newTree.setParent_Column_ID(currentTree.getParent_Column_ID());
			newTree.setAD_ColumnSortOrder_ID(newTree.getAD_ColumnSortOrder_ID());
			newTree.saveEx();
			copy.saveReference(currentTree, newTree);
			if (currentTree.getTreeType().equals(X_AD_Tree.TREETYPE_Organization)) {
				treeOrgId.set(newTree.getAD_Tree_ID());
			} else if (currentTree.getTreeType().equals(X_AD_Tree.TREETYPE_BPartner)) {
				treeBPartnerId.set(newTree.getAD_Tree_ID());
			} else if (currentTree.getTreeType().equals(X_AD_Tree.TREETYPE_Project)) {
				treeProjectId.set(newTree.getAD_Tree_ID());
			} else if (currentTree.getTreeType().equals(X_AD_Tree.TREETYPE_SalesRegion)) {
				treeSalesRegionId.set(newTree.getAD_Tree_ID());
			} else if (currentTree.getTreeType().equals(X_AD_Tree.TREETYPE_Product)) {
				treeProductId.set(newTree.getAD_Tree_ID());
			} else if (currentTree.getTreeType().equals(X_AD_Tree.TREETYPE_ElementValue)) {
				treeAccountId.set(newTree.getAD_Tree_ID());
			} else if (currentTree.getTreeType().equals(X_AD_Tree.TREETYPE_Campaign)) {
				treeCampaignId.set(newTree.getAD_Tree_ID());
			} else if (currentTree.getTreeType().equals(X_AD_Tree.TREETYPE_Activity)) {
				treeActivityId.set(newTree.getAD_Tree_ID());
			}
			copy.putReferenceId(I_AD_Tree.Table_Name, currentTreeId, newTree.getAD_Tree_ID());
		});
		//	Create ClientInfo
		newClientInfo = new MClientInfo (newClient,
			treeOrgId.get(), treeBPartnerId.get(), treeProjectId.get(), 
			treeSalesRegionId.get(), treeProductId.get(),
			treeCampaignId.get(), treeActivityId.get(), get_TrxName());
		newClientInfo.saveEx();
	}	//	createTrees
	
	/**
	 * Create Calendar for New Client
	 */
	private void createCalendar() {
		MCalendar newCalendar = new MCalendar(newClient);
		newCalendar.saveEx();
		newClientInfo.setC_Calendar_ID(newCalendar.getC_Calendar_ID());
		newClientInfo.saveEx();
		Calendar cal = Calendar.getInstance(newClient.getLocale());
		cal.set(Calendar.DATE,1);
		cal.set(Calendar.MONTH, 0);
		// get last day of financial year
		Calendar lastday = Calendar.getInstance();
		lastday.setTime(cal.getTime());
		lastday.add(Calendar.YEAR, 1);
		lastday.add(Calendar.DATE, -1);
		IntStream.range(0, 5).forEach(page -> {
			MYear year = new MYear (newCalendar);
			String Year = String.valueOf(lastday.get(Calendar.YEAR));
			year.setFiscalYear(Year);
			year.saveEx();
			year.createStdPeriods(newClient.getLocale(), new Timestamp(cal.getTimeInMillis()), "yyyy-MM");
			cal.add(Calendar.YEAR, 1);
			lastday.add(Calendar.YEAR, 1);
		});
	}
}
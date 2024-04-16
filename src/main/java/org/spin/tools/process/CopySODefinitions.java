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
import org.adempiere.core.domains.models.I_C_BP_Group;
import org.adempiere.core.domains.models.I_C_Charge;
import org.adempiere.core.domains.models.I_C_ChargeType;
import org.adempiere.core.domains.models.I_C_ChargeType_DocType;
import org.adempiere.core.domains.models.I_C_Commission;
import org.adempiere.core.domains.models.I_C_CommissionGroup;
import org.adempiere.core.domains.models.I_C_CommissionLine;
import org.adempiere.core.domains.models.I_C_CommissionType;
import org.adempiere.core.domains.models.I_C_DocType;
import org.adempiere.core.domains.models.I_C_Dunning;
import org.adempiere.core.domains.models.I_C_DunningLevel;
import org.adempiere.core.domains.models.I_C_Greeting;
import org.adempiere.core.domains.models.I_C_InvoiceSchedule;
import org.adempiere.core.domains.models.I_C_PaySchedule;
import org.adempiere.core.domains.models.I_C_PaymentTerm;
import org.adempiere.core.domains.models.I_C_Tax;
import org.adempiere.core.domains.models.I_C_TaxCategory;
import org.adempiere.core.domains.models.I_C_TaxDefinition;
import org.adempiere.core.domains.models.I_C_TaxGroup;
import org.adempiere.core.domains.models.I_C_TaxType;
import org.adempiere.core.domains.models.I_M_RMAType;
import org.compiere.model.MBPGroup;
import org.compiere.model.MCharge;
import org.compiere.model.MCommission;
import org.compiere.model.MCommissionGroup;
import org.compiere.model.MCommissionLine;
import org.compiere.model.MCommissionType;
import org.compiere.model.MDunning;
import org.compiere.model.MDunningLevel;
import org.compiere.model.MInvoiceSchedule;
import org.compiere.model.MPaySchedule;
import org.compiere.model.MPaymentTerm;
import org.compiere.model.MTable;
import org.compiere.model.MTax;
import org.compiere.model.MTaxCategory;
import org.compiere.model.PO;
import org.compiere.model.Query;
//import org.shw.model.I_LCO_TaxPayerType;
//import org.shw.model.I_LCO_WithholdingCalc;
//import org.shw.model.I_LCO_WithholdingRule;
//import org.shw.model.I_LCO_WithholdingRuleConf;
//import org.shw.model.I_LCO_WithholdingType;
//import org.shw.model.X_LCO_TaxPayerType;
//import org.shw.model.X_LCO_WithholdingCalc;
//import org.shw.model.X_LCO_WithholdingRule;
//import org.shw.model.X_LCO_WithholdingRuleConf;
//import org.shw.model.X_LCO_WithholdingType;
import org.adempiere.core.domains.models.X_C_ChargeType;
import org.adempiere.core.domains.models.X_C_Greeting;
import org.adempiere.core.domains.models.X_C_Tax;
import org.adempiere.core.domains.models.X_C_TaxDefinition;
import org.adempiere.core.domains.models.X_C_TaxGroup;
import org.adempiere.core.domains.models.X_C_TaxPostal;
import org.adempiere.core.domains.models.X_C_TaxType;
import org.adempiere.core.domains.models.X_M_RMAType;
import org.spin.tools.util.CopyContextUtil;
import org.spin.tools.util.CopyUtil;

/** Generated Process for (Copy SO Definitions)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.3
 */
public class CopySODefinitions extends CopySODefinitionsAbstract {
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
			//	BP Group
			copy.getTemplateReferencesOnlyNewIds(I_C_BP_Group.Table_Name).forEach(currentBusinessPartnerGroupId -> {
				MBPGroup currentBusinessPartnerGroup = new MBPGroup(getCtx(), currentBusinessPartnerGroupId, get_TrxName()); 
				MBPGroup newBusinessPartnerGroup = new MBPGroup(getCtx(), 0, get_TrxName());
				copy.copyCurrentValues(currentBusinessPartnerGroup, newBusinessPartnerGroup);
				int newOrganizationId = copy.getReferenceId(I_AD_Org.Table_Name, currentBusinessPartnerGroup.getAD_Org_ID());
				if(newOrganizationId < 0) {
					newOrganizationId = 0;
				}
				newBusinessPartnerGroup.setAD_Org_ID(newOrganizationId);
				newBusinessPartnerGroup.saveEx();
				copy.saveReference(currentBusinessPartnerGroup, newBusinessPartnerGroup);
			});
			//	Dunning
			copy.getTemplateReferencesOnlyNewIds(I_C_Dunning.Table_Name).forEach(currentDunningId -> {
				MDunning currentDunning = new MDunning(getCtx(), currentDunningId, get_TrxName()); 
				MDunning newDunning = new MDunning(getCtx(), 0, get_TrxName());
				copy.copyCurrentValues(currentDunning, newDunning);
				newDunning.setAD_Org_ID(0);
				newDunning.saveEx();
				copy.saveReference(currentDunning, newDunning);
				new Query(getCtx(), I_C_DunningLevel.Table_Name, "C_Dunning_ID = ?", get_TrxName())
				.setParameters(currentDunning.getC_Dunning_ID())
				.getIDsAsList().forEach(currentDunningLevelId -> {
					MDunningLevel currentDunningLevel = new MDunningLevel(getCtx(), currentDunningLevelId, get_TrxName()); 
					MDunningLevel newDunningLevel = new MDunningLevel(getCtx(), 0, get_TrxName());
					copy.copyCurrentValues(currentDunningLevel, newDunningLevel);
					newDunningLevel.setAD_Org_ID(0);
					newDunningLevel.setC_Dunning_ID(newDunning.getC_Dunning_ID());
					newDunningLevel.saveEx();
					copy.saveReference(currentDunningLevel, newDunningLevel);
					copy.copyTranslation(currentDunningLevel, newDunningLevel);
				});
			});
			//	Invoice Schedule
			copy.getTemplateReferencesOnlyNewIds(I_C_InvoiceSchedule.Table_Name).forEach(currentInvoiceScheduleId -> {
				MInvoiceSchedule currentInvoiceSchedule = new MInvoiceSchedule(getCtx(), currentInvoiceScheduleId, get_TrxName()); 
				MInvoiceSchedule newInvoiceSchedule = new MInvoiceSchedule(getCtx(), 0, get_TrxName());
				copy.copyCurrentValues(currentInvoiceSchedule, newInvoiceSchedule);
				newInvoiceSchedule.setAD_Org_ID(0);
				newInvoiceSchedule.saveEx();
				copy.saveReference(currentInvoiceSchedule, newInvoiceSchedule);
			});
			//	Commission Type
			copy.getTemplateReferencesOnlyNewIds(I_C_CommissionType.Table_Name).forEach(currentCommissionTypeId -> {
				MCommissionType currentCommissionType = new MCommissionType(getCtx(), currentCommissionTypeId, get_TrxName()); 
				MCommissionType newCommissionType = new MCommissionType(getCtx(), 0, get_TrxName());
				copy.copyCurrentValues(currentCommissionType, newCommissionType);
				newCommissionType.setAD_Org_ID(0);
				newCommissionType.saveEx();
				copy.saveReference(currentCommissionType, newCommissionType);
			});
			//	Commission Group
			copy.getTemplateReferencesOnlyNewIds(I_C_CommissionGroup.Table_Name).forEach(currentCommissionGroupId -> {
				MCommissionGroup currentommissionGroup = new MCommissionGroup(getCtx(), currentCommissionGroupId, get_TrxName()); 
				MCommissionGroup newommissionGroup = new MCommissionGroup(getCtx(), 0, get_TrxName());
				copy.copyCurrentValues(currentommissionGroup, newommissionGroup);
				newommissionGroup.setAD_Org_ID(0);
				newommissionGroup.saveEx();
				copy.saveReference(currentommissionGroup, newommissionGroup);
			});
			//	Commission
			copy.getTemplateReferencesOnlyNewIds(I_C_Commission.Table_Name).forEach(currentCommissionId -> {
				MCommission currentCommission = new MCommission(getCtx(), currentCommissionId, get_TrxName()); 
				MCommission newCommission = new MCommission(getCtx(), 0, get_TrxName());
				copy.copyCurrentValues(currentCommission, newCommission);
				newCommission.setAD_Org_ID(0);
				newCommission.saveEx();
				copy.saveReference(currentCommission, newCommission);
				new Query(getCtx(), I_C_CommissionLine.Table_Name, "C_Commission_ID = ?", get_TrxName())
				.setParameters(currentCommission.getC_Commission_ID())
				.getIDsAsList().forEach(currentCommissionLineId -> {
					MCommissionLine currentCommissionLine = new MCommissionLine(getCtx(), currentCommissionLineId, get_TrxName()); 
					MCommissionLine newCommissionLine = new MCommissionLine(getCtx(), 0, get_TrxName());
					copy.copyCurrentValues(currentCommissionLine, newCommissionLine);
					newCommissionLine.setAD_Org_ID(0);
					newCommissionLine.setC_Commission_ID(newCommission.getC_Commission_ID());
					newCommissionLine.saveEx();
					copy.saveReference(currentCommissionLine, newCommissionLine);
				});
			});
			//	Payment Terms
			copy.getTemplateReferencesOnlyNewIds(I_C_PaymentTerm.Table_Name).forEach(currentPaymentTermId -> {
				MPaymentTerm currentPaymentTerm = new MPaymentTerm(getCtx(), currentPaymentTermId, get_TrxName()); 
				MPaymentTerm newPaymentTerm = new MPaymentTerm(getCtx(), 0, get_TrxName());
				copy.copyCurrentValues(currentPaymentTerm, newPaymentTerm);
				newPaymentTerm.setAD_Org_ID(0);
				newPaymentTerm.saveEx();
				copy.saveReference(currentPaymentTerm, newPaymentTerm);
				copy.copyTranslation(currentPaymentTerm, newPaymentTerm);
				new Query(getCtx(), I_C_PaySchedule.Table_Name, "C_PaymentTerm_ID = ?", get_TrxName())
				.setParameters(currentPaymentTerm.getC_PaymentTerm_ID())
				.getIDsAsList().forEach(currentPaymentScheduleId -> {
					MPaySchedule currentPaymentSchedule = new MPaySchedule(getCtx(), currentPaymentScheduleId, get_TrxName()); 
					MPaySchedule newPaymentSchedule = new MPaySchedule(getCtx(), 0, get_TrxName());
					copy.copyCurrentValues(currentPaymentSchedule, newPaymentSchedule);
					newPaymentSchedule.setAD_Org_ID(0);
					newPaymentSchedule.setC_PaymentTerm_ID(newPaymentTerm.getC_PaymentTerm_ID());
					newPaymentSchedule.saveEx();
					copy.saveReference(currentPaymentSchedule, newPaymentSchedule);
				});
			});
			//	Greeting
			copy.getTemplateReferencesOnlyNewIds(I_C_Greeting.Table_Name).forEach(currentGreetingId -> {
				X_C_Greeting currentGreeting = new X_C_Greeting(getCtx(), currentGreetingId, get_TrxName()); 
				X_C_Greeting newGreeting = new X_C_Greeting(getCtx(), 0, get_TrxName());
				copy.copyCurrentValues(currentGreeting, newGreeting);
				newGreeting.setAD_Org_ID(0);
				copy.saveReference(currentGreeting, newGreeting);
				copy.copyTranslation(currentGreeting, newGreeting);
			});
			//	RMA Type
			copy.getTemplateReferencesOnlyNewIds(I_M_RMAType.Table_Name).forEach(currentReturnMaterialTypeId -> {
				X_M_RMAType currentReturnMaterialType = new X_M_RMAType(getCtx(), currentReturnMaterialTypeId, get_TrxName()); 
				X_M_RMAType newReturnMaterialType = new X_M_RMAType(getCtx(), 0, get_TrxName());
				copy.copyCurrentValues(currentReturnMaterialType, newReturnMaterialType);
				newReturnMaterialType.setAD_Org_ID(0);
				newReturnMaterialType.saveEx();
				copy.saveReference(currentReturnMaterialType, newReturnMaterialType);
			});
			//	Tax Category
			copy.getTemplateReferencesOnlyNewIds(I_C_TaxCategory.Table_Name).forEach(currentTaxCategoryId -> {
				MTaxCategory currentTaxCategory = new MTaxCategory(getCtx(), currentTaxCategoryId, get_TrxName()); 
				MTaxCategory newTaxCategory = new MTaxCategory(getCtx(), 0, get_TrxName());
				copy.copyCurrentValues(currentTaxCategory, newTaxCategory);
				newTaxCategory.setAD_Org_ID(0);
				newTaxCategory.saveEx();
				copy.saveReference(currentTaxCategory, newTaxCategory);
				copy.copyTranslation(currentTaxCategory, newTaxCategory);
				new Query(getCtx(), I_C_Tax.Table_Name, "C_TaxCategory_ID = ?", get_TrxName())
				.setParameters(currentTaxCategory.getC_TaxCategory_ID())
				.getIDsAsList().forEach(currentTaxId -> {
					MTax currentTax = new MTax(getCtx(), currentTaxId, get_TrxName());
					MTax newTax = new MTax(getCtx(), 0, get_TrxName());
					int newOrganizationId = copy.getReferenceId(I_AD_Org.Table_Name, currentTax.getAD_Org_ID());
					if(newOrganizationId < 0) {
						newOrganizationId = 0;
					}
					copy.copyCurrentValues(currentTax, newTax);
					newTax.setC_TaxCategory_ID(newTaxCategory.getC_TaxCategory_ID());
					newTax.setAD_Org_ID(newOrganizationId);
					newTax.saveEx();
					copy.copyTranslation(currentTax, newTax);
					copy.saveReference(currentTax, newTax);
				});
			});
			//	Charge Type
			copy.getTemplateReferencesOnlyNewIds(I_C_ChargeType.Table_Name).forEach(currentChargeTypeId -> {
				X_C_ChargeType currentChargeType = new X_C_ChargeType(getCtx(), currentChargeTypeId, get_TrxName()); 
				X_C_ChargeType newChargeType = new X_C_ChargeType(getCtx(), 0, get_TrxName());
				copy.copyCurrentValues(currentChargeType, newChargeType);
				newChargeType.setAD_Org_ID(0);
				newChargeType.saveEx();
				copy.saveReference(currentChargeType, newChargeType);
				new Query(getCtx(), I_C_ChargeType_DocType.Table_Name, "C_ChargeType_ID = ?", get_TrxName())
				.setParameters(currentChargeType.getC_ChargeType_ID())
				.list().forEach(currentChargeTypeByDocument -> {
					PO newChargeTypeByDocument = MTable.get(getCtx(), I_C_ChargeType_DocType.Table_Name).getPO(0, get_TrxName());
					int newDocumentTypeId = copy.getReferenceId(I_C_DocType.Table_Name, currentChargeTypeByDocument.get_ValueAsInt("C_DocType_ID"));
					if(newDocumentTypeId > 0) {
						copy.copyCurrentValues(currentChargeTypeByDocument, newChargeTypeByDocument);
						newChargeTypeByDocument.setAD_Org_ID(0);
						newChargeTypeByDocument.set_ValueOfColumn("C_ChargeType_ID", newChargeType.getC_ChargeType_ID());
						newChargeTypeByDocument.set_ValueOfColumn("C_DocType_ID", newDocumentTypeId);
						newChargeTypeByDocument.saveEx();
						copy.saveReference(currentChargeTypeByDocument, newChargeTypeByDocument);
					}
				});
			});
			//	Charge
			copy.getTemplateReferencesOnlyNewIds(I_C_Charge.Table_Name).forEach(currentChargeId -> {
				MCharge currentCharge = new MCharge(getCtx(), currentChargeId, get_TrxName()); 
				MCharge newCharge = new MCharge(getCtx(), 0, get_TrxName());
				copy.copyCurrentValues(currentCharge, newCharge);
				newCharge.setAD_Org_ID(0);
				if(currentCharge.getC_ChargeType_ID() > 0) {
					int newcCargeTypeId = copy.getReferenceId(I_C_ChargeType.Table_Name, currentCharge.getC_ChargeType_ID());
					if(newcCargeTypeId > 0) {
						newCharge.setC_ChargeType_ID(newcCargeTypeId);
					}
				}
				newCharge.saveEx();
				copy.saveReference(currentCharge, newCharge);
				copy.copyTranslation(currentCharge, newCharge);
			});
			//			TaxGroup
			copy.getTemplateReferencesOnlyNewIds(I_C_TaxGroup.Table_Name).forEach(currentTaxGroupId -> {
				X_C_TaxGroup currenttaxGroup = new X_C_TaxGroup(getCtx(), currentTaxGroupId, get_TrxName()); 
				X_C_TaxGroup newTaxgGroup = new X_C_TaxGroup(getCtx(), 0, get_TrxName());
				copy.copyCurrentValues(currenttaxGroup, newTaxgGroup);
				newTaxgGroup.setAD_Org_ID(0);

				newTaxgGroup.saveEx();
				copy.saveReference(currenttaxGroup, newTaxgGroup);			});

			//			TaxType
			copy.getTemplateReferencesOnlyNewIds(I_C_TaxType.Table_Name).forEach(currentTaxTypeId -> {
				X_C_TaxType currenttaxType = new X_C_TaxType(getCtx(), currentTaxTypeId, get_TrxName()); 
				X_C_TaxType newTaxType = new X_C_TaxType(getCtx(), 0, get_TrxName());
				copy.copyCurrentValues(currenttaxType, newTaxType);
				newTaxType.setAD_Org_ID(0);
				newTaxType.saveEx();
				copy.saveReference(currenttaxType, newTaxType);
			});
			
//			TaxDefinition
			copy.getTemplateReferencesOnlyNewIds(I_C_TaxDefinition.Table_Name).forEach(currentTaxDefinitionId -> {
				
				  X_C_TaxDefinition currenttaxDefinition = new X_C_TaxDefinition(getCtx(),
				  currentTaxDefinitionId, get_TrxName()); 
				  X_C_TaxDefinition newTaxDefinition =	new X_C_TaxDefinition(getCtx(), 0, get_TrxName());
				  copy.copyCurrentValues(currenttaxDefinition, newTaxDefinition);
				  newTaxDefinition.setAD_Org_ID(0); 
				  int newTaxID =
				  copy.getReferenceId(X_C_Tax.Table_Name, currenttaxDefinition.getC_Tax_ID());
				  int newTaxGroupID = copy.getReferenceId(X_C_TaxGroup.Table_Name,  currenttaxDefinition.getC_TaxGroup_ID()); 
				  int newTaxTypeID =  copy.getReferenceId(X_C_TaxType.Table_Name,  currenttaxDefinition.getC_TaxType_ID()); 
				  if (newTaxID>0)
					  newTaxDefinition.setC_Tax_ID(newTaxID);
				  if (newTaxGroupID>0)
					  newTaxDefinition.setC_TaxGroup_ID(newTaxGroupID);
				  if (newTaxTypeID>0)
					  newTaxDefinition.setC_TaxType_ID(newTaxTypeID);
				  newTaxDefinition.saveEx();
				  copy.saveReference(currenttaxDefinition, newTaxDefinition);
				 });
////			TaxPayerType
//			copy.getTemplateReferencesOnlyNewIds(I_LCO_TaxPayerType.Table_Name).forEach(currentTaxPayerTypeId -> {
//				X_LCO_TaxPayerType currentTaxpayerType = new X_LCO_TaxPayerType(getCtx(), currentTaxPayerTypeId, get_TrxName()); 
//				X_LCO_TaxPayerType newTaxPayerType = new X_LCO_TaxPayerType(getCtx(), 0, get_TrxName());
//				copy.copyCurrentValues(currentTaxpayerType, newTaxPayerType);
//				newTaxPayerType.setAD_Org_ID(0);
//				newTaxPayerType.saveEx();
//				copy.saveReference(currentTaxpayerType, newTaxPayerType);
//			});
////			LCO_WithholdingType 
//			copy.getTemplateReferencesOnlyNewIds(I_LCO_WithholdingType.Table_Name).forEach(currentWithholdingTypeId -> {
//				X_LCO_WithholdingType currentWithholdingType = new X_LCO_WithholdingType(getCtx(), currentWithholdingTypeId, get_TrxName()); 
//				X_LCO_WithholdingType newWithholdingType = new X_LCO_WithholdingType(getCtx(), 0, get_TrxName());
//				copy.copyCurrentValues(currentWithholdingType, newWithholdingType);
//				newWithholdingType.setAD_Org_ID(0);
//				newWithholdingType.saveEx();
//				copy.saveReference(currentWithholdingType, newWithholdingType);
//			});
//
////			LCO_WithholdingRuleConf 
//			copy.getTemplateReferencesOnlyNewIds(I_LCO_WithholdingRuleConf.Table_Name).forEach(currentWithholdingRuleConfigId -> {
//				X_LCO_WithholdingRuleConf currentWithholdingRuleConfig= new X_LCO_WithholdingRuleConf(getCtx(), currentWithholdingRuleConfigId, get_TrxName()); 
//				X_LCO_WithholdingRuleConf newWithholdingRuleConfig = new X_LCO_WithholdingRuleConf(getCtx(), 0, get_TrxName());
//				copy.copyCurrentValues(currentWithholdingRuleConfig, newWithholdingRuleConfig);
//
//				int newwithholidingType_ID = copy.getReferenceId(X_LCO_WithholdingType.Table_Name, currentWithholdingRuleConfig.getLCO_WithholdingType_ID());
//				newWithholdingRuleConfig.setAD_Org_ID(0);
//				newWithholdingRuleConfig.setLCO_WithholdingType_ID(newwithholidingType_ID);
//				newWithholdingRuleConfig.saveEx();
//				copy.saveReference(currentWithholdingRuleConfig, newWithholdingRuleConfig);
//			});
//			
//
////			LCO_WithholdingCalc 
//			copy.getTemplateReferencesOnlyNewIds(I_LCO_WithholdingCalc.Table_Name).forEach(currentWithholdingCalcId -> {
//				X_LCO_WithholdingCalc currentWithholdingCalc= new X_LCO_WithholdingCalc(getCtx(), currentWithholdingCalcId, get_TrxName()); 
//				X_LCO_WithholdingCalc newWithholdingCalc = new X_LCO_WithholdingCalc(getCtx(), 0, get_TrxName());
//				copy.copyCurrentValues(currentWithholdingCalc, newWithholdingCalc);
//				newWithholdingCalc.setAD_Org_ID(0);
//				int newwithholidingType_ID = copy.getReferenceId(X_LCO_WithholdingType.Table_Name, currentWithholdingCalc.getLCO_WithholdingType_ID());
//				int newwithholidingtax_ID = copy.getReferenceId(X_C_Tax.Table_Name, currentWithholdingCalc.getC_Tax_ID());
//				int newwithholidingbasetax_ID = copy.getReferenceId(X_C_Tax.Table_Name, currentWithholdingCalc.getC_BaseTax_ID());
//				newWithholdingCalc.setLCO_WithholdingType_ID(newwithholidingType_ID);
//
//				if (newwithholidingtax_ID>0)
//					newWithholdingCalc.setC_Tax_ID(newwithholidingtax_ID);
//				if (newwithholidingbasetax_ID>0)
//					newWithholdingCalc.setC_BaseTax_ID(newwithholidingbasetax_ID);
//				newWithholdingCalc.saveEx();
//				copy.saveReference(currentWithholdingCalc, newWithholdingCalc);
//			});
//			
//
////			LCO_WithholdingRule 
//			copy.getTemplateReferencesOnlyNewIds(I_LCO_WithholdingRule.Table_Name).forEach(currentWithholdingRuleId -> {
//				X_LCO_WithholdingRule currentWithholdingRule= new X_LCO_WithholdingRule(getCtx(), currentWithholdingRuleId,  get_TrxName());
//				X_LCO_WithholdingRule newWithholdingRule = new X_LCO_WithholdingRule(getCtx(), 0, get_TrxName());
//				copy.copyCurrentValues(currentWithholdingRule, newWithholdingRule);
//				newWithholdingRule.setAD_Org_ID(0);
//				int newwithholidingType_ID = copy.getReferenceId(X_LCO_WithholdingType.Table_Name, currentWithholdingRule.getLCO_WithholdingType_ID());
//				newWithholdingRule.setLCO_WithholdingType_ID(newwithholidingType_ID);
//				int newwithholidingCalc_ID = copy.getReferenceId(X_LCO_WithholdingCalc.Table_Name, currentWithholdingRule.getLCO_WithholdingCalc_ID());
//				if (newwithholidingCalc_ID>0)
//					newWithholdingRule.setLCO_WithholdingCalc_ID(newwithholidingCalc_ID);
//				int newwithholidingTaxpayerType_ID = copy.getReferenceId(X_LCO_TaxPayerType.Table_Name, currentWithholdingRule.getLCO_BP_TaxPayerType_ID());
//				if (newwithholidingTaxpayerType_ID>0);
//					newWithholdingRule.setLCO_BP_TaxPayerType_ID(newwithholidingTaxpayerType_ID);
//				newWithholdingRule.saveEx();
//				copy.saveReference(currentWithholdingRule, newWithholdingRule);
//			});
//			







		} catch (Exception e) {
			throw new AdempiereException(e);
		} finally {
			CopyContextUtil.newInstance().revertContextToCurrentClient(getCtx(), getProcessInfo().isBatch());
		}
		return "Ok";
	}
}
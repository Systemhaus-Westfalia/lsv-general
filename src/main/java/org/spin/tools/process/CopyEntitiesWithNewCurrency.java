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
import java.math.MathContext;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DocTypeNotFoundException;
import org.adempiere.model.GenericPO;
import org.adempiere.core.domains.models.I_AD_Element;
import org.adempiere.core.domains.models.I_AD_Record_Access;
import org.adempiere.core.domains.models.I_AD_Role;
import org.adempiere.core.domains.models.I_C_BPartner;
import org.adempiere.core.domains.models.I_C_BankAccount;
import org.adempiere.core.domains.models.I_C_BankAccountDoc;
import org.adempiere.core.domains.models.I_C_BankAccount_Acct;
import org.adempiere.core.domains.models.I_C_CashBook;
import org.adempiere.core.domains.models.I_C_CashBook_Acct;
import org.adempiere.core.domains.models.I_C_Commission;
import org.adempiere.core.domains.models.I_C_CommissionLine;
import org.adempiere.core.domains.models.I_C_CommissionSalesRep;
import org.adempiere.core.domains.models.I_C_Country;
import org.adempiere.core.domains.models.I_C_POS;
import org.adempiere.core.domains.models.I_C_Project;
import org.adempiere.core.domains.models.I_M_Freight;
import org.adempiere.core.domains.models.I_M_PriceList;
import org.adempiere.core.domains.models.I_M_PriceList_Version;
import org.adempiere.core.domains.models.I_M_Product_PO;
import org.compiere.model.MBPartner;
import org.compiere.model.MBankAccount;
import org.compiere.model.MBankStatement;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.MCashBook;
import org.compiere.model.MClientInfo;
import org.compiere.model.MCommission;
import org.compiere.model.MCommissionLine;
import org.compiere.model.MConversionRate;
import org.compiere.model.MCountry;
import org.compiere.model.MCurrency;
import org.compiere.model.MDocType;
import org.compiere.model.MFreight;
import org.compiere.model.MPOS;
import org.compiere.model.MPayment;
import org.compiere.model.MPriceList;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.MProductPO;
import org.compiere.model.MProject;
import org.compiere.model.MRecordAccess;
import org.compiere.model.MRole;
import org.compiere.model.MSysConfig;
import org.compiere.model.MTable;
import org.compiere.model.M_Element;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.adempiere.core.domains.models.X_C_BankAccountDoc;
import org.adempiere.core.domains.models.X_C_BankAccount_Acct;
import org.adempiere.core.domains.models.X_C_CashBook_Acct;
import org.adempiere.core.domains.models.X_C_CommissionSalesRep;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.adempiere.core.domains.models.I_C_CashFlow;
import org.adempiere.core.domains.models.I_HR_Attribute;
import org.adempiere.core.domains.models.I_HR_Concept;
import org.adempiere.core.domains.models.I_HR_Employee;
import org.adempiere.core.domains.models.I_HR_Payroll;
import org.adempiere.core.domains.models.I_HR_Process;
import org.eevolution.cashflow.model.MCashFlow;
import org.eevolution.hr.model.MHRAttribute;
import org.eevolution.hr.model.MHRConcept;
import org.eevolution.hr.model.MHREmployee;
import org.eevolution.hr.model.MHRMovement;
import org.eevolution.hr.model.MHRPayroll;
import org.eevolution.hr.model.MHRPayrollConcept;
import org.eevolution.hr.model.MHRProcess;
//import org.erpya.lve.model.I_LVE_List;
//import org.erpya.lve.model.MLVEList;
//import org.erpya.lve.model.MLVEListVersion;
//import org.erpya.lve.util.LVEUtil;

/** Generated Process for (Copy Entities With new Currency)
 *  Just copy all entities related to currency and set the new currency
 *  @author ADempiere (generated) 
 *  @version Release 3.9.3
 */
public class CopyEntitiesWithNewCurrency extends CopyEntitiesWithNewCurrencyAbstract {
	
	/**	Just make a where clause for assurance that is correct entity	*/
	private final String DEFAULT_WHERE_CLAUSE = "UUID IS NOT NULL AND LENGTH(UUID) > 7";
	private final String PRODUCT_PRICE_INSERT = "INSERT INTO M_ProductPrice (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, M_ProductPrice_ID, UUID, M_PriceList_Version_ID, M_Product_ID, PriceList, PriceStd, PriceLimit, PublicMaxPrice) SELECT AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Sequence AS M_ProductPrice_ID, ('' || M_ProductPrice_ID) AS UUID, ?, M_Product_ID, (PriceList * ?), (PriceStd * ?), (PriceLimit * ?), (PublicMaxPrice * ?) FROM M_ProductPrice WHERE M_PriceList_Version_ID = ?";
	private final String NATIVE_SEQUENCE = "nextval('m_productprice_seq') AS M_ProductPrice_ID";
	private final String ADEMPIERE_SEQUENCE = "nextidfunc(165, 'N') AS M_ProductPrice_ID";
	private Map<Integer, Integer> priceListToReplace = new HashMap<Integer, Integer>();
	private Map<Integer, Integer> bankAccountsToReplace = new HashMap<Integer, Integer>();
	private BigDecimal multiplyRate = Env.ZERO;
	private List<Integer> pointOfSales = new ArrayList<Integer>();
	private int precision = 0;
	
	
	@Override
	protected void prepare() {
		super.prepare();
		multiplyRate = MConversionRate.getRate(getCurrencyId(), getCurrencyToId(), getDateTrx(), getConversionTypeId(), getAD_Client_ID(), Env.getAD_Org_ID(getCtx()));
		if(Optional.ofNullable(multiplyRate).orElse(Env.ZERO).compareTo(Env.ZERO) == 0) {
			throw new AdempiereException("@C_ConversionRate_ID@ @NotFound@");
		}
		precision = MCurrency.getStdPrecision(getCtx(), getCurrencyToId());
	}
	
	@Override
	protected String doIt() throws Exception {
		copyPriceList();
		copyBankAccountsAndCashAccounts();
		copyCashBooks();
		copyCashFlows();
		copyPointOfSales();
		copyCommissions();
		copyFreights();
		copyPayrollConcepts();
		copyProductsPO();
		convertPayrollProcess();
		changeDefaultValuesForEntities();
		return "Ok";
	}
	
	/**
	 * Just change the old currency by new
	 */
	private void changeDefaultValuesForEntities() {
		//	Role
		List<Integer> recordIdsList = new ArrayList<Integer>();
		//	Price List to change
		if(priceListToReplace.size() > 0) {
			List<Integer> pricesListIds = new ArrayList<Integer>();
			priceListToReplace.keySet().forEach(key -> pricesListIds.add(key));
			//	Get all price list
			recordIdsList = new Query(getCtx(), I_C_BPartner.Table_Name, "(M_PriceList_ID IN " + pricesListIds.toString().replace('[','(').replace(']',')') + " OR PO_PriceList_ID IN " + pricesListIds.toString().replace('[','(').replace(']',')') + ")", get_TrxName())
			.setClient_ID()
			.getIDsAsList();
			recordIdsList.forEach(businessPartnerId -> {
				MBPartner businessPartner = new MBPartner(getCtx(), businessPartnerId, get_TrxName());
				if(businessPartner.getM_PriceList_ID() > 0) {
					Optional.ofNullable(priceListToReplace.get(businessPartner.getM_PriceList_ID())).ifPresent(priceListId -> businessPartner.setM_PriceList_ID(priceListId));
				}
				if(businessPartner.getPO_PriceList_ID() > 0) {
					Optional.ofNullable(priceListToReplace.get(businessPartner.getPO_PriceList_ID())).ifPresent(priceListId -> businessPartner.setPO_PriceList_ID(priceListId));
				}
				businessPartner.saveEx();
			});
			addLog("@M_PriceList_ID@ => @C_BPartner_ID@ @Changed@: " + recordIdsList.size());
			//	POS
			pointOfSales.forEach(pointOfSalesId -> {
				MPOS pointOfSales = new MPOS(getCtx(), pointOfSalesId, get_TrxName());
				if(pointOfSales.getM_PriceList_ID() > 0) {
					Optional.ofNullable(priceListToReplace.get(pointOfSales.getM_PriceList_ID())).ifPresent(priceListId -> pointOfSales.setM_PriceList_ID(priceListId));
				}
				pointOfSales.saveEx();
			});
			addLog("@M_PriceList_ID@ => @C_POS_ID@ @Changed@: " + pointOfSales.size());
		}
		//	Bank Account
		if(bankAccountsToReplace.size() > 0) {
			List<Integer> bankAccountIds = new ArrayList<Integer>();
			bankAccountsToReplace.keySet().forEach(key -> bankAccountIds.add(key));
			//	Get all price list
			pointOfSales.forEach(pointOfSalesId -> {
				MPOS pointOfSales = new MPOS(getCtx(), pointOfSalesId, get_TrxName());
				if(pointOfSales.getC_BankAccount_ID() > 0) {
					Optional.ofNullable(bankAccountsToReplace.get(pointOfSales.getC_BankAccount_ID())).ifPresent(bankAccountId -> pointOfSales.setC_BankAccount_ID(bankAccountId));
				}
				if(pointOfSales.getCashTransferBankAccount_ID() > 0) {
					Optional.ofNullable(bankAccountsToReplace.get(pointOfSales.getCashTransferBankAccount_ID())).ifPresent(bankAccountId -> pointOfSales.setCashTransferBankAccount_ID(bankAccountId));
				}
				pointOfSales.saveEx();
			});
			addLog("@C_BankAccount_ID@ => @C_POS_ID@ @Changed@: " + pointOfSales.size());
		}
		//	Payroll
		if(Optional.ofNullable(getIsManageClosing()).orElse("N").equals("Y") 
				&& isReconvertClosePayrolls()) {
			recordIdsList = new Query(getCtx(), I_HR_Process.Table_Name, I_HR_Process.COLUMNNAME_DocStatus + " NOT IN('VO', 'RE')", get_TrxName())
					.setClient_ID()
					.getIDsAsList();
					recordIdsList.forEach(payrollProcessId -> {
						MHRProcess payrollProcess = new MHRProcess(getCtx(), payrollProcessId, get_TrxName());
						if(payrollProcess.getAD_Org_ID() == 0) {
							payrollProcess.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
						}
						payrollProcess.setDocStatus(MHRProcess.DOCSTATUS_Closed);
						payrollProcess.setDocAction(MHRProcess.ACTION_None);
						payrollProcess.setProcessed(true);
						payrollProcess.saveEx();
					});
					addLog("@HR_Process_ID@ => @Changed@: " + recordIdsList.size());
		}
		//	For all business partner
		if(isReconvertSetupCurrency()) {
			//	Tribute Unit
			/*
			 * MLVEList tributeUnitDefinition = new Query(getCtx(), I_LVE_List.Table_Name,
			 * I_LVE_List.COLUMNNAME_LVE_ListType_ID + " = ?", get_TrxName())
			 * .setClient_ID()
			 * .setParameters(MClientInfo.get(getCtx()).get_ValueAsInt(LVEUtil.
			 * COLUMNNAME_TributeUnitType_ID)) .setOnlyActiveRecords(true) .first();
			 * if(tributeUnitDefinition != null && tributeUnitDefinition.getLVE_List_ID() >
			 * 0) { MLVEListVersion currentTributeUnitVersion =
			 * tributeUnitDefinition.getValidVersionInstance(getDateTrx());
			 * if(currentTributeUnitVersion != null) { MLVEListVersion newTributeUnitVersion
			 * = new MLVEListVersion(getCtx(), 0, get_TrxName());
			 * PO.copyValues(currentTributeUnitVersion, newTributeUnitVersion, true);
			 * setCopyValues(currentTributeUnitVersion, newTributeUnitVersion);
			 * newTributeUnitVersion.setValidFrom(getDateTrx());
			 * newTributeUnitVersion.saveEx(); saveUuidReference(currentTributeUnitVersion,
			 * newTributeUnitVersion); currentTributeUnitVersion.setIsActive(false);
			 * currentTributeUnitVersion.saveEx(); } }
			 */
			//	
			recordIdsList = new Query(getCtx(), I_AD_Role.Table_Name, DEFAULT_WHERE_CLAUSE + " AND C_Currency_ID = ?", get_TrxName())
			.setParameters(getCurrencyId())
			.setClient_ID()
			.getIDsAsList();
			recordIdsList.forEach(roleId -> {
				MRole role = new MRole(getCtx(), roleId, get_TrxName());
				role.setC_Currency_ID(getCurrencyToId());
				role.saveEx();
			});
			addLog("@AD_Role_ID@ @Changed@: " + recordIdsList.size());
			//	Country
			recordIdsList = new Query(getCtx(), I_C_Country.Table_Name, DEFAULT_WHERE_CLAUSE + " AND C_Currency_ID = ?", get_TrxName())
			.setParameters(getCurrencyId())
			.getIDsAsList();
			recordIdsList.forEach(countryId -> {
				MCountry country = new MCountry(getCtx(), countryId, get_TrxName());
				country.setC_Currency_ID(getCurrencyToId());
				country.saveEx();
			});
			addLog("@C_Country_ID@ @Changed@: " + recordIdsList.size());
			//	Project, maybe this is a no better solution
			recordIdsList = new Query(getCtx(), I_C_Project.Table_Name, DEFAULT_WHERE_CLAUSE + " AND C_Currency_ID = ?", get_TrxName())
			.setParameters(getCurrencyId())
			.getIDsAsList();
			recordIdsList.forEach(projectId -> {
				MProject project = new MProject(getCtx(), projectId, get_TrxName());
				project.setC_Currency_ID(getCurrencyToId());
				project.saveEx();
			});
			addLog("@C_Project_ID@ @Changed@: " + recordIdsList.size());
			int businessPartnerUpdated = DB.executeUpdateEx("UPDATE C_BPartner SET "
					+ "SO_CreditLimit = ROUND(COALESCE(SO_CreditLimit, 0) * ?, ?), "
					+ "SO_CreditUsed = ROUND(COALESCE(SO_CreditUsed, 0) * ?, ?), "
					+ "TotalOpenBalance = ROUND(COALESCE(TotalOpenBalance, 0) * ?, ?), "
					+ "ActualLifeTimeValue = ROUND(COALESCE(ActualLifeTimeValue, 0) * ?, ?) "
					+ "WHERE AD_Client_ID = ?", new Object[]{multiplyRate, precision, multiplyRate, precision, multiplyRate, precision, multiplyRate, precision, getAD_Client_ID()}, get_TrxName());
			addLog("@TotalOpenBalances@ => @C_BPartner_ID@ @Changed@: " + businessPartnerUpdated);
			//	Update currency
			try {
				MCurrency oldCurrency = new MCurrency(getCtx(), getCurrencyId(), get_TrxName());
				String oldISOCode = oldCurrency.getISO_Code();
				String changeValue = oldISOCode.substring(0, 2) +  "~";
				oldCurrency.setISO_Code(changeValue);
				oldCurrency.saveEx();
				MCurrency newCurrency = new MCurrency(getCtx(), getCurrencyToId(), get_TrxName());
				newCurrency.setISO_Code(oldISOCode);
				newCurrency.saveEx();
				addLog("@C_Currency_ID@ => " + oldISOCode + " @Changed@ @To@ " + changeValue);
			} catch (Exception e) {
				addLog("@C_Currency_ID@ => @Error@ " + e.getLocalizedMessage());
			}
			//	Update Employee data
			recordIdsList = new Query(getCtx(), I_HR_Employee.Table_Name, null, get_TrxName())
			.setClient_ID()
			.setOnlyActiveRecords(true)
			.getIDsAsList();
			recordIdsList.forEach(employeeId -> {
				MHREmployee employee = new MHREmployee(getCtx(), employeeId, get_TrxName());
				Optional.ofNullable(employee.getDailySalary()).ifPresent(dailySalary -> {
					employee.setDailySalary(dailySalary.multiply(multiplyRate, MathContext.DECIMAL128));
				});
				Optional.ofNullable(employee.getMonthlySalary()).ifPresent(montlySalary -> {
					employee.setMonthlySalary(montlySalary.multiply(multiplyRate, MathContext.DECIMAL128));
				});
				employee.saveEx();
			});
			addLog("@HR_Employee_ID@ => @Changed@: " + recordIdsList.size());
		}
	}
	
	/**
	 * Convert Payroll Process
	 */
	private void convertPayrollProcess() {
		if(Optional.ofNullable(getIsManageClosing()).orElse("N").equals("N") 
				|| !isReconvertClosePayrolls()) {
			return;
		}
		if(Optional.ofNullable(getIsManageClosing()).orElse("N").equals("N")) {
			return;
		}
		List<Integer> recordIdsList = new Query(getCtx(), I_HR_Payroll.Table_Name, null, get_TrxName())
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.getIDsAsList();
		recordIdsList.forEach(payrollId -> copyPayrollProcess(payrollId));
		addLog("@HR_Payroll_ID@: " + recordIdsList.size());
	}

	/**
	 * Copy all Price Lists
	 */
	private void copyPriceList() {
		if(!isReconvertPriceLists()) {
			return;
		}
		List<Integer> recordIdsList = new Query(getCtx(), I_M_PriceList.Table_Name, DEFAULT_WHERE_CLAUSE + " AND C_Currency_ID = ? "
				+ "AND NOT EXISTS(SELECT 1 FROM M_PriceList r WHERE r.UUID ~ '^\\d+$' AND CAST(r.UUID AS NUMERIC) = M_PriceList.M_PriceList_ID)", get_TrxName())
				.setParameters(getCurrencyId())
				.setClient_ID()
				.getIDsAsList();
		recordIdsList.forEach(priceListId -> copyPriceListById(priceListId));
		addLog("@M_PriceList_ID@: " + recordIdsList.size());
	}
	
	/**
	 * Copy all Product PO
	 */
	private void copyProductsPO() {
		if(!isReconvertProductPOs()) {
			return;
		}
		List<MProductPO> recordList = new Query(getCtx(), I_M_Product_PO.Table_Name, DEFAULT_WHERE_CLAUSE + " AND C_Currency_ID = ? "
				+ "AND NOT EXISTS(SELECT 1 FROM M_Product_PO r WHERE r.UUID ~ '^\\d+$' AND CAST(r.UUID AS NUMERIC) = M_Product_PO.M_Product_ID AND r.C_Currency_ID = ?)", get_TrxName())
				.setParameters(getCurrencyId(), getCurrencyToId())
				.setClient_ID()
				.<MProductPO>list();
		recordList.forEach(productPO -> copyProductPO(productPO));
		addLog("@M_Product_ID@ @IsPurchased@: " + recordList.size());
	}
	
	/**
	 * Copy all Commissions
	 */
	private void copyCommissions() {
		if(!isReconvertCommissions()) {
			return;
		}
		List<Integer> recordIdsList = new Query(getCtx(), I_C_Commission.Table_Name, DEFAULT_WHERE_CLAUSE + " AND C_Currency_ID = ? "
				+ "AND NOT EXISTS(SELECT 1 FROM C_Commission r WHERE r.UUID ~ '^\\d+$' AND CAST(r.UUID AS NUMERIC) = C_Commission.C_Commission_ID)", get_TrxName())
				.setParameters(getCurrencyId())
				.setClient_ID()
				.getIDsAsList();
		recordIdsList.forEach(recordId -> copyCommisionById(recordId));
		addLog("@C_Commission_ID@: " + recordIdsList.size());
	}
	
	/**
	 * Copy all Freight
	 */
	private void copyFreights() {
		if(!isReconvertFreights()) {
			return;
		}
		List<Integer> recordIdsList = new Query(getCtx(), I_M_Freight.Table_Name, DEFAULT_WHERE_CLAUSE + " AND C_Currency_ID = ? "
				+ "AND NOT EXISTS(SELECT 1 FROM M_Freight r WHERE r.UUID ~ '^\\d+$' AND CAST(r.UUID AS NUMERIC) = M_Freight.M_Freight_ID)", get_TrxName())
				.setParameters(getCurrencyId())
				.setClient_ID()
				.getIDsAsList();
		recordIdsList.forEach(recordId -> copyFreight(recordId));
		addLog("@M_Freight_ID@: " + recordIdsList.size());
	}
	
	/**
	 * Copy all attributes
	 */
	private void copyPayrollConcepts() {
		if(!isReconvertPayrollConcepts()) {
			return;
		}
		if (M_Element.get(Env.getCtx(), "IsManageReconversion") == null) {
			return;
		}
		List<Integer> conceptsIds = new Query(getCtx(), I_HR_Concept.Table_Name, "IsManageReconversion = 'Y'", get_TrxName())
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.getIDsAsList();
		conceptsIds.forEach(conceptId -> {
			MHRConcept concept = MHRConcept.getById(getCtx(), conceptId, get_TrxName());
			List<MHRAttribute> currentAttributes = new Query(getCtx(), I_HR_Attribute.Table_Name, DEFAULT_WHERE_CLAUSE + " AND " + I_HR_Attribute.COLUMNNAME_HR_Concept_ID + " = ?", get_TrxName())
					.setParameters(concept.getHR_Concept_ID())
					.setOnlyActiveRecords(true)
					.<MHRAttribute>list();
			if(concept.isEmployee()) {
				Map<Integer, Integer> validAttribute = new HashMap<Integer, Integer>();
				currentAttributes.forEach(attribute -> {
					if(!validAttribute.containsKey(attribute.getC_BPartner_ID())) {
						Optional<MHRAttribute> maybeAttribute = currentAttributes.stream()
								.filter(attributeToFind -> attributeToFind.getC_BPartner_ID() == attribute.getC_BPartner_ID())
								.sorted(Comparator.comparing(MHRAttribute::getValidFrom).reversed())
								.findFirst();
						maybeAttribute.ifPresent(attributeFinded -> validAttribute.put(attribute.getC_BPartner_ID(), attributeFinded.getHR_Attribute_ID()));
					}
				});
				if(validAttribute.size() > 0) {
					validAttribute.values().forEach(attributeId -> copyAttribute(attributeId));
				}
			} else {
				Optional<MHRAttribute> maybeAttribute = currentAttributes.stream().sorted(Comparator.comparing(MHRAttribute::getValidFrom).reversed()).findFirst();
				maybeAttribute.ifPresent(attribute -> copyAttribute(attribute.getHR_Attribute_ID()));
			}
			//	
			currentAttributes.stream()
			.filter(attribute -> attribute.get_ValueAsInt("C_Currency_ID") <= 0 || attribute.get_ValueAsInt("C_Currency_ID") == getCurrencyId()).forEach(attribute -> {
				attribute.setIsActive(false);
				attribute.saveEx();
			});
		});
		addLog("@HR_Concept_ID@: " + conceptsIds.size());
	}
	
	/**
	 * Create ajust process
	 * @param payrollId
	 * @return
	 */
	private void copyPayrollProcess(int payrollId) {
		if(payrollId <= 0) {
			return;
		}
		MHRPayroll payroll = MHRPayroll.getById(getCtx(), payrollId, get_TrxName());
		MHRProcess process = new MHRProcess(getCtx(), 0, get_TrxName());
		Optional<MDocType> defaultDocumentType = Arrays.asList(MDocType.getOfDocBaseType(Env.getCtx(), MDocType.DOCBASETYPE_Payroll))
        		.stream()
        		.findFirst();	
		if (!defaultDocumentType.isPresent()) {
        	throw new DocTypeNotFoundException(MDocType.DOCBASETYPE_Payroll, "");
        }
		process.setHR_Payroll_ID(payroll.getHR_Payroll_ID());
		process.setDateAcct(getDateTrx());
		process.setC_DocTypeTarget_ID(defaultDocumentType.get().getC_DocType_ID());
		process.setC_DocType_ID(defaultDocumentType.get().getC_DocType_ID());
		process.setDocumentNo(Msg.parseTranslation(getCtx(), "@closed@") + getSuffix());
		process.setC_Currency_ID(getCurrencyToId());
		process.setC_ConversionType_ID(getConversionTypeId());
		process.setName(Msg.parseTranslation(getCtx(), "@closed@") + getSuffix());
		process.setProcessed(true);
		process.setDocStatus(MHRProcess.DOCSTATUS_Closed);
		process.setDocAction(MHRProcess.ACTION_None);
		process.saveEx();
		saveUuidReference(payroll, process);
		//	For Concepts
		List<MHRConcept> concepts = Arrays.asList(MHRPayrollConcept.getPayrollConcepts(process)).stream()
			.map(payrollConcept -> MHRConcept.getById(getCtx(), payrollConcept.getHR_Concept_ID(), get_TrxName()))
			.filter(concept -> concept.get_ValueAsBoolean("IsAccumulatedConcept"))
			.collect(Collectors.toList());
		//	For employee
		Arrays.asList(MHREmployee.getEmployees(process)).forEach(employee -> {
			concepts.forEach(concept -> {
				MHRMovement lastMovement = MHRMovement.getLastMovement(getCtx(), concept.getValue(), payroll.getHR_Payroll_ID(), employee.getC_BPartner_ID(), getDateTrx(), get_TrxName());
				if(lastMovement != null) {
					MHRMovement newMovement = new MHRMovement(getCtx(), 0, get_TrxName());
					PO.copyValues(lastMovement, newMovement, true);
					setCopyValues(lastMovement, lastMovement);
					newMovement.setHR_Process_ID(process.getHR_Process_ID());
					newMovement.setValidFrom(process.getDateAcct());
					newMovement.setValidTo(process.getDateAcct());
					newMovement.setAmount(Optional.ofNullable(newMovement.getAmount()).orElse(Env.ZERO).multiply(multiplyRate, MathContext.DECIMAL128));
					newMovement.setQty(Optional.ofNullable(newMovement.getQty()).orElse(Env.ZERO).multiply(multiplyRate, MathContext.DECIMAL128));
					newMovement.setProcessed(true);
					newMovement.saveEx();
					saveUuidReference(lastMovement, newMovement);
				}
			});
		});
	}
	
	/**
	 * Copy Product Purchases
	 * @param currentProductPO
	 * @return
	 */
	private void copyProductPO(MProductPO currentProductPO) {
		MProductPO newProductPO = new MProductPO(getCtx(), currentProductPO.getM_Product_ID(), currentProductPO.getC_BPartner_ID(), getCurrencyToId(), get_TrxName());
		PO.copyValues(currentProductPO, newProductPO, true);
		setCopyValues(currentProductPO, newProductPO);
		newProductPO.setC_Currency_ID(getCurrencyToId());
		//	Save
		newProductPO.saveEx(get_TrxName());
		newProductPO.setUUID(String.valueOf(currentProductPO.getM_Product_ID()));
		Optional.ofNullable(newProductPO.getPricePO()).ifPresent(price -> newProductPO.setPricePO(price.multiply(multiplyRate, MathContext.DECIMAL128)));
		Optional.ofNullable(newProductPO.getPriceLastInv()).ifPresent(price -> newProductPO.setPriceLastInv(price.multiply(multiplyRate, MathContext.DECIMAL128)));
		Optional.ofNullable(newProductPO.getPriceLastPO()).ifPresent(price -> newProductPO.setPriceLastPO(price.multiply(multiplyRate, MathContext.DECIMAL128)));
		Optional.ofNullable(newProductPO.getPriceList()).ifPresent(price -> newProductPO.setPriceList(price.multiply(multiplyRate, MathContext.DECIMAL128)));
		Optional.ofNullable(newProductPO.getRoyaltyAmt()).ifPresent(price -> newProductPO.setRoyaltyAmt(price.multiply(multiplyRate, MathContext.DECIMAL128)));
		newProductPO.saveEx(get_TrxName());
		currentProductPO.setIsCurrentVendor(false);
		currentProductPO.setIsActive(false);
		currentProductPO.saveEx(get_TrxName());
	}
	
	/**
	 * Create and get new Freight
	 * @param currentFreightId
	 * @return
	 */
	private void copyFreight(int currentFreightId) {
		if(currentFreightId <= 0) {
			return;
		}
		MFreight currentFreight = new MFreight(getCtx(), currentFreightId, get_TrxName());
		//	Create
		MFreight newFreight = new MFreight(getCtx(), 0, get_TrxName());
		PO.copyValues(currentFreight, newFreight, true);
		setCopyValues(currentFreight, newFreight);
		Optional.ofNullable(currentFreight.getFreightAmt()).ifPresent(price -> currentFreight.setFreightAmt(price.multiply(multiplyRate, MathContext.DECIMAL128)));
		//	Save
		newFreight.saveEx(get_TrxName());
		newFreight.setC_Currency_ID(getCurrencyToId());
		saveUuidReference(currentFreight, newFreight);
	}
	
	/**
	 * Create and get new Payroll Attribute
	 * @param currentAttributeId
	 * @return
	 */
	private void copyAttribute(int currentAttributeId) {
		if(currentAttributeId <= 0) {
			return;
		}
		MHRAttribute currentAttribute = new MHRAttribute(getCtx(), currentAttributeId, get_TrxName());
		//	Create
		MHRAttribute newAttributes = new MHRAttribute(getCtx(), 0, get_TrxName());
		PO.copyValues(currentAttribute, newAttributes, true);
		setCopyValues(currentAttribute, newAttributes);
		newAttributes.setValidFrom(getDateTrx());
		//	Save
		newAttributes.saveEx(get_TrxName());
		if(currentAttribute.get_ValueAsInt("C_Currency_ID") > 0) {
			newAttributes.set_ValueOfColumn("C_Currency_ID", getCurrencyToId());
		}
		//	Only without currency or with source currency
		if(currentAttribute.get_ValueAsInt("C_Currency_ID") <= 0 || currentAttribute.get_ValueAsInt("C_Currency_ID") == getCurrencyId()) {
			Optional.ofNullable(newAttributes.getAmount()).ifPresent(price -> newAttributes.setAmount(price.multiply(multiplyRate, MathContext.DECIMAL128)));
		}
		saveUuidReference(currentAttribute, newAttributes);
	}
	
	/**
	 * Copy Price List
	 * @param currentCommissionId
	 * @return
	 */
	private void copyCommisionById(int currentCommissionId) {
		if(currentCommissionId <= 0) {
			return;
		}
		MCommission currentCommission = new MCommission(getCtx(), currentCommissionId, get_TrxName());
		String newName = getName(currentCommission.getName());
		//	Create
		MCommission newCommission = new MCommission(getCtx(), 0, get_TrxName());
		PO.copyValues(currentCommission, newCommission, true);
		setCopyValues(currentCommission, newCommission);
		currentCommission.setName(newName);
		currentCommission.setIsActive(false);
		currentCommission.saveEx();
		newCommission.setC_Currency_ID(getCurrencyToId());
		//	Save
		newCommission.saveEx(get_TrxName());
		saveUuidReference(currentCommission, newCommission);
		//	For Lines
		new Query(getCtx(), I_C_CommissionLine.Table_Name, I_C_CommissionLine.COLUMNNAME_C_Commission_ID + " = ?", get_TrxName())
			.setParameters(currentCommission.getC_Commission_ID())
			.getIDsAsList()
			.forEach(commissionLineId -> {
				MCommissionLine currentCommissionLine = new MCommissionLine(getCtx(), commissionLineId, get_TrxName());
				MCommissionLine newCommissionLine = new MCommissionLine(getCtx(), 0, get_TrxName());
				PO.copyValues(currentCommissionLine, newCommissionLine, true);
				setCopyValues(currentCommissionLine, newCommissionLine);
				newCommissionLine.setC_Commission_ID(newCommission.getC_Commission_ID());
				newCommissionLine.saveEx();
				saveUuidReference(currentCommissionLine, newCommissionLine);
			});
		//	For Sales Rep
		new Query(getCtx(), I_C_CommissionSalesRep.Table_Name, I_C_CommissionSalesRep.COLUMNNAME_C_Commission_ID + " = ?", get_TrxName())
			.setParameters(currentCommission.getC_Commission_ID())
			.getIDsAsList()
			.forEach(commissionSalesRepId -> {
				X_C_CommissionSalesRep currentCommissionLine = new X_C_CommissionSalesRep(getCtx(), commissionSalesRepId, get_TrxName());
				X_C_CommissionSalesRep newCommissionLine = new X_C_CommissionSalesRep(getCtx(), 0, get_TrxName());
				PO.copyValues(currentCommissionLine, newCommissionLine, true);
				setCopyValues(currentCommissionLine, newCommissionLine);
				newCommissionLine.setC_Commission_ID(newCommission.getC_Commission_ID());
				newCommissionLine.saveEx();
				saveUuidReference(currentCommissionLine, newCommissionLine);
			});
	}
	
	/**
	 * Copy Price List
	 * @param currentPriceListId
	 * @return
	 */
	private void copyPriceListById(int currentPriceListId) {
		if(currentPriceListId <= 0) {
			return;
		}
		boolean SYSTEM_NATIVE_SEQUENCE = MSysConfig.getBooleanValue("SYSTEM_NATIVE_SEQUENCE", false);
		String sqlInsert = PRODUCT_PRICE_INSERT.replace("Sequence AS M_ProductPrice_ID", SYSTEM_NATIVE_SEQUENCE? NATIVE_SEQUENCE: ADEMPIERE_SEQUENCE);
		MPriceList currentPriceList = new MPriceList(getCtx(), currentPriceListId, get_TrxName());
		String newName = getName(currentPriceList.getName());
		//	Create
		MPriceList newPriceList = new MPriceList(getCtx(), 0, get_TrxName());
		PO.copyValues(currentPriceList, newPriceList, true);
		setCopyValues(currentPriceList, newPriceList);
		currentPriceList.setName(newName);
		currentPriceList.setIsDefault(false);
		currentPriceList.setIsActive(false);
		currentPriceList.saveEx();
		newPriceList.setC_Currency_ID(getCurrencyToId());
		//	Save
		newPriceList.saveEx(get_TrxName());
		saveUuidReference(currentPriceList, newPriceList);
		//	Copy access
		copyRecordAccess(currentPriceList.getM_PriceList_ID(), newPriceList.getM_PriceList_ID(), I_M_PriceList.Table_ID);
		//	Add to price list to replace
		priceListToReplace.put(currentPriceList.getM_PriceList_ID(), newPriceList.getM_PriceList_ID());
		MPriceListVersion currentVersion = currentPriceList.getPriceListVersion(getDateTrx());
		if(currentVersion != null) {
			AtomicInteger counter = new AtomicInteger();
			MPriceListVersion newVersion = new MPriceListVersion(getCtx(), 0, get_TrxName());
			PO.copyValues(currentVersion, newVersion, true);
			setCopyValues(currentVersion, newVersion);
			currentVersion.setName(getName(currentVersion.getName()));
			currentVersion.saveEx();
			newVersion.setM_PriceList_ID(newPriceList.getM_PriceList_ID());
			newVersion.setValidFrom(getDateTrx());
			newVersion.saveEx();
			saveUuidReference(currentVersion, newVersion);
			//	Insert
			counter.addAndGet(DB.executeUpdateEx(sqlInsert, new Object[]{ newVersion.getM_PriceList_Version_ID(), multiplyRate, multiplyRate, multiplyRate, multiplyRate, currentVersion.getM_PriceList_Version_ID() }, get_TrxName()));
			//	For Version
			new Query(getCtx(), I_M_PriceList_Version.Table_Name, I_M_PriceList_Version.COLUMNNAME_M_PriceList_ID + " = ?", get_TrxName())
				.setParameters(currentPriceList.getM_PriceList_ID())
				.getIDsAsList()
				.forEach(currentPriceListVersionId -> {
					MPriceListVersion currentPriceListVersion = new MPriceListVersion(getCtx(), currentPriceListVersionId, get_TrxName());
					currentPriceListVersion.setIsActive(false);
					currentPriceListVersion.saveEx();
				});
			addLog("@M_ProductPrice_ID@ " + currentPriceList.getName() + " => @Copied@: " + counter);
		}
	}
	
	/**
	 * Copy default values
	 * @param source
	 * @param target
	 */
	private void setCopyValues(PO source, PO target) {
		target.setAD_Org_ID(source.getAD_Org_ID());
		target.setIsActive(source.isActive());
	}
	
	/**
	 * Set reference with UUID
	 * @param source
	 * @param target
	 */
	private void saveUuidReference(PO source, PO target) {
		target.set_ValueOfColumn(I_AD_Element.COLUMNNAME_UUID, String.valueOf(source.get_ID()));
		target.saveEx();
	}
	
	/**
	 * Copy all Cash Accounts
	 */
	private void copyBankAccountsAndCashAccounts() {
		if(!isReconvertBankAccounts()) {
			return;
		}
		List<Integer> recordIdsList = new Query(getCtx(), I_C_BankAccount.Table_Name, DEFAULT_WHERE_CLAUSE + " AND C_Currency_ID = ? "
				+ "AND NOT EXISTS(SELECT 1 FROM C_BankAccount r WHERE r.UUID ~ '^\\d+$' AND CAST(r.UUID AS NUMERIC) = C_BankAccount.C_BankAccount_ID)", get_TrxName())
				.setParameters(getCurrencyId())
				.setClient_ID()
				.getIDsAsList();
		recordIdsList.forEach(currentCashAccountId -> copyBankAccountAndCashAccount(currentCashAccountId));
		addLog("@C_BankAccount_ID@: " + recordIdsList.size());
	}
	
	/**
	 * Copy all Cash (Old definition)
	 */
	private void copyCashBooks() {
		if(!isReconvertCashBooks()) {
			return;
		}
		List<Integer> recordIdsList = new Query(getCtx(), I_C_CashBook.Table_Name, DEFAULT_WHERE_CLAUSE + " AND C_Currency_ID = ? "
				+ "AND NOT EXISTS(SELECT 1 FROM C_CashBook r WHERE r.UUID ~ '^\\d+$' AND CAST(r.UUID AS NUMERIC) = C_CashBook.C_CashBook_ID)", get_TrxName())
				.setParameters(getCurrencyId())
				.setClient_ID()
				.getIDsAsList();
		recordIdsList.forEach(currentCashId -> copyCashBook(currentCashId));
		addLog("@C_Cash_ID@: " + recordIdsList.size());
	}
	
	/**
	 * Copy all Cash Flow
	 */
	private void copyCashFlows() {
		if(!isReconvertCashFlows()) {
			return;
		}
		List<Integer> recordIdsList = new Query(getCtx(), I_C_CashFlow.Table_Name, DEFAULT_WHERE_CLAUSE + " AND C_Currency_ID = ? "
				+ "AND NOT EXISTS(SELECT 1 FROM C_CashFlow r WHERE r.UUID ~ '^\\d+$' AND CAST(r.UUID AS NUMERIC) = C_CashFlow.C_CashFlow_ID)", get_TrxName())
				.setParameters(getCurrencyId())
				.setClient_ID()
				.getIDsAsList();
		recordIdsList.forEach(currentCashFlowId -> copyCashFlow(currentCashFlowId));
		addLog("@C_CashFlow_ID@: " + recordIdsList.size());
	}
	
	/**
	 * Copy all Point of Sales
	 */
	private void copyPointOfSales() {
		if(!isReconvertPOS()) {
			return;
		}
		List<Integer> recordIdsList = new Query(getCtx(), I_C_POS.Table_Name, DEFAULT_WHERE_CLAUSE + " "
				+ "AND EXISTS(SELECT 1 FROM M_PriceList pl WHERE pl.M_PriceList_ID = C_POS.M_PriceList_ID AND pl.C_Currency_ID = ?) "
				+ "AND NOT EXISTS(SELECT 1 FROM C_POS r WHERE r.UUID ~ '^\\d+$' AND CAST(r.UUID AS NUMERIC) = C_POS.C_POS_ID)", get_TrxName())
				.setParameters(getCurrencyId())
				.setClient_ID()
				.getIDsAsList();
		recordIdsList.forEach(currentPointOfSalesId -> copyPointOfSales(currentPointOfSalesId));
		addLog("@C_POS_ID@: " + recordIdsList.size());
	}
	
	/**
	 * Create and get new Point of Sales
	 * @param currentPointOfSalesId
	 * @return
	 */
	private void copyPointOfSales(int currentPointOfSalesId) {
		if(currentPointOfSalesId <= 0) {
			return;
		}
		MPOS currentPointOfSales = new MPOS(getCtx(), currentPointOfSalesId, get_TrxName());
		String newName = getName(currentPointOfSales.getName());
		//	Create
		MPOS newPointOfSales = new MPOS(getCtx(), 0, get_TrxName());
		PO.copyValues(currentPointOfSales, newPointOfSales, true);
		setCopyValues(currentPointOfSales, newPointOfSales);
		currentPointOfSales.setName(newName);
		currentPointOfSales.saveEx();
		//	Save
		newPointOfSales.saveEx(get_TrxName());
		saveUuidReference(currentPointOfSales, newPointOfSales);
		currentPointOfSales.setIsActive(false);
		currentPointOfSales.saveEx();
		pointOfSales.add(newPointOfSales.getC_POS_ID());
	}
	
	/**
	 * Copy Cash
	 * @param currentCashBookId
	 * @return
	 */
	private void copyCashBook(int currentCashBookId) {
		if(currentCashBookId <= 0) {
			return;
		}
		MCashBook currentCashBook = new MCashBook(getCtx(), currentCashBookId, get_TrxName());
		String newName = getName(currentCashBook.getName());
		//	Create
		MCashBook newCashBook = new MCashBook(getCtx(), 0, get_TrxName());
		PO.copyValues(currentCashBook, newCashBook, true);
		setCopyValues(currentCashBook, newCashBook);
		newCashBook.setName(newName);
		newCashBook.setC_Currency_ID(getCurrencyToId());
		//	Save
		newCashBook.saveEx(get_TrxName());
		saveUuidReference(currentCashBook, newCashBook);
		currentCashBook.setIsActive(false);
		currentCashBook.saveEx();
		//	Copy access
		copyRecordAccess(currentCashBook.getC_CashBook_ID(), newCashBook.getC_CashBook_ID(), I_C_CashBook.Table_ID);
		copyCashBookAcct(currentCashBook.getC_CashBook_ID(), newCashBook.getC_CashBook_ID());
	}
	
	/**
	 * Copy Cash Flow
	 * @param currentCashFlowId
	 * @return
	 */
	private void copyCashFlow(int currentCashFlowId) {
		if(currentCashFlowId <= 0) {
			return;
		}
		MCashFlow currentCashFlow = new MCashFlow(getCtx(), currentCashFlowId, get_TrxName());
		String newName = getName(currentCashFlow.getDocumentNo());
		//	Create
		MCashFlow newCashFlow = new MCashFlow(getCtx(), 0, get_TrxName());
		PO.copyValues(currentCashFlow, newCashFlow, true);
		setCopyValues(currentCashFlow, newCashFlow);
		currentCashFlow.setDocumentNo(newName);
		currentCashFlow.saveEx();
		newCashFlow.setC_Currency_ID(getCurrencyToId());
		//	Save
		newCashFlow.saveEx(get_TrxName());
		saveUuidReference(currentCashFlow, newCashFlow);
	}
	
	/**
	 * Create and get Cash account
	 * @param currentCashAccountId
	 * @return
	 */
	private void copyBankAccountAndCashAccount(int currentCashAccountId) {
		if(currentCashAccountId <= 0) {
			return;
		}
		MBankAccount currentCashAccount = new MBankAccount(getCtx(), currentCashAccountId, get_TrxName());
		String newName = getName(currentCashAccount.getAccountNo());
		//	Create
		MBankAccount newCashAccount = new MBankAccount(getCtx(), 0, get_TrxName());
		PO.copyValues(currentCashAccount, newCashAccount, true);
		setCopyValues(currentCashAccount, newCashAccount);
		newCashAccount.setAccountNo(currentCashAccount.getAccountNo());
		newCashAccount.setCurrentBalance(Env.ZERO);
		newCashAccount.setC_Currency_ID(getCurrencyToId());
		//	Save
		newCashAccount.saveEx(get_TrxName());
		saveUuidReference(currentCashAccount, newCashAccount);
		//	Bank accounts to replace
		bankAccountsToReplace.put(currentCashAccount.getC_BankAccount_ID(), newCashAccount.getC_BankAccount_ID());
		//	Copy Document by bank account
		copyBankAccountDocument(currentCashAccount.getC_BankAccount_ID(), newCashAccount.getC_BankAccount_ID());
		copyBankAccountWithdrawal(currentCashAccount.getC_BankAccount_ID(), newCashAccount.getC_BankAccount_ID());
		copyBankAccountAcct(currentCashAccount.getC_BankAccount_ID(), newCashAccount.getC_BankAccount_ID());
		//	
		currentCashAccount.setAccountNo(newName);
		currentCashAccount.setIsActive(!isDisableOldBankAccounts());
		currentCashAccount.setIsDirectLoad(true);
		currentCashAccount.saveEx();
		//	Copy access
		copyRecordAccess(currentCashAccount.getC_BankAccount_ID(), newCashAccount.getC_BankAccount_ID(), I_C_BankAccount.Table_ID);
		//Close Bank Account 
		closeBankCashAccount(currentCashAccount, newCashAccount);
	}
	
	/**
	 * Copy document by bank account
	 * @param currentCashAccountId
	 * @param newCashAccountId
	 */
	private void copyBankAccountDocument(int currentCashAccountId, int newCashAccountId) {
		//	For Role
		new Query(getCtx(), I_C_BankAccountDoc.Table_Name, I_C_BankAccountDoc.COLUMNNAME_C_BankAccount_ID + " = ?", get_TrxName())
				.setParameters(currentCashAccountId)
				.<X_C_BankAccountDoc>list().stream()
				.forEach(currentBankAccountDocument -> {
					X_C_BankAccountDoc bankAccountDocument = new X_C_BankAccountDoc(getCtx(), 0, get_TrxName());
					PO.copyValues(currentBankAccountDocument, bankAccountDocument, true);
					bankAccountDocument.setC_BankAccount_ID(newCashAccountId);
					setCopyValues(currentBankAccountDocument, bankAccountDocument);
					bankAccountDocument.setCurrentNext(currentBankAccountDocument.getCurrentNext());
					bankAccountDocument.saveEx();
					saveUuidReference(currentBankAccountDocument, bankAccountDocument);
		});
	}
	
	/**
	 * Copy document by bank account
	 * @param currentCashAccountId
	 * @param newCashAccountId
	 */
	private void copyBankAccountWithdrawal(int currentCashAccountId, int newCashAccountId) {
		String tableName = "C_BankAccountWithdrawal";
		if(MTable.getTable_ID(tableName) <= 0) {
			return;
		}
		//	For Role
		new Query(getCtx(), tableName, "C_BankAccount_ID = ?", get_TrxName())
				.setParameters(currentCashAccountId)
				.list().stream()
				.forEach(currentBankAccountWithdrawal -> {
					GenericPO newBankAccountWithdrawal = new GenericPO(tableName, getCtx(), 0, get_TrxName());
					String newName = getName(currentBankAccountWithdrawal.get_ValueAsString("Name"));
					PO.copyValues(currentBankAccountWithdrawal, newBankAccountWithdrawal, true);
					newBankAccountWithdrawal.set_ValueOfColumn("C_BankAccount_ID", newCashAccountId);
					setCopyValues(currentBankAccountWithdrawal, newBankAccountWithdrawal);
					if(currentBankAccountWithdrawal.get_ValueAsInt("C_Currency_ID") > 0) {
						newBankAccountWithdrawal.set_ValueOfColumn("C_Currency_ID", getCurrencyToId());
					}
					newBankAccountWithdrawal.set_ValueOfColumn("Name", newName);
					newBankAccountWithdrawal.saveEx();
					saveUuidReference(currentBankAccountWithdrawal, newBankAccountWithdrawal);
					//	Add
					addLog("@C_BankAccountWithdrawal_ID@ @Added@: " + newBankAccountWithdrawal.get_ValueAsString("Name"));
		});
	}
	
	/**
	 * Copy bank account acct
	 * @param currentCashAccountId
	 * @param newCashAccountId
	 */
	private void copyBankAccountAcct(int currentCashAccountId, int newCashAccountId) {
		//	For Role
		List<X_C_BankAccount_Acct> currentBankAccountsAcct = new Query(getCtx(), I_C_BankAccount_Acct.Table_Name, I_C_BankAccount_Acct.COLUMNNAME_C_BankAccount_ID + " = ?", get_TrxName())
				.setParameters(currentCashAccountId)
				.<X_C_BankAccount_Acct>list();
		List<X_C_BankAccount_Acct> newBankAccountsAcct = new Query(getCtx(), I_C_BankAccount_Acct.Table_Name, I_C_BankAccount_Acct.COLUMNNAME_C_BankAccount_ID + " = ?", get_TrxName())
				.setParameters(newCashAccountId)
				.<X_C_BankAccount_Acct>list();
		//	
		newBankAccountsAcct.forEach(newBankAccountAcct -> {
			Optional<X_C_BankAccount_Acct> maybeBankAccountAcct = currentBankAccountsAcct.stream().filter(currentBankAccountAcct -> currentBankAccountAcct.getC_AcctSchema_ID() == newBankAccountAcct.getC_AcctSchema_ID()).findFirst();
			maybeBankAccountAcct.ifPresent(currentBankAccountAcct -> {
				PO.copyValues(currentBankAccountAcct, newBankAccountAcct, true);
				newBankAccountAcct.setC_BankAccount_ID(newCashAccountId);
				saveUuidReference(currentBankAccountAcct, newBankAccountAcct);
			});
		});
	}
	
	/**
	 * Copy bank account acct
	 * @param currentCashId
	 * @param newCashAId
	 */
	private void copyCashBookAcct(int currentCashId, int newCashAId) {
		//	For Role
		List<X_C_CashBook_Acct> currentBankAccountsAcct = new Query(getCtx(), I_C_CashBook_Acct.Table_Name, I_C_CashBook_Acct.COLUMNNAME_C_CashBook_ID + " = ?", get_TrxName())
				.setParameters(currentCashId)
				.<X_C_CashBook_Acct>list();
		List<X_C_CashBook_Acct> newBankAccountsAcct = new Query(getCtx(), I_C_CashBook_Acct.Table_Name, I_C_CashBook_Acct.COLUMNNAME_C_CashBook_ID + " = ?", get_TrxName())
				.setParameters(newCashAId)
				.<X_C_CashBook_Acct>list();
		//	
		newBankAccountsAcct.forEach(newBankAccountAcct -> {
			Optional<X_C_CashBook_Acct> maybeBankAccountAcct = currentBankAccountsAcct.stream().filter(currentBankAccountAcct -> currentBankAccountAcct.getC_AcctSchema_ID() == newBankAccountAcct.getC_AcctSchema_ID()).findFirst();
			maybeBankAccountAcct.ifPresent(currentBankAccountAcct -> {
				PO.copyValues(currentBankAccountAcct, newBankAccountAcct, true);
				newBankAccountAcct.setC_CashBook_ID(newCashAId);
				saveUuidReference(currentBankAccountAcct, newBankAccountAcct);
			});
		});
	}
	
	/**
	 * Get Document Type name
	 * @param name
	 * @return
	 */
	private String getName(String name) {
		return name + getSuffix();
	}
	
	/**
	 * Close Transaction of Bank
	 * @param sourceBankAccount
	 * @param targetBankAccount
	 */
	private void closeBankCashAccount(MBankAccount sourceBankAccount,  MBankAccount targetBankAccount) {
		if(Optional.ofNullable(getIsManageClosing()).orElse("N").equals("N")
				|| !isReconvertCloseBankAccounts()) {
			return;
		}
		AtomicReference<Integer> lineNo = new AtomicReference<>(10);
		String whereClause = MPayment.COLUMNNAME_C_BankAccount_ID + " = ? AND DocStatus IN (?,?) AND IsReconciled = 'N'";
		List<MPayment> unReconciledPayments = new Query(getCtx(), MPayment.Table_Name, whereClause, get_TrxName())
				.setParameters(sourceBankAccount.get_ID(),
								MPayment.DOCSTATUS_Completed,
								MPayment.DOCSTATUS_Closed)
				.<MPayment>list();

		MBankStatement closeBankStatement = new MBankStatement(sourceBankAccount);
		closeBankStatement.setStatementDate(getDateTrx());
		MBankStatement openBankStatement = new MBankStatement(targetBankAccount);
		openBankStatement.setStatementDate(getDateTrx());
		
		String closeDescription = Msg.parseTranslation(getCtx(), "@closed@ @C_BankAccount_ID@ :" .concat(sourceBankAccount.getAccountNo()
				.concat(" @C_Currency_ID@ : ".concat(MCurrency.getISO_Code(getCtx(), sourceBankAccount.getC_Currency_ID())))));
		String openDescription = Msg.parseTranslation(getCtx(), "@Open@ @C_BankAccount_ID@ :" .concat(targetBankAccount.getAccountNo()
				.concat(" @C_Currency_ID@ : ".concat(MCurrency.getISO_Code(getCtx(), targetBankAccount.getC_Currency_ID())))));
		
		MPayment paymentTargetOpen = null;
		
		AtomicReference<BigDecimal> unallocateAmount = new AtomicReference<BigDecimal>(Env.ZERO);
		
		unReconciledPayments.forEach(sourcePayment ->{
			MPayment targetPayment = new MPayment(getCtx(), 0, get_TrxName());
			PO.copyValues(sourcePayment, targetPayment, true);
			targetPayment.setC_BankAccount_ID(targetBankAccount.get_ID());
			targetPayment.setDescription(openDescription);
			targetPayment.setDocStatus(MPayment.DOCSTATUS_Closed);
			targetPayment.setDocAction(MPayment.DOCACTION_None);
			targetPayment.setC_Currency_ID(targetBankAccount.getC_Currency_ID());
			targetPayment.setProcessed(true);
			targetPayment.setPayAmt(targetPayment.getPayAmt().multiply(multiplyRate)
															 .setScale(MCurrency.getStdPrecision(getCtx(), targetBankAccount.getC_Currency_ID()), BigDecimal.ROUND_HALF_UP));
			targetPayment.setPosted(true);
			targetPayment.setIsDirectLoad(true);
			targetPayment.saveEx();
			
			if(closeBankStatement.get_ID()==0)
				closeBankStatement.saveEx();
			
			MBankStatementLine bankStatementLine = new MBankStatementLine(closeBankStatement, lineNo.get());
			setPayment(sourcePayment, sourceBankAccount, bankStatementLine);
			bankStatementLine.saveEx();
			unallocateAmount.set(unallocateAmount.get().add(bankStatementLine.getTrxAmt()));
			lineNo.set(lineNo.get() + 10);
		});	
		
		BigDecimal currentBalanace = sourceBankAccount.getCurrentBalance().negate();
		currentBalanace = currentBalanace.add(unallocateAmount.get().negate());
		if (currentBalanace.compareTo(Env.ZERO)!=0) {
			MPayment paymentSourceClose = new MPayment(getCtx(), 0, get_TrxName());
			paymentSourceClose.setC_BankAccount_ID(sourceBankAccount.getC_BankAccount_ID());
			paymentSourceClose.setDocStatus(MPayment.DOCSTATUS_Closed);
			paymentSourceClose.setDocAction(MPayment.DOCACTION_None);
			paymentSourceClose.setC_Currency_ID(sourceBankAccount.getC_Currency_ID());
			paymentSourceClose.setC_BPartner_ID(getBPartnerId());
			paymentSourceClose.setDescription(closeDescription);
			paymentSourceClose.setProcessed(true);
			paymentSourceClose.setPosted(true);
			paymentSourceClose.setPayAmt(currentBalanace.abs());
			if (currentBalanace.compareTo(Env.ZERO) > 0) 
				paymentSourceClose.setC_DocType_ID(true);
			else 
				paymentSourceClose.setC_DocType_ID(false);
			
			paymentSourceClose.setIsDirectLoad(true);
			paymentSourceClose.saveEx();
			
			if(closeBankStatement.get_ID()==0)
				closeBankStatement.saveEx();
			MBankStatementLine bankStatementLine = new MBankStatementLine(closeBankStatement, lineNo.get());
			setPayment(paymentSourceClose, sourceBankAccount, bankStatementLine);
			bankStatementLine.saveEx();
			
			//Open Bank Statement
			currentBalanace = sourceBankAccount.getCurrentBalance();
			paymentTargetOpen = new MPayment(getCtx(), 0, get_TrxName());
			PO.copyValues(paymentSourceClose, paymentSourceClose, true);
			paymentTargetOpen.setC_BankAccount_ID(targetBankAccount.get_ID());
			paymentTargetOpen.setC_Currency_ID(targetBankAccount.getC_Currency_ID());
			paymentTargetOpen.setC_BPartner_ID(getBPartnerId());
			paymentTargetOpen.setPayAmt(currentBalanace.abs());
			if (currentBalanace.compareTo(Env.ZERO) > 0) 
				paymentTargetOpen.setC_DocType_ID(true);
			else 
				paymentTargetOpen.setC_DocType_ID(false);
			
			paymentTargetOpen.setPayAmt(currentBalanace.abs().multiply(multiplyRate)
					 		 .setScale(MCurrency.getStdPrecision(getCtx(), targetBankAccount.getC_Currency_ID()),BigDecimal.ROUND_HALF_UP));
			
			paymentTargetOpen.setDescription(openDescription);
			paymentTargetOpen.setIsDirectLoad(true);
			paymentTargetOpen.saveEx();
			
			openBankStatement.saveEx();
			MBankStatementLine openBankStatementLine = new MBankStatementLine(openBankStatement, 10);
			setPayment(paymentTargetOpen, targetBankAccount, openBankStatementLine);
			openBankStatementLine.saveEx();
			if (openBankStatement.get_ID() > 0) {
				openBankStatement.setName(openDescription);
				openBankStatement.setDocStatus(MBankStatement.DOCSTATUS_Closed);
				openBankStatement.setDocAction(MBankStatement.DOCACTION_None);
				openBankStatement.setProcessed(true);
				openBankStatement.setPosted(true);
				openBankStatement.saveEx();
				DB.executeUpdate("UPDATE C_BankStatement SET EndingBalance = COALESCE(BeginningBalance,0) + COALESCE(StatementDifference,0) WHERE C_BankStatement_ID = ? ", openBankStatement.get_ID(), get_TrxName());
				DB.executeUpdate("UPDATE C_BankAccount SET CurrentBalance = COALESCE(bs.EndingBalance,0) FROM C_BankStatement bs WHERE bs.C_BankAccount_ID = C_BankAccount.C_BankAccount_ID AND bs.C_BankStatement_ID = ? ", openBankStatement.get_ID(), get_TrxName());
				
			}
			
		}
		
		if (closeBankStatement.get_ID() > 0) {
			closeBankStatement.setName(closeDescription);
			closeBankStatement.setDocStatus(MBankStatement.DOCSTATUS_Closed);
			closeBankStatement.setDocAction(MBankStatement.DOCACTION_None);
			closeBankStatement.setProcessed(true);
			closeBankStatement.setPosted(true);
			closeBankStatement.saveEx();
			DB.executeUpdate("UPDATE C_BankStatement SET EndingBalance = COALESCE(BeginningBalance,0) + COALESCE(StatementDifference,0) WHERE C_BankStatement_ID = ? ", closeBankStatement.get_ID(), get_TrxName());
			DB.executeUpdate("UPDATE C_BankAccount SET CurrentBalance = COALESCE(bs.EndingBalance,0) FROM C_BankStatement bs WHERE bs.C_BankAccount_ID = C_BankAccount.C_BankAccount_ID AND bs.C_BankStatement_ID = ? ", closeBankStatement.get_ID(), get_TrxName());
		}
		
		DB.executeUpdate("UPDATE C_Payment SET DocStatus = '".concat(MPayment.DOCSTATUS_Closed).concat("', ")
									.concat("DocAction = '").concat(MPayment.DOCACTION_None).concat("' ")
									.concat("WHERE C_BankAccount_ID = ? AND ")
									.concat("DocStatus ='".concat(MPayment.STATUS_Completed).concat("'"))
						, sourceBankAccount.get_ID(), get_TrxName());
		
		DB.executeUpdate("UPDATE C_BankStatement SET DocStatus = '".concat(MBankStatement.DOCSTATUS_Closed).concat("', ")
				.concat("DocAction = '").concat(MBankStatement.DOCACTION_None).concat("' ")
				.concat("WHERE C_BankAccount_ID = ? AND ")
				.concat("DocStatus ='".concat(MBankStatement.STATUS_Completed).concat("'"))
						, sourceBankAccount.get_ID(), get_TrxName());
		
	}
	
	private void setPayment (MPayment payment, MBankAccount bankAccount, MBankStatementLine bankStatementLine) {
		AtomicReference<BigDecimal> paymentAmount = new AtomicReference<BigDecimal>(payment.getPayAmt(true));
        //	
        if(bankAccount.getC_Currency_ID() != payment.getC_Currency_ID()) {
            // Get Currency Info
            MCurrency currency = MCurrency.get(getCtx(), payment.getC_Currency_ID());
            MCurrency currencyTo = MCurrency.get (getCtx(), bankAccount.getC_Currency_ID());
            Timestamp conversionDate = payment.getDateAcct();
            StringBuffer errorMassage = new StringBuffer()
    		.append("@C_Payment_ID@ = " + payment.getDocumentNo())
            .append(" @C_Conversion_Rate_ID@ @From@ @C_Currency_ID@ ")
            .append(currency.getISO_Code())
            .append(" @to@ @C_Currency_ID@ ")
            .append(currencyTo.getISO_Code())
            .append(" @StatementDate@ ").append(DisplayType.getDateFormat(DisplayType.Date).format(conversionDate)).append(" @NotFound@");
            // Get Currency Rate
            BigDecimal currencyRate = Optional.ofNullable(MConversionRate.getRate (payment.getC_Currency_ID(),
                    bankAccount.getC_Currency_ID(), conversionDate, payment.getC_ConversionType_ID(), payment.getAD_Client_ID(),
                    payment.getAD_Org_ID()))
            		.orElseThrow(() -> new AdempiereException(errorMassage.toString()));
            //	Set convert amount
			paymentAmount.updateAndGet(payAmount -> payAmount.multiply(currencyRate).setScale(currencyTo.getStdPrecision(), BigDecimal.ROUND_HALF_UP));
        }
        bankStatementLine.setC_Payment_ID (payment.getC_Payment_ID());
        bankStatementLine.setC_Currency_ID (bankAccount.getC_Currency_ID());

        BigDecimal chargeAmt = bankStatementLine.getChargeAmt();
        if (chargeAmt == null)
            chargeAmt = Env.ZERO;
        BigDecimal interestAmt = bankStatementLine.getInterestAmt();
        if (interestAmt == null)
            interestAmt = Env.ZERO;
        bankStatementLine.setTrxAmt(paymentAmount.get());
        bankStatementLine.setStmtAmt(paymentAmount.get().add(chargeAmt).add(interestAmt));
        //
        bankStatementLine.setDescription(payment.getDescription());
	}	//	setPayment
	
	/**
	 * Copy record access entity
	 * @param currentEntityId
	 * @param newEntityId
	 */
	private void copyRecordAccess(int currentEntityId, int newEntityId, int tableId) {
		//	For Role
		new Query(getCtx(), I_AD_Record_Access.Table_Name, I_AD_Record_Access.COLUMNNAME_Record_ID + " = ? AND " + I_AD_Record_Access.COLUMNNAME_AD_Table_ID + " = ?", get_TrxName())
				.setParameters(currentEntityId, tableId)
				.<MRecordAccess>list()
				.forEach(currentRecordAccess -> {
					MRecordAccess documentAccionAccess = new Query(getCtx(), I_AD_Record_Access.Table_Name, 
							"AD_Record_Access.AD_Table_ID = ? AND AD_Record_Access.Record_ID = ? ", get_TrxName())
							.setParameters(tableId, newEntityId)
							.first();
					if(documentAccionAccess == null
							|| documentAccionAccess.getAD_Table_ID() <= 0) {
						documentAccionAccess = new MRecordAccess(getCtx(), currentRecordAccess.getAD_Role_ID(), tableId, newEntityId, get_TrxName());
						documentAccionAccess.setIsActive(currentRecordAccess.isActive());
						documentAccionAccess.setIsDependentEntities(currentRecordAccess.isDependentEntities());
						documentAccionAccess.setIsReadOnly(currentRecordAccess.isReadOnly());
						documentAccionAccess.setIsExclude(currentRecordAccess.isExclude());
				    	documentAccionAccess.saveEx();
					}
		});
	}
}
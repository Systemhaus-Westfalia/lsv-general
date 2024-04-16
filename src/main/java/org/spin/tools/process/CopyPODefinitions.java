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

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.core.domains.models.I_AD_Org;
import org.adempiere.core.domains.models.I_C_BPartner;
import org.adempiere.core.domains.models.I_C_ConversionType;
import org.adempiere.core.domains.models.I_M_DiscountSchema;
import org.adempiere.core.domains.models.I_M_DiscountSchemaBreak;
import org.adempiere.core.domains.models.I_M_DiscountSchemaLine;
import org.adempiere.core.domains.models.I_M_PriceList;
import org.adempiere.core.domains.models.I_M_Product;
import org.adempiere.core.domains.models.I_M_Product_Category;
import org.adempiere.core.domains.models.I_M_Product_Class;
import org.adempiere.core.domains.models.I_M_Product_Classification;
import org.adempiere.core.domains.models.I_M_Product_Group;
import org.compiere.model.MConversionType;
import org.compiere.model.MDiscountSchema;
import org.compiere.model.MDiscountSchemaBreak;
import org.compiere.model.MDiscountSchemaLine;
import org.compiere.model.MPriceList;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.Query;
import org.spin.tools.util.CopyContextUtil;
import org.spin.tools.util.CopyUtil;

/** Generated Process for (Copy PO Definitions)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.3
 */
public class CopyPODefinitions extends CopyPODefinitionsAbstract {
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
			//	Copy Discount Schema
			copy.getTemplateReferencesOnlyNewIds(I_M_DiscountSchema.Table_Name).forEach(currentDiscountSchematId -> {
				MDiscountSchema currentDiscountSchema = new MDiscountSchema(getCtx(), currentDiscountSchematId, get_TrxName()); 
				MDiscountSchema newDiscountSchema = new MDiscountSchema(getCtx(), 0, get_TrxName());
				copy.copyCurrentValues(currentDiscountSchema, newDiscountSchema);
				int newOrganizationId = copy.getReferenceId(I_AD_Org.Table_Name, currentDiscountSchema.getAD_Org_ID());
				if(newOrganizationId < 0) {
					newOrganizationId = 0;
				}
				newDiscountSchema.setAD_Org_ID(newOrganizationId);
				newDiscountSchema.saveEx();
				copy.saveReference(currentDiscountSchema, newDiscountSchema);
				//	For Line
				new Query(getCtx(), I_M_DiscountSchemaLine.Table_Name, "M_DiscountSchema_ID = ?", get_TrxName())
				.setParameters(currentDiscountSchema.getM_DiscountSchema_ID())
				.getIDsAsList().forEach(currentDiscountSchemaLineId -> {
					MDiscountSchemaLine currentDiscountSchemaLine = new MDiscountSchemaLine(getCtx(), currentDiscountSchemaLineId, get_TrxName()); 
					MDiscountSchemaLine newDiscountSchemaLine = new MDiscountSchemaLine(getCtx(), 0, get_TrxName());
					copy.copyCurrentValues(currentDiscountSchemaLine, newDiscountSchemaLine);
					newDiscountSchemaLine.setAD_Org_ID(0);
					newDiscountSchemaLine.setM_DiscountSchema_ID(newDiscountSchema.getM_DiscountSchema_ID());
					int newReferenceId = copy.getReferenceId(I_M_Product.Table_Name, currentDiscountSchemaLine.getM_Product_ID());
					newDiscountSchemaLine.setM_Product_ID(newReferenceId);
					newReferenceId = copy.getReferenceId(I_M_Product_Category.Table_Name, currentDiscountSchemaLine.getM_Product_Category_ID());
					newDiscountSchemaLine.setM_Product_Category_ID(newReferenceId);
					newReferenceId = copy.getReferenceId(I_M_Product_Group.Table_Name, currentDiscountSchemaLine.getM_Product_Group_ID());
					newDiscountSchemaLine.setM_Product_Group_ID(newReferenceId);
					newReferenceId = copy.getReferenceId(I_M_Product_Class.Table_Name, currentDiscountSchemaLine.getM_Product_Class_ID());
					newDiscountSchemaLine.setM_Product_Class_ID(newReferenceId);
					newReferenceId = copy.getReferenceId(I_M_Product_Classification.Table_Name, currentDiscountSchemaLine.getM_Product_Classification_ID());
					newDiscountSchemaLine.setM_Product_Classification_ID(newReferenceId);
					newReferenceId = copy.getReferenceId(I_C_BPartner.Table_Name, currentDiscountSchemaLine.getC_BPartner_ID());
					newDiscountSchemaLine.setC_BPartner_ID(newReferenceId);
					newReferenceId = copy.getReferenceId(I_C_ConversionType.Table_Name, currentDiscountSchemaLine.getC_ConversionType_ID());
					if(newReferenceId <= 0) {
						MConversionType currentConversionType = new MConversionType(getCtx(), currentDiscountSchemaLine.getC_ConversionType_ID(), get_TrxName());
						MConversionType newConversionType = new MConversionType(getCtx(), 0, get_TrxName());
						copy.copyCurrentValues(currentConversionType, newConversionType);
						newConversionType.setAD_Org_ID(0);
						newConversionType.saveEx();
						copy.saveReference(currentConversionType, newConversionType);
						newReferenceId = newConversionType.getC_ConversionType_ID();
					}
					newDiscountSchemaLine.setC_ConversionType_ID(newReferenceId);
					newDiscountSchemaLine.saveEx();
					copy.saveReference(currentDiscountSchemaLine, newDiscountSchemaLine);
				});
				//	For Discount Schema Break
				new Query(getCtx(), I_M_DiscountSchemaBreak.Table_Name, "M_DiscountSchema_ID = ?", get_TrxName())
				.setParameters(currentDiscountSchema.getM_DiscountSchema_ID())
				.getIDsAsList().forEach(currentDiscountSchemaBreakId -> {
					MDiscountSchemaBreak currentDiscountSchemaBreak = new MDiscountSchemaBreak(getCtx(), currentDiscountSchemaBreakId, get_TrxName()); 
					MDiscountSchemaBreak newDiscountSchemaBreak = new MDiscountSchemaBreak(getCtx(), 0, get_TrxName());
					copy.copyCurrentValues(currentDiscountSchemaBreak, newDiscountSchemaBreak);
					newDiscountSchemaBreak.setAD_Org_ID(0);
					newDiscountSchemaBreak.setM_DiscountSchema_ID(newDiscountSchema.getM_DiscountSchema_ID());
					//	Validate Product and category
					int newProductCategoryId = copy.getReferenceId(I_M_Product_Category.Table_Name, currentDiscountSchemaBreak.getM_Product_Category_ID());
					newDiscountSchemaBreak.setM_Product_Category_ID(newProductCategoryId);
					int newProductId = copy.getReferenceId(I_M_Product.Table_Name, currentDiscountSchemaBreak.getM_Product_ID());
					newDiscountSchemaBreak.setM_Product_ID(newProductId);
					newDiscountSchemaBreak.saveEx();
					copy.saveReference(currentDiscountSchemaBreak, newDiscountSchemaBreak);
				});
			});
			//	Price List
			copy.getTemplateReferencesOnlyNewIds(I_M_PriceList.Table_Name).forEach(currentPriceListId -> {
				MPriceList currentPriceList = new MPriceList(getCtx(), currentPriceListId, get_TrxName()); 
				MPriceList newPriceList = new MPriceList(getCtx(), 0, get_TrxName());
				copy.copyCurrentValues(currentPriceList, newPriceList);
				int newOrganizationId = copy.getReferenceId(I_AD_Org.Table_Name, currentPriceList.getAD_Org_ID());
				if(newOrganizationId < 0) {
					newOrganizationId = 0;
				}
				newPriceList.setAD_Org_ID(newOrganizationId);
				newPriceList.saveEx();
				copy.saveReference(currentPriceList, newPriceList);
				copy.copyTranslation(currentPriceList, newPriceList);
				MPriceListVersion currentVersion = currentPriceList.getPriceListVersion(new Timestamp(System.currentTimeMillis()));
				if(currentVersion != null && currentVersion.getM_DiscountSchema_ID() > 0) {
					MPriceListVersion newVersion = new MPriceListVersion(newPriceList);
					newVersion.setName(newPriceList.getName());
					int newReferenceId = copy.getReferenceId(I_M_DiscountSchema.Table_Name, currentVersion.getM_DiscountSchema_ID());
					newVersion.setM_DiscountSchema_ID(newReferenceId);
					newVersion.saveEx(get_TrxName());
					copy.saveReference(currentVersion, newVersion);
				}
			});
		} catch (Exception e) {
			throw new AdempiereException(e);
		} finally {
			CopyContextUtil.newInstance().revertContextToCurrentClient(getCtx(), getProcessInfo().isBatch());
		}
		return "Ok";
	}
}
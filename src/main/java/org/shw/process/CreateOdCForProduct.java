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

package org.shw.process;

import org.adempiere.core.domains.models.I_C_Project;
import org.adempiere.core.domains.models.X_C_TaxDefinition;
import org.compiere.model.*;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/** Generated Process for (CreateOdCForProduct)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.0
 */
public class CreateOdCForProduct extends CreateOdCForProductAbstract
{
	@Override
	protected void prepare()
	{
		super.prepare();
	}

	@Override
	protected String doIt() throws Exception
	{

		//	Created
		MRequest request = new MRequest(getCtx(), getRecord_ID(),get_TrxName());
		MProject project = (MProject)request.getC_Project();
		for (int key:getSelectionKeys())
		{
			int bpartnerID 	= getSelectionAsInt(key, "BP_C_BPartner_ID");	//	2-BPartner
			int productID = getSelectionAsInt(key, "PP_M_Product_ID");		//	2-Product
			BigDecimal price 	= getSelectionAsBigDecimal(key, "PP_PriceStd");	//  5- Price;
			int bpLocationID = getSelectionAsInt(key, "BPL_C_BPartner_Location_ID");
			int priceListID = getSelectionAsInt(key, "PL_M_PriceList_ID");
			int paymentTermID = getSelectionAsInt(key, "BP_PO_PaymentTerm_ID");
			MOrder order = getofBPartner(bpartnerID, getDocTypeId(), request.getC_Project_ID());
			if (order == null)
				order = createOrder(request.getC_Project(), bpartnerID,bpLocationID, priceListID,
					paymentTermID, getDocTypeId());
			MOrderLine orderLine  = null;
			if (order != null){
				orderLine = new MOrderLine(order);
				orderLine.setM_Product_ID(productID);
				orderLine.setPrice(price);
				orderLine.setQty(Env.ONE);
				orderLine.set_ValueOfColumn("isSalesOrderImmediate", "Y");
				orderLine.set_ValueOfColumn("Weight", project.get_Value("Weight"));
				orderLine.set_ValueOfColumn("Bultos", request.get_Value("Bultos"));
				orderLine.set_ValueOfColumn("Volume", project.get_Value("Volume"));
				orderLine.set_ValueOfColumn("Pedido", project.get_Value("Pedido"));
				taxDefinition(orderLine);
				orderLine.saveEx();
				order.processIt(MOrder.DOCACTION_Prepare);
				request.setC_Order_ID(order.getC_Order_ID());
				request.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
				request.setM_Product_ID(orderLine.getM_Product_ID());
				request.saveEx();
				requestFinalClose(request);
			}
		}

		//	Return Created
		return  "";
	}

	private MOrder createOrder(I_C_Project i_project, int bpartnerID, int bpLocationID,
							   int priceListID, int paymentTermID, int docTypeID){
		MProject project = (MProject)i_project;
		MOrder order = new MOrder (getCtx(), 0, get_TrxName());
		order.setClientOrg(project.getAD_Client_ID(), project.getAD_Org_ID());
		order.setC_Campaign_ID(project.getC_Campaign_ID());
		order.setSalesRep_ID(project.getSalesRep_ID());
		//
		order.setC_Project_ID(project.getC_Project_ID());
		order.setDescription(project.getName());
		order.setC_BPartner_ID(bpartnerID);
		order.setC_BPartner_Location_ID(bpLocationID);
		order.setAD_User_ID(project.getAD_User_ID());
		order.setUser1_ID(project.get_ValueAsInt("User1_ID"));
		order.setM_Warehouse_ID(project.getM_Warehouse_ID());
		order.setM_PriceList_ID(priceListID);
		order.setC_PaymentTerm_ID(paymentTermID);
		order.setIsSOTrx(false);
		order.setC_DocTypeTarget_ID(docTypeID);
		order.saveEx();
		return order;
	}

	private MOrder getofBPartner(int bpartnerID, int doctypeID, int projectID){
		ArrayList<Object> parameters = new ArrayList();
		parameters.add(bpartnerID);
		parameters.add(doctypeID);
		parameters.add(projectID);
		String whereClause = MOrder.COLUMNNAME_C_BPartner_ID + "=? AND " +
				MOrder.COLUMNNAME_C_DocTypeTarget_ID + "=? AND " +
				MOrder.COLUMNNAME_C_Project_ID + "=? AND " +
				MOrder.COLUMNNAME_Processed + "='N'";
		MOrder order = new Query(getCtx(), MOrder.Table_Name, whereClause,get_TrxName())
				.setParameters(parameters)
				.setOnlyActiveRecords(true)
				.setOrderBy(MOrder.COLUMNNAME_DateOrdered)
				.first();
		return order;
	}

	private void requestFinalClose(MRequest request){
		if (request.getStatus().isClosed())
			return;
		String whereClause = " EXISTS (SELECT * FROM R_RequestType rt " +
				"INNER JOIN R_StatusCategory sc ON (rt.R_StatusCategory_ID=sc.R_StatusCategory_ID)" +
				"WHERE R_Status.R_StatusCategory_ID=sc.R_StatusCategory_ID " +
				"AND rt.R_RequestType_ID=?)";

		List<MStatus> statusList = new Query(getCtx(), MStatus.Table_Name, whereClause, get_TrxName())
				.setParameters(request.getR_RequestType_ID())
				.list();
		for (MStatus statusClosed:statusList)
		{
			if (statusClosed.isFinalClose()){
				request.setR_Status_ID(statusClosed.getR_Status_ID());
				request.saveEx();
				break;
			}
		}
	}

	public void taxDefinition (MOrderLine orderLine)
	{
		int M_Product_ID = orderLine.getM_Product_ID();

		int C_Charge_ID = orderLine.getC_Charge_ID();

		ArrayList<Object> params = new ArrayList();
		//	Check Partner Location
		String whereClause = "(c_taxgroup_ID =? or c_taxgroup_ID is null)";
		MBPartner bpartner = (MBPartner)orderLine.getParent().getC_BPartner();
		params.add(bpartner.getC_TaxGroup_ID());
		if (C_Charge_ID != 0)
		{
			whereClause = whereClause + " AND (c_taxcategory_ID =? or c_taxcategory_ID is null)";
			params.add(orderLine.getC_Charge().getC_TaxCategory_ID());
		}
		else if (M_Product_ID != 0)
		{
			whereClause = whereClause + " AND (c_taxcategory_ID =? or c_taxcategory_ID is null)";
			params.add(orderLine.getM_Product().getC_TaxCategory_ID());
		}
		X_C_TaxDefinition taxdefinition = new Query(Env.getCtx(), X_C_TaxDefinition.Table_Name, whereClause, null)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setParameters(params)
				.setOrderBy("seqNo")
				.first();
		orderLine.setC_Tax_ID(taxdefinition.getC_Tax_ID());
	}

}
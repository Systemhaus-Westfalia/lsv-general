/******************************************************************************
 * Product: ADempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2006-2017 ADempiere Foundation, All Rights Reserved.         *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * or (at your option) any later version.                                     *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * or via info@adempiere.net                                                  *
 * or https://github.com/adempiere/adempiere/blob/develop/license.html        *
 *****************************************************************************/

package org.shw.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.adempiere.core.domains.models.I_GL_DistributionLine;
import org.adempiere.core.domains.models.X_C_Project;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MDistribution;
import org.compiere.model.MDistributionLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MPeriod;
import org.compiere.model.MProject;
import org.compiere.model.MUOMConversion;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;

/** Generated Process for (UpdateProjectCost)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.4
 */
public class UpdateProjectCost extends UpdateProjectCostAbstract
{
	int projectID=0;
	MProject projectInicial = null;
	@Override
	protected void prepare()
	{
		super.prepare();
		projectID= 0;
		
	}

	@Override
	protected String doIt() throws Exception
	{

		if (getProjectId()>0) {
			projectID = getProjectId();
			projectInicial = new MProject(getCtx(), projectID, get_TrxName());
			updateProjectPerformanceCalculationSons( projectID);
			return "";
		}
		if (getRecord_ID()>0) {
			projectID = getRecord_ID();
			projectInicial = new MProject(getCtx(), projectID, get_TrxName());
			updateProjectPerformanceCalculationSons( projectID);
			return "";
		}
		getSelectionKeys().stream().forEach( key -> {
			projectID=key;
			projectInicial = new MProject(getCtx(), projectID, get_TrxName());
			updateProjectPerformanceCalculationSons( projectID);
		});   //  for all rows
		return "";
	}
	
	private String updateProjectPerformanceCalculationSons(int projectID) {	

		
    	String whereClause = "C_Project_Parent_ID=?";
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(projectID);

		List<MProject> childrenProjects = new Query(getCtx(), X_C_Project.Table_Name, whereClause, get_TrxName())
		.setParameters(params)
		.list(MProject.class);

		MProject project = new MProject(getCtx(), projectID, get_TrxName());
		if (childrenProjects == null) {	
			// No children -> just update this project
			updateProjectPerformanceCalculation(project);
			return"";	
		}
		
		for (MProject childProject:childrenProjects) {
			// update all children of this child
			updateProjectPerformanceCalculationSons(childProject.getC_Project_ID(), 6);
		}
		// last but not least, update this project
		updateProjectPerformanceCalculation(project);
		return"";
    }
	
	/**
	 * 	Update Costs and Revenues 
	 * 1.- For a given Project 
	 * 2.- For the children of the project
	 * 3.- For the parent project, if any
	 *	@return message
	 */	
	public String updateProjectPerformanceCalculation(MProject project) {
		BigDecimal result = Env.ZERO;
		
		// Update Prices
		result = calcLineNetAmt();
		
		project.set_ValueOfColumn("ProjectPriceListRevenuePlanned", result.setScale(2, RoundingMode.HALF_UP));
		result = calcActualamt();
		project.set_ValueOfColumn("ProjectOfferedRevenuePlanned", result.setScale(2, RoundingMode.HALF_UP));
		
		// Update costs
		// Parameters:                     Project         isSOtrx  isParentProject
		result = calcCostOrRevenuePlanned(projectID, false, false);      // Planned costs from Purchase Orders this project
		project.set_ValueOfColumn("CostPlanned", result.setScale(2, RoundingMode.HALF_UP));
		result = calcCostOrRevenueActual(projectID, false, false); 		 // Actual costs from Purchase Invoices
		project.set_ValueOfColumn("CostAmt", result.setScale(2, RoundingMode.HALF_UP));
		result = calcNotInvoicedCostOrRevenue(projectID, false, false);  // Planned but not yet invoiced costs
		project.set_ValueOfColumn("CostNotInvoiced", result.setScale(2, RoundingMode.HALF_UP));
		BigDecimal costExtrapolated = calcCostOrRevenueExtrapolated(projectID, false, false); // Actual costs + Planned but not yet invoiced costs
		project.set_ValueOfColumn("CostExtrapolated", costExtrapolated.setScale(2, RoundingMode.HALF_UP));

		// Update revenues
		result = calcCostOrRevenuePlanned(projectID, true, false);     // Planned revenue from Sales Orders this project
		project.set_ValueOfColumn("RevenuePlanned", result.setScale(2, RoundingMode.HALF_UP));
		result = calcCostOrRevenueActual(projectID, true, false); 	   // Actual revenue from Sales Invoices
		project.set_ValueOfColumn("RevenueAmt", result.setScale(2, RoundingMode.HALF_UP));
		result = calcNotInvoicedCostOrRevenue(projectID, true, false); // Planned but not yet invoiced revenue
		project.set_ValueOfColumn("RevenueNotInvoiced", result.setScale(2, RoundingMode.HALF_UP));
		BigDecimal revenueExtrapolated = calcCostOrRevenueExtrapolated(projectID, true, false);  // Actual revenue + Planned but not yet invoiced revenue
		project.set_ValueOfColumn("RevenueExtrapolated", revenueExtrapolated.setScale(2, RoundingMode.HALF_UP));
		
		// Update Issue Costs
		BigDecimal costIssueProduct = calcCostIssueProduct(projectID, false);		// Costs of Product Issues
		project.set_ValueOfColumn("CostIssueProduct", costIssueProduct.setScale(2, RoundingMode.HALF_UP));
		BigDecimal costIssueResource = calcCostIssueResource(projectID, false);		// Costs of Resource Issues
		project.set_ValueOfColumn("CostIssueResource", costIssueResource.setScale(2, RoundingMode.HALF_UP));
		BigDecimal costIssueInventory = calcCostIssueInventory(projectID, false);   // Costs of Inventory Issues
		project.set_ValueOfColumn("CostIssueInventory", costIssueInventory.setScale(2, RoundingMode.HALF_UP));
		project.set_ValueOfColumn("CostIssueSum", costIssueProduct.add(costIssueResource).
				add(costIssueInventory).setScale(2, RoundingMode.HALF_UP));  // Issue sum = Costs of Product Issue + Costs of Resource Issue + Costs of Inventory Issues
		project.set_ValueOfColumn("CostDiffExcecution", ((BigDecimal)project.get_Value("CostPlanned")).
				subtract(costIssueProduct).
				subtract(costIssueInventory).setScale(2, RoundingMode.HALF_UP));  // Execution Diff = Planned Costs - (Product Issue Costs + Inventory Issue Costs

		// Gross Margin
		// Gross margin = extrapolated revenue - (extrapolated costs + resource issue costs + inventory issue costs)
		BigDecimal sumCosts = costExtrapolated.add(costIssueResource).add(costIssueInventory);
		
		BigDecimal grossMargin = revenueExtrapolated.subtract(sumCosts);
		project.set_ValueOfColumn("GrossMargin",grossMargin.setScale(2, RoundingMode.HALF_UP));		

		// Margin (%) only for this level; there is no use to calculate it on LL
		if(sumCosts.compareTo(Env.ZERO)==0 && revenueExtrapolated.compareTo(Env.ZERO)==0) {
			project.set_ValueOfColumn("Margin", Env.ZERO); // Costs==0, Revenue== -> 0% margin	
			}
		else if(sumCosts.compareTo(Env.ZERO)!=0) {
			if(revenueExtrapolated.compareTo(Env.ZERO)!=0) {
				project.set_ValueOfColumn("Margin", revenueExtrapolated.divide(sumCosts, 6, RoundingMode.HALF_UP).subtract(Env.ONE).
						multiply(Env.ONEHUNDRED).setScale(2, RoundingMode.HALF_UP));
			}
			else {
				project.set_ValueOfColumn("Margin", Env.ONEHUNDRED.negate()); // Revenue==0 -> -100% margin						
			}

		} 
		else {
			project.set_ValueOfColumn("Margin", Env.ONEHUNDRED); // Costs==0 -> 100% margin			
		}
		
		BigDecimal grossMarginLL = Env.ZERO; // Gross Margin of children

		if (project.isSummary()) { // Project is a parent project
			// Update costs of direct children (not recursively all children!)
			BigDecimal costPlannedLL = calcCostOrRevenuePlannedSons(project.getC_Project_ID(), false, true);	       // Planned costs from Purchase Orders of children
			project.set_ValueOfColumn("CostPlannedLL", costPlannedLL.setScale(2, RoundingMode.HALF_UP));
			BigDecimal costAmtLL = calcCostOrRevenueActualSons(project.getC_Project_ID(), false, true);		           // Actual costs from Purchase Invoices of children
			project.set_ValueOfColumn("CostAmtLL", costAmtLL.setScale(2, RoundingMode.HALF_UP));
			BigDecimal costNotInvoicedLL = calcNotInvoicedCostOrRevenueSons(project.getC_Project_ID(), false, true);   // Planned but not yet invoiced costs of children
			project.set_ValueOfColumn("CostNotInvoicedLL", costNotInvoicedLL.setScale(2, RoundingMode.HALF_UP));	
			BigDecimal costExtrapolatedLL = calcCostOrRevenueExtrapolatedSons(project.getC_Project_ID(), false, true); // Actual costs + Planned but not yet invoiced costs of children
			project.set_ValueOfColumn("CostExtrapolatedLL", costExtrapolatedLL.setScale(2, RoundingMode.HALF_UP));			
			
			// update revenues of children
			BigDecimal revenuePlannedLL = calcCostOrRevenuePlannedSons(project.getC_Project_ID(), true, true);	         // Planned revenue from Sales Orders of children
			project.set_ValueOfColumn("RevenuePlannedLL", revenuePlannedLL.setScale(2, RoundingMode.HALF_UP));
			BigDecimal revenueAmtLL = calcCostOrRevenueActualSons(project.getC_Project_ID(), true, true);		         // Actual revenue from Sales Invoices of children
			project.set_ValueOfColumn("RevenueAmtLL", revenueAmtLL.setScale(2, RoundingMode.HALF_UP));
			BigDecimal revenueNotInvoicedLL = calcNotInvoicedCostOrRevenueSons(project.getC_Project_ID(), true, true);   // Planned but not yet invoiced revenue of children
			project.set_ValueOfColumn("RevenueNotInvoicedLL", revenueNotInvoicedLL.setScale(2, RoundingMode.HALF_UP));	
			BigDecimal revenueExtrapolatedLL = calcCostOrRevenueExtrapolatedSons(project.getC_Project_ID(), true, true); // Actual revenue + Planned but not yet invoiced revenue of children
			project.set_ValueOfColumn("RevenueExtrapolatedLL", revenueExtrapolatedLL.setScale(2, RoundingMode.HALF_UP));				
			
			// Update Issue Costs of children
			BigDecimal costIssueProductLL = calcCostIssueProductSons(project.getC_Project_ID(), true);      // Costs of Product Issues of children
			project.set_ValueOfColumn("CostIssueProductLL", costIssueProductLL.setScale(2, RoundingMode.HALF_UP));
			BigDecimal costIssueResourceLL = calcCostIssueResourceSons(project.getC_Project_ID(), true);    // Costs of Resource Issues of children
			project.set_ValueOfColumn("CostIssueResourceLL", costIssueResourceLL.setScale(2, RoundingMode.HALF_UP));
			BigDecimal costIssueInventoryLL = calcCostIssueInventorySons(project.getC_Project_ID(), true);  // Costs of Inventory Issues of children
			project.set_ValueOfColumn("CostIssueInventoryLL", costIssueInventoryLL.setScale(2, RoundingMode.HALF_UP));
			BigDecimal costIssueSumLL  = costIssueProductLL.  // Issue sum LL = Costs of Product Issue LL + Costs of Resource Issue LL+ Costs of Inventory Issue LL
					add(costIssueResourceLL).add(costIssueInventoryLL).setScale(2, RoundingMode.HALF_UP); 
			project.set_ValueOfColumn("CostIssueSumLL", costIssueSumLL.setScale(2, RoundingMode.HALF_UP));
			BigDecimal costDiffExcecutionLL  = costPlannedLL. // Execution Diff LL = Planned Costs LL - (Product Issue Costs LL + Inventory Issue Costs LL)
					subtract(costIssueProductLL).subtract(costIssueInventoryLL).setScale(2, RoundingMode.HALF_UP);
			project.set_ValueOfColumn("CostDiffExcecutionLL", costDiffExcecutionLL.setScale(2, RoundingMode.HALF_UP));

			// Gross margin LL = extrapolated revenue LL - (extrapolated costs LL + resource issue costs LL + inventory issue costs LL)
			grossMarginLL = revenueExtrapolatedLL.subtract(costExtrapolatedLL).
					subtract(costIssueResourceLL).subtract(costIssueInventoryLL);
			if(grossMarginLL==null)
				grossMarginLL = Env.ZERO;
			project.set_ValueOfColumn("GrossMarginLL",grossMarginLL.setScale(2, RoundingMode.HALF_UP));

			project.saveEx();  // TODO: delete line

	    	BigDecimal costActualFather       = (BigDecimal)project.get_Value("CostAmt");
	    	BigDecimal costPlannedFather      = (BigDecimal)project.get_Value("CostPlanned");
	    	BigDecimal costExtrapolatedFather = (BigDecimal)project.get_Value("CostExtrapolated");	    	
	    	
	    	// BigDecimal revenuePlannedSons = (BigDecimal)get_Value("RevenuePlannedLL");
	    	BigDecimal revenueExtrapolatedSons =  (BigDecimal)project.get_Value("RevenueExtrapolatedLL");
	    	
	    	BigDecimal weightFather = (BigDecimal)project.get_Value("Weight");
	    	BigDecimal volumeFather = (BigDecimal)project.get_Value("Volume");	    	
	    	
			List<MProject> projectsOfFather = new Query(getCtx(), MProject.Table_Name, "C_Project_Parent_ID=?", get_TrxName())
			.setParameters(project.getC_Project_ID())
			.list();
			for (MProject sonProject: projectsOfFather)	{
				//BigDecimal revenuePlannedSon = (BigDecimal)sonProject.get_Value("RevenuePlanned");
				BigDecimal revenueExtrapolatedSon = (BigDecimal)sonProject.get_Value("RevenueExtrapolated");
				BigDecimal weight = (BigDecimal)sonProject.get_Value("Weight");
				BigDecimal volume = (BigDecimal)sonProject.get_Value("volume");
				BigDecimal shareRevenue = Env.ZERO;
				BigDecimal shareWeight = Env.ZERO;
				BigDecimal shareVolume = Env.ZERO;
				if (revenueExtrapolatedSon!=null && revenueExtrapolatedSons!=null && revenueExtrapolatedSons.longValue()!= 0)
					shareRevenue = revenueExtrapolatedSon.divide(revenueExtrapolatedSons, 5, RoundingMode.HALF_UP);
				if (weight!=null && weightFather != null && weightFather.longValue()!=0)
					shareWeight = weight.divide(weightFather, 5, RoundingMode.HALF_UP);
				if (volume!=null && volumeFather != null && volumeFather.longValue() != 0)
					shareVolume = volume.divide(volumeFather, 5, RoundingMode.HALF_UP);
				calcCostPlannedInherited(sonProject, costPlannedFather,costActualFather,costExtrapolatedFather, shareVolume, shareWeight, shareRevenue);
				
				// Collect Low Level Amounts
				costPlannedLL         = costPlannedLL.add((BigDecimal)sonProject.get_Value("CostPlannedLL")==null                ?Env.ZERO:(BigDecimal)sonProject.get_Value("CostPlannedLL"));
				costAmtLL             = costAmtLL.add((BigDecimal)sonProject.get_Value("CostAmtLL")==null                        ?Env.ZERO:(BigDecimal)sonProject.get_Value("CostAmtLL"));
				costNotInvoicedLL     = costNotInvoicedLL.add((BigDecimal)sonProject.get_Value("CostNotInvoicedLL")==null        ?Env.ZERO:(BigDecimal)sonProject.get_Value("CostNotInvoicedLL"));
				costExtrapolatedLL    = costExtrapolatedLL.add((BigDecimal)sonProject.get_Value("CostExtrapolatedLL")==null      ?Env.ZERO:(BigDecimal)sonProject.get_Value("CostExtrapolatedLL"));
				revenuePlannedLL      = revenuePlannedLL.add((BigDecimal)sonProject.get_Value("RevenuePlannedLL")==null          ?Env.ZERO:(BigDecimal)sonProject.get_Value("RevenuePlannedLL"));
				revenueAmtLL          = revenueAmtLL.add((BigDecimal)sonProject.get_Value("RevenueAmtLL")==null                  ?Env.ZERO:(BigDecimal)sonProject.get_Value("RevenueAmtLL"));
				revenueNotInvoicedLL  = revenueNotInvoicedLL.add((BigDecimal)sonProject.get_Value("RevenueNotInvoicedLL")==null  ?Env.ZERO:(BigDecimal)sonProject.get_Value("RevenueNotInvoicedLL"));
				revenueExtrapolatedLL = revenueExtrapolatedLL.add((BigDecimal)sonProject.get_Value("RevenueExtrapolatedLL")==null?Env.ZERO:(BigDecimal)sonProject.get_Value("RevenueExtrapolatedLL"));
				costIssueProductLL    = costIssueProductLL.add((BigDecimal)sonProject.get_Value("CostIssueProductLL")==null      ?Env.ZERO:(BigDecimal)sonProject.get_Value("CostIssueProductLL"));
				costIssueResourceLL   = costIssueResourceLL.add((BigDecimal)sonProject.get_Value("CostIssueResourceLL")==null    ?Env.ZERO:(BigDecimal)sonProject.get_Value("CostIssueResourceLL"));
				costIssueInventoryLL  = costIssueInventoryLL.add((BigDecimal)sonProject.get_Value("CostIssueInventoryLL")==null  ?Env.ZERO:(BigDecimal)sonProject.get_Value("CostIssueInventoryLL"));
				costIssueSumLL        = costIssueSumLL.add((BigDecimal)sonProject.get_Value("CostIssueSumLL")==null              ?Env.ZERO:(BigDecimal)sonProject.get_Value("CostIssueSumLL"));
				costDiffExcecutionLL  = costDiffExcecutionLL.add((BigDecimal)sonProject.get_Value("CostDiffExcecutionLL")==null  ?Env.ZERO:(BigDecimal)sonProject.get_Value("CostDiffExcecutionLL"));
			}
			// Set Low Level Amounts
			project.set_ValueOfColumn("CostPlannedLL",         costPlannedLL.setScale(2, RoundingMode.HALF_UP));
			project.set_ValueOfColumn("CostAmtLL",             costAmtLL.setScale(2, RoundingMode.HALF_UP));
			project.set_ValueOfColumn("CostNotInvoicedLL",     costNotInvoicedLL.setScale(2, RoundingMode.HALF_UP));
			project.set_ValueOfColumn("CostExtrapolatedLL",    costExtrapolatedLL.setScale(2, RoundingMode.HALF_UP));
			project.set_ValueOfColumn("RevenuePlannedLL",      revenuePlannedLL.setScale(2, RoundingMode.HALF_UP));
			project.set_ValueOfColumn("RevenueAmtLL",          revenueAmtLL.setScale(2, RoundingMode.HALF_UP));
			project.set_ValueOfColumn("RevenueNotInvoicedLL",  revenueNotInvoicedLL.setScale(2, RoundingMode.HALF_UP));
			project.set_ValueOfColumn("RevenueExtrapolatedLL", revenueExtrapolatedLL.setScale(2, RoundingMode.HALF_UP));
			project.set_ValueOfColumn("CostIssueProductLL",    costIssueProductLL.setScale(2, RoundingMode.HALF_UP));
			project.set_ValueOfColumn("CostIssueResourceLL",   costIssueResourceLL.setScale(2, RoundingMode.HALF_UP));
			project.set_ValueOfColumn("CostIssueInventoryLL",  costIssueInventoryLL.setScale(2, RoundingMode.HALF_UP));
			project.set_ValueOfColumn("CostIssueSumLL",        costIssueSumLL.setScale(2, RoundingMode.HALF_UP));
			project.set_ValueOfColumn("CostDiffExcecutionLL",  costDiffExcecutionLL.setScale(2, RoundingMode.HALF_UP));

			// Gross margin LL = extrapolated revenue LL - (extrapolated costs LL + resource issue costs LL + inventory issue costs LL)
			grossMarginLL = revenueExtrapolatedLL.subtract(costExtrapolatedLL).
					subtract(costIssueResourceLL).subtract(costIssueInventoryLL);
			if(grossMarginLL==null)
				grossMarginLL = Env.ZERO;
			project.set_ValueOfColumn("GrossMarginLL",grossMarginLL.setScale(2, RoundingMode.HALF_UP));		

			project.saveEx();  // Low Level Amounts
		}


		int C_Project_Parent_ID = project.get_ValueAsInt("C_Project_Parent_ID");  // Father Project -if any
		if (C_Project_Parent_ID!= 0)	{	
			// Project is child: update direct parent project
	    	MProject fatherProject = new MProject(getCtx(), C_Project_Parent_ID, get_TrxName());
			result = calcCostOrRevenuePlannedSons(C_Project_Parent_ID, false, true);      // Planned costs from Purchase Orders of all children of parent project
			fatherProject.set_ValueOfColumn("CostPlannedLL", result.setScale(2, RoundingMode.HALF_UP));
			result = calcCostOrRevenueActualSons(C_Project_Parent_ID, false, true);       // Actual costs from Purchase Invoices of all children of parent project
			fatherProject.set_ValueOfColumn("CostAmtLL", result.setScale(2, RoundingMode.HALF_UP));
			result = calcCostOrRevenueExtrapolatedSons(C_Project_Parent_ID, false, true); // Sum of actual costs and planned (not yet invoiced) costs of all children of parent project
			fatherProject.set_ValueOfColumn("CostExtrapolatedLL", result.setScale(2, RoundingMode.HALF_UP));		
			
			fatherProject.saveEx();

	    	BigDecimal costActualFather       = (BigDecimal)fatherProject.get_Value("CostAmt");
	    	BigDecimal costPlannedFather      = (BigDecimal)fatherProject.get_Value("CostPlanned");
	    	BigDecimal costExtrapolatedFather = (BigDecimal)fatherProject.get_Value("CostExtrapolated");

	    	BigDecimal revenueAmtSons         = calcCostOrRevenueActualSons(C_Project_Parent_ID, true, true);	
	    	BigDecimal revenuePlannedSons     = calcCostOrRevenuePlannedSons(C_Project_Parent_ID, true, true);
	    	BigDecimal revenueAllExtrapolated = calcCostOrRevenueExtrapolatedSons(C_Project_Parent_ID, true, true);	
	    	
	    	BigDecimal weightFather = (BigDecimal)fatherProject.get_Value("Weight");
	    	BigDecimal volumeFather = (BigDecimal)fatherProject.get_Value("Volume");
	    	
	    	
			List<MProject> projectsOfFather = new Query(getCtx(), MProject.Table_Name, "C_Project_Parent_ID=?", get_TrxName())
			.setParameters(C_Project_Parent_ID)
			.list();
			// Update all children of parent project
			for (MProject sonProject: projectsOfFather)	{
				BigDecimal revenueExtrapolatedSon = 
						(BigDecimal)sonProject.get_Value("RevenueExtrapolated");
				BigDecimal weight = (BigDecimal)sonProject.get_Value("Weight");
				BigDecimal volume = (BigDecimal)sonProject.get_Value("volume");
				if (volume == null)
					volume = Env.ZERO;
				BigDecimal shareRevenue = Env.ZERO;
				BigDecimal shareWeight  = Env.ZERO;
				BigDecimal shareVolume  = Env.ZERO;
				if (revenueExtrapolatedSon!=null && revenueAllExtrapolated.longValue()!= 0)
					shareRevenue = revenueExtrapolatedSon.divide(revenueAllExtrapolated, 5, RoundingMode.HALF_UP);
				if (weight!=null && weightFather != null && weightFather.longValue()!=0)
					shareWeight = weight.divide(weightFather, 5, RoundingMode.HALF_UP);
				if (volume!=null && volumeFather != null && volumeFather.longValue()!= 0)
					shareVolume = volume.divide(volumeFather, 5, RoundingMode.HALF_UP);
				calcCostPlannedInherited(sonProject, costPlannedFather,costActualFather,costExtrapolatedFather, shareVolume, shareWeight, shareRevenue);				
			}
			fatherProject.set_ValueOfColumn("RevenuePlannedLL", revenuePlannedSons.setScale(2, RoundingMode.HALF_UP));
			fatherProject.set_ValueOfColumn("RevenueAmtLL", revenueAmtSons.setScale(2, RoundingMode.HALF_UP));
			fatherProject.set_ValueOfColumn("RevenueExtrapolatedLL", revenueAllExtrapolated.setScale(2, RoundingMode.HALF_UP));
			fatherProject.saveEx();
			project.saveEx();
		}

		BigDecimal grossMarginTotal = ((BigDecimal)project.get_Value("GrossMargin")).add(grossMarginLL);
		if(grossMarginTotal==null)
			grossMarginTotal = Env.ZERO;
		project.set_ValueOfColumn("GrossMarginTotal", grossMarginTotal.setScale(2, RoundingMode.HALF_UP));
		
		Date date = new Date();
		long time = date.getTime();
		Timestamp timestamp = new Timestamp(time);
		project.set_ValueOfColumn("DateLastRun", timestamp);
		project.saveEx();
		
		return "";
	}

	/**
	 * Calculates this Project's Actual Costs or Revenue based on Invoices
	 * @param c_Project_ID Project ID
	 * @param isSOTrx  (boolean) true (Revenue) or false (Cost)
	 * @param isParentProject  (boolean) true (Include all child Projects) or false (Only this Project)
	 *	@return Amount of Cost or Revenue, depending on parameter 
	 */
    private BigDecimal calcCostOrRevenueActual(int c_Project_ID, boolean isSOTrx, boolean isParentProject) {
    	String expresion = "LineNetAmtRealInvoiceLine(c_invoiceline_ID)";
    	StringBuffer whereClause = new StringBuffer();
    	whereClause.append("c_invoice_ID IN (SELECT c_invoice_ID FROM c_invoice WHERE docstatus IN ('CO','CL') ");
    	whereClause.append(" AND issotrx = ");
    	whereClause.append(isSOTrx==true?  " 'Y') " : " 'N') ");

    	if(isParentProject)
    		whereClause.append( "AND c_project_ID IN (SELECT c_project_ID FROM c_project WHERE c_project_parent_ID =?) ");
    	else
    		whereClause.append("AND c_project_ID = ? ");
    	
    	BigDecimal result = Env.ZERO;
    	result = new Query(getCtx(), MInvoiceLine.Table_Name, whereClause.toString(), get_TrxName())
    		.setParameters(c_Project_ID)
    		.aggregate(expresion, Query.AGGREGATE_SUM);
    	return result==null?Env.ZERO:result;
    }

	/**
	 * Calculates this Project's Planned Costs or Revenue based on Orders
	 * Special treatment for closed orders
	 * @param c_Project_ID Project ID
	 * @param isSOTrx  (boolean) true (Revenue) or false (Cost)
	 * @param isParentProject  (boolean) true (Include all child Projects) or false (Only this Project)
	 *	@return Amount of Cost or Revenue, depending on parameter 
	 */
    private BigDecimal calcCostOrRevenuePlanned(int c_Project_ID, boolean isSOTrx, boolean isParentProject) {
    	StringBuffer sql = new StringBuffer();
    	sql.append("SELECT COALESCE (SUM( ");
    	sql.append("CASE ");
    	sql.append("     WHEN pl.istaxincluded = 'Y' ");
    	sql.append("     THEN ");
    	sql.append("         CASE ");
    	sql.append("         WHEN o.docstatus in ('CL') ");
    	sql.append("         THEN ((ol.qtyinvoiced * ol.priceactual)- (ol.qtyinvoiced * ol.priceactual)/(1+(t.rate/100)))  ");
    	sql.append("         ELSE (ol.linenetamt- ol.linenetamt/(1+(t.rate/100))) ");
    	sql.append("         END ");
    	sql.append("     ELSE ");
    	sql.append("         CASE ");
    	sql.append("         WHEN o.docstatus IN ('CL') ");
    	sql.append("         THEN (ol.qtyinvoiced * ol.priceactual) ");
    	sql.append("         ELSE (ol.linenetamt) ");
    	sql.append("         END ");
    	sql.append("     END ");
    	sql.append("),0) ");
    	sql.append("FROM C_OrderLine ol ");
    	sql.append("INNER JOIN c_order o ON ol.c_order_ID = o.c_order_ID ");
    	sql.append("INNER JOIN m_pricelist pl ON o.m_pricelist_ID = pl.m_pricelist_ID ");
    	sql.append("INNER JOIN c_tax t ON ol.c_tax_ID = t.c_tax_ID ");
    	sql.append("WHERE ");
    	sql.append("o.c_order_ID IN  (select c_order_ID from c_order where docstatus in ('CO','CL','IP')   AND issotrx =  ? ) ");

    	if(isParentProject)
    		sql.append( "AND o.c_project_ID IN (SELECT c_project_ID FROM c_project WHERE c_project_parent_ID =?) ");
    	else
    		sql.append("AND o.c_project_ID = ? ");

    	ArrayList<Object> params = new ArrayList<Object>();
    	params.add(isSOTrx);
    	params.add(c_Project_ID);

    	BigDecimal result = DB.getSQLValueBDEx(null, sql.toString(), params);
    	return result==null?Env.ZERO:result;
    }

	/**
	 * Calculates this Project's ordered, but not invoiced Costs or Revenue based on Orders
	 * Special treatment for closed orders
	 * @param c_Project_ID Project ID
	 * @param isSOTrx  (boolean) true (Revenue) or false (Cost)
	 * @param isParentProject  (boolean) true (Include all child Projects) or false (Only this Project)
	 *	@return Amount of Cost or Revenue, depending on parameter 
	 */
   private BigDecimal calcNotInvoicedCostOrRevenue(int c_Project_ID, boolean isSOTrx, boolean isParentProject) {
   	StringBuffer sql = new StringBuffer();
   	sql.append("SELECT COALESCE (SUM( ");
   	sql.append("CASE ");
   	sql.append("     WHEN pl.istaxincluded = 'Y' ");
   	sql.append("     THEN ");
   	sql.append("         CASE ");
   	sql.append("         WHEN o.docstatus in ('CL') ");
   	sql.append("         THEN 0  ");
   	sql.append("         ELSE ((ol.qtyordered-ol.qtyinvoiced)*ol.Priceactual) - (taxamt_Notinvoiced(ol.c_Orderline_ID)) ");
   	sql.append("         END ");
   	sql.append("     ELSE ");
   	sql.append("         CASE ");
   	sql.append("         WHEN o.docstatus IN ('CL') ");
   	sql.append("         THEN 0 ");
   	sql.append("         ELSE ((ol.qtyordered-ol.qtyinvoiced)*ol.Priceactual) ");
   	sql.append("         END ");
   	sql.append("     END ");
   	sql.append("),0) ");
   	sql.append("FROM C_OrderLine ol ");
   	sql.append("INNER JOIN c_order o ON ol.c_order_ID = o.c_order_ID ");
   	sql.append("INNER JOIN m_pricelist pl ON o.m_pricelist_ID = pl.m_pricelist_ID ");
   	sql.append("INNER JOIN c_tax t ON ol.c_tax_ID = t.c_tax_ID ");
   	sql.append("WHERE ");
	sql.append("o.c_order_ID IN  (select c_order_ID from c_order where docstatus in ('CO','CL','IP')   AND issotrx =  ? ) ");

	if(isParentProject)
		sql.append( "AND o.c_project_ID IN (SELECT c_project_ID FROM c_project WHERE c_project_parent_ID =?) ");
	else
		sql.append("AND o.c_project_ID = ? ");
	
	ArrayList<Object> params = new ArrayList<Object>();
	params.add(isSOTrx);
	params.add(c_Project_ID);
		
   	BigDecimal result = DB.getSQLValueBDEx(null, sql.toString(), params);
   	return result==null?Env.ZERO:result;
    }
    
   /**
    * Calculates this Project's ordered, but not invoiced Costs or Revenue based on Order
	 *  added to this Project's Actual Costs or Revenue based on Invoices
	 * @param c_Project_ID Project ID
	 * @param isSOTrx  (boolean) true (Revenue) or false (Cost)
	 * @param isParentProject  (boolean) true (Include all child Projects) or false (Only this Project)
	 *	@return Amount of Cost or Revenue, depending on parameter 
	 */
   private BigDecimal calcCostOrRevenueExtrapolated(int c_Project_ID, boolean isSOTrx, boolean isParentProject) {
    	BigDecimal result = calcNotInvoicedCostOrRevenue(c_Project_ID, isSOTrx, isParentProject).add(calcCostOrRevenueActual(c_Project_ID, isSOTrx, isParentProject));
    	return result==null?Env.ZERO:result;
    }

	/**
	 * Calculates Actual Costs or Revenue of a Project's direct children based on Invoices
	 * @param c_Project_Parent_ID Project ID
	 * @param isSOTrx boolean true (Revenue) or false (Cost)
	 * @param isParentProject  (boolean) true (Include all child Projects) or false (Only this Project)
	 *	@return Amount of Cost or Revenue, depending on parameter 
	 */
    private BigDecimal calcCostOrRevenueActualSons(int c_Project_Parent_ID, boolean isSOTrx, boolean isParentProject) {
    	BigDecimal result = calcCostOrRevenueActual(c_Project_Parent_ID, isSOTrx, isParentProject);
    	return result==null?Env.ZERO:result;
    }       

    /**
	 * Calculates Planned Costs or Revenue of a Project's direct children based on Orders
	 * It considers low level amounts.
	 * @param c_project_parent_ID Project ID
	 * @param isSOTrx boolean true (Revenue) or false (Cost)
	 * @param isParentProject  (boolean) true (Include all child Projects) or false (Only this Project)
	 *	@return Amount of Cost or Revenue, depending on parameter 
	 */
    private BigDecimal calcCostOrRevenuePlannedSons(int c_Project_Parent_ID, boolean isSOTrx, boolean isParentProject) {
    	BigDecimal result = calcCostOrRevenuePlanned(c_Project_Parent_ID, isSOTrx, isParentProject);
    	return result==null?Env.ZERO:result;
    }
    
    /**
	 * Calculates not Invoiced Costs or Revenue of a Project's direct children based on Orders
	 * @param c_Project_Parent_ID Project ID
	 * @param isSOTrx boolean true (Revenue) or false (Cost)
	 * @param isParentProject  (boolean) true (Include all child Projects) or false (Only this Project)
	 *	@return Amount of Cost or Revenue, depending on parameter 
	 */
     private BigDecimal calcNotInvoicedCostOrRevenueSons(int c_Project_Parent_ID, boolean isSOTrx, boolean isParentProject) {
     	BigDecimal result = calcNotInvoicedCostOrRevenue(c_Project_Parent_ID, isSOTrx, isParentProject);
     	return result==null?Env.ZERO:result;  
    }
     
     /**
      * Calculates not Invoiced Costs or Revenue of a Project's direct children based on Orders
  	 *  added to  Actual Costs or Revenue of a Project's children based on Invoices
	 * @param c_Project_Parent_ID Project ID
  	 * @param isSOTrx  (boolean) true (Revenue) or false (Cost)
	 * @param isParentProject  (boolean) true (Include all child Projects) or false (Only this Project)
  	 *	@return Amount of Cost or Revenue, depending on parameter 
  	 */
     private BigDecimal calcCostOrRevenueExtrapolatedSons(int c_Project_Parent_ID, boolean isSOTrx, boolean isParentProject) {
    	BigDecimal result = calcNotInvoicedCostOrRevenueSons(c_Project_Parent_ID, isSOTrx, isParentProject).add(calcCostOrRevenueActualSons(c_Project_Parent_ID, isSOTrx, isParentProject));
    	return result==null?Env.ZERO:result;
    }    
    
    private Boolean calcCostPlannedInherited(MProject son, BigDecimal costPlannedFather
    		, BigDecimal costActualFather
    		, BigDecimal costExtrapolatedFather
    		, BigDecimal shareVolume
    		, BigDecimal shareWeight
    		, BigDecimal shareRevenue)
    {
    	if(son==null)
        	return true;
    	if(costPlannedFather==null)
    		costPlannedFather = Env.ZERO;
    	if(costActualFather==null)
    		costActualFather = Env.ZERO;
    	if(costExtrapolatedFather==null)
    		costExtrapolatedFather = Env.ZERO;
    	if(shareVolume==null)
    		shareVolume = Env.ZERO;
    	if(shareWeight==null)
    		shareWeight = Env.ZERO;
    	if(shareRevenue==null)
    		shareRevenue = Env.ZERO;
    	
    	BigDecimal result = Env.ZERO;
    	result = costPlannedFather.multiply(shareRevenue);
    	son.set_ValueOfColumn("CostPlannedInherited", result);
    	result = costPlannedFather.multiply(shareVolume);
    	son.set_ValueOfColumn("CostPlannedVolumeInherited", result);
    	result = costPlannedFather.multiply(shareWeight);
    	son.set_ValueOfColumn("CostPlannedWeightInherited", result);

    	result = costActualFather.multiply(shareRevenue);
    	son.set_ValueOfColumn("CostAmtInherited", result);
    	result = costActualFather.multiply(shareVolume);
    	son.set_ValueOfColumn("CostAmtVolumeInherited", result);
    	result = costActualFather.multiply(shareWeight);
    	son.set_ValueOfColumn("CostAmtWeightInherited", result);
    	
    	result = costExtrapolatedFather.multiply(shareRevenue);
    	son.set_ValueOfColumn("CostExtrapolatedInherited", result);
    	result = costExtrapolatedFather.multiply(shareVolume);
    	son.set_ValueOfColumn("CostExtrapolatedVolInherited", result);
    	result = costExtrapolatedFather.multiply(shareWeight);
    	son.set_ValueOfColumn("CostExtrapolatedWghtInherited", result);
    	
    	if (son.getC_Project_ID() != projectInicial.getC_Project_ID())
    		son.saveEx();    	
    	return true;
    }
    
    
    public String createDistribution()
    {     	  
    	if (projectInicial.isSummary() || projectInicial.get_ValueAsInt("C_Project_Parent_ID") == 0)
    		return "";
    		
        for (MAcctSchema as:MAcctSchema.getClientAcctSchema(getCtx(), getAD_Client_ID()))
    		{
    			ArrayList<Object> params = new ArrayList<Object>();
    			params.add(projectInicial.get_ValueAsInt("C_Project_Parent_ID"));
    			params.add(as.getC_AcctSchema_ID());
    			List<MDistribution> distributions = new Query(getCtx(), MDistribution.Table_Name, "C_Project_ID =? " +
    					" and c_acctschema_ID=?", get_TrxName())
    			.setParameters(params)
    			.list();
    			for (MDistribution distribution:distributions)
    				distribution.delete(true);
    			createDistribution(as);
    			distributeOrders(as);
    			distributeInvoices(as);
    		}
    	return "";
    }
    
    private void createDistribution(MAcctSchema as)
    {
    	String ProjectDistribution = projectInicial.get_ValueAsString("ProjectDistribution");
    	if (ProjectDistribution == null || ProjectDistribution == "")
    		ProjectDistribution = "I";
    	MDistribution distribution = new MDistribution(getCtx()	, 0, get_TrxName());
    	distribution.setAnyProject(false);
    	distribution.setC_Project_ID(projectInicial.get_ValueAsInt("C_Project_Parent_ID"));
    	distribution.setName(getName());
    	distribution.setC_AcctSchema_ID(as.getC_AcctSchema_ID());
    	distribution.setIsCreateReversal(false);
    	distribution.saveEx();
    	MProject father = new MProject(getCtx(), projectInicial.get_ValueAsInt("C_Project_Parent_ID"), get_TrxName());
    	List<MProject> projectsOfFather = new Query(getCtx(), MProject.Table_Name, "C_Project_Parent_ID=?", get_TrxName())
    	.setParameters(projectInicial.get_ValueAsInt("C_Project_Parent_ID"))
    	.list();
    	BigDecimal weight_father = (BigDecimal)father.get_Value("Weight");
    	BigDecimal Volume_father = (BigDecimal)father.get_Value("Volume");
    	BigDecimal share = Env.ONEHUNDRED;
    	BigDecimal revenueAll = calcCostOrRevenueActualSons(projectInicial.get_ValueAsInt("C_Project_ID"), true, true);	
    	
    		for (MProject projectson: projectsOfFather)
    		{
    			if (projectsOfFather.size() > 1)
    			{
    				BigDecimal weight = (BigDecimal)projectson.get_Value("Weight");
    				BigDecimal volume = (BigDecimal)projectson.get_Value("volume");
    				if (weight.compareTo(Env.ZERO) == 0 && volume.compareTo(Env.ZERO)==0
    						&& revenueAll.compareTo(Env.ZERO)!= 0 
    						&& calcCostOrRevenueActual(projectson.getC_Project_ID(), true, false).compareTo(Env.ZERO)!=0)
    				{
    					calcCostOrRevenueActual(projectson.getC_Project_ID(), true, false).divide(revenueAll,  5, RoundingMode.HALF_UP);
    				}
    				else
    				{
    					if (ProjectDistribution.equals("W") && weight_father.compareTo(Env.ZERO)==0)
    						return ;

    					if (ProjectDistribution.equals("V") && Volume_father.compareTo(Env.ZERO)==0)
    						return ;
    					share = ProjectDistribution.equals("W")?
    							weight.divide(weight_father, 5, RoundingMode.HALF_UP)
    							: volume.divide(Volume_father, 5, RoundingMode.HALF_UP);
    				}
    			}
				share = share.multiply(Env.ONEHUNDRED); 
    			MDistributionLine dLine = new MDistributionLine(getCtx(), 0, get_TrxName());
    			dLine.setGL_Distribution_ID(distribution.getGL_Distribution_ID());
    			dLine.setOverwriteProject(true);
    			dLine.setC_Project_ID(projectson.getC_Project_ID());
    			dLine.setPercent(share);
    			distribution.setPercentTotal(distribution.getPercentTotal().add(share));
    			dLine.saveEx();			
    		}
    	distribution.saveEx();
    	BigDecimal diff = Env.ONEHUNDRED.subtract(distribution.getPercentTotal());
    	if (diff.compareTo(Env.ZERO)!=0)
    	{
    		final String whereClause = I_GL_DistributionLine.COLUMNNAME_GL_Distribution_ID+"=?";
    		MDistributionLine maxLine = new Query(getCtx(),I_GL_DistributionLine.Table_Name,whereClause,get_TrxName())
    		.setParameters(distribution.getGL_Distribution_ID())
    		.setOrderBy(MDistributionLine.COLUMNNAME_Percent + " desc")
    		.first();
    		maxLine.setPercent(maxLine.getPercent().add(diff));
    		maxLine.saveEx();
    	}
    	distribution.validate();
    }
    
    private String distributeOrders(MAcctSchema as)
	{
    	ArrayList<MOrder> ordersToPost = new ArrayList<MOrder>();
		String whereClause = "C_Project_ID=?";
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(projectInicial.get_ValueAsInt("C_Project_Parent_ID"));

		List<MOrderLine> oLines = new Query(getCtx(), MOrderLine.Table_Name, whereClause, get_TrxName())
		.setParameters(params)
		.setOrderBy("C_Order_ID ")
		.list();
		if (oLines == null)
			return"";
		for (MOrderLine oLine:oLines)
		{
			if ((oLine.getC_Order().getDocStatus().equals(MOrder.DOCSTATUS_Drafted)
					|| oLine.getC_Order().getDocStatus().equals(MOrder.DOCSTATUS_InProgress)
					|| oLine.getC_Order().getDocStatus().equals(MOrder.DOCSTATUS_Invalid)))
				continue;
			//if (MPeriod.isOpen(getCtx(), oLine.getC_Order().getDateAcct()
			//		, oLine.getC_Order().getC_DocType().getDocBaseType(), oLine.getAD_Org_ID()))
			{

				Boolean isadded = false;
				for (MOrder order:ordersToPost)
				{
					if (order.getC_Order_ID() ==  oLine.getC_Order_ID())
					{
						isadded = true;
						break;
					}
				}
				if (!isadded)
					ordersToPost.add(oLine.getParent());
			}
			//else
			{
				//createJournal(as, oLine);						
			}
		}
		for (MOrder order:ordersToPost)
		{
			clearAccounting(as, order);}
		return "";
	}
	

	private String distributeInvoices(MAcctSchema as)
	{
		String whereClause = "C_Project_ID=?";
		ArrayList<MInvoice> invoicesToPost = new ArrayList<MInvoice>();
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(projectInicial.getC_Project_ID());
		List<MInvoiceLine> oLines = new Query(getCtx(), MInvoiceLine.Table_Name, whereClause, get_TrxName())
		.setParameters(params)
		.setOrderBy("C_Invoice_ID ")
		.list();
		if (oLines == null)
			return"";
		for (MInvoiceLine oLine:oLines)
		{
			if ((oLine.getC_Invoice().getDocStatus().equals(MOrder.DOCSTATUS_Drafted)
					|| oLine.getC_Invoice().getDocStatus().equals(MOrder.DOCSTATUS_InProgress)
					|| oLine.getC_Invoice().getDocStatus().equals(MOrder.DOCSTATUS_Invalid)))
				continue;
			if (MPeriod.isOpen(getCtx(), oLine.getC_Invoice().getDateAcct()
					, oLine.getC_Invoice().getC_DocType().getDocBaseType(), oLine.getAD_Org_ID(), get_TrxName()))
			{

				Boolean isadded = false;
				for (MInvoice invoice:invoicesToPost)
				{
					if (invoice.getC_Invoice_ID() ==  oLine.getC_Invoice_ID())
					{
						isadded = true;
						break;
					}
				}
				if (!isadded)
					invoicesToPost.add(oLine.getParent());
			}
			else
			{
				//createJournal(as, oLine);						
			}
			for (MInvoice invoice:invoicesToPost)
			{
				clearAccounting(as, invoice);
			}
		}
		return "";
	
	}
	
	public boolean clearAccounting(MAcctSchema accountSchema, PO model) 
	{
		final String sqlUpdate = "UPDATE " + model.get_TableName() + " SET Posted = 'N' WHERE "+ model.get_TableName() + "_ID=?";
		int no = DB.executeUpdate(sqlUpdate, new Object[] {model.get_ID()}, false , model.get_TrxName());
		//Delete account
		final String sqldelete = "DELETE FROM Fact_Acct WHERE Record_ID =? AND AD_Table_ID=?";		
		no = DB.executeUpdate (sqldelete ,new Object[] { model.get_ID(),
				model.get_Table_ID() }, false , model.get_TrxName());
		return true;
	}	
	
	public String updateWeightVolume()
	{

        if (!(projectInicial.getProjectCategory().equals("M")
                || projectInicial.getProjectCategory().equals("T")))
            return "";
        
        if (projectInicial.getProjectCategory().equals("T"))
        {

        	int C_Project_Parent_ID = projectInicial.get_ValueAsInt("C_Project_Parent_ID");
        	if (C_Project_Parent_ID == 0)
        		return "" ;
            MProject parent = new MProject(getCtx(), projectInicial.get_ValueAsInt("C_Project_Parent_ID"), get_TrxName());
            BigDecimal total = new Query(getCtx(), MProject.Table_Name, "C_Project_Parent_ID=?", get_TrxName())
                .setParameters(projectInicial.get_ValueAsInt("C_Project_Parent_ID"))
                .aggregate("Weight", Query.AGGREGATE_SUM);
            parent.set_ValueOfColumn("Weight", total);
            total = new Query(getCtx(), MProject.Table_Name, "C_Project_Parent_ID=?", get_TrxName())
                .setParameters(projectInicial.get_ValueAsInt("C_Project_Parent_ID"))
                .aggregate("Volume", Query.AGGREGATE_SUM);
            parent.set_ValueOfColumn("Volume", total);
            parent.saveEx();
            return "";
        }
        MProject parent = new MProject(getCtx(),projectInicial.get_ValueAsInt("C_Project_ID"), get_TrxName());
        List<MProject> sons = new Query(getCtx(), MProject.Table_Name, "C_Project_Parent_ID=?", get_TrxName())
        .setParameters(projectInicial.get_ValueAsInt("C_Project_ID"))
        .list();        
        BigDecimal totalWeight = Env.ZERO;
        BigDecimal totalVolume = Env.ZERO;
        for (MProject son:sons)
        {
            BigDecimal rateWeight = MUOMConversion.convert(son.get_ValueAsInt("C_UOM_ID"), 
                    parent.get_ValueAsInt("C_UOM_ID"), (BigDecimal)son.get_Value("WeightEntered"), true);

            BigDecimal rateVolume = MUOMConversion.convert(son.get_ValueAsInt("C_UOM_Volume_ID"), 
                    parent.get_ValueAsInt("C_UOM_Volume_ID"), (BigDecimal)son.get_Value("VolumeEntered"), true);
            son.set_ValueOfColumn("Weight", rateWeight);
            son.set_ValueOfColumn("Volume", rateVolume);
            totalWeight = totalWeight.add(rateWeight);
            totalVolume = totalVolume.add(rateVolume);
            son.saveEx();
        }
        parent.set_ValueOfColumn("Weight", totalWeight);
        parent.set_ValueOfColumn("Volume", totalVolume);
        parent.saveEx();
        return "";        
	}

	/**
	 * Calculates this Project's Line Net Amount
	 * For phases and tasks with products
	 *	@return sum of Line Net Amount of all phases and tasks 
	 */
    private BigDecimal calcLineNetAmt() {		
    	StringBuffer sql = new StringBuffer();
    	sql.append("select sum (linenetamt) ");
    	sql.append("from c_project_calculate_price ");
    	sql.append("where C_Project_ID=?");
    	
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(projectInicial.getC_Project_ID());
		
    	BigDecimal result = DB.getSQLValueBDEx(null, sql.toString(), params);
    	return result==null?Env.ZERO:result;
    }

	/**
	 * Calculates this Project's Actual Amount
	 * For phases and tasks with products
	 *	@return sum of Actual Amount of all phases and tasks 
	 */
    private BigDecimal calcActualamt() {		
    	StringBuffer sql = new StringBuffer();
    	sql.append("select sum (actualamt) ");
    	sql.append("from c_project_calculate_price ");
    	sql.append("where C_Project_ID=?");
    	
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(projectInicial.getC_Project_ID());
		
    	BigDecimal result = DB.getSQLValueBDEx(null, sql.toString(), params);
    	return result==null?Env.ZERO:result;
    }

	/**
	 * Calculates a Project's Costs of Product Issues
	 * Out of Cost Detail
	 * @param c_Project_ID Project ID
	 * @param isParentProject  (boolean) true (Include all child Projects) or false (Only this Project)
	 *	@return sum of Costs of Product Issues of all phases and tasks 
	 */
    private BigDecimal calcCostIssueProduct(int c_Project_ID, boolean isParentProject) {		
    	StringBuffer sql = new StringBuffer();
    	sql.append("SELECT COALESCE(SUM(cd.CostAmt + cd.CostAmtLL + cd.CostAdjustment + cd.CostAdjustmentLL),0) ");
    	sql.append("FROM C_ProjectIssue pi ");
    	sql.append("INNER JOIN M_CostDetail cd ON pi.c_ProjectIssue_ID=cd.c_ProjectIssue_ID ");

    	if(isParentProject)
    		sql.append("WHERE pi.C_Project_ID IN (SELECT c_project_ID FROM c_project WHERE c_project_parent_ID =?) " );
    	else
    	    sql.append("WHERE pi.C_Project_ID=? ");
    	   	
    	sql.append("AND pi.M_InOutLine_ID IS NOT NULL ");
    	
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(c_Project_ID);
		
    	BigDecimal result = DB.getSQLValueBDEx(null, sql.toString(), params);
    	return result==null?Env.ZERO:result;
    }

	/**
	 * Calculates a Project's Costs of Product Issues
	 * Out of Project Lines
	 * @param c_Project_ID Project ID
	 * @param isParentProject  (boolean) true (Include all child Projects) or false (Only this Project)
	 *	@return sum of Costs of Product Issues of all phases and tasks 
	 */
    private BigDecimal calcCostIssueResource(int c_Project_ID, boolean isParentProject) {		
    	StringBuffer sql = new StringBuffer();
    	sql.append("SELECT SUM (pl.committedamt) ");
    	sql.append("FROM c_projectline pl ");
    	sql.append("INNER JOIN c_project p ON (pl.c_project_id=p.c_project_id) ");

    	if(isParentProject)
    		sql.append("WHERE pl.C_Project_ID IN (SELECT c_project_ID FROM c_project WHERE c_project_parent_ID =?) " );
    	else
    	    sql.append("WHERE pl.C_Project_ID=? ");
    	
    	sql.append("AND pl.c_projectissue_ID IS NOT NULL ");
    	sql.append("AND pl.s_timeexpenseline_ID IS NOT NULL ");
    	
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(c_Project_ID);
		
    	BigDecimal result = DB.getSQLValueBDEx(null, sql.toString(), params);
    	return result==null?Env.ZERO:result;
    }   

	/**
	 * Calculates a Project's Costs of Inventory Issues
	 * @param c_Project_ID Project ID
	 * @param isParentProject  (boolean) true (Include all child Projects) or false (Only this Project)
	 * Out of Cost Detail
	 *	@return sum of Costs of Inventory Issues of all phases and tasks 
	 */
    private BigDecimal calcCostIssueInventory(int c_Project_ID, boolean isParentProject) {		
    	StringBuffer sql = new StringBuffer();
    	sql.append("SELECT COALESCE(SUM(cd.CostAmt + cd.CostAmtLL + cd.CostAdjustment + cd.CostAdjustmentLL),0) ");
    	sql.append("FROM C_ProjectIssue pi ");
    	sql.append("INNER JOIN M_CostDetail cd ON pi.c_ProjectIssue_ID=cd.c_ProjectIssue_ID ");

    	if(isParentProject)
    		sql.append("WHERE pi.C_Project_ID IN (SELECT c_project_ID FROM c_project WHERE c_project_parent_ID =?) " );
    	else
    	    sql.append("WHERE pi.C_Project_ID=? ");
    	
    	sql.append("AND pi.M_InOutLine_ID IS  NULL ");
    	sql.append("AND pi.s_timeexpenseline_ID IS NULL ");
    	
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(c_Project_ID);
		
    	BigDecimal result = DB.getSQLValueBDEx(null, sql.toString(), params);
    	return result==null?Env.ZERO:result;
    }   
    
	/**
	 * Calculates Costs of Product Issues for this Project's children 
	 * Out of Cost Detail
	 * @param c_project_parent_ID Project ID
	 * @param isParentProject  (boolean) true (Include all child Projects) or false (Only this Project)
	 *	@return sum of Costs of Product Issues of all phases and tasks of the project's children
	 */
    private BigDecimal calcCostIssueProductSons(int c_Project_Parent_ID, boolean isParentProject) {	
    	BigDecimal result = calcCostIssueProduct(c_Project_Parent_ID, isParentProject);
    	return result==null?Env.ZERO:result;
    }  
    
	/**
	 * Calculates Costs of Resource Issues for this Project's children 
	 * Out of Project Lines
	 * @param c_project_parent_ID Project ID
	 * @param isParentProject  (boolean) true (Include all child Projects) or false (Only this Project)
	 *	@return sum of Costs of Resource Issues of all phases and tasks of the project's children
	 */
    private BigDecimal calcCostIssueResourceSons(int c_Project_Parent_ID, boolean isParentProject) {		
    	BigDecimal result = calcCostIssueResource(c_Project_Parent_ID, isParentProject);
    	return result==null?Env.ZERO:result;
    }  
    
	/**
	 * Calculates Costs of Inventory Issues for this Project's children 
	 * Out of Cost Detail
	 * @param c_project_parent_ID Project ID
	 * @param isParentProject  (boolean) true (Include all child Projects) or false (Only this Project)
	 *	@return sum of Costs of Inventory Issues of all phases and tasks of the project's children
	 */
    private BigDecimal calcCostIssueInventorySons(int c_Project_Parent_ID, boolean isParentProject) {	
    	BigDecimal result = calcCostIssueInventory(c_Project_Parent_ID, isParentProject);
    	return result==null?Env.ZERO:result;
    }
	
	/**
	 * 	Update Costs and Revenues in the following order 
	 * 1.- For the children of the project up to the lowest levels
	 * 2.- For a given Project 
	 * To avoid recursive relations, it breaks after 5 loops.
	 * @param levelCount depth of the call
	 *	@return message
	 */	
    private String updateProjectPerformanceCalculationSons(int c_Project_ID, int levelCount) {	

		if (levelCount == 5)  // For now, allow for 5 level depth
			return"";         // Right now, there is no verification of circular reference
		
    	String whereClause = "C_Project_Parent_ID=?";
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(c_Project_ID);

		List<MProject> childrenProjects = new Query(getCtx(), MProject.Table_Name, whereClause, get_TrxName())
		.setParameters(params)
		.list();

		if (childrenProjects == null) {	
			// No children -> just update this project
			updateProjectPerformanceCalculation(projectInicial);
			return"";	
		}
		
		for (MProject childProject:childrenProjects) {
			// update all children of this child
			updateProjectPerformanceCalculationSons(childProject.getC_Project_ID(), levelCount+1);
		}
		// last but not least, update this project
		updateProjectPerformanceCalculation(projectInicial);
		return"";
    }
	
}
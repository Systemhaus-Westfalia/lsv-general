/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.shw.model;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.model.GridTabWrapper;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MBPartner;
import org.compiere.model.MProject;
import org.compiere.model.MProjectType;
import org.compiere.model.MUOMConversion;
import org.compiere.util.Env;


/**
 *	Project Callouts
 *	
 *  @author Jorg Janke
 *  @version $Id: CalloutProject.java,v 1.3 2006/07/30 00:51:04 jjanke Exp $
 */
public class CalloutProjectSA extends CalloutEngine
{
	/**
	 *	Project Planned - Price + Qty.
	 *		- called from PlannedPrice, PlannedQty
	 *		- calculates PlannedAmt (same as Trigger)
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return null or error message
	 */
	public  String bPartner (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive() || value == null)
			return "";
           Integer C_BPartner_ID = (Integer)value;
        if (C_BPartner_ID == null || C_BPartner_ID.intValue() == 0)
            return "";
            MBPartner bpartner = new MBPartner(ctx, C_BPartner_ID, null);
            mTab.setValue("C_Activity_ID", bpartner.get_ValueAsInt("C_Activity_ID"));
            mTab.setValue("User1_ID", bpartner.get_ValueAsInt("User1_ID"));
            mTab.setValue("SalesRep_ID", bpartner.get_ValueAsInt("SalesRep_ID"));
            if (bpartner.getAD_Org_ID()!=0)
            	mTab.setValue("AD_Org_ID", bpartner.getAD_Org_ID());
            if (bpartner.get_ValueAsInt("SuperVisor_Custom") != 0)
            	mTab.setValue("SuperVisor_Custom", bpartner.get_ValueAsInt("SuperVisor_Custom"));
            if (bpartner.get_ValueAsInt("SuperVisor_Shipment") !=0)
            	mTab.setValue("SuperVisor_Shipment", bpartner.get_ValueAsInt("SuperVisor_Shipment"));

		return "";
	}	//	planned
	
	public  String projectParent (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive() || value == null)
			return "";


	           Integer C_Project_Parent_ID = (Integer)value;
	            if (C_Project_Parent_ID == null || C_Project_Parent_ID==0)       
	            return "";
	            MProject project_Parent = new MProject(ctx,C_Project_Parent_ID,null );
	            mTab.setValue("LG_Route_ID", project_Parent.get_ValueAsInt("LG_Route_ID"));
	            mTab.setValue("M_Shipper_ID", project_Parent.get_ValueAsInt("M_Shipper_ID"));
	            mTab.setValue("C_UOM_ID", project_Parent.get_ValueAsInt("C_UOM_ID"));
	            mTab.setValue("C_UOM_Volume_ID", project_Parent.get_ValueAsInt("C_UOM_Volume_ID"));
	            mTab.setValue("ProjectDistribution", project_Parent.get_ValueAsString("ProjectDistribution"));

		return "";
	}	//	planned
	
	public  String projectType (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive() || value == null)
			return "";

        Integer C_ProductType_ID = (Integer)value;
        MProjectType pt = new MProjectType(ctx, C_ProductType_ID, null);
        mTab.setValue(MProjectType.COLUMNNAME_ProjectCategory, pt.getProjectCategory());
        return "";
	}	//	planned
	
	public  String lGRoute (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive() || value == null)
			return "";

			   Integer LG_Route_ID = (Integer)value;
			   if (LG_Route_ID == 0 || LG_Route_ID == null)
			       return "";
			   X_LG_Route i = new X_LG_Route(Env.getCtx(), LG_Route_ID, null);
			   mTab.setValue("LG_CityTo_ID", i.getLG_CityTo_ID());  
			   mTab.setValue("LG_CityFrom_ID", i.getLG_CityFrom_ID());

			
			   mTab.setValue("C_Country_ID", i.getLG_CityTo().getC_Country_ID());  
			   mTab.setValue("c_country_origin_ID", i.getLG_CityFrom().getC_Country_ID());
		       return "";
	}	//	planned
	
	public  String convertWeightVolume (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive() || value == null )
			return "";

		BigDecimal WeightEntered = (BigDecimal)mTab.getValue("WeightEntered");
		        BigDecimal VolumeEntered = (BigDecimal)mTab.getValue("VolumeEntered");
		        Integer C_Project_Parent_ID = (Integer)mTab.getValue("C_Project_Parent_ID");
		        Integer C_UOM_ID = (Integer)mTab.getValue("C_UOM_ID");
		        Integer C_UOM_Volume_ID = (Integer)mTab.getValue("C_UOM_Volume_ID");
		        Boolean IsSummary = (Boolean)mTab.getValue("IsSummary");
		        if (C_Project_Parent_ID == null || C_Project_Parent_ID == 0)
		        {
		            if (IsSummary)
		                return"";
		            mTab.setValue("Weight",  mTab.getValue("WeightEntered"));
		            mTab.setValue("Volume",  mTab.getValue("VolumeEntered"));
		            return "";
		        }
		        MProject parent = new MProject(ctx, C_Project_Parent_ID, null);    
		            BigDecimal rateWeight = MUOMConversion.convert(C_UOM_ID, 
		                parent.get_ValueAsInt("C_UOM_ID"), WeightEntered, true);

		            BigDecimal rateVolume = MUOMConversion.convert(C_UOM_Volume_ID, 
		                    parent.get_ValueAsInt("C_UOM_Volume_ID"), VolumeEntered, true);
		            mTab.setValue("Weight", rateWeight);
		            mTab.setValue("Volume", rateVolume);        
		        return "";
	}	//	planned


	/**
	 * Fill Project Standard Phase
	 * @param ctx
	 * @param windowNo
	 * @param gridTab
	 * @param gridField
	 * @param value
	 * @return
	 */
	
}	//	CalloutProject

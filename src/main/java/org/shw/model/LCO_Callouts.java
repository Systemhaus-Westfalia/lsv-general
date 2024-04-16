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

import java.util.Properties;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MBPartner;


/**
 *	User Callout for LCO Localization Colombia
 *
 *  @author Carlos Ruiz
 *  @version  $Id: LCO_Callouts.java,v 1.0 2008/05/26
 */
public class LCO_Callouts extends CalloutEngine
{

	/**
	 *	Check Digit based on TaxID.
	 */
	public String checkTaxIdDigit (Properties ctx, int WindowNo,
			GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		return "";
	}	//	checkTaxIdDigit

	/**
	 *	taxIdType
	 */
	public String taxIdType (Properties ctx, int WindowNo,
			GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		return "";
	}	//	taxIdType

	/**
	 *	taxIdType
	 */
	public String fillName (Properties ctx, int WindowNo,
			GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		log.info("");
		
		if (mTab.getValue("FirstName1") == null 
				&& mTab.getValue("FirstName2") == null
				&& mTab.getValue("LastName1") == null
				&& mTab.getValue("LastName2") == null)
			mTab.setValue(MBPartner.COLUMNNAME_Name, null);
		
		String filledName = (String) mTab.getValue("FirstName1");
		if (filledName == null)
			filledName = new String("");
		if (mTab.getValue("FirstName2") != null)
			filledName = filledName + " " + ((String) mTab.getValue("FirstName2")).trim();
		
		if (filledName != null)
		//	filledName = filledName + ", "; -- Separa nombres y apellidos con coma
			filledName = filledName + " ";
		
		if (mTab.getValue("LastName1") != null)
			filledName = filledName + ((String) mTab.getValue("LastName1")).trim();
		if (mTab.getValue("LastName2") != null)
			filledName = filledName + " " + ((String) mTab.getValue("LastName2")).trim();
		
		if (filledName.length() > 60)
		{
			// log.warning("Length > 60 - truncated");
			filledName = filledName.substring(0, 60);
		}

		mTab.setValue(MBPartner.COLUMNNAME_Name, filledName);
		return "";
	}	//	taxIdType

}	//	LCO_Callouts
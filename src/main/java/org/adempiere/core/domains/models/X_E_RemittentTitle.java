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
/** Generated Model - DO NOT CHANGE */
package org.adempiere.core.domains.models;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.I_Persistent;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/** Generated Model for E_RemittentTitle
 *  @author Adempiere (generated) 
 *  @version Release 3.9.4 - $Id$ */
public class X_E_RemittentTitle extends PO implements I_E_RemittentTitle, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20230903L;

    /** Standard Constructor */
    public X_E_RemittentTitle (Properties ctx, int E_RemittentTitle_ID, String trxName)
    {
      super (ctx, E_RemittentTitle_ID, trxName);
      /** if (E_RemittentTitle_ID == 0)
        {
			setE_RemittentTitle_ID (0);
        } */
    }

    /** Load Constructor */
    public X_E_RemittentTitle (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_E_RemittentTitle[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set CAT-025 Título a que se remiten los bienes.
		@param E_RemittentTitle_ID CAT-025 Título a que se remiten los bienes	  */
	public void setE_RemittentTitle_ID (int E_RemittentTitle_ID)
	{
		if (E_RemittentTitle_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_E_RemittentTitle_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_E_RemittentTitle_ID, Integer.valueOf(E_RemittentTitle_ID));
	}

	/** Get CAT-025 Título a que se remiten los bienes.
		@return CAT-025 Título a que se remiten los bienes	  */
	public int getE_RemittentTitle_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_E_RemittentTitle_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set Immutable Universally Unique Identifier.
		@param UUID 
		Immutable Universally Unique Identifier
	  */
	public void setUUID (String UUID)
	{
		set_Value (COLUMNNAME_UUID, UUID);
	}

	/** Get Immutable Universally Unique Identifier.
		@return Immutable Universally Unique Identifier
	  */
	public String getUUID () 
	{
		return (String)get_Value(COLUMNNAME_UUID);
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}
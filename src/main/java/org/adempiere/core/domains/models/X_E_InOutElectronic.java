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
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/** Generated Model for E_InOutElectronic
 *  @author Adempiere (generated) 
 *  @version Release 3.9.4 - $Id$ */
public class X_E_InOutElectronic extends PO implements I_E_InOutElectronic, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20260713L;

    /** Standard Constructor */
    public X_E_InOutElectronic (Properties ctx, int E_InOutElectronic_ID, String trxName)
    {
      super (ctx, E_InOutElectronic_ID, trxName);
      /** if (E_InOutElectronic_ID == 0)
        {
			setE_InOutElectronic_ID (0);
			setProcessed (false);
// N
			setProcessing (false);
// N
        } */
    }

    /** Load Constructor */
    public X_E_InOutElectronic (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System 
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
      StringBuffer sb = new StringBuffer ("X_E_InOutElectronic[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set E_InOutElectronic.
		@param E_InOutElectronic_ID E_InOutElectronic	  */
	public void setE_InOutElectronic_ID (int E_InOutElectronic_ID)
	{
		if (E_InOutElectronic_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_E_InOutElectronic_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_E_InOutElectronic_ID, Integer.valueOf(E_InOutElectronic_ID));
	}

	/** Get E_InOutElectronic.
		@return E_InOutElectronic	  */
	public int getE_InOutElectronic_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_E_InOutElectronic_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** ei_ValidationStatus AD_Reference_ID=1000011 */
	public static final int EI_VALIDATIONSTATUS_AD_Reference_ID=1000011;
	/** Valid = 01 */
	public static final String EI_VALIDATIONSTATUS_Valid = "01";
	/** Not Valid = 02 */
	public static final String EI_VALIDATIONSTATUS_NotValid = "02";
	/** Set EI Validation Status (Intern).
		@param ei_ValidationStatus 
		EI Validation Status
	  */
	public void setei_ValidationStatus (String ei_ValidationStatus)
	{

		set_Value (COLUMNNAME_ei_ValidationStatus, ei_ValidationStatus);
	}

	/** Get EI Validation Status (Intern).
		@return EI Validation Status
	  */
	public String getei_ValidationStatus () 
	{
		return (String)get_Value(COLUMNNAME_ei_ValidationStatus);
	}

	/** Set error Msg Intern.
		@param errMsgIntern error Msg Intern	  */
	public void seterrMsgIntern (String errMsgIntern)
	{
		set_Value (COLUMNNAME_errMsgIntern, errMsgIntern);
	}

	/** Get error Msg Intern.
		@return error Msg Intern	  */
	public String geterrMsgIntern () 
	{
		return (String)get_Value(COLUMNNAME_errMsgIntern);
	}

	/** Set json.
		@param json json	  */
	public void setjson (String json)
	{
		set_Value (COLUMNNAME_json, json);
	}

	/** Get json.
		@return json	  */
	public String getjson () 
	{
		return (String)get_Value(COLUMNNAME_json);
	}

	public org.adempiere.core.domains.models.I_M_InOut getM_InOut() throws RuntimeException
    {
		return (org.adempiere.core.domains.models.I_M_InOut)MTable.get(getCtx(), org.adempiere.core.domains.models.I_M_InOut.Table_Name)
			.getPO(getM_InOut_ID(), get_TrxName());	}

	/** Set Shipment/Receipt.
		@param M_InOut_ID 
		Material Shipment Document
	  */
	public void setM_InOut_ID (int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
	}

	/** Get Shipment/Receipt.
		@return Material Shipment Document
	  */
	public int getM_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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
}
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

import org.compiere.process.SvrProcess;

/** Generated Process for (SHW_ImportConversionRateFromAPI)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.4
 */
public abstract class SHW_ImportConversionRateFromAPIAbstract extends SvrProcess {
	/** Process Value 	*/
	private static final String VALUE_FOR_PROCESS = "SHW_ImportConversionRateFromAPI";
	/** Process Name 	*/
	private static final String NAME_FOR_PROCESS = "SHW_ImportConversionRateFromAPI";
	/** Process Id 	*/
	private static final int ID_FOR_PROCESS = 1000100;
	/**	Parameter Name for URL	*/
	public static final String URL = "URL";
	/**	Parameter Name for Currency	*/
	public static final String C_CURRENCY_ID = "C_Currency_ID";
	/**	Parameter Name for Currency To	*/
	public static final String C_CURRENCY_ID_TO = "C_Currency_ID_To";
	/**	Parameter Name for Currency Type	*/
	public static final String C_CONVERSIONTYPE_ID = "C_ConversionType_ID";
	/**	Parameter Name for Create Reciprocal Rate	*/
	public static final String CREATERECIPROCALRATE = "CreateReciprocalRate";
	/**	Parameter Value for URL	*/
	private String uRL;
	/**	Parameter Value for Currency	*/
	private int currencyId;
	/**	Parameter Value for Currency To	*/
	private int currencyTo;
	/**	Parameter Value for Currency Type	*/
	private int conversionTypeId;
	/**	Parameter Value for Create Reciprocal Rate	*/
	private boolean isCreateReciprocalRate;

	@Override
	protected void prepare() {
		uRL = getParameterAsString(URL);
		currencyId = getParameterAsInt(C_CURRENCY_ID);
		currencyTo = getParameterAsInt(C_CURRENCY_ID_TO);
		conversionTypeId = getParameterAsInt(C_CONVERSIONTYPE_ID);
		isCreateReciprocalRate = getParameterAsBoolean(CREATERECIPROCALRATE);
	}

	/**	 Getter Parameter Value for URL	*/
	protected String getURL() {
		return uRL;
	}

	/**	 Setter Parameter Value for URL	*/
	protected void setURL(String uRL) {
		this.uRL = uRL;
	}

	/**	 Getter Parameter Value for Currency	*/
	protected int getCurrencyId() {
		return currencyId;
	}

	/**	 Setter Parameter Value for Currency	*/
	protected void setCurrencyId(int currencyId) {
		this.currencyId = currencyId;
	}

	/**	 Getter Parameter Value for Currency To	*/
	protected int getCurrencyTo() {
		return currencyTo;
	}

	/**	 Setter Parameter Value for Currency To	*/
	protected void setCurrencyTo(int currencyTo) {
		this.currencyTo = currencyTo;
	}

	/**	 Getter Parameter Value for Currency Type	*/
	protected int getConversionTypeId() {
		return conversionTypeId;
	}

	/**	 Setter Parameter Value for Currency Type	*/
	protected void setConversionTypeId(int conversionTypeId) {
		this.conversionTypeId = conversionTypeId;
	}

	/**	 Getter Parameter Value for Create Reciprocal Rate	*/
	protected boolean isCreateReciprocalRate() {
		return isCreateReciprocalRate;
	}

	/**	 Setter Parameter Value for Create Reciprocal Rate	*/
	protected void setCreateReciprocalRate(boolean isCreateReciprocalRate) {
		this.isCreateReciprocalRate = isCreateReciprocalRate;
	}

	/**	 Getter Parameter Value for Process ID	*/
	public static final int getProcessId() {
		return ID_FOR_PROCESS;
	}

	/**	 Getter Parameter Value for Process Value	*/
	public static final String getProcessValue() {
		return VALUE_FOR_PROCESS;
	}

	/**	 Getter Parameter Value for Process Name	*/
	public static final String getProcessName() {
		return NAME_FOR_PROCESS;
	}
}
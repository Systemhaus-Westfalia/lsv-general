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

import org.compiere.process.SvrProcess;

/** Generated Process for (Copy Print Format With Conversion)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.3
 */
public abstract class CopyPrintFormatWithConversionAbstract extends SvrProcess {
	/** Process Value 	*/
	private static final String VALUE_FOR_PROCESS = "CopyPrintFormatWithConversion";
	/** Process Name 	*/
	private static final String NAME_FOR_PROCESS = "Copy Print Format With Conversion";
	/** Process Id 	*/
	private static final int ID_FOR_PROCESS = 54549;
	/**	Parameter Name for Currency Type	*/
	public static final String C_CONVERSIONTYPE_ID = "C_ConversionType_ID";
	/**	Parameter Name for Currency	*/
	public static final String C_CURRENCY_ID = "C_Currency_ID";
	/**	Parameter Name for Suffix	*/
	public static final String SUFFIX = "Suffix";
	/**	Parameter Value for Currency Type	*/
	private int conversionTypeId;
	/**	Parameter Value for Currency	*/
	private int currencyId;
	/**	Parameter Value for Suffix	*/
	private String suffix;

	@Override
	protected void prepare() {
		conversionTypeId = getParameterAsInt(C_CONVERSIONTYPE_ID);
		currencyId = getParameterAsInt(C_CURRENCY_ID);
		suffix = getParameterAsString(SUFFIX);
	}

	/**	 Getter Parameter Value for Currency Type	*/
	protected int getConversionTypeId() {
		return conversionTypeId;
	}

	/**	 Setter Parameter Value for Currency Type	*/
	protected void setConversionTypeId(int conversionTypeId) {
		this.conversionTypeId = conversionTypeId;
	}

	/**	 Getter Parameter Value for Currency	*/
	protected int getCurrencyId() {
		return currencyId;
	}

	/**	 Setter Parameter Value for Currency	*/
	protected void setCurrencyId(int currencyId) {
		this.currencyId = currencyId;
	}

	/**	 Getter Parameter Value for Suffix	*/
	protected String getSuffix() {
		return suffix;
	}

	/**	 Setter Parameter Value for Suffix	*/
	protected void setSuffix(String suffix) {
		this.suffix = suffix;
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
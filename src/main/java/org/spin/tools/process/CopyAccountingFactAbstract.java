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
import org.compiere.process.SvrProcess;

/** Generated Process for (Copy Accounting Fact From Another Accounting Schema)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.3
 */
public abstract class CopyAccountingFactAbstract extends SvrProcess {
	/** Process Value 	*/
	private static final String VALUE_FOR_PROCESS = "Copy AccountingFact";
	/** Process Name 	*/
	private static final String NAME_FOR_PROCESS = "Copy Accounting Fact From Another Accounting Schema";
	/** Process Id 	*/
	private static final int ID_FOR_PROCESS = 54545;
	/**	Parameter Name for Accounting Schema	*/
	public static final String C_ACCTSCHEMA_ID = "C_AcctSchema_ID";
	/**	Parameter Name for Account Date	*/
	public static final String DATEACCT = "DateAcct";
	/**	Parameter Name for Currency Type	*/
	public static final String C_CONVERSIONTYPE_ID = "C_ConversionType_ID";
	/**	Parameter Name for Conversion Rate	*/
	public static final String C_CONVERSION_RATE_ID = "C_Conversion_Rate_ID";
	/**	Parameter Name for Currency Convert Account	*/
	public static final String CURRENCY_CONVERT_ACCT = "Currency_Convert_Acct";
	/**	Parameter Value for Accounting Schema	*/
	private int acctSchemaId;
	/**	Parameter Value for Accounting Schema(To)	*/
	private int acctSchemaIdTo;
	/**	Parameter Value for Account Date	*/
	private Timestamp dateAcct;
	/**	Parameter Value for Account Date(To)	*/
	private Timestamp dateAcctTo;
	/**	Parameter Value for Currency Type	*/
	private int conversionTypeId;
	/**	Parameter Value for Conversion Rate	*/
	private int conversionRateId;
	/**	Parameter Value for Currency Convert Account	*/
	private int convertAcct;

	@Override
	protected void prepare() {
		acctSchemaId = getParameterAsInt(C_ACCTSCHEMA_ID);
		acctSchemaIdTo = getParameterToAsInt(C_ACCTSCHEMA_ID);
		dateAcct = getParameterAsTimestamp(DATEACCT);
		dateAcctTo = getParameterToAsTimestamp(DATEACCT);
		conversionTypeId = getParameterAsInt(C_CONVERSIONTYPE_ID);
		conversionRateId = getParameterAsInt(C_CONVERSION_RATE_ID);
		convertAcct = getParameterAsInt(CURRENCY_CONVERT_ACCT);
	}

	/**	 Getter Parameter Value for Accounting Schema	*/
	protected int getAcctSchemaId() {
		return acctSchemaId;
	}

	/**	 Setter Parameter Value for Accounting Schema	*/
	protected void setAcctSchemaId(int acctSchemaId) {
		this.acctSchemaId = acctSchemaId;
	}

	/**	 Getter Parameter Value for Accounting Schema(To)	*/
	protected int getAcctSchemaIdTo() {
		return acctSchemaIdTo;
	}

	/**	 Setter Parameter Value for Accounting Schema(To)	*/
	protected void setAcctSchemaIdTo(int acctSchemaIdTo) {
		this.acctSchemaIdTo = acctSchemaIdTo;
	}

	/**	 Getter Parameter Value for Account Date	*/
	protected Timestamp getDateAcct() {
		return dateAcct;
	}

	/**	 Setter Parameter Value for Account Date	*/
	protected void setDateAcct(Timestamp dateAcct) {
		this.dateAcct = dateAcct;
	}

	/**	 Getter Parameter Value for Account Date(To)	*/
	protected Timestamp getDateAcctTo() {
		return dateAcctTo;
	}

	/**	 Setter Parameter Value for Account Date(To)	*/
	protected void setDateAcctTo(Timestamp dateAcctTo) {
		this.dateAcctTo = dateAcctTo;
	}

	/**	 Getter Parameter Value for Currency Type	*/
	protected int getConversionTypeId() {
		return conversionTypeId;
	}

	/**	 Setter Parameter Value for Currency Type	*/
	protected void setConversionTypeId(int conversionTypeId) {
		this.conversionTypeId = conversionTypeId;
	}

	/**	 Getter Parameter Value for Conversion Rate	*/
	protected int getConversionRateId() {
		return conversionRateId;
	}

	/**	 Setter Parameter Value for Conversion Rate	*/
	protected void setConversionRateId(int conversionRateId) {
		this.conversionRateId = conversionRateId;
	}

	/**	 Getter Parameter Value for Currency Convert Account	*/
	protected int getConvertAcct() {
		return convertAcct;
	}

	/**	 Setter Parameter Value for Currency Convert Account	*/
	protected void setConvertAcct(int convertAcct) {
		this.convertAcct = convertAcct;
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
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
import java.sql.Timestamp;
import org.compiere.process.SvrProcess;

/** Generated Process for (POS Bank Statement Close)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.4
 */
public abstract class SHW_POS_TransferAndCloseAbstract extends SvrProcess {
	/** Process Value 	*/
	private static final String VALUE_FOR_PROCESS = "SHW_POS_TransferAndClose";
	/** Process Name 	*/
	private static final String NAME_FOR_PROCESS = "POS Bank Statement Close";
	/** Process Id 	*/
	private static final int ID_FOR_PROCESS = 54967;
	/**	Parameter Name for Bank Account To	*/
	public static final String C_BANKACCOUNTTO_ID = "C_BankAccountTo_ID";
	/**	Parameter Name for Charge	*/
	public static final String C_CHARGE_ID = "C_Charge_ID";
	/**	Parameter Name for Transaction Date	*/
	public static final String DATETRX = "DateTrx";
	/**	Parameter Name for Statement difference	*/
	public static final String STATEMENTDIFFERENCE = "StatementDifference";
	/**	Parameter Name for Amount	*/
	public static final String AMOUNT = "Amount";
	/**	Parameter Name for Bank Account	*/
	public static final String C_BANKACCOUNT_ID = "C_BankAccount_ID";
	/**	Parameter Name for Tender type	*/
	public static final String TENDERTYPE = "TenderType";
	/**	Parameter Name for POS Terminal	*/
	public static final String C_POS_ID = "C_POS_ID";
	/**	Parameter Name for IsCloseDocument	*/
	public static final String ISCLOSEDOCUMENT = "IsCloseDocument";
	/**	Parameter Value for Bank Account To	*/
	private int bankAccountToId;
	/**	Parameter Value for Charge	*/
	private int chargeId;
	/**	Parameter Value for Transaction Date	*/
	private Timestamp dateTrx;
	/**	Parameter Value for Statement difference	*/
	private BigDecimal statementDifference;
	/**	Parameter Value for Amount	*/
	private BigDecimal amount;
	/**	Parameter Value for Bank Account	*/
	private int bankAccountId;
	/**	Parameter Value for Tender type	*/
	private String tenderType;
	/**	Parameter Value for POS Terminal	*/
	private int pOSId;
	/**	Parameter Value for IsCloseDocument	*/
	private boolean isCloseDocument;

	@Override
	protected void prepare() {
		bankAccountToId = getParameterAsInt(C_BANKACCOUNTTO_ID);
		chargeId = getParameterAsInt(C_CHARGE_ID);
		dateTrx = getParameterAsTimestamp(DATETRX);
		statementDifference = getParameterAsBigDecimal(STATEMENTDIFFERENCE);
		amount = getParameterAsBigDecimal(AMOUNT);
		bankAccountId = getParameterAsInt(C_BANKACCOUNT_ID);
		tenderType = getParameterAsString(TENDERTYPE);
		pOSId = getParameterAsInt(C_POS_ID);
		isCloseDocument = getParameterAsBoolean(ISCLOSEDOCUMENT);
	}

	/**	 Getter Parameter Value for Bank Account To	*/
	protected int getBankAccountToId() {
		return bankAccountToId;
	}

	/**	 Setter Parameter Value for Bank Account To	*/
	protected void setBankAccountToId(int bankAccountToId) {
		this.bankAccountToId = bankAccountToId;
	}

	/**	 Getter Parameter Value for Charge	*/
	protected int getChargeId() {
		return chargeId;
	}

	/**	 Setter Parameter Value for Charge	*/
	protected void setChargeId(int chargeId) {
		this.chargeId = chargeId;
	}

	/**	 Getter Parameter Value for Transaction Date	*/
	protected Timestamp getDateTrx() {
		return dateTrx;
	}

	/**	 Setter Parameter Value for Transaction Date	*/
	protected void setDateTrx(Timestamp dateTrx) {
		this.dateTrx = dateTrx;
	}

	/**	 Getter Parameter Value for Statement difference	*/
	protected BigDecimal getStatementDifference() {
		return statementDifference;
	}

	/**	 Setter Parameter Value for Statement difference	*/
	protected void setStatementDifference(BigDecimal statementDifference) {
		this.statementDifference = statementDifference;
	}

	/**	 Getter Parameter Value for Amount	*/
	protected BigDecimal getAmount() {
		return amount;
	}

	/**	 Setter Parameter Value for Amount	*/
	protected void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**	 Getter Parameter Value for Bank Account	*/
	protected int getBankAccountId() {
		return bankAccountId;
	}

	/**	 Setter Parameter Value for Bank Account	*/
	protected void setBankAccountId(int bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	/**	 Getter Parameter Value for Tender type	*/
	protected String getTenderType() {
		return tenderType;
	}

	/**	 Setter Parameter Value for Tender type	*/
	protected void setTenderType(String tenderType) {
		this.tenderType = tenderType;
	}

	/**	 Getter Parameter Value for POS Terminal	*/
	protected int getPOSId() {
		return pOSId;
	}

	/**	 Setter Parameter Value for POS Terminal	*/
	protected void setPOSId(int pOSId) {
		this.pOSId = pOSId;
	}

	/**	 Getter Parameter Value for IsCloseDocument	*/
	protected boolean isCloseDocument() {
		return isCloseDocument;
	}

	/**	 Setter Parameter Value for IsCloseDocument	*/
	protected void setIsCloseDocument(boolean isCloseDocument) {
		this.isCloseDocument = isCloseDocument;
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
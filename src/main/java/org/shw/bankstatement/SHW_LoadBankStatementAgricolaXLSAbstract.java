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

package org.shw.bankstatement;

import org.compiere.process.SvrProcess;

/** Generated Process for (SHW_LoadBankStatementAgricolaXLS)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.4
 */
public abstract class SHW_LoadBankStatementAgricolaXLSAbstract extends SvrProcess {
	/** Process Value 	*/
	private static final String VALUE_FOR_PROCESS = "SHW_LoadBankStatementAgricolaXLS";
	/** Process Name 	*/
	private static final String NAME_FOR_PROCESS = "SHW_LoadBankStatementAgricolaXLS";
	/** Process Id 	*/
	private static final int ID_FOR_PROCESS = 1000101;
	/**	Parameter Name for File Path or Name	*/
	public static final String FILEPATHORNAME = "FilePathOrName";
	/**	Parameter Name for Bank Account	*/
	public static final String C_BANKACCOUNT_ID = "C_BankAccount_ID";
	/**	Parameter Name for Delete old imported records	*/
	public static final String DELETEOLDIMPORTED = "DeleteOldImported";
	/**	Parameter Value for File Path or Name	*/
	private String filePathOrName;
	/**	Parameter Value for Bank Account	*/
	private int bankAccountId;
	/**	Parameter Value for Delete old imported records	*/
	private boolean isDeleteOldImported;

	@Override
	protected void prepare() {
		filePathOrName = getParameterAsString(FILEPATHORNAME);
		bankAccountId = getParameterAsInt(C_BANKACCOUNT_ID);
		isDeleteOldImported = getParameterAsBoolean(DELETEOLDIMPORTED);
	}

	/**	 Getter Parameter Value for File Path or Name	*/
	protected String getFilePathOrName() {
		return filePathOrName;
	}

	/**	 Setter Parameter Value for File Path or Name	*/
	protected void setFilePathOrName(String filePathOrName) {
		this.filePathOrName = filePathOrName;
	}

	/**	 Getter Parameter Value for Bank Account	*/
	protected int getBankAccountId() {
		return bankAccountId;
	}

	/**	 Setter Parameter Value for Bank Account	*/
	protected void setBankAccountId(int bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	/**	 Getter Parameter Value for Delete old imported records	*/
	protected boolean isDeleteOldImported() {
		return isDeleteOldImported;
	}

	/**	 Setter Parameter Value for Delete old imported records	*/
	protected void setDeleteOldImported(boolean isDeleteOldImported) {
		this.isDeleteOldImported = isDeleteOldImported;
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
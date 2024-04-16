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

/** Generated Process for (Copy Entities with new Currency)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.3
 */
public abstract class CopyEntitiesWithNewCurrencyAbstract extends SvrProcess {
	/** Process Value 	*/
	private static final String VALUE_FOR_PROCESS = "CopyEntitiesWithNewCurrency";
	/** Process Name 	*/
	private static final String NAME_FOR_PROCESS = "Copy Entities with new Currency";
	/** Process Id 	*/
	private static final int ID_FOR_PROCESS = 54563;
	/**	Parameter Name for Currency	*/
	public static final String C_CURRENCY_ID = "C_Currency_ID";
	/**	Parameter Name for Currency To	*/
	public static final String C_CURRENCY_ID_TO = "C_Currency_ID_To";
	/**	Parameter Name for Suffix	*/
	public static final String SUFFIX = "Suffix";
	/**	Parameter Name for Currency Type	*/
	public static final String C_CONVERSIONTYPE_ID = "C_ConversionType_ID";
	/**	Parameter Name for Transaction Date	*/
	public static final String DATETRX = "DateTrx";
	/**	Parameter Name for Reconvert Price Lists	*/
	public static final String ISRECONVERTPRICELISTS = "IsReconvertPriceLists";
	/**	Parameter Name for Reconvert Bank Accounts	*/
	public static final String ISRECONVERTBANKACCOUNTS = "IsReconvertBankAccounts";
	/**	Parameter Name for Reconvert Cash Books	*/
	public static final String ISRECONVERTCASHBOOKS = "IsReconvertCashBooks";
	/**	Parameter Name for Reconvert Cash Flows	*/
	public static final String ISRECONVERTCASHFLOWS = "IsReconvertCashFlows";
	/**	Parameter Name for Reconvert Point Of Sales	*/
	public static final String ISRECONVERTPOS = "IsReconvertPOS";
	/**	Parameter Name for Reconvert Commission Definition	*/
	public static final String ISRECONVERTCOMMISSIONS = "IsReconvertCommissions";
	/**	Parameter Name for Reconvert Freights	*/
	public static final String ISRECONVERTFREIGHTS = "IsReconvertFreights";
	/**	Parameter Name for Reconvert Payroll Concepts	*/
	public static final String ISRECONVERTPAYROLLCONCEPTS = "IsReconvertPayrollConcepts";
	/**	Parameter Name for Reconvert Purchase Products	*/
	public static final String ISRECONVERTPRODUCTPOS = "IsReconvertProductPOs";
	/**	Parameter Name for Manage Closing Operations	*/
	public static final String ISMANAGECLOSING = "IsManageClosing";
	/**	Parameter Name for Business Partner 	*/
	public static final String C_BPARTNER_ID = "C_BPartner_ID";
	/**	Parameter Name for Close Payrolls	*/
	public static final String ISRECONVERTCLOSEPAYROLLS = "IsReconvertClosePayrolls";
	/**	Parameter Name for Close Bank Accounts	*/
	public static final String ISRECONVERTCLOSEBANKACCOUNTS = "IsReconvertCloseBankAccounts";
	/**	Parameter Name for Disable Old Bank Accounts	*/
	public static final String ISDISABLEOLDBANKACCOUNTS = "IsDisableOldBankAccounts";
	/**	Parameter Name for Setup new Currency	*/
	public static final String ISRECONVERTSETUPCURRENCY = "IsReconvertSetupCurrency";
	/**	Parameter Value for Currency	*/
	private int currencyId;
	/**	Parameter Value for Currency To	*/
	private int currencyToId;
	/**	Parameter Value for Suffix	*/
	private String suffix;
	/**	Parameter Value for Currency Type	*/
	private int conversionTypeId;
	/**	Parameter Value for Transaction Date	*/
	private Timestamp dateTrx;
	/**	Parameter Value for Reconvert Price Lists	*/
	private boolean isReconvertPriceLists;
	/**	Parameter Value for Reconvert Bank Accounts	*/
	private boolean isReconvertBankAccounts;
	/**	Parameter Value for Reconvert Cash Books	*/
	private boolean isReconvertCashBooks;
	/**	Parameter Value for Reconvert Cash Flows	*/
	private boolean isReconvertCashFlows;
	/**	Parameter Value for Reconvert Point Of Sales	*/
	private boolean isReconvertPOS;
	/**	Parameter Value for Reconvert Commission Definition	*/
	private boolean isReconvertCommissions;
	/**	Parameter Value for Reconvert Freights	*/
	private boolean isReconvertFreights;
	/**	Parameter Value for Reconvert Payroll Concepts	*/
	private boolean isReconvertPayrollConcepts;
	/**	Parameter Value for Reconvert Purchase Products	*/
	private boolean isReconvertProductPOs;
	/**	Parameter Value for Manage Closing Operations	*/
	private String isManageClosing;
	/**	Parameter Value for Business Partner 	*/
	private int bPartnerId;
	/**	Parameter Value for Close Payrolls	*/
	private boolean isReconvertClosePayrolls;
	/**	Parameter Value for Close Bank Accounts	*/
	private boolean isReconvertCloseBankAccounts;
	/**	Parameter Value for Disable Old Bank Accounts	*/
	private boolean isDisableOldBankAccounts;
	/**	Parameter Value for Setup new Currency	*/
	private boolean isReconvertSetupCurrency;

	@Override
	protected void prepare() {
		currencyId = getParameterAsInt(C_CURRENCY_ID);
		currencyToId = getParameterAsInt(C_CURRENCY_ID_TO);
		suffix = getParameterAsString(SUFFIX);
		conversionTypeId = getParameterAsInt(C_CONVERSIONTYPE_ID);
		dateTrx = getParameterAsTimestamp(DATETRX);
		isReconvertPriceLists = getParameterAsBoolean(ISRECONVERTPRICELISTS);
		isReconvertBankAccounts = getParameterAsBoolean(ISRECONVERTBANKACCOUNTS);
		isReconvertCashBooks = getParameterAsBoolean(ISRECONVERTCASHBOOKS);
		isReconvertCashFlows = getParameterAsBoolean(ISRECONVERTCASHFLOWS);
		isReconvertPOS = getParameterAsBoolean(ISRECONVERTPOS);
		isReconvertCommissions = getParameterAsBoolean(ISRECONVERTCOMMISSIONS);
		isReconvertFreights = getParameterAsBoolean(ISRECONVERTFREIGHTS);
		isReconvertPayrollConcepts = getParameterAsBoolean(ISRECONVERTPAYROLLCONCEPTS);
		isReconvertProductPOs = getParameterAsBoolean(ISRECONVERTPRODUCTPOS);
		isManageClosing = getParameterAsString(ISMANAGECLOSING);
		bPartnerId = getParameterAsInt(C_BPARTNER_ID);
		isReconvertClosePayrolls = getParameterAsBoolean(ISRECONVERTCLOSEPAYROLLS);
		isReconvertCloseBankAccounts = getParameterAsBoolean(ISRECONVERTCLOSEBANKACCOUNTS);
		isDisableOldBankAccounts = getParameterAsBoolean(ISDISABLEOLDBANKACCOUNTS);
		isReconvertSetupCurrency = getParameterAsBoolean(ISRECONVERTSETUPCURRENCY);
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
	protected int getCurrencyToId() {
		return currencyToId;
	}

	/**	 Setter Parameter Value for Currency To	*/
	protected void setCurrencyToId(int currencyToId) {
		this.currencyToId = currencyToId;
	}

	/**	 Getter Parameter Value for Suffix	*/
	protected String getSuffix() {
		return suffix;
	}

	/**	 Setter Parameter Value for Suffix	*/
	protected void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	/**	 Getter Parameter Value for Currency Type	*/
	protected int getConversionTypeId() {
		return conversionTypeId;
	}

	/**	 Setter Parameter Value for Currency Type	*/
	protected void setConversionTypeId(int conversionTypeId) {
		this.conversionTypeId = conversionTypeId;
	}

	/**	 Getter Parameter Value for Transaction Date	*/
	protected Timestamp getDateTrx() {
		return dateTrx;
	}

	/**	 Setter Parameter Value for Transaction Date	*/
	protected void setDateTrx(Timestamp dateTrx) {
		this.dateTrx = dateTrx;
	}

	/**	 Getter Parameter Value for Reconvert Price Lists	*/
	protected boolean isReconvertPriceLists() {
		return isReconvertPriceLists;
	}

	/**	 Setter Parameter Value for Reconvert Price Lists	*/
	protected void setIsReconvertPriceLists(boolean isReconvertPriceLists) {
		this.isReconvertPriceLists = isReconvertPriceLists;
	}

	/**	 Getter Parameter Value for Reconvert Bank Accounts	*/
	protected boolean isReconvertBankAccounts() {
		return isReconvertBankAccounts;
	}

	/**	 Setter Parameter Value for Reconvert Bank Accounts	*/
	protected void setIsReconvertBankAccounts(boolean isReconvertBankAccounts) {
		this.isReconvertBankAccounts = isReconvertBankAccounts;
	}

	/**	 Getter Parameter Value for Reconvert Cash Books	*/
	protected boolean isReconvertCashBooks() {
		return isReconvertCashBooks;
	}

	/**	 Setter Parameter Value for Reconvert Cash Books	*/
	protected void setIsReconvertCashBooks(boolean isReconvertCashBooks) {
		this.isReconvertCashBooks = isReconvertCashBooks;
	}

	/**	 Getter Parameter Value for Reconvert Cash Flows	*/
	protected boolean isReconvertCashFlows() {
		return isReconvertCashFlows;
	}

	/**	 Setter Parameter Value for Reconvert Cash Flows	*/
	protected void setIsReconvertCashFlows(boolean isReconvertCashFlows) {
		this.isReconvertCashFlows = isReconvertCashFlows;
	}

	/**	 Getter Parameter Value for Reconvert Point Of Sales	*/
	protected boolean isReconvertPOS() {
		return isReconvertPOS;
	}

	/**	 Setter Parameter Value for Reconvert Point Of Sales	*/
	protected void setIsReconvertPOS(boolean isReconvertPOS) {
		this.isReconvertPOS = isReconvertPOS;
	}

	/**	 Getter Parameter Value for Reconvert Commission Definition	*/
	protected boolean isReconvertCommissions() {
		return isReconvertCommissions;
	}

	/**	 Setter Parameter Value for Reconvert Commission Definition	*/
	protected void setIsReconvertCommissions(boolean isReconvertCommissions) {
		this.isReconvertCommissions = isReconvertCommissions;
	}

	/**	 Getter Parameter Value for Reconvert Freights	*/
	protected boolean isReconvertFreights() {
		return isReconvertFreights;
	}

	/**	 Setter Parameter Value for Reconvert Freights	*/
	protected void setIsReconvertFreights(boolean isReconvertFreights) {
		this.isReconvertFreights = isReconvertFreights;
	}

	/**	 Getter Parameter Value for Reconvert Payroll Concepts	*/
	protected boolean isReconvertPayrollConcepts() {
		return isReconvertPayrollConcepts;
	}

	/**	 Setter Parameter Value for Reconvert Payroll Concepts	*/
	protected void setIsReconvertPayrollConcepts(boolean isReconvertPayrollConcepts) {
		this.isReconvertPayrollConcepts = isReconvertPayrollConcepts;
	}

	/**	 Getter Parameter Value for Reconvert Purchase Products	*/
	protected boolean isReconvertProductPOs() {
		return isReconvertProductPOs;
	}

	/**	 Setter Parameter Value for Reconvert Purchase Products	*/
	protected void setIsReconvertProductPOs(boolean isReconvertProductPOs) {
		this.isReconvertProductPOs = isReconvertProductPOs;
	}

	/**	 Getter Parameter Value for Manage Closing Operations	*/
	protected String getIsManageClosing() {
		return isManageClosing;
	}

	/**	 Setter Parameter Value for Manage Closing Operations	*/
	protected void setIsManageClosing(String isManageClosing) {
		this.isManageClosing = isManageClosing;
	}

	/**	 Getter Parameter Value for Business Partner 	*/
	protected int getBPartnerId() {
		return bPartnerId;
	}

	/**	 Setter Parameter Value for Business Partner 	*/
	protected void setBPartnerId(int bPartnerId) {
		this.bPartnerId = bPartnerId;
	}

	/**	 Getter Parameter Value for Close Payrolls	*/
	protected boolean isReconvertClosePayrolls() {
		return isReconvertClosePayrolls;
	}

	/**	 Setter Parameter Value for Close Payrolls	*/
	protected void setIsReconvertClosePayrolls(boolean isReconvertClosePayrolls) {
		this.isReconvertClosePayrolls = isReconvertClosePayrolls;
	}

	/**	 Getter Parameter Value for Close Bank Accounts	*/
	protected boolean isReconvertCloseBankAccounts() {
		return isReconvertCloseBankAccounts;
	}

	/**	 Setter Parameter Value for Close Bank Accounts	*/
	protected void setIsReconvertCloseBankAccounts(boolean isReconvertCloseBankAccounts) {
		this.isReconvertCloseBankAccounts = isReconvertCloseBankAccounts;
	}

	/**	 Getter Parameter Value for Disable Old Bank Accounts	*/
	protected boolean isDisableOldBankAccounts() {
		return isDisableOldBankAccounts;
	}

	/**	 Setter Parameter Value for Disable Old Bank Accounts	*/
	protected void setIsDisableOldBankAccounts(boolean isDisableOldBankAccounts) {
		this.isDisableOldBankAccounts = isDisableOldBankAccounts;
	}

	/**	 Getter Parameter Value for Setup new Currency	*/
	protected boolean isReconvertSetupCurrency() {
		return isReconvertSetupCurrency;
	}

	/**	 Setter Parameter Value for Setup new Currency	*/
	protected void setIsReconvertSetupCurrency(boolean isReconvertSetupCurrency) {
		this.isReconvertSetupCurrency = isReconvertSetupCurrency;
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
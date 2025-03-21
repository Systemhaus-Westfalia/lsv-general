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

package org.shw.lsv.einvoice.process;

import org.compiere.process.SvrProcess;

/** Generated Process for (EInvoiceGenerateAndPost)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.4
 */
public abstract class EInvoiceGenerateAndPostAbstract extends SvrProcess {
	/** Process Value 	*/
	private static final String VALUE_FOR_PROCESS = "EInvoiceGenerateAndPost";
	/** Process Name 	*/
	private static final String NAME_FOR_PROCESS = "EInvoiceGenerateAndPost";
	/** Process Id 	*/
	private static final int ID_FOR_PROCESS = 54638;
	/**	Parameter Name for Client	*/
	public static final String AD_CLIENT_ID = "AD_Client_ID";
	/**	Parameter Name for Invoice	*/
	public static final String C_INVOICE_ID = "C_Invoice_ID";
	/**	Parameter Name for Mail Template	*/
	public static final String R_MAILTEXT_ID = "R_MailText_ID";
	/**	Parameter Name for Direct print	*/
	public static final String ISDIRECTPRINT = "IsDirectPrint";
	/**	Parameter Name for Record ID	*/
	public static final String RECORD_ID = "Record_ID";
	/**	Parameter Value for Client	*/
	private int clientId;
	/**	Parameter Value for Invoice	*/
	private int invoiceId;
	/**	Parameter Value for Mail Template	*/
	private int mailTextId;
	/**	Parameter Value for Direct print	*/
	private boolean isDirectPrint;
	/**	Parameter Value for Record ID	*/
	private Object record;

	@Override
	protected void prepare() {
		clientId = getParameterAsInt(AD_CLIENT_ID);
		invoiceId = getParameterAsInt(C_INVOICE_ID);
		mailTextId = getParameterAsInt(R_MAILTEXT_ID);
		isDirectPrint = getParameterAsBoolean(ISDIRECTPRINT);
		record = getParameter(RECORD_ID);
	}

	/**	 Getter Parameter Value for Client	*/
	protected int getClientId() {
		return clientId;
	}

	/**	 Setter Parameter Value for Client	*/
	protected void setClientId(int clientId) {
		this.clientId = clientId;
	}

	/**	 Getter Parameter Value for Invoice	*/
	protected int getInvoiceId() {
		return invoiceId;
	}

	/**	 Setter Parameter Value for Invoice	*/
	protected void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	/**	 Getter Parameter Value for Mail Template	*/
	protected int getMailTextId() {
		return mailTextId;
	}

	/**	 Setter Parameter Value for Mail Template	*/
	protected void setMailTextId(int mailTextId) {
		this.mailTextId = mailTextId;
	}

	/**	 Getter Parameter Value for Direct print	*/
	protected boolean isDirectPrint() {
		return isDirectPrint;
	}

	/**	 Setter Parameter Value for Direct print	*/
	protected void setIsDirectPrint(boolean isDirectPrint) {
		this.isDirectPrint = isDirectPrint;
	}

	/**	 Getter Parameter Value for Record ID	*/
	protected Object getRecord() {
		return record;
	}

	/**	 Setter Parameter Value for Record ID	*/
	protected void setRecord(Object record) {
		this.record = record;
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
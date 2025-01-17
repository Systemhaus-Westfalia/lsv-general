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

/** Generated Process for (EI_C_Invoice_Print)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.4
 */
public abstract class EI_C_Invoice_PrintAbstract extends SvrProcess {
	/** Process Value 	*/
	private static final String VALUE_FOR_PROCESS = "EI_C_Invoice_Print";
	/** Process Name 	*/
	private static final String NAME_FOR_PROCESS = "EI_C_Invoice_Print";
	/** Process Id 	*/
	private static final int ID_FOR_PROCESS = 1000084;
	/**	Parameter Name for Mail Template	*/
	public static final String R_MAILTEXT_ID = "R_MailText_ID";
	/**	Parameter Name for Record ID	*/
	public static final String RECORD_ID = "Record_ID";
	/**	Parameter Value for Mail Template	*/
	private int mailTextId;
	/**	Parameter Value for Record ID	*/
	private int recordId;

	@Override
	protected void prepare() {
		mailTextId = getParameterAsInt(R_MAILTEXT_ID);
		recordId = getParameterAsInt(RECORD_ID);
	}

	/**	 Getter Parameter Value for Mail Template	*/
	protected int getMailTextId() {
		return mailTextId;
	}

	/**	 Setter Parameter Value for Mail Template	*/
	protected void setMailTextId(int mailTextId) {
		this.mailTextId = mailTextId;
	}

	/**	 Getter Parameter Value for Record ID	*/
	protected int getRecordId() {
		return recordId;
	}

	/**	 Setter Parameter Value for Record ID	*/
	protected void setRecordId(int recordId) {
		this.recordId = recordId;
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
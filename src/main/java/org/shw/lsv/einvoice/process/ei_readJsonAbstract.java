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

/** Generated Process for (ei_readJson)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.4
 */
public abstract class ei_readJsonAbstract extends SvrProcess {
	/** Process Value 	*/
	private static final String VALUE_FOR_PROCESS = "ei_readJson";
	/** Process Name 	*/
	private static final String NAME_FOR_PROCESS = "ei_readJson";
	/** Process Id 	*/
	private static final int ID_FOR_PROCESS = 54940;
	/**	Parameter Name for File Path or Name	*/
	public static final String FILEPATHORNAME = "FilePathOrName";
	/**	Parameter Name for Charge	*/
	public static final String C_CHARGE_ID = "C_Charge_ID";
	/**	Parameter Name for Order	*/
	public static final String C_ORDER_ID = "C_Order_ID";
	/**	Parameter Name for Business Partner 	*/
	public static final String C_BPARTNER_ID = "C_BPartner_ID";
	/**	Parameter Value for File Path or Name	*/
	private String filePathOrName;
	/**	Parameter Value for Charge	*/
	private int chargeId;
	/**	Parameter Value for Order	*/
	private int orderId;
	/**	Parameter Value for Business Partner 	*/
	private int bPartnerId;

	@Override
	protected void prepare() {
		filePathOrName = getParameterAsString(FILEPATHORNAME);
		chargeId = getParameterAsInt(C_CHARGE_ID);
		orderId = getParameterAsInt(C_ORDER_ID);
		bPartnerId = getParameterAsInt(C_BPARTNER_ID);
	}

	/**	 Getter Parameter Value for File Path or Name	*/
	protected String getFilePathOrName() {
		return filePathOrName;
	}

	/**	 Setter Parameter Value for File Path or Name	*/
	protected void setFilePathOrName(String filePathOrName) {
		this.filePathOrName = filePathOrName;
	}

	/**	 Getter Parameter Value for Charge	*/
	protected int getChargeId() {
		return chargeId;
	}

	/**	 Setter Parameter Value for Charge	*/
	protected void setChargeId(int chargeId) {
		this.chargeId = chargeId;
	}

	/**	 Getter Parameter Value for Order	*/
	protected int getOrderId() {
		return orderId;
	}

	/**	 Setter Parameter Value for Order	*/
	protected void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	/**	 Getter Parameter Value for Business Partner 	*/
	protected int getBPartnerId() {
		return bPartnerId;
	}

	/**	 Setter Parameter Value for Business Partner 	*/
	protected void setBPartnerId(int bPartnerId) {
		this.bPartnerId = bPartnerId;
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
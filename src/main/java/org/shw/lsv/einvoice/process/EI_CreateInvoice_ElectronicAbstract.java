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

/** Generated Process for (EI_CreateInvoice_Electronic)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.4
 */
public abstract class EI_CreateInvoice_ElectronicAbstract extends SvrProcess {
	/** Process Value 	*/
	private static final String VALUE_FOR_PROCESS = "EI_CreateInvoice_Electronic";
	/** Process Name 	*/
	private static final String NAME_FOR_PROCESS = "EI_CreateInvoice_Electronic";
	/** Process Id 	*/
	private static final int ID_FOR_PROCESS = 54637;
	/**	Parameter Name for Invoice	*/
	public static final String C_INVOICE_ID = "C_Invoice_ID";
	/**	Parameter Name for Shipment/Receipt	*/
	public static final String M_INOUT_ID = "M_InOut_ID";
	/**	Parameter Name for Movement	*/
	public static final String M_MOVEMENT_ID = "M_Movement_ID";
	/**	Parameter Value for Invoice	*/
	private int invoiceId;
	/**	Parameter Value for Shipment/Receipt	*/
	private int inOutId;
	/**	Parameter Value for Movement	*/
	private int movementId;

	@Override
	protected void prepare() {
		invoiceId = getParameterAsInt(C_INVOICE_ID);
		inOutId = getParameterAsInt(M_INOUT_ID);
		movementId = getParameterAsInt(M_MOVEMENT_ID);
	}

	/**	 Getter Parameter Value for Invoice	*/
	protected int getInvoiceId() {
		return invoiceId;
	}

	/**	 Setter Parameter Value for Invoice	*/
	protected void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	/**	 Getter Parameter Value for Shipment/Receipt	*/
	protected int getInOutId() {
		return inOutId;
	}

	/**	 Setter Parameter Value for Shipment/Receipt	*/
	protected void setInOutId(int inOutId) {
		this.inOutId = inOutId;
	}

	/**	 Getter Parameter Value for Movement	*/
	protected int getMovementId() {
		return movementId;
	}

	/**	 Setter Parameter Value for Movement	*/
	protected void setMovementId(int movementId) {
		this.movementId = movementId;
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

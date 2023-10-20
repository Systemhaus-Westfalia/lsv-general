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

import org.adempiere.core.domains.models.X_E_InvoiceElectronic;
import org.compiere.model.MInvoice;
import org.shw.lsv.einvoice.utils.EDocument;
import org.shw.lsv.util.support.findex.ElectronicInvoice;

/** Generated Process for (EI_CreateInvoice_Electronic)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.4
 */
public class EI_CreateInvoice_Electronic extends EI_CreateInvoice_ElectronicAbstract
{	
	@Override
	protected void prepare()
	{
		super.prepare();
	}

	@Override
	protected String doIt() throws Exception
	{	
		System.out.println("Process EI_CreateInvoice_Electronic: started");
		
		MInvoice invoice = new MInvoice(getCtx(), getInvoiceId(), get_TrxName());
		System.out.println("Process EI_CreateInvoice_Electronic: Started with Invoice " + invoice.getDocumentNo());

		ElectronicInvoice electronicInvoice = new ElectronicInvoice(invoice);
		X_E_InvoiceElectronic electronicInvoiceModel = electronicInvoice.processElectronicInvoice();
		if(electronicInvoiceModel==null) {
	    	System.out.println("Error(s) en Process EI_CreateInvoice_Electronic: " + electronicInvoice.getErrorMsg());
			System.out.println("Process EI_CreateInvoice_Electronic: finished");
			return electronicInvoice.getErrorMsg();
		} else  {
	    	System.out.println("NO ERRORS in Process EI_CreateInvoice_Electronic");
		}
		
    	String documentAsJsonString = electronicInvoiceModel.getjson();
    	
    	if (isSaveInHistoric()) {
    		if (!electronicInvoice.getDocumentFactory().writeToFile(documentAsJsonString, invoice, EDocument.ABSDIRECTORY)) {
    			electronicInvoiceModel.seterrMsgIntern("Root File From MSystConfig EI_PATH does not exist");
    		}
    	}
		
    	System.out.println("Credito Fiscal generado: " + invoice.getDocumentNo() + "Estado: " + electronicInvoiceModel.getei_ValidationStatus());
		System.out.println("Process EI_CreateInvoice_Electronic : finished");
		return "OK";
	}


}
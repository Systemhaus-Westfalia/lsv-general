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

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.adempiere.core.domains.models.I_C_Invoice;
import org.adempiere.core.domains.models.X_E_InvoiceElectronic;
import org.compiere.model.MInvoice;
import org.compiere.model.PO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.shw.lsv.einvoice.utils.EDocument;
import org.shw.lsv.util.support.IDeclarationDocument;
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
		IDeclarationDocument declarationDocument = getDeclarationDocument(invoice);
		if(declarationDocument == null) {
			return null;
		}
		X_E_InvoiceElectronic electronicInvoiceModel = declarationDocument.processElectronicInvoice();
		if(electronicInvoiceModel==null) {
			return null;
		}

		String documentAsJsonString = electronicInvoiceModel.getjson();
		Entity<String> entity = Entity.json(documentAsJsonString);
		{
			invoice.saveEx();
		}
		return "OK";
	}
	
        
        public IDeclarationDocument getDeclarationDocument(PO entity) {
    		if(entity == null) {
    			return null;
    		}
    		if(entity.get_TableName().equals(I_C_Invoice.Table_Name)) {
    			return new ElectronicInvoice((MInvoice) entity);
    		}
    		return null;
    	}


}
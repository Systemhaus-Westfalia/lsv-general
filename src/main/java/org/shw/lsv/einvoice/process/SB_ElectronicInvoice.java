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

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.adempiere.core.domains.models.*;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.process.ProcessInfo;
import org.eevolution.services.dsl.ProcessBuilder;

/** Generated Process for (SB_ElectronicInvoice)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.4
 */
public class SB_ElectronicInvoice extends SB_ElectronicInvoiceAbstract
{
	ArrayList<String> result = new ArrayList<String>();
	@Override
	protected void prepare()
	{
		super.prepare();
	}
	

	@Override
	protected String doIt() 
	{
		List<MInvoice> invoices = (List<MInvoice>) getInstancesForSelection(get_TrxName());
		Hashtable<Integer, MInvoice> invoicesList = new Hashtable<>();
		invoices.forEach(invoice -> {

			if (!invoicesList.contains(invoice.get_ID()))
				invoicesList.put(invoice.get_ID(), invoice);
		});

		invoicesList.forEach((key, invoice) -> {
			MDocType docType = (MDocType)invoice.getC_DocType();
			if (docType.get_ValueAsInt(X_E_DocType.COLUMNNAME_E_DocType_ID)> 0) {
				
				processingInvoice(invoice);
			}
			
		});;
		return result.toString();
	}
	
	
	private void processingInvoice(MInvoice invoice) {

            ProcessInfo processInfo = ProcessBuilder.create(getCtx())
                    .process(EI_CreateInvoice_Electronic.getProcessId())
                    .withParameter(MInvoice.COLUMNNAME_C_Invoice_ID, invoice.getC_Invoice_ID())
                    .withParameter(ISSAVEINHISTORIC, isSaveInHistoric())
                    .withoutTransactionClose()
                    .execute(get_TrxName());
            if (processInfo.isError()) {

                throw new AdempiereException(processInfo.getSummary());
            }

            addLog(processInfo.getSummary());

            result.add(invoice.getDocumentNo());
            /*Arrays.stream(processInfo.getIDs()).forEach(recordId -> {
                Optional<MMovement> maybeMovement = Optional.ofNullable(new MMovement(getCtx(), recordId, get_TrxName()));
                maybeMovement.ifPresent(movement -> {
                    documentCreated++;
                    printDocument(movement, true);
                });
            });*/
    }
}
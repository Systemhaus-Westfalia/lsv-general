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

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.shw.lsv.util.support.provider.Findex;
import org.spin.model.MADAppRegistration;

/** Generated Process for (EInvoiceGenerateAndPost_Voided)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.4
 */
public class EInvoiceGenerateAndPost_Voided extends EInvoiceGenerateAndPost_VoidedAbstract
{

	public static final String APPLICATION_TYPE = "LSV_VOIDED";
	public StringBuffer errorMessages = new StringBuffer();
	@Override
	protected void prepare()
	{
		super.prepare();
	}

	@Override
	protected String doIt() throws Exception
	{
		String errorMessage= "";
		MClient client = new MClient(getCtx(),getClientId(), get_TrxName());
		System.out.println("\n" + "******************************************************");
		System.out.println("Process EInvoiceGenerateAndPost: started with Client '" + client.getName() + "', ID: " + getClientId());
		MADAppRegistration registration = new Query(getCtx(), MADAppRegistration.Table_Name, "EXISTS(SELECT 1 FROM AD_AppSupport s "
				+ "WHERE s.AD_AppSupport_ID = AD_AppRegistration.AD_AppSupport_ID "
				+ "AND s.ApplicationType = ?"
				+ "AND s.IsActive = 'Y'"
				+ "AND s.Classname = ?)", get_TrxName())
				.setParameters(APPLICATION_TYPE, Findex.class.getName())
				.<MADAppRegistration>first();

		if(registration==null) {
			errorMessage = "Process EInvoiceGenerateAndPost : no registration for Application Type " + APPLICATION_TYPE;
			errorMessages.append(errorMessage);
			System.out.println(errorMessage);
			return errorMessage.toString();
		}

		Findex findex = new Findex();
		findex.setAppRegistrationId(registration.getAD_AppRegistration_ID() );
		Timestamp startdate = (Timestamp)(client.get_Value("ei_Startdate"));
		String whereClause = "AD_CLIENT_ID = ?  "
				+ " AND Exists (select 1 from c_Doctype dt where dt.c_Doctype_ID=c_Invoice.c_Doctype_ID AND E_DocType_ID is not null) "
				+ " AND processed = 'Y' AND dateacct>=?  AND processing = 'N' "
				+ " AND ei_Processing = 'N' "
				+ " AND ((docstatus IN ('CO','CL') OR coalesce(reversal_ID,0) > c_Invoice_ID) "
				+ "	OR (docstatus in ('VO','RE') AND coalesce(reversal_ID,0) < c_Invoice_ID " 
				+ "	AND (select ei_Status_Extern from c_Invoice i where c_Invoice.reversal_ID=i.c_Invoice_ID) = 'Firmado')) "
				+ " AND (ei_Status_Extern is NULL OR ei_Status_Extern <> 'Firmado')";
	
		try {
			int[] invoiceIds = new Query(Env.getCtx(), MInvoice.Table_Name, whereClause, null)
						.setParameters(getClientId(), startdate)
						.getIDs();
			final int length = invoiceIds.length;
			if(length==0) {
				System.out.println("****************** Process EInvoiceGenerateAndPost: There is no invoice to process!!!");
				System.out.println("Process EInvoiceGenerateAndPost: finished" + "\n");
				return "OK";
			}

			System.out.println("Collecting invoices to be processed..."); 
			Trx updateTransaction = Trx.get("UpdateDB_ei_Processing", true);  
			StringBuffer sqlUpdate = new StringBuffer("UPDATE C_Invoice set ei_Processing = 'Y' WHERE c_INvoice_ID in (");
			String character = ",";
			for ( int i = 0; i < length; i++) {
				character = i<length-1? ",": ")";
				int invoiceID = invoiceIds[i];
				sqlUpdate.append(invoiceID + character);
				System.out.println("InvoiceID to be processed: " + invoiceID); 
			}
			System.out.println("Set 'processing' flag so invoices cannot be processed by other processes..."+ "\n"); 
			DB.executeUpdateEx(sqlUpdate.toString(), updateTransaction.getTrxName());
			if (updateTransaction != null) {
				updateTransaction.commit(true);
				updateTransaction.close();
			}
			
			AtomicInteger counter = new AtomicInteger(0);

			Arrays.stream(invoiceIds)
			.filter(invoiceId -> invoiceId > 0)
				.forEach(invoiceId -> {
					 Trx dbTransaction = null;
					try {
						counter.getAndIncrement();
						System.out.println("Start invoice No. " + counter + " of " + length); 
						Integer id = (Integer)invoiceId;
	                    dbTransaction = Trx.get(id.toString(), true);   
						MInvoice invoice = new MInvoice(Env.getCtx(), invoiceId, dbTransaction.getTrxName());
						findex.publishDocument(invoice);
						invoice.set_ValueOfColumn("ei_Processing", false);
						invoice.saveEx();
	                    if (dbTransaction != null) {
	                        dbTransaction.commit(true);
	                        dbTransaction.close();
	                    }
	                    System.out.println("End invoice No. " + counter + " of " + length+ "\n"+ "\n");
					} catch (Exception e) {
						String error = "Error al procesar documento #" + invoiceId + " " + e;
						System.out.println(error);
					}
					finally {
						if (dbTransaction != null) {							
	                        dbTransaction.close();
	                    }
					}
					System.out.println("Publish document successful"); 
				});
		}
		catch (Exception e) {
			System.out.println("Process EInvoiceGenerateAndPost: error " + e);
		}
		System.out.println("Process EInvoiceGenerateAndPost: finished");
		System.out.println("******************************************************" + "\n");
		return "OK";
	}
}
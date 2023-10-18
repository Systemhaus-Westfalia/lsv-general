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

import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.shw.lsv.util.support.findex.Findex;
import org.spin.model.MADAppRegistration;

/** Generated Process for (EI_CreateInvoice_Electronic)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.4
 */
public class EInvoiceGenerateAndPost extends EInvoiceGenerateAndPostAbstract
{
	public static final String APPLICATION_TYPE = "LSV";
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
		System.out.println("Process EInvoiceGenerateAndPost: started with Client " + getClientId());
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
		MClient client = new MClient(getCtx(),getClientId(), get_TrxName());
		Timestamp startdate = (Timestamp)(client.get_Value("ei_Startdate"));
		String whereClause = "AD_CLIENT_ID = ?  "
				+ " AND Exists (select 1 from c_Doctype dt where dt.c_Doctype_ID=c_Invoice.c_Doctype_ID AND E_DocType_ID is not null) "
				+ " AND processed = 'Y' AND dateacct>=? "
				+ " AND ei_Processing = 'N' "
				+ " AND (ei_Status_Extern is NULL OR ei_Status_Extern <> 'Firmado')";
	
		try {
			int[] invoiceIds = new Query(Env.getCtx(), MInvoice.Table_Name, whereClause, null)
						.setParameters(getClientId(), startdate)
						.getIDs();
			Arrays.stream(invoiceIds)
			.filter(invoiceId -> invoiceId > 0)
				.forEach(invoiceId -> {
					try {
						Integer id = (Integer)invoiceId;
	                    Trx dbTransaction = Trx.get(id.toString(), true);   
						MInvoice invoice = new MInvoice(Env.getCtx(), invoiceId, dbTransaction.getTrxName());
						invoice.set_ValueOfColumn("ei_Processing", true);
						invoice.saveEx();
						dbTransaction.commit(true);
						findex.publishDocument(invoice);
						invoice.set_ValueOfColumn("ei_Processing", false);
						invoice.saveEx();
	                    if (dbTransaction != null) {
	                        dbTransaction.commit(true);
	                        dbTransaction.close();
	                    }
						// TODO: set EIProcessing == false
					} catch (Exception e) {
						String error = "Error al procesar documento #" + invoiceId + " " + e;
						System.out.println(error);
					}

					System.out.println("Publish document successful"); 


				});

			
		}
		catch (Exception e) {
			
		}
		System.out.println("Process EInvoiceGenerateAndPost : finished");
		return "OK";
	}


}
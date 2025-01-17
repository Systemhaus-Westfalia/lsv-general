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
import org.shw.lsv.util.support.provider.SVMinHacienda;
import org.spin.model.MADAppRegistration;

/** Generated Process for (EI_CreateInvoice_Electronic)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.4
 */
public class EInvoiceGenerateAndPost extends EInvoiceGenerateAndPostAbstract implements IGenerateAndPost
{
	//public static final String APPLICATION_TYPE = "LSV";
	@Override
	protected void prepare()
	{
		super.prepare();
	}

	@Override
	protected String doIt() throws Exception
	{
		StringBuffer result = new StringBuffer();
		result.append(processInvoices());
		result.append(processVoided());
		return result.toString();
	}
	
	protected String processVoided() throws Exception{


		StringBuffer errorMessages = new StringBuffer();
		int noCompletados = 0;
		String applicationType = IGenerateAndPost.getApplicationType();
		MADAppRegistration registration = null;
		Timestamp startdate = null;
		String errorMessage= "";
		MClient client = new MClient(getCtx(),getClientId(), get_TrxName());

		startdate = (Timestamp)(client.get_Value("ei_Startdate"));
		System.out.println("\n" + "******************************************************");
		System.out.println("Process EInvoiceGenerateAndPost: started with Client '" + client.getName() + "', ID: " + getClientId());
		registration = new Query(getCtx(), MADAppRegistration.Table_Name, "EXISTS(SELECT 1 FROM AD_AppSupport s "
				+ "WHERE s.AD_AppSupport_ID = AD_AppRegistration.AD_AppSupport_ID "
				+ "AND s.ApplicationType = ?"
				+ "AND s.IsActive = 'Y'"
				+ "AND s.Classname = ?)", get_TrxName())
				.setParameters(applicationType, SVMinHacienda.class.getName())
				.<MADAppRegistration>first();

		if(registration==null) {
			errorMessage = "Process EInvoiceGenerateAndPost : no registration for Application Type " + applicationType;
			errorMessages.append(errorMessage);
			System.out.println(errorMessage);
			return errorMessage.toString();
		}
		SVMinHacienda sv_minhacienda = new SVMinHacienda();
		sv_minhacienda.setVoided(true);
		sv_minhacienda.setADClientID(client.getAD_Client_ID());
		sv_minhacienda.setAppRegistrationId(registration.getAD_AppRegistration_ID() );
		

		String whereClause = IGenerateAndPost.getWhereclause(true); 
		if (getInvoiceId()>0)
			whereClause = whereClause + " AND C_Invoice_ID=" + getInvoiceId();
			
	
		try {
			int[] invoiceIds = new Query(Env.getCtx(), MInvoice.Table_Name, whereClause, null)
						.setParameters(getClientId(), startdate)
						.getIDs();
			final int length = invoiceIds.length;
			noCompletados = length;
			if(length==0) {
				System.out.println("****************** Process EInvoiceGenerateAndPost: There is no invoice to process!!!");
				System.out.println("Process EInvoiceGenerateAndPost: finished" + "\n");
				return " No hay Documentos anulados pendientes";
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
						sv_minhacienda.publishDocument(invoice);
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
		return " Documentos anulados: " + noCompletados;
	
		
	}
	

	
	protected String processInvoices() throws Exception{


		StringBuffer errorMessages = new StringBuffer();
		int noCompletados = 0;
		String applicationType = IGenerateAndPost.getApplicationType();
		MADAppRegistration registration = null;
		Timestamp startdate = null;
		String errorMessage= "";
		MClient client = new MClient(getCtx(),getClientId(), get_TrxName());

		startdate = (Timestamp)(client.get_Value("ei_Startdate"));
		System.out.println("\n" + "******************************************************");
		System.out.println("Process EInvoiceGenerateAndPost: started with Client '" + client.getName() + "', ID: " + getClientId());
		registration = new Query(getCtx(), MADAppRegistration.Table_Name, " AD_Client_ID=? AND EXISTS(SELECT 1 FROM AD_AppSupport s "
				+ "WHERE s.AD_AppSupport_ID = AD_AppRegistration.AD_AppSupport_ID "
				+ "AND s.ApplicationType = ? "
				+ "AND s.IsActive = 'Y' "
				+ "AND s.Classname = ? )"
				+ "AND AD_Client_ID=?", get_TrxName())
				.setParameters(getClientId(),  applicationType, SVMinHacienda.class.getName(), getClientId())
				.<MADAppRegistration>first();

		if(registration==null) {
			errorMessage = "Process EInvoiceGenerateAndPost : no registration for Application Type " + applicationType;
			errorMessages.append(errorMessage);
			System.out.println(errorMessage);
			return errorMessage.toString();
		}

		SVMinHacienda sv_minhacienda = new SVMinHacienda();
		sv_minhacienda.setVoided(false);
		sv_minhacienda.setADClientID(client.getAD_Client_ID());
		sv_minhacienda.setAppRegistrationId(registration.getAD_AppRegistration_ID() );
		

		String whereClause = IGenerateAndPost.getWhereclause(false);
		if (getInvoiceId()>0)
				whereClause = whereClause + " AND C_Invoice_ID=" + getInvoiceId();
				
	
		try {
			int[] invoiceIds = new Query(Env.getCtx(), MInvoice.Table_Name, whereClause, null)
						.setParameters(getClientId(), startdate)
						.getIDs();
			final int length = invoiceIds.length;
			noCompletados = length;
			if(length==0) {
				System.out.println("****************** Process EInvoiceGenerateAndPost: There is no invoice to process!!!");
				System.out.println("Process EInvoiceGenerateAndPost: finished" + "\n");
				return "No hay documentos completados pendientes ";
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
						sv_minhacienda.publishDocument(invoice);
						invoice.set_ValueOfColumn("ei_Processing", false);
						invoice.saveEx();
	                    if (dbTransaction != null) {
	                        dbTransaction.commit(true);
	                        dbTransaction.close();
	                    }
	                    if (invoice.get_ValueAsString("ei_selloRecibido") != null)
	                    	//sendIndividualMail(null, invoice);
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
		return "Documentos completados: " + noCompletados ;
	
		
	}
	
	
	 
	
	
	


}
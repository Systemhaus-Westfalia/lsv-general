/*************************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                              *
 * Copyright (C) 2012-2018 E.R.P. Consultores y Asociados, C.A.                      *
 * Contributor(s): Yamel Senih ysenih@erpya.com                                      *
 * This program is free software: you can redistribute it and/or modify              *
 * it under the terms of the GNU General Public License as published by              *
 * the Free Software Foundation, either version 3 of the License, or                 *
 * (at your option) any later version.                                               *
 * This program is distributed in the hope that it will be useful,                   *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                    *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                     *
 * GNU General Public License for more details.                                      *
 * You should have received a copy of the GNU General Public License                 *
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.            *
 ************************************************************************************/
package org.shw.lsv.util.support.provider;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.ws.rs.core.Form;
import org.adempiere.core.domains.models.I_C_Invoice;
import org.adempiere.core.domains.models.X_E_InvoiceElectronic;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.glassfish.grizzly.http.HttpHeader;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.json.JSONArray;
import org.json.JSONObject;
import org.shw.lsv.util.support.IDeclarationDocument;
import org.shw.lsv.util.support.IDeclarationProvider;
import org.spin.model.MADAppRegistration;

import com.fasterxml.jackson.annotation.JsonInclude;  

/**
 * 	A implementation class for findex.la provider using LSV
 * 	@author Yamel Senih, ysenih@erpya.com, ERPCyA http://www.erpya.com
 */
public class SVMinHaciendaToken implements IDeclarationProvider {
	public static final int HTTP_RESPONSE_200_OK = 200;
	public static final int HTTP_RESPONSE_201_CREATED = 201;
	private final String PROVIDER_HOST =  "providerhost"; 
	private final String PWD = "pwd";
	private final String USER = "user";

	private final String PATH = "path";
	
	private String providerHost = null;
	private String path = null;
	private String user = null;
	private String pwd = null;
	private int registrationId = 0;
	private boolean voided = false;
	private MClient client;
	

	public SVMinHaciendaToken() {
	}
	
	/**
	 * Validate connection
	 */
	private void validate() {
		if(getAppRegistrationId() <= 0) {
			throw new AdempiereException("@AD_AppRegistration_ID@ @NotFound@");
		}
		MADAppRegistration registration = MADAppRegistration.getById(Env.getCtx(), getAppRegistrationId(), null);
		if(registration == null) {
			throw new AdempiereException("@AD_AppRegistration_ID@ @NotFound@");
		}
		this.providerHost 	= registration.getParameterValue(PROVIDER_HOST);
		this.path 			= registration.getParameterValue(PATH);
		this.user 			= client.get_ValueAsString("ei_nit");
		this.pwd 			= registration.getParameterValue(PWD);
	}

	@Override
	public String testConnection() {
		return "Ok";
	}

	@Override
	public void setAppRegistrationId(int registrationId) {
		this.registrationId = registrationId;
		validate();
	}

	@Override
	public int getAppRegistrationId() {
		return registrationId;
	}

	public String getProviderHost() {
		return providerHost;
	}

	public void setProviderHost(String providerHost) {
		this.providerHost = providerHost;
	}	
	

	public boolean isVoided() {
		return voided;
	}

	public void setVoided(boolean voided) {
		this.voided = voided;
	}

	@Override
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public String publishDocument(PO document) throws Exception {
		Invocation.Builder invocationBuilder = getClient().target(providerHost)
				.path(path)
				//.path("api")
				//.path("procesar-json")	
				//.path("3pl")
				.request(MediaType.APPLICATION_FORM_URLENCODED)
				.header(HttpHeaders.ACCEPT, "*/*")
				;
		Form form = new Form();
		form.param(PWD, this.pwd);
		form.param(USER, this.user);

		//form.param("pwd", "Qazxsw369!");
		//form.param("user", "06140904181038");
		Entity<Form> entity = Entity.form(form);
		Response response = invocationBuilder.post(entity);
		if (response.getStatus()==HTTP_RESPONSE_200_OK) {

			String output = response.readEntity(String.class);
			JSONObject jsonoutput = new JSONObject(output); 	
			JSONObject body = jsonoutput.getJSONObject("body");
        	String token = body.getString("token");
        	token = token.trim();
        	client.set_ValueOfColumn("ei_jwt", token);
        	client.saveEx();     
		}
		
		
		else {
			int status = response.getStatus();
			String error = "error " + status ;
			client.set_ValueOfColumn("ei_jwt", error);
        	client.saveEx();     
			
			return "No token";
		}
		return null;
	}

	/**
	 * Get client
	 * @return
	 */
	public Client getClient() {
		return ClientBuilder.newClient(new ClientConfig())
		.property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true);
	}

	@Override
	public IDeclarationDocument getDeclarationDocument(PO entity) {
		if(entity == null) {
			return null;
		}
		if(entity.get_TableName().equals(I_C_Invoice.Table_Name)) {
			return new ElectronicInvoice((MInvoice) entity);
		}
		return null;
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) throws SQLException {
		Adempiere.startupEnvironment(true);

        int registrationId = Integer.parseInt(args[1]);
		Findex findex = new Findex();
		findex.setAppRegistrationId(registrationId);
		//Trx dbTransaction = null;
		String whereClause = "AD_CLIENT_ID = ?  "
				+ " AND Exists (select 1 from c_Doctype dt where dt.c_Doctype_ID=c_Invoice.c_Doctype_ID AND E_DocType_ID is not null) "
				+ " AND processed = 'Y' AND dateacct>=? AND processing = 'N' "
				+ " AND ei_Processing = 'N' "
				+ " AND (ei_Status_Extern is NULL OR ei_Status_Extern <> 'Firmado')";
		MClient client = new MClient(Env.getCtx(),1000001, null);
		Timestamp startdate = (Timestamp)(client.get_Value("ei_Startdate"));
		try {
			int[] invoiceIds = new Query(Env.getCtx(), MInvoice.Table_Name, whereClause, null)
						.setParameters(startdate)
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
		
	}
	public void setAD_Client(MClient adClient) {
		client = adClient;
	}
}


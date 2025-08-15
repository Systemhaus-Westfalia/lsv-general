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

import java.util.Iterator;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.adempiere.core.domains.models.I_C_Invoice;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.json.JSONObject;
import org.shw.lsv.util.support.IDeclarationDocument;
import org.shw.lsv.util.support.IDeclarationProvider;
import org.spin.model.MADAppRegistration;

import com.fasterxml.jackson.annotation.JsonInclude;  

/**
 * 	A implementation class for findex.la provider using LSV
 * 	@author Yamel Senih, ysenih@erpya.com, ERPCyA http://www.erpya.com
 */
public class SVBACToken implements IDeclarationProvider {
	public static final int HTTP_RESPONSE_200_OK = 200;
	public static final int HTTP_RESPONSE_201_CREATED = 201;
	private final String PROVIDER_HOST =  "providerhost"; 
	private final String GRAND_TYPE = "grant_type";
	private final String SCOPE = "scope";
	private final String AUTHORIZATION = "authorization";

	private final String PATH = "path";
	
	private String providerHost = null;
	private String path = null;
	private String authorization = null;
	private String grant_type = null;
	private String scope = null;
	private int registrationId = 0;
	private boolean voided = false;
	private MClient client;
	

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
		this.scope			= registration.getParameterValue(SCOPE);
		this.grant_type  	= registration.getParameterValue(GRAND_TYPE);
		this.authorization 	= registration.getParameterValue(AUTHORIZATION);
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
		return "";
	}
	
	public String getToken(){
			Invocation.Builder invocationBuilder = getClient().target(providerHost)
					.path(path)
					.request(MediaType.APPLICATION_FORM_URLENCODED)
					.header(HttpHeaders.ACCEPT, "*/*")
					.header(AUTHORIZATION, this.authorization);
			Form form = new Form();
			form.param(GRAND_TYPE, this.grant_type);
			form.param(SCOPE, this.scope);
			//System.out.println("Form with parameter PWD: " + pwd + ": User: " + user);

			//form.param("pwd", "Qazxsw369!");
			//form.param("user", "06140904181038");
			Entity<Form> entity = Entity.form(form);
			System.out.println("Response: Post" );
			String result = "";
			Response response = invocationBuilder.post(entity);
			if (response.getStatus()==HTTP_RESPONSE_200_OK) {

				String output = response.readEntity(String.class);
				JSONObject jsonoutput = new JSONObject(output); 	
				String access_token = jsonoutput.getString("access_token");
				result = access_token;
			}	
			
			else {
				int status = response.getStatus();
				String error = "error " + status ;
				client.set_ValueOfColumn("ei_jwt", error);
	        	client.saveEx();     
	        	System.out.println("reponse: Status " +  response.getStatus() );

				result =  "No token";
			}
			return result;
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
	

}


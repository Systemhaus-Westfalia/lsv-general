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
package org.shw.lsv.util.support.findex;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.adempiere.core.domains.models.I_C_Invoice;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoice;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.json.JSONObject;
import org.shw.lsv.util.support.IDeclarationDocument;
import org.shw.lsv.util.support.IDeclarationProvider;
import org.spin.model.MADAppRegistration;

/**
 * 	A implementation class for findex.la provider using LSV
 * 	@author Yamel Senih, ysenih@erpya.com, ERPCyA http://www.erpya.com
 */
public class Findex implements IDeclarationProvider {
	/**	Findex Host	*/
	private final String PROVIDER_HOST = "https://pruebas.findex.la";
	private final String TOKEN = "token";
	private String token = null;
	/**	Registration Id	*/
	private int registrationId = 0;
	
	
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
		token = registration.getParameterValue(TOKEN);
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

	@Override
	public String publishDocument(PO document) {
		IDeclarationDocument declarationDocument = getDeclarationDocument(document);
		if(declarationDocument == null) {
			return null;
		}
		Invocation.Builder invocationBuilder = getClient().target(PROVIDER_HOST)
				.path("api")
				.path("procesar-json")
				.path("3pl")
    			.request(MediaType.APPLICATION_JSON)
    			.header(HttpHeaders.AUTHORIZATION, token)
    			.header(HttpHeaders.ACCEPT, "application/json");
		//	
		JSONObject jsonValue = new JSONObject(declarationDocument.getDocumentValues());
		Entity<String> entity = Entity.json(jsonValue.toString());
        Response response = invocationBuilder.post(entity);
        if(response.getStatus() != 201
        		|| response.getStatus() != 200) {
        	String output = response.readEntity(String.class);
        	return output;
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
			return new Invoice((MInvoice) entity);
		}
		return null;
	}
}

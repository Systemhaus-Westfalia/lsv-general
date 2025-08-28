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
public class SVMinHacienda implements IDeclarationProvider {
	public static final int HTTP_RESPONSE_200_OK = 200;
	public static final int HTTP_RESPONSE_201_CREATED = 201;
	private final String PROVIDER_HOST =  "providerhost"; 

	private final String PROVIDER_HOST_SIGNATURE =  "providerhost_signature"; 

	private final String PATH = "path";
	private final String PATH_SIGNATURE = "path_signature";
	private final String PATHVOIDED = "pathVoided";
	
	private final String VERSION = "version";
	private final String AMBIENTE = "ambiente";
	private final String IDENVIO = "idEnvio";
	private final String TIPODTE = "tipoDte";
	private final String CODIGOGENERATION = "codigoGeneracion"; 
	private final String DOCUMENTO = "documento";

	
	private String token = null;
	private String providerHost = null;
	private String path = null;
	private int version = 0;
	private String ambiente = null;
	private int idEnvio = 0;
	private String tipoDte = null;
	private String codigoGeneracion = null;
	private int registrationId = 0;
	private boolean voided = false;
	private int ADClientId = 0;
	private MClient client = null;
	private MADAppRegistration registration = null;
	
	private Boolean testlocal = true;
	

	public SVMinHacienda() {
	}
	
	/**
	 * Validate connection
	 */
	private void validate() {
		client = new MClient(Env.getCtx(),getADClientId(),null);
		if(getAppRegistrationId() <= 0) {
			throw new AdempiereException("@AD_AppRegistration_ID@ @NotFound@");
		}
		registration = MADAppRegistration.getById(Env.getCtx(), getAppRegistrationId(), null);
		if(registration == null) {
			throw new AdempiereException("@AD_AppRegistration_ID@ @NotFound@");
		}
		this.token = client.get_ValueAsString("ei_jwt");
		/*
		 * this.providerHost = registration.getParameterValue(PROVIDER_HOST); if
		 * (!isVoided()) this.path = registration.getParameterValue(PATH); else
		 * this.path = registration.getParameterValue(PATHVOIDED);
		 */	
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

	public int getADClientId() {
		return ADClientId;
	}

	public void setADClientID(int ADCLient_ID) {
		this.ADClientId = ADCLient_ID;
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
		
		IDeclarationDocument declarationDocument = getDeclarationDocument(document);
		MInvoice invoice = (MInvoice)document;
		if(declarationDocument == null) {
			return null;
		}
		X_E_InvoiceElectronic electronicInvoiceModel = declarationDocument.processElectronicInvoice();
		if(electronicInvoiceModel==null) {
			return null;
		}
		String documentAsJsonString = electronicInvoiceModel.getjson();
		JSONObject jsonorg = new JSONObject(documentAsJsonString);
		JSONObject identificacion =  jsonorg.getJSONObject("identificacion");
		

		testlocal = invoice.get_ValueAsString("ei_Status_Extern").equals("Firmado");
		if (testlocal) {
			return "";
		}
		
		
		
		JSONObject documento = null;
		if (voided)
			documento = jsonorg.getJSONObject("documento");
		ambiente = identificacion.getString("ambiente");
		version = identificacion.getInt("version");
		idEnvio = electronicInvoiceModel.getC_Invoice_ID();
		
			
		codigoGeneracion = identificacion.getString("codigoGeneracion");
		
		
		String signature = getSignature(jsonorg);
		if (!voided) {
			tipoDte = identificacion.getString("tipoDte");
			this.path = registration.getParameterValue(PATH); 
		}
		else
		{
			tipoDte = documento.getString("tipoDte");
			this.path = registration.getParameterValue(PATHVOIDED); 
		}
		  this.providerHost = registration.getParameterValue(PROVIDER_HOST);
		 
		  		 	
		Invocation.Builder invocationBuilder = getClient().target(providerHost)
				.path(path)
				//.path("api")
				//.path("procesar-json")	
				//.path("3pl")
  			.request(MediaType.APPLICATION_JSON)
  			.header(HttpHeaders.AUTHORIZATION, token)
  			.header(HttpHeaders.CONTENT_TYPE, "application/json")
  			.header(HttpHeaders.ACCEPT, "*/*");

		JSONObject jsonSello = new JSONObject();
		jsonSello.put(VERSION, version);
		jsonSello.put(AMBIENTE, ambiente);
		jsonSello.put(IDENVIO, idEnvio);
		jsonSello.put(IDENVIO, idEnvio); 
		jsonSello.put(TIPODTE, tipoDte);
		jsonSello.put(CODIGOGENERATION, codigoGeneracion);
		jsonSello.put(DOCUMENTO, signature);
    	System.out.println("Signature: " + signature);

		String jsonSelloString = jsonSello.toString();

    	System.out.println("Signature: " + jsonSelloString);
		Entity<String> entity = Entity.json(jsonSelloString);
		Response response = invocationBuilder.post(entity);
		

		System.out.println("Start post json for " + electronicInvoiceModel.getC_Invoice().getDocumentNo() );
		int status = response.getStatus();
    	String output = response.readEntity(String.class);
    	if (response.getStatus() == 403 || status == 401) {
    		//MInvoice invoice = (MInvoice)electronicInvoiceModel.getC_Invoice();
    		invoice.set_ValueOfColumn("ei_Status_Extern", status);
    		invoice.saveEx();
    		return "";
    	}
    	JSONObject jsonOutput = new JSONObject(output);
        if(response.getStatus() !=200 && response.getStatus() != 201) {
        		
        	System.out.println("reponse: Status " +  response.getStatus() + " For "+ electronicInvoiceModel.getC_Invoice().getDocumentNo() );
        	//MInvoice invoice = (MInvoice)electronicInvoiceModel.getC_Invoice();
        	String estado = "";
        	if (jsonOutput.has("estado")) {
                estado = jsonOutput.optString("estado", "Estado NO Valido");
                System.out.println("Estado: " + estado);
            } else {
                System.out.println("The key 'estado' does not exist.");
            }

        	if (estado.equals("Estado NO Valido")) {
        		invoice.set_ValueOfColumn("ei_Status_Extern",response.getStatus());
    			invoice.set_ValueOfColumn("ei_output", output);
        		invoice.saveEx();
        		return "";
        	}
        	//String estado = jsonOutput.getString("estado");
    		String error = "";
		if (estado.equals("RECHAZADO"))
		{
        	System.out.println("reponse: Status " +  status + " For "+ electronicInvoiceModel.getC_Invoice().getDocumentNo() );
			String descriptionMsg = "";
			if (isVoided()) {
				descriptionMsg = jsonOutput.getString("descripcionMsg");
			}
			else {
	        	descriptionMsg = jsonOutput.getString("descripcionMsg");
			}
    		if (descriptionMsg.contains("YA EXISTE UN REGISTRO CON ESE VALOR"))
    		{
    			invoice.set_ValueOfColumn("ei_Status_Extern", "Firmado");
            	System.out.println("reponse: Status " +  status + " error " + error + " For "+ electronicInvoiceModel.getC_Invoice().getDocumentNo() );
            	invoice.saveEx();
            	return null;
    		}
    		else {   		
    			invoice.set_ValueOfColumn("ei_Status_Extern",status);
    			invoice.set_ValueOfColumn("ei_Error_Extern", error + " " + descriptionMsg);

    			invoice.set_ValueOfColumn("ei_output", output);
    			System.out.println("reponse: Status " +  status + " error " + error + " For "+ electronicInvoiceModel.getC_Invoice().getDocumentNo() );
    			invoice.saveEx();
    			System.out.println("Return status false");
    			return null;
    		}
    	
		}
        	
        }
        else if (response.getStatus() ==200 ) 
        {
        	//MInvoice invoice = (MInvoice)electronicInvoiceModel.getC_Invoice();
        	String estado = jsonOutput.getString("estado");
        	String descriptionMsg = jsonOutput.getString("descripcionMsg");
        	Boolean completed = estado.equals("PROCESADO");
        	if (completed|| estado.equals("Anulado")) {
        		System.out.println("reponse: Status " +  status + " For "+ electronicInvoiceModel.getC_Invoice().getDocumentNo() );

        		invoice.set_ValueOfColumn("ei_Status_Extern", "Firmado");
        		String sellorecibido = jsonOutput.getString("selloRecibido");
        		invoice.set_ValueOfColumn("ei_selloRecibido",sellorecibido);

        		String fecha = jsonOutput.getString("fhProcesamiento");
        		System.out.println("Status Firmado: fecha " + fecha+ " For "+ electronicInvoiceModel.getC_Invoice().getDocumentNo() );
        		invoice.set_ValueOfColumn("ei_dateReceived", fecha);
        		System.out.println("Invoice save" + " For "+ electronicInvoiceModel.getC_Invoice().getDocumentNo() );
        		invoice.saveEx();
        		return null;
        	}

        }
        
        else{
        	System.out.println("reponse of output " + " For "+ electronicInvoiceModel.getC_Invoice().getDocumentNo()  + " Output: " + output);
        	//MInvoice invoice = (MInvoice)electronicInvoiceModel.getC_Invoice();
        	String estado = jsonOutput.getString("estado");

        	System.out.println("reponse: Status " +  status + " For "+ electronicInvoiceModel.getC_Invoice().getDocumentNo() );
        	if (estado.equals("Pendiente")) {

            	System.out.println("Save data "+ " For "+ electronicInvoiceModel.getC_Invoice().getDocumentNo() );
    			invoice.set_ValueOfColumn("ei_Status_Extern",status);
    			String errorCode = isVoided()?"description":"error";
    			String error = "";
    			if (isVoided()) {
    				error = jsonOutput.getString("descripcion");
    			}
    			else {

            		JSONArray array = jsonOutput.getJSONArray(errorCode);
            		error = array.getString(0);
    			}
        		invoice.set_ValueOfColumn("ei_Error_Extern", error);
            	System.out.println("Stop " + electronicInvoiceModel.getC_Invoice().getDocumentNo() );
            	invoice.saveEx();
        	}
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
	
	public String setSignatureRegistration() {
		
		
		return "";
	}
	
	public String getSignature(JSONObject jsonorg) throws Exception{
		String result = "";

		
		  this.providerHost = registration.getParameterValue(PROVIDER_HOST_SIGNATURE); 
		  
		  this.path = registration.getParameterValue(PATH_SIGNATURE); 
		 	
		Invocation.Builder invocationBuilder = getClient().target(providerHost)
				.path(path)
				//.path("api")
				//.path("procesar-json")	
				//.path("3pl")
    			.request(MediaType.APPLICATION_JSON)
    			.header(HttpHeaders.AUTHORIZATION, token)
    			.header(HttpHeaders.CONTENT_TYPE, "application/json")
    			.header(HttpHeaders.ACCEPT, "*/*");

		
		

		JSONObject jsonSignature = new JSONObject();
		jsonSignature.put("nit", client.get_ValueAsString("ei_nit"));
		jsonSignature.put("activo", true);
		jsonSignature.put("passwordPri", client.get_ValueAsString("ei_privatePassword"));
		jsonSignature.put("dteJson", jsonorg);
		String jsonSignatureString = jsonSignature.toString();
		Entity<String> entity = Entity.json(jsonSignatureString);
		if (1==1) {
			String output = "{\"status\": \"OK\",\"body\": \"eyJhbGciOiJSUzUxMiJ9.ew0KICAiZXh0ZW5zaW9uIiA6IG51bGwsDQogICJyZWNlcHRvciIgOiB7DQogICAgImRlc2NBY3RpdmlkYWQiIDogIlZlbnRhIGRlIHByb2R1Y3RvcyBmYXJtYWPDqXV0aWNvcyB5IG1lZGljaW5hbGVzIiwNCiAgICAiY29kQWN0aXZpZGFkIiA6ICI0NjQ4NCIsDQogICAgImNvcnJlbyIgOiAiY29tcHJhcy5zdkBiYnJhdW4uY29tIiwNCiAgICAibml0IiA6ICIwNjE0MTYwOTg3MDAyNyIsDQogICAgImRpcmVjY2lvbiIgOiB7DQogICAgICAiY29tcGxlbWVudG8iIDogIkJvdWxldmFyZCBMb3MgUHJvY2VyZXMgeSBBdi4gQWxiZXJ0IEVpbnN0ZWluLCBUb3JyZSBDdXNjYXRsw6FuIG5pdmVsIDQiLA0KICAgICAgIm11bmljaXBpbyIgOiAiMDEiLA0KICAgICAgImRlcGFydGFtZW50byIgOiAiMDUiDQogICAgfSwNCiAgICAibm9tYnJlQ29tZXJjaWFsIiA6IG51bGwsDQogICAgInRlbGVmb25vIiA6ICIyNTI0LTQ1NTQiLA0KICAgICJub21icmUiIDogIiBCLiBCcmF1biBNZWRpY2FsIENlbnRyYWwgQW1lcmljYSAmIENhcmliZSBTLkEuIGRlIEMuVi4iLA0KICAgICJucmMiIDogIjkxODYzIg0KICB9LA0KICAiaWRlbnRpZmljYWNpb24iIDogew0KICAgICJjb2RpZ29HZW5lcmFjaW9uIiA6ICJEMTgxMTFDQS1EQzg5LTQ5MEItOEY4MC1FOEFDNUQzNTE2MzMiLA0KICAgICJ0aXBvQ29udGluZ2VuY2lhIiA6IG51bGwsDQogICAgIm51bWVyb0NvbnRyb2wiIDogIkRURS0wMy1NMDAxUE8wMS0wMDAwMDAwMDAwMDAwOTciLA0KICAgICJ0aXBvT3BlcmFjaW9uIiA6IDEsDQogICAgImFtYmllbnRlIiA6ICIwMCIsDQogICAgImZlY0VtaSIgOiAiMjAyNS0wOC0yMSIsDQogICAgInRpcG9Nb2RlbG8iIDogMSwNCiAgICAidGlwb0R0ZSIgOiAiMDMiLA0KICAgICJ2ZXJzaW9uIiA6IDMsDQogICAgInRpcG9Nb25lZGEiIDogIlVTRCIsDQogICAgIm1vdGl2b0NvbnRpbiIgOiBudWxsLA0KICAgICJob3JFbWkiIDogIjIzOjUzOjQ4Ig0KICB9LA0KICAicmVzdW1lbiIgOiB7DQogICAgInRvdGFsTm9TdWoiIDogMCwNCiAgICAiaXZhUGVyY2kxIiA6IDAsDQogICAgImRlc2N1Tm9TdWoiIDogMCwNCiAgICAidG90YWxMZXRyYXMiIDogIlFVSU5JRU5UT1MgT0NIRU5UQSBZIE5VRVZFIDg2LzEwMCIsDQogICAgIml2YVJldGUxIiA6IDAsDQogICAgInN1YlRvdGFsVmVudGFzIiA6IDUyMiwNCiAgICAic3ViVG90YWwiIDogNTIyLA0KICAgICJyZXRlUmVudGEiIDogMCwNCiAgICAidHJpYnV0b3MiIDogWyB7DQogICAgICAiZGVzY3JpcGNpb24iIDogIkltcHVlc3RvIGFsIFZhbG9yIEFncmVnYWRvIDEzJSIsDQogICAgICAiY29kaWdvIiA6ICIyMCIsDQogICAgICAidmFsb3IiIDogNjcuODYNCiAgICB9IF0sDQogICAgInBhZ29zIiA6IFsgew0KICAgICAgImNvZGlnbyIgOiAiMDUiLA0KICAgICAgInBlcmlvZG8iIDogOTAsDQogICAgICAicGxhem8iIDogIjAxIiwNCiAgICAgICJtb250b1BhZ28iIDogNTg5Ljg2LA0KICAgICAgInJlZmVyZW5jaWEiIDogIlRyYW5zZmVyZW5jaWFfIERlcG9zaXRvIEJhbmNhcmlvIg0KICAgIH0gXSwNCiAgICAiZGVzY3VFeGVudGEiIDogMCwNCiAgICAidG90YWxEZXNjdSIgOiAwLA0KICAgICJudW1QYWdvRWxlY3Ryb25pY28iIDogbnVsbCwNCiAgICAiZGVzY3VHcmF2YWRhIiA6IDAsDQogICAgInBvcmNlbnRhamVEZXNjdWVudG8iIDogMCwNCiAgICAidG90YWxHcmF2YWRhIiA6IDUyMiwNCiAgICAibW9udG9Ub3RhbE9wZXJhY2lvbiIgOiA1ODkuODYsDQogICAgInRvdGFsTm9HcmF2YWRvIiA6IDAsDQogICAgInNhbGRvRmF2b3IiIDogMCwNCiAgICAidG90YWxFeGVudGEiIDogMCwNCiAgICAidG90YWxQYWdhciIgOiA1ODkuODYsDQogICAgImNvbmRpY2lvbk9wZXJhY2lvbiIgOiAyDQogIH0sDQogICJjdWVycG9Eb2N1bWVudG8iIDogWyB7DQogICAgImRlc2NyaXBjaW9uIiA6ICI0MjAyMTEgUHVibGljaWRhZCB5IFByb3BhZ2FuZGEiLA0KICAgICJtb250b0Rlc2N1IiA6IDAsDQogICAgImNvZGlnbyIgOiAiVGVzdCIsDQogICAgInZlbnRhR3JhdmFkYSIgOiA1MjIsDQogICAgInZlbnRhTm9TdWoiIDogMCwNCiAgICAidmVudGFFeGVudGEiIDogMCwNCiAgICAidHJpYnV0b3MiIDogWyAiMjAiIF0sDQogICAgIm51bUl0ZW0iIDogMSwNCiAgICAibm9HcmF2YWRvIiA6IDAsDQogICAgInBzdiIgOiAwLA0KICAgICJ0aXBvSXRlbSIgOiAzLA0KICAgICJjb2RUcmlidXRvIiA6IG51bGwsDQogICAgInVuaU1lZGlkYSIgOiA1OSwNCiAgICAibnVtZXJvRG9jdW1lbnRvIiA6IG51bGwsDQogICAgImNhbnRpZGFkIiA6IDEsDQogICAgInByZWNpb1VuaSIgOiA1MjINCiAgfSBdLA0KICAib3Ryb3NEb2N1bWVudG9zIiA6IG51bGwsDQogICJ2ZW50YVRlcmNlcm8iIDogbnVsbCwNCiAgImFwZW5kaWNlIiA6IFsgew0KICAgICJldGlxdWV0YSIgOiAiRGVzY3JpcGNpb24iLA0KICAgICJ2YWxvciIgOiAiSW1wb3J0IENDRkYgfCAoRkNSRi0wMDk3LTIwMjVePC0pIiwNCiAgICAiY2FtcG8iIDogIkluZm8iDQogIH0gXSwNCiAgImRvY3VtZW50b1JlbGFjaW9uYWRvIiA6IG51bGwsDQogICJlbWlzb3IiIDogew0KICAgICJkZXNjQWN0aXZpZGFkIiA6ICJBbHF1aWxlciBkZSBpbnN0YWxhY2lvbmVzIGNvbiByZWZyaWdlcmFjacOzbiBwYXJhIGFsbWFjZW5hbWllIiwNCiAgICAidGlwb0VzdGFibGVjaW1pZW50byIgOiAiMDIiLA0KICAgICJkaXJlY2Npb24iIDogew0KICAgICAgImNvbXBsZW1lbnRvIiA6ICJGQUxUQSIsDQogICAgICAibXVuaWNpcGlvIiA6ICIxNCIsDQogICAgICAiZGVwYXJ0YW1lbnRvIiA6ICIwNiINCiAgICB9LA0KICAgICJjb2RFc3RhYmxlIiA6IG51bGwsDQogICAgImNvZFB1bnRvVmVudGEiIDogbnVsbCwNCiAgICAibm9tYnJlIiA6ICJLQUxUTUFOTiBTLkEuIGRlIEMuVi4gIiwNCiAgICAiY29kQWN0aXZpZGFkIiA6ICI1MjEwMyIsDQogICAgImNvZEVzdGFibGVNSCIgOiBudWxsLA0KICAgICJjb3JyZW8iIDogIlNDYWxkZXJvbkBnbXguZGUiLA0KICAgICJuaXQiIDogIjA2MTQyMDA2MTcxMTI2IiwNCiAgICAibm9tYnJlQ29tZXJjaWFsIiA6ICJLQUxUTUFOTiBTLkEuIGRlIEMuVi4gIiwNCiAgICAidGVsZWZvbm8iIDogIjc5ODQ1MTI1IiwNCiAgICAibnJjIiA6ICIyNjI1NjExIiwNCiAgICAiY29kUHVudG9WZW50YU1IIiA6IG51bGwNCiAgfQ0KfQ.JXBYU-0YI1MWg48AYUHonbBUOzwB2O62KLJhrAsDWKFV-TfQ9bnTDzjX3-l2Q6DH0mfwoobZ6E_EPkq42ORghCvA97tZwq8CU_59WOgd1U2NVs5luwOywLm66txfScL-bsQwi7oxE_v4AApNy3u9_j2oNsg4Yl6cpZxWOo44x9z3J5afVq5R-8tnoQAXFRZ0NXPLnLldaMH4xIjwJ5s1YQy2kDKKlmw6WZKnx1o6G_TROZQDnXmEIJoMyf-OE4gXNWjPvnI1BbQcvEV35T1Sz9LbdCvHD47y19pIAtJqySdaHOoOuKmqbGOYUj0LgmMw_vw1zeH6fWGqfjbrfz05cg\"}";
			JSONObject jsonoutput = new JSONObject(output);  
    	result = jsonoutput.getString("body");   
			return result;
		}
		
		Response response = invocationBuilder.post(entity);
		if (response.getStatus()==HTTP_RESPONSE_200_OK) {

        	String output = response.readEntity(String.class);
			JSONObject jsonoutput = new JSONObject(output);  
    	result = jsonoutput.getString("body");    	
		}
		else 
			result = "No Signature";
		return result;
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
	
}


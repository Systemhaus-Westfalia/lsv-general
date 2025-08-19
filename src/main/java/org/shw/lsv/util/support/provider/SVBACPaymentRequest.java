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

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.adempiere.core.domains.models.I_C_Invoice;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBankAccount;
import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.MPayment;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfo;
import org.compiere.util.Env;
import org.eevolution.services.dsl.ProcessBuilder;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.json.JSONObject;
import org.shw.lsv.ebanking.bac.sv.camt052.request.CAMT052Request;
import org.shw.lsv.ebanking.bac.sv.camt052.response.CAMT052Response;
import org.shw.lsv.ebanking.bac.sv.camt052.response.CAMT052ResponseFile;
import org.shw.lsv.ebanking.bac.sv.handling.JsonProcessor;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationException;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestBuilder;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.misc.Rejection;
import org.shw.lsv.ebanking.bac.sv.pain001.request.PAIN001Request;
import org.shw.lsv.ebanking.bac.sv.pain001.response.PAIN001ResponseEvtNtfn;
import org.shw.lsv.ebanking.bac.sv.pain001.response.PAIN001ResponseEvtNtfnDocument;
import org.shw.lsv.ebanking.bac.sv.pain001.response.PAIN001ResponseEvtNtfnEnvelope;
import org.shw.lsv.ebanking.bac.sv.pain001.response.PAIN001ResponseEvtNtfnFile;
import org.shw.lsv.ebanking.bac.sv.pain001.response.PAIN001ResponseStatusReport;
import org.shw.lsv.ebanking.bac.sv.process.SVBACGetToken;
import org.shw.lsv.ebanking.bac.sv.tmst039.response.TMST039Response;
import org.shw.lsv.ebanking.bac.sv.util.RequestParamsFactory;
import org.shw.lsv.ebanking.bac.sv.z_test.util.TestRequestParamsFactory;
import org.shw.lsv.util.support.IDeclarationDocument;
import org.shw.lsv.util.support.IDeclarationProvider;
import org.spin.model.MADAppRegistration;

import com.fasterxml.jackson.annotation.JsonInclude;  

/**
 * 	A implementation class for findex.la provider using LSV
 * 	@author Yamel Senih, ysenih@erpya.com, ERPCyA http://www.erpya.com
 */
public class SVBACPaymentRequest implements IDeclarationProvider {
	public static final int HTTP_RESPONSE_200_OK = 200;
	public static final int HTTP_RESPONSE_201_CREATED = 201;
	private final String PROVIDER_HOST =  "providerhost"; 


	private final String PATH = "path";
	

	
	private String newtoken = null;
	private String providerHost = null;
	private String path = null;
	private int registrationId = 0;
	private int registrationTokenId = 0;
	private boolean voided = false;
	private int ADClientId = 0;
	private MClient client = null;
	private MADAppRegistration registration = null;
	private MADAppRegistration registrationToken = null;
	private int paymentID;
	private String transactionName = "";
	
	

	public SVBACPaymentRequest() {
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

	public String getNewtoken() {
		return newtoken;
	}

	public void setNewtoken(String newtoken) {
		this.newtoken = newtoken;
	}

	@Override
	public int getAppRegistrationId() {
		return registrationId;
	}

	public int getADClientId() {
		return ADClientId;
	}


	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
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
		return "";
	}
	

	public String sendPaymentRequestBACtoBAC() throws Exception {
		String result = "";
		MPayment payment = new MPayment(Env.getCtx(), paymentID, getTransactionName());
		getToken(payment);

		this.providerHost = registration.getParameterValue(PROVIDER_HOST);
		this.path = registration.getParameterValue(PATH); 


		String jsonOutput = "";

		LocalDateTime now = LocalDateTime.now();
		System.err.println("PAIN001 serialization started at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

		// 1. Create collector for test diagnostics
		JsonValidationExceptionCollector collector = new JsonValidationExceptionCollector();
		collector.setPrintImmediately(true); // See errors as they happen

		// 2. Build test parameters
		RequestParams params = RequestParamsFactory.createPain001Params_BAC_to_BAC(payment);

		try {
			PAIN001Request request = RequestBuilder.build(PAIN001Request.class, params, collector);

			// 4. Serialization test
			JsonProcessor processor = new JsonProcessor(collector);
			jsonOutput = processor.serialize(request);

			// 5. Print Json
			System.out.println("\nGenerated JSON:");
			System.out.println(jsonOutput);

			System.out.println("PAIN001 serialization succeeded without errors.\n");

			now = LocalDateTime.now();
			System.err.println("PAIN001 serialization finished at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

		} catch (JsonValidationException e) {
			System.err.println("PAIN001 serialization Test failed: " + e.getMessage());
			System.err.println(e.getValidationErrors());
			System.err.println("********************************************");
		}

		String newTokenMan = "";
		newTokenMan = getNewtoken();
		Invocation.Builder invocationBuilder = getClient().target(providerHost)
				.path(path)
				//.path("api")
				//.path("procesar-json")	
				//.path("3pl")
				.request(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + newTokenMan)
				.header(HttpHeaders.CONTENT_TYPE, "application/json")
				.header(HttpHeaders.ACCEPT, "*/*")
				.header("X-IBM-Client-Id", "6b443615f7adf11b1149f03da1c0f10f");


		System.out.println("Request: " + jsonOutput);
		Entity<String> entity = Entity.json(jsonOutput);
		Response response = invocationBuilder.post(entity);


		int status = response.getStatus();
		String output = response.readEntity(String.class);
		if (response.getStatus() == 403 || status == 401) {
			return "";
		}


		JsonProcessor processor = new JsonProcessor(collector);
		if(response.getStatus() !=200 && response.getStatus() != 201) {
			return "";
		}

		else if (response.getStatus() ==200 ) 
		{			
			System.out.println("Starting TMST039 deserialization test...");          

			// 3. Execute deserialization
			try {
				PAIN001ResponseEvtNtfn responseEvt = processor.deserialize(output, PAIN001ResponseEvtNtfn.class);
				PAIN001ResponseEvtNtfnEnvelope envelope = responseEvt.getPain001ResponseEvtNtfnFile().getPain001ResponseEnvelope();
				PAIN001ResponseEvtNtfnDocument document = envelope.getpAIN001ResponseEvtNtfnDocument();

				if (document != null && document.getRejection() != null) {
					Rejection rejection = document.getRejection();
					if (rejection.getRsn() != null) {
						payment.set_ValueOfColumn("evtCd", rejection.getRsn().getRjctgPtyRsn());
						payment.set_ValueOfColumn("EvtDesc", rejection.getRsn().getRsnDesc());
					} else {

						payment.set_ValueOfColumn("EvtDesc", "Rejection object present but Reason (Rsn) is null.");
					}
				}

				else if (document != null && document.getSysEvtNtfctn() != null) {
					collectResponseSummary(responseEvt, payment);
				} 
				else {
					result = ("Deserialization successful, but response document contains neither a notification nor a rejection.");
				}           

				// 5. Check for non-fatal warnings
				if (collector.hasErrors()) {
					result = "TMST039 Deserialization succeeded with warnings:";
					System.out.println(collector.getAllErrors());
				} else {
					result = "TMST039 Deserialization completed cleanly";
				}


			}catch (JsonValidationException e) {
				result  = "\nCritical validation failures:\n" + e.getValidationErrors();
			}

		}
		return result;
	}
	
	public String sendPaymentRequestBACtoDOM() throws Exception {
		String result = "";
		MPayment payment = new MPayment(Env.getCtx(), paymentID, getTransactionName());
		getToken(payment);

		this.providerHost = registration.getParameterValue(PROVIDER_HOST);
		this.path = registration.getParameterValue(PATH); 
        String jsonOutput = "";

        LocalDateTime now = LocalDateTime.now();
        System.err.println("PAIN001 serialization started at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        // 1. Create collector for test diagnostics
        JsonValidationExceptionCollector collector = new JsonValidationExceptionCollector();
        collector.setPrintImmediately(true); // See errors as they happen

        // 2. Build test parameters
        RequestParams params = RequestParamsFactory.createPain001Params_Domestico(payment);
        
        try {
            PAIN001Request request = RequestBuilder.build(PAIN001Request.class, params, collector);
            
            JsonProcessor processor = new JsonProcessor(collector);
            jsonOutput = processor.serialize(request);

            System.out.println("\nGenerated JSON:");
            System.out.println(jsonOutput);
            
            System.out.println("PAIN001 serialization succeeded without errors.\n");
            
            now = LocalDateTime.now();
            System.err.println("PAIN001 serialization finished at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        } catch (JsonValidationException e) {
            System.err.println("PAIN001 serialization Test failed: " + e.getMessage());
            System.err.println(e.getValidationErrors());
            System.err.println("********************************************");
        }
		String newTokenMan = "";
		newTokenMan = getNewtoken();
		Invocation.Builder invocationBuilder = getClient().target(providerHost)
				.path(path)
				//.path("api")
				//.path("procesar-json")	
				//.path("3pl")
				.request(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + newTokenMan)
				.header(HttpHeaders.CONTENT_TYPE, "application/json")
				.header(HttpHeaders.ACCEPT, "*/*")
				.header("X-IBM-Client-Id", "6b443615f7adf11b1149f03da1c0f10f");



		System.out.println("Request: " + jsonOutput);
		Entity<String> entity = Entity.json(jsonOutput);
		Response response = invocationBuilder.post(entity);


		int status = response.getStatus();
		String output = response.readEntity(String.class);
		if (response.getStatus() == 403 || status == 401) {
			return "";
		}


		JsonProcessor processor = new JsonProcessor(collector);
		if(response.getStatus() !=200 && response.getStatus() != 201) {
			return "";
		}

		else if (response.getStatus() ==200 ) 
		{			
			System.out.println("Starting TMST039 deserialization test...");          

			// 3. Execute deserialization
			try {
				PAIN001ResponseEvtNtfn responseEvt = processor.deserialize(output, PAIN001ResponseEvtNtfn.class);
				PAIN001ResponseEvtNtfnEnvelope envelope = responseEvt.getPain001ResponseEvtNtfnFile().getPain001ResponseEnvelope();
				PAIN001ResponseEvtNtfnDocument document = envelope.getpAIN001ResponseEvtNtfnDocument();

				if (document != null && document.getRejection() != null) {
					Rejection rejection = document.getRejection();
					if (rejection.getRsn() != null) {
						payment.set_ValueOfColumn("evtCd", rejection.getRsn().getRjctgPtyRsn());
						payment.set_ValueOfColumn("EvtDesc", rejection.getRsn().getRsnDesc());
					} else {

						payment.set_ValueOfColumn("EvtDesc", "Rejection object present but Reason (Rsn) is null.");
					}
				}

				else if (document != null && document.getSysEvtNtfctn() != null) {
					collectResponseSummary(responseEvt, payment);
				} 
				else {
					result = ("Deserialization successful, but response document contains neither a notification nor a rejection.");
				}           

				// 5. Check for non-fatal warnings
				if (collector.hasErrors()) {
					result = "TMST039 Deserialization succeeded with warnings:";
					System.out.println(collector.getAllErrors());
				} else {
					result = "TMST039 Deserialization completed cleanly";
				}


			}catch (JsonValidationException e) {
				result  = "\nCritical validation failures:\n" + e.getValidationErrors();
			}

		}
		return result;
	}
	

	public String sendPaymentRequestBACtoInt() throws Exception {
		String result = "";
		MPayment payment = new MPayment(Env.getCtx(), paymentID, getTransactionName());
		getToken(payment);

		this.providerHost = registration.getParameterValue(PROVIDER_HOST);
		this.path = registration.getParameterValue(PATH); 
        String jsonOutput = "";

        LocalDateTime now = LocalDateTime.now();
        System.err.println("PAIN001 serialization started at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        // 1. Create collector for test diagnostics
        JsonValidationExceptionCollector collector = new JsonValidationExceptionCollector();
        collector.setPrintImmediately(true); // See errors as they happen

        // 2. Build test parameters
        RequestParams params = RequestParamsFactory.createPain001Params_Domestico(payment);
        
        try {
            PAIN001Request request = RequestBuilder.build(PAIN001Request.class, params, collector);
            
            JsonProcessor processor = new JsonProcessor(collector);
            jsonOutput = processor.serialize(request);

            System.out.println("\nGenerated JSON:");
            System.out.println(jsonOutput);
            
            System.out.println("PAIN001 serialization succeeded without errors.\n");
            
            now = LocalDateTime.now();
            System.err.println("PAIN001 serialization finished at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        } catch (JsonValidationException e) {
            System.err.println("PAIN001 serialization Test failed: " + e.getMessage());
            System.err.println(e.getValidationErrors());
            System.err.println("********************************************");
        }
		String newTokenMan = "";
		newTokenMan = getNewtoken();
		Invocation.Builder invocationBuilder = getClient().target(providerHost)
				.path(path)
				//.path("api")
				//.path("procesar-json")	
				//.path("3pl")
				.request(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + newTokenMan)
				.header(HttpHeaders.CONTENT_TYPE, "application/json")
				.header(HttpHeaders.ACCEPT, "*/*")
				.header("X-IBM-Client-Id", "6b443615f7adf11b1149f03da1c0f10f");



		System.out.println("Request: " + jsonOutput);
		Entity<String> entity = Entity.json(jsonOutput);
		Response response = invocationBuilder.post(entity);


		int status = response.getStatus();
		String output = response.readEntity(String.class);
		if (response.getStatus() == 403 || status == 401) {
			return "";
		}


		JsonProcessor processor = new JsonProcessor(collector);
		if(response.getStatus() !=200 && response.getStatus() != 201) {
			return "";
		}

		else if (response.getStatus() ==200 ) 
		{			
			System.out.println("Starting TMST039 deserialization test...");          

			// 3. Execute deserialization
			try {
				PAIN001ResponseEvtNtfn responseEvt = processor.deserialize(output, PAIN001ResponseEvtNtfn.class);
				PAIN001ResponseEvtNtfnEnvelope envelope = responseEvt.getPain001ResponseEvtNtfnFile().getPain001ResponseEnvelope();
				PAIN001ResponseEvtNtfnDocument document = envelope.getpAIN001ResponseEvtNtfnDocument();

				if (document != null && document.getRejection() != null) {
					Rejection rejection = document.getRejection();
					if (rejection.getRsn() != null) {
						payment.set_ValueOfColumn("evtCd", rejection.getRsn().getRjctgPtyRsn());
						payment.set_ValueOfColumn("EvtDesc", rejection.getRsn().getRsnDesc());
					} else {

						payment.set_ValueOfColumn("EvtDesc", "Rejection object present but Reason (Rsn) is null.");
					}
				}

				else if (document != null && document.getSysEvtNtfctn() != null) {
					collectResponseSummary(responseEvt, payment);
				} 
				else {
					result = ("Deserialization successful, but response document contains neither a notification nor a rejection.");
				}           

				// 5. Check for non-fatal warnings
				if (collector.hasErrors()) {
					result = "TMST039 Deserialization succeeded with warnings:";
					System.out.println(collector.getAllErrors());
				} else {
					result = "TMST039 Deserialization completed cleanly";
				}


			}catch (JsonValidationException e) {
				result  = "\nCritical validation failures:\n" + e.getValidationErrors();
			}

		}
		return result;
	}
	
	public String getToken(MPayment payment) {				 
		ProcessInfo  processInfo =
				ProcessBuilder.create(Env.getCtx())
				.process(SVBACGetToken.getProcessId())
				.withTitle(SVBACGetToken.getProcessName())
				.withRecordId(MBankAccount.Table_ID, payment.getC_BankAccount().getC_BankAccount_ID())
				.withoutTransactionClose()
				.execute(getTransactionName()); 
		String result = processInfo.getSummary();
		setNewtoken(result);
		return "OK";
	}
	
	


	/**
	 * Get client
	 * @return
	 */
	public Client getClient() {
		return ClientBuilder.newClient(new ClientConfig())
		.property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true);
	}
	
	

	public int getRegistrationTokenId() {
		return registrationTokenId;
	}

	public void setRegistrationTokenId(int registrationTokenId) {
		this.registrationTokenId = registrationTokenId;
	}

	public int getPaymentID() {
		return paymentID;
	}

	public void setPaymentID(int paymentID) {
		this.paymentID = paymentID;
	}

	public MADAppRegistration getRegistrationToken() {
		return registrationToken;
	}

	public void setRegistrationToken(MADAppRegistration registrationToken) {
		this.registrationToken = registrationToken;
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
	
	private static void collectResponseSummary(PAIN001ResponseEvtNtfn response, MPayment payment) {
		PAIN001ResponseEvtNtfnEnvelope envelope = response.getPain001ResponseEvtNtfnFile().getPain001ResponseEnvelope();
		if (envelope != null) {
			PAIN001ResponseEvtNtfnDocument document = envelope.getpAIN001ResponseEvtNtfnDocument();
			if (document != null) {
				if (document.getSysEvtNtfctn() != null) {
					if (document.getSysEvtNtfctn() != null)
						if (document.getSysEvtNtfctn().getEvtInf() != null) {
							payment.set_ValueOfColumn("evtCd", document.getSysEvtNtfctn().getEvtInf().getEvtCd());

							payment.set_ValueOfColumn("EvtDesc", document.getSysEvtNtfctn().getEvtInf().getEvtDesc());
							payment.set_ValueOfColumn("EvtTime", document.getSysEvtNtfctn().getEvtInf().getEvtTm());
							payment.saveEx();
						}
				}
			}
		}
	}
	
	

	
	
	

	/**
	 * @param args
	 */
	
}


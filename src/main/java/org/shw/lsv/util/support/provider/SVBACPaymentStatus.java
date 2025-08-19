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
import org.shw.lsv.ebanking.bac.sv.camt052.response.CAMT052ResponseDocument;
import org.shw.lsv.ebanking.bac.sv.camt052.response.CAMT052ResponseEnvelope;
import org.shw.lsv.ebanking.bac.sv.camt052.response.CAMT052ResponseFile;
import org.shw.lsv.ebanking.bac.sv.handling.JsonProcessor;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationException;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestBuilder;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.misc.Rejection;
import org.shw.lsv.ebanking.bac.sv.process.SVBACGetToken;
import org.shw.lsv.ebanking.bac.sv.tmst038.request.TMST038RequestStatusReport;
import org.shw.lsv.ebanking.bac.sv.tmst039.response.TMST039Response;
import org.shw.lsv.ebanking.bac.sv.tmst039.response.TMST039ResponseDocument;
import org.shw.lsv.ebanking.bac.sv.util.RequestParamsFactory;
import org.shw.lsv.util.support.IDeclarationDocument;
import org.shw.lsv.util.support.IDeclarationProvider;
import org.spin.model.MADAppRegistration;

import com.fasterxml.jackson.annotation.JsonInclude;  

/**
 * 	A implementation class for findex.la provider using LSV
 * 	@author Yamel Senih, ysenih@erpya.com, ERPCyA http://www.erpya.com
 */
public class SVBACPaymentStatus implements IDeclarationProvider {
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
	private int paymentID = 0;
	private String transactionName = "";
	
	

	public SVBACPaymentStatus() {
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
	

	public int getPaymentID() {
		return paymentID;
	}

	public void setPaymentID(int paymentID) {
		this.paymentID = paymentID;
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
	

	public String getStatus() throws Exception {
		String result = "";
		MPayment payment = new MPayment(Env.getCtx(), paymentID, getTransactionName());
		getToken(payment);

		this.providerHost = registration.getParameterValue(PROVIDER_HOST);
		this.path = registration.getParameterValue(PATH); 

		String jsonOutput = "";

		LocalDateTime now = LocalDateTime.now();
		System.err.println("CAMT052 serialization started at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

		// 1. Create collector for test diagnostics
		JsonValidationExceptionCollector collector = new JsonValidationExceptionCollector();
		collector.setPrintImmediately(true); // See errors as they happen

		// 2. Build test parameters
		RequestParams params = RequestParamsFactory.createTmst038StatusReportParams(payment);

		try {
			// 3. Build request with test's collector
			// CAMT052Request request = RequestBuilder.build(params, collector);  // Deprecated. Kann spaeter geloescht werden
            TMST038RequestStatusReport request = RequestBuilder.build(TMST038RequestStatusReport.class, params, collector);

			// 4. Serialization test
			JsonProcessor processor = new JsonProcessor(collector);
			jsonOutput = processor.serialize(request);

			// 5. Print Json
			System.out.println("\nGenerated JSON:");
			System.out.println(jsonOutput);

			System.out.println("TMST038RequestStatusReport serialization succeeded without errors.\n");

			now = LocalDateTime.now();
			System.err.println("TMST038RequestStatusReport serialization finished at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

		} catch (JsonValidationException e) {
			System.err.println("CAMT052 serialization Test failed: " + e.getMessage());
			System.err.println(e.getValidationErrors());
			System.err.println("********************************************");
		}

		//setNewtoken(      "AAIgNmI0NDM2MTVmN2FkZjExYjExNDlmMDNkYTFjMGYxMGalUSEOvwZOiKZcGrLJskf1oWNQg-aUYVt0iWdgdM-ooKEEij7_Y2zaD1_a_zCCb1SpyfhYMHUI8JuvpZergcBkCMhxPU0H1sQ66vg8AjunX3aVE139IIYs8IPAFXkRvLo");
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


		System.out.println("Signature: " + jsonOutput);
		Entity<String> entity = Entity.json(jsonOutput);
		Response response = invocationBuilder.post(entity);


		int status = response.getStatus();
		String output = response.readEntity(String.class);
		if (response.getStatus() == 403 || status == 401) {
			return "";
		}


        // 2. Inject collector into processor
        JsonProcessor processor = new JsonProcessor(collector);
		JSONObject jsonResponse = new JSONObject(output);
		if(response.getStatus() !=200 && response.getStatus() != 201) {
			return "";
		}

		else if (response.getStatus() ==200 ) 
		{
			StringBuffer log = new StringBuffer();

			TMST039Response  tmst039Response    = processor.deserialize(jsonResponse.toString(), TMST039Response.class);
            
			TMST039ResponseDocument document = tmst039Response.getTmst039ResponseFile().getTMST039ResponseEnvelope().getTMST039ResponseDocument();
            //if (document != null && document.getStsRptRsp().get  != null) {
             //   log.append("\n--- Begin of Rejection Summary ---");
             //   Rejection rejection = document.getRejection();
             //   if (rejection.getRsn() != null) {
             //       log.append("\nRejection Message Received:");
             //       log.append("\nReason Code: ").append(rejection.getRsn().getRjctgPtyRsn());
             //       log.append("\nDescription: ").append(rejection.getRsn().getRsnDesc());
              //      result = log.toString();
              //  } else {
              //      log.append("\nRejection object present but Reason (Rsn) is null.");
              //      result = log.toString();
             //   }
             //   log.append("\n--- End of Rejection Summary ---\n");
           // } else if (document != null && document.getBkToCstmrAcctRpt() != null) {
             //   log.append("\n--- Begin of Response Summary ---");
            //    collectResponseSummary(camt052Response, log, bankAccount);
            //    log.append("\n--- End of Response Summary ---\n");
            //    result = log.toString();
           // } else {
           //     log.append("\n\nDeserialization successful, but response document contains neither a report nor a rejection.");
           // }
            		}

		//else{}
		return result;
	}
	
	public String getToken(MPayment payment) {				 
		ProcessInfo  processInfo =
				ProcessBuilder.create(Env.getCtx())
				.process(SVBACGetToken.getProcessId())
				.withTitle(SVBACGetToken.getProcessName())
				.withRecordId(MBankAccount.Table_ID, payment.getC_BankAccount_ID())
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
	
	public String setSignatureRegistration() {
		
		
		return "";
	}
	
	private static String collectResponseSummary(CAMT052Response response, StringBuffer log, MBankAccount bankAccount) {
        CAMT052ResponseEnvelope responseEnvelope = null;
        CAMT052ResponseDocument responseDocument = null;

        boolean isEnvelope = response.getcAMT052ResponseFile().getcAMT052ResponseEnvelope() != null;
        if (isEnvelope) {
            log.append("\nCAMT052 Envelope present: " + isEnvelope);
            responseEnvelope   = response.getcAMT052ResponseFile().getcAMT052ResponseEnvelope();

            boolean isDocument = responseEnvelope.getcAMT052ResponseDocument() != null;
            if (isDocument) {
                log.append("\nCAMT052 Document present: " + isDocument);
                responseDocument = responseEnvelope.getcAMT052ResponseDocument();
            } else {
                log.append("\nCAMT052 Document is present: false");
                return "CAMT052 Document is present: false";
            }
        } else {
            log.append("\nCAMT052 Envelop present: false");
            return "CAMT052 Envelop present: false";
        }
        String amtString =  responseDocument.getBkToCstmrAcctRpt().getRpt().getBal().getAmt().getAmt();
                BigDecimal amt =  new BigDecimal(amtString);
                bankAccount.set_ValueOfColumn("currentBalanceOnline", amt);
                bankAccount.saveEx();
        log.append("\n********************************************");
        log.append("\n******* CAMT052 Response *******");
        log.append("\n********************************************");
        log.append("\n*** AppHdr ***");
        log.append("\n    Fr-BICFI : "  + responseEnvelope.getAppHdr().getFr().getfIId().getFinInstnId().getbICFI());
        log.append("\n    To-BICFI : "  + responseEnvelope.getAppHdr().getTo().getfIId().getFinInstnId().getbICFI());
        log.append("\n    BizMshIdr: "  + responseEnvelope.getAppHdr().getBizMsgIdr());
        log.append("\n    BizSvc:    "  + responseEnvelope.getAppHdr().getBizSvc());
        log.append("\n    CreDt:     "  + responseEnvelope.getAppHdr().getCreDt());
        log.append("\n    MsgDefIdr: "  + responseEnvelope.getAppHdr().getMsgDefIdr());
        log.append("\n********************************************");
        log.append("\n************* GrpHdr ***********************");
        log.append("\n    CreDtTm:   "  + responseDocument.getBkToCstmrAcctRpt().getGrpHdr().getCreDtTm());
        log.append("\n    MsgId:     "  + responseDocument.getBkToCstmrAcctRpt().getGrpHdr().getMsgId());
        log.append("\n********************************************");
        log.append("\n*** CAMT052 Response Document ***");
        log.append("\n********************************************");
        log.append("\n    AcctId:    "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getAcct().getAcctId().getAcctIdOthr().getId());
        log.append("\n    Acct-Ccy:  "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getAcct().getCcy());
        log.append("\n    Bal-Amt:   "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getBal().getAmt().getAmt());
        log.append("\n    Bal-Ccy:   "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getBal().getAmt().getCcy());
        log.append("\n    CdtDbtInd: "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getBal().getCdtDbtInd());
        log.append("\n    Bal-DtTm:  "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getBal().getDt().getDtTm());
        log.append("\n    Bal-Cd:    "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getBal().getTp().getCdOrPrtry().getCd());
        log.append("\n    Id:        "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getId());

        log.append("\n    Bal-RptId: "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getRptPgntn().getPgNb());
        log.append("\n    LastPgInd: "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getRptPgntn().isLastPgInd());
        log.append("\n********************************************");
        log.append("\n********************************************");
        return log.toString();
    }

	

	
	
	

	/**
	 * @param args
	 */
	
}


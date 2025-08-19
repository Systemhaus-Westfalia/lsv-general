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

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.adempiere.core.domains.models.I_C_Invoice;
import org.adempiere.core.domains.models.X_I_BankStatement;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBankAccount;
import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.PO;
import org.compiere.process.ImportBankStatement;
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
import org.shw.lsv.ebanking.bac.sv.camt052.response.RptPgntn;
import org.shw.lsv.ebanking.bac.sv.camt053.request.CAMT053Request;
import org.shw.lsv.ebanking.bac.sv.camt053.response.Bal;
import org.shw.lsv.ebanking.bac.sv.camt053.response.BkToCstmrStmt;
import org.shw.lsv.ebanking.bac.sv.camt053.response.CAMT053Response;
import org.shw.lsv.ebanking.bac.sv.camt053.response.CAMT053ResponseDocument;
import org.shw.lsv.ebanking.bac.sv.camt053.response.CAMT053ResponseEnvelope;
import org.shw.lsv.ebanking.bac.sv.camt053.response.FrToDt;
import org.shw.lsv.ebanking.bac.sv.camt053.response.NtryDtls;
import org.shw.lsv.ebanking.bac.sv.camt053.response.Stmt;
import org.shw.lsv.ebanking.bac.sv.camt053.response.StmtOfAccountElement;
import org.shw.lsv.ebanking.bac.sv.camt053.response.TxDtls;
import org.shw.lsv.ebanking.bac.sv.handling.JsonProcessor;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationException;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestBuilder;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.Acct;
import org.shw.lsv.ebanking.bac.sv.misc.AppHdr;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.misc.GrpHdr;
import org.shw.lsv.ebanking.bac.sv.misc.Rejection;
import org.shw.lsv.ebanking.bac.sv.process.SVBACGetToken;
import org.shw.lsv.ebanking.bac.sv.util.RequestParamsFactory;
import org.shw.lsv.ebanking.bac.sv.z_test.estado_de_cuenta.response.CAMT053DeSerializationWithFiles;
import org.shw.lsv.util.support.IDeclarationDocument;
import org.shw.lsv.util.support.IDeclarationProvider;
import org.spin.model.MADAppRegistration;

import com.fasterxml.jackson.annotation.JsonInclude;  

/**
 * 	A implementation class for findex.la provider using LSV
 * 	@author Yamel Senih, ysenih@erpya.com, ERPCyA http://www.erpya.com
 */
public class SVBACStatmentOfAccount implements IDeclarationProvider {
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
	private int bankAccountID=0;
	private String transactionName = "";
	private Timestamp dateFrom = null;
	private Timestamp dateTo = null;
	
	

	public SVBACStatmentOfAccount() {
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

	public int getBankAccountID() {
		return bankAccountID;
	}

	public void setBankAccountID(int bankAccountID) {
		this.bankAccountID = bankAccountID;
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
	

	public String getStatementOfAccount() throws Exception {
		String result = "";
		MBankAccount bankAccount = new MBankAccount(Env.getCtx(), bankAccountID, getTransactionName());
		getToken();

		this.providerHost = registration.getParameterValue(PROVIDER_HOST);
		this.path = registration.getParameterValue(PATH); 

		String jsonOutput = "";

		LocalDateTime now = LocalDateTime.now();
		System.err.println("CAMT052 serialization started at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

		// 1. Create collector for test diagnostics
		JsonValidationExceptionCollector collector = new JsonValidationExceptionCollector();
		collector.setPrintImmediately(true); // See errors as they happen

		// 2. Build test parameters
        Integer  pageNumber = 1;
		RequestParams params = RequestParamsFactory.createCamt053Params(pageNumber, bankAccount);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		String dateFrom = dateFormat.format(getDateFrom());
		String dateTo = dateFormat.format(getDateTo());
		params.setFrdt(dateFrom);
		params.setTodt(dateTo);

		try {
            CAMT053Request request = RequestBuilder.build(CAMT053Request.class, params, collector);

			// 4. Serialization test
			JsonProcessor processor = new JsonProcessor(collector);
			jsonOutput = processor.serialize(request);

			// 5. Print Json
			System.out.println("\nGenerated JSON:");
			System.out.println(jsonOutput);

			System.out.println("CAMT052 serialization succeeded without errors.\n");

			now = LocalDateTime.now();
			System.err.println("CAMT052 serialization finished at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

		} catch (JsonValidationException e) {
            System.err.println("CAMT053 serialization Test failed: " + e.getMessage());
            System.err.println(e.getValidationErrors());
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


		System.out.println("StatementOfAccount: " + jsonOutput);
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

            CAMT053Response  camt053Response= processor.deserialize(jsonResponse.toString(), CAMT053Response.class);
            
            CAMT053ResponseDocument document = camt053Response.getCamt053ResponseFile().getCamt053ResponseEnvelope().getcAMT053ResponseDocument();
            		
            if (document != null && document.getRejection() != null) {
                log.append("\n--- Begin of Rejection Summary ---");
                Rejection rejection = document.getRejection();
                if (rejection.getRsn() != null) {
                    log.append("\nRejection Message Received:");
                    log.append("\nReason Code: ").append(rejection.getRsn().getRjctgPtyRsn());
                    log.append("\nDescription: ").append(rejection.getRsn().getRsnDesc());
                    result = log.toString();
                } else {
                    log.append("\nRejection object present but Reason (Rsn) is null.");
                    result = log.toString();
                }
                log.append("\n--- End of Rejection Summary ---\n");
            } else if (document != null && document.getBkToCstmrStmt() != null) {
                log.append("\n--- Begin of Response Summary ---");
                collectResponseSummary(camt053Response, log, bankAccount);
                collectResponseSummaryText(camt053Response, log);
                log.append("\n--- End of Response Summary ---\n");
                result = log.toString();
            } else {
                log.append("\n\nDeserialization successful, but response document contains neither a report nor a rejection.");
            }
            		}

		else{}
		return result;
	}
	
	public String getToken() {				 
		ProcessInfo  processInfo =
				ProcessBuilder.create(Env.getCtx())
				.process(SVBACGetToken.getProcessId())
				.withTitle(SVBACGetToken.getProcessName())
				.withRecordId(MBankAccount.Table_ID, bankAccountID)
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
	
	

	public Timestamp getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Timestamp dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Timestamp getDateTo() {
		return dateTo;
	}

	public void setDateTo(Timestamp dateTo) {
		this.dateTo = dateTo;
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
	
	private static String collectResponseSummary(CAMT053Response response, StringBuffer log, MBankAccount bankAccount) {
        CAMT053ResponseEnvelope responseEnvelope = null;
        CAMT053ResponseDocument responseDocument = null;

        boolean isEnvelope = response.getCamt053ResponseFile().getCamt053ResponseEnvelope() != null;
        if (isEnvelope) {
            log.append("\nCAMT052 Envelope present: " + isEnvelope);
            responseEnvelope   = response.getCamt053ResponseFile().getCamt053ResponseEnvelope();

            boolean isDocument = responseEnvelope.getcAMT053ResponseDocument() != null;
            if (isDocument) {
                log.append("\nCAMT052 Document present: " + isDocument);
                responseDocument = responseEnvelope.getcAMT053ResponseDocument();
                BkToCstmrStmt bkToCstmrStmt = responseDocument.getBkToCstmrStmt();

                Stmt stmt = bkToCstmrStmt.getStmt();

                RptPgntn rptPgntn = stmt.getRptPgntn();
                Acct acct = stmt.getAcct();
                if (stmt.getStmtOfAccountElements() != null && !stmt.getStmtOfAccountElements().isEmpty()) {
                    for (int i = 0; i < stmt.getStmtOfAccountElements().size(); i++) {

                        StmtOfAccountElement entry = stmt.getStmtOfAccountElements().get(i);
                        X_I_BankStatement impbankStatement = new X_I_BankStatement(bankAccount.getCtx(), 0, bankAccount.get_TrxName());
                        impbankStatement.setC_BankAccount_ID(bankAccount.getC_BankAccount_ID());
                        impbankStatement.setC_Currency_ID(bankAccount.getC_Currency_ID());
                        if (entry.getAmt() != null) {
                        	BigDecimal amt = new BigDecimal(entry.getAmt().getAmt());

                            String debcred = entry.getCdtDbtInd();
                            BigDecimal multiplier = Env.ONE;
                            if (debcred.equals(EBankingConstants.DBIT))
                            	multiplier.negate();
                        	impbankStatement.setStmtAmt(amt.multiply(multiplier));
                        	impbankStatement.setTrxAmt(amt);
                        	String bookgDt = (entry.getBookgDt() != null) ? entry.getBookgDt().getDt() : "N/A";
                        	if (bookgDt.equals("\"N/A\""))
                            	return "error";
                            String datetimeString = bookgDt + " 00:00:00";

                            // Define the format of your string
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                            // Parse the string into a LocalDateTime object
                            LocalDateTime localDateTime = LocalDateTime.parse(datetimeString, formatter);

                            // Convert the LocalDateTime to a Timestamp
                            Timestamp timestamp = Timestamp.valueOf(localDateTime);
                            impbankStatement.setStatementDate(timestamp);
                            impbankStatement.setStatementLineDate(timestamp);
                            impbankStatement.setDateAcct(timestamp);
                            impbankStatement.setReferenceNo(entry.getNtryRef());
                            impbankStatement.setLineDescription(entry.getAddtlNtryInf());
                            impbankStatement.saveEx();
                           // summary.append("        Amt-Ccy:     ").append(balance.getAmt().getCcy() != null ? balance.getAmt().getCcy() : "N/A").append("\n");
                            //summary.append("        Amt-Amt:     ").append(balance.getAmt().getAmt() != null ? balance.getAmt().getAmt() : "N/A").append("\n");
                        }
                    }
                }
                if (stmt.getBalances() != null && !stmt.getBalances().isEmpty()) {}
            } else {
                log.append("\nCAMT052 Document is present: false");
                return "CAMT052 Document is present: false";
            }
        } else {
            log.append("\nCAMT052 Envelop present: false");
            return "CAMT052 Envelop present: false";
        }
        return "";
        		
    }
	
	private static void collectResponseSummaryText(CAMT053Response response, StringBuffer summary) {

        LocalDateTime now = LocalDateTime.now();
        final String CLASS_NAME = CAMT053DeSerializationWithFiles.class.getSimpleName();
        System.err.println(CLASS_NAME + " started at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        // Build file names
        String inputFileName  = CLASS_NAME + "_INPUT_FILE_01.json";
        String outputFileName = CLASS_NAME + "_OUTPUT.txt";
        String errorFileName  = String.format("%s_ERRORS.txt", CLASS_NAME);

        // Build file paths
        Path baseDir        = Paths.get(EBankingConstants.TEST_BASE_DIRECTORY_PATH, 
            "estado_de_cuenta", 
            EBankingConstants.TEST_FILES_RESPONSE);
        Path inputFilePath  = baseDir.resolve(inputFileName);
        Path outputFilePath = baseDir.resolve(outputFileName);
        Path errorFilePath  = baseDir.resolve(errorFileName);
        CAMT053ResponseEnvelope envelope = response.getCamt053ResponseFile().getCamt053ResponseEnvelope();
        summary.append("CAMT053 Envelope present: ").append(envelope != null).append("\n");

        if (envelope == null) return;

        CAMT053ResponseDocument document = envelope.getcAMT053ResponseDocument();
        summary.append("CAMT053 Document present: ").append(document != null).append("\n");

        summary.append("********************************************\n");
        summary.append("Ergebnisse:\n");

        summary.append("*** AppHdr ***\n");
        AppHdr appHdr = envelope.getAppHdr();
        if (appHdr != null) {
            // Safely access nested properties
            String frBicfi = (appHdr.getFr() != null && appHdr.getFr().getfIId() != null && appHdr.getFr().getfIId().getFinInstnId() != null) ? appHdr.getFr().getfIId().getFinInstnId().getbICFI() : "N/A";
            summary.append("    Fr-BICFI : ").append(frBicfi).append("\n");
            String toBicfi = (appHdr.getTo() != null && appHdr.getTo().getfIId() != null && appHdr.getTo().getfIId().getFinInstnId() != null) ? appHdr.getTo().getfIId().getFinInstnId().getbICFI() : "N/A";
            summary.append("    To-BICFI : ").append(toBicfi).append("\n");
            summary.append("    BizMsgIdr: ").append(appHdr.getBizMsgIdr()).append("\n");
            summary.append("    MsgDefIdr: ").append(appHdr.getMsgDefIdr()).append("\n");
            summary.append("    BizSvc:    ").append(appHdr.getBizSvc()).append("\n");
            summary.append("    CreDt:     ").append(appHdr.getCreDt()).append("\n");
        } else {
            summary.append("    AppHdr not available.\n");
        }

        summary.append("********************************************\n");
        summary.append("******** CAMT053 Response Document ********\n");
        summary.append("********************************************\n");
        if (document != null && document.getBkToCstmrStmt() != null) {
            BkToCstmrStmt bkToCstmrStmt = document.getBkToCstmrStmt();
            if (bkToCstmrStmt.getGrpHdr() != null) {
                GrpHdr grpHdr = bkToCstmrStmt.getGrpHdr();
                summary.append("    MsgId:     ").append(grpHdr.getMsgId()).append("\n");
                summary.append("    CreDtTm:   ").append(grpHdr.getCreDtTm()).append("\n");
            }

            Stmt stmt = bkToCstmrStmt.getStmt();
            if (stmt != null) {
                summary.append("************* Stmt ***********************\n");
                summary.append("    Id:             ").append(stmt.getId() != null ? stmt.getId() : "N/A").append("\n");

                RptPgntn rptPgntn = stmt.getRptPgntn();
                if (rptPgntn != null) {
                    summary.append("    StmtPgntn-PgNb: ").append(rptPgntn.getPgNb() != null ? rptPgntn.getPgNb() : "N/A").append("\n");
                    summary.append("    StmtPgntn-Last: ").append(rptPgntn.isLastPgInd()).append("\n");
                }

                summary.append("    ElctrncSeqNb:   ").append(stmt.getElctrncSeqNb() != null ? stmt.getElctrncSeqNb() : "N/A").append("\n");

                FrToDt frToDt = stmt.getFrToDt();
                if (frToDt != null) {
                    summary.append("    FrToDt-FrDtTm:  ").append(frToDt.getFrDtTm() != null ? frToDt.getFrDtTm() : "N/A").append("\n");
                    summary.append("    FrToDt-ToDtTm:  ").append(frToDt.getToDtTm() != null ? frToDt.getToDtTm() : "N/A").append("\n");
                }

                Acct acct = stmt.getAcct();
                if (acct != null) {
                    String acctIdStr = (acct.getAcctId() != null && acct.getAcctId().getAcctIdOthr() != null) ? acct.getAcctId().getAcctIdOthr().getId() : "N/A";
                    summary.append("    Acct-Id:        ").append(acctIdStr).append("\n");
                    summary.append("    Acct-Ccy:       ").append(acct.getCcy() != null ? acct.getCcy() : "N/A").append("\n");
                }

                summary.append("*** Balances ***\n");
                if (stmt.getBalances() != null && !stmt.getBalances().isEmpty()) {
                    for (int i = 0; i < stmt.getBalances().size(); i++) {
                        Bal balance = stmt.getBalances().get(i);
                        summary.append("      Balance[").append(i).append("]:\n");
                        if (balance != null) {
                            String balTpCd = (balance.getTp() != null && balance.getTp().getCdOrPrtry() != null) ? balance.getTp().getCdOrPrtry().getCd() : "N/A";
                            summary.append("        Tp-Cd:       ").append(balTpCd).append("\n");
                            summary.append("        CdtDbtInd:   ").append(balance.getCdtDbtInd() != null ? balance.getCdtDbtInd() : "N/A").append("\n");
                            String balDt = (balance.getDt() != null) ? balance.getDt().getDt() : "N/A";
                            summary.append("        Dt:          ").append(balDt).append("\n");
                            if (balance.getAmt() != null) {
                                summary.append("        Amt-Ccy:     ").append(balance.getAmt().getCcy() != null ? balance.getAmt().getCcy() : "N/A").append("\n");
                                summary.append("        Amt-Amt:     ").append(balance.getAmt().getAmt() != null ? balance.getAmt().getAmt() : "N/A").append("\n");
                            }
                        }
                    }
                }

                summary.append("********  StmtOfAccountElements  ***********\n");
                if (stmt.getStmtOfAccountElements() != null && !stmt.getStmtOfAccountElements().isEmpty()) {
                    for (int i = 0; i < stmt.getStmtOfAccountElements().size(); i++) {
                        StmtOfAccountElement entry = stmt.getStmtOfAccountElements().get(i);
                        summary.append("      Ntry[").append(i).append("]:\n");
                        if (entry != null) {
                            summary.append("        NtryRef:        ").append(entry.getNtryRef() != null ? entry.getNtryRef() : "N/A").append("\n");
                            if (entry.getAmt() != null) {
                                summary.append("        Amt-Ccy:        ").append(entry.getAmt().getCcy() != null ? entry.getAmt().getCcy() : "N/A").append("\n");
                                summary.append("        Amt-Amt:        ").append(entry.getAmt().getAmt() != null ? entry.getAmt().getAmt() : "N/A").append("\n");
                            }
                            summary.append("        CdtDbtInd:      ").append(entry.getCdtDbtInd() != null ? entry.getCdtDbtInd() : "N/A").append("\n");
                            
                            // Adding missing fields from the other implementation
                            String stsCd = (entry.getSts() != null) ? entry.getSts().getCd() : "N/A";
                            summary.append("        Sts-Cd:         ").append(stsCd).append("\n");

                            String bookgDt = (entry.getBookgDt() != null) ? entry.getBookgDt().getDt() : "N/A";
                            summary.append("        BookgDt-Dt:     ").append(bookgDt).append("\n");

                            String valDt = (entry.getValDt() != null) ? entry.getValDt().getDt() : "N/A";
                            summary.append("        ValDt-Dt:       ").append(valDt).append("\n");

                            if (entry.getBkTxCd() != null && entry.getBkTxCd().getDomn() != null) {
                                summary.append("        BkTxCd-Domn-Cd: ").append(entry.getBkTxCd().getDomn().getCd() != null ? entry.getBkTxCd().getDomn().getCd() : "N/A").append("\n");
                                if (entry.getBkTxCd().getDomn().getFmly() != null) {
                                    summary.append("        BkTxCd-Fmly-Cd: ").append(entry.getBkTxCd().getDomn().getFmly().getCd() != null ? entry.getBkTxCd().getDomn().getFmly().getCd() : "N/A").append("\n");
                                    summary.append("        BkTxCd-SubFmly: ").append(entry.getBkTxCd().getDomn().getFmly().getSubFmlyCd() != null ? entry.getBkTxCd().getDomn().getFmly().getSubFmlyCd() : "N/A").append("\n");
                                }
                            }

                            summary.append("        AddtlNtryInf:   ").append(entry.getAddtlNtryInf() != null ? entry.getAddtlNtryInf() : "N/A").append("\n");

                            List<NtryDtls> ntryDtlsList = entry.getNtryDtls();
                            if (ntryDtlsList != null && !ntryDtlsList.isEmpty()) {
                                int ntryDtlsIndex = 0;
                                for (NtryDtls ntryDtls : ntryDtlsList) {
                                    summary.append("        NtryDtls[").append(ntryDtlsIndex).append("]:\n");
                                    if (ntryDtls != null && ntryDtls.getTxDtls() != null) {
                                        TxDtls txDtls = ntryDtls.getTxDtls();
                                        summary.append("          TxDtls:\n");
                                        if (txDtls.getPmtId() != null) {
                                            summary.append("            Refs-EndToEndId: ").append(txDtls.getPmtId().getEndToEndId() != null ? txDtls.getPmtId().getEndToEndId() : "N/A").append("\n");
                                        }
                                        if (txDtls.getAmt() != null) {
                                            summary.append("            Amt:             ").append(txDtls.getAmt().getAmt()).append("\n");
                                        }
                                        summary.append("            CdtDbtInd:       ").append(txDtls.getCdtDbtInd() != null ? txDtls.getCdtDbtInd() : "N/A").append("\n");
                                    }
                                    ntryDtlsIndex++;
                                }
                            }
                        }
                    }
                }
            }
        }
        summary.append("********************************************\n");
        writeToFile(outputFilePath, summary.toString());
    }
	
	private static void writeToFile(Path filePath, String content) {
        try {
            Files.createDirectories(filePath.getParent());
            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                writer.write(content);
            }
            System.out.println("Wrote output to: " + filePath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error writing to file: " + filePath.toAbsolutePath());
            e.printStackTrace();
        }
    }

	

	
	
	

	/**
	 * @param args
	 */
	
}


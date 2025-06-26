package org.shw.lsv.ebanking.bac.sv.test.estado_de_cuenta.response;

import java.time.LocalDateTime;
import org.shw.lsv.ebanking.bac.sv.camt052.response.RptPgntn;
import org.shw.lsv.ebanking.bac.sv.camt053.request.CAMT053Request;
import org.shw.lsv.ebanking.bac.sv.camt053.response.CAMT053Response;
import org.shw.lsv.ebanking.bac.sv.camt053.response.BkToCstmrStmt;
import org.shw.lsv.ebanking.bac.sv.camt053.response.Stmt;
import org.shw.lsv.ebanking.bac.sv.handling.JsonProcessor;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationException;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestBuilder;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.AppHdr;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

public class CAMT053CombinedPaginationTest {

    // Simulate a scenario with a specific number of pages
    private static final int TOTAL_SIMULATED_PAGES = 3;
    private static final int MAX_LOOP_ITERATIONS = 10; // Safety break

    public static void main(String[] args) {
        LocalDateTime testStartTime = LocalDateTime.now();
        System.err.println("CAMT.053 Combined Pagination Test started at: " + testStartTime.format(EBankingConstants.DATETIME_FORMATTER));

        int currentPageToRequest = 0; // CAMT.053 eqseq is often 0-indexed for the first page
        boolean isLastPageReceived = false;
        int iterations = 0;

        while (!isLastPageReceived && iterations < MAX_LOOP_ITERATIONS) {
            iterations++;
            System.out.println("\n--- Iteration " + iterations + ": Requesting page " + currentPageToRequest + " ---");

            JsonValidationExceptionCollector requestCollector = new JsonValidationExceptionCollector();
            requestCollector.setPrintImmediately(true);

            RequestParams params = createRequestParams(currentPageToRequest);
            String requestJsonOutput = "";
            CAMT053Response deserializedResponse = null;

            try {
                // 1. Build and Serialize CAMT.053 Request
                System.out.println("Building CAMT.053 request for page " + currentPageToRequest + "...");
                CAMT053Request request = RequestBuilder.build(CAMT053Request.class, params, requestCollector);

                JsonProcessor requestProcessor = new JsonProcessor(requestCollector);
                requestJsonOutput = requestProcessor.serialize(request);

                System.out.println("Generated Request JSON for page " + currentPageToRequest + ":");
                System.out.println(requestJsonOutput);
                if (requestCollector.hasErrors()) {
                    System.err.println("Errors during request generation for page " + currentPageToRequest + ":");
                    System.err.println(requestCollector.getAllErrors());
                    break; // Stop if request generation failed
                }
                System.out.println("CAMT.053 request serialization for page " + currentPageToRequest + " succeeded.");

                // 2. Simulate Bank Response
                // In a real scenario, requestJsonOutput would be sent to the bank,
                // and the bank's response (String) would be received here.
                // We simulate this by generating a response JSON.
                System.out.println("\nSimulating bank response for page " + currentPageToRequest + "...");
                String simulatedResponseJson = simulateAndGetResponseJson(currentPageToRequest, TOTAL_SIMULATED_PAGES);
                System.out.println("Simulated Response JSON:");
                System.out.println(simulatedResponseJson);

                // 3. Deserialize CAMT.053 Response
                JsonValidationExceptionCollector responseCollector = new JsonValidationExceptionCollector();
                responseCollector.setPrintImmediately(true);
                JsonProcessor responseProcessor = new JsonProcessor(responseCollector);

                System.out.println("\nDeserializing simulated bank response...");
                deserializedResponse = responseProcessor.deserialize(simulatedResponseJson, CAMT053Response.class);

                if (responseCollector.hasErrors()) {
                    System.err.println("CAMT.053 deserialization for page " + currentPageToRequest + " succeeded with warnings/errors:");
                    System.err.println(responseCollector.getAllErrors());
                    // Decide if we should break or continue despite warnings
                } else {
                    System.out.println("CAMT.053 deserialization for page " + currentPageToRequest + " completed cleanly.");
                }

                System.out.println("\nDeserialized CAMT.053 Response Details for page " + currentPageToRequest + ":");
                printResponseSummary(deserializedResponse);

                // 4. Pagination Logic
                Stmt stmt = deserializedResponse.getcAMT053ResponseEnvelope().getcAMT053ResponseDocument().getBkToCstmrStmt().getStmt();
                if (stmt != null && stmt.getRptPgntn() != null) {
                    RptPgntn rptPgntn = stmt.getRptPgntn();
                    String responsePgNbStr = rptPgntn.getPgNb();
                    isLastPageReceived = rptPgntn.isLastPgInd();

                    System.out.println("\nPagination Info from Response:");
                    System.out.println("  Response Page Number (PgNb): " + responsePgNbStr);
                    System.out.println("  Is Last Page (LastPgInd): " + isLastPageReceived);

                    if (!isLastPageReceived) {
                        try {
                            int responsePgNb = Integer.parseInt(responsePgNbStr);
                            currentPageToRequest = responsePgNb + 1;
                            System.out.println("  Next page to request: " + currentPageToRequest);
                        } catch (NumberFormatException e) {
                            System.err.println("Error parsing PgNb from response: '" + responsePgNbStr + "'. Stopping.");
                            break;
                        }
                    } else {
                        System.out.println("Last page received. Terminating pagination loop.");
                    }
                } else {
                    System.err.println("Could not find Stmt or RptPgntn in the response. Stopping pagination.");
                    break;
                }

            } catch (JsonValidationException e) {
                System.err.println("CAMT.053 processing for page " + currentPageToRequest + " failed critically: " + e.getMessage());
                System.err.println(e.getValidationErrors());
                System.err.println("********************************************");
                break; // Stop on critical failure
            } catch (Exception e) {
                System.err.println("An unexpected error occurred during iteration for page " + currentPageToRequest + ": " + e.getMessage());
                e.printStackTrace(System.err);
                break;
            }
        }

        if (iterations >= MAX_LOOP_ITERATIONS && !isLastPageReceived) {
            System.err.println("\nMax loop iterations reached. Terminating test.");
        }

        LocalDateTime testEndTime = LocalDateTime.now();
        System.err.println("\nCAMT.053 Combined Pagination Test finished at: " + testEndTime.format(EBankingConstants.DATETIME_FORMATTER));
    }

    private static RequestParams createRequestParams(int pageNumber) {
        String ESTADO_CUENTA_MESSAGE_ID = "EdC-ADClientName/(CuentaNr)";

        return new RequestParams()
            // AppHdr
            .setBicfiFr("AMERICA3PLX")
            .setBicfiTo("BAMCSVSS")
            .setBizMsgIdr(ESTADO_CUENTA_MESSAGE_ID + "-Page-" + pageNumber) // Make BizMsgIdr unique per page
            .setMsgDefIdr("camt.060.001.05") // For CAMT053Request, this should be camt.053.001.08 as per CAMT053SerializationTest comments.
                                             // However, CAMT053SerializationTest uses camt.060.001.05. Clarify correct value.
                                             // For now, using what was in CAMT053SerializationTest.
            .setBizSvc("swift.cbprplus.01")
            .setCreDt(LocalDateTime.now().minusHours(EBankingConstants.TIMEZONE_SHIFT_EL_SALVADOR).format(EBankingConstants.ISO_OFFSET_DATE_TIME_FORMATTER)) // Dynamic CreDt
            .setXmlns("urn:iso:std:iso:20022:tech:xsd:camt.060.001.05") // Same as MsgDefIdr, check if this is for request or response.
                                                                      // CAMT053 Request usually doesn't set xmlns at this level.

            // Group Header
            .setMsgId(ESTADO_CUENTA_MESSAGE_ID + "-Grp-" + pageNumber) // Make MsgId unique per page
            .setCreDtTm(LocalDateTime.now().minusHours(6).format(EBankingConstants.ISO_OFFSET_DATE_TIME_FORMATTER)) // Dynamic CreDtTm

            // Document (Report Parameters for camt.060, which is AccountReportingRequest)
            .setReqdMsgNmId(ESTADO_CUENTA_MESSAGE_ID + "-Doc-" + pageNumber) // Specifies the type of report being requested
            .setAcctId("999888666")
            .setBicfiAcctOwnr("AMERICA3PLX")
            .setCcy("USD")
            .setFrdt("2025-06-01")
            .setTodt("2025-06-30")
            .setTp(EBankingConstants.PATTERN_TP)
            .setEqseq(Integer.toString(pageNumber)); // This is the crucial pagination parameter
    }

    private static String simulateAndGetResponseJson(int requestedPageNumber, int totalPages) {
        boolean isLastPage = (requestedPageNumber >= totalPages - 1);
        // Using a simplified version of the JSON from CAMT053DeSerializationTest for clarity
        // In a real simulation, you'd populate more fields meaningfully.
        String pageNb = String.valueOf(requestedPageNumber);
        String lastPgInd = String.valueOf(isLastPage).toLowerCase(); // "true" or "false"

        // Dynamic values for the response
        String bizMsgIdr = "Resp-" + System.currentTimeMillis(); // Unique ID for the response
        String creDt = LocalDateTime.now().minusHours(6).format(EBankingConstants.ISO_OFFSET_DATE_TIME_FORMATTER);
        String stmtId = bizMsgIdr + pageNb; // Unique statement ID

        // Basic structure, you might need to add more elements for your actual StmtOfAccountElement, Bal, etc.
        // For this example, we focus on AppHdr and pagination fields.
        String jsonContent = String.format(
            "{\n" +
            "  \"Envelope\": {\n" +
            "    \"AppHdr\": {\n" +
            "      \"Fr\": { \"FIId\": { \"FinInstnId\": { \"BICFI\": \"BAMCSVSSXXX\" } } },\n" + // Bank sending
            "      \"To\": { \"FIId\": { \"FinInstnId\": { \"BICFI\": \"AMERICA3PLX\" } } },\n" + // Company receiving
            "      \"BizMsgIdr\": \"%s\",\n" + // Response's BizMsgIdr
            "      \"MsgDefIdr\": \"camt.053.001.08\",\n" +
            "      \"BizSvc\": \"swift.cbprplus.02\",\n" + // As per CAMT053DeSerialization example
            "      \"CreDt\": \"%s\"\n" +
            "    },\n" +
            "    \"Document\": {\n" +
            "      \"BkToCstmrStmt\": {\n" +
            "        \"GrpHdr\": {\n" +
            "          \"MsgId\": \"%s\",\n" + // Correlate with BizMsgIdr or make unique
            "          \"CreDtTm\": \"%s\"\n" +
            "        },\n" +
            "        \"Stmt\": {\n" +
            "          \"Id\": \"%s\",\n" +
            "          \"StmtPgntn\": {\n" +
            "            \"PgNb\": \"%s\",\n" +
            "            \"LastPgInd\": %s\n" + // Note: boolean true/false, not string
            "          },\n" +
            "          \"ElctrncSeqNb\": %d,\n" + // Example, make dynamic if needed
            "          \"FrToDt\": { \"FrDtTm\": \"2024-07-22T00:00:00+00:00\", \"ToDtTm\": \"2024-07-22T23:59:59+00:00\" },\n" +
            "          \"Acct\": { \"Id\": { \"Othr\": { \"Id\": \"999888666\" } }, \"Ccy\": \"USD\" },\n" +
            "          \"Bal\": [ { \"Tp\": { \"CdOrPrtry\": { \"Cd\": \"OPBD\" } }, \"CdtDbtInd\": \"DBIT\", \"Dt\": { \"Dt\": \"2024-07-22\" }, \"Amt\": { \"Ccy\": \"USD\", \"Amt\": 10000.00 } } ],\n" + // Minimal balance
            "          \"Ntry\": []\n" + // Empty entries for simplicity, populate if needed for test
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}", bizMsgIdr, creDt, bizMsgIdr, creDt, stmtId, pageNb, lastPgInd, (requestedPageNumber + 1) * 100 // Example ElctrncSeqNb
        );
        return jsonContent;
    }

    // Copied and adapted from CAMT053DeSerialization for brevity.
    // Ensure all necessary classes (AppHdr, GrpHdr, etc.) are imported.
    private static void printResponseSummary(CAMT053Response response) {
        if (response == null || response.getcAMT053ResponseEnvelope() == null) {
            System.err.println("Response or Envelope is null. Cannot print summary.");
            return;
        }
        System.err.println("--- Response Summary ---");
        AppHdr appHdr = response.getcAMT053ResponseEnvelope().getAppHdr();
        if (appHdr != null) {
            System.err.println("AppHdr.BizMsgIdr: " + appHdr.getBizMsgIdr());
            System.err.println("AppHdr.MsgDefIdr: " + appHdr.getMsgDefIdr());
        } else {
            System.err.println("AppHdr not available.");
        }

        if (response.getcAMT053ResponseEnvelope().getcAMT053ResponseDocument() != null &&
            response.getcAMT053ResponseEnvelope().getcAMT053ResponseDocument().getBkToCstmrStmt() != null) {
            BkToCstmrStmt bkToCstmrStmt = response.getcAMT053ResponseEnvelope().getcAMT053ResponseDocument().getBkToCstmrStmt();
            if (bkToCstmrStmt.getGrpHdr() != null) {
                System.err.println("GrpHdr.MsgId: " + bkToCstmrStmt.getGrpHdr().getMsgId());
            }
            Stmt stmt = bkToCstmrStmt.getStmt();
            if (stmt != null) {
                System.err.println("Stmt.Id: " + stmt.getId());
                if (stmt.getRptPgntn() != null) {
                    System.err.println("Stmt.RptPgntn.PgNb: " + stmt.getRptPgntn().getPgNb());
                    System.err.println("Stmt.RptPgntn.LastPgInd: " + stmt.getRptPgntn().isLastPgInd());
                }
                 if (stmt.getAcct() != null && stmt.getAcct().getAcctId() != null && stmt.getAcct().getAcctId().getAcctIdOthr() != null) {
                    System.err.println("Stmt.Acct.Id: " + stmt.getAcct().getAcctId().getAcctIdOthr().getId());
                }
            }
        } else {
            System.err.println("Document or BkToCstmrStmt not available for detailed summary.");
        }
        System.err.println("--- End Response Summary ---");
    }
}

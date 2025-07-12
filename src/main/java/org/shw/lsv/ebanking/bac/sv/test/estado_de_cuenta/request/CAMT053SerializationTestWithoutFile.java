package org.shw.lsv.ebanking.bac.sv.test.estado_de_cuenta.request;

import java.time.LocalDateTime;

import org.shw.lsv.ebanking.bac.sv.camt053.request.CAMT053Request;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.RequestBuilder;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.handling.JsonProcessor;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationException;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

public class CAMT053SerializationTestWithoutFile {
    public static void main(String[] args) {
        String jsonOutput = "";

        LocalDateTime now = LocalDateTime.now();
        System.err.println("CAMT053 serialization started at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        // 1. Create collector for test diagnostics
        JsonValidationExceptionCollector collector = new JsonValidationExceptionCollector();
        collector.setPrintImmediately(true); // See errors as they happen

        // 2. Build test parameters
        Integer  pageNumber = 0;
        RequestParams params = createTestParams(pageNumber);

        try {
            // 3. Build request with test's collector
            CAMT053Request request = RequestBuilder.build(CAMT053Request.class, params, collector);

            // 4. Serialization test
            JsonProcessor processor = new JsonProcessor(collector);
            jsonOutput = processor.serialize(request);

            // 5. Print Json
            System.out.println("\nGenerated JSON:");
            System.out.println(jsonOutput);

            System.out.println("CAMT053 serialization succeeded without errors.\n");

            now = LocalDateTime.now();
            System.err.println("CAMT053 serialization finished at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        } catch (JsonValidationException e) {
            System.err.println("CAMT053 serialization Test failed: " + e.getMessage());
            System.err.println(e.getValidationErrors());
            System.err.println("********************************************");
        }
    }

    private static RequestParams createTestParams(Integer pageNumber) {
        String ESTADO_CUENTA_MESSAGE_ID = "EdC-ADClientName/(CuentaNr)";

        return new RequestParams()
            // AppHdr
            .setBicfiFr("AMERICA3PLX")                    // BIC of Company (festgelegt)
            // Official definition: The sending Bank Identifier Code.
            // "INVALIDBIC" Will trigger an error
            .setBicfiTo("BAMCSVSS")                     // BIC of bank (festgelegt)
            // Official definition: The receiving Bank Identifier Code.
            // "INVALIDBIC" Will trigger an error

            // BizMsgIdr is a unique message ID, assigned by the sender for tracking and reference.
            // This MAY BE echoed in the response, but must not
            // Max length: 35
            .setBizMsgIdr(ESTADO_CUENTA_MESSAGE_ID + "-01")

            .setMsgDefIdr("camt.060.001.05")          // The message definition identifier, indicating the type of message being sent. For CAMT053Request, must be camt.053.001.08
            .setBizSvc("swift.cbprplus.01")              // The business service identifier. Must be "swift.cbprplus.01"
            .setCreDt("2025-06-12T07:42:49-06:00")

            .setXmlns("urn:iso:std:iso:20022:tech:xsd:camt.060.001.05") // The XML namespace for the message, indicating the standard and version used

            // Group Header
            // Max. length: 35
            // Examples: "test:case? (ok)", "abc/def-ghi:jkl(mno)pqr.stu,vwx'y+z"
            // Usually not echoed in Response
            .setMsgId(ESTADO_CUENTA_MESSAGE_ID + "-02")         // ID assigned by the sender

            .setCreDtTm("2025-06-12T07:42:49-06:00")

            // Document

            // Max length: 35
            // Examples: "test:case? (ok)", "abc/def-ghi:jkl(mno)pqr.stu,vwx'y+z"
            // Usually not echoed in Response
            .setReqdMsgNmId(ESTADO_CUENTA_MESSAGE_ID + "-03")   // Specifies the type of report being requested

            .setAcctId("999888666")                      // Bank Account
            .setBicfiAcctOwnr("AMERICA3PLX")      // BIC of Company
            // Official definition: The BIC (SWIFT code) of the account owner’s bank (the agent).
            .setCcy("USD")
            
            .setFrdt("2025-06-01")                         // Start date for the reporting period
            .setTodt("2025-06-30")                         // End date for the reporting period
            .setTp(EBankingConstants.PATTERN_TP)                // Type of report, e.g., "ALL" for all elements
            .setEqseq(pageNumber.toString())                    // Sequence number for the report, used for pagination or ordering of reports
            
            ;
    }
}
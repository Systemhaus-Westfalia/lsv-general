package org.shw.lsv.ebanking.bac.sv.test.saldo_cuenta.request;

import java.time.LocalDateTime;

import org.shw.lsv.ebanking.bac.sv.camt052.request.CAMT052Request;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.RequestBuilder;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.handling.JsonProcessor;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationException;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;


public class CAMT052SerializationTestWithoutFile {
public static void main(String[] args) {
    String jsonOutput = "";

        LocalDateTime now = LocalDateTime.now();
        System.err.println("CAMT052 serialization started at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        // 1. Create collector for test diagnostics
        JsonValidationExceptionCollector collector = new JsonValidationExceptionCollector();
        collector.setPrintImmediately(true); // See errors as they happen

        // 2. Build test parameters
        RequestParams params = createTestParams();
        
        try {
            // 3. Build request with test's collector
            // CAMT052Request request = RequestBuilder.build(params, collector);  // Deprecated. Kann spaeter geloescht werden
            CAMT052Request request = RequestBuilder.build(CAMT052Request.class, params, collector);
            
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
            System.err.println("CAMT052 serialization Test failed: " + e.getMessage());
            System.err.println(e.getValidationErrors());
            System.err.println("********************************************");
        }
    }

    private static RequestParams createTestParams() {
        String SALDO_MESSAGE_ID = "Saldo-ADClientName/(CuentaNr)";

        return new RequestParams()
            // AppHdr
            .setBicfiFr(      "AMERICA3PLX")                    // BIC of Company (festgelegt)
                                                                      // Official definition: The sending Bank Identifier Code.
                                                                      // "INVALIDBIC" Will trigger an error
            .setBicfiTo(      "BAMCSVSSXXX")                  // BIC of bank (festgelegt)
                                                                      // Official definition: The receiving Bank Identifier Code.
                                                                      // "INVALIDBIC" Will trigger an error

            // BizMsgIdr is a unique message ID, assigned by the sender for tracking and reference.
            // This MAY BE echeoed in the response, but must not
            // Max length: 35
            .setBizMsgIdr(    SALDO_MESSAGE_ID + "-01")

            .setMsgDefIdr(    "camt.060.001.05")            // The message definition identifier, indicating the type of message being sent. Bei "Consulta Saldo Request" muß =() camt.052 ist das camt.060.001.05
            .setBizSvc(       "swift.cbprplus.01")             // The business service identifier. Hier muß == "swift.cbprplus.01"
            .setCreDt(        "2025-05-16T07:56:49-06:00")

            // Group Header
            // Max. length: 35
            // Examples: "test:case? (ok)", "abc/def-ghi:jkl(mno)pqr.stu,vwx'y+z"
            // Usually not echoed in Response
            .setMsgId(        SALDO_MESSAGE_ID + "-02")                // ID assigned by the sender

            .setCreDtTm(      "2025-05-16T07:56:49-06:00")

            // Document

            // Max length: 35
            // Examples: "test:case? (ok)", "abc/def-ghi:jkl(mno)pqr.stu,vwx'y+z"
            // Usually not echoed in Response
            .setReqdMsgNmId(  SALDO_MESSAGE_ID + "-03")                // Specifies the type of report being requested

            .setAcctId(       "200268472")                      // Bank Account
            .setBicfiAcctOwnr("AMERICA3PLX")             // BIC of Company
                                                                       // Official definition: The BIC (SWIFT code) of the account owner’s bank (the agent).
            .setCcy(          "USD")
            ;
    }
}

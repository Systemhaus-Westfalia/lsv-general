package org.shw.lsv.ebanking.bac.sv.test;

import java.time.LocalDateTime;

import org.adempiere.legacy.apache.ecs.xhtml.code;
import org.shw.lsv.ebanking.bac.sv.camt052.request.CAMT052Request;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.RequestBuilder;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.handling.JsonProcessor;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationException;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;


public class CAMT052SerializationTest {
public static void main(String[] args) {
    String jsonOutput = "";

        LocalDateTime now = LocalDateTime.now();
        System.err.println("Serialization started at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

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
            
            System.out.println("Serialization succeeded without errors.\n");
            
            now = LocalDateTime.now();
            System.err.println("Serialization finished at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        } catch (JsonValidationException e) {
            System.err.println("Serialization Test failed: " + e.getMessage());
            System.err.println(e.getValidationErrors());
            System.err.println("********************************************");
        }
    }

    private static RequestParams createTestParams() {
        return new RequestParams()
            // AppHdr
            .setBicfiFr(      "DUMMYMASTER")                    // The sending Bank Identifier Code (festgelegt). "INVALIDBIC" Will trigger error
            .setBicfiTo(      "BAMCSVSS")                     // The receiving Bank Identifier Code (festgelegt)
            .setBizMsgIdr(    "DummySaldoCta1")             // BizMsgIdr is a unique message ID, assigned by the sender for tracking and reference. 
            .setMsgDefIdr(    "camt.060.001.05")            // The message definition identifier, indicating the type of message being sent. Bei "Consulta Saldo Request" muß =() camt.052 ist das camt.060.001.05
            .setBizSvc(       "swift.cbprplus.01")             // The business service identifier. Hier muß == "swift.cbprplus.01"
            .setCreDt(        "2025-05-16T07:56:49-06:00")

            // Group Header
            .setMsgId(        "DummySaldoCta1")                             // ID assigned by the sender
            .setCreDtTm(      "2025-05-16T07:56:49-06:00")

            // Document
            .setReqdMsgNmId(  "ConsultaDeSaldoParaEmpresa" + "MiEmpresa")           // Specifies the type of report being requested
            .setAcctId(       "999888666")                                 // Bank Account
            .setBicfiAcctOwnr("DUMMYORDENA")                        // The BIC (SWIFT code) of the account owner’s bank (the agent).
            .setCcy(          "USD")
            ;
    }
}

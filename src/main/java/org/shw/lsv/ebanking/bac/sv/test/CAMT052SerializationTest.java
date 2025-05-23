package org.shw.lsv.ebanking.bac.sv.test;

import java.time.LocalDateTime;

import org.shw.lsv.ebanking.bac.sv.camt052.request.CAMT052Request;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParamsCamt052;
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
        RequestParamsCamt052 params = createTestParams();
        
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

    private static RequestParamsCamt052 createTestParams() {
        return new RequestParamsCamt052()
            .setBicfiFr(     "DUMMYMASTER")  // "INVALIDBIC" Will trigger error
            .setBicfiTo(      "BAMCSVSS")
            .setBizMsgIdr(    "DummySaldoCta1")
            .setMsgDefIdr(    "camt.060.001.05")
            .setBizSvc(       "swift.cbprplus.01")
            .setCreDt(        "2025-05-16T07:56:49-06:00")
            .setMsgId(        "DummySaldoCta1")
            .setCreDtTm(      "2025-05-16T07:56:49-06:00")
            .setReqdMsgNmId(  "AccountBalanceReportV08")
            .setAcctidothr(   "999888666")
            .setBicfiAcctOwnr("DUMMYORDENA")
            .setCcy(          "USD")
            ;
    }
}

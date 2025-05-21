package org.shw.lsv.ebanking.bac.sv.test;

import org.shw.lsv.ebanking.bac.sv.camt052.request.CAMT052Request;
import org.shw.lsv.ebanking.bac.sv.handling.Camt052RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.CamtRequestBuilder;
import org.shw.lsv.ebanking.bac.sv.handling.JsonProcessor;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationException;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;


public class CAMT052SerializationTest {
public static void main(String[] args) {
    String jsonOutput = "";

        // 1. Create collector for test diagnostics
        JsonValidationExceptionCollector collector = new JsonValidationExceptionCollector();
        collector.setPrintImmediately(true); // See errors as they happen

        // 2. Build test parameters
        Camt052RequestParams params = createTestParams();
        
        try {
            // 3. Build request with test's collector
            CAMT052Request request = CamtRequestBuilder.build(params, collector);
            
            // 4. Serialization test
            JsonProcessor processor = new JsonProcessor(collector);
            jsonOutput = processor.serialize(request);

            // 5. Print Json
            System.out.println("\nGenerated JSON:");
            System.out.println(jsonOutput);
            
            System.out.println("Serialization succeeded without errors:\n");

        } catch (JsonValidationException e) {
            System.err.println("Serialization Test failed: " + e.getMessage());
            System.err.println(e.getValidationErrors());
            System.err.println("********************************************");
        }
    }

    private static Camt052RequestParams createTestParams() {
        return new Camt052RequestParams()
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

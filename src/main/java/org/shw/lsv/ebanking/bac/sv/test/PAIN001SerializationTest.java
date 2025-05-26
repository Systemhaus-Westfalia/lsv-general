package org.shw.lsv.ebanking.bac.sv.test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.pain001.request.PAIN001Request;
import org.shw.lsv.ebanking.bac.sv.handling.RequestBuilder;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.handling.JsonProcessor;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationException;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

public class PAIN001SerializationTest {
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
            //PAIN001Request request = RequestBuilder.build(params, collector);  // Deprecated. Kann spaeter geloescht werden
            PAIN001Request request = RequestBuilder.build(PAIN001Request.class, params, collector);
            
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
            .setBicfiFr(      "DUMMYMASTER")  // "INVALIDBIC" Will trigger error
            .setBicfiTo(      "BAMCSVSS")
            .setBizMsgIdr(    "DummySaldoCta1")
            .setMsgDefIdr(    "camt.060.001.05")
            .setBizSvc(       "swift.cbprplus.01")
            .setCreDt(        "2025-05-16T07:56:49-06:00")

            // Group Header
            .setMsgId(        "DummySaldoCta1")
            .setCreDtTm(      "2025-05-16T07:56:49-06:00")

            //Document
            .setNbOfTxs(      Integer.valueOf(3)) // Number of transactions
            .setCtrlSum(      new BigDecimal("469.87"))
            .setCd(           "SUPP")
            .setReqdExctnDt(  "2023-06-27")
            .setNm(           "CLIENTE01")
            .setDbtrAcctID(   "999888666")
            .setIban(         "CR42010200690010163989")
            .setBicOrBEI(     "BSNJCRSJXXX")
            .setBic(          "BAMCSVSS")
            .setCountry(      "SV")

            // Payment Element
            .setEndToEndId(   "E2E-1234567890")
            .setInstrPrty(    "NORM")  // Instruction Priority, e.g. "NORM" or "HIGH"
            .setCcy(          "USD")
            .setInstdAmt(     "469.87")
            .setDebtrNm(      "CLIENTE01")  // Debtor Name: Unterschied zu "Nm"??
            ;
    }

}

package org.shw.lsv.ebanking.bac.sv.test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.pain001.response.PAIN001ResponseStatusReport;
import org.shw.lsv.ebanking.bac.sv.handling.RequestBuilder;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.handling.JsonProcessor;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationException;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

public class TMST038SerializationTest {
    public static void main(String[] args) {
        String jsonOutput = "";

        LocalDateTime now = LocalDateTime.now();
        System.err.println("TMST038 serialization started at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        // 1. Create collector for test diagnostics
        JsonValidationExceptionCollector collector = new JsonValidationExceptionCollector();
        collector.setPrintImmediately(true); // See errors as they happen

        // 2. Build test parameters
        RequestParams params = createTestParams();

        try {
            // 3. Build request with test's collector
            PAIN001ResponseStatusReport request = RequestBuilder.build(PAIN001ResponseStatusReport.class, params, collector);

            // 4. Serialization test
            JsonProcessor processor = new JsonProcessor(collector);
            jsonOutput = processor.serialize(request);

            // 5. Print Json
            System.out.println("\nGenerated JSON:");
            System.out.println(jsonOutput);

            System.out.println("TMST038 serialization succeeded without errors.\n");

            now = LocalDateTime.now();
            System.err.println("TMST038 serialization finished at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        } catch (JsonValidationException e) {
            System.err.println("TMST038 serialization Test failed: " + e.getMessage());
            System.err.println(e.getValidationErrors());
            System.err.println("********************************************");
        }
    }

    private static RequestParams createTestParams() {
        return new RequestParams()
            // AppHdr
            .setBicfiFr("DUMMYMASTER")
            .setBicfiTo("BAMCSVSS")
            .setBizMsgIdr("DummySaldoCta1")
            .setMsgDefIdr("PAIN.001.001.03")
            .setBizSvc("swift.cbprplus.01")
            .setCreDt("2025-05-16T07:56:49-06:00")

            // Group Header
            .setMsgId("DummySaldoCta1")
            .setCreDtTm("2025-05-16T07:56:49-06:00")
            .setPmtMtd("TRF")
            .setNbOfTxs(Integer.valueOf(3))
            .setCtrlSum(new BigDecimal("469.87"))
            .setNameInitParty("Sistemas Aereos")

            // Document
            .setCatPurpCd("SUPP")
            .setReqdExctnDt("2023-06-27")
            .setNameDebtor("Sistemas Aereos")
            .setDbtrId("06140904181038")
            .setIbanDbtrAcct("CR42010200690010163989")
            .setBicOrBEI("BSNJCRSJXXX")
            .setBicDbtr("BAMCSVSS")
            .setCountry("SV")

            // Payment Element
            .setEndToEndId("E2E-1234567890")
            .setInstrPrty("NORM")
            .setCcy("USD")
            .setInstdAmt("469.87")
            .setBic("BSNJCRSJXXX")
            .setNameCreditor("Nombre cliente destino")
            .setCdtrId("987654321")
            .setIbanCdtrAcct("CR42010200690010112233")
            .setCdtrAcctCd("CACC")
            .setPymtPurpose("Motivo del pago es el siguiente...")
            .setRmtncInf("Referencia a factura numero NV-112");
    }
}
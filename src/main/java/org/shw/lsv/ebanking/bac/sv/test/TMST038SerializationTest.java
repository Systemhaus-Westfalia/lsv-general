package org.shw.lsv.ebanking.bac.sv.test;

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
            // TMST038 Request is the same as PAIN001ResponseStatusReport.
            // This is the reason why PAIN001ResponseStatusReport is used here.
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
        String PYMT_MESSAGE_ID = "ConsPago-ADClientName/(CuentaNr)";
        String PYMT_DOCUMENT_ID = "PYMT-0001";  // Ich glaube, das ist die DocumentNr, die in der Antwort referenziert wird.

        return new RequestParams()

            // AppHdr
            .setBicfiFr(      "BAMCSVSS")                       // BIC of Company's account (festgelegt)
                                                                      // Official definition: The sending Bank Identifier Code.
                                                                      // "INVALIDBIC" Will trigger an error
            .setBicfiTo(      "BAMCSVSS")                     // BIC of Company's account (festgelegt)
                                                                      // Official definition: The receiving Bank Identifier Code.
            .setBizMsgIdr(    PYMT_MESSAGE_ID)                        // BizMsgIdr is a unique message ID, assigned by the sender for tracking and reference.
            .setMsgDefIdr(    "TSMT.038.001.03")            // The message definition identifier, indicating the type of message being sent. Bei "Payment Consult Request" muß =TSMT.038.001.03
            .setBizSvc(       "swift.cbprplus.01")             // The business service identifier. Hier muß == "swift.cbprplus.01"
            .setCreDt(        "2025-06-10T17:26:49-06:00")

            // Document
            .setXmlns(    "urn:iso:std:iso:20022:tech:xsd:tsmt.038.001.03") // XML namespace for the message
            .setMsgId(    PYMT_DOCUMENT_ID) 
            .setCreDtTm("2025-06-10T17:26:49-06:00")
            .setBic(        "BAMCSVSS");                           // BIC of Company's account
                                                                       // Official definition: The BIC (SWIFT code) of the account’s bank.
    }
}
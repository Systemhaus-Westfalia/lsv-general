package org.shw.lsv.ebanking.bac.sv.test;

import java.time.LocalDateTime;

import org.shw.lsv.ebanking.bac.sv.pain001.response.PAIN001Response;
import org.shw.lsv.ebanking.bac.sv.handling.JsonProcessor;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationException;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

public class PAIN001DeSerializationSystemEventNotification {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        System.err.println("Deserialization started at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        // 1. Create collector with explicit settings
        JsonValidationExceptionCollector collector = new JsonValidationExceptionCollector();
        //collector.setPrintImmediately(true); // Print errors as they occur

        // 2. Inject collector into processor
        JsonProcessor processor = new JsonProcessor(collector);

        // 3. Prepare test JSON (could also read from file)
        String testJson = createTestJson();

        // 4. Execute deserialization
        try {
            System.out.println("Starting deserialization test...");
            PAIN001Response response = processor.deserialize(testJson, PAIN001Response.class);

            // 5. Check for non-fatal warnings
            if (collector.hasErrors()) {
                System.out.println("\nDeserialization succeeded with warnings:");
                System.out.println(collector.getAllErrors());
            } else {
                System.out.println("Deserialization completed cleanly");
            }

            // 6. Use the deserialized object
            System.out.println("\nDeserialized object details:");
            printResponseSummary(response);

        } catch (JsonValidationException e) {
            System.err.println("\nCritical validation failures:");
            System.err.println(e.getValidationErrors());
        }
    }

    private static String createTestJson() {
        // Example JSON for PAIN.001 System Event Notification
        String jsonContent =
            "{\n" +
            "    \"Envelope\": {\n" +
            "        \"AppHdr\": {\n" +
            "            \"BizMsgIdr\": \"123456\",\n" +
            "            \"MsgDefIdr\": \"pain.001.001.09\",\n" +
            "            \"BizSvc\": \"swift.cbprplus.01\",\n" +
            "            \"CreDt\": \"2024-07-23T18:44:54-06:00\"\n" +
            "        },\n" +
            "        \"Document\": {\n" +
            "            \"SysEvtNtfctn\": {\n" +
            "                \"EvtInf\": {\n" +
            "                    \"EvtCd\": \"ACCEPTED\",\n" +
            "                    \"EvtDesc\": \"Payment accepted\",\n" +
            "                    \"EvtTm\": \"2024-07-23T18:44:54-06:00\"\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}";
        return jsonContent;
    }

    private static void printResponseSummary(PAIN001Response response) {
        LocalDateTime now = LocalDateTime.now();
        System.err.println("Deserialization finished at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        System.out.println("Envelope present: " + (response.getPAIN001ResponseEnvelope() != null));
        if (response.getPAIN001ResponseEnvelope() != null) {
            System.out.println("Document present: " +
                (response.getPAIN001ResponseEnvelope().getPAIN001ResponseDocument() != null));
            if (response.getPAIN001ResponseEnvelope().getPAIN001ResponseDocument() != null) {
                System.out.println("SysEvtNtfctn present: " +
                    (response.getPAIN001ResponseEnvelope().getPAIN001ResponseDocument().getSysEvtNtfctn() != null));
                if (response.getPAIN001ResponseEnvelope().getPAIN001ResponseDocument().getSysEvtNtfctn() != null) {
                    System.out.println("EvtInf present: " +
                        (response.getPAIN001ResponseEnvelope().getPAIN001ResponseDocument().getSysEvtNtfctn().getEvtInf() != null));
                    if (response.getPAIN001ResponseEnvelope().getPAIN001ResponseDocument().getSysEvtNtfctn().getEvtInf() != null) {
                        System.out.println("EvtCd: " +
                            response.getPAIN001ResponseEnvelope().getPAIN001ResponseDocument().getSysEvtNtfctn().getEvtInf().getEvtCd());
                        System.out.println("EvtDesc: " +
                            response.getPAIN001ResponseEnvelope().getPAIN001ResponseDocument().getSysEvtNtfctn().getEvtInf().getEvtDesc());
                        System.out.println("EvtTm: " +
                            response.getPAIN001ResponseEnvelope().getPAIN001ResponseDocument().getSysEvtNtfctn().getEvtInf().getEvtTm());
                    }
                }
            }
        }
        System.err.println("********************************************");
        System.err.println("********************************************");
    }
}
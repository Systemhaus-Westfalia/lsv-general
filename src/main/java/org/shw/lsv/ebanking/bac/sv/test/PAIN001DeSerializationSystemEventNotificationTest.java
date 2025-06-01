package org.shw.lsv.ebanking.bac.sv.test;

import java.time.LocalDateTime;

import org.shw.lsv.ebanking.bac.sv.handling.JsonProcessor;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationException;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.pain001.response.PAIN001ResponseEvtNtfn;

public class PAIN001DeSerializationSystemEventNotificationTest {
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
            PAIN001ResponseEvtNtfn response = processor.deserialize(testJson, PAIN001ResponseEvtNtfn.class);

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
            "            \"Fr\": {\n" +
            "                \"FIId\": {\n" +
            "                    \"FinInstnId\": {\n" +
            "                        \"BICFI\": \"BMILHNTE\"\n" +
            "                    }\n" +
            "                }\n" +
            "            },\n" +
            "            \"To\": {\n" +
            "                \"FIId\": {\n" +
            "                    \"FinInstnId\": {\n" +
            "                        \"BICFI\": \"DUMMYMASTER\"\n" +
            "                    }\n" +
            "                }\n" +
            "            },\n" +
            "            \"BizMsgIdr\": \"8034\",\n" +
            "            \"MsgDefIdr\": \"ADMIN.004.001.02\",\n" +
            "            \"BizSvc\": \"swift.cbprplus.02\",\n" +
            "            \"CreDt\": \"2024-07-23T12:47:32-06:00\"\n" +
            "        },\n" +
            "        \"Document\": {\n" +
            "            \"SysEvtNtfctn\": {\n" +
            "                \"EvtInf\": {\n" +
            "                    \"EvtCd\": \"RCVD\",\n" +
            "                    \"EvtDesc\": \"Solicitud recibida. Su pago se esta procesando o se procesara en la fecha indicada en el mensaje. Consulte mas tarde.\",\n" +
            "                    \"EvtTm\": \"2024-07-23T12:47:32-06:00\"\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}";
        return jsonContent;
    }

    /*
    * Zu erwartetes Ergebnis:
    *
    *
    ********************************************
    ********************************************
    *** AppHdr ***
        Fr-BICFI : BMILHNTE
        BizMshIdr: 8034
        BizSvc:    swift.cbprplus.02
        CreDt:     2024-07-23T12:47:32-06:00
        MsgDefIdr: ADMIN.004.001.02
    ********************************************
    *** PAIN001 (System Notification) Response Document ***
    ********************************************
    SysEvtNtfctn present: true
    EvtInf present:
    Erhaltene Variablen: true
        EvtCd  : RCVD
        EvtDesc: Solicitud recibida. Su pago se esta procesando o se procesara en la fecha indicada en el mensaje. Consulte mas tarde.
        EvtTm  : 2024-07-23T12:47:32-06:00
    ********************************************
    ********************************************
    *
    */
    private static void printResponseSummary(PAIN001ResponseEvtNtfn response) {
        LocalDateTime now = LocalDateTime.now();
        System.err.println("Deserialization finished at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        System.out.println("Envelope present: " + (response.getpAIN001ResponseEvtNtfnEnvelope() != null));
        if (response.getpAIN001ResponseEvtNtfnEnvelope() != null) {
            System.out.println("Document present: " +
                (response.getpAIN001ResponseEvtNtfnEnvelope().getpAIN001ResponseEvtNtfnDocument() != null));
            if (response.getpAIN001ResponseEvtNtfnEnvelope().getpAIN001ResponseEvtNtfnDocument() != null) {
                System.err.println("********************************************");
                System.err.println("********************************************");
                System.err.println("Ergebinisse:");
                System.err.println("*** AppHdr ***");
                System.err.println("    Fr-BICFI : "  + response.getpAIN001ResponseEvtNtfnEnvelope().getAppHdr().getFr().getfIId().getFinInstnId().getbICFI());
                System.err.println("    BizMshIdr: "  + response.getpAIN001ResponseEvtNtfnEnvelope().getAppHdr().getBizMsgIdr());
                System.err.println("    BizSvc:    "  + response.getpAIN001ResponseEvtNtfnEnvelope().getAppHdr().getBizSvc());
                System.err.println("    CreDt:     "  + response.getpAIN001ResponseEvtNtfnEnvelope().getAppHdr().getCreDt());
                System.err.println("    MsgDefIdr: "  + response.getpAIN001ResponseEvtNtfnEnvelope().getAppHdr().getMsgDefIdr());
                System.err.println("********************************************");
                System.err.println("*** PAIN001 (System Event Notification) Response Document ***");
                System.err.println("********************************************");
                System.out.println("SysEvtNtfctn present: " +
                    (response.getpAIN001ResponseEvtNtfnEnvelope().getpAIN001ResponseEvtNtfnDocument().getSysEvtNtfctn() != null));
                if (response.getpAIN001ResponseEvtNtfnEnvelope().getpAIN001ResponseEvtNtfnDocument().getSysEvtNtfctn() != null) {
                    System.out.println("EvtInf present: ");
                    System.out.println("Erhaltene Variablen: " +
                        (response.getpAIN001ResponseEvtNtfnEnvelope().getpAIN001ResponseEvtNtfnDocument().getSysEvtNtfctn().getEvtInf() != null));
                    if (response.getpAIN001ResponseEvtNtfnEnvelope().getpAIN001ResponseEvtNtfnDocument().getSysEvtNtfctn().getEvtInf() != null) {
                        System.out.println("    EvtCd  : " +
                            response.getpAIN001ResponseEvtNtfnEnvelope().getpAIN001ResponseEvtNtfnDocument().getSysEvtNtfctn().getEvtInf().getEvtCd());
                        System.out.println("    EvtDesc: " +
                            response.getpAIN001ResponseEvtNtfnEnvelope().getpAIN001ResponseEvtNtfnDocument().getSysEvtNtfctn().getEvtInf().getEvtDesc());
                        System.out.println("    EvtTm  : " +
                            response.getpAIN001ResponseEvtNtfnEnvelope().getpAIN001ResponseEvtNtfnDocument().getSysEvtNtfctn().getEvtInf().getEvtTm());
                    }
                }
            }
        }
        System.err.println("********************************************");
        System.err.println("********************************************");
    }
}
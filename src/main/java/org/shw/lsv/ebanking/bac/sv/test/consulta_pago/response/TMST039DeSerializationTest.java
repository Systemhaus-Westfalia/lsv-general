package org.shw.lsv.ebanking.bac.sv.test.consulta_pago.response;

import java.time.LocalDateTime;

import org.shw.lsv.ebanking.bac.sv.handling.JsonProcessor;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationException;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.tmst039.response.TMST039Response;

public class TMST039DeSerializationTest {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        System.err.println("TMST039 Deserialization started at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        // 1. Create collector with explicit settings
        JsonValidationExceptionCollector collector = new JsonValidationExceptionCollector();
        //collector.setPrintImmediately(true); // Print errors as they occur

        // 2. Inject collector into processor
        JsonProcessor processor = new JsonProcessor(collector);

        // 3. Prepare test JSON (could also read from file)
        String testJson = createTestJson();

        // 4. Execute deserialization
        try {
            System.out.println("Starting TMST039 deserialization test...");
            TMST039Response response = processor.deserialize(testJson, TMST039Response.class);

            // 5. Check for non-fatal warnings
            if (collector.hasErrors()) {
                System.out.println("\nTMST039 Deserialization succeeded with warnings:");
                System.out.println(collector.getAllErrors());
            } else {
                System.out.println("TMST039 Deserialization completed cleanly");
            }

            // 6. Use the deserialized object
            System.out.println("\nTMST039 Deserialized object details:");
            printResponseSummary(response);

        } catch (JsonValidationException e) {
            System.err.println("\nCritical validation failures:");
            System.err.println(e.getValidationErrors());
        }
    }


    /*
    * Vorsicht!!!
    * BizMsgIdr ist max 35 Zeichen lang und darf nur bestimmte Zeichen aufweisen!
    *
    * "Cd" hat meistens einen der folgenden Inhalte: 
    *   ACCP	Accepted
    *   RJCT	Rejected
    *   PDNG	Pending
    * Andere Werte: 
    *   PART	Partially Accepted
    *   RCVD	Received
    *   ACSP	Accepted Settlement in Process
    *   ACSC	Accepted Settlement Completed
    *   ACWC	Accepted with Change
    *   ACFC	Accepted with Forwarding to Clearing
    *   CANC	Cancelled
    *   TECH	Technical Error
    *   WARN	Warning
    */
    private static String createTestJson() {
        // Ohne Blanks und unsichtbare Zeichen (also, wie erwartet wird):
        //7String jsonContent = "{\"Envelope\":{\"AppHdr\":{\"Fr\":{\"FIId\":{\"FinInstnId\":{\"BICFI\":\"BAMCSVSS\"}}},\"To\":{\"FIId\":{\"FinInstnId\":{\"BICFI\":\"BAMCSVSS\"}}},\"BizMsgIdr\":\"RespConsPago-ADClientName/(CuentaNr\",\"MsgDefIdr\":\"TSMT.039.001.03\",\"BizSvc\":\"swift.cbprplus.01\",\"CreDt\":\"2025-06-10T17:27:10-06:00\"},\"Document\":{\"xmlns\":\"urn:iso:std:iso:20022:tech:xsd:tsmt.039.001.03\",\"StsRptRsp\":{\"ReqId\":{\"Id\":\"PYMT-0001\",\"CreDtTm\":\"2025-06-10T17:26:49-06:00\"},\"NttiesRptd\":{\"BIC\":\"BAMCSVSS\"},\"Sts\":{\"Cd\":\"ACCP\",\"Rsn\":\"Payment found and accepted\"}}}}}";
        
        String jsonContent =
            "{\n" +
            "  \"Envelope\": {\n" +
            "    \"AppHdr\": {\n" +
            "      \"Fr\": { \"FIId\": { \"FinInstnId\": { \"BICFI\": \"BAMCSVSS\" } } },\n" +
            "      \"To\": { \"FIId\": { \"FinInstnId\": { \"BICFI\": \"BAMCSVSS\" } } },\n" +
            "      \"BizMsgIdr\": \"RespConsPago-ADClientName/(CuentaNr\",\n" +
            "      \"MsgDefIdr\": \"TSMT.039.001.03\",\n" +
            "      \"BizSvc\": \"swift.cbprplus.01\",\n" +
            "      \"CreDt\": \"2025-06-10T17:27:10-06:00\"\n" +
            "    },\n" +
            "    \"Document\": {\n" +
            "      \"xmlns\": \"urn:iso:std:iso:20022:tech:xsd:tsmt.039.001.03\",\n" +
            "      \"StsRptRsp\": {\n" +
            "        \"ReqId\": {\n" +
            "          \"Id\": \"PYMT-0001\",\n" +
            "          \"CreDtTm\": \"2025-06-10T17:26:49-06:00\"\n" +
            "        },\n" +
            "        \"NttiesRptd\": {\n" +
            "          \"BIC\": \"BAMCSVSS\"\n" +
            "        },\n" +
            "        \"Sts\": {\n" +
            "          \"Cd\": \"ACCP\",\n" +
            "          \"Rsn\": \"Payment found and accepted\"\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

        return jsonContent;
    }



    /*
    * Zu erwartetes Ergebnis:
    *TMST039 Deserialization started at: 2025-06-10 03:04:14
    Starting TMST039 deserialization test...
    TMST039 Deserialization completed cleanly

    TMST039 Deserialized object details:
    TMST039 Deserialization finished at: 2025-06-10 03:04:15
    Envelope present: true
    TMST039 Document present: true
    ********************************************
    ********************************************
    Ergebinisse:
    *** AppHdr ***
        Fr-BICFI : BAMCSVSS
        To-BICFI : BAMCSVSS
        BizMshIdr: RespConsPago-ADClientName/(CuentaNr
        BizSvc:    swift.cbprplus.01
        CreDt:     2025-06-10T17:27:10-06:00
        MsgDefIdr: TSMT.039.001.03
    ********************************************
    *** TMST039 Response Document            ***
    ********************************************
    xmlns: urn:iso:std:iso:20022:tech:xsd:tsmt.039.001.03
    StsRptRsp present: true
    ReqId present: true
        Id      : PYMT-0001
        CreDtTm : 2025-06-10T17:26:49-06:00
    NttiesRptd present: true
        BIC     : BAMCSVSS
    Sts present: true
        Cd      : ACCP
        Rsn     : Payment found and accepted
    ********************************************
    *******************************************
    */
    private static void printResponseSummary(TMST039Response response) {
        LocalDateTime now = LocalDateTime.now();
        System.err.println("TMST039 Deserialization finished at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        System.out.println("TMST039 Envelope present: " + (response.getTMST039ResponseEnvelope() != null));
        if (response.getTMST039ResponseEnvelope() != null) {
            System.out.println("TMST039 Document present: " +
                (response.getTMST039ResponseEnvelope().getTMST039ResponseDocument() != null));
            if (response.getTMST039ResponseEnvelope().getTMST039ResponseDocument() != null) {
                System.err.println("********************************************");
                System.err.println("********************************************");
                System.err.println("Ergebinisse:");
                System.err.println("*** AppHdr ***");
                System.err.println("    Fr-BICFI : "  + response.getTMST039ResponseEnvelope().getAppHdr().getFr().getfIId().getFinInstnId().getbICFI());
                System.err.println("    To-BICFI : "  + response.getTMST039ResponseEnvelope().getAppHdr().getTo().getfIId().getFinInstnId().getbICFI());
                System.err.println("    BizMshIdr: "  + response.getTMST039ResponseEnvelope().getAppHdr().getBizMsgIdr());
                System.err.println("    BizSvc:    "  + response.getTMST039ResponseEnvelope().getAppHdr().getBizSvc());
                System.err.println("    CreDt:     "  + response.getTMST039ResponseEnvelope().getAppHdr().getCreDt());
                System.err.println("    MsgDefIdr: "  + response.getTMST039ResponseEnvelope().getAppHdr().getMsgDefIdr());
                System.err.println("********************************************");
                System.err.println("*** TMST039 Response Document            ***");
                System.err.println("********************************************");
                System.out.println("xmlns: " +
                    response.getTMST039ResponseEnvelope().getTMST039ResponseDocument().getXmlns());
                System.out.println("StsRptRsp present: " +
                    (response.getTMST039ResponseEnvelope().getTMST039ResponseDocument().getStsRptRsp() != null));
                if (response.getTMST039ResponseEnvelope().getTMST039ResponseDocument().getStsRptRsp() != null) {
                    System.out.println("ReqId present: " +
                        (response.getTMST039ResponseEnvelope().getTMST039ResponseDocument().getStsRptRsp().getReqId() != null));
                    if (response.getTMST039ResponseEnvelope().getTMST039ResponseDocument().getStsRptRsp().getReqId() != null) {
                        System.out.println("    Id      : " +
                            response.getTMST039ResponseEnvelope().getTMST039ResponseDocument().getStsRptRsp().getReqId().getId());
                        System.out.println("    CreDtTm : " +
                            response.getTMST039ResponseEnvelope().getTMST039ResponseDocument().getStsRptRsp().getReqId().getCreDtTm());
                    }
                    System.out.println("NttiesRptd present: " +
                        (response.getTMST039ResponseEnvelope().getTMST039ResponseDocument().getStsRptRsp().getNttiesToBeRptd() != null));
                    if (response.getTMST039ResponseEnvelope().getTMST039ResponseDocument().getStsRptRsp().getNttiesToBeRptd() != null) {
                        System.out.println("    BIC     : " +
                            response.getTMST039ResponseEnvelope().getTMST039ResponseDocument().getStsRptRsp().getNttiesToBeRptd().getbIC());
                    }
                    System.out.println("Sts present: " +
                        (response.getTMST039ResponseEnvelope().getTMST039ResponseDocument().getStsRptRsp().getSts() != null));
                    if (response.getTMST039ResponseEnvelope().getTMST039ResponseDocument().getStsRptRsp().getSts() != null) {
                        System.out.println("    Cd      : " +
                            response.getTMST039ResponseEnvelope().getTMST039ResponseDocument().getStsRptRsp().getSts().getCd());
                        System.out.println("    Rsn     : " +
                            response.getTMST039ResponseEnvelope().getTMST039ResponseDocument().getStsRptRsp().getSts().getRsn());
                    }
                }
            }
        }
        System.err.println("********************************************");
        System.err.println("********************************************");
    }
}
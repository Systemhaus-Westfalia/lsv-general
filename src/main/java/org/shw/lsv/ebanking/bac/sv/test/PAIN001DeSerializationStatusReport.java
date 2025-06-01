package org.shw.lsv.ebanking.bac.sv.test;

import java.time.LocalDateTime;

import org.shw.lsv.ebanking.bac.sv.handling.JsonProcessor;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationException;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.pain001.response.PAIN001ResponseStatusReport;

public class PAIN001DeSerializationStatusReport {
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
            PAIN001ResponseStatusReport response = processor.deserialize(testJson, PAIN001ResponseStatusReport.class);

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
        String jsonContent =
            "{\n" +
            "    \"Envelope\": {\n" +
            "        \"AppHdr\": {\n" +
            "            \"Fr\": {\n" +
            "                \"FIId\": {\n" +
            "                    \"FinInstnId\": {\n" +
            "                        \"BICFI\": \"AMERICA3PLX\"\n" +
            "                    }\n" +
            "                }\n" +
            "            },\n" +
            "            \"To\": {\n" +
            "                \"FIId\": {\n" +
            "                    \"FinInstnId\": {\n" +
            "                        \"BICFI\": \"BCINPAPA\"\n" +
            "                    }\n" +
            "                }\n" +
            "            },\n" +
            "            \"BizMsgIdr\": \"Prueba_01\",\n" +
            "            \"MsgDefIdr\": \"TSMT.038.001.03\",\n" +
            "            \"BizSvc\": \"swift.cbprplus.01\",\n" +
            "            \"CreDt\": \"2022-08-16T08:00:00-06:00\"\n" +
            "        },\n" +
            "        \"Document\": {\n" +
            "            \"xmlns\": \"urn:iso:std:iso:20022:tech:xsd:tsmt.038.001.03\",\n" +
            "            \"StsRptReq\": {\n" +
            "                \"ReqId\": {\n" +
            "                    \"Id\": \"Prueba_01\",\n" +
            "                    \"CreDtTm\": \"2022-05-20T12:21:35\"\n" +
            "                },\n" +
            "                \"NttiesToBeRptd\": {\n" +
            "                    \"BIC\": \"AMERICA3PLX\"\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}";
        return jsonContent;
    }
/*  
 * Zu erwartetes Ergebnis u.a.:
********************************************
********************************************
*** AppHdr ***
    Fr-BICFI : AMERICA3PLX
    To-BICFI : BCINPAPA
    BizMshIdr: Prueba_01
    BizSvc:    swift.cbprplus.01
    CreDt:     2022-08-16T08:00:00-06:00
    MsgDefIdr: TSMT.038.001.03
    
********************************************
    xmlns    : urn:iso:std:iso:20022:tech:xsd:tsmt.038.001.03
    
    Id      : Prueba_01
    CreDtTm : 2022-05-20T12:21:35
    
    BIC     : AMERICA3PLX
********************************************
********************************************
 * 
*/
    private static void printResponseSummary(PAIN001ResponseStatusReport response) {
        LocalDateTime now = LocalDateTime.now();
        System.err.println("Deserialization finished at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        System.out.println("Envelope present: " + (response.getpAIN001ResponseStatusReportEnvelope() != null));
        if (response.getpAIN001ResponseStatusReportEnvelope() != null) {
            System.out.println("Document present: " +
                (response.getpAIN001ResponseStatusReportEnvelope().getpAIN001ResponseStatusReportDocument() != null));
            if (response.getpAIN001ResponseStatusReportEnvelope().getpAIN001ResponseStatusReportDocument() != null) {
                System.err.println("********************************************");
                System.err.println("********************************************");
                System.err.println("Ergebinisse:");
                System.err.println("*** AppHdr ***");
                System.err.println("    Fr-BICFI : "  + response.getpAIN001ResponseStatusReportEnvelope().getAppHdr().getFr().getfIId().getFinInstnId().getbICFI());
                System.err.println("    To-BICFI : "  + response.getpAIN001ResponseStatusReportEnvelope().getAppHdr().getTo().getfIId().getFinInstnId().getbICFI());
                System.err.println("    BizMshIdr: "  + response.getpAIN001ResponseStatusReportEnvelope().getAppHdr().getBizMsgIdr());
                System.err.println("    BizSvc:    "  + response.getpAIN001ResponseStatusReportEnvelope().getAppHdr().getBizSvc());
                System.err.println("    CreDt:     "  + response.getpAIN001ResponseStatusReportEnvelope().getAppHdr().getCreDt());
                System.err.println("    MsgDefIdr: "  + response.getpAIN001ResponseStatusReportEnvelope().getAppHdr().getMsgDefIdr());
                System.err.println("********************************************");
                System.err.println("*** PAIN001 (Status Report) Response Document ***");
                System.err.println("********************************************");

                System.out.println("    xmlns    : " +
                    response.getpAIN001ResponseStatusReportEnvelope().getpAIN001ResponseStatusReportDocument().getXmlns());
                System.out.println("StsRptReq present: " +
                    (response.getpAIN001ResponseStatusReportEnvelope().getpAIN001ResponseStatusReportDocument().getStsRptReq() != null));
                if (response.getpAIN001ResponseStatusReportEnvelope().getpAIN001ResponseStatusReportDocument().getStsRptReq() != null) {
                    System.out.println("ReqId present: " +
                        (response.getpAIN001ResponseStatusReportEnvelope().getpAIN001ResponseStatusReportDocument().getStsRptReq().getReqId() != null));
                    if (response.getpAIN001ResponseStatusReportEnvelope().getpAIN001ResponseStatusReportDocument().getStsRptReq().getReqId() != null) {
                        System.out.println("    Id      : " +
                            response.getpAIN001ResponseStatusReportEnvelope().getpAIN001ResponseStatusReportDocument().getStsRptReq().getReqId().getId());
                        System.out.println("    CreDtTm : " +
                            response.getpAIN001ResponseStatusReportEnvelope().getpAIN001ResponseStatusReportDocument().getStsRptReq().getReqId().getCreDtTm());
                    }
                    System.out.println("NttiesToBeRptd present: " +
                        (response.getpAIN001ResponseStatusReportEnvelope().getpAIN001ResponseStatusReportDocument().getStsRptReq().getNttiesToBeRptd() != null));
                    if (response.getpAIN001ResponseStatusReportEnvelope().getpAIN001ResponseStatusReportDocument().getStsRptReq().getNttiesToBeRptd() != null) {
                        System.out.println("    BIC     : " +
                            response.getpAIN001ResponseStatusReportEnvelope().getpAIN001ResponseStatusReportDocument().getStsRptReq().getNttiesToBeRptd().getBIC());
                    }
                }
            }
        }
        System.err.println("********************************************");
        System.err.println("********************************************");
    }
}
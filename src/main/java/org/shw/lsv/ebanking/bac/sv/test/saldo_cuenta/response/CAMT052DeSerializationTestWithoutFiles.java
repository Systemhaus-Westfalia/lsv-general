package org.shw.lsv.ebanking.bac.sv.test.saldo_cuenta.response;

import java.time.LocalDateTime;

import org.shw.lsv.ebanking.bac.sv.camt052.response.CAMT052Response;
import org.shw.lsv.ebanking.bac.sv.camt052.response.CAMT052ResponseDocument;
import org.shw.lsv.ebanking.bac.sv.camt052.response.CAMT052ResponseEnvelope;
import org.shw.lsv.ebanking.bac.sv.handling.JsonProcessor;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationException;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

public class CAMT052DeSerializationTestWithoutFiles {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        System.err.println("CAMT052 deserialization started at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        // 1. Create collector with explicit settings
        JsonValidationExceptionCollector collector = new JsonValidationExceptionCollector();
        //collector.setPrintImmediately(true); // Print errors as they occur

        // 2. Inject collector into processor
        JsonProcessor processor = new JsonProcessor(collector);

        // 3. Prepare test JSON (could also read from file)
        String testJson = createTestJson();

        // 4. Execute deserialization
        try {
            System.out.println("Starting CAMT052 deserialization test...");
            CAMT052Response response = processor.deserialize(testJson, CAMT052Response.class);

            // 5. Check for non-fatal warnings
            if (collector.hasErrors()) {
                System.out.println("\nCAMT052 deserialization succeeded with warnings:");
                System.out.println(collector.getAllErrors());
            } else {
                System.out.println("CAMT052 deserialization completed cleanly");
            }

            // 6. Use the deserialized object
            System.out.println("\nCAMT052 deserialized object details:");
            printResponseSummary(response);
            
        } catch (JsonValidationException e) {
            System.err.println("\nCritical validation failures:");
            System.err.println(e.getValidationErrors());
        }
    }

    private static String createTestJson() {
        // Example JSON that might trigger validation rules
        String jsonContent =
            "{\n" +
            "  \"File\": {\n" +
            "    \"Envelope\": {\n" +
            "      \"AppHdr\": {\n" +
            "        \"Fr\": {\n" +
            "          \"FIId\": {\n" +
            "            \"FinInstnId\": {\n" +
            "              \"BICFI\": \"BAMCSVSS\"\n" +
            "            }\n" +
            "          }\n" +
            "        },\n" +
            "        \"To\": {\n" +
            "          \"FIId\": {\n" +
            "            \"FinInstnId\": {\n" +
            "              \"BICFI\": \"AMERICA3PLX\"\n" +
            "            }\n" +
            "          }\n" +
            "        },\n" +
            "        \"BizMsgIdr\": \"2213703\",\n" +
            "        \"MsgDefIdr\": \"AccountBalanceReportV08\",\n" +
            "        \"BizSvc\": \"swift.cbprplus.02\",\n" +
            "        \"CreDt\": \"2025-06-28T13:06:22-06:00\"\n" +
            "      },\n" +
            "      \"Document\": {\n" +
            "        \"BkToCstmrAcctRpt\": {\n" +
            "          \"GrpHdr\": {\n" +
            "            \"MsgId\": \"2213703\",\n" +
            "            \"CreDtTm\": \"2025-06-28T13:06:22-06:00\"\n" +
            "          },\n" +
            "          \"Rpt\": {\n" +
            "            \"Id\": \"2025062813062260000\",\n" +
            "            \"RptPgntn\": {\n" +
            "              \"PgNb\": \"1\",\n" +
            "              \"LastPgInd\": \"true\"\n" +
            "            },\n" +
            "            \"Acct\": {\n" +
            "              \"Id\": {\n" +
            "                \"Othr\": {\n" +
            "                  \"Id\": \"200268472\"\n" +
            "                }\n" +
            "              },\n" +
            "              \"Ccy\": \"USD\"\n" +
            "            },\n" +
            "            \"Bal\": {\n" +
            "              \"Tp\": {\n" +
            "                \"CdOrPrtry\": {\n" +
            "                  \"Cd\": \"ITAV\"\n" +
            "                }\n" +
            "              },\n" +
            "              \"CdtDbtInd\": \"CRDT\",\n" +
            "              \"Dt\": {\n" +
            "                \"DtTm\": \"2025-06-28T13:06:22-06:00\"\n" +
            "              },\n" +
            "              \"Amt\": {\n" +
            "                \"Ccy\": \"USD\",\n" +
            "                \"Amt\": \"55555555555.00\"\n" +
            "              }\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";
    
            return jsonContent;
        }

        
               // For error testing, you could:
               // - Omit required fields
               // - Set invalid values
               // - Include nulls where prohibited
/*  
 * Zu erwartetes Ergebnis u.a.:
 * *** AppHdr ***
 *   Fr-BICFI : BAMCSVSS
 *   To-BICFI : AMERICA3PLX
 *   BizMshIdr: 2213703
 *   BizSvc:    swift.cbprplus.02
 *   CreDt:     2025-06-28T13:06:22-06:00
 *   MsgDefIdr: AccountBalanceReportV08
 * *** CAMT052 Response Document ***
 * *******************************************
 *   CreDtTm:   2025-06-28T13:06:22-06:00
 *   MsgId:     2213703
 *   AcctId:    200268472
 *   Acct-Ccy:  USD
 *   Bal-Amt:   55555555555.00
 *   Bal-Ccy:   USD
 *   CdtDbtInd: CRDT
 *   Bal-DtTm:  2025-06-28T13:06:22-06:00
 *   Bal-Cd:    ITAV
 *   Id:        2025062813062260000
 *   Bal-RptId: 1
 *   LastPgInd: true
 * 
*/
    private static void printResponseSummary(CAMT052Response response) {
        LocalDateTime now = LocalDateTime.now();
        System.err.println("CAMT052 Deserialization finished at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        System.out.println("CAMT052 Envelope present: " + (response.getcAMT052ResponseFile().getcAMT052ResponseEnvelope() != null));
        if (response.getcAMT052ResponseFile().getcAMT052ResponseEnvelope() != null) {
            System.out.println("CAMT052 Document present: " +
                (response.getcAMT052ResponseFile().getcAMT052ResponseEnvelope().getcAMT052ResponseDocument() != null));
        }

        CAMT052ResponseEnvelope responseEnvelope = response.getcAMT052ResponseFile().getcAMT052ResponseEnvelope();
        CAMT052ResponseDocument responseDocument = responseEnvelope.getcAMT052ResponseDocument();

        System.err.println("********************************************");
        System.err.println("********************************************");
        System.err.println("Ergebnisse:");
        System.err.println("*** AppHdr ***");
        System.err.println("    Fr-BICFI : "  + responseEnvelope.getAppHdr().getFr().getfIId().getFinInstnId().getbICFI());
        System.err.println("    To-BICFI : "  + responseEnvelope.getAppHdr().getTo().getfIId().getFinInstnId().getbICFI());
        System.err.println("    BizMshIdr: "  + responseEnvelope.getAppHdr().getBizMsgIdr());
        System.err.println("    BizSvc:    "  + responseEnvelope.getAppHdr().getBizSvc());
        System.err.println("    CreDt:     "  + responseEnvelope.getAppHdr().getCreDt());
        System.err.println("    MsgDefIdr: "  + responseEnvelope.getAppHdr().getMsgDefIdr());
        System.err.println("********************************************");
        System.err.println("*** CAMT052 Response Document ***");
        System.err.println("********************************************");
        System.err.println("************* GrpHdr ***********************");
        System.err.println("    CreDtTm:   "  + responseDocument.getBkToCstmrAcctRpt().getGrpHdr().getCreDtTm());
        System.err.println("    MsgId:     "  + responseDocument.getBkToCstmrAcctRpt().getGrpHdr().getMsgId());

        System.err.println("    AcctId:    "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getAcct().getAcctId().getAcctIdOthr().getId());
        System.err.println("    Acct-Ccy:  "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getAcct().getCcy());
        System.err.println("    Bal-Amt:   "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getBal().getAmt().getAmt());
        System.err.println("    Bal-Ccy:   "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getBal().getAmt().getCcy());
        System.err.println("    CdtDbtInd: "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getBal().getCdtDbtInd());
        System.err.println("    Bal-DtTm:  "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getBal().getDt().getDtTm());
        System.err.println("    Bal-Cd:    "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getBal().getTp().getCdOrPrtry().getCd());
        System.err.println("    Id:        "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getId());

        System.err.println("    Bal-RptId: "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getRptPgntn().getPgNb());
        System.err.println("    LastPgInd: "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getRptPgntn().isLastPgInd());
        System.err.println("********************************************");
        System.err.println("********************************************");
    }
}

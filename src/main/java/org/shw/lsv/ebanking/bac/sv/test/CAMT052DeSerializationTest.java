package org.shw.lsv.ebanking.bac.sv.test;

import java.time.LocalDateTime;

import org.shw.lsv.ebanking.bac.sv.camt052.response.CAMT052Response;
import org.shw.lsv.ebanking.bac.sv.handling.JsonProcessor;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationException;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

public class CAMT052DeSerializationTest {
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
            CAMT052Response request = processor.deserialize(testJson, CAMT052Response.class);

            // 5. Check for non-fatal warnings
            if (collector.hasErrors()) {
                System.out.println("\nDeserialization succeeded with warnings:");
                System.out.println(collector.getAllErrors());
            } else {
                System.out.println("Deserialization completed cleanly");
            }

            // 6. Use the deserialized object
            System.out.println("\nDeserialized object details:");
            printResponseSummary(request);
            
        } catch (JsonValidationException e) {
            System.err.println("\nCritical validation failures:");
            System.err.println(e.getValidationErrors());
        }
    }

    private static String createTestJson() {
        // Example JSON that might trigger validation rules
        String jsonContent =
            "{\n" +
            "    \"Envelope\": {\n" +
            "        \"AppHdr\": {\n" +
            "            \"Fr\": {\n" +
            "                \"FIId\": {\n" +
            "                    \"FinInstnId\": {\n" +
            "                        \"BICFI\": \"BAMCSVSS\"\n" +
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
            "            \"BizMsgIdr\": \"182142\",\n" +
            "            \"MsgDefIdr\": \"AccountBalanceReportV08\",\n" +
            "            \"BizSvc\": \"swift.cbprplus.02\",\n" +
            "            \"CreDt\": \"2024-07-23T18:44:54-06:00\"\n" +
            "        },\n" +
            "        \"Document\": {\n" +
            "            \"BkToCstmrAcctRpt\": {\n" +
            "                \"GrpHdr\": {\n" +
            "                    \"MsgId\": \"182142\",\n" +
            "                    \"CreDtTm\": \"2024-07-23T18:44:54-06:00\"\n" +
            "                },\n" +
            "                \"Rpt\": {\n" +
            "                    \"Id\": \"2024072318445460000\",\n" +
            "                    \"RptPgntn\": {\n" +
            "                        \"PgNb\": \"1\",\n" +
            "                        \"LastPgInd\": \"true\"\n" +
            "                    },\n" +
            "                    \"Acct\": {\n" +
            "                        \"Id\": {\n" +
            "                            \"Othr\": {\n" +
            "                                \"Id\": \"999888666\"\n" +
            "                            }\n" +
            "                        },\n" +
            "                        \"Ccy\": \"USD\"\n" +
            "                    },\n" +
            "                    \"Bal\": {\n" +
            "                        \"Tp\": {\n" +
            "                            \"CdOrPrtry\": {\n" +
            "                                \"Cd\": \"ITAV\"\n" +
            "                            }\n" +
            "                        },\n" +
            "                        \"CdtDbtInd\": \"CRDT\",\n" +
            "                        \"Dt\": {\n" +
            "                            \"DtTm\": \"2024-07-23T18:44:54-06:00\"\n" +
            "                        },\n" +
            "                        \"Amt\": {\n" +
            "                            \"Ccy\": \"USD\",\n" +
            "                            \"Amt\": \"999994769.99\"\n" +
            "                        }\n" +
            "                    }\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}";
    
            return jsonContent;
        }

        
               // For error testing, you could:
               // - Omit required fields
               // - Set invalid values
               // - Include nulls where prohibited
/*  
 * Zu erwartetes Ergebnis:
 * *** AppHdr ***
    Fr-BICFI : BAMCSVSS
    To-BICFI : DUMMYMASTER
    BizMshIdr: 182142
    BizSvc:    swift.cbprplus.02
    CreDt:     2024-07-23T18:44:54-06:00
    MsgDefIdr: AccountBalanceReportV08
*** CAMT052 Response Document ***
********************************************
    CreDtTm:   2024-07-23T18:44:54-06:00
    MsgId:     182142
    AcctId:    999888666
    Acct-Ccy:  USD
    Bal-Amt:   999994769.99
    Bal-Ccy:   USD
    CdtDbtInd: CRDT
    Bal-Dt:    2024-07-23T18:44:54-06:00
    Bal-Cd:    ITAV
    Id:        2024072318445460000
    Bal-RptId: 1
    LastPgInd: true
 * 
*/
    private static void printResponseSummary(CAMT052Response response) {
        LocalDateTime now = LocalDateTime.now();
        System.err.println("Deserialization finished at: " + now.format(EBankingConstants.DATETIME_FORMATTER));
    
        System.out.println("Envelope present: " + (response.getcAMT052ResponseEnvelope() != null));
        if (response.getcAMT052ResponseEnvelope() != null) {
            System.out.println("Document present: " + 
                (response.getcAMT052ResponseEnvelope().getcAMT052ResponseDocument() != null));
        }
        System.err.println("********************************************");
        System.err.println("********************************************");
        System.err.println("Ergebinisse:");
        System.err.println("*** AppHdr ***");
        System.err.println("    Fr-BICFI : "  + response.getcAMT052ResponseEnvelope().getAppHdr().getFr().getfIId().getFinInstnId().getbICFI());
        System.err.println("    To-BICFI : "  + response.getcAMT052ResponseEnvelope().getAppHdr().getTo().getfIId().getFinInstnId().getbICFI());
        System.err.println("    BizMshIdr: "  + response.getcAMT052ResponseEnvelope().getAppHdr().getBizMsgIdr());
        System.err.println("    BizSvc:    "  + response.getcAMT052ResponseEnvelope().getAppHdr().getBizSvc());
        System.err.println("    CreDt:     "  + response.getcAMT052ResponseEnvelope().getAppHdr().getCreDt());
        System.err.println("    MsgDefIdr: "  + response.getcAMT052ResponseEnvelope().getAppHdr().getMsgDefIdr());
        System.err.println("*** CAMT052 Response Document ***");
        System.err.println("********************************************");
        System.err.println("    CreDtTm:   "  + response.getcAMT052ResponseEnvelope().getcAMT052ResponseDocument().getBkToCstmrAcctRpt().getGrpHdr().getCreDtTm());
        System.err.println("    MsgId:     "  + response.getcAMT052ResponseEnvelope().getcAMT052ResponseDocument().getBkToCstmrAcctRpt().getGrpHdr().getMsgId());
        System.err.println("    AcctId:    "  + response.getcAMT052ResponseEnvelope().getcAMT052ResponseDocument().getBkToCstmrAcctRpt().getRpt().getAcct().getAcctId().getAcctIdOthr().getId());
        System.err.println("    Acct-Ccy:  "  + response.getcAMT052ResponseEnvelope().getcAMT052ResponseDocument().getBkToCstmrAcctRpt().getRpt().getAcct().getCcy());
        System.err.println("    Bal-Amt:   "  + response.getcAMT052ResponseEnvelope().getcAMT052ResponseDocument().getBkToCstmrAcctRpt().getRpt().getBal().getAmt().getAmt());
        System.err.println("    Bal-Ccy:   "  + response.getcAMT052ResponseEnvelope().getcAMT052ResponseDocument().getBkToCstmrAcctRpt().getRpt().getBal().getAmt().getCcy());
        System.err.println("    CdtDbtInd: "  + response.getcAMT052ResponseEnvelope().getcAMT052ResponseDocument().getBkToCstmrAcctRpt().getRpt().getBal().getCdtDbtInd());
        System.err.println("    Bal-DtTm:  "  + response.getcAMT052ResponseEnvelope().getcAMT052ResponseDocument().getBkToCstmrAcctRpt().getRpt().getBal().getDt().getDtTm());
        System.err.println("    Bal-Cd:    "  + response.getcAMT052ResponseEnvelope().getcAMT052ResponseDocument().getBkToCstmrAcctRpt().getRpt().getBal().getTp().getCdOrPrtry().getCd());
        System.err.println("    Id:        "  + response.getcAMT052ResponseEnvelope().getcAMT052ResponseDocument().getBkToCstmrAcctRpt().getRpt().getId());

        System.err.println("    Bal-RptId: "  + response.getcAMT052ResponseEnvelope().getcAMT052ResponseDocument().getBkToCstmrAcctRpt().getRpt().getRptPgntn().getPgNb());
        System.err.println("    LastPgInd: "  + response.getcAMT052ResponseEnvelope().getcAMT052ResponseDocument().getBkToCstmrAcctRpt().getRpt().getRptPgntn().isLastPgInd());
        System.err.println("********************************************");
        System.err.println("********************************************");
    }
}

package org.shw.lsv.ebanking.bac.sv.test;

import org.shw.lsv.ebanking.bac.sv.camt052.response.CAMT052Response;
import org.shw.lsv.ebanking.bac.sv.handling.JsonProcessor;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationException;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

public class CAMT052DeSerializationTest {
    public static void main(String[] args) {
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

    private static void printResponseSummary(CAMT052Response response) {
        // Implement meaningful object inspection
        System.out.println("Envelope present: " + (response.getcAMT052ResponseEnvelope() != null));
        if (response.getcAMT052ResponseEnvelope() != null) {
            System.out.println("Document present: " + 
                (response.getcAMT052ResponseEnvelope().getcAMT052ResponseDocument() != null));
            // Add more details as needed
        }
    }
}

package org.shw.lsv.ebanking.bac.sv.test;

import java.time.LocalDateTime;

import org.shw.lsv.ebanking.bac.sv.handling.JsonProcessor;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationException;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.camt053.response.CAMT053Response;

public class CAMT053DeSerialization {

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        System.err.println("CAMT053 Deserialization started at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        // 1. Create collector with explicit settings
        JsonValidationExceptionCollector collector = new JsonValidationExceptionCollector();

        // 2. Inject collector into processor
        JsonProcessor processor = new JsonProcessor(collector);

        // 3. Prepare test JSON (could also read from file)
        String testJson = createTestJson();

        // 4. Execute deserialization
        try {
            System.out.println("Starting CAMT053 deserialization test...");
            CAMT053Response response = processor.deserialize(testJson, CAMT053Response.class);

            // 5. Check for non-fatal warnings
            if (collector.hasErrors()) {
                System.out.println("\nCAMT053 Deserialization succeeded with warnings:");
                System.out.println(collector.getAllErrors());
            } else {
                System.out.println("CAMT053 Deserialization completed cleanly");
            }

            // 6. Use the deserialized object
            System.out.println("\nCAMT053 Deserialized object details:");
            printResponseSummary(response);

        } catch (JsonValidationException e) {
            System.err.println("\nCritical validation failures:");
            System.err.println(e.getValidationErrors());
        }
    }

    private static String createTestJson() {
        String jsonContent =
            "{\n" +
            "  \"Envelope\": {\n" +
            "    \"AppHdr\": {\n" +
            "      \"Fr\": {\n" +
            "        \"FIId\": {\n" +
            "          \"FinInstnId\": {\n" +
            "            \"BICFI\": \"BSNJCRSJ\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"To\": {\n" +
            "        \"FIId\": {\n" +
            "          \"FinInstnId\": {\n" +
            "            \"BICFI\": \"DUMMYMASTER\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"BizMsgIdr\": \"182442\",\n" +
            "      \"MsgDefIdr\": \"camt.053.001.08\",\n" +
            "      \"BizSvc\": \"swift.cbprplus.02\",\n" +
            "      \"CreDt\": \"2024-07-23T18:11:37-06:00\"\n" +
            "    },\n" +
            "    \"Document\": {\n" +
            "      \"BkToCstmrStmt\": {\n" +
            "        \"GrpHdr\": {\n" +
            "          \"MsgId\": \"182442\",\n" +
            "          \"CreDtTm\": \"2024-07-23T18:11:37-06:00\"\n" +
            "        },\n" +
            "        \"Stmt\": {\n" +
            "          \"Id\": \"1824422024072318113764256\",\n" +
            "          \"StmtPgntn\": {\n" +
            "            \"PgNb\": \"1\",\n" +
            "            \"LastPgInd\": \"true\"\n" +
            "          },\n" +
            "          \"ElctrncSeqNb\": 723,\n" +
            "          \"FrToDt\": {\n" +
            "            \"FrDtTm\": \"2024-07-22T00:00:00+00:00\",\n" +
            "            \"ToDtTm\": \"2024-07-22T23:59:59+00:00\"\n" +
            "          },\n" +
            "          \"Acct\": {\n" +
            "            \"Id\": {\n" +
            "              \"Othr\": {\n" +
            "                \"Id\": \"999888666\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"Ccy\": \"CRC\"\n" +
            "          },\n" +
            "          \"Bal\": [\n" +
            "            {\n" +
            "              \"Tp\": {\n" +
            "                \"CdOrPrtry\": {\n" +
            "                  \"Cd\": \"OPBD\"\n" +
            "                }\n" +
            "              },\n" +
            "              \"CdtDbtInd\": \"DBIT\",\n" +
            "              \"Dt\": {\n" +
            "                \"Dt\": \"2024-07-22\"\n" +
            "              },\n" +
            "              \"Amt\": {\n" +
            "                \"Ccy\": \"CRC\",\n" +
            "                \"Amt\": 317899.03\n" +
            "              }\n" +
            "            },\n" +
            "            {\n" +
            "              \"Tp\": {\n" +
            "                \"CdOrPrtry\": {\n" +
            "                  \"Cd\": \"CLBD\"\n" +
            "                }\n" +
            "              },\n" +
            "              \"CdtDbtInd\": \"DBIT\",\n" +
            "              \"Dt\": {\n" +
            "                \"Dt\": \"2024-07-22\"\n" +
            "              },\n" +
            "              \"Amt\": {\n" +
            "                \"Ccy\": \"CRC\",\n" +
            "                \"Amt\": 317985.1\n" +
            "              }\n" +
            "            }\n" +
            "          ],\n" +
            "          \"Ntry\": [\n" +
            "            {\n" +
            "              \"NtryRef\": \"390300598\",\n" +
            "              \"Amt\": {\n" +
            "                \"Amt\": 221.19,\n" +
            "                \"Ccy\": \"CRC\"\n" +
            "              },\n" +
            "              \"CdtDbtInd\": \"DBIT\",\n" +
            "              \"Sts\": {\n" +
            "                \"Cd\": \"BOOK\"\n" +
            "              },\n" +
            "              \"BookgDt\": {\n" +
            "                \"Dt\": \"2024-07-22\"\n" +
            "              },\n" +
            "              \"ValDt\": {\n" +
            "                \"Dt\": \"2024-07-22\"\n" +
            "              },\n" +
            "              \"BkTxCd\": {\n" +
            "                \"Domn\": {\n" +
            "                  \"Cd\": \"NMSC\",\n" +
            "                  \"Fmly\": {\n" +
            "                    \"Cd\": \"NTAV\",\n" +
            "                    \"SubFmlyCd\": \"NTAV\"\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"AddtlNtryInf\": \"PP INV TRN DIA TRX 1\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"NtryRef\": \"390300599\",\n" +
            "              \"Amt\": {\n" +
            "                \"Amt\": 430.5,\n" +
            "                \"Ccy\": \"CRC\"\n" +
            "              },\n" +
            "              \"CdtDbtInd\": \"DBIT\",\n" +
            "              \"Sts\": {\n" +
            "                \"Cd\": \"BOOK\"\n" +
            "              },\n" +
            "              \"BookgDt\": {\n" +
            "                \"Dt\": \"2024-07-22\"\n" +
            "              },\n" +
            "              \"ValDt\": {\n" +
            "                \"Dt\": \"2024-07-22\"\n" +
            "              },\n" +
            "              \"BkTxCd\": {\n" +
            "                \"Domn\": {\n" +
            "                  \"Cd\": \"NMSC\",\n" +
            "                  \"Fmly\": {\n" +
            "                    \"Cd\": \"NTAV\",\n" +
            "                    \"SubFmlyCd\": \"NTAV\"\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"AddtlNtryInf\": \"PP INV TRN DIA TRX 2\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"NtryRef\": \"390300601\",\n" +
            "              \"Amt\": {\n" +
            "                \"Amt\": 305.22,\n" +
            "                \"Ccy\": \"CRC\"\n" +
            "              },\n" +
            "              \"CdtDbtInd\": \"CRDT\",\n" +
            "              \"Sts\": {\n" +
            "                \"Cd\": \"BOOK\"\n" +
            "              },\n" +
            "              \"BookgDt\": {\n" +
            "                \"Dt\": \"2024-07-22\"\n" +
            "              },\n" +
            "              \"ValDt\": {\n" +
            "                \"Dt\": \"2024-07-22\"\n" +
            "              },\n" +
            "              \"BkTxCd\": {\n" +
            "                \"Domn\": {\n" +
            "                  \"Cd\": \"NMSC\",\n" +
            "                  \"Fmly\": {\n" +
            "                    \"Cd\": \"NTAV\",\n" +
            "                    \"SubFmlyCd\": \"NTAV\"\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"AddtlNtryInf\": \"PP INV TRN DIA TRX 4\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"NtryRef\": \"390300602\",\n" +
            "              \"Amt\": {\n" +
            "                \"Amt\": 260.4,\n" +
            "                \"Ccy\": \"CRC\"\n" +
            "              },\n" +
            "              \"CdtDbtInd\": \"CRDT\",\n" +
            "              \"Sts\": {\n" +
            "                \"Cd\": \"BOOK\"\n" +
            "              },\n" +
            "              \"BookgDt\": {\n" +
            "                \"Dt\": \"2024-07-22\"\n" +
            "              },\n" +
            "              \"ValDt\": {\n" +
            "                \"Dt\": \"2024-07-22\"\n" +
            "              },\n" +
            "              \"BkTxCd\": {\n" +
            "                \"Domn\": {\n" +
            "                  \"Cd\": \"NMSC\",\n" +
            "                  \"Fmly\": {\n" +
            "                    \"Cd\": \"NTAV\",\n" +
            "                    \"SubFmlyCd\": \"NTAV\"\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"AddtlNtryInf\": \"PP INV TRN DIA TRX 5\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"NtryRef\": \"8738\",\n" +
            "              \"Amt\": {\n" +
            "                \"Amt\": 10,\n" +
            "                \"Ccy\": \"CRC\"\n" +
            "              },\n" +
            "              \"CdtDbtInd\": \"CRDT\",\n" +
            "              \"Sts\": {\n" +
            "                \"Cd\": \"BOOK\"\n" +
            "              },\n" +
            "              \"BookgDt\": {\n" +
            "                \"Dt\": \"2024-07-22\"\n" +
            "              },\n" +
            "              \"ValDt\": {\n" +
            "                \"Dt\": \"2024-07-22\"\n" +
            "              },\n" +
            "              \"BkTxCd\": {\n" +
            "                \"Domn\": {\n" +
            "                  \"Cd\": \"NTRF\",\n" +
            "                  \"Fmly\": {\n" +
            "                    \"Cd\": \"NTAV\",\n" +
            "                    \"SubFmlyCd\": \"NTAV\"\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"AddtlNtryInf\": \"RobotAPI\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"NtryRef\": \"390300598\",\n" +
            "              \"Amt\": {\n" +
            "                \"Amt\": \"10\",\n" +
            "                \"Ccy\": \"CRC\"\n" +
            "              },\n" +
            "              \"CdtDbtInd\": \"DBIT\",\n" +
            "              \"Sts\": {\n" +
            "                \"Cd\": \"BOOK\"\n" +
            "              },\n" +
            "              \"BookgDt\": {\n" +
            "                \"Dt\": \"2024-07-22\"\n" +
            "              },\n" +
            "              \"ValDt\": {\n" +
            "                \"Dt\": \"2024-07-22\"\n" +
            "              },\n" +
            "              \"BkTxCd\": {\n" +
            "                \"Domn\": {\n" +
            "                  \"Cd\": \"NTRF\",\n" +
            "                  \"Fmly\": {\n" +
            "                    \"Cd\": \"NTAV\",\n" +
            "                    \"SubFmlyCd\": \"NTAV\"\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"NtryDtls\": {\n" +
            "                \"TxDtls\": {\n" +
            "                  \"Refs\": {\n" +
            "                    \"EndToEndId\": \"Pago factura 01234\"\n" +
            "                  },\n" +
            "                  \"Amt\": \"10\",\n" +
            "                  \"CdtDbtInd\": \"DBIT\"\n" +
            "                }\n" +
            "              },\n" +
            "              \"AddtlNtryInf\": \"Pago proveedor\"\n" +
            "            }\n" +
            "          ]\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";
        return jsonContent;
    }

    private static void printResponseSummary(CAMT053Response response) {
        // You can implement a summary printout similar to TMST039DeSerializationTest if needed
        System.out.println("Envelope present: " + (response.getcAMT053ResponseEnvelope() != null));
        if (response.getcAMT053ResponseEnvelope() != null) {
            System.out.println("Document present: " +
                (response.getcAMT053ResponseEnvelope().getcAMT053ResponseDocument() != null));
        }
    }
}
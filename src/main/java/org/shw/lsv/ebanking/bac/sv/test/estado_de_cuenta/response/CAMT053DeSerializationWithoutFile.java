package org.shw.lsv.ebanking.bac.sv.test.estado_de_cuenta.response;

import java.time.LocalDateTime;

import org.shw.lsv.ebanking.bac.sv.handling.JsonProcessor;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationException;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.AppHdr;
import org.shw.lsv.ebanking.bac.sv.misc.GrpHdr;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.camt053.response.BkToCstmrStmt;
import org.shw.lsv.ebanking.bac.sv.camt053.response.CAMT053Response;
import org.shw.lsv.ebanking.bac.sv.camt053.response.Stmt;
import org.shw.lsv.ebanking.bac.sv.camt052.response.RptPgntn;
import org.shw.lsv.ebanking.bac.sv.camt053.response.FrToDt;
import org.shw.lsv.ebanking.bac.sv.camt053.response.Bal;
import org.shw.lsv.ebanking.bac.sv.camt053.response.StmtOfAccountElement;
import org.shw.lsv.ebanking.bac.sv.camt053.response.NtryDtls;
import org.shw.lsv.ebanking.bac.sv.camt053.response.TxDtls;
import org.shw.lsv.ebanking.bac.sv.misc.Acct;

public class CAMT053DeSerializationWithoutFile {

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
        LocalDateTime now = LocalDateTime.now();
        System.err.println("CAMT053 Deserialization finished at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        System.out.println("CAMT053 Envelope present: " + (response.getcAMT053ResponseEnvelope() != null));

        if (response.getcAMT053ResponseEnvelope() != null) {
            System.out.println("CAMT053 Document present: " +
                (response.getcAMT053ResponseEnvelope().getcAMT053ResponseDocument() != null));

            System.err.println("********************************************");
            System.err.println("********************************************");
            System.err.println("Ergebnisse:");

            System.err.println("*** AppHdr ***");
            AppHdr appHdr = response.getcAMT053ResponseEnvelope().getAppHdr();
            if (appHdr != null) {
                String frBicfi = "Not available";
                if (appHdr.getFr() != null && appHdr.getFr().getfIId() != null && appHdr.getFr().getfIId().getFinInstnId() != null) {
                    frBicfi = appHdr.getFr().getfIId().getFinInstnId().getbICFI();
                }
                System.err.println("    Fr-BICFI : "  + frBicfi);

                String toBicfi = "Not available";
                if (appHdr.getTo() != null && appHdr.getTo().getfIId() != null && appHdr.getTo().getfIId().getFinInstnId() != null) {
                    toBicfi = appHdr.getTo().getfIId().getFinInstnId().getbICFI();
                }
                System.err.println("    To-BICFI : "  + toBicfi);

                System.err.println("    BizMsgIdr: "  + appHdr.getBizMsgIdr());
                System.err.println("    MsgDefIdr: "  + appHdr.getMsgDefIdr());
                System.err.println("    BizSvc:    "  + appHdr.getBizSvc());
                System.err.println("    CreDt:     "  + appHdr.getCreDt());
            } else {
                System.err.println("    AppHdr not available.");
            }

            System.err.println("********************************************");
            System.err.println("******** CAMT053 Response Document ******** *");
            System.err.println("********************************************");
            if (response.getcAMT053ResponseEnvelope().getcAMT053ResponseDocument() != null &&
                response.getcAMT053ResponseEnvelope().getcAMT053ResponseDocument().getBkToCstmrStmt() != null) {

                BkToCstmrStmt bkToCstmrStmt = response.getcAMT053ResponseEnvelope().getcAMT053ResponseDocument().getBkToCstmrStmt();
                if (bkToCstmrStmt.getGrpHdr() != null) {
                    GrpHdr grpHdr = bkToCstmrStmt.getGrpHdr();
                    System.err.println("    MsgId:     "  + grpHdr.getMsgId());
                    System.err.println("    CreDtTm:   "  + grpHdr.getCreDtTm());
                } else {
                    System.err.println("    GrpHdr not available in BkToCstmrStmt.");
                }

                Stmt stmt = bkToCstmrStmt.getStmt();
                if (stmt != null) {
                    System.err.println("************* Stmt ***********************");
                    // Id
                    if (stmt.getId() != null) {
                        System.err.println("    Id:             " + stmt.getId());
                    } else {
                        System.err.println("    Id:             Not available");
                    }

                    RptPgntn rptPgntn = stmt.getRptPgntn();
                    if (rptPgntn != null) {
                        System.err.println("    StmtPgntn-PgNb: " + (rptPgntn.getPgNb() != null ? rptPgntn.getPgNb() : "Not available"));
                        System.err.println("    StmtPgntn-Last: " + rptPgntn.isLastPgInd()); // boolean primitive, no null check needed for value itself
                    } else {
                        System.err.println("    StmtPgntn:      Not available");
                    }

                    if (stmt.getElctrncSeqNb() != null) {
                        System.err.println("    ElctrncSeqNb:   " + stmt.getElctrncSeqNb());
                    } else {
                        System.err.println("    ElctrncSeqNb:   Not available");
                    }

                    FrToDt frToDt = stmt.getFrToDt();
                    if (frToDt != null) {
                        System.err.println("    FrToDt-FrDtTm:  " + (frToDt.getFrDtTm() != null ? frToDt.getFrDtTm() : "Not available"));
                        System.err.println("    FrToDt-ToDtTm:  " + (frToDt.getToDtTm() != null ? frToDt.getToDtTm() : "Not available"));
                    } else {
                        System.err.println("    FrToDt:         Not available");
                    }

                    Acct acct = stmt.getAcct();
                    if (acct != null) {
                        String acctIdStr = "Not available";
                        if (acct.getAcctId() != null && acct.getAcctId().getAcctIdOthr() != null && acct.getAcctId().getAcctIdOthr().getId() != null) {
                            acctIdStr = acct.getAcctId().getAcctIdOthr().getId();
                        }
                        System.err.println("    Acct-Id:        " + acctIdStr);
                        System.err.println("    Acct-Ccy:       " + (acct.getCcy() != null ? acct.getCcy() : "Not available"));
                    } else {
                        System.err.println("    Acct:           Not available");
                    }

            System.err.println("********************************************");
            System.err.println("*** Balances ***");
            System.err.println("********************************************");
                    if (stmt.getBalances() != null && !stmt.getBalances().isEmpty()) {
                        System.err.println("    Balances:");
                        int balIndex = 0;
                        for (Bal balance : stmt.getBalances()) {
                            System.err.println("      Balance[" + balIndex + "]:");
                            if (balance != null) {
                                String balTpCd = "Not available";
                                if (balance.getTp() != null && balance.getTp().getCdOrPrtry() != null) {
                                    balTpCd = balance.getTp().getCdOrPrtry().getCd();
                                }
                                System.err.println("        Tp-Cd:       " + balTpCd);
                                System.err.println("        CdtDbtInd:   " + (balance.getCdtDbtInd() != null ? balance.getCdtDbtInd() : "Not available"));

                                String balDt = "Not available";
                                if (balance.getDt() != null) {
                                    balDt = balance.getDt().getDt();
                                }
                                System.err.println("        Dt:          " + balDt);

                                if (balance.getAmt() != null) {
                                    System.err.println("        Amt-Ccy:     " + (balance.getAmt().getCcy() != null ? balance.getAmt().getCcy() : "Not available"));
                                    System.err.println("        Amt-Amt:     " + (balance.getAmt().getAmt() != null ? balance.getAmt().getAmt() : "Not available"));
                                } else {
                                    System.err.println("        Amt:         Not available");
                                }
                            } else {
                                System.err.println("        Balance object is null");
                            }
                            balIndex++;
                        }
                    } else {
                        System.err.println("    Balances:       Not available or empty");
                    }

            System.err.println("********************************************");
            System.err.println("********  StmtOfAccountElements  ***********");
            System.err.println("********************************************");
                    if (stmt.getStmtOfAccountElements() != null && !stmt.getStmtOfAccountElements().isEmpty()) {
                        System.err.println("    Entries (Ntry):");
                        int ntryIndex = 0;
                        for (StmtOfAccountElement entry : stmt.getStmtOfAccountElements()) {
                            System.err.println("      Ntry[" + ntryIndex + "]:");
                            if (entry != null) {
                                System.err.println("        NtryRef:        " + (entry.getNtryRef() != null ? entry.getNtryRef() : "Not available"));

                                if (entry.getAmt() != null) {
                                    System.err.println("        Amt-Ccy:        " + (entry.getAmt().getCcy() != null ? entry.getAmt().getCcy() : "Not available"));
                                    System.err.println("        Amt-Amt:        " + (entry.getAmt().getAmt() != null ? entry.getAmt().getAmt() : "Not available"));
                                } else {
                                    System.err.println("        Amt:            Not available");
                                }

                                System.err.println("        CdtDbtInd:      " + (entry.getCdtDbtInd() != null ? entry.getCdtDbtInd() : "Not available"));

                                if (entry.getSts() != null) {
                                    System.err.println("        Sts-Cd:         " + (entry.getSts().getCd() != null ? entry.getSts().getCd() : "Not available"));
                                } else {
                                    System.err.println("        Sts:            Not available");
                                }

                                if (entry.getBookgDt() != null && entry.getBookgDt().getDt() != null) {
                                    System.err.println("        BookgDt-Dt:     " + (entry.getBookgDt().getDt() != null ? entry.getBookgDt().getDt() : "Not available"));
                                } else {
                                    System.err.println("        BookgDt:        Not available");
                                }

                                if (entry.getValDt() != null && entry.getValDt().getDt() != null) {
                                    System.err.println("        ValDt-Dt:       " + (entry.getValDt().getDt() != null ? entry.getValDt().getDt() : "Not available"));
                                } else {
                                    System.err.println("        ValDt:          Not available");
                                }

                                if (entry.getBkTxCd() != null && entry.getBkTxCd().getDomn() != null) {
                                    System.err.println("        BkTxCd-Domn-Cd: " + (entry.getBkTxCd().getDomn().getCd() != null ? entry.getBkTxCd().getDomn().getCd() : "Not available"));
                                    if (entry.getBkTxCd().getDomn().getFmly() != null) {
                                        System.err.println("        BkTxCd-Fmly-Cd: " + (entry.getBkTxCd().getDomn().getFmly().getCd() != null ? entry.getBkTxCd().getDomn().getFmly().getCd() : "Not available"));
                                        System.err.println("        BkTxCd-SubFmly: " + (entry.getBkTxCd().getDomn().getFmly().getSubFmlyCd() != null ? entry.getBkTxCd().getDomn().getFmly().getSubFmlyCd() : "Not available"));
                                    } else {
                                        System.err.println("        BkTxCd-Fmly:    Not available");
                                    }
                                } else {
                                    System.err.println("        BkTxCd:         Not available");
                                }

                                System.err.println("        AddtlNtryInf:   " + (entry.getAddtlNtryInf() != null ? entry.getAddtlNtryInf() : "Not available"));

                                NtryDtls ntryDtls = entry.getNtryDtls();
                                if (ntryDtls != null && ntryDtls.getTxDtls() != null) {
                                    TxDtls txDtls = ntryDtls.getTxDtls();
                                    System.err.println("        NtryDtls-TxDtls:");
                                    if (txDtls.getPmtId() != null) {
                                        System.err.println("          Refs-EndToEndId: " + (txDtls.getPmtId().getEndToEndId() != null ? txDtls.getPmtId().getEndToEndId() : "Not available"));
                                    } else {
                                        System.err.println("          Refs:            Not available");
                                    }
                                    System.err.println("          Amt:             " + (txDtls.getAmt() != null ? txDtls.getAmt() : "Not available"));
                                    System.err.println("          CdtDbtInd:       " + (txDtls.getCdtDbtInd() != null ? txDtls.getCdtDbtInd() : "Not available"));
                                } else {
                                    System.err.println("        NtryDtls-TxDtls: Not available");
                                }
                            } else {
                                System.err.println("        Ntry object is null");
                            }
                            ntryIndex++;
                            System.err.println("********************************************");
                        }
                    } else {
                        System.err.println("    Entries (Ntry): Not available or empty");
                    }
                } else {
                    System.err.println("    Stmt not available in BkToCstmrStmt.");
                }
            } else {
                System.err.println("    Document or BkToCstmrStmt not available for GrpHdr details.");
            }

            System.err.println("********************************************");
            System.err.println("********************************************");

        } // End of if (response.getcAMT053ResponseEnvelope() != null)
    }
}
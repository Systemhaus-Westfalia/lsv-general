package org.shw.lsv.ebanking.bac.sv.z_test.estado_de_cuenta.response;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import org.shw.lsv.ebanking.bac.sv.camt052.response.RptPgntn;
import org.shw.lsv.ebanking.bac.sv.camt053.response.Bal;
import org.shw.lsv.ebanking.bac.sv.camt053.response.BkToCstmrStmt;
import org.shw.lsv.ebanking.bac.sv.camt053.response.CAMT053Response;
import org.shw.lsv.ebanking.bac.sv.camt053.response.CAMT053ResponseDocument;
import org.shw.lsv.ebanking.bac.sv.camt053.response.CAMT053ResponseEnvelope;
import org.shw.lsv.ebanking.bac.sv.camt053.response.FrToDt;
import org.shw.lsv.ebanking.bac.sv.camt053.response.NtryDtls;
import org.shw.lsv.ebanking.bac.sv.camt053.response.Stmt;
import org.shw.lsv.ebanking.bac.sv.camt053.response.StmtOfAccountElement;
import org.shw.lsv.ebanking.bac.sv.camt053.response.TxDtls;
import org.shw.lsv.ebanking.bac.sv.handling.JsonProcessor;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationException;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.Acct;
import org.shw.lsv.ebanking.bac.sv.misc.AppHdr;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.misc.GrpHdr;

public class CAMT053DeSerializationWithFiles {
    private static final String CLASS_NAME = CAMT053DeSerializationWithFiles.class.getSimpleName();

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        System.err.println(CLASS_NAME + " started at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        // Build file names
        String inputFileName  = CLASS_NAME + "_INPUT_FILE.json";
        String outputFileName = CLASS_NAME + "_OUTPUT.txt";
        String errorFileName  = String.format("%s_ERRORS.txt", CLASS_NAME);

        // Build file paths
        Path baseDir        = Paths.get(EBankingConstants.TEST_BASE_DIRECTORY_PATH, 
            "estado_de_cuenta", 
            EBankingConstants.TEST_FILES_RESPONSE);
        Path inputFilePath  = baseDir.resolve(inputFileName);
        Path outputFilePath = baseDir.resolve(outputFileName);
        Path errorFilePath  = baseDir.resolve(errorFileName);

        // Ensure output directory exists
        try {
            Files.createDirectories(baseDir);
        } catch (IOException e) {
            System.err.println("Could not create output directory: " + baseDir.toAbsolutePath());
            e.printStackTrace();
            return;
        }

        // Read test JSON from file
        String testJson = "";
        try {
            System.err.println("Reading from file: " + inputFilePath.toAbsolutePath());
            testJson = Files.readString(inputFilePath);
        } catch (IOException e) {
            System.err.println("Could not read input file: " + inputFilePath.toAbsolutePath());
            System.err.println("Please ensure the file exists and is readable.");
            e.printStackTrace();
            return;
        }

        // 1. Create collector for diagnostics
        JsonValidationExceptionCollector collector = new JsonValidationExceptionCollector();

        // 2. Inject collector into processor
        JsonProcessor processor = new JsonProcessor(collector);

        // 3. Execute deserialization
        try {
            System.out.println("Starting CAMT053 deserialization test...");
            CAMT053Response response = processor.deserialize(testJson, CAMT053Response.class);

            // 4. Check for non-fatal warnings
            if (collector.hasErrors()) {
                String errorContent = "\nCAMT053 Deserialization succeeded with warnings/errors:\n" + collector.getAllErrors();
                System.err.println(errorContent);
                writeToFile(errorFilePath, errorContent);
            } else {
                System.out.println("CAMT053 Deserialization completed cleanly");
            }

            // 5. Use the deserialized object and collect summary
            StringBuffer summary = new StringBuffer();
            collectResponseSummary(response, summary);

            System.err.println("\n--- Response Summary ---");
            System.err.println(summary.toString());
            System.err.println("--- End Response Summary ---\n");

            writeToFile(outputFilePath, summary.toString());

        } catch (JsonValidationException e) {
            String errorContent = "\nCritical validation failures:\n" + e.getValidationErrors();
            System.err.println(errorContent);
            writeToFile(errorFilePath, errorContent);
        }

        now = LocalDateTime.now();
        System.err.println(CLASS_NAME + " finished at: " + now.format(EBankingConstants.DATETIME_FORMATTER));
    }

    private static void collectResponseSummary(CAMT053Response response, StringBuffer summary) {
        CAMT053ResponseEnvelope envelope = response.getCamt053ResponseFile().getCamt053ResponseEnvelope();
        summary.append("CAMT053 Envelope present: ").append(envelope != null).append("\n");

        if (envelope == null) return;

        CAMT053ResponseDocument document = envelope.getcAMT053ResponseDocument();
        summary.append("CAMT053 Document present: ").append(document != null).append("\n");

        summary.append("********************************************\n");
        summary.append("Ergebnisse:\n");

        summary.append("*** AppHdr ***\n");
        AppHdr appHdr = envelope.getAppHdr();
        if (appHdr != null) {
            // Safely access nested properties
            String frBicfi = (appHdr.getFr() != null && appHdr.getFr().getfIId() != null && appHdr.getFr().getfIId().getFinInstnId() != null) ? appHdr.getFr().getfIId().getFinInstnId().getbICFI() : "N/A";
            summary.append("    Fr-BICFI : ").append(frBicfi).append("\n");
            String toBicfi = (appHdr.getTo() != null && appHdr.getTo().getfIId() != null && appHdr.getTo().getfIId().getFinInstnId() != null) ? appHdr.getTo().getfIId().getFinInstnId().getbICFI() : "N/A";
            summary.append("    To-BICFI : ").append(toBicfi).append("\n");
            summary.append("    BizMsgIdr: ").append(appHdr.getBizMsgIdr()).append("\n");
            summary.append("    MsgDefIdr: ").append(appHdr.getMsgDefIdr()).append("\n");
            summary.append("    BizSvc:    ").append(appHdr.getBizSvc()).append("\n");
            summary.append("    CreDt:     ").append(appHdr.getCreDt()).append("\n");
        } else {
            summary.append("    AppHdr not available.\n");
        }

        summary.append("********************************************\n");
        summary.append("******** CAMT053 Response Document ********\n");
        summary.append("********************************************\n");
        if (document != null && document.getBkToCstmrStmt() != null) {
            BkToCstmrStmt bkToCstmrStmt = document.getBkToCstmrStmt();
            if (bkToCstmrStmt.getGrpHdr() != null) {
                GrpHdr grpHdr = bkToCstmrStmt.getGrpHdr();
                summary.append("    MsgId:     ").append(grpHdr.getMsgId()).append("\n");
                summary.append("    CreDtTm:   ").append(grpHdr.getCreDtTm()).append("\n");
            }

            Stmt stmt = bkToCstmrStmt.getStmt();
            if (stmt != null) {
                summary.append("************* Stmt ***********************\n");
                summary.append("    Id:             ").append(stmt.getId() != null ? stmt.getId() : "N/A").append("\n");

                RptPgntn rptPgntn = stmt.getRptPgntn();
                if (rptPgntn != null) {
                    summary.append("    StmtPgntn-PgNb: ").append(rptPgntn.getPgNb() != null ? rptPgntn.getPgNb() : "N/A").append("\n");
                    summary.append("    StmtPgntn-Last: ").append(rptPgntn.isLastPgInd()).append("\n");
                }

                summary.append("    ElctrncSeqNb:   ").append(stmt.getElctrncSeqNb() != null ? stmt.getElctrncSeqNb() : "N/A").append("\n");

                FrToDt frToDt = stmt.getFrToDt();
                if (frToDt != null) {
                    summary.append("    FrToDt-FrDtTm:  ").append(frToDt.getFrDtTm() != null ? frToDt.getFrDtTm() : "N/A").append("\n");
                    summary.append("    FrToDt-ToDtTm:  ").append(frToDt.getToDtTm() != null ? frToDt.getToDtTm() : "N/A").append("\n");
                }

                Acct acct = stmt.getAcct();
                if (acct != null) {
                    String acctIdStr = (acct.getAcctId() != null && acct.getAcctId().getAcctIdOthr() != null) ? acct.getAcctId().getAcctIdOthr().getId() : "N/A";
                    summary.append("    Acct-Id:        ").append(acctIdStr).append("\n");
                    summary.append("    Acct-Ccy:       ").append(acct.getCcy() != null ? acct.getCcy() : "N/A").append("\n");
                }

                summary.append("*** Balances ***\n");
                if (stmt.getBalances() != null && !stmt.getBalances().isEmpty()) {
                    for (int i = 0; i < stmt.getBalances().size(); i++) {
                        Bal balance = stmt.getBalances().get(i);
                        summary.append("      Balance[").append(i).append("]:\n");
                        if (balance != null) {
                            String balTpCd = (balance.getTp() != null && balance.getTp().getCdOrPrtry() != null) ? balance.getTp().getCdOrPrtry().getCd() : "N/A";
                            summary.append("        Tp-Cd:       ").append(balTpCd).append("\n");
                            summary.append("        CdtDbtInd:   ").append(balance.getCdtDbtInd() != null ? balance.getCdtDbtInd() : "N/A").append("\n");
                            String balDt = (balance.getDt() != null) ? balance.getDt().getDt() : "N/A";
                            summary.append("        Dt:          ").append(balDt).append("\n");
                            if (balance.getAmt() != null) {
                                summary.append("        Amt-Ccy:     ").append(balance.getAmt().getCcy() != null ? balance.getAmt().getCcy() : "N/A").append("\n");
                                summary.append("        Amt-Amt:     ").append(balance.getAmt().getAmt() != null ? balance.getAmt().getAmt() : "N/A").append("\n");
                            }
                        }
                    }
                }

                summary.append("********  StmtOfAccountElements  ***********\n");
                if (stmt.getStmtOfAccountElements() != null && !stmt.getStmtOfAccountElements().isEmpty()) {
                    for (int i = 0; i < stmt.getStmtOfAccountElements().size(); i++) {
                        StmtOfAccountElement entry = stmt.getStmtOfAccountElements().get(i);
                        summary.append("      Ntry[").append(i).append("]:\n");
                        if (entry != null) {
                            summary.append("        NtryRef:        ").append(entry.getNtryRef() != null ? entry.getNtryRef() : "N/A").append("\n");
                            if (entry.getAmt() != null) {
                                summary.append("        Amt-Ccy:        ").append(entry.getAmt().getCcy() != null ? entry.getAmt().getCcy() : "N/A").append("\n");
                                summary.append("        Amt-Amt:        ").append(entry.getAmt().getAmt() != null ? entry.getAmt().getAmt() : "N/A").append("\n");
                            }
                            summary.append("        CdtDbtInd:      ").append(entry.getCdtDbtInd() != null ? entry.getCdtDbtInd() : "N/A").append("\n");
                            
                            // Adding missing fields from the other implementation
                            String stsCd = (entry.getSts() != null) ? entry.getSts().getCd() : "N/A";
                            summary.append("        Sts-Cd:         ").append(stsCd).append("\n");

                            String bookgDt = (entry.getBookgDt() != null) ? entry.getBookgDt().getDt() : "N/A";
                            summary.append("        BookgDt-Dt:     ").append(bookgDt).append("\n");

                            String valDt = (entry.getValDt() != null) ? entry.getValDt().getDt() : "N/A";
                            summary.append("        ValDt-Dt:       ").append(valDt).append("\n");

                            if (entry.getBkTxCd() != null && entry.getBkTxCd().getDomn() != null) {
                                summary.append("        BkTxCd-Domn-Cd: ").append(entry.getBkTxCd().getDomn().getCd() != null ? entry.getBkTxCd().getDomn().getCd() : "N/A").append("\n");
                                if (entry.getBkTxCd().getDomn().getFmly() != null) {
                                    summary.append("        BkTxCd-Fmly-Cd: ").append(entry.getBkTxCd().getDomn().getFmly().getCd() != null ? entry.getBkTxCd().getDomn().getFmly().getCd() : "N/A").append("\n");
                                    summary.append("        BkTxCd-SubFmly: ").append(entry.getBkTxCd().getDomn().getFmly().getSubFmlyCd() != null ? entry.getBkTxCd().getDomn().getFmly().getSubFmlyCd() : "N/A").append("\n");
                                }
                            }

                            summary.append("        AddtlNtryInf:   ").append(entry.getAddtlNtryInf() != null ? entry.getAddtlNtryInf() : "N/A").append("\n");

                            List<NtryDtls> ntryDtlsList = entry.getNtryDtls();
                            if (ntryDtlsList != null && !ntryDtlsList.isEmpty()) {
                                int ntryDtlsIndex = 0;
                                for (NtryDtls ntryDtls : ntryDtlsList) {
                                    summary.append("        NtryDtls[").append(ntryDtlsIndex).append("]:\n");
                                    if (ntryDtls != null && ntryDtls.getTxDtls() != null) {
                                        TxDtls txDtls = ntryDtls.getTxDtls();
                                        summary.append("          TxDtls:\n");
                                        if (txDtls.getPmtId() != null) {
                                            summary.append("            Refs-EndToEndId: ").append(txDtls.getPmtId().getEndToEndId() != null ? txDtls.getPmtId().getEndToEndId() : "N/A").append("\n");
                                        }
                                        if (txDtls.getAmt() != null) {
                                            summary.append("            Amt:             ").append(txDtls.getAmt().getAmt()).append("\n");
                                        }
                                        summary.append("            CdtDbtInd:       ").append(txDtls.getCdtDbtInd() != null ? txDtls.getCdtDbtInd() : "N/A").append("\n");
                                    }
                                    ntryDtlsIndex++;
                                }
                            }
                        }
                    }
                }
            }
        }
        summary.append("********************************************\n");
    }

    private static void writeToFile(Path filePath, String content) {
        try {
            Files.createDirectories(filePath.getParent());
            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                writer.write(content);
            }
            System.out.println("Wrote output to: " + filePath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error writing to file: " + filePath.toAbsolutePath());
            e.printStackTrace();
        }
    }
}

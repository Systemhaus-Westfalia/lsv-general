package org.shw.lsv.ebanking.bac.sv.z_test.saldo_cuenta.response;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.shw.lsv.ebanking.bac.sv.camt052.response.CAMT052Response;
import org.shw.lsv.ebanking.bac.sv.camt052.response.CAMT052ResponseDocument;
import org.shw.lsv.ebanking.bac.sv.camt052.response.CAMT052ResponseEnvelope;
import org.shw.lsv.ebanking.bac.sv.handling.JsonProcessor;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationException;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.misc.Rejection;

public class CAMT052DeSerializationTestWithFiles {
    private static final String CLASS_NAME = CAMT052DeSerializationTestWithFiles.class.getSimpleName();

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        StringBuffer log  = new StringBuffer();
        log.append(CLASS_NAME + " started at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        // Build file names
        String inputFileName  = CLASS_NAME + "_INPUT_FILE.json";
        String outputFileName = CLASS_NAME + "_OUTPUT.txt";
        String errorFileName  = String.format("%s_ERRORS.txt", CLASS_NAME);

        // Build file paths
        Path baseDir          = Paths.get(EBankingConstants.TEST_BASE_DIRECTORY_PATH, 
            EBankingConstants.TEST_FILES_DIRECTORY_SALDO_CUENTA,
            EBankingConstants.TEST_FILES_RESPONSE);
        Path inputFilePath    = baseDir.resolve(inputFileName);
        Path outputFilePath   = baseDir.resolve(outputFileName);
        Path errorFilePath    = baseDir.resolve(errorFileName);

        // Read test JSON from file
        String testJson = "";
        try {
            log.append("\nReading from file: " + inputFilePath.toAbsolutePath());
            testJson = Files.readString(inputFilePath);
        } catch (IOException e) {
            System.err.println("Could not read input file: " + inputFilePath.toAbsolutePath());
            e.printStackTrace();
            return;
        }

        // 1. Create collector for diagnostics
        JsonValidationExceptionCollector collector = new JsonValidationExceptionCollector();

        // 2. Inject collector into processor
        JsonProcessor processor = new JsonProcessor(collector);

        // 3. Execute deserialization
        try {
            log.append("\nStarting CAMT052 deserialization test...");
            CAMT052Response response = processor.deserialize(testJson, CAMT052Response.class);

            // 4. Check for non-fatal warnings
            if (collector.hasErrors()) {
                String errorContent = "\nCAMT052 Deserialization succeeded with errors:\n" + collector.getAllErrors();
                System.err.println(errorContent);
                writeToFile(errorFilePath, errorContent);
            } else {
                log.append("\nCAMT052 Deserialization completed cleanly");
            }

            now = LocalDateTime.now();
            log.append("\nCAMT052 Deserialization finished at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

            // 5. Check for success or rejection and collect the appropriate summary
            CAMT052ResponseDocument document = response.getcAMT052ResponseFile().getcAMT052ResponseEnvelope().getcAMT052ResponseDocument();
            if (document != null && document.getRejection() != null) {
                log.append("\n--- Begin of Rejection Summary ---");
                Rejection rejection = document.getRejection();
                if (rejection.getRsn() != null) {
                    log.append("\nRejection Message Received:");
                    log.append("\nReason Code: ").append(rejection.getRsn().getRjctgPtyRsn());
                    log.append("\nDescription: ").append(rejection.getRsn().getRsnDesc());
                } else {
                    log.append("\nRejection object present but Reason (Rsn) is null.");
                }
                log.append("\n--- End of Rejection Summary ---\n");
            } else if (document != null && document.getBkToCstmrAcctRpt() != null) {
                log.append("\n--- Begin of Response Summary ---");
                collectResponseSummary(response, log);
                log.append("\n--- End of Response Summary ---\n");
            } else {
                log.append("\n\nDeserialization successful, but response document contains neither a report nor a rejection.");
            }

            System.err.println(log.toString());
            writeToFile(outputFilePath, log.toString());

        } catch (JsonValidationException e) {
            // This now only catches true, unexpected deserialization failures
            String errorContent = "\nCritical validation failures:\n" + e.getValidationErrors();
            log.append(errorContent);
            System.err.println(log.toString());
            writeToFile(errorFilePath, log.toString());
        }
    }

    private static void collectResponseSummary(CAMT052Response response, StringBuffer log) {
        CAMT052ResponseEnvelope responseEnvelope = null;
        CAMT052ResponseDocument responseDocument = null;

        boolean isEnvelope = response.getcAMT052ResponseFile().getcAMT052ResponseEnvelope() != null;
        if (isEnvelope) {
            log.append("\nCAMT052 Envelope present: " + isEnvelope);
            responseEnvelope   = response.getcAMT052ResponseFile().getcAMT052ResponseEnvelope();

            boolean isDocument = responseEnvelope.getcAMT052ResponseDocument() != null;
            if (isDocument) {
                log.append("\nCAMT052 Document present: " + isDocument);
                responseDocument = responseEnvelope.getcAMT052ResponseDocument();
            } else {
                log.append("\nCAMT052 Document is present: false");
                return;
            }
        } else {
            log.append("\nCAMT052 Envelop present: false");
            return;
        }

        log.append("\n********************************************");
        log.append("\n******* CAMT052 Response *******");
        log.append("\n********************************************");
        log.append("\n*** AppHdr ***");
        log.append("\n    Fr-BICFI : "  + responseEnvelope.getAppHdr().getFr().getfIId().getFinInstnId().getbICFI());
        log.append("\n    To-BICFI : "  + responseEnvelope.getAppHdr().getTo().getfIId().getFinInstnId().getbICFI());
        log.append("\n    BizMshIdr: "  + responseEnvelope.getAppHdr().getBizMsgIdr());
        log.append("\n    BizSvc:    "  + responseEnvelope.getAppHdr().getBizSvc());
        log.append("\n    CreDt:     "  + responseEnvelope.getAppHdr().getCreDt());
        log.append("\n    MsgDefIdr: "  + responseEnvelope.getAppHdr().getMsgDefIdr());
        log.append("\n********************************************");
        log.append("\n************* GrpHdr ***********************");
        log.append("\n    CreDtTm:   "  + responseDocument.getBkToCstmrAcctRpt().getGrpHdr().getCreDtTm());
        log.append("\n    MsgId:     "  + responseDocument.getBkToCstmrAcctRpt().getGrpHdr().getMsgId());
        log.append("\n********************************************");
        log.append("\n*** CAMT052 Response Document ***");
        log.append("\n********************************************");
        log.append("\n    AcctId:    "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getAcct().getAcctId().getAcctIdOthr().getId());
        log.append("\n    Acct-Ccy:  "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getAcct().getCcy());
        log.append("\n    Bal-Amt:   "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getBal().getAmt().getAmt());
        log.append("\n    Bal-Ccy:   "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getBal().getAmt().getCcy());
        log.append("\n    CdtDbtInd: "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getBal().getCdtDbtInd());
        log.append("\n    Bal-DtTm:  "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getBal().getDt().getDtTm());
        log.append("\n    Bal-Cd:    "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getBal().getTp().getCdOrPrtry().getCd());
        log.append("\n    Id:        "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getId());

        log.append("\n    Bal-RptId: "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getRptPgntn().getPgNb());
        log.append("\n    LastPgInd: "  + responseDocument.getBkToCstmrAcctRpt().getRpt().getRptPgntn().isLastPgInd());
        log.append("\n********************************************");
        log.append("\n********************************************");
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
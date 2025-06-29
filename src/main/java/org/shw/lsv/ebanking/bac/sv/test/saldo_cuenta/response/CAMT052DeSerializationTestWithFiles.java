package org.shw.lsv.ebanking.bac.sv.test.saldo_cuenta.response;

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

public class CAMT052DeSerializationTestWithFiles {
    private static final String CLASS_NAME = CAMT052DeSerializationTestWithFiles.class.getSimpleName();

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        System.err.println(CLASS_NAME + " started at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        // Build file names
        String inputFileName  = CLASS_NAME + "_INPUT_FILE.json";
        String outputFileName = CLASS_NAME + "_OUTPUT.txt";
        String errorFileName  = String.format("%s_ERRORS.txt", CLASS_NAME);

        // Build file paths
        Path baseDir        = Paths.get(EBankingConstants.TEST_BASE_DIRECTORY_PATH, EBankingConstants.TEST_FILES_DIRECTORY);
        Path inputFilePath  = baseDir.resolve(inputFileName);
        Path outputFilePath = baseDir.resolve(outputFileName);
        Path errorFilePath  = baseDir.resolve(errorFileName);

        // Read test JSON from file
        String testJson = "";
        try {
            System.err.println("Reading from file: " + inputFilePath.toAbsolutePath());
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
            System.out.println("Starting CAMT052 deserialization test...");
            CAMT052Response response = processor.deserialize(testJson, CAMT052Response.class);

            // 4. Check for non-fatal warnings
            if (collector.hasErrors()) {
                String errorContent = "\nCAMT052 Deserialization succeeded with errors:\n" + collector.getAllErrors();
                System.err.println(errorContent);
                writeToFile(errorFilePath, errorContent);
            } else {
                System.out.println("CAMT052 Deserialization completed cleanly");
            }

            // 5. Use the deserialized object and write summary to file
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
    }

    private static void collectResponseSummary(CAMT052Response response, StringBuffer summary) {
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
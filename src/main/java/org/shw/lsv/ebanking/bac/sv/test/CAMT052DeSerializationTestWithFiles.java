package org.shw.lsv.ebanking.bac.sv.test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.shw.lsv.ebanking.bac.sv.camt052.response.CAMT052Response;
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
        String errorFileName  = String.format("%s_ERRORS_%s.txt", CLASS_NAME, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

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
        summary.append("Envelope present: ").append(response != null && response.getcAMT052ResponseEnvelope() != null).append("\n");
        if (response != null && response.getcAMT052ResponseEnvelope() != null) {
            summary.append("Document present: ")
                   .append(response.getcAMT052ResponseEnvelope().getcAMT052ResponseDocument() != null)
                   .append("\n");
            
        summary
        .append("********************************************").append("\n")
        .append("********************************************").append("\n")
        .append("Ergebnisse:").append("\n")
        .append("*** AppHdr ***").append("\n")
        .append("    Fr-BICFI : "  + response.getcAMT052ResponseEnvelope().getAppHdr().getFr().getfIId().getFinInstnId().getbICFI()).append("\n")
        .append("    To-BICFI : "  + response.getcAMT052ResponseEnvelope().getAppHdr().getTo().getfIId().getFinInstnId().getbICFI()).append("\n")
        .append("    BizMshIdr: "  + response.getcAMT052ResponseEnvelope().getAppHdr().getBizMsgIdr()).append("\n")
        .append("    BizSvc:    "  + response.getcAMT052ResponseEnvelope().getAppHdr().getBizSvc()).append("\n")
        .append("    CreDt:     "  + response.getcAMT052ResponseEnvelope().getAppHdr().getCreDt()).append("\n")
        .append("    MsgDefIdr: "  + response.getcAMT052ResponseEnvelope().getAppHdr().getMsgDefIdr()).append("\n")
        .append("********************************************").append("\n")
        .append("*** CAMT052 Response Document ***").append("\n")
        .append("********************************************").append("\n")
        .append("************* GrpHdr ***********************").append("\n")
        .append("    CreDtTm:   "  + response.getcAMT052ResponseEnvelope().getcAMT052ResponseDocument().getBkToCstmrAcctRpt().getGrpHdr().getCreDtTm()).append("\n")
        .append("    MsgId:     "  + response.getcAMT052ResponseEnvelope().getcAMT052ResponseDocument().getBkToCstmrAcctRpt().getGrpHdr().getMsgId()).append("\n")

        .append("    AcctId:    "  + response.getcAMT052ResponseEnvelope().getcAMT052ResponseDocument().getBkToCstmrAcctRpt().getRpt().getAcct().getAcctId().getAcctIdOthr().getId()).append("\n")
        .append("    Acct-Ccy:  "  + response.getcAMT052ResponseEnvelope().getcAMT052ResponseDocument().getBkToCstmrAcctRpt().getRpt().getAcct().getCcy()).append("\n")
        .append("    Bal-Amt:   "  + response.getcAMT052ResponseEnvelope().getcAMT052ResponseDocument().getBkToCstmrAcctRpt().getRpt().getBal().getAmt().getAmt()).append("\n")
        .append("    Bal-Ccy:   "  + response.getcAMT052ResponseEnvelope().getcAMT052ResponseDocument().getBkToCstmrAcctRpt().getRpt().getBal().getAmt().getCcy()).append("\n")
        .append("    CdtDbtInd: "  + response.getcAMT052ResponseEnvelope().getcAMT052ResponseDocument().getBkToCstmrAcctRpt().getRpt().getBal().getCdtDbtInd()).append("\n")
        .append("    Bal-DtTm:  "  + response.getcAMT052ResponseEnvelope().getcAMT052ResponseDocument().getBkToCstmrAcctRpt().getRpt().getBal().getDt().getDtTm()).append("\n")
        .append("    Bal-Cd:    "  + response.getcAMT052ResponseEnvelope().getcAMT052ResponseDocument().getBkToCstmrAcctRpt().getRpt().getBal().getTp().getCdOrPrtry().getCd()).append("\n")
        .append("    Id:        "  + response.getcAMT052ResponseEnvelope().getcAMT052ResponseDocument().getBkToCstmrAcctRpt().getRpt().getId()).append("\n")

        .append("    Bal-RptId: "  + response.getcAMT052ResponseEnvelope().getcAMT052ResponseDocument().getBkToCstmrAcctRpt().getRpt().getRptPgntn().getPgNb()).append("\n")
        .append("    LastPgInd: "  + response.getcAMT052ResponseEnvelope().getcAMT052ResponseDocument().getBkToCstmrAcctRpt().getRpt().getRptPgntn().isLastPgInd()).append("\n")
        .append("********************************************").append("\n")
        .append("********************************************").append("\n");
            
        }
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
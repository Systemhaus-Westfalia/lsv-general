package org.shw.lsv.ebanking.bac.sv.test.pago.response;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.shw.lsv.ebanking.bac.sv.handling.JsonProcessor;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationException;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.pain001.response.PAIN001ResponseEvtNtfn;
import org.shw.lsv.ebanking.bac.sv.pain001.response.PAIN001ResponseEvtNtfnDocument;
import org.shw.lsv.ebanking.bac.sv.pain001.response.PAIN001ResponseEvtNtfnEnvelope;

public class PAIN001DeSerializationSystemEventNotificationTestWithFiles {
    private static final String CLASS_NAME = PAIN001DeSerializationSystemEventNotificationTestWithFiles.class.getSimpleName();

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
        //collector.setPrintImmediately(true); // Uncomment to see errors as they occur during deserialization

        // 2. Inject collector into processor
        JsonProcessor processor = new JsonProcessor(collector);

        // 3. Execute deserialization
        try {
            System.out.println("Starting PAIN001 deserialization test...");
            PAIN001ResponseEvtNtfn response = processor.deserialize(testJson, PAIN001ResponseEvtNtfn.class);

            // 4. Check for non-fatal warnings
            if (collector.hasErrors()) {
                String errorContent = "\nPAIN001 Deserialization succeeded with warnings/errors:\n" + collector.getAllErrors();
                System.err.println(errorContent);
                writeToFile(errorFilePath, errorContent);
            } else {
                System.out.println("PAIN001 Deserialization completed cleanly");
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

    /**
     * Collects the response summary into a StringBuffer.
     * This method is adapted from the original printResponseSummary to append to a buffer.
     * @param response The deserialized PAIN001ResponseEvtNtfn object.
     * @param summary The StringBuffer to append the summary to.
     */
    private static void collectResponseSummary(PAIN001ResponseEvtNtfn response, StringBuffer summary) {
        PAIN001ResponseEvtNtfnEnvelope envelope = response.getPain001ResponseEvtNtfnFile().getPain001ResponseEnvelope();
        summary.append("PAIN001 Envelope present: ").append(envelope != null).append("\n");
        if (envelope != null) {
            PAIN001ResponseEvtNtfnDocument document = envelope.getpAIN001ResponseEvtNtfnDocument();
            summary.append("PAIN001 Document present: ")
                   .append(document != null)
                   .append("\n");
            if (document != null) {
                summary.append("********************************************").append("\n");
                summary.append("********************************************").append("\n");
                summary.append("Ergebinisse:").append("\n");
                summary.append("*** AppHdr ***").append("\n");
                summary.append("    Fr-BICFI : ").append(envelope.getAppHdr().getFr().getfIId().getFinInstnId().getbICFI()).append("\n");
                summary.append("    BizMshIdr: ").append(envelope.getAppHdr().getBizMsgIdr()).append("\n");
                summary.append("    BizSvc:    ").append(envelope.getAppHdr().getBizSvc()).append("\n");
                summary.append("    CreDt:     ").append(envelope.getAppHdr().getCreDt()).append("\n");
                summary.append("    MsgDefIdr: ").append(envelope.getAppHdr().getMsgDefIdr()).append("\n");
                summary.append("********************************************").append("\n");
                summary.append("*** PAIN001 (System Event Notification) Response Document ***").append("\n");
                summary.append("********************************************").append("\n");
                summary.append("SysEvtNtfctn present: ")
                       .append(document.getSysEvtNtfctn() != null)
                       .append("\n");
                if (document.getSysEvtNtfctn() != null) {
                    summary.append("EvtInf present: ").append("\n");
                    summary.append("Erhaltene Variablen: ")
                           .append(document.getSysEvtNtfctn().getEvtInf() != null)
                           .append("\n");
                    if (document.getSysEvtNtfctn().getEvtInf() != null) {
                        summary.append("    EvtCd  : ")
                               .append(document.getSysEvtNtfctn().getEvtInf().getEvtCd())
                               .append("\n");
                        summary.append("    EvtDesc: ")
                               .append(document.getSysEvtNtfctn().getEvtInf().getEvtDesc())
                               .append("\n");
                        summary.append("    EvtTm  : ")
                               .append(document.getSysEvtNtfctn().getEvtInf().getEvtTm())
                               .append("\n");
                    }
                }
            }
        }
        summary.append("********************************************").append("\n");
        summary.append("********************************************").append("\n");
    }

    /**
     * Helper method to write content to a specified file path.
     * @param filePath The path to the file.
     * @param content The string content to write.
     */
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
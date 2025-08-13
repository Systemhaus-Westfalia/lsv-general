package org.shw.lsv.ebanking.bac.sv.z_test.consulta_pago.response;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

public class TMST039DeSerializationTestWitFiles {
    private static final String CLASS_NAME = TMST039DeSerializationTestWitFiles.class.getSimpleName();

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        System.err.println(CLASS_NAME + " started at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        // Build file names
        String inputFileName  = CLASS_NAME + "_INPUT_FILE.json";
        String outputFileName = CLASS_NAME + "_OUTPUT.txt";
        String errorFileName  = String.format("%s_ERRORS.txt", CLASS_NAME);

        // Build file paths
        // Note: You may want to create a constant in EBankingConstants for "consulta_pago"
        Path baseDir        = Paths.get(EBankingConstants.TEST_BASE_DIRECTORY_PATH, 
            "consulta_pago", 
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

        // 1. Create an ObjectMapper to parse the JSON
        ObjectMapper mapper = new ObjectMapper();

        // 2. Execute parsing to a generic JsonNode tree
        try {
            System.out.println("Starting TMST039 JSON parsing test...");
            JsonNode rootNode = mapper.readTree(testJson);

            // 3. Use the parsed JsonNode and collect a summary of required fields
            StringBuffer summary = new StringBuffer();
            collectResponseSummary(rootNode, summary);

            System.err.println("\n--- Response Summary ---");
            System.err.println(summary.toString());
            System.err.println("--- End Response Summary ---\n");

            writeToFile(outputFilePath, summary.toString());
            System.out.println("TMST039 parsing completed cleanly.");

        } catch (IllegalArgumentException e) {
            String errorContent = "\nJSON validation failure: " + e.getMessage();
            System.err.println(errorContent);
            writeToFile(errorFilePath, errorContent);
        } catch (IOException e) {
            String errorContent = "\nCritical JSON parsing failure:\n" + e.getMessage();
            System.err.println(errorContent);
            writeToFile(errorFilePath, errorContent);
        }

        now = LocalDateTime.now();
        System.err.println(CLASS_NAME + " finished at: " + now.format(EBankingConstants.DATETIME_FORMATTER));
    }

    private static void collectResponseSummary(JsonNode rootNode, StringBuffer summary) {
        summary.append("********************************************\n");
        summary.append("Ergebnisse der Zahlungsabfrage:\n");
        summary.append("********************************************\n");

        // AppHdr fields
        summary.append("BizMsgIdr: ").append(getRequiredText(rootNode, "File", "Envelope", "AppHdr", "BizMsgIdr")).append("\n");
        summary.append("MsgDefIdr: ").append(getRequiredText(rootNode, "File", "Envelope", "AppHdr", "MsgDefIdr")).append("\n");
        summary.append("\n");

        // OrgnlPmtInfAndSts fields
        summary.append("Original Control Sum: ").append(getRequiredText(rootNode, "File", "Envelope", "Document", "CstmrPmtStsRpt", "OrgnlPmtInfAndSts", "OrgnlCtrlSum")).append("\n");
        summary.append("Payment Info Status: ").append(getRequiredText(rootNode, "File", "Envelope", "Document", "CstmrPmtStsRpt", "OrgnlPmtInfAndSts", "PmtInfSts")).append("\n");
        summary.append("Status Reason Info: ").append(getRequiredText(rootNode, "File", "Envelope", "Document", "CstmrPmtStsRpt", "OrgnlPmtInfAndSts", "StsRsnInf", "AddtlInf")).append("\n");
        summary.append("\n");

        // TxInfAndSts array fields
        summary.append("--- Transaction Details ---\n");
        JsonNode txInfAndStsArray = rootNode.path("File").path("Envelope").path("Document").path("CstmrPmtStsRpt").path("OrgnlPmtInfAndSts").path("TxInfAndSts");

        if (txInfAndStsArray.isMissingNode() || !txInfAndStsArray.isArray() || txInfAndStsArray.isEmpty()) {
            throw new IllegalArgumentException("Required JSON array '...TxInfAndSts' is missing, not an array, or empty.");
        }

        // Assuming we only care about the first transaction in the array
        JsonNode firstTx = txInfAndStsArray.get(0);
        summary.append("Transaction Status ID: ").append(getRequiredText(firstTx, "StsId")).append("\n");
        summary.append("Transaction Status: ").append(getRequiredText(firstTx, "TxSts")).append("\n");
        summary.append("Transaction Status Reason: ").append(getRequiredText(firstTx, "StsRsnInf", "AddtlInf")).append("\n");

        summary.append("********************************************\n");
    }

    /**
     * Navigates a JsonNode tree using a specified path and returns the text value.
     * Throws an exception if the path is invalid, or if the final node is not a non-empty text field.
     *
     * @param node The root JsonNode to start from.
     * @param path A sequence of strings representing the path to the desired field.
     * @return The non-empty string value of the target field.
     * @throws IllegalArgumentException if the path is invalid or the target field is missing/empty.
     */
    private static String getRequiredText(JsonNode node, String... path) {
        JsonNode currentNode = node;
        for (String field : path) {
            currentNode = currentNode.path(field);
            if (currentNode.isMissingNode()) {
                throw new IllegalArgumentException("Required JSON path is missing. Failed at segment '" + field + "' in path: " + String.join(".", path));
            }
        }

        if (!currentNode.isTextual() || currentNode.asText().trim().isEmpty()) {
            throw new IllegalArgumentException("Required JSON field is missing, not text, or empty: " + String.join(".", path));
        }

        return currentNode.asText();
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

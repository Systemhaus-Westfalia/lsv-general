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
        Path baseDir        = Paths.get(EBankingConstants.TEST_BASE_DIRECTORY_PATH, 
            EBankingConstants.TEST_FILES_DIRECTORY_CONSULTA_PAGO, 
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

            // 3. Check for success, rejection, or error format and collect the appropriate summary
            StringBuffer summary = new StringBuffer();
            JsonNode documentNode = rootNode.path("File").path("Envelope").path("Document");

            if (documentNode.has("CstmrPmtStsRpt")) {
                summary.append("--- Begin of TMST.039 Success Summary ---\n");
                collectSuccessSummary(rootNode, summary);
                summary.append("--- End of TMST.039 Success Summary ---\n");
            } else if (documentNode.has("admi.002.001.01")) {
                summary.append("--- Begin of Rejection Summary ---\n");
                collectRejectionSummary(documentNode, summary);
                summary.append("--- End of Rejection Summary ---\n");
            } else if (rootNode.has("httpCode")) {
                summary.append("--- Begin of HTTP Error Summary ---\n");
                collectErrorSummary(rootNode, summary);
                summary.append("--- End of HTTP Error Summary ---\n");
            } else {
                summary.append("Unknown JSON format. Neither a known success, rejection, nor an error response.\n");
                summary.append("JSON Content: \n").append(rootNode.toPrettyString());
            }

            System.err.println(summary.toString());
            writeToFile(outputFilePath, summary.toString());
            System.out.println("\nTMST039 parsing completed.");

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


    /*
        * Zu erwartetes Ergebnis:
        *TMST039 Deserialization started at: 2025-06-10 03:04:14
        Starting TMST039 deserialization test...
        TMST039 Deserialization completed cleanly

        TMST039 Deserialized object details:
        TMST039 Deserialization finished at: 2025-06-10 03:04:15
        Envelope present: true
        TMST039 Document present: true
        ********************************************
        ********************************************
        Ergebinisse:
        *** AppHdr ***
            Fr-BICFI : BAMCSVSS
            To-BICFI : BAMCSVSS
            BizMshIdr: RespConsPago-ADClientName/(CuentaNr
            BizSvc:    swift.cbprplus.01
            CreDt:     2025-06-10T17:27:10-06:00
            MsgDefIdr: TSMT.039.001.03
        ********************************************
        *** TMST039 Response Document            ***
        ********************************************
        xmlns: urn:iso:std:iso:20022:tech:xsd:tsmt.039.001.03
        StsRptRsp present: true
        ReqId present: true
            Id      : PYMT-0001
            CreDtTm : 2025-06-10T17:26:49-06:00
        NttiesRptd present: true
            BIC     : BAMCSVSS
        Sts present: true
            Cd      : ACCP
            Rsn     : Payment found and accepted
        ********************************************
        *******************************************
    */
    private static void collectSuccessSummary(JsonNode rootNode, StringBuffer summary) {
        summary.append("********************************************\n");
        summary.append("Ergebnisse der Zahlungsabfrage (TMST.039):\n");
        summary.append("********************************************\n");

        // AppHdr fields
        summary.append("Fr-BICFI: ").append(getRequiredText(rootNode, "File", "Envelope", "AppHdr", "Fr", "FIId", "FinInstnId", "BICFI")).append("\n");
        summary.append("To-BICFI: ").append(getRequiredText(rootNode, "File", "Envelope", "AppHdr", "To", "FIId", "FinInstnId", "BICFI")).append("\n");
        summary.append("BizMsgIdr: ").append(getRequiredText(rootNode, "File", "Envelope", "AppHdr", "BizMsgIdr")).append("\n");
        summary.append("MsgDefIdr: ").append(getRequiredText(rootNode, "File", "Envelope", "AppHdr", "MsgDefIdr")).append("\n");
        summary.append("BizSvc: ").append(getRequiredText(rootNode, "File", "Envelope", "AppHdr", "BizSvc")).append("\n");
        summary.append("CreDt: ").append(getRequiredText(rootNode, "File", "Envelope", "AppHdr", "CreDt")).append("\n");
        summary.append("\n");

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

    private static void collectRejectionSummary(JsonNode documentNode, StringBuffer summary) {
        summary.append("********************************************\n");
        summary.append("Rejection Message Received:\n");
        summary.append("********************************************\n");

        summary.append("Reason Code: ").append(getRequiredText(documentNode, "admi.002.001.01", "Rsn", "RjctgPtyRsn")).append("\n");
        summary.append("Description: ").append(getRequiredText(documentNode, "admi.002.001.01", "Rsn", "RsnDesc")).append("\n");

        summary.append("********************************************\n");
    }

    private static void collectErrorSummary(JsonNode rootNode, StringBuffer summary) {
        summary.append("********************************************\n");
        summary.append("HTTP Error Response Received:\n");
        summary.append("********************************************\n");

        summary.append("HTTP Code: ").append(getRequiredText(rootNode, "httpCode")).append("\n");
        summary.append("HTTP Message: ").append(getRequiredText(rootNode, "httpMessage")).append("\n");
        summary.append("More Information: ").append(getRequiredText(rootNode, "moreInformation")).append("\n");

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

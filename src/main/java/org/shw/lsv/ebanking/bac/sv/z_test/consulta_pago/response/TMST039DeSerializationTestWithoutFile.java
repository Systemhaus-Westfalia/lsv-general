package org.shw.lsv.ebanking.bac.sv.z_test.consulta_pago.response;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

public class TMST039DeSerializationTestWithoutFile {
    public static void main(String[] args) {
        boolean testSuccess = false; // Set to false to test the error case

        LocalDateTime now = LocalDateTime.now();
        System.err.println("TMST039 Deserialization started at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        // 1. Create an ObjectMapper to parse the JSON
        ObjectMapper mapper = new ObjectMapper();

        // 3. Prepare test JSON (could also read from file)
        String testJson = testSuccess ? createTestJsonOK() : createTestJsonError();

        // 4. Execute parsing to a generic JsonNode tree
        try {
            System.out.println("Starting TMST039 JSON parsing test...");
            JsonNode rootNode = mapper.readTree(testJson);

            // 5. Check for success, rejection, or error format and collect the appropriate summary
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
            System.out.println("\nTMST039 parsing completed.");

        } catch (IllegalArgumentException e) {
            String errorContent = "\nJSON validation failure: " + e.getMessage();
            System.err.println(errorContent);
        } catch (IOException e) {
            String errorContent = "\nCritical JSON parsing failure:\n" + e.getMessage();
            System.err.println(errorContent);
        }
    }


    /*
    * Vorsicht!!!
    * BizMsgIdr ist max 35 Zeichen lang und darf nur bestimmte Zeichen aufweisen!
    *
    * "Cd" hat meistens einen der folgenden Inhalte: 
    *   ACCP	Accepted
    *   RJCT	Rejected
    *   PDNG	Pending
    * Andere Werte: 
    *   PART	Partially Accepted
    *   RCVD	Received
    *   ACSP	Accepted Settlement in Process
    *   ACSC	Accepted Settlement Completed
    *   ACWC	Accepted with Change
    *   ACFC	Accepted with Forwarding to Clearing
    *   CANC	Cancelled
    *   TECH	Technical Error
    *   WARN	Warning
    */
    private static String createTestJsonOK() {
        // Ohne Blanks und unsichtbare Zeichen (also, wie erwartet wird):
        //7String jsonContent = "{\"Envelope\":{\"AppHdr\":{\"Fr\":{\"FIId\":{\"FinInstnId\":{\"BICFI\":\"BAMCSVSS\"}}},\"To\":{\"FIId\":{\"FinInstnId\":{\"BICFI\":\"BAMCSVSS\"}}},\"BizMsgIdr\":\"RespConsPago-ADClientName/(CuentaNr\",\"MsgDefIdr\":\"TSMT.039.001.03\",\"BizSvc\":\"swift.cbprplus.01\",\"CreDt\":\"2025-06-10T17:27:10-06:00\"},\"Document\":{\"xmlns\":\"urn:iso:std:iso:20022:tech:xsd:tsmt.039.001.03\",\"StsRptRsp\":{\"ReqId\":{\"Id\":\"PYMT-0001\",\"CreDtTm\":\"2025-06-10T17:26:49-06:00\"},\"NttiesRptd\":{\"BIC\":\"BAMCSVSS\"},\"Sts\":{\"Cd\":\"ACCP\",\"Rsn\":\"Payment found and accepted\"}}}}}";
        
        String jsonContent =
            "{\n" +
            "  \"Envelope\": {\n" +
            "    \"AppHdr\": {\n" +
            "      \"Fr\": { \"FIId\": { \"FinInstnId\": { \"BICFI\": \"BAMCSVSS\" } } },\n" +
            "      \"To\": { \"FIId\": { \"FinInstnId\": { \"BICFI\": \"BAMCSVSS\" } } },\n" +
            "      \"BizMsgIdr\": \"RespConsPago-ADClientName/(CuentaNr\",\n" +
            "      \"MsgDefIdr\": \"TSMT.039.001.03\",\n" +
            "      \"BizSvc\": \"swift.cbprplus.01\",\n" +
            "      \"CreDt\": \"2025-06-10T17:27:10-06:00\"\n" +
            "    },\n" +
            "    \"Document\": {\n" +
            "      \"xmlns\": \"urn:iso:std:iso:20022:tech:xsd:tsmt.039.001.03\",\n" +
            "      \"StsRptRsp\": {\n" +
            "        \"ReqId\": {\n" +
            "          \"Id\": \"PYMT-0001\",\n" +
            "          \"CreDtTm\": \"2025-06-10T17:26:49-06:00\"\n" +
            "        },\n" +
            "        \"NttiesRptd\": {\n" +
            "          \"BIC\": \"BAMCSVSS\"\n" +
            "        },\n" +
            "        \"Sts\": {\n" +
            "          \"Cd\": \"ACCP\",\n" +
            "          \"Rsn\": \"Payment found and accepted\"\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

        return jsonContent;
    }

    private static String createTestJsonError() {
        String jsonContent =
            "{\n" +
            "    \"httpCode\": \"400\",\n" +
            "    \"httpMessage\": \"Bad Request\",\n" +
            "    \"moreInformation\": \"Invalid JSON property value\"\n" +
            "}";
        return jsonContent;
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
        summary.append("Ergebnisse der Zahlungsabfrage:\n");
        summary.append("********************************************\n");

        // AppHdr fields
        summary.append("Fr-BICFI: ").append(getRequiredText(rootNode, "Envelope", "AppHdr", "Fr", "FIId", "FinInstnId", "BICFI")).append("\n");
        summary.append("To-BICFI: ").append(getRequiredText(rootNode, "Envelope", "AppHdr", "To", "FIId", "FinInstnId", "BICFI")).append("\n");
        summary.append("BizMsgIdr: ").append(getRequiredText(rootNode, "Envelope", "AppHdr", "BizMsgIdr")).append("\n");
        summary.append("MsgDefIdr: ").append(getRequiredText(rootNode, "Envelope", "AppHdr", "MsgDefIdr")).append("\n");
        summary.append("BizSvc: ").append(getRequiredText(rootNode, "Envelope", "AppHdr", "BizSvc")).append("\n");
        summary.append("CreDt: ").append(getRequiredText(rootNode, "Envelope", "AppHdr", "CreDt")).append("\n");
        summary.append("\n");

        // Document fields
        summary.append("xmlns: ").append(getRequiredText(rootNode, "Envelope", "Document", "xmlns")).append("\n");
        summary.append("ReqId Id: ").append(getRequiredText(rootNode, "Envelope", "Document", "StsRptRsp", "ReqId", "Id")).append("\n");
        summary.append("ReqId CreDtTm: ").append(getRequiredText(rootNode, "Envelope", "Document", "StsRptRsp", "ReqId", "CreDtTm")).append("\n");
        summary.append("NttiesRptd BIC: ").append(getRequiredText(rootNode, "Envelope", "Document", "StsRptRsp", "NttiesRptd", "BIC")).append("\n");
        summary.append("Status Cd: ").append(getRequiredText(rootNode, "Envelope", "Document", "StsRptRsp", "Sts", "Cd")).append("\n");
        summary.append("Status Rsn: ").append(getRequiredText(rootNode, "Envelope", "Document", "StsRptRsp", "Sts", "Rsn")).append("\n");

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
}
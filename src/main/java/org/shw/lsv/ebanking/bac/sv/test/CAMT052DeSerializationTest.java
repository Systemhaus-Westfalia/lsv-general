package org.shw.lsv.ebanking.bac.sv.test;

import org.shw.lsv.ebanking.bac.sv.camt052.request.CAMT052Request;
import org.shw.lsv.ebanking.bac.sv.handling.JsonProcessor;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationException;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

public class CAMT052DeSerializationTest {
    public static void main(String[] args) {
        // 1. Create collector with explicit settings
        JsonValidationExceptionCollector collector = new JsonValidationExceptionCollector();
        //collector.setPrintImmediately(true); // Print errors as they occur

        // 2. Inject collector into processor
        JsonProcessor processor = new JsonProcessor(collector);

        // 3. Prepare test JSON (could also read from file)
        String testJson = createTestJson();

        // 4. Execute deserialization
        try {
            System.out.println("Starting deserialization test...");
            CAMT052Request request = processor.deserialize(testJson, CAMT052Request.class);

            // 5. Check for non-fatal warnings
            if (collector.hasErrors()) {
                System.out.println("\nDeserialization succeeded with warnings:");
                System.out.println(collector.getAllErrors());
            } else {
                System.out.println("Deserialization completed cleanly");
            }

            // 6. Use the deserialized object
            System.out.println("\nDeserialized object details:");
            printRequestSummary(request);
            
        } catch (JsonValidationException e) {
            System.err.println("\nCritical validation failures:");
            System.err.println(e.getValidationErrors());
        }
    }

    private static String createTestJson() {
        // Example JSON that might trigger validation rules
        return "{\n" +
               "    \"Envelope\": {\n" +
               "        \"Document\": {\n" +
               "            \"Bal\": {\n" +
               "                // Add more JSON content here\n" +
               "            }\n" +
               "        }\n" +
               "    }\n" +
               "}";
        
               // For error testing, you could:
               // - Omit required fields
               // - Set invalid values
               // - Include nulls where prohibited
    }

    private static void printRequestSummary(CAMT052Request request) {
        // Implement meaningful object inspection
        System.out.println("Envelope present: " + (request.getcAMT052RequestEnvelope() != null));
        if (request.getcAMT052RequestEnvelope() != null) {
            System.out.println("Document present: " + 
                (request.getcAMT052RequestEnvelope().getcAMT052RequestDocument() != null));
            // Add more details as needed
        }
    }
}

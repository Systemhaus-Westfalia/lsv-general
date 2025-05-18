package org.shw.lsv.ebanking.bac.sv.test;

import org.shw.lsv.ebanking.bac.sv.camt052.request.CAMT052Request;
import org.shw.lsv.ebanking.bac.sv.handling.Camt052RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.CamtRequestBuilder;
import org.shw.lsv.ebanking.bac.sv.handling.JsonProcessor;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationException;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;


public class CAMT052SerializationTest {
    public static void main(String[] args) {

        // 1. Create collector with explicit settings
        JsonValidationExceptionCollector collector = new JsonValidationExceptionCollector();
        collector.setPrintImmediately(true); // Print errors as they occur

        // 2. Inject collector into processor
        JsonProcessor processor = new JsonProcessor(collector);

        // 3. Prepare test data
        CAMT052Request request = createTestRequest();
        
        try {
            // 4. Serialize to JSON with validation
            System.out.println("Starting serialization test...");
            String jsonOutput = processor.serialize(request);

            // 5. Check for non-fatal warnings (even if serialization succeeded)
            if (collector.hasErrors()) {
                System.out.println("\nSerialization succeeded with warnings:");
                System.out.println(collector.getAllErrors());
            } else {
                System.out.println("Serialization completed without errors");
            }

            System.out.println("\nGenerated JSON:");
            System.out.println(jsonOutput);


            
        } catch (JsonValidationException e) {
            // 6. Handle validation errors
            System.err.println("Serialization failed with " + e.getValidationErrors().split("\n").length + " errors:");
            System.err.println(e.getValidationErrors()); // Already includes timestamps
            
            if (collector.hasErrors()) {
                System.out.println("\nSerialization succeeded with warnings:");
                System.out.println(collector.getAllErrors());
            }
            
            // For debugging: Print the complete exception
            e.printStackTrace();
        }
    }
    
    private static CAMT052Request createTestRequest() {

        Camt052RequestParams params = new Camt052RequestParams()
            .setBicfi("BANKDEFFXXX")
            .setBizMsgIdr("MSG123")
            .setCreDt("2023-11-16T10:30:00Z");

        CAMT052Request request = CamtRequestBuilder.build(params);


        
        return request;
    }
}

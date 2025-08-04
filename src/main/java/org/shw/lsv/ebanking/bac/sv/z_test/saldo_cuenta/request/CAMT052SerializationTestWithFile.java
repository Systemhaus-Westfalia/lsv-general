package org.shw.lsv.ebanking.bac.sv.z_test.saldo_cuenta.request;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.shw.lsv.ebanking.bac.sv.camt052.request.CAMT052Request;
import org.shw.lsv.ebanking.bac.sv.handling.JsonProcessor;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationException;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestBuilder;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;


public class CAMT052SerializationTestWithFile {
    private static final String CLASS_NAME = CAMT052SerializationTestWithFile.class.getSimpleName();

    public static void main(String[] args) {
        String jsonOutput = "";

        LocalDateTime now = LocalDateTime.now();
        System.err.println("CAMT052 serialization started at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        String outputFileName = String.format("%s_OUTPUT.json", CLASS_NAME);
        String errorFileName  = String.format("%s_ERROR.txt", CLASS_NAME);

        Path outputDirPath = Paths.get(EBankingConstants.TEST_BASE_DIRECTORY_PATH, 
            EBankingConstants.TEST_FILES_DIRECTORY_SALDO_CUENTA,
            EBankingConstants.TEST_FILES_REQUEST);

        try {
            Files.createDirectories(outputDirPath);
        } catch (IOException e) {
            System.err.println("Could not create output directory: " + outputDirPath.toAbsolutePath());
            e.printStackTrace();
            return;
        }

        Path outputFilePath = outputDirPath.resolve(outputFileName);
        Path errorFilePath  = outputDirPath.resolve(errorFileName);

        // 1. Create collector for test diagnostics
        JsonValidationExceptionCollector collector = new JsonValidationExceptionCollector();
        collector.setPrintImmediately(true); // See errors as they happen

        // 2. Build test parameters
        RequestParams params = createTestParams(now);
        
        try {
            // 3. Build request with test's collector
            CAMT052Request request = RequestBuilder.build(CAMT052Request.class, params, collector);
            
            // 4. Serialization test
            JsonProcessor processor = new JsonProcessor(collector);
            jsonOutput = processor.serialize(request);

            // 5. Print Json
            System.out.println("\nGenerated JSON:");
            System.out.println(jsonOutput);

            // 6. Write JSON to file
            try (BufferedWriter writer = Files.newBufferedWriter(outputFilePath)) {
                writer.write(jsonOutput);
                System.out.println("\nSuccessfully wrote JSON to: " + outputFilePath.toAbsolutePath());
            } catch (IOException e) {
                System.err.println("\nError writing JSON to file: " + e.getMessage());
                e.printStackTrace(System.err);
            }
            
            System.out.println("CAMT052 serialization succeeded without errors.\n");
            
            now = LocalDateTime.now();
            System.err.println("CAMT052 serialization finished at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        } catch (JsonValidationException e) {
            // Print errors to console in a format consistent with deserialization tests
            System.err.println("\nCritical validation failures:");
            System.err.println(e.getValidationErrors());

            // Write error to file
            String fileErrorMessage = "CAMT052 serialization Test failed: " + e.getMessage() + "\n" + e.getValidationErrors();
            try (BufferedWriter writer = Files.newBufferedWriter(errorFilePath)) {
                writer.write(fileErrorMessage);
                System.err.println("\nSuccessfully wrote error details to: " + errorFilePath.toAbsolutePath());
            } catch (IOException ioEx) {
                System.err.println("\nError writing error details to file: " + ioEx.getMessage());
                ioEx.printStackTrace(System.err);
            }
        }
    }

    private static RequestParams createTestParams(LocalDateTime now) {
        String SALDO_MESSAGE_ID = "Saldo-ADClientName/(CuentaNr)";

        // Folgende Werte sind von BAC fest definiert
        String MSGDEFIDR   = "camt.060.001.05";
        String BIZSVC      = "swift.cbprplus.01";
        String REQDMSGNMID = "AccountBalanceReportV08";
        String XMLNS       = "urn:iso:std:iso:20022:tech:xsd:camt.060.001.05";

        String formattedTimestamp = now.atOffset(EBankingConstants.ELSALVADOR_OFFSET).format(EBankingConstants.ISO_OFFSET_DATE_TIME_FORMATTER);

        return new RequestParams()
            // AppHdr
            .setBicfiFr(      "AMERICA3PLX")                    // BIC of Company (festgelegt)
                                                                      // Official definition: The sending Bank Identifier Code.
                                                                      // "INVALIDBIC" Will trigger an error
            .setBicfiTo(      "BAMCSVSS")                     // BIC of bank (festgelegt)
                                                                      // Official definition: The receiving Bank Identifier Code.
                                                                      // "INVALIDBIC" Will trigger an error
            // BizMsgIdr is a unique message ID, assigned by the sender for tracking and reference.
            // This MAY BE echeoed in the response, but must not
            // Max length: 35
            .setBizMsgIdr(    SALDO_MESSAGE_ID + "-01")                // ID assigned by the sender; es kann der gleiche sein wie bei setMsgId()

            .setMsgDefIdr(    MSGDEFIDR)                               // The message definition identifier, indicating the type of message being sent. Bei "Consulta Saldo Request" muß =() camt.052 ist das camt.060.001.05
            .setBizSvc(       BIZSVC)                                  // The business service identifier. Hier muß == "swift.cbprplus.01"
            .setCreDt(        formattedTimestamp)                      // Es wird erwartet ein DateTime, obwohl der name ist "Dt"

            // Document
            .setXmlns(XMLNS)                                           // festgelegt bei BAC

            // Group Header
            .setMsgId(        SALDO_MESSAGE_ID + "-02")                // ID assigned by the sender; es kann der gleiche sein wie bei setBizMsgIdr()

            .setCreDtTm(      formattedTimestamp)

            // Document
            .setReqdMsgNmId(  REQDMSGNMID)                              // Specifies the type of report being requested

            .setAcctId(       "200268472")                      // Bank Account
            .setBicfiAcctOwnr("AMERICA3PLX")             // BIC of Company
                                                                       // Official definition: The BIC (SWIFT code) of the account owner’s bank (the agent).
            .setCcy(          "USD")
            ;
    }
}
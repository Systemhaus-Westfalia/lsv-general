package org.shw.lsv.ebanking.bac.sv.z_test.pago.request;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.shw.lsv.ebanking.bac.sv.handling.JsonProcessor;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationException;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestBuilder;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.pain001.request.PAIN001Request;
import org.shw.lsv.ebanking.bac.sv.z_test.util.TestDateUtils;

public class PAIN001SerializationTestWithFile {
    private static final String CLASS_NAME = PAIN001SerializationTestWithFile.class.getSimpleName();

    public static void main(String[] args) {
        String jsonOutput = "";

        LocalDateTime now = LocalDateTime.now();
        System.err.println("PAIN001 serialization started at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        String outputFileName = String.format("%s_OUTPUT.json", CLASS_NAME);
        String errorFileName  = String.format("%s_ERROR.txt", CLASS_NAME);

        Path outputDirPath = Paths.get(EBankingConstants.TEST_BASE_DIRECTORY_PATH, 
            EBankingConstants.TEST_FILES_DIRECTORY_PAGO,
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
        RequestParams params = createTestParams();
        
        try {
            // 3. Build request with test's collector
            PAIN001Request request = RequestBuilder.build(PAIN001Request.class, params, collector);
            
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
            
            System.out.println("PAIN001 serialization succeeded without errors.\n");
            
            now = LocalDateTime.now();
            System.err.println("PAIN001 serialization finished at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        } catch (JsonValidationException e) {
            System.err.println("\nCritical validation failures:");
            System.err.println(e.getValidationErrors());

            String fileErrorMessage = "PAIN001 serialization Test failed: " + e.getMessage() + "\n" + e.getValidationErrors();
            try (BufferedWriter writer = Files.newBufferedWriter(errorFilePath)) {
                writer.write(fileErrorMessage);
                System.err.println("\nSuccessfully wrote error details to: " + errorFilePath.toAbsolutePath());
            } catch (IOException ioEx) {
                System.err.println("\nError writing error details to file: " + ioEx.getMessage());
                ioEx.printStackTrace(System.err);
            }
        }
    }

    private static RequestParams createTestParams() {
        String PYMT_MESSAGE_ID  = "Pago-ADClientName/(CuentaNr)";
        String PYMT_DOCUMENT_ID = "PYMT-0001";  // Ich glaube, das ist die DocumentNr, die in der Antwort NICHT zurueckgeliefert wird.
        String currentTimestamp = TestDateUtils.getCurrentApiTimestamp();

        return new RequestParams()

        // TODO: sicher stellen, dass im Betrieb, folgender Aufruf nicht bei Payments vorkomtt!!!
        //.setAnyBIC()
        // weil es dann PmtInf->Dbtr->Id->OrgId->AnyBIC gesetzt wird, was nicht erlaubt ist.
        //
        // Weitere Felder:
        //  Nb	Reference number for the remittance document (e.g., invoice number)

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
            .setBizMsgIdr(    PYMT_MESSAGE_ID)

            .setMsgDefIdr(    "PaymentInitiationServiceV03")  // The message definition identifier, indicating the type of message being sent. 
                                                                        // Bei "Payment Request" muß ==PaymentInitiationServiceV03
            .setBizSvc(       "swift.cbprplus.01")               // The business service identifier. Hier muß == "swift.cbprplus.01"
            .setCreDt(        currentTimestamp)

            // Group Header
            .setMsgId(        PYMT_DOCUMENT_ID)                       // Unique identifier for the payment group (all transactions in this request).
            // TODO: wo wird "PmtInfId"  mit PYMT_DOCUMENT_ID gesetzt?
            .setCreDtTm(      currentTimestamp)
            .setPmtMtd(       "TRF")                           // Transfer immer. TRF oder CHK

            // Unique identifier for the payment instruction set (e.g., one set of creditor/amount details).
            // Often matches GrpHdr/MsgId (if only one payment instruction exists).
            // In production, this could be:a payment order ID (e.g., "PO-987654").
            // Helps the bank associate the payment with your internal records.
            .setPmtInfId(       PYMT_DOCUMENT_ID)

            .setNbOfTxs(      Integer.valueOf(1))                   // Normalerweise 1, weil wir immmer jeweils nur 1 Zahlung vornehmen. Number of transactions
            .setCtrlSum(      new BigDecimal("469.87"))           // Payment Amout.
            .setNameInitParty("Sistemas Aereos")                   // AD_Client.name: Name of the Initiating Party: The party that initiates the payment (the sender/customer).s

            //Document
            .setCatPurpCd(    "SUPP")                       // SUPP als Konstante erstmal. Es koennen viele andere Werte angegeben werden.
            .setReqdExctnDt(  TestDateUtils.getTodayDate())
            .setNameDebtor(   "Sistemas Aereos")           // AD_Client.name:  Name of the Debtor: The party whose account will be debited (payer).
            .setDbtrId(       "06140904181038")                // NIT vom AD_Client. Debtor Identifier: Unique ID for the debtor (payer), often a tax ID or customer number.

            // Choose one Account Number:
            //.setDbtrAcctId(   "999888666")                          // Das oder IBAN. Debtor Identifier: Unique ID for the debtor (payer), often a tax ID or customer number.
            .setIbanDbtrAcct( "CR42010200690010163989")  // IBAN from Sender. IBAN is set by each bank.

            .setBicOrBEI(     "AMERICA3PLX")                 // Identifier for the initiating party, which can be a BIC (Bank Identifier Code) or BEI (Business Entity Identifier).
            .setBicDbtr(      "BAMCSVSS")
            .setCountry(      "SV")

            // Payment Element
            .setEndToEndId(   PYMT_DOCUMENT_ID)                        // Damit es nur eine einzige ID gibt im Payment (also hier) und im Payment Consult Request.
                                                                       // Wenn es nicht funktioniert, folgende, jetzt auskommentierte Zeile verwenden:
            //.setEndToEndId(   "E2E-1234567890")             // C_Payment_ID oder aehnliches
                                                                       // End-to-end identifier for the payment, used to track the transaction throughout its lifecycle.
                                                                       // A unique reference for the transaction, assigned by the initiating party.
                                                                       // Used for tracking the payment from origin to destination.
                                                                       // Use EndToEndId to match payments with responses.

            .setInstrPrty(    "NORM")                        // Instruction Priority, e.g. "NORM" or "HIGH"
            .setCcy(          "USD")
            .setInstdAmt(     "469.87")                       // Payment Amount

            // Choose one to determine the receivig Institution: BIC (Bank Identifier Code, SWIFT code) or MB:
            .setBic(          "BAMCSVSS")                          // BIC vom Receiver. Entweder BIC oder MmbId
            //.setMmbId     (     "102")                               // MmbId (Clearing System Member Identifier):
                                                                       // An identifier assigned by a local clearing system (such as a national payment network). Entweder BIC oder MmbId

            .setNameCreditor( "Nombre cliente destino")   // Name of receiver.
            .setCdtrId(       "987654321")                      // TaxID of receiver.

            // Choose one Account Number:
            //.setCdtrAcctId(   "112233445")                           // AccId oder IBAN. Creditor Identifier.
            .setIbanCdtrAcct( "CR42010200690010112233")   // IBAN from Receiver. IBAN is set by each bank.

            .setCdtrAcctCd(   "CACC")                       // Andere Werte, nach copilot: CACC: Current account, SVGS: Savings account, COMM: Commission account, TRAN: Transit account, etc.
            .setPymtPurpose(  "Motivo del pago es el siguiente...")  // TODO: ermitteln, ob Purpose Muss-Feld ist, oder nicht
            .setRmtncInf(     "Referencia a factura numero NV-112")
            ;
    }
}
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
import org.shw.lsv.ebanking.bac.sv.z_test.util.TestRequestParamsFactory;

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
        RequestParams params = TestRequestParamsFactory.createPain001Params_Domestico();
        
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
        // Folgende Parameter müssen Werte enthalten, damit Serialisierung klappt:
        String BIC_SISTEMAS_AEREOS   = "AMERICA3PLX"; // BIC of Company
        String BIC_BAC_EL_SALVADOR   = "BAMCSVSS"; // BIC of BAC EL Salvador
        String PYMT_MESSAGE_ID       = "Pago-ADClientName/(CuentaNr)";
        String PYMT_DOCUMENT_ID      = "PYMT-0001";  // Ich glaube, das ist die DocumentNr, die in der Antwort NICHT zurueckgeliefert wird.
        String MSGDEFIDR             = "PaymentInitiationServiceV03";
        String BIZSVC                = "swift.cbprplus.01";
        String CURRENCY              = "USD";
        String BAC_ACCOUNT_ID        = "200268472"; // Bank Account ID
        String PAYMENT_METHOD        = "TRF"; // Transfer immer. TRF oder CHK
        String SENDER_NAME           = "Sistemas Aereos"; // AD_Client.name: Name of the Initiating Party: The party that initiates the payment (the sender).
        String SENDER_TAX_ID         = "06140904181038"; // NIT vom AD_Client. Debtor Identifier: Unique ID for the debtor (payer), often a tax ID or customer number.
        String SENDER_IBAN           = "CR4201020069001010163989"; // IBAN from Sender. IBAN is set by each bank.
        String RECEIVER_NAME         = "Nombre cliente destino"; // Name of receiver.
        String RECEIVER_TAX_ID       = "987654321"; // TaxID of receiver
        String INSTRUCTION_PRIORITY  = "NORM"; // Instruction Priority, e.g. "NORM" or "HIGH"
        String RECEIVER_IBAN         = "CR42010200690010112233"; // IBAN from Receiver. IBAN is set by each bank.
        String COUNTRY               = "SV"; // Country code
        String CAT_PURPOSE_CD        = "SUPP"; // SUPP als Konstante erstmal. Es koennen viele andere Werte angegeben werden.
        String CRDTR_ACCT_CD         = "CACC"; // Andere Werte, nach copilot: CACC: Current account, SVGS: Savings account, COMM: Commission account, TRAN: Transit account, etc.
        String PAYMENT_PURPOSE       = "Motivo del pago es el siguiente..."; // TODO: ermitteln, ob Purpose Muss-Feld ist, oder nicht
        String RMNNC_INF             = "Referencia a factura numero NV-112"; // Reference to invoice number

        String currentTimestamp      = TestDateUtils.getCurrentApiTimestamp();
        String currentDate           = TestDateUtils.getTodayDate();
        Integer numberOfTransactions = Integer.valueOf(1); // Number of transactions

        String PAYMENT_AMOUNT        = "469.87"; // Payment Amount
        BigDecimal paymentAmount     = new BigDecimal(PAYMENT_AMOUNT);


        return new RequestParams()

            // AppHdr
            .setBicfiFr(      BIC_SISTEMAS_AEREOS)                    // BIC of Company (festgelegt)
                                                                      // Official definition: The sending Bank Identifier Code.
                                                                      // "INVALIDBIC" Will trigger an error
            .setBicfiTo(      BIC_BAC_EL_SALVADOR)                     // BIC of bank (festgelegt)
                                                                      // Official definition: The receiving Bank Identifier Code.
                                                                      // "INVALIDBIC" Will trigger an error
            // BizMsgIdr is a unique message ID, assigned by the sender for tracking and reference.
            // This MAY BE echeoed in the response, but must not
            // Max length: 35
            .setBizMsgIdr(    PYMT_MESSAGE_ID)

            .setMsgDefIdr(    MSGDEFIDR) // Muss so sein.
            .setBizSvc(       BIZSVC)              // The business service identifier. Hier muß == "swift.cbprplus.01"
            .setCreDt(        currentTimestamp)

            // Group Header
            .setMsgId(        PYMT_DOCUMENT_ID)                       // Unique identifier for the payment group (all transactions in this request).
            // TODO: wo wird "PmtInfId"  mit PYMT_DOCUMENT_ID gesetzt?
            .setCreDtTm(      currentTimestamp)
            .setPmtMtd(       PAYMENT_METHOD)                           // Transfer immer. TRF oder CHK

            // Unique identifier for the payment instruction set (e.g., one set of creditor/amount details).
            // Often matches GrpHdr/MsgId (if only one payment instruction exists).
            // In production, this could be:a payment order ID (e.g., "PO-987654").
            // Helps the bank associate the payment with your internal records.
            .setPmtInfId(     PYMT_DOCUMENT_ID)

            .setNbOfTxs(      numberOfTransactions)                   // Normalerweise 1, weil wir immmer jeweils nur 1 Zahlung vornehmen. Number of transactions
            .setCtrlSum(      paymentAmount)           // Payment Amout.
            .setCcy(          CURRENCY)
            .setNameInitParty(SENDER_NAME)                   // AD_Client.name: Name of the Initiating Party: The party that initiates the payment (the sender/customer).s

            //Document
            .setCatPurpCd(    CAT_PURPOSE_CD)                       // SUPP als Konstante erstmal. Es koennen viele andere Werte angegeben werden.
            .setReqdExctnDt(  currentDate)
            .setNameDebtor(   SENDER_NAME)           // AD_Client.name:  Name of the Debtor: The party whose account will be debited (payer).
            .setDbtrId(       SENDER_TAX_ID)                // NIT vom AD_Client. Debtor Identifier: Unique ID for the debtor (payer), often a tax ID or customer number.

            // Choose one Account Number:
            //.setDbtrAcctId(   BAC_ACCOUNT_ID)                       // Das oder IBAN. Debtor Identifier: Unique ID for the debtor (payer), often a tax ID or customer number.
            .setIbanDbtrAcct( SENDER_IBAN)  // IBAN from Sender. IBAN is set by each bank.

            .setBicOrBEI(     BIC_SISTEMAS_AEREOS)                 // Identifier for the initiating party, which can be a BIC (Bank Identifier Code) or BEI (Business Entity Identifier).
            .setBicDbtr(      BIC_SISTEMAS_AEREOS)
            .setDbtrAgtCountry(      COUNTRY)

            // Payment Element
            .setEndToEndId(   PYMT_DOCUMENT_ID)                        // Damit es nur eine einzige ID gibt im Payment (also hier) und im Payment Consult Request.
                                                                       // Wenn es nicht funktioniert, folgende, jetzt auskommentierte Zeile verwenden:
            //.setEndToEndId(   "E2E-1234567890")             // C_Payment_ID oder aehnliches
                                                                       // End-to-end identifier for the payment, used to track the transaction throughout its lifecycle.
                                                                       // A unique reference for the transaction, assigned by the initiating party.
                                                                       // Used for tracking the payment from origin to destination.
                                                                       // Use EndToEndId to match payments with responses.

            .setInstrPrty(    INSTRUCTION_PRIORITY)                        // Instruction Priority, e.g. "NORM" or "HIGH"
            .setCcy(          CURRENCY)
            .setInstdAmt(     PAYMENT_AMOUNT)                       // Payment Amount

            // Choose one to determine the receivig Institution: BIC (Bank Identifier Code, SWIFT code) or MB:
            .setBic(          BIC_BAC_EL_SALVADOR)                          // BIC vom Receiver. Entweder BIC oder MmbId
            //.setMmbId     (     "102")                               // MmbId (Clearing System Member Identifier):
                                                                       // An identifier assigned by a local clearing system (such as a national payment network). Entweder BIC oder MmbId

            .setNameCreditor( RECEIVER_NAME)   // Name of receiver.
            .setCdtrId(       RECEIVER_TAX_ID)                      // TaxID of receiver.

            // Choose one Account Number:
            //.setCdtrAcctId(   "112233445")                           // AccId oder IBAN. Creditor Identifier.
            .setIbanCdtrAcct( RECEIVER_IBAN)   // IBAN from Receiver. IBAN is set by each bank.

            .setCdtrAcctCd(   CRDTR_ACCT_CD)                       // Andere Werte, nach copilot: CACC: Current account, SVGS: Savings account, COMM: Commission account, TRAN: Transit account, etc.
            .setPymtPurpose(  PAYMENT_PURPOSE)  // TODO: ermitteln, ob Purpose Muss-Feld ist, oder nicht
            .setRmtncInf(     RMNNC_INF)
            ;
    }
}
package org.shw.lsv.ebanking.bac.sv.test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.pain001.request.PAIN001Request;
import org.shw.lsv.ebanking.bac.sv.handling.RequestBuilder;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.handling.JsonProcessor;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationException;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

public class PAIN001SerializationTest {
    public static void main(String[] args) {
        String jsonOutput = "";

        LocalDateTime now = LocalDateTime.now();
        System.err.println("PAIN001 serialization started at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        // 1. Create collector for test diagnostics
        JsonValidationExceptionCollector collector = new JsonValidationExceptionCollector();
        collector.setPrintImmediately(true); // See errors as they happen

        // 2. Build test parameters
        RequestParams params = createTestParams();
        
        try {
            // 3. Build request with test's collector
            //PAIN001Request request = RequestBuilder.build(params, collector);  // Deprecated. Kann spaeter geloescht werden
            PAIN001Request request = RequestBuilder.build(PAIN001Request.class, params, collector);
            
            // 4. Serialization test
            JsonProcessor processor = new JsonProcessor(collector);
            jsonOutput = processor.serialize(request);

            // 5. Print Json
            System.out.println("\nGenerated JSON:");
            System.out.println(jsonOutput);
            
            System.out.println("PAIN001 serialization succeeded without errors.\n");
            
            now = LocalDateTime.now();
            System.err.println("PAIN001 serialization finished at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        } catch (JsonValidationException e) {
            System.err.println("PAIN001 serialization Test failed: " + e.getMessage());
            System.err.println(e.getValidationErrors());
            System.err.println("********************************************");
        }
    }

    private static RequestParams createTestParams() {
        return new RequestParams()

        // TODO: sicher stellen, dass im Betrieb, folgender Aufruf nicht bei Payments vorkomtt!!!
        //.setAnyBIC()
        // weil es dann PmtInf->Dbtr->Id->OrgId->AnyBIC gesetzt wird, was nicht erlaubt ist.
        //
        // Weitere Felder:
        //  Nb	Reference number for the remittance document (e.g., invoice number)

            // AppHdr
            .setBicfiFr(      "DUMMYMASTER")                    // The sending Bank Identifier Code (festgelegt). "INVALIDBIC" Will trigger an error
            .setBicfiTo(      "BAMCSVSS")                     // The receiving Bank Identifier Code (festgelegt)
            .setBizMsgIdr(    "DummySaldoCta1")             // BizMsgIdr is a unique message ID, assigned by the sender for tracking and reference. 
            .setMsgDefIdr(    "PAIN.001.001.03")            // The message definition identifier, indicating the type of message being sent. Bei "Payment Request" muß =PAIN.001.001.03
            .setBizSvc(       "swift.cbprplus.01")             // The business service identifier. Hier muß == "swift.cbprplus.01"
            .setCreDt(        "2025-05-16T07:56:49-06:00")

            // Group Header
            .setMsgId(        "DummySaldoCta1")
            .setCreDtTm(      "2025-05-16T07:56:49-06:00")
            .setPmtMtd(       "TRF")                           // Transfer immer. TRF oder CHK
            .setNbOfTxs(      Integer.valueOf(3))                   // Normalerweise 1, weil wir immmer jeweils nur 1 Zahlung vornehmen. Number of transactions
            .setCtrlSum(      new BigDecimal("469.87"))           // Payment Amout.
            .setNameInitParty("Sistemas Aereos")                   // AD_Client.name: Name of the Initiating Party: The party that initiates the payment (the sender/customer).s

            //Document
            .setCatPurpCd(    "SUPP")                       // SUPP als Konstante erstmal. Es koennen viele andere Werte angegeben werden.
            .setReqdExctnDt(  "2023-06-27")
            .setNameDebtor(   "Sistemas Aereos")           // AD_Client.name:  Name of the Debtor: The party whose account will be debited (payer).
            .setDbtrId(       "06140904181038")                // NIT vom AD_Client. Debtor Identifier: Unique ID for the debtor (payer), often a tax ID or customer number.

            // Choose one Account Number:
            //.setDbtrAcctId(   "999888666")                          // Das oder IBAN. Debtor Identifier: Unique ID for the debtor (payer), often a tax ID or customer number.
            .setIbanDbtrAcct( "CR42010200690010163989")  // IBAN from Sender. IBAN is set by each bank.

            .setBicOrBEI(     "BSNJCRSJXXX")                 // Identifier for the initiating party, which can be a BIC (Bank Identifier Code) or BEI (Business Entity Identifier).
            .setBicDbtr(      "BAMCSVSS")
            .setCountry(      "SV")

            // Payment Element
            .setEndToEndId(   "E2E-1234567890")             // C_Payment_ID oder aehnliches
                                                                       // End-to-end identifier for the payment, used to track the transaction throughout its lifecycle.
                                                                       // A unique reference for the transaction, assigned by the initiating party.
                                                                       // Used for tracking the payment from origin to destination.

            .setInstrPrty(    "NORM")                                  // Instruction Priority, e.g. "NORM" or "HIGH"
            .setCcy(          "USD")
            .setInstdAmt(     "469.87")                                 // Payment Amount

            // Choose one to determine the receivig Institution: BIC (Bank Identifier Code, SWIFT code) or MB:
            .setBic(          "BSNJCRSJXXX")                                 // BIC vom Receiver. Entweder BIC oder MmbId
            //.setMmbId     (     "102")                                         // MmbId (Clearing System Member Identifier):
                                                                                 // An identifier assigned by a local clearing system (such as a national payment network). Entweder BIC oder MmbId

            .setNameCreditor( "Nombre cliente destino")             // Name of receiver.
            .setCdtrId(       "987654321")                                // TaxID of receiver.

            // Choose one Account Number:
            //.setCdtrAcctId(   "112233445")                                     // AccId oder IBAN. Creditor Identifier.
            .setIbanCdtrAcct( "CR42010200690010112233")             // IBAN from Receiver. IBAN is set by each bank.

            .setCdtrAcctCd(   "CACC")                                 // Andere Werte, nach copilot: CACC: Current account, SVGS: Savings account, COMM: Commission account, TRAN: Transit account, etc.
            .setPymtPurpose(  "Motivo del pago es el siguiente...")  // TODO: ermitteln, ob Purpose Muss-Feld ist, oder nicht
            .setRmtncInf(     "Referencia a factura numero NV-112")
            ;
    }

}

package org.shw.lsv.ebanking.bac.sv.z_test.util;

import java.math.BigDecimal;

import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

/**
 * Factory class for creating standardized RequestParams for various test scenarios.
 * This centralizes test data setup and reduces code duplication in test classes.
 */
public final class TestRequestParamsFactory {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private TestRequestParamsFactory() {}

    /**
     * Creates RequestParams for a CAMT.052 (Account Balance Report) request.
     * @return A fully configured RequestParams object for a CAMT.052 test.
     */
    public static RequestParams createCamt052Params() {
        String BIC_SISTEMAS_AEREOS = "AMERICA3PLX"; // BIC of Company
        String BIC_BAC_EL_SALVADOR = "BAMCSVSS"; // BIC of BAC EL Salvador
        String SALDO_MESSAGE_ID    = "Saldo-ADClientName/(CuentaNr)";
        String MSGDEFIDR           = "camt.060.001.05";
        String BIZSVC              = "swift.cbprplus.01";
        String XMLNS               = "urn:iso:std:iso:20022:tech:xsd:camt.060.001.05";
        String REQDMSGNMID         = "AccountBalanceReportV08";
        String BAC_ACCOUNT_ID      = "200268472"; // Bank Account ID
        String CURRENCY            = "USD";

        String currentTimestamp    = TestDateUtils.getCurrentApiTimestamp();

        return new RequestParams()
            .setBicfiFr(       BIC_SISTEMAS_AEREOS)
            .setBicfiTo(       BIC_BAC_EL_SALVADOR)
            .setBizMsgIdr(     SALDO_MESSAGE_ID)
            .setMsgDefIdr(     MSGDEFIDR)
            .setBizSvc(        BIZSVC)
            .setCreDt(         currentTimestamp)
            .setXmlns(         XMLNS)
            .setMsgId(         SALDO_MESSAGE_ID)
            .setCreDtTm(       currentTimestamp)
            .setReqdMsgNmId(   REQDMSGNMID)
            .setAcctId(        BAC_ACCOUNT_ID)
            .setBicfiAcctOwnr( BIC_SISTEMAS_AEREOS)
            .setCcy(           CURRENCY);
    }

    /**
     * Creates RequestParams for a PAIN.001 (Payment Initiation) local account request.
     * @return A fully configured RequestParams object for a PAIN.001 test.
     */
    public static RequestParams createPain001Params_Domestico() {
        String BIC_SISTEMAS_AEREOS   = "AMERICA3PLX";
        String BIC_BAC_EL_SALVADOR   = "BAMCSVSS";
        //String PYMT_MESSAGE_ID       = "Pago-ADClientName/(CuentaNr)";
        String PYMT_DOCUMENT_ID      = "PYMT-DOMESTICO-" + TestDateUtils.getTodayDate() + "-" + // Generate a unique ID for testing purposes by appending a random 5-digit number.
                                        java.util.concurrent.ThreadLocalRandom.current().nextInt(10000, 100000);
        String MSGDEFIDR             = "PaymentInitiationServiceV03";
        String BIZSVC                = "swift.cbprplus.01";
        String CURRENCY              = "USD";
        String FININSTNID_MMBID      = "001";   // Código local de banco destino (Indica si es una transferencia hacia una cuenta BAC o a otro banco (ACH/SINPE)), los valores esperados para ambiente de pruebas son:
                                                // ACH/SINPE (Panamá: 1588, Costa Rica: 151, Nicaragua: 001, Honduras: 001, El Salvador: 001, Guatemala: 101) (Este es solo un ejemplo, solicitar a su ejecutivo la lista completa de códigos de banco ACH, de ser necesario)
                                                // ENTRE CUENTAS BAC (Panamá: 1384, Costa Rica: 102, Nicaragua: 007, Honduras: 024, El Salvador: 025, Guatemala: 042)

        String BAC_ACCOUNT_ID        = "200268472"; // Bank Account ID
        String PAYMENT_METHOD        = "TRF";
        String SENDER_NAME           = "Sistemas Aereos";
        //String SENDER_IBAN           = "CR4201020069001010163989";
        String RECEIVER_NAME         = "Nombre del proveedor";
        String CODIGO_PROVEEDOR      = "Codigo del Proveedor";
        String INSTRUCTION_PRIORITY  = "NORM";
        String RECEIVER_ACCOUNT      = "999888777";
        String DBTRAGT_COUNTRY       = "SV";
        String CAT_PURPOSE_CD        = "SUPP";
        //String CRDTR_ACCT_CD         = "CACC";
        //String PAYMENT_PURPOSE       = "Motivo del pago es el siguiente...";
        //String RMNNC_INF             = "Referencia a factura numero NV-112";
        String PAYMENT_REFERENCE     = "06140904181038";  // Max 35 Zeichen

        String currentTimestamp      = TestDateUtils.getCurrentApiTimestamp();
        String currentDate           = TestDateUtils.getTodayDate();
        Integer numberOfTransactions = 1;

        String PAYMENT_AMOUNT        = "22.44";
        BigDecimal paymentAmount     = new BigDecimal(PAYMENT_AMOUNT);

        return new RequestParams()
            .setBicfiFr(       BIC_SISTEMAS_AEREOS)
            .setBicfiTo(       BIC_BAC_EL_SALVADOR)
            .setBizMsgIdr(     PYMT_DOCUMENT_ID)
            .setMsgDefIdr(     MSGDEFIDR)
            .setBizSvc(        BIZSVC)
            .setCreDt(         currentTimestamp)
            .setMsgId(         PYMT_DOCUMENT_ID)
            .setCreDtTm(       currentTimestamp)
            .setPmtMtd(        PAYMENT_METHOD)
            .setPmtInfId(      PYMT_DOCUMENT_ID)
            .setNbOfTxs(       numberOfTransactions)
            .setCtrlSum(       paymentAmount)
            .setCcy(           CURRENCY)
            .setNameInitParty( RECEIVER_NAME)
            .setCatPurpCd(     CAT_PURPOSE_CD)
            .setReqdExctnDt(   currentDate)
            .setNameDebtor(    SENDER_NAME)

            // Choose one Account Number:
            .setDbtrAcctId(    BAC_ACCOUNT_ID)  // Das oder IBAN. Debtor Identifier: Unique ID for the debtor (payer), often a tax ID or customer number.
            //.setIbanDbtrAcct(  SENDER_IBAN)

            .setBicOrBEI(      BIC_SISTEMAS_AEREOS)
            .setBicDbtr(       BIC_BAC_EL_SALVADOR)
            .setDbtrAgtCountry(   DBTRAGT_COUNTRY)
            .setEndToEndId(    PYMT_DOCUMENT_ID)
            .setInstrPrty(     INSTRUCTION_PRIORITY)
            .setInstdAmt(      PAYMENT_AMOUNT)
            .setMmbId(         FININSTNID_MMBID)
            .setNameCreditor(  RECEIVER_NAME)
            .setCdtrId(        CODIGO_PROVEEDOR)
            .setCdtrAcctId(    RECEIVER_ACCOUNT)
            //.setCdtrAcctCd(    CRDTR_ACCT_CD)
            //.setPymtPurpose(   PAYMENT_PURPOSE)
            //.setRmtncInf(      RMNNC_INF)
            .setRfrdDocInfNb(  PAYMENT_REFERENCE);
    }

    /**
     * Creates RequestParams for a PAIN.001 (Payment Initiation) international account request.
     * @return A fully configured RequestParams object for a PAIN.001 test.
     */
    public static RequestParams createPain001Params_International() {
        String BIC_SISTEMAS_AEREOS   = "AMERICA3PLX";
        String BIC_BAC_EL_SALVADOR   = "BAMCSVSS";
        String PYMT_DOCUMENT_ID      = "PYMT-INTERNACIONAL-" + TestDateUtils.getTodayDate() + "-" + // Generate a unique ID for testing purposes by appending a random 5-digit number.
                                        java.util.concurrent.ThreadLocalRandom.current().nextInt(10000, 100000);
        String MSGDEFIDR             = "PaymentInitiationServiceV03";
        String BIZSVC                = "swift.cbprplus.01";
        String CURRENCY              = "USD";
        String BIC_AGENT_BANK        = "CITIUSXXXXX";
        String BAC_ACCOUNT_ID        = "200268472"; // Bank Account ID
        String PAYMENT_METHOD        = "TRF";
        String SENDER_NAME           = "Sistemas Aereos";
        String RECEIVER_NAME         = "Nombre del proveedor";
        String CODIGO_PROVEEDOR      = "Codigo del Proveedor";
        String INSTRUCTION_PRIORITY  = "NORM";
        String RECEIVER_ACCOUNT      = "999888777";
        String DBTR_AGT_COUNTRY       = "SV";
        String CAT_PURPOSE_CD        = "SUPP";
        String CDTR_COUNTRY          = "NI";
        String CDTR_CITY             = "Managua";
        String CDTR_ADDRESS          = "100 sur Mall Galeria";
        String PAYMENT_REFERENCE     = "06140904181038";  // Max 35 Zeichen
        String PAYMENT_PURPOSE       = "26.3"; // APLICA PARA EL SALVADOR ÚNICAMENTE. Código propietario, listado de códigos definidos localmente para el concepto de transferencia solicitado por el banco central de El Salvador
        String TYPE_CODE             = "SVGS"; // Este campo NO es necesario para Costa Rica. Especifica la naturaleza o el uso de la cuenta, los valores esperados son: 
                                               // SVGS: Cuenta de ahorros, CASH: Cuenta Corriente, LOAN: Cuenta para préstamos y tarjetas
        String currentTimestamp      = TestDateUtils.getCurrentApiTimestamp();
        String currentDate           = TestDateUtils.getTodayDate();
        Integer numberOfTransactions = 1;

        String PAYMENT_AMOUNT        = "32.76";
        BigDecimal paymentAmount     = new BigDecimal(PAYMENT_AMOUNT);

        return new RequestParams()
            .setBicfiFr(       BIC_SISTEMAS_AEREOS)
            .setBicfiTo(       BIC_BAC_EL_SALVADOR)
            .setBizMsgIdr(     PYMT_DOCUMENT_ID)
            .setMsgDefIdr(     MSGDEFIDR)
            .setBizSvc(        BIZSVC)
            .setCreDt(         currentTimestamp)
            .setMsgId(         PYMT_DOCUMENT_ID)
            .setCreDtTm(       currentTimestamp)
            .setPmtMtd(        PAYMENT_METHOD)
            .setPmtInfId(      PYMT_DOCUMENT_ID)
            .setNbOfTxs(       numberOfTransactions)
            .setCtrlSum(       paymentAmount)
            .setCcy(           CURRENCY)
            .setNameInitParty( RECEIVER_NAME)
            .setCatPurpCd(     CAT_PURPOSE_CD)
            .setReqdExctnDt(   currentDate)
            .setNameDebtor(    SENDER_NAME)

            // Choose one Account Number:
            .setDbtrAcctId(    BAC_ACCOUNT_ID)  // Das oder IBAN. Debtor Identifier: Unique ID for the debtor (payer), often a tax ID or customer number.
            //.setIbanDbtrAcct(  SENDER_IBAN)

            .setBicOrBEI(      BIC_SISTEMAS_AEREOS)
            .setBicDbtr(       BIC_BAC_EL_SALVADOR)
            .setDbtrAgtCountry(DBTR_AGT_COUNTRY)

            .setCdtrCountry(   CDTR_COUNTRY)
            .setCdtrCity(      CDTR_CITY)
            .setCdtrAddress(   CDTR_ADDRESS)

            .setEndToEndId(    PYMT_DOCUMENT_ID)
            .setInstrPrty(     INSTRUCTION_PRIORITY)
            .setInstdAmt(      PAYMENT_AMOUNT)
            .setBic(           BIC_AGENT_BANK)
            .setNameCreditor(  RECEIVER_NAME)
            .setCdtrId(        CODIGO_PROVEEDOR)
            .setCdtrAcctId(    RECEIVER_ACCOUNT)
            .setPymtPurpose(   PAYMENT_PURPOSE)
            .setTpCd(          TYPE_CODE)
            //.setCdtrAcctCd(    CRDTR_ACCT_CD)
            //.setRmtncInf(      RMNNC_INF)
            .setRfrdDocInfNb(  PAYMENT_REFERENCE);
    }

    /**
     * Creates RequestParams for a TSMT.038 (Status Report) request.
     * @return A fully configured RequestParams object for a TSMT.038 Status Report test.
     */
    public static RequestParams createTmst038StatusReportParams() {
        String BIC_SISTEMAS_AEREOS = "AMERICA3PLX";
        String BIC_BAC_EL_SALVADOR = "BAMCSVSS";
        String PYMT_MESSAGE_ID     = "ConsPago-ADClientName/(CuentaNr)";
        String MSGDEFIDR           = "TSMT.038.001.03";
        String BIZSVC              = "swift.cbprplus.01";
        String XMLNS               = "urn:iso:std:iso:20022:tech:xsd:tsmt.038.001.03";
        String PYMT_DOCUMENT_ID    = "PYMT-0001";

        String currentTimestamp = TestDateUtils.getCurrentApiTimestamp();

        return new RequestParams()
            .setBicfiFr(   BIC_SISTEMAS_AEREOS)
            .setBicfiTo(   BIC_BAC_EL_SALVADOR)
            .setBizMsgIdr( PYMT_MESSAGE_ID)
            .setMsgDefIdr( MSGDEFIDR)
            .setBizSvc(    BIZSVC)
            .setCreDt(     currentTimestamp)
            .setXmlns(     XMLNS)
            .setMsgId(     PYMT_DOCUMENT_ID)
            .setCreDtTm(   currentTimestamp)
            .setBic(       BIC_SISTEMAS_AEREOS);
    }

    /**
     * Creates RequestParams for a TSMT.038 (System Event Notification) request.
     * This is typically an ADMIN.004 message.
     * @return A fully configured RequestParams object for a TSMT.038 System Event Notification test.
     */
    public static RequestParams createTmst038SysEvtNtfnParams() {
        String BIC_SISTEMAS_AEREOS = "AMERICA3PLX";
        String BIC_BAC_EL_SALVADOR = "BAMCSVSS";
        String PYMT_MESSAGE_ID     = "SysEvt-ADClientName/(CuentaNr)";
        String MSGDEFIDR           = "ADMIN.004.001.02";
        String BIZSVC              = "swift.cbprplus.02";
        String EVT_CD              = "RCVD";
        String EVT_DESC            = "Solicitud recibida. Su pago se esta procesando o se procesara en la fecha indicada en el mensaje. Consulte mas tarde.";
        String currentTimestamp    = TestDateUtils.getCurrentApiTimestamp();

        return new RequestParams()
            .setBicfiFr(BIC_BAC_EL_SALVADOR)
            .setBicfiTo(BIC_SISTEMAS_AEREOS)
            .setBizMsgIdr(PYMT_MESSAGE_ID)
            .setMsgDefIdr(MSGDEFIDR)
            .setBizSvc(BIZSVC)
            .setCreDt(currentTimestamp)
            .setEvtCd(EVT_CD)
            .setEvtDesc(EVT_DESC)
            .setEvtTm(currentTimestamp);
    }

    /**
     * Creates RequestParams for a CAMT.053 (Bank to Customer Statement) request.
     * @param pageNumber The page number for pagination.
     * @return A fully configured RequestParams object for a CAMT.053 test.
     */
    public static RequestParams createCamt053Params(Integer pageNumber) {
        String BIC_SISTEMAS_AEREOS      = "AMERICA3PLX";
        String BIC_BAC_EL_SALVADOR      = "BAMCSVSS";
        String ESTADO_CUENTA_MESSAGE_ID = "EdC-ADClientName/(CuentaNr)";
        String MSGDEFIDR                = "camt.060.001.05";
        String BIZSVC                   = "swift.cbprplus.01";
        String XMLNS                    = "urn:iso:std:iso:20022:tech:xsd:camt.060.001.05";
        String BAC_ACCOUNT_ID           = "200268472";
        String CURRENCY                 = "USD";
        String TP                       = EBankingConstants.PATTERN_TP;

        String currentTimestamp         = TestDateUtils.getCurrentApiTimestamp();
        String previousMonthStartDate   = TestDateUtils.getPreviousMonthStartDate();
        String previousMonthEndDate     = TestDateUtils.getPreviousMonthEndDate();

        return new RequestParams()
            .setBicfiFr(       BIC_SISTEMAS_AEREOS)
            .setBicfiTo(       BIC_BAC_EL_SALVADOR)
            .setBizMsgIdr(     ESTADO_CUENTA_MESSAGE_ID + "-Page-" + pageNumber.toString())
            .setMsgDefIdr(     MSGDEFIDR)
            .setBizSvc(        BIZSVC)
            .setCreDt(         currentTimestamp)
            .setXmlns(         XMLNS)
            .setMsgId(         ESTADO_CUENTA_MESSAGE_ID  + "-Grp-" + pageNumber.toString())
            .setCreDtTm(       currentTimestamp)
            .setReqdMsgNmId(   ESTADO_CUENTA_MESSAGE_ID  + "-Doc-" + pageNumber.toString())
            .setAcctId(        BAC_ACCOUNT_ID)
            .setBicfiAcctOwnr( BIC_SISTEMAS_AEREOS)
            .setCcy(           CURRENCY)
            .setFrdt(          previousMonthStartDate)
            .setTodt(          previousMonthEndDate)
            .setTp(            TP)
            .setEqseq(         pageNumber.toString());
    }
}
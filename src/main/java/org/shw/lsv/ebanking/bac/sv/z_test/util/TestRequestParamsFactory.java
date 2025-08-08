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
     * Creates RequestParams for a PAIN.001 (Payment Initiation) request.
     * @return A fully configured RequestParams object for a PAIN.001 test.
     */
    public static RequestParams createPain001Params() {
        String BIC_SISTEMAS_AEREOS   = "AMERICA3PLX";
        String BIC_BAC_EL_SALVADOR   = "BAMCSVSS";
        String PYMT_MESSAGE_ID       = "Pago-ADClientName/(CuentaNr)";
        String PYMT_DOCUMENT_ID      = "PYMT-0001";
        String MSGDEFIDR             = "PaymentInitiationServiceV03";
        String BIZSVC                = "swift.cbprplus.01";
        String CURRENCY              = "USD";
        String BAC_ACCOUNT_ID        = "200268472"; // Bank Account ID
        String PAYMENT_METHOD        = "TRF";
        String SENDER_NAME           = "Sistemas Aereos";
        String SENDER_TAX_ID         = "06140904181038";
        String SENDER_IBAN           = "CR4201020069001010163989";
        String RECEIVER_NAME         = "Nombre cliente destino";
        String RECEIVER_TAX_ID       = "987654321";
        String INSTRUCTION_PRIORITY  = "NORM";
        String RECEIVER_IBAN         = "CR42010200690010112233";
        String COUNTRY               = "SV";
        String CAT_PURPOSE_CD        = "SUPP";
        String CRDTR_ACCT_CD         = "CACC";
        String PAYMENT_PURPOSE       = "Motivo del pago es el siguiente...";
        String RMNNC_INF             = "Referencia a factura numero NV-112";

        String currentTimestamp      = TestDateUtils.getCurrentApiTimestamp();
        String currentDate           = TestDateUtils.getTodayDate();
        Integer numberOfTransactions = 1;

        String PAYMENT_AMOUNT        = "469.87";
        BigDecimal paymentAmount     = new BigDecimal(PAYMENT_AMOUNT);

        return new RequestParams()
            .setBicfiFr(       BIC_SISTEMAS_AEREOS)
            .setBicfiTo(       BIC_BAC_EL_SALVADOR)
            .setBizMsgIdr(     PYMT_MESSAGE_ID)
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
            .setNameInitParty( SENDER_NAME)
            .setCatPurpCd(     CAT_PURPOSE_CD)
            .setReqdExctnDt(   currentDate)
            .setNameDebtor(    SENDER_NAME)
            .setDbtrId(        SENDER_TAX_ID)

            // Choose one Account Number:
            //.setDbtrAcctId(   BAC_ACCOUNT_ID)  // Das oder IBAN. Debtor Identifier: Unique ID for the debtor (payer), often a tax ID or customer number.
            .setIbanDbtrAcct(  SENDER_IBAN)

            .setBicOrBEI(      BIC_SISTEMAS_AEREOS)
            .setBicDbtr(       BIC_SISTEMAS_AEREOS)
            .setCountry(       COUNTRY)
            .setEndToEndId(    PYMT_DOCUMENT_ID)
            .setInstrPrty(     INSTRUCTION_PRIORITY)
            .setInstdAmt(      PAYMENT_AMOUNT)
            .setBic(           BIC_BAC_EL_SALVADOR)
            .setNameCreditor(  RECEIVER_NAME)
            .setCdtrId(        RECEIVER_TAX_ID)
            .setIbanCdtrAcct(  RECEIVER_IBAN)
            .setCdtrAcctCd(    CRDTR_ACCT_CD)
            .setPymtPurpose(   PAYMENT_PURPOSE)
            .setRmtncInf(      RMNNC_INF);
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
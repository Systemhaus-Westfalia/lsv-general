package org.shw.lsv.ebanking.bac.sv.pain001.request;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;

import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PmtInf {
    @JsonProperty("PmtInfId")
    String pmtInfId;  // Payment Information Identification

    @JsonProperty("PmtMtd")
    String pmtMtd;  // means of payment

    @JsonProperty("NbOfTxs")
    Integer nbOfTxs;  // Total number of individual transactions (credit transfers)

    @JsonProperty("CtrlSum")
    String ctrlSum;  // total sum of all individual transaction amounts (

    @JsonProperty("PmtTpInf")
    PmtTpInf pmtTpInf;

    @JsonProperty("ReqdExctnDt")
    String reqdExctnDt;  // Requested Execution Date.

    @JsonProperty("Dbtr")
    Dbtr dbtr;

    @JsonProperty("DbtrAcct")
    DbtrAcct dbtrAcct;

    @JsonProperty("DbtrAgt")
    DbtrAgt dbtrAgt;

    /*
    * Credit Transfer Transaction Information.
    * Represents a single payment instruction within a batch.
    * Contains all details for one credit transfer (amount, debtor, creditor, accounts, references, etc.).
    * There can be many CdtTrfTxInf elements in a PAIN.001 message. 
    */
    @JsonProperty("CdtTrfTxInf")
    List<PAIN001PaymentElement> paymentElements;


    public PmtInf() { }

    /**
     * @param params    the RequestParams containing the values to set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     * 
     * Constructor with parameters
     * For using the Constructor at deserialization time, it has to be of the form:
     * public PmtInf(@JsonProperty(value = "PmtInfId", required = true) String pmtInfId, ...)
     */
    public PmtInf(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            int trxCount = params.getNbOfTxs() != null ? params.getNbOfTxs() : 0;

            setPmtInfId(params.getMsgId(),     collector);
            setPmtMtd(  params.getMsgDefIdr(), collector);
            setNbOfTxs( trxCount,              collector);
            setCtrlSum( params.getCtrlSum(),   collector);

            setPmtTpInf (    new PmtTpInf(    params, collector), collector);
            setReqdExctnDt( params.getReqdExctnDt(),              collector);
            setDbtr (        new Dbtr(        params, collector), collector);
            setDbtrAcct (    new DbtrAcct(    params, collector), collector);
            setDbtrAgt (     new DbtrAgt(     params, collector), collector);

            // Payments
            paymentElements = initPayment(params, trxCount, collector);
            setPaymentElements(paymentElements, collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_PMTINF_INIT, e);
        }
    }

    private List<PAIN001PaymentElement> initPayment(RequestParams params, int n, JsonValidationExceptionCollector collector) {
        List<PAIN001PaymentElement> paymentElements = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            paymentElements.add(new PAIN001PaymentElement(params, collector));
        }
        return paymentElements;
    }


    /**
     * @return the PmtInfId
     */
    public String getPmtInfId() {
        return pmtInfId;
    }

    /**
     * @param pmtInfId the Payment Information Identification to be set<br>
     * <p>
     * It is a unique identifier for a group of payment instructions within a payment message.
     * It is assigned by the initiating party (the sender, usually the company or bank creating the payment file).
     * It helps both the sender and the receiver (bank) to reference, track, and reconcile the payment batch.
     *
     * The parameter is validated.<br>
     * "minLength" : 1, "maxLength" : 35; null not allowed.<br>
     */
    public void setPmtInfId(String pmtInfId) {
        final int MINLENGTH = 1;
        final int MAXLENGTH = 35;
        int length = (pmtInfId == null || pmtInfId.isEmpty()) ? 0 : pmtInfId.length();

        if (length < MINLENGTH || length > MAXLENGTH) {
            throw new IllegalArgumentException(
                "Wrong parameter 'pmtInfId' (" + pmtInfId + ") in setPmtInfId()"
            );
        }
        this.pmtInfId = pmtInfId;
    }

    /**
     * @param pmtInfId the PmtInfId to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     * <p>
     * Payment Information Identification to be set<br>
     * It is a unique identifier for a group of payment instructions within a payment message.
     * It is assigned by the initiating party (the sender, usually the company or bank creating the payment file).
     * It helps both the sender and the receiver (bank) to reference, track, and reconcile the payment batch.
     */
    public void setPmtInfId(String pmtInfId, JsonValidationExceptionCollector collector) {
        try {
            setPmtInfId(pmtInfId);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_WRONG_LENGTH, e);
            //throw e;
        }
    }


    /**
     * @return the PmtMtd
     */
    public String getPmtMtd() {
        return pmtMtd;
    }

    /**
     * @param pmtMtd the means of payment to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.
     * <p>
     * Specifies the means of payment that will be used to move the amount of money.<p>
     * The parameter is validated: null not allowed.<br>
     * And must equal one of the following values: [TRF, CHK]
     * -> TRF = Transaccion, CHK = Cheques"
     */
    public void setPmtMtd(String pmtMtd) {
        if ((pmtMtd == null || pmtMtd.isEmpty()) ||
            !(pmtMtd.equals(EBankingConstants.TRF) || pmtMtd.equals(EBankingConstants.CHK)
            )) {
            throw new IllegalArgumentException(
                "Wrong parameter 'pmtMtd' (" + pmtMtd + ") in setPmtMtd()"
            );
        }
        this.pmtMtd = pmtMtd;
    }

    /**
     * @param pmtMtd the means of payment to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.
     * <p>
     * Specifies the means of payment that will be used to move the amount of money.
     * Descripccion segun ficha técnica": "[TRF, CHK] -> TRF = Transacción, CHK = Cheques".
     * Example:"TRF"
     */
    public void setPmtMtd(String pmtMtd, JsonValidationExceptionCollector collector) {
        try {
            setPmtMtd(pmtMtd);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_WRONG_LENGTH, e);
            //throw e;
        }
    }

        
    /**
     * @return the NbOfTxs
     */
    public Integer getNbOfTxs() {
        return nbOfTxs;
    }

    /**
     * @param nbOfTxs the number of individual transactions to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     * <p>
     * It indicates the total number of individual transactions (credit transfers) included in the payment batch (PmtInf block).
     * <p>
     * It must match the number of CdtTrfTxInf (Credit Transfer Transaction Information) elements in the same PmtInf.
     * The parameter is validated: must be positive; null not allowed.<br>
     * Example: 3
     */
    public void setNbOfTxs(Integer nbOfTxs) {
        if (nbOfTxs == null || nbOfTxs <= 0) {
            throw new IllegalArgumentException(
                "Wrong parameter 'nbOfTxs' (" + nbOfTxs + ") in setNbOfTxs(): must be positive and not null"
            );
        }
        this.nbOfTxs = nbOfTxs;
    }

    /**
     * @param nbOfTxs the number of individual transactions to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     * <p>
     * It indicates the total number of individual transactions (credit transfers) included in the payment batch (PmtInf block).
     * <p>
     * It must match the number of CdtTrfTxInf (Credit Transfer Transaction Information) elements in the same PmtInf.
     */
    public void setNbOfTxs(Integer nbOfTxs, JsonValidationExceptionCollector collector) {
        try {
            setNbOfTxs(nbOfTxs);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_WRONG_LENGTH, e);
            //throw e;
        }
    }

    /**
     * @return the CtrlSum
     */
    public BigDecimal getCtrlSum() {
        if (ctrlSum == null) {
            return null;
        }
        try {
            return new BigDecimal(ctrlSum);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                "Wrong format for 'ctrlSum' (" + ctrlSum + ") in getCtrlSum(): not a valid amount", e
            );
        }
    }

    /**
     * @param ctrlSum the total sum of all individual transaction amounts to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     * <p>
     * It is the sum of all credit transfers within a payment batch (PmtInf block).
     * The parameter is validated: must be non-null, non-negative, and have two decimal places.<br>
     * Example: 123.45
     */
    public void setCtrlSum(BigDecimal ctrlSum) {
        if (ctrlSum == null || ctrlSum.scale() != 2 || ctrlSum.signum() < 0) {
            throw new IllegalArgumentException(
                "Wrong parameter 'ctrlSum' (" + ctrlSum + ") in setCtrlSum(): must be non-null, non-negative, and have two decimal places"
            );
        }
        this.ctrlSum = ctrlSum.toPlainString();
    }

    /**
     * @param ctrlSum the total sum of all individual transaction amounts to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     * <p>
     * It is the sum of all credit transfers within a payment batch (PmtInf block).
     */
    public void setCtrlSum(BigDecimal ctrlSum, JsonValidationExceptionCollector collector) {
        try {
            setCtrlSum(ctrlSum);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_WRONG_LENGTH, e);
            //throw e;
        }
    }

    /**
     * @return the PmtTpInf object<br>
     */
    public PmtTpInf getPmtTpInf() {
        return pmtTpInf;
    }

    /**
     * @param pmtTpInf the PmtTpInf to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setPmtTpInf(PmtTpInf pmtTpInf) {
        if (pmtTpInf == null) {
            throw new IllegalArgumentException("Wrong parameter 'pmtTpInf' in setPmtTpInf()");
        }
        this.pmtTpInf = pmtTpInf;
    }

    /**
     * @param pmtTpInf  the PmtTpInf to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setPmtTpInf(PmtTpInf pmtTpInf, JsonValidationExceptionCollector collector) {
        try {
            setPmtTpInf(pmtTpInf);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }

    /**
     * @return the ReqdExctnDt
     */
    public String getReqdExctnDt() {
        return reqdExctnDt;
    }

    /**
     * @param reqdExctnDt the Requested Execution Date to be set<br>
     * The parameter is validated: null not allowed.<br>
     * "pattern" : "^\d{4}-\d{2}-\d{2}$"<br>
     * Example: "2023-12-31"
     */
    public void setReqdExctnDt(String reqdExctnDt) {
        boolean patternOK = (reqdExctnDt != null) && 
            java.util.regex.Pattern.matches(EBankingConstants.PATTERN_DATE, reqdExctnDt);

        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'reqdExctnDt' (" + reqdExctnDt + ") in setReqdExctnDt()");
        }
        this.reqdExctnDt = reqdExctnDt;
    }

    /**
     * @param reqdExctnDt the Requested Execution Date to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setReqdExctnDt(String reqdExctnDt, JsonValidationExceptionCollector collector) {
        try {
            setReqdExctnDt(reqdExctnDt);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
            //throw e;
        }
    }


    /**
     * @return the Dbtr object<br>
     */
    public Dbtr getDbtr() {
        return dbtr;
    }

    /**
     * @param dbtr the Dbtr to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setDbtr(Dbtr dbtr) {
        if (dbtr == null) {
            throw new IllegalArgumentException("Wrong parameter 'dbtr' in setDbtr()");
        }
        this.dbtr = dbtr;
    }

    /**
     * @param dbtr the Dbtr to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setDbtr(Dbtr dbtr, JsonValidationExceptionCollector collector) {
        try {
            setDbtr(dbtr);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }


    /**
     * @return the DbtrAcct object<br>
     */
    public DbtrAcct getDbtrAcct() {
        return dbtrAcct;
    }

    /**
     * @param dbtrAcct the DbtrAcct to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setDbtrAcct(DbtrAcct dbtrAcct) {
        if (dbtrAcct == null) {
            throw new IllegalArgumentException("Wrong parameter 'dbtrAcct' in setDbtrAcct()");
        }
        this.dbtrAcct = dbtrAcct;
    }

    /**
     * @param dbtrAcct the DbtrAcct to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setDbtrAcct(DbtrAcct dbtrAcct, JsonValidationExceptionCollector collector) {
        try {
            setDbtrAcct(dbtrAcct);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }

    /**
     * @return the DbtrAgt object<br>
     */
    public DbtrAgt getDbtrAgt() {
        return dbtrAgt;
    }

    /**
     * @param dbtrAgt the DbtrAgt to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setDbtrAgt(DbtrAgt dbtrAgt) {
        if (dbtrAgt == null) {
            throw new IllegalArgumentException("Wrong parameter 'dbtrAgt' in setDbtrAgt()");
        }
        this.dbtrAgt = dbtrAgt;
    }

    /**
     * @param dbtrAgt the DbtrAgt to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setDbtrAgt(DbtrAgt dbtrAgt, JsonValidationExceptionCollector collector) {
        try {
            setDbtrAgt(dbtrAgt);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }

    /**
     * @return the <PaymentElement array <br>
     */
    public List<PAIN001PaymentElement> getPaymentElements() {
        return paymentElements;
    }

    /**
     * @param paymentElements the PaymentElement array to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setPaymentElements(List<PAIN001PaymentElement> paymentElements) {
        if (paymentElements == null) {
            throw new IllegalArgumentException("Wrong parameter 'paymentElements' in setPaymentElements()");
        }
        this.paymentElements = paymentElements;
    }

    /**
     * @param paymentElements the PaymentElement array to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setPaymentElements(List<PAIN001PaymentElement> paymentElements, JsonValidationExceptionCollector collector) {
        try {
            setPaymentElements(paymentElements);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }



}

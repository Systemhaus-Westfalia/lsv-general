    package org.shw.lsv.ebanking.bac.sv.pain001.request;

    import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
    import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
    import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

    public class PAIN001PaymentElement {

    @JsonProperty("PmtId")
    PmtId pmtId;

    @JsonProperty("PmtTpInf")
    PmtElementtPInf pmtElementtPInf;  // additional information about the type of payment being made.

    @JsonProperty("Amt")
    PmtElementtAmt pmtElementtAmt;  // Amount and currency

    @JsonProperty("CdtrAgt")
    CdtrAgt cdtrAgt;  // Creditor's agent (bank or financial institution)

    @JsonProperty("Cdtr")
    Cdtr cdtr;

    @JsonProperty("CdtrAcct")
    CdtrAcct cdtrAcct;

    @JsonProperty("RmtInf")
    RmtInf rmtInf;  // Remittance Information

    @JsonProperty("Purp")
    @JsonInclude(JsonInclude.Include.NON_NULL)  // Include Purp only if it is not null
    Purp purp;  // Purpose of the payment (optional, but often included for regulatory or informational purposes)

    public PAIN001PaymentElement() { }

       public PAIN001PaymentElement(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setPmtId(           new PmtId(           params, collector), collector);
            setPmtElementtPInf( new PmtElementtPInf( params, collector), collector);
            setPmtElementtAmt(  new PmtElementtAmt(  params, collector), collector);
            setCdtrAgt(         new CdtrAgt(         params, collector), collector);
            setCdtr(            new Cdtr(            params, collector), collector);
            setCdtrAcct(        new CdtrAcct(        params, collector), collector);
            setRmtInf(          new RmtInf(          params, collector), collector);

            if ( !(params.getPymtPurpose() == null || params.getPymtPurpose ().isEmpty()) ) {
                setPurp( new Purp(params, collector), collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_PAYMENT_ELEMENT_INIT, e);
        }
    }


    /**
     * @return the PmtId object<br>
     */
    public PmtId getPmtId() {
        return pmtId;
    }

    /**
     * @param pmtId the PmtId to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setPmtId(PmtId pmtId) {
        if (pmtId == null) {
            throw new IllegalArgumentException("Wrong parameter 'pmtId' in setPmtId()");
        }
        this.pmtId = pmtId;
    }

    /**
     * @param pmtId the PmtId to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setPmtId(PmtId pmtId, JsonValidationExceptionCollector collector) {
        try {
            setPmtId(pmtId);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }

    /**
     * @return the PmtElementtPInf object<br>
     */
    public PmtElementtPInf getPmtElementtPInf() {
        return pmtElementtPInf;
    }

    /**
     * @param pmtElementtPInf the PmtElementtPInf to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setPmtElementtPInf(PmtElementtPInf pmtElementtPInf) {
        if (pmtElementtPInf == null) {
            throw new IllegalArgumentException("Wrong parameter 'pmtElementtPInf' in setPmtElementtPInf()");
        }
        this.pmtElementtPInf = pmtElementtPInf;
    }

    /**
     * @param pmtElementtPInf the PmtElementtPInf to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setPmtElementtPInf(PmtElementtPInf pmtElementtPInf, JsonValidationExceptionCollector collector) {
        try {
            setPmtElementtPInf(pmtElementtPInf);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }

    /**
     * @return the PmtElementtAmt object<br>
     */
    public PmtElementtAmt getPmtElementtAmt() {
        return pmtElementtAmt;
    }

    /**
     * @param pmtElementtAmt the PmtElementtAmt to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setPmtElementtAmt(PmtElementtAmt pmtElementtAmt) {
        if (pmtElementtAmt == null) {
            throw new IllegalArgumentException("Wrong parameter 'pmtElementtAmt' in setPmtElementtAmt()");
        }
        this.pmtElementtAmt = pmtElementtAmt;
    }

    /**
     * @param pmtElementtAmt the PmtElementtAmt to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setPmtElementtAmt(PmtElementtAmt pmtElementtAmt, JsonValidationExceptionCollector collector) {
        try {
            setPmtElementtAmt(pmtElementtAmt);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }


    /**
     * @return the Creditor's agent (bank or financial institution) object<br>
     */
    public CdtrAgt getCdtrAgt() {
        return cdtrAgt;
    }

    /**
     * @param cdtrAgt the Creditor's agent (bank or financial institution)<br>
     * <p>
     * CdtrAgt identifies the financial institution (bank) of the creditor (the recipient of the payment).
     * It typically contains details such as the BIC (Bank Identifier Code, also known as SWIFT code) and, optionally, postal address information.
     * <p>
     * Required for routing the payment to the correct financial institution in PAIN.001 files.
     * The parameter is validated: null not allowed.<br>
     */
    public void setCdtrAgt(CdtrAgt cdtrAgt) {
        if (cdtrAgt == null) {
            throw new IllegalArgumentException("Wrong parameter 'cdtrAgt' in setCdtrAgt()");
        }
        this.cdtrAgt = cdtrAgt;
    }

    /**
     * @param cdtrAgt the Creditor's agent (bank or financial institution)<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     * <p>
     * CdtrAgt identifies the financial institution (bank) of the creditor (the recipient of the payment).
     * It typically contains details such as the BIC (Bank Identifier Code, also known as SWIFT code) and, optionally, postal address information.
     * <p>
     * Required for routing the payment to the correct financial institution in PAIN.001 files.
     *
     */
    public void setCdtrAgt(CdtrAgt cdtrAgt, JsonValidationExceptionCollector collector) {
        try {
            setCdtrAgt(cdtrAgt);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }

    /**
     * @return the Cdtr object<br>
     */
    public Cdtr getCdtr() {
        return cdtr;
    }

    /**
     * @param cdtr the Cdtr to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setCdtr(Cdtr cdtr) {
        if (cdtr == null) {
            throw new IllegalArgumentException("Wrong parameter 'cdtr' in setCdtr()");
        }
        this.cdtr = cdtr;
    }

    /**
     * @param cdtr the Cdtr to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setCdtr(Cdtr cdtr, JsonValidationExceptionCollector collector) {
        try {
            setCdtr(cdtr);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }

    /**
     * @return the CdtrAcct object<br>
     */
    public CdtrAcct getCdtrAcct() {
        return cdtrAcct;
    }

    /**
     * @param cdtrAcct the CdtrAcct to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setCdtrAcct(CdtrAcct cdtrAcct) {
        if (cdtrAcct == null) {
            throw new IllegalArgumentException("Wrong parameter 'cdtrAcct' in setCdtrAcct()");
        }
        this.cdtrAcct = cdtrAcct;
    }

    /**
     * @param cdtrAcct the CdtrAcct to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setCdtrAcct(CdtrAcct cdtrAcct, JsonValidationExceptionCollector collector) {
        try {
            setCdtrAcct(cdtrAcct);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }

    /**
     * @return the RmtInf object<br>
     */
    public RmtInf getRmtInf() {
        return rmtInf;
    }

    /**
     * @param rmtInf the RmtInf to be set<br>
     * <p>
     * RmtInf contains remittance information, which is additional information about the payment.
     * It can include details such as invoice numbers, payment references, or any other information that helps the creditor identify the purpose of the payment.
     * <p>
     * It is often used to provide context for the payment and can be important for reconciliation purposes.
     * <p>
     * The parameter is validated: null not allowed.<br>
     */
    public void setRmtInf(RmtInf rmtInf) {
        if (rmtInf == null) {
            throw new IllegalArgumentException("Wrong parameter 'rmtInf' in setRmtInf()");
        }
        this.rmtInf = rmtInf;
    }

    /**
     * @param rmtInf the RmtInf to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     * <p>
     * RmtInf contains remittance information, which is additional information about the payment.
     * It can include details such as invoice numbers, payment references, or any other information that helps the creditor identify the purpose of the payment.
     * <p>
     * It is often used to provide context for the payment and can be important for reconciliation purposes.
     */
    public void setRmtInf(RmtInf rmtInf, JsonValidationExceptionCollector collector) {
        try {
            setRmtInf(rmtInf);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }

    /**
     * @return the Purp object<br>
     */
    public Purp getPurp() {
        return purp;
    }

    /**
     * @param purp the Purp to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setPurp(Purp purp) {
        if (purp == null) {
            throw new IllegalArgumentException("Wrong parameter 'purp' in setPurp()");
        }
        this.purp = purp;
    }

    /**
     * @param purp the Purp to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setPurp(Purp purp, JsonValidationExceptionCollector collector) {
        try {
            setPurp(purp);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }


}

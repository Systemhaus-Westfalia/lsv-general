package org.shw.lsv.ebanking.bac.sv.camt052.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CAMT052ResponseDocument {

    @JsonProperty("BkToCstmrAcctRpt")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    BkToCstmrAcctRpt bkToCstmrAcctRpt;

    @JsonProperty("admi.002.001.01")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Rejection rejection;


    public CAMT052ResponseDocument() { }


    /**
    * @return the BkToCstmrAcctRpt object<br>
    */
    public BkToCstmrAcctRpt getBkToCstmrAcctRpt() {
        return bkToCstmrAcctRpt;
    }


    /**
     * @param bkToCstmrAcctRpt the BkToCstmrAcctRpt to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setBkToCstmrAcctRpt(BkToCstmrAcctRpt bkToCstmrAcctRpt) {
        if (bkToCstmrAcctRpt == null) {
            throw new IllegalArgumentException("Wrong parameter 'bkToCstmrAcctRpt' in setBkToCstmrAcctRpt()");
        }
        this.bkToCstmrAcctRpt = bkToCstmrAcctRpt;
    }

    /**
     * @param bkToCstmrAcctRpt the BkToCstmrAcctRpt to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setBkToCstmrAcctRpt(BkToCstmrAcctRpt bkToCstmrAcctRpt, JsonValidationExceptionCollector collector) {
        try {
            setBkToCstmrAcctRpt(bkToCstmrAcctRpt);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }

    /**
    * @return the Rejection object<br>
    */
    public Rejection getRejection() {
        return rejection;
    }

    /**
     * @param rejection the Rejection to be set<br>
     */
    public void setRejection(Rejection rejection) {
        this.rejection = rejection;
    }

    /**
     * @param rejection the Rejection to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setRejection(Rejection rejection, JsonValidationExceptionCollector collector) {
        try {
            setRejection(rejection);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}

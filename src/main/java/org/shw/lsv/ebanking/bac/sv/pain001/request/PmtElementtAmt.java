package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PmtElementtAmt {

    // TODO: checken, ob nicht doch hier InstdAmt unf CCY implementiert werden!!!!
    @JsonProperty("InstdAmt")
    InstdAmt instdAmt;

    public PmtElementtAmt() { }

    /**
     * @param params    the RequestParams containing the values to set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     * 
     * Constructor with parameters
     * For using the Constructor at deserialization time, it has to be of the form:
     * public PmtElementtAmt(@JsonProperty(value = "InstdAmt", required = true) InstdAmt instdAmt, ...)
     */
    public PmtElementtAmt(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setInstdAmt(new InstdAmt(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_PMTINF_INIT, e);
        }
    }

    /**
     * @return the InstdAmt object<br>
     */
    public InstdAmt getInstdAmt() {
        return instdAmt;
    }

    /**
     * @param instdAmt the amount of money to be transferred in a single payment transaction<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     * <p>
     * It is the amount that the debtor instructs the bank to transfer to the creditor.
     * The value is always accompanied by a currency code (e.g., EUR, USD, CRC).
     * <p>
     * The parameter is validated: null not allowed.<br>
     */
    public void setInstdAmt(InstdAmt instdAmt) {
        if (instdAmt == null) {
            throw new IllegalArgumentException("Wrong parameter 'instdAmt' in setInstdAmt()");
        }
        this.instdAmt = instdAmt;
    }

    /**
     * @param instdAmt the amount of money to be transferred in a single payment transaction<br>
     * <p>
     * It is the amount that the debtor instructs the bank to transfer to the creditor.
     * The value is always accompanied by a currency code (e.g., EUR, USD, CRC).
     */
    public void setInstdAmt(InstdAmt instdAmt, JsonValidationExceptionCollector collector) {
        try {
            setInstdAmt(instdAmt);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }


}

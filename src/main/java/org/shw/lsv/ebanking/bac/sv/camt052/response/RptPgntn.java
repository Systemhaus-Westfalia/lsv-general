package org.shw.lsv.ebanking.bac.sv.camt052.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RptPgntn {

    @JsonProperty(value = "PgNb", required = true)
    String pgNb;  // Page Number

    @JsonProperty(value = "LastPgInd", required = true)
    boolean lastPgInd; // Last Page Number Indicator

    public RptPgntn() { }

    /**
     * @param pgNb the PgNb to be set<br>
     * @param lastPgInd the LastPgInd to be set<br>
     * Both @pgNb and @lastPgInd are mandatory in the JSON definition.
     * 
     * Constructor with parameters
     * For using the Constructor at deserialization time, it has to be of the form:
     * public RptPgntn(@JsonProperty(value = "PgNb", required = true) String pgNb,.....)
     */
    public RptPgntn(String pgNb, String lastPgInd) {
        setPgNb(pgNb);
        setLastPgInd(lastPgInd);
    }

    public String getPgNb() {
        return pgNb;
    }

    /**
     * @param pgNb the PgNb to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setPgNb(String pgNb) {
        if (pgNb == null || pgNb.isEmpty()) {
            throw new IllegalArgumentException("Wrong parameter 'PgNb' (" + pgNb + ") in setPgNb()");
        }
        this.pgNb = pgNb;
    }

    /**
     * @param pgNb the PgNb to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setPgNb(String pgNb, JsonValidationExceptionCollector collector) {
        try {
            setPgNb(pgNb);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_EMPTY_OR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }

    public boolean isLastPgInd() {
        return lastPgInd;
    }

    /**
     * @param lastPgInd the LastPgInd to be set<br>
     * The parameter is validated: must be "true" or "false".<br>
     */
    public void setLastPgInd(String lastPgInd) {
        if (lastPgInd == null || (!lastPgInd.equalsIgnoreCase("true") && !lastPgInd.equalsIgnoreCase("false"))) {
            throw new IllegalArgumentException("Wrong parameter 'LastPgInd' (" + lastPgInd + ") in setLastPgInd()");
        }
        this.lastPgInd = Boolean.parseBoolean(lastPgInd);
    }

    /**
     * @param lastPgInd the LastPgInd to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setLastPgInd(String lastPgInd, JsonValidationExceptionCollector collector) {
        try {
            setLastPgInd(lastPgInd);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_EMPTY_OR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }
}
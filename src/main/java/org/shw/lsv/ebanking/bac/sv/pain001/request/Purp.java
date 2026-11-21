package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Purp {

    @JsonProperty("Prtry")
    String prtry;  // Proprietary

    public Purp() {}

    public Purp(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setPrtry(params.getPymtPurpose(), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_PURP_INIT, e);
        }
    }

    /**
     * @return the Prtry value<br>
     */
    public String getPrtry() {
        return prtry;
    }

    /**
     * @param prtry the Prtry value to be set.<br>
     * The parameter is validated: null not allowed.<br>
     * Pattern: "[0-9a-zA-Z/\\-?:().,'+ ]{1,35}"<br>
     * Example: "SALA"
     */
    public void setPrtry(String prtry) {
        boolean patternOK = (prtry != null && !prtry.isEmpty()) &&
            prtry.matches(EBankingConstants.PATTERN_OTHER_ID);
        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'prtry' (" + prtry + ") in setPrtry()");
        }
        this.prtry = prtry;
    }

    /**
     * @param prtry the Prtry value to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setPrtry(String prtry, JsonValidationExceptionCollector collector) {
        try {
            setPrtry(prtry);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
        }
    }
}
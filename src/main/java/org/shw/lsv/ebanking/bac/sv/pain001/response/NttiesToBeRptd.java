package org.shw.lsv.ebanking.bac.sv.pain001.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NttiesToBeRptd {

    @JsonProperty("BIC")
    String bIC;

    public NttiesToBeRptd() {}

    public NttiesToBeRptd(RequestParams params, JsonValidationExceptionCollector collector) {
        setbIC(params.getBic(), collector);
    }

    /**
     * @return the BIC (Bank Identifier Code)
     */
    public String getbIC() {
        return bIC;
    }

    /**
     * @param bIC the BIC (Bank Identifier Code).<br>
     * The parameter is validated: null not allowed.<br>
     * <p>
     * Pattern: EBankingConstants.PATTERN_BIC<br>
     * <p>
     * Pattern: "[A-Z0-9]{4,4}[A-Z]{2,2}[A-Z0-9]{2,2}([A-Z0-9]{3,3}){0,1}"
     * <p>
     * Example: "DEUTDEFF" or "DEUTDEFF500"
     */
    public void setbIC(String bIC) {
        boolean patternOK = (bIC != null) && bIC.matches(EBankingConstants.PATTERN_BIC);
        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'bIC' (" + bIC + ") in setBIC()");
        }
        this.bIC = bIC;
    }

    /**
     * @param bIC the BIC (Bank Identifier Code).<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     * <p>
     * Pattern: EBankingConstants.PATTERN_BIC<br>
     * <p>
     * Pattern: "[A-Z0-9]{4,4}[A-Z]{2,2}[A-Z0-9]{2,2}([A-Z0-9]{3,3}){0,1}"
     * <p>
     * Example: "DEUTDEFF" or "DEUTDEFF500"
     */
    public void setbIC(String bIC, JsonValidationExceptionCollector collector) {
        try {
            setbIC(bIC);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
        }
    }
}
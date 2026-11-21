package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Tp {

    @JsonProperty("Cd")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String cd;

    public Tp() {}

    public Tp(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            // Note: You may need to add a new constant like ERROR_TP_INIT to EBankingConstants.java
            if (params.getTpCd() != null && !params.getTpCd().isEmpty()) {
                setCd(params.getTpCd(), collector);
            }
        } catch (Exception e) {
            collector.addError("ERROR_TP_INIT", e);
        }
    }

    /**
     * @return the Cd
     */
    public String getCd() {
        return cd;
    }

    /**
     * @param cd the Cd to be set.<br>
     * The parameter is validated.<br>
     * Pattern: "[A-Z]{4}" (example: "SVGS", "CACC", etc.)
     */
    public void setCd(String cd) {
        boolean patternOK = (cd != null && !cd.isEmpty()) &&
            java.util.regex.Pattern.matches(EBankingConstants.PATTERN_CD_STATUS, cd);

        if (!patternOK) {
            throw new IllegalArgumentException(
                "Wrong parameter 'cd' (" + cd + ") in setCd()"
            );
        }
        this.cd = cd;
    }

    /**
     * @param cd the Cd to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setCd(String cd, JsonValidationExceptionCollector collector) {
        try {
            setCd(cd);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
        }
    }
}

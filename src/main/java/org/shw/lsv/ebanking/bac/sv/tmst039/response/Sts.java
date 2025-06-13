package org.shw.lsv.ebanking.bac.sv.tmst039.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Sts {
    @JsonProperty("Cd")  // Status Code
    String cd;

    @JsonProperty("Rsn") // Reason
    String rsn;

    public Sts() { }

    public Sts(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            if (params.getCatPurpCd() != null && !params.getCatPurpCd().isEmpty()) {
                setCd(params.getCatPurpCd(), collector);
            }
            if (params.getRsn() != null && !params.getRsn().isEmpty()) {
                setRsn(params.getRsn(), collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_STS_INIT, e);
        }
    }

    /**
     * @return the Cd (status code)
     */
    public String getCd() {
        return cd;
    }

    /**
     * @param cd the Cd (status code) to be set.<br>
     * The parameter is validated: null not allowed.<br>
     * Pattern: "[A-Z]{4}" (example: "ACCP", "RJCT", etc.)
     */
    public void setCd(String cd) {
        boolean patternOK = (cd != null && !cd.isEmpty()) &&
            java.util.regex.Pattern.matches(EBankingConstants.PATTERN_CD_STATUS, cd);

        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'cd' (" + cd + ") in setCd()");
        }
        this.cd = cd;
    }

    /**
     * @param cd the Cd (status code) to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setCd(String cd, JsonValidationExceptionCollector collector) {
        try {
            setCd(cd);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
        }
    }

    /**
     * @return the Rsn (reason)
     */
    public String getRsn() {
        return rsn;
    }

    /**
     * @param rsn the Rsn (reason) to be set.<br>
     * The parameter is validated: null not allowed.<br>
     * Pattern: ".{1,256}" (1 to 256 characters, adjust as needed)
     */
    public void setRsn(String rsn) {
        boolean patternOK = (rsn != null && !rsn.isEmpty()) && rsn.length() <= 256;
        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'rsn' (" + rsn + ") in setRsn()");
        }
        this.rsn = rsn;
    }

    /**
     * @param rsn the Rsn (reason) to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setRsn(String rsn, JsonValidationExceptionCollector collector) {
        try {
            setRsn(rsn);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
        }
    }
}
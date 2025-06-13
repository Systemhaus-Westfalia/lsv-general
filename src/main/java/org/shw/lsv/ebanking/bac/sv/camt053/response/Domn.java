package org.shw.lsv.ebanking.bac.sv.camt053.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Domn {

    @JsonProperty("Cd")
    String cd;

    @JsonProperty("Fmly")
    Fmly fmly;

    public Domn() {}

    /**
     * Constructor with parameters.
     * Initializes the object using values from RequestParams.
     * @param params    the RequestParams containing the values to set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public Domn(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            if (params.getCatPurpCd() != null && !params.getCatPurpCd().isEmpty()) {
                setCd(params.getCatPurpCd(), collector);
            }
            setFmly(new Fmly(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_DOMN_INIT, e);
        }
    }

    /**
     * @return the Cd (domain code)
     */
    public String getCd() {
        return cd;
    }

    /**
     * @param cd the Cd (domain code) to be set.<br>
     * The parameter is validated: null not allowed.<br>
     * Pattern: "[A-Z]{4}" (example: "NMSC")
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
     * @param cd the Cd (domain code) to be set.<br>
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
     * @return the Fmly object<br>
     */
    public Fmly getFmly() {
        return fmly;
    }

    /**
     * @param fmly the Fmly to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setFmly(Fmly fmly) {
        if (fmly == null) {
            throw new IllegalArgumentException("Wrong parameter 'fmly' in setFmly()");
        }
        this.fmly = fmly;
    }

    /**
     * @param fmly the Fmly to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setFmly(Fmly fmly, JsonValidationExceptionCollector collector) {
        try {
            setFmly(fmly);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}
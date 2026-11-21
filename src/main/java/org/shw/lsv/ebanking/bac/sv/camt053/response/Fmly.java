package org.shw.lsv.ebanking.bac.sv.camt053.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Fmly {

    @JsonProperty("Cd")
    String cd;

    @JsonProperty("SubFmlyCd")
    String subFmlyCd;

    public Fmly() {}

    /**
     * Constructor with parameters.
     * Initializes the object using values from parameters.
     * @param cd         the family code
     * @param subFmlyCd  the sub-family code
     */
    public Fmly(String cd, String subFmlyCd) {
        setCd(cd);
        setSubFmlyCd(subFmlyCd);
    }

    /**
     * Constructor with RequestParams and collector.
     */
    public Fmly(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            if (params.getCatPurpCd() != null && !params.getCatPurpCd().isEmpty()) {
                setCd(params.getCatPurpCd(), collector);
            }
            if (params.getSubFmlyCd() != null && !params.getSubFmlyCd().isEmpty()) {
                setSubFmlyCd(params.getSubFmlyCd(), collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_FMLY_INIT, e);
        }
    }

    /**
     * @return the Cd (family code)
     */
    public String getCd() {
        return cd;
    }

    /**
     * @param cd the Cd (family code) to be set.<br>
     * The parameter is validated: null not allowed.<br>
     * Pattern: "[A-Z]{4}" (example: "NTAV")
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
     * @param cd the Cd (family code) to be set.<br>
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
     * @return the SubFmlyCd (sub-family code)
     */
    public String getSubFmlyCd() {
        return subFmlyCd;
    }

    /**
     * @param subFmlyCd the SubFmlyCd (sub-family code) to be set.<br>
     * The parameter is validated: null not allowed.<br>
     * Pattern: "[A-Z]{4}" (example: "NTAV")
     */
    public void setSubFmlyCd(String subFmlyCd) {
        boolean patternOK = (subFmlyCd != null && !subFmlyCd.isEmpty()) &&
            java.util.regex.Pattern.matches(EBankingConstants.PATTERN_CD_STATUS, subFmlyCd);

        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'subFmlyCd' (" + subFmlyCd + ") in setSubFmlyCd()");
        }
        this.subFmlyCd = subFmlyCd;
    }

    /**
     * @param subFmlyCd the SubFmlyCd (sub-family code) to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setSubFmlyCd(String subFmlyCd, JsonValidationExceptionCollector collector) {
        try {
            setSubFmlyCd(subFmlyCd);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
        }
    }
}
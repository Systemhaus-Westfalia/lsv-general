package org.shw.lsv.ebanking.bac.sv.pain001.request;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PstlAdr {
    @JsonProperty("TwnNm")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String twnNm;

    @JsonProperty("Ctry")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String ctry;

    @JsonProperty("AdrLine")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String adrLine;

    public PstlAdr() { }

    public PstlAdr(RequestParams params, String context, JsonValidationExceptionCollector collector) {
        try {
            if (context.equals(EBankingConstants.CONTEXT_DBTRAGT)) {
                if (params.getDbtrAgtCity() != null && !params.getDbtrAgtCity().isEmpty()) {
                    setTwnNm(params.getDbtrAgtCity(), collector);
                }

                if (params.getDbtrAgtCountry() != null && !params.getDbtrAgtCountry().isEmpty()) {
                    setCtry(params.getDbtrAgtCountry(), collector);
                }

                if (params.getDbtrAgtAddress() != null && !params.getDbtrAgtAddress().isEmpty()) {
                    setAdrLine(params.getDbtrAgtAddress(), collector);
                }
            } else if (context.equals(EBankingConstants.CONTEXT_CDTR)) {
                if (params.getCdtrCity() != null && !params.getCdtrCity().isEmpty()) {
                    setTwnNm(params.getCdtrCity(), collector);
                }

                if (params.getCdtrCountry() != null && !params.getCdtrCountry().isEmpty()) {
                    setCtry(params.getCdtrCountry(), collector);
                }

                if (params.getCdtrAddress() != null && !params.getCdtrAddress().isEmpty()) {
                    setAdrLine(params.getCdtrAddress(), collector);
                }
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_PMTTPINF_INIT, e);
        }
    }

    /**
     * @return the TwnNm
     */
    public String getTwnNm() {
        return twnNm;
    }

    /**
     * @param twnNm the Town Name to be set.<br>
     * The parameter is validated according to PAIN.001 schema (Max35Text).<br>
     * "minLength" : 1, "maxLength" : 35; null not allowed.<br>
     */
    public void setTwnNm(String twnNm) {
        final int MINLENGTH = 1;
        final int MAXLENGTH = 35;
        int length = (twnNm == null || twnNm.isEmpty()) ? 0 : twnNm.length();

        if (length < MINLENGTH || length > MAXLENGTH) {
            throw new IllegalArgumentException(
                "Wrong parameter 'twnNm' (" + twnNm + ") in setTwnNm()"
            );
        }
        this.twnNm = twnNm;
    }

    /**
     * @param twnNm the Town Name to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setTwnNm(String twnNm, JsonValidationExceptionCollector collector) {
        try {
            setTwnNm(twnNm);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_WRONG_LENGTH, e);
        }
    }

    /**
     * @return the Ctry
     */
    public String getCtry() {
        return ctry;
    }

    /**
     * @param ctry the country code to be set.<br>
     * The parameter is validated: null not allowed.<br>
     * Pattern: "[A-Z]{2}" (ISO 3166-1 alpha-2 country code, e.g. "CR", "US", "DE")
     */
    public void setCtry(String ctry) {
        boolean patternOK = (ctry != null) && Pattern.matches(EBankingConstants.PATTERN_COUNTRY, ctry);

        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'Ctry' (" + ctry + ") in setCtry()");
        }
        this.ctry = ctry;
    }

    /**
     * @param ctry the country code to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setCtry(String ctry, JsonValidationExceptionCollector collector) {
        try {
            setCtry(ctry);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
            //throw e;
        }
    }

    /**
     * @return the AdrLine
     */
    public String getAdrLine() {
        return adrLine;
    }

    /**
     * @param adrLine the Address Line to be set.<br>
     * The parameter is validated according to PAIN.001 schema (Max70Text).<br>
     * "minLength" : 1, "maxLength" : 70; null not allowed.<br>
     */
    public void setAdrLine(String adrLine) {
        final int MINLENGTH = 1;
        final int MAXLENGTH = 70;
        int length = (adrLine == null || adrLine.isEmpty()) ? 0 : adrLine.length();

        if (length < MINLENGTH || length > MAXLENGTH) {
            throw new IllegalArgumentException(
                "Wrong parameter 'adrLine' (" + adrLine + ") in setAdrLine()"
            );
        }
        this.adrLine = adrLine;
    }

    /**
     * @param adrLine the Address Line to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setAdrLine(String adrLine, JsonValidationExceptionCollector collector) {
        try {
            setAdrLine(adrLine);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_WRONG_LENGTH, e);
        }
    }


}

package org.shw.lsv.ebanking.bac.sv.pain001.request;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PstlAdr {
    @JsonProperty("Ctry")
    String ctry; 

    public PstlAdr() { }

    public PstlAdr(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setCtry(params.getCountry(), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_PMTTPINF_INIT, e);
        }
    }

    /**
     * @return the Ctry
     */
    public String getCtry() {
        return ctry;
    }

    /**
     * @param Ctry the country code to be set.<br>
     * The parameter is validated: null not allowed.<br>
     * Pattern: "[A-Z]{2}" (ISO 3166-1 alpha-2 country code, e.g. "CR", "US", "DE")
     */
    public void setCtry(String Ctry) {
        boolean patternOK = (Ctry != null) && Pattern.matches(EBankingConstants.PATTERN_COUNTRY, Ctry);

        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'Ctry' (" + Ctry + ") in setCtry()");
        }
        this.ctry = Ctry;
    }

    /**
     * @param Ctry the country code to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setCtry(String Ctry, JsonValidationExceptionCollector collector) {
        try {
            setCtry(Ctry);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
            //throw e;
        }
    }


}

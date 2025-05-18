package org.shw.lsv.ebanking.bac.sv.camt052.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CdOrPrtry {

    @JsonProperty(value = "Cd", required = true)
    String Cd;  // ToDo: check pattern


    public CdOrPrtry() {
    }


    /*
     * Constructor with parameters
     * For using the Constructor at deserialization time, it has to be of the form:
     * public CdOrPrtry(@JsonProperty(value = "Cd", required = true) Cd cd,.....)
     */
    public CdOrPrtry(String cd) {
        setCd(cd);
    }


    /**
     * @return the Cd object<br>
     */
    public String getCd() {
        return Cd;
    }


    /**
     * @param cd the Cd to be set<br>
     * The parameter is validated: null not allowed.<br>
     * And must equal one of the following values: [ITAV, ITBD, OPAV, OPBD]
     */
    public void setCd(String cd) {
        if ((cd == null || cd.isEmpty()) || 
            !(cd.equals(EBankingConstants.ITAV) || cd.equals(EBankingConstants.ITBD) || 
            cd.equals(EBankingConstants.OPAV) || cd.equals(EBankingConstants.OPBD))) {
            throw new IllegalArgumentException("Wrong parameter 'cd' (" + cd + ") in setCd()");
        }
        this.Cd = cd;
    }

    /**
     * @param cd the Cd to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setCd(String cd, JsonValidationExceptionCollector collector) {
        try {
            setCd(cd);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_INVALID_CD_VALUE, e);
            //throw e;
        }
    }


}

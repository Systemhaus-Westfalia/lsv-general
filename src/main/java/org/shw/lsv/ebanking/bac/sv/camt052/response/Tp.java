package org.shw.lsv.ebanking.bac.sv.camt052.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tp {

    @JsonProperty("CdOrPrtry")
    CdOrPrtry CdOrPrtry;


    public Tp() {
    }


    /**
     * @return the CdOrPrtry object<br>
     */
    public CdOrPrtry getCdOrPrtry() {
        return CdOrPrtry;
    }


    /**
     * @param cdOrPrtry the CdOrPrtry to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setCdOrPrtry(CdOrPrtry cdOrPrtry) {
        if (cdOrPrtry == null) {
            throw new IllegalArgumentException("Wrong parameter 'cdOrPrtry' in setCdOrPrtry()");
        }
        this.CdOrPrtry = cdOrPrtry;
    }

    /**
     * @param cdOrPrtry the CdOrPrtry to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setCdOrPrtry(CdOrPrtry cdOrPrtry, JsonValidationExceptionCollector collector) {
        try {
            setCdOrPrtry(cdOrPrtry);
        } catch (IllegalArgumentException e) {
            collector.addError("setCdOrPrtry()", e);
            //throw e;
        }
    }



}

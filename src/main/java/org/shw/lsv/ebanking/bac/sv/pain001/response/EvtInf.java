package org.shw.lsv.ebanking.bac.sv.pain001.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EvtInf {
    @JsonProperty("EvtCd")
    String evtCd;  // Event Code`

    @JsonProperty("EvtDesc")
    String evtDesc;  // Event Description

    @JsonProperty("EvtTm")
    String evtTm;  // Event Time

    public EvtInf() {}

    public EvtInf(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setEvtCd(params.getEvtCd(), collector);
            setEvtDesc(params.getEvtDesc(), collector);
            setEvtTm(params.getEvtTm(), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_EVTINF_INIT, e);
        }
    }

        /**
     * @return the EvtCd (Event Code)
     */
    public String getEvtCd() {
        return evtCd;
    }

    /**
     * @param evtCd the Event Code to be set.<br>
     * The parameter is validated: null not allowed.<br>
     * Pattern: "[A-Z0-9]{1,35}"<br>
     * Example: "ACCEPTED"
     */
    public void setEvtCd(String evtCd) {
        boolean patternOK = (evtCd != null && !evtCd.isEmpty()) && evtCd.matches(EBankingConstants.PATTERN_EVENTCODE);
        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'evtCd' (" + evtCd + ") in setEvtCd()");
        }
        this.evtCd = evtCd;
    }

    /**
     * @param evtCd the Event Code to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setEvtCd(String evtCd, JsonValidationExceptionCollector collector) {
        try {
            setEvtCd(evtCd);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
        }
    }

    /**
     * @return the EvtDesc (Event Description)
     */
    public String getEvtDesc() {
        return evtDesc;
    }

    /**
     * @param evtDesc the Event Description to be set.<br>
     * The parameter is validated: null not allowed.<br>
     * Pattern: ".{1,140}"<br>
     * Example: "Payment accepted"
     */
    public void setEvtDesc(String evtDesc) {
        boolean patternOK = (evtDesc != null && !evtDesc.isEmpty()) && evtDesc.length() <= 140;
        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'evtDesc' (" + evtDesc + ") in setEvtDesc()");
        }
        this.evtDesc = evtDesc;
    }

    /**
     * @param evtDesc the Event Description to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setEvtDesc(String evtDesc, JsonValidationExceptionCollector collector) {
        try {
            setEvtDesc(evtDesc);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
        }
    }

    /**
     * @return the EvtTm (Event Time)
     */
    public String getEvtTm() {
        return evtTm;
    }

    /**
     * @param evtTm the Event Time to be set.<br>
     * The parameter is validated: null not allowed.<br>
     * Pattern: "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}[+-]([0][0-9]|[1][0-1]):[0-5][0-9]$"<br>
     * Example: "2023-06-27T14:23:00Z"
     */
    public void setEvtTm(String evtTm) {
        boolean patternOK = (evtTm != null && !evtTm.isEmpty()) && evtTm.matches(EBankingConstants.PATTERN_DATETIME);
        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'evtTm' (" + evtTm + ") in setEvtTm()");
        }
        this.evtTm = evtTm;
    }

    /**
     * @param evtTm the Event Time to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setEvtTm(String evtTm, JsonValidationExceptionCollector collector) {
        try {
            setEvtTm(evtTm);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
        }
    }

}

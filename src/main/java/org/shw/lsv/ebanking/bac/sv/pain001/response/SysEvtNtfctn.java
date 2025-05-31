package org.shw.lsv.ebanking.bac.sv.pain001.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SysEvtNtfctn {

    @JsonProperty("EvtInf")
    EvtInf evtInf;  // Event Information

    public SysEvtNtfctn() {}

    public SysEvtNtfctn(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setEvtInf(new EvtInf(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_SYSEVTNTFCTN_INIT, e);
        }
    }

    /**
     * @return the EvtInf object<br>
     */
    public EvtInf getEvtInf() {
        return evtInf;
    }

    /**
     * @param evtInf the Event Information<br>
     * <p>
     * Used to provide structured details about a system event (status, errors, acknowledgments) in ISO 20022 messaging.
     * <p>
     * Commonly found in responses to PAIN.001 requests, but not part of the original PAIN.001 schema itself—rather, it is part of the broader ISO 20022 event/status notification framework.
     * <p>
     * The parameter is validated: null not allowed.<br>
     */
    public void setEvtInf(EvtInf evtInf) {
        if (evtInf == null) {
            throw new IllegalArgumentException("Wrong parameter 'evtInf' in setEvtInf()");
        }
        this.evtInf = evtInf;
    }

    /**
     * @param evtInf the Event Information<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     * <p>
     * Used to provide structured details about a system event (status, errors, acknowledgments) in ISO 20022 messaging.
     * <p>
     * Commonly found in responses to PAIN.001 requests, but not part of the original PAIN.001 schema itself—rather, it is part of the broader ISO 20022 event/status notification framework.
     * <p>
     */
    public void setEvtInf(EvtInf evtInf, JsonValidationExceptionCollector collector) {
        try {
            setEvtInf(evtInf);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}
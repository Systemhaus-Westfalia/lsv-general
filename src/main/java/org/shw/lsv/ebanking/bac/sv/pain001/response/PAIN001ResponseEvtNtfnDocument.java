package org.shw.lsv.ebanking.bac.sv.pain001.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.misc.Rejection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PAIN001ResponseEvtNtfnDocument {

    @JsonProperty("SysEvtNtfctn")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    SysEvtNtfctn sysEvtNtfctn;  // System Event Notification.

    @JsonProperty("admi.002.001.01")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Rejection rejection;

    public PAIN001ResponseEvtNtfnDocument() { }

    public PAIN001ResponseEvtNtfnDocument(RequestParams params, JsonValidationExceptionCollector collector) {
        setSysEvtNtfctn(new SysEvtNtfctn(params, collector), collector);}


    /**
    * @return the SysEvtNtfctn object<br>
    */
    public SysEvtNtfctn getSysEvtNtfctn() {
        return sysEvtNtfctn;
    }

    /**
     * @param sysEvtNtfctn the  System Event Notification<br>
     * <p>
     * <p>
     * Used in responses to payment requests (like PAIN.001) to provide system status, acknowledgments, or error information.
     * Not part of the original PAIN.001 schema, but part of the broader ISO 20022 messaging framework for status and event reporting.
     * <p>
     * The parameter is validated: null not allowed.<br>
     */
    public void setSysEvtNtfctn(SysEvtNtfctn sysEvtNtfctn) {
        if (sysEvtNtfctn == null) {
            throw new IllegalArgumentException("Wrong parameter 'sysEvtNtfctn' in setSysEvtNtfctn()");
        }
        this.sysEvtNtfctn = sysEvtNtfctn;
    }

    /**
     * @param sysEvtNtfctn the  System Event Notificationt<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     * <p>
     * Used in responses to payment requests (like PAIN.001) to provide system status, acknowledgments, or error information.
     * Not part of the original PAIN.001 schema, but part of the broader ISO 20022 messaging framework for status and event reporting.
     * <p>
     */
    public void setSysEvtNtfctn(SysEvtNtfctn sysEvtNtfctn, JsonValidationExceptionCollector collector) {
        try {
            setSysEvtNtfctn(sysEvtNtfctn);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }

    /**
    * @return the Rejection object<br>
    */
    public Rejection getRejection() {
        return rejection;
    }

    /**
     * @param rejection the Rejection to be set<br>
     */
    public void setRejection(Rejection rejection) {
        this.rejection = rejection;
    }

    /**
     * @param rejection the Rejection to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setRejection(Rejection rejection, JsonValidationExceptionCollector collector) {
        try {
            setRejection(rejection);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}
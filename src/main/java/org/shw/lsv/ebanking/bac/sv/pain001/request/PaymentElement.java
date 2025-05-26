package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentElement {

    @JsonProperty("PmtId")
    PmtId pmtId;

    @JsonProperty("PmtTpInf")
    PmtElementtPInf pmtElementtPInf;  // additional information about the type of payment being made.

    @JsonProperty("Amt")
    PmtElementtAmt pmtElementtAmt;

    @JsonProperty("CdtrAgt")
    CdtrAgt cdtrAgt;

    @JsonProperty("Cdtr")
    Cdtr cdtr;

    @JsonProperty("CdtrAcct")
    CdtrAcct cdtrAcct;

    @JsonProperty("RmtInf")
    RmtInf rmtInf;

    public PaymentElement(RequestParams params, JsonValidationExceptionCollector collector) {
        //TODO Auto-generated constructor stub
    }

}

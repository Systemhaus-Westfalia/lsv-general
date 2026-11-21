package org.shw.lsv.ebanking.bac.sv.pain001.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StsRptReq {

    @JsonProperty("ReqId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    ReqId reqId;

    @JsonProperty("NttiesToBeRptd")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    NttiesToBeRptd nttiesToBeRptd;  // Entities To Be Reported

    /**
     * Default constructor.
     */
    public StsRptReq() {}

    /**
     * Constructor with parameters.
     * Initializes the object using values from RequestParams.
     * @param params    the RequestParams containing the values to set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public StsRptReq(RequestParams params, JsonValidationExceptionCollector collector) {
        setReqId(new ReqId(params, collector), collector);
        setNttiesToBeRptd(new NttiesToBeRptd(params, collector), collector);
    }

    /**
     * @return the ReqId object<br>
     */
    public ReqId getReqId() {
        return reqId;
    }

    /**
     * @param reqId the ReqId to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setReqId(ReqId reqId) {
        if (reqId == null) {
            throw new IllegalArgumentException("Wrong parameter 'reqId' in setReqId()");
        }
        this.reqId = reqId;
    }

    /**
     * @param reqId the ReqId to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setReqId(ReqId reqId, JsonValidationExceptionCollector collector) {
        try {
            setReqId(reqId);
        } catch (IllegalArgumentException e) {
            collector.addError("ERROR_NULL_NOT_ALLOWED", e);
        }
    }

    /**
     * @return the NttiesToBeRptd object<br>
     */
    public NttiesToBeRptd getNttiesToBeRptd() {
        return nttiesToBeRptd;
    }

    /**
     * @param nttiesToBeRptd the NttiesToBeRptd to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setNttiesToBeRptd(NttiesToBeRptd nttiesToBeRptd) {
        if (nttiesToBeRptd == null) {
            throw new IllegalArgumentException("Wrong parameter 'nttiesToBeRptd' in setNttiesToBeRptd()");
        }
        this.nttiesToBeRptd = nttiesToBeRptd;
    }

    /**
     * @param nttiesToBeRptd the NttiesToBeRptd to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setNttiesToBeRptd(NttiesToBeRptd nttiesToBeRptd, JsonValidationExceptionCollector collector) {
        try {
            setNttiesToBeRptd(nttiesToBeRptd);
        } catch (IllegalArgumentException e) {
            collector.addError("ERROR_NULL_NOT_ALLOWED", e);
        }
    }
}
package org.shw.lsv.ebanking.bac.sv.pain001.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StsRptReq {

    @JsonProperty("ReqId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    ReqId reqId;

    @JsonProperty("NttiesToBeRptd")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    NttiesToBeRptd nttiesToBeRptd;  // Entities To Be Reported

    public StsRptReq() {
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
     * @return the Entities To Be Reported<br>
     */
    public NttiesToBeRptd getNttiesToBeRptd() {
        return nttiesToBeRptd;
    }

    /**
     * @param nttiesToBeRptd the Entities To Be Reported<br>
     * The parameter is validated: null not allowed.<br>
     * <p>
     * Used to specify which parties (usually by BIC) are the subject of a status report or status report request.
     * Not part of the core PAIN.001 schema, but may appear in related status or notification messages in ISO 20022 workflows.
     */
    public void setNttiesToBeRptd(NttiesToBeRptd nttiesToBeRptd) {
        if (nttiesToBeRptd == null) {
            throw new IllegalArgumentException("Wrong parameter 'nttiesToBeRptd' in setNttiesToBeRptd()");
        }
        this.nttiesToBeRptd = nttiesToBeRptd;
    }
}
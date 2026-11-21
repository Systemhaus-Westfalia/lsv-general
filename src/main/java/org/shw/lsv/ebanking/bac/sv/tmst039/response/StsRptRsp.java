package org.shw.lsv.ebanking.bac.sv.tmst039.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.pain001.response.NttiesToBeRptd;
import org.shw.lsv.ebanking.bac.sv.pain001.response.ReqId;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StsRptRsp {

    @JsonProperty("ReqId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    ReqId reqId;

    @JsonProperty("NttiesRptd") // NttiesRptd is the name in Json  
    @JsonInclude(JsonInclude.Include.NON_NULL)
    NttiesToBeRptd nttiesToBeRptd;  // Entities Reported

    @JsonProperty("Sts")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Sts sts;

    /**
     * Default constructor.
     */
    public StsRptRsp() {}

    /**
     * Constructor with parameters.
     * Initializes the object using values from RequestParams.
     * @param params    the RequestParams containing the values to set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public StsRptRsp(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setReqId(new ReqId(params, collector), collector);
            setNttiesRptd(new NttiesToBeRptd(params, collector), collector);
            setSts(new Sts(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_STSRPTRSP_INIT, e);
        }
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
     * @return the NttiesRptd object<br>
     */
    public NttiesToBeRptd getNttiesToBeRptd() {
        return nttiesToBeRptd;
    }

    /**
     * @param nttiesRptd the NttiesRptd to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setNttiesToBeRptd(NttiesToBeRptd nttiesRptd) {
        if (nttiesRptd == null) {
            throw new IllegalArgumentException("Wrong parameter 'nttiesRptd' in setNttiesRptd()");
        }
        this.nttiesToBeRptd = nttiesRptd;
    }

    /**
     * @param nttiesRptd the NttiesRptd to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setNttiesRptd(NttiesToBeRptd nttiesRptd, JsonValidationExceptionCollector collector) {
        try {
            setNttiesToBeRptd(nttiesRptd);
        } catch (IllegalArgumentException e) {
            collector.addError("ERROR_NULL_NOT_ALLOWED", e);
        }
    }

    /**
     * @return the Sts object<br>
     */
    public Sts getSts() {
        return sts;
    }

    /**
     * @param sts the Sts to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setSts(Sts sts) {
        if (sts == null) {
            throw new IllegalArgumentException("Wrong parameter 'sts' in setSts()");
        }
        this.sts = sts;
    }

    /**
     * @param sts the Sts to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setSts(Sts sts, JsonValidationExceptionCollector collector) {
        try {
            setSts(sts);
        } catch (IllegalArgumentException e) {
            collector.addError("ERROR_NULL_NOT_ALLOWED", e);
        }
    }
}
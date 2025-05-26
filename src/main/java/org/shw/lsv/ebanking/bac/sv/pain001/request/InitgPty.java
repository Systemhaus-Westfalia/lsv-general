package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InitgPty {

    @JsonProperty("Nm")
    String nm;  // Debtor name

    @JsonProperty("Id")
    GrpHdrId grpHdrId;

    public InitgPty() { }

    public InitgPty(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setNm(params.getNm(), collector);
            setGrpHdrId(new GrpHdrId(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_INITGPTY_INIT, e);
        }
    }

    /**
     * @return the Nm (Debtor name)
     */
    public String getNm() {
        return nm;
    }

    /**
     * @param nm the debtor name to be set.<br>
     * The parameter is validated: null not allowed.<br>
     * "minLength" : 1, "maxLength" : 70<br>
     * Example: "CLIENTE01"
     */
    public void setNm(String nm) {
        if (nm == null || nm.isEmpty() || nm.length() > 70) {
            throw new IllegalArgumentException("Wrong parameter 'nm' (" + nm + ") in setNm()");
        }
        this.nm = nm;
    }

    /**
     * @param nm the debtor name to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setNm(String nm, JsonValidationExceptionCollector collector) {
        try {
            setNm(nm);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_WRONG_LENGTH, e);
            //throw e;
        }
    }

    /**
     * @return the GrpHdrId object<br>
     */
    public GrpHdrId getGrpHdrId() {
        return grpHdrId;
    }

    /**
     * @param grpHdrId the GrpHdrId to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setGrpHdrId(GrpHdrId grpHdrId) {
        if (grpHdrId == null) {
            throw new IllegalArgumentException("Wrong parameter 'grpHdrId' in setGrpHdrId()");
        }
        this.grpHdrId = grpHdrId;
    }

    /**
     * @param grpHdrId the GrpHdrId to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setGrpHdrId(GrpHdrId grpHdrId, JsonValidationExceptionCollector collector) {
        try {
            setGrpHdrId(grpHdrId);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }



}

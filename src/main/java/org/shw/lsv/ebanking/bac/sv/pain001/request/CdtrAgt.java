package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CdtrAgt {

    @JsonProperty("FinInstnId")
    CdtrAgtFinInstnId cdtrAgtFinInstnId;

    public CdtrAgt() { }

    public CdtrAgt(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setCdtrAgtFinInstnId(new CdtrAgtFinInstnId(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_CDTRAGT_INIT, e);
        }
    }

    /**
     * @return the CdtrAgtFinInstnId object<br>
     */
    public CdtrAgtFinInstnId getCdtrAgtFinInstnId() {
        return cdtrAgtFinInstnId;
    }

    /**
     * @param cdtrAgtFinInstnId the CdtrAgtFinInstnId to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setCdtrAgtFinInstnId(CdtrAgtFinInstnId cdtrAgtFinInstnId) {
        if (cdtrAgtFinInstnId == null) {
            throw new IllegalArgumentException("Wrong parameter 'cdtrAgtFinInstnId' in setCdtrAgtFinInstnId()");
        }
        this.cdtrAgtFinInstnId = cdtrAgtFinInstnId;
    }

    /**
     * @param cdtrAgtFinInstnId the CdtrAgtFinInstnId to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setCdtrAgtFinInstnId(CdtrAgtFinInstnId cdtrAgtFinInstnId, JsonValidationExceptionCollector collector) {
        try {
            setCdtrAgtFinInstnId(cdtrAgtFinInstnId);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }

}

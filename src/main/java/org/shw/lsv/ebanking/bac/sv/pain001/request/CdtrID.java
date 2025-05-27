package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CdtrID {

    @JsonProperty("OrgId")
    CdtrOrgId cdtrOrgId;

    public CdtrID() { }

    public CdtrID(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setCdtrOrgId(new CdtrOrgId(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_CDTRID_INIT, e);
        }
    }

    /**
     * @return the CdtrOrgId object<br>
     */
    public CdtrOrgId getCdtrOrgId() {
        return cdtrOrgId;
    }

    /**
     * @param cdtrOrgId the CdtrOrgId to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setCdtrOrgId(CdtrOrgId cdtrOrgId) {
        if (cdtrOrgId == null) {
            throw new IllegalArgumentException("Wrong parameter 'cdtrOrgId' in setCdtrOrgId()");
        }
        this.cdtrOrgId = cdtrOrgId;
    }

    /**
     * @param cdtrOrgId the CdtrOrgId to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setCdtrOrgId(CdtrOrgId cdtrOrgId, JsonValidationExceptionCollector collector) {
        try {
            setCdtrOrgId(cdtrOrgId);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }


}

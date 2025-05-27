package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CdtrOrgId {

    @JsonProperty("Othr")
    CdtrOrgIdOthr cdtrOrgIdOthr;

    public CdtrOrgId() { }

    public CdtrOrgId(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setCdtrOrgIdOthr(new CdtrOrgIdOthr(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_CDTRORGID_INIT, e);
        }
    }

    /**
     * @return the CdtrOrgIdOthr object<br>
     */
    public CdtrOrgIdOthr getCdtrOrgIdOthr() {
        return cdtrOrgIdOthr;
    }

    /**
     * @param cdtrOrgIdOthr the CdtrOrgIdOthr to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setCdtrOrgIdOthr(CdtrOrgIdOthr cdtrOrgIdOthr) {
        if (cdtrOrgIdOthr == null) {
            throw new IllegalArgumentException("Wrong parameter 'cdtrOrgIdOthr' in setCdtrOrgIdOthr()");
        }
        this.cdtrOrgIdOthr = cdtrOrgIdOthr;
    }

    /**
     * @param cdtrOrgIdOthr the CdtrOrgIdOthr to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setCdtrOrgIdOthr(CdtrOrgIdOthr cdtrOrgIdOthr, JsonValidationExceptionCollector collector) {
        try {
            setCdtrOrgIdOthr(cdtrOrgIdOthr);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }


}

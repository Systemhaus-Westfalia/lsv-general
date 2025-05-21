package org.shw.lsv.ebanking.bac.sv.camt052.request;

import org.shw.lsv.ebanking.bac.sv.handling.Camt052RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.misc.OrgId;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PtyId {
    
    @JsonProperty("OrgId")    // "OrgId" is the name of the field in the JSON
    OrgId orgId;


    public PtyId() { }


    public PtyId(Camt052RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setOrgId(new OrgId(params, EBankingConstants.CONTEXT_PTYORGID, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_PTYID_INIT, e);
        }
    }


    public OrgId getOrgId() {
        return orgId;
    }


    /**
     * @param orgId the OrgId to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setOrgId(OrgId orgId) {
        if (orgId == null) {
            throw new IllegalArgumentException("Wrong parameter 'orgId' in setOrgId()");
        }
        this.orgId = orgId;
    }

    /**
     * @param orgId the OrgId to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setOrgId(OrgId orgId, JsonValidationExceptionCollector collector) {
        try {
            setOrgId(orgId);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }

}

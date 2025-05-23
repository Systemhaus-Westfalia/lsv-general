package org.shw.lsv.ebanking.bac.sv.misc;

import org.shw.lsv.ebanking.bac.sv.handling.RequestParamsCamt052;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IDOrgID {

    @JsonProperty("OrgId")  // "OrgId" is the name of the field in the JSON
    OrgId OrgId;
    

    public IDOrgID() {
    }


    public IDOrgID(RequestParamsCamt052 params, String context, JsonValidationExceptionCollector collector) {
        try {
            setOrgId(new OrgId(params, context, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_IDORGID_INIT, e);
        }
    }


    /**
     * @return the OrgId object<br>
     */
	public OrgId getOrgId() {
        return OrgId;
    }


    /**
     * @param orgId the OrgId to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setOrgId(OrgId orgId) {
        if (orgId == null) {
            throw new IllegalArgumentException("Wrong parameter 'orgId' in setOrgId()");
        }
        this.OrgId = orgId;
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

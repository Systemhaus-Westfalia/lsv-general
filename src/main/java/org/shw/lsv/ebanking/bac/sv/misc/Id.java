package org.shw.lsv.ebanking.bac.sv.misc;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Id {

    @JsonProperty("OrgId")  // "OrgId" is the name of the field in the JSON
    OrgId orgId;
    

    public Id() {
    }


    public Id(RequestParams params, String context, JsonValidationExceptionCollector collector) {
        try {
            setOrgId(new OrgId(params, context, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_TOID_INIT, e);
        }
    }


    /**
     * @return the OrgId object<br>
     */
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

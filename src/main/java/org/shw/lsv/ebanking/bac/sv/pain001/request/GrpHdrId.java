package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GrpHdrId {
    @JsonProperty("OrgId")
    GrpHdrOrgId grpHdrOrgId; 

        public GrpHdrId(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setGrpHdrOrgId(new GrpHdrOrgId(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_GRPHDRID_INIT, e);
        }
    }

    /**
     * @return the GrpHdrOrgId object<br>
     */
    public GrpHdrOrgId getGrpHdrOrgId() {
        return grpHdrOrgId;
    }

    /**
     * @param grpHdrOrgId the GrpHdrOrgId to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setGrpHdrOrgId(GrpHdrOrgId grpHdrOrgId) {
        if (grpHdrOrgId == null) {
            throw new IllegalArgumentException("Wrong parameter 'grpHdrOrgId' in setGrpHdrOrgId()");
        }
        this.grpHdrOrgId = grpHdrOrgId;
    }

    /**
     * @param grpHdrOrgId the GrpHdrOrgId to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setGrpHdrOrgId(GrpHdrOrgId grpHdrOrgId, JsonValidationExceptionCollector collector) {
        try {
            setGrpHdrOrgId(grpHdrOrgId);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }


}

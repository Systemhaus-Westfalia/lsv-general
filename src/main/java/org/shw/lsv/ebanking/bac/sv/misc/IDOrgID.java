package org.shw.lsv.ebanking.bac.sv.misc;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IDOrgID {

    @JsonProperty("OrgId")  // "OrgId" is the name of the field in the JSON
    OrgId OrgId;

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=IDOrgID.class.getName();
    

    /**
     * @return the OrgId object<br>
     */
	public OrgId getOrgId() {
        return OrgId;
    }


    /**
     * @param orgId the OrgId to be set.
     * The parameter is validated: null not allowed.
     */
    public void setOrgId(OrgId orgId, JsonValidationExceptionCollector collector) {
        try {
            if (orgId == null) {
                throw new IllegalArgumentException(
                    "Wrong parameter 'orgId' in " + FULLY_QUALIFIED_CLASSNAME + ".setOrgId()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setOrgId()";
            collector.addError(context, e);

            throw e;
        }

        this.OrgId = orgId;
    }

}

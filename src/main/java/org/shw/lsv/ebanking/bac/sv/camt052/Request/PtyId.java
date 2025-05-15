package org.shw.lsv.ebanking.bac.sv.camt052.Request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.OrgId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PtyId {
    
    @JsonProperty("OrgId")    // "OrgId" is the name of the field in the JSON
    OrgId OrgId;

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=PtyId.class.getName();


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

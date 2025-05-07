package org.shw.lsv.ebanking.bac.sv.camt060.Request;

import org.shw.lsv.ebanking.bac.sv.utils.OrgId;

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

    public void setOrgId(OrgId orgId) {
        if (orgId == null ) {
            throw new IllegalArgumentException("Wrong parameter 'orgId' in " +  FULLY_QUALIFIED_CLASSNAME + ".setOrgId()" + "\n");
        }
        this.OrgId = orgId;
    }

}

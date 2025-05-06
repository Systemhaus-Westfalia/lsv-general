package org.shw.lsv.ebanking.bac.sv.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IDOrgID {

    @JsonProperty("OrgId")  // "OrgId" is the name of the field in the JSON
    OrgId OrgId;

    @JsonIgnore
    final String fullyQualifiedClassName=IDOrgID.class.getName();
    

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
        if (orgId == null ) {
            throw new IllegalArgumentException("Wrong parameter 'orgId' in " +  fullyQualifiedClassName + ".setOrgId()" + "\n");
        }
        this.OrgId = orgId;
    }

}

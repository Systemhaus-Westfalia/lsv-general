package org.shw.lsv.ebanking.bac.sv.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The sending MessagingEndpoint that creates a Business Message. 
 * Must choose one of "FIId", "OrgId".
 * Here, "FIId" was implemented.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)  // Exclude fields with null values
public class Fr {
    @JsonProperty("FIId")
    @JsonInclude(JsonInclude.Include.NON_NULL)  // Exclude this field if its value is null
    FIId FIId=null;  // BICFI (Bank Identifier Code)

    @JsonProperty("OrgId")  // "OrgId" is the name of the field in the JSON
    @JsonInclude(JsonInclude.Include.NON_NULL)  // Exclude this field if its value is null
    OrgId OrgId=null;

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=Fr.class.getName();


    /**
	 * @return the FIId object<br>
	 */
    public FIId getFIId() {
        return FIId;
    }


	/**
	 * @param fIId the FIId object to be set<br>
	 * The parameter is validated: null not allowed.<br>
	 */
    public void setFIId(FIId fIId) {
        if (fIId == null ) {
            throw new IllegalArgumentException("Wrong parameter 'fIId' in " +  FULLY_QUALIFIED_CLASSNAME + ".setFIId()" + "\n");
        }
        FIId = fIId;
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
        if (orgId == null ) {
            throw new IllegalArgumentException("Wrong parameter 'orgId' in " +  FULLY_QUALIFIED_CLASSNAME + ".setOrgId()" + "\n");
        }
        this.OrgId = orgId;
    }


    public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


}

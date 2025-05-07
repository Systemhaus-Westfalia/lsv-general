package org.shw.lsv.ebanking.bac.sv.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The receiving MessagingEndpoint that processes a Business Message. 
 * Must choose one of "FIId", "OrgId".
 * Here, "FIId" was implemented.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)  // Exclude fields with null values
public class To {
    @JsonProperty("FIId")
    @JsonInclude(JsonInclude.Include.NON_NULL)  // Exclude this field if its value is null
    FIId FIId=null;  // BICFI (Bank Identifier Code)

    @JsonProperty("OrgId")  // "OrgId" is the name of the field in the JSON
    @JsonInclude(JsonInclude.Include.NON_NULL)  // Exclude this field if its value is null
    IDOrgID IDOrgID=null;

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=To.class.getName();


	/**
	 * @return the FIId object
	 */
    public FIId getFIId() {
        return FIId;
    }


	/**
	 * @param fIId the FIId to be set<br>
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
	public IDOrgID getIDOrgID() {
        return IDOrgID;
    }


    /**
     * @param IDOrgID the IDOrgID to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setIDOrgID(IDOrgID IDOrgID) {
        if (IDOrgID == null ) {
            throw new IllegalArgumentException("Wrong parameter 'IDOrgID' in " +  FULLY_QUALIFIED_CLASSNAME + ".setIDOrgID()" + "\n");
        }
        this.IDOrgID = IDOrgID;
    }


    public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


}

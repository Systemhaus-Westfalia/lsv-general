package org.shw.lsv.ebanking.bac.sv.misc;

import org.shw.lsv.ebanking.bac.sv.handling.Camt052RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

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


    public Fr() {}

    public Fr(Camt052RequestParams params, String context, JsonValidationExceptionCollector collector) {
        try {
            // TODO: Sicherstellen, daß es nur eines der beiden geht!
            setFIId(new FIId(params, context, collector), collector);
            
            //setOrgId(new OrgId(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_FR_INIT, e);
        }
    }


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
        if (fIId == null) {
            throw new IllegalArgumentException("Wrong parameter 'fIId' in setFIId()");
        }
        this.FIId = fIId;
    }

    /**
     * @param fIId the FIId object to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setFIId(FIId fIId, JsonValidationExceptionCollector collector) {
        try {
            setFIId(fIId);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
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


    public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


}

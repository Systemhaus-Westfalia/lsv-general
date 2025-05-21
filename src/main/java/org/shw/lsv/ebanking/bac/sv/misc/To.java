package org.shw.lsv.ebanking.bac.sv.misc;

import org.shw.lsv.ebanking.bac.sv.handling.Camt052RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

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
    FIId fIId=null;  // BICFI (Bank Identifier Code)

    @JsonProperty("OrgId")  // "OrgId" is the name of the field in the JSON
    @JsonInclude(JsonInclude.Include.NON_NULL)  // Exclude this field if its value is null
    IDOrgID iDOrgID=null;


	public To() {
    }

    public To(Camt052RequestParams params, String context, JsonValidationExceptionCollector collector) {
        try {
            // TODO: Sicherstellen, daß es nur eines der beiden geht!
            if ( !(params.getBicfiFr() == null || params.getBicfiFr().isEmpty()) ) {
                setFIId(new FIId(params, context, collector), collector);
            }
            else {
                setIDOrgID(new IDOrgID(params, context, collector), collector);
            }
            
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_FR_INIT, e);
        }
    }

    /**
	 * @return the FIId object
	 */
    public FIId getfIId() {
        return fIId;
    }

    /**
     * @param fIId the FIId to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setfIId(FIId fIId) {
        if (fIId == null) {
            throw new IllegalArgumentException("Wrong parameter 'fIId' in setFIId()");
        }
        this.fIId = fIId;
    }

    /**
     * @param fIId the FIId to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setFIId(FIId fIId, JsonValidationExceptionCollector collector) {
        try {
            setfIId(fIId);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }
    

    /**
     * @return the OrgId object<br>
     */
	public IDOrgID getiDOrgID() {
        return iDOrgID;
    }


    /**
     * @param IDOrgID the IDOrgID to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setiDOrgID(IDOrgID IDOrgID) {
        if (IDOrgID == null) {
            throw new IllegalArgumentException("Wrong parameter 'IDOrgID' in setIDOrgID()");
        }
        this.iDOrgID = IDOrgID;
    }

    /**
     * @param IDOrgID the IDOrgID to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setIDOrgID(IDOrgID IDOrgID, JsonValidationExceptionCollector collector) {
        try {
            setiDOrgID(IDOrgID);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }


    public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


}

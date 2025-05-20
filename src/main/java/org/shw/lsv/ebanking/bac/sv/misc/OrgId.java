package org.shw.lsv.ebanking.bac.sv.misc;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.Camt052RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;



/**
 * Either AnyBIC or PtyIdOthr
*/
@JsonInclude(JsonInclude.Include.NON_NULL)// Exclude fields with null values
public class OrgId {
    
    @JsonProperty("AnyBIC")
	String AnyBIC=null;        // Choice AcctOwnr_OrgId_1: Business identification code of the organisation.

    @JsonProperty("Othr")      // "Othr" is the name of the field in the JSON
    IdOthr IdOthr=null;  // Choice AcctOwnr_OrgId_2: Identification assigned by an institution.


    public OrgId() {
    }


    public OrgId(Camt052RequestParams params, String context, JsonValidationExceptionCollector collector) {
        
		try {
            if ( !(params.getAnyBIC() == null || params.getAnyBIC().isEmpty()) ) {
                setAnyBIC(params.getAnyBIC(), collector);
            }
            else {
                setIdOthr(new IdOthr(params, collector), collector);
            }

			
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_ORGID_INIT, e);
        }
    }


    /**
	 * @return the AnyBIC
	 */
	public String getAnyBIC() {
        return AnyBIC;
    }


    /**
     * @param anyBIC the AnyBIC to be set<br>
     * The parameter is validated.<br>
     * Pattern: "[A-Z0-9]{4,4}[A-Z]{2,2}[A-Z0-9]{2,2}([A-Z0-9]{3,3}){0,1}".<br>
     * Example: "BSNJCRSJ".
     */
    public void setAnyBIC(String anyBIC) {
        boolean patternOK = (anyBIC != null && !anyBIC.isEmpty()) && Pattern.matches(EBankingConstants.PATTERN_ANYBIC, anyBIC);

        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'anyBIC' (" + anyBIC + ") in setAnyBIC()");
        }
        this.AnyBIC = anyBIC;
    }

    /**
     * @param anyBIC the AnyBIC to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setAnyBIC(String anyBIC, JsonValidationExceptionCollector collector) {
        try {
            setAnyBIC(anyBIC);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
            //throw e;
        }
    }

	public IdOthr getIdOthr() {
        return IdOthr;
    }


    /**
     * @param idOthr the IdOthr to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setIdOthr(IdOthr idOthr) {
        if (idOthr == null) {
            throw new IllegalArgumentException("Wrong parameter 'idOthr' in setIdOthr()");
        }
        this.IdOthr = idOthr;
    }

    /**
     * @param idOthr the IdOthr to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setIdOthr(IdOthr idOthr, JsonValidationExceptionCollector collector) {
        try {
            setIdOthr(idOthr);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
}

}

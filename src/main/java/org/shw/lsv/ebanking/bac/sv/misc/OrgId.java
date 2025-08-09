package org.shw.lsv.ebanking.bac.sv.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;



/**
 * Either AnyBIC or PtyIdOthr
*/
@JsonInclude(JsonInclude.Include.NON_NULL)// Exclude fields with null values
public class OrgId {
    
    @JsonProperty("AnyBIC")
    @JsonInclude(JsonInclude.Include.NON_NULL)  // Exclude fields with null values
	String anyBIC=null;        // Choice AcctOwnr_OrgId_1: Business identification code of the organisation.

    // In der Schema-Definitionen kann "IdOthr" sowohl ein einzelnes Objekt wie ein Array eines Objektes sein;
    // deswegen habe ich hier beides im folgenden als Java-, aber nicht als Json-Properties angelegt.
    // Ansonsten gibt es Runtime-Fehler.
    // Das richtige Objekt wird angelegt und spaeter serialisiert sei es  als Element oder als Array.
    @JsonInclude(JsonInclude.Include.NON_NULL)  // Exclude fields with null values
    IdOthr idOthr=null;  // Choice AcctOwnr_OrgId_2: Identification assigned by an institution.

    // New field for array representation, used for specific schemas like PAIN.001
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<IdOthr> othrList = null;


    public OrgId() { }

    public OrgId(RequestParams params, String context, JsonValidationExceptionCollector collector) {
		try {
            if ( !(params.getAnyBIC() == null || params.getAnyBIC().isEmpty()) ) {
                // Hier darf man bei Payments nicht gelangen!!
                setAnyBIC(params.getAnyBIC(), collector);
            } else {
                // For PAIN.001's Creditor/Debtor, the schema expects an array for "Othr".
                // For other contexts, it expects a single object.
                if (EBankingConstants.CONTEXT_CDTR.equals(context) || EBankingConstants.CONTEXT_DBTR.equals(context)) {
                    List<IdOthr> tempList = new ArrayList<>();
                    tempList.add(new IdOthr(params, context, collector));
                    setOthrList(tempList, collector);
                } else {
                    setIdOthr(new IdOthr(params, context, collector), collector);
                }
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_ORGID_INIT, e);
        }
    }

    /**
     * Virtual property for Jackson serialization.
     * This method resolves the conflict by providing a single source for the "Othr" property.
     * It returns either the List or the single object, whichever is populated.
     * @return The object to be serialized as "Othr".
     */
    @JsonProperty("Othr")
    public Object getOthr() {
        if (othrList != null) {
            return othrList;
        }
        return idOthr;
    }

    /**
	 * @return the AnyBIC
	 */
	public String getAnyBIC() {
        return anyBIC;
    }


    /**
     * @param anyBIC the AnyBIC to be set<br>
     * The parameter is validated.<br>
     * Pattern: "[A-Z0-9]{4,4}[A-Z]{2,2}[A-Z0-9]{2,2}([A-Z0-9]{3,3}){0,1}".<br>
     * Example: "BSNJCRSJ".
     */
    public void setAnyBIC(String anyBIC) {
        boolean patternOK = (anyBIC != null && !anyBIC.isEmpty()) && Pattern.matches(EBankingConstants.PATTERN_BIC, anyBIC);

        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'anyBIC' (" + anyBIC + ") in setAnyBIC()");
        }
        this.anyBIC = anyBIC;
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

    @JsonIgnore // Hide this from Jackson to prevent conflict with the virtual getOthr()
	public IdOthr getIdOthr() {
        return idOthr;
    }


    /**
     * @param idOthr the IdOthr to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setIdOthr(IdOthr idOthr) {
        if (idOthr == null) {
            throw new IllegalArgumentException("Wrong parameter 'idOthr' in setIdOthr()");
        }
        this.idOthr = idOthr;
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

    /**
     * @return the othrList
     */
    @JsonIgnore // Hide this from Jackson to prevent conflict with the virtual getOthr()
    public List<IdOthr> getOthrList() {
        return othrList;
    }

    /**
     * @param othrList the othrList to be set<br>
     * The parameter is validated: null or empty not allowed.<br>
     */
    public void setOthrList(List<IdOthr> othrList) {
        if (othrList == null || othrList.isEmpty()) {
            throw new IllegalArgumentException("Wrong parameter 'othrList' in setOthrList()");
        }
        this.othrList = othrList;
    }

    /**
     * @param othrList the othrList to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setOthrList(List<IdOthr> othrList, JsonValidationExceptionCollector collector) {
        try {
            setOthrList(othrList);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}

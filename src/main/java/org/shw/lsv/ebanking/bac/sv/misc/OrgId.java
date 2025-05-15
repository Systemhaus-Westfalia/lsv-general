package org.shw.lsv.ebanking.bac.sv.misc;

import java.util.regex.Pattern;

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
	String AnyBIC=null;        // Choice AcctOwnr_OrgId_1: Business identification code of the organisation.

    @JsonProperty("Othr")      // "Othr" is the name of the field in the JSON
    IdOthr IdOthr=null;  // Choice AcctOwnr_OrgId_2: Identification assigned by an institution.

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=OrgId.class.getName();


    /**
	 * @return the AnyBIC
	 */
	public String getAnyBIC() {
        return AnyBIC;
    }


	/**
	 * @param anyBIC the AnyBIC to be set.
	 * The parameter is validated.
	 * "pattern" : "[A-Z0-9]{4,4}[A-Z]{2,2}[A-Z0-9]{2,2}([A-Z0-9]{3,3}){0,1}".
     * e.g.: "BSNJCRSJ".
	 */
    public void setAnyBIC(String anyBIC, JsonValidationExceptionCollector collector) {
        try {
            final String PATTERN = "[A-Z0-9]{4,4}[A-Z]{2,2}[A-Z0-9]{2,2}([A-Z0-9]{3,3}){0,1}";
            boolean patternOK = (anyBIC != null && !anyBIC.isEmpty()) && Pattern.matches(PATTERN, anyBIC);

            if (!patternOK) {
                throw new IllegalArgumentException(
                    "Wrong parameter 'anyBIC' (" + anyBIC + ") in " + FULLY_QUALIFIED_CLASSNAME + ".setAnyBIC()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setAnyBIC()";
            collector.addError(context, e);

            throw e;
        }

        this.AnyBIC = anyBIC;
    }

	public IdOthr getIdOthr() {
        return IdOthr;
    }


    /**
    * @param idOthr the IdOthr to be set.
    * The parameter is validated: null not allowed.
    */
    public void setIdOthr(IdOthr idOthr, JsonValidationExceptionCollector collector) {
        try {
            if (idOthr == null) {
                throw new IllegalArgumentException(
                    "Wrong parameter 'idOthr' in " + FULLY_QUALIFIED_CLASSNAME + ".setIdOthr()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setIdOthr()";
            collector.addError(context, e);

            throw e;
        }

        this.IdOthr = idOthr;
    }

}

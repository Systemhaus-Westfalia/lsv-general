package org.shw.lsv.ebanking.bac.sv.utils;

import java.util.regex.Pattern;

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
	 * @param anyBIC the AnyBIC to be set<br>
	 * The parameter is validated.<br>
	 * "pattern" : "[A-Z0-9]{4,4}[A-Z]{2,2}[A-Z0-9]{2,2}([A-Z0-9]{3,3}){0,1}"
     * e.g.: "BSNJCRSJ"
	 */
    public void setAnyBIC(String anyBIC) {
		final String PATTERN = "[A-Z0-9]{4,4}[A-Z]{2,2}[A-Z0-9]{2,2}([A-Z0-9]{3,3}){0,1}";
		boolean patternOK = (anyBIC!=null && !anyBIC.isEmpty()) && Pattern.matches(PATTERN, anyBIC);
		
		if(patternOK)
			this.AnyBIC = anyBIC;
		else
	        throw new IllegalArgumentException("Wrong parameter 'Id' (" + anyBIC +  ") in " +  FULLY_QUALIFIED_CLASSNAME + ".setAnyBIC()" + "\n");
    }

	public IdOthr getIdOthr() {
        return IdOthr;
    }


    public void setIdOthr(IdOthr idOthr) {
        if (idOthr == null ) {
            throw new IllegalArgumentException("Wrong parameter 'idOthr' in " +  FULLY_QUALIFIED_CLASSNAME + ".setIdOthr()" + "\n");
        }
        this.IdOthr = idOthr;
    }

}

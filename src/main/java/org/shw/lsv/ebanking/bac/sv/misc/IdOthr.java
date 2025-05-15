package org.shw.lsv.ebanking.bac.sv.misc;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.camt052.Request.SchmeNm;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IdOthr {
    @JsonProperty("Id")
    String Id;  // Identification assigned by an institution.

    @JsonProperty("SchmeNm")
	SchmeNm SchmeNm;

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=IdOthr.class.getName();


	/**
	 * @return the Id
	 */
	public String getId() {
        return Id;
    }


	/**
	 * @param id the Id to be set.
	 * The parameter is validated.
	 * "pattern" : "[0-9a-zA-Z/\\\\-\\?:\\(\\)\\.,'\\+ ]+".
     * e.g.: "ALIASXXX".
	 */
    public void setId(String id, JsonValidationExceptionCollector collector) {
        try {
            final String PATTERN = "[0-9a-zA-Z/\\\\-\\?:\\(\\)\\.,'\\+ ]+";
            boolean patternOK = (id != null && !id.isEmpty()) && Pattern.matches(PATTERN, id);

            if (!patternOK) {
                throw new IllegalArgumentException(
                    "Wrong parameter 'Id' (" + id + ") in " + FULLY_QUALIFIED_CLASSNAME + ".setId()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setId()";
            collector.addError(context, e);

            throw e;
        }

        this.Id = id;
    }


	public SchmeNm getSchmeNm() {
		return SchmeNm;
	}


	/**
	 * @param schmeNm the SchmeNm to be set.
	 * The parameter is validated: null not allowed.
	 */
    public void setSchmeNm(SchmeNm schmeNm, JsonValidationExceptionCollector collector) {
        try {
            if (schmeNm == null) {
                throw new IllegalArgumentException(
                    "Wrong parameter 'schmeNm' in " + FULLY_QUALIFIED_CLASSNAME + ".setSchmeNm()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setSchmeNm()";
            collector.addError(context, e);

            throw e;
        }

        this.SchmeNm = schmeNm;
    }
}

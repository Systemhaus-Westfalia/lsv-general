package org.shw.lsv.ebanking.bac.sv.camt052.Request;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SchmeNm {
    @JsonProperty("Prtry")
    String Prtry;  // "Name of the identification scheme, in a free text form."

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=SchmeNm.class.getName();


	/**
	 * @return the Prtry
	 */
    public String getPrtry() {
        return Prtry;
    }


	/**
	 * @param prtry the Prtry to be set.
	 * The parameter is validated.
	 * "minLength" : 1, "maxLength" : 35; null not allowed.
	 * "pattern" : "[0-9a-zA-Z/\\\\-\\?:\\(\\)\\.,'\\+ ]+".
     * e.g.: "BAC_SchmeNm".
	 */
    public void setPrtry(String prtry, JsonValidationExceptionCollector collector) {
        try {
            final int MINLENGTH = 1;
            final int MAXLENGTH = 35;
            final String PATTERN = "[0-9a-zA-Z/\\\\-\\?:\\(\\)\\.,'\\+ ]+";

            int length = (prtry == null || prtry.isEmpty()) ? 0 : prtry.length();
            boolean patternOK = (prtry != null) && Pattern.matches(PATTERN, prtry);

            if (!(length >= MINLENGTH && length <= MAXLENGTH && patternOK)) {
                throw new IllegalArgumentException(
                    "Wrong parameter 'prtry' (" + prtry + ") in " + FULLY_QUALIFIED_CLASSNAME + ".setPrtry()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setPrtry()";
            collector.addError(context, e);

            throw e;
        }

        this.Prtry = prtry;
    }

}

package org.shw.lsv.ebanking.bac.sv.camt060.Request;

import java.util.regex.Pattern;

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
	 * @param prtry the Prtry to be set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 1, "maxLength" : 35; null not allowed.<br>
	 * "pattern" : "[0-9a-zA-Z/\\\\-\\?:\\(\\)\\.,'\\+ ]+"
     * e.g.: "BAC_SchmeNm"
	 */
    public void setId(String prtry) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 35;
		int length = (prtry==null || prtry.isEmpty())?0:prtry.length();

		final String PATTERN = "[0-9a-zA-Z/\\\\-\\?:\\(\\)\\.,'\\+ ]+";
		boolean patternOK = (prtry!=null) && Pattern.matches(PATTERN, prtry);
		
		if((length>=MINLENGTH && length<=MAXLENGTH) && patternOK)
			this.Prtry = prtry;
		else
	        throw new IllegalArgumentException("Wrong parameter 'Id' (" + prtry +  ") in " +  FULLY_QUALIFIED_CLASSNAME + ".setId()" + "\n");
    }

}

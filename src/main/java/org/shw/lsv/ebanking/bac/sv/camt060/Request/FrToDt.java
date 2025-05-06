package org.shw.lsv.ebanking.bac.sv.camt060.Request;

import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FrToDt {
    @JsonProperty("FrDt")
    String FrDt;

    @JsonProperty("ToDt")
    String ToDt;

    @JsonIgnore
    final String fullyQualifiedClassName=FrToDt.class.getName();


	/**
	 * @return the FrDt
	 */
    public String getFrDt() {
        return FrDt;
    }


	/**
	 * @param frDt the FrDt to be set<br>
	 * The parameter is validated.<br>
     * pattern copilot: "^\d{4}-\d{2}-\d{2}$"
     * e.g.: "2020-09-08"
	 */
    public void setFrDt(String frDt) {
		final String PATTERN = "^\\d{4}-\\d{2}-\\d{2}$";
		boolean patternOK = (frDt!=null) && Pattern.matches(PATTERN, frDt);
		
		if(patternOK)
			this.FrDt = frDt;
		else
	        throw new IllegalArgumentException("Wrong parameter 'frDt' (" + frDt +  ") in " +  fullyQualifiedClassName + ".setFrDt()" + "\n");
    }


	/**
	 * @return the ToDt
	 */
    public String getToDt() {
        return ToDt;
    }


	/**
	 * @param toDt the FrDt to be set<br>
	 * The parameter is validated.<br>
     * pattern copilot: "^\d{4}-\d{2}-\d{2}$"
     * e.g.: "2020-09-08"
	 */
    public void setToDt(String toDt) {
		final String PATTERN = "^\\d{4}-\\d{2}-\\d{2}$";
		boolean patternOK = (toDt!=null) && Pattern.matches(PATTERN, toDt);
		
		if(patternOK)
			this.ToDt = toDt;
		else
	        throw new IllegalArgumentException("Wrong parameter 'toDt' (" + toDt +  ") in " +  fullyQualifiedClassName + ".setToDt()" + "\n");
    }

}

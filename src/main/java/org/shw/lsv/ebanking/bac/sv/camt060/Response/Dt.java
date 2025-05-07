package org.shw.lsv.ebanking.bac.sv.camt060.Response;

import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Dt {
                      
    @JsonProperty(value = "DtTm", required = true)
    String DtTm;  // DateTime with timezone

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=Dt.class.getName();


    /*
     * Constructor with parameters
     * For using the Constructor at deserialization time, it has to be of the form:
     * public Dt(@JsonProperty(value = "DtTm", required = true) String dtTm,.....)
     */
    public Dt(String dtTm) {
        setDtTm(dtTm);
    }


	/**
	 * @return the DtTm
	 */
    public String getDtTm() {
        return DtTm;
    }


	/**
	 * @param dtTm the DtTm to be set<br>
	 * The parameter is validated.<br>
	 * "pattern" : ".*(+|-)((0[0-9])|(1[0-3])):[0-5][0-9]"
     * pattern copilot: "^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}[+-]([0][0-9]|[1][0-1]):[0-5][0-9])$"
     * e.g.: "2020-09-08T18:00:00+02:00"
	 */
    public void setDtTm(String dtTm) {
		final String PATTERN = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}[+-]([0][0-9]|[1][0-1]):[0-5][0-9])$";
		boolean patternOK = (dtTm!=null) && Pattern.matches(PATTERN, dtTm);
		
		if(patternOK)
			this.DtTm = dtTm;
		else
	        throw new IllegalArgumentException("Wrong parameter 'creDt' (" + dtTm +  ") in " +  FULLY_QUALIFIED_CLASSNAME + ".setCreDt()" + "\n");
    }


}

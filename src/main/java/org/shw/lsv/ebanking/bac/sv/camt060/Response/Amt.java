package org.shw.lsv.ebanking.bac.sv.camt060.Response;

import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Amt {

    @JsonProperty("Ccy")
    String Ccy= null;  // ToDo: check actual Ccy values.

    @JsonProperty("Amt")
    String Amt= null;  // ToDo: check actual Amt pattern,

    @JsonIgnore
    final String fullyQualifiedClassName=Amt.class.getName();


    /**
     * @return the Ccy object<br>
     */
    public String getCcy() {
        return Ccy;
    }

    /**
     * @param ccy the Ccy to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setCcy(String ccy) {
        if (ccy == null ) {
            throw new IllegalArgumentException("Wrong parameter 'ccy' (" + ccy + ") in " +  fullyQualifiedClassName + ".setCcy()" + "\n");
        }
        this.Ccy = ccy;
    }

    /**
     * @return the Amt<br>
     */
    public String getAmt() {
        return Amt;
    }


	/**
	 * @param amt the Amt to be set<br>
	 * The parameter is validated.<br>
     * pattern copilot: "^\d+\.\d{2}$"
     * e.g.: "999994769.99"
	 */
    public void setAmt(String amt) {
        final String PATTERN = "^\\d+\\.\\d{2}$";
		boolean patternOK = (amt!=null) && Pattern.matches(PATTERN, amt);
		
		if(patternOK)
			this.Amt = amt;
		else
	        throw new IllegalArgumentException("Wrong parameter 'amt' (" + amt +  ") in " +  fullyQualifiedClassName + ".setAmt()" + "\n");
    }

    

}

package org.shw.lsv.ebanking.bac.sv.camt060.Response;

import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Amt {
                      
    @JsonProperty("Ccy")  // "Ccy" is optional in the JSON definition
    String Ccy= null;
                      
    @JsonProperty(value = "Amt", required = true)
    String Amt= null;

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=Amt.class.getName();

   /*
    * Constructor with parameters
    * For using the Constructor at deserialization time, it has to be of the form:
    * public Amt(@JsonProperty(value = "Ccy", required = true) String ccy,.....)
    */
    public Amt(String ccy, String amt) {
        setCcy(ccy);
        setAmt(amt);
    }

    
    public Amt(String amt) {
        setAmt(amt);
    }


    /**
     * @return the Ccy object<br>
     */
    public String getCcy() {
        return Ccy;
    }


    /**
     * @param ccy the Ccy to be set<br>
     * The parameter is validated: null not allowed.<br>
     * pattern BAC onboarding documentation: "[A-Z]{3,3}"
     * e.g.: "EUR", "USD", "GBP"
     */
    public void setCcy(String ccy) {
        final String PATTERN = "^\\d+\\.\\d{2}$";
		boolean patternOK = (ccy!=null) && Pattern.matches(PATTERN, ccy);
		
		if(patternOK)
            this.Ccy = ccy;
		else
            throw new IllegalArgumentException("Wrong parameter 'ccy' (" + ccy + ") in " +  FULLY_QUALIFIED_CLASSNAME + ".setCcy()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'amt' (" + amt +  ") in " +  FULLY_QUALIFIED_CLASSNAME + ".setAmt()" + "\n");
    }

    

}

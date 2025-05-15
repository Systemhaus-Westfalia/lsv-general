package org.shw.lsv.ebanking.bac.sv.camt052.Response;

import org.shw.lsv.ebanking.bac.sv.misc.EBankingCentral;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CdOrPrtry extends EBankingCentral {
                      
    @JsonProperty(value = "Cd", required = true)
    String Cd;  // ToDo: check pattern

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME = CdOrPrtry.class.getName();


    /*
     * Constructor with parameters
     * For using the Constructor at deserialization time, it has to be of the form:
     * public CdOrPrtry(@JsonProperty(value = "Cd", required = true) Cd cd,.....)
     */
    public CdOrPrtry(String cd) {
        setCd(cd);
    }


    /**
     * @return the Cd object<br>
     */
    public String getCd() {
        return Cd;
    }


    /**
     * @param cd the Cd to be set<br>
     * The parameter is validated: null not allowed.
     * And must equal one of the following values: [ITAV, ITBD, OPAV, OPBD]
     */
    public void setCd(String cd) {
        if ( (cd == null || cd.isEmpty()) || 
             (
                !( cd.equals(ITAV) || cd.equals(ITBD) || cd.equals(OPAV) || cd.equals(OPBD) )
             )
            ) {
            throw new IllegalArgumentException("Wrong parameter 'cd' (" + cd + ")  in " +  FULLY_QUALIFIED_CLASSNAME + ".setCd() \n");
        }
        this.Cd = cd;
    }



}

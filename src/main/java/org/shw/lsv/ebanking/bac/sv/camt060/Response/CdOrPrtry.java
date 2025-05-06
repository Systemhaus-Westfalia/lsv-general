package org.shw.lsv.ebanking.bac.sv.camt060.Response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CdOrPrtry {

    @JsonProperty("Cd")
    String Cd;  // ToDo: check pattern

    @JsonIgnore
    final String fullyQualifiedClassName = CdOrPrtry.class.getName();


    /**
     * @return the Cd object<br>
     */
    public String getCd() {
        return Cd;
    }


    /**
     * @param cd the Cd to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setCd(String cd) {
        if (cd == null || cd.isEmpty()) {
            throw new IllegalArgumentException("Wrong parameter 'cd' (" + cd + ")  in " +  fullyQualifiedClassName + ".setCd() \n");
        }
        this.Cd = cd;
    }



}

package org.shw.lsv.ebanking.bac.sv.camt060.Response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Tp {

    @JsonProperty("CdOrPrtry")
    CdOrPrtry CdOrPrtry;

    @JsonIgnore
    final String fullyQualifiedClassName=Tp.class.getName();


    /**
     * @return the CdOrPrtry object<br>
     */
    public CdOrPrtry getCdOrPrtry() {
        return CdOrPrtry;
    }


    /**
     * @param cdOrPrtry the CdOrPrtry to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setCdOrPrtry(CdOrPrtry cdOrPrtry) {
        if (cdOrPrtry == null) {
            throw new IllegalArgumentException("Wrong parameter 'cdOrPrtry' in " +  fullyQualifiedClassName + ".setCdOrPrtry() \n");
        }
        this.CdOrPrtry = cdOrPrtry;
    }



}

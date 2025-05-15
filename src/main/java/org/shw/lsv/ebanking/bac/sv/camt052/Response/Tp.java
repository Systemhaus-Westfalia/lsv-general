package org.shw.lsv.ebanking.bac.sv.camt052.Response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Tp {

    @JsonProperty("CdOrPrtry")
    CdOrPrtry CdOrPrtry;

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=Tp.class.getName();


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
            throw new IllegalArgumentException("Wrong parameter 'cdOrPrtry' in " +  FULLY_QUALIFIED_CLASSNAME + ".setCdOrPrtry() \n");
        }
        this.CdOrPrtry = cdOrPrtry;
    }



}

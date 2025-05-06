package org.shw.lsv.ebanking.bac.sv.camt060.Request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RptgPrd {
    @JsonProperty("FrToDt")
    FrToDt FrToDt;

    @JsonProperty("Tp")
    String Tp;

    @JsonIgnore
    final String fullyQualifiedClassName=RptgPrd.class.getName();


    public FrToDt getFrToDt() {
        return FrToDt;

    }


    /**
     * @param frToDt the FrToDt to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setFrToDt(FrToDt frToDt) {
        if (frToDt == null ) {
            throw new IllegalArgumentException("Wrong parameter 'frToDt' in " +  fullyQualifiedClassName + ".setFrToDt()" + "\n");
        }
        this.FrToDt = frToDt;
    }


    public String getTp() {
        return Tp;
    }


    /**
     * @param tp the Tp to be set<br>
     * The parameter is validated: null not allowed.<br>
     * Matching items to be reported. Available options: ALLL (all elements).
     * example: "ALLL"
     */
    public void setTp(String tp) {
        if (tp == null || tp.isEmpty()) {
            throw new IllegalArgumentException("Wrong parameter 'tp' (" + tp + ") in " +  fullyQualifiedClassName + ".setTp()" + "\n");
        }
        this.Tp = tp;
    }

}   

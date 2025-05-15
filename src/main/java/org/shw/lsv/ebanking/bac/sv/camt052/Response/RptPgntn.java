package org.shw.lsv.ebanking.bac.sv.camt052.Response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RptPgntn {
                      
    @JsonProperty(value = "PgNb", required = true)
    String PgNb;

                      
    @JsonProperty(value = "LastPgInd", required = true)
    String LastPgInd;

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=RptPgntn.class.getName();

    /**
	 * @param pgNb the PgNb to be set<br>
	 * @param lastPgInd the LastPgInd to be set<br>
	 * Both @pgNb and @lastPgInd are mandatory in the JSON definition.
     * 
     * Constructor with parameters
     * For using the Constructor at deserialization time, it has to be of the form:
     * public RptPgntn(@JsonProperty(value = "PgNb", required = true) String pgNb,.....)
     */
    public RptPgntn(String pgNb, String lastPgInd) {
        setPgNb(pgNb);
        setLastPgInd(lastPgInd);
    }
    

    public String getPgNb() {
        return PgNb;
    }

    public void setPgNb(String pgNb) {
        if (PgNb == null || PgNb.isEmpty()) {
            throw new IllegalArgumentException("Wrong parameter 'PgNb' (" + PgNb + ") in " +  FULLY_QUALIFIED_CLASSNAME + ".setPgNb()" + "\n");
        }
        this.PgNb = pgNb;
    }

    public String getLastPgInd() {
        return LastPgInd;
    }

    /**
     * @param lastPgInd the LastPgInd to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setLastPgInd(String lastPgInd) {
        if (LastPgInd == null || LastPgInd.isEmpty()) {
            throw new IllegalArgumentException("Wrong parameter 'LastPgInd' (" + LastPgInd + ") in " +  FULLY_QUALIFIED_CLASSNAME + ".setLastPgInd()" + "\n");
        }
        this.LastPgInd = lastPgInd;
    }

    
}

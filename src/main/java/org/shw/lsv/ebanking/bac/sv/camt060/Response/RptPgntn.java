package org.shw.lsv.ebanking.bac.sv.camt060.Response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RptPgntn {

    @JsonProperty("PgNb")
    String PgNb;                // ToDo: check the correct values in the JSON

    @JsonProperty("LastPgInd")  // ToDo: check the correct values in the JSON
    String LastPgInd;

    @JsonIgnore
    final String fullyQualifiedClassName=RptPgntn.class.getName();
    

    public String getPgNb() {
        return PgNb;
    }

    public void setPgNb(String pgNb) {
        if (PgNb == null || PgNb.isEmpty()) {
            throw new IllegalArgumentException("Wrong parameter 'PgNb' (" + PgNb + ") in " +  fullyQualifiedClassName + ".setPgNb()" + "\n");
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
            throw new IllegalArgumentException("Wrong parameter 'LastPgInd' (" + LastPgInd + ") in " +  fullyQualifiedClassName + ".setLastPgInd()" + "\n");
        }
        this.LastPgInd = lastPgInd;
    }

    
}

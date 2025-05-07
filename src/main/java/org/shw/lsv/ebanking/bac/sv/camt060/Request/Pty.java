package org.shw.lsv.ebanking.bac.sv.camt060.Request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Pty {
    
    @JsonProperty("Id")    // "Id" is the name of the field in the JSON
    PtyId PtyId;

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=PtyId.class.getName();


    /**
     * @return the PtyId object<br>
     */
    public PtyId getPtyId() {
        return PtyId;
    }

    /**
     * @param ptyId the PtyId to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setPtyId(PtyId ptyId) {
        if (ptyId == null ) {
            throw new IllegalArgumentException("Wrong parameter 'ptyId' in " +  FULLY_QUALIFIED_CLASSNAME + ".setPtyId()" + "\n");
        }
        this.PtyId = ptyId;
    }
}

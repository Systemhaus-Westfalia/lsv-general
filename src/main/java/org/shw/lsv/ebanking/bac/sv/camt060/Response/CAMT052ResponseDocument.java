package org.shw.lsv.ebanking.bac.sv.camt060.Response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 */
public class CAMT052ResponseDocument {

    @JsonProperty("BkToCstmrAcctRpt") 
    BkToCstmrAcctRpt BkToCstmrAcctRpt;

    @JsonIgnore
    final String fullyQualifiedClassName=CAMT052ResponseDocument.class.getName();

        
    /**
    * @return the BkToCstmrAcctRpt object<br>
    */
    public BkToCstmrAcctRpt getBkToCstmrAcctRpt() {
        return BkToCstmrAcctRpt;
    }


    /**
     * @param bkToCstmrAcctRpt the BkToCstmrAcctRpt to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setBkToCstmrAcctRpt(BkToCstmrAcctRpt bkToCstmrAcctRpt) {
        if (bkToCstmrAcctRpt == null) {
            throw new IllegalArgumentException("Wrong parameter 'bkToCstmrAcctRpt' in " +  fullyQualifiedClassName + ".setBkToCstmrAcctRpt()" + "\n");
        }
        this.BkToCstmrAcctRpt   = bkToCstmrAcctRpt;
    }

}

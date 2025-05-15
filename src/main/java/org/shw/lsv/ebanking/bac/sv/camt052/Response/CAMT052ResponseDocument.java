package org.shw.lsv.ebanking.bac.sv.camt052.Response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 */
public class CAMT052ResponseDocument {

    @JsonProperty("BkToCstmrAcctRpt") 
    BkToCstmrAcctRpt BkToCstmrAcctRpt;

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=CAMT052ResponseDocument.class.getName();

        
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
            throw new IllegalArgumentException("Wrong parameter 'bkToCstmrAcctRpt' in " +  FULLY_QUALIFIED_CLASSNAME + ".setBkToCstmrAcctRpt()" + "\n");
        }
        this.BkToCstmrAcctRpt   = bkToCstmrAcctRpt;
    }

}

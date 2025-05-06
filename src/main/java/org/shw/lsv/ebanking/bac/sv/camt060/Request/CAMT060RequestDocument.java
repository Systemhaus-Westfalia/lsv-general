    package org.shw.lsv.ebanking.bac.sv.camt060.Request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 */
public class CAMT060RequestDocument {

    @JsonProperty("AcctRptgReq")  // "AcctRptgReq" is the name of the field in the JSON
    AcctRptgReq AcctRptgReq;

    @JsonIgnore
    final String fullyQualifiedClassName=CAMT060RequestDocument.class.getName();

        
    /**
    * @return the AcctRptgReq object<br>
    */
    public AcctRptgReq getAcctRptgReq() {
        return AcctRptgReq;
    }


    /**
     * @param acctRptgReq the AcctRptgReq to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setAcctRptgReq(AcctRptgReq acctRptgReq) {
        if (acctRptgReq == null ) {
            throw new IllegalArgumentException("Wrong parameter 'acctRptgReq' in " +  fullyQualifiedClassName + ".setAcctRptgReq()" + "\n");
        }
        this.AcctRptgReq = acctRptgReq;
    }

}

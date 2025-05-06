package org.shw.lsv.ebanking.bac.sv.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
* This class may be used with or without field "Ccy".
* When "Ccy" is not used, it should be set (=keep) to null.
* When "Ccy" is used, it should be set to a valid value.
*/
public class Acct {

    @JsonProperty("Id")   // "Id" is the name of the field in the JSON
    AcctId AcctId;

    @JsonProperty("Ccy")
    @JsonInclude(JsonInclude.Include.NON_NULL)// Exclude fields with null values
    String Ccy= null;  // ToDo: check actual Ccy values.

    @JsonIgnore
    final String fullyQualifiedClassName=Acct.class.getName();


    /**
     * @return the AcctId object<br>
     */
    public AcctId getAcctId() {
        return AcctId;
    }
    

    /**
     * @param acctId the AcctId to be set<br>
	 * The parameter is validated: null not allowed.<br>
     */
    public void setAcctId(AcctId acctId) {
        if (acctId == null ) {
            throw new IllegalArgumentException("Wrong parameter 'acctId' in " +  fullyQualifiedClassName + ".setAcctId()" + "\n");
        }
        this.AcctId = acctId;
    }


    /**
     * @return the Ccy object<br>
     */
    public String getCcy() {
        return Ccy;
    }

    /**
     * @param ccy the Ccy to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setCcy(String ccy) {
        if (ccy == null ) {
            throw new IllegalArgumentException("Wrong parameter 'ccy' (" + ccy + ") in " +  fullyQualifiedClassName + ".setCcy()" + "\n");
        }
        this.Ccy = ccy;
    }
    
    
}

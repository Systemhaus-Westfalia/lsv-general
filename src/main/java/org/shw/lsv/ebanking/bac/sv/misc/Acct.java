package org.shw.lsv.ebanking.bac.sv.misc;

import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
* This class may be used with or without field "Ccy".
* When "Ccy" is not used, it should be set (=keep) to null.
* When "Ccy" is used, it should be set to a valid value.
*/
public class Acct {

    @JsonProperty("Id")   // "Id" is the name of the field in the JSON
    AcctId acctId;

    @JsonProperty("Ccy")
    @JsonInclude(JsonInclude.Include.NON_NULL)// Exclude fields with null values
    String ccy= null;  // ToDo: check actual Ccy values.


    public Acct() {}


    public Acct(RequestParams params, String context, JsonValidationExceptionCollector collector) {
        try {
            setAcctId (new AcctId( params, context, collector), collector);
            setCcy(params.getCcy(), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_ACCT_INIT, e);
        }
    }


    /**
     * @return the AcctId object<br>
     */
    public AcctId getAcctId() {
        return acctId;
    }


    /**
     * @param acctId the AcctId to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setAcctId(AcctId acctId) {
        if (acctId == null) {
            throw new IllegalArgumentException("Wrong parameter 'acctId' in setAcctId()");
        }
        this.acctId = acctId;
    }

    /**
     * @param acctId the AcctId to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setAcctId(AcctId acctId, JsonValidationExceptionCollector collector) {
        try {
            setAcctId(acctId);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }


    /**
     * @return the Ccy object<br>
     */
    public String getCcy() {
        return ccy;
    }


    /**
     * @param ccy the Ccy to be set.<br>
     * The parameter is validated: null or empty not allowed.<br>
     */
    public void setCcy(String ccy) {
        if (ccy == null || ccy.isEmpty()) {
            throw new IllegalArgumentException("Wrong parameter 'ccy' (" + ccy + ") in setCcy()");
        }
        this.ccy = ccy;
    }

    /**
     * @param ccy the Ccy to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setCcy(String ccy, JsonValidationExceptionCollector collector) {
        try {
            setCcy(ccy);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_EMPTY_OR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }
    
    
}

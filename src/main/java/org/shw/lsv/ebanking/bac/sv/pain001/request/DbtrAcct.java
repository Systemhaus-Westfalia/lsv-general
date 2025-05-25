package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.AcctId;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DbtrAcct {

    @JsonProperty("Id")
    AcctId acctId;

    public DbtrAcct() {}

    public DbtrAcct(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setAcctId (new AcctId(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_DBTRACCT_INIT, e);
        }
    }

    /**
     * @return the DbtrAcctId object<br>
     */
    public AcctId getAcctId() {
        return acctId;
    }

    /**
     * @param acctId the DbtrAcctId to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setAcctId(AcctId acctId) {
        if (acctId == null) {
            throw new IllegalArgumentException("Wrong parameter 'dbtrAcctId' in setAcctId()");
        }
        this.acctId = acctId;
    }

    /**
     * @param acctId the DbtrAcctId to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setAcctId(AcctId acctId, JsonValidationExceptionCollector collector) {
        try {
            setAcctId(acctId);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }

}

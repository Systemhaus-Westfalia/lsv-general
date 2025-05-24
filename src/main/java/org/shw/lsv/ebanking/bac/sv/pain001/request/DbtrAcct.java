package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DbtrAcct {

    @JsonProperty("Id")
    DbtrAcctId dbtrAcctId;

    public DbtrAcct() {}

    public DbtrAcct(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setDbtrAcctId (new DbtrAcctId(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_PMT_INIT, e);
        }
    }

    /**
     * @return the DbtrAcctId object<br>
     */
    public DbtrAcctId getDbtrAcctId() {
        return dbtrAcctId;
    }

    /**
     * @param dbtrAcctId the DbtrAcctId to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setDbtrAcctId(DbtrAcctId dbtrAcctId) {
        if (dbtrAcctId == null) {
            throw new IllegalArgumentException("Wrong parameter 'dbtrAcctId' in setDbtrAcctId()");
        }
        this.dbtrAcctId = dbtrAcctId;
    }

    /**
     * @param dbtrAcctId the DbtrAcctId to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setDbtrAcctId(DbtrAcctId dbtrAcctId, JsonValidationExceptionCollector collector) {
        try {
            setDbtrAcctId(dbtrAcctId);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }

}

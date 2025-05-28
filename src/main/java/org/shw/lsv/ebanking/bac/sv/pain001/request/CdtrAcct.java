package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.misc.AcctId;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CdtrAcct {

    @JsonProperty("Id")   // "Id" is the name of the field in the JSON
    AcctId acctId;

    @JsonProperty("Tp")
    Tp tp;

    public CdtrAcct() {}

    /**
     * @param params    the RequestParams containing the values to set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public CdtrAcct(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setAcctId(new AcctId(params, collector), collector);
            setTp(new Tp(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_CDTRACCT_INIT, e);
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
        }
    }

    /**
     * @return the Tp object<br>
     */
    public Tp getTp() {
        return tp;
    }

    /**
     * @param tp the Tp to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setTp(Tp tp) {
        if (tp == null) {
            throw new IllegalArgumentException("Wrong parameter 'tp' in setTp()");
        }
        this.tp = tp;
    }

    /**
     * @param tp the Tp to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setTp(Tp tp, JsonValidationExceptionCollector collector) {
        try {
            setTp(tp);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}
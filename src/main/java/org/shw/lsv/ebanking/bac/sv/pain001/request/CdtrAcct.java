package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.misc.AcctId;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CdtrAcct {

    @JsonProperty("Id")   // "Id" is the name of the field in the JSON
    AcctId acctId;

    @JsonProperty("Tp")  // "Tp" is the name of the field in the JSON
    CtgyPurp ctgyPurp;   // Type of account or type of purpose

    public CdtrAcct() {}

    /**
     * @param params    the RequestParams containing the values to set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public CdtrAcct(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setAcctId(new AcctId(params, collector), collector);
            setCtgyPurp(new CtgyPurp(params, collector), collector);
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
     * @return the CtgyPurp object<br>
     */
    public CtgyPurp getCtgyPurp() {
        return ctgyPurp;
    }

    /**
     * @param ctgyPurp the CtgyPurp to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setCtgyPurp(CtgyPurp ctgyPurp) {
        if (ctgyPurp == null) {
            throw new IllegalArgumentException("Wrong parameter 'ctgyPurp' in setCtgyPurp()");
        }
        this.ctgyPurp = ctgyPurp;
    }

    /**
     * @param ctgyPurp the CtgyPurp to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setCtgyPurp(CtgyPurp ctgyPurp, JsonValidationExceptionCollector collector) {
        try {
            setCtgyPurp(ctgyPurp);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}
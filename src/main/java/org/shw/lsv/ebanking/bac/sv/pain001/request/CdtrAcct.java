package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.misc.AcctId;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CdtrAcct {
    // TODO: ueberpruefen, ob nur "Id" als Property vorkommt, oder ob es noch andere gibt.

    @JsonProperty("Id")   // "Id" is the name of the field in the JSON
    AcctId acctId;

    public CdtrAcct() {}

    /**
     * @param params    the RequestParams containing the values to set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public CdtrAcct(RequestParams params, String context, JsonValidationExceptionCollector collector) {
        try {
            setAcctId(new AcctId(params, EBankingConstants.CONTEXT_CDTRACCT, collector), collector);
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
}
package org.shw.lsv.ebanking.bac.sv.camt053.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NtryDtls {

    @JsonProperty("TxDtls")
    TxDtls txDtls;  // Transaction Details

    public NtryDtls() {}

    public NtryDtls(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setTxDtls(new TxDtls(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NTRYDTLS_INIT, e);
        }
    }

    /**
     * @return the TxDtls object<br>
     */
    public TxDtls getTxDtls() {
        return txDtls;
    }

    /**
     * @param txDtls the TxDtls to be set.<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setTxDtls(TxDtls txDtls) {
        if (txDtls == null) {
            throw new IllegalArgumentException("Wrong parameter 'txDtls' in setTxDtls()");
        }
        this.txDtls = txDtls;
    }

    /**
     * @param txDtls the TxDtls to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setTxDtls(TxDtls txDtls, JsonValidationExceptionCollector collector) {
        try {
            setTxDtls(txDtls);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}

package org.shw.lsv.ebanking.bac.sv.camt053.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BkTxCd {

    @JsonProperty("Domn")
    Domn domn;

    public BkTxCd() {}

    public BkTxCd(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setDomn(new Domn(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_BKTXCD_INIT, e);
        }
    }

    /**
     * @return the Domn object<br>
     */
    public Domn getDomn() {
        return domn;
    }

    /**
     * @param domn the Domn to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setDomn(Domn domn) {
        if (domn == null) {
            throw new IllegalArgumentException("Wrong parameter 'domn' in setDomn()");
        }
        this.domn = domn;
    }

    /**
     * @param domn the Domn to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setDomn(Domn domn, JsonValidationExceptionCollector collector) {
        try {
            setDomn(domn);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}
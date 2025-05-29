package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Strd {

    @JsonProperty("RfrdDocInf")
    RfrdDocInf rfrdDocInf;  // Referred Document Information.


    public Strd() {}

    public Strd(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setRfrdDocInf(new RfrdDocInf(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_STRD_INIT, e);
        }
    }

    /**
     * @return the RfrdDocInf object<br>
     */
    public RfrdDocInf getRfrdDocInf() {
        return rfrdDocInf;
    }

    /**
     * @param rfrdDocInf the RfrdDocInf to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setRfrdDocInf(RfrdDocInf rfrdDocInf) {
        if (rfrdDocInf == null) {
            throw new IllegalArgumentException("Wrong parameter 'rfrdDocInf' in setRfrdDocInf()");
        }
        this.rfrdDocInf = rfrdDocInf;
    }

    /**
     * @param rfrdDocInf the RfrdDocInf to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setRfrdDocInf(RfrdDocInf rfrdDocInf, JsonValidationExceptionCollector collector) {
        try {
            setRfrdDocInf(rfrdDocInf);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }


}

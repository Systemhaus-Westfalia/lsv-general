package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RmtInf {

    @JsonProperty("Strd")
    Strd strd;

    /**
     * Default constructor for RmtInf.
     */
    public RmtInf() {}

       public RmtInf(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setStrd(new Strd(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_RMTINF_INIT, e);
        }
    }

    /**
     * @return the Strd object<br>
     */
    public Strd getStrd() {
        return strd;
    }

    /**
     * @param strd the Strd to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setStrd(Strd strd) {
        if (strd == null) {
            throw new IllegalArgumentException("Wrong parameter 'strd' in setStrd()");
        }
        this.strd = strd;
    }

    /**
     * @param strd the Strd to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setStrd(Strd strd, JsonValidationExceptionCollector collector) {
        try {
            setStrd(strd);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }

}

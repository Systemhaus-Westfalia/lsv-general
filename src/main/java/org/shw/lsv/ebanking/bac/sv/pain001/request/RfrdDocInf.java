package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RfrdDocInf {
    @JsonProperty("Nb")
    String nb;

        public RfrdDocInf(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setNb(params.getRfrdDocInfNb(), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_RFRDDOCINF_INIT, e);
        }
    }

    /**
     * @return the Nb (Referred Document Number)
     */
    public String getNb() {
        return nb;
    }

    /**
     * @param nb the Nb (Referred Document Number) to be set.<br>
     * The parameter is validated: null not allowed.<br>
     * Example: "INV-112"
     */
    public void setNb(String nb) {
        if (nb == null) {
            throw new IllegalArgumentException("Wrong parameter 'nb' (null) in setNb()");
        }
        this.nb = nb;
    }

    /**
     * @param nb the Nb (Referred Document Number) to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setNb(String nb, JsonValidationExceptionCollector collector) {
        try {
            setNb(nb);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }


}

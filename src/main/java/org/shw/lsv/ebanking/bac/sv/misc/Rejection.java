package org.shw.lsv.ebanking.bac.sv.misc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

/**
 * Models the content of an "admi.002.001.01" rejection message.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rejection {

    @JsonProperty("Rsn")
    private Rsn rsn;

    public Rejection() {
    }

    public Rejection(Rsn rsn) {
        setRsn(rsn);
    }

    public Rsn getRsn() {
        return rsn;
    }

    public void setRsn(Rsn rsn) {
        if (rsn == null) {
            throw new IllegalArgumentException("Wrong parameter 'rsn' in setRsn()");
        }
        this.rsn = rsn;
    }

    public void setRsn(Rsn rsn, JsonValidationExceptionCollector collector) {
        try {
            setRsn(rsn);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}

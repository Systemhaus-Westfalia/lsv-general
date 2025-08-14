package org.shw.lsv.ebanking.bac.sv.misc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

/**
 * Models the content of an "admi.002.001.01" rejection message.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rejection {

    @JsonProperty("RltdRef")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private RltdRef rltdRef;

    @JsonProperty("Rsn")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Rsn rsn;

    public Rejection() {
    }

    public Rejection(Rsn rsn) {
        this(null, rsn);
    }

    public Rejection(RltdRef rltdRef, Rsn rsn) {
        setRltdRef(rltdRef);
        setRsn(rsn);
    }

    public RltdRef getRltdRef() {
        return rltdRef;
    }

    public void setRltdRef(RltdRef rltdRef) {
        // This field is optional, so a null value is acceptable.
        this.rltdRef = rltdRef;
    }

    public void setRltdRef(RltdRef rltdRef, JsonValidationExceptionCollector collector) {
        // No validation needed for this optional field, just set it.
        setRltdRef(rltdRef);
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

package org.shw.lsv.ebanking.bac.sv.camt052.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rsn {

    @JsonProperty("RjctgPtyRsn")
    private String rjctgPtyRsn;

    @JsonProperty("RsnDesc")
    private String rsnDesc;

    public Rsn() {
    }

    /**
     * Constructor with parameters.
     * For using the Constructor at deserialization time, it has to be of the form:
     * public Rsn(@JsonProperty("RjctgPtyRsn") String rjctgPtyRsn, @JsonProperty("RsnDesc") String rsnDesc)
     */
    public Rsn(String rjctgPtyRsn, String rsnDesc) {
        setRjctgPtyRsn(rjctgPtyRsn);
        setRsnDesc(rsnDesc);
    }

    /**
     * @return the RjctgPtyRsn
     */
    public String getRjctgPtyRsn() {
        return rjctgPtyRsn;
    }

    /**
     * @param rjctgPtyRsn the RjctgPtyRsn to be set.
     */
    public void setRjctgPtyRsn(String rjctgPtyRsn) {
        // Since this is optional, no validation is performed.
        this.rjctgPtyRsn = rjctgPtyRsn;
    }

    /**
     * @return the RsnDesc
     */
    public String getRsnDesc() {
        return rsnDesc;
    }

    /**
     * @param rsnDesc the RsnDesc to be set.
     */
    public void setRsnDesc(String rsnDesc) {
        // Since this is optional, no validation is performed.
        this.rsnDesc = rsnDesc;
    }

    public void setRjctgPtyRsn(String rjctgPtyRsn, JsonValidationExceptionCollector collector) {
        setRjctgPtyRsn(rjctgPtyRsn);
    }

    public void setRsnDesc(String rsnDesc, JsonValidationExceptionCollector collector) {
        setRsnDesc(rsnDesc);
    }
}

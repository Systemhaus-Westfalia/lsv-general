package org.shw.lsv.ebanking.bac.sv.misc;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true) // Be more robust against new, unknown fields
public class Rsn {

    @JsonProperty("RjctgPtyRsn")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String rjctgPtyRsn;

    @JsonProperty("RsnDesc")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String rsnDesc;

    @JsonProperty("RjctnDtTm")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String rjctnDtTm;

    public Rsn() {
    }

    /**
     * Constructor with parameters.
     * For using the Constructor at deserialization time, it has to be of the form:
     * public Rsn(@JsonProperty("RjctgPtyRsn") String rjctgPtyRsn, @JsonProperty("RsnDesc") String rsnDesc)
     */
    public Rsn(String rjctgPtyRsn, String rsnDesc) {
        this(rjctgPtyRsn, rsnDesc, null);
    }

    public Rsn(String rjctgPtyRsn, String rsnDesc, String rjctnDtTm) {
        setRjctgPtyRsn(rjctgPtyRsn);
        setRsnDesc(rsnDesc);
        setRjctnDtTm(rjctnDtTm);
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

    /**
     * @return the RjctnDtTm
     */
    public String getRjctnDtTm() {
        return rjctnDtTm;
    }

    /**
     * @param rjctnDtTm the RjctnDtTm to be set.
     */
    public void setRjctnDtTm(String rjctnDtTm) {
        this.rjctnDtTm = rjctnDtTm;
    }

    public void setRjctgPtyRsn(String rjctgPtyRsn, JsonValidationExceptionCollector collector) {
        setRjctgPtyRsn(rjctgPtyRsn);
    }

    public void setRsnDesc(String rsnDesc, JsonValidationExceptionCollector collector) {
        setRsnDesc(rsnDesc);
    }

    public void setRjctnDtTm(String rjctnDtTm, JsonValidationExceptionCollector collector) {
        setRjctnDtTm(rjctnDtTm);
    }
}

package org.shw.lsv.ebanking.bac.sv.misc;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Models the "RltdRef" (Related Reference) object found in some API responses,
 * typically containing a reference to the original request.
 */
public class RltdRef {

    @JsonProperty("Ref")
    private String ref;

    public RltdRef() {
    }

    /**
     * Constructor with parameters.
     * For using the Constructor at deserialization time, it has to be of the form:
     * public RltdRef(@JsonProperty("Ref") String ref)
     * @param ref The related reference, often a timestamp or unique ID (e.g., "2025-08-14T10:09:40").
     */
    public RltdRef(String ref) {
        setRef(ref);
    }

    /**
     * @return the Ref
     */
    public String getRef() {
        return ref;
    }

    /**
     * @param ref the Ref to be set.
     */
    public void setRef(String ref) {
        this.ref = ref;
    }

    public void setRef(String ref, JsonValidationExceptionCollector collector) {
        setRef(ref);
    }
}

package org.shw.lsv.ebanking.bac.sv.tmst039.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TMST039Response implements Validatable {

    @JsonProperty("Envelope")    // "Envelope" is the name of the field in the JSON
    TMST039ResponseEnvelope tMST039ResponseEnvelope;

    public TMST039Response() {}

    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (tMST039ResponseEnvelope == null) {
                throw new IllegalArgumentException("Envelope cannot be null");
            }
            if (tMST039ResponseEnvelope instanceof Validatable) {
                ((Validatable) tMST039ResponseEnvelope).validate(collector);
            }
        } catch (Exception e) {
            collector.addError("ERROR_NULL_NOT_ALLOWED", e);
        }
    }

    public TMST039ResponseEnvelope getTMST039ResponseEnvelope() {
        return tMST039ResponseEnvelope;
    }

    public void setTMST039ResponseEnvelope(TMST039ResponseEnvelope tMST039ResponseEnvelope) {
        if (tMST039ResponseEnvelope == null) {
            throw new IllegalArgumentException("Wrong parameter 'tMST039ResponseEnvelope' in setTMST039ResponseEnvelope()");
        }
        this.tMST039ResponseEnvelope = tMST039ResponseEnvelope;
    }

    public void setTMST039ResponseEnvelope(TMST039ResponseEnvelope tMST039ResponseEnvelope, JsonValidationExceptionCollector collector) {
        try {
            setTMST039ResponseEnvelope(tMST039ResponseEnvelope);
        } catch (IllegalArgumentException e) {
            collector.addError("ERROR_NULL_NOT_ALLOWED", e);
        }
    }
}
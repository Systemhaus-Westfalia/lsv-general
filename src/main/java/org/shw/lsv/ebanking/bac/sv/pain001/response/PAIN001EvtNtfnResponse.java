package org.shw.lsv.ebanking.bac.sv.pain001.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PAIN001EvtNtfnResponse implements Validatable {

    @JsonProperty("Envelope")    // "Envelope" is the name of the field in the JSON
    PAIN001ResponseEvtNtfnEnvelope pAIN001ResponseEvtNtfnEnvelope;

    public PAIN001EvtNtfnResponse() {}

    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (pAIN001ResponseEvtNtfnEnvelope == null) {
                throw new IllegalArgumentException("Envelope cannot be null");
            }

            // Validate nested objects
            if (pAIN001ResponseEvtNtfnEnvelope instanceof Validatable) {
                ((Validatable) pAIN001ResponseEvtNtfnEnvelope).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    public PAIN001ResponseEvtNtfnEnvelope getpAIN001ResponseEnvelope() {
        return pAIN001ResponseEvtNtfnEnvelope;
    }

    public void setpAIN001ResponseEnvelope(PAIN001ResponseEvtNtfnEnvelope pAIN001ResponseEvtNtfnEnvelope) {
        this.pAIN001ResponseEvtNtfnEnvelope = pAIN001ResponseEvtNtfnEnvelope;
    }
}
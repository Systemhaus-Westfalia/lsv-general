package org.shw.lsv.ebanking.bac.sv.camt053.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CAMT053Response implements Validatable {

    @JsonProperty("Envelope")    // "Envelope" is the name of the field in the JSON
    CAMT053ResponseEnvelope cAMT053ResponseEnvelope;

    public CAMT053Response() {}

    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (cAMT053ResponseEnvelope == null) {
                throw new IllegalArgumentException("Envelope cannot be null");
            }

            // Validate nested objects
            if (cAMT053ResponseEnvelope instanceof Validatable) {
                ((Validatable) cAMT053ResponseEnvelope).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    public CAMT053ResponseEnvelope getcAMT053ResponseEnvelope() {
        return cAMT053ResponseEnvelope;
    }

    public void setcAMT053ResponseEnvelope(CAMT053ResponseEnvelope cAMT053ResponseEnvelope) {
        this.cAMT053ResponseEnvelope = cAMT053ResponseEnvelope;
    }
}
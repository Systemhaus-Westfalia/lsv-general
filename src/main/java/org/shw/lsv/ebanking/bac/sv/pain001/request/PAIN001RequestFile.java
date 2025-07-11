package org.shw.lsv.ebanking.bac.sv.pain001.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

/**
 * Represents the "file" level object in the PAIN.001 request structure, containing the envelope.
 */
public class PAIN001RequestFile implements Validatable {
    @JsonProperty("Envelope")
    private PAIN001RequestEnvelope pain001RequestEnvelope;

    // Jackson constructor
    public PAIN001RequestFile() {}

    // Validation constructor
    public PAIN001RequestFile(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setPain001RequestEnvelope(new PAIN001RequestEnvelope(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_PAIN001REQUESTFILE_INIT, e);
        }
    }

    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (pain001RequestEnvelope == null) {
                throw new IllegalArgumentException(EBankingConstants.ERROR_ENVELOPE_NOT_NULL);
            }

            // Validate nested objects
            if (pain001RequestEnvelope instanceof Validatable) {
                pain001RequestEnvelope.validate(collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    public PAIN001RequestEnvelope getPain001RequestEnvelope() {
        return pain001RequestEnvelope;
    }

    /**
     * @param pain001RequestEnvelope the PAIN001RequestEnvelope to be set.
     */
    public void setPain001RequestEnvelope(PAIN001RequestEnvelope pain001RequestEnvelope) {
        if (pain001RequestEnvelope == null) {
            throw new IllegalArgumentException(
                "Wrong parameter 'pain001RequestEnvelope' in setPain001RequestEnvelope()"
            );
        }
        this.pain001RequestEnvelope = pain001RequestEnvelope;
    }

    /**
     * @param pain001RequestEnvelope the PAIN001RequestEnvelope to be set.
     * The parameter is validated: null not allowed.
     * @param collector the JsonValidationExceptionCollector to be set.
     */
    public void setPain001RequestEnvelope(PAIN001RequestEnvelope pain001RequestEnvelope, JsonValidationExceptionCollector collector) {
        try {
            setPain001RequestEnvelope(pain001RequestEnvelope);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}
package org.shw.lsv.ebanking.bac.sv.pain001.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.PAIN001RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

public class PAIN001Request implements Validatable {
    @JsonProperty("Envelope")
    private Pain0012RequestEnvelope pain0012RequestEnvelope;

    // Jackson constructor
    public PAIN001Request() {}

    // Validation constructor
    public PAIN001Request(PAIN001RequestParams params, 
                         JsonValidationExceptionCollector collector) {
        try {
            setPain0012RequestEnvelope(new Pain0012RequestEnvelope(params, collector));
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_PAIN001REQUEST_INIT, e);
        }
    }

    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (pain0012RequestEnvelope == null) {
                throw new IllegalArgumentException(EBankingConstants.ERROR_ENVELOPE_NOT_NULL);
            }

            // Validate nested objects
            if (pain0012RequestEnvelope instanceof Validatable) {
                ((Validatable) pain0012RequestEnvelope).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    public Pain0012RequestEnvelope getPain0012RequestEnvelope() {
        return pain0012RequestEnvelope;
    }



    /**
     * @param cAMT052RequestEnvelope the CAMT052RequestEnvelope to be set.
     */
    public void setPain0012RequestEnvelope(Pain0012RequestEnvelope pain0012RequestEnvelope) {
        if (pain0012RequestEnvelope == null) {
            throw new IllegalArgumentException(
                "Wrong parameter 'pain0012RequestEnvelope' in setPain0012RequestEnvelope()"
            );
        }
        this.pain0012RequestEnvelope = pain0012RequestEnvelope;
    }



    /**
     * @param pain0012RequestEnvelope the CAMT052RequestEnvelope to be set.
     * The parameter is validated: null not allowed.
     * @param collector the JsonValidationExceptionCollector to be set.
     */
    public void setPain0012RequestEnvelope(Pain0012RequestEnvelope pain0012RequestEnvelope, 
                                        JsonValidationExceptionCollector collector) {
        try {
            setPain0012RequestEnvelope(pain0012RequestEnvelope);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }
}
package org.shw.lsv.ebanking.bac.sv.pain001.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

public class PAIN001Request implements Validatable {
    @JsonProperty("Envelope")
    private PAIN001RequestEnvelope pAIN001RequestEnvelope;

    // Jackson constructor
    public PAIN001Request() {}

    // Validation constructor
    public PAIN001Request(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setPain0012RequestEnvelope(new PAIN001RequestEnvelope(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_CAMT052REQUEST_INIT, e);
        }
    }

    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (pAIN001RequestEnvelope == null) {
                throw new IllegalArgumentException(EBankingConstants.ERROR_ENVELOPE_NOT_NULL);
            }

            // Validate nested objects
            if (pAIN001RequestEnvelope instanceof Validatable) {
                ((Validatable) pAIN001RequestEnvelope).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    public PAIN001RequestEnvelope getpAIN001RequestEnvelope() {
        return pAIN001RequestEnvelope;
    }



    /**
     * @param cAMT052RequestEnvelope the PAIN001RequestEnvelope to be set.
     */
    public void setpAIN001RequestEnvelope(PAIN001RequestEnvelope pain0012RequestEnvelope) {
        if (pain0012RequestEnvelope == null) {
            throw new IllegalArgumentException(
                "Wrong parameter 'pain0012RequestEnvelope' in setpAIN001RequestEnvelope()"
            );
        }
        this.pAIN001RequestEnvelope = pain0012RequestEnvelope;
    }



    /**
     * @param pain0012RequestEnvelope the PAIN001RequestEnvelope to be set.
     * The parameter is validated: null not allowed.
     * @param collector the JsonValidationExceptionCollector to be set.
     */
    public void setPain0012RequestEnvelope(PAIN001RequestEnvelope pain0012RequestEnvelope, 
                                        JsonValidationExceptionCollector collector) {
        try {
            setpAIN001RequestEnvelope(pain0012RequestEnvelope);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }
}
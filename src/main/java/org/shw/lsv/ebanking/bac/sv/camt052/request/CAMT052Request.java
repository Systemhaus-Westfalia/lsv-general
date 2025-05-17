package org.shw.lsv.ebanking.bac.sv.camt052.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request Consulta de Saldo    
 */
public class CAMT052Request implements Validatable {
    
    @JsonProperty("Envelope")    // "Envelope" is the name of the field in the JSON
    CAMT052RequestEnvelope envelopeCAMT052Request;
    
    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (envelopeCAMT052Request == null) {
                throw new IllegalArgumentException("Envelope cannot be null");
            }

            // Validate nested objects
            if (envelopeCAMT052Request instanceof Validatable) {
                ((Validatable) envelopeCAMT052Request).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }


    public CAMT052RequestEnvelope getEnvelopeCAMT052Request() {
        return envelopeCAMT052Request;
    }


    /**
     * @param envelopeCAMT052Request the CAMT052RequestEnvelope to be set.
     * The parameter is validated: null not allowed.
     * @param collector the JsonValidationExceptionCollector to be set.
     */
    public void setEnvelopeCAMT052Request(CAMT052RequestEnvelope envelopeCAMT052Request, 
                                        JsonValidationExceptionCollector collector) {
        try {
            setEnvelopeCAMT052Request(envelopeCAMT052Request);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }

    /**
     * @param envelopeCAMT052Request the CAMT052RequestEnvelope to be set.
     */
    public void setEnvelopeCAMT052Request(CAMT052RequestEnvelope envelopeCAMT052Request) {
        if (envelopeCAMT052Request == null) {
            throw new IllegalArgumentException(
                "Wrong parameter 'envelopeCAMT052Request' in setEnvelopeCAMT052Request()"
            );
        }
        this.envelopeCAMT052Request = envelopeCAMT052Request;
    }

}

package org.shw.lsv.ebanking.bac.sv.camt052.request;

import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request Consulta de Saldo    
 */
public class CAMT052Request implements Validatable {
    
    @JsonProperty("Envelope")    // "Envelope" is the name of the field in the JSON
    CAMT052RequestEnvelope cAMT052RequestEnvelope;
    
    public CAMT052Request() {}

    public CAMT052Request(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setcAMT052RequestEnvelope(new CAMT052RequestEnvelope(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_CAMT052REQUEST_INIT, e);
        }
    }


    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (cAMT052RequestEnvelope == null) {
                throw new IllegalArgumentException(EBankingConstants.ERROR_ENVELOPE_NOT_NULL);
            }

            // Validate nested objects
            if (cAMT052RequestEnvelope instanceof Validatable) {
                ((Validatable) cAMT052RequestEnvelope).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }


    public CAMT052RequestEnvelope getcAMT052RequestEnvelope() {
        return cAMT052RequestEnvelope;
    }


    /**
     * @param cAMT052RequestEnvelope the CAMT052RequestEnvelope to be set.
     * The parameter is validated: null not allowed.
     * @param collector the JsonValidationExceptionCollector to be set.
     */
    public void setcAMT052RequestEnvelope(CAMT052RequestEnvelope cAMT052RequestEnvelope, 
                                        JsonValidationExceptionCollector collector) {
        try {
            setcAMT052RequestEnvelope(cAMT052RequestEnvelope);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }

    /**
     * @param cAMT052RequestEnvelope the CAMT052RequestEnvelope to be set.
     */
    public void setcAMT052RequestEnvelope(CAMT052RequestEnvelope cAMT052RequestEnvelope) {
        if (cAMT052RequestEnvelope == null) {
            throw new IllegalArgumentException(
                "Wrong parameter 'envelopeCAMT052Request' in setcAMT052RequestEnvelope()"
            );
        }
        this.cAMT052RequestEnvelope = cAMT052RequestEnvelope;
    }

}

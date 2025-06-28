package org.shw.lsv.ebanking.bac.sv.camt052.request;

import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the "file" level object in the request structure, containing the envelope.
 */
public class CAMT052RequestFile implements Validatable {
    
    @JsonProperty("Envelope")    // "Envelope" is the name of the field in the JSON
    CAMT052RequestEnvelope cAMT052RequestEnvelope;
    
    public CAMT052RequestFile() {}

    public CAMT052RequestFile(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setcAMT052RequestEnvelope(new CAMT052RequestEnvelope(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_CAMT052REQUESTFILE_INIT, e);
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
        }
    }

    /**
     * @param cAMT052RequestEnvelope the CAMT052RequestEnvelope to be set.
     */
    public void setcAMT052RequestEnvelope(CAMT052RequestEnvelope cAMT052RequestEnvelope) {
        if (cAMT052RequestEnvelope == null) {
            throw new IllegalArgumentException("Wrong parameter 'cAMT052RequestEnvelope' in setcAMT052RequestEnvelope()");
        }
        this.cAMT052RequestEnvelope = cAMT052RequestEnvelope;
    }

}
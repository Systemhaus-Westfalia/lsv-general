package org.shw.lsv.ebanking.bac.sv.camt052.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the "file" level object in the response structure, containing the envelope.
 */
public class CAMT052ResponseFile implements Validatable {

    @JsonProperty("Envelope")    // "Envelope" is the name of the field in the JSON
    CAMT052ResponseEnvelope cAMT052ResponseEnvelope;

    public CAMT052ResponseFile() {}

    public CAMT052ResponseFile(CAMT052ResponseEnvelope cAMT052ResponseEnvelope) {
        setcAMT052ResponseEnvelope(cAMT052ResponseEnvelope);
    }

    /**
     * @return the Envelope object<br>
     */
    public CAMT052ResponseEnvelope getcAMT052ResponseEnvelope() {
        return cAMT052ResponseEnvelope;
    }

    /**
     * @param cAMT052ResponseEnvelope the Envelope to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setcAMT052ResponseEnvelope(CAMT052ResponseEnvelope cAMT052ResponseEnvelope) {
        if (cAMT052ResponseEnvelope == null) {
            throw new IllegalArgumentException("Wrong parameter 'cAMT052ResponseEnvelope' in setcAMT052ResponseEnvelope()");
        }
        this.cAMT052ResponseEnvelope = cAMT052ResponseEnvelope;
    }

    /**
     * @param cAMT052ResponseEnvelope the Envelope to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setcAMT052ResponseEnvelope(CAMT052ResponseEnvelope cAMT052ResponseEnvelope, JsonValidationExceptionCollector collector) {
        try {
            setcAMT052ResponseEnvelope(cAMT052ResponseEnvelope);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (cAMT052ResponseEnvelope == null) {
                throw new IllegalArgumentException("Envelope cannot be null");
            }
            if (cAMT052ResponseEnvelope instanceof Validatable) {
                ((Validatable) cAMT052ResponseEnvelope).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}
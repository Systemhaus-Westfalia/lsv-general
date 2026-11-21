package org.shw.lsv.ebanking.bac.sv.camt053.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

/**
 * Represents the "File" level object in the CAMT.053 response structure, containing the envelope.
 */
public class CAMT053ResponseFile implements Validatable {

    @JsonProperty("Envelope")    // "Envelope" is the name of the field in the JSON
    CAMT053ResponseEnvelope camt053ResponseEnvelope;

    public CAMT053ResponseFile() {}

    public CAMT053ResponseFile(CAMT053ResponseEnvelope camt053ResponseEnvelope) {
        setCamt053ResponseEnvelope(camt053ResponseEnvelope);
    }

    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (camt053ResponseEnvelope == null) {
                throw new IllegalArgumentException(EBankingConstants.ERROR_ENVELOPE_NOT_NULL);
            }

            // Validate nested objects.
            // Note: For this to work, CAMT053ResponseEnvelope should implement the Validatable interface.
            if (camt053ResponseEnvelope instanceof Validatable) {
                ((Validatable) camt053ResponseEnvelope).validate(collector);
            } else {
                // Since CAMT053ResponseEnvelope has a validate() method but doesn't implement the
                // interface, we call it directly to ensure validation is performed.
                camt053ResponseEnvelope.validate(collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    public CAMT053ResponseEnvelope getCamt053ResponseEnvelope() {
        return camt053ResponseEnvelope;
    }

    /**
     * @param camt053ResponseEnvelope the CAMT053ResponseEnvelope to be set.
     */
    public void setCamt053ResponseEnvelope(CAMT053ResponseEnvelope camt053ResponseEnvelope) {
        if (camt053ResponseEnvelope == null) {
            throw new IllegalArgumentException("Wrong parameter 'camt053ResponseEnvelope' in setCamt053ResponseEnvelope()");
        }
        this.camt053ResponseEnvelope = camt053ResponseEnvelope;
    }

    /**
     * @param camt053ResponseEnvelope the CAMT053ResponseEnvelope to be set.
     * @param collector the JsonValidationExceptionCollector to be set.
     */
    public void setCamt053ResponseEnvelope(CAMT053ResponseEnvelope camt053ResponseEnvelope,
                                        JsonValidationExceptionCollector collector) {
        try {
            setCamt053ResponseEnvelope(camt053ResponseEnvelope);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
  }
package org.shw.lsv.ebanking.bac.sv.tmst039.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

/**
 * Represents the "File" level object in the TMST039 response structure, containing the envelope.
 */
public class TMST039ResponseFile implements Validatable {

    @JsonProperty("Envelope")    // "Envelope" is the name of the field in the JSON
    TMST039ResponseEnvelope tMST039ResponseEnvelope;

    public TMST039ResponseFile() {}

    public TMST039ResponseFile(TMST039ResponseEnvelope tMST039ResponseEnvelope) {
        setTMST039ResponseEnvelope(tMST039ResponseEnvelope);
    }

    /**
     * @return the Envelope object<br>
     */
    public TMST039ResponseEnvelope getTMST039ResponseEnvelope() {
        return tMST039ResponseEnvelope;
    }

    /**
     * @param tMST039ResponseEnvelope the Envelope to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setTMST039ResponseEnvelope(TMST039ResponseEnvelope tMST039ResponseEnvelope) {
        if (tMST039ResponseEnvelope == null) {
            throw new IllegalArgumentException("Wrong parameter 'tMST039ResponseEnvelope' in setTMST039ResponseEnvelope()");
        }
        this.tMST039ResponseEnvelope = tMST039ResponseEnvelope;
    }

    /**
     * @param tMST039ResponseEnvelope the Envelope to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setTMST039ResponseEnvelope(TMST039ResponseEnvelope tMST039ResponseEnvelope, JsonValidationExceptionCollector collector) {
        try {
            setTMST039ResponseEnvelope(tMST039ResponseEnvelope);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (tMST039ResponseEnvelope == null) {
                throw new IllegalArgumentException("Envelope cannot be null");
            }
            if (tMST039ResponseEnvelope instanceof Validatable) {
                ((Validatable) tMST039ResponseEnvelope).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}
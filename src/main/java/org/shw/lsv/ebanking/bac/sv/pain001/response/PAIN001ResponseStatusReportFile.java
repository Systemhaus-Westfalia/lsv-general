package org.shw.lsv.ebanking.bac.sv.pain001.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

/**
 * Represents the "File" level object in the PAIN001 Status Report response structure, containing the envelope.
 */
public class PAIN001ResponseStatusReportFile implements Validatable {

    @JsonProperty("Envelope")    // "Envelope" is the name of the field in the JSON
    PAIN001ResponseStatusReportEnvelope pain001ResponseEnvelope;

    public PAIN001ResponseStatusReportFile() {}

    public PAIN001ResponseStatusReportFile(PAIN001ResponseStatusReportEnvelope pain001ResponseEnvelope) {
        setPain001ResponseEnvelope(pain001ResponseEnvelope);
    }

    /**
     * @return the Envelope object<br>
     */
    public PAIN001ResponseStatusReportEnvelope getPain001ResponseEnvelope() {
        return pain001ResponseEnvelope;
    }

    /**
     * @param pain001ResponseEnvelope the Envelope to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setPain001ResponseEnvelope(PAIN001ResponseStatusReportEnvelope pain001ResponseEnvelope) {
        if (pain001ResponseEnvelope == null) {
            throw new IllegalArgumentException("Wrong parameter 'pain001ResponseEnvelope' in setPain001ResponseEnvelope()");
        }
        this.pain001ResponseEnvelope = pain001ResponseEnvelope;
    }

    /**
     * @param pain001ResponseEnvelope the Envelope to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setPain001ResponseEnvelope(PAIN001ResponseStatusReportEnvelope pain001ResponseEnvelope, JsonValidationExceptionCollector collector) {
        try {
            setPain001ResponseEnvelope(pain001ResponseEnvelope);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (pain001ResponseEnvelope == null) {
                throw new IllegalArgumentException("Envelope cannot be null");
            }
            if (pain001ResponseEnvelope instanceof Validatable) {
                ((Validatable) pain001ResponseEnvelope).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}
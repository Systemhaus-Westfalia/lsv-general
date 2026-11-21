package org.shw.lsv.ebanking.bac.sv.camt053.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

/**
 * Represents the "file" level object in the CAMT.053 request structure, containing the envelope.
 */
public class CAMT053RequestFile implements Validatable {

    @JsonProperty("Envelope")    // "Envelope" is the name of the field in the JSON
    CAMT053RequestEnvelope cAMT053RequestEnvelope;

    public CAMT053RequestFile() {}

    public CAMT053RequestFile(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setcAMT053RequestEnvelope(new CAMT053RequestEnvelope(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_CAMT053REQUESTFILE_INIT, e);
        }
    }


    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (cAMT053RequestEnvelope == null) {
                throw new IllegalArgumentException(EBankingConstants.ERROR_ENVELOPE_NOT_NULL);
            }

            // Validate nested objects
            if (cAMT053RequestEnvelope instanceof Validatable) {
                ((Validatable) cAMT053RequestEnvelope).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }


    public CAMT053RequestEnvelope getcAMT053RequestEnvelope() {
        return cAMT053RequestEnvelope;
    }


    /**
     * @param cAMT053RequestEnvelope the CAMT053RequestEnvelope to be set.
     * The parameter is validated: null not allowed.
     * @param collector the JsonValidationExceptionCollector to be set.
     */
    public void setcAMT053RequestEnvelope(CAMT053RequestEnvelope cAMT053RequestEnvelope,
                                        JsonValidationExceptionCollector collector) {
        try {
            setcAMT053RequestEnvelope(cAMT053RequestEnvelope);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    /**
     * @param cAMT053RequestEnvelope the CAMT053RequestEnvelope to be set.
     */
    public void setcAMT053RequestEnvelope(CAMT053RequestEnvelope cAMT053RequestEnvelope) {
        if (cAMT053RequestEnvelope == null) {
            throw new IllegalArgumentException("Wrong parameter 'cAMT053RequestEnvelope' in setcAMT053RequestEnvelope()");
        }
        this.cAMT053RequestEnvelope = cAMT053RequestEnvelope;
    }
}
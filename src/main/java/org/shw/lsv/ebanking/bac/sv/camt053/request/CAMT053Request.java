package org.shw.lsv.ebanking.bac.sv.camt053.request;

import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request Consulta de Saldo CATM053
 */
public class CAMT053Request implements Validatable {

    @JsonProperty("Envelope")    // "Envelope" is the name of the field in the JSON
    CAMT053RequestEnvelope cAMT052RequestEnvelope;

    public CAMT053Request() {}

    public CAMT053Request(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setcAMT052RequestEnvelope(new CAMT053RequestEnvelope(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_CAMT053REQUEST_INIT, e);
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

    public CAMT053RequestEnvelope getcAMT052RequestEnvelope() {
        return cAMT052RequestEnvelope;
    }

    /**
     * @param cAMT052RequestEnvelope the CATM053RequestEnvelope to be set.
     * The parameter is validated: null not allowed.
     * @param collector the JsonValidationExceptionCollector to be set.
     */
    public void setcAMT052RequestEnvelope(CAMT053RequestEnvelope cAMT052RequestEnvelope,
                                          JsonValidationExceptionCollector collector) {
        try {
            setcAMT052RequestEnvelope(cAMT052RequestEnvelope);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    /**
     * @param cAMT052RequestEnvelope the CATM053RequestEnvelope to be set.
     */
    public void setcAMT052RequestEnvelope(CAMT053RequestEnvelope cAMT052RequestEnvelope) {
        if (cAMT052RequestEnvelope == null) {
            throw new IllegalArgumentException(
                "Wrong parameter 'envelopeCATM053Request' in setcAMT052RequestEnvelope()"
            );
        }
        this.cAMT052RequestEnvelope = cAMT052RequestEnvelope;
    }
}
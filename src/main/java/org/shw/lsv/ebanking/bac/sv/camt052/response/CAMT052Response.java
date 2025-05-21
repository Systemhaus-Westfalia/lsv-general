package org.shw.lsv.ebanking.bac.sv.camt052.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CAMT052Response implements Validatable {
    
    @JsonProperty("Envelope")    // "Envelope" is the name of the field in the JSON
    CAMT052ResponseEnvelope cAMT052ResponseEnvelope;


    public CAMT052Response() {}


    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (cAMT052ResponseEnvelope == null) {
                throw new IllegalArgumentException("Envelope cannot be null");
            }

            // Validate nested objects
            if (cAMT052ResponseEnvelope instanceof Validatable) {
                ((Validatable) cAMT052ResponseEnvelope).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
    
    public CAMT052ResponseEnvelope getcAMT052ResponseEnvelope() {
        return cAMT052ResponseEnvelope;
    }


    public void setcAMT052ResponseEnvelope(CAMT052ResponseEnvelope cAMT052ResponseEnvelope) {
        this.cAMT052ResponseEnvelope = cAMT052ResponseEnvelope;
    }

}

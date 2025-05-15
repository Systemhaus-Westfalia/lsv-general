package org.shw.lsv.ebanking.bac.sv.camt052.Request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request Consulta de Saldo    
 */
public class CAMT052Request implements Validatable {
    
    @JsonProperty("Envelope")    // "Envelope" is the name of the field in the JSON
    CAMT052RequestEnvelope envelopeCAMT060Request;

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=CAMT052Request.class.getName();
    
    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (envelopeCAMT060Request == null) {
                throw new IllegalArgumentException("Envelope cannot be null");
            }

            // Validate nested objects
            if (envelopeCAMT060Request instanceof Validatable) {
                ((Validatable) envelopeCAMT060Request).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(FULLY_QUALIFIED_CLASSNAME + ".validate()", e);
        }
    }


    public CAMT052RequestEnvelope getEnvelopeCAMT060Request() {
        return envelopeCAMT060Request;
    }

    /**
     * @param envelopeCAMT060Request the CAMT060RequestEnvelope to be set.
     * The parameter is validated: null not allowed.
     */
    public void setEnvelopeCAMT060Request(CAMT052RequestEnvelope envelopeCAMT060Request, 
                                            JsonValidationExceptionCollector collector) {
        try {
            if (envelopeCAMT060Request == null) {
                throw new IllegalArgumentException(
                "Wrong parameter 'envelopeCAMT060Request' in " + FULLY_QUALIFIED_CLASSNAME + ".setEnvelopeCAMT060Request()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setEnvelopeCAMT060Request()";
            collector.addError(context, e);

            throw e;
        }

        this.envelopeCAMT060Request = envelopeCAMT060Request;
    }

}

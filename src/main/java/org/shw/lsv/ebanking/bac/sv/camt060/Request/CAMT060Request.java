package org.shw.lsv.ebanking.bac.sv.camt060.Request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request Consulta de Saldo    
 */
public class CAMT060Request {
    @JsonProperty("Envelope")    // "Envelope" is the name of the field in the JSON
    CAMT060RequestEnvelope envelopeCAMT060Request;

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=CAMT060Request.class.getName();


    public CAMT060RequestEnvelope getEnvelopeCAMT060Request() {
        return envelopeCAMT060Request;
    }


	/**
	 * @param envelopeCAMT060Request the CAMT060RequestEnvelope to be set<br>
	 * The parameter is validated: null not allowed.<br>
	 */
    public void setEnvelopeCAMT060Request(CAMT060RequestEnvelope envelopeCAMT060Request) {
        if (envelopeCAMT060Request == null ) {
            throw new IllegalArgumentException("Wrong parameter 'envelopeCAMT060Request' in " +  FULLY_QUALIFIED_CLASSNAME + ".setEnvelopeCAMT060Request()" + "\n");
        }
        this.envelopeCAMT060Request = envelopeCAMT060Request;
    }

}

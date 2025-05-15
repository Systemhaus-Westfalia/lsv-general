package org.shw.lsv.ebanking.bac.sv.misc;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Unique and unambiguous identification of a financial institution, as assigned under an internationally recognised or proprietary identification scheme. 
 */
public class FinInstnId {

    /**
     * Code allocated to a financial institution by the ISO 9362 - Business identifier code (BIC). Multiplicity [1..1]
     */                         
    @JsonProperty(value = "BICFI", required = true)
	String BICFI;  // BICFI (Bank Identifier Code)

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=FinInstnId.class.getName();


    /*
     * Constructor with parameters
     * For using the Constructor at deserialization time, it has to be of the form:
     * public FinInstnId(@JsonProperty(value = "BICFI", required = true) String BICFI,.....)
     */
    public FinInstnId(String BICFI, JsonValidationExceptionCollector collector) {
        setBICFI(BICFI, collector);
    }


	/**
	 * @return the BICFI
	 */
	public String getBICFI() {
        return BICFI;
    }


	/**
	 * @param BICFI the BICFI to be set.
	 * The parameter is validated: null not allowed.
	 */
    public void setBICFI(String BICFI, JsonValidationExceptionCollector collector) {
        try {
            if (BICFI == null || BICFI.isEmpty()) {
                throw new IllegalArgumentException(
                    "Wrong parameter 'BICFI' (" + BICFI + ") in " + FULLY_QUALIFIED_CLASSNAME + ".setBICFI()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setBICFI()";
            collector.addError(context, e);

            throw e;
        }

        this.BICFI = BICFI;
    }


    public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}

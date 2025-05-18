package org.shw.lsv.ebanking.bac.sv.misc;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

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


    public FinInstnId() {
    }


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
     * @param BICFI the BICFI to be set<br>
     * The parameter is validated: null not allowed.<br>
     * Pattern: "[A-Z0-9]{4,4}[A-Z]{2,2}[A-Z0-9]{2,2}([A-Z0-9]{3,3}){0,1}".
     */
    public void setBICFI(String BICFI) {
        boolean patternOK = (BICFI != null) && Pattern.matches(EBankingConstants.PATTERN_BICFI, BICFI);

        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'BICFI' (" + BICFI + ") in setBICFI()");
        }
        this.BICFI = BICFI;
    }

    /**
     * @param BICFI the BICFI to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setBICFI(String BICFI, JsonValidationExceptionCollector collector) {
        try {
            setBICFI(BICFI);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
            //throw e;
        }
    }


    public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}

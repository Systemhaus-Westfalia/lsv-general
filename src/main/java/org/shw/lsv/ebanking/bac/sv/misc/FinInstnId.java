package org.shw.lsv.ebanking.bac.sv.misc;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
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
	String bICFI;  // BICFI (Bank Identifier Code)


    public FinInstnId() {
    }


    public FinInstnId(String bicfi) {
        setbICFI(bicfi);
    }


    public FinInstnId(RequestParams params, String context, JsonValidationExceptionCollector collector) {
        try {
            if (context.equals(EBankingConstants.CONTEXT_FR)) {
                setBICFI(params.getBicfiFr(), collector);
            } else if (context.equals(EBankingConstants.CONTEXT_TO)) {
                setBICFI(params.getBicfiTo(), collector);
            } else if (context.equals(EBankingConstants.CONTEXT_AGT)) {
                setBICFI(params.getBicfiAcctOwnr(), collector);
            } else {
                throw new IllegalArgumentException("Wrong parameter 'context' (" + context + ") in FinInstnId()");
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_FININSTN_INIT, e);
        }
    }

    


	/**
	 * @return the BICFI
	 */
	public String getbICFI() {
        return bICFI;
    }


    /**
     * @param BICFI the BICFI to be set<br>
     * The parameter is validated: null not allowed.<br>
     * Pattern: "[A-Z0-9]{4,4}[A-Z]{2,2}[A-Z0-9]{2,2}([A-Z0-9]{3,3}){0,1}".
     */
    public void setbICFI(String BICFI) {
        boolean patternOK = (BICFI != null) && Pattern.matches(EBankingConstants.PATTERN_BIC, BICFI);

        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'BICFI' (" + BICFI + ") in setBICFI()");
        }
        this.bICFI = BICFI;
    }

    /**
     * @param BICFI the BICFI to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setBICFI(String BICFI, JsonValidationExceptionCollector collector) {
        try {
            setbICFI(BICFI);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
            //throw e;
        }
    }


    public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}

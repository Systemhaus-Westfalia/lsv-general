package org.shw.lsv.ebanking.bac.sv.camt052.request;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SchmeNm {
    @JsonProperty("Prtry")
    String prtry;  // "Name of the identification scheme, in a free text form."


	public SchmeNm() {
    }


    public SchmeNm(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setPrtry(params.getPrtry(), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_FININSTN_INIT, e);
        }
    }


    /**
	 * @return the Prtry
	 */
    public String getPrtry() {
        return prtry;
    }


    /**
     * @param prtry the Prtry to be set.<br>
     * The parameter is validated.<br>
     * "minLength" : 1, "maxLength" : 35; null not allowed.<br>
     * "pattern" : "[0-9a-zA-Z/\\\\-\\?:\\(\\)\\.,'\\+ ]+".<br>
     * Example: "BAC_SchmeNm".
     */
    public void setPrtry(String prtry) {
        final int MINLENGTH = 1;
        final int MAXLENGTH = 35;

        int length = (prtry == null || prtry.isEmpty()) ? 0 : prtry.length();
        boolean patternOK = (prtry != null) && Pattern.matches(EBankingConstants.PATTERN_PRTRY, prtry);

        if (!(length >= MINLENGTH && length <= MAXLENGTH && patternOK)) {
            throw new IllegalArgumentException(
                "Wrong parameter 'prtry' (" + prtry + ") in setPrtry()"
            );
        }
        this.prtry = prtry;
    }

    /**
     * @param prtry the Prtry to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setPrtry(String prtry, JsonValidationExceptionCollector collector) {
        try {
            setPrtry(prtry);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
            //throw e;
        }
    }

}

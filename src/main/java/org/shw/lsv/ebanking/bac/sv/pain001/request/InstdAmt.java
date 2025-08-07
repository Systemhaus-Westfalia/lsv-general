package org.shw.lsv.ebanking.bac.sv.pain001.request;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InstdAmt {
    @JsonProperty("InstdAmt")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String instdAmt;
    
    @JsonProperty("-Ccy")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String ccy;

    public InstdAmt() { }
    
        public InstdAmt(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setInstdAmt(params.getInstdAmt(), collector);
            setCcy(     params.getCcy(),      collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_INSTDAMT_INIT, e);
        }
    }

    /**
     * @return the InstdAmt
     */
    public String getInstdAmt() {
        return instdAmt;
    }

    /**
     * @param instdAmt the instructed amount to be set.<br>
     * <p>
     * Pattern: "\\d+(\\.\\d{1,2})?";
     * Laut copilot sind folgende Eigenschaften dieses Patterns:
     * <p>
     * Matches: Integers (123), one decimal (123.4), two decimals (123.45)
     *   - Does NOT match: More than two decimals (123.456)
     *   - Flexible: Allows whole numbers and up to two decimals.
     * <p>
     * The parameter is validated: must be a valid decimal number, not null or empty.<br>
     * Example: "123.45"
     */
    public void setInstdAmt(String instdAmt) {
        if (instdAmt == null || instdAmt.isEmpty() || !instdAmt.matches(EBankingConstants.PATTERN_CURRENCY_AMT)) {
            throw new IllegalArgumentException("Wrong parameter 'instdAmt' (" + instdAmt + ") in setInstdAmt()");
        }
        this.instdAmt = instdAmt;
    }

    /**
     * @param instdAmt the instructed amount to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     * <p>
     * Pattern: "\\d+(\\.\\d{1,2})?";
     * Laut copilot sind folgende Eigenschaften dieses Patterns:
     * <p>
     * Matches: Integers (123), one decimal (123.4), two decimals (123.45)
     *   - Does NOT match: More than two decimals (123.456)
     *   - Flexible: Allows whole numbers and up to two decimals.
     */
    public void setInstdAmt(String instdAmt, JsonValidationExceptionCollector collector) {
        try {
            setInstdAmt(instdAmt);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_INVALID_AMT_FORMAT, e);
            //throw e;
        }
    }



    /**
     * @return the Ccy object<br>
     */
    public String getCcy() {
        return ccy;
    }


    /**
     * @param ccy the Ccy to be set<br>
     * The parameter is validated: null not allowed.<br>
     * pattern BAC onboarding documentation: "[A-Z]{3,3]"
     * e.g.: "EUR", "USD", "GBP"
     */
    public void setCcy(String ccy) {
        boolean patternOK = (ccy != null) && Pattern.matches(EBankingConstants.PATTERN_CURRENCY, ccy);

        if (patternOK) {
            this.ccy = ccy;
        } else {
            throw new IllegalArgumentException("Wrong parameter 'ccy' (" + ccy + ") in setCcy()");
        }
    }

    /**
     * @param ccy the Ccy to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setCcy(String ccy, JsonValidationExceptionCollector collector) {
        try {
            setCcy(ccy);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_INVALID_CCY_FORMAT, e);
            //throw e;
        }
    }


}

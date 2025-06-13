package org.shw.lsv.ebanking.bac.sv.camt052.response;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Amt {

    @JsonProperty("Ccy")  // "Ccy" is optional in the JSON definition
    String ccy= null;

    @JsonProperty(value = "Amt", required = true)
    String amt= null;

   public Amt() {
    }


    /*
    * Constructor with parameters
    * For using the Constructor at deserialization time, it has to be of the form:
    * public Amt(@JsonProperty(value = "Ccy", required = true) String ccy,.....)
    */
    public Amt(String ccy, String amt) {
        setCcy(ccy);
        setAmt(amt);
    }

    
    public Amt(String amt) {
        setAmt(amt);
    }

    public Amt(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            if (params.getCcy() != null && !params.getCcy().isEmpty()) {
                setCcy(params.getCcy());
            }
            if (params.getStmtOfAcctAmt() != null && !params.getStmtOfAcctAmt().isEmpty()) {
                setAmt(params.getStmtOfAcctAmt());
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_STS_INIT, e);
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

    /**
     * @return the Amt<br>
     */
    public String getAmt() {
        return amt;
    }


    /**
     * @param amt the Amt to be set<br>
     * <p>
     * Pattern: "\\d+(\\.\\d{1,2})?";
     * Laut copilot sind folgende Eigenschaften dieses Patterns:
     * <p>
     * Matches: Integers (123), one decimal (123.4), two decimals (123.45)
     *   - Does NOT match: More than two decimals (123.456)
     *   - Flexible: Allows whole numbers and up to two decimals.
     * <p>
     * The parameter is validated.<br>
     * pattern: "^\d+\.\d{2}$"<br>
     * e.g.: "999994769.99"
     */
    public void setAmt(String amt) {
        boolean patternOK = (amt != null) && Pattern.matches(EBankingConstants.PATTERN_CURRENCY_AMT, amt);

        if (patternOK) {
            this.amt = amt;
        } else {
            throw new IllegalArgumentException("Wrong parameter 'amt' (" + amt + ") in setAmt()");
        }
    }

    /**
     * @param amt the Amt to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     * <p>
     * Pattern: "\\d+(\\.\\d{1,2})?";
     * Laut copilot sind folgende Eigenschaften dieses Patterns:
     * <p>
     * Matches: Integers (123), one decimal (123.4), two decimals (123.45)
     *   - Does NOT match: More than two decimals (123.456)
     *   - Flexible: Allows whole numbers and up to two decimals.
     * <p>
     */
    public void setAmt(String amt, JsonValidationExceptionCollector collector) {
        try {
            setAmt(amt);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_INVALID_AMT_FORMAT, e);
            //throw e;
        }
    }


}

package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CtgyPurp {
    @JsonProperty("Cd")  // Category Purpose Code
    String cd;

    CtgyPurp() { }

    public CtgyPurp(RequestParams params, String context, JsonValidationExceptionCollector collector) {
        try {
            if ( context.equals(EBankingConstants.CONTEXT_CDTRACCT)) {
                if ( !(params.getCdtrAcctCd() == null || params.getCdtrAcctCd().isEmpty()) ) {
                    setCd(params.getCdtrAcctCd(), collector);
            }
            } else if ( context.equals(EBankingConstants.CONTEXT_PMTTPINF)) {
                if (( !(params.getCatPurpCd() == null || params.getCatPurpCd().isEmpty()) ) ) {
                    setCd(params.getCatPurpCd(), collector);
                } 
        }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_CTGYPURP_INIT, e);
        }
    }

    /**
     * @return the Cd
     */
    public String getCd() {
        return cd;
    }

    /**
     * @param cd the Cd to be set.<br>
     * The parameter is validated: null not allowed.<br>
     * Pattern: "[A-Z]{4}" (example: "SALA", "TAXS", etc.)
     * There are many possible values, but here are ony the following used. SUPP, SALA, TREA...
     */
    public void setCd(String cd) {
        boolean patternOK = (cd != null && !cd.isEmpty()) &&
            java.util.regex.Pattern.matches("[A-Z]{4}", cd);

        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'cd' (" + cd + ") in setCd()");
        }
        this.cd = cd;
    }

    /**
     * @param cd the Cd to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setCd(String cd, JsonValidationExceptionCollector collector) {
        try {
            setCd(cd);
        } catch (IllegalArgumentException e) {
            collector.addError("ERROR_PATTERN_MISMATCH", e);
            //throw e;
        }
    }

}

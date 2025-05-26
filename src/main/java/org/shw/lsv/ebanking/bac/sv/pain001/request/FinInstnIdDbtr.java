package org.shw.lsv.ebanking.bac.sv.pain001.request;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FinInstnIdDbtr {
    /* Business Identifier Code (BIC) of the debtor's financial institution.
    The BIC is a standard format for identifying banks and financial institutions globally.
    It is used to facilitate international transactions and communications between banks.
    The BIC is typically 8 or 11 characters long, consisting of letters and digits.
    The first four characters represent the bank code, the next two characters represent the country code,
    and the next two characters represent the location code. The optional last three characters represent the branch code.
    Example: "BSNJCRSJ" (where "BSNJ" is the bank code, "CR" is the country code for Costa Rica, and "SJ" is the location code).
    It is also known as SWIFT code. */
    @JsonProperty("BIC")  
    String BIC;  
    
    @JsonProperty("PstlAdr")
    PstlAdr pstlAdr;  // Postal address of the debtor's financial institution, if available.
    
    public FinInstnIdDbtr() { }
    

    /**
     * Constructor for initializing FinInstnIdDbtr with RequestParams and JsonValidationExceptionCollector.
     *
     * @param params    Request parameters containing necessary data.
     * @param collector Collector for validation exceptions.
     */
    public FinInstnIdDbtr(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setBIC(params.getBicDbtr(), collector);
            setPstlAdr ( new PstlAdr( params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_PMTTPINF_INIT, e);
        }
    }

    /**
     * @return the BIC
     */
    public String getBIC() {
        return BIC;
    }

    /**
     * @param BIC the BIC to be set.<br>
     * The parameter is validated: null not allowed.<br>
     * Pattern: "[A-Z0-9]{4,4}[A-Z]{2,2}[A-Z0-9]{2,2}([A-Z0-9]{3,3}){0,1}"<br>
     * Example: "BSNJCRSJ".
     */
    public void setBIC(String BIC) {
        boolean patternOK = (BIC != null) && Pattern.matches(EBankingConstants.PATTERN_BIC, BIC);

        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'BIC' (" + BIC + ") in setBIC()");
        }
        this.BIC = BIC;
    }

    /**
     * @param BIC the BIC to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setBIC(String BIC, JsonValidationExceptionCollector collector) {
        try {
            setBIC(BIC);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
        }
    }


    /**
     * @return the PstlAdr object<br>
     */
    public PstlAdr getPstlAdr() {
        return pstlAdr;
    }

    /**
     * @param pstlAdr the PstlAdr to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setPstlAdr(PstlAdr pstlAdr) {
        if (pstlAdr == null) {
            throw new IllegalArgumentException("Wrong parameter 'pstlAdr' in setPstlAdr()");
        }
        this.pstlAdr = pstlAdr;
    }

    /**
     * @param pstlAdr the PstlAdr to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setPstlAdr(PstlAdr pstlAdr, JsonValidationExceptionCollector collector) {
        try {
            setPstlAdr(pstlAdr);
        } catch (IllegalArgumentException e) {
            collector.addError("ERROR_NULL_NOT_ALLOWED", e);
            // throw e;
        }
    }

}

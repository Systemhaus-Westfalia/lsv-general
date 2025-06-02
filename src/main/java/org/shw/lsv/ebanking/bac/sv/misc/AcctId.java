package org.shw.lsv.ebanking.bac.sv.misc;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Unique and unambiguous identification for the account between the account owner and the account servicer.
 * Choose between "IBAN" and "Othr"
*/
public class AcctId {
    @JsonProperty("IBAN")
    @JsonInclude(JsonInclude.Include.NON_NULL)// Exclude fields with null values
    String iBAN=null;            // Choice Acct_Id_1: International Bank Account Number (IBAN) - identifier used internationally by financial institutions to uniquely identify the account of a customer.
    
    @JsonProperty("Othr")        // Das Json-Feld heisst nur "Othr"
    @JsonInclude(JsonInclude.Include.NON_NULL)// Exclude fields with null values
    AcctIdOthr acctIdOthr=null;  // Choice Acct_Id_2: Unique identification of an account, as assigned by the account servicer, using an identification scheme.


    
    public AcctId() {}


	public AcctId(RequestParams params, JsonValidationExceptionCollector collector) {
        // TODO: sich vergewissern, was BAC will: IBAN oder AcctIdOthr
        try {
            if ( !(params.getIbanDbtrAcct() == null || params.getIbanDbtrAcct().isEmpty()) ) {
                setIBAN(params.getIbanDbtrAcct(), collector);
            }
            else {
                setAcctIdOthr(new AcctIdOthr(params, collector), collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_ACCTID_INIT, e);
        }
    }
    
    public AcctId(String iban, JsonValidationExceptionCollector collector) {
		setIBAN(iban, collector);
    }

    
    public AcctId(AcctIdOthr acctIdOthr, JsonValidationExceptionCollector collector) {
		setAcctIdOthr(acctIdOthr, collector);
    }

    /**
	 * @return the IBAN
	 */
	public String getiBAN() {
        return iBAN; 
    }


    /**
     * @param IBAN the International Bank Account Number.t.<br>
     * <p>
     * The IBAN structure is defined by the ISO 13616 standard.
     * Each country’s national banking authority defines the format for its own IBANs (length, structure, included fields).
     * The account-holding bank assigns the IBAN to each customer account, following the national rules.
     * <p>
     * The parameter is validated.<br>
     * "pattern" : "[A-Z]{2,2}[0-9]{2,2}[a-zA-Z0-9]{1,30}".<br>
     * Example: "CR05011111111111111111".
     */
    public void setiBAN(String IBAN) {
        boolean patternOK = (IBAN != null && !IBAN.isEmpty()) && Pattern.matches(EBankingConstants.PATTERN_IBAN, IBAN);

        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'IBAN' (" + IBAN + ") in setIBAN()");
        }
        this.iBAN = IBAN;
    }

    /**
     * @param IBAN the International Bank Account Number.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     * <p>
     * The IBAN structure is defined by the ISO 13616 standard.
     * Each country’s national banking authority defines the format for its own IBANs (length, structure, included fields).
     * The account-holding bank assigns the IBAN to each customer account, following the national rules.
     * <p>
     */
    public void setIBAN(String IBAN, JsonValidationExceptionCollector collector) {
        try {
            setiBAN(IBAN);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
            //throw e;
        }
    }


    /**
	 * @return the AcctIdOthr object<br>
	 */
    public AcctIdOthr getAcctIdOthr() {
        return acctIdOthr;
    }


    /**
     * @param acctIdOthr the AcctIdOthr object to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setAcctIdOthr(AcctIdOthr acctIdOthr) {
        if (acctIdOthr == null) {
            throw new IllegalArgumentException("Wrong parameter 'acctIdOthr' in setAcctIdOthr()");
        }
        this.acctIdOthr = acctIdOthr;
    }

    /**
     * @param acctIdOthr the AcctIdOthr object to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setAcctIdOthr(AcctIdOthr acctIdOthr, JsonValidationExceptionCollector collector) {
        try {
            setAcctIdOthr(acctIdOthr);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }
}

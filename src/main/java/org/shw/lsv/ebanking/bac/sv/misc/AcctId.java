package org.shw.lsv.ebanking.bac.sv.misc;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.Camt052RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Unique and unambiguous identification for the account between the account owner and the account servicer.
 * Choose between "IBAN" and "Othr"
*/
@JsonInclude(JsonInclude.Include.NON_NULL)// Exclude fields with null values
public class AcctId {
    @JsonProperty("IBAN")
    String IBAN=null;            // Choice Acct_Id_1: International Bank Account Number (IBAN) - identifier used internationally by financial institutions to uniquely identify the account of a customer.
    
    @JsonProperty("Othr")        // Das Json-Feld heisst nur "Othr"
    AcctIdOthr AcctIdOthr=null;  // Choice Acct_Id_2: Unique identification of an account, as assigned by the account servicer, using an identification scheme.


    
    public AcctId() {}


	public AcctId(Camt052RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            if ( !(params.getIban() == null || params.getIban().isEmpty()) ) {
                setIBAN(params.getIban(), collector);
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
	public String getIBAN() {
        return IBAN; 
    }


    /**
     * @param IBAN the IBAN to be set.<br>
     * The parameter is validated.<br>
     * "pattern" : "[A-Z]{2,2}[0-9]{2,2}[a-zA-Z0-9]{1,30}".<br>
     * Example: "CR05011111111111111111".
     */
    public void setIBAN(String IBAN) {
        boolean patternOK = (IBAN != null && !IBAN.isEmpty()) && Pattern.matches(EBankingConstants.PATTERN_IBAN, IBAN);

        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'IBAN' (" + IBAN + ") in setIBAN()");
        }
        this.IBAN = IBAN;
    }

    /**
     * @param IBAN the IBAN to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setIBAN(String IBAN, JsonValidationExceptionCollector collector) {
        try {
            setIBAN(IBAN);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
            //throw e;
        }
    }


    /**
	 * @return the AcctIdOthr object<br>
	 */
    public AcctIdOthr getAcctIdOthr() {
        return AcctIdOthr;
    }


    /**
     * @param acctIdOthr the AcctIdOthr object to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setAcctIdOthr(AcctIdOthr acctIdOthr) {
        if (acctIdOthr == null) {
            throw new IllegalArgumentException("Wrong parameter 'acctIdOthr' in setAcctIdOthr()");
        }
        this.AcctIdOthr = acctIdOthr;
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

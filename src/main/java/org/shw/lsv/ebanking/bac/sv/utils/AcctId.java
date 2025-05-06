package org.shw.lsv.ebanking.bac.sv.utils;

import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    final String fullyQualifiedClassName=AcctId.class.getName();


	/**
	 * @return the IBAN
	 */
	public String getIBAN() {
        return IBAN; 
    }


	/**
	 * @param IBAN the IBAN to be set<br>
	 * The parameter is validated.<br>
	 * "pattern" : "[A-Z]{2,2}[0-9]{2,2}[a-zA-Z0-9]{1,30}"
     * e.g.: "CR05011111111111111111"
	 */
    public void setIBAN(String IBAN) {

		final String PATTERN = "[A-Z]{2,2}[0-9]{2,2}[a-zA-Z0-9]{1,30}";
		boolean patternOK = (IBAN!=null && !IBAN.isEmpty()) && Pattern.matches(PATTERN, IBAN);
		
		if(patternOK)
			this.IBAN = IBAN;
		else
	        throw new IllegalArgumentException("Wrong parameter 'IBAN' (" + IBAN +  ") in " +  fullyQualifiedClassName + ".setIBAN()" + "\n");
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
        if (acctIdOthr == null ) {
            throw new IllegalArgumentException("Wrong parameter 'acctIdOthr' in " +  fullyQualifiedClassName + ".setAcctIdOthr()" + "\n");
        }
        this.AcctIdOthr = acctIdOthr;
    }
}

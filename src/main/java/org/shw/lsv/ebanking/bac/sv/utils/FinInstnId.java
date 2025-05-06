package org.shw.lsv.ebanking.bac.sv.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Unique and unambiguous identification of a financial institution, as assigned under an internationally recognised or proprietary identification scheme. 
 */
public class FinInstnId {

    /**
     * Code allocated to a financial institution by the ISO 9362 - Business identifier code (BIC). Multiplicity [1..1]
     */
    @JsonProperty("BICFI")
	String BICFI;  // BICFI (Bank Identifier Code)

    @JsonIgnore
    final String fullyQualifiedClassName=FinInstnId.class.getName();


	/**
	 * @return the BICFI
	 */
	public String getBICFI() {
        return BICFI;
    }


	/**
	 * @param BICFI the BICFI to be set<br>
	 * The parameter is validated: null not allowed.<br>
	 */
    public void setBICFI(String BICFI) {
        if (BICFI == null || BICFI.isEmpty()) {
            throw new IllegalArgumentException("Wrong parameter 'BICFI' (" + BICFI + ") in " +  fullyQualifiedClassName + ".setBICFI()" + "\n");
        }
        this.BICFI = BICFI;
    }


    public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}

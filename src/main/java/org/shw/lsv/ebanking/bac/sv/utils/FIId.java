package org.shw.lsv.ebanking.bac.sv.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Identification of a financial institution
 */
public class FIId {
    @JsonProperty("FinInstnId")
    FinInstnId FinInstnId;  // BICFI (Bank Identifier Code)

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=FIId.class.getName();


	/**
	 * @return the FinInstnId object<br>
	 */

    public FinInstnId getFinInstnId() {
        return FinInstnId;
    }


	/**
	 * @param finInstnId the FinInstnId object to be set<br>
	 * The parameter is validated: null not allowed.<br>
	 */

    public void setFinInstnId(FinInstnId finInstnId) {
        if (finInstnId == null ) {
            throw new IllegalArgumentException("Wrong parameter 'finInstnId' in " +  FULLY_QUALIFIED_CLASSNAME + ".setFinInstnId()" + "\n");
        }
        this.FinInstnId = finInstnId;
    }


    public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

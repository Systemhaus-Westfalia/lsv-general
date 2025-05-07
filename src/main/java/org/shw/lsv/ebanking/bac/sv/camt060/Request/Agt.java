package org.shw.lsv.ebanking.bac.sv.camt060.Request;

import org.shw.lsv.ebanking.bac.sv.utils.FinInstnId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Agt {
    @JsonProperty("FinInstnId")
    FinInstnId FinInstnId;  // BICFI (Bank Identifier Code)

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=Agt.class.getName();


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

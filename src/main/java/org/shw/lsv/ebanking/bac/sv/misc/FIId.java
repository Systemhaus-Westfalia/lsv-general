package org.shw.lsv.ebanking.bac.sv.misc;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

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
	 * @param finInstnId the FinInstnId object to be set.
	 * The parameter is validated: null not allowed.
	 */
    public void setFinInstnId(FinInstnId finInstnId, JsonValidationExceptionCollector collector) {
        try {
            if (finInstnId == null) {
                throw new IllegalArgumentException(
                    "Wrong parameter 'finInstnId' in " + FULLY_QUALIFIED_CLASSNAME + ".setFinInstnId()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setFinInstnId()";
            collector.addError(context, e);

            throw e;
        }

        this.FinInstnId = finInstnId;
    }


    public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

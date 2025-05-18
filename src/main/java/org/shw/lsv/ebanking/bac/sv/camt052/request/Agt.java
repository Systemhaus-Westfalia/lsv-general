package org.shw.lsv.ebanking.bac.sv.camt052.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.misc.FinInstnId;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Agt {
    @JsonProperty("FinInstnId")
    FinInstnId FinInstnId;  // BICFI (Bank Identifier Code)


	public Agt() {
    }


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
        if (finInstnId == null) {
            throw new IllegalArgumentException("Wrong parameter 'finInstnId' in setFinInstnId()");
        }
        this.FinInstnId = finInstnId;
    }

    /**
     * @param finInstnId the FinInstnId object to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setFinInstnId(FinInstnId finInstnId, JsonValidationExceptionCollector collector) {
        try {
            setFinInstnId(finInstnId);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }


    public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

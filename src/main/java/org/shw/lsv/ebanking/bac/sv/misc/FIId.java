package org.shw.lsv.ebanking.bac.sv.misc;

import org.shw.lsv.ebanking.bac.sv.handling.Camt052RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Identification of a financial institution
 */
public class FIId {
    @JsonProperty("FinInstnId")
    FinInstnId FinInstnId;  // BICFI (Bank Identifier Code)


	public FIId() {}


    public FIId(Camt052RequestParams params, String context, JsonValidationExceptionCollector collector) {
        try {
            setFinInstnId(new FinInstnId(params, context, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_FIID_INIT, e);
        }
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

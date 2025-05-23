package org.shw.lsv.ebanking.bac.sv.camt052.request;

import org.shw.lsv.ebanking.bac.sv.handling.RequestParamsCamt052;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.misc.FinInstnId;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Agt {
    @JsonProperty("FinInstnId")
    FinInstnId finInstnId;  // BICFI (Bank Identifier Code)


	public Agt() {
    }


    public Agt(RequestParamsCamt052 params, String context, JsonValidationExceptionCollector collector) {
        try {
            setFinInstnId (new FinInstnId( params, context, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_AGT_INIT, e);
        }
    }


    /**
	 * @return the FinInstnId object<br>
	 */
    public FinInstnId getFinInstnId() {
        return finInstnId;
    }


    /**
     * @param finInstnId the FinInstnId object to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setFinInstnId(FinInstnId finInstnId) {
        if (finInstnId == null) {
            throw new IllegalArgumentException("Wrong parameter 'finInstnId' in setFinInstnId()");
        }
        this.finInstnId = finInstnId;
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

package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PAIN001RequestDocument implements Validatable {

    @JsonProperty("CstmrCdtTrfInitn")  // "CstmrCdtTrfInitn" is the name of the field in the JSON
    CstmrCdtTrfInitn cstmrCdtTrfInitn;

    public PAIN001RequestDocument() { }


    public PAIN001RequestDocument(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setCstmrCdtTrfInitn(new CstmrCdtTrfInitn(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_DOCUMENT_INIT, e);
        }
    }


    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (cstmrCdtTrfInitn == null) {
                throw new IllegalArgumentException("cstmrCdtTrfInitn cannot be null");
            }

            // Validate nested objects
            if (cstmrCdtTrfInitn instanceof Validatable) {
                ((Validatable) cstmrCdtTrfInitn).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    /**
    * @return the CstmrCdtTrfInitn object<br>
    */
    public CstmrCdtTrfInitn getCstmrCdtTrfInitn() {
        return cstmrCdtTrfInitn;
    }

    /**
     * @param cstmrCdtTrfInitn the CstmrCdtTrfInitn to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setCstmrCdtTrfInitn(CstmrCdtTrfInitn cstmrCdtTrfInitn, JsonValidationExceptionCollector collector) {
        try {
            setCstmrCdtTrfInitn(cstmrCdtTrfInitn);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }

    /**
     * @param cstmrCdtTrfInitn the CstmrCdtTrfInitn to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setCstmrCdtTrfInitn(CstmrCdtTrfInitn cstmrCdtTrfInitn) {
        if (cstmrCdtTrfInitn == null) {
            throw new IllegalArgumentException("Wrong parameter 'cstmrCdtTrfInitn' in setCstmrCdtTrfInitn()");
        }
        this.cstmrCdtTrfInitn = cstmrCdtTrfInitn;
    }
}

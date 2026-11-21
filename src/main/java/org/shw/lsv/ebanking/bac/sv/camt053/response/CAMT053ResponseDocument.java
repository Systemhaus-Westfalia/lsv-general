package org.shw.lsv.ebanking.bac.sv.camt053.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.misc.Rejection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CAMT053ResponseDocument {

    @JsonProperty("BkToCstmrStmt")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    BkToCstmrStmt bkToCstmrStmt;

    @JsonProperty("admi.002.001.01")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Rejection rejection;

    public CAMT053ResponseDocument() {}

    public CAMT053ResponseDocument(BkToCstmrStmt bkToCstmrStmt) {
        this.bkToCstmrStmt = bkToCstmrStmt;
    }

    /**
     * Validates the CAMT053ResponseDocument.
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            // A document must contain either a statement or a rejection.
            boolean hasStmt = bkToCstmrStmt != null;
            boolean hasRejection = rejection != null;

            if (!hasStmt && !hasRejection) {
                throw new IllegalArgumentException("Document must contain either a BkToCstmrStmt or a Rejection.");
            }

            // Only validate the BkToCstmrStmt if it exists. Rejection has no validation.
            if (hasStmt && bkToCstmrStmt instanceof org.shw.lsv.ebanking.bac.sv.handling.Validatable) {
                ((org.shw.lsv.ebanking.bac.sv.handling.Validatable) bkToCstmrStmt).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }


    /**
     * @return the BkToCstmrStmt object<br>
     */
    public BkToCstmrStmt getBkToCstmrStmt() {
        return bkToCstmrStmt;
    }

    /**
     * @param bkToCstmrStmt the BkToCstmrStmt to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setBkToCstmrStmt(BkToCstmrStmt bkToCstmrStmt) {
        if (bkToCstmrStmt == null) {
            throw new IllegalArgumentException("Wrong parameter 'bkToCstmrStmt' in setBkToCstmrStmt()");
        }
        this.bkToCstmrStmt = bkToCstmrStmt;
    }

    /**
     * @param bkToCstmrStmt the BkToCstmrStmt to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setBkToCstmrStmt(BkToCstmrStmt bkToCstmrStmt, JsonValidationExceptionCollector collector) {
        try {
            setBkToCstmrStmt(bkToCstmrStmt);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    /**
    * @return the Rejection object<br>
    */
    public Rejection getRejection() {
        return rejection;
    }

    /**
     * @param rejection the Rejection to be set<br>
     */
    public void setRejection(Rejection rejection) {
        this.rejection = rejection;
    }

    /**
     * @param rejection the Rejection to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setRejection(Rejection rejection, JsonValidationExceptionCollector collector) {
        try {
            setRejection(rejection);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}
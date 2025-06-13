package org.shw.lsv.ebanking.bac.sv.camt053.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CAMT053ResponseDocument {

    @JsonProperty("BkToCstmrStmt")
    BkToCstmrStmt bkToCstmrStmt;

    public CAMT053ResponseDocument() { }

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
     * Validates the CAMT053ResponseDocument.
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (bkToCstmrStmt == null) {
                throw new IllegalArgumentException("BkToCstmrStmt cannot be null");
            }
            // Optionally, validate nested object if it implements a validation interface
            if (bkToCstmrStmt instanceof org.shw.lsv.ebanking.bac.sv.handling.Validatable) {
                ((org.shw.lsv.ebanking.bac.sv.handling.Validatable) bkToCstmrStmt).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}
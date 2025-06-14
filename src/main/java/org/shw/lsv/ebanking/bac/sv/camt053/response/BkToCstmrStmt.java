package org.shw.lsv.ebanking.bac.sv.camt053.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.misc.GrpHdr;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BkToCstmrStmt {

    @JsonProperty(value = "GrpHdr", required = true)
    GrpHdr grpHdr;

    @JsonProperty("Stmt")
    Stmt stmt;

    public BkToCstmrStmt() {}

    public BkToCstmrStmt(GrpHdr grpHdr, Stmt stmt) {
        setGrpHdr(grpHdr);
        setStmt(stmt);
    }

    public BkToCstmrStmt(JsonValidationExceptionCollector collector) {
        // Constructor for using the JsonValidationExceptionCollector
        // This constructor can be used to initialize the object with validation errors
        // if needed in the future.
    }


    /**
     * @return the GrpHdr object<br>
     */
    public GrpHdr getGrpHdr() {
        return grpHdr;
    }

    /**
     * @param grpHdr the GrpHdr to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setGrpHdr(GrpHdr grpHdr) {
        if (grpHdr == null) {
            throw new IllegalArgumentException("Wrong parameter 'grpHdr' in setGrpHdr()");
        }
        this.grpHdr = grpHdr;
    }

    /**
     * @param grpHdr the GrpHdr to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setGrpHdr(GrpHdr grpHdr, JsonValidationExceptionCollector collector) {
        try {
            setGrpHdr(grpHdr);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    /**
     * @return the Stmt object<br>
     */
    public Stmt getStmt() {
        return stmt;
    }

    /**
     * @param stmt the Stmt to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setStmt(Stmt stmt) {
        if (stmt == null) {
            throw new IllegalArgumentException("Wrong parameter 'stmt' in setStmt()");
        }
        this.stmt = stmt;
    }

    /**
     * @param stmt the Stmt to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setStmt(Stmt stmt, JsonValidationExceptionCollector collector) {
        try {
            setStmt(stmt);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    /**
     * Validates the BkToCstmrStmt.
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (grpHdr == null) {
                throw new IllegalArgumentException("GrpHdr cannot be null");
            }
            if (stmt == null) {
                throw new IllegalArgumentException("Stmt cannot be null");
            }
            // Optionally, validate nested objects if they implement a validation interface
            if (grpHdr instanceof org.shw.lsv.ebanking.bac.sv.handling.Validatable) {
                ((org.shw.lsv.ebanking.bac.sv.handling.Validatable) grpHdr).validate(collector);
            }
            if (stmt instanceof org.shw.lsv.ebanking.bac.sv.handling.Validatable) {
                ((org.shw.lsv.ebanking.bac.sv.handling.Validatable) stmt).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}
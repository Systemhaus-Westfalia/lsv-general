package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DbtrAcctId {

    @JsonProperty("Othr")
    DbtrAcctIdOthr dbtrAcctIdOthr;

    @JsonProperty("IBAN")
    String iBAN;  // "International Bank Account Number (IBAN) - identifier used internationally by financial institutions to uniquely identify the account of a customer."

    public DbtrAcctId(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setDbtrAcctIdOthr (new DbtrAcctIdOthr(params, collector), collector);

            // TODO: ueberpruefen, ob IBAN ein muss-Feld ist
            setIBAN(params.getIban(), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_DBTRACCTID_INIT, e);
        }
    }

    /**
     * @return the DbtrAcctIdOthr object<br>
     */
    public DbtrAcctIdOthr getDbtrAcctIdOthr() {
        return dbtrAcctIdOthr;
    }

    /**
     * @param dbtrAcctIdOthr the DbtrAcctIdOthr to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setDbtrAcctIdOthr(DbtrAcctIdOthr dbtrAcctIdOthr) {
        if (dbtrAcctIdOthr == null) {
            throw new IllegalArgumentException("Wrong parameter 'dbtrAcctIdOthr' in setDbtrAcctIdOthr()");
        }
        this.dbtrAcctIdOthr = dbtrAcctIdOthr;
    }

    /**
     * @param dbtrAcctIdOthr the DbtrAcctIdOthr to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setDbtrAcctIdOthr(DbtrAcctIdOthr dbtrAcctIdOthr, JsonValidationExceptionCollector collector) {
        try {
            setDbtrAcctIdOthr(dbtrAcctIdOthr);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }

    /**
     * @return the IBAN
     */
    public String getIBAN() {
        return iBAN;
    }

    /**
     * @param IBAN the IBAN to be set.<br>
     * The parameter is validated: null not allowed.<br>
     * Pattern: "[A-Z]{2}[0-9]{2}[a-zA-Z0-9]{1,30}".<br>
     * Example: "CR05011111111111111111".
     */
    public void setIBAN(String IBAN) {
        boolean patternOK = (IBAN != null && !IBAN.isEmpty()) &&
            java.util.regex.Pattern.matches(EBankingConstants.PATTERN_IBAN, IBAN);

        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'IBAN' (" + IBAN + ") in setIBAN()");
        }
        this.iBAN = IBAN;
    }

    /**
     * @param IBAN the IBAN to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setIBAN(String IBAN, JsonValidationExceptionCollector collector) {
        try {
            setIBAN(IBAN);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
            //throw e;
        }
    }

}

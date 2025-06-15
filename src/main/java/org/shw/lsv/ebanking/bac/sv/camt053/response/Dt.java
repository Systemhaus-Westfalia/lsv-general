package org.shw.lsv.ebanking.bac.sv.camt053.response;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Dt {

    @JsonProperty("Dt")
    String dt;  // Date

    /**
     * Default constructor.
     */
    public Dt() {
    }

    /**
     * Constructor initializing the Dt object with a date string.
     * @param dt the date string, expected to match EBankingConstants.PATTERN_DATE.
     */
    public Dt(String dt) {
        setDt(dt);
    }

    /**
     * @return the Dt (Date string).
     */
    public String getDt() {
        return dt;
    }

    /**
     * Sets the Dt (Date string).
     * The parameter is validated against EBankingConstants.PATTERN_DATE.
     * Example: "2020-09-08".
     *
     * @param dt the Dt (Date string) to be set.
     * @throws IllegalArgumentException if the dt is null or does not match the pattern.
     */
    public void setDt(String dt) {
        boolean patternOK = (dt != null) && Pattern.matches(EBankingConstants.PATTERN_DATE, dt);
        if (!patternOK) {
            throw new IllegalArgumentException(
                "Wrong parameter 'dt' (" + dt + ") in setDt(). Expected pattern: " + EBankingConstants.PATTERN_DATE
            );
        }
        this.dt = dt;
    }

    /**
     * Sets the Dt (Date string) and collects validation errors.
     * The parameter is validated against EBankingConstants.PATTERN_DATE.
     *
     * @param dt the Dt (Date string) to be set.
     * @param collector the JsonValidationExceptionCollector to collect validation errors.
     */
    public void setDt(String dt, JsonValidationExceptionCollector collector) {
        try {
            setDt(dt);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
        }
    }
}

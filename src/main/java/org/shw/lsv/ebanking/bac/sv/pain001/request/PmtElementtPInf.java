package org.shw.lsv.ebanking.bac.sv.pain001.request;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PmtElementtPInf {

    @JsonProperty("InstrPrty")
    String instrPrty;  // Instruction Priority

    public PmtElementtPInf() { }

    public PmtElementtPInf(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setInstrPrty( params.getInstrPrty(), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_PMTINF_INIT, e);
        }
    }

    /**
     * @return the InstrPrty
     */
    public String getInstrPrty() {
        return instrPrty;
    }

    /**
     * @param instrPrty the instruction priority to be set.<br>instruction priority to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     * <p>
     * InstrPrty specifies the priority level of the payment instruction.
     * It tells the bank whether the payment should be processed as normal or urgent.
     * <p>
     * The parameter is validated: null not allowed.<br>
     * Pattern: "(HIGH|NORM)"<br>
     * Example: "NORM"
     */
    public void setInstrPrty(String instrPrty) {
        boolean patternOK = (instrPrty != null) && Pattern.matches(EBankingConstants.PATTERN_INSTR_PRTY, instrPrty);

        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'instrPrty' (" + instrPrty + ") in setInstrPrty()");
        }
        this.instrPrty = instrPrty;
    }

    /**
     * @param instrPrty the instruction priority to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     * <p>
     * InstrPrty specifies the priority level of the payment instruction.
     * It tells the bank whether the payment should be processed as normal or urgent.
     * <p>
     * Pattern: "(HIGH|NORM)"<br>
     * Example: "NORM"
     */
    public void setInstrPrty(String instrPrty, JsonValidationExceptionCollector collector) {
        try {
            setInstrPrty(instrPrty);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
            //throw e;
        }
    }


}

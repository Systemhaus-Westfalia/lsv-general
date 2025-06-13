package org.shw.lsv.ebanking.bac.sv.camt053.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookgOrValDt {

    @JsonProperty("Dt")
    String dt;

    public BookgOrValDt() {}

    public  BookgOrValDt(RequestParams params, String context, JsonValidationExceptionCollector collector) {
        try {
            if ( context.equals(EBankingConstants.CONTEXT_BOOKING_DATE)) {
                if ( !(params.getBookgDt() == null || params.getBookgDt().isEmpty()) ) {
                    setDt(params.getBookgDt(), collector);
            }
            } else if ( context.equals(EBankingConstants.CONTEXT_VALID_DATE)) {
                if (( !(params.getValDt() == null || params.getValDt().isEmpty()) ) ) {
                    setDt(params.getCatPurpCd(), collector);
                } 
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_BOOKGORVALDT_INIT, e);
        }
    }

    /**
     * @return the Dt (booking date)
     */
    public String getDt() {
        return dt;
    }

    /**
     * @param dt the Dt (booking date) to be set.<br>
     * The parameter is validated: null not allowed.<br>
     * Pattern: "^\\d{4}-\\d{2}-\\d{2}$" (example: "2024-07-22")
     */
    public void setDt(String dt) {
        boolean patternOK = (dt != null) && dt.matches(EBankingConstants.PATTERN_DATE);
        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'dt' (" + dt + ") in setDt()");
        }
        this.dt = dt;
    }

    /**
     * @param dt the Dt (booking date) to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setDt(String dt, JsonValidationExceptionCollector collector) {
        try {
            setDt(dt);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
        }
    }
}
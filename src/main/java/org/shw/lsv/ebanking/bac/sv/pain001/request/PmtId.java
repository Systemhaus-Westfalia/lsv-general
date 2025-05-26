package org.shw.lsv.ebanking.bac.sv.pain001.request;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PmtId {

    @JsonProperty("EndToEndId")
    String endToEndId;  // unique identifier for each individual transaction within a payment batch.

    public PmtId(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setEndToEndId(params.getEndToEndId(), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_FININSTN_INIT, e);
        }
    }

    /**
     * @return the EndToEndId
     */
    public String getEndToEndId() {
        return endToEndId;
    }

    /**
     * @param endToEndId the unique identifier for each individual transaction within a payment batch.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     * <p>
     * It is assigned by the initiating party (the sender, usually the company or bank creating the payment file).
     * It is used to track and reconcile the payment from the originator to the ultimate beneficiary, across all intermediaries.
     * <p>
     * The value is passed unchanged from the sender to the receiver, ensuring traceability.
     * <p>
     * The parameter is validated: null not allowed.<br>
     * Pattern: "[0-9a-zA-Z/\\-?:().,'+ ]{1,35}"<br>
     * Example: "DOM12345"
     */
    public void setEndToEndId(String endToEndId) {
        boolean patternOK = (endToEndId != null && !endToEndId.isEmpty()) &&
            Pattern.matches(EBankingConstants.PATTERN_ENDTOENDID, endToEndId);

        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'endToEndId' (" + endToEndId + ") in setEndToEndId()");
        }
        this.endToEndId = endToEndId;
    }

    /**
     * @param endToEndId the unique identifier for each individual transaction within a payment batch.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     * <p>
     * It is assigned by the initiating party (the sender, usually the company or bank creating the payment file).
     * It is used to track and reconcile the payment from the originator to the ultimate beneficiary, across all intermediaries.
     * <p>
     * The value is passed unchanged from the sender to the receiver, ensuring traceability.
     */
    public void setEndToEndId(String endToEndId, JsonValidationExceptionCollector collector) {
        try {
            setEndToEndId(endToEndId);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
            //throw e;
        }
    }

}

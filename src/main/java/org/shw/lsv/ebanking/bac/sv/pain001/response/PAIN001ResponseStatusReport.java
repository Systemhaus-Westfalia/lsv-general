package org.shw.lsv.ebanking.bac.sv.pain001.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PAIN001ResponseStatusReport implements Validatable {

    @JsonProperty("Envelope")    // "Envelope" is the name of the field in the JSON
    PAIN001ResponseStatusReportEnvelope pAIN001ResponseStatusReportEnvelope;

    public PAIN001ResponseStatusReport() {}

    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (pAIN001ResponseStatusReportEnvelope == null) {
                throw new IllegalArgumentException("Envelope cannot be null");
            }

            // Validate nested objects
            if (pAIN001ResponseStatusReportEnvelope instanceof Validatable) {
                ((Validatable) pAIN001ResponseStatusReportEnvelope).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    public PAIN001ResponseStatusReportEnvelope getpAIN001ResponseStatusReportEnvelope() {
        return pAIN001ResponseStatusReportEnvelope;
    }

    public void setpAIN001ResponseStatusReportEnvelope(PAIN001ResponseStatusReportEnvelope pAIN001ResponseStatusReportEnvelope) {
        this.pAIN001ResponseStatusReportEnvelope = pAIN001ResponseStatusReportEnvelope;
    }
}
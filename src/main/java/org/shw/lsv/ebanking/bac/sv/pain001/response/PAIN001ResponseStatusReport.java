package org.shw.lsv.ebanking.bac.sv.pain001.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PAIN001ResponseStatusReport implements Validatable {

    @JsonProperty("Envelope")    // "Envelope" is the name of the field in the JSON
    PAIN001ResponseStatusReportEnvelope pAIN001ResponseStatusReportEnvelope;

    public PAIN001ResponseStatusReport() {}

    public PAIN001ResponseStatusReport(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setPAIN001ResponseStatusReportEnvelope(new PAIN001ResponseStatusReportEnvelope(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_REQUEST_ENVELOP_INIT, e);
        }
    }

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

    /**
     * @return the PAIN001ResponseStatusReportEnvelope object<br>
     */
    public PAIN001ResponseStatusReportEnvelope getpAIN001ResponseStatusReportEnvelope() {
        return pAIN001ResponseStatusReportEnvelope;
    }

    /**
     * @param pAIN001ResponseStatusReportEnvelope the PAIN001ResponseStatusReportEnvelope to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setpAIN001ResponseStatusReportEnvelope(PAIN001ResponseStatusReportEnvelope pAIN001ResponseStatusReportEnvelope) {
        if (pAIN001ResponseStatusReportEnvelope == null) {
            throw new IllegalArgumentException("Wrong parameter 'pAIN001ResponseStatusReportEnvelope' in setPAIN001ResponseStatusReportEnvelope()");
        }
        this.pAIN001ResponseStatusReportEnvelope = pAIN001ResponseStatusReportEnvelope;
    }

    /**
     * @param pAIN001ResponseStatusReportEnvelope the PAIN001ResponseStatusReportEnvelope to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setPAIN001ResponseStatusReportEnvelope(PAIN001ResponseStatusReportEnvelope pAIN001ResponseStatusReportEnvelope,
                                                      JsonValidationExceptionCollector collector) {
        try {
            setpAIN001ResponseStatusReportEnvelope(pAIN001ResponseStatusReportEnvelope);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}
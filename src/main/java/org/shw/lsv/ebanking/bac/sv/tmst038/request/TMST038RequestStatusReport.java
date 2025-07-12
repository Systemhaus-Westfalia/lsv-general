package org.shw.lsv.ebanking.bac.sv.tmst038.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.pain001.response.PAIN001ResponseStatusReportEnvelope;
import org.shw.lsv.ebanking.bac.sv.pain001.response.PAIN001ResponseStatusReportFile;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TMST038RequestStatusReport implements Validatable {

    @JsonProperty("file")    // This is the only difference to PAIN001ResponseStatusReport!!!!
    PAIN001ResponseStatusReportFile pain001ResponseStatusReportFile;

    public TMST038RequestStatusReport() {}

    public TMST038RequestStatusReport(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setPain001ResponseStatusReportFile(new PAIN001ResponseStatusReportFile(new PAIN001ResponseStatusReportEnvelope(params, collector)), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_REQUEST_ENVELOP_INIT, e);
        }
    }

    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (pain001ResponseStatusReportFile == null) {
                throw new IllegalArgumentException("File cannot be null");
            }

            // Validate nested objects
            if (pain001ResponseStatusReportFile instanceof Validatable) {
                ((Validatable) pain001ResponseStatusReportFile).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    public PAIN001ResponseStatusReportFile getPain001ResponseStatusReportFile() {
        return pain001ResponseStatusReportFile;
    }

    public void setPain001ResponseStatusReportFile(PAIN001ResponseStatusReportFile pain001ResponseStatusReportFile) {
        if (pain001ResponseStatusReportFile == null) {
            throw new IllegalArgumentException("Wrong parameter 'pain001ResponseStatusReportFile' in setPain001ResponseStatusReportFile()");
        }
        this.pain001ResponseStatusReportFile = pain001ResponseStatusReportFile;
    }

    public void setPain001ResponseStatusReportFile(PAIN001ResponseStatusReportFile pain001ResponseStatusReportFile, JsonValidationExceptionCollector collector) {
        try {
            setPain001ResponseStatusReportFile(pain001ResponseStatusReportFile);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}
package org.shw.lsv.ebanking.bac.sv.camt053.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CAMT053Response implements Validatable {

    @JsonProperty("File")    // "File" is the name of the field in the JSON
    CAMT053ResponseFile camt053ResponseFile;

    public CAMT053Response() {}

    public CAMT053Response(CAMT053ResponseFile camt053ResponseFile) {
        setCamt053ResponseFile(camt053ResponseFile);
    }

    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (camt053ResponseFile == null) {
                throw new IllegalArgumentException("File cannot be null");
            }

            // Validate nested objects
            if (camt053ResponseFile instanceof Validatable) {
                ((Validatable) camt053ResponseFile).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    public CAMT053ResponseFile getCamt053ResponseFile() {
        return camt053ResponseFile;
    }

    public void setCamt053ResponseFile(CAMT053ResponseFile camt053ResponseFile) {
        if (camt053ResponseFile == null) {
            throw new IllegalArgumentException("Wrong parameter 'camt053ResponseFile' in setCamt053ResponseFile()");
        }
        this.camt053ResponseFile = camt053ResponseFile;
    }

    public void setCamt053ResponseFile(CAMT053ResponseFile camt053ResponseFile, JsonValidationExceptionCollector collector) {
        try {
            setCamt053ResponseFile(camt053ResponseFile);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}
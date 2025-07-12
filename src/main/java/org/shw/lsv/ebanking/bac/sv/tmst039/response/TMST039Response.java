package org.shw.lsv.ebanking.bac.sv.tmst039.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TMST039Response implements Validatable {

    @JsonProperty("File")    // "File" is the name of the field in the JSON
    TMST039ResponseFile tmst039ResponseFile;

    public TMST039Response() {}

    public TMST039Response(TMST039ResponseFile tmst039ResponseFile) {
        setTmst039ResponseFile(tmst039ResponseFile);
    }

    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (tmst039ResponseFile == null) {
                throw new IllegalArgumentException("File cannot be null");
            }
            if (tmst039ResponseFile instanceof Validatable) {
                ((Validatable) tmst039ResponseFile).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    public TMST039ResponseFile getTmst039ResponseFile() {
        return tmst039ResponseFile;
    }

    public void setTmst039ResponseFile(TMST039ResponseFile tmst039ResponseFile) {
        if (tmst039ResponseFile == null) {
            throw new IllegalArgumentException("Wrong parameter 'tmst039ResponseFile' in setTmst039ResponseFile()");
        }
        this.tmst039ResponseFile = tmst039ResponseFile;
    }

    public void setTmst039ResponseFile(TMST039ResponseFile tmst039ResponseFile, JsonValidationExceptionCollector collector) {
        try {
            setTmst039ResponseFile(tmst039ResponseFile);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}
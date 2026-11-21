package org.shw.lsv.ebanking.bac.sv.pain001.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Root class for PAIN001 Event Notification Response.
 */
public class PAIN001ResponseEvtNtfn implements Validatable {

    @JsonProperty("File")    // "File" is the name of the field in the JSON
    PAIN001ResponseEvtNtfnFile pain001ResponseEvtNtfnFile;

    public PAIN001ResponseEvtNtfn() {}

    public PAIN001ResponseEvtNtfn(PAIN001ResponseEvtNtfnFile pain001ResponseEvtNtfnFile) {
        setPain001ResponseEvtNtfnFile(pain001ResponseEvtNtfnFile);
    }

    public PAIN001ResponseEvtNtfnFile getPain001ResponseEvtNtfnFile() {
        return pain001ResponseEvtNtfnFile;
    }

    public void setPain001ResponseEvtNtfnFile(PAIN001ResponseEvtNtfnFile pain001ResponseEvtNtfnFile) {
        if (pain001ResponseEvtNtfnFile == null) {
            throw new IllegalArgumentException("Wrong parameter 'pain001ResponseEvtNtfnFile' in setPain001ResponseEvtNtfnFile()");
        }
        this.pain001ResponseEvtNtfnFile = pain001ResponseEvtNtfnFile;
    }

    public void setPain001ResponseEvtNtfnFile(PAIN001ResponseEvtNtfnFile pain001ResponseEvtNtfnFile, JsonValidationExceptionCollector collector) {
        try {
            setPain001ResponseEvtNtfnFile(pain001ResponseEvtNtfnFile);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (pain001ResponseEvtNtfnFile == null) {
                throw new IllegalArgumentException("File cannot be null");
            }
            if (pain001ResponseEvtNtfnFile instanceof Validatable) {
                ((Validatable) pain001ResponseEvtNtfnFile).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}
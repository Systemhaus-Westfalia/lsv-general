package org.shw.lsv.ebanking.bac.sv.tmst038.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.pain001.response.PAIN001ResponseEvtNtfnEnvelope;
import org.shw.lsv.ebanking.bac.sv.pain001.response.PAIN001ResponseEvtNtfnFile;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Root class for PAIN001 Event Notification Response.
 */
public class TMST038RequestEvtNtfn implements Validatable {

    @JsonProperty("file")    // This is the only difference to PAIN001ResponseEvtNtfn!!!!
    PAIN001ResponseEvtNtfnFile pain001ResponseEvtNtfnFile;

    public TMST038RequestEvtNtfn() {}

    public TMST038RequestEvtNtfn(RequestParams params, JsonValidationExceptionCollector collector) {
        setPain001ResponseEvtNtfnFile(new PAIN001ResponseEvtNtfnFile(new PAIN001ResponseEvtNtfnEnvelope(params, collector)), collector);
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
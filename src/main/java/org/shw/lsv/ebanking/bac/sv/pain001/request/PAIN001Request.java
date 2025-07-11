package org.shw.lsv.ebanking.bac.sv.pain001.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

public class PAIN001Request implements Validatable {
    @JsonProperty("file")    // "file" is the name of the field in the JSON
    private PAIN001RequestFile pain001RequestFile;

    // Jackson constructor
    public PAIN001Request() {}

    // Validation constructor
    public PAIN001Request(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setPain001RequestFile(new PAIN001RequestFile(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_PAIN001REQUEST_INIT, e);
        }
    }

    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (pain001RequestFile == null) {
                throw new IllegalArgumentException("PAIN001RequestFile cannot be null");
            }

            // Validate nested objects
            if (pain001RequestFile instanceof Validatable) {
                ((Validatable) pain001RequestFile).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    public PAIN001RequestFile getPain001RequestFile() {
        return pain001RequestFile;
    }



    /**
     * @param pain001RequestFile the PAIN001RequestFile to be set.
     */
    public void setPain001RequestFile(PAIN001RequestFile pain001RequestFile) {
        if (pain001RequestFile == null) {
            throw new IllegalArgumentException(
                "Wrong parameter 'pain001RequestFile' in setPain001RequestFile()"
            );
        }
        this.pain001RequestFile = pain001RequestFile;
    }



    /**
     * @param pain001RequestFile the PAIN001RequestFile to be set.
     * The parameter is validated: null not allowed.
     * @param collector the JsonValidationExceptionCollector to be set.
     */
    public void setPain001RequestFile(PAIN001RequestFile pain001RequestFile, 
                                        JsonValidationExceptionCollector collector) {
        try {
            setPain001RequestFile(pain001RequestFile);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }
}
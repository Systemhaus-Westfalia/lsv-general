package org.shw.lsv.ebanking.bac.sv.camt052.request;

import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request Consulta de Saldo    
 */
public class CAMT052Request implements Validatable {
    
    @JsonProperty("file")    // "file" is the name of the field in the JSON
    CAMT052RequestFile cAMT052RequestFile;
    
    public CAMT052Request() {}

    public CAMT052Request(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setcAMT052RequestFile(new CAMT052RequestFile(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_CAMT052REQUEST_INIT, e);
        }
    }


    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (cAMT052RequestFile == null) {
                throw new IllegalArgumentException("Property 'file' cannot be null");
            }

            // Validate nested objects
            if (cAMT052RequestFile instanceof Validatable) {
                ((Validatable) cAMT052RequestFile).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    public CAMT052RequestFile getcAMT052RequestFile() {
        return cAMT052RequestFile;
    }


    /**
     * @param cAMT052RequestFile the CAMT052RequestFile to be set.
     * The parameter is validated: null not allowed.
     * @param collector the JsonValidationExceptionCollector to be set.
     */
    public void setcAMT052RequestFile(CAMT052RequestFile cAMT052RequestFile, 
                                        JsonValidationExceptionCollector collector) {
        try {
            setcAMT052RequestFile(cAMT052RequestFile);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    /**
     * @param cAMT052RequestFile the CAMT052RequestFile to be set.
     */
    public void setcAMT052RequestFile(CAMT052RequestFile cAMT052RequestFile) {
        if (cAMT052RequestFile == null) {
            throw new IllegalArgumentException(
                "Wrong parameter 'cAMT052RequestFile' in setcAMT052RequestFile()"
            );
        }
        this.cAMT052RequestFile = cAMT052RequestFile;
    }

}

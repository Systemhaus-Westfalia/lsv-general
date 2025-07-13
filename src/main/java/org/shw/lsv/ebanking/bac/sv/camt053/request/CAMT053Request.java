package org.shw.lsv.ebanking.bac.sv.camt053.request;

import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request Consulta de Saldo CAMT053
 */
public class CAMT053Request implements Validatable {

    @JsonProperty("file")    // "file" is the name of the field in the JSON
    CAMT053RequestFile camt053RequestFile;

    public CAMT053Request() {}

    public CAMT053Request(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setCamt053RequestFile(new CAMT053RequestFile(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_CAMT053REQUEST_INIT, e);
        }
    }

    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (camt053RequestFile == null) {
                throw new IllegalArgumentException("Property 'file' cannot be null");
            }

            // Validate nested objects
            if (camt053RequestFile instanceof Validatable) {
                ((Validatable) camt053RequestFile).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    public CAMT053RequestFile getCamt053RequestFile() {
        return camt053RequestFile;
    }

    /**
     * @param camt053RequestFile the CAMT053RequestFile to be set.
     * The parameter is validated: null not allowed.
     * @param collector the JsonValidationExceptionCollector to be set.
     */
    public void setCamt053RequestFile(CAMT053RequestFile camt053RequestFile,
                                          JsonValidationExceptionCollector collector) {
        try {
            setCamt053RequestFile(camt053RequestFile);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    /**
     * @param camt053RequestFile the CAMT053RequestFile to be set.
     */
    public void setCamt053RequestFile(CAMT053RequestFile camt053RequestFile) {
        if (camt053RequestFile == null) {
            throw new IllegalArgumentException(
                "Wrong parameter 'camt053RequestFile' in setCamt053RequestFile()"
            );
        }
        this.camt053RequestFile = camt053RequestFile;
    }
}
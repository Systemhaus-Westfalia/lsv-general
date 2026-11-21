package org.shw.lsv.ebanking.bac.sv.camt052.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CAMT052Response implements Validatable {
    
    @JsonProperty("File")    // "File" is the name of the field in the JSON
    CAMT052ResponseFile cAMT052ResponseFile;


    public CAMT052Response() {}


    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (cAMT052ResponseFile == null) {
                throw new IllegalArgumentException("File cannot be null");
            }

            // Validate nested objects
            if (cAMT052ResponseFile instanceof Validatable) {
                ((Validatable) cAMT052ResponseFile).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
    
    public CAMT052ResponseFile getcAMT052ResponseFile() {
        return cAMT052ResponseFile;
    }


    public void setcAMT052ResponseFile(CAMT052ResponseFile cAMT052ResponseFile) {
        this.cAMT052ResponseFile = cAMT052ResponseFile;
    }

}

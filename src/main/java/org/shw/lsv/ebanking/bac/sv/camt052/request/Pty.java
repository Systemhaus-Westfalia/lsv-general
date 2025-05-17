package org.shw.lsv.ebanking.bac.sv.camt052.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pty {
    
    @JsonProperty("Id")    // "Id" is the name of the field in the JSON
    PtyId PtyId;


    /**
     * @return the PtyId object<br>
     */
    public PtyId getPtyId() {
        return PtyId;
    }


    /**
     * @param ptyId the PtyId to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setPtyId(PtyId ptyId) {
        if (ptyId == null) {
            throw new IllegalArgumentException("Wrong parameter 'ptyId' in setPtyId()");
        }
        this.PtyId = ptyId;
    }

    /**
     * @param ptyId the PtyId to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setPtyId(PtyId ptyId, JsonValidationExceptionCollector collector) {
        try {
            setPtyId(ptyId);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }

}

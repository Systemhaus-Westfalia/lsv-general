package org.shw.lsv.ebanking.bac.sv.misc;

import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IDOrgID {

    @JsonProperty("Id")  // "Id" is the name of the field in the JSON
    Id id;

    public IDOrgID() {
    }

    public IDOrgID(RequestParams params, String context, JsonValidationExceptionCollector collector) {
        try {
            setId(new Id(params, context, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_IDORGID_INIT, e);
        }
    }

    /**
     * @return the id object<br>
     */
    public Id getId() {
        return id;
    }

    /**
     * @param id the id to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setId(Id id) {
        if (id == null) {
            throw new IllegalArgumentException("Wrong parameter 'id' in setId()");
        }
        this.id = id;
    }

    /**
     * @param id the id to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setId(Id id, JsonValidationExceptionCollector collector) {
        try {
            setId(id);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }
}

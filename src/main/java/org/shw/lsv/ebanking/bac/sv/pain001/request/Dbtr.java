package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.misc.IDOrgID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Dbtr {

    @JsonProperty("Nm")
    String nm;  // Debtor name

    @JsonProperty("Id")
    @JsonInclude(JsonInclude.Include.NON_NULL)  // Exclude fields with null values
    IDOrgID id;

    public Dbtr() {}

    public Dbtr(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setNm(params.getNameDebtor(), collector);

        // TODO: Wird das Objekt verwendet? Sicher stellen, dass es keinen Konflikt mit CAMT052 Request gibt.
		if(params.getDbtrAcctID() != null && !params.getDbtrAcctID().isEmpty()) {
            setId ( new IDOrgID(  params, nm, collector), collector);
        }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_DBTR_INIT, e);
        }
    }

    /**
     * @return the Nm
     */
    public String getNm() {
        return nm;
    }

    /**
     * @param nm the Nm to be set<br>
     * The parameter is validated.<br>
     * "minLength" : 1, "maxLength" : 70; null not allowed.<br>
     */
    public void setNm(String nm) {
        final int MINLENGTH = 1;
        final int MAXLENGTH = 70;
        int length = (nm == null || nm.isEmpty()) ? 0 : nm.length();

        if (length < MINLENGTH || length > MAXLENGTH) {
            throw new IllegalArgumentException(
                "Wrong parameter 'nm' (" + nm + ") in setNm()"
            );
        }
        this.nm = nm;
    }

    /**
     * @param nm the Nm to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setNm(String nm, JsonValidationExceptionCollector collector) {
        try {
            setNm(nm);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_WRONG_LENGTH, e);
            //throw e;
        }
    }

    /**
     * @return the DbtrId object<br>
     */
    public IDOrgID getId() {
        return id;
    }

    /**
     * @param id the DbtrId to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setId(IDOrgID id) {
        if (id == null) {
            throw new IllegalArgumentException("Wrong parameter 'id' in setId()");
        }
        this.id = id;
    }

    /**
     * @param id the DbtrId to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setId(IDOrgID id, JsonValidationExceptionCollector collector) {
        try {
            setId(id);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }

}

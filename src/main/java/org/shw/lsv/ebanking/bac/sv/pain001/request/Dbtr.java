package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Dbtr {

    @JsonProperty("Nm")
    String nm;

    @JsonProperty("Id")
    DbtrId id;

    public Dbtr() {}

    public  Dbtr(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setNm(params.getNm(), collector);
            setId ( new DbtrId(  params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_PMT_INIT, e);
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
    public DbtrId getId() {
        return id;
    }

    /**
     * @param id the DbtrId to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setId(DbtrId id) {
        if (id == null) {
            throw new IllegalArgumentException("Wrong parameter 'id' in setId()");
        }
        this.id = id;
    }

    /**
     * @param id the DbtrId to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setId(DbtrId id, JsonValidationExceptionCollector collector) {
        try {
            setId(id);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }

}

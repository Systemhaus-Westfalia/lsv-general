package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Cdtr {

    @JsonProperty("Nm")
    String nm;  // Creditor name

    @JsonProperty("Id")
    CdtrID cdtrID;

    public Cdtr(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setNm(params.getNameCreditor(), collector);
            setCdtrID(new CdtrID(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_CDTR_INIT, e);
        }
    }


    /**
     * @return the Creditor name
     */
    public String getNm() {
        return nm;
    }

    /**
     * @param nm the Creditor name<br>
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
     * @param nm the Creditor name<br>
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
     * @return the CdtrID object<br>
     */
    public CdtrID getCdtrID() {
        return cdtrID;
    }

    /**
     * @param cdtrID the CdtrID to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setCdtrID(CdtrID cdtrID) {
        if (cdtrID == null) {
            throw new IllegalArgumentException("Wrong parameter 'cdtrID' in setCdtrID()");
        }
        this.cdtrID = cdtrID;
    }

    /**
     * @param cdtrID the CdtrID to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setCdtrID(CdtrID cdtrID, JsonValidationExceptionCollector collector) {
        try {
            setCdtrID(cdtrID);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }


}

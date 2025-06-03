package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.AppHdr;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PAIN001RequestEnvelope implements Validatable {

    @JsonProperty("AppHdr")
     AppHdr appHdr;

     @JsonProperty("Document")    // "Document" is the name of the field in the JSON
     PAIN001RequestDocument pAIN001RequestDocument;

    public PAIN001RequestEnvelope() { }

    public PAIN001RequestEnvelope(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setAppHdr(new AppHdr(params, collector), collector);
            setPAIN001RequestDocument(new PAIN001RequestDocument(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_REQUEST_ENVELOP_INIT, e);
        }
    }


    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (appHdr == null) {
                throw new IllegalArgumentException("AppHdr cannot be null");
            }
            if (pAIN001RequestDocument == null) {
                throw new IllegalArgumentException("Document cannot be null");
            }

            // Validate nested objects
            if (appHdr instanceof Validatable) {
                ((Validatable) appHdr).validate(collector);
            }
            if (pAIN001RequestDocument instanceof Validatable) {
                ((Validatable) pAIN001RequestDocument).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    /**
    * @return the AppHdr object<br>
    */
     public AppHdr getAppHdr() {
         return appHdr;
     }


     /**
     * @param appHdr the AppHdr to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setAppHdr(AppHdr appHdr) {
        if (appHdr == null) {
            throw new IllegalArgumentException(
                "Wrong parameter 'appHdr' in setAppHdr()"
            );
        }
        this.appHdr = appHdr;
    }

    /**
     * @param appHdr the AppHdr to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setAppHdr(AppHdr appHdr, JsonValidationExceptionCollector collector) {
        try {
            setAppHdr(appHdr);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }


    public PAIN001RequestDocument getpAIN001RequestDocument() {
        return pAIN001RequestDocument;
    }

    /**
     * @param pAIN001RequestDocument the PAIN001RequestDocument to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setPAIN001RequestDocument(PAIN001RequestDocument pAIN001RequestDocument,
                                        JsonValidationExceptionCollector collector) {
        try {
            setpAIN001RequestDocument(pAIN001RequestDocument);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }

    /**
     * @param pAIN001RequestDocument the PAIN001RequestDocument to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setpAIN001RequestDocument(PAIN001RequestDocument pAIN001RequestDocument) {
        if (pAIN001RequestDocument == null) {
            throw new IllegalArgumentException(
                "Wrong parameter 'pAIN001RequestDocument' in setPAIN001RequestDocument()"
            );
        }
        this.pAIN001RequestDocument = pAIN001RequestDocument;
    }
}

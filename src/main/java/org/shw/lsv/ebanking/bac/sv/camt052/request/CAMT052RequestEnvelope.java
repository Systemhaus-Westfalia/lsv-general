package org.shw.lsv.ebanking.bac.sv.camt052.request;

import org.shw.lsv.ebanking.bac.sv.misc.AppHdr;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParamsCamt052;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 */
public class CAMT052RequestEnvelope implements Validatable {
    
    @JsonProperty("AppHdr")
     AppHdr appHdr;
    
     @JsonProperty("Document")    // "Document" is the name of the field in the JSON
     CAMT052RequestDocument cAMT052RequestDocument;
    
    public CAMT052RequestEnvelope() {}


    public CAMT052RequestEnvelope(RequestParamsCamt052 params, JsonValidationExceptionCollector collector) {
        try {
            setAppHdr(new AppHdr(params, collector), collector);
            setCAMT052RequestDocument(new CAMT052RequestDocument(params, collector), collector);
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
            if (cAMT052RequestDocument == null) {
                throw new IllegalArgumentException("Document cannot be null");
            }

            // Validate nested objects
            if (appHdr instanceof Validatable) {
                ((Validatable) appHdr).validate(collector);
            }
            if (cAMT052RequestDocument instanceof Validatable) {
                ((Validatable) cAMT052RequestDocument).validate(collector);
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


    /**
    * @return the CAMT052RequestDocument object<br>
    */
     public CAMT052RequestDocument getcAMT052RequestDocument() {
         return cAMT052RequestDocument;
     }


    /**
     * @param documentCAMT052Request the CAMT052RequestDocument to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setcAMT052RequestDocument(CAMT052RequestDocument documentCAMT052Request) {
        if (documentCAMT052Request == null) {
            throw new IllegalArgumentException(
                "Wrong parameter 'documentCAMT052Request' in setCAMT052RequestDocument()"
            );
        }
        this.cAMT052RequestDocument = documentCAMT052Request;
    }

    /**
     * @param documentCAMT052Request the CAMT052RequestDocument to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setCAMT052RequestDocument(CAMT052RequestDocument documentCAMT052Request, 
                                        JsonValidationExceptionCollector collector) {
        try {
            setcAMT052RequestDocument(documentCAMT052Request);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }

}

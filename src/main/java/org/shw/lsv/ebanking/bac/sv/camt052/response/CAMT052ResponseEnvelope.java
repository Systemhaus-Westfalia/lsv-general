package org.shw.lsv.ebanking.bac.sv.camt052.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.AppHdr;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 */
public class CAMT052ResponseEnvelope {
    
    @JsonProperty("AppHdr")
     AppHdr appHdr;
    
     @JsonProperty("Document")    // "Document" is the name of the field in the JSON
     CAMT052ResponseDocument CAMT052ResponseDocument;

        
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
            throw new IllegalArgumentException("Wrong parameter 'appHdr' in setAppHdr()");
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
    * @return the CAMT060RequestDocument object<br>
    */
     public CAMT052ResponseDocument getCAMT052ResponseDocument() {
         return CAMT052ResponseDocument;
     }


    /**
     * @param documentCAMT052Response the CAMT052ResponseDocument to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setCAMT052ResponseDocument(CAMT052ResponseDocument documentCAMT052Response) {
        if (documentCAMT052Response == null) {
            throw new IllegalArgumentException("Wrong parameter 'documentCAMT052Response' in setCAMT052ResponseDocument()");
        }
        this.CAMT052ResponseDocument = documentCAMT052Response;
    }

    /**
     * @param documentCAMT052Response the CAMT052ResponseDocument to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setCAMT052ResponseDocument(CAMT052ResponseDocument documentCAMT052Response, JsonValidationExceptionCollector collector) {
        try {
            setCAMT052ResponseDocument(documentCAMT052Response);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }

}

package org.shw.lsv.ebanking.bac.sv.camt053.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.AppHdr;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Envelope for CAMT053 Response
 */
public class CAMT053ResponseEnvelope {

    @JsonProperty("AppHdr")
    AppHdr appHdr;

    @JsonProperty("Document")    // "Document" is the name of the field in the JSON
    CAMT053ResponseDocument cAMT053ResponseDocument;

    public CAMT053ResponseEnvelope() {}

    /**
     * Validates the CAMT053ResponseEnvelope.
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (appHdr == null) {
                throw new IllegalArgumentException("AppHdr cannot be null");
            }
            if (cAMT053ResponseDocument == null) {
                throw new IllegalArgumentException("Document cannot be null");
            }
            // Optionally, validate nested objects if they implement a validation interface
            if (appHdr instanceof org.shw.lsv.ebanking.bac.sv.handling.Validatable) {
                ((org.shw.lsv.ebanking.bac.sv.handling.Validatable) appHdr).validate(collector);
            }
            if (cAMT053ResponseDocument instanceof org.shw.lsv.ebanking.bac.sv.handling.Validatable) {
                ((org.shw.lsv.ebanking.bac.sv.handling.Validatable) cAMT053ResponseDocument).validate(collector);
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
        }
    }

    /**
     * @return the CAMT053ResponseDocument object<br>
     */
    public CAMT053ResponseDocument getcAMT053ResponseDocument() {
        return cAMT053ResponseDocument;
    }

    /**
     * @param documentCAMT053Response the CAMT053ResponseDocument to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setcAMT053ResponseDocument(CAMT053ResponseDocument documentCAMT053Response) {
        if (documentCAMT053Response == null) {
            throw new IllegalArgumentException("Wrong parameter 'documentCAMT053Response' in setCAMT053ResponseDocument()");
        }
        this.cAMT053ResponseDocument = documentCAMT053Response;
    }

    /**
     * @param documentCAMT053Response the CAMT053ResponseDocument to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setCAMT053ResponseDocument(CAMT053ResponseDocument documentCAMT053Response, JsonValidationExceptionCollector collector) {
        try {
            setcAMT053ResponseDocument(documentCAMT053Response);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}
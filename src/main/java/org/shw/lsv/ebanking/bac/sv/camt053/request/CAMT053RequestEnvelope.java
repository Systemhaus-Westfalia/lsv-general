package org.shw.lsv.ebanking.bac.sv.camt053.request;

import org.shw.lsv.ebanking.bac.sv.misc.AppHdr;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Envelope for CAMT053 Request
 */
public class CAMT053RequestEnvelope implements Validatable {

    @JsonProperty("AppHdr")
    AppHdr appHdr;

    @JsonProperty("Document")    // "Document" is the name of the field in the JSON
    CAMT053RequestDocument cAMT053RequestDocument;

    public CAMT053RequestEnvelope() {}

    public CAMT053RequestEnvelope(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setAppHdr(new AppHdr(params, collector), collector);
            setCAMT053RequestDocument(new CAMT053RequestDocument(params, collector), collector);
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
            if (cAMT053RequestDocument == null) {
                throw new IllegalArgumentException("Document cannot be null");
            }

            // Validate nested objects
            if (appHdr instanceof Validatable) {
                ((Validatable) appHdr).validate(collector);
            }
            if (cAMT053RequestDocument instanceof Validatable) {
                ((Validatable) cAMT053RequestDocument).validate(collector);
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
        }
    }

    /**
     * @return the CAMT053RequestDocument object<br>
     */
    public CAMT053RequestDocument getcAMT053RequestDocument() {
        return cAMT053RequestDocument;
    }

    /**
     * @param documentCAMT053Request the CAMT053RequestDocument to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setcAMT053RequestDocument(CAMT053RequestDocument documentCAMT053Request) {
        if (documentCAMT053Request == null) {
            throw new IllegalArgumentException(
                "Wrong parameter 'documentCAMT053Request' in setCAMT053RequestDocument()"
            );
        }
        this.cAMT053RequestDocument = documentCAMT053Request;
    }

    /**
     * @param documentCAMT053Request the CAMT053RequestDocument to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setCAMT053RequestDocument(CAMT053RequestDocument documentCAMT053Request, 
                                         JsonValidationExceptionCollector collector) {
        try {
            setcAMT053RequestDocument(documentCAMT053Request);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}
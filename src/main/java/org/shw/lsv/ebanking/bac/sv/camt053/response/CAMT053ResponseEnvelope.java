package org.shw.lsv.ebanking.bac.sv.camt053.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.AppHdr;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CAMT053ResponseEnvelope implements Validatable {

    @JsonProperty("AppHdr")
    AppHdr appHdr;

    @JsonProperty("Document")
    CAMT053ResponseDocument cAMT053ResponseDocument;

    public CAMT053ResponseEnvelope() {}

    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (cAMT053ResponseDocument == null) {
                throw new IllegalArgumentException("Document cannot be null");
            }

            // A rejection message (admi.002.001.01) does not contain an AppHdr.
            // Therefore, AppHdr is only mandatory if the document is NOT a rejection.
            boolean isRejection = cAMT053ResponseDocument.getRejection() != null;

            if (!isRejection) {
                if (appHdr == null) {
                    throw new IllegalArgumentException("AppHdr cannot be null for a successful statement");
                }
                if (appHdr instanceof Validatable) {
                    ((Validatable) appHdr).validate(collector);
                }
            }
            if (cAMT053ResponseDocument instanceof Validatable) {
                ((Validatable) cAMT053ResponseDocument).validate(collector);
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
     */
    public void setAppHdr(AppHdr appHdr) {
        this.appHdr = appHdr;
    }

    /**
     * @param appHdr the AppHdr to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setAppHdr(AppHdr appHdr, JsonValidationExceptionCollector collector) {
        setAppHdr(appHdr);
    }

    /**
     * @return the CAMT053ResponseDocument object<br>
     */
    public CAMT053ResponseDocument getcAMT053ResponseDocument() {
        return cAMT053ResponseDocument;
    }

    /**
     * @param cAMT053ResponseDocument the CAMT053ResponseDocument to be set<br>
     */
    public void setcAMT053ResponseDocument(CAMT053ResponseDocument cAMT053ResponseDocument) {
        this.cAMT053ResponseDocument = cAMT053ResponseDocument;
    }

    /**
     * @param cAMT053ResponseDocument the CAMT053ResponseDocument to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setCAMT053ResponseDocument(CAMT053ResponseDocument cAMT053ResponseDocument, JsonValidationExceptionCollector collector) {
        try {
            setcAMT053ResponseDocument(cAMT053ResponseDocument);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}
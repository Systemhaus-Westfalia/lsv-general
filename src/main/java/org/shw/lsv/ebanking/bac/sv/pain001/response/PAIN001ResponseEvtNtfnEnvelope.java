package org.shw.lsv.ebanking.bac.sv.pain001.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.AppHdr;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PAIN001ResponseEvtNtfnEnvelope {

    @JsonProperty("AppHdr")
    AppHdr appHdr;

    @JsonProperty("Document")
    PAIN001ResponseEvtNtfnDocument pAIN001ResponseDocument;

    public PAIN001ResponseEvtNtfnEnvelope() {}

    public AppHdr getAppHdr() {
        return appHdr;
    }

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

    public PAIN001ResponseEvtNtfnDocument getPAIN001ResponseDocument() {
        return pAIN001ResponseDocument;
    }

    public void setPAIN001ResponseDocument(PAIN001ResponseEvtNtfnDocument pAIN001ResponseDocument) {
        if (pAIN001ResponseDocument == null) {
            throw new IllegalArgumentException("Wrong parameter 'pAIN001ResponseDocument' in setPAIN001ResponseDocument()");
        }
        this.pAIN001ResponseDocument = pAIN001ResponseDocument;
    }

    /**
     * @param pAIN001ResponseDocument the PAIN001ResponseDocument to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setPAIN001ResponseDocument(PAIN001ResponseEvtNtfnDocument pAIN001ResponseDocument, JsonValidationExceptionCollector collector) {
        try {
            setPAIN001ResponseDocument(pAIN001ResponseDocument);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }

}
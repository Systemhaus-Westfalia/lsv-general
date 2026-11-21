package org.shw.lsv.ebanking.bac.sv.pain001.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.AppHdr;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PAIN001ResponseEvtNtfnEnvelope {

    @JsonProperty("AppHdr")
    AppHdr appHdr;

    @JsonProperty("Document")
    PAIN001ResponseEvtNtfnDocument pAIN001ResponseEvtNtfnDocument;

    public PAIN001ResponseEvtNtfnEnvelope() {}

    public PAIN001ResponseEvtNtfnEnvelope(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setAppHdr(new AppHdr(params, collector), collector);
            setpAIN001ResponseEvtNtfnDocument(new PAIN001ResponseEvtNtfnDocument(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_REQUEST_ENVELOP_INIT, e);
        }
    }

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

    public PAIN001ResponseEvtNtfnDocument getpAIN001ResponseEvtNtfnDocument() {
        return pAIN001ResponseEvtNtfnDocument;
    }

    public void setpAIN001ResponseEvtNtfnDocument(PAIN001ResponseEvtNtfnDocument pAIN001ResponseDocument) {
        if (pAIN001ResponseDocument == null) {
            throw new IllegalArgumentException("Wrong parameter 'pAIN001ResponseDocument' in setPAIN001ResponseDocument()");
        }
        this.pAIN001ResponseEvtNtfnDocument = pAIN001ResponseDocument;
    }

    /**
     * @param pAIN001ResponseDocument the PAIN001ResponseDocument to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setpAIN001ResponseEvtNtfnDocument(PAIN001ResponseEvtNtfnDocument pAIN001ResponseDocument, JsonValidationExceptionCollector collector) {
        try {
            setpAIN001ResponseEvtNtfnDocument(pAIN001ResponseDocument);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }

}
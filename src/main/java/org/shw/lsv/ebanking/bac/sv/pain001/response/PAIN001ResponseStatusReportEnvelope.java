package org.shw.lsv.ebanking.bac.sv.pain001.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.AppHdr;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PAIN001ResponseStatusReportEnvelope {

    @JsonProperty("AppHdr")
    AppHdr appHdr;

    @JsonProperty("Document")
    PAIN001ResponseStatusReportDocument pAIN001ResponseStatusReportDocument;

    public PAIN001ResponseStatusReportEnvelope() {}

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
        }
    }

    public PAIN001ResponseStatusReportDocument getpAIN001ResponseStatusReportDocument() {
        return pAIN001ResponseStatusReportDocument;
    }

    public void setpAIN001ResponseStatusReportDocument(PAIN001ResponseStatusReportDocument pAIN001ResponseStatusReportDocument) {
        if (pAIN001ResponseStatusReportDocument == null) {
            throw new IllegalArgumentException("Wrong parameter 'pAIN001ResponseStatusReportDocument' in setPAIN001ResponseStatusReportDocument()");
        }
        this.pAIN001ResponseStatusReportDocument = pAIN001ResponseStatusReportDocument;
    }

    /**
     * @param pAIN001ResponseStatusReportDocument the PAIN001ResponseStatusReportDocument to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setpAIN001ResponseStatusReportDocument(PAIN001ResponseStatusReportDocument pAIN001ResponseStatusReportDocument, JsonValidationExceptionCollector collector) {
        try {
            setpAIN001ResponseStatusReportDocument(pAIN001ResponseStatusReportDocument);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}
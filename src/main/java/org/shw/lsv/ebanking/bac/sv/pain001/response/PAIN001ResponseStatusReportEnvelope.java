package org.shw.lsv.ebanking.bac.sv.pain001.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.AppHdr;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PAIN001ResponseStatusReportEnvelope implements Validatable {

    @JsonProperty("AppHdr")
    AppHdr appHdr;

    @JsonProperty("Document")
    PAIN001ResponseStatusReportDocument pAIN001ResponseStatusReportDocument;

    public PAIN001ResponseStatusReportEnvelope() {}

    public PAIN001ResponseStatusReportEnvelope(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setAppHdr(new AppHdr(params, collector), collector);
            setPAIN001ResponseStatusReportDocument(new PAIN001ResponseStatusReportDocument(params, collector), collector);
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
            if (pAIN001ResponseStatusReportDocument == null) {
                throw new IllegalArgumentException("Document cannot be null");
            }
            if (appHdr instanceof Validatable) {
                ((Validatable) appHdr).validate(collector);
            }
            if (pAIN001ResponseStatusReportDocument instanceof Validatable) {
                ((Validatable) pAIN001ResponseStatusReportDocument).validate(collector);
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
     * @return the PAIN001ResponseStatusReportDocument object<br>
     */
    public PAIN001ResponseStatusReportDocument getpAIN001ResponseStatusReportDocument() {
        return pAIN001ResponseStatusReportDocument;
    }

    /**
     * @param pAIN001ResponseStatusReportDocument the PAIN001ResponseStatusReportDocument to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
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
    public void setPAIN001ResponseStatusReportDocument(PAIN001ResponseStatusReportDocument pAIN001ResponseStatusReportDocument,
                                                      JsonValidationExceptionCollector collector) {
        try {
            setpAIN001ResponseStatusReportDocument(pAIN001ResponseStatusReportDocument);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}
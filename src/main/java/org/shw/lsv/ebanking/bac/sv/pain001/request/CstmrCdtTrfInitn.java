package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.misc.GrpHdr;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CstmrCdtTrfInitn {
    @JsonProperty("GrpHdr")
    GrpHdr grpHdr;
    
    @JsonProperty("PmtInf")
    PmtInf pmtInf;

    public CstmrCdtTrfInitn() {}

    public CstmrCdtTrfInitn(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setGrpHdr(new GrpHdr(params, collector), collector);
            setPmtInf(new PmtInf(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_ACCTRPTGREQ_INIT, e);
        }
    }


    /**
     * @return the GrpHdr object<br>
     */
    public GrpHdr getGrpHdr() {
        return grpHdr;
    }


    /**
     * @param grpHdr the GrpHdr to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setGrpHdr(GrpHdr grpHdr) {
        if (grpHdr == null) {
            throw new IllegalArgumentException("Wrong parameter 'grpHdr' in setGrpHdr()");
        }
        this.grpHdr = grpHdr;
    }

    /**
     * @param grpHdr the GrpHdr to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setGrpHdr(GrpHdr grpHdr, JsonValidationExceptionCollector collector) {
        try {
            setGrpHdr(grpHdr);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }


    /**
     * @return the PmtInf object<br>
     */
    public PmtInf getPmtInf() {
        return pmtInf;
    }

    /**
     * @param pmtInf the PmtInf to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setPmtInf(PmtInf pmtInf) {
        if (pmtInf == null) {
            throw new IllegalArgumentException("Wrong parameter 'pmtInf' in setPmtInf()");
        }
        this.pmtInf = pmtInf;
    }

    /**
     * @param pmtInf the PmtInf to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setPmtInf(PmtInf pmtInf, JsonValidationExceptionCollector collector) {
        try {
            setPmtInf(pmtInf);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }

}

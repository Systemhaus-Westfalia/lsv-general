package org.shw.lsv.ebanking.bac.sv.camt052.request;

import org.shw.lsv.ebanking.bac.sv.handling.Camt052RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.misc.GrpHdr;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AcctRptgReq {
    
    @JsonProperty("GrpHdr")
    GrpHdr grpHdr;
    
    @JsonProperty("RptgReq")
    RptgReq rptgReq;

    public AcctRptgReq() {
    }


    public AcctRptgReq(Camt052RequestParams params, JsonValidationExceptionCollector collector) {
        
        try {
            setGrpHdr(new GrpHdr(params, collector), collector);
            setRptgReq(new RptgReq(params, collector), collector);
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
     * @return the RptgReq object<br>
     */
    public RptgReq getRptgReq() {
        return rptgReq;
    }


    /**
     * @param rptgReq the RptgReq to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setRptgReq(RptgReq rptgReq) {
        if (rptgReq == null) {
            throw new IllegalArgumentException("Wrong parameter 'rptgReq' in setRptgReq()");
        }
        this.rptgReq = rptgReq;
    }

    /**
     * @param rptgReq the RptgReq to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setRptgReq(RptgReq rptgReq, JsonValidationExceptionCollector collector) {
        try {
            setRptgReq(rptgReq);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }

}

package org.shw.lsv.ebanking.bac.sv.camt052.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.misc.GrpHdr;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BkToCstmrAcctRpt {

    @JsonProperty(value = "GrpHdr", required = true)
    GrpHdr grpHdr;
    
    @JsonProperty("Rpt")
    Rpt rpt;


    public BkToCstmrAcctRpt() {
    }


    /*
     * Constructor with parameters
     * For using the Constructor at deserialization time, it has to be of the form:
     * public BkToCstmrAcctRpt(@JsonProperty(value = "GrpHdr", required = true) GrpHdr grpHdr,.....)
     */
    public BkToCstmrAcctRpt(GrpHdr grpHdr) {
        setGrpHdr(grpHdr);
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
     * @return the Rpt object<br>
     */
    public Rpt getRpt() {
        return rpt;
    }


    /**
     * @param Rpt the Rpt to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setRpt(Rpt Rpt) {
        if (Rpt == null) {
            throw new IllegalArgumentException("Wrong parameter 'Rpt' in setRpt()");
        }
        this.rpt = Rpt;
    }

    /**
     * @param Rpt the Rpt to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setRpt(Rpt Rpt, JsonValidationExceptionCollector collector) {
        try {
            setRpt(Rpt);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }

}

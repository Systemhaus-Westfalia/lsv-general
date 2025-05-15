package org.shw.lsv.ebanking.bac.sv.camt052.Request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.GrpHdr;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AcctRptgReq {
    
    @JsonProperty("GrpHdr")
    GrpHdr GrpHdr;
    
    @JsonProperty("RptgReq")
    RptgReq RptgReq;

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=AcctRptgReq.class.getName();

    /**
     * @return the GrpHdr object<br>
     */
    public GrpHdr getGrpHdr() {
        return GrpHdr;
    }

    /**
     * @param grpHdr the GrpHdr to be set.
     * The parameter is validated: null not allowed.
     */
    public void setGrpHdr(GrpHdr grpHdr, JsonValidationExceptionCollector collector) {
        try {
            if (grpHdr == null) {
                throw new IllegalArgumentException(
                    "Wrong parameter 'grpHdr' in " + FULLY_QUALIFIED_CLASSNAME + ".setGrpHdr()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setGrpHdr()";
            collector.addError(context, e);

            throw e;
        }

        this.GrpHdr = grpHdr;
    }


    /**
     * @return the RptgReq object<br>
     */
    public RptgReq getRptgReq() {
        return RptgReq;
    }

    /**
     * @param rptgReq the RptgReq to be set.
     * The parameter is validated: null not allowed.
     */
    public void setRptgReq(RptgReq rptgReq, JsonValidationExceptionCollector collector) {
        try {
            if (rptgReq == null) {
                throw new IllegalArgumentException(
                    "Wrong parameter 'rptgReq' in " + FULLY_QUALIFIED_CLASSNAME + ".setRptgReq()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setRptgReq()";
            collector.addError(context, e);

            throw e;
        }

        this.RptgReq = rptgReq;
    }

}

package org.shw.lsv.ebanking.bac.sv.camt060.Request;

import org.shw.lsv.ebanking.bac.sv.utils.GrpHdr;

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
     * @param grpHdr the GrpHdr to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setGrpHdr(GrpHdr grpHdr) {
        if (grpHdr == null ) {
            throw new IllegalArgumentException("Wrong parameter 'grpHdr' in " +  FULLY_QUALIFIED_CLASSNAME + ".setGrpHdr()" + "\n");
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
     * @param rptgReq the RptgReq to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setRptgReq(RptgReq rptgReq) {  
        if (rptgReq == null ) {
            throw new IllegalArgumentException("Wrong parameter 'rptgReq' in " +  FULLY_QUALIFIED_CLASSNAME + ".setRptgReq()" + "\n");
        } 
        this.RptgReq = rptgReq;
    }

}

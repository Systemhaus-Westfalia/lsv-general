package org.shw.lsv.ebanking.bac.sv.camt060.Response;

import org.shw.lsv.ebanking.bac.sv.utils.GrpHdr;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BkToCstmrAcctRpt {
                      
    @JsonProperty(value = "GrpHdr", required = true)
    GrpHdr GrpHdr;
    
    @JsonProperty("Rpt")
    Rpt Rpt;

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=BkToCstmrAcctRpt.class.getName();


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
     * @return the Rpt object<br>
     */
    public Rpt getRpt() {
        return Rpt;
    }


    /**
     * @param Rpt the Rpt to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setRpt(Rpt Rpt) {  
        if (Rpt == null ) {
            throw new IllegalArgumentException("Wrong parameter 'Rpt' in " +  FULLY_QUALIFIED_CLASSNAME + ".setRpt()" + "\n");
        } 
        this.Rpt = Rpt;
    }

}

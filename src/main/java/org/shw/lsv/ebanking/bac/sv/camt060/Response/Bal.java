package org.shw.lsv.ebanking.bac.sv.camt060.Response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Bal {

    @JsonProperty("Tp")
    Tp Tp;

    @JsonProperty("CdtDbtInd")
    String CdtDbtInd;

    @JsonProperty("Dt")
    Dt Dt;

    @JsonProperty("Amt")
    Amt Amt;

    @JsonIgnore
    final String fullyQualifiedClassName=Bal.class.getName();


    /**
     * @return the Tp object<br>
     */
    public Tp getTp() {
        return Tp;
    }


    /**
     * @param tp the Tp to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setTp(Tp tp) {
        if (tp == null) {
            throw new IllegalArgumentException("Wrong parameter 'tp' in " +  fullyQualifiedClassName + ".setTp() \n");
        }
        this.Tp = tp;
    }


    /**
     * @return the CdtDbtInd<br>
     */
    public String getCdtDbtInd() {
        return CdtDbtInd;
    }


    /**
     * @param cdtDbtInd the CdtDbtInd to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setCdtDbtInd(String cdtDbtInd) {
        if (cdtDbtInd == null || cdtDbtInd.isEmpty()) {
            throw new IllegalArgumentException("Wrong parameter 'cdtDbtInd' (" + cdtDbtInd + ") in " +  fullyQualifiedClassName + ".setCdtDbtInd() \n");
        }
        this.CdtDbtInd = cdtDbtInd;
    }

    /**
     * @return the Dt object<br>
     */
    public Dt getDt() {
        return Dt;
    }

    /**
     * @param dt the Dt to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setDt(Dt dt) {
        if (dt == null) {
            throw new IllegalArgumentException("Wrong parameter 'dt' in " +  fullyQualifiedClassName + ".setDt() \n");
        }
        this.Dt = dt;
    }


    /**
     * @return the Amt object<br>
     */
    public Amt getAmt() {
        return Amt;
    }

    /**
     * @param amt the Amt to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setAmt(Amt amt) {
        if (amt == null) {
            throw new IllegalArgumentException("Wrong parameter 'amt' in " +  fullyQualifiedClassName + ".getAmt() \n");
        }
        this.Amt = amt;
    }



}

package org.shw.lsv.ebanking.bac.sv.camt052.Response;

import org.shw.lsv.ebanking.bac.sv.misc.EBankingCentral;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Bal  extends EBankingCentral {

    @JsonProperty("Tp")
    Tp Tp;
                      
    @JsonProperty(value = "CdtDbtInd", required = true)
    String CdtDbtInd;  // Credit/Debit indicator

    @JsonProperty("Dt")
    Dt Dt;  // DateTime with timezone           

    @JsonProperty("Amt")
    Amt Amt;

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=Bal.class.getName();


   /*
    * Constructor with parameters
    * For using the Constructor at deserialization time, it has to be of the form:
    * public Bal(@JsonProperty(value = "Dt", required = true) Dt dt,.....)
    */
    public Bal(String cdtDbtInd, Dt dt, Amt amt) {
        setCdtDbtInd(cdtDbtInd);
        setDt(dt);
        setAmt(amt);
    }


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
            throw new IllegalArgumentException("Wrong parameter 'tp' in " +  FULLY_QUALIFIED_CLASSNAME + ".setTp() \n");
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
     * The parameter is validated: null not allowed.
     * And must equal one of the following values: [CRDT, DBIT]
     */
    public void setCdtDbtInd(String cdtDbtInd) {
        if ( (cdtDbtInd == null || cdtDbtInd.isEmpty()) || 
             (
                !( cdtDbtInd.equals(CRDT) || cdtDbtInd.equals(DBIT)  )
             )
            ) {
            throw new IllegalArgumentException("Wrong parameter 'cdtDbtInd' (" + cdtDbtInd + ") in " +  FULLY_QUALIFIED_CLASSNAME + ".setCdtDbtInd() \n");
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
            throw new IllegalArgumentException("Wrong parameter 'dt' in " +  FULLY_QUALIFIED_CLASSNAME + ".setDt() \n");
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
            throw new IllegalArgumentException("Wrong parameter 'amt' in " +  FULLY_QUALIFIED_CLASSNAME + ".getAmt() \n");
        }
        this.Amt = amt;
    }



}

package org.shw.lsv.ebanking.bac.sv.camt052.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Bal  {

    @JsonProperty("Tp")
    Tp tp;

    @JsonProperty(value = "CdtDbtInd", required = true)
    String cdtDbtInd;  // Credit/Debit indicator

    @JsonProperty("Dt")
    Dt dt;  // DateTime with timezone

    @JsonProperty("Amt")
    Amt amt;


   public Bal() {}


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


    public Bal(RequestParams params, JsonValidationExceptionCollector collector) {
        //TODO Auto-generated constructor stub
    }


    /**
     * @return the Tp object<br>
     */
    public Tp getTp() {
        return tp;
    }


    /**
     * @param tp the Tp to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setTp(Tp tp) {
        if (tp == null) {
            throw new IllegalArgumentException("Wrong parameter 'tp' in setTp()");
        }
        this.tp = tp;
    }

    /**
     * @param tp the Tp to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setTp(Tp tp, JsonValidationExceptionCollector collector) {
        try {
            setTp(tp);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }


    /**
     * @return the CdtDbtInd<br>
     */
    public String getCdtDbtInd() {
        return cdtDbtInd;
    }


    /**
     * @param cdtDbtInd the CdtDbtInd to be set<br>
     * The parameter is validated: null not allowed.<br>
     * And must equal one of the following values: [CRDT, DBIT]
     */
    public void setCdtDbtInd(String cdtDbtInd) {
        if ((cdtDbtInd == null || cdtDbtInd.isEmpty()) || 
            !(cdtDbtInd.equals(EBankingConstants.CRDT) || cdtDbtInd.equals(EBankingConstants.DBIT))) {
            throw new IllegalArgumentException("Wrong parameter 'cdtDbtInd' (" + cdtDbtInd + ") in setCdtDbtInd()");
        }
        this.cdtDbtInd = cdtDbtInd;
    }

    /**
     * @param cdtDbtInd the CdtDbtInd to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setCdtDbtInd(String cdtDbtInd, JsonValidationExceptionCollector collector) {
        try {
            setCdtDbtInd(cdtDbtInd);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_EMPTY_OR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }

    /**
     * @return the Dt object<br>
     */
    public Dt getDt() {
        return dt;
    }


    /**
     * @param dt the Dt to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setDt(Dt dt) {
        if (dt == null) {
            throw new IllegalArgumentException("Wrong parameter 'dt' in setDt()");
        }
        this.dt = dt;
    }

    /**
     * @param dt the Dt to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setDt(Dt dt, JsonValidationExceptionCollector collector) {
        try {
            setDt(dt);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }


    /**
     * @return the Amt object<br>
     */
    public Amt getAmt() {
        return amt;
    }


    /**
     * @param amt the Amt to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setAmt(Amt amt) {
        if (amt == null) {
            throw new IllegalArgumentException("Wrong parameter 'amt' in setAmt()");
        }
        this.amt = amt;
    }

    /**
     * @param amt the Amt to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setAmt(Amt amt, JsonValidationExceptionCollector collector) {
        try {
            setAmt(amt);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }



}

package org.shw.lsv.ebanking.bac.sv.camt052.response;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.Acct;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rpt {

    @JsonProperty(value = "Id", required = true)
    String Id;

    @JsonProperty(value = "RptPgntn", required = true)
    RptPgntn RptPgntn;

    @JsonProperty("Acct")
    Acct Acct;

    @JsonProperty("Bal")
    Bal Bal;


    public Rpt() {
    }


    /*
     * Constructor with parameters
     * For using the Constructor at deserialization time, it has to be of the form:
     * public Rpt(@JsonProperty(value = "Id", required = true) String id,.....)
     */
    public Rpt(String id, RptPgntn rptPgntn) {
        setId(id);
        setRptPgntn(rptPgntn);
    }


	/**
	 * @return the Id
	 */
	public String getId() {
        return Id;
    }


    /**
     * @param id the Id to be set<br>
     * The parameter is validated.<br>
     * "minLength" : 1, "maxLength" : 35; null not allowed.<br>
     * "pattern" : "([0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ]([0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ]*(/[0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ])?)*)"
    * e.g.: "ABNA202009081223"
    */
    public void setId(String id) {
        final int MINLENGTH = 1;
        final int MAXLENGTH = 35;

        int length = (id == null || id.isEmpty()) ? 0 : id.length();
        boolean patternOK = (id != null) && Pattern.matches(EBankingConstants.PATTERN_RPTID, id);

        if (length >= MINLENGTH && length <= MAXLENGTH && patternOK) {
            this.Id = id;
        } else {
            throw new IllegalArgumentException("Wrong parameter 'Id' (" + id + ") in setId()");
        }
    }

    /**
     * @param id the Id to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setId(String id, JsonValidationExceptionCollector collector) {
        try {
            setId(id);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
            //throw e;
        }
    }


    /**
     * @return the RptPgntn
     */
    public RptPgntn getRptPgntn() {
        return RptPgntn;
    }


    /**
     * @param rptPgntn the RptPgntn to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setRptPgntn(RptPgntn rptPgntn) {
        if (rptPgntn == null) {
            throw new IllegalArgumentException("Wrong parameter 'rptPgntn' in setRptPgntn()");
        }
        this.RptPgntn = rptPgntn;
    }

    /**
     * @param rptPgntn the RptPgntn to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setRptPgntn(RptPgntn rptPgntn, JsonValidationExceptionCollector collector) {
        try {
            setRptPgntn(rptPgntn);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }


    /**
     * @return the Acct
     */
    public Acct getAcct() {
        return Acct;
    }


    /**
     * @param acct the Acct to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setAcct(Acct acct) {
        if (acct == null) {
            throw new IllegalArgumentException("Wrong parameter 'acct' in setAcct()");
        }
        this.Acct = acct;
    }

    /**
     * @param acct the Acct to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setAcct(Acct acct, JsonValidationExceptionCollector collector) {
        try {
            setAcct(acct);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }


    /**
     * @return the Bal
     */
    public Bal getBal() {
        return Bal;
    }


    /**
     * @param bal the Bal to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setBal(Bal bal) {
        if (bal == null) {
            throw new IllegalArgumentException("Wrong parameter 'bal' in setBal()");
        }
        this.Bal = bal;
    }

    /**
     * @param bal the Bal to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setBal(Bal bal, JsonValidationExceptionCollector collector) {
        try {
            setBal(bal);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }


}

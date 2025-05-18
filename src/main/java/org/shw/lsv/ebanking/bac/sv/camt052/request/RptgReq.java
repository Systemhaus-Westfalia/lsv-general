package org.shw.lsv.ebanking.bac.sv.camt052.request;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.Camt052RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.Acct;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RptgReq {
    
    @JsonProperty("ReqdMsgNmId")
    String ReqdMsgNmId;  // Specifies the type of the requested reporting message

    @JsonProperty("Acct")
    Acct Acct;

    @JsonProperty("AcctOwnr")
    AcctOwnr AcctOwnr;
    
    @JsonProperty("RptgPrd")
    RptgPrd RptgPrd;
    
    @JsonProperty("RptgSeq")
    RptgSeq RptgSeq;


    public RptgReq() {
    }


    public RptgReq(Camt052RequestParams params, JsonValidationExceptionCollector collector) {
        //TODO Auto-generated constructor stub
    }


    /**
     * @return the ReqdMsgNmId
     */    
    public String getReqdMsgNmId() {
        return ReqdMsgNmId;
    }


    /**
     * @param reqdMsgNmId the ReqdMsgNmId to be set.<br>
     * The parameter is validated.<br>
     * "minLength" : 1, "maxLength" : 35; null not allowed.<br>
     * "pattern" : "[0-9a-zA-Z/\\\\-\\?:\\(\\)\\.,'\\+ ]+".<br>
     * Example: "camt.053.001.08".
     */
    public void setReqdMsgNmId(String reqdMsgNmId) {
        final int MINLENGTH = 1;
        final int MAXLENGTH = 35;

        int length = (reqdMsgNmId == null || reqdMsgNmId.isEmpty()) ? 0 : reqdMsgNmId.length();
        boolean patternOK = (reqdMsgNmId != null) && Pattern.matches(EBankingConstants.PATTERN_REQDMSGNMID, reqdMsgNmId);

        if (!(length >= MINLENGTH && length <= MAXLENGTH && patternOK)) {
            throw new IllegalArgumentException(
                "Wrong parameter 'reqdMsgNmId' (" + reqdMsgNmId + ") in setReqdMsgNmId()"
            );
        }
        this.ReqdMsgNmId = reqdMsgNmId;
    }

    /**
     * @param reqdMsgNmId the ReqdMsgNmId to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setReqdMsgNmId(String reqdMsgNmId, JsonValidationExceptionCollector collector) {
        try {
            setReqdMsgNmId(reqdMsgNmId);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
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
     * @return the AcctOwnr
     */
    public AcctOwnr getAcctOwnr() {
        return AcctOwnr;
    }
    


    /**
     * @param acctOwnr the AcctOwnr to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setAcctOwnr(AcctOwnr acctOwnr) {
        if (acctOwnr == null) {
            throw new IllegalArgumentException("Wrong parameter 'acctOwnr' in setAcctOwnr()");
        }
        this.AcctOwnr = acctOwnr;
    }

    /**
     * @param acctOwnr the AcctOwnr to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setAcctOwnr(AcctOwnr acctOwnr, JsonValidationExceptionCollector collector) {
        try {
            setAcctOwnr(acctOwnr);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }

    /**
     * @return the RptgPrd
     */
    public RptgPrd getRptgPrd() {
        return RptgPrd;
    }


    /**
     * @param rptgPrd the RptgPrd to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setRptgPrd(RptgPrd rptgPrd) {
        if (rptgPrd == null) {
            throw new IllegalArgumentException("Wrong parameter 'rptgPrd' in setRptgPrd()");
        }
        this.RptgPrd = rptgPrd;
    }

    /**
     * @param rptgPrd the RptgPrd to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setRptgPrd(RptgPrd rptgPrd, JsonValidationExceptionCollector collector) {
        try {
            setRptgPrd(rptgPrd);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }


    /**
     * @return the RptgSeq
     */
    public RptgSeq getRptgSeq() {
        return RptgSeq;
    }
    

    /**
     * @param rptgSeq the RptgSeq to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setRptgSeq(RptgSeq rptgSeq) {
        if (rptgSeq == null) {
            throw new IllegalArgumentException("Wrong parameter 'rptgSeq' in setRptgSeq()");
        }
        this.RptgSeq = rptgSeq;
    }

    /**
     * @param rptgSeq the RptgSeq to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setRptgSeq(RptgSeq rptgSeq, JsonValidationExceptionCollector collector) {
        try {
            setRptgSeq(rptgSeq);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
}
}

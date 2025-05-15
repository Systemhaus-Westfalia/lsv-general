package org.shw.lsv.ebanking.bac.sv.camt052.Request;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.Acct;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=RptgReq.class.getName();


    /**
     * @return the ReqdMsgNmId
     */    
    public String getReqdMsgNmId() {
        return ReqdMsgNmId;
    }


    /**
	 * @param reqdMsgNmId the ReqdMsgNmId to be set.
	 * The parameter is validated.
	 * "minLength" : 1, "maxLength" : 35; null not allowed.
	 * "pattern" : "[0-9a-zA-Z/\\\\-\\?:\\(\\)\\.,'\\+ ]+".
     * e.g.: "camt.053.001.08".
	 */
    public void setReqdMsgNmId(String reqdMsgNmId, JsonValidationExceptionCollector collector) {
        try {
            final int MINLENGTH = 1;
            final int MAXLENGTH = 35;
            final String PATTERN = "[0-9a-zA-Z/\\\\-\\?:\\(\\)\\.,'\\+ ]+";

            int length = (reqdMsgNmId == null || reqdMsgNmId.isEmpty()) ? 0 : reqdMsgNmId.length();
            boolean patternOK = (reqdMsgNmId != null) && Pattern.matches(PATTERN, reqdMsgNmId);

            if (!(length >= MINLENGTH && length <= MAXLENGTH && patternOK)) {
                throw new IllegalArgumentException(
                    "Wrong parameter 'reqdMsgNmId' (" + reqdMsgNmId + ") in " + FULLY_QUALIFIED_CLASSNAME + ".setReqdMsgNmId()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setReqdMsgNmId()";
            collector.addError(context, e);

            throw e;
        }

        this.ReqdMsgNmId = reqdMsgNmId;
    }


    /**
     * @return the Acct
     */
	public Acct getAcct() {
        return Acct;
    }


    /**
     * @param acct the Acct to be set.
	 * The parameter is validated: null not allowed.
     */
    public void setAcct(Acct acct, JsonValidationExceptionCollector collector) {
        try {
            if (acct == null) {
                throw new IllegalArgumentException(
                    "Wrong parameter 'acct' in " + FULLY_QUALIFIED_CLASSNAME + ".setAcct()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setAcct()";
            collector.addError(context, e);

            throw e;
        }

        this.Acct = acct;
    }


    /**
     * @return the AcctOwnr
     */
    public AcctOwnr getAcctOwnr() {
        return AcctOwnr;
    }
    

    /**
     * @param acctOwnr the AcctOwnr to be set.
	 * The parameter is validated: null not allowed.
     */
    public void setAcctOwnr(AcctOwnr acctOwnr, JsonValidationExceptionCollector collector) {
        try {
            if (acctOwnr == null) {
                throw new IllegalArgumentException(
                    "Wrong parameter 'acctOwnr' in " + FULLY_QUALIFIED_CLASSNAME + ".setAcctOwnr()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setAcctOwnr()";
            collector.addError(context, e);

            throw e;
        }

        this.AcctOwnr = acctOwnr;
    }


    /**
     * @return the RptgPrd
     */
    public RptgPrd getRptgPrd() {
        return RptgPrd;
    }
    

    /**
     * @param rptgPrd the RptgPrd to be set.
	 * The parameter is validated: null not allowed.
     */
    public void setRptgPrd(RptgPrd rptgPrd, JsonValidationExceptionCollector collector) {
        try {
            if (rptgPrd == null) {
                throw new IllegalArgumentException(
                    "Wrong parameter 'rptgPrd' in " + FULLY_QUALIFIED_CLASSNAME + ".setRptgPrd()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setRptgPrd()";
            collector.addError(context, e);

            throw e;
        }

        this.RptgPrd = rptgPrd;
    }


    /**
     * @return the RptgSeq
     */
    public RptgSeq getRptgSeq() {
        return RptgSeq;
    }
    

    /**
     * @param rptgSeq the RptgSeq to be set.
	 * The parameter is validated: null not allowed.
     */
    public void setRptgSeq(RptgSeq rptgSeq, JsonValidationExceptionCollector collector) {
        try {
            if (rptgSeq == null) {
                throw new IllegalArgumentException(
                    "Wrong parameter 'rptgSeq' in " + FULLY_QUALIFIED_CLASSNAME + ".setRptgSeq()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setRptgSeq()";
            collector.addError(context, e);

            throw e;
        }

        this.RptgSeq = rptgSeq;
    }
}

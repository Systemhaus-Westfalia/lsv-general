package org.shw.lsv.ebanking.bac.sv.camt060.Request;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.utils.Acct;

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
    final String fullyQualifiedClassName=RptgReq.class.getName();


    /**
     * @return the ReqdMsgNmId
     */    
    public String getReqdMsgNmId() {
        return ReqdMsgNmId;
    }


    /**
	 * @param reqdMsgNmId the ReqdMsgNmId to be set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 1, "maxLength" : 35; null not allowed.<br>
	 * "pattern" : "[0-9a-zA-Z/\\\\-\\?:\\(\\)\\.,'\\+ ]+"
     * e.g.: "camt.053.001.08"
	 */
    public void setReqdMsgNmId(String reqdMsgNmId) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 35;
		int length = (reqdMsgNmId==null || reqdMsgNmId.isEmpty())?0:reqdMsgNmId.length();

		final String PATTERN = "[0-9a-zA-Z/\\\\-\\?:\\(\\)\\.,'\\+ ]+";
		boolean patternOK = (reqdMsgNmId!=null) && Pattern.matches(PATTERN, reqdMsgNmId);
		
		if((length>=MINLENGTH && length<=MAXLENGTH) && patternOK)
            this.ReqdMsgNmId = reqdMsgNmId;
		else
	        throw new IllegalArgumentException("Wrong parameter 'reqdMsgNmId' (" + reqdMsgNmId +  ") in " +  fullyQualifiedClassName + ".setReqdMsgNmId()" + "\n");
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
        if (acct == null ) {
            throw new IllegalArgumentException("Wrong parameter 'acct' in " +  fullyQualifiedClassName + ".setAcct()" + "\n");
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
     * @param acctOwnr the AcctOwnr to be set<br>
	 * The parameter is validated: null not allowed.<br>
     */
    public void setAcctOwnr(AcctOwnr acctOwnr) {
        if (acctOwnr == null ) {
            throw new IllegalArgumentException("Wrong parameter 'acctOwnr' in " +  fullyQualifiedClassName + ".setAcctOwnr()" + "\n");
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
     * @param rptgPrd the RptgPrd to be set<br>
	 * The parameter is validated: null not allowed.<br>
     */
    public void setRptgPrd(RptgPrd rptgPrd) {
        if (rptgPrd == null ) {
            throw new IllegalArgumentException("Wrong parameter 'rptgPrd' in " +  fullyQualifiedClassName + ".setRptgPrd()" + "\n");
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
     * @param rptgSeq the RptgSeq to be set<br>
	 * The parameter is validated: null not allowed.<br>
     */
    public void setRptgSeq(RptgSeq rptgSeq) {
        if (rptgSeq == null ) {
            throw new IllegalArgumentException("Wrong parameter 'rptgSeq' in " +  fullyQualifiedClassName + ".setRptgSeq()" + "\n");
        }
        this.RptgSeq = rptgSeq;
    }
}

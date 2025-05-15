package org.shw.lsv.ebanking.bac.sv.camt052.Response;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.misc.Acct;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=Rpt.class.getName();


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
		int length = (id==null || id.isEmpty())?0:id.length();

		final String PATTERN = "([0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ]([0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ]*(/[0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ])?)*)";
		boolean patternOK = (id!=null) && Pattern.matches(PATTERN, id);
		
		if((length>=MINLENGTH && length<=MAXLENGTH) && patternOK)
			this.Id = id;
		else
	        throw new IllegalArgumentException("Wrong parameter 'Id' (" + id +  ") in " +  FULLY_QUALIFIED_CLASSNAME + ".setId()" + "\n");
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
        if (rptPgntn == null ) {
            throw new IllegalArgumentException("Wrong parameter 'rptPgntn' in " +  FULLY_QUALIFIED_CLASSNAME + ".setRptPgntn()" + "\n");
        }
        this.RptPgntn = rptPgntn;
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
            throw new IllegalArgumentException("Wrong parameter 'acct' in Rpt.setAcct()" + "\n");
        }
        this.Acct = acct;
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
        if (bal == null ) {
            throw new IllegalArgumentException("Wrong parameter 'bal' in Rpt.setBal()" + "\n");
        }
        this.Bal = bal;
    }


}

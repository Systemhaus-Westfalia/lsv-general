package org.shw.lsv.ebanking.bac.sv.utils;

import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Common information for the message.
 */
public class GrpHdr {
                      
    @JsonProperty(value = "MsgId", required = true)
    String MsgId;  // Point to point reference, as assigned by the account servicing institution, and sent to the account owner or the party authorised to receive the message.
                      
    @JsonProperty(value = "CreDtTm", required = true)
    String CreDtTm;  // Date and time at which the message was created.

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=GrpHdr.class.getName();

    /**
	 * @param msgId the MsgId to be set<br>
	 * @param creDtTm the CreDtTm to be set<br>
	 * Both @msgId and @creDtTm are mandatory in the JSON definition.
	 * 
     * Constructor with parameters
     * For using the Constructor at deserialization time, it has to be of the form:
     * public GrpHdr(@JsonProperty(value = "MsgId", required = true) String msgId,.....)
     */
    public GrpHdr(String msgId, String creDtTm) {
		setMsgId(msgId);
        setCreDtTm(creDtTm);
    }


	/**
	 * @return the MsgId
	 */
	public String getMsgId() {
        return MsgId;
    }


	/**
	 * @param msgId the MsgId to be set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 1, "maxLength" : 35; null not allowed.<br>
	 * "pattern" : "[0-9a-zA-Z/\\-\\?:\\(\\)\\.,'\\+ ]+"
     * e.g.: "ABNA202009081223"
	 */
    public void setMsgId(String msgId) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 35;
		int length = (msgId==null || msgId.isEmpty())?0:msgId.length();

		final String PATTERN = "[0-9a-zA-Z/\\-\\?:\\(\\)\\.,'\\+ ]+";
		boolean patternOK = (msgId!=null) && Pattern.matches(PATTERN, msgId);
		
		if((length>=MINLENGTH && length<=MAXLENGTH) && patternOK)
			this.MsgId = msgId;
		else
	        throw new IllegalArgumentException("Wrong parameter 'msgId' (" + msgId +  ") in " +  FULLY_QUALIFIED_CLASSNAME + ".setMsgId()" + "\n");
    }


	/**
	 * @return the CreDtTm
	 */
    public String getCreDtTm() {
        return CreDtTm;
    }


	/**
	 * @param creDtTm the CreDtTm to be set<br>
	 * The parameter is validated.<br>
	 * "pattern" : ".*(+|-)((0[0-9])|(1[0-3])):[0-5][0-9]"
     * pattern copilot: "^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}[+-]([0][0-9]|[1][0-1]):[0-5][0-9])$"
     * e.g.: "2020-09-08T18:00:00+02:00"
	 */
    public void setCreDtTm(String creDtTm) {
		final String PATTERN = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}[+-]([0][0-9]|[1][0-1]):[0-5][0-9])$";
		boolean patternOK = (creDtTm!=null) && Pattern.matches(PATTERN, creDtTm);
		
		if(patternOK)
			this.CreDtTm = creDtTm;
		else
	        throw new IllegalArgumentException("Wrong parameter 'creDtTm' (" + creDtTm +  ") in " +  FULLY_QUALIFIED_CLASSNAME + ".setCreDtTm()" + "\n");
    }
	

    public static void main(String[] args) {
        System.out.println(GrpHdr.class.getName());
    }

}

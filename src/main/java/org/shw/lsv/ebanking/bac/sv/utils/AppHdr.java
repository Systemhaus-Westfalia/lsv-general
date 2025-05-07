package org.shw.lsv.ebanking.bac.sv.utils;

import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Application Header.
 * Applies to all schemas.
 */
@JsonIgnoreProperties(ignoreUnknown = true) // Ignore unknown JSON fields during deserialization
public class AppHdr {
    @JsonProperty("Fr")
    private Fr fr; 				// Sending MessagingEndpoint

    @JsonProperty("To")
    private To to; 				// Receiving MessagingEndpoint

    @JsonProperty("BizMsgIdr")
    private String BizMsgIdr;	// Business Message Identifier

    @JsonProperty("MsgDefIdr")
    private String MsgDefIdr; 	// Message Identifier

    @JsonProperty("BizSvc")
    private String BizSvc; // Business service agreed between two MessagingEndpoints

    @JsonProperty("CreDt")
    String CreDt;     			// Creation Date and Time

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=AppHdr.class.getName();


    /**
	 * @return the Fr object<br>
	 */
    public Fr getFr() {
        return fr;
    }


	/**
	 * @param fr the Fr object to be set<br>
	 * The parameter is validated: null not allowed.<br>
	 */
    public void setFr(Fr fr) {
        if (fr == null ) {
            throw new IllegalArgumentException("Wrong parameter 'fr' in " +  FULLY_QUALIFIED_CLASSNAME + ".setFr()" + "\n");
        }
        this.fr = fr;
    }


    /**
	 * @return the To object<br>
	 */
	public To getTo() {
        return to;
    }


	/**
	 * @param to the To object to be set<br>
	 * The parameter is validated: null not allowed.<br>
	 */
    public void setTo(To to) {
        if (to == null ) {
            throw new IllegalArgumentException("Wrong parameter 'to' in " +  FULLY_QUALIFIED_CLASSNAME + ".setTo()" + "\n");
        }
        this.to = to;
    }


	/**
	 * @return the BizMsgIdr
	 */
    public String getBizMsgIdr() {
        return BizMsgIdr;
    }


	/**
	 * @param bizMsgIdr the bizMsgIdr to be set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 1, "maxLength" : 35; null not allowed.
	 */
    public void setBizMsgIdr(String bizMsgIdr) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 35;
		int length = bizMsgIdr==null?0:bizMsgIdr.length();
		
		if(length>=MINLENGTH && length<=MAXLENGTH)
			this.BizMsgIdr = bizMsgIdr;
		else
	        throw new IllegalArgumentException("Wrong parameter 'bizMsgIdr' (" + bizMsgIdr +  ") in " +  FULLY_QUALIFIED_CLASSNAME + ".setBizMsgIdr()" + "\n");
    }


	/**
	 * @return the MsgDefIdr
	 */
    public String getMsgDefIdr() {
        return MsgDefIdr;
    }


	/**
	 * @param msgDefIdr the msgDefIdr to be set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 1, "maxLength" : 35; null not allowed.
	 */
    public void setMsgDefIdr(String msgDefIdr) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 35;
		int length = (msgDefIdr==null || msgDefIdr.isEmpty())?0:msgDefIdr.length();
		
		if(length>=MINLENGTH && length<=MAXLENGTH)
			this.MsgDefIdr = msgDefIdr;
		else
	        throw new IllegalArgumentException("Wrong parameter 'msgDefIdr' (" + msgDefIdr +  ") in " +  FULLY_QUALIFIED_CLASSNAME + ".setMsgDefIdr()" + "\n");
    }


	/**
	 * @return the BizSvc
	 */
    public String getBizSvc() {
        return BizSvc;
    }


	/**
	 * @param bizSvc the BizSvc to be set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 6, "maxLength" : 35; null not allowed.<br>
	 * "pattern" : "[a-z0-9]{1,10}.([a-z0-9]{1,10}.)+dd"
     * e.g.: "swift.cbprplus.01"
	 */
    public void setBizSvc(String bizSvc) {
		final int MINLENGTH = 6;
		final int MAXLENGTH = 35;
		int length = (bizSvc==null || bizSvc.isEmpty())?0:bizSvc.length();

		final String PATTERN = "[a-z0-9]{1,10}.([a-z0-9]{1,10}.)+dd";
		boolean patternOK = (bizSvc!=null) && Pattern.matches(PATTERN, bizSvc);
		
		if((length>=MINLENGTH && length<=MAXLENGTH) && patternOK)
			this.BizSvc = bizSvc;
		else
	        throw new IllegalArgumentException("Wrong parameter 'bizSvc' (" + bizSvc +  ") in " +  FULLY_QUALIFIED_CLASSNAME + ".setBizSvc()" + "\n");
    }


	/**
	 * @return the CreDt
	 */
    public String getCreDt() {
        return CreDt;
    }


	/**
	 * @param creDt the CreDt to be set<br>
	 * The parameter is validated.<br>
	 * "pattern" : ".*(+|-)((0[0-9])|(1[0-3])):[0-5][0-9]"
     * pattern copilot: "^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}[+-]([0][0-9]|[1][0-1]):[0-5][0-9])$"
     * e.g.: "2020-09-08T18:00:00+02:00"
	 */
    public void setCreDt(String creDt) {
		final String PATTERN = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}[+-]([0][0-9]|[1][0-1]):[0-5][0-9])$";
		boolean patternOK = (creDt!=null) && Pattern.matches(PATTERN, creDt);
		
		if(patternOK)
			this.CreDt = creDt;
		else
	        throw new IllegalArgumentException("Wrong parameter 'creDt' (" + creDt +  ") in " +  FULLY_QUALIFIED_CLASSNAME + ".setCreDt()" + "\n");
    }


    public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}

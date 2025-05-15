package org.shw.lsv.ebanking.bac.sv.misc;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

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
    public GrpHdr(String msgId, String creDtTm, JsonValidationExceptionCollector collector) {
		setMsgId(msgId, collector);
        setCreDtTm(creDtTm, collector);
    }


	/**
	 * @return the MsgId
	 */
	public String getMsgId() {
        return MsgId;
    }


	/**
	 * @param msgId the MsgId to be set.
	 * The parameter is validated.
	 * "minLength" : 1, "maxLength" : 35; null not allowed.
	 * "pattern" : "[0-9a-zA-Z/\\-\\?:\\(\\)\\.,'\\+ ]+".
     * e.g.: "ABNA202009081223".
	 */
    public void setMsgId(String msgId, JsonValidationExceptionCollector collector) {
        try {
            // Validate the msgId parameter
            final int MINLENGTH = 1;
            final int MAXLENGTH = 35;
            final String PATTERN = "[0-9a-zA-Z/\\-\\?:\\(\\)\\.,'\\+ ]+";

            int length = (msgId == null || msgId.isEmpty()) ? 0 : msgId.length();
            boolean patternOK = (msgId != null) && Pattern.matches(PATTERN, msgId);

            if (!(length >= MINLENGTH && length <= MAXLENGTH && patternOK)) {
                throw new IllegalArgumentException(
                    "Wrong parameter 'msgId' (" + msgId + ") in " + FULLY_QUALIFIED_CLASSNAME + ".setMsgId()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            // Add error to the collector
            String context = FULLY_QUALIFIED_CLASSNAME + ".setMsgId()";
            collector.addError(context, e);

            // Re-throw the exception to stop processing
            throw e;
        }

        // Set the MsgId field
        this.MsgId = msgId;
    }


	/**
	 * @return the CreDtTm
	 */
    public String getCreDtTm() {
        return CreDtTm;
    }


	/**
	 * @param creDtTm the CreDtTm to be set.
	 * The parameter is validated.
	 * "pattern" : ".*(+|-)((0[0-9])|(1[0-3])):[0-5][0-9]".
     * pattern copilot: "^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}[+-]([0][0-9]|[1][0-1]):[0-5][0-9])$".
     * e.g.: "2020-09-08T18:00:00+02:00".
	 */
    public void setCreDtTm(String creDtTm, JsonValidationExceptionCollector collector) {
        try {
            final String PATTERN = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}[+-]([0][0-9]|[1][0-1]):[0-5][0-9]$";
            boolean patternOK = (creDtTm != null) && Pattern.matches(PATTERN, creDtTm);

            if (!patternOK) {
                throw new IllegalArgumentException(
                    "Wrong parameter 'creDtTm' (" + creDtTm + ") in " + FULLY_QUALIFIED_CLASSNAME + ".setCreDtTm()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setCreDtTm()";
            collector.addError(context, e);

            throw e;
        }

        this.CreDtTm = creDtTm;
    }
	

    public static void main(String[] args) {
        System.out.println(GrpHdr.class.getName());
    }

}

package org.shw.lsv.ebanking.bac.sv.misc;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Common information for the message.
 */
public class GrpHdr {

    @JsonProperty(value = "MsgId", required = true)
    String MsgId;  // Point to point reference, as assigned by the account servicing institution, and sent to the account owner or the party authorised to receive the message.

    @JsonProperty(value = "CreDtTm", required = true)
    String CreDtTm;  // Date and time at which the message was created.

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
	 * @param msgId the MsgId to be set.<br>
	 * The parameter is validated.<br>
	 * "minLength" : 1, "maxLength" : 35; null not allowed.<br>
	 * "pattern" : "[0-9a-zA-Z/\\-\\?:\\(\\)\\.,'\\+ ]+".<br>
	 * Example: "ABNA202009081223".
	 */
	public void setMsgId(String msgId) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 35;

		int length = (msgId == null || msgId.isEmpty()) ? 0 : msgId.length();
		boolean patternOK = (msgId != null) && Pattern.matches(EBankingConstants.PATTERN_MSG_ID, msgId);

		if (!(length >= MINLENGTH && length <= MAXLENGTH && patternOK)) {
			throw new IllegalArgumentException(
				"Wrong parameter 'msgId' (" + msgId + ") in setMsgId()"
			);
		}
		this.MsgId = msgId;
	}

	/**
	 * @param msgId the MsgId to be set.<br>
	 * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
	 */
	public void setMsgId(String msgId, JsonValidationExceptionCollector collector) {
		try {
			setMsgId(msgId);
		} catch (IllegalArgumentException e) {
			collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
			//throw e;
		}
	}


	/**
	 * @return the CreDtTm
	 */
    public String getCreDtTm() {
        return CreDtTm;
    }


	/**
	 * @param creDtTm the CreDtTm to be set.<br>
	 * The parameter is validated.<br>
	 * "pattern" : ".*(+|-)((0[0-9])|(1[0-3])):[0-5][0-9]".<br>
		 * pattern copilot: "^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}[+-]([0][0-9]|[1][0-1]):[0-5][0-9])$".
	 * Example: "2020-09-08T18:00:00+02:00".
	 */
	public void setCreDtTm(String creDtTm) {
		boolean patternOK = (creDtTm != null) && Pattern.matches(EBankingConstants.PATTERN_CREDTM, creDtTm);

		if (!patternOK) {
			throw new IllegalArgumentException(
				"Wrong parameter 'creDtTm' (" + creDtTm + ") in setCreDtTm()"
			);
		}
		this.CreDtTm = creDtTm;
	}

	/**
	 * @param creDtTm the CreDtTm to be set.<br>
	 * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
	 */
	public void setCreDtTm(String creDtTm, JsonValidationExceptionCollector collector) {
		try {
			setCreDtTm(creDtTm);
		} catch (IllegalArgumentException e) {
			collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
			//throw e;
		}
	}
	

    public static void main(String[] args) {
        System.out.println(GrpHdr.class.getName());
    }

}

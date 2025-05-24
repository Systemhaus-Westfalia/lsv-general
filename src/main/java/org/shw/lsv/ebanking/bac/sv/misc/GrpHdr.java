package org.shw.lsv.ebanking.bac.sv.misc;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.pain001.request.InitgPty;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Common information for the message.
 */
public class GrpHdr {

    @JsonProperty(value = "MsgId", required = true)
    String msgId;  // Point to point reference, as assigned by the account servicing institution, and sent to the account owner or the party authorised to receive the message.

    @JsonProperty(value = "CreDtTm", required = true)
    String creDtTm;  // Date and time at which the message was created.

    @JsonProperty("NbOfTxs")                    // For payments
    @JsonInclude(JsonInclude.Include.NON_NULL)  // Exclude this field if its value is null
    Integer nbOfTxs;

    @JsonProperty("CtrlSum")                    // For payments
    @JsonInclude(JsonInclude.Include.NON_NULL)  // Exclude this field if its value is null
    BigDecimal ctrlSum;

    @JsonProperty("InitgPty")                   // For payments
    @JsonInclude(JsonInclude.Include.NON_NULL)  // Exclude this field if its value is null
    InitgPty initgPty;

    public GrpHdr() {
	}


	/**
	 * @param msgId the MsgId to be set<br>
	 * @param creDtTm the CreDtTm to be set<br>
	 * Both @msgId and @creDtTm are mandatory in the JSON definition.
	 * 
     * Constructor with parameters
     * For using the Constructor at deserialization time, it has to be of the form:
     * public GrpHdr(@JsonProperty(value = "MsgId", required = true) String msgId,.....)
     */
    public GrpHdr(RequestParams params, JsonValidationExceptionCollector collector) {
		setMsgId(  params.getMsgId(),   collector);
        setCreDtTm(params.getCreDtTm(), collector);

		// Payments
		if(params.getNbOfTxs() != null) {
			setNbOfTxs(params.getNbOfTxs());
		}
		if(params.getCtrlSum() != null ) {
			setCtrlSum(params.getCtrlSum());
		}

		if(params.getNm() != null && !params.getNm().isEmpty()) {
        	setInitgPty(new InitgPty(params, collector), collector);
    	}

    }


	/**
	 * @return the MsgId
	 */
	public String getMsgId() {
        return msgId;
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
		this.msgId = msgId;
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
        return creDtTm;
    }


	/**
	 * @param creDtTm the CreDtTm to be set.<br>
	 * The parameter is validated.<br>
	 * "pattern" : ".*(+|-)((0[0-9])|(1[0-3])):[0-5][0-9]".<br>
		 * pattern copilot: "^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}[+-]([0][0-9]|[1][0-1]):[0-5][0-9]$".
	 * Example: "2020-09-08T18:00:00+02:00".
	 */
	public void setCreDtTm(String creDtTm) {
		boolean patternOK = (creDtTm != null) && Pattern.matches(EBankingConstants.PATTERN_DATETIME, creDtTm);

		if (!patternOK) {
			throw new IllegalArgumentException(
				"Wrong parameter 'creDtTm' (" + creDtTm + ") in setCreDtTm()"
			);
		}
		this.creDtTm = creDtTm;
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

/**
     * @return the NbOfTxs
     */
    public Integer getNbOfTxs() {
        return nbOfTxs;
    }


	/**
     * @param nbOfTxs the NbOfTxs to be set<br>
     * The parameter is validated: must be positive; null not allowed.<br>
     * Example: 3
     */
    public void setNbOfTxs(Integer nbOfTxs) {
        if (nbOfTxs == null || nbOfTxs <= 0) {
            throw new IllegalArgumentException(
                "Wrong parameter 'nbOfTxs' (" + nbOfTxs + ") in setNbOfTxs(): must be positive and not null"
            );
        }
        this.nbOfTxs = nbOfTxs;
    }


	/**
     * @return the CtrlSum
     */
    public BigDecimal getCtrlSum() {
        return ctrlSum;
    }


	/**
     * @param ctrlSum the CtrlSum to be set.<br>
     * The parameter is validated: must be non-null, non-negative, and have two decimal places.<br>
     * Example: 123.45
     */
    public void setCtrlSum(BigDecimal ctrlSum) {
        if (ctrlSum == null || ctrlSum.scale() != 2 || ctrlSum.signum() < 0) {
            throw new IllegalArgumentException(
                "Wrong parameter 'ctrlSum' (" + ctrlSum + ") in setCtrlSum(): must be non-null, non-negative, and have two decimal places"
            );
        }
        this.ctrlSum = ctrlSum;
    }


	/**
     * @param initgPty the InitgPty to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setInitgPty(InitgPty initgPty) {
        if (initgPty == null) {
            throw new IllegalArgumentException("Wrong parameter 'initgPty' in setInitgPty()");
        }
        this.initgPty = initgPty;
    }


	/**
     * @param initgPty the InitgPty to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setInitgPty(InitgPty initgPty, JsonValidationExceptionCollector collector) {
        try {
            setInitgPty(initgPty);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }


	public static void main(String[] args) {
        System.out.println(GrpHdr.class.getName());
    }

}

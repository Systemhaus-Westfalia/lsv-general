package org.shw.lsv.ebanking.bac.sv.misc;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.Camt052RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

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
    private String bizMsgIdr;	// Business Message Identifier

    @JsonProperty("MsgDefIdr")
    private String msgDefIdr; 	// Message Identifier

    @JsonProperty("BizSvc")
    private String bizSvc; // Business service agreed between two MessagingEndpoints

    @JsonProperty("CreDt")
    String creDt;     			// Creation Date and Time


    public AppHdr() {}


	public AppHdr(Camt052RequestParams params, JsonValidationExceptionCollector collector) {
		try {
			setFr(new Fr(params, EBankingConstants.CONTEXT_FR, collector), collector);
			setTo(new To(params, EBankingConstants.CONTEXT_TO, collector), collector);
			
            setBizMsgIdr(params.getBizMsgIdr(), collector);
            setMsgDefIdr(params.getMsgDefIdr(), collector);
            setBizSvc   (params.getBizSvc(),    collector);
            setCreDt    (params.getCreDt(),     collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_APPHDR_INIT, e);
        }
	}


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
		if (fr == null) {
			throw new IllegalArgumentException("Wrong parameter 'fr' in setFr()");
		}
		this.fr = fr;
	}

	/**
	 * @param fr the Fr object to be set<br>
	 * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
	 */
	public void setFr(Fr fr, JsonValidationExceptionCollector collector) {
		try {
			setFr(fr);
		} catch (IllegalArgumentException e) {
			collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
			//throw e;
		}
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
		if (to == null) {
			throw new IllegalArgumentException("Wrong parameter 'to' in setTo()");
		}
		this.to = to;
	}

	/**
	 * @param to the To object to be set<br>
	 * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
	 */
	public void setTo(To to, JsonValidationExceptionCollector collector) {
		try {
			setTo(to);
		} catch (IllegalArgumentException e) {
			collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
			//throw e;
		}
	}


	/**
	 * @return the BizMsgIdr
	 */
    public String getBizMsgIdr() {
        return bizMsgIdr;
    }


	/**
	 * @param bizMsgIdr the BizMsgIdr to be set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 1, "maxLength" : 35; null not allowed.<br>
	 */
	public void setBizMsgIdr(String bizMsgIdr) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 35;
		int length = (bizMsgIdr == null || bizMsgIdr.isEmpty()) ? 0 : bizMsgIdr.length();

		if (length < MINLENGTH || length > MAXLENGTH) {
			throw new IllegalArgumentException(
				"Wrong parameter 'bizMsgIdr' (" + bizMsgIdr + ") in setBizMsgIdr()"
			);
		}
		this.bizMsgIdr = bizMsgIdr;
	}

	/**
	 * @param bizMsgIdr the BizMsgIdr to be set<br>
	 * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
	 */
	public void setBizMsgIdr(String bizMsgIdr, JsonValidationExceptionCollector collector) {
		try {
			setBizMsgIdr(bizMsgIdr);
		} catch (IllegalArgumentException e) {
			collector.addError(EBankingConstants.ERROR_WRONG_LENGTH, e);
			//throw e;
		}
	}


	/**
	 * @return the MsgDefIdr
	 */
    public String getMsgDefIdr() {
        return msgDefIdr;
    }


	/**
	 * @param msgDefIdr the MsgDefIdr to be set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 1, "maxLength" : 35; null not allowed.<br>
	 */
	public void setMsgDefIdr(String msgDefIdr) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 35;
		int length = (msgDefIdr == null || msgDefIdr.isEmpty()) ? 0 : msgDefIdr.length();

		if (length < MINLENGTH || length > MAXLENGTH) {
			throw new IllegalArgumentException(
				"Wrong parameter 'msgDefIdr' (" + msgDefIdr + ") in setMsgDefIdr()"
			);
		}
		this.msgDefIdr = msgDefIdr;
	}

	/**
	 * @param msgDefIdr the MsgDefIdr to be set<br>
	 * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
	 */
	public void setMsgDefIdr(String msgDefIdr, JsonValidationExceptionCollector collector) {
		try {
			setMsgDefIdr(msgDefIdr);
		} catch (IllegalArgumentException e) {
			collector.addError(EBankingConstants.ERROR_WRONG_LENGTH, e);
			//throw e;
		}
	}


	/**
	 * @return the BizSvc
	 */
    public String getBizSvc() {
        return bizSvc;
    }


	/**
	 * @param bizSvc the BizSvc to be set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 6, "maxLength" : 35; null not allowed.<br>
	 * "pattern" : "[a-z0-9]{1,10}.([a-z0-9]{1,10}.)+dd".<br>
	 * copilot "pattern" : "[a-z0-9]{1,10}.([a-z0-9]{1,10}.)+\\d{2}".<br>
	 * e.g.: "swift.cbprplus.01".
	 */
	public void setBizSvc(String bizSvc) {
		final int MINLENGTH = 6;
		final int MAXLENGTH = 35;

		int length = (bizSvc == null || bizSvc.isEmpty()) ? 0 : bizSvc.length();
		boolean patternOK = (bizSvc != null) && Pattern.matches(EBankingConstants.PATTERN_BIZSVC, bizSvc);

		if (!(length >= MINLENGTH && length <= MAXLENGTH && patternOK)) {
			throw new IllegalArgumentException("Wrong parameter 'bizSvc' (" + bizSvc + ") in setBizSvc()");
		}
		this.bizSvc = bizSvc;
	}

	/**
	 * @param bizSvc the BizSvc to be set<br>
	 * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
	 */
	public void setBizSvc(String bizSvc, JsonValidationExceptionCollector collector) {
		try {
			setBizSvc(bizSvc);
		} catch (IllegalArgumentException e) {
			collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
			//throw e;
		}
	}


	/**
	 * @return the CreDt
	 */
    public String getCreDt() {
        return creDt;
    }
	

	/**
	 * @param creDt the CreDt to be set<br>
	 * The parameter is validated: null not allowed.<br>
	 * "pattern" : ".*(+|-)((0[0-9])|(1[0-3])):[0-5][0-9]"
	 * pattern copilot: "^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}[+-]([0][0-9]|[1][0-1]):[0-5][0-9])$".
	 */
	public void setCreDt(String creDt) {
		boolean patternOK = (creDt != null) && Pattern.matches(EBankingConstants.PATTERN_DATETIME, creDt);

		if (!patternOK) {
			throw new IllegalArgumentException("Wrong parameter 'creDt' (" + creDt + ") in setCreDt()");
		}
		this.creDt = creDt;
	}

	/**
	 * @param creDt the CreDt to be set<br>
	 * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
	 */
	public void setCreDt(String creDt, JsonValidationExceptionCollector collector) {
		try {
			setCreDt(creDt);
		} catch (IllegalArgumentException e) {
			collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
			//throw e;
		}
	}


    public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}

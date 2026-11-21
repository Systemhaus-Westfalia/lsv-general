package org.shw.lsv.ebanking.bac.sv.misc;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AcctIdOthr {

    @JsonProperty("Id")
    String id;


    public AcctIdOthr() {}


	public AcctIdOthr(RequestParams params, String context, JsonValidationExceptionCollector collector) {
        try {  // TODO: sich vergewissern, dass alle drei gleichzeitig vorkomment koennen!! Bei Payments sind DbtrAcctId und CdtrAcctId moeglich.
            if ( context.equals(EBankingConstants.CONTEXT_RPTGREQ) && ( !(params.getAcctId() == null || params.getAcctId().isEmpty()) )) {
				setId(params.getAcctId(), collector);			// # 2
            }
			if (context.equals(EBankingConstants.CONTEXT_DBTRACCT)  && ( !(params.getDbtrAcctId() == null || params.getDbtrAcctId().isEmpty()) )) {
				setId(params.getDbtrAcctId(), collector);			// # 6
            }
			if (context.equals(EBankingConstants.CONTEXT_CDTRACCT)  && ( !(params.getCdtrAcctId() == null || params.getCdtrAcctId().isEmpty()) )) {
				setId(params.getCdtrAcctId(), collector);			// # 8
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_ACCTIDOTHR_INIT, e);
        }
    }


	/*
     * Constructor with parameters
     * For using the Constructor at deserialization time, it has to be of the form:
     * public AcctIdOthr(@JsonProperty(value = "Id", required = true) String id,.....)
     */
    public AcctIdOthr(String id, JsonValidationExceptionCollector collector) {
        setId(id, collector);
    }


    /**
	 * @return the Id
	 */
	public String getId() {
        return id;  // Choice Acct_Id_2: Unique identification of an account, as assigned by the account servicer, using an identification scheme.
    }


	/**
	 * @param id the Id to be set.<br>
	 * The parameter is validated.<br>
	 * "minLength" : 1, "maxLength" : 34; null not allowed.<br>
	 * "pattern" : "([0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ]([0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ]*(/[0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ])?)*)".<br>
	* Example: "ABNA202009081223".
	*/
	public void setId(String id) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 34;

		int length = (id == null || id.isEmpty()) ? 0 : id.length();
		boolean patternOK = (id != null) && Pattern.matches(EBankingConstants.PATTERN_ACCT_ID, id);

		if (!(length >= MINLENGTH && length <= MAXLENGTH && patternOK)) {
			throw new IllegalArgumentException(
				"Wrong parameter 'Id' (" + id + ") in setId()"
			);
		}
		this.id = id;
	}

	/**
	 * @param id the Id to be set.<br>
	 * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
	 */
	public void setId(String id, JsonValidationExceptionCollector collector) {
		try {
			setId(id);
		} catch (IllegalArgumentException e) {
			collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
			//throw e;
		}
	}
}

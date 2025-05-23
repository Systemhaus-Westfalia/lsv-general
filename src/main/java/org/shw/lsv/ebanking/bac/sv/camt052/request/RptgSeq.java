package org.shw.lsv.ebanking.bac.sv.camt052.request;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.RequestParamsCamt052;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RptgSeq {
    @JsonProperty("EQSeq")
    String eQSeq;


	public RptgSeq() {}


	public RptgSeq(RequestParamsCamt052 params, JsonValidationExceptionCollector collector) {
        try {
            setEQSeq(params.getEqseq(), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_RPTGREQ_INIT, e);
        }
	}


	/**
	 * @return the EQSeq
	 */
    public String geteQSeq() {
        return eQSeq;
    }


	/**
	 * @param eQSeq the EQSeq to be set.<br>
	 * The parameter is validated.<br>
	 * Pattern: "[0-9a-zA-Z/\\\\-\\?:\\(\\)\\.,'\\+ ]+".<br>
	 * Example: "1", "abc123", "A-Z/?", "Hello, World!", "1+2-3", "abc/def\\ghi".
	 */
	public void seteQSeq(String eQSeq) {
		boolean patternOK = (eQSeq != null) && Pattern.matches(EBankingConstants.PATTERN_EQSEQ, eQSeq);

		if (!patternOK) {
			throw new IllegalArgumentException(
				"Wrong parameter 'eQSeq' (" + eQSeq + ") in setEQSeq()"
			);
		}
		this.eQSeq = eQSeq;
	}

	/**
	 * @param eQSeq the EQSeq to be set.<br>
	 * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
	 */
	public void setEQSeq(String eQSeq, JsonValidationExceptionCollector collector) {
		try {
			seteQSeq(eQSeq);
		} catch (IllegalArgumentException e) {
			collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
			//throw e;
		}
	}

}

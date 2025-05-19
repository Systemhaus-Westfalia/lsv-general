package org.shw.lsv.ebanking.bac.sv.camt052.request;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.Camt052RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FrToDt {
    @JsonProperty("FrDt")
    String FrDt;

    @JsonProperty("ToDt")
    String ToDt;


	public FrToDt() {}


	public FrToDt(Camt052RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setFrDt(params.getFrdt(), collector);
            setToDt(params.getTodt(), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_FRTODT_INIT, e);
        }
	}


	/**
	 * @return the FrDt
	 */
    public String getFrDt() {
        return FrDt;
    }


	/**
	 * @param frDt the FrDt to be set.<br>
	 * The parameter is validated.<br>
	 * Pattern: "^\d{4}-\d{2}-\d{2}$".<br>
	 * Example: "2020-09-08".
	 */
	public void setFrDt(String frDt) {
		boolean patternOK = (frDt != null) && Pattern.matches(EBankingConstants.PATTERN_FRDT, frDt);

		if (!patternOK) {
			throw new IllegalArgumentException(
				"Wrong parameter 'frDt' (" + frDt + ") in setFrDt()"
			);
		}
		this.FrDt = frDt;
	}

	/**
	 * @param frDt the FrDt to be set.<br>
	 * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
	 */
	public void setFrDt(String frDt, JsonValidationExceptionCollector collector) {
		try {
			setFrDt(frDt);
		} catch (IllegalArgumentException e) {
			collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
			//throw e;
		}
	}


	/**
	 * @return the ToDt
	 */
    public String getToDt() {
        return ToDt;
    }


	/**
	 * @param toDt the ToDt to be set.<br>
	 * The parameter is validated.<br>
	 * pattern copilot: "^\d{4}-\d{2}-\d{2}$".<br>
	 * Example: "2020-09-08".
	 */
	public void setToDt(String toDt) {
		boolean patternOK = (toDt != null) && Pattern.matches(EBankingConstants.PATTERN_TODT, toDt);

		if (!patternOK) {
			throw new IllegalArgumentException(
				"Wrong parameter 'toDt' (" + toDt + ") in setToDt()"
			);
		}
		this.ToDt = toDt;
	}

	/**
	 * @param toDt the ToDt to be set.<br>
	 * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
	 */
	public void setToDt(String toDt, JsonValidationExceptionCollector collector) {
		try {
			setToDt(toDt);
		} catch (IllegalArgumentException e) {
			collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
			//throw e;
		}
	}

}

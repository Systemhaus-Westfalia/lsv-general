package org.shw.lsv.ebanking.bac.sv.camt053.response;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FrToDt {
    @JsonProperty("FrDtTm")
    String frDtTm;

    @JsonProperty("ToDtTm")
    String toDtTm;


	public FrToDt() {}


	public FrToDt(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setFrDtTm(params.getFrdt(), collector);
            setToDtTm(params.getTodt(), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_FRTODT_INIT, e);
        }
	}


	/**
	 * @return the FrDtTm (From Date Time)
	 */
    public String getFrDtTm() {
        return frDtTm;
    }


	/**
	 * @param frDtTm the FrDtTm (From Date Time) to be set.<br>
	 * The parameter is validated.<br>
	 * Pattern: "^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}[+-]([0][0-9]|[1][0-1]):[0-5][0-9]$".<br>
	 * Example: "2020-09-08T00:00:00+02:00".
	 */
	public void setFrDtTm(String frDtTm) {
		boolean patternOK = (frDtTm != null) && Pattern.matches(EBankingConstants.PATTERN_DATETIME_TZ, frDtTm);

		if (!patternOK) {
			throw new IllegalArgumentException(
				"Wrong parameter 'frDtTm' (" + frDtTm + ") in setFrDtTm()"
			);
		}
		this.frDtTm = frDtTm;
	}

	/**
	 * @param frDtTm the FrDtTm (From Date Time) to be set.<br>
	 * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
	 */
	public void setFrDtTm(String frDtTm, JsonValidationExceptionCollector collector) {
		try {
			setFrDtTm(frDtTm);
		} catch (IllegalArgumentException e) {
			collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
		}
	}


	/**
	 * @return the ToDtTm (To Date Time)
	 */
    public String getToDtTm() {
        return toDtTm;
    }


	/**
	 * @param toDtTm the ToDtTm (To Date Time) to be set.<br>
	 * The parameter is validated.<br>
	 * Pattern: "^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}[+-]([0][0-9]|[1][0-1]):[0-5][0-9]$".<br>
	 * Example: "2020-09-08T23:59:59+02:00".
	 */
	public void setToDtTm(String toDtTm) {
		boolean patternOK = (toDtTm != null) && Pattern.matches(EBankingConstants.PATTERN_DATETIME_TZ, toDtTm);

		if (!patternOK) {
			throw new IllegalArgumentException(
				"Wrong parameter 'toDtTm' (" + toDtTm + ") in setToDtTm()"
			);
		}
		this.toDtTm = toDtTm;
	}

	/**
	 * @param toDtTm the ToDtTm (To Date Time) to be set.<br>
	 * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
	 */
	public void setToDtTm(String toDtTm, JsonValidationExceptionCollector collector) {
		try {
			setToDtTm(toDtTm);
		} catch (IllegalArgumentException e) {
			collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
		}
	}

}

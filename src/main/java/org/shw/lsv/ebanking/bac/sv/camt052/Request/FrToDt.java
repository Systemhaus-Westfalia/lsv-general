package org.shw.lsv.ebanking.bac.sv.camt052.Request;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FrToDt {
    @JsonProperty("FrDt")
    String FrDt;

    @JsonProperty("ToDt")
    String ToDt;

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=FrToDt.class.getName();


	/**
	 * @return the FrDt
	 */
    public String getFrDt() {
        return FrDt;
    }


	/**
	 * @param frDt the FrDt to be set.
	 * The parameter is validated.
     * pattern copilot: "^\d{4}-\d{2}-\d{2}$".
     * e.g.: "2020-09-08".
	 */
    public void setFrDt(String frDt, JsonValidationExceptionCollector collector) {
        try {
            final String PATTERN = "^\\d{4}-\\d{2}-\\d{2}$";
            boolean patternOK = (frDt != null) && Pattern.matches(PATTERN, frDt);

            if (!patternOK) {
                throw new IllegalArgumentException(
                    "Wrong parameter 'frDt' (" + frDt + ") in " + FULLY_QUALIFIED_CLASSNAME + ".setFrDt()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setFrDt()";
            collector.addError(context, e);

            throw e;
        }

        this.FrDt = frDt;
    }


	/**
	 * @return the ToDt
	 */
    public String getToDt() {
        return ToDt;
    }


	/**
	 * @param toDt the ToDt to be set.
	 * The parameter is validated.
     * pattern copilot: "^\d{4}-\d{2}-\d{2}$".
     * e.g.: "2020-09-08".
	 */
    public void setToDt(String toDt, JsonValidationExceptionCollector collector) {
        try {
            final String PATTERN = "^\\d{4}-\\d{2}-\\d{2}$";
            boolean patternOK = (toDt != null) && Pattern.matches(PATTERN, toDt);

            if (!patternOK) {
                throw new IllegalArgumentException(
                    "Wrong parameter 'toDt' (" + toDt + ") in " + FULLY_QUALIFIED_CLASSNAME + ".setToDt()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setToDt()";
            collector.addError(context, e);

            throw e;
        }

        this.ToDt = toDt;
    }

}

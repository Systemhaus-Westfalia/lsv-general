package org.shw.lsv.ebanking.bac.sv.camt052.Request;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RptgSeq {
    @JsonProperty("EQSeq")
    String EQSeq;

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=RptgSeq.class.getName();


	/**
	 * @return the EQSeq
	 */
    public String getEQSeq() {
        return EQSeq;
    }


	/**
	 * @param eQSeq the EQSeq to be set.
	 * The parameter is validated.
     * pattern copilot: "[0-9a-zA-Z/\\\\-\\?:\\(\\)\\.,'\\+ ]+".
     * e.g.: "1", "abc123", "A-Z/?", "Hello, World!", "1+2-3", "abc/def\\ghi".
	 */
    public void setEQSeq(String eQSeq, JsonValidationExceptionCollector collector) {
        try {
            final String PATTERN = "[0-9a-zA-Z/\\\\-\\?:\\(\\)\\.,'\\+ ]+";
            boolean patternOK = (eQSeq != null) && Pattern.matches(PATTERN, eQSeq);

            if (!patternOK) {
                throw new IllegalArgumentException(
                    "Wrong parameter 'eQSeq' (" + eQSeq + ") in " + FULLY_QUALIFIED_CLASSNAME + ".setEQSeq()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setEQSeq()";
            collector.addError(context, e);

            throw e;
        }

        this.EQSeq = eQSeq;
    }

}

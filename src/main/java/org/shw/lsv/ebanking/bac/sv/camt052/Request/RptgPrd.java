package org.shw.lsv.ebanking.bac.sv.camt052.Request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RptgPrd {
    @JsonProperty("FrToDt")
    FrToDt FrToDt;

    @JsonProperty("Tp")
    String Tp;

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=RptgPrd.class.getName();


    public FrToDt getFrToDt() {
        return FrToDt;

    }


    /**
     * @param frToDt the FrToDt to be set.
     * The parameter is validated: null not allowed.
     */
    public void setFrToDt(FrToDt frToDt, JsonValidationExceptionCollector collector) {
        try {
            if (frToDt == null) {
                throw new IllegalArgumentException(
                    "Wrong parameter 'frToDt' in " + FULLY_QUALIFIED_CLASSNAME + ".setFrToDt()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setFrToDt()";
            collector.addError(context, e);

            throw e;
        }

        this.FrToDt = frToDt;
    }


    public String getTp() {
        return Tp;
    }


    /**
     * @param tp the Tp to be set.
     * The parameter is validated: null not allowed.
     * Matching items to be reported. Available options: ALLL (all elements).
     * example: "ALLL".
     */
    public void setTp(String tp, JsonValidationExceptionCollector collector) {
        try {
            final String PATTERN = "ALLL";
            boolean patternOK = (tp != null && tp.equals(PATTERN));

            if (!patternOK) {
                throw new IllegalArgumentException(
                    "Wrong parameter 'tp' (" + tp + ") in " + FULLY_QUALIFIED_CLASSNAME + ".setTp()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setTp()";
            collector.addError(context, e);

            throw e;
        }

        this.Tp = tp;
    }

}   

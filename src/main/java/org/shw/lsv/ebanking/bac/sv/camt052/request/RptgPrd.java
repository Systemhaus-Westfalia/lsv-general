package org.shw.lsv.ebanking.bac.sv.camt052.request;

import org.shw.lsv.ebanking.bac.sv.handling.RequestParamsCamt052;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RptgPrd {
    @JsonProperty("FrToDt")
    FrToDt frToDt;

    @JsonProperty("Tp")
    String tp;


    public RptgPrd() {
    }


    public RptgPrd(RequestParamsCamt052 params, JsonValidationExceptionCollector collector) {
        try {
            setFrToDt (new FrToDt( params, collector), collector);

            setTp(params.getTp(), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_RPTGPRD_INIT, e);
        }
    }


    public FrToDt getFrToDt() {
        return frToDt;

    }


    /**
     * @param frToDt the FrToDt to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setFrToDt(FrToDt frToDt) {
        if (frToDt == null) {
            throw new IllegalArgumentException("Wrong parameter 'frToDt' in setFrToDt()");
        }
        this.frToDt = frToDt;
    }

    /**
     * @param frToDt the FrToDt to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setFrToDt(FrToDt frToDt, JsonValidationExceptionCollector collector) {
        try {
            setFrToDt(frToDt);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }


    /**
     * @param tp the Tp to be set.<br>
     * The parameter is validated: null not allowed.<br>
     * Matching items to be reported. Available options: ALLL (all elements).<br>
     * Example: "ALLL".
     */
    public void setTp(String tp) {
        boolean patternOK = (tp != null && tp.equals(EBankingConstants.PATTERN_TP));

        if (!patternOK) {
            throw new IllegalArgumentException(
                "Wrong parameter 'tp' (" + tp + ") in setTp()"
            );
        }
        this.tp = tp;
    }

    /**
     * @param tp the Tp to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setTp(String tp, JsonValidationExceptionCollector collector) {
        try {
            setTp(tp);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
            //throw e;
        }
    }

}   

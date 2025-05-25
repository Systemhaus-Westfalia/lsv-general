package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PmtTpInf {

    /* Category purpose
    "Propósito de pago (Payment Information), los valores esperados son: SUPP, SALA, TREA.
    Para planilla (SALA) o cualquier otro propósito, este campo es obligatorio.
    Para proveedores (SUPP) u otro valor sino se indica se tomará como valor por defecto SUPP"
    Segun copilto, hac muchos vlores, pero los bancos utilizan sus propios. */
    @JsonProperty("CtgyPurp")
    CtgyPurp ctgyPurp;

    public PmtTpInf(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setCtgyPurp (new CtgyPurp(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_PMTTPINF_INIT, e);
        }
    }

    /**
     * @return the CtgyPurp object<br>
     */
    public CtgyPurp getCtgyPurp() {
        return ctgyPurp;
    }

    /**
     * @param ctgyPurp the CtgyPurp to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setCtgyPurp(CtgyPurp ctgyPurp) {
        if (ctgyPurp == null) {
            throw new IllegalArgumentException("Wrong parameter 'ctgyPurp' in setCtgyPurp()");
        }
        this.ctgyPurp = ctgyPurp;
    }

    /**
     * @param ctgyPurp the CtgyPurp to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setCtgyPurp(CtgyPurp ctgyPurp, JsonValidationExceptionCollector collector) {
        try {
            setCtgyPurp(ctgyPurp);
        } catch (IllegalArgumentException e) {
            collector.addError("ERROR_NULL_NOT_ALLOWED", e);
            // throw e;
        }
    }

}

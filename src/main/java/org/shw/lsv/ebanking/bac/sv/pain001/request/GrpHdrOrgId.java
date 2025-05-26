package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GrpHdrOrgId {
    @JsonProperty("BICOrBEI")
    String bicOrBEI;

    public GrpHdrOrgId() {}

        public GrpHdrOrgId(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setBicOrBEI(params.getBicOrBEI(), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_ACCTIDOTHR_INIT, e);
        }
    }

    /**
     * @return the BICOrBEI
     */
    public String getBicOrBEI() {
        return bicOrBEI;
    }

    /**
     * @param bicOrBEI the BIC or BEI to be set.<br>
     * The parameter is validated: null not allowed.<br>
     * Pattern: "[A-Z0-9]{8,11}"<br>
     * Example: "DUMMYORDENA" or "BAMCSVSS"
     */
    public void setBicOrBEI(String bicOrBEI) {
        boolean patternOK = (bicOrBEI != null) && bicOrBEI.matches(EBankingConstants.PATTERN_BICORBEI);
        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'bicOrBEI' (" + bicOrBEI + ") in setBicOrBEI()");
        }
        this.bicOrBEI = bicOrBEI;
    }

    /**
     * @param bicOrBEI the BIC or BEI to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     * Pattern: "[A-Z0-9]{8,11}"<br>
     * Example: "DUMMYORDENA" or "BAMCSVSS"
     */
    public void setBicOrBEI(String bicOrBEI, JsonValidationExceptionCollector collector) {
        try {
            setBicOrBEI(bicOrBEI);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_INVALID_BIC_OR_BEI_FORMAT, e);
            //throw e;
        }
    }


}

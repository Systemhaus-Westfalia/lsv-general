package org.shw.lsv.ebanking.bac.sv.pain001.request;

import java.util.ArrayList;
import java.util.List;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Strd {

    @JsonProperty("RfrdDocInf")
    List<RfrdDocInf> rfrdDocInf;  // Referred Document Information.


    public Strd() {}

    public Strd(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            // The schema requires RfrdDocInf to be an array.
            List<RfrdDocInf> docInfList = new ArrayList<>();
            docInfList.add(new RfrdDocInf(params, collector));
            setRfrdDocInf(docInfList, collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_STRD_INIT, e);
        }
    }

    /**
     * @return the list of RfrdDocInf objects<br>
     */
    public List<RfrdDocInf> getRfrdDocInf() {
        return rfrdDocInf;
    }

    /**
     * @param rfrdDocInfList the list of RfrdDocInf to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setRfrdDocInf(List<RfrdDocInf> rfrdDocInfList) {
        if (rfrdDocInfList == null || rfrdDocInfList.isEmpty()) {
            throw new IllegalArgumentException("Wrong parameter 'rfrdDocInf' in setRfrdDocInf()");
        }
        this.rfrdDocInf = rfrdDocInfList;
    }

    /**
     * @param rfrdDocInfList the list of RfrdDocInf to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setRfrdDocInf(List<RfrdDocInf> rfrdDocInfList, JsonValidationExceptionCollector collector) {
        try {
            setRfrdDocInf(rfrdDocInfList);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }


}

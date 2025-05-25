package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DbtrAgt {

    @JsonProperty("FinInstnId")
    FinInstnIdDbtr finInstnIdDbtr;

    public DbtrAgt() {}


    /**
     * Constructor for initializing DbtrAgt with RequestParams and JsonValidationExceptionCollector.
     *
     * @param params    Request parameters containing necessary data.
     * @param collector Collector for validation exceptions.
     */
    public DbtrAgt(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setFinInstnIdDbtr ( new FinInstnIdDbtr( params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_DBTRAGT_INIT, e);
        }
    }

    /**
     * @return the FinInstnIdDbtr object<br>
     */
    public FinInstnIdDbtr getFinInstnIdDbtr() {
        return finInstnIdDbtr;
    }

    /**
     * @param finInstnIdDbtr the FinInstnIdDbtr to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setFinInstnIdDbtr(FinInstnIdDbtr finInstnIdDbtr) {
        if (finInstnIdDbtr == null) {
            throw new IllegalArgumentException("Wrong parameter 'finInstnIdDbtr' in setFinInstnIdDbtr()");
        }
        this.finInstnIdDbtr = finInstnIdDbtr;
    }

    /**
     * @param finInstnIdDbtr the FinInstnIdDbtr to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setFinInstnIdDbtr(FinInstnIdDbtr finInstnIdDbtr, JsonValidationExceptionCollector collector) {
        try {
            setFinInstnIdDbtr(finInstnIdDbtr);
        } catch (IllegalArgumentException e) {
            collector.addError("ERROR_NULL_NOT_ALLOWED", e);
            // throw e;
        }
    }

}

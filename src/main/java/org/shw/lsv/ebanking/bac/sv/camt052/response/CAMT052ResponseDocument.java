package org.shw.lsv.ebanking.bac.sv.camt052.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 */
public class CAMT052ResponseDocument {

    @JsonProperty("BkToCstmrAcctRpt") 
    BkToCstmrAcctRpt BkToCstmrAcctRpt;

        
    public CAMT052ResponseDocument() {
    }


    /**
    * @return the BkToCstmrAcctRpt object<br>
    */
    public BkToCstmrAcctRpt getBkToCstmrAcctRpt() {
        return BkToCstmrAcctRpt;
    }


    /**
     * @param bkToCstmrAcctRpt the BkToCstmrAcctRpt to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setBkToCstmrAcctRpt(BkToCstmrAcctRpt bkToCstmrAcctRpt) {
        if (bkToCstmrAcctRpt == null) {
            throw new IllegalArgumentException("Wrong parameter 'bkToCstmrAcctRpt' in setBkToCstmrAcctRpt()");
        }
        this.BkToCstmrAcctRpt = bkToCstmrAcctRpt;
    }

    /**
     * @param bkToCstmrAcctRpt the BkToCstmrAcctRpt to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setBkToCstmrAcctRpt(BkToCstmrAcctRpt bkToCstmrAcctRpt, JsonValidationExceptionCollector collector) {
        try {
            setBkToCstmrAcctRpt(bkToCstmrAcctRpt);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }

}

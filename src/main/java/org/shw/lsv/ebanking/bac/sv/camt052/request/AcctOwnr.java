package org.shw.lsv.ebanking.bac.sv.camt052.request;

import org.shw.lsv.ebanking.bac.sv.handling.Camt052RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
* Either Pty or Agt
*/
@JsonInclude(JsonInclude.Include.NON_NULL)// Exclude fields with null values
public class AcctOwnr {
    
    @JsonProperty("Pty")
    Pty pty= null;

    @JsonProperty("Agt")
    Agt agt= null;
    

    public AcctOwnr() {}

    public AcctOwnr(Camt052RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            if ( params.getBicfiAcctOwnr() == null || params.getBicfiAcctOwnr().isEmpty() ) {
                setPty(new Pty(params, collector), collector);
            }
            else {
                setAgt(new Agt(params, EBankingConstants.CONTEXT_AGT, collector), collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_ACCT_OWNER_INIT, e);
        }
    }


    public Pty getPty() {
        return pty;
    }



    /**
     * @param pty the Pty object to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setPty(Pty pty) {
        if (pty == null) {
            throw new IllegalArgumentException("Wrong parameter 'pty' in setPty()");
        }
        this.pty = pty;
    }

    /**
     * @param pty the Pty object to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setPty(Pty pty, JsonValidationExceptionCollector collector) {
        try {
            setPty(pty);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }


    /**
	 * @return the Agt object<br>
	 */
    public Agt getAgt() {
        return agt;
    }


    /**
     * @param agt the Agt object to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setAgt(Agt agt) {
        if (agt == null) {
            throw new IllegalArgumentException("Wrong parameter 'agt' in setAgt()");
        }
        this.agt = agt;
    }

    /**
     * @param agt the Agt object to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setAgt(Agt agt, JsonValidationExceptionCollector collector) {
        try {
            setAgt(agt);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }    

}

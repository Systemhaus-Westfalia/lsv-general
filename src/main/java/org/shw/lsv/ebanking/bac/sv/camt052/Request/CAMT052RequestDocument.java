    package org.shw.lsv.ebanking.bac.sv.camt052.Request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 */
public class CAMT052RequestDocument implements Validatable {

    @JsonProperty("AcctRptgReq")  // "AcctRptgReq" is the name of the field in the JSON
    AcctRptgReq AcctRptgReq;

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=CAMT052RequestDocument.class.getName();
    
    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (AcctRptgReq == null) {
                throw new IllegalArgumentException("AcctRptgReq cannot be null");
            }

            // Validate nested objects
            if (AcctRptgReq instanceof Validatable) {
                ((Validatable) AcctRptgReq).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(FULLY_QUALIFIED_CLASSNAME + ".validate()", e);
        }
    }

        
    /**
    * @return the AcctRptgReq object<br>
    */
    public AcctRptgReq getAcctRptgReq() {
        return AcctRptgReq;
    }


    /**
     * @param acctRptgReq the AcctRptgReq to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setAcctRptgReq(AcctRptgReq acctRptgReq, JsonValidationExceptionCollector collector) {
        try {
            if (acctRptgReq == null) {
                throw new IllegalArgumentException(
                    "Wrong parameter 'acctRptgReq' in " + FULLY_QUALIFIED_CLASSNAME + ".setAcctRptgReq()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setAcctRptgReq()";
            collector.addError(context, e);

            throw e;
        }

        this.AcctRptgReq = acctRptgReq;
}

}

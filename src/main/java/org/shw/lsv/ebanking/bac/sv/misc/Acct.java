package     org.shw.lsv.ebanking.bac.sv.misc;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
* This class may be used with or without field "Ccy".
* When "Ccy" is not used, it should be set (=keep) to null.
* When "Ccy" is used, it should be set to a valid value.
*/
public class Acct {

    @JsonProperty("Id")   // "Id" is the name of the field in the JSON
    AcctId AcctId;

    @JsonProperty("Ccy")
    @JsonInclude(JsonInclude.Include.NON_NULL)// Exclude fields with null values
    String Ccy= null;  // ToDo: check actual Ccy values.

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=Acct.class.getName();


    /**
     * @return the AcctId object<br>
     */
    public AcctId getAcctId() {
        return AcctId;
    }
    

    /**
     * @param acctId the AcctId to be set.
	 * The parameter is validated: null not allowed.
     */
    public void setAcctId(AcctId acctId, JsonValidationExceptionCollector collector) {
        try {
            if (acctId == null) {
                throw new IllegalArgumentException(
                    "Wrong parameter 'acctId' in " + FULLY_QUALIFIED_CLASSNAME + ".setAcctId()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setAcctId()";
            collector.addError(context, e);

            throw e;
        }

        this.AcctId = acctId;
    }


    /**
     * @return the Ccy object<br>
     */
    public String getCcy() {
        return Ccy;
    }

    /**
     * @param ccy the Ccy to be set.
     * The parameter is validated: null not allowed.
     */
    public void setCcy(String ccy, JsonValidationExceptionCollector collector) {
        try {
            if ((ccy == null) || (ccy.isEmpty()) ) {
                throw new IllegalArgumentException(
                    "Wrong parameter 'ccy' (" + ccy + ") in " + FULLY_QUALIFIED_CLASSNAME + ".setCcy()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setCcy()";
            collector.addError(context, e);

            throw e;
        }

        // Set the Ccy field
        this.Ccy = ccy;
    }
    
    
}

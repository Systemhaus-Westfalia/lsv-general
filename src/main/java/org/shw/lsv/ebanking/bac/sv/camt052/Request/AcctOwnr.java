package org.shw.lsv.ebanking.bac.sv.camt052.Request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
* Either Pty or Agt
*/
@JsonInclude(JsonInclude.Include.NON_NULL)// Exclude fields with null values
public class AcctOwnr {
    
    @JsonProperty("Pty")
    Pty Pty= null;

    @JsonProperty("Agt")
    Agt Agt= null;

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=AcctOwnr.class.getName();
    

    public Pty getPty() {
        return Pty;
    }


        /**
    * @param pty the Pty object to be set.
    * The parameter is validated: null not allowed.
    */
    public void setPty(Pty pty, JsonValidationExceptionCollector collector) {
        try {
            if (pty == null) {
                throw new IllegalArgumentException(
                    "Wrong parameter 'pty' in " + FULLY_QUALIFIED_CLASSNAME + ".setPty()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setPty()";
            collector.addError(context, e);

            throw e;
        }

        this.Pty = pty;
    }


    /**
	 * @return the Agt object<br>
	 */
    public Agt getAgt() {
        return Agt;
    }


	/**
	 * @param agt the Agt object to be set.
	 * The parameter is validated: null not allowed.
	 */
    public void setAgt(Agt agt, JsonValidationExceptionCollector collector) {
        try {
            if (agt == null) {
                throw new IllegalArgumentException(
                    "Wrong parameter 'agt' in " + FULLY_QUALIFIED_CLASSNAME + ".setAgt()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setAgt()";
            collector.addError(context, e);

            throw e;
        }

        this.Agt = agt;
    }
}

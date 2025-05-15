package org.shw.lsv.ebanking.bac.sv.misc;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AcctIdOthr {
                      
    @JsonProperty(value = "Id", required = true)
    String Id;

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=AcctIdOthr.class.getName();


    /*
     * Constructor with parameters
     * For using the Constructor at deserialization time, it has to be of the form:
     * public AcctIdOthr(@JsonProperty(value = "Id", required = true) String id,.....)
     */
    public AcctIdOthr(String id, JsonValidationExceptionCollector collector) {
        setId(id, collector);
    }


	/**
	 * @return the Id
	 */
	public String getId() {
        return Id;  // Choice Acct_Id_2: Unique identification of an account, as assigned by the account servicer, using an identification scheme.
    }


	/**
	 * @param id the Id to be set.
	 * The parameter is validated.
	 * "minLength" : 1, "maxLength" : 34; null not allowed.
	 * "pattern" : "([0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ]([0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ]*(/[0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ])?)*)".
     * e.g.: "ABNA202009081223".
	 */
    public void setId(String id, JsonValidationExceptionCollector collector) {
        try {
            final int MINLENGTH = 1;
            final int MAXLENGTH = 34;
            final String PATTERN = "([0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ]([0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ]*(/[0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ])?)*)";

            int length = (id == null || id.isEmpty()) ? 0 : id.length();
            boolean patternOK = (id != null) && Pattern.matches(PATTERN, id);

            if (!(length >= MINLENGTH && length <= MAXLENGTH && patternOK)) {
                throw new IllegalArgumentException(
                    "Wrong parameter 'Id' (" + id + ") in " + FULLY_QUALIFIED_CLASSNAME + ".setId()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setId()";
            collector.addError(context, e);

            throw e;
        }

        this.Id = id;
    }
}

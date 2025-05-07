package org.shw.lsv.ebanking.bac.sv.utils;

import java.util.regex.Pattern;

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
    public AcctIdOthr(String id) {
        setId(id);
    }


	/**
	 * @return the Id
	 */
	public String getId() {
        return Id;  // Choice Acct_Id_2: Unique identification of an account, as assigned by the account servicer, using an identification scheme.
    }


	/**
	 * @param id the Id to be set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 1, "maxLength" : 34; null not allowed.<br>
	 * "pattern" : "([0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ]([0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ]*(/[0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ])?)*)"
     * e.g.: "ABNA202009081223"
	 */
    public void setId(String id) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 34;
		int length = (id==null || id.isEmpty())?0:id.length();

		final String PATTERN = "([0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ]([0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ]*(/[0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ])?)*)";
		boolean patternOK = (id!=null) && Pattern.matches(PATTERN, id);
		
		if((length>=MINLENGTH && length<=MAXLENGTH) && patternOK)
			this.Id = id;
		else
	        throw new IllegalArgumentException("Wrong parameter 'Id' (" + id +  ") in " +  FULLY_QUALIFIED_CLASSNAME + ".setId()" + "\n");
    }
}

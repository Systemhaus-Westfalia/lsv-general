package org.shw.lsv.ebanking.bac.sv.utils;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.camt060.Request.SchmeNm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IdOthr {
    @JsonProperty("Id")
    String Id;  // Identification assigned by an institution.

    @JsonProperty("SchmeNm")
	SchmeNm SchmeNm;

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=IdOthr.class.getName();


	/**
	 * @return the Id
	 */
	public String getId() {
        return Id;
    }


	/**
	 * @param id the Id to be set<br>
	 * The parameter is validated.<br>
	 * "pattern" : "[0-9a-zA-Z/\\\\-\\?:\\(\\)\\.,'\\+ ]+"
     * e.g.: "ALIASXXX"
	 */
    public void setId(String id) {
		final String PATTERN = "[0-9a-zA-Z/\\\\-\\?:\\(\\)\\.,'\\+ ]+";
		boolean patternOK = (id!=null && !id.isEmpty()) && Pattern.matches(PATTERN, id);
		
		if(patternOK)
			this.Id = id;
		else
	        throw new IllegalArgumentException("Wrong parameter 'Id' (" + id +  ") in " +  FULLY_QUALIFIED_CLASSNAME + ".setId()" + "\n");
    }


	public SchmeNm getSchmeNm() {
		return SchmeNm;
	}


	/**
	 * @param schmeNm the FIId to be set<br>
	 * The parameter is validated: null not allowed.<br>
	 */
	public void setSchmeNm(SchmeNm schmeNm) {
        if (schmeNm == null ) {
            throw new IllegalArgumentException("Wrong parameter 'fIId' in " +  FULLY_QUALIFIED_CLASSNAME + ".setSchmeNm()" + "\n");
        }
		this.SchmeNm = schmeNm;
	}
}

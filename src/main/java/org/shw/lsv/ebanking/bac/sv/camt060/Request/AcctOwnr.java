package org.shw.lsv.ebanking.bac.sv.camt060.Request;

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
    final String fullyQualifiedClassName=AcctOwnr.class.getName();
    

    public Pty getPty() {
        return Pty;
    }


    public void setPty(Pty pty) {
        if (pty == null ) {
            throw new IllegalArgumentException("Wrong parameter 'pty' in " +  fullyQualifiedClassName + ".setPty()" + "\n");
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
	 * @param agt the Agt object to be set<br>
	 * The parameter is validated: null not allowed.<br>
	 */
    public void setAgt(Agt agt) {
        if (agt == null ) {
            throw new IllegalArgumentException("Wrong parameter 'agt' in " +  fullyQualifiedClassName + ".setAgt()" + "\n");
        }
        this.Agt = agt;
    }
}

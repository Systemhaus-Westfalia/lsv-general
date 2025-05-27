package org.shw.lsv.ebanking.bac.sv.misc;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.camt052.request.SchmeNm;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IdOthr {
    @JsonProperty("Id")
    String id;  // Identification assigned by an institution.

    @JsonProperty("SchmeNm")
	@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude fields with null values
	SchmeNm schmeNm;


	public IdOthr() {
	}


	public IdOthr(RequestParams params, String context, JsonValidationExceptionCollector collector) {
		try {
            if (context.equals(EBankingConstants.CONTEXT_TO)) {
				setId(params.getToOthrId(), collector);
            }  else if (context.equals(EBankingConstants.CONTEXT_DBTR)) {
				setId(params.getDbtrID(), collector);
            } else if (context.equals(EBankingConstants.CONTEXT_DBTRACCT)) {
				setId(params.getDbtrAcctID(), collector);
            }  else {
                throw new IllegalArgumentException("Wrong parameter 'context' (" + context + ") in FinInstnId()");
            }

			setSchmeNm(new SchmeNm(params, collector), collector);
			
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_IDOTHR_INIT, e);
        }
	}


	/**
	 * @return the Id
	 */
	public String getId() {
        return id;
    }

    /**
     * @param id the Id to be set<br>
     * The parameter is validated.<br>
     * Pattern: "[0-9a-zA-Z/\\-?:().,'+ ]{1,35}"<br>
     * Example: "ALIASXXX".
     */
    public void setId(String id) {
        boolean patternOK = (id != null && !id.isEmpty()) && Pattern.matches(EBankingConstants.PATTERN_OTHER_ID, id);

        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'Id' (" + id + ") in setId()");
        }
        this.id = id;
    }

	/**
	 * @param id the Id to be set<br>
	 * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
	 */
	public void setId(String id, JsonValidationExceptionCollector collector) {
		try {
			setId(id);
		} catch (IllegalArgumentException e) {
			collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
			//throw e;
		}
	}


	public SchmeNm getSchmeNm() {
		return schmeNm;
	}


	/**
	 * @param schmeNm the SchmeNm to be set<br>
	 * The parameter is validated: null not allowed.<br>
	 */
	public void setSchmeNm(SchmeNm schmeNm) {
		if (schmeNm == null) {
			throw new IllegalArgumentException("Wrong parameter 'schmeNm' in setSchmeNm()");
		}
		this.schmeNm = schmeNm;
	}

	/**
	 * @param schmeNm the SchmeNm to be set<br>
	 * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
	 */
	public void setSchmeNm(SchmeNm schmeNm, JsonValidationExceptionCollector collector) {
		try {
			setSchmeNm(schmeNm);
		} catch (IllegalArgumentException e) {
			collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
			//throw e;
		}
	}
}

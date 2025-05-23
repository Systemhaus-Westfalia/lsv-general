package org.shw.lsv.ebanking.bac.sv.misc;

import java.util.regex.Pattern;

import org.shw.lsv.ebanking.bac.sv.camt052.request.SchmeNm;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdOthr {
    @JsonProperty("Id")
    String id;  // Identification assigned by an institution.

    @JsonProperty("SchmeNm")
	SchmeNm cchmeNm;


	public IdOthr() {
	}


	public IdOthr(RequestParams params, JsonValidationExceptionCollector collector) {
		
		try {
            setId(params.getId(), collector);

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
	 * Pattern: "[0-9a-zA-Z/\\\\-\\?:\\(\\)\\.,'\\+ ]+".<br>
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


	public SchmeNm getCchmeNm() {
		return cchmeNm;
	}


	/**
	 * @param schmeNm the SchmeNm to be set<br>
	 * The parameter is validated: null not allowed.<br>
	 */
	public void setCchmeNm(SchmeNm schmeNm) {
		if (schmeNm == null) {
			throw new IllegalArgumentException("Wrong parameter 'schmeNm' in setSchmeNm()");
		}
		this.cchmeNm = schmeNm;
	}

	/**
	 * @param schmeNm the SchmeNm to be set<br>
	 * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
	 */
	public void setSchmeNm(SchmeNm schmeNm, JsonValidationExceptionCollector collector) {
		try {
			setCchmeNm(schmeNm);
		} catch (IllegalArgumentException e) {
			collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
			//throw e;
		}
	}
}

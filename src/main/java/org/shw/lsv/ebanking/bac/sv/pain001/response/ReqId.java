package org.shw.lsv.ebanking.bac.sv.pain001.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.regex.Pattern;

public class ReqId {

    @JsonProperty("Id")
    String id;

    @JsonProperty("CreDtTm")
    String creDtTm;

    /**
     * Default constructor.
     */
    public ReqId() {}

    /**
     * Constructor with parameters.
     * Initializes the object using values from RequestParams.
     * @param params    the RequestParams containing the values to set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public ReqId(RequestParams params, JsonValidationExceptionCollector collector) {
        setId(params.getMsgId(), collector);
        setCreDtTm(params.getCreDtTm(), collector);
    }

    /**
     * @return the Id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the Id to be set.<br>
     * The parameter is validated.<br>
     * "minLength" : 1, "maxLength" : 35; null not allowed.<br>
     */
    public void setId(String id) {
        final int MINLENGTH = 1;
        final int MAXLENGTH = 35;
        int length = (id == null || id.isEmpty()) ? 0 : id.length();

        if (length < MINLENGTH || length > MAXLENGTH) {
            throw new IllegalArgumentException(
                "Wrong parameter 'id' (" + id + ") in setId()"
            );
        }
        this.id = id;
    }

    /**
     * @param id the Id to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setId(String id, JsonValidationExceptionCollector collector) {
        try {
            setId(id);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
        }
    }

    /**
     * @return the CreDtTm
     */
    public String getCreDtTm() {
        return creDtTm;
    }

    /**
     * @param creDtTm the CreDtTm to be set.<br>
     * The parameter is validated.<br>
     * "pattern" : EBankingConstants.PATTERN_DATETIME<br>
     * Example: "2020-09-08T18:00:00+02:00".
     */
    public void setCreDtTm(String creDtTm) {
        boolean patternOK = (creDtTm != null) && Pattern.matches(EBankingConstants.PATTERN_DATETIME, creDtTm);

        if (!patternOK) {
            throw new IllegalArgumentException(
                "Wrong parameter 'creDtTm' (" + creDtTm + ") in setCreDtTm()"
            );
        }
        this.creDtTm = creDtTm;
    }

    /**
     * @param creDtTm the CreDtTm to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setCreDtTm(String creDtTm, JsonValidationExceptionCollector collector) {
        try {
            setCreDtTm(creDtTm);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
        }
    }
}
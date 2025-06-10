package org.shw.lsv.ebanking.bac.sv.tmst039.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TMST039ResponseDocument implements Validatable {

    @JsonProperty("xmlns")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String xmlns;

    @JsonProperty("StsRptRsp")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    StsRptRsp stsRptRsp;

    /**
     * Default constructor.
     */
    public TMST039ResponseDocument() {}

    /**
     * Constructor with parameters.
     * Initializes the object using values from RequestParams.
     * @param params    the RequestParams containing the values to set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public TMST039ResponseDocument(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            if (params.getXmlns() != null && !params.getXmlns().isEmpty()) {
                setXmlns(params.getXmlns(), collector);
            }
            setStsRptRsp(new StsRptRsp(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_TMST0039RESPONSE_INIT, e);
        }
    }

    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (stsRptRsp == null) {
                throw new IllegalArgumentException("stsRptRsp cannot be null");
            }
            if (stsRptRsp instanceof Validatable) {
                ((Validatable) stsRptRsp).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    /**
     * @return the xmlns attribute<br>
     */
    public String getXmlns() {
        return xmlns;
    }

    /**
     * @param xmlns the xmlns attribute to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     * Pattern: "^urn:iso:std:iso:20022:tech:xsd:[a-z0-9\\.]+$"<br>
     * Example: "urn:iso:std:iso:20022:tech:xsd:tsmt.039.001.03"
     */
    public void setXmlns(String xmlns, JsonValidationExceptionCollector collector) {
        try {
            setXmlns(xmlns);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
        }
    }

    /**
     * @param xmlns the xmlns attribute to be set.<br>
     * Pattern: "^urn:iso:std:iso:20022:tech:xsd:[a-z0-9\\.]+$"<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setXmlns(String xmlns) {
        String pattern = EBankingConstants.PATTERN_XMLNS;
        boolean patternOK = (xmlns != null) && xmlns.matches(pattern);
        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'xmlns' (" + xmlns + ") in setXmlns()");
        }
        this.xmlns = xmlns;
    }

    /**
     * @return the StsRptRsp object<br>
     */
    public StsRptRsp getStsRptRsp() {
        return stsRptRsp;
    }

    /**
     * @param stsRptRsp the StsRptRsp to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setStsRptRsp(StsRptRsp stsRptRsp) {
        if (stsRptRsp == null) {
            throw new IllegalArgumentException("Wrong parameter 'stsRptRsp' in setStsRptRsp()");
        }
        this.stsRptRsp = stsRptRsp;
    }

    /**
     * @param stsRptRsp the StsRptRsp to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setStsRptRsp(StsRptRsp stsRptRsp, JsonValidationExceptionCollector collector) {
        try {
            setStsRptRsp(stsRptRsp);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}
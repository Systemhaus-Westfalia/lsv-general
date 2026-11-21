package org.shw.lsv.ebanking.bac.sv.pain001.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.misc.Rejection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PAIN001ResponseStatusReportDocument implements Validatable {

    @JsonProperty("xmlns")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String xmlns;

    @JsonProperty("StsRptReq")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    StsRptReq stsRptReq;

    @JsonProperty("admi.002.001.01")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Rejection rejection;

    /**
     * Default constructor.
     */
    public PAIN001ResponseStatusReportDocument() {}

    /**
     * Constructor with parameters.
     * Initializes the object using values from RequestParams.
     * @param params    the RequestParams containing the values to set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public PAIN001ResponseStatusReportDocument(RequestParams params, JsonValidationExceptionCollector collector) {
		if(params.getXmlns() != null && !params.getXmlns().isEmpty()) {
            setXmlns(params.getXmlns(), collector);
    	}
        setStsRptReq(new StsRptReq(params, collector), collector);
    }


    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            // A document must contain either a status report or a rejection.
            boolean hasStsRpt = stsRptReq != null;
            boolean hasRejection = rejection != null;

            if (!hasStsRpt && !hasRejection) {
                throw new IllegalArgumentException("Document must contain either a StsRptReq or a Rejection.");
            }

            // Only validate the StsRptReq if it exists. Rejection has no validation.
            if (hasStsRpt && stsRptReq instanceof Validatable) {
                ((Validatable) stsRptReq).validate(collector);
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
     * <p>
     * Pattern: "^urn:iso:std:iso:20022:tech:xsd:[a-z0-9\\.]+$"<br>
     * <p>
-    * This is a generic pattern, that matches the generic ISO 20022 namespacee, where the last two digits are the version (e.g., 09).
     * <p>
-    * Example: "urn:iso:std:iso:20022:tech:xsd:pain.002.001.09", "urn:iso:std:iso:20022:tech:xsd:tsmt.038.001.03"
     * <p>
     * Example: "urn:iso:std:iso:20022:tech:xsd:pain.002.001.09"
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
     * <p>
     * Pattern: "^urn:iso:std:iso:20022:tech:xsd:[a-z0-9\\.]+$"<br>
     * <p>
-    * This is a generic pattern, that matches the generic ISO 20022 namespacee, where the last two digits are the version (e.g., 09).
     * <p>
-    * Example: "urn:iso:std:iso:20022:tech:xsd:pain.002.001.09", "urn:iso:std:iso:20022:tech:xsd:tsmt.038.001.03"
     * <p>
     * Example: "urn:iso:std:iso:20022:tech:xsd:pain.002.001.09"
     * <p>
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
     * @return the StsRptReq object<br>
     */
    public StsRptReq getStsRptReq() {
        return stsRptReq;
    }

    /**
     * @param stsRptReq the StsRptReq to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setStsRptReq(StsRptReq stsRptReq) {
        if (stsRptReq == null) {
            throw new IllegalArgumentException("Wrong parameter 'stsRptReq' in setStsRptReq()");
        }
        this.stsRptReq = stsRptReq;
    }

    /**
     * @param stsRptReq the StsRptReq to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setStsRptReq(StsRptReq stsRptReq, JsonValidationExceptionCollector collector) {
        try {
            setStsRptReq(stsRptReq);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    /**
    * @return the Rejection object<br>
    */
    public Rejection getRejection() {
        return rejection;
    }

    /**
     * @param rejection the Rejection to be set<br>
     */
    public void setRejection(Rejection rejection) {
        this.rejection = rejection;
    }

    /**
     * @param rejection the Rejection to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setRejection(Rejection rejection, JsonValidationExceptionCollector collector) {
        try {
            setRejection(rejection);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}
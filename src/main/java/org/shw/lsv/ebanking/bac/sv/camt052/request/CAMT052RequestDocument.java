package org.shw.lsv.ebanking.bac.sv.camt052.request;

import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 */
public class CAMT052RequestDocument implements Validatable {

    @JsonProperty("xmlns")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String xmlns;

    @JsonProperty("AcctRptgReq")  // "AcctRptgReq" is the name of the field in the JSON
    AcctRptgReq acctRptgReq;
    
    public CAMT052RequestDocument() {}

    public CAMT052RequestDocument(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            if (params.getXmlns() != null && !params.getXmlns().isEmpty()) {
                setXmlns(params.getXmlns(), collector);
            }
            setAcctRptgReq(new AcctRptgReq(params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_DOCUMENT_INIT, e);
        }
    }


    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (acctRptgReq == null) {
                throw new IllegalArgumentException("AcctRptgReq cannot be null");
            }

            // Validate nested objects
            if (acctRptgReq instanceof Validatable) {
                ((Validatable) acctRptgReq).validate(collector);
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
     * Example: "urn:iso:std:iso:20022:tech:xsd:camt.052.001.08"
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
    * @return the AcctRptgReq object<br>
    */
    public AcctRptgReq getAcctRptgReq() {
        return acctRptgReq;
    }


    /**
     * @param acctRptgReq the AcctRptgReq to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setAcctRptgReq(AcctRptgReq acctRptgReq) {
        if (acctRptgReq == null) {
            throw new IllegalArgumentException("Wrong parameter 'acctRptgReq' in setAcctRptgReq()");
        }
        this.acctRptgReq = acctRptgReq;
    }

    /**
     * @param acctRptgReq the AcctRptgReq to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setAcctRptgReq(AcctRptgReq acctRptgReq, JsonValidationExceptionCollector collector) {
        try {
            setAcctRptgReq(acctRptgReq);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            //throw e;
        }
    }

}

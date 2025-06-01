package org.shw.lsv.ebanking.bac.sv.pain001.response;

import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PAIN001ResponseStatusReportDocument {

    @JsonProperty("xmlns")
    String xmlns;

    @JsonProperty("StsRptReq")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    StsRptReq stsRptReq;

    public PAIN001ResponseStatusReportDocument() {
    }

    /**
     * @return the xmlns attribute<br>
     */
    public String getXmlns() {
        return xmlns;
    }

    /**
     * @param xmlns the xmlns attribute to be set.<br>
     * The parameter is validated: null not allowed.<br>
     * Pattern: "^urn:iso:std:iso:20022:tech:xsd:[a-z0-9\\.]+$"<br>
     * This is a generic pattern, that matches the generic ISO 20022 namespacee, where the last two digits are the version (e.g., 09).
     * Example: "urn:iso:std:iso:20022:tech:xsd:pain.002.001.09", "urn:iso:std:iso:20022:tech:xsd:tsmt.038.001.03"
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
}
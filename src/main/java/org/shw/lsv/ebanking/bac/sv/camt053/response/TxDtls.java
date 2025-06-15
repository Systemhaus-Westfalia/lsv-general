package org.shw.lsv.ebanking.bac.sv.camt053.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.pain001.request.PmtId;

import java.util.regex.Pattern;

public class TxDtls {

    @JsonProperty("Refs")  // Field "Refs" is defined in the parent class "NtryDtls" in the JSON schema.
    PmtId pmtId;           // Class PmtId contains field "EndToEndId".

    @JsonProperty("Amt")
    String amt;

    @JsonProperty("CdtDbtInd")
    String cdtDbtInd;

    public TxDtls() {
    }

    public TxDtls(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setPmtId(new PmtId(params, collector), collector);

            if (params.getNtryDtlsAmt() != null && !params.getNtryDtlsAmt().isEmpty()) {
                setAmt(params.getNtryDtlsAmt(), collector);
            }
            if (params.getCdtDbtInd() != null && !params.getCdtDbtInd().isEmpty()) {
                setCdtDbtInd(params.getCdtDbtInd(), collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_TXDTLS_INIT, e);
        }
    }

    /**
     * @return the PmtId object<br>
     */
    public PmtId getPmtId() {
        return pmtId;
    }

    /**
     * @param pmtId the PmtId to be set.<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setPmtId(PmtId pmtId) {
        if (pmtId == null) {
            throw new IllegalArgumentException("Wrong parameter 'pmtId' in setPmtId()");
        }
        this.pmtId = pmtId;
    }

    /**
     * @param pmtId the PmtId to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setPmtId(PmtId pmtId, JsonValidationExceptionCollector collector) {
        try {
            setPmtId(pmtId);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    /**
     * @return the Amt<br>
     */
    public String getAmt() {
        return amt;
    }

    /**
     * @param amt the Amt to be set<br>
     * <p>
     * Pattern: "\d+(\.\d{1,2})?"; (Allows integers, one decimal, or two decimals)
     * <p>
     * The parameter is validated.<br>
     */
    public void setAmt(String amt) {
        boolean patternOK = (amt != null) && Pattern.matches(EBankingConstants.PATTERN_CURRENCY_AMT, amt);

        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'amt' (" + amt + ") in setAmt()");
        }
        this.amt = amt;
    }

    /**
     * @param amt the Amt to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setAmt(String amt, JsonValidationExceptionCollector collector) {
        try {
            setAmt(amt);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_INVALID_AMT_FORMAT, e);
        }
    }

    /**
     * @return the CdtDbtInd (Credit/Debit Indicator)
     */
    public String getCdtDbtInd() {
        return cdtDbtInd;
    }

    /**
     * @param cdtDbtInd the CdtDbtInd to be set.<br>
     * Pattern: "CRDT|DBIT"<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setCdtDbtInd(String cdtDbtInd) {
        boolean patternOK = (cdtDbtInd != null) && cdtDbtInd.matches(EBankingConstants.PATTERN_CDTDBTIND);
        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'cdtDbtInd' (" + cdtDbtInd + ") in setCdtDbtInd()");
        }
        this.cdtDbtInd = cdtDbtInd;
    }

    /**
     * @param cdtDbtInd the CdtDbtInd to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setCdtDbtInd(String cdtDbtInd, JsonValidationExceptionCollector collector) {
        try {
            setCdtDbtInd(cdtDbtInd);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
        }
    }
}

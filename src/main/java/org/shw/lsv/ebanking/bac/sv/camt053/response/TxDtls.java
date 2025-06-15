package org.shw.lsv.ebanking.bac.sv.camt053.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import java.util.regex.Pattern;

public class TxDtls {

    @JsonProperty("Refs")
    Refs refs;

    @JsonProperty("Amt")
    String amt;

    @JsonProperty("CdtDbtInd")
    String cdtDbtInd;

    public TxDtls() {
    }

    public TxDtls(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setRefs(new Refs(params, collector), collector);

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
     * @return the Refs object<br>
     */
    public Refs getRefs() {
        return refs;
    }

    /**
     * @param refs the Refs to be set.<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setRefs(Refs refs) {
        if (refs == null) {
            throw new IllegalArgumentException("Wrong parameter 'refs' in setRefs()");
        }
        this.refs = refs;
    }

    /**
     * @param refs the Refs to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setRefs(Refs refs, JsonValidationExceptionCollector collector) {
        try {
            setRefs(refs);
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

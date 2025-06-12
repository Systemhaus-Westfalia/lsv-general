package org.shw.lsv.ebanking.bac.sv.camt053.response;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.tmst039.response.Sts;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StmtOfAccountElement {

    @JsonProperty("NtryRef")
    String ntryRef;

    @JsonProperty("Amt")
    PmtElementtAmt pmtElementtAmt;  // Amount and currency

    @JsonProperty("CdtDbtInd")
    String cdtDbtInd;

    @JsonProperty("Sts")
    Sts sts;  // additional information about the type of payment being made.

    @JsonProperty("BookgDt")
    BookgDt bookgDt;

    @JsonProperty("ValDt")
    ValDt valDt;

    @JsonProperty("BkTxCd")
    BkTxCd bkTxCd;

    @JsonProperty("AddtlNtryInf")
    String addtlNtryInf;

    public StmtOfAccountElement() { }

    public StmtOfAccountElement(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setNtryRef(        params.getNtryRef(), collector);
            setPmtElementtAmt( new PmtElementtAmt(params, collector), collector);
            setCdtDbtInd(      params.getCdtDbtInd(), collector);
            setSts(            new Sts(params, collector), collector);
            setBookgDt(        new BookgDt(params, collector), collector);
            setValDt(          new ValDt(params, collector), collector);
            setBkTxCd(         new BkTxCd(params, collector), collector);
            setAddtlNtryInf(   params.getAddtlNtryInf(), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_STMT_OF_ACCT_ELEMENT_INIT, e);
        }
    }

    /**
     * @return the NtryRef (Entry Reference)
     */
    public String getNtryRef() {
        return ntryRef;
    }

    /**
     * @param ntryRef the NtryRef to be set.<br>
     * Pattern: "[0-9A-Za-z/\\-\\?:().,'\\+ ]{1,35}"<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setNtryRef(String ntryRef) {
        boolean patternOK = (ntryRef != null) && ntryRef.matches(EBankingConstants.PATTERN_NTRYREF);
        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'ntryRef' (" + ntryRef + ") in setNtryRef()");
        }
        this.ntryRef = ntryRef;
    }

    /**
     * @param ntryRef the NtryRef to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setNtryRef(String ntryRef, JsonValidationExceptionCollector collector) {
        try {
            setNtryRef(ntryRef);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
        }
    }

    /**
     * @return the PmtElementtAmt object<br>
     */
    public PmtElementtAmt getPmtElementtAmt() {
        return pmtElementtAmt;
    }

    /**
     * @param pmtElementtAmt the PmtElementtAmt to be set.<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setPmtElementtAmt(PmtElementtAmt pmtElementtAmt) {
        if (pmtElementtAmt == null) {
            throw new IllegalArgumentException("Wrong parameter 'pmtElementtAmt' in setPmtElementtAmt()");
        }
        this.pmtElementtAmt = pmtElementtAmt;
    }

    /**
     * @param pmtElementtAmt the PmtElementtAmt to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setPmtElementtAmt(PmtElementtAmt pmtElementtAmt, JsonValidationExceptionCollector collector) {
        try {
            setPmtElementtAmt(pmtElementtAmt);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
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

    /**
     * @return the Sts object<br>
     */
    public Sts getSts() {
        return sts;
    }

    /**
     * @param sts the Sts to be set.<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setSts(Sts sts) {
        if (sts == null) {
            throw new IllegalArgumentException("Wrong parameter 'sts' in setSts()");
        }
        this.sts = sts;
    }

    /**
     * @param sts the Sts to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setSts(Sts sts, JsonValidationExceptionCollector collector) {
        try {
            setSts(sts);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    /**
     * @return the BookgDt object<br>
     */
    public BookgDt getBookgDt() {
        return bookgDt;
    }

    /**
     * @param bookgDt the BookgDt to be set.<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setBookgDt(BookgDt bookgDt) {
        if (bookgDt == null) {
            throw new IllegalArgumentException("Wrong parameter 'bookgDt' in setBookgDt()");
        }
        this.bookgDt = bookgDt;
    }

    /**
     * @param bookgDt the BookgDt to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setBookgDt(BookgDt bookgDt, JsonValidationExceptionCollector collector) {
        try {
            setBookgDt(bookgDt);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    /**
     * @return the ValDt object<br>
     */
    public ValDt getValDt() {
        return valDt;
    }

    /**
     * @param valDt the ValDt to be set.<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setValDt(ValDt valDt) {
        if (valDt == null) {
            throw new IllegalArgumentException("Wrong parameter 'valDt' in setValDt()");
        }
        this.valDt = valDt;
    }

    /**
     * @param valDt the ValDt to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setValDt(ValDt valDt, JsonValidationExceptionCollector collector) {
        try {
            setValDt(valDt);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    /**
     * @return the BkTxCd object<br>
     */
    public BkTxCd getBkTxCd() {
        return bkTxCd;
    }

    /**
     * @param bkTxCd the BkTxCd to be set.<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setBkTxCd(BkTxCd bkTxCd) {
        if (bkTxCd == null) {
            throw new IllegalArgumentException("Wrong parameter 'bkTxCd' in setBkTxCd()");
        }
        this.bkTxCd = bkTxCd;
    }

    /**
     * @param bkTxCd the BkTxCd to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setBkTxCd(BkTxCd bkTxCd, JsonValidationExceptionCollector collector) {
        try {
            setBkTxCd(bkTxCd);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    /**
     * @return the AddtlNtryInf (Additional Entry Information)
     */
    public String getAddtlNtryInf() {
        return addtlNtryInf;
    }

    /**
     * @param addtlNtryInf the AddtlNtryInf to be set.<br>
     * Pattern: "[0-9A-Za-z/\\-\\?:().,'\\+ ]{1,140}"<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setAddtlNtryInf(String addtlNtryInf) {
        boolean patternOK = (addtlNtryInf != null) && addtlNtryInf.matches(EBankingConstants.PATTERN_ADDTLNTRYINF);
        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'addtlNtryInf' (" + addtlNtryInf + ") in setAddtlNtryInf()");
        }
        this.addtlNtryInf = addtlNtryInf;
    }

    /**
     * @param addtlNtryInf the AddtlNtryInf to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setAddtlNtryInf(String addtlNtryInf, JsonValidationExceptionCollector collector) {
        try {
            setAddtlNtryInf(addtlNtryInf);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
        }
    }
}
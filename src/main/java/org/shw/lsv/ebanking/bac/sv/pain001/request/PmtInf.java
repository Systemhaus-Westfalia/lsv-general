package org.shw.lsv.ebanking.bac.sv.pain001.request;

import java.math.BigDecimal;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;

import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PmtInf {
    @JsonProperty("PmtInfId")
    String pmtInfId;

    @JsonProperty("PmtMtd")
    String pmtMtd;

    @JsonProperty("NbOfTxs")
    Integer nbOfTxs;

    @JsonProperty("CtrlSum")
    String ctrlSum;

    @JsonProperty("PmtTpInf")
    PmtTpInf pmtTpInf;

    @JsonProperty("ReqdExctnDt")
    String reqdExctnDt;

    @JsonProperty("Dbtr")
    Dbtr dbtr;

    @JsonProperty("DbtrAcct")
    DbtrAcct dbtrAcct;

    @JsonProperty("DbtrAgt")
    DbtrAgt dbtrAgt;

    @JsonProperty("CdtTrfTxInf")
    CdtTrfTxInf cdtTrfTxInf;


    public PmtInf() { }

    /**
     * @param params    the RequestParams containing the values to set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     * 
     * Constructor with parameters
     * For using the Constructor at deserialization time, it has to be of the form:
     * public PmtInf(@JsonProperty(value = "PmtInfId", required = true) String pmtInfId, ...)
     */
    public PmtInf(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setPmtInfId(params.getMsgId(),     collector);
            setPmtMtd(  params.getMsgDefIdr(), collector);
            setNbOfTxs( params.getNbOfTxs(),   collector);
            setCtrlSum( params.getCtrlSum(),   collector);

            setPmtTpInf (    new PmtTpInf(    params, collector), collector);
            setReqdExctnDt( params.getReqdExctnDt(),   collector);
            setDbtr (        new Dbtr(        params, collector), collector);
            setDbtrAcct (    new DbtrAcct(    params, collector), collector);
            setDbtrAgt (     new DbtrAgt(     params, collector), collector);
            setCdtTrfTxInf ( new CdtTrfTxInf( params, collector), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_PMTINF_INIT, e);
        }
    }

    /**
     * @return the PmtInfId
     */
    public String getPmtInfId() {
        return pmtInfId;
    }

    /**
     * @param pmtInfId the PmtInfId to be set<br>
     * The parameter is validated.<br>
     * "minLength" : 1, "maxLength" : 35; null not allowed.<br>
     */
    public void setPmtInfId(String pmtInfId) {
        final int MINLENGTH = 1;
        final int MAXLENGTH = 35;
        int length = (pmtInfId == null || pmtInfId.isEmpty()) ? 0 : pmtInfId.length();

        if (length < MINLENGTH || length > MAXLENGTH) {
            throw new IllegalArgumentException(
                "Wrong parameter 'pmtInfId' (" + pmtInfId + ") in setPmtInfId()"
            );
        }
        this.pmtInfId = pmtInfId;
    }

    /**
     * @param pmtInfId the PmtInfId to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setPmtInfId(String pmtInfId, JsonValidationExceptionCollector collector) {
        try {
            setPmtInfId(pmtInfId);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_WRONG_LENGTH, e);
            //throw e;
        }
    }


    /**
     * @return the PmtMtd
     */
    public String getPmtMtd() {
        return pmtMtd;
    }

    /**
     * @param pmtMtd the PmtMtd to be set<br>
     * The parameter is validated: null not allowed.<br>
     * And must equal one of the following values: [TRF, CHK]
     * -> TRF = Transaccion, CHK = Cheques"
     */
    public void setPmtMtd(String pmtMtd) {
        if ((pmtMtd == null || pmtMtd.isEmpty()) ||
            !(pmtMtd.equals(EBankingConstants.TRF) || pmtMtd.equals(EBankingConstants.CHK)
            )) {
            throw new IllegalArgumentException(
                "Wrong parameter 'pmtMtd' (" + pmtMtd + ") in setPmtMtd()"
            );
        }
        this.pmtMtd = pmtMtd;
    }

    /**
     * @param pmtMtd the PmtMtd to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setPmtMtd(String pmtMtd, JsonValidationExceptionCollector collector) {
        try {
            setPmtMtd(pmtMtd);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_WRONG_LENGTH, e);
            //throw e;
        }
    }

        
    /**
     * @return the NbOfTxs
     */
    public Integer getNbOfTxs() {
        return nbOfTxs;
    }

    /**
     * @param nbOfTxs the NbOfTxs to be set<br>
     * The parameter is validated: must be positive; null not allowed.<br>
     * Example: 3
     */
    public void setNbOfTxs(Integer nbOfTxs) {
        if (nbOfTxs == null || nbOfTxs <= 0) {
            throw new IllegalArgumentException(
                "Wrong parameter 'nbOfTxs' (" + nbOfTxs + ") in setNbOfTxs(): must be positive and not null"
            );
        }
        this.nbOfTxs = nbOfTxs;
    }

    /**
     * @param nbOfTxs the NbOfTxs to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setNbOfTxs(Integer nbOfTxs, JsonValidationExceptionCollector collector) {
        try {
            setNbOfTxs(nbOfTxs);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_WRONG_LENGTH, e);
            //throw e;
        }
    }

    /**
     * @return the CtrlSum
     */
    public BigDecimal getCtrlSum() {
        if (ctrlSum == null) {
            return null;
        }
        try {
            return new BigDecimal(ctrlSum);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                "Wrong format for 'ctrlSum' (" + ctrlSum + ") in getCtrlSum(): not a valid amount", e
            );
        }
    }

    /**
     * @param ctrlSum the CtrlSum to be set.<br>
     * The parameter is validated: must be non-null, non-negative, and have two decimal places.<br>
     * Example: 123.45
     */
    public void setCtrlSum(BigDecimal ctrlSum) {
        if (ctrlSum == null || ctrlSum.scale() != 2 || ctrlSum.signum() < 0) {
            throw new IllegalArgumentException(
                "Wrong parameter 'ctrlSum' (" + ctrlSum + ") in setCtrlSum(): must be non-null, non-negative, and have two decimal places"
            );
        }
        this.ctrlSum = ctrlSum.toPlainString();
    }

    /**
     * @param ctrlSum the CtrlSum to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setCtrlSum(BigDecimal ctrlSum, JsonValidationExceptionCollector collector) {
        try {
            setCtrlSum(ctrlSum);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_WRONG_LENGTH, e);
            //throw e;
        }
    }

    /**
     * @return the PmtTpInf object<br>
     */
    public PmtTpInf getPmtTpInf() {
        return pmtTpInf;
    }

    /**
     * @param pmtTpInf the PmtTpInf to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setPmtTpInf(PmtTpInf pmtTpInf) {
        if (pmtTpInf == null) {
            throw new IllegalArgumentException("Wrong parameter 'pmtTpInf' in setPmtTpInf()");
        }
        this.pmtTpInf = pmtTpInf;
    }

    /**
     * @param pmtTpInf  the PmtTpInf to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setPmtTpInf(PmtTpInf pmtTpInf, JsonValidationExceptionCollector collector) {
        try {
            setPmtTpInf(pmtTpInf);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }

    /**
     * @return the ReqdExctnDt
     */
    public String getReqdExctnDt() {
        return reqdExctnDt;
    }

    /**
     * @param reqdExctnDt the ReqdExctnDt to be set<br>
     * The parameter is validated: null not allowed.<br>
     * "pattern" : "^\d{4}-\d{2}-\d{2}$"<br>
     * Example: "2023-12-31"
     */
    public void setReqdExctnDt(String reqdExctnDt) {
        boolean patternOK = (reqdExctnDt != null) && 
            java.util.regex.Pattern.matches(EBankingConstants.PATTERN_DATE, reqdExctnDt);

        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'reqdExctnDt' (" + reqdExctnDt + ") in setReqdExctnDt()");
        }
        this.reqdExctnDt = reqdExctnDt;
    }

    /**
     * @param reqdExctnDt the ReqdExctnDt to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setReqdExctnDt(String reqdExctnDt, JsonValidationExceptionCollector collector) {
        try {
            setReqdExctnDt(reqdExctnDt);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
            //throw e;
        }
    }


    /**
     * @return the Dbtr object<br>
     */
    public Dbtr getDbtr() {
        return dbtr;
    }

    /**
     * @param dbtr the Dbtr to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setDbtr(Dbtr dbtr) {
        if (dbtr == null) {
            throw new IllegalArgumentException("Wrong parameter 'dbtr' in setDbtr()");
        }
        this.dbtr = dbtr;
    }

    /**
     * @param dbtr the Dbtr to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setDbtr(Dbtr dbtr, JsonValidationExceptionCollector collector) {
        try {
            setDbtr(dbtr);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }


    /**
     * @return the DbtrAcct object<br>
     */
    public DbtrAcct getDbtrAcct() {
        return dbtrAcct;
    }

    /**
     * @param dbtrAcct the DbtrAcct to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setDbtrAcct(DbtrAcct dbtrAcct) {
        if (dbtrAcct == null) {
            throw new IllegalArgumentException("Wrong parameter 'dbtrAcct' in setDbtrAcct()");
        }
        this.dbtrAcct = dbtrAcct;
    }

    /**
     * @param dbtrAcct the DbtrAcct to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setDbtrAcct(DbtrAcct dbtrAcct, JsonValidationExceptionCollector collector) {
        try {
            setDbtrAcct(dbtrAcct);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }

    /**
     * @return the DbtrAgt object<br>
     */
    public DbtrAgt getDbtrAgt() {
        return dbtrAgt;
    }

    /**
     * @param dbtrAgt the DbtrAgt to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setDbtrAgt(DbtrAgt dbtrAgt) {
        if (dbtrAgt == null) {
            throw new IllegalArgumentException("Wrong parameter 'dbtrAgt' in setDbtrAgt()");
        }
        this.dbtrAgt = dbtrAgt;
    }

    /**
     * @param dbtrAgt the DbtrAgt to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setDbtrAgt(DbtrAgt dbtrAgt, JsonValidationExceptionCollector collector) {
        try {
            setDbtrAgt(dbtrAgt);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }

    /**
     * @return the CdtTrfTxInf object<br>
     */
    public CdtTrfTxInf getCdtTrfTxInf() {
        return cdtTrfTxInf;
    }

    /**
     * @param cdtTrfTxInf the CdtTrfTxInf to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setCdtTrfTxInf(CdtTrfTxInf cdtTrfTxInf) {
        if (cdtTrfTxInf == null) {
            throw new IllegalArgumentException("Wrong parameter 'cdtTrfTxInf' in setCdtTrfTxInf()");
        }
        this.cdtTrfTxInf = cdtTrfTxInf;
    }

    /**
     * @param cdtTrfTxInf the CdtTrfTxInf to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setCdtTrfTxInf(CdtTrfTxInf cdtTrfTxInf, JsonValidationExceptionCollector collector) {
        try {
            setCdtTrfTxInf(cdtTrfTxInf);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }



}

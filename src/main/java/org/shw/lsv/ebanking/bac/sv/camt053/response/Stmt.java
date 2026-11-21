package org.shw.lsv.ebanking.bac.sv.camt053.response;

import java.util.ArrayList;
import java.util.List;

import org.shw.lsv.ebanking.bac.sv.camt052.response.RptPgntn;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.Acct;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Stmt {

    @JsonProperty("Id")
    String id;

    @JsonProperty("StmtPgntn")
    RptPgntn rptPgntn;

    @JsonProperty("ElctrncSeqNb")
    String elctrncSeqNb;

    @JsonProperty("FrToDt")
    FrToDt frToDt;

    @JsonProperty("Acct")
    Acct acct;

    @JsonProperty("Bal")
    List<Bal> balances;

    @JsonProperty("Ntry")
    List<StmtOfAccountElement> stmtOfAccountElements;

    public Stmt() {}

    public Stmt(String id, RptPgntn rptPgntn, String elctrncSeqNb, FrToDt frToDt, Acct acct, List<Bal> balances,
            List<StmtOfAccountElement> stmtOfAccountElements) {
        this.id = id;
        this.rptPgntn = rptPgntn;
        this.elctrncSeqNb = elctrncSeqNb;
        this.frToDt = frToDt;
        this.acct = acct;
        this.balances = balances;
        this.stmtOfAccountElements = stmtOfAccountElements;
    }

    public Stmt(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setId(params.getMsgId(), collector);
            setRptPgntn(new RptPgntn(params, collector), collector);
            setElctrncSeqNb(params.getEqseq(), collector);
            setFrToDt(new FrToDt(params, collector), collector);
            setAcct(new Acct(params, EBankingConstants.CONTEXT_STMT, collector), collector);

            // Initialize balances list
            int balCount = params.getBalCount() != null ? params.getBalCount() : 0;
            List<Bal> balList = new ArrayList<>();
            for (int i = 0; i < balCount; i++) {
                balList.add(new Bal(params, collector));
            }
            setBalances(balList, collector);

            // Initialize statement of account elements list
            int stmtElemCount = params.getStmtElemCount() != null ? params.getStmtElemCount() : 0;
            List<StmtOfAccountElement> stmtElemList = new ArrayList<>();
            for (int i = 0; i < stmtElemCount; i++) {
                stmtElemList.add(new StmtOfAccountElement(params, collector));
            }
            setStmtOfAccountElements(stmtElemList, collector);

        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_STMT_INIT, e);
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
     * The parameter is validated: null not allowed.<br>
     * Pattern: "[0-9A-Za-z/\\-\\?:().,'\\+ ]{1,35}"
     */
    public void setId(String id) {
        boolean patternOK = (id != null) && id.matches(EBankingConstants.PATTERN_NTRYREF);
        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'id' (" + id + ") in setId()");
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
        }
    }

    /**
     * @return the RptPgntn object<br>
     */
    public RptPgntn getRptPgntn() {
        return rptPgntn;
    }

    /**
     * @param rptPgntn the RptPgntn to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setRptPgntn(RptPgntn rptPgntn) {
        if (rptPgntn == null) {
            throw new IllegalArgumentException("Wrong parameter 'rptPgntn' in setRptPgntn()");
        }
        this.rptPgntn = rptPgntn;
    }

    /**
     * @param rptPgntn the RptPgntn to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setRptPgntn(RptPgntn rptPgntn, JsonValidationExceptionCollector collector) {
        try {
            setRptPgntn(rptPgntn);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    /**
     * @return the ElctrncSeqNb
     */
    public String getElctrncSeqNb() {
        return elctrncSeqNb;
    }

    /**
     * @param elctrncSeqNb the ElctrncSeqNb to be set<br>
     * The parameter is validated: null not allowed.<br>
     * Pattern: "[0-9]{1,5}"
     */
    public void setElctrncSeqNb(String elctrncSeqNb) {
        boolean patternOK = (elctrncSeqNb != null) && elctrncSeqNb.matches(EBankingConstants.PATTERN_ELCTRNCSEQNB);
        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'elctrncSeqNb' (" + elctrncSeqNb + ") in setElctrncSeqNb()");
        }
        this.elctrncSeqNb = elctrncSeqNb;
    }

    /**
     * @param elctrncSeqNb the ElctrncSeqNb to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setElctrncSeqNb(String elctrncSeqNb, JsonValidationExceptionCollector collector) {
        try {
            setElctrncSeqNb(elctrncSeqNb);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_PATTERN_MISMATCH, e);
        }
    }

    /**
     * @return the FrToDt object<br>
     */
    public FrToDt getFrToDt() {
        return frToDt;
    }

    /**
     * @param frToDt the FrToDt to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setFrToDt(FrToDt frToDt) {
        if (frToDt == null) {
            throw new IllegalArgumentException("Wrong parameter 'frToDt' in setFrToDt()");
        }
        this.frToDt = frToDt;
    }

    /**
     * @param frToDt the FrToDt to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setFrToDt(FrToDt frToDt, JsonValidationExceptionCollector collector) {
        try {
            setFrToDt(frToDt);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }


    /**
     * @return the Acct object<br>
     */
    public Acct getAcct() {
        return acct;
    }

    /**
     * @param acct the Acct to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setAcct(Acct acct) {
        if (acct == null) {
            throw new IllegalArgumentException("Wrong parameter 'acct' in setAcct()");
        }
        this.acct = acct;
    }

    /**
     * @param acct the Acct to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setAcct(Acct acct, JsonValidationExceptionCollector collector) {
        try {
            setAcct(acct);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    /**
     * @return the list of balances<br>
     */
    public List<Bal> getBalances() {
        return balances;
    }

    /**
     * @param balances the list of Bal to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setBalances(List<Bal> balances) {
        if (balances == null) {
            throw new IllegalArgumentException("Wrong parameter 'balances' in setBalances()");
        }
        this.balances = balances;
    }

    /**
     * @param balances the list of Bal to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setBalances(List<Bal> balances, JsonValidationExceptionCollector collector) {
        try {
            setBalances(balances);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    /**
     * @return the list of StmtOfAccountElement<br>
     */
    public List<StmtOfAccountElement> getStmtOfAccountElements() {
        return stmtOfAccountElements;
    }

    /**
     * @param stmtOfAccountElements the list of StmtOfAccountElement to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setStmtOfAccountElements(List<StmtOfAccountElement> stmtOfAccountElements) {
        if (stmtOfAccountElements == null) {
            throw new IllegalArgumentException("Wrong parameter 'stmtOfAccountElements' in setStmtOfAccountElements()");
        }
        this.stmtOfAccountElements = stmtOfAccountElements;
    }

    /**
     * @param stmtOfAccountElements the list of StmtOfAccountElement to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setStmtOfAccountElements(List<StmtOfAccountElement> stmtOfAccountElements, JsonValidationExceptionCollector collector) {
        try {
            setStmtOfAccountElements(stmtOfAccountElements);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }

    /**
     * Validates the Stmt object.
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (id == null || id.isEmpty()) {
                throw new IllegalArgumentException("Id cannot be null or empty");
            }
            if (rptPgntn == null) {
                throw new IllegalArgumentException("RptPgntn cannot be null");
            }
            if (frToDt == null) {
                throw new IllegalArgumentException("FrToDt cannot be null");
            }
            if (acct == null) {
                throw new IllegalArgumentException("Acct cannot be null");
            }
            if (balances == null) {
                throw new IllegalArgumentException("Balances cannot be null");
            }
            if (stmtOfAccountElements == null) {
                throw new IllegalArgumentException("StmtOfAccountElements cannot be null");
            }
            // Optionally, validate nested objects if they implement a validation interface
            if (rptPgntn instanceof org.shw.lsv.ebanking.bac.sv.handling.Validatable) {
                ((org.shw.lsv.ebanking.bac.sv.handling.Validatable) rptPgntn).validate(collector);
            }
            if (frToDt instanceof org.shw.lsv.ebanking.bac.sv.handling.Validatable) {
                ((org.shw.lsv.ebanking.bac.sv.handling.Validatable) frToDt).validate(collector);
            }
            if (acct instanceof org.shw.lsv.ebanking.bac.sv.handling.Validatable) {
                ((org.shw.lsv.ebanking.bac.sv.handling.Validatable) acct).validate(collector);
            }
            for (Bal bal : balances) {
                if (bal instanceof org.shw.lsv.ebanking.bac.sv.handling.Validatable) {
                    ((org.shw.lsv.ebanking.bac.sv.handling.Validatable) bal).validate(collector);
                }
            }
            for (StmtOfAccountElement elem : stmtOfAccountElements) {
                if (elem instanceof org.shw.lsv.ebanking.bac.sv.handling.Validatable) {
                    ((org.shw.lsv.ebanking.bac.sv.handling.Validatable) elem).validate(collector);
                }
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
        }
    }
}

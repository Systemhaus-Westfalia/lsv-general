package org.shw.lsv.ebanking.bac.sv.handling;

import java.math.BigDecimal;

public class RequestParams {
    private String bicfiFr;
    private String bicfiTo;
    private String bicfiAcctOwnr;
    private String bizMsgIdr;
    private String creDt;
    private String currency;
    private String msgDefIdr;
    private String bizSvc;
    private String anyBIC;
    private String frOthrId;
    private String toOthrId;

    private String prtry;
    private String msgId;
    private String creDtTm;
    private String reqdMsgNmId;
    private String ccy;
    private String iban;
    private String acctId;
    private String tp;
    private String frdt;
    private String todt;
    private String eqseq;
    private String acctOwntPtyId;
    private String pymtPurpose;

    // Payments
    private String     pmtMtd;
    private Integer    nbOfTxs;
    private BigDecimal ctrlSum;
    private String     nameInitParty;
    private String     nameDebtor;
    private String     nameCreditor;
    private String     bicOrBEI;
    private String     reqdExctnDt;
    private String     dbtrAcctId;
    private String     cdtrAcctId;
    private String     dbtrId;
    private String     cdtrId;

    private String     catPurpCd;  // Category Purpose Code
    private String     cdtrAcctCd; // Category Purpose Code
    private String     bic;        // BIC  (Business Identifier Code)
    private String     bicDbtr;    // Debtor BIC  (Business Identifier Code)
    private String     country;    // Country code, e.g. "DE" for Germany

    private String     endToEndId; // End-to-End Identification
    private String     instrPrty;  // Instruction Priority, e.g. "NORM" for Normal
    private String     instdAmt;   // Amount to be transferred in a single payment transaction
    private String     mmbId;
    private String     rmtncInf;


    public String     getBicfiFr()       { return bicfiFr;       }
    public String     getBicfiTo()       { return bicfiTo;       }
    public String     getBicfiAcctOwnr() { return bicfiAcctOwnr; }
    public String     getBizMsgIdr()     { return bizMsgIdr;     }
    public String     getCreDt()         { return creDt;         }
    public String     getCurrency()      { return currency;      }
    public String     getMsgDefIdr()     { return msgDefIdr;     }
    public String     getBizSvc()        { return bizSvc;        }
    public String     getAnyBIC()        { return anyBIC;        }
    public String     getFrOthrId()      { return frOthrId;      }
    public String     getToOthrId()      { return toOthrId;      }
    public String     getPrtry()         { return prtry;         }
    public String     getMsgId()         { return msgId;         }
    public String     getCreDtTm()       { return creDtTm;       }
    public String     getReqdMsgNmId()   { return reqdMsgNmId;   }
    public String     getCcy()           { return ccy;           }
    public String     getIban()          { return iban;          }
    public String     getAcctId()        { return acctId;        }
    public String     getTp()            { return tp;            }
    public String     getFrdt()          { return frdt;          }
    public String     getTodt()          { return todt;          }
    public String     getEqseq()         { return eqseq;         }
    public String     getPmtMtd()        { return pmtMtd;        }
    public Integer    getNbOfTxs()       { return nbOfTxs;       }
    public BigDecimal getCtrlSum()       { return ctrlSum;       }
    public String     getNameInitParty() { return nameInitParty; }
    public String     getNameDebtor()    { return nameDebtor;    }
    public String     getNameCreditor()  { return nameCreditor;  }
    public String     getBicOrBEI()      { return bicOrBEI;      }
    public String     getReqdExctnDt()   { return reqdExctnDt;   }
    public String     getDbtrAcctId()    { return dbtrAcctId;    }
    public String     getCatPurpCd()     { return catPurpCd;     }
    public String     getBic()           { return bic;           }
    public String     getBicDbtr()       { return bicDbtr;       }
    public String     getCountry()       { return country;       }
    public String     getEndToEndId()    { return endToEndId;    }
    public String     getInstrPrty()     { return instrPrty;     }
    public String     getInstdAmt()      { return instdAmt;      }
    public String     getMmbId()         { return mmbId;         }
    public String     getDbtrId()        { return dbtrId;        }
    public String     getAcctOwntPtyId() { return acctOwntPtyId; }
    public String     getCdtrId()        { return cdtrId;        }
    public String     getCdtrAcctId()    { return cdtrAcctId;    }
    public String     getCdtrAcctCd()    { return cdtrAcctCd;    }
    public String     getPymtPurpose()   { return pymtPurpose;   }
    public String     getRmtncInf()      { return rmtncInf;      }



    public RequestParams setBicfiFr(String bicfi) {
        this.bicfiFr = bicfi;
        return this;
    }

    public RequestParams setBicfiTo(String bicfiTo) {
        this.bicfiTo = bicfiTo;
        return this;
    }

    public RequestParams setBicfiAcctOwnr(String bicfiAcctOwnr) {
        this.bicfiAcctOwnr = bicfiAcctOwnr;
        return this;
    }

    public RequestParams setBizMsgIdr(String bizMsgIdr) {
        this.bizMsgIdr = bizMsgIdr;
        return this;
    }

    public RequestParams setCreDt(String creDt) {
        this.creDt = creDt;
        return this;
    }

    public RequestParams setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public RequestParams setMsgDefIdr(String msgDefIdr) {
        this.msgDefIdr = msgDefIdr;
        return this;
    }

    public RequestParams setBizSvc(String bizSvc) {
        this.bizSvc = bizSvc;
        return this;
    }

    public RequestParams setAnyBIC(String anyBIC) {
        this.anyBIC = anyBIC;
        return this;
    }

    public RequestParams setToOthrId(String id) {
        this.toOthrId = id;
        return this;
    }

    public RequestParams setPrtry(String prtry) {
        this.prtry = prtry;
        return this;
    }

    public RequestParams setMsgId(String msgId) {
        this.msgId = msgId;
        return this;
    }

    public RequestParams setCreDtTm(String creDtTm) {
        this.creDtTm = creDtTm;
        return this;
    }

    public RequestParams setReqdMsgNmId(String reqdMsgNmId) {
        this.reqdMsgNmId = reqdMsgNmId;
        return this;
    }

    public RequestParams setCcy(String ccy) {
        this.ccy = ccy;
        return this;
    }

    public RequestParams setIban(String iban) {
        this.iban = iban;
        return this;
    }

    public RequestParams setAcctId(String acctId) {
        this.acctId = acctId;
        return this;
    }

    public RequestParams setTp(String tp) {
        this.tp = tp;
        return this;
    }

    public RequestParams setFrdt(String frdt) {
        this.frdt = frdt;
        return this;
    }

    public RequestParams setTodt(String todt) {
        this.todt = todt;
        return this;
    }


    public RequestParams setEqseq(String eqseq) {
        this.eqseq = eqseq;
        return this;
    }

    public RequestParams setPmtMtd(String pmtMtd) {
        this.pmtMtd = pmtMtd;
        return this;
    }

    public RequestParams setNbOfTxs(Integer nbOfTxs) {
        this.nbOfTxs = nbOfTxs;
        return this;
    }

    public RequestParams setCtrlSum(BigDecimal ctrlSum) {
        this.ctrlSum = ctrlSum;
        return this;
    }

    public RequestParams setNameInitParty(String nm) {
        this.nameInitParty = nm;
        return this;
    }

    public RequestParams setBicOrBEI(String bICOrBEI) {
        this.bicOrBEI = bICOrBEI;
        return this;
    }

    public RequestParams setReqdExctnDt(String reqdExctnDt) {
        this.reqdExctnDt = reqdExctnDt;
        return this;
    }

    public RequestParams setDbtrAcctId(String dbtrAcctId) {
        this.dbtrAcctId = dbtrAcctId;
        return this;
    }

    public RequestParams setCatPurpCd(String catPurpCd) {
        this.catPurpCd = catPurpCd;
        return this;
    }

    public RequestParams setBic(String bic) {
        this.bic = bic;
        return this;
    }

    public RequestParams setCountry(String country) {
        this.country = country;
        return this;
    }

    public RequestParams setEndToEndId(String endToEndId) {
        this.endToEndId = endToEndId;
        return this;
    }

    public RequestParams setInstrPrty(String instrPrty) {
        this.instrPrty = instrPrty;
        return this;
    }

    public RequestParams setInstdAmt(String instdAmt) {
        this.instdAmt = instdAmt;
        return this;
    }

    public RequestParams setBicDbtr(String bicDbtr) {
        this.bicDbtr = bicDbtr;
        return this;
    }

    public RequestParams setMmbId(String mmbId) {
        this.mmbId = mmbId;
        return this;
    }

    public RequestParams setNameDebtor(String nameDebtor) {
        this.nameDebtor = nameDebtor;
        return this;
    }

    public RequestParams setNameCreditor(String nameCreditor) {
        this.nameCreditor = nameCreditor;
        return this;
    }

    public RequestParams setDbtrId(String dbtrId) {
        this.dbtrId = dbtrId;
        return this;
    }

    public RequestParams setFrOthrId(String frOthrId) {
        this.frOthrId = frOthrId;
        return this;
    }

    public RequestParams setAcctOwntPtyId(String acctOwntPtyId) {
        this.acctOwntPtyId = acctOwntPtyId;
        return this;
    }

    public RequestParams setCdtrId(String cdtrId) {
        this.cdtrId = cdtrId;
        return this;
    }

    public RequestParams setCdtrAcctId(String cdtrAcctId) {
        this.cdtrAcctId = cdtrAcctId;
        return this;
    }

    public RequestParams setCdtrAcctCd(String cdtrAcctCd) {
        this.cdtrAcctCd = cdtrAcctCd;
        return this;
    }

    public RequestParams setPymtPurpose(String pymtPurpose) {
        this.pymtPurpose = pymtPurpose;
        return this;
    }

    public RequestParams setRmtncInf(String rmtncInf) {
        this.rmtncInf = rmtncInf;
        return this;
    }


}

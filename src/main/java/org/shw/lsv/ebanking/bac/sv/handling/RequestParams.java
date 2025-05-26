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
    private String id;
    private String prtry;
    private String msgId;
    private String creDtTm;
    private String reqdMsgNmId;
    private String ccy;
    private String iban;
    private String acctidothr;
    private String tp;
    private String frdt;
    private String todt;
    private String eqseq;

    // Payments
    private Integer    nbOfTxs;
    private BigDecimal ctrlSum;
    private String     nm;
    private String     bicOrBEI;
    private String     reqdExctnDt;
    private String     dbtrAcctID;
    private String     cd;         // Category Purpose Code
    private String     bic;        // BIC  (Business Identifier Code)
    private String     bicDbtr;    // Debtor BIC  (Business Identifier Code)
    private String     country;    // Country code, e.g. "DE" for Germany

    private String     endToEndId; // End-to-End Identification
    private String     instrPrty;  // Instruction Priority, e.g. "NORM" for Normal
    private String     instdAmt;   // Amount to be transferred in a single payment transaction
    private String     debtrNm;    // Debtor Name
    private String     mmbId;


    public String     getBicfiFr()       { return bicfiFr;       }
    public String     getBicfiTo()       { return bicfiTo;       }
    public String     getBicfiAcctOwnr() { return bicfiAcctOwnr; }
    public String     getBizMsgIdr()     { return bizMsgIdr;     }
    public String     getCreDt()         { return creDt;         }
    public String     getCurrency()      { return currency;      }
    public String     getMsgDefIdr()     { return msgDefIdr;     }
    public String     getBizSvc()        { return bizSvc;        }
    public String     getAnyBIC()        { return anyBIC;        }
    public String     getId()            { return id;            }
    public String     getPrtry()         { return prtry;         }
    public String     getMsgId()         { return msgId;         }
    public String     getCreDtTm()       { return creDtTm;       }
    public String     getReqdMsgNmId()   { return reqdMsgNmId;   }
    public String     getCcy()           { return ccy;           }
    public String     getIban()          { return iban;          }
    public String     getAcctidothr()    { return acctidothr;    }
    public String     getTp()            { return tp;            }
    public String     getFrdt()          { return frdt;          }
    public String     getTodt()          { return todt;          }
    public String     getEqseq()         { return eqseq;         }
    public Integer    getNbOfTxs()       { return nbOfTxs;       }
    public BigDecimal getCtrlSum()       { return ctrlSum;       }
    public String     getNm()            { return nm;            }
    public String     getBicOrBEI()      { return bicOrBEI;      }
    public String     getReqdExctnDt()   { return reqdExctnDt;   }
    public String     getDbtrAcctID()    { return dbtrAcctID;    }
    public String     getCd()            { return cd;            }
    public String     getBic()           { return bic;           }
    public String     getBicDbtr()       { return bicDbtr;       }
    public String     getCountry()       { return country;       }
    public String     getEndToEndId()    { return endToEndId;    }
    public String     getInstrPrty()     { return instrPrty;     }
    public String     getInstdAmt()      { return instdAmt;      }
    public String     getDebtrNm()       { return debtrNm;       }
    public String     getMmbId()         { return mmbId;         }
    
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

    public RequestParams setId(String id) {
        this.id = id;
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

    public RequestParams setAcctidothr(String acctidothr) {
        this.acctidothr = acctidothr;
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

    public RequestParams setNbOfTxs(Integer nbOfTxs) {
        this.nbOfTxs = nbOfTxs;
        return this;
    }

    public RequestParams setCtrlSum(BigDecimal ctrlSum) {
        this.ctrlSum = ctrlSum;
        return this;
    }

    public RequestParams setNm(String nm) {
        this.nm = nm;
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

    public RequestParams setDbtrAcctID(String dbtrAcctID) {
        this.dbtrAcctID = dbtrAcctID;
        return this;
    }

    public RequestParams setCd(String cd) {
        this.cd = cd;
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

    public RequestParams setDebtrNm(String debtrNm) {
        this.debtrNm = debtrNm;
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

}

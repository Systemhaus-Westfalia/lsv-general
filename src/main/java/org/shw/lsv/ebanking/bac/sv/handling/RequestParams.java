package org.shw.lsv.ebanking.bac.sv.handling;


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
    private String nbOfTxs;
    private String ctrlSum;
    private String nm;
    private String bicOrBEI;


    public String getBicfiFr()       { return bicfiFr;       }
    public String getBicfiTo()       { return bicfiTo;       }
    public String getBicfiAcctOwnr() { return bicfiAcctOwnr; }
    public String getBizMsgIdr()     { return bizMsgIdr;     }
    public String getCreDt()         { return creDt;         }
    public String getCurrency()      { return currency;      }
    public String getMsgDefIdr()     { return msgDefIdr;     }
    public String getBizSvc()        { return bizSvc;        }
    public String getAnyBIC()        { return anyBIC;        }
    public String getId()            { return id;            }
    public String getPrtry()         { return prtry;         }
    public String getMsgId()         { return msgId;         }
    public String getCreDtTm()       { return creDtTm;       }
    public String getReqdMsgNmId()   { return reqdMsgNmId;   }
    public String getCcy()           { return ccy;           }
    public String getIban()          { return iban;          }
    public String getAcctidothr()    { return acctidothr;    }
    public String getTp()            { return tp;            }
    public String getFrdt()          { return frdt;          }
    public String getTodt()          { return todt;          }
    public String getEqseq()         { return eqseq;         }
    public String getNbOfTxs()       { return nbOfTxs;       }
    public String getCtrlSum()       { return ctrlSum;       }
    public String getNm()            { return nm;            }
    public String getBicOrBEI()      { return bicOrBEI;      }

    
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

    public RequestParams setNbOfTxs(String nbOfTxs) {
        this.nbOfTxs = nbOfTxs;
        return this;
    }

    public RequestParams setCtrlSum(String ctrlSum) {
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
}

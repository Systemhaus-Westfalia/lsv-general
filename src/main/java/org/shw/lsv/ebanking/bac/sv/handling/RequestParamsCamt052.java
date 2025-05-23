package org.shw.lsv.ebanking.bac.sv.handling;

import org.shw.lsv.ebanking.bac.sv.camt052.request.CAMT052Request;

public class RequestParamsCamt052 implements RequestParams<CAMT052Request> {
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

    

    public RequestParamsCamt052 setBicfiFr(String bicfi) {
        this.bicfiFr = bicfi;
        return this;
    }

    public RequestParamsCamt052 setBicfiTo(String bicfiTo) {
        this.bicfiTo = bicfiTo;
        return this;
    }

    public RequestParamsCamt052 setBicfiAcctOwnr(String bicfiAcctOwnr) {
        this.bicfiAcctOwnr = bicfiAcctOwnr;
        return this;
    }

    public RequestParamsCamt052 setBizMsgIdr(String bizMsgIdr) {
        this.bizMsgIdr = bizMsgIdr;
        return this;
    }

    public RequestParamsCamt052 setCreDt(String creDt) {
        this.creDt = creDt;
        return this;
    }

    public RequestParamsCamt052 setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public RequestParamsCamt052 setMsgDefIdr(String msgDefIdr) {
        this.msgDefIdr = msgDefIdr;
        return this;
    }

    public RequestParamsCamt052 setBizSvc(String bizSvc) {
        this.bizSvc = bizSvc;
        return this;
    }

    public RequestParamsCamt052 setAnyBIC(String anyBIC) {
        this.anyBIC = anyBIC;
        return this;
    }

    public RequestParamsCamt052 setId(String id) {
        this.id = id;
        return this;
    }

    public RequestParamsCamt052 setPrtry(String prtry) {
        this.prtry = prtry;
        return this;
    }

    public RequestParamsCamt052 setMsgId(String msgId) {
        this.msgId = msgId;
        return this;
    }

    public RequestParamsCamt052 setCreDtTm(String creDtTm) {
        this.creDtTm = creDtTm;
        return this;
    }

    public RequestParamsCamt052 setReqdMsgNmId(String reqdMsgNmId) {
        this.reqdMsgNmId = reqdMsgNmId;
        return this;
    }

    public RequestParamsCamt052 setCcy(String ccy) {
        this.ccy = ccy;
        return this;
    }

    public RequestParamsCamt052 setIban(String iban) {
        this.iban = iban;
        return this;
    }

    public RequestParamsCamt052 setAcctidothr(String acctidothr) {
        this.acctidothr = acctidothr;
        return this;
    }

    public RequestParamsCamt052 setTp(String tp) {
        this.tp = tp;
        return this;
    }

    public RequestParamsCamt052 setFrdt(String frdt) {
        this.frdt = frdt;
        return this;
    }

    public RequestParamsCamt052 setTodt(String todt) {
        this.todt = todt;
        return this;
    }


    public RequestParamsCamt052 setEqseq(String eqseq) {
        this.eqseq = eqseq;
        return this;
    }
}

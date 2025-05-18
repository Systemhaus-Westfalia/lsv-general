package org.shw.lsv.ebanking.bac.sv.handling;


public class Camt052RequestParams {
    // No validation here - just data transfer
    private String bicfi;
    private String bizMsgIdr;
    private String creDt;
    private String currency;
    private String msgDefIdr;
    private String bizSvc;


    // Fluent setters
    public Camt052RequestParams setBicfi(String bicfi) {
        this.bicfi = bicfi;
        return this;
    }

    public Camt052RequestParams setBizMsgIdr(String bizMsgIdr) {
        this.bizMsgIdr = bizMsgIdr;
        return this;
    }

    public Camt052RequestParams setCreDt(String creDt) {
        this.creDt = creDt;
        return this;
    }

    public Camt052RequestParams setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public Camt052RequestParams setMsgDefIdr(String msgDefIdr) {
        this.msgDefIdr = msgDefIdr;
        return this;
    }

    public Camt052RequestParams setBizSvc(String bizSvc) {
        this.bizSvc = bizSvc;
        return this;
    }



    // Getters
    public String getBicfi()     { return bicfi; }
    public String getBizMsgIdr() { return bizMsgIdr; }
    public String getCreDt()     { return creDt; }
    public String getCurrency()  { return currency; }
    public String getMsgDefIdr() { return msgDefIdr; }
    public String getBizSvc()    { return bizSvc; }



}

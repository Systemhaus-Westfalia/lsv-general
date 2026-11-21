package org.shw.lsv.ebanking.bac.sv.tmst039.response;

import org.shw.lsv.ebanking.bac.sv.misc.AppHdr;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TMST039ResponseEnvelope {

    @JsonProperty("AppHdr")
    AppHdr appHdr;

    @JsonProperty("Document")
    TMST039ResponseDocument tMST039ResponseDocument;

    public TMST039ResponseEnvelope() {}

    public AppHdr getAppHdr() {
        return appHdr;
    }

    public void setAppHdr(AppHdr appHdr) {
        if (appHdr == null) {
            throw new IllegalArgumentException("Wrong parameter 'appHdr' in setAppHdr()");
        }
        this.appHdr = appHdr;
    }

    public TMST039ResponseDocument getTMST039ResponseDocument() {
        return tMST039ResponseDocument;
    }

    public void setTMST039ResponseDocument(TMST039ResponseDocument tMST039ResponseDocument) {
        if (tMST039ResponseDocument == null) {
            throw new IllegalArgumentException("Wrong parameter 'tMST039ResponseDocument' in setTMST039ResponseDocument()");
        }
        this.tMST039ResponseDocument = tMST039ResponseDocument;
    }
}
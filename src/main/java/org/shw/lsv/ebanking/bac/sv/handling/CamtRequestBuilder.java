package org.shw.lsv.ebanking.bac.sv.handling;

import org.shw.lsv.ebanking.bac.sv.camt052.request.CAMT052Request;
import org.shw.lsv.ebanking.bac.sv.camt052.request.CAMT052RequestDocument;
import org.shw.lsv.ebanking.bac.sv.camt052.request.CAMT052RequestEnvelope;
import org.shw.lsv.ebanking.bac.sv.misc.AppHdr;
import org.shw.lsv.ebanking.bac.sv.misc.FIId;
import org.shw.lsv.ebanking.bac.sv.misc.FinInstnId;
import org.shw.lsv.ebanking.bac.sv.misc.Fr;

public class CamtRequestBuilder {

    //TODO: Implement the builder pattern for CAMT052Request

    public static CAMT052Request build(Camt052RequestParams params) {
        /* return new CAMT052Request(
            new CAMT052RequestEnvelope(
                buildAppHdr(params),
                buildDocument(params)
            )
        ); */
        return new CAMT052Request();
    }

    private static AppHdr buildAppHdr(Camt052RequestParams params) {
        /* return new AppHdr(
            new Fr(
                new FIId(
                    new FinInstnId(params.getBicfi()) // Validation happens here
                ),
                params.getBizMsgIdr(),
                params.getCreDt()
        ); */
        return new AppHdr();
    }

    private static CAMT052RequestDocument buildDocument(Camt052RequestParams params) {
        /* return new CAMT052RequestDocument(
            new Bal(
                new Amt(
                    params.getCurrency(),
                    params.getAmount()
                )
            )
        ); */
        return new CAMT052RequestDocument();
    }
}
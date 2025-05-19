package org.shw.lsv.ebanking.bac.sv.handling;

import org.shw.lsv.ebanking.bac.sv.camt052.request.CAMT052Request;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;


    // In case error collection wanted.
public class CamtRequestBuilder {
    public static CAMT052Request build(Camt052RequestParams params, JsonValidationExceptionCollector collector)
        throws JsonValidationException {

        try {
            CAMT052Request request = new CAMT052Request(params, collector);

            if (collector.hasErrors()) {
                throw new JsonValidationException(EBankingConstants.ERROR_CAMT052REQUEST_BUILDING);
            }

            return request;
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_CAMT052REQUEST_PARAM, e);
            throw new JsonValidationException(collector.getAllErrors(), e);
        }
    }

    // In case no error collection wanted.
    public static CAMT052Request build(Camt052RequestParams params)
        throws JsonValidationException {
        return build(params, new JsonValidationExceptionCollector());
    }
}
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
                    throw new JsonValidationException(collector, "at: CamtRequestBuilder.build()");
                }
                return request;
            } catch (Exception e) {
                System.err.println("********************************************");
                collector.addError(EBankingConstants.ERROR_CAMT052REQUEST_BUILDING, e);
                throw new JsonValidationException(collector, EBankingConstants.ERROR_CAMT052REQUEST_PARAM, e);
            }
        }
    }
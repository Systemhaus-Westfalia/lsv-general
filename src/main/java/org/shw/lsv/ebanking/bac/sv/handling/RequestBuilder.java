package org.shw.lsv.ebanking.bac.sv.handling;

import org.shw.lsv.ebanking.bac.sv.camt052.request.CAMT052Request;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.pain001.request.PAIN001Request;

/* 
* Generic RequestBuilder class to build request objects based on the provided parameters.
*/
public class RequestBuilder {

    /*
     * Build the Request object based on the parameters provided.
     * Example: PAIN001Request request = RequestBuilder.build(PAIN001Request.class, params, collector);
     */
    public static <T, P extends RequestParams<T>> T build(
            Class<T> requestClass,
            P params,
            JsonValidationExceptionCollector collector)
        throws JsonValidationException {

        try {
            // Find the constructor with (params, collector)
            T request = (T) requestClass
                .getConstructor(params.getClass(), JsonValidationExceptionCollector.class)
                .newInstance(params, collector);

            if (request instanceof Validatable) {
                ((Validatable) request).validate(collector);
            }
            return request;
        } catch (NoSuchMethodException e) {
            collector.addError("Constructor not found for " + requestClass.getName(), e);
            throw new JsonValidationException(collector, "Missing constructor for " + requestClass.getName(), e);
        } catch (Exception e) {
            collector.addError("Build failed at: RequestBuilder.build()", e);
            System.err.println("********************************************");
            collector.addError(EBankingConstants.ERROR_REQUEST_BUILDING, e);
            throw new JsonValidationException(collector, EBankingConstants.ERROR_REQUEST_PARAM, e);
        }
    }


    /**
     * Generic build method for all request types
     * @param params Request parameters (must implement RequestParams<T>)
     * @param collector Error collector
     * @return Fully constructed request object
     * Code example: PAIN001Request request = RequestBuilder.build(params, collector);
     * Zurzeit nicht verwendet, weil die generische Variante bevorzugt wird.
     */
    public static <T, P extends RequestParams<T>> T build(
            P params, 
            JsonValidationExceptionCollector collector) 
        throws JsonValidationException {
        
        try {
            // Dispatch to specialized builders
            if (params instanceof Camt052RequestParams) {
                @SuppressWarnings("unchecked")
                T result = (T) buildCamt052((Camt052RequestParams) params, collector);
                return result;
            } else if (params instanceof PAIN001RequestParams) {
                @SuppressWarnings("unchecked")
                T result = (T) buildPain001((PAIN001RequestParams) params, collector);
                return result;
            } else {
                throw new IllegalArgumentException("Unsupported request type");
            }
        } catch (Exception e) {
            collector.addError("Build failed at: RequestBuilder.build()", e);
                System.err.println("********************************************");
                collector.addError(EBankingConstants.ERROR_REQUEST_BUILDING, e);
                throw new JsonValidationException(collector, EBankingConstants.ERROR_REQUEST_PARAM, e);
        }
    }

    // CAMT052-specific implementation
    private static CAMT052Request buildCamt052(
            Camt052RequestParams params,
            JsonValidationExceptionCollector collector) {
        CAMT052Request request = new CAMT052Request(params, collector);
        if (request instanceof Validatable) {
            ((Validatable) request).validate(collector);
        }
        return request;
    }

    // PAIN001-specific implementation
    private static PAIN001Request buildPain001(
            PAIN001RequestParams params,
            JsonValidationExceptionCollector collector) {
        PAIN001Request request = new PAIN001Request(params, collector);
        if (request instanceof Validatable) {
            ((Validatable) request).validate(collector);
        }
        return request;
    }
}

/* 
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
         */
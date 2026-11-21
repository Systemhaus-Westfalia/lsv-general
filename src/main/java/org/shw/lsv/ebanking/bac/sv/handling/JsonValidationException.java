package org.shw.lsv.ebanking.bac.sv.handling;

/**
 * Custom exception for JSON validation failures
 */
public class JsonValidationException extends Exception {
    private final JsonValidationExceptionCollector collector;

    public JsonValidationException(JsonValidationExceptionCollector collector, String message) {
        super(message);
        this.collector = collector;
    }

    public JsonValidationException(JsonValidationExceptionCollector collector, String message, Throwable cause) {
        super(message, cause);
        this.collector = collector;
    }

    public String getValidationErrors() {
        return collector.getAllErrors();
    }

    public JsonValidationExceptionCollector getCollector() {
        return collector;
    }
}

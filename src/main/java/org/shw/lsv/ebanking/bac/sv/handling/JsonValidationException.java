package org.shw.lsv.ebanking.bac.sv.handling;

/**
 * Custom exception for JSON validation failures
 */
public class JsonValidationException extends RuntimeException {
    private final String validationErrors;
    
    public JsonValidationException(String errors) {
        super("JSON validation failed");
        this.validationErrors = errors;
    }
    
    public JsonValidationException(String errors, Throwable cause) {
        super("JSON validation failed", cause);
        this.validationErrors = errors;
    }
    
    public String getValidationErrors() {
        return validationErrors;
    }
    
    @Override
    public String getMessage() {
        return super.getMessage() + ":\n" + validationErrors;
    }
}

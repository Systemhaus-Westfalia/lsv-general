package org.shw.lsv.ebanking.bac.sv.handling;

public class JsonValidationExceptionCollector {
    private final StringBuffer errorBuffer = new StringBuffer();
    private boolean printImmediately = true;

    public void addError(String context, Exception e) {
        // Using Java 8's DateTimeFormatter for thread safety
        String timestamp = java.time.LocalDateTime.now()
            .format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        
        String errorMessage = String.format("[%s] [%s] %s%n\t%s%n",
            timestamp,
            context,
            e.getMessage(),
            e.getClass().getSimpleName());
        
        errorBuffer.append(errorMessage);
        if (printImmediately) {
            System.err.print(errorMessage);
        }
    }
    
    public String getAllErrors() {
        return errorBuffer.toString();
    }
    
    public boolean hasErrors() {
        return errorBuffer.length() > 0;
    }
    
    public void setPrintImmediately(boolean printImmediately) {
        this.printImmediately = printImmediately;
    }

    /**
     * Clears all collected errors and resets the collector
     */
    public void clear() {
        errorBuffer.setLength(0); // More efficient than creating new StringBuffer
    }

    public void reset() {
        clear();
        printImmediately = true; // Reset to default
    }
}

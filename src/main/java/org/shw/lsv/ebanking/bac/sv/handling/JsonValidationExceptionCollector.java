package org.shw.lsv.ebanking.bac.sv.handling;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JsonValidationExceptionCollector {
    private final StringBuilder errorBuffer = new StringBuilder();
    private boolean printImmediately = true;
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public synchronized void addError(String context, Exception e) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        String errorMessage = String.format("[%s][%s] %s%n", 
                                          timestamp, 
                                          context, 
                                          e.getMessage());
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

    public void clear() {
        errorBuffer.setLength(0);
    }

    public void setPrintImmediately(boolean printImmediately) {
        this.printImmediately = printImmediately;
    }

    public void reset() {
        clear();
        printImmediately = true; // Reset to default
    }
}
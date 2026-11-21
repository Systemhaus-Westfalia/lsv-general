package org.shw.lsv.ebanking.bac.sv.handling;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JsonValidationExceptionCollector {
    private final StringBuilder errorBuffer = new StringBuilder();

    // Soll eine Exception sofort ausgegeben werden?
    // Wenn nicht, es ist Aufgabe des Programms, Abzufragen, ob es Fehler gibt, und sie auszugeben.
    private boolean printImmediately = false;  
    
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
        printImmediately = false; // Reset to default
    }
}
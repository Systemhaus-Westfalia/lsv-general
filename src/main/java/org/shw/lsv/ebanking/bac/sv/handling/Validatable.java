package org.shw.lsv.ebanking.bac.sv.handling;

public interface Validatable {
    void validate(JsonValidationExceptionCollector collector);
}

package org.shw.lsv.ebanking.bac.sv.handling;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonProcessor {
    private final ObjectMapper mapper;
    private final JsonValidationExceptionCollector collector;
    
    public JsonProcessor() {
        this.mapper = new ObjectMapper();
        this.collector = new JsonValidationExceptionCollector();
    }

    // Constructor with dependency injection
    public JsonProcessor(JsonValidationExceptionCollector collector) {
        this.mapper = new ObjectMapper();
        this.collector = collector;
    }
    
    public <T> T deserialize(String json, Class<T> valueType) throws JsonValidationException {
        try {
            collector.reset(); // Reset error collector
            
            // 1. First attempt basic deserialization
            T result = mapper.readValue(json, valueType);
            
            // 2. Run custom validation if object implements Validatable
            if (result instanceof Validatable) {
                ((Validatable) result).validate(collector);
            }
            
            // 3. Check if any errors occurred
            if (collector.hasErrors()) {
                throw new JsonValidationException(collector, "Deserialization validation failed");
            }
            
            return result;
        } catch (Exception e) {
            // Handle both Jackson parsing errors and our validation errors
            collector.addError("Deserialization error", e);
            throw new JsonValidationException(collector, "Deserialization failed", e);
        }
    }
    
    public String serialize(Object value) throws JsonValidationException {
        collector.reset(); // Reset error collector
        
        try {
            // 1. First validate if object implements Validatable
            if (value instanceof Validatable) {
                ((Validatable) value).validate(collector);
            }
            
            // 2. Check for validation errors
            if (collector.hasErrors()) {
                throw new JsonValidationException(collector, "Serialization validation failed");
            }
            
            // 3. Proceed with serialization
            return mapper.writeValueAsString(value);
        } catch (Exception e) {
            collector.addError("Serialization error", e);
            throw new JsonValidationException(collector, "Serialization failed", e);
        }
    }
}
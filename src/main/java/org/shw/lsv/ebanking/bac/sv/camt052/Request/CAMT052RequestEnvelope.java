package org.shw.lsv.ebanking.bac.sv.camt052.Request;

import org.shw.lsv.ebanking.bac.sv.misc.AppHdr;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.Validatable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 */
public class CAMT052RequestEnvelope implements Validatable {
    
    @JsonProperty("AppHdr")
     AppHdr appHdr;
    
     @JsonProperty("Document")    // "Document" is the name of the field in the JSON
     CAMT052RequestDocument CAMT060RequestDocument;

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=CAMT052RequestEnvelope.class.getName();
    
    @Override
    public void validate(JsonValidationExceptionCollector collector) {
        try {
            if (appHdr == null) {
                throw new IllegalArgumentException("AppHdr cannot be null");
            }
            if (CAMT060RequestDocument == null) {
                throw new IllegalArgumentException("CAMT060RequestDocument cannot be null");
            }

            // Validate nested objects
            if (appHdr instanceof Validatable) {
                ((Validatable) appHdr).validate(collector);
            }
            if (CAMT060RequestDocument instanceof Validatable) {
                ((Validatable) CAMT060RequestDocument).validate(collector);
            }
        } catch (Exception e) {
            collector.addError(FULLY_QUALIFIED_CLASSNAME + ".validate()", e);
        }
    }

        
    /**
    * @return the AppHdr object<br>
    */
     public AppHdr getAppHdr() {
         return appHdr;
     }


     /**
      * @param appHdr the AppHdr to be set.
      * The parameter is validated: null not allowed.
      */
     public void setAppHdr(AppHdr appHdr, 
                           JsonValidationExceptionCollector collector) {
        try {
            if (appHdr == null ) {
                throw new IllegalArgumentException("Wrong parameter 'appHdr' in " +  FULLY_QUALIFIED_CLASSNAME + ".setAppHdr()" + "\n");
            }
        } catch (IllegalArgumentException e) {
            String context = FULLY_QUALIFIED_CLASSNAME + ".setAppHdr()";
            collector.addError(context, e);
            throw e;
        }
         this.appHdr = appHdr;
    }


    /**
    * @return the CAMT060RequestDocument object<br>
    */
     public CAMT052RequestDocument getCAMT060RequestDocument() {
         return CAMT060RequestDocument;
     }
     
     /**
     * @param documentCAMT060Request the CAMT060RequestDocument to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setCAMT060RequestDocument(CAMT052RequestDocument documentCAMT060Request, 
                                            JsonValidationExceptionCollector collector) {
        try {
            // Validate the documentCAMT060Request object
            if (documentCAMT060Request == null) {
                throw new IllegalArgumentException(
                "Wrong parameter 'documentCAMT060Request' in " + FULLY_QUALIFIED_CLASSNAME + ".setCAMT060RequestDocument()\n"
                );
            }
        } catch (IllegalArgumentException e) {
            // Add error to the collector
            String context = FULLY_QUALIFIED_CLASSNAME + ".setCAMT060RequestDocument()";
            collector.addError(context, e);

            // Re-throw if you want to stop processing
            throw e;
        }

        // Set the CAMT060RequestDocument field
        this.CAMT060RequestDocument = documentCAMT060Request;
        }

}

package org.shw.lsv.ebanking.bac.sv.camt060.Request;

import org.shw.lsv.ebanking.bac.sv.utils.AppHdr;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 */
public class CAMT060RequestEnvelope {
    
    @JsonProperty("AppHdr")
     AppHdr appHdr;
    
     @JsonProperty("Document")    // "Document" is the name of the field in the JSON
     CAMT060RequestDocument CAMT060RequestDocument;

    @JsonIgnore
    final String fullyQualifiedClassName=CAMT060RequestEnvelope.class.getName();

        
    /**
    * @return the AppHdr object<br>
    */
     public AppHdr getAppHdr() {
         return appHdr;
     }


     /**
      * @param appHdr the AppHdr to be set<br>
      * The parameter is validated: null not allowed.<br>
      */
     public void setAppHdr(AppHdr appHdr) {
        if (appHdr == null ) {
            throw new IllegalArgumentException("Wrong parameter 'appHdr' in " +  fullyQualifiedClassName + ".setAppHdr()" + "\n");
        }
         this.appHdr = appHdr;
     }


    /**
    * @return the CAMT060RequestDocument object<br>
    */
     public CAMT060RequestDocument getCAMT060RequestDocument() {
         return CAMT060RequestDocument;
     }


     /**
      * @param documentCAMT060Request the CAMT060RequestDocument to be set<br>
      * The parameter is validated: null not allowed.<br>
      */
     public void setCAMT060RequestDocument(CAMT060RequestDocument documentCAMT060Request) {
        if (documentCAMT060Request == null ) {
            throw new IllegalArgumentException("Wrong parameter 'documentCAMT060Request' in " +  fullyQualifiedClassName + ".setCAMT060RequestDocument()" + "\n");
        }
         this.CAMT060RequestDocument = documentCAMT060Request;
     }

}

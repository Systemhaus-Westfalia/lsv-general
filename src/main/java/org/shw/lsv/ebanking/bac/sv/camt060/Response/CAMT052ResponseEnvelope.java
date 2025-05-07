package org.shw.lsv.ebanking.bac.sv.camt060.Response;

import org.shw.lsv.ebanking.bac.sv.utils.AppHdr;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 */
public class CAMT052ResponseEnvelope {
    
    @JsonProperty("AppHdr")
     AppHdr appHdr;
    
     @JsonProperty("Document")    // "Document" is the name of the field in the JSON
     CAMT052ResponseDocument CAMT052ResponseDocument;

    @JsonIgnore
    final String FULLY_QUALIFIED_CLASSNAME=CAMT052ResponseEnvelope.class.getName();

        
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
            throw new IllegalArgumentException("Wrong parameter 'appHdr' in " +  FULLY_QUALIFIED_CLASSNAME + ".setAppHdr()" + "\n");
        }
         this.appHdr = appHdr;
     }
     

    /**
    * @return the CAMT060RequestDocument object<br>
    */
     public CAMT052ResponseDocument getCAMT052ResponseDocument() {
         return CAMT052ResponseDocument;
     }


     /**
      * @param documentCAMT052Response the CAMT052ResponseDocument to be set<br>
      * The parameter is validated: null not allowed.<br>
      */
     public void setCAMT052ResponseDocument(CAMT052ResponseDocument documentCAMT052Response) {
        if (documentCAMT052Response == null ) {
            throw new IllegalArgumentException("Wrong parameter 'documentCAMT052Response' in " +  FULLY_QUALIFIED_CLASSNAME + ".setDocumentCAMT060Request()" + "\n");
        }
         this.CAMT052ResponseDocument = documentCAMT052Response;
     }

}

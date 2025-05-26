package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CdtrAgtFinInstnId {

    @JsonProperty("BIC")
    @JsonInclude(JsonInclude.Include.NON_NULL) // Include only if not null
    String bIC;

    @JsonProperty("ClrSysMmbId")
    @JsonInclude(JsonInclude.Include.NON_NULL) // Include only if not null
    ClrSysMmbId clrSysMmbId;

       public CdtrAgtFinInstnId(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            if ( !(params.getBic() == null || params.getBic().isEmpty()) ) {
                setBIC(params.getBic(), collector);
            } else {
                setClrSysMmbId(new ClrSysMmbId(params, collector), collector);
            }
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_CDTRAGT_FININSTNID_INIT, e);
        }
    }

    /**
     * @return the BIC
     */
    public String getBIC() {
        return bIC;
    }

    /**
     * @param bIC the BIC (Bank Identifier Code) to be set.<br>
     * The parameter is validated: null not allowed.<br>
     * Pattern: "[A-Z0-9]{4,4}[A-Z]{2,2}[A-Z0-9]{2,2}([A-Z0-9]{3,3}){0,1}"<br>
     * Example: "BSNJCRSJXXX"
     */
    public void setBIC(String bIC) {
        boolean patternOK = (bIC != null) && bIC.matches(EBankingConstants.PATTERN_BIC);
        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'bIC' (" + bIC + ") in setBIC()");
        }
        this.bIC = bIC;
    }

    /**
     * @param bIC the BIC (Bank Identifier Code) to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setBIC(String bIC, JsonValidationExceptionCollector collector) {
        try {
            setBIC(bIC);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_INVALID_BIC_FORMAT, e);
            //throw e;
        }
    }

    /**
     * @return the ClrSysMmbId object<br>
     */
    public ClrSysMmbId getClrSysMmbId() {
        return clrSysMmbId;
    }

    /**
     * @param clrSysMmbId the ClrSysMmbId to be set<br>
     * The parameter is validated: null not allowed.<br>
     */
    public void setClrSysMmbId(ClrSysMmbId clrSysMmbId) {
        if (clrSysMmbId == null) {
            throw new IllegalArgumentException("Wrong parameter 'clrSysMmbId' in setClrSysMmbId()");
        }
        this.clrSysMmbId = clrSysMmbId;
    }

    /**
     * @param clrSysMmbId the ClrSysMmbId to be set<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setClrSysMmbId(ClrSysMmbId clrSysMmbId, JsonValidationExceptionCollector collector) {
        try {
            setClrSysMmbId(clrSysMmbId);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_NULL_NOT_ALLOWED, e);
            // throw e;
        }
    }


}

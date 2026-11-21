package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClrSysMmbId {

    @JsonProperty("MmbId")
    String mmbId;

        public ClrSysMmbId(RequestParams params, JsonValidationExceptionCollector collector) {
        try {
            setMmbId(params.getCdtrAgtMmmb(), collector);
        } catch (Exception e) {
            collector.addError(EBankingConstants.ERROR_CLRSYSMMBID_INIT, e);
        }
    }

    /**
     * @return the MmbId
     */
    public String getMmbId() {
        return mmbId;
    }

    /**
     * @param mmbId the Member Identification to be set.<br>
     * The parameter is validated: null not allowed.<br>
     * Pattern (BAC onboarding): "[A-Z0-9]{1,35}"<br>
     * Example: "123456789"
     */
    public void setMmbId(String mmbId) {
        boolean patternOK = (mmbId != null) && mmbId.matches(EBankingConstants.PATTERN_MMBID);
        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'mmbId' (" + mmbId + ") in setMmbId()");
        }
        this.mmbId = mmbId;
    }

    /**
     * @param mmbId the Member Identification to be set.<br>
     * @param collector the JsonValidationExceptionCollector to collect validation errors.<br>
     */
    public void setMmbId(String mmbId, JsonValidationExceptionCollector collector) {
        try {
            setMmbId(mmbId);
        } catch (IllegalArgumentException e) {
            collector.addError(EBankingConstants.ERROR_INVALID_MMBID_FORMAT , e);
            //throw e;
        }
    }


}

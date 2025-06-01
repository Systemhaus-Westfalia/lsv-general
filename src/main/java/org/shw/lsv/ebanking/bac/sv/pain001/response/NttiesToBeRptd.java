package org.shw.lsv.ebanking.bac.sv.pain001.response;

import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NttiesToBeRptd {

    @JsonProperty("BIC")
    String bIC;

    public NttiesToBeRptd() {}

        /**
     * @return the BIC (Bank Identifier Code)
     */
    public String getBIC() {
        return bIC;
    }

    /**
     * @param bIC the BIC (Bank Identifier Code) to be set.<br>
     * The parameter is validated: null not allowed.<br>
     * Pattern: "[A-Z0-9]{4,4}[A-Z]{2,2}[A-Z0-9]{2,2}([A-Z0-9]{3,3}){0,1}"<br>
     * Example: "DEUTDEFF" or "DEUTDEFF500"
     */
    public void setBIC(String bIC) {
        boolean patternOK = (bIC != null) && bIC.matches(EBankingConstants.PATTERN_BIC);
        if (!patternOK) {
            throw new IllegalArgumentException("Wrong parameter 'bIC' (" + bIC + ") in setBIC()");
        }
        this.bIC = bIC;
    }

}

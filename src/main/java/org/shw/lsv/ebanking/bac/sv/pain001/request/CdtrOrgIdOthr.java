package org.shw.lsv.ebanking.bac.sv.pain001.request;

import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CdtrOrgIdOthr {

    @JsonProperty("Id")
    String id;

    public  CdtrOrgIdOthr(RequestParams params, JsonValidationExceptionCollector collector) {
        //TODO Auto-generated constructor stub
    }

}

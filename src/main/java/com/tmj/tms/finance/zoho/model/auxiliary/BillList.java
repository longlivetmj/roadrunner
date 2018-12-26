package com.tmj.tms.finance.zoho.model.auxiliary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tmj.tms.finance.zoho.model.Bill;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillList {

    private String code;
    private String message;
    private List<Bill> bills;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }
}

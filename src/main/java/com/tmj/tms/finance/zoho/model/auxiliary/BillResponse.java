package com.tmj.tms.finance.zoho.model.auxiliary;

import com.tmj.tms.finance.zoho.model.Bill;

public class BillResponse {
    private Integer code;
    private String message;
    private Bill bill;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}

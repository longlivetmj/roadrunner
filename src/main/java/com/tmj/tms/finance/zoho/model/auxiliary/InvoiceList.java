package com.tmj.tms.finance.zoho.model.auxiliary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tmj.tms.finance.zoho.model.Invoice;
import com.tmj.tms.finance.zoho.model.Item;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceList {

    private String code;
    private String message;
    private List<Invoice> invoices;

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

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }
}

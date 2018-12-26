package com.tmj.tms.finance.zoho.model.auxiliary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tmj.tms.finance.zoho.model.Contact;
import com.tmj.tms.finance.zoho.model.Item;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemList {

    private String code;
    private String message;
    private List<Item> items;

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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}

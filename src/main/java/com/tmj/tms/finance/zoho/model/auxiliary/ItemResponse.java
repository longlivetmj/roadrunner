package com.tmj.tms.finance.zoho.model.auxiliary;

import com.tmj.tms.finance.zoho.model.Contact;
import com.tmj.tms.finance.zoho.model.Item;

public class ItemResponse {
    private Integer code;
    private String message;
    private Item item;

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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}

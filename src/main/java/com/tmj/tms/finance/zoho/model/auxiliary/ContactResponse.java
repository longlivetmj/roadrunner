package com.tmj.tms.finance.zoho.model.auxiliary;

import com.tmj.tms.finance.zoho.model.Contact;

public class ContactResponse {
    private Integer code;
    private String message;
    private Contact contact;

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

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}

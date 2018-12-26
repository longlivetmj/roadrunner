package com.tmj.tms.finance.zoho.model.auxiliary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tmj.tms.finance.zoho.model.Contact;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactList {

    private String code;
    private String message;
    private List<Contact> contacts;

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

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}

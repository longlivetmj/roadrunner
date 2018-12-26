package com.tmj.tms.finance.zoho.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactPerson {

    private String salutation;

    private String first_name;

    private String last_name;

    private String email;

    private String phone;

    private String mobile;

    private String designation;

    private String department;

    private String skype;

    private Boolean is_primary_contact;

    private Boolean enable_portal;

    public String getSalutation() {
        return this.salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFirst_name() {
        return this.first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return this.last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDesignation() {
        return this.designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSkype() {
        return this.skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public Boolean getIs_primary_contact() {
        return is_primary_contact;
    }

    public void setIs_primary_contact(Boolean is_primary_contact) {
        this.is_primary_contact = is_primary_contact;
    }

    public Boolean getEnable_portal() {
        return enable_portal;
    }

    public void setEnable_portal(Boolean enable_portal) {
        this.enable_portal = enable_portal;
    }
}

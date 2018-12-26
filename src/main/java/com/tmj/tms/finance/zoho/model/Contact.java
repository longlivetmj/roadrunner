package com.tmj.tms.finance.zoho.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact {

    private Long contact_id;

    private String contact_name;

    private String company_name;

    private Boolean has_transaction;

    private String contact_type;

    private Boolean is_taxable;

    private String tax_id;

    private String tax_name;

    private Double tax_percentage;

    private String tax_authority_id;

    private String tax_exemption_id;

    private String place_of_contact;

    private String gst_no;

    private String gst_treatment;

    private Boolean is_linked_with_zohocrm;

    private String website;

    private String primary_contact_id;

    private String payment_terms;

    private String payment_terms_label;

    private String currency_id;

    private String currency_code;

    private String currency_symbol;

    private Double outstanding_receivable_amount;

    private Double outstanding_payable_amount;

    private Double outstanding_receivable_amount_bcy;

    private Double unused_credits_receivable_amount;

    private Double unused_credits_receivable_amount_bcy;

    private String status;

    private String facebook;

    private String twitter;

    private Boolean payment_reminder_enabled;

    private List<CustomField> custom_fields;

    private Address billing_address;

    private Address shipping_address;

    private List<ContactPerson> contact_persons;

    private DefaultTemplate default_templates;

    private String notes;

    private String language_code;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String mobile;

    public Long getContact_id() {
        return contact_id;
    }

    public void setContact_id(Long contact_id) {
        this.contact_id = contact_id;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public Boolean getHas_transaction() {
        return has_transaction;
    }

    public void setHas_transaction(Boolean has_transaction) {
        this.has_transaction = has_transaction;
    }

    public String getContact_type() {
        return contact_type;
    }

    public void setContact_type(String contact_type) {
        this.contact_type = contact_type;
    }

    public Boolean getIs_taxable() {
        return is_taxable;
    }

    public void setIs_taxable(Boolean is_taxable) {
        this.is_taxable = is_taxable;
    }

    public String getTax_id() {
        return tax_id;
    }

    public void setTax_id(String tax_id) {
        this.tax_id = tax_id;
    }

    public String getTax_name() {
        return tax_name;
    }

    public void setTax_name(String tax_name) {
        this.tax_name = tax_name;
    }

    public Double getTax_percentage() {
        return tax_percentage;
    }

    public void setTax_percentage(Double tax_percentage) {
        this.tax_percentage = tax_percentage;
    }

    public String getTax_authority_id() {
        return tax_authority_id;
    }

    public void setTax_authority_id(String tax_authority_id) {
        this.tax_authority_id = tax_authority_id;
    }

    public String getTax_exemption_id() {
        return tax_exemption_id;
    }

    public void setTax_exemption_id(String tax_exemption_id) {
        this.tax_exemption_id = tax_exemption_id;
    }

    public String getPlace_of_contact() {
        return place_of_contact;
    }

    public void setPlace_of_contact(String place_of_contact) {
        this.place_of_contact = place_of_contact;
    }

    public String getGst_no() {
        return gst_no;
    }

    public void setGst_no(String gst_no) {
        this.gst_no = gst_no;
    }

    public String getGst_treatment() {
        return gst_treatment;
    }

    public void setGst_treatment(String gst_treatment) {
        this.gst_treatment = gst_treatment;
    }

    public Boolean getIs_linked_with_zohocrm() {
        return is_linked_with_zohocrm;
    }

    public void setIs_linked_with_zohocrm(Boolean is_linked_with_zohocrm) {
        this.is_linked_with_zohocrm = is_linked_with_zohocrm;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPrimary_contact_id() {
        return primary_contact_id;
    }

    public void setPrimary_contact_id(String primary_contact_id) {
        this.primary_contact_id = primary_contact_id;
    }

    public String getPayment_terms() {
        return payment_terms;
    }

    public void setPayment_terms(String payment_terms) {
        this.payment_terms = payment_terms;
    }

    public String getPayment_terms_label() {
        return payment_terms_label;
    }

    public void setPayment_terms_label(String payment_terms_label) {
        this.payment_terms_label = payment_terms_label;
    }

    public String getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(String currency_id) {
        this.currency_id = currency_id;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public String getCurrency_symbol() {
        return currency_symbol;
    }

    public void setCurrency_symbol(String currency_symbol) {
        this.currency_symbol = currency_symbol;
    }

    public Double getOutstanding_receivable_amount() {
        return outstanding_receivable_amount;
    }

    public void setOutstanding_receivable_amount(Double outstanding_receivable_amount) {
        this.outstanding_receivable_amount = outstanding_receivable_amount;
    }

    public Double getOutstanding_receivable_amount_bcy() {
        return outstanding_receivable_amount_bcy;
    }

    public void setOutstanding_receivable_amount_bcy(Double outstanding_receivable_amount_bcy) {
        this.outstanding_receivable_amount_bcy = outstanding_receivable_amount_bcy;
    }

    public Double getUnused_credits_receivable_amount() {
        return unused_credits_receivable_amount;
    }

    public void setUnused_credits_receivable_amount(Double unused_credits_receivable_amount) {
        this.unused_credits_receivable_amount = unused_credits_receivable_amount;
    }

    public Double getUnused_credits_receivable_amount_bcy() {
        return unused_credits_receivable_amount_bcy;
    }

    public void setUnused_credits_receivable_amount_bcy(Double unused_credits_receivable_amount_bcy) {
        this.unused_credits_receivable_amount_bcy = unused_credits_receivable_amount_bcy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public Boolean getPayment_reminder_enabled() {
        return payment_reminder_enabled;
    }

    public void setPayment_reminder_enabled(Boolean payment_reminder_enabled) {
        this.payment_reminder_enabled = payment_reminder_enabled;
    }

    public List<CustomField> getCustom_fields() {
        return custom_fields;
    }

    public void setCustom_fields(List<CustomField> custom_fields) {
        this.custom_fields = custom_fields;
    }

    public Address getBilling_address() {
        return billing_address;
    }

    public void setBilling_address(Address billing_address) {
        this.billing_address = billing_address;
    }

    public Address getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(Address shipping_address) {
        this.shipping_address = shipping_address;
    }

    public List<ContactPerson> getContact_persons() {
        return contact_persons;
    }

    public void setContact_persons(List<ContactPerson> contact_persons) {
        this.contact_persons = contact_persons;
    }

    public DefaultTemplate getDefault_templates() {
        return default_templates;
    }

    public void setDefault_templates(DefaultTemplate default_templates) {
        this.default_templates = default_templates;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Double getOutstanding_payable_amount() {
        return outstanding_payable_amount;
    }

    public void setOutstanding_payable_amount(Double outstanding_payable_amount) {
        this.outstanding_payable_amount = outstanding_payable_amount;
    }

    public String getLanguage_code() {
        return language_code;
    }

    public void setLanguage_code(String language_code) {
        this.language_code = language_code;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}

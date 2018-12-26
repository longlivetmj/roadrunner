package com.tmj.tms.finance.zoho.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "invoice_id",
        "ach_payment_initiated",
        "invoice_number",
        "is_pre_gst",
        "place_of_supply",
        "gst_no",
        "gst_treatment",
        "date",
        "status",
        "payment_terms",
        "payment_terms_label",
        "due_date",
        "payment_expected_date",
        "last_payment_date",
        "reference_number",
        "customer_id",
        "customer_name",
        "contact_persons",
        "currency_id",
        "currency_code",
        "exchange_rate",
        "discount",
        "is_discount_before_tax",
        "discount_type",
        "is_inclusive_tax",
        "recurring_invoice_id",
        "is_viewed_by_client",
        "has_attachment",
        "client_viewed_time",
        "line_items",
        "shipping_charge",
        "adjustment",
        "adjustment_description",
        "sub_total",
        "tax_total",
        "total",
        "taxes",
        "payment_reminder_enabled",
        "payment_made",
        "credits_applied",
        "tax_amount_withheld",
        "balance",
        "write_off_amount",
        "allow_partial_payments",
        "price_precision",
        "is_emailed",
        "reminders_sent",
        "last_reminder_sent_date",
        "notes",
        "terms",
        "custom_fields",
        "template_id",
        "template_name",
        "created_time",
        "last_modified_time",
        "attachment_name",
        "can_send_in_mail",
        "salesperson_id",
        "salesperson_name",
        "invoice_url"
})
public class Invoice {

    @JsonProperty("invoice_id")
    private Long invoiceId;
    @JsonProperty("ach_payment_initiated")
    private Boolean achPaymentInitiated;
    @JsonProperty("invoice_number")
    private String invoiceNumber;
    @JsonProperty("is_pre_gst")
    private Boolean isPreGst;
    @JsonProperty("place_of_supply")
    private String placeOfSupply;
    @JsonProperty("gst_no")
    private String gstNo;
    @JsonProperty("gst_treatment")
    private String gstTreatment;
    @JsonProperty("date")
    private String date;
    @JsonProperty("status")
    private String status;
    @JsonProperty("payment_terms")
    private Integer paymentTerms;
    @JsonProperty("payment_terms_label")
    private String paymentTermsLabel;
    @JsonProperty("due_date")
    private String dueDate;
    @JsonProperty("payment_expected_date")
    private String paymentExpectedDate;
    @JsonProperty("last_payment_date")
    private String lastPaymentDate;
    @JsonProperty("reference_number")
    private String referenceNumber;
    @JsonProperty("customer_id")
    private Long customerId;
    @JsonProperty("customer_name")
    private String customerName;
    @JsonProperty("contact_persons")
    private List<String> contactPersons = null;
    @JsonProperty("currency_id")
    private Long currencyId;
    @JsonProperty("currency_code")
    private String currencyCode;
    @JsonProperty("exchange_rate")
    private Integer exchangeRate;
    @JsonProperty("discount")
    private Double discount;
    @JsonProperty("is_discount_before_tax")
    private Boolean isDiscountBeforeTax;
    @JsonProperty("discount_type")
    private String discountType;
    @JsonProperty("is_inclusive_tax")
    private Boolean isInclusiveTax;
    @JsonProperty("recurring_invoice_id")
    private String recurringInvoiceId;
    @JsonProperty("is_viewed_by_client")
    private Boolean isViewedByClient;
    @JsonProperty("has_attachment")
    private Boolean hasAttachment;
    @JsonProperty("client_viewed_time")
    private String clientViewedTime;
    @JsonProperty("line_items")
    private List<LineItem> lineItems = null;
    @JsonProperty("shipping_charge")
    private Double shippingCharge;
    @JsonProperty("adjustment")
    private Double adjustment;
    @JsonProperty("adjustment_description")
    private String adjustmentDescription;
    @JsonProperty("sub_total")
    private Double subTotal;
    @JsonProperty("tax_total")
    private Double taxTotal;
    @JsonProperty("total")
    private Double total;
    @JsonProperty("taxes")
    private List<Tax> taxes = null;
    @JsonProperty("payment_reminder_enabled")
    private Boolean paymentReminderEnabled;
    @JsonProperty("payment_made")
    private Double paymentMade;
    @JsonProperty("credits_applied")
    private Double creditsApplied;
    @JsonProperty("tax_amount_withheld")
    private Double taxAmountWithheld;
    @JsonProperty("balance")
    private Double balance;
    @JsonProperty("write_off_amount")
    private Double writeOffAmount;
    @JsonProperty("allow_partial_payments")
    private Boolean allowPartialPayments;
    @JsonProperty("price_precision")
    private Integer pricePrecision;
    @JsonProperty("is_emailed")
    private Boolean isEmailed;
    @JsonProperty("reminders_sent")
    private Integer remindersSent;
    @JsonProperty("last_reminder_sent_date")
    private String lastReminderSentDate;
    @JsonProperty("notes")
    private String notes;
    @JsonProperty("terms")
    private String terms;
    @JsonProperty("custom_fields")
    private List<CustomField> customFields = null;
    @JsonProperty("template_id")
    private Long templateId;
    @JsonProperty("template_name")
    private String templateName;
    @JsonProperty("created_time")
    private String createdTime;
    @JsonProperty("last_modified_time")
    private String lastModifiedTime;
    @JsonProperty("attachment_name")
    private String attachmentName;
    @JsonProperty("can_send_in_mail")
    private Boolean canSendInMail;
    @JsonProperty("salesperson_id")
    private String salespersonId;
    @JsonProperty("salesperson_name")
    private String salespersonName;
    @JsonProperty("invoice_url")
    private String invoiceUrl;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("invoice_id")
    public Long getInvoiceId() {
        return invoiceId;
    }

    @JsonProperty("invoice_id")
    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    @JsonProperty("ach_payment_initiated")
    public Boolean getAchPaymentInitiated() {
        return achPaymentInitiated;
    }

    @JsonProperty("ach_payment_initiated")
    public void setAchPaymentInitiated(Boolean achPaymentInitiated) {
        this.achPaymentInitiated = achPaymentInitiated;
    }

    @JsonProperty("invoice_number")
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    @JsonProperty("invoice_number")
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    @JsonProperty("is_pre_gst")
    public Boolean getIsPreGst() {
        return isPreGst;
    }

    @JsonProperty("is_pre_gst")
    public void setIsPreGst(Boolean isPreGst) {
        this.isPreGst = isPreGst;
    }

    @JsonProperty("place_of_supply")
    public String getPlaceOfSupply() {
        return placeOfSupply;
    }

    @JsonProperty("place_of_supply")
    public void setPlaceOfSupply(String placeOfSupply) {
        this.placeOfSupply = placeOfSupply;
    }

    @JsonProperty("gst_no")
    public String getGstNo() {
        return gstNo;
    }

    @JsonProperty("gst_no")
    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    @JsonProperty("gst_treatment")
    public String getGstTreatment() {
        return gstTreatment;
    }

    @JsonProperty("gst_treatment")
    public void setGstTreatment(String gstTreatment) {
        this.gstTreatment = gstTreatment;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("payment_terms")
    public Integer getPaymentTerms() {
        return paymentTerms;
    }

    @JsonProperty("payment_terms")
    public void setPaymentTerms(Integer paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    @JsonProperty("payment_terms_label")
    public String getPaymentTermsLabel() {
        return paymentTermsLabel;
    }

    @JsonProperty("payment_terms_label")
    public void setPaymentTermsLabel(String paymentTermsLabel) {
        this.paymentTermsLabel = paymentTermsLabel;
    }

    @JsonProperty("due_date")
    public String getDueDate() {
        return dueDate;
    }

    @JsonProperty("due_date")
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    @JsonProperty("payment_expected_date")
    public String getPaymentExpectedDate() {
        return paymentExpectedDate;
    }

    @JsonProperty("payment_expected_date")
    public void setPaymentExpectedDate(String paymentExpectedDate) {
        this.paymentExpectedDate = paymentExpectedDate;
    }

    @JsonProperty("last_payment_date")
    public String getLastPaymentDate() {
        return lastPaymentDate;
    }

    @JsonProperty("last_payment_date")
    public void setLastPaymentDate(String lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    @JsonProperty("reference_number")
    public String getReferenceNumber() {
        return referenceNumber;
    }

    @JsonProperty("reference_number")
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    @JsonProperty("customer_id")
    public Long getCustomerId() {
        return customerId;
    }

    @JsonProperty("customer_id")
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @JsonProperty("customer_name")
    public String getCustomerName() {
        return customerName;
    }

    @JsonProperty("customer_name")
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @JsonProperty("contact_persons")
    public List<String> getContactPersons() {
        return contactPersons;
    }

    @JsonProperty("contact_persons")
    public void setContactPersons(List<String> contactPersons) {
        this.contactPersons = contactPersons;
    }

    @JsonProperty("currency_id")
    public Long getCurrencyId() {
        return currencyId;
    }

    @JsonProperty("currency_id")
    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    @JsonProperty("currency_code")
    public String getCurrencyCode() {
        return currencyCode;
    }

    @JsonProperty("currency_code")
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @JsonProperty("exchange_rate")
    public Integer getExchangeRate() {
        return exchangeRate;
    }

    @JsonProperty("exchange_rate")
    public void setExchangeRate(Integer exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @JsonProperty("discount")
    public Double getDiscount() {
        return discount;
    }

    @JsonProperty("discount")
    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @JsonProperty("is_discount_before_tax")
    public Boolean getIsDiscountBeforeTax() {
        return isDiscountBeforeTax;
    }

    @JsonProperty("is_discount_before_tax")
    public void setIsDiscountBeforeTax(Boolean isDiscountBeforeTax) {
        this.isDiscountBeforeTax = isDiscountBeforeTax;
    }

    @JsonProperty("discount_type")
    public String getDiscountType() {
        return discountType;
    }

    @JsonProperty("discount_type")
    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    @JsonProperty("is_inclusive_tax")
    public Boolean getIsInclusiveTax() {
        return isInclusiveTax;
    }

    @JsonProperty("is_inclusive_tax")
    public void setIsInclusiveTax(Boolean isInclusiveTax) {
        this.isInclusiveTax = isInclusiveTax;
    }

    @JsonProperty("recurring_invoice_id")
    public String getRecurringInvoiceId() {
        return recurringInvoiceId;
    }

    @JsonProperty("recurring_invoice_id")
    public void setRecurringInvoiceId(String recurringInvoiceId) {
        this.recurringInvoiceId = recurringInvoiceId;
    }

    @JsonProperty("is_viewed_by_client")
    public Boolean getIsViewedByClient() {
        return isViewedByClient;
    }

    @JsonProperty("is_viewed_by_client")
    public void setIsViewedByClient(Boolean isViewedByClient) {
        this.isViewedByClient = isViewedByClient;
    }

    @JsonProperty("has_attachment")
    public Boolean getHasAttachment() {
        return hasAttachment;
    }

    @JsonProperty("has_attachment")
    public void setHasAttachment(Boolean hasAttachment) {
        this.hasAttachment = hasAttachment;
    }

    @JsonProperty("client_viewed_time")
    public String getClientViewedTime() {
        return clientViewedTime;
    }

    @JsonProperty("client_viewed_time")
    public void setClientViewedTime(String clientViewedTime) {
        this.clientViewedTime = clientViewedTime;
    }

    @JsonProperty("line_items")
    public List<LineItem> getLineItems() {
        return lineItems;
    }

    @JsonProperty("line_items")
    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    @JsonProperty("shipping_charge")
    public Double getShippingCharge() {
        return shippingCharge;
    }

    @JsonProperty("shipping_charge")
    public void setShippingCharge(Double shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    @JsonProperty("adjustment")
    public Double getAdjustment() {
        return adjustment;
    }

    @JsonProperty("adjustment")
    public void setAdjustment(Double adjustment) {
        this.adjustment = adjustment;
    }

    @JsonProperty("adjustment_description")
    public String getAdjustmentDescription() {
        return adjustmentDescription;
    }

    @JsonProperty("adjustment_description")
    public void setAdjustmentDescription(String adjustmentDescription) {
        this.adjustmentDescription = adjustmentDescription;
    }

    @JsonProperty("sub_total")
    public Double getSubTotal() {
        return subTotal;
    }

    @JsonProperty("sub_total")
    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    @JsonProperty("tax_total")
    public Double getTaxTotal() {
        return taxTotal;
    }

    @JsonProperty("tax_total")
    public void setTaxTotal(Double taxTotal) {
        this.taxTotal = taxTotal;
    }

    @JsonProperty("total")
    public Double getTotal() {
        return total;
    }

    @JsonProperty("total")
    public void setTotal(Double total) {
        this.total = total;
    }

    @JsonProperty("taxes")
    public List<Tax> getTaxes() {
        return taxes;
    }

    @JsonProperty("taxes")
    public void setTaxes(List<Tax> taxes) {
        this.taxes = taxes;
    }

    @JsonProperty("payment_reminder_enabled")
    public Boolean getPaymentReminderEnabled() {
        return paymentReminderEnabled;
    }

    @JsonProperty("payment_reminder_enabled")
    public void setPaymentReminderEnabled(Boolean paymentReminderEnabled) {
        this.paymentReminderEnabled = paymentReminderEnabled;
    }

    @JsonProperty("payment_made")
    public Double getPaymentMade() {
        return paymentMade;
    }

    @JsonProperty("payment_made")
    public void setPaymentMade(Double paymentMade) {
        this.paymentMade = paymentMade;
    }

    @JsonProperty("credits_applied")
    public Double getCreditsApplied() {
        return creditsApplied;
    }

    @JsonProperty("credits_applied")
    public void setCreditsApplied(Double creditsApplied) {
        this.creditsApplied = creditsApplied;
    }

    @JsonProperty("tax_amount_withheld")
    public Double getTaxAmountWithheld() {
        return taxAmountWithheld;
    }

    @JsonProperty("tax_amount_withheld")
    public void setTaxAmountWithheld(Double taxAmountWithheld) {
        this.taxAmountWithheld = taxAmountWithheld;
    }

    @JsonProperty("balance")
    public Double getBalance() {
        return balance;
    }

    @JsonProperty("balance")
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @JsonProperty("write_off_amount")
    public Double getWriteOffAmount() {
        return writeOffAmount;
    }

    @JsonProperty("write_off_amount")
    public void setWriteOffAmount(Double writeOffAmount) {
        this.writeOffAmount = writeOffAmount;
    }

    @JsonProperty("allow_partial_payments")
    public Boolean getAllowPartialPayments() {
        return allowPartialPayments;
    }

    @JsonProperty("allow_partial_payments")
    public void setAllowPartialPayments(Boolean allowPartialPayments) {
        this.allowPartialPayments = allowPartialPayments;
    }

    @JsonProperty("price_precision")
    public Integer getPricePrecision() {
        return pricePrecision;
    }

    @JsonProperty("price_precision")
    public void setPricePrecision(Integer pricePrecision) {
        this.pricePrecision = pricePrecision;
    }

    @JsonProperty("is_emailed")
    public Boolean getIsEmailed() {
        return isEmailed;
    }

    @JsonProperty("is_emailed")
    public void setIsEmailed(Boolean isEmailed) {
        this.isEmailed = isEmailed;
    }

    @JsonProperty("reminders_sent")
    public Integer getRemindersSent() {
        return remindersSent;
    }

    @JsonProperty("reminders_sent")
    public void setRemindersSent(Integer remindersSent) {
        this.remindersSent = remindersSent;
    }

    @JsonProperty("last_reminder_sent_date")
    public String getLastReminderSentDate() {
        return lastReminderSentDate;
    }

    @JsonProperty("last_reminder_sent_date")
    public void setLastReminderSentDate(String lastReminderSentDate) {
        this.lastReminderSentDate = lastReminderSentDate;
    }

    @JsonProperty("notes")
    public String getNotes() {
        return notes;
    }

    @JsonProperty("notes")
    public void setNotes(String notes) {
        this.notes = notes;
    }

    @JsonProperty("terms")
    public String getTerms() {
        return terms;
    }

    @JsonProperty("terms")
    public void setTerms(String terms) {
        this.terms = terms;
    }

    @JsonProperty("custom_fields")
    public List<CustomField> getCustomFields() {
        return customFields;
    }

    @JsonProperty("custom_fields")
    public void setCustomFields(List<CustomField> customFields) {
        this.customFields = customFields;
    }

    @JsonProperty("template_id")
    public Long getTemplateId() {
        return templateId;
    }

    @JsonProperty("template_id")
    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    @JsonProperty("template_name")
    public String getTemplateName() {
        return templateName;
    }

    @JsonProperty("template_name")
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    @JsonProperty("created_time")
    public String getCreatedTime() {
        return createdTime;
    }

    @JsonProperty("created_time")
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    @JsonProperty("last_modified_time")
    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    @JsonProperty("last_modified_time")
    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    @JsonProperty("attachment_name")
    public String getAttachmentName() {
        return attachmentName;
    }

    @JsonProperty("attachment_name")
    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    @JsonProperty("can_send_in_mail")
    public Boolean getCanSendInMail() {
        return canSendInMail;
    }

    @JsonProperty("can_send_in_mail")
    public void setCanSendInMail(Boolean canSendInMail) {
        this.canSendInMail = canSendInMail;
    }

    @JsonProperty("salesperson_id")
    public String getSalespersonId() {
        return salespersonId;
    }

    @JsonProperty("salesperson_id")
    public void setSalespersonId(String salespersonId) {
        this.salespersonId = salespersonId;
    }

    @JsonProperty("salesperson_name")
    public String getSalespersonName() {
        return salespersonName;
    }

    @JsonProperty("salesperson_name")
    public void setSalespersonName(String salespersonName) {
        this.salespersonName = salespersonName;
    }

    @JsonProperty("invoice_url")
    public String getInvoiceUrl() {
        return invoiceUrl;
    }

    @JsonProperty("invoice_url")
    public void setInvoiceUrl(String invoiceUrl) {
        this.invoiceUrl = invoiceUrl;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

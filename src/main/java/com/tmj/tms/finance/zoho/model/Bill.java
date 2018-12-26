package com.tmj.tms.finance.zoho.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "bill_id",
        "purchaseorder_ids",
        "vendor_id",
        "source_of_supply",
        "destination_of_supply",
        "place_of_supply",
        "gst_no",
        "gst_treatment",
        "is_pre_gst",
        "pricebook_id",
        "is_reverse_charge_applied",
        "unused_credits_payable_amount",
        "status",
        "bill_number",
        "date",
        "due_date",
        "payment_terms",
        "payment_terms_label",
        "reference_number",
        "due_in_days",
        "currency_id",
        "currency_code",
        "price_precision",
        "exchange_rate",
        "is_item_level_tax_calc",
        "is_inclusive_tax",
        "line_items",
        "sub_total",
        "tax_total",
        "total",
        "payment_made",
        "vendor_credits_applied",
        "is_line_item_invoiced",
        "purchaseorders",
        "taxes",
        "acquisition_vat_summary",
        "reverse_charge_vat_summary",
        "balance",
        "billing_address",
        "payments",
        "vendor_credits",
        "created_time",
        "created_by_id",
        "last_modified_time",
        "notes",
        "terms",
        "open_purchaseorders_count"
})
public class Bill {

    @JsonProperty("bill_id")
    private Long billId;
    @JsonProperty("purchaseorder_ids")
    private List<Long> purchaseorderIds = null;
    @JsonProperty("vendor_id")
    private Long vendorId;
    @JsonProperty("source_of_supply")
    private String sourceOfSupply;
    @JsonProperty("destination_of_supply")
    private String destinationOfSupply;
    @JsonProperty("place_of_supply")
    private String placeOfSupply;
    @JsonProperty("gst_no")
    private String gstNo;
    @JsonProperty("gst_treatment")
    private String gstTreatment;
    @JsonProperty("is_pre_gst")
    private Boolean isPreGst;
    @JsonProperty("pricebook_id")
    private Long pricebookId;
    @JsonProperty("is_reverse_charge_applied")
    private Boolean isReverseChargeApplied;
    @JsonProperty("unused_credits_payable_amount")
    private Double unusedCreditsPayableAmount;
    @JsonProperty("status")
    private String status;
    @JsonProperty("bill_number")
    private String billNumber;
    @JsonProperty("date")
    private String date;
    @JsonProperty("due_date")
    private String dueDate;
    @JsonProperty("payment_terms")
    private Integer paymentTerms;
    @JsonProperty("payment_terms_label")
    private String paymentTermsLabel;
    @JsonProperty("reference_number")
    private String referenceNumber;
    @JsonProperty("due_in_days")
    private Integer dueInDays;
    @JsonProperty("currency_id")
    private String currencyId;
    @JsonProperty("currency_code")
    private String currencyCode;
    @JsonProperty("price_precision")
    private Integer pricePrecision;
    @JsonProperty("exchange_rate")
    private Integer exchangeRate;
    @JsonProperty("is_item_level_tax_calc")
    private Boolean isItemLevelTaxCalc;
    @JsonProperty("is_inclusive_tax")
    private Boolean isInclusiveTax;
    @JsonProperty("line_items")
    private List<BillLineItem> lineItems = null;
    @JsonProperty("sub_total")
    private Double subTotal;
    @JsonProperty("tax_total")
    private Double taxTotal;
    @JsonProperty("total")
    private Double total;
    @JsonProperty("payment_made")
    private Double paymentMade;
    @JsonProperty("vendor_credits_applied")
    private Integer vendorCreditsApplied;
    @JsonProperty("is_line_item_invoiced")
    private Boolean isLineItemInvoiced;
    @JsonProperty("taxes")
    private List<Tax> taxes = null;
    @JsonProperty("balance")
    private Double balance;
    @JsonProperty("created_time")
    private String createdTime;
    @JsonProperty("created_by_id")
    private String createdById;
    @JsonProperty("last_modified_time")
    private String lastModifiedTime;
    @JsonProperty("notes")
    private String notes;
    @JsonProperty("terms")
    private String terms;
    @JsonProperty("open_purchaseorders_count")
    private Integer openPurchaseordersCount;
    @JsonProperty("billing_address")
    private Address billingAddress;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("bill_id")
    public Long getBillId() {
        return billId;
    }

    @JsonProperty("bill_id")
    public void setBillId(Long billId) {
        this.billId = billId;
    }

    @JsonProperty("purchaseorder_ids")
    public List<Long> getPurchaseorderIds() {
        return purchaseorderIds;
    }

    @JsonProperty("purchaseorder_ids")
    public void setPurchaseorderIds(List<Long> purchaseorderIds) {
        this.purchaseorderIds = purchaseorderIds;
    }

    @JsonProperty("vendor_id")
    public Long getVendorId() {
        return vendorId;
    }

    @JsonProperty("vendor_id")
    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    @JsonProperty("source_of_supply")
    public String getSourceOfSupply() {
        return sourceOfSupply;
    }

    @JsonProperty("source_of_supply")
    public void setSourceOfSupply(String sourceOfSupply) {
        this.sourceOfSupply = sourceOfSupply;
    }

    @JsonProperty("destination_of_supply")
    public String getDestinationOfSupply() {
        return destinationOfSupply;
    }

    @JsonProperty("destination_of_supply")
    public void setDestinationOfSupply(String destinationOfSupply) {
        this.destinationOfSupply = destinationOfSupply;
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

    @JsonProperty("is_pre_gst")
    public Boolean getIsPreGst() {
        return isPreGst;
    }

    @JsonProperty("is_pre_gst")
    public void setIsPreGst(Boolean isPreGst) {
        this.isPreGst = isPreGst;
    }

    @JsonProperty("pricebook_id")
    public Long getPricebookId() {
        return pricebookId;
    }

    @JsonProperty("pricebook_id")
    public void setPricebookId(Long pricebookId) {
        this.pricebookId = pricebookId;
    }

    @JsonProperty("is_reverse_charge_applied")
    public Boolean getIsReverseChargeApplied() {
        return isReverseChargeApplied;
    }

    @JsonProperty("is_reverse_charge_applied")
    public void setIsReverseChargeApplied(Boolean isReverseChargeApplied) {
        this.isReverseChargeApplied = isReverseChargeApplied;
    }

    @JsonProperty("unused_credits_payable_amount")
    public Double getUnusedCreditsPayableAmount() {
        return unusedCreditsPayableAmount;
    }

    @JsonProperty("unused_credits_payable_amount")
    public void setUnusedCreditsPayableAmount(Double unusedCreditsPayableAmount) {
        this.unusedCreditsPayableAmount = unusedCreditsPayableAmount;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("bill_number")
    public String getBillNumber() {
        return billNumber;
    }

    @JsonProperty("bill_number")
    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    @JsonProperty("due_date")
    public String getDueDate() {
        return dueDate;
    }

    @JsonProperty("due_date")
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
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

    @JsonProperty("reference_number")
    public String getReferenceNumber() {
        return referenceNumber;
    }

    @JsonProperty("reference_number")
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    @JsonProperty("due_in_days")
    public Integer getDueInDays() {
        return dueInDays;
    }

    @JsonProperty("due_in_days")
    public void setDueInDays(Integer dueInDays) {
        this.dueInDays = dueInDays;
    }

    @JsonProperty("currency_id")
    public String getCurrencyId() {
        return currencyId;
    }

    @JsonProperty("currency_id")
    public void setCurrencyId(String currencyId) {
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

    @JsonProperty("price_precision")
    public Integer getPricePrecision() {
        return pricePrecision;
    }

    @JsonProperty("price_precision")
    public void setPricePrecision(Integer pricePrecision) {
        this.pricePrecision = pricePrecision;
    }

    @JsonProperty("exchange_rate")
    public Integer getExchangeRate() {
        return exchangeRate;
    }

    @JsonProperty("exchange_rate")
    public void setExchangeRate(Integer exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @JsonProperty("is_item_level_tax_calc")
    public Boolean getIsItemLevelTaxCalc() {
        return isItemLevelTaxCalc;
    }

    @JsonProperty("is_item_level_tax_calc")
    public void setIsItemLevelTaxCalc(Boolean isItemLevelTaxCalc) {
        this.isItemLevelTaxCalc = isItemLevelTaxCalc;
    }

    @JsonProperty("is_inclusive_tax")
    public Boolean getIsInclusiveTax() {
        return isInclusiveTax;
    }

    @JsonProperty("is_inclusive_tax")
    public void setIsInclusiveTax(Boolean isInclusiveTax) {
        this.isInclusiveTax = isInclusiveTax;
    }

    @JsonProperty("line_items")
    public List<BillLineItem> getLineItems() {
        return lineItems;
    }

    @JsonProperty("line_items")
    public void setLineItems(List<BillLineItem> lineItems) {
        this.lineItems = lineItems;
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

    @JsonProperty("payment_made")
    public Double getPaymentMade() {
        return paymentMade;
    }

    @JsonProperty("payment_made")
    public void setPaymentMade(Double paymentMade) {
        this.paymentMade = paymentMade;
    }

    @JsonProperty("vendor_credits_applied")
    public Integer getVendorCreditsApplied() {
        return vendorCreditsApplied;
    }

    @JsonProperty("vendor_credits_applied")
    public void setVendorCreditsApplied(Integer vendorCreditsApplied) {
        this.vendorCreditsApplied = vendorCreditsApplied;
    }

    @JsonProperty("is_line_item_invoiced")
    public Boolean getIsLineItemInvoiced() {
        return isLineItemInvoiced;
    }

    @JsonProperty("is_line_item_invoiced")
    public void setIsLineItemInvoiced(Boolean isLineItemInvoiced) {
        this.isLineItemInvoiced = isLineItemInvoiced;
    }

    @JsonProperty("taxes")
    public List<Tax> getTaxes() {
        return taxes;
    }

    @JsonProperty("taxes")
    public void setTaxes(List<Tax> taxes) {
        this.taxes = taxes;
    }

    @JsonProperty("balance")
    public Double getBalance() {
        return balance;
    }

    @JsonProperty("balance")
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @JsonProperty("billing_address")
    public Address getBillingAddress() {
        return billingAddress;
    }

    @JsonProperty("billing_address")
    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    @JsonProperty("created_time")
    public String getCreatedTime() {
        return createdTime;
    }

    @JsonProperty("created_time")
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    @JsonProperty("created_by_id")
    public String getCreatedById() {
        return createdById;
    }

    @JsonProperty("created_by_id")
    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    @JsonProperty("last_modified_time")
    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    @JsonProperty("last_modified_time")
    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
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

    @JsonProperty("open_purchaseorders_count")
    public Integer getOpenPurchaseordersCount() {
        return openPurchaseordersCount;
    }

    @JsonProperty("open_purchaseorders_count")
    public void setOpenPurchaseordersCount(Integer openPurchaseordersCount) {
        this.openPurchaseordersCount = openPurchaseordersCount;
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

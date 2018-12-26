package com.tmj.tms.finance.zoho.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "line_item_id",
        "account_id",
        "description",
        "amount",
        "tax_id",
        "item_order",
        "product_type",
        "acquisition_vat_id",
        "reverse_charge_vat_id",
        "reverse_charge_tax_id",
        "tax_exemption_id"
})
public class ExpenseLineItem {

    @JsonProperty("line_item_id")
    private Long lineItemId;
    @JsonProperty("account_id")
    private Long accountId;
    @JsonProperty("description")
    private String description;
    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("tax_id")
    private Long taxId;
    @JsonProperty("item_order")
    private Integer itemOrder;
    @JsonProperty("product_type")
    private String productType;
    @JsonProperty("acquisition_vat_id")
    private String acquisitionVatId;
    @JsonProperty("reverse_charge_vat_id")
    private String reverseChargeVatId;
    @JsonProperty("reverse_charge_tax_id")
    private Long reverseChargeTaxId;
    @JsonProperty("tax_exemption_id")
    private Long taxExemptionId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("line_item_id")
    public Long getLineItemId() {
        return lineItemId;
    }

    @JsonProperty("line_item_id")
    public void setLineItemId(Long lineItemId) {
        this.lineItemId = lineItemId;
    }

    @JsonProperty("account_id")
    public Long getAccountId() {
        return accountId;
    }

    @JsonProperty("account_id")
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("amount")
    public Double getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @JsonProperty("tax_id")
    public Long getTaxId() {
        return taxId;
    }

    @JsonProperty("tax_id")
    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    @JsonProperty("item_order")
    public Integer getItemOrder() {
        return itemOrder;
    }

    @JsonProperty("item_order")
    public void setItemOrder(Integer itemOrder) {
        this.itemOrder = itemOrder;
    }

    @JsonProperty("product_type")
    public String getProductType() {
        return productType;
    }

    @JsonProperty("product_type")
    public void setProductType(String productType) {
        this.productType = productType;
    }

    @JsonProperty("acquisition_vat_id")
    public String getAcquisitionVatId() {
        return acquisitionVatId;
    }

    @JsonProperty("acquisition_vat_id")
    public void setAcquisitionVatId(String acquisitionVatId) {
        this.acquisitionVatId = acquisitionVatId;
    }

    @JsonProperty("reverse_charge_vat_id")
    public String getReverseChargeVatId() {
        return reverseChargeVatId;
    }

    @JsonProperty("reverse_charge_vat_id")
    public void setReverseChargeVatId(String reverseChargeVatId) {
        this.reverseChargeVatId = reverseChargeVatId;
    }

    @JsonProperty("reverse_charge_tax_id")
    public Long getReverseChargeTaxId() {
        return reverseChargeTaxId;
    }

    @JsonProperty("reverse_charge_tax_id")
    public void setReverseChargeTaxId(Long reverseChargeTaxId) {
        this.reverseChargeTaxId = reverseChargeTaxId;
    }

    @JsonProperty("tax_exemption_id")
    public Long getTaxExemptionId() {
        return taxExemptionId;
    }

    @JsonProperty("tax_exemption_id")
    public void setTaxExemptionId(Long taxExemptionId) {
        this.taxExemptionId = taxExemptionId;
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

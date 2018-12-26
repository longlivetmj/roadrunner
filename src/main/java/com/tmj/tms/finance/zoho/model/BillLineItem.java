package com.tmj.tms.finance.zoho.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "line_item_id",
        "item_id",
        "account_id",
        "account_name",
        "image_document_id",
        "warehouse_id",
        "reverse_charge_tax_id",
        "reverse_charge_tax_name",
        "reverse_charge_tax_percentage",
        "reverse_charge_tax_amount",
        "bcy_rate",
        "rate",
        "tags",
        "quantity",
        "tax_id",
        "tax_name",
        "tax_type",
        "tax_percentage",
        "item_total",
        "item_total_inclusive_of_tax",
        "item_order",
        "unit",
        "hsn_or_sac",
        "is_billable"
})
public class BillLineItem {

    @JsonProperty("line_item_id")
    private Long lineItemId;
    @JsonProperty("item_id")
    private Long itemId;
    @JsonProperty("account_id")
    private Long accountId;
    @JsonProperty("account_name")
    private String accountName;
    @JsonProperty("image_document_id")
    private Long imageDocumentId;
    @JsonProperty("warehouse_id")
    private Long warehouseId;
    @JsonProperty("reverse_charge_tax_id")
    private Long reverseChargeTaxId;
    @JsonProperty("reverse_charge_tax_name")
    private String reverseChargeTaxName;
    @JsonProperty("reverse_charge_tax_percentage")
    private Double reverseChargeTaxPercentage;
    @JsonProperty("reverse_charge_tax_amount")
    private Double reverseChargeTaxAmount;
    @JsonProperty("bcy_rate")
    private Double bcyRate;
    @JsonProperty("rate")
    private Double rate;
    @JsonProperty("quantity")
    private Double quantity;
    @JsonProperty("tax_id")
    private Long taxId;
    @JsonProperty("tax_name")
    private String taxName;
    @JsonProperty("tax_type")
    private String taxType;
    @JsonProperty("tax_percentage")
    private Double taxPercentage;
    @JsonProperty("item_total")
    private Double itemTotal;
    @JsonProperty("item_total_inclusive_of_tax")
    private Double itemTotalInclusiveOfTax;
    @JsonProperty("item_order")
    private Integer itemOrder;
    @JsonProperty("unit")
    private String unit;
    @JsonProperty("hsn_or_sac")
    private Integer hsnOrSac;
    @JsonProperty("is_billable")
    private Boolean isBillable;
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

    @JsonProperty("item_id")
    public Long getItemId() {
        return itemId;
    }

    @JsonProperty("item_id")
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    @JsonProperty("account_id")
    public Long getAccountId() {
        return accountId;
    }

    @JsonProperty("account_id")
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @JsonProperty("account_name")
    public String getAccountName() {
        return accountName;
    }

    @JsonProperty("account_name")
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @JsonProperty("image_document_id")
    public Long getImageDocumentId() {
        return imageDocumentId;
    }

    @JsonProperty("image_document_id")
    public void setImageDocumentId(Long imageDocumentId) {
        this.imageDocumentId = imageDocumentId;
    }

    @JsonProperty("warehouse_id")
    public Long getWarehouseId() {
        return warehouseId;
    }

    @JsonProperty("warehouse_id")
    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    @JsonProperty("reverse_charge_tax_id")
    public Long getReverseChargeTaxId() {
        return reverseChargeTaxId;
    }

    @JsonProperty("reverse_charge_tax_id")
    public void setReverseChargeTaxId(Long reverseChargeTaxId) {
        this.reverseChargeTaxId = reverseChargeTaxId;
    }

    @JsonProperty("reverse_charge_tax_name")
    public String getReverseChargeTaxName() {
        return reverseChargeTaxName;
    }

    @JsonProperty("reverse_charge_tax_name")
    public void setReverseChargeTaxName(String reverseChargeTaxName) {
        this.reverseChargeTaxName = reverseChargeTaxName;
    }

    @JsonProperty("reverse_charge_tax_percentage")
    public Double getReverseChargeTaxPercentage() {
        return reverseChargeTaxPercentage;
    }

    @JsonProperty("reverse_charge_tax_percentage")
    public void setReverseChargeTaxPercentage(Double reverseChargeTaxPercentage) {
        this.reverseChargeTaxPercentage = reverseChargeTaxPercentage;
    }

    @JsonProperty("reverse_charge_tax_amount")
    public Double getReverseChargeTaxAmount() {
        return reverseChargeTaxAmount;
    }

    @JsonProperty("reverse_charge_tax_amount")
    public void setReverseChargeTaxAmount(Double reverseChargeTaxAmount) {
        this.reverseChargeTaxAmount = reverseChargeTaxAmount;
    }

    @JsonProperty("bcy_rate")
    public Double getBcyRate() {
        return bcyRate;
    }

    @JsonProperty("bcy_rate")
    public void setBcyRate(Double bcyRate) {
        this.bcyRate = bcyRate;
    }

    @JsonProperty("rate")
    public Double getRate() {
        return rate;
    }

    @JsonProperty("rate")
    public void setRate(Double rate) {
        this.rate = rate;
    }

    @JsonProperty("quantity")
    public Double getQuantity() {
        return quantity;
    }

    @JsonProperty("quantity")
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    @JsonProperty("tax_id")
    public Long getTaxId() {
        return taxId;
    }

    @JsonProperty("tax_id")
    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    @JsonProperty("tax_name")
    public String getTaxName() {
        return taxName;
    }

    @JsonProperty("tax_name")
    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    @JsonProperty("tax_type")
    public String getTaxType() {
        return taxType;
    }

    @JsonProperty("tax_type")
    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    @JsonProperty("tax_percentage")
    public Double getTaxPercentage() {
        return taxPercentage;
    }

    @JsonProperty("tax_percentage")
    public void setTaxPercentage(Double taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    @JsonProperty("item_total")
    public Double getItemTotal() {
        return itemTotal;
    }

    @JsonProperty("item_total")
    public void setItemTotal(Double itemTotal) {
        this.itemTotal = itemTotal;
    }

    @JsonProperty("item_total_inclusive_of_tax")
    public Double getItemTotalInclusiveOfTax() {
        return itemTotalInclusiveOfTax;
    }

    @JsonProperty("item_total_inclusive_of_tax")
    public void setItemTotalInclusiveOfTax(Double itemTotalInclusiveOfTax) {
        this.itemTotalInclusiveOfTax = itemTotalInclusiveOfTax;
    }

    @JsonProperty("item_order")
    public Integer getItemOrder() {
        return itemOrder;
    }

    @JsonProperty("item_order")
    public void setItemOrder(Integer itemOrder) {
        this.itemOrder = itemOrder;
    }

    @JsonProperty("unit")
    public String getUnit() {
        return unit;
    }

    @JsonProperty("unit")
    public void setUnit(String unit) {
        this.unit = unit;
    }

    @JsonProperty("hsn_or_sac")
    public Integer getHsnOrSac() {
        return hsnOrSac;
    }

    @JsonProperty("hsn_or_sac")
    public void setHsnOrSac(Integer hsnOrSac) {
        this.hsnOrSac = hsnOrSac;
    }

    @JsonProperty("is_billable")
    public Boolean getIsBillable() {
        return isBillable;
    }

    @JsonProperty("is_billable")
    public void setIsBillable(Boolean isBillable) {
        this.isBillable = isBillable;
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

package com.tmj.tms.finance.zoho.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "line_item_id",
        "item_id",
        "project_id",
        "warehouses",
        "item_type",
        "product_type",
        "expense_id",
        "name",
        "description",
        "item_order",
        "bcy_rate",
        "rate",
        "quantity",
        "unit",
        "discount_amount",
        "discount",
        "tax_id",
        "tax_name",
        "tax_type",
        "tax_percentage",
        "item_total"
})
public class LineItem {

    @JsonProperty("line_item_id")
    private Long lineItemId;
    @JsonProperty("item_id")
    private Long itemId;
    @JsonProperty("project_id")
    private String projectId;
    @JsonProperty("item_type")
    private String itemType;
    @JsonProperty("product_type")
    private String productType;
    @JsonProperty("expense_id")
    private String expenseId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("item_order")
    private Integer itemOrder;
    @JsonProperty("bcy_rate")
    private Integer bcyRate;
    @JsonProperty("rate")
    private Double rate;
    @JsonProperty("quantity")
    private Double quantity;
    @JsonProperty("unit")
    private String unit;
    @JsonProperty("discount_amount")
    private Double discountAmount;
    @JsonProperty("discount")
    private Double discount;
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

    @JsonProperty("project_id")
    public String getProjectId() {
        return projectId;
    }

    @JsonProperty("project_id")
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @JsonProperty("item_type")
    public String getItemType() {
        return itemType;
    }

    @JsonProperty("item_type")
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @JsonProperty("product_type")
    public String getProductType() {
        return productType;
    }

    @JsonProperty("product_type")
    public void setProductType(String productType) {
        this.productType = productType;
    }

    @JsonProperty("expense_id")
    public String getExpenseId() {
        return expenseId;
    }

    @JsonProperty("expense_id")
    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("item_order")
    public Integer getItemOrder() {
        return itemOrder;
    }

    @JsonProperty("item_order")
    public void setItemOrder(Integer itemOrder) {
        this.itemOrder = itemOrder;
    }

    @JsonProperty("bcy_rate")
    public Integer getBcyRate() {
        return bcyRate;
    }

    @JsonProperty("bcy_rate")
    public void setBcyRate(Integer bcyRate) {
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

    @JsonProperty("unit")
    public String getUnit() {
        return unit;
    }

    @JsonProperty("unit")
    public void setUnit(String unit) {
        this.unit = unit;
    }

    @JsonProperty("discount_amount")
    public Double getDiscountAmount() {
        return discountAmount;
    }

    @JsonProperty("discount_amount")
    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    @JsonProperty("discount")
    public Double getDiscount() {
        return discount;
    }

    @JsonProperty("discount")
    public void setDiscount(Double discount) {
        this.discount = discount;
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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

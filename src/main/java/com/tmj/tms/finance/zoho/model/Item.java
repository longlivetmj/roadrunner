package com.tmj.tms.finance.zoho.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "item_id",
        "name",
        "rate",
        "description",
        "tax_id",
        "tax_percentage",
        "sku",
        "product_type",
        "account_id",
        "avatax_tax_code",
        "avatax_use_code",
        "item_type",
        "purchase_description",
        "purchase_rate",
        "purchase_account_id",
        "inventory_account_id",
        "vendor_id",
        "reorder_level",
        "initial_stock",
        "initial_stock_rate"
})
public class Item {

    @JsonProperty("item_id")
    private Long itemId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("rate")
    private Integer rate;
    @JsonProperty("description")
    private String description;
    @JsonProperty("tax_id")
    private Integer taxId;
    @JsonProperty("tax_percentage")
    private String taxPercentage;
    @JsonProperty("sku")
    private String sku;
    @JsonProperty("product_type")
    private String productType;
    @JsonProperty("account_id")
    private String accountId;
    @JsonProperty("avatax_tax_code")
    private Integer avataxTaxCode;
    @JsonProperty("avatax_use_code")
    private Integer avataxUseCode;
    @JsonProperty("item_type")
    private String itemType;
    @JsonProperty("purchase_description")
    private String purchaseDescription;
    @JsonProperty("purchase_rate")
    private String purchaseRate;
    @JsonProperty("purchase_account_id")
    private String purchaseAccountId;
    @JsonProperty("inventory_account_id")
    private String inventoryAccountId;
    @JsonProperty("vendor_id")
    private String vendorId;
    @JsonProperty("reorder_level")
    private String reorderLevel;
    @JsonProperty("initial_stock")
    private String initialStock;
    @JsonProperty("initial_stock_rate")
    private String initialStockRate;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("item_id")
    public Long getItemId() {
        return itemId;
    }

    @JsonProperty("item_id")
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("rate")
    public Integer getRate() {
        return rate;
    }

    @JsonProperty("rate")
    public void setRate(Integer rate) {
        this.rate = rate;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("tax_id")
    public Integer getTaxId() {
        return taxId;
    }

    @JsonProperty("tax_id")
    public void setTaxId(Integer taxId) {
        this.taxId = taxId;
    }

    @JsonProperty("tax_percentage")
    public String getTaxPercentage() {
        return taxPercentage;
    }

    @JsonProperty("tax_percentage")
    public void setTaxPercentage(String taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    @JsonProperty("sku")
    public String getSku() {
        return sku;
    }

    @JsonProperty("sku")
    public void setSku(String sku) {
        this.sku = sku;
    }

    @JsonProperty("product_type")
    public String getProductType() {
        return productType;
    }

    @JsonProperty("product_type")
    public void setProductType(String productType) {
        this.productType = productType;
    }

    @JsonProperty("account_id")
    public String getAccountId() {
        return accountId;
    }

    @JsonProperty("account_id")
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @JsonProperty("avatax_tax_code")
    public Integer getAvataxTaxCode() {
        return avataxTaxCode;
    }

    @JsonProperty("avatax_tax_code")
    public void setAvataxTaxCode(Integer avataxTaxCode) {
        this.avataxTaxCode = avataxTaxCode;
    }

    @JsonProperty("avatax_use_code")
    public Integer getAvataxUseCode() {
        return avataxUseCode;
    }

    @JsonProperty("avatax_use_code")
    public void setAvataxUseCode(Integer avataxUseCode) {
        this.avataxUseCode = avataxUseCode;
    }

    @JsonProperty("item_type")
    public String getItemType() {
        return itemType;
    }

    @JsonProperty("item_type")
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @JsonProperty("purchase_description")
    public String getPurchaseDescription() {
        return purchaseDescription;
    }

    @JsonProperty("purchase_description")
    public void setPurchaseDescription(String purchaseDescription) {
        this.purchaseDescription = purchaseDescription;
    }

    @JsonProperty("purchase_rate")
    public String getPurchaseRate() {
        return purchaseRate;
    }

    @JsonProperty("purchase_rate")
    public void setPurchaseRate(String purchaseRate) {
        this.purchaseRate = purchaseRate;
    }

    @JsonProperty("purchase_account_id")
    public String getPurchaseAccountId() {
        return purchaseAccountId;
    }

    @JsonProperty("purchase_account_id")
    public void setPurchaseAccountId(String purchaseAccountId) {
        this.purchaseAccountId = purchaseAccountId;
    }

    @JsonProperty("inventory_account_id")
    public String getInventoryAccountId() {
        return inventoryAccountId;
    }

    @JsonProperty("inventory_account_id")
    public void setInventoryAccountId(String inventoryAccountId) {
        this.inventoryAccountId = inventoryAccountId;
    }

    @JsonProperty("vendor_id")
    public String getVendorId() {
        return vendorId;
    }

    @JsonProperty("vendor_id")
    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    @JsonProperty("reorder_level")
    public String getReorderLevel() {
        return reorderLevel;
    }

    @JsonProperty("reorder_level")
    public void setReorderLevel(String reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    @JsonProperty("initial_stock")
    public String getInitialStock() {
        return initialStock;
    }

    @JsonProperty("initial_stock")
    public void setInitialStock(String initialStock) {
        this.initialStock = initialStock;
    }

    @JsonProperty("initial_stock_rate")
    public String getInitialStockRate() {
        return initialStockRate;
    }

    @JsonProperty("initial_stock_rate")
    public void setInitialStockRate(String initialStockRate) {
        this.initialStockRate = initialStockRate;
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
package com.tmj.tms.finance.zoho.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "expense_id",
        "account_id",
        "date",
        "amount",
        "tax_id",
        "source_of_supply",
        "destination_of_supply",
        "hsn_or_sac",
        "gst_no",
        "reverse_charge_tax_id",
        "line_items",
        "is_inclusive_tax",
        "is_billable",
        "reference_number",
        "description",
        "customer_id",
        "currency_id",
        "exchange_rate",
        "project_id",
        "mileage_type",
        "vat_treatment",
        "product_type",
        "acquisition_vat_id",
        "reverse_charge_vat_id",
        "start_reading",
        "end_reading",
        "distance",
        "mileage_unit",
        "mileage_rate",
        "employee_id",
        "vehicle_type",
        "can_reclaim_vat_on_mileage",
        "fuel_type",
        "engine_capacity_range",
        "paid_through_account_id",
        "vendor_id",
        "custom_fields"
})
public class Expense {

    @JsonProperty("expense_id")
    private Long expenseId;
    @JsonProperty("account_id")
    private Long accountId;
    @JsonProperty("date")
    private String date;
    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("tax_id")
    private Long taxId;
    @JsonProperty("source_of_supply")
    private String sourceOfSupply;
    @JsonProperty("destination_of_supply")
    private String destinationOfSupply;
    @JsonProperty("hsn_or_sac")
    private Integer hsnOrSac;
    @JsonProperty("gst_no")
    private String gstNo;
    @JsonProperty("reverse_charge_tax_id")
    private Long reverseChargeTaxId;
    @JsonProperty("line_items")
    private List<ExpenseLineItem> lineItems = null;
    @JsonProperty("is_inclusive_tax")
    private Boolean isInclusiveTax;
    @JsonProperty("is_billable")
    private Boolean isBillable;
    @JsonProperty("reference_number")
    private String referenceNumber;
    @JsonProperty("description")
    private String description;
    @JsonProperty("customer_id")
    private Long customerId;
    @JsonProperty("currency_id")
    private Long currencyId;
    @JsonProperty("exchange_rate")
    private Integer exchangeRate;
    @JsonProperty("project_id")
    private Long projectId;
    @JsonProperty("mileage_type")
    private String mileageType;
    @JsonProperty("vat_treatment")
    private String vatTreatment;
    @JsonProperty("product_type")
    private String productType;
    @JsonProperty("acquisition_vat_id")
    private String acquisitionVatId;
    @JsonProperty("reverse_charge_vat_id")
    private String reverseChargeVatId;
    @JsonProperty("start_reading")
    private String startReading;
    @JsonProperty("end_reading")
    private String endReading;
    @JsonProperty("distance")
    private String distance;
    @JsonProperty("mileage_unit")
    private String mileageUnit;
    @JsonProperty("mileage_rate")
    private String mileageRate;
    @JsonProperty("employee_id")
    private Long employeeId;
    @JsonProperty("vehicle_type")
    private String vehicleType;
    @JsonProperty("can_reclaim_vat_on_mileage")
    private String canReclaimVatOnMileage;
    @JsonProperty("fuel_type")
    private String fuelType;
    @JsonProperty("engine_capacity_range")
    private String engineCapacityRange;
    @JsonProperty("paid_through_account_id")
    private Long paidThroughAccountId;
    @JsonProperty("vendor_id")
    private Long vendorId;
    @JsonProperty("custom_fields")
    private List<CustomField> customFields = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("expense_id")
    public Long getExpenseId() {
        return expenseId;
    }

    @JsonProperty("expense_id")
    public void setExpenseId(Long expenseId) {
        this.expenseId = expenseId;
    }

    @JsonProperty("account_id")
    public Long getAccountId() {
        return accountId;
    }

    @JsonProperty("account_id")
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
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

    @JsonProperty("hsn_or_sac")
    public Integer getHsnOrSac() {
        return hsnOrSac;
    }

    @JsonProperty("hsn_or_sac")
    public void setHsnOrSac(Integer hsnOrSac) {
        this.hsnOrSac = hsnOrSac;
    }

    @JsonProperty("gst_no")
    public String getGstNo() {
        return gstNo;
    }

    @JsonProperty("gst_no")
    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    @JsonProperty("reverse_charge_tax_id")
    public Long getReverseChargeTaxId() {
        return reverseChargeTaxId;
    }

    @JsonProperty("reverse_charge_tax_id")
    public void setReverseChargeTaxId(Long reverseChargeTaxId) {
        this.reverseChargeTaxId = reverseChargeTaxId;
    }

    @JsonProperty("line_items")
    public List<ExpenseLineItem> getLineItems() {
        return lineItems;
    }

    @JsonProperty("line_items")
    public void setLineItems(List<ExpenseLineItem> lineItems) {
        this.lineItems = lineItems;
    }

    @JsonProperty("is_inclusive_tax")
    public Boolean getIsInclusiveTax() {
        return isInclusiveTax;
    }

    @JsonProperty("is_inclusive_tax")
    public void setIsInclusiveTax(Boolean isInclusiveTax) {
        this.isInclusiveTax = isInclusiveTax;
    }

    @JsonProperty("is_billable")
    public Boolean getIsBillable() {
        return isBillable;
    }

    @JsonProperty("is_billable")
    public void setIsBillable(Boolean isBillable) {
        this.isBillable = isBillable;
    }

    @JsonProperty("reference_number")
    public String getReferenceNumber() {
        return referenceNumber;
    }

    @JsonProperty("reference_number")
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("customer_id")
    public Long getCustomerId() {
        return customerId;
    }

    @JsonProperty("customer_id")
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @JsonProperty("currency_id")
    public Long getCurrencyId() {
        return currencyId;
    }

    @JsonProperty("currency_id")
    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    @JsonProperty("exchange_rate")
    public Integer getExchangeRate() {
        return exchangeRate;
    }

    @JsonProperty("exchange_rate")
    public void setExchangeRate(Integer exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @JsonProperty("project_id")
    public Long getProjectId() {
        return projectId;
    }

    @JsonProperty("project_id")
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @JsonProperty("mileage_type")
    public String getMileageType() {
        return mileageType;
    }

    @JsonProperty("mileage_type")
    public void setMileageType(String mileageType) {
        this.mileageType = mileageType;
    }

    @JsonProperty("vat_treatment")
    public String getVatTreatment() {
        return vatTreatment;
    }

    @JsonProperty("vat_treatment")
    public void setVatTreatment(String vatTreatment) {
        this.vatTreatment = vatTreatment;
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

    @JsonProperty("start_reading")
    public String getStartReading() {
        return startReading;
    }

    @JsonProperty("start_reading")
    public void setStartReading(String startReading) {
        this.startReading = startReading;
    }

    @JsonProperty("end_reading")
    public String getEndReading() {
        return endReading;
    }

    @JsonProperty("end_reading")
    public void setEndReading(String endReading) {
        this.endReading = endReading;
    }

    @JsonProperty("distance")
    public String getDistance() {
        return distance;
    }

    @JsonProperty("distance")
    public void setDistance(String distance) {
        this.distance = distance;
    }

    @JsonProperty("mileage_unit")
    public String getMileageUnit() {
        return mileageUnit;
    }

    @JsonProperty("mileage_unit")
    public void setMileageUnit(String mileageUnit) {
        this.mileageUnit = mileageUnit;
    }

    @JsonProperty("mileage_rate")
    public String getMileageRate() {
        return mileageRate;
    }

    @JsonProperty("mileage_rate")
    public void setMileageRate(String mileageRate) {
        this.mileageRate = mileageRate;
    }

    @JsonProperty("employee_id")
    public Long getEmployeeId() {
        return employeeId;
    }

    @JsonProperty("employee_id")
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    @JsonProperty("vehicle_type")
    public String getVehicleType() {
        return vehicleType;
    }

    @JsonProperty("vehicle_type")
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    @JsonProperty("can_reclaim_vat_on_mileage")
    public String getCanReclaimVatOnMileage() {
        return canReclaimVatOnMileage;
    }

    @JsonProperty("can_reclaim_vat_on_mileage")
    public void setCanReclaimVatOnMileage(String canReclaimVatOnMileage) {
        this.canReclaimVatOnMileage = canReclaimVatOnMileage;
    }

    @JsonProperty("fuel_type")
    public String getFuelType() {
        return fuelType;
    }

    @JsonProperty("fuel_type")
    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    @JsonProperty("engine_capacity_range")
    public String getEngineCapacityRange() {
        return engineCapacityRange;
    }

    @JsonProperty("engine_capacity_range")
    public void setEngineCapacityRange(String engineCapacityRange) {
        this.engineCapacityRange = engineCapacityRange;
    }

    @JsonProperty("paid_through_account_id")
    public Long getPaidThroughAccountId() {
        return paidThroughAccountId;
    }

    @JsonProperty("paid_through_account_id")
    public void setPaidThroughAccountId(Long paidThroughAccountId) {
        this.paidThroughAccountId = paidThroughAccountId;
    }

    @JsonProperty("vendor_id")
    public Long getVendorId() {
        return vendorId;
    }

    @JsonProperty("vendor_id")
    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    @JsonProperty("custom_fields")
    public List<CustomField> getCustomFields() {
        return customFields;
    }

    @JsonProperty("custom_fields")
    public void setCustomFields(List<CustomField> customFields) {
        this.customFields = customFields;
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

package com.tmj.tms.finance.datalayer.modal.auxiliary;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class BulkInvoiceSearchAux {
    private Integer moduleSeq;
    private Integer departmentSeq;
    private Integer exchangeRateSourceType;
    private Integer sourceBankSeq;
    private Integer currencySeq;
    private Integer targetType;
    private Integer customerSeq;
    private Integer shipperSeq;
    private Integer referenceType;
    private Integer vehicleSeq;
    private Integer dateFilterType;
    private Date startDate;
    private Date endDate;
    private Integer exchangeRateSeq;
    private Set<Integer> financialChargeSeq;
    private Set<Integer> selectedChargeSeq;
    private Set<Integer> finalDestinationSeq;
    private Integer companyProfileSeq;
    private Integer stakeholderSeq;
    private String remark;

    public Integer getModuleSeq() {
        return moduleSeq;
    }

    public void setModuleSeq(Integer moduleSeq) {
        this.moduleSeq = moduleSeq;
    }

    public Integer getDepartmentSeq() {
        return departmentSeq;
    }

    public void setDepartmentSeq(Integer departmentSeq) {
        this.departmentSeq = departmentSeq;
    }

    public Integer getExchangeRateSourceType() {
        return exchangeRateSourceType;
    }

    public void setExchangeRateSourceType(Integer exchangeRateSourceType) {
        this.exchangeRateSourceType = exchangeRateSourceType;
    }

    public Integer getSourceBankSeq() {
        return sourceBankSeq;
    }

    public void setSourceBankSeq(Integer sourceBankSeq) {
        this.sourceBankSeq = sourceBankSeq;
    }

    public Integer getCurrencySeq() {
        return currencySeq;
    }

    public void setCurrencySeq(Integer currencySeq) {
        this.currencySeq = currencySeq;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public Integer getCustomerSeq() {
        return customerSeq;
    }

    public void setCustomerSeq(Integer customerSeq) {
        this.customerSeq = customerSeq;
    }

    public Integer getShipperSeq() {
        return shipperSeq;
    }

    public void setShipperSeq(Integer shipperSeq) {
        this.shipperSeq = shipperSeq;
    }

    public Integer getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(Integer referenceType) {
        this.referenceType = referenceType;
    }

    public Integer getVehicleSeq() {
        return vehicleSeq;
    }

    public void setVehicleSeq(Integer vehicleSeq) {
        this.vehicleSeq = vehicleSeq;
    }

    public Integer getDateFilterType() {
        return dateFilterType;
    }

    public void setDateFilterType(Integer dateFilterType) {
        this.dateFilterType = dateFilterType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Set<Integer> getFinancialChargeSeq() {
        return financialChargeSeq;
    }

    public void setFinancialChargeSeq(Set<Integer> financialChargeSeq) {
        this.financialChargeSeq = financialChargeSeq;
    }

    public Integer getExchangeRateSeq() {
        return exchangeRateSeq;
    }

    public void setExchangeRateSeq(Integer exchangeRateSeq) {
        this.exchangeRateSeq = exchangeRateSeq;
    }

    public Integer getCompanyProfileSeq() {
        return companyProfileSeq;
    }

    public void setCompanyProfileSeq(Integer companyProfileSeq) {
        this.companyProfileSeq = companyProfileSeq;
    }

    public Set<Integer> getSelectedChargeSeq() {
        return selectedChargeSeq;
    }

    public void setSelectedChargeSeq(Set<Integer> selectedChargeSeq) {
        this.selectedChargeSeq = selectedChargeSeq;
    }

    public Integer getStakeholderSeq() {
        return stakeholderSeq;
    }

    public void setStakeholderSeq(Integer stakeholderSeq) {
        this.stakeholderSeq = stakeholderSeq;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Set<Integer> getFinalDestinationSeq() {
        return finalDestinationSeq;
    }

    public void setFinalDestinationSeq(Set<Integer> finalDestinationSeq) {
        this.finalDestinationSeq = finalDestinationSeq;
    }
}

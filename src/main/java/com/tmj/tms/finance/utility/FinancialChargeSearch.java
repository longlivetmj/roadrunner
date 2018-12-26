package com.tmj.tms.finance.utility;

import java.util.Date;

public class FinancialChargeSearch {

    private Integer moduleSeq;
    private Integer transportBookingSeq;
    private Integer departmentSeq;
    private Integer customerSeq;
    private Integer dateFilterType;
    private Integer financeStatus;
    private Date startDate;
    private Date endDate;
    private Integer currentStatus;
    private Integer vehicleTypeSeq;
    private Integer transportCompanySeq;
    private Integer vehicleSeq;
    private String jobNo;
    private String customerRefNo;

    public Integer getModuleSeq() {
        return moduleSeq;
    }

    public void setModuleSeq(Integer moduleSeq) {
        this.moduleSeq = moduleSeq;
    }

    public Integer getTransportBookingSeq() {
        return transportBookingSeq;
    }

    public void setTransportBookingSeq(Integer transportBookingSeq) {
        this.transportBookingSeq = transportBookingSeq;
    }

    public Integer getDepartmentSeq() {
        return departmentSeq;
    }

    public void setDepartmentSeq(Integer departmentSeq) {
        this.departmentSeq = departmentSeq;
    }

    public Integer getCustomerSeq() {
        return customerSeq;
    }

    public void setCustomerSeq(Integer customerSeq) {
        this.customerSeq = customerSeq;
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

    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Integer getVehicleTypeSeq() {
        return vehicleTypeSeq;
    }

    public void setVehicleTypeSeq(Integer vehicleTypeSeq) {
        this.vehicleTypeSeq = vehicleTypeSeq;
    }

    public Integer getTransportCompanySeq() {
        return transportCompanySeq;
    }

    public void setTransportCompanySeq(Integer transportCompanySeq) {
        this.transportCompanySeq = transportCompanySeq;
    }

    public Integer getVehicleSeq() {
        return vehicleSeq;
    }

    public void setVehicleSeq(Integer vehicleSeq) {
        this.vehicleSeq = vehicleSeq;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getCustomerRefNo() {
        return customerRefNo;
    }

    public void setCustomerRefNo(String customerRefNo) {
        this.customerRefNo = customerRefNo;
    }

    public Integer getFinanceStatus() {
        return financeStatus;
    }

    public void setFinanceStatus(Integer financeStatus) {
        this.financeStatus = financeStatus;
    }
}

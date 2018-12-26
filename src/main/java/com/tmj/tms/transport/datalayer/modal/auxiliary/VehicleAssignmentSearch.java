package com.tmj.tms.transport.datalayer.modal.auxiliary;

import java.util.Date;

public class VehicleAssignmentSearch {

    private Integer transportBookingSeq;
    private Integer departmentSeq;
    private Integer customerSeq;
    private Integer dateFilterType;
    private Date startDate;
    private Date endDate;
    private Integer currentStatus;
    private Integer vehicleTypeSeq;
    private Integer pickupLocationSeq;
    private Integer deliveryLocationSeq;
    private String jobNo;
    private String customerRefNo;

    public Integer getTransportBookingSeq() {
        return transportBookingSeq;
    }

    public void setTransportBookingSeq(Integer transportBookingSeq) {
        this.transportBookingSeq = transportBookingSeq;
    }

    public Integer getCustomerSeq() {
        return customerSeq;
    }

    public void setCustomerSeq(Integer customerSeq) {
        this.customerSeq = customerSeq;
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

    public Integer getPickupLocationSeq() {
        return pickupLocationSeq;
    }

    public void setPickupLocationSeq(Integer pickupLocationSeq) {
        this.pickupLocationSeq = pickupLocationSeq;
    }

    public Integer getDeliveryLocationSeq() {
        return deliveryLocationSeq;
    }

    public void setDeliveryLocationSeq(Integer deliveryLocationSeq) {
        this.deliveryLocationSeq = deliveryLocationSeq;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public Integer getDepartmentSeq() {
        return departmentSeq;
    }

    public void setDepartmentSeq(Integer departmentSeq) {
        this.departmentSeq = departmentSeq;
    }

    public String getCustomerRefNo() {
        return customerRefNo;
    }

    public void setCustomerRefNo(String customerRefNo) {
        this.customerRefNo = customerRefNo;
    }

    public Integer getDateFilterType() {
        return dateFilterType;
    }

    public void setDateFilterType(Integer dateFilterType) {
        this.dateFilterType = dateFilterType;
    }
}

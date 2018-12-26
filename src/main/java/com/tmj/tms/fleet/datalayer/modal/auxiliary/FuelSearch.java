package com.tmj.tms.fleet.datalayer.modal.auxiliary;

import java.util.Date;

public class FuelSearch {

    private Integer stakeholderSeq;
    private Integer vehicleSeq;
    private Integer statusSeq;
    private Date startDate;
    private Date endDate;
    private Integer supplierSeq;
    private Integer fuelType;
    private Integer fuelTypeVariantSeq;
    private Integer stakeholderCashTypeSeq;

    public Integer getVehicleSeq() {
        return vehicleSeq;
    }

    public void setVehicleSeq(Integer vehicleSeq) {
        this.vehicleSeq = vehicleSeq;
    }

    public Integer getStakeholderSeq() {
        return stakeholderSeq;
    }

    public void setStakeholderSeq(Integer stakeholderSeq) {
        this.stakeholderSeq = stakeholderSeq;
    }


    public Integer getStatusSeq() {
        return statusSeq;
    }

    public void setStatusSeq(Integer statusSeq) {
        this.statusSeq = statusSeq;
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

    public Integer getSupplierSeq() {
        return supplierSeq;
    }

    public void setSupplierSeq(Integer supplierSeq) {
        this.supplierSeq = supplierSeq;
    }

    public Integer getFuelType() {
        return fuelType;
    }

    public void setFuelType(Integer fuelType) {
        this.fuelType = fuelType;
    }

    public Integer getFuelTypeVariantSeq() {
        return fuelTypeVariantSeq;
    }

    public void setFuelTypeVariantSeq(Integer fuelTypeVariantSeq) {
        this.fuelTypeVariantSeq = fuelTypeVariantSeq;
    }

    public Integer getStakeholderCashTypeSeq() {
        return stakeholderCashTypeSeq;
    }

    public void setStakeholderCashTypeSeq(Integer stakeholderCashTypeSeq) {
        this.stakeholderCashTypeSeq = stakeholderCashTypeSeq;
    }
}

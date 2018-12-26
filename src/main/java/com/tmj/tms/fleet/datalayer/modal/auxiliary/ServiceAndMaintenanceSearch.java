package com.tmj.tms.fleet.datalayer.modal.auxiliary;

import java.util.Date;

public class ServiceAndMaintenanceSearch {

    private Integer actionType;
    private Integer stakeholderSeq;
    private Integer vehicleSeq;
    private Integer statusSeq;
    private Date startDate;
    private Date endDate;
    private Integer supplierSeq;
    private Integer stakeholderCashTypeSeq;

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public Integer getStakeholderSeq() {
        return stakeholderSeq;
    }

    public void setStakeholderSeq(Integer stakeholderSeq) {
        this.stakeholderSeq = stakeholderSeq;
    }

    public Integer getVehicleSeq() {
        return vehicleSeq;
    }

    public void setVehicleSeq(Integer vehicleSeq) {
        this.vehicleSeq = vehicleSeq;
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

    public Integer getStakeholderCashTypeSeq() {
        return stakeholderCashTypeSeq;
    }

    public void setStakeholderCashTypeSeq(Integer stakeholderCashTypeSeq) {
        this.stakeholderCashTypeSeq = stakeholderCashTypeSeq;
    }
}

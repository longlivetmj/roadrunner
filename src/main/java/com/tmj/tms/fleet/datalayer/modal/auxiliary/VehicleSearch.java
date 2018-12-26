package com.tmj.tms.fleet.datalayer.modal.auxiliary;

import java.util.Date;

public class VehicleSearch {
    private String vehicleNo;
    private Integer stakeholderSeq;
    private String vehicleCode;
    private Integer statusSeq;
    private Date startDate;
    private Date endDate;
    private Integer vehicleTypeSeq;

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public Integer getStakeholderSeq() {
        return stakeholderSeq;
    }

    public void setStakeholderSeq(Integer stakeholderSeq) {
        this.stakeholderSeq = stakeholderSeq;
    }

    public String getVehicleCode() {
        return vehicleCode;
    }

    public void setVehicleCode(String vehicleCode) {
        this.vehicleCode = vehicleCode;
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

    public Integer getVehicleTypeSeq() {
        return vehicleTypeSeq;
    }

    public void setVehicleTypeSeq(Integer vehicleTypeSeq) {
        this.vehicleTypeSeq = vehicleTypeSeq;
    }
}

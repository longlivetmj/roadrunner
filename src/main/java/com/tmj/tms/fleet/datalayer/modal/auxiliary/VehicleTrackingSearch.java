package com.tmj.tms.fleet.datalayer.modal.auxiliary;

import java.util.Date;

public class VehicleTrackingSearch {
    private String vehicleNo;
    private Integer customerSeq;
    private Integer transporterSeq;
    private Integer status;
    private Integer vehicleTypeSeq;

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public Integer getCustomerSeq() {
        return customerSeq;
    }

    public void setCustomerSeq(Integer customerSeq) {
        this.customerSeq = customerSeq;
    }

    public Integer getTransporterSeq() {
        return transporterSeq;
    }

    public void setTransporterSeq(Integer transporterSeq) {
        this.transporterSeq = transporterSeq;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getVehicleTypeSeq() {
        return vehicleTypeSeq;
    }

    public void setVehicleTypeSeq(Integer vehicleTypeSeq) {
        this.vehicleTypeSeq = vehicleTypeSeq;
    }
}

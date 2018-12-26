package com.tmj.tms.transport.datalayer.modal;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "transport_booking_status")
public class TransportBookingStatus {

    private Integer currentStatus;
    private String currentStatusDescription;
    private String colorCode;
    private Integer status;
    private Integer updatable;
    private Integer invoiceable;
    private Integer vehicleAssign;
    private Integer timeUpdate;
    private Integer kmUpdate;

    @Id
    @Column(name = "current_status", nullable = false, unique = true)
    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    @Basic
    @Column(name = "current_status_description", nullable = false, length = 50)
    public String getCurrentStatusDescription() {
        return currentStatusDescription;
    }

    public void setCurrentStatusDescription(String currentStatusDescription) {
        this.currentStatusDescription = currentStatusDescription;
    }

    @Basic
    @Column(name = "color_code", nullable = false, length = 10)
    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "updatable", nullable = false)
    public Integer getUpdatable() {
        return updatable;
    }

    public void setUpdatable(Integer updatable) {
        this.updatable = updatable;
    }

    @Basic
    @Column(name = "invoiceable", nullable = false)
    public Integer getInvoiceable() {
        return invoiceable;
    }

    public void setInvoiceable(Integer invoiceable) {
        this.invoiceable = invoiceable;
    }

    @Basic
    @Column(name = "vehicle_assign")
    public Integer getVehicleAssign() {
        return vehicleAssign;
    }

    public void setVehicleAssign(Integer vehicleAssign) {
        this.vehicleAssign = vehicleAssign;
    }

    @Basic
    @Column(name = "time_update")
    public Integer getTimeUpdate() {
        return timeUpdate;
    }

    public void setTimeUpdate(Integer timeUpdate) {
        this.timeUpdate = timeUpdate;
    }

    @Basic
    @Column(name = "km_update")
    public Integer getKmUpdate() {
        return kmUpdate;
    }

    public void setKmUpdate(Integer kmUpdate) {
        this.kmUpdate = kmUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportBookingStatus that = (TransportBookingStatus) o;
        return Objects.equals(currentStatus, that.currentStatus) &&
                Objects.equals(currentStatusDescription, that.currentStatusDescription) &&
                Objects.equals(colorCode, that.colorCode) &&
                Objects.equals(status, that.status) &&
                Objects.equals(updatable, that.updatable) &&
                Objects.equals(vehicleAssign, that.vehicleAssign) &&
                Objects.equals(timeUpdate, that.timeUpdate) &&
                Objects.equals(invoiceable, that.invoiceable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentStatus, currentStatusDescription, colorCode, status, updatable, vehicleAssign, invoiceable);
    }
}

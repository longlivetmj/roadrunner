package com.tmj.tms.fleet.datalayer.modal.auxiliary;

import com.tmj.tms.transport.datalayer.modal.TransportBooking;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "job_assigned_vehicles")
public class JobAssignedVehicles {

    private Integer transportBookingSeq;
    private Integer vehicleSeq;
    private Integer transportBookingVehicleSeq;
    private Integer currentStatus;
    private Integer customerSeq;
    private Integer transportCompanySeq;

    private TransportBooking transportBooking;

    @Id
    @Column(name = "transport_booking_seq", unique = true)
    public Integer getTransportBookingSeq() {
        return transportBookingSeq;
    }

    public void setTransportBookingSeq(Integer transportBookingSeq) {
        this.transportBookingSeq = transportBookingSeq;
    }

    @Basic
    @Column(name = "vehicle_seq")
    public Integer getVehicleSeq() {
        return vehicleSeq;
    }

    public void setVehicleSeq(Integer vehicleSeq) {
        this.vehicleSeq = vehicleSeq;
    }

    @Basic
    @Column(name = "transport_booking_vehicle_seq")
    public Integer getTransportBookingVehicleSeq() {
        return transportBookingVehicleSeq;
    }

    public void setTransportBookingVehicleSeq(Integer transportBookingVehicleSeq) {
        this.transportBookingVehicleSeq = transportBookingVehicleSeq;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_booking_seq", insertable = false, updatable = false)
    public TransportBooking getTransportBooking() {
        return transportBooking;
    }

    public void setTransportBooking(TransportBooking transportBooking) {
        this.transportBooking = transportBooking;
    }

    @Basic
    @Column(name = "current_status")
    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    @Basic
    @Column(name = "customer_seq")
    public Integer getCustomerSeq() {
        return customerSeq;
    }

    public void setCustomerSeq(Integer customerSeq) {
        this.customerSeq = customerSeq;
    }

    @Basic
    @Column(name = "transport_company_seq")
    public Integer getTransportCompanySeq() {
        return transportCompanySeq;
    }

    public void setTransportCompanySeq(Integer transportCompanySeq) {
        this.transportCompanySeq = transportCompanySeq;
    }
}

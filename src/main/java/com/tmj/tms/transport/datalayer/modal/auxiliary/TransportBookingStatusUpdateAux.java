package com.tmj.tms.transport.datalayer.modal.auxiliary;

import com.tmj.tms.transport.datalayer.modal.TransportBookingStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "transport_booking")
public class TransportBookingStatusUpdateAux {

    private Integer transportBookingSeq;
    private Integer currentStatus;
    private Double estimatedKm;
    private String humanReadableEta;
    private Integer etaInMinutes;
    private Date requestedArrivalTime;
    private Integer companyProfileSeq;
    private Integer invoiceCustomerSeq;
    private Integer pickupLocationSeq;
    private Integer deliveryLocationSeq;
    private TransportBookingStatus transportBookingStatus;

    @Id
    @Column(name = "transport_booking_seq", nullable = false, unique = true)
    public Integer getTransportBookingSeq() {
        return transportBookingSeq;
    }

    public void setTransportBookingSeq(Integer transportBookingSeq) {
        this.transportBookingSeq = transportBookingSeq;
    }

    @Basic
    @Column(name = "current_status", nullable = false)
    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "current_status", insertable = false, updatable = false)
    public TransportBookingStatus getTransportBookingStatus() {
        return transportBookingStatus;
    }

    public void setTransportBookingStatus(TransportBookingStatus transportBookingStatus) {
        this.transportBookingStatus = transportBookingStatus;
    }

    @Basic
    @Column(name = "estimated_km")
    public Double getEstimatedKm() {
        return estimatedKm;
    }

    public void setEstimatedKm(Double estimatedKm) {
        this.estimatedKm = estimatedKm;
    }

    @Basic
    @Column(name = "human_readable_eta")
    public String getHumanReadableEta() {
        return humanReadableEta;
    }

    public void setHumanReadableEta(String humanReadableEta) {
        this.humanReadableEta = humanReadableEta;
    }

    @Basic
    @Column(name = "eta_in_minutes")
    public Integer getEtaInMinutes() {
        return etaInMinutes;
    }

    public void setEtaInMinutes(Integer etaInMinutes) {
        this.etaInMinutes = etaInMinutes;
    }

    @Basic
    @Column(name = "requested_arrival_time")
    public Date getRequestedArrivalTime() {
        return requestedArrivalTime;
    }

    public void setRequestedArrivalTime(Date requestedArrivalTime) {
        this.requestedArrivalTime = requestedArrivalTime;
    }

    @Basic
    @Column(name = "company_profile_seq")
    public Integer getCompanyProfileSeq() {
        return companyProfileSeq;
    }

    public void setCompanyProfileSeq(Integer companyProfileSeq) {
        this.companyProfileSeq = companyProfileSeq;
    }

    @Basic
    @Column(name = "invoice_customer_seq")
    public Integer getInvoiceCustomerSeq() {
        return invoiceCustomerSeq;
    }

    public void setInvoiceCustomerSeq(Integer invoiceCustomerSeq) {
        this.invoiceCustomerSeq = invoiceCustomerSeq;
    }

    @Basic
    @Column(name = "pickup_location_seq")
    public Integer getPickupLocationSeq() {
        return pickupLocationSeq;
    }

    public void setPickupLocationSeq(Integer pickupLocationSeq) {
        this.pickupLocationSeq = pickupLocationSeq;
    }

    @Basic
    @Column(name = "delivery_location_seq")
    public Integer getDeliveryLocationSeq() {
        return deliveryLocationSeq;
    }

    public void setDeliveryLocationSeq(Integer deliveryLocationSeq) {
        this.deliveryLocationSeq = deliveryLocationSeq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportBookingStatusUpdateAux that = (TransportBookingStatusUpdateAux) o;
        return Objects.equals(transportBookingSeq, that.transportBookingSeq) &&
                Objects.equals(currentStatus, that.currentStatus) &&
                Objects.equals(estimatedKm, that.estimatedKm) &&
                Objects.equals(humanReadableEta, that.humanReadableEta) &&
                Objects.equals(etaInMinutes, that.etaInMinutes) &&
                Objects.equals(requestedArrivalTime, that.requestedArrivalTime) &&
                Objects.equals(companyProfileSeq, that.companyProfileSeq) &&
                Objects.equals(invoiceCustomerSeq, that.invoiceCustomerSeq) &&
                Objects.equals(pickupLocationSeq, that.pickupLocationSeq) &&
                Objects.equals(deliveryLocationSeq, that.deliveryLocationSeq) &&
                Objects.equals(transportBookingStatus, that.transportBookingStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transportBookingSeq, currentStatus, estimatedKm, humanReadableEta, etaInMinutes, requestedArrivalTime, companyProfileSeq, invoiceCustomerSeq, pickupLocationSeq, deliveryLocationSeq, transportBookingStatus);
    }
}

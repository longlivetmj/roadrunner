package com.tmj.tms.transport.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.transport.utility.ArrivalConfirmation;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "transport_booking_feedback")
public class TransportBookingFeedback {

    private Integer transportBookingSeq;
    private Integer arrivalConfirmation;
    private String arrivalConfirmedBy;
    private Date arrivalConfirmedDate;
    private Double placementKm;
    private Integer startMeterReading;
    private Integer endMeterReading;
    private String supplierNegotiations;
    private Double supplierNegotiationValue;
    private Integer supplierNegotiationType;
    private String supplierNegotiatedBy;
    private Date supplierNegotiatedDate;
    private Integer connectingTransportBookingSeq;
    private Date arrivedAtPickup;
    private String arrivedAtPickupUpdatedBy;
    private Date arrivedAtPickupUpdatedDate;
    private Date departedFromPickup;
    private String departedFromPickupUpdatedBy;
    private Date departedFromPickupUpdatedDate;
    private Date arrivedAtDelivery;
    private String arrivedAtDeliveryUpdatedBy;
    private Date arrivedAtDeliveryUpdatedDate;
    private Date departedFromDelivery;
    private String departedFromDeliveryUpdatedBy;
    private Date departedFromDeliveryUpdatedDate;
    private Double cargoInHandKm;
    private Double chargeableKm;
    private String operationRemarks;
    private Date documentsCollectedDate;
    private Integer chargesCalculated;

    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer status;

    private String arrivalConfirmationDescription;

    @Id
    @Column(name = "transport_booking_seq", nullable = false, unique = true)
    public Integer getTransportBookingSeq() {
        return transportBookingSeq;
    }

    public void setTransportBookingSeq(Integer transportBookingSeq) {
        this.transportBookingSeq = transportBookingSeq;
    }

    @Basic
    @Column(name = "arrival_confirmation")
    public Integer getArrivalConfirmation() {
        return arrivalConfirmation;
    }

    public void setArrivalConfirmation(Integer arrivalConfirmation) {
        this.arrivalConfirmation = arrivalConfirmation;
        if (arrivalConfirmation != null) {
            this.setArrivalConfirmationDescription(ArrivalConfirmation.findOne(arrivalConfirmation).getArrivalConfirmationDescription());
        }
    }

    @Basic
    @Column(name = "arrival_confirmed_by")
    public String getArrivalConfirmedBy() {
        return arrivalConfirmedBy;
    }

    public void setArrivalConfirmedBy(String arrivalConfirmedBy) {
        this.arrivalConfirmedBy = arrivalConfirmedBy;
    }

    @Basic
    @Column(name = "arrival_confirmed_date")
    public Date getArrivalConfirmedDate() {
        return arrivalConfirmedDate;
    }

    public void setArrivalConfirmedDate(Date arrivalConfirmedDate) {
        this.arrivalConfirmedDate = arrivalConfirmedDate;
    }

    @Basic
    @Column(name = "placement_km")
    public Double getPlacementKm() {
        return placementKm;
    }

    public void setPlacementKm(Double placementKm) {
        this.placementKm = placementKm;
    }

    @Basic
    @Column(name = "start_meter_reading")
    public Integer getStartMeterReading() {
        return startMeterReading;
    }

    public void setStartMeterReading(Integer startMeterReading) {
        this.startMeterReading = startMeterReading;
    }

    @Basic
    @Column(name = "end_meter_reading")
    public Integer getEndMeterReading() {
        return endMeterReading;
    }

    public void setEndMeterReading(Integer endMeterReading) {
        this.endMeterReading = endMeterReading;
    }

    @Basic
    @Column(name = "supplier_negotiations")
    public String getSupplierNegotiations() {
        return supplierNegotiations;
    }

    public void setSupplierNegotiations(String supplierNegotiations) {
        this.supplierNegotiations = supplierNegotiations;
    }

    @Basic
    @Column(name = "supplier_negotiation_value")
    public Double getSupplierNegotiationValue() {
        return supplierNegotiationValue;
    }

    public void setSupplierNegotiationValue(Double supplierNegotiationValue) {
        this.supplierNegotiationValue = supplierNegotiationValue;
    }

    @Basic
    @Column(name = "supplier_negotiation_type")
    public Integer getSupplierNegotiationType() {
        return supplierNegotiationType;
    }

    public void setSupplierNegotiationType(Integer supplierNegotiationType) {
        this.supplierNegotiationType = supplierNegotiationType;
    }

    @Basic
    @Column(name = "supplier_negotiated_by")
    public String getSupplierNegotiatedBy() {
        return supplierNegotiatedBy;
    }

    public void setSupplierNegotiatedBy(String supplierNegotiatedBy) {
        this.supplierNegotiatedBy = supplierNegotiatedBy;
    }

    @Basic
    @Column(name = "supplier_negotiated_date")
    public Date getSupplierNegotiatedDate() {
        return supplierNegotiatedDate;
    }

    public void setSupplierNegotiatedDate(Date supplierNegotiatedDate) {
        this.supplierNegotiatedDate = supplierNegotiatedDate;
    }

    @Basic
    @Column(name = "connecting_transport_booking_seq")
    public Integer getConnectingTransportBookingSeq() {
        return connectingTransportBookingSeq;
    }

    public void setConnectingTransportBookingSeq(Integer connectingTransportBookingSeq) {
        this.connectingTransportBookingSeq = connectingTransportBookingSeq;
    }

    @Basic
    @Column(name = "arrived_at_pickup")
    public Date getArrivedAtPickup() {
        return arrivedAtPickup;
    }

    public void setArrivedAtPickup(Date arrivedAtPickup) {
        this.arrivedAtPickup = arrivedAtPickup;
    }

    @Basic
    @Column(name = "arrived_at_pickup_updated_by")
    public String getArrivedAtPickupUpdatedBy() {
        return arrivedAtPickupUpdatedBy;
    }

    public void setArrivedAtPickupUpdatedBy(String arrivedAtPickupUpdatedBy) {
        this.arrivedAtPickupUpdatedBy = arrivedAtPickupUpdatedBy;
    }

    @Basic
    @Column(name = "arrived_at_pickup_updated_date")
    public Date getArrivedAtPickupUpdatedDate() {
        return arrivedAtPickupUpdatedDate;
    }

    public void setArrivedAtPickupUpdatedDate(Date arrivedAtPickupUpdatedDate) {
        this.arrivedAtPickupUpdatedDate = arrivedAtPickupUpdatedDate;
    }

    @Basic
    @Column(name = "departed_from_pickup")
    public Date getDepartedFromPickup() {
        return departedFromPickup;
    }

    public void setDepartedFromPickup(Date departedFromPickup) {
        this.departedFromPickup = departedFromPickup;
    }

    @Basic
    @Column(name = "departed_from_pickup_updated_by")
    public String getDepartedFromPickupUpdatedBy() {
        return departedFromPickupUpdatedBy;
    }

    public void setDepartedFromPickupUpdatedBy(String departedFromPickupUpdatedBy) {
        this.departedFromPickupUpdatedBy = departedFromPickupUpdatedBy;
    }

    @Basic
    @Column(name = "departed_from_pickup_updated_date")
    public Date getDepartedFromPickupUpdatedDate() {
        return departedFromPickupUpdatedDate;
    }

    public void setDepartedFromPickupUpdatedDate(Date departedFromPickupUpdatedDate) {
        this.departedFromPickupUpdatedDate = departedFromPickupUpdatedDate;
    }

    @Basic
    @Column(name = "arrived_at_delivery")
    public Date getArrivedAtDelivery() {
        return arrivedAtDelivery;
    }

    public void setArrivedAtDelivery(Date arrivedAtDelivery) {
        this.arrivedAtDelivery = arrivedAtDelivery;
    }

    @Basic
    @Column(name = "arrived_at_delivery_updated_by")
    public String getArrivedAtDeliveryUpdatedBy() {
        return arrivedAtDeliveryUpdatedBy;
    }

    public void setArrivedAtDeliveryUpdatedBy(String arrivedAtDeliveryUpdatedBy) {
        this.arrivedAtDeliveryUpdatedBy = arrivedAtDeliveryUpdatedBy;
    }

    @Basic
    @Column(name = "arrived_at_delivery_updated_date")
    public Date getArrivedAtDeliveryUpdatedDate() {
        return arrivedAtDeliveryUpdatedDate;
    }

    public void setArrivedAtDeliveryUpdatedDate(Date arrivedAtDeliveryUpdatedDate) {
        this.arrivedAtDeliveryUpdatedDate = arrivedAtDeliveryUpdatedDate;
    }

    @Basic
    @Column(name = "departed_from_delivery")
    public Date getDepartedFromDelivery() {
        return departedFromDelivery;
    }

    public void setDepartedFromDelivery(Date departedFromDelivery) {
        this.departedFromDelivery = departedFromDelivery;
    }

    @Basic
    @Column(name = "departed_from_delivery_updated_by")
    public String getDepartedFromDeliveryUpdatedBy() {
        return departedFromDeliveryUpdatedBy;
    }

    public void setDepartedFromDeliveryUpdatedBy(String departedFromDeliveryUpdatedBy) {
        this.departedFromDeliveryUpdatedBy = departedFromDeliveryUpdatedBy;
    }

    @Basic
    @Column(name = "departed_from_delivery_updated_date")
    public Date getDepartedFromDeliveryUpdatedDate() {
        return departedFromDeliveryUpdatedDate;
    }

    public void setDepartedFromDeliveryUpdatedDate(Date departedFromDeliveryUpdatedDate) {
        this.departedFromDeliveryUpdatedDate = departedFromDeliveryUpdatedDate;
    }

    @Basic
    @Column(name = "cargo_in_hand_km")
    public Double getCargoInHandKm() {
        return cargoInHandKm;
    }

    public void setCargoInHandKm(Double cargoInHandKm) {
        this.cargoInHandKm = cargoInHandKm;
    }

    @Basic
    @Column(name = "chargeable_km")
    public Double getChargeableKm() {
        return chargeableKm;
    }

    public void setChargeableKm(Double chargeableKm) {
        this.chargeableKm = chargeableKm;
    }

    @Basic
    @Column(name = "operation_remarks")
    public String getOperationRemarks() {
        return operationRemarks;
    }

    public void setOperationRemarks(String operationRemarks) {
        this.operationRemarks = operationRemarks;
    }

    @Basic
    @Column(name = "charges_calculated")
    public Integer getChargesCalculated() {
        return chargesCalculated;
    }

    public void setChargesCalculated(Integer chargesCalculated) {
        this.chargesCalculated = chargesCalculated;
    }

    @Basic
    @Column(name = "documents_collected_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Date getDocumentsCollectedDate() {
        return documentsCollectedDate;
    }

    public void setDocumentsCollectedDate(Date documentsCollectedDate) {
        this.documentsCollectedDate = documentsCollectedDate;
    }

    @Basic
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @LastModifiedBy
    @Column(name = "last_modified_by", nullable = false)
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Basic
    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Transient
    public String getArrivalConfirmationDescription() {
        return arrivalConfirmationDescription;
    }

    public void setArrivalConfirmationDescription(String arrivalConfirmationDescription) {
        this.arrivalConfirmationDescription = arrivalConfirmationDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportBookingFeedback that = (TransportBookingFeedback) o;
        return Objects.equals(transportBookingSeq, that.transportBookingSeq) &&
                Objects.equals(arrivalConfirmation, that.arrivalConfirmation) &&
                Objects.equals(arrivalConfirmedBy, that.arrivalConfirmedBy) &&
                Objects.equals(arrivalConfirmedDate, that.arrivalConfirmedDate) &&
                Objects.equals(placementKm, that.placementKm) &&
                Objects.equals(startMeterReading, that.startMeterReading) &&
                Objects.equals(endMeterReading, that.endMeterReading) &&
                Objects.equals(supplierNegotiations, that.supplierNegotiations) &&
                Objects.equals(supplierNegotiationValue, that.supplierNegotiationValue) &&
                Objects.equals(supplierNegotiationType, that.supplierNegotiationType) &&
                Objects.equals(supplierNegotiatedBy, that.supplierNegotiatedBy) &&
                Objects.equals(supplierNegotiatedDate, that.supplierNegotiatedDate) &&
                Objects.equals(connectingTransportBookingSeq, that.connectingTransportBookingSeq) &&
                Objects.equals(arrivedAtPickup, that.arrivedAtPickup) &&
                Objects.equals(arrivedAtPickupUpdatedBy, that.arrivedAtPickupUpdatedBy) &&
                Objects.equals(arrivedAtPickupUpdatedDate, that.arrivedAtPickupUpdatedDate) &&
                Objects.equals(departedFromPickup, that.departedFromPickup) &&
                Objects.equals(departedFromPickupUpdatedBy, that.departedFromPickupUpdatedBy) &&
                Objects.equals(departedFromPickupUpdatedDate, that.departedFromPickupUpdatedDate) &&
                Objects.equals(arrivedAtDelivery, that.arrivedAtDelivery) &&
                Objects.equals(arrivedAtDeliveryUpdatedBy, that.arrivedAtDeliveryUpdatedBy) &&
                Objects.equals(arrivedAtDeliveryUpdatedDate, that.arrivedAtDeliveryUpdatedDate) &&
                Objects.equals(departedFromDelivery, that.departedFromDelivery) &&
                Objects.equals(departedFromDeliveryUpdatedBy, that.departedFromDeliveryUpdatedBy) &&
                Objects.equals(departedFromDeliveryUpdatedDate, that.departedFromDeliveryUpdatedDate) &&
                Objects.equals(cargoInHandKm, that.cargoInHandKm) &&
                Objects.equals(chargeableKm, that.chargeableKm) &&
                Objects.equals(operationRemarks, that.operationRemarks) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
                Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transportBookingSeq, arrivalConfirmation, arrivalConfirmedBy, arrivalConfirmedDate, placementKm, startMeterReading, endMeterReading, supplierNegotiations, supplierNegotiationValue, supplierNegotiationType, supplierNegotiatedBy, supplierNegotiatedDate, connectingTransportBookingSeq, arrivedAtPickup, arrivedAtPickupUpdatedBy, arrivedAtPickupUpdatedDate, departedFromPickup, departedFromPickupUpdatedBy, departedFromPickupUpdatedDate, arrivedAtDelivery, arrivedAtDeliveryUpdatedBy, arrivedAtDeliveryUpdatedDate, departedFromDelivery, departedFromDeliveryUpdatedBy, departedFromDeliveryUpdatedDate, cargoInHandKm, chargeableKm, operationRemarks, createdBy, createdDate, lastModifiedBy, lastModifiedDate, status);
    }
}

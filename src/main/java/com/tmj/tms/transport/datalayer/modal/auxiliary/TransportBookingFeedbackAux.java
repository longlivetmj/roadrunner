package com.tmj.tms.transport.datalayer.modal.auxiliary;

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
@Table(name = "transport_booking_feedback")
public class TransportBookingFeedbackAux {

    private Integer transportBookingSeq;

    private Double placementKm;

    private Double cargoInHandKm;

    private Double chargeableKm;


    @Id
    @Column(name = "transport_booking_seq", nullable = false, unique = true)
    public Integer getTransportBookingSeq() {
        return transportBookingSeq;
    }

    public void setTransportBookingSeq(Integer transportBookingSeq) {
        this.transportBookingSeq = transportBookingSeq;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportBookingFeedbackAux that = (TransportBookingFeedbackAux) o;
        return Objects.equals(transportBookingSeq, that.transportBookingSeq) &&
                Objects.equals(placementKm, that.placementKm) &&
                Objects.equals(cargoInHandKm, that.cargoInHandKm) &&
                Objects.equals(chargeableKm, that.chargeableKm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transportBookingSeq, placementKm, cargoInHandKm, chargeableKm);
    }
}

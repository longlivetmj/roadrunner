package com.tmj.tms.transport.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.datalayer.modal.FinalDestination;
import com.tmj.tms.master.datalayer.modal.PackageType;
import com.tmj.tms.master.datalayer.modal.Stakeholder;
import com.tmj.tms.master.datalayer.modal.Unit;
import com.tmj.tms.transport.utility.DropPick;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "transport_booking_via_location_feedback")
public class TransportBookingViaLocationFeedback {

    private Integer transportBookingViaLocationSeq;

    private Date actualArrivalTime;
    private String actualArrivalTimeUpdatedBy;
    private Date actualArrivalTimeUpdatedDate;
    private Date actualDepartureTime;
    private String actualDepartureTimeUpdatedBy;
    private Date actualDepartureTimeUpdatedDate;

    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer status;

    @Id
    @Column(name = "transport_booking_via_location_seq", nullable = false, unique = true)
    public Integer getTransportBookingViaLocationSeq() {
        return transportBookingViaLocationSeq;
    }

    public void setTransportBookingViaLocationSeq(Integer transportBookingViaLocationSeq) {
        this.transportBookingViaLocationSeq = transportBookingViaLocationSeq;
    }

    @Basic
    @Column(name = "actual_arrival_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getActualArrivalTime() {
        return actualArrivalTime;
    }

    public void setActualArrivalTime(Date actualArrivalTime) {
        this.actualArrivalTime = actualArrivalTime;
    }

    @Basic
    @Column(name = "actual_arrival_time_updated_by")
    public String getActualArrivalTimeUpdatedBy() {
        return actualArrivalTimeUpdatedBy;
    }

    public void setActualArrivalTimeUpdatedBy(String actualArrivalTimeUpdatedBy) {
        this.actualArrivalTimeUpdatedBy = actualArrivalTimeUpdatedBy;
    }

    @Basic
    @Column(name = "actual_arrival_time_updated_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getActualArrivalTimeUpdatedDate() {
        return actualArrivalTimeUpdatedDate;
    }

    public void setActualArrivalTimeUpdatedDate(Date actualArrivalTimeUpdatedDate) {
        this.actualArrivalTimeUpdatedDate = actualArrivalTimeUpdatedDate;
    }

    @Basic
    @Column(name = "actual_departure_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getActualDepartureTime() {
        return actualDepartureTime;
    }

    public void setActualDepartureTime(Date actualDepartureTime) {
        this.actualDepartureTime = actualDepartureTime;
    }

    @Basic
    @Column(name = "actual_departure_time_updated_by")
    public String getActualDepartureTimeUpdatedBy() {
        return actualDepartureTimeUpdatedBy;
    }

    public void setActualDepartureTimeUpdatedBy(String actualDepartureTimeUpdatedBy) {
        this.actualDepartureTimeUpdatedBy = actualDepartureTimeUpdatedBy;
    }

    @Basic
    @Column(name = "actual_departure_time_updated_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getActualDepartureTimeUpdatedDate() {
        return actualDepartureTimeUpdatedDate;
    }

    public void setActualDepartureTimeUpdatedDate(Date actualDepartureTimeUpdatedDate) {
        this.actualDepartureTimeUpdatedDate = actualDepartureTimeUpdatedDate;
    }

    @Basic
    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 50, updatable = false)
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
    @Column(name = "last_modified_by", nullable = false, length = 50)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportBookingViaLocationFeedback that = (TransportBookingViaLocationFeedback) o;
        return Objects.equals(transportBookingViaLocationSeq, that.transportBookingViaLocationSeq) &&
                Objects.equals(actualArrivalTime, that.actualArrivalTime) &&
                Objects.equals(actualArrivalTimeUpdatedBy, that.actualArrivalTimeUpdatedBy) &&
                Objects.equals(actualArrivalTimeUpdatedDate, that.actualArrivalTimeUpdatedDate) &&
                Objects.equals(actualDepartureTime, that.actualDepartureTime) &&
                Objects.equals(actualDepartureTimeUpdatedBy, that.actualDepartureTimeUpdatedBy) &&
                Objects.equals(actualDepartureTimeUpdatedDate, that.actualDepartureTimeUpdatedDate) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transportBookingViaLocationSeq, actualArrivalTime, actualArrivalTimeUpdatedBy, actualArrivalTimeUpdatedDate, actualDepartureTime, actualDepartureTimeUpdatedBy, actualDepartureTimeUpdatedDate, status);
    }
}

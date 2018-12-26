package com.tmj.tms.finance.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.datalayer.modal.FinalDestination;
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
@Table(name = "rate_master_detail")
public class RateMasterDetail {

    private Integer rateMasterDetailSeq;
    private Integer rateMasterSeq;
    private Integer rateType;
    private Integer typeSeq;
    private Double rateValue;
    private Integer pickupLocationSeq;
    private Integer deliveryLocationSeq;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer status;

    private FinalDestination pickupLocation;
    private FinalDestination deliveryLocation;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "rate_master_detail_seq", allocationSize = 1)
    @Column(name = "rate_master_detail_seq", unique = true)
    public Integer getRateMasterDetailSeq() {
        return rateMasterDetailSeq;
    }

    public void setRateMasterDetailSeq(Integer rateMasterDetailSeq) {
        this.rateMasterDetailSeq = rateMasterDetailSeq;
    }

    @Basic
    @Column(name = "rate_master_seq", insertable = false, updatable = false)
    public Integer getRateMasterSeq() {
        return rateMasterSeq;
    }

    public void setRateMasterSeq(Integer rateMasterSeq) {
        this.rateMasterSeq = rateMasterSeq;
    }

    @Basic
    @Column(name = "rate_type", nullable = false)
    public Integer getRateType() {
        return rateType;
    }

    public void setRateType(Integer rateType) {
        this.rateType = rateType;
    }

    @Basic
    @Column(name = "type_seq", nullable = false)
    public Integer getTypeSeq() {
        return typeSeq;
    }

    public void setTypeSeq(Integer typeSeq) {
        this.typeSeq = typeSeq;
    }

    @Basic
    @Column(name = "rate_value", nullable = false)
    public Double getRateValue() {
        return rateValue;
    }

    public void setRateValue(Double rateValue) {
        this.rateValue = rateValue;
    }

    @Basic
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    @Basic
    @CreatedDate
    @Column(name = "created_date", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @LastModifiedBy
    @Column(name = "last_modified_by")
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Basic
    @LastModifiedDate
    @Column(name = "last_modified_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pickup_location_seq", referencedColumnName = "final_destination_seq", insertable = false, updatable = false)
    public FinalDestination getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(FinalDestination pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_location_seq", referencedColumnName = "final_destination_seq", insertable = false, updatable = false)
    public FinalDestination getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(FinalDestination deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RateMasterDetail that = (RateMasterDetail) o;
        return Objects.equals(rateMasterDetailSeq, that.rateMasterDetailSeq) &&
                Objects.equals(rateMasterSeq, that.rateMasterSeq) &&
                Objects.equals(rateType, that.rateType) &&
                Objects.equals(typeSeq, that.typeSeq) &&
                Objects.equals(rateValue, that.rateValue) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rateMasterDetailSeq, rateMasterSeq, rateType, typeSeq, rateValue, status);
    }
}

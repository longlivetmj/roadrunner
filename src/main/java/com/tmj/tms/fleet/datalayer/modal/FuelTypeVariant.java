package com.tmj.tms.fleet.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "fuel_type_variant")
public class FuelTypeVariant {

    private Integer fuelTypeVariantSeq;
    private String variantName;
    private Integer countrySeq;
    private Integer fuelType;
    private Integer unitSeq;
    private Double unitPrice;
    private String remarks;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer status;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "fuel_type_variant_seq", allocationSize = 1)
    @Column(name = "fuel_type_variant_seq", nullable = false, unique = true)
    public Integer getFuelTypeVariantSeq() {
        return fuelTypeVariantSeq;
    }

    public void setFuelTypeVariantSeq(Integer fuelTypeVariantSeq) {
        this.fuelTypeVariantSeq = fuelTypeVariantSeq;
    }

    @Basic
    @Column(name = "variant_name", nullable = false)
    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }

    @Basic
    @Column(name = "country_seq", nullable = false)
    public Integer getCountrySeq() {
        return countrySeq;
    }

    public void setCountrySeq(Integer countrySeq) {
        this.countrySeq = countrySeq;
    }

    @Basic
    @Column(name = "fuel_type", nullable = false)
    public Integer getFuelType() {
        return fuelType;
    }

    public void setFuelType(Integer fuelType) {
        this.fuelType = fuelType;
    }

    @Basic
    @Column(name = "unit_seq", nullable = false)
    public Integer getUnitSeq() {
        return unitSeq;
    }

    public void setUnitSeq(Integer unitSeq) {
        this.unitSeq = unitSeq;
    }

    @Basic
    @Column(name = "unit_price", nullable = false)
    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Basic
    @Column(name = "remarks", length = 500)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FuelTypeVariant that = (FuelTypeVariant) o;
        return Objects.equals(fuelTypeVariantSeq, that.fuelTypeVariantSeq) &&
                Objects.equals(variantName, that.variantName) &&
                Objects.equals(countrySeq, that.countrySeq) &&
                Objects.equals(fuelType, that.fuelType) &&
                Objects.equals(unitSeq, that.unitSeq) &&
                Objects.equals(unitPrice, that.unitPrice) &&
                Objects.equals(remarks, that.remarks) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fuelTypeVariantSeq, variantName, countrySeq, fuelType, unitSeq, unitPrice, remarks, status);
    }
}

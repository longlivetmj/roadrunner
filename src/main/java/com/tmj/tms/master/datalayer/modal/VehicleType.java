package com.tmj.tms.master.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.config.utility.MasterDataStatus;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "VehicleType.default")
})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "vehicle_type")
public class VehicleType {

    private Integer vehicleTypeSeq;
    private String vehicleTypeName;
    private String description;
    private Double length;
    private Double height;
    private Double width;
    private Double maxCBM;
    private Double maxCBF;
    private Double maxWeight;
    private Integer vehicleTypeCategory;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer status;
    private String statusDescription;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "vehicle_type_seq", allocationSize = 1)
    @Column(name = "vehicle_type_seq", nullable = false)
    public Integer getVehicleTypeSeq() {
        return vehicleTypeSeq;
    }

    public void setVehicleTypeSeq(Integer vehicleTypeSeq) {
        this.vehicleTypeSeq = vehicleTypeSeq;
    }

    @Basic
    @Column(name = "vehicle_type_name", nullable = false, length = 200)
    public String getVehicleTypeName() {
        return vehicleTypeName;
    }

    public void setVehicleTypeName(String vehicleTypeName) {
        this.vehicleTypeName = vehicleTypeName;
    }

    @Basic
    @Column(name = "description", nullable = false, length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "length", length = 50)
    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    @Basic
    @Column(name = "height", length = 50)
    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    @Basic
    @Column(name = "width", length = 50)
    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    @Basic
    @Column(name = "max_cbm")
    public Double getMaxCBM() {
        return maxCBM;
    }

    public void setMaxCBM(Double maxCBM) {
        this.maxCBM = maxCBM;
    }

    @Basic
    @Column(name = "max_cbf")
    public Double getMaxCBF() {
        return maxCBF;
    }

    public void setMaxCBF(Double maxCBF) {
        this.maxCBF = maxCBF;
    }

    @Basic
    @Column(name = "max_weight")
    public Double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Double maxWeight) {
        this.maxWeight = maxWeight;
    }

    @Basic
    @Column(name = "vehicle_type_category")
    public Integer getVehicleTypeCategory() {
        return vehicleTypeCategory;
    }

    public void setVehicleTypeCategory(Integer vehicleTypeCategory) {
        this.vehicleTypeCategory = vehicleTypeCategory;
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
        if (status != null) {
            this.setStatusDescription(MasterDataStatus.findOne(status).getStatus());
        }
    }

    @Transient
    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleType that = (VehicleType) o;
        return Objects.equals(vehicleTypeSeq, that.vehicleTypeSeq) &&
                Objects.equals(vehicleTypeName, that.vehicleTypeName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(length, that.length) &&
                Objects.equals(height, that.height) &&
                Objects.equals(width, that.width) &&
                Objects.equals(maxCBM, that.maxCBM) &&
                Objects.equals(maxCBF, that.maxCBF) &&
                Objects.equals(maxWeight, that.maxWeight) &&
                Objects.equals(vehicleTypeCategory, that.vehicleTypeCategory) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleTypeSeq, vehicleTypeName, description, length, height, width, maxCBM, maxCBF, maxWeight, vehicleTypeCategory, status);
    }
}

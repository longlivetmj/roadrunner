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
@Table(name = "vehicle_modal")
public class VehicleModal {

    private Integer vehicleModalSeq;
    private Integer vehicleManufacturerSeq;
    private String modalName;
    private String description;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer status;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "vehicle_modal_seq", allocationSize = 1)
    @Column(name = "vehicle_modal_seq", nullable = false, unique = true)
    public Integer getVehicleModalSeq() {
        return vehicleModalSeq;
    }

    public void setVehicleModalSeq(Integer vehicleModalSeq) {
        this.vehicleModalSeq = vehicleModalSeq;
    }

    @Column(name = "vehicle_manufacturer_seq", nullable = false)
    public Integer getVehicleManufacturerSeq() {
        return vehicleManufacturerSeq;
    }

    public void setVehicleManufacturerSeq(Integer vehicleManufacturerSeq) {
        this.vehicleManufacturerSeq = vehicleManufacturerSeq;
    }

    @Column(name = "modal_name", nullable = false, length = 100)
    public String getModalName() {
        return modalName;
    }

    public void setModalName(String modalName) {
        this.modalName = modalName;
    }

    @Column(name = "description", length = 500)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        VehicleModal that = (VehicleModal) o;
        return Objects.equals(vehicleModalSeq, that.vehicleModalSeq) &&
                Objects.equals(vehicleManufacturerSeq, that.vehicleManufacturerSeq) &&
                Objects.equals(modalName, that.modalName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleModalSeq, vehicleManufacturerSeq, modalName, description, status);
    }
}

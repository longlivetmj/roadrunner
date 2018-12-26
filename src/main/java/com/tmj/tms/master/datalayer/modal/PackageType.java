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

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "package_type")
public class PackageType {
    private Integer packageTypeSeq;
    private String packageTypeName;
    private String packageTypeCode;
    private String asycudaCode;
    private String description;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer status;

    private String statusDescription;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "PACKAGE_TYPE_SEQ", allocationSize = 1)
    @Column(name = "package_type_seq", nullable = false, precision = 0, unique = true)
    public Integer getPackageTypeSeq() {
        return packageTypeSeq;
    }

    public void setPackageTypeSeq(Integer packageTypeSeq) {
        this.packageTypeSeq = packageTypeSeq;
    }

    @Basic
    @Column(name = "package_type_name", nullable = false, length = 50)
    public String getPackageTypeName() {
        return packageTypeName;
    }

    public void setPackageTypeName(String packageTypeName) {
        this.packageTypeName = packageTypeName;
    }

    @Basic
    @Column(name = "package_type_code", nullable = false, length = 50, unique = true)
    public String getPackageTypeCode() {
        return packageTypeCode;
    }

    public void setPackageTypeCode(String packageTypeCode) {
        this.packageTypeCode = packageTypeCode;
    }

    @Basic
    @Column(name = "asycuda_code", nullable = false, length = 50, unique = true)
    public String getAsycudaCode() {
        return asycudaCode;
    }

    public void setAsycudaCode(String asycudaCode) {
        this.asycudaCode = asycudaCode;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 50)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    @Column(name = "status", nullable = false, precision = 0)
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
        if (!(o instanceof PackageType)) return false;
        PackageType that = (PackageType) o;
        return Objects.equals(getPackageTypeSeq(), that.getPackageTypeSeq()) &&
                Objects.equals(getPackageTypeName(), that.getPackageTypeName()) &&
                Objects.equals(getPackageTypeCode(), that.getPackageTypeCode()) &&
                Objects.equals(getAsycudaCode(), that.getAsycudaCode()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getStatus(), that.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPackageTypeSeq(), getPackageTypeName(), getPackageTypeCode(), getAsycudaCode(), getDescription(), getStatus());
    }
}

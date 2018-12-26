package com.tmj.tms.master.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "stakeholder_type")
public class StakeholderType {
    private Integer stakeholderTypeSeq;
    private String stakeholderTypeName;
    private String v1CustomerTypeCode;
    private String stakeholderTypeCode;
    private Integer status;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "stakeholder_type_seq", allocationSize = 1)
    @Column(name = "stakeholder_type_seq", unique = true)
    public Integer getStakeholderTypeSeq() {
        return stakeholderTypeSeq;
    }

    public void setStakeholderTypeSeq(Integer stakeholderTypeSeq) {
        this.stakeholderTypeSeq = stakeholderTypeSeq;
    }

    @Basic
    @Column(name = "stakeholder_type_name")
    public String getStakeholderTypeName() {
        return stakeholderTypeName;
    }

    public void setStakeholderTypeName(String stakeholderTypeName) {
        this.stakeholderTypeName = stakeholderTypeName;
    }

    @Basic
    @Column(name = "v1_customer_type_code")
    public String getV1CustomerTypeCode() {
        return v1CustomerTypeCode;
    }

    public void setV1CustomerTypeCode(String v1CustomerTypeCode) {
        this.v1CustomerTypeCode = v1CustomerTypeCode;
    }

    @Basic
    @Column(name = "stakeholder_type_code")
    public String getStakeholderTypeCode() {
        return stakeholderTypeCode;
    }

    public void setStakeholderTypeCode(String stakeholderTypeCode) {
        this.stakeholderTypeCode = stakeholderTypeCode;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "created_by")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "created_date", nullable = true, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "last_modified_by")
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Basic
    @Column(name = "last_modified_date", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StakeholderType that = (StakeholderType) o;
        return Objects.equals(stakeholderTypeSeq, that.stakeholderTypeSeq) &&
                Objects.equals(stakeholderTypeName, that.stakeholderTypeName) &&
                Objects.equals(stakeholderTypeCode, that.stakeholderTypeCode) &&
                Objects.equals(status, that.status) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
                Objects.equals(lastModifiedDate, that.lastModifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stakeholderTypeSeq, stakeholderTypeName, stakeholderTypeCode, status, createdBy, createdDate, lastModifiedBy, lastModifiedDate);
    }
}

package com.tmj.tms.master.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employee_designation")
public class EmployeeDesignation {
    private Integer employeeDesignationSeq;
    private String designation;
    private String description;
    private Integer status;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "employee_designation_seq", allocationSize = 1)
    @Column(name = "employee_designation_seq", unique = true)
    public Integer getEmployeeDesignationSeq() {
        return employeeDesignationSeq;
    }

    public void setEmployeeDesignationSeq(Integer employeeDesignationSeq) {
        this.employeeDesignationSeq = employeeDesignationSeq;
    }

    @Basic
    @Column(name = "designation", length = 100, nullable = false)
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "status", nullable = true)
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
        if (!(o instanceof EmployeeDesignation)) return false;

        EmployeeDesignation that = (EmployeeDesignation) o;

        if (!getEmployeeDesignationSeq().equals(that.getEmployeeDesignationSeq())) return false;
        if (!getDescription().equals(that.getDescription())) return false;
        if (!getStatus().equals(that.getStatus())) return false;
        if (!getCreatedBy().equals(that.getCreatedBy())) return false;
        if (!getCreatedDate().equals(that.getCreatedDate())) return false;
        if (!getLastModifiedBy().equals(that.getLastModifiedBy())) return false;
        return getLastModifiedDate().equals(that.getLastModifiedDate());

    }

    @Override
    public int hashCode() {
        int result = getEmployeeDesignationSeq().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + getStatus().hashCode();
        result = 31 * result + getCreatedBy().hashCode();
        result = 31 * result + getCreatedDate().hashCode();
        result = 31 * result + getLastModifiedBy().hashCode();
        result = 31 * result + getLastModifiedDate().hashCode();
        return result;
    }
}

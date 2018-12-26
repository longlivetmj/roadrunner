package com.tmj.tms.finance.datalayer.modal;

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
@Table(name = "local_credit_note_ref_no_config")
public class LocalCreditNoteRefNoConfig {
    private Integer localCreditNoteRefNoConfigSeq;
    private Integer companyProfileSeq;
    private Integer departmentSeq;
    private String creditDebitStatus;
    private Integer nextValue;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer status;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "local_credit_note_ref_no_config_seq", allocationSize = 1)
    @Column(name = "local_credit_note_ref_no_config_seq", nullable = false, precision = 0, unique = true)
    public Integer getLocalCreditNoteRefNoConfigSeq() {
        return localCreditNoteRefNoConfigSeq;
    }

    public void setLocalCreditNoteRefNoConfigSeq(Integer localCreditNoteRefNoConfigSeq) {
        this.localCreditNoteRefNoConfigSeq = localCreditNoteRefNoConfigSeq;
    }

    @Basic
    @Column(name = "company_profile_seq", nullable = false)
    public Integer getCompanyProfileSeq() {
        return companyProfileSeq;
    }

    public void setCompanyProfileSeq(Integer companyProfileSeq) {
        this.companyProfileSeq = companyProfileSeq;
    }

    @Basic
    @Column(name = "department_seq", nullable = false)
    public Integer getDepartmentSeq() {
        return departmentSeq;
    }

    public void setDepartmentSeq(Integer departmentSeq) {
        this.departmentSeq = departmentSeq;
    }

    @Basic
    @Column(name = "credit_debit_status", nullable = false)
    public String getCreditDebitStatus() {
        return creditDebitStatus;
    }

    public void setCreditDebitStatus(String creditDebitStatus) {
        this.creditDebitStatus = creditDebitStatus;
    }

    @Basic
    @Column(name = "next_value", nullable = false)
    public Integer getNextValue() {
        return nextValue;
    }

    public void setNextValue(Integer nextValue) {
        this.nextValue = nextValue;
    }

    @Basic
    @CreatedBy
    @Column(name = "created_by")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @CreatedDate
    @Column(name = "created_date")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalCreditNoteRefNoConfig that = (LocalCreditNoteRefNoConfig) o;
        return Objects.equals(localCreditNoteRefNoConfigSeq, that.localCreditNoteRefNoConfigSeq) &&
                Objects.equals(companyProfileSeq, that.companyProfileSeq) &&
                Objects.equals(departmentSeq, that.departmentSeq) &&
                Objects.equals(creditDebitStatus, that.creditDebitStatus) &&
                Objects.equals(nextValue, that.nextValue) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
                Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(localCreditNoteRefNoConfigSeq, companyProfileSeq, departmentSeq, creditDebitStatus, nextValue, createdBy, createdDate, lastModifiedBy, lastModifiedDate, status);
    }
}

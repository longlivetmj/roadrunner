package com.tmj.tms.finance.datalayer.modal;

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
@Table(name = "local_invoice_no_config")
public class LocalInvoiceNoConfig {
    private Integer localInvoiceNoConfigSeq;
    private Integer departmentSeq;
    private Integer nextLocalInvoiceNo;
    private Integer nextExpenseVoucherNo;
    private String letterCode;
    private Integer companyProfileSeq;
    private Integer status;

    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "local_invoice_no_config_seq", allocationSize = 1)
    @Column(name = "local_invoice_no_config_seq", nullable = false, precision = 0, unique = true)
    public Integer getLocalInvoiceNoConfigSeq() {
        return localInvoiceNoConfigSeq;
    }

    public void setLocalInvoiceNoConfigSeq(Integer localInvoiceNoConfigSeq) {
        this.localInvoiceNoConfigSeq = localInvoiceNoConfigSeq;
    }

    @Basic
    @Column(name = "department_seq", nullable = true, precision = 0)
    public Integer getDepartmentSeq() {
        return departmentSeq;
    }

    public void setDepartmentSeq(Integer departmentSeq) {
        this.departmentSeq = departmentSeq;
    }

    @Basic
    @Column(name = "next_local_invoice_no", nullable = true, precision = 0)
    public Integer getNextLocalInvoiceNo() {
        return nextLocalInvoiceNo;
    }

    public void setNextLocalInvoiceNo(Integer nextLocalInvoiceNo) {
        this.nextLocalInvoiceNo = nextLocalInvoiceNo;
    }

    @Basic
    @Column(name = "next_expense_voucher_no", nullable = true, precision = 0)
    public Integer getNextExpenseVoucherNo() {
        return nextExpenseVoucherNo;
    }

    public void setNextExpenseVoucherNo(Integer nextExpenseVoucherNo) {
        this.nextExpenseVoucherNo = nextExpenseVoucherNo;
    }

    @Basic
    @Column(name = "letter_code", nullable = true, precision = 0)
    public String getLetterCode() {
        return letterCode;
    }

    public void setLetterCode(String letterCode) {
        this.letterCode = letterCode;
    }

    @Basic
    @Column(name = "company_profile_seq", nullable = true, precision = 0)
    public Integer getCompanyProfileSeq() {
        return companyProfileSeq;
    }

    public void setCompanyProfileSeq(Integer companyProfileSeq) {
        this.companyProfileSeq = companyProfileSeq;
    }

    @Basic
    @Column(name = "status", nullable = true, precision = 0)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalInvoiceNoConfig that = (LocalInvoiceNoConfig) o;
        return Objects.equals(localInvoiceNoConfigSeq, that.localInvoiceNoConfigSeq) &&
                Objects.equals(departmentSeq, that.departmentSeq) &&
                Objects.equals(nextLocalInvoiceNo, that.nextLocalInvoiceNo) &&
                Objects.equals(nextExpenseVoucherNo, that.nextExpenseVoucherNo) &&
                Objects.equals(letterCode, that.letterCode) &&
                Objects.equals(companyProfileSeq, that.companyProfileSeq) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(localInvoiceNoConfigSeq, departmentSeq, nextLocalInvoiceNo, nextExpenseVoucherNo, letterCode, companyProfileSeq, status);
    }
}

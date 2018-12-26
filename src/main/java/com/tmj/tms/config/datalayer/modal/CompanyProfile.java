package com.tmj.tms.config.datalayer.modal;

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
@Table(name = "company_profile")
public class CompanyProfile {
    private Integer companyProfileSeq;
    private String companyName;
    private String shortCode;
    private String description;
    private String financialYear;
    private Integer localCurrencySeq;
    private Integer foreignCurrencySeq;
    private Integer defaultTransporterSeq;
    private Integer addressBookSeq;
    private String vatNo;
    private String svatNo;
    private Integer uploadDocumentSeq;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer status;

    private String statusDesc;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "company_profile_seq", allocationSize = 1)
    @Column(name = "company_profile_seq", nullable = false)
    public Integer getCompanyProfileSeq() {
        return companyProfileSeq;
    }

    public void setCompanyProfileSeq(Integer companyProfileSeq) {
        this.companyProfileSeq = companyProfileSeq;
    }

    @Basic
    @Column(name = "company_name", nullable = false, length = 500)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyProfileName) {
        this.companyName = companyProfileName;
    }

    @Basic
    @Column(name = "short_code", nullable = false, length = 10)
    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    @Basic
    @Column(name = "description", length = 512)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "financial_year", nullable = false, length = 10)
    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    @Basic
    @Column(name = "local_currency_seq")
    public Integer getLocalCurrencySeq() {
        return localCurrencySeq;
    }

    public void setLocalCurrencySeq(Integer localCurrencySeq) {
        this.localCurrencySeq = localCurrencySeq;
    }

    @Basic
    @Column(name = "default_transporter_seq")
    public Integer getDefaultTransporterSeq() {
        return defaultTransporterSeq;
    }

    public void setDefaultTransporterSeq(Integer defaultTransporterSeq) {
        this.defaultTransporterSeq = defaultTransporterSeq;
    }

    @Basic
    @Column(name = "foreign_currency_seq")
    public Integer getForeignCurrencySeq() {
        return foreignCurrencySeq;
    }

    public void setForeignCurrencySeq(Integer foreignCurrencySeq) {
        this.foreignCurrencySeq = foreignCurrencySeq;
    }

    @Basic
    @Column(name = "address_book_seq")
    public Integer getAddressBookSeq() {
        return addressBookSeq;
    }

    public void setAddressBookSeq(Integer addressBookSeq) {
        this.addressBookSeq = addressBookSeq;
    }

    @Basic
    @Column(name = "vat_no")
    public String getVatNo() {
        return vatNo;
    }

    public void setVatNo(String vatNo) {
        this.vatNo = vatNo;
    }

    @Basic
    @Column(name = "svat_no")
    public String getSvatNo() {
        return svatNo;
    }

    public void setSvatNo(String svatNo) {
        this.svatNo = svatNo;
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
        this.setStatusDesc(MasterDataStatus.findOne(status).getStatus());
    }

    @Basic
    @Column(name = "upload_document_seq")
    public Integer getUploadDocumentSeq() {
        return uploadDocumentSeq;
    }

    public void setUploadDocumentSeq(Integer uploadDocumentSeq) {
        this.uploadDocumentSeq = uploadDocumentSeq;
    }

    @Transient
    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyProfile that = (CompanyProfile) o;
        return Objects.equals(companyProfileSeq, that.companyProfileSeq) &&
                Objects.equals(companyName, that.companyName) &&
                Objects.equals(shortCode, that.shortCode) &&
                Objects.equals(description, that.description) &&
                Objects.equals(financialYear, that.financialYear) &&
                Objects.equals(defaultTransporterSeq, that.defaultTransporterSeq) &&
                Objects.equals(localCurrencySeq, that.localCurrencySeq) &&
                Objects.equals(foreignCurrencySeq, that.foreignCurrencySeq) &&
                Objects.equals(addressBookSeq, that.addressBookSeq) &&
                Objects.equals(vatNo, that.vatNo) &&
                Objects.equals(svatNo, that.svatNo) &&
                Objects.equals(uploadDocumentSeq, that.uploadDocumentSeq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyProfileSeq, companyName, shortCode, description, financialYear, localCurrencySeq, defaultTransporterSeq, foreignCurrencySeq, addressBookSeq, vatNo, svatNo, uploadDocumentSeq);
    }
}

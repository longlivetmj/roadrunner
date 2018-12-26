package com.tmj.tms.master.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.config.utility.MasterDataStatus;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "TaxRegistration.default", attributeNodes = {
                @NamedAttributeNode("country"),
                @NamedAttributeNode("taxType")
        })
})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tax_registration")
public class TaxRegistration implements Serializable {
    private Integer taxRegistrationSeq;
    private Integer companyProfileSeq;
    private Integer countrySeq;
    private String taxName;
    private String remarks;
    private Integer status;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Double taxRate;
    private String taxRateDisplay;
    private Integer taxTypeSeq;

    private Country country;
    private TaxType taxType;

    private String statusDescription;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "tax_registration_seq", allocationSize = 1)
    @Column(name = "tax_registration_seq", nullable = false, precision = 0)
    public Integer getTaxRegistrationSeq() {
        return taxRegistrationSeq;
    }

    public void setTaxRegistrationSeq(Integer taxRegistrationSeq) {
        this.taxRegistrationSeq = taxRegistrationSeq;
    }

    @Basic
    @Column(name = "company_profile_seq", nullable = false, precision = 0)
    public Integer getCompanyProfileSeq() {
        return companyProfileSeq;
    }

    public void setCompanyProfileSeq(Integer companyProfileSeq) {
        this.companyProfileSeq = companyProfileSeq;
    }

    @Basic
    @Column(name = "country_seq", nullable = false, precision = 0)
    public Integer getCountrySeq() {
        return countrySeq;
    }

    public void setCountrySeq(Integer countrySeq) {
        this.countrySeq = countrySeq;
    }

    @Basic
    @Column(name = "tax_name", nullable = false, length = 50, unique = true)
    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    @Basic
    @Column(name = "remarks", nullable = true, length = 150)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Basic
    @Column(name = "status", nullable = true, precision = 0)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
        if (status != null) {
            this.setStatusDescription(MasterDataStatus.findOne(status).getStatus());
        }
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_seq", insertable = false, updatable = false)
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Basic
    @Column(name = "tax_rate", nullable = false, precision = 0)
    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    @Basic
    @Column(name = "tax_rate_display")
    public String getTaxRateDisplay() {
        return taxRateDisplay;
    }

    public void setTaxRateDisplay(String taxRateDisplay) {
        this.taxRateDisplay = taxRateDisplay;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tax_type_seq", insertable = false, updatable = false)
    public TaxType getTaxType() {
        return taxType;
    }

    public void setTaxType(TaxType taxType) {
        this.taxType = taxType;
    }

    @Basic
    @Column(name = "tax_type_seq", nullable = false, precision = 0)
    public Integer getTaxTypeSeq() {
        return taxTypeSeq;
    }

    public void setTaxTypeSeq(Integer taxTypeSeq) {
        this.taxTypeSeq = taxTypeSeq;
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
        if (!(o instanceof TaxRegistration)) return false;
        TaxRegistration that = (TaxRegistration) o;
        return Objects.equals(getTaxRegistrationSeq(), that.getTaxRegistrationSeq()) &&
                Objects.equals(getCompanyProfileSeq(), that.getCompanyProfileSeq()) &&
                Objects.equals(getCountrySeq(), that.getCountrySeq()) &&
                Objects.equals(getTaxName(), that.getTaxName()) &&
                Objects.equals(getRemarks(), that.getRemarks()) &&
                Objects.equals(getStatus(), that.getStatus()) &&
                Objects.equals(getTaxRate(), that.getTaxRate()) &&
                Objects.equals(getTaxTypeSeq(), that.getTaxTypeSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTaxRegistrationSeq(), getCompanyProfileSeq(), getCountrySeq(), getTaxName(), getRemarks(), getStatus(), getTaxRate(), getTaxTypeSeq());
    }
}

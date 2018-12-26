package com.tmj.tms.config.datalayer.modal;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "company_module")
public class CompanyModule {
    private Integer companyModuleSeq;
    private Integer companyProfileSeq;
    private Integer moduleSeq;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    private Integer status;

    private Module module;

    private Integer defaultBankSeq;
    private Integer defaultCurrencySeq;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "company_module_seq", allocationSize = 1)
    @Column(name = "company_module_seq", nullable = false, precision = 0, unique = true)
    public Integer getCompanyModuleSeq() {
        return companyModuleSeq;
    }

    public void setCompanyModuleSeq(Integer companyModuleSeq) {
        this.companyModuleSeq = companyModuleSeq;
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
    @Column(name = "module_seq", nullable = false, precision = 0)
    public Integer getModuleSeq() {
        return moduleSeq;
    }

    public void setModuleSeq(Integer moduleSeq) {
        this.moduleSeq = moduleSeq;
    }

    @Basic
    @Column(name = "created_by", nullable = true, length = 50)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "created_date", nullable = true)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "modified_by", nullable = true, length = 50)
    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Basic
    @Column(name = "modified_date", nullable = true)
    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Basic
    @Column(name = "status", nullable = true, precision = 0)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "module_seq", insertable = false, updatable = false, nullable = false)
    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    @Basic
    @Column(name = "default_bank_seq")
    public Integer getDefaultBankSeq() {
        return defaultBankSeq;
    }

    public void setDefaultBankSeq(Integer defaultBankSeq) {
        this.defaultBankSeq = defaultBankSeq;
    }

    @Basic
    @Column(name = "default_currency_seq")
    public Integer getDefaultCurrencySeq() {
        return defaultCurrencySeq;
    }

    public void setDefaultCurrencySeq(Integer defaultCurrencySeq) {
        this.defaultCurrencySeq = defaultCurrencySeq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyModule that = (CompanyModule) o;
        return java.util.Objects.equals(companyModuleSeq, that.companyModuleSeq) &&
                java.util.Objects.equals(companyProfileSeq, that.companyProfileSeq) &&
                java.util.Objects.equals(moduleSeq, that.moduleSeq) &&
                java.util.Objects.equals(createdBy, that.createdBy) &&
                java.util.Objects.equals(createdDate, that.createdDate) &&
                java.util.Objects.equals(modifiedBy, that.modifiedBy) &&
                java.util.Objects.equals(modifiedDate, that.modifiedDate) &&
                java.util.Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(companyModuleSeq, companyProfileSeq, moduleSeq, createdBy, createdDate, modifiedBy, modifiedDate, status);
    }
}

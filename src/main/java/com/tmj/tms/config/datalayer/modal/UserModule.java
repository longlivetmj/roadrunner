package com.tmj.tms.config.datalayer.modal;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_module")
public class UserModule {
    private Integer userModuleSeq;
    private Integer userSeq;
    private Integer companyModuleSeq;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    private Integer status;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "user_module_seq", allocationSize = 1)
    @Column(name = "user_module_seq", nullable = false, precision = 0, unique = true)
    public Integer getUserModuleSeq() {
        return userModuleSeq;
    }

    public void setUserModuleSeq(Integer userModuleSeq) {
        this.userModuleSeq = userModuleSeq;
    }

    @Basic
    @Column(name = "user_seq", nullable = false, precision = 0)
    public Integer getUserSeq() {
        return userSeq;
    }

    public void setUserSeq(Integer userSeq) {
        this.userSeq = userSeq;
    }

    @Basic
    @Column(name = "company_module_seq", nullable = false, precision = 0)
    public Integer getCompanyModuleSeq() {
        return companyModuleSeq;
    }

    public void setCompanyModuleSeq(Integer companyModuleSeq) {
        this.companyModuleSeq = companyModuleSeq;
    }

    @Basic
    @Column(name = "created_by", nullable = true, length = 50, updatable = false)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "created_date", nullable = true, updatable = false)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserModule)) return false;

        UserModule that = (UserModule) o;

        if (getUserModuleSeq() != null ? !getUserModuleSeq().equals(that.getUserModuleSeq()) : that.getUserModuleSeq() != null)
            return false;
        if (getUserSeq() != null ? !getUserSeq().equals(that.getUserSeq()) : that.getUserSeq() != null) return false;
        if (getCompanyModuleSeq() != null ? !getCompanyModuleSeq().equals(that.getCompanyModuleSeq()) : that.getCompanyModuleSeq() != null)
            return false;
        if (getCreatedBy() != null ? !getCreatedBy().equals(that.getCreatedBy()) : that.getCreatedBy() != null)
            return false;
        if (getCreatedDate() != null ? !getCreatedDate().equals(that.getCreatedDate()) : that.getCreatedDate() != null)
            return false;
        if (getModifiedBy() != null ? !getModifiedBy().equals(that.getModifiedBy()) : that.getModifiedBy() != null)
            return false;
        if (getModifiedDate() != null ? !getModifiedDate().equals(that.getModifiedDate()) : that.getModifiedDate() != null)
            return false;
        return getStatus() != null ? getStatus().equals(that.getStatus()) : that.getStatus() == null;

    }

    @Override
    public int hashCode() {
        int result = getUserModuleSeq() != null ? getUserModuleSeq().hashCode() : 0;
        result = 31 * result + (getUserSeq() != null ? getUserSeq().hashCode() : 0);
        result = 31 * result + (getCompanyModuleSeq() != null ? getCompanyModuleSeq().hashCode() : 0);
        result = 31 * result + (getCreatedBy() != null ? getCreatedBy().hashCode() : 0);
        result = 31 * result + (getCreatedDate() != null ? getCreatedDate().hashCode() : 0);
        result = 31 * result + (getModifiedBy() != null ? getModifiedBy().hashCode() : 0);
        result = 31 * result + (getModifiedDate() != null ? getModifiedDate().hashCode() : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        return result;
    }
}

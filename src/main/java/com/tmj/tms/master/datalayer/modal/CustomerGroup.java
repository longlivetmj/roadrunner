package com.tmj.tms.master.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.utility.MasterDataStatus;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "CustomerGroup.default", attributeNodes = {
                @NamedAttributeNode("companyProfile")
        })
})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "customer_group")
public class CustomerGroup {
    private Integer customerGroupSeq;
    private String customerGroupCode;
    private String customerGroupName;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer companyProfileSeq;
    private Integer status;
    private String statusDescription;

    private CompanyProfile companyProfile;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "customer_group_seq", allocationSize = 1)
    @Column(name = "customer_group_seq", unique = true)
    public Integer getCustomerGroupSeq() {
        return customerGroupSeq;
    }

    public void setCustomerGroupSeq(Integer customerGroupSeq) {
        this.customerGroupSeq = customerGroupSeq;
    }

    @Basic
    @Column(name = "customer_group_code", length = 15, nullable = false)
    public String getCustomerGroupCode() {
        return customerGroupCode;
    }

    public void setCustomerGroupCode(String customerGroupCode) {
        this.customerGroupCode = customerGroupCode;
    }

    @Basic
    @Column(name = "customer_group_name", length = 50, nullable = false)
    public String getCustomerGroupName() {
        return customerGroupName;
    }

    public void setCustomerGroupName(String customerGroupName) {
        this.customerGroupName = customerGroupName;
    }

    @Basic
    @Column(name = "status", nullable = true)
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
    @Column(name = "created_by", length = 50, nullable = false, updatable = false)
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
    @Column(name = "last_modified_by", length = 50, nullable = true)
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Basic
    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_profile_seq",insertable = false, updatable = false, nullable = false)
    public CompanyProfile getCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(CompanyProfile companyProfile) {
        this.companyProfile = companyProfile;
    }

    @Basic
    @Column(name = "company_profile_seq", updatable = false,nullable = false)
    public Integer getCompanyProfileSeq() {
        return companyProfileSeq;
    }

    public void setCompanyProfileSeq(Integer companyProfileSeq) {
        this.companyProfileSeq = companyProfileSeq;
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
        if (!(o instanceof CustomerGroup)) return false;
        CustomerGroup that = (CustomerGroup) o;
        return Objects.equals(getCustomerGroupSeq(), that.getCustomerGroupSeq()) &&
                Objects.equals(getCustomerGroupCode(), that.getCustomerGroupCode()) &&
                Objects.equals(getCustomerGroupName(), that.getCustomerGroupName()) &&
                Objects.equals(getCompanyProfileSeq(), that.getCompanyProfileSeq()) &&
                Objects.equals(getStatus(), that.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomerGroupSeq(), getCustomerGroupCode(), getCustomerGroupName(), getCompanyProfileSeq(), getStatus());
    }
}

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
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "charge")
public class Charge {
    private Integer chargeSeq;
    private Integer companyProfileSeq;
    private String chargeName;
    private String description;
    private Integer status;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private String statusDescription;
    private Integer financeIntegration;
    private Long financeIntegrationKey;
    private Set<ChargeMode> chargeModes = new HashSet<ChargeMode>(0);

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "charge_seq", allocationSize = 1)
    @Column(name = "charge_seq", nullable = false, precision = 0, unique = true)
    public Integer getChargeSeq() {
        return chargeSeq;
    }

    public void setChargeSeq(Integer chargeSeq) {
        this.chargeSeq = chargeSeq;
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
    @Column(name = "charge_name", nullable = false, length = 100, unique = true)
    public String getChargeName() {
        return chargeName;
    }

    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 150)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    @Column(name = "created_by", nullable = false, length = 50)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @CreatedDate
    @Column(name = "created_date", nullable = false)
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
    @Column(name = "finance_integration")
    public Integer getFinanceIntegration() {
        return financeIntegration;
    }

    public void setFinanceIntegration(Integer financeIntegration) {
        this.financeIntegration = financeIntegration;
    }

    @Basic
    @Column(name = "finance_integration_key")
    public Long getFinanceIntegrationKey() {
        return financeIntegrationKey;
    }

    public void setFinanceIntegrationKey(Long financeIntegrationKey) {
        this.financeIntegrationKey = financeIntegrationKey;
    }

    @Transient
    public Set<ChargeMode> getChargeModes() {
        return chargeModes;
    }

    public void setChargeModes(Set<ChargeMode> chargeModes) {
        this.chargeModes = chargeModes;
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
        if (o == null || getClass() != o.getClass()) return false;
        Charge charge = (Charge) o;
        return Objects.equals(chargeSeq, charge.chargeSeq) &&
                Objects.equals(chargeName, charge.chargeName) &&
                Objects.equals(status, charge.status) &&
                Objects.equals(companyProfileSeq, charge.companyProfileSeq) &&
                Objects.equals(chargeModes, charge.chargeModes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chargeSeq, chargeName, status, companyProfileSeq, chargeModes);
    }
}

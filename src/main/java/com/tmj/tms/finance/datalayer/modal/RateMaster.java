package com.tmj.tms.finance.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.datalayer.modal.Charge;
import com.tmj.tms.master.datalayer.modal.Currency;
import com.tmj.tms.master.datalayer.modal.Stakeholder;
import com.tmj.tms.master.datalayer.modal.Unit;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "rate_master")
public class RateMaster {

    private Integer rateMasterSeq;
    private Integer companyProfileSeq;
    private Integer stakeholderSeq;
    private Integer currencySeq;
    private Integer chargeSeq;
    private Integer chargeType;
    private Double minimumCharge;
    private Double thresholdValue;
    private Integer multiplyType;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer status;

    private List<RateMasterDetail> rateMasterDetailList = new ArrayList<>();
    private List<RateMasterStakeholder> rateMasterStakeholderList;

    private String statusDescription;

    private Stakeholder stakeholder;
    private Currency currency;
    private Charge charge;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "rate_master_seq", allocationSize = 1)
    @Column(name = "rate_master_seq", unique = true)
    public Integer getRateMasterSeq() {
        return rateMasterSeq;
    }

    public void setRateMasterSeq(Integer rateMasterSeq) {
        this.rateMasterSeq = rateMasterSeq;
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
    @Column(name = "stakeholder_seq", nullable = false)
    public Integer getStakeholderSeq() {
        return stakeholderSeq;
    }

    public void setStakeholderSeq(Integer stakeholderSeq) {
        this.stakeholderSeq = stakeholderSeq;
    }

    @Basic
    @Column(name = "currency_seq", nullable = false)
    public Integer getCurrencySeq() {
        return currencySeq;
    }

    public void setCurrencySeq(Integer currencySeq) {
        this.currencySeq = currencySeq;
    }

    @Basic
    @Column(name = "charge_seq", nullable = false)
    public Integer getChargeSeq() {
        return chargeSeq;
    }

    public void setChargeSeq(Integer chargeSeq) {
        this.chargeSeq = chargeSeq;
    }

    @Basic
    @Column(name = "charge_type", nullable = false)
    public Integer getChargeType() {
        return chargeType;
    }

    public void setChargeType(Integer chargeType) {
        this.chargeType = chargeType;
    }

    @Basic
    @Column(name = "minimum_charge", nullable = false)
    public Double getMinimumCharge() {
        return minimumCharge;
    }

    public void setMinimumCharge(Double minimumCharge) {
        this.minimumCharge = minimumCharge;
    }

    @Basic
    @Column(name = "multiply_type", nullable = false)
    public Integer getMultiplyType() {
        return multiplyType;
    }

    public void setMultiplyType(Integer multiplyType) {
        this.multiplyType = multiplyType;
    }

    @Basic
    @Column(name = "threshold_value")
    public Double getThresholdValue() {
        return thresholdValue;
    }

    public void setThresholdValue(Double thresholdValue) {
        this.thresholdValue = thresholdValue;
    }


    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "rate_master_seq", nullable = false)
    public List<RateMasterDetail> getRateMasterDetailList() {
        return rateMasterDetailList;
    }

    public void setRateMasterDetailList(List<RateMasterDetail> rateMasterDetailList) {
        this.rateMasterDetailList = rateMasterDetailList;
    }

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "rate_master_seq")
    public List<RateMasterStakeholder> getRateMasterStakeholderList() {
        return rateMasterStakeholderList;
    }

    public void setRateMasterStakeholderList(List<RateMasterStakeholder> rateMasterStakeholderList) {
        this.rateMasterStakeholderList = rateMasterStakeholderList;
    }

    @Basic
    @CreatedBy
    @Column(name = "created_by", nullable = true, updatable = false)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @CreatedDate
    @Column(name = "created_date", nullable = true, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @LastModifiedBy
    @Column(name = "last_modified_by", nullable = true)
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

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "stakeholder_seq", insertable = false, updatable = false)
    public Stakeholder getStakeholder() {
        return stakeholder;
    }

    public void setStakeholder(Stakeholder stakeholder) {
        this.stakeholder = stakeholder;
    }

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_seq", insertable = false, updatable = false)
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "charge_seq", insertable = false, updatable = false)
    public Charge getCharge() {
        return charge;
    }

    public void setCharge(Charge charge) {
        this.charge = charge;
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
        RateMaster that = (RateMaster) o;
        return Objects.equals(rateMasterSeq, that.rateMasterSeq) &&
                Objects.equals(companyProfileSeq, that.companyProfileSeq) &&
                Objects.equals(stakeholderSeq, that.stakeholderSeq) &&
                Objects.equals(currencySeq, that.currencySeq) &&
                Objects.equals(chargeSeq, that.chargeSeq) &&
                Objects.equals(chargeType, that.chargeType) &&
                Objects.equals(minimumCharge, that.minimumCharge) &&
                Objects.equals(thresholdValue, that.thresholdValue) &&
                Objects.equals(multiplyType, that.multiplyType) &&
                Objects.equals(status, that.status) &&
                Objects.equals(rateMasterDetailList, that.rateMasterDetailList) &&
                Objects.equals(rateMasterStakeholderList, that.rateMasterStakeholderList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rateMasterSeq, companyProfileSeq, stakeholderSeq, currencySeq, chargeSeq, chargeType, minimumCharge, thresholdValue, multiplyType, status, rateMasterDetailList, rateMasterStakeholderList);
    }
}

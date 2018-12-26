package com.tmj.tms.home.datalayer.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "temp_revenue_and_cost")
public class TempRevenueAndCost {
    private Integer tempRevenueAndCostSeq;
    private Integer referenceSeq;
    private Integer companyProfileSeq;
    private Integer moduleSeq;
    private Integer chargeType;
    private Double amount;
    private Date createdDate;
    private Date transactionDate;
    private Integer financeDocumentCount;
    private Double financeValue;
    private Integer stakeholderSeq;
    private Integer currentStatus;
    private Integer pickupLocationSeq;
    private Integer deliveryLocationSeq;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "temp_revenue_and_cost_seq", allocationSize = 1)
    @Column(name = "temp_revenue_and_cost_seq", unique = true)
    public Integer getTempRevenueAndCostSeq() {
        return tempRevenueAndCostSeq;
    }

    public void setTempRevenueAndCostSeq(Integer tempRevenueAndCostSeq) {
        this.tempRevenueAndCostSeq = tempRevenueAndCostSeq;
    }

    @Basic
    @Column(name = "reference_seq", nullable = false)
    public Integer getReferenceSeq() {
        return referenceSeq;
    }

    public void setReferenceSeq(Integer referenceSeq) {
        this.referenceSeq = referenceSeq;
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
    @Column(name = "module_seq", nullable = false)
    public Integer getModuleSeq() {
        return moduleSeq;
    }

    public void setModuleSeq(Integer moduleSeq) {
        this.moduleSeq = moduleSeq;
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
    @Column(name = "amount", nullable = false)
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "created_date", nullable = false)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "transaction_date", nullable = false)
    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Basic
    @Column(name = "finance_document_count")
    public Integer getFinanceDocumentCount() {
        return financeDocumentCount;
    }

    public void setFinanceDocumentCount(Integer financeDocumentCount) {
        this.financeDocumentCount = financeDocumentCount;
    }

    @Basic
    @Column(name = "finance_document_value")
    public Double getFinanceValue() {
        return financeValue;
    }

    public void setFinanceValue(Double financeValue) {
        this.financeValue = financeValue;
    }

    @Basic
    @Column(name = "stakeholder_seq")
    public Integer getStakeholderSeq() {
        return stakeholderSeq;
    }

    public void setStakeholderSeq(Integer stakeholderSeq) {
        this.stakeholderSeq = stakeholderSeq;
    }

    @Basic
    @Column(name = "current_status")
    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    @Basic
    @Column(name = "pickup_location_seq")
    public Integer getPickupLocationSeq() {
        return pickupLocationSeq;
    }

    public void setPickupLocationSeq(Integer pickupLocationSeq) {
        this.pickupLocationSeq = pickupLocationSeq;
    }

    @Basic
    @Column(name = "delivery_location_seq")
    public Integer getDeliveryLocationSeq() {
        return deliveryLocationSeq;
    }

    public void setDeliveryLocationSeq(Integer deliveryLocationSeq) {
        this.deliveryLocationSeq = deliveryLocationSeq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TempRevenueAndCost that = (TempRevenueAndCost) o;
        return Objects.equals(tempRevenueAndCostSeq, that.tempRevenueAndCostSeq) &&
                Objects.equals(referenceSeq, that.referenceSeq) &&
                Objects.equals(companyProfileSeq, that.companyProfileSeq) &&
                Objects.equals(moduleSeq, that.moduleSeq) &&
                Objects.equals(chargeType, that.chargeType) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(transactionDate, that.transactionDate) &&
                Objects.equals(financeDocumentCount, that.financeDocumentCount) &&
                Objects.equals(financeValue, that.financeValue) &&
                Objects.equals(stakeholderSeq, that.stakeholderSeq) &&
                Objects.equals(currentStatus, that.currentStatus) &&
                Objects.equals(pickupLocationSeq, that.pickupLocationSeq) &&
                Objects.equals(deliveryLocationSeq, that.deliveryLocationSeq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tempRevenueAndCostSeq, referenceSeq, companyProfileSeq, moduleSeq, chargeType, amount, createdDate, transactionDate, financeDocumentCount, financeValue, stakeholderSeq, currentStatus, pickupLocationSeq, deliveryLocationSeq);
    }
}

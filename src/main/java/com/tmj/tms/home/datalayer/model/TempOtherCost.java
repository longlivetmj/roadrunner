package com.tmj.tms.home.datalayer.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "temp_other_cost")
public class TempOtherCost {
    private Integer tempOtherCostSeq;
    private Integer costType;
    private Integer companyProfileSeq;
    private Integer referenceSeq;
    private Double amount;
    private Date transactionDate;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "temp_other_cost_seq", allocationSize = 1)
    @Column(name = "temp_other_cost_seq", unique = true)
    public Integer getTempOtherCostSeq() {
        return tempOtherCostSeq;
    }

    public void setTempOtherCostSeq(Integer tempOtherCostSeq) {
        this.tempOtherCostSeq = tempOtherCostSeq;
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
    @Column(name = "reference_seq", nullable = false)
    public Integer getReferenceSeq() {
        return referenceSeq;
    }

    public void setReferenceSeq(Integer referenceSeq) {
        this.referenceSeq = referenceSeq;
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
    @Column(name = "cost_type", nullable = false)
    public Integer getCostType() {
        return costType;
    }

    public void setCostType(Integer costType) {
        this.costType = costType;
    }

    @Basic
    @Column(name = "transaction_date", nullable = false)
    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }


}

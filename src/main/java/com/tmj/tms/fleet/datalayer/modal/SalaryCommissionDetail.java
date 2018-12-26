package com.tmj.tms.fleet.datalayer.modal;

import com.tmj.tms.config.datalayer.modal.Module;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "salary_commission_details")
public class SalaryCommissionDetail {

    private Integer salaryCommissionDetailSeq;
    private Integer salaryCommissionSeq;
    private Integer moduleSeq;
    private Integer financialChargeDetailSeq;
    private Integer targetType;
    private Integer referenceType;
    private Integer referenceSeq;
    private String referenceNo;
    private Double amount;
    private Double totalKm;
    private Double rate;
    private Integer status;

    private Module module;

    private String referenceTypeDescription;

    private TransportBooking transportBooking;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "salary_commission_detail_seq", allocationSize = 1)
    @Column(name = "salary_commission_detail_seq", nullable = false, unique = true)

    public Integer getSalaryCommissionDetailSeq() {
        return salaryCommissionDetailSeq;
    }

    public void setSalaryCommissionDetailSeq(Integer salaryCommissionDetailSeq) {
        this.salaryCommissionDetailSeq = salaryCommissionDetailSeq;
    }

    @Basic
    @Column(name = "salary_commission_seq", nullable = false, updatable = false, insertable = false)
    public Integer getSalaryCommissionSeq() {
        return salaryCommissionSeq;
    }

    public void setSalaryCommissionSeq(Integer salaryCommissionSeq) {
        this.salaryCommissionSeq = salaryCommissionSeq;
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
    @Column(name = "financial_charge_detail_seq", nullable = false)
    public Integer getFinancialChargeDetailSeq() {
        return financialChargeDetailSeq;
    }

    public void setFinancialChargeDetailSeq(Integer financialChargeDetailSeq) {
        this.financialChargeDetailSeq = financialChargeDetailSeq;
    }

    @Basic
    @Column(name = "target_type", nullable = false)
    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    @Basic
    @Column(name = "reference_type", nullable = false)
    public Integer getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(Integer referenceType) {
        this.referenceType = referenceType;
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
    @Column(name = "reference_no", nullable = false)
    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
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
    @Column(name = "total_km", nullable = false)
    public Double getTotalKm() {
        return totalKm;
    }

    public void setTotalKm(Double totalKm) {
        this.totalKm = totalKm;
    }

    @Basic
    @Column(name = "rate", nullable = false)
    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_seq", insertable = false, updatable = false)
    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    @Transient
    public String getReferenceTypeDescription() {
        return referenceTypeDescription;
    }

    public void setReferenceTypeDescription(String referenceTypeDescription) {
        this.referenceTypeDescription = referenceTypeDescription;
    }

    @Transient
    public TransportBooking getTransportBooking() {
        return transportBooking;
    }

    public void setTransportBooking(TransportBooking transportBooking) {
        this.transportBooking = transportBooking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalaryCommissionDetail that = (SalaryCommissionDetail) o;
        return Objects.equals(salaryCommissionDetailSeq, that.salaryCommissionDetailSeq) &&
                Objects.equals(salaryCommissionSeq, that.salaryCommissionSeq) &&
                Objects.equals(moduleSeq, that.moduleSeq) &&
                Objects.equals(targetType, that.targetType) &&
                Objects.equals(referenceType, that.referenceType) &&
                Objects.equals(referenceSeq, that.referenceSeq) &&
                Objects.equals(referenceNo, that.referenceNo) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(salaryCommissionDetailSeq, salaryCommissionSeq, moduleSeq, targetType, referenceType, referenceSeq, referenceNo, amount, status);
    }
}

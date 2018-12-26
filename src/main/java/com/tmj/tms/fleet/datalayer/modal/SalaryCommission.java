package com.tmj.tms.fleet.datalayer.modal;

import com.tmj.tms.fleet.utility.CalculationType;
import com.tmj.tms.master.datalayer.modal.Charge;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "salary_commission")
public class SalaryCommission {

    private Integer salaryCommissionSeq;
    private Integer salarySeq;
    private Integer chargeSeq;
    private Integer payrollCommissionSeq;
    private Integer calculationType;
    private Double chargeValue;
    private Double multiplyValue;
    private Double commission;
    private Integer status;

    private List<SalaryCommissionDetail> salaryCommissionDetailList;

    private Charge charge;
    private String calculationTypeDescription;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "salary_commission_seq", allocationSize = 1)
    @Column(name = "salary_commission_seq", nullable = false, unique = true)
    public Integer getSalaryCommissionSeq() {
        return salaryCommissionSeq;
    }

    public void setSalaryCommissionSeq(Integer salaryCommissionSeq) {
        this.salaryCommissionSeq = salaryCommissionSeq;
    }

    @Basic
    @Column(name = "salary_seq", nullable = false, updatable = false, insertable = false)
    public Integer getSalarySeq() {
        return salarySeq;
    }

    public void setSalarySeq(Integer salarySeq) {
        this.salarySeq = salarySeq;
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
    @Column(name = "charge_value", nullable = false)
    public Double getChargeValue() {
        return chargeValue;
    }

    public void setChargeValue(Double chargeValue) {
        this.chargeValue = chargeValue;
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
    @JoinColumn(name = "charge_seq", insertable = false, updatable = false)
    public Charge getCharge() {
        return charge;
    }

    public void setCharge(Charge charge) {
        this.charge = charge;
    }

    @Basic
    @Column(name = "payroll_commission_seq", nullable = false)
    public Integer getPayrollCommissionSeq() {
        return payrollCommissionSeq;
    }

    public void setPayrollCommissionSeq(Integer payrollCommissionSeq) {
        this.payrollCommissionSeq = payrollCommissionSeq;
    }

    @Basic
    @Column(name = "calculation_type", nullable = false)
    public Integer getCalculationType() {
        return calculationType;
    }

    public void setCalculationType(Integer calculationType) {
        this.calculationType = calculationType;
        if (calculationType != null) {
            this.setCalculationTypeDescription(CalculationType.findOne(calculationType).getCalculationTypeDescription());
        }
    }

    @Basic
    @Column(name = "multiply_value", nullable = false)
    public Double getMultiplyValue() {
        return multiplyValue;
    }

    public void setMultiplyValue(Double multiplyValue) {
        this.multiplyValue = multiplyValue;
    }

    @Basic
    @Column(name = "commission", nullable = false)
    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "salary_commission_seq", nullable = false)
    public List<SalaryCommissionDetail> getSalaryCommissionDetailList() {
        return salaryCommissionDetailList;
    }

    public void setSalaryCommissionDetailList(List<SalaryCommissionDetail> salaryCommissionDetailList) {
        this.salaryCommissionDetailList = salaryCommissionDetailList;
    }

    @Transient
    public String getCalculationTypeDescription() {
        return calculationTypeDescription;
    }

    public void setCalculationTypeDescription(String calculationTypeDescription) {
        this.calculationTypeDescription = calculationTypeDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalaryCommission that = (SalaryCommission) o;
        return Objects.equals(salaryCommissionSeq, that.salaryCommissionSeq) &&
                Objects.equals(salarySeq, that.salarySeq) &&
                Objects.equals(chargeSeq, that.chargeSeq) &&
                Objects.equals(payrollCommissionSeq, that.payrollCommissionSeq) &&
                Objects.equals(calculationType, that.calculationType) &&
                Objects.equals(chargeValue, that.chargeValue) &&
                Objects.equals(multiplyValue, that.multiplyValue) &&
                Objects.equals(commission, that.commission) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(salaryCommissionSeq, salarySeq, chargeSeq, payrollCommissionSeq, calculationType, chargeValue, multiplyValue, commission, status);
    }
}

package com.tmj.tms.fleet.datalayer.modal;

import com.tmj.tms.fleet.utility.CalculationType;
import com.tmj.tms.master.datalayer.modal.Charge;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "salary_allowance")
public class SalaryAllowance {

    private Integer salaryAllowanceSeq;
    private Integer salarySeq;
    private Integer payrollAllowanceSeq;
    private Integer calculationType;
    private Integer chargeSeq;
    private Double chargeValue;
    private Double multiplyValue;
    private Double allowance;
    private Integer status;

    private Charge charge;
    private String calculationTypeDescription;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "salary_allowance_seq", allocationSize = 1)
    @Column(name = "salary_allowance_seq", nullable = false, unique = true)
    public Integer getSalaryAllowanceSeq() {
        return salaryAllowanceSeq;
    }

    public void setSalaryAllowanceSeq(Integer salaryAllowanceSeq) {
        this.salaryAllowanceSeq = salaryAllowanceSeq;
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

    @Basic
    @Column(name = "payroll_allowance_seq", nullable = false)
    public Integer getPayrollAllowanceSeq() {
        return payrollAllowanceSeq;
    }

    public void setPayrollAllowanceSeq(Integer payrollAllowanceSeq) {
        this.payrollAllowanceSeq = payrollAllowanceSeq;
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
    @Column(name = "allowance", nullable = false)
    public Double getAllowance() {
        return allowance;
    }

    public void setAllowance(Double allowance) {
        this.allowance = allowance;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "charge_seq", insertable = false, updatable = false)
    public Charge getCharge() {
        return charge;
    }

    public void setCharge(Charge charge) {
        this.charge = charge;
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
        SalaryAllowance that = (SalaryAllowance) o;
        return Objects.equals(salaryAllowanceSeq, that.salaryAllowanceSeq) &&
                Objects.equals(salarySeq, that.salarySeq) &&
                Objects.equals(payrollAllowanceSeq, that.payrollAllowanceSeq) &&
                Objects.equals(calculationType, that.calculationType) &&
                Objects.equals(chargeSeq, that.chargeSeq) &&
                Objects.equals(chargeValue, that.chargeValue) &&
                Objects.equals(multiplyValue, that.multiplyValue) &&
                Objects.equals(allowance, that.allowance) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(salaryAllowanceSeq, salarySeq, payrollAllowanceSeq, calculationType, chargeSeq, chargeValue, multiplyValue, allowance, status);
    }
}

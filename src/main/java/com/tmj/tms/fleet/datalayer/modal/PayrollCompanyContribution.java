package com.tmj.tms.fleet.datalayer.modal;

import com.tmj.tms.master.datalayer.modal.Charge;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "payroll_company_contribution")
public class PayrollCompanyContribution {

    private Integer payrollCompanyContributionSeq;
    private Integer payrollSeq;
    private Integer payrollChargeConfigSeq;
    private Integer chargeSeq;
    private Integer status;

    private Charge charge;
    private PayrollChargeConfig payrollChargeConfig;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "payroll_company_contribution_seq", allocationSize = 1)
    @Column(name = "payroll_company_contribution_seq", nullable = false, unique = true)
    public Integer getPayrollCompanyContributionSeq() {
        return payrollCompanyContributionSeq;
    }

    public void setPayrollCompanyContributionSeq(Integer payrollCompanyContributionSeq) {
        this.payrollCompanyContributionSeq = payrollCompanyContributionSeq;
    }

    @Basic
    @Column(name = "payroll_seq", updatable = false, insertable = false)
    public Integer getPayrollSeq() {
        return payrollSeq;
    }

    public void setPayrollSeq(Integer payrollSeq) {
        this.payrollSeq = payrollSeq;
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
    @Column(name = "payroll_charge_config_seq", nullable = false)
    public Integer getPayrollChargeConfigSeq() {
        return payrollChargeConfigSeq;
    }

    public void setPayrollChargeConfigSeq(Integer payrollChargeConfigSeq) {
        this.payrollChargeConfigSeq = payrollChargeConfigSeq;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payroll_charge_config_seq", insertable = false, updatable = false)
    public PayrollChargeConfig getPayrollChargeConfig() {
        return payrollChargeConfig;
    }

    public void setPayrollChargeConfig(PayrollChargeConfig payrollChargeConfig) {
        this.payrollChargeConfig = payrollChargeConfig;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PayrollCompanyContribution that = (PayrollCompanyContribution) o;
        return Objects.equals(payrollCompanyContributionSeq, that.payrollCompanyContributionSeq) &&
                Objects.equals(payrollSeq, that.payrollSeq) &&
                Objects.equals(payrollChargeConfigSeq, that.payrollChargeConfigSeq) &&
                Objects.equals(chargeSeq, that.chargeSeq) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payrollCompanyContributionSeq, payrollSeq, payrollChargeConfigSeq, chargeSeq, status);
    }
}

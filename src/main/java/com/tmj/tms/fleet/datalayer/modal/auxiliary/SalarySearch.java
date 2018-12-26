package com.tmj.tms.fleet.datalayer.modal.auxiliary;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalarySearch {

    private Integer employeeSeq;
    private Integer employeeDesignationSeq;
    private Integer salaryYear;
    private Integer salaryMonth;
    private Date startDate;
    private Date endDate;
    private Double salaryAdvance;
    private String remarks;

    private List<Integer> salaryAllowanceChargeSeq = new ArrayList<>();
    private List<Integer> salaryCommissionChargeSeq = new ArrayList<>();
    private List<Integer> salaryDeductionChargeSeq = new ArrayList<>();
    private List<Integer> salaryCompanyContributionChargeSeq = new ArrayList<>();

    public Integer getEmployeeSeq() {
        return employeeSeq;
    }

    public void setEmployeeSeq(Integer employeeSeq) {
        this.employeeSeq = employeeSeq;
    }

    public Integer getSalaryYear() {
        return salaryYear;
    }

    public void setSalaryYear(Integer salaryYear) {
        this.salaryYear = salaryYear;
    }

    public Integer getSalaryMonth() {
        return salaryMonth;
    }

    public void setSalaryMonth(Integer salaryMonth) {
        this.salaryMonth = salaryMonth;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Integer> getSalaryAllowanceChargeSeq() {
        return salaryAllowanceChargeSeq;
    }

    public void setSalaryAllowanceChargeSeq(List<Integer> salaryAllowanceChargeSeq) {
        this.salaryAllowanceChargeSeq = salaryAllowanceChargeSeq;
    }

    public List<Integer> getSalaryCommissionChargeSeq() {
        return salaryCommissionChargeSeq;
    }

    public void setSalaryCommissionChargeSeq(List<Integer> salaryCommissionChargeSeq) {
        this.salaryCommissionChargeSeq = salaryCommissionChargeSeq;
    }

    public List<Integer> getSalaryDeductionChargeSeq() {
        return salaryDeductionChargeSeq;
    }

    public void setSalaryDeductionChargeSeq(List<Integer> salaryDeductionChargeSeq) {
        this.salaryDeductionChargeSeq = salaryDeductionChargeSeq;
    }

    public List<Integer> getSalaryCompanyContributionChargeSeq() {
        return salaryCompanyContributionChargeSeq;
    }

    public void setSalaryCompanyContributionChargeSeq(List<Integer> salaryCompanyContributionChargeSeq) {
        this.salaryCompanyContributionChargeSeq = salaryCompanyContributionChargeSeq;
    }

    public Double getSalaryAdvance() {
        return salaryAdvance;
    }

    public void setSalaryAdvance(Double salaryAdvance) {
        this.salaryAdvance = salaryAdvance;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getEmployeeDesignationSeq() {
        return employeeDesignationSeq;
    }

    public void setEmployeeDesignationSeq(Integer employeeDesignationSeq) {
        this.employeeDesignationSeq = employeeDesignationSeq;
    }
}

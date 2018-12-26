package com.tmj.tms.fleet.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.datalayer.modal.Employee;
import com.tmj.tms.master.datalayer.modal.EmployeeDesignation;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "Salary.default", attributeNodes = {
                @NamedAttributeNode("employee"),
                @NamedAttributeNode("employeeDesignation")
        })})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "salary")
public class Salary {

    private Integer salarySeq;
    private Integer payrollSeq;
    private Integer companyProfileSeq;
    private Integer employeeSeq;
    private Integer employeeDesignationSeq;
    private Double basicSalary;
    private Double totalAllowance;
    private Double totalCommission;
    private Double totalDeduction;
    private Double totalCompanyContribution;
    private Double totalSalaryAdvance;
    private Integer attendance;
    private Double netPay;
    private Integer salaryYear;
    private Integer salaryMonth;
    private Date startDate;
    private Date endDate;
    private Integer currencySeq;
    private String remarks;

    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer status;

    private Integer financeIntegration;
    private Long financeIntegrationKey;

    private Employee employee;
    private Payroll payroll;
    private EmployeeDesignation employeeDesignation;

    private List<SalaryAllowance> salaryAllowanceList;
    private List<SalaryCommission> salaryCommissionList;
    private List<SalaryDeduction> salaryDeductionList;
    private List<SalaryCompanyContribution> salaryCompanyContributionList;
    private List<SalaryAdvance> salaryAdvanceList;

    private String yearMonth;
    private String statusDescription;
    private Integer daysOfMonth;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "salary_seq", allocationSize = 1)
    @Column(name = "salary_seq", nullable = false, unique = true)
    public Integer getSalarySeq() {
        return salarySeq;
    }

    public void setSalarySeq(Integer salarySeq) {
        this.salarySeq = salarySeq;
    }

    @Basic
    @Column(name = "payroll_seq", updatable = false, nullable = false)
    public Integer getPayrollSeq() {
        return payrollSeq;
    }

    public void setPayrollSeq(Integer payrollSeq) {
        this.payrollSeq = payrollSeq;
    }

    @Basic
    @Column(name = "company_profile_seq", updatable = false, nullable = false)
    public Integer getCompanyProfileSeq() {
        return companyProfileSeq;
    }

    public void setCompanyProfileSeq(Integer companyProfileSeq) {
        this.companyProfileSeq = companyProfileSeq;
    }

    @Basic
    @Column(name = "employee_seq", updatable = false, nullable = false)
    public Integer getEmployeeSeq() {
        return employeeSeq;
    }

    public void setEmployeeSeq(Integer employeeSeq) {
        this.employeeSeq = employeeSeq;
    }

    @Basic
    @Column(name = "employee_designation_seq", updatable = false, nullable = false)
    public Integer getEmployeeDesignationSeq() {
        return employeeDesignationSeq;
    }

    public void setEmployeeDesignationSeq(Integer employeeDesignationSeq) {
        this.employeeDesignationSeq = employeeDesignationSeq;
    }

    @Basic
    @Column(name = "basic_salary", nullable = false)
    public Double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Double basicSalary) {
        this.basicSalary = basicSalary;
    }

    @Basic
    @Column(name = "total_allowance")
    public Double getTotalAllowance() {
        return totalAllowance;
    }

    public void setTotalAllowance(Double totalAllowance) {
        this.totalAllowance = totalAllowance;
    }

    @Basic
    @Column(name = "total_commission")
    public Double getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(Double totalCommission) {
        this.totalCommission = totalCommission;
    }

    @Basic
    @Column(name = "total_deduction")
    public Double getTotalDeduction() {
        return totalDeduction;
    }

    public void setTotalDeduction(Double totalDeduction) {
        this.totalDeduction = totalDeduction;
    }

    @Basic
    @Column(name = "total_salary_advance")
    public Double getTotalSalaryAdvance() {
        return totalSalaryAdvance;
    }

    public void setTotalSalaryAdvance(Double totalSalaryAdvance) {
        this.totalSalaryAdvance = totalSalaryAdvance;
    }

    @Basic
    @Column(name = "total_company_contribution")
    public Double getTotalCompanyContribution() {
        return totalCompanyContribution;
    }

    public void setTotalCompanyContribution(Double totalCompanyContribution) {
        this.totalCompanyContribution = totalCompanyContribution;
    }

    @Basic
    @Column(name = "net_pay", nullable = false)
    public Double getNetPay() {
        return netPay;
    }

    public void setNetPay(Double netPay) {
        this.netPay = netPay;
    }

    @Basic
    @Column(name = "salary_year", nullable = false)
    public Integer getSalaryYear() {
        return salaryYear;
    }

    public void setSalaryYear(Integer salaryYear) {
        this.salaryYear = salaryYear;
    }

    @Basic
    @Column(name = "salary_month", nullable = false)
    public Integer getSalaryMonth() {
        return salaryMonth;
    }

    public void setSalaryMonth(Integer salaryMonth) {
        this.salaryMonth = salaryMonth;
    }

    @Basic
    @Column(name = "start_date", nullable = false)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date", nullable = false)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
    @Column(name = "remarks")
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Basic
    @Column(name = "attendance")
    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }

    @Basic
    @Column(name = "status", nullable = false)
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
    @Column(name = "created_by", nullable = false, updatable = false)
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
    @Column(name = "last_modified_by", nullable = true)
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Basic
    @Column(name = "last_modified_date", nullable = true)
    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_seq", insertable = false, updatable = false)
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payroll_seq", insertable = false, updatable = false)
    public Payroll getPayroll() {
        return payroll;
    }

    public void setPayroll(Payroll payroll) {
        this.payroll = payroll;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_designation_seq", insertable = false, updatable = false)
    public EmployeeDesignation getEmployeeDesignation() {
        return employeeDesignation;
    }

    public void setEmployeeDesignation(EmployeeDesignation employeeDesignation) {
        this.employeeDesignation = employeeDesignation;
    }

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "salary_seq", nullable = false)
    public List<SalaryAllowance> getSalaryAllowanceList() {
        return salaryAllowanceList;
    }

    public void setSalaryAllowanceList(List<SalaryAllowance> salaryAllowanceList) {
        this.salaryAllowanceList = salaryAllowanceList;
    }

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "salary_seq", nullable = false)
    public List<SalaryCommission> getSalaryCommissionList() {
        return salaryCommissionList;
    }

    public void setSalaryCommissionList(List<SalaryCommission> salaryCommissionList) {
        this.salaryCommissionList = salaryCommissionList;
    }

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "salary_seq", nullable = false)
    public List<SalaryDeduction> getSalaryDeductionList() {
        return salaryDeductionList;
    }

    public void setSalaryDeductionList(List<SalaryDeduction> salaryDeductionList) {
        this.salaryDeductionList = salaryDeductionList;
    }

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "salary_seq", nullable = false)
    public List<SalaryCompanyContribution> getSalaryCompanyContributionList() {
        return salaryCompanyContributionList;
    }

    public void setSalaryCompanyContributionList(List<SalaryCompanyContribution> salaryCompanyContributionList) {
        this.salaryCompanyContributionList = salaryCompanyContributionList;
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
    public List<SalaryAdvance> getSalaryAdvanceList() {
        return salaryAdvanceList;
    }

    public void setSalaryAdvanceList(List<SalaryAdvance> salaryAdvanceList) {
        this.salaryAdvanceList = salaryAdvanceList;
    }

    @Transient
    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    @Transient
    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    @Transient
    public Integer getDaysOfMonth() {
        return daysOfMonth;
    }

    public void setDaysOfMonth(Integer daysOfMonth) {
        this.daysOfMonth = daysOfMonth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Salary salary = (Salary) o;
        return Objects.equals(salarySeq, salary.salarySeq) &&
                Objects.equals(payrollSeq, salary.payrollSeq) &&
                Objects.equals(companyProfileSeq, salary.companyProfileSeq) &&
                Objects.equals(employeeSeq, salary.employeeSeq) &&
                Objects.equals(employeeDesignationSeq, salary.employeeDesignationSeq) &&
                Objects.equals(basicSalary, salary.basicSalary) &&
                Objects.equals(totalAllowance, salary.totalAllowance) &&
                Objects.equals(totalCommission, salary.totalCommission) &&
                Objects.equals(totalDeduction, salary.totalDeduction) &&
                Objects.equals(totalCompanyContribution, salary.totalCompanyContribution) &&
                Objects.equals(totalSalaryAdvance, salary.totalSalaryAdvance) &&
                Objects.equals(attendance, salary.attendance) &&
                Objects.equals(netPay, salary.netPay) &&
                Objects.equals(salaryYear, salary.salaryYear) &&
                Objects.equals(salaryMonth, salary.salaryMonth) &&
                Objects.equals(startDate, salary.startDate) &&
                Objects.equals(endDate, salary.endDate) &&
                Objects.equals(currencySeq, salary.currencySeq) &&
                Objects.equals(remarks, salary.remarks) &&
                Objects.equals(createdBy, salary.createdBy) &&
                Objects.equals(createdDate, salary.createdDate) &&
                Objects.equals(lastModifiedBy, salary.lastModifiedBy) &&
                Objects.equals(lastModifiedDate, salary.lastModifiedDate) &&
                Objects.equals(status, salary.status) &&
                Objects.equals(employee, salary.employee) &&
                Objects.equals(payroll, salary.payroll) &&
                Objects.equals(employeeDesignation, salary.employeeDesignation) &&
                Objects.equals(salaryAllowanceList, salary.salaryAllowanceList) &&
                Objects.equals(salaryCommissionList, salary.salaryCommissionList) &&
                Objects.equals(salaryDeductionList, salary.salaryDeductionList) &&
                Objects.equals(salaryCompanyContributionList, salary.salaryCompanyContributionList) &&
                Objects.equals(salaryAdvanceList, salary.salaryAdvanceList) &&
                Objects.equals(yearMonth, salary.yearMonth) &&
                Objects.equals(statusDescription, salary.statusDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(salarySeq, payrollSeq, companyProfileSeq, employeeSeq, employeeDesignationSeq, basicSalary, totalAllowance, totalCommission, totalDeduction, totalCompanyContribution, totalSalaryAdvance, attendance, netPay, salaryYear, salaryMonth, startDate, endDate, currencySeq, remarks, createdBy, createdDate, lastModifiedBy, lastModifiedDate, status, employee, payroll, employeeDesignation, salaryAllowanceList, salaryCommissionList, salaryDeductionList, salaryCompanyContributionList, salaryAdvanceList, yearMonth, statusDescription);
    }
}

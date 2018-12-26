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
import java.util.Objects;

@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "SalaryAdvance.default", attributeNodes = {
                @NamedAttributeNode("employee"),
                @NamedAttributeNode("employeeDesignation")
        })})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "salary_advance")
public class SalaryAdvance {

    private Integer salaryAdvanceSeq;
    private Integer payrollSeq;
    private Integer companyProfileSeq;
    private Integer employeeSeq;
    private Integer employeeDesignationSeq;
    private Double netPay;
    private Double salaryAdvance;
    private String remarks;
    private Integer salaryYear;
    private Integer salaryMonth;
    private Date startDate;
    private Date endDate;
    private Date transactionDate;
    private Integer currencySeq;

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

    private String yearMonth;
    private String statusDescription;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "salary_advance_seq", allocationSize = 1)
    @Column(name = "salary_advance_seq", nullable = false, unique = true)
    public Integer getSalaryAdvanceSeq() {
        return salaryAdvanceSeq;
    }

    public void setSalaryAdvanceSeq(Integer salaryAdvanceSeq) {
        this.salaryAdvanceSeq = salaryAdvanceSeq;
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
    @Column(name = "net_pay", nullable = false)
    public Double getNetPay() {
        return netPay;
    }

    public void setNetPay(Double netPay) {
        this.netPay = netPay;
    }

    @Basic
    @Column(name = "salary_advance", nullable = false)
    public Double getSalaryAdvance() {
        return salaryAdvance;
    }

    public void setSalaryAdvance(Double salaryAdvance) {
        this.salaryAdvance = salaryAdvance;
    }

    @Basic
    @Column(name = "remarks", nullable = false)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
    @Column(name = "transaction_date")
    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalaryAdvance that = (SalaryAdvance) o;
        return Objects.equals(salaryAdvanceSeq, that.salaryAdvanceSeq) &&
                Objects.equals(payrollSeq, that.payrollSeq) &&
                Objects.equals(companyProfileSeq, that.companyProfileSeq) &&
                Objects.equals(employeeSeq, that.employeeSeq) &&
                Objects.equals(employeeDesignationSeq, that.employeeDesignationSeq) &&
                Objects.equals(netPay, that.netPay) &&
                Objects.equals(salaryAdvance, that.salaryAdvance) &&
                Objects.equals(remarks, that.remarks) &&
                Objects.equals(salaryYear, that.salaryYear) &&
                Objects.equals(salaryMonth, that.salaryMonth) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(transactionDate, that.transactionDate) &&
                Objects.equals(currencySeq, that.currencySeq) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(salaryAdvanceSeq, payrollSeq, companyProfileSeq, employeeSeq, employeeDesignationSeq, netPay, salaryAdvance, remarks, salaryYear, salaryMonth, startDate, endDate, transactionDate, currencySeq, status);
    }
}

package com.tmj.tms.fleet.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.datalayer.modal.Employee;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
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
        @NamedEntityGraph(name = "Payroll.default"),
        @NamedEntityGraph(name = "Payroll.create", attributeNodes = {
                @NamedAttributeNode("employee")
        }),
})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "payroll")
public class Payroll {

    private Integer payrollSeq;
    private Integer companyProfileSeq;
    private Integer employeeSeq;
    private Double basicSalary;

    private List<PayrollAllowance> payrollAllowanceList;
    private List<PayrollDeduction> payrollDeductionList;
    private List<PayrollCommission> payrollCommissionList;
    private List<PayrollCompanyContribution> payrollCompanyContributionList;

    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer status;

    private Employee employee;

    private String statusDescription;

    private List<Integer> deductionSeqList;
    private List<Integer> commissionSeqList;
    private List<Integer> allowanceSeqList;
    private List<Integer> companyContributionSeqList;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "payroll_seq", allocationSize = 1)
    @Column(name = "payroll_seq", nullable = false, unique = true)
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
    @Column(name = "basic_salary", nullable = false)
    public Double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Double basicSalary) {
        this.basicSalary = basicSalary;
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

    @Transient
    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "payroll_seq", nullable = false)
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<PayrollAllowance> getPayrollAllowanceList() {
        return payrollAllowanceList;
    }

    public void setPayrollAllowanceList(List<PayrollAllowance> payrollAllowanceList) {
        this.payrollAllowanceList = payrollAllowanceList;
    }

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "payroll_seq", nullable = false)
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<PayrollDeduction> getPayrollDeductionList() {
        return payrollDeductionList;
    }

    public void setPayrollDeductionList(List<PayrollDeduction> payrollDeductionList) {
        this.payrollDeductionList = payrollDeductionList;
    }

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "payroll_seq", nullable = false)
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<PayrollCommission> getPayrollCommissionList() {
        return payrollCommissionList;
    }

    public void setPayrollCommissionList(List<PayrollCommission> payrollCommissionList) {
        this.payrollCommissionList = payrollCommissionList;
    }

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "payroll_seq", nullable = false)
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<PayrollCompanyContribution> getPayrollCompanyContributionList() {
        return payrollCompanyContributionList;
    }

    public void setPayrollCompanyContributionList(List<PayrollCompanyContribution> payrollCompanyContributionList) {
        this.payrollCompanyContributionList = payrollCompanyContributionList;
    }

    @Transient
    public List<Integer> getDeductionSeqList() {
        return deductionSeqList;
    }

    public void setDeductionSeqList(List<Integer> deductionSeqList) {
        this.deductionSeqList = deductionSeqList;
    }

    @Transient
    public List<Integer> getCommissionSeqList() {
        return commissionSeqList;
    }

    public void setCommissionSeqList(List<Integer> commissionSeqList) {
        this.commissionSeqList = commissionSeqList;
    }

    @Transient
    public List<Integer> getAllowanceSeqList() {
        return allowanceSeqList;
    }

    public void setAllowanceSeqList(List<Integer> allowanceSeqList) {
        this.allowanceSeqList = allowanceSeqList;
    }

    @Transient
    public List<Integer> getCompanyContributionSeqList() {
        return companyContributionSeqList;
    }

    public void setCompanyContributionSeqList(List<Integer> companyContributionSeqList) {
        this.companyContributionSeqList = companyContributionSeqList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payroll payroll = (Payroll) o;
        return Objects.equals(payrollSeq, payroll.payrollSeq) &&
                Objects.equals(companyProfileSeq, payroll.companyProfileSeq) &&
                Objects.equals(employeeSeq, payroll.employeeSeq) &&
                Objects.equals(basicSalary, payroll.basicSalary) &&
                Objects.equals(status, payroll.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payrollSeq, companyProfileSeq, employeeSeq, basicSalary, status);
    }
}

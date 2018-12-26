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
        @NamedEntityGraph(name = "leave.default"),
        @NamedEntityGraph(name = "leave.createLeave", attributeNodes = {
                @NamedAttributeNode("employee"),
                @NamedAttributeNode("leaveType")
        }),
})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "leave")
public class Leave {

    private Integer leaveSeq;
    private Integer companyProfileSeq;
    private Integer employeeSeq;
    private Integer employeeDesignationSeq;
    private Integer leaveTypeSeq;
    private Integer leaveYear;
    private Integer leaveMonth;
    private Date startDate;
    private Date endDate;
    private String reason;
    private Integer noOfDays;

    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer status;

    private Employee employee;
    private LeaveType leaveType;
    private EmployeeDesignation employeeDesignation;

    private String statusDescription;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "leave_seq", allocationSize = 1)
    @Column(name = "leave_seq", nullable = false, unique = true)
    public Integer getLeaveSeq() {
        return leaveSeq;
    }

    public void setLeaveSeq(Integer leaveSeq) {
        this.leaveSeq = leaveSeq;
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
    @Column(name = "employee_designation_seq", nullable = false)
    public Integer getEmployeeDesignationSeq() {
        return employeeDesignationSeq;
    }

    public void setEmployeeDesignationSeq(Integer employeeDesignationSeq) {
        this.employeeDesignationSeq = employeeDesignationSeq;
    }

    @Basic
    @Column(name = "leave_type_seq", nullable = false)
    public Integer getLeaveTypeSeq() {
        return leaveTypeSeq;
    }

    public void setLeaveTypeSeq(Integer leaveTypeSeq) {
        this.leaveTypeSeq = leaveTypeSeq;
    }

    @Basic
    @Column(name = "leave_year", updatable = false, nullable = false)
    public Integer getLeaveYear() {
        return leaveYear;
    }

    public void setLeaveYear(Integer leaveYear) {
        this.leaveYear = leaveYear;
    }

    @Basic
    @Column(name = "leave_month", nullable = false)
    public Integer getLeaveMonth() {
        return leaveMonth;
    }

    public void setLeaveMonth(Integer leaveMonth) {
        this.leaveMonth = leaveMonth;
    }

    @Basic
    @Column(name = "start_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "reason")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Basic
    @Column(name = "no_of_days")
    public Integer getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(Integer noOfDays) {
        this.noOfDays = noOfDays;
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
    @JoinColumn(name = "leave_type_seq", insertable = false, updatable = false)
    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_designation_seq", insertable = false, updatable = false)
    public EmployeeDesignation getEmployeeDesignation() {
        return employeeDesignation;
    }

    public void setEmployeeDesignation(EmployeeDesignation employeeDesignation) {
        this.employeeDesignation = employeeDesignation;
    }

    @Transient
    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Leave leave = (Leave) o;
        return Objects.equals(leaveSeq, leave.leaveSeq) &&
                Objects.equals(companyProfileSeq, leave.companyProfileSeq) &&
                Objects.equals(employeeSeq, leave.employeeSeq) &&
                Objects.equals(employeeDesignationSeq, leave.employeeDesignationSeq) &&
                Objects.equals(leaveTypeSeq, leave.leaveTypeSeq) &&
                Objects.equals(leaveYear, leave.leaveYear) &&
                Objects.equals(leaveMonth, leave.leaveMonth) &&
                Objects.equals(startDate, leave.startDate) &&
                Objects.equals(endDate, leave.endDate) &&
                Objects.equals(reason, leave.reason) &&
                Objects.equals(status, leave.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leaveSeq, companyProfileSeq, employeeSeq, employeeDesignationSeq, leaveTypeSeq, leaveYear, leaveMonth, startDate, endDate, reason, status);
    }
}

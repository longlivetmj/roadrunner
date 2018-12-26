package com.tmj.tms.home.datalayer.model;

import com.tmj.tms.master.datalayer.modal.Employee;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "temp_employee_statistics")
public class TempEmployeeStatistics {
    private Integer tempEmployeeStatisticsSeq;
    private Integer employeeSeq;
    private Integer employeeDesignationSeq;
    private Integer companyProfileSeq;
    private Integer moduleSeq;
    private Integer year;
    private Integer month;
    private Double totalCommission;
    private Integer attendance;
    private Integer noOfJobs;
    private Double totalKm;
    private Integer score;
    private Double zScore;
    private Double mean;
    private Date lastUpdatedDate;

    private Employee employee;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "temp_employee_statistics_seq", allocationSize = 1)
    @Column(name = "temp_employee_statistics_seq", unique = true)
    public Integer getTempEmployeeStatisticsSeq() {
        return tempEmployeeStatisticsSeq;
    }

    public void setTempEmployeeStatisticsSeq(Integer tempEmployeeStatisticsSeq) {
        this.tempEmployeeStatisticsSeq = tempEmployeeStatisticsSeq;
    }

    @Basic
    @Column(name = "employee_seq", nullable = false)
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
    @Column(name = "company_profile_seq", nullable = false)
    public Integer getCompanyProfileSeq() {
        return companyProfileSeq;
    }

    public void setCompanyProfileSeq(Integer companyProfileSeq) {
        this.companyProfileSeq = companyProfileSeq;
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
    @Column(name = "year", nullable = false)
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Basic
    @Column(name = "month", nullable = false)
    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    @Basic
    @Column(name = "total_commission", nullable = false)
    public Double getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(Double totalCommission) {
        this.totalCommission = totalCommission;
    }

    @Basic
    @Column(name = "attendance", nullable = false)
    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }

    @Basic
    @Column(name = "no_of_jobs", nullable = false)
    public Integer getNoOfJobs() {
        return noOfJobs;
    }

    public void setNoOfJobs(Integer noOfJobs) {
        this.noOfJobs = noOfJobs;
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
    @Column(name = "score", nullable = false)
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Basic
    @Column(name = "z_score")
    public Double getzScore() {
        return zScore;
    }

    public void setzScore(Double zScore) {
        this.zScore = zScore;
    }

    @Basic
    @Column(name = "mean")
    public Double getMean() {
        return mean;
    }

    public void setMean(Double mean) {
        this.mean = mean;
    }

    @Basic
    @Column(name = "last_updated_date")
    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_seq", insertable = false, updatable = false)
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TempEmployeeStatistics that = (TempEmployeeStatistics) o;
        return Objects.equals(tempEmployeeStatisticsSeq, that.tempEmployeeStatisticsSeq) &&
                Objects.equals(employeeSeq, that.employeeSeq) &&
                Objects.equals(employeeDesignationSeq, that.employeeDesignationSeq) &&
                Objects.equals(companyProfileSeq, that.companyProfileSeq) &&
                Objects.equals(moduleSeq, that.moduleSeq) &&
                Objects.equals(year, that.year) &&
                Objects.equals(month, that.month) &&
                Objects.equals(totalCommission, that.totalCommission) &&
                Objects.equals(attendance, that.attendance) &&
                Objects.equals(noOfJobs, that.noOfJobs) &&
                Objects.equals(totalKm, that.totalKm) &&
                Objects.equals(score, that.score) &&
                Objects.equals(zScore, that.zScore) &&
                Objects.equals(mean, that.mean) &&
                Objects.equals(lastUpdatedDate, that.lastUpdatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tempEmployeeStatisticsSeq, employeeSeq, employeeDesignationSeq, companyProfileSeq, moduleSeq, year, month, totalCommission, attendance, noOfJobs, totalKm, score, zScore, mean, lastUpdatedDate);
    }
}

package com.tmj.tms.fleet.datalayer.modal.auxiliary;

import java.util.Date;

public class LeaveSearch {

    private String employeeName;
    private Integer status;
    private Integer employeeDesignationSeq;
    private Date startDate;
    private Date endDate;
    private Integer leaveTypeSeq;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getEmployeeDesignationSeq() {
        return employeeDesignationSeq;
    }

    public void setEmployeeDesignationSeq(Integer employeeDesignationSeq) {
        this.employeeDesignationSeq = employeeDesignationSeq;
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

    public Integer getLeaveTypeSeq() {
        return leaveTypeSeq;
    }

    public void setLeaveTypeSeq(Integer leaveTypeSeq) {
        this.leaveTypeSeq = leaveTypeSeq;
    }
}

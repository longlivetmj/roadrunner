package com.tmj.tms.master.datalayer.modal.auxiliary;

public class EmployeeSearch {

    private String employeeName;
    private Integer stakeholderSeq;
    private Integer status;
    private Integer employeeDesignationSeq;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getStakeholderSeq() {
        return stakeholderSeq;
    }

    public void setStakeholderSeq(Integer stakeholderSeq) {
        this.stakeholderSeq = stakeholderSeq;
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
}

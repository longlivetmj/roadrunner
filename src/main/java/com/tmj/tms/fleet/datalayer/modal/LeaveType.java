package com.tmj.tms.fleet.datalayer.modal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "leave_type")
public class LeaveType {

    private Integer leaveTypeSeq;
    private String leaveType;
    private String description;
    private Integer status;

    @Id
    @Column(name = "leave_type_seq", nullable = false, unique = true)
    public Integer getLeaveTypeSeq() {
        return leaveTypeSeq;
    }

    public void setLeaveTypeSeq(Integer leaveTypeSeq) {
        this.leaveTypeSeq = leaveTypeSeq;
    }

    @Column(name = "leave_type", nullable = false, unique = true)
    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "status", nullable = false)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeaveType leaveType1 = (LeaveType) o;
        return Objects.equals(leaveTypeSeq, leaveType1.leaveTypeSeq) &&
                Objects.equals(leaveType, leaveType1.leaveType) &&
                Objects.equals(description, leaveType1.description) &&
                Objects.equals(status, leaveType1.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leaveTypeSeq, leaveType, description, status);
    }
}

package com.tmj.tms.finance.utility;

import com.tmj.tms.master.datalayer.modal.Stakeholder;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;

import java.util.Date;

public class ExpenseVoucherSearchFromAux {
    private String expenseVoucherNo;
    private Integer targetTypeSeq;
    private Date startDate;
    private Date endDate;
    private Integer stakeholderSeq;
    private Integer status;
    private Integer departmentSeq;
    private Integer moduleSeq;
    private String requestId;

    public String getExpenseVoucherNo() {
        return expenseVoucherNo;
    }

    public void setExpenseVoucherNo(String expenseVoucherNo) {
        this.expenseVoucherNo = expenseVoucherNo;
    }

    public Integer getTargetTypeSeq() {
        return targetTypeSeq;
    }

    public void setTargetTypeSeq(Integer targetTypeSeq) {
        this.targetTypeSeq = targetTypeSeq;
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

    public Integer getDepartmentSeq() {
        return departmentSeq;
    }

    public void setDepartmentSeq(Integer departmentSeq) {
        this.departmentSeq = departmentSeq;
    }

    public Integer getModuleSeq() {
        return moduleSeq;
    }

    public void setModuleSeq(Integer moduleSeq) {
        this.moduleSeq = moduleSeq;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}

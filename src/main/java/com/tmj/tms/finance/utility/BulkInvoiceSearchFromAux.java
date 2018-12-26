package com.tmj.tms.finance.utility;

import java.util.Date;

public class BulkInvoiceSearchFromAux {

    private String bulkInvoiceNo;
    private Integer targetTypeSeq;
    private Date startDate;
    private Date endDate;
    private Integer stakeholderSeq;
    private Integer status;
    private Integer departmentSeq;
    private Integer moduleSeq;

    public String getBulkInvoiceNo() {
        return bulkInvoiceNo;
    }

    public void setBulkInvoiceNo(String bulkInvoiceNo) {
        this.bulkInvoiceNo = bulkInvoiceNo;
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

}

package com.tmj.tms.transport.datalayer.modal.auxiliary;

import java.util.Date;

public class BulkBookingSearch {

    private Date startDate;
    private Date endDate;
    private Integer customerSeq;

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

    public Integer getCustomerSeq() {
        return customerSeq;
    }

    public void setCustomerSeq(Integer customerSeq) {
        this.customerSeq = customerSeq;
    }
}

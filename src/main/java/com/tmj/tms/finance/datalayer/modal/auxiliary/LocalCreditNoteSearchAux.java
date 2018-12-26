package com.tmj.tms.finance.datalayer.modal.auxiliary;

import java.util.Date;

public class LocalCreditNoteSearchAux {

    private Integer invoiceTypeSeq;
    private String localInvoiceNo;
    private String expenseVoucherNo;
    private Date startDate;
    private Date endDate;
    private Integer status;
    private String creditDebitNo;

    public String getCreditDebitNo() {
        return creditDebitNo;
    }

    public void setCreditDebitNo(String creditDebitNo) {
        this.creditDebitNo = creditDebitNo;
    }

    public Integer getInvoiceTypeSeq() {
        return invoiceTypeSeq;
    }

    public void setInvoiceTypeSeq(Integer invoiceTypeSeq) {
        this.invoiceTypeSeq = invoiceTypeSeq;
    }

    public String getLocalInvoiceNo() {
        return localInvoiceNo;
    }

    public void setLocalInvoiceNo(String localInvoiceNo) {
        this.localInvoiceNo = localInvoiceNo;
    }

    public String getExpenseVoucherNo() {
        return expenseVoucherNo;
    }

    public void setExpenseVoucherNo(String expenseVoucherNo) {
        this.expenseVoucherNo = expenseVoucherNo;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

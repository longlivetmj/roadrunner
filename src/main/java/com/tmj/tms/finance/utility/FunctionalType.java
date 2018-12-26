package com.tmj.tms.finance.utility;

public enum FunctionalType {
    LOCAL_INVOICE(1, "LOCAL INVOICE"),
    EXPENSE_VOUCHER(2, "EXPENSE VOUCHER");

    private final Integer invoiceTypeSeq;
    private final String invoiceType;

    FunctionalType(Integer invoiceTypeSeq, String invoiceType) {
        this.invoiceTypeSeq = invoiceTypeSeq;
        this.invoiceType = invoiceType;
    }

    public Integer getInvoiceTypeSeq() {
        return invoiceTypeSeq;
    }

    public String getInvoiceType() {
        return invoiceType;
    }
}

package com.tmj.tms.finance.utility;

import java.util.Arrays;

public enum InvoiceType {
    VAT(1, "VAT"),
    SVAT(2, "SVAT");

    private final Integer invoiceType;
    private final String invoiceTypeDiscription;

    InvoiceType(Integer invoiceType, String invoiceTypeDiscription) {
        this.invoiceType = invoiceType;
        this.invoiceTypeDiscription = invoiceTypeDiscription;
    }

    public static InvoiceType findOne(Integer invoiceType) {
        return Arrays.stream(InvoiceType.values())
                .filter(x -> x.invoiceType.equals(invoiceType))
                .findFirst()
                .orElse(null);
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public String getInvoiceTypeDiscription() {
        return invoiceTypeDiscription;
    }
}

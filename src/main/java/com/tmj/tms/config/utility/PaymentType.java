package com.tmj.tms.config.utility;

import java.util.Arrays;

public enum PaymentType {

    REVENUE(1, "REVENUE"),
    COST(2, "COST");

    private Integer paymentTypeSeq;
    private String paymentType;

    public static PaymentType findOne(Integer paymentTypeSeq) {
        return Arrays.stream(PaymentType.values())
                .filter(x -> x.paymentTypeSeq.equals(paymentTypeSeq))
                .findFirst()
                .orElse(null);
    }

    PaymentType(Integer paymentTypeSeq, String paymentType) {
        this.paymentTypeSeq = paymentTypeSeq;
        this.paymentType = paymentType;
    }

    public Integer getPaymentTypeSeq() {
        return paymentTypeSeq;
    }

    public String getPaymentType() {
        return paymentType;
    }
}

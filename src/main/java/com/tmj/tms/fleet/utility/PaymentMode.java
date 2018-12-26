package com.tmj.tms.fleet.utility;

import java.util.Arrays;

public enum PaymentMode {
    NONE(0, "None"),
    MONTHLY_COMMITMENT(1, "Monthly Commitment"),
    KM_COMMITMENT(2, "KM Commitment"),
    POINT_TO_POINT(3, "Point to Point"),
    PER_KM_BASIS(4, "Per KM Basis");

    private Integer paymentMode;
    private String paymentModeDescription;

    PaymentMode(Integer paymentMode, String paymentModeDescription) {
        this.paymentMode = paymentMode;
        this.paymentModeDescription = paymentModeDescription;
    }

    public static PaymentMode findOne(Integer paymentMode) {
        return Arrays.stream(PaymentMode.values())
                .filter(x -> x.paymentMode.equals(paymentMode))
                .findFirst()
                .orElse(null);
    }

    public Integer getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Integer paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentModeDescription() {
        return paymentModeDescription;
    }

    public void setPaymentModeDescription(String paymentModeDescription) {
        this.paymentModeDescription = paymentModeDescription;
    }
}

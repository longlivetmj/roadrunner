package com.tmj.tms.master.utility;

import java.util.Arrays;

public enum StakeholderCreditType {
    THREE_DAYS(0, "3 Days"),
    TWO_WEEKS(1, "2 Weeks"),
    THREE_MONTH(2, "3 Month");

    private Integer creditTypeSeq;
    private String creditTypeDescription;

    StakeholderCreditType(Integer creditTypeSeq, String creditTypeDescription) {
        this.creditTypeSeq = creditTypeSeq;
        this.creditTypeDescription = creditTypeDescription;
    }

    public static StakeholderCreditType findOne(Integer creditTypeSeq) {
        return Arrays.stream(StakeholderCreditType.values())
                .filter(x -> x.creditTypeSeq.equals(creditTypeSeq))
                .findFirst()
                .orElse(null);
    }

    public Integer getCreditTypeSeq() {
        return creditTypeSeq;
    }

    public String getCreditTypeDescription() {
        return creditTypeDescription;
    }
}

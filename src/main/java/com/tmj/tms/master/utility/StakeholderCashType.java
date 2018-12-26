package com.tmj.tms.master.utility;

import java.util.Arrays;

public enum StakeholderCashType {
    CASH(0, "Cash"),
    CREDIT(1, "Credit");

    private Integer stakeholderCashTypeSeq;
    private String stakeholderCashType;

    public static StakeholderCashType findOne(Integer stakeholderCashTypeSeq) {
        return Arrays.stream(StakeholderCashType.values())
                .filter(x -> x.stakeholderCashTypeSeq.equals(stakeholderCashTypeSeq))
                .findFirst()
                .orElse(null);
    }

    StakeholderCashType(Integer stakeholderCashTypeSeq, String stakeholderCashType) {
        this.stakeholderCashTypeSeq = stakeholderCashTypeSeq;
        this.stakeholderCashType = stakeholderCashType;
    }

    public Integer getStakeholderCashTypeSeq() {
        return stakeholderCashTypeSeq;
    }

    public String getStakeholderCashType() {
        return stakeholderCashType;
    }
}

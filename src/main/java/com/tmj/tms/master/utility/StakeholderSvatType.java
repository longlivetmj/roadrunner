package com.tmj.tms.master.utility;

import java.util.Arrays;

public enum StakeholderSvatType {
    PURCHASER(0, "Purchaser"),
    SUPPLIER(1, "Supplier");

    private Integer stakeholderSvatTypeSeq;
    private String stakeholderSvatType;

    StakeholderSvatType(Integer stakeholderSvatTypeSeq, String stakeholderSvatType) {
        this.stakeholderSvatTypeSeq = stakeholderSvatTypeSeq;
        this.stakeholderSvatType = stakeholderSvatType;
    }

    public static StakeholderSvatType findOne(Integer stakeholderSvatTypeSeq) {
        return Arrays.stream(StakeholderSvatType.values())
                .filter(x -> x.stakeholderSvatTypeSeq.equals(stakeholderSvatTypeSeq))
                .findFirst()
                .orElse(null);
    }

    public Integer getStakeholderSvatTypeSeq() {
        return stakeholderSvatTypeSeq;
    }

    public String getStakeholderSvatType() {
        return stakeholderSvatType;
    }

}

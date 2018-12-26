package com.tmj.tms.finance.utility;

import java.util.Arrays;

public enum TargetType {

    TRANSPORT_JOB(1, "TRANSPORT JOB");

    private final Integer targetType;
    private final String targetTypeDescription;

    TargetType(Integer targetType, String targetTypeDescription) {
        this.targetType = targetType;
        this.targetTypeDescription = targetTypeDescription;
    }

    public static TargetType findOne(Integer targetType) {
        return Arrays.stream(TargetType.values())
                .filter(x -> x.targetType.equals(targetType))
                .findFirst()
                .orElse(null);
    }


    public Integer getTargetType() {
        return targetType;
    }

    public String getTargetTypeDescription() {
        return targetTypeDescription;
    }
}

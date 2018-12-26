package com.tmj.tms.finance.utility;

import java.util.Arrays;

public enum MultiplyType {

    PER_KM(1, "Per KM"),
    PER_VOLUME(2, "Per Volume"),
    FIXED_RATE(3, "Fixed Rate"),
    PER_WEIGHT(4, "Per Weight"),
    ADDITIONAL_HOUR(5, "Additional Hour"),
    PER_NIGHT(6, "Per Night"),
    PER_CBM(7, "Per CBM");

    private Integer multiplyType;
    private String multiplyTypeDescription;

    MultiplyType(Integer multiplyType, String multiplyTypeDescription) {
        this.multiplyType = multiplyType;
        this.multiplyTypeDescription = multiplyTypeDescription;
    }

    public static MultiplyType findOne(Integer multiplyType) {
        return Arrays.stream(MultiplyType.values())
                .filter(x -> x.multiplyType.equals(multiplyType))
                .findFirst()
                .orElse(null);
    }

    public Integer getMultiplyType() {
        return multiplyType;
    }

    public void setMultiplyType(Integer multiplyType) {
        this.multiplyType = multiplyType;
    }

    public String getMultiplyTypeDescription() {
        return multiplyTypeDescription;
    }

    public void setMultiplyTypeDescription(String multiplyTypeDescription) {
        this.multiplyTypeDescription = multiplyTypeDescription;
    }
}

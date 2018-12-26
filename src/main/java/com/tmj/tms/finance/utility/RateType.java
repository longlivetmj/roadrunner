package com.tmj.tms.finance.utility;

import java.util.Arrays;

public enum RateType {

    VEHICLE_TYPE(1, "Vehicle Type"),
    CONTAINER_TYPE(2, "Container Type");

    private Integer rateType;
    private String rateTypeDescription;

    RateType(Integer rateType, String rateTypeDescription) {
        this.rateType = rateType;
        this.rateTypeDescription = rateTypeDescription;
    }

    public static RateType findOne(Integer rateType) {
        return Arrays.stream(RateType.values())
                .filter(x -> x.rateType.equals(rateType))
                .findFirst()
                .orElse(null);
    }

    public Integer getRateType() {
        return rateType;
    }

    public void setRateType(Integer rateType) {
        this.rateType = rateType;
    }

    public String getRateTypeDescription() {
        return rateTypeDescription;
    }

    public void setRateTypeDescription(String rateTypeDescription) {
        this.rateTypeDescription = rateTypeDescription;
    }
}

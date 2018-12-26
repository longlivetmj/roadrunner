package com.tmj.tms.fleet.utility;

import java.util.Arrays;

public enum DurationType {
    NONE(0, "None"),
    SIX_MONTHS(1, "6 Months"),
    THREE_MONTHS(2, "3 Months");

    private Integer durationExpiration;
    private String durationExpirationDescription;

    DurationType(Integer durationExpiration, String durationExpirationDescription) {
        this.durationExpiration = durationExpiration;
        this.durationExpirationDescription = durationExpirationDescription;
    }

    public static DurationType findOne(Integer durationExpiration) {
        return Arrays.stream(DurationType.values())
                .filter(x -> x.durationExpiration.equals(durationExpiration))
                .findFirst()
                .orElse(null);
    }

    public Integer getDurationExpiration() {
        return durationExpiration;
    }

    public void setDurationExpiration(Integer durationExpiration) {
        this.durationExpiration = durationExpiration;
    }

    public String getDurationExpirationDescription() {
        return durationExpirationDescription;
    }

    public void setDurationExpirationDescription(String durationExpirationDescription) {
        this.durationExpirationDescription = durationExpirationDescription;
    }
}

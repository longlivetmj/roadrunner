package com.tmj.tms.config.utility;

import java.util.Arrays;

public enum IncurredAt {
    ORIGIN(1, "Origin"),
    DESTINATION(2, "Destination");

    private Integer incurredAtSeq;
    private String incurredAt;

    IncurredAt(Integer incurredAtSeq, String incurredAt) {
        this.incurredAtSeq = incurredAtSeq;
        this.incurredAt = incurredAt;
    }

    public static IncurredAt findOne(Integer incurredAtSeq) {
        return Arrays.stream(IncurredAt.values())
                .filter(x -> x.incurredAtSeq.equals(incurredAtSeq))
                .findFirst()
                .orElse(null);
    }

    public Integer getIncurredAtSeq() {
        return incurredAtSeq;
    }

    public String getIncurredAt() {
        return incurredAt;
    }

}

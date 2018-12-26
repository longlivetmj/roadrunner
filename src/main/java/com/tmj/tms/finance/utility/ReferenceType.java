package com.tmj.tms.finance.utility;

import java.util.Arrays;

public enum ReferenceType {

    TRANSPORT_BOOKING(1, "TRANSPORT BOOKING");

    private final Integer referenceType;
    private final String referenceTypeDescription;

    ReferenceType(Integer referenceType, String referenceTypeDescription) {
        this.referenceType = referenceType;
        this.referenceTypeDescription = referenceTypeDescription;
    }

    public static ReferenceType findOne(Integer referenceType) {
        return Arrays.stream(ReferenceType.values())
                .filter(x -> x.referenceType.equals(referenceType))
                .findFirst()
                .orElse(null);
    }

    public Integer getReferenceType() {
        return referenceType;
    }

    public String getReferenceTypeDescription() {
        return referenceTypeDescription;
    }
}

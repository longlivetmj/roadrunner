package com.tmj.tms.utility;

import java.util.Arrays;

public enum JobReferenceType {
    FREIGHT("Freight", 0),
    TRANSPORT("Transport", 1),
    COURIER("Courier", 2);

    private Integer referenceType;
    private String referenceTypeDescription;

    JobReferenceType(String referenceTypeDescription, Integer referenceType) {
        this.referenceType = referenceType;
        this.referenceTypeDescription = referenceTypeDescription;
    }

    public static JobReferenceType findOne(Integer referenceType) {
        return Arrays.stream(JobReferenceType.values())
                .filter(x -> x.referenceType.equals(referenceType))
                .findFirst()
                .orElse(null);
    }

    public Integer getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(Integer referenceType) {
        this.referenceType = referenceType;
    }

    public String getReferenceTypeDescription() {
        return referenceTypeDescription;
    }

    public void setReferenceTypeDescription(String referenceTypeDescription) {
        this.referenceTypeDescription = referenceTypeDescription;
    }
}

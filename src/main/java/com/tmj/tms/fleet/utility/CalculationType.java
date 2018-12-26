package com.tmj.tms.fleet.utility;

import java.util.Arrays;

public enum CalculationType {

    PERCENTAGE(1, "Percentage"),
    DAILY_FIXED(2, "Daily Fixed"),
    MONTHLY_FIXED(3, "Monthly Fixed");

    private Integer calculationType;
    private String calculationTypeDescription;

    CalculationType(Integer calculationType, String calculationTypeDescription) {
        this.calculationType = calculationType;
        this.calculationTypeDescription = calculationTypeDescription;
    }

    public static CalculationType findOne(Integer calculationType) {
        return Arrays.stream(CalculationType.values())
                .filter(x -> x.calculationType.equals(calculationType))
                .findFirst()
                .orElse(null);
    }

    public Integer getCalculationType() {
        return calculationType;
    }

    public void setCalculationType(Integer calculationType) {
        this.calculationType = calculationType;
    }

    public String getCalculationTypeDescription() {
        return calculationTypeDescription;
    }

    public void setCalculationTypeDescription(String calculationTypeDescription) {
        this.calculationTypeDescription = calculationTypeDescription;
    }
}

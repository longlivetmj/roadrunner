package com.tmj.tms.home.utility;

import java.util.Arrays;

public enum CostType {

    FUEL("Fuel", 1),
    SALARY("Salary", 2),
    SALARY_ADVANCE("Salary Advance", 3),
    SERVICE("Service", 4),
    MAINTENANCE("Maintenance", 5);

    private String costTypeDescription;
    private Integer costType;

    CostType(String costTypeDescription, Integer costType) {
        this.costTypeDescription = costTypeDescription;
        this.costType = costType;
    }

    public String getCostTypeDescription() {
        return costTypeDescription;
    }

    public void setCostTypeDescription(String costTypeDescription) {
        this.costTypeDescription = costTypeDescription;
    }

    public Integer getCostType() {
        return costType;
    }

    public void setCostType(Integer costType) {
        this.costType = costType;
    }
}

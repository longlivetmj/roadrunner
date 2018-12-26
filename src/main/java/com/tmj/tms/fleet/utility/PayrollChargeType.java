package com.tmj.tms.fleet.utility;

import java.util.Arrays;

public enum PayrollChargeType {

    DEDUCTION(1, "Deduction"),
    ALLOWANCE(2, "Allowance"),
    COMMISSION(3, "Commission"),
    COMPANY_CONTRIBUTION(4, "Company Contribution");

    private Integer payrollChargeType;
    private String payrollChargeTypeDescription;

    PayrollChargeType(Integer payrollChargeType, String payrollChargeTypeDescription) {
        this.payrollChargeType = payrollChargeType;
        this.payrollChargeTypeDescription = payrollChargeTypeDescription;
    }

    public static PayrollChargeType findOne(Integer payrollChargeType) {
        return Arrays.stream(PayrollChargeType.values())
                .filter(x -> x.payrollChargeType.equals(payrollChargeType))
                .findFirst()
                .orElse(null);
    }

    public Integer getPayrollChargeType() {
        return payrollChargeType;
    }

    public void setPayrollChargeType(Integer payrollChargeType) {
        this.payrollChargeType = payrollChargeType;
    }

    public String getPayrollChargeTypeDescription() {
        return payrollChargeTypeDescription;
    }

    public void setPayrollChargeTypeDescription(String payrollChargeTypeDescription) {
        this.payrollChargeTypeDescription = payrollChargeTypeDescription;
    }
}

package com.tmj.tms.finance.utility;

import java.util.Arrays;

public enum ChargeType {

    REVENUE(1, "REVENUE"),
    COST(2, "COST");

    private final Integer chargeType;
    private final String chargeTypeDiscription;

    ChargeType(Integer chargeType, String chargeTypeDiscription) {
        this.chargeType = chargeType;
        this.chargeTypeDiscription = chargeTypeDiscription;
    }

    public static ChargeType findOne(Integer chargeType) {
        return Arrays.stream(ChargeType.values())
                .filter(x -> x.chargeType.equals(chargeType))
                .findFirst()
                .orElse(null);
    }

    public Integer getChargeType() {
        return chargeType;
    }

    public String getChargeTypeDiscription() {
        return chargeTypeDiscription;
    }
}

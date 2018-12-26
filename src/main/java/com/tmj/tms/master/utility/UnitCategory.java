package com.tmj.tms.master.utility;

public enum UnitCategory {
    VOLUME("Volume"),
    WEIGHT("Weight"),
    PIECES("Pieces"),
    LENGTH("Length"),
    TIME("Time");

    private String unitCategory;

    UnitCategory(String unitCategory) {
        this.unitCategory = unitCategory;
    }

    public String getUnitCategory() {
        return unitCategory;
    }
}

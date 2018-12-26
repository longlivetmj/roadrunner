package com.tmj.tms.fleet.utility;

import java.util.Arrays;

public enum FuelType {

    PETROL(0, "Petrol"),
    DIESEL(1, "Diesel");

    private Integer fuelType;
    private String fuelTypeDescription;

    FuelType(Integer fuelType, String fuelTypeDescription) {
        this.fuelType = fuelType;
        this.fuelTypeDescription = fuelTypeDescription;
    }

    public static FuelType findOne(Integer fuelType) {
        return Arrays.stream(FuelType.values())
                .filter(x -> x.fuelType.equals(fuelType))
                .findFirst()
                .orElse(null);
    }

    public Integer getFuelType() {
        return fuelType;
    }

    public void setFuelType(Integer fuelType) {
        this.fuelType = fuelType;
    }

    public String getFuelTypeDescription() {
        return fuelTypeDescription;
    }

    public void setFuelTypeDescription(String fuelTypeDescription) {
        this.fuelTypeDescription = fuelTypeDescription;
    }
}

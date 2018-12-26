package com.tmj.tms.master.utility;

import java.util.Arrays;

public enum VehicleTypeCategory {
    BY_TRUCK_SIZE(1, "By Truck Size"),
    BY_WEIGHT(2, "By Weight"),
    BY_VOLUME(3, "By Volume"),
    BY_WHEELS(4, "By WHEELS");

    private Integer vehicleTypeCategory;
    private String vehicleTypeCategoryDescription;

    VehicleTypeCategory(Integer vehicleTypeCategory, String vehicleTypeCategoryDescription) {
        this.vehicleTypeCategory = vehicleTypeCategory;
        this.vehicleTypeCategoryDescription = vehicleTypeCategoryDescription;
    }

    public static VehicleTypeCategory findOne(Integer vehicleTypeCategory) {
        return Arrays.stream(VehicleTypeCategory.values())
                .filter(x -> x.vehicleTypeCategory.equals(vehicleTypeCategory))
                .findFirst()
                .orElse(null);
    }


    public Integer getVehicleTypeCategory() {
        return vehicleTypeCategory;
    }

    public void setVehicleTypeCategory(Integer vehicleTypeCategory) {
        this.vehicleTypeCategory = vehicleTypeCategory;
    }

    public String getVehicleTypeCategoryDescription() {
        return vehicleTypeCategoryDescription;
    }

    public void setVehicleTypeCategoryDescription(String vehicleTypeCategoryDescription) {
        this.vehicleTypeCategoryDescription = vehicleTypeCategoryDescription;
    }
}

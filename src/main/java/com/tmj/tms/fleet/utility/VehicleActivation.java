package com.tmj.tms.fleet.utility;

import java.util.Arrays;

public enum VehicleActivation {

    INACTIVE(0, "Inactive"),
    ACTIVE(1, "Active");

    private Integer vehicleActivation;
    private String vehicleActivationDescription;

    VehicleActivation(Integer vehicleActivation, String vehicleActivationDescription) {
        this.vehicleActivation = vehicleActivation;
        this.vehicleActivationDescription = vehicleActivationDescription;
    }

    public static VehicleActivation findOne(Integer vehicleActivation) {
        return Arrays.stream(VehicleActivation.values())
                .filter(x -> x.vehicleActivation.equals(vehicleActivation))
                .findFirst()
                .orElse(null);
    }


    public Integer getVehicleActivation() {
        return vehicleActivation;
    }

    public void setVehicleActivation(Integer vehicleActivation) {
        this.vehicleActivation = vehicleActivation;
    }

    public String getVehicleActivationDescription() {
        return vehicleActivationDescription;
    }

    public void setVehicleActivationDescription(String vehicleActivationDescription) {
        this.vehicleActivationDescription = vehicleActivationDescription;
    }
}

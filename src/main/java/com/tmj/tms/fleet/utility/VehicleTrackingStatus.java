package com.tmj.tms.fleet.utility;

public enum  VehicleTrackingStatus {

    ALL_VEHICLES(0, "All Vehicles"),
    CARGO_IN_HAND(1, "Cargo In Hand"),
    ARRIVED_AT_PICKUP(2, "Arrived at Pickup"),
    ARRIVED_AT_DELIVERY(3, "Arrived at Delivery"),
    NO_JOB_VEHICLES(4, "No Job Vehicles");

    private Integer trackingType;
    private String statusDescription;

    VehicleTrackingStatus(Integer trackingType, String statusDescription) {
        this.trackingType = trackingType;
        this.statusDescription = statusDescription;
    }

    public Integer getTrackingType() {
        return trackingType;
    }

    public void setTrackingType(Integer trackingType) {
        this.trackingType = trackingType;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
}

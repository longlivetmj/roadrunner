package com.tmj.tms.fleet.integration.auxiliary;

import java.util.Date;

public class VehicleLocationFireBase {

    private String gpsTerminalKey;
    private Double latitude;
    private Double longitude;
    private Integer direction;
    private Integer speed;
    private String vehicleNo;

    public VehicleLocationFireBase(String gpsTerminalKey, Double latitude, Double longitude, Integer direction, Integer speed, String vehicleNo) {
        this.gpsTerminalKey = gpsTerminalKey;
        this.latitude = latitude;
        this.longitude = longitude;
        this.direction = direction;
        this.speed = speed;
        this.vehicleNo = vehicleNo;
    }

    public String getGpsTerminalKey() {
        return gpsTerminalKey;
    }

    public void setGpsTerminalKey(String gpsTerminalKey) {
        this.gpsTerminalKey = gpsTerminalKey;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }
}

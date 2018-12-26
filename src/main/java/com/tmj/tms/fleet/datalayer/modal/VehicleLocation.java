package com.tmj.tms.fleet.datalayer.modal;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "vehicle_location")
public class VehicleLocation {

    private String gpsTerminalKey;
    private Date dateAndTime;
    private Double latitude;
    private Double longitude;
    private Integer speed;
    private String fuelLevel;
    private Integer direction;
    private Integer mileage;

    @Id
    @Column(name = "gps_terminal_key", nullable = false, unique = true)
    public String getGpsTerminalKey() {
        return gpsTerminalKey;
    }

    public void setGpsTerminalKey(String gpsTerminalKey) {
        this.gpsTerminalKey = gpsTerminalKey;
    }

    @Basic
    @Column(name = "date_time", nullable = false)
    public Date getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    @Basic
    @Column(name = "latitude", nullable = false)
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude", nullable = false)
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "speed", nullable = false)
    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    @Basic
    @Column(name = "fuel_level")
    public String getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(String fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    @Basic
    @Column(name = "direction", nullable = false)
    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    @Basic
    @Column(name = "mileage", nullable = false)
    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }
}

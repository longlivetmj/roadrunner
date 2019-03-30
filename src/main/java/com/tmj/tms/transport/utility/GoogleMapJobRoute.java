package com.tmj.tms.transport.utility;

import com.google.maps.model.LatLng;
import com.tmj.tms.master.datalayer.modal.FinalDestination;
import com.tmj.tms.utility.LatLong;

import java.util.List;

public class GoogleMapJobRoute {

    private String bookingNo;
    private LatLng center;
    private List<FinalDestination> finalDestinationList;
    private String eta;
    private Double distance;
    private Double chargeableKm;

    private LatLng currentLocation;
    private List<LatLong> currentPath;
    private Integer direction;

    public LatLng getCenter() {
        return center;
    }

    public void setCenter(LatLng center) {
        this.center = center;
    }

    public List<FinalDestination> getFinalDestinationList() {
        return finalDestinationList;
    }

    public void setFinalDestinationList(List<FinalDestination> finalDestinationList) {
        this.finalDestinationList = finalDestinationList;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    public Double getChargeableKm() {
        return chargeableKm;
    }

    public void setChargeableKm(Double chargeableKm) {
        this.chargeableKm = chargeableKm;
    }

    public LatLng getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(LatLng currentLocation) {
        this.currentLocation = currentLocation;
    }

    public List<LatLong> getCurrentPath() {
        return currentPath;
    }

    public void setCurrentPath(List<LatLong> currentPath) {
        this.currentPath = currentPath;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }
}

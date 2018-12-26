package com.tmj.tms.transport.utility;

import com.google.maps.model.LatLng;
import com.tmj.tms.master.datalayer.modal.FinalDestination;

import java.util.List;

public class GoogleMapJobRoute {

    private String bookingNo;
    private LatLng center;
    private List<FinalDestination> finalDestinationList;
    private String eta;
    private Double distance;
    private Double chargeableKm;

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
}

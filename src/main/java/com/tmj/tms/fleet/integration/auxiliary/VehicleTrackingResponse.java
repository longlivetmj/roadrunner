package com.tmj.tms.fleet.integration.auxiliary;

public class VehicleTrackingResponse {

    private String gpsTerminalKey;
    private String vehicleNo;
    private String message;

    public String getGpsTerminalKey() {
        return gpsTerminalKey;
    }

    public void setGpsTerminalKey(String gpsTerminalKey) {
        this.gpsTerminalKey = gpsTerminalKey;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

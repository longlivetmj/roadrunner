package com.tmj.tms.transport.datalayer.modal.auxiliary;

public class TransportBookingSummary {

    private Integer transportBookingSeq;

    private Integer connectingTransportBookingSeq;

    private Double placementKm;

    private Double cargoInHandKm;

    private Double chargeableKm;

    private String operationRemarks;

    private Integer startMeterReading;

    private Integer endMeterReading;

    public Integer getTransportBookingSeq() {
        return transportBookingSeq;
    }

    public void setTransportBookingSeq(Integer transportBookingSeq) {
        this.transportBookingSeq = transportBookingSeq;
    }

    public Integer getConnectingTransportBookingSeq() {
        return connectingTransportBookingSeq;
    }

    public void setConnectingTransportBookingSeq(Integer connectingTransportBookingSeq) {
        this.connectingTransportBookingSeq = connectingTransportBookingSeq;
    }

    public Double getPlacementKm() {
        return placementKm;
    }

    public void setPlacementKm(Double placementKm) {
        this.placementKm = placementKm;
    }

    public Double getCargoInHandKm() {
        return cargoInHandKm;
    }

    public void setCargoInHandKm(Double cargoInHandKm) {
        this.cargoInHandKm = cargoInHandKm;
    }

    public Double getChargeableKm() {
        return chargeableKm;
    }

    public void setChargeableKm(Double chargeableKm) {
        this.chargeableKm = chargeableKm;
    }

    public String getOperationRemarks() {
        return operationRemarks;
    }

    public void setOperationRemarks(String operationRemarks) {
        this.operationRemarks = operationRemarks;
    }

    public Integer getStartMeterReading() {
        return startMeterReading;
    }

    public void setStartMeterReading(Integer startMeterReading) {
        this.startMeterReading = startMeterReading;
    }

    public Integer getEndMeterReading() {
        return endMeterReading;
    }

    public void setEndMeterReading(Integer endMeterReading) {
        this.endMeterReading = endMeterReading;
    }
}

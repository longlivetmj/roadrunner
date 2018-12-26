package com.tmj.tms.transport.datalayer.modal.auxiliary;

import com.tmj.tms.transport.datalayer.modal.TransportBookingViaLocation;

import java.util.List;

public class TransportBookingViaLocationAux {

    private List<TransportBookingViaLocation> bookingViaLocations;

    private Integer transportBookingSeq;

    public List<TransportBookingViaLocation> getBookingViaLocations() {
        return bookingViaLocations;
    }

    public void setBookingViaLocations(List<TransportBookingViaLocation> bookingViaLocations) {
        this.bookingViaLocations = bookingViaLocations;
    }

    public Integer getTransportBookingSeq() {
        return transportBookingSeq;
    }

    public void setTransportBookingSeq(Integer transportBookingSeq) {
        this.transportBookingSeq = transportBookingSeq;
    }
}

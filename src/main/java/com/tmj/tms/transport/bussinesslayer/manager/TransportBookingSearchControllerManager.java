package com.tmj.tms.transport.bussinesslayer.manager;

import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.transport.datalayer.modal.auxiliary.TransportBookingSearch;

import java.util.List;

public interface TransportBookingSearchControllerManager {

    List<TransportBooking> searchBooking(TransportBookingSearch transportBookingSearch);

}

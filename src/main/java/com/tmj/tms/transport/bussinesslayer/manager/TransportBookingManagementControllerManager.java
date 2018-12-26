package com.tmj.tms.transport.bussinesslayer.manager;

import com.tmj.tms.master.datalayer.modal.VehicleType;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.transport.datalayer.modal.TransportBookingViaLocation;
import com.tmj.tms.transport.datalayer.modal.auxiliary.TransportBookingViaLocationAux;
import com.tmj.tms.utility.ResponseObject;

import java.util.List;

public interface TransportBookingManagementControllerManager {

    ResponseObject saveTransportBooking(TransportBooking transportBooking);

    ResponseObject updateTransportBooking(TransportBooking transportBooking);

    ResponseObject validateTransportBooking(TransportBooking transportBooking);

    ResponseObject addViaLocations(TransportBookingViaLocationAux transportBookingViaLocationAux);

    ResponseObject addViaLocationsIgnoreValidation(TransportBookingViaLocationAux transportBookingViaLocationAux);

    ResponseObject updateViaLocations(TransportBookingViaLocationAux transportBookingViaLocationAux);

    ResponseObject validateTransportBookingViaLocations(List<TransportBookingViaLocation> transportBookingViaLocationList);

    TransportBooking copyBooking(Integer bookingSeq);

    ResponseObject deleteBooking(Integer bookingSeq);

    List<VehicleType> findByStakeholderSeq(Integer customerSeq);

}

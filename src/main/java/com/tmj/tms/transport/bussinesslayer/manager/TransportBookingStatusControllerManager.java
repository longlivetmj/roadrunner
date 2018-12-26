package com.tmj.tms.transport.bussinesslayer.manager;

import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.transport.datalayer.modal.auxiliary.TransportBookingStatusSearch;
import com.tmj.tms.transport.utility.GoogleMapJobRoute;
import com.tmj.tms.utility.ResponseObject;

import java.security.Principal;
import java.util.Date;
import java.util.List;

public interface TransportBookingStatusControllerManager {

    List<TransportBooking> searchBooking(TransportBookingStatusSearch transportBookingStatusSearch);

    ResponseObject changeAAP(Integer transportBookingSeq, Date arrivedAtPickup, Principal principal);

    ResponseObject changeDFP(Integer transportBookingSeq, Date departedFromPickup, Principal principal);

    ResponseObject changeAAD(Integer transportBookingSeq, Date arrivedAtDelivery, Principal principal);

    ResponseObject changeDFD(Integer transportBookingSeq, Date departedFromDelivery, Principal principal);

    ResponseObject validateTimeUpdate(Integer transportBookingSeq, Date date, String dateType);

    GoogleMapJobRoute findGoogleMapJobRoute(Integer transportBookingSeq);

    ResponseObject changeActualEndLocation(Integer transportBookingSeq, Integer actualEndLocationSeq, Principal principal);

    ResponseObject changeActualStartLocation(Integer transportBookingSeq, Integer actualStartLocationSeq, Principal principal);

    ResponseObject changeDocumentsCollectedDate(Integer transportBookingSeq, Date documentsCollectedDate);

    ResponseObject changeCustomerReferenceNo(Integer transportBookingSeq, String customerReferenceNo);
}

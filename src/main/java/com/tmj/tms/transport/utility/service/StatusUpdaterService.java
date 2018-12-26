package com.tmj.tms.transport.utility.service;

import com.tmj.tms.transport.datalayer.modal.TransportBookingFeedback;
import com.tmj.tms.transport.datalayer.modal.TransportBookingStatus;
import com.tmj.tms.utility.ResponseObject;

public interface StatusUpdaterService {

    TransportBookingStatus vehicleAssigned(Integer transportBookingSeq);

    TransportBookingStatus vehicleRemoved(Integer transportBookingSeq);

    TransportBookingStatus timeUpdate(TransportBookingFeedback transportBookingFeedback);

    TransportBookingStatus kmConfirmed(Integer transportBookingSeq);
}

package com.tmj.tms.transport.bussinesslayer.manager;

import com.tmj.tms.transport.datalayer.modal.auxiliary.TransportBookingSummary;
import com.tmj.tms.utility.ResponseObject;

public interface TransportBookingSummaryControllerManager {

    ResponseObject saveTransportBookingFeedback(TransportBookingSummary transportBookingSummary);

    ResponseObject validateForSaving(TransportBookingSummary transportBookingSummary);
}

package com.tmj.tms.transport.utility;

import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.transport.datalayer.modal.TransportBookingStatus;
import com.tmj.tms.transport.datalayer.service.TransportBookingStatusService;
import com.tmj.tms.utility.ResponseObject;
import com.tmj.tms.utility.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusChecker {

    private final TransportBookingStatusService transportBookingStatusService;

    @Autowired
    public StatusChecker(TransportBookingStatusService transportBookingStatusService) {
        this.transportBookingStatusService = transportBookingStatusService;
    }

    public ResponseObject canUpdate(TransportBooking transportBooking) {
        ResponseObject responseObject = new ResponseObject();
        List<TransportBookingStatus> updatableList = this.transportBookingStatusService.findByUpdatable(YesOrNo.Yes.getYesOrNoSeq());
        boolean canUpdate = false;
        for (TransportBookingStatus transportBookingStatus : updatableList) {
            if (transportBooking.getCurrentStatus().equals(transportBookingStatus.getCurrentStatus())) {
                canUpdate = true;
                break;
            }
        }
        responseObject.setStatus(canUpdate);
        if (!canUpdate) {
            responseObject.setMessage("Cannot Update Job");
        }
        return responseObject;
    }

    public ResponseObject canDelete(TransportBooking transportBooking) {
        ResponseObject responseObject = new ResponseObject();
        List<TransportBookingStatus> updatableList = this.transportBookingStatusService.findByUpdatable(YesOrNo.Yes.getYesOrNoSeq());
        boolean canUpdate = false;
        for (TransportBookingStatus transportBookingStatus : updatableList) {
            if (transportBooking.getCurrentStatus().equals(transportBookingStatus.getCurrentStatus())) {
                canUpdate = true;
                break;
            }
        }
        responseObject.setStatus(canUpdate);
        if (!canUpdate) {
            responseObject.setMessage("Cannot Update Job");
        }
        return responseObject;
    }

}

package com.tmj.tms.transport.utility;

import com.tmj.tms.transport.datalayer.modal.TransportBookingFeedback;
import com.tmj.tms.transport.datalayer.modal.TransportBookingStatus;
import com.tmj.tms.transport.datalayer.modal.auxiliary.TransportBookingStatusUpdateAux;
import com.tmj.tms.transport.datalayer.service.TransportBookingStatusService;
import com.tmj.tms.transport.utility.service.TransportBookingStatusUpdateAuxService;
import com.tmj.tms.transport.utility.service.StatusUpdaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusUpdater implements StatusUpdaterService {

    private final TransportBookingStatusUpdateAuxService transportBookingStatusUpdateAuxService;
    private final TransportBookingStatusService transportBookingStatusService;

    @Autowired
    public StatusUpdater(TransportBookingStatusUpdateAuxService transportBookingStatusUpdateAuxService,
                         TransportBookingStatusService transportBookingStatusService) {
        this.transportBookingStatusUpdateAuxService = transportBookingStatusUpdateAuxService;
        this.transportBookingStatusService = transportBookingStatusService;
    }

    public TransportBookingStatus vehicleAssigned(Integer transportBookingSeq) {
        TransportBookingStatusUpdateAux transportBookingStatusUpdateAux = this.transportBookingStatusUpdateAuxService.findOne(transportBookingSeq);
        transportBookingStatusUpdateAux.setCurrentStatus(BookingStatus.ATTENDED.getCurrentStatus());
        this.transportBookingStatusUpdateAuxService.save(transportBookingStatusUpdateAux);
        return this.transportBookingStatusService.findOne(BookingStatus.ATTENDED.getCurrentStatus());
    }

    public TransportBookingStatus vehicleRemoved(Integer transportBookingSeq) {
        TransportBookingStatusUpdateAux transportBookingStatusUpdateAux = this.transportBookingStatusUpdateAuxService.findOne(transportBookingSeq);
        transportBookingStatusUpdateAux.setCurrentStatus(BookingStatus.ACCEPTED.getCurrentStatus());
        this.transportBookingStatusUpdateAuxService.save(transportBookingStatusUpdateAux);
        return this.transportBookingStatusService.findOne(BookingStatus.ACCEPTED.getCurrentStatus());
    }

    @Override
    public TransportBookingStatus timeUpdate(TransportBookingFeedback transportBookingFeedback) {
        TransportBookingStatusUpdateAux transportBookingStatusUpdateAux = this.transportBookingStatusUpdateAuxService.findOne(transportBookingFeedback.getTransportBookingSeq());
        if (transportBookingFeedback.getArrivedAtPickup() != null &&
                transportBookingFeedback.getDepartedFromPickup() == null &&
                transportBookingFeedback.getArrivedAtDelivery() == null &&
                transportBookingFeedback.getDepartedFromDelivery() == null) {
            transportBookingStatusUpdateAux.setCurrentStatus(BookingStatus.ARRIVED_AT_PICKUP.getCurrentStatus());
        } else if (transportBookingFeedback.getArrivedAtPickup() != null &&
                transportBookingFeedback.getDepartedFromPickup() != null &&
                transportBookingFeedback.getArrivedAtDelivery() == null &&
                transportBookingFeedback.getDepartedFromDelivery() == null) {
            transportBookingStatusUpdateAux.setCurrentStatus(BookingStatus.DEPARTED_FROM_PICKUP.getCurrentStatus());
        } else if (transportBookingFeedback.getArrivedAtPickup() != null &&
                transportBookingFeedback.getDepartedFromPickup() != null &&
                transportBookingFeedback.getArrivedAtDelivery() != null &&
                transportBookingFeedback.getDepartedFromDelivery() == null) {
            transportBookingStatusUpdateAux.setCurrentStatus(BookingStatus.ARRIVED_AT_DELIVERY.getCurrentStatus());
        } else if (transportBookingFeedback.getArrivedAtPickup() != null &&
                transportBookingFeedback.getDepartedFromDelivery() != null) {
            transportBookingStatusUpdateAux.setCurrentStatus(BookingStatus.JOB_COMPLETED.getCurrentStatus());
        }
        this.transportBookingStatusUpdateAuxService.save(transportBookingStatusUpdateAux);
        return this.transportBookingStatusService.findOne(transportBookingStatusUpdateAux.getCurrentStatus());
    }

    @Override
    public TransportBookingStatus kmConfirmed(Integer transportBookingSeq) {
        TransportBookingStatusUpdateAux transportBookingStatusUpdateAux = this.transportBookingStatusUpdateAuxService.findOne(transportBookingSeq);
        transportBookingStatusUpdateAux.setCurrentStatus(BookingStatus.KM_CONFIRMED.getCurrentStatus());
        this.transportBookingStatusUpdateAuxService.save(transportBookingStatusUpdateAux);
        return this.transportBookingStatusService.findOne(BookingStatus.KM_CONFIRMED.getCurrentStatus());
    }


}

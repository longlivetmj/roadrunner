package com.tmj.tms.transport.bussinesslayer.managerimpl;

import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.transport.bussinesslayer.manager.TransportBookingSummaryControllerManager;
import com.tmj.tms.transport.bussinesslayer.manager.VehicleAssignmentControllerManager;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.transport.datalayer.modal.TransportBookingFeedback;
import com.tmj.tms.transport.datalayer.modal.TransportBookingStatus;
import com.tmj.tms.transport.datalayer.modal.TransportBookingVehicle;
import com.tmj.tms.transport.datalayer.modal.auxiliary.TransportBookingSummary;
import com.tmj.tms.transport.datalayer.service.TransportBookingFeedbackService;
import com.tmj.tms.transport.datalayer.service.TransportBookingService;
import com.tmj.tms.transport.datalayer.service.TransportBookingStatusService;
import com.tmj.tms.transport.datalayer.service.TransportBookingVehicleService;
import com.tmj.tms.transport.utility.service.DistanceCalculatorService;
import com.tmj.tms.transport.utility.service.StatusUpdaterService;
import com.tmj.tms.utility.ResponseObject;
import com.tmj.tms.utility.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportBookingSummaryControllerManagerImpl implements TransportBookingSummaryControllerManager {

    private final TransportBookingFeedbackService transportBookingFeedbackService;
    private final TransportBookingVehicleService transportBookingVehicleService;
    private final VehicleAssignmentControllerManager vehicleAssignmentControllerManager;
    private final TransportBookingService transportBookingService;
    private final TransportBookingStatusService transportBookingStatusService;
    private final StatusUpdaterService statusUpdaterService;
    private final DistanceCalculatorService distanceCalculatorService;

    @Autowired
    public TransportBookingSummaryControllerManagerImpl(TransportBookingFeedbackService transportBookingFeedbackService,
                                                        TransportBookingVehicleService transportBookingVehicleService,
                                                        VehicleAssignmentControllerManager vehicleAssignmentControllerManager,
                                                        TransportBookingService transportBookingService,
                                                        TransportBookingStatusService transportBookingStatusService,
                                                        StatusUpdaterService statusUpdaterService,
                                                        DistanceCalculatorService distanceCalculatorService) {
        this.transportBookingFeedbackService = transportBookingFeedbackService;
        this.transportBookingVehicleService = transportBookingVehicleService;
        this.vehicleAssignmentControllerManager = vehicleAssignmentControllerManager;
        this.transportBookingService = transportBookingService;
        this.transportBookingStatusService = transportBookingStatusService;
        this.statusUpdaterService = statusUpdaterService;
        this.distanceCalculatorService = distanceCalculatorService;
    }

    @Override
    public ResponseObject saveTransportBookingFeedback(TransportBookingSummary transportBookingSummary) {
        ResponseObject responseObject = this.validateForSaving(transportBookingSummary);
        if (responseObject.isStatus()) {
            TransportBookingFeedback transportBookingFeedback = this.transportBookingFeedbackService.findOne(transportBookingSummary.getTransportBookingSeq());
            transportBookingFeedback.setConnectingTransportBookingSeq(transportBookingFeedback.getConnectingTransportBookingSeq());
            transportBookingFeedback.setCargoInHandKm(transportBookingSummary.getCargoInHandKm());
            transportBookingFeedback.setChargeableKm(this.distanceCalculatorService.calculateChargeableDistance(transportBookingSummary.getTransportBookingSeq()));
            transportBookingFeedback.setStartMeterReading(transportBookingSummary.getStartMeterReading());
            transportBookingFeedback.setEndMeterReading(transportBookingSummary.getEndMeterReading());
            transportBookingFeedback.setPlacementKm(transportBookingSummary.getPlacementKm());
            transportBookingFeedback.setOperationRemarks(transportBookingSummary.getOperationRemarks());
            this.transportBookingFeedbackService.save(transportBookingFeedback);
            responseObject.setMessage("Successfully Saved");
            if (transportBookingSummary.getConnectingTransportBookingSeq() != null) {
                TransportBookingVehicle currentBooking = this.transportBookingVehicleService.findByTransportBookingSeqAndStatus(transportBookingSummary.getTransportBookingSeq(), MasterDataStatus.APPROVED.getStatusSeq());
                TransportBookingVehicle transportBookingVehicle = new TransportBookingVehicle();
                transportBookingVehicle.setTransportBookingSeq(transportBookingSummary.getTransportBookingSeq());
                transportBookingVehicle.setVehicleSeq(currentBooking.getVehicleSeq());
                transportBookingVehicle.setDriverSeq(currentBooking.getDriverSeq());
                this.vehicleAssignmentControllerManager.assignVehicle(transportBookingVehicle);
            }

            if (transportBookingSummary.getChargeableKm() != null) {
                this.statusUpdaterService.kmConfirmed(transportBookingSummary.getTransportBookingSeq());
            }
        }
        return responseObject;
    }

    @Override
    public ResponseObject validateForSaving(TransportBookingSummary transportBookingSummary) {
        ResponseObject responseObject = new ResponseObject();
        TransportBooking transportBooking = this.transportBookingService.findOne(transportBookingSummary.getTransportBookingSeq());
        boolean inKmUpdateStatus = false;
        List<TransportBookingStatus> transportBookingStatusList = this.transportBookingStatusService.findByKmUpdate(YesOrNo.Yes.getYesOrNoSeq());
        for (TransportBookingStatus transportBookingStatus : transportBookingStatusList) {
            if (transportBooking.getCurrentStatus().equals(transportBookingStatus.getCurrentStatus())) {
                inKmUpdateStatus = true;
                break;
            }
        }
        if (!inKmUpdateStatus) {
            responseObject.setStatus(false);
            responseObject.setMessage("Cannot Update as Km Details!!");
        } else {
            responseObject.setStatus(true);
        }
        return responseObject;
    }
}

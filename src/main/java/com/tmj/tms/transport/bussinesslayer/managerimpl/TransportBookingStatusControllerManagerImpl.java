package com.tmj.tms.transport.bussinesslayer.managerimpl;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.querydsl.core.BooleanBuilder;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.utility.ChargesCalculator;
import com.tmj.tms.master.datalayer.modal.FinalDestination;
import com.tmj.tms.master.datalayer.service.FinalDestinationService;
import com.tmj.tms.transport.bussinesslayer.manager.TransportBookingStatusControllerManager;
import com.tmj.tms.transport.datalayer.modal.*;
import com.tmj.tms.transport.datalayer.modal.auxiliary.TransportBookingStatusSearch;
import com.tmj.tms.transport.datalayer.modal.auxiliary.TransportBookingStatusUpdateAux;
import com.tmj.tms.transport.datalayer.service.TransportBookingFeedbackService;
import com.tmj.tms.transport.datalayer.service.TransportBookingService;
import com.tmj.tms.transport.datalayer.service.TransportBookingStatusService;
import com.tmj.tms.transport.utility.BooleanBuilderDuplicateRemover;
import com.tmj.tms.transport.utility.GoogleMapJobRoute;
import com.tmj.tms.transport.utility.StatusChecker;
import com.tmj.tms.transport.utility.ViaLocationFormatter;
import com.tmj.tms.transport.utility.service.DistanceCalculatorService;
import com.tmj.tms.transport.utility.service.StatusUpdaterService;
import com.tmj.tms.transport.utility.service.TransportBookingStatusUpdateAuxService;
import com.tmj.tms.utility.DateFormatter;
import com.tmj.tms.utility.ResponseObject;
import com.tmj.tms.utility.YesOrNo;
import com.wialon.extra.SearchSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransportBookingStatusControllerManagerImpl implements TransportBookingStatusControllerManager {

    private final BooleanBuilderDuplicateRemover booleanBuilderDuplicateRemover;
    private final ViaLocationFormatter viaLocationFormatter;
    private final TransportBookingService transportBookingService;
    private final TransportBookingFeedbackService transportBookingFeedbackService;
    private final StatusUpdaterService statusUpdaterService;
    private final TransportBookingStatusUpdateAuxService transportBookingStatusUpdateAuxService;
    private final TransportBookingStatusService transportBookingStatusService;
    private final DistanceCalculatorService distanceCalculatorService;
    private final FinalDestinationService finalDestinationService;
    private final StatusChecker statusChecker;
    private final ChargesCalculator chargesCalculator;

    @Autowired
    public TransportBookingStatusControllerManagerImpl(BooleanBuilderDuplicateRemover booleanBuilderDuplicateRemover,
                                                       ViaLocationFormatter viaLocationFormatter,
                                                       TransportBookingService transportBookingService,
                                                       TransportBookingFeedbackService transportBookingFeedbackService,
                                                       StatusUpdaterService statusUpdaterService,
                                                       TransportBookingStatusUpdateAuxService transportBookingStatusUpdateAuxService,
                                                       TransportBookingStatusService transportBookingStatusService,
                                                       DistanceCalculatorService distanceCalculatorService,
                                                       FinalDestinationService finalDestinationService,
                                                       StatusChecker statusChecker,
                                                       ChargesCalculator chargesCalculator) {
        this.booleanBuilderDuplicateRemover = booleanBuilderDuplicateRemover;
        this.viaLocationFormatter = viaLocationFormatter;
        this.transportBookingService = transportBookingService;
        this.transportBookingFeedbackService = transportBookingFeedbackService;
        this.statusUpdaterService = statusUpdaterService;
        this.transportBookingStatusUpdateAuxService = transportBookingStatusUpdateAuxService;
        this.transportBookingStatusService = transportBookingStatusService;
        this.distanceCalculatorService = distanceCalculatorService;
        this.finalDestinationService = finalDestinationService;
        this.statusChecker = statusChecker;
        this.chargesCalculator = chargesCalculator;
    }

    @Override
    public List<TransportBooking> searchBooking(TransportBookingStatusSearch transportBookingStatusSearch) {
        List<TransportBooking> transportBookingList = null;
        try {
            QTransportBooking transportBooking = QTransportBooking.transportBooking;
            BooleanBuilder builder = new BooleanBuilder();
            builder = this.booleanBuilderDuplicateRemover.transportBooking(builder, transportBooking, transportBookingStatusSearch.getTransportBookingSeq());
            if (transportBookingStatusSearch.getTransportBookingSeq() == null) {
                if (transportBookingStatusSearch.getCustomerSeq() != null) {
                    builder.and(transportBooking.customerSeq.eq(transportBookingStatusSearch.getCustomerSeq()));
                }
                if (!transportBookingStatusSearch.getCurrentStatus().equals(-1)) {
                    builder.and(transportBooking.currentStatus.eq(transportBookingStatusSearch.getCurrentStatus()));
                }
                if (transportBookingStatusSearch.getPickupLocationSeq() != null) {
                    builder.and(transportBooking.pickupLocationSeq.eq(transportBookingStatusSearch.getPickupLocationSeq()));
                }
                if (transportBookingStatusSearch.getDeliveryLocationSeq() != null) {
                    builder.and(transportBooking.deliveryLocationSeq.eq(transportBookingStatusSearch.getDeliveryLocationSeq()));
                }
                if (transportBookingStatusSearch.getVehicleTypeSeq() != null) {
                    builder.and(transportBooking.vehicleTypeSeq.eq(transportBookingStatusSearch.getVehicleTypeSeq()));
                }
                if (transportBookingStatusSearch.getJobNo() != null) {
                    builder.and(transportBooking.job.jobNo.containsIgnoreCase(transportBookingStatusSearch.getJobNo()));
                }
                if (transportBookingStatusSearch.getCustomerRefNo() != null && transportBookingStatusSearch.getCustomerRefNo().trim().length() > 0) {
                    builder.and(transportBooking.customerReferenceNo.containsIgnoreCase(transportBookingStatusSearch.getCustomerRefNo()));
                }
                if (transportBookingStatusSearch.getStartDate() != null && transportBookingStatusSearch.getEndDate() != null) {
                    builder = this.booleanBuilderDuplicateRemover.dateFilterType(builder, transportBooking,
                            transportBookingStatusSearch.getDateFilterType(),
                            transportBookingStatusSearch.getStartDate(), DateFormatter.getEndOfTheDay(transportBookingStatusSearch.getEndDate()));
                }
                transportBookingList = (List<TransportBooking>) this.transportBookingService.findAll(builder, EntityGraphUtils.fromName("TransportBooking.vehicleAssignment"));
            } else {
                transportBookingList = (List<TransportBooking>) this.transportBookingService.findAll(builder, EntityGraphUtils.fromName("TransportBooking.vehicleAssignment"));
            }

            for (TransportBooking booking : transportBookingList) {
                booking.setViaLocationString(this.viaLocationFormatter.getViaLocationAsString(booking.getTransportBookingViaLocationList()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transportBookingList;
    }

    @Override
    public ResponseObject changeAAP(Integer transportBookingSeq, Date arrivedAtPickup, Principal principal) {
        ResponseObject responseObject = this.validateTimeUpdate(transportBookingSeq, arrivedAtPickup, "AAP");
        if (responseObject.isStatus()) {
            TransportBookingFeedback transportBookingFeedback = this.transportBookingFeedbackService.findOne(transportBookingSeq);
            if (transportBookingFeedback == null) {
                transportBookingFeedback = new TransportBookingFeedback();
                transportBookingFeedback.setTransportBookingSeq(transportBookingSeq);
                transportBookingFeedback.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
            }

            transportBookingFeedback.setArrivedAtPickup(arrivedAtPickup);
            transportBookingFeedback.setArrivedAtPickupUpdatedBy(principal.getName());
            transportBookingFeedback.setArrivedAtPickupUpdatedDate(new Date());
            transportBookingFeedback = this.transportBookingFeedbackService.save(transportBookingFeedback);
            TransportBookingStatus transportBookingStatus = this.statusUpdaterService.timeUpdate(transportBookingFeedback);
            responseObject.setMessage(new DateFormatter().returnLongFormattedDateTime(arrivedAtPickup));
            responseObject.setObject(transportBookingStatus);
        }
        return responseObject;
    }

    @Override
    public ResponseObject changeDFP(Integer transportBookingSeq, Date departedFromPickup, Principal principal) {
        ResponseObject responseObject = this.validateTimeUpdate(transportBookingSeq, departedFromPickup, "DFP");
        if (responseObject.isStatus()) {
            TransportBookingFeedback transportBookingFeedback = this.transportBookingFeedbackService.findOne(transportBookingSeq);
            if (transportBookingFeedback == null) {
                transportBookingFeedback = new TransportBookingFeedback();
                transportBookingFeedback.setTransportBookingSeq(transportBookingSeq);
                transportBookingFeedback.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
            }

            transportBookingFeedback.setDepartedFromPickup(departedFromPickup);
            transportBookingFeedback.setDepartedFromPickupUpdatedBy(principal.getName());
            transportBookingFeedback.setDepartedFromPickupUpdatedDate(new Date());
            transportBookingFeedback = this.transportBookingFeedbackService.save(transportBookingFeedback);
            TransportBookingStatus transportBookingStatus = this.statusUpdaterService.timeUpdate(transportBookingFeedback);
            responseObject.setMessage(new DateFormatter().returnLongFormattedDateTime(departedFromPickup));
            responseObject.setObject(transportBookingStatus);
        }
        return responseObject;
    }

    @Override
    public ResponseObject changeAAD(Integer transportBookingSeq, Date arrivedAtDelivery, Principal principal) {
        ResponseObject responseObject = this.validateTimeUpdate(transportBookingSeq, arrivedAtDelivery, "AAD");
        if (responseObject.isStatus()) {
            TransportBookingFeedback transportBookingFeedback = this.transportBookingFeedbackService.findOne(transportBookingSeq);
            if (transportBookingFeedback == null) {
                transportBookingFeedback = new TransportBookingFeedback();
                transportBookingFeedback.setTransportBookingSeq(transportBookingSeq);
                transportBookingFeedback.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
            }

            transportBookingFeedback.setArrivedAtDelivery(arrivedAtDelivery);
            transportBookingFeedback.setArrivedAtDeliveryUpdatedBy(principal.getName());
            transportBookingFeedback.setArrivedAtDeliveryUpdatedDate(new Date());
            transportBookingFeedback = this.transportBookingFeedbackService.save(transportBookingFeedback);
            TransportBookingStatus transportBookingStatus = this.statusUpdaterService.timeUpdate(transportBookingFeedback);
            responseObject.setMessage(new DateFormatter().returnLongFormattedDateTime(arrivedAtDelivery));
            responseObject.setObject(transportBookingStatus);
        }
        return responseObject;
    }

    @Override
    public ResponseObject changeDFD(Integer transportBookingSeq, Date departedFromDelivery, Principal principal) {
        ResponseObject responseObject = this.validateTimeUpdate(transportBookingSeq, departedFromDelivery, "DFD");
        if (responseObject.isStatus()) {
            TransportBookingFeedback transportBookingFeedback = this.transportBookingFeedbackService.findOne(transportBookingSeq);
            if (transportBookingFeedback == null) {
                transportBookingFeedback = new TransportBookingFeedback();
                transportBookingFeedback.setTransportBookingSeq(transportBookingSeq);
                transportBookingFeedback.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
            }

            transportBookingFeedback.setDepartedFromDelivery(departedFromDelivery);
            transportBookingFeedback.setDepartedFromDeliveryUpdatedBy(principal.getName());
            transportBookingFeedback.setDepartedFromDeliveryUpdatedDate(new Date());
            if (transportBookingFeedback.getChargeableKm() == null) {
                transportBookingFeedback.setChargeableKm(this.distanceCalculatorService.calculateChargeableDistance(transportBookingSeq));
                transportBookingFeedback.setPlacementKm(this.distanceCalculatorService.calculatePlacementDistance(transportBookingSeq));
            }
            transportBookingFeedback = this.transportBookingFeedbackService.save(transportBookingFeedback);

            this.chargesCalculator.revenueCostChargesCalculator(transportBookingSeq);

            TransportBookingStatus transportBookingStatus = this.statusUpdaterService.timeUpdate(transportBookingFeedback);
            responseObject.setMessage(new DateFormatter().returnLongFormattedDateTime(departedFromDelivery));
            responseObject.setObject(transportBookingStatus);
        }
        return responseObject;
    }

    @Override
    public ResponseObject changeActualEndLocation(Integer transportBookingSeq, Integer actualEndLocationSeq, Principal principal) {
        TransportBooking transportBooking = this.transportBookingService.findOne(transportBookingSeq);
        ResponseObject responseObject = this.statusChecker.canUpdate(transportBooking);
        if (responseObject.isStatus()) {
            transportBooking.setActualEndLocationSeq(actualEndLocationSeq);
            this.transportBookingService.save(transportBooking);
            FinalDestination finalDestination = this.finalDestinationService.findOne(actualEndLocationSeq);
            responseObject.setObject(finalDestination);
            responseObject.setStatus(true);
            this.chargesCalculator.chargeFixer(transportBooking);
        }
        return responseObject;
    }

    @Override
    public ResponseObject changeActualStartLocation(Integer transportBookingSeq, Integer actualStartLocationSeq, Principal principal) {
        TransportBooking transportBooking = this.transportBookingService.findOne(transportBookingSeq);
        ResponseObject responseObject = this.statusChecker.canUpdate(transportBooking);
        if (responseObject.isStatus()) {
            transportBooking.setActualStartLocationSeq(actualStartLocationSeq);
            this.transportBookingService.save(transportBooking);
            FinalDestination finalDestination = this.finalDestinationService.findOne(actualStartLocationSeq);
            responseObject.setObject(finalDestination);
            responseObject.setStatus(true);
            this.chargesCalculator.chargeFixer(transportBooking);
        }
        return responseObject;
    }

    @Override
    public ResponseObject changeDocumentsCollectedDate(Integer transportBookingSeq, Date documentsCollectedDate) {
        ResponseObject responseObject = new ResponseObject(true, "");
        TransportBooking transportBooking = this.transportBookingService.findOne(transportBookingSeq);
        if (transportBooking.getLocalInvoiceList() != null && transportBooking.getLocalInvoiceList().size() > 0) {
            responseObject.setStatus(false);
            responseObject.setMessage("Invoices has been Already Created!!");
        } else {
            TransportBookingFeedback transportBookingFeedback = this.transportBookingFeedbackService.findOne(transportBookingSeq);
            transportBookingFeedback.setDocumentsCollectedDate(documentsCollectedDate);
            this.transportBookingFeedbackService.save(transportBookingFeedback);
            responseObject.setStatus(true);
            responseObject.setMessage(new DateFormatter().returnSortFormattedDate(documentsCollectedDate));
        }
        return responseObject;
    }

    @Override
    public ResponseObject changeCustomerReferenceNo(Integer transportBookingSeq, String customerReferenceNo) {
        ResponseObject responseObject = new ResponseObject(true, "");
        TransportBooking transportBooking = this.transportBookingService.findOne(transportBookingSeq);
        if (transportBooking.getLocalInvoiceList() != null && transportBooking.getLocalInvoiceList().size() > 0) {
            responseObject.setStatus(false);
            responseObject.setMessage("Invoices has been already created");
        } else {
            transportBooking.setCustomerReferenceNo(customerReferenceNo);
            this.transportBookingService.save(transportBooking);
            responseObject.setStatus(true);
            responseObject.setMessage(customerReferenceNo);
        }
        return responseObject;
    }

    @Override
    public ResponseObject validateTimeUpdate(Integer transportBookingSeq, Date date, String dateType) {
        ResponseObject responseObject = new ResponseObject(true, "");
        TransportBookingStatusUpdateAux transportBookingStatusUpdateAux = this.transportBookingStatusUpdateAuxService.findOne(transportBookingSeq);
        List<TransportBookingStatus> transportBookingStatusList = this.transportBookingStatusService.findAllByStatusAndTimeUpdateOrderByCurrentStatus(MasterDataStatus.APPROVED.getStatusSeq(), YesOrNo.Yes.getYesOrNoSeq());
        boolean validStatus = false;
        for (TransportBookingStatus transportBookingStatus : transportBookingStatusList) {
            if (transportBookingStatusUpdateAux.getCurrentStatus().equals(transportBookingStatus.getCurrentStatus())) {
                validStatus = true;
                break;
            }
        }
        if (validStatus) {
            DateFormatter dateFormatter = new DateFormatter();
            Date allowedDateThreshold = dateFormatter.addMinutesToDate(new Date(), 5);
            TransportBookingFeedback transportBookingFeedback = this.transportBookingFeedbackService.findOne(transportBookingSeq);
            if (!date.before(allowedDateThreshold)) {
                responseObject.setStatus(false);
                responseObject.setMessage("Cannot enter future dates!!");
            }
            if (transportBookingFeedback != null) {
                switch (dateType) {
                    case "AAP":
                        if (transportBookingFeedback.getDepartedFromPickup() != null && !date.before(transportBookingFeedback.getDepartedFromPickup())) {
                            responseObject.setStatus(false);
                            responseObject.setMessage("Should be smaller than Departed from pickup point!!");
                        }
                        if (transportBookingFeedback.getArrivedAtDelivery() != null && !date.before(transportBookingFeedback.getArrivedAtDelivery())) {
                            responseObject.setStatus(false);
                            responseObject.setMessage("Should be smaller than Arrived at Delivery point!!");
                        }
                        if (transportBookingFeedback.getDepartedFromDelivery() != null && !date.before(transportBookingFeedback.getDepartedFromDelivery())) {
                            responseObject.setStatus(false);
                            responseObject.setMessage("Should be smaller than Departed from delivery point!!");
                        }
                        break;
                    case "DFP":
                        if (transportBookingFeedback.getArrivedAtPickup() != null && !date.after(transportBookingFeedback.getArrivedAtPickup())) {
                            responseObject.setStatus(false);
                            responseObject.setMessage("Should be Greater than Arrived from Pickup point!!");
                        }
                        if (transportBookingFeedback.getArrivedAtDelivery() != null && !date.before(transportBookingFeedback.getArrivedAtDelivery())) {
                            responseObject.setStatus(false);
                            responseObject.setMessage("Should be smaller than Arrived at Delivery point!!");
                        }
                        if (transportBookingFeedback.getDepartedFromDelivery() != null && !date.before(transportBookingFeedback.getDepartedFromDelivery())) {
                            responseObject.setStatus(false);
                            responseObject.setMessage("Should be smaller than Departed from Delivery point!!");
                        }
                        break;
                    case "AAD":
                        if (transportBookingFeedback.getArrivedAtPickup() != null && !date.after(transportBookingFeedback.getArrivedAtPickup())) {
                            responseObject.setStatus(false);
                            responseObject.setMessage("Should be Greater than Arrived at Pickup point!!");
                        }
                        if (transportBookingFeedback.getDepartedFromPickup() != null && !date.after(transportBookingFeedback.getDepartedFromPickup())) {
                            responseObject.setStatus(false);
                            responseObject.setMessage("Should be Greater than Departed from Pickup point!!");
                        }
                        if (transportBookingFeedback.getDepartedFromDelivery() != null && !date.before(transportBookingFeedback.getDepartedFromDelivery())) {
                            responseObject.setStatus(false);
                            responseObject.setMessage("Should be smaller than Departed from Delivery point!!");
                        }
                        break;
                    case "DFD":
                        if (transportBookingFeedback.getArrivedAtPickup() != null && !date.after(transportBookingFeedback.getArrivedAtPickup())) {
                            responseObject.setStatus(false);
                            responseObject.setMessage("Should be Greater than Arrived at Pickup point!!");
                        }
                        if (transportBookingFeedback.getDepartedFromPickup() != null && !date.after(transportBookingFeedback.getDepartedFromPickup())) {
                            responseObject.setStatus(false);
                            responseObject.setMessage("Should be Greater than Departed from Pickup point!!");
                        }
                        if (transportBookingFeedback.getArrivedAtDelivery() != null && !date.after(transportBookingFeedback.getArrivedAtDelivery())) {
                            responseObject.setStatus(false);
                            responseObject.setMessage("Should be Greater than Arrived at Delivery point!!");
                        }

                        if (transportBookingFeedback.getArrivedAtPickup() == null) {
                            responseObject.setStatus(false);
                            responseObject.setMessage("Arrived at pickup Date is not entered yet!!");
                        }
                        break;
                }
            }
        } else {
            responseObject.setStatus(false);
            responseObject.setMessage("Cannot update Times!!");
        }
        return responseObject;
    }

    @Override
    public GoogleMapJobRoute findGoogleMapJobRoute(Integer transportBookingSeq) {
        GoogleMapJobRoute googleMapJobRoute = new GoogleMapJobRoute();
        TransportBooking transportBooking = this.transportBookingService.findOne(transportBookingSeq, EntityGraphUtils.fromName("TransportBooking.forGoogleMaps"));
        googleMapJobRoute.setBookingNo(transportBooking.getBookingNo());
        googleMapJobRoute.setDistance(transportBooking.getEstimatedKm());
        googleMapJobRoute.setEta(transportBooking.getHumanReadableEta());
        googleMapJobRoute.setChargeableKm(transportBooking.getTransportBookingFeedback().getChargeableKm());
        List<FinalDestination> finalDestinationList = new ArrayList<>();
        finalDestinationList.add(transportBooking.getPickupLocation());
        List<TransportBookingViaLocation> transportBookingViaLocationList = transportBooking.getTransportBookingViaLocationList();
        for (TransportBookingViaLocation transportBookingViaLocation : transportBookingViaLocationList) {
            finalDestinationList.add(transportBookingViaLocation.getViaLocation());
        }
        finalDestinationList.add(transportBooking.getDeliveryLocation());
        googleMapJobRoute.setFinalDestinationList(finalDestinationList);
        googleMapJobRoute.setCenter(this.distanceCalculatorService.calculateCentroid(finalDestinationList));

        return googleMapJobRoute;
    }


}

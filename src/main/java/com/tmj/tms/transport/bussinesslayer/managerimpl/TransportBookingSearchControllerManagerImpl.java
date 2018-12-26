package com.tmj.tms.transport.bussinesslayer.managerimpl;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.querydsl.core.BooleanBuilder;
import com.tmj.tms.transport.bussinesslayer.manager.TransportBookingSearchControllerManager;
import com.tmj.tms.transport.datalayer.modal.QTransportBooking;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.transport.datalayer.modal.auxiliary.TransportBookingSearch;
import com.tmj.tms.transport.datalayer.service.TransportBookingService;
import com.tmj.tms.transport.utility.BooleanBuilderDuplicateRemover;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportBookingSearchControllerManagerImpl implements TransportBookingSearchControllerManager {

    private final TransportBookingService transportBookingService;
    private final BooleanBuilderDuplicateRemover booleanBuilderDuplicateRemover;

    @Autowired
    public TransportBookingSearchControllerManagerImpl(TransportBookingService transportBookingService,
                                                       BooleanBuilderDuplicateRemover booleanBuilderDuplicateRemover) {
        this.transportBookingService = transportBookingService;
        this.booleanBuilderDuplicateRemover = booleanBuilderDuplicateRemover;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TransportBooking> searchBooking(TransportBookingSearch transportBookingSearch) {
        List<TransportBooking> transportBookingList = null;
        try {
            QTransportBooking transportBooking = QTransportBooking.transportBooking;
            BooleanBuilder builder = new BooleanBuilder();
            builder = this.booleanBuilderDuplicateRemover.transportBooking(builder, transportBooking, transportBookingSearch.getTransportBookingSeq());
            if (transportBookingSearch.getTransportBookingSeq() == null) {
                if (transportBookingSearch.getCustomerSeq() != null) {
                    builder.and(transportBooking.customerSeq.eq(transportBookingSearch.getCustomerSeq()));
                }
                if (!transportBookingSearch.getCurrentStatus().equals(-1)) {
                    builder.and(transportBooking.currentStatus.eq(transportBookingSearch.getCurrentStatus()));
                }
                if (transportBookingSearch.getPickupLocationSeq() != null) {
                    builder.and(transportBooking.pickupLocationSeq.eq(transportBookingSearch.getPickupLocationSeq()));
                }
                if (transportBookingSearch.getDeliveryLocationSeq() != null) {
                    builder.and(transportBooking.deliveryLocationSeq.eq(transportBookingSearch.getDeliveryLocationSeq()));
                }
                if (transportBookingSearch.getVehicleTypeSeq() != null) {
                    builder.and(transportBooking.vehicleTypeSeq.eq(transportBookingSearch.getVehicleTypeSeq()));
                }
                if (transportBookingSearch.getJobNo() != null) {
                    builder.and(transportBooking.job.jobNo.containsIgnoreCase(transportBookingSearch.getJobNo()));
                }
                if (transportBookingSearch.getCustomerRefNo() != null) {
                    builder.and(transportBooking.customerReferenceNo.containsIgnoreCase(transportBookingSearch.getCustomerRefNo()));
                }
                if (transportBookingSearch.getStartDate() != null && transportBookingSearch.getEndDate() != null) {
                    builder.and(transportBooking.createdDate.between(transportBookingSearch.getStartDate(), transportBookingSearch.getEndDate()));
                }
                transportBookingList = (List<TransportBooking>) this.transportBookingService.findAll(builder, EntityGraphUtils.fromName("TransportBooking.default"));
            } else {
                transportBookingList = (List<TransportBooking>) this.transportBookingService.findAll(builder, EntityGraphUtils.fromName("TransportBooking.default"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transportBookingList;
    }

}

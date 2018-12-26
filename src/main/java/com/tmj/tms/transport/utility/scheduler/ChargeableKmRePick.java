package com.tmj.tms.transport.utility.scheduler;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.transport.datalayer.modal.TransportBookingFeedback;
import com.tmj.tms.transport.datalayer.modal.TransportBookingViaLocation;
import com.tmj.tms.transport.datalayer.service.TransportBookingFeedbackService;
import com.tmj.tms.transport.datalayer.service.TransportBookingService;
import com.tmj.tms.transport.datalayer.service.TransportBookingViaLocationService;
import com.tmj.tms.transport.utility.BookingStatus;
import com.tmj.tms.transport.utility.DistanceAndDuration;
import com.tmj.tms.transport.utility.service.DistanceCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile(value = "prod")
public class ChargeableKmRePick {

    private final TransportBookingService transportBookingService;
    private final DistanceCalculatorService distanceCalculatorService;
    private final TransportBookingFeedbackService transportBookingFeedbackService;
    private final TransportBookingViaLocationService transportBookingViaLocationService;

    @Autowired
    public ChargeableKmRePick(TransportBookingService transportBookingService,
                              DistanceCalculatorService distanceCalculatorService,
                              TransportBookingFeedbackService transportBookingFeedbackService,
                              TransportBookingViaLocationService transportBookingViaLocationService) {
        this.transportBookingService = transportBookingService;
        this.distanceCalculatorService = distanceCalculatorService;
        this.transportBookingFeedbackService = transportBookingFeedbackService;
        this.transportBookingViaLocationService = transportBookingViaLocationService;
    }

    @Scheduled(fixedRate = 60000 * 10)
    @Transactional
    public void rePickKmDetails() {
        try {
            System.out.println(">>>>>>>>>>>>>Started");
            List<TransportBooking> transportBookingList = this.transportBookingService.findFirst100ByCurrentStatusAndTransportBookingFeedback_ChargeableKmIsNull(BookingStatus.JOB_COMPLETED.getCurrentStatus());
            for (TransportBooking transportBooking : transportBookingList) {
                try {
                    TransportBookingFeedback transportBookingFeedback = this.transportBookingFeedbackService.findOne(transportBooking.getTransportBookingSeq());
                    if (transportBookingFeedback != null) {
                        List<Integer> deliveryList = new ArrayList<>();
                        List<TransportBookingViaLocation> transportBookingViaLocationList = this.transportBookingViaLocationService.findByTransportBookingSeqAndStatusOrderByRequestedArrivalTime(transportBooking.getTransportBookingSeq(), MasterDataStatus.APPROVED.getStatusSeq(), EntityGraphUtils.fromName("TransportBookingViaLocation.default"));
                        for (TransportBookingViaLocation transportBookingViaLocation : transportBookingViaLocationList) {
                            deliveryList.add(transportBookingViaLocation.getViaLocationSeq());
                        }

                        DistanceAndDuration distanceAndDuration = this.distanceCalculatorService.calculateDistance(transportBooking.getRequestedArrivalTime(),
                                true, null,
                                transportBooking.getPickupLocationSeq(), deliveryList, transportBooking.getDeliveryLocationSeq());
                        transportBooking.setEstimatedKm(distanceAndDuration.getDistance());
                        transportBooking.setHumanReadableEta(distanceAndDuration.getDuration());
                        transportBooking = this.transportBookingService.save(transportBooking);

                        transportBookingFeedback.setTransportBookingSeq(transportBooking.getTransportBookingSeq());
                        transportBookingFeedback.setChargeableKm(this.distanceCalculatorService.calculateChargeableDistance(transportBooking.getTransportBookingSeq()));
                        transportBookingFeedback.setPlacementKm(this.distanceCalculatorService.calculatePlacementDistance(transportBooking.getTransportBookingSeq()));
                        transportBookingFeedback.setCargoInHandKm(transportBooking.getEstimatedKm());
                        this.transportBookingFeedbackService.save(transportBookingFeedback);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error in " + transportBooking.getTransportBookingSeq());
                }
            }
            System.out.println(">>>>>>>>>>>>>Finished");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

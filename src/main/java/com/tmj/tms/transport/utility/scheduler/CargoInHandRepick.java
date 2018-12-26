package com.tmj.tms.transport.utility.scheduler;

import com.tmj.tms.finance.utility.ChargesCalculator;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.transport.datalayer.service.TransportBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile(value = "dev2")
public class CargoInHandRepick {

    private final TransportBookingService transportBookingService;
    private final ChargesCalculator chargesCalculator;

    @Autowired
    public CargoInHandRepick(TransportBookingService transportBookingService,
                             ChargesCalculator chargesCalculator) {
        this.transportBookingService = transportBookingService;
        this.chargesCalculator = chargesCalculator;
    }

    @Scheduled(fixedDelay = 60000 * 1000)
    @Transactional
    public void rePickKmDetails() {
        try {
            System.out.println(">>>>>>>>>>>>>Started Placement Repick");
//            List<TransportBooking> transportBookingList = this.transportBookingService.findByTransportBookingSeqGreaterThanAndTransportBookingFeedback_PlacementKm(4138, 0.0);
            List<TransportBooking> transportBookingList =  new ArrayList<>();
//            transportBookingList.add(this.transportBookingService.findOne(3666));
            for (TransportBooking transportBooking : transportBookingList) {
                this.chargesCalculator.chargeFixer(transportBooking);
            }
            System.out.println(">>>>>>>>>>>>>Finished Placement Repick");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

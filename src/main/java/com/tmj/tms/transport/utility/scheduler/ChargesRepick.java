package com.tmj.tms.transport.utility.scheduler;

import com.tmj.tms.finance.utility.ChargesCalculator;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.transport.datalayer.modal.TransportBookingFeedback;
import com.tmj.tms.transport.datalayer.service.TransportBookingFeedbackService;
import com.tmj.tms.transport.datalayer.service.TransportBookingService;
import com.tmj.tms.transport.utility.BookingStatus;
import com.tmj.tms.transport.utility.service.DistanceCalculatorService;
import com.tmj.tms.utility.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Profile(value = "prod")
public class ChargesRepick {

    private final TransportBookingService transportBookingService;
    private final ChargesCalculator chargesCalculator;
    private final TransportBookingFeedbackService transportBookingFeedbackService;

    @Autowired
    public ChargesRepick(TransportBookingService transportBookingService,
                         ChargesCalculator chargesCalculator,
                         TransportBookingFeedbackService transportBookingFeedbackService) {
        this.transportBookingService = transportBookingService;
        this.chargesCalculator = chargesCalculator;
        this.transportBookingFeedbackService = transportBookingFeedbackService;
    }

    @Scheduled(fixedRate = 60000 * 10)
    @Transactional
    public void rePickCharges() {
        try {
            System.out.println(">>>>>>>>>>>>>Started");
            List<TransportBooking> transportBookingList = this.transportBookingService.findFirst100ByCurrentStatusAndTransportBookingFeedback_ChargesCalculatedIsNull(BookingStatus.JOB_COMPLETED.getCurrentStatus());
            for (TransportBooking transportBooking : transportBookingList) {
                try {
                    TransportBookingFeedback transportBookingFeedback = this.transportBookingFeedbackService.findOne(transportBooking.getTransportBookingSeq());
                    if (transportBookingFeedback != null) {
                        transportBookingFeedback.setChargesCalculated(YesOrNo.Yes.getYesOrNoSeq());
                        this.transportBookingFeedbackService.save(transportBookingFeedback);
                        this.chargesCalculator.revenueCostChargesCalculator(transportBooking.getTransportBookingSeq());
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

package com.tmj.tms.transport.presentationlayer.controller;

import com.tmj.tms.finance.utility.ReferenceType;
import com.tmj.tms.finance.utility.TargetType;
import com.tmj.tms.fleet.utility.PaymentMode;
import com.tmj.tms.master.utility.StakeholderCashType;
import com.tmj.tms.transport.bussinesslayer.manager.TransportBookingSummaryControllerManager;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.transport.datalayer.modal.TransportBookingStatus;
import com.tmj.tms.transport.datalayer.modal.auxiliary.TransportBookingSummary;
import com.tmj.tms.transport.datalayer.service.ConnectingJobSearchService;
import com.tmj.tms.transport.datalayer.service.TransportBookingService;
import com.tmj.tms.transport.datalayer.service.TransportBookingStatusService;
import com.tmj.tms.transport.utility.ArrivalConfirmation;
import com.tmj.tms.transport.utility.DropPick;
import com.tmj.tms.utility.NDaysBefore;
import com.tmj.tms.utility.ResponseObject;
import com.tmj.tms.utility.TrueOrFalse;
import com.tmj.tms.utility.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/transport/transportBookingSummary")
public class TransportBookingSummaryController {

    private final TransportBookingService transportBookingService;
    private final TransportBookingSummaryControllerManager transportBookingSummaryControllerManager;
    private final ConnectingJobSearchService connectingJobSearchService;
    private final TransportBookingStatusService transportBookingStatusService;

    @Autowired
    public TransportBookingSummaryController(TransportBookingService transportBookingService,
                                             TransportBookingSummaryControllerManager transportBookingSummaryControllerManager,
                                             ConnectingJobSearchService connectingJobSearchService,
                                             TransportBookingStatusService transportBookingStatusService) {
        this.transportBookingService = transportBookingService;
        this.transportBookingSummaryControllerManager = transportBookingSummaryControllerManager;
        this.connectingJobSearchService = connectingJobSearchService;
        this.transportBookingStatusService = transportBookingStatusService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingSummary_VIEW')")
    public String getPage(@RequestParam(value = "bookingSeq") Integer bookingSeq, Model model) {
        this.pageLoad(model);
        TransportBooking transportBooking = this.transportBookingService.findOne(bookingSeq);
        List<Integer> statusList = this.transportBookingStatusService.findByVehicleAssign(YesOrNo.Yes.getYesOrNoSeq()).stream().map(TransportBookingStatus::getCurrentStatus).collect(Collectors.toList());
        Date date = new NDaysBefore().getDateNDaysAfterDate(transportBooking.getRequestedDeliveryTime(), 2);
        model.addAttribute("booking", transportBooking);
        model.addAttribute("connectingBookingList", this.connectingJobSearchService.findByRequestedArrivalTimeAfterAndRequestedArrivalTimeBeforeAndCurrentStatusIn(transportBooking.getRequestedArrivalTime(), date, statusList));
        return "transport/transportBookingSummary";
    }

    @RequestMapping(value = "/saveFeedback", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingSummary_UPDATE')")
    public
    @ResponseBody
    ResponseObject saveTransportBookingFeedback(@ModelAttribute TransportBookingSummary transportBookingSummary) {
        return this.transportBookingSummaryControllerManager.saveTransportBookingFeedback(transportBookingSummary);
    }

    private void pageLoad(Model model) {
        model.addAttribute("trueOrFalse", TrueOrFalse.values());
        model.addAttribute("paymentModeList", PaymentMode.values());
        model.addAttribute("cashCreditList", StakeholderCashType.values());
        model.addAttribute("dropPickList", DropPick.values());
        model.addAttribute("arrivalConfirmationData", ArrivalConfirmation.valuesForJEditable());
        model.addAttribute("targetType", TargetType.TRANSPORT_JOB.getTargetType());
        model.addAttribute("referenceType", ReferenceType.TRANSPORT_BOOKING.getReferenceType());
    }
}

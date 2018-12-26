package com.tmj.tms.transport.presentationlayer.controller;

import com.tmj.tms.config.datalayer.service.ModuleService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.datalayer.modal.FinalDestination;
import com.tmj.tms.master.datalayer.modal.Stakeholder;
import com.tmj.tms.master.datalayer.modal.VehicleType;
import com.tmj.tms.master.datalayer.service.FinalDestinationService;
import com.tmj.tms.master.datalayer.service.StakeholderService;
import com.tmj.tms.master.datalayer.service.StakeholderTypeService;
import com.tmj.tms.master.datalayer.service.VehicleTypeService;
import com.tmj.tms.transport.bussinesslayer.manager.TransportBookingStatusControllerManager;
import com.tmj.tms.transport.datalayer.modal.auxiliary.TransportBookingStatusSearch;
import com.tmj.tms.transport.datalayer.service.TransportBookingStatusService;
import com.tmj.tms.transport.utility.DateFilterType;
import com.tmj.tms.transport.utility.GoogleMapJobRoute;
import com.tmj.tms.utility.MultipleDateEditor;
import com.tmj.tms.utility.NDaysBefore;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/transport/transportBookingStatus")
public class TransportBookingStatusController {

    private final ModuleService moduleService;
    private final HttpSession httpSession;
    private final TransportBookingStatusService transportBookingStatusService;
    private final FinalDestinationService finalDestinationService;
    private final StakeholderService stakeholderService;
    private final StakeholderTypeService stakeholderTypeService;
    private final VehicleTypeService vehicleTypeService;
    private final TransportBookingStatusControllerManager transportBookingStatusControllerManager;

    @Autowired
    public TransportBookingStatusController(ModuleService moduleService,
                                            HttpSession httpSession,
                                            TransportBookingStatusService transportBookingStatusService,
                                            FinalDestinationService finalDestinationService,
                                            StakeholderService stakeholderService,
                                            StakeholderTypeService stakeholderTypeService,
                                            VehicleTypeService vehicleTypeService,
                                            TransportBookingStatusControllerManager transportBookingStatusControllerManager) {
        this.moduleService = moduleService;
        this.httpSession = httpSession;
        this.transportBookingStatusService = transportBookingStatusService;
        this.finalDestinationService = finalDestinationService;
        this.stakeholderService = stakeholderService;
        this.stakeholderTypeService = stakeholderTypeService;
        this.vehicleTypeService = vehicleTypeService;
        this.transportBookingStatusControllerManager = transportBookingStatusControllerManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingStatus_VIEW')")
    public String getPage(Model model) {
        this.pageLoad(model);
        return "transport/transportBookingStatus";
    }

    @RequestMapping(value = "/findBookings", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingStatus_VIEW')")
    public String findBookings(@ModelAttribute TransportBookingStatusSearch transportBookingStatusSearch,
                               Model model) {
        model.addAttribute("bookingList", this.transportBookingStatusControllerManager.searchBooking(transportBookingStatusSearch));
        return "transport/content/transportBookingStatusData";
    }

    @RequestMapping(value = "/changeAAP", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingStatus_TIME-UPDATE')")
    public
    @ResponseBody
    ResponseObject changeAAP(@RequestParam(value = "id") Integer transportBookingSeq,
                             @RequestParam(value = "value") Date arrivedAtPickup,
                             Principal principal) {
        return this.transportBookingStatusControllerManager.changeAAP(transportBookingSeq, arrivedAtPickup, principal);
    }

    @RequestMapping(value = "/changeActualStartLocation", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingStatus_TIME-UPDATE')")
    public
    @ResponseBody
    ResponseObject changeActualStartLocation(@RequestParam(value = "id") Integer transportBookingSeq,
                                             @RequestParam(value = "value") Integer actualStartLocationSeq,
                                             Principal principal) {
        return this.transportBookingStatusControllerManager.changeActualStartLocation(transportBookingSeq, actualStartLocationSeq, principal);
    }

    @RequestMapping(value = "/changeActualEndLocation", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingStatus_TIME-UPDATE')")
    public
    @ResponseBody
    ResponseObject changeActualEndLocation(@RequestParam(value = "id") Integer transportBookingSeq,
                                           @RequestParam(value = "value") Integer actualEndLocationSeq,
                                           Principal principal) {
        return this.transportBookingStatusControllerManager.changeActualEndLocation(transportBookingSeq, actualEndLocationSeq, principal);
    }

    @RequestMapping(value = "/changeDFP", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingStatus_TIME-UPDATE')")
    public
    @ResponseBody
    ResponseObject changeDFP(@RequestParam(value = "id") Integer transportBookingSeq,
                             @RequestParam(value = "value") Date departedFromPickup,
                             Principal principal) {
        return this.transportBookingStatusControllerManager.changeDFP(transportBookingSeq, departedFromPickup, principal);
    }

    @RequestMapping(value = "/changeAAD", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingStatus_TIME-UPDATE')")
    public
    @ResponseBody
    ResponseObject changeAAD(@RequestParam(value = "id") Integer transportBookingSeq,
                             @RequestParam(value = "value") Date arrivedAtDelivery,
                             Principal principal) {
        return this.transportBookingStatusControllerManager.changeAAD(transportBookingSeq, arrivedAtDelivery, principal);
    }

    @RequestMapping(value = "/changeDFD", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingStatus_TIME-UPDATE')")
    public
    @ResponseBody
    ResponseObject changeDFD(@RequestParam(value = "id") Integer transportBookingSeq,
                             @RequestParam(value = "value") Date departedFromDelivery,
                             Principal principal) {
        return this.transportBookingStatusControllerManager.changeDFD(transportBookingSeq, departedFromDelivery, principal);
    }

    @RequestMapping(value = "/changeDocumentsCollectedDate", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingStatus_TIME-UPDATE')")
    public
    @ResponseBody
    ResponseObject changeDocumentsCollectedDate(@RequestParam(value = "id") Integer transportBookingSeq,
                                                @RequestParam(value = "value") Date documentsCollectedDate) {
        return this.transportBookingStatusControllerManager.changeDocumentsCollectedDate(transportBookingSeq, documentsCollectedDate);
    }

    @RequestMapping(value = "/changeCustomerReferenceNo", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingStatus_TIME-UPDATE')")
    public
    @ResponseBody
    ResponseObject changeCustomerReferenceNo(@RequestParam(value = "id") Integer transportBookingSeq,
                                             @RequestParam(value = "value") String customerReferenceNo) {
        return this.transportBookingStatusControllerManager.changeCustomerReferenceNo(transportBookingSeq, customerReferenceNo);
    }

    @RequestMapping(value = "/findCustomer", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingStatus_VIEW')")
    public
    @ResponseBody
    List<Stakeholder> findCustomer(@RequestParam("q") String searchParam) {
        return this.stakeholderService.getStakeholderDetails(searchParam,
                this.stakeholderTypeService.findByStakeholderTypeCodeAndStatus("CUSTOMER", MasterDataStatus.APPROVED.getStatusSeq()).getStakeholderTypeSeq(),
                Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString()),
                MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findFinalDestination", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingStatus_VIEW')")
    public
    @ResponseBody
    List<FinalDestination> findFinalDestination(@RequestParam("q") String searchParam) {
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        return this.finalDestinationService.findByDestinationContainingIgnoreCaseAndStatusAndCompanyProfileSeq(searchParam,
                MasterDataStatus.APPROVED.getStatusSeq(),
                companyProfileSeq);
    }

    @RequestMapping(value = "/findVehicleType", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingStatus_VIEW')")
    public
    @ResponseBody
    List<VehicleType> findVehicleType(@RequestParam("q") String searchParam) {
        return this.vehicleTypeService.findByVehicleTypeNameStartsWithIgnoreCaseAndStatus(searchParam, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findGoogleMapJobRoute/{transportBookingSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingStatus_VIEW')")
    public
    @ResponseBody
    GoogleMapJobRoute findGoogleMapJobRoute(@PathVariable("transportBookingSeq") Integer transportBookingSeq) {
        return this.transportBookingStatusControllerManager.findGoogleMapJobRoute(transportBookingSeq);
    }

    private void pageLoad(Model model) {
        Integer moduleSeq = this.moduleService.findByModuleCode("TRANSPORT").getModuleSeq();
        model.addAttribute("departmentList", this.httpSession.getAttribute("userDepartmentList" + moduleSeq));
        model.addAttribute("statusList", this.transportBookingStatusService.findAllByStatusOrderByCurrentStatus(MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("moduleSeq", moduleSeq);
        model.addAttribute("companyProfileSeq", this.httpSession.getAttribute("companyProfileSeq"));
        model.addAttribute("endDate", new Date());
        model.addAttribute("startDate", new NDaysBefore().getDateNDaysBeforeCurrentDate(5));
        model.addAttribute("dateFilterTypeList", DateFilterType.values());
        model.addAttribute("defaultFilterType", DateFilterType.ARRIVING_DATE.getDateFilterType());
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new MultipleDateEditor());
    }

}

package com.tmj.tms.transport.presentationlayer.controller;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.datalayer.modal.*;
import com.tmj.tms.master.datalayer.service.ContainerTypeService;
import com.tmj.tms.master.datalayer.service.FinalDestinationService;
import com.tmj.tms.master.datalayer.service.StakeholderService;
import com.tmj.tms.master.datalayer.service.StakeholderTypeService;
import com.tmj.tms.transport.bussinesslayer.manager.TransportBookingManagementControllerManager;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.transport.datalayer.modal.TransportBookingViaLocation;
import com.tmj.tms.transport.datalayer.modal.auxiliary.TransportBookingViaLocationAux;
import com.tmj.tms.transport.datalayer.service.TransportBookingService;
import com.tmj.tms.transport.datalayer.service.TransportBookingViaLocationService;
import com.tmj.tms.transport.utility.TransportBookingUtils;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/transport/transportBookingManagement")
public class TransportBookingManagementController {

    private final HttpSession httpSession;
    private final StakeholderService stakeholderService;
    private final StakeholderTypeService stakeholderTypeService;
    private final FinalDestinationService finalDestinationService;
    private final ContainerTypeService containerTypeService;
    private final TransportBookingManagementControllerManager transportBookingManagementControllerManager;
    private final TransportBookingService transportBookingService;
    private final TransportBookingViaLocationService transportBookingViaLocationService;
    private final TransportBookingUtils transportBookingUtils;

    @Autowired
    public TransportBookingManagementController(HttpSession httpSession,
                                                StakeholderService stakeholderService,
                                                StakeholderTypeService stakeholderTypeService,
                                                FinalDestinationService finalDestinationService,
                                                ContainerTypeService containerTypeService,
                                                TransportBookingManagementControllerManager transportBookingManagementControllerManager,
                                                TransportBookingService transportBookingService,
                                                TransportBookingViaLocationService transportBookingViaLocationService,
                                                TransportBookingUtils transportBookingUtils) {
        this.httpSession = httpSession;
        this.stakeholderService = stakeholderService;
        this.stakeholderTypeService = stakeholderTypeService;
        this.finalDestinationService = finalDestinationService;
        this.containerTypeService = containerTypeService;
        this.transportBookingManagementControllerManager = transportBookingManagementControllerManager;
        this.transportBookingService = transportBookingService;
        this.transportBookingViaLocationService = transportBookingViaLocationService;
        this.transportBookingUtils = transportBookingUtils;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingManagement_VIEW')")
    public String getPage(Model model) {
        this.pageLoad(model);
        return "transport/transportBookingManagement";
    }

    @RequestMapping(params = "bookingSeq", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingManagement_VIEW')")
    public String loadBooking(@RequestParam(value = "bookingSeq") Integer bookingSeq,
                              Model model) {
        this.pageLoad(model);
        model.addAttribute("booking", this.transportBookingService.findOne(bookingSeq, EntityGraphUtils.fromName("TransportBooking.createBooking")));
        return "transport/transportBookingManagement";
    }

    @RequestMapping(value = "/copyBooking/{bookingSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingManagement_VIEW')")
    public String copyBooking(@PathVariable(value = "bookingSeq") Integer bookingSeq,
                              Model model) {
        this.pageLoad(model);
        model.addAttribute("booking", this.transportBookingManagementControllerManager.copyBooking(bookingSeq));
        return "transport/transportBookingManagement";
    }

    @RequestMapping(value = "/saveTransportBooking", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject saveTransportBooking(@Valid @ModelAttribute TransportBooking transportBooking) {
        return this.transportBookingManagementControllerManager.saveTransportBooking(transportBooking);
    }

    @RequestMapping(value = "/updateTransportBooking", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateVehicle(@Valid @ModelAttribute TransportBooking transportBooking) {
        return this.transportBookingManagementControllerManager.updateTransportBooking(transportBooking);
    }

    @RequestMapping(value = "/addViaLocations", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject addViaLocations(@Valid @ModelAttribute TransportBookingViaLocationAux transportBookingViaLocationAux) {
        return this.transportBookingManagementControllerManager.addViaLocations(transportBookingViaLocationAux);
    }

    @RequestMapping(value = "/updateViaLocations", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateViaLocations(@Valid @ModelAttribute TransportBookingViaLocationAux transportBookingViaLocationAux) {
        return this.transportBookingManagementControllerManager.updateViaLocations(transportBookingViaLocationAux);
    }

    @RequestMapping(value = "/findCustomer", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingManagement_VIEW')")
    public
    @ResponseBody
    List<Stakeholder> findCustomer(@RequestParam("q") String searchParam) {
        return this.stakeholderService.getStakeholderDetails(searchParam,
                this.stakeholderTypeService.findByStakeholderTypeCodeAndStatus("CUSTOMER", MasterDataStatus.APPROVED.getStatusSeq()).getStakeholderTypeSeq(),
                Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString()),
                MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findShipper", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingManagement_VIEW')")
    public
    @ResponseBody
    List<Stakeholder> findShipper(@RequestParam("q") String searchParam) {
        return this.stakeholderService.getStakeholderDetails(searchParam,
                this.stakeholderTypeService.findByStakeholderTypeCodeAndStatus("SHIPPER", MasterDataStatus.APPROVED.getStatusSeq()).getStakeholderTypeSeq(),
                Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString()),
                MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findConsignee", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingManagement_VIEW')")
    public
    @ResponseBody
    List<Stakeholder> findConsignee(@RequestParam("q") String searchParam) {
        return this.stakeholderService.getStakeholderDetails(searchParam,
                this.stakeholderTypeService.findByStakeholderTypeCodeAndStatus("CONSIGNEE", MasterDataStatus.APPROVED.getStatusSeq()).getStakeholderTypeSeq(),
                Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString()),
                MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findStakeholderSeqSeq/{customerSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingManagement_VIEW')")
    public
    @ResponseBody
    Stakeholder findStakeholder(@PathVariable("customerSeq") Integer customerSeq) {
        return this.stakeholderService.findByStakeholderSeq(customerSeq);
    }

    @RequestMapping(value = "/findVehicleTypeList/{customerSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingManagement_VIEW')")
    public
    @ResponseBody
    List<VehicleType> findVehicleTypeList(@PathVariable("customerSeq") Integer customerSeq) {
        return this.transportBookingManagementControllerManager.findByStakeholderSeq(customerSeq);
    }

    @RequestMapping(value = "/findAddressBookByFinalDestinationSeq/{finalDestinationSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingManagement_VIEW')")
    public
    @ResponseBody
    AddressBook findAddressBookByFinalDestinationSeq(@PathVariable("finalDestinationSeq") Integer finalDestinationSeq) {
        FinalDestination finalDestination = this.finalDestinationService.findOne(finalDestinationSeq);
        return finalDestination.getAddressBook();
    }

    @RequestMapping(value = "/findFinalDestination", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingManagement_VIEW')")
    public
    @ResponseBody
    List<FinalDestination> findFinalDestination(@RequestParam("q") String searchParam) {
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        return this.finalDestinationService.findByDestinationContainingIgnoreCaseAndStatusAndCompanyProfileSeq(searchParam,
                MasterDataStatus.APPROVED.getStatusSeq(),
                companyProfileSeq);
    }

    @RequestMapping(value = "/findContainerType", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingManagement_VIEW')")
    public
    @ResponseBody
    List<ContainerType> findContainerType(@RequestParam("q") String searchParam) {
        return this.containerTypeService.findByContainerTypeNameStartsWithIgnoreCaseAndStatus(searchParam, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findByTransportBookingSeq/{transportBookingSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingManagement_VIEW')")
    public
    @ResponseBody
    TransportBooking findByTransportBookingSeq(@PathVariable("transportBookingSeq") Integer transportBookingSeq) {
        return this.transportBookingService.findOne(transportBookingSeq, EntityGraphUtils.fromName("TransportBooking.createBooking"));
    }

    @RequestMapping(value = "/findViaLocationsByBookingSeq/{transportBookingSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingManagement_VIEW')")
    public
    @ResponseBody
    List<TransportBookingViaLocation> findViaLocationsByBookingSeq(@PathVariable("transportBookingSeq") Integer transportBookingSeq) {
        return this.transportBookingViaLocationService.findByTransportBookingSeqAndStatusOrderByRequestedArrivalTime(transportBookingSeq,
                MasterDataStatus.APPROVED.getStatusSeq(), EntityGraphUtils.fromName("TransportBookingViaLocation.createViaLocation"));
    }

    @RequestMapping(value = "/deleteBooking", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingManagement_DELETE')")
    public
    @ResponseBody
    ResponseObject deleteBooking(@RequestParam("bookingSeq") Integer bookingSeq) {
        return this.transportBookingManagementControllerManager.deleteBooking(bookingSeq);
    }

    private void pageLoad(Model model) {
        model = this.transportBookingUtils.loadFormData(model);
        model.addAttribute("booking", this.transportBookingUtils.getDefaultBooking());
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}
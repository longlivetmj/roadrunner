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
import com.tmj.tms.transport.bussinesslayer.manager.TransportBookingSearchControllerManager;
import com.tmj.tms.transport.datalayer.modal.auxiliary.TransportBookingSearch;
import com.tmj.tms.transport.datalayer.service.TransportBookingStatusService;
import com.tmj.tms.utility.NDaysBefore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/transport/transportBookingSearch")
public class TransportBookingSearchController {

    private final ModuleService moduleService;
    private final HttpSession httpSession;
    private final TransportBookingStatusService transportBookingStatusService;
    private final StakeholderService stakeholderService;
    private final StakeholderTypeService stakeholderTypeService;
    private final VehicleTypeService vehicleTypeService;
    private final FinalDestinationService finalDestinationService;
    private final TransportBookingSearchControllerManager transportBookingSearchControllerManager;

    @Autowired
    public TransportBookingSearchController(ModuleService moduleService,
                                            HttpSession httpSession,
                                            TransportBookingStatusService transportBookingStatusService,
                                            StakeholderService stakeholderService,
                                            StakeholderTypeService stakeholderTypeService,
                                            VehicleTypeService vehicleTypeService,
                                            FinalDestinationService finalDestinationService,
                                            TransportBookingSearchControllerManager transportBookingSearchControllerManager) {
        this.moduleService = moduleService;
        this.httpSession = httpSession;
        this.transportBookingStatusService = transportBookingStatusService;
        this.stakeholderService = stakeholderService;
        this.stakeholderTypeService = stakeholderTypeService;
        this.vehicleTypeService = vehicleTypeService;
        this.finalDestinationService = finalDestinationService;
        this.transportBookingSearchControllerManager = transportBookingSearchControllerManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingSearch_VIEW')")
    public String getPage(Model model) {
        this.pageLoad(model);
        return "transport/transportBookingSearch";
    }

    @RequestMapping(value = "/findBookings", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingSearch_VIEW')")
    public String findBookings(@ModelAttribute TransportBookingSearch transportBookingSearch,
                               Model model) {
        model.addAttribute("bookingList", this.transportBookingSearchControllerManager.searchBooking(transportBookingSearch));
        return "transport/content/transportBookingData";
    }

    @RequestMapping(value = "/findCustomer", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingSearch_VIEW')")
    public
    @ResponseBody
    List<Stakeholder> findCustomer(@RequestParam("q") String searchParam) {
        return this.stakeholderService.getStakeholderDetails(searchParam,
                this.stakeholderTypeService.findByStakeholderTypeCodeAndStatus("CUSTOMER", MasterDataStatus.APPROVED.getStatusSeq()).getStakeholderTypeSeq(),
                Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString()),
                MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findFinalDestination", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingSearch_VIEW')")
    public
    @ResponseBody
    List<FinalDestination> findFinalDestination(@RequestParam("q") String searchParam) {
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        return this.finalDestinationService.findByDestinationContainingIgnoreCaseAndStatusAndCompanyProfileSeq(searchParam,
                MasterDataStatus.APPROVED.getStatusSeq(),
                companyProfileSeq);
    }

    @RequestMapping(value = "/findVehicleType", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@transportBookingSearch_VIEW')")
    public
    @ResponseBody
    List<VehicleType> findVehicleType(@RequestParam("q") String searchParam) {
        return this.vehicleTypeService.findByVehicleTypeNameStartsWithIgnoreCaseAndStatus(searchParam, MasterDataStatus.APPROVED.getStatusSeq());
    }

    private void pageLoad(Model model) {
        Integer moduleSeq = this.moduleService.findByModuleCode("TRANSPORT").getModuleSeq();
        model.addAttribute("departmentList", this.httpSession.getAttribute("userDepartmentList" + moduleSeq));
        model.addAttribute("statusList", this.transportBookingStatusService.findAllByStatusOrderByCurrentStatus(MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("moduleSeq", moduleSeq);
        model.addAttribute("companyProfileSeq", this.httpSession.getAttribute("companyProfileSeq"));
        model.addAttribute("endDate", new Date());
        model.addAttribute("startDate", new NDaysBefore().getDateNDaysBeforeCurrentDate(10));
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}

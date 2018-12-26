package com.tmj.tms.fleet.presentationlayer.controller;

import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.fleet.datalayer.modal.auxiliary.VehicleTrackingSearch;
import com.tmj.tms.fleet.integration.auxiliary.VehicleTrackingResponse;
import com.tmj.tms.fleet.utility.VehicleTrackingStatus;
import com.tmj.tms.home.businesslayer.manager.VehicleTrackingControllerManager;
import com.tmj.tms.master.datalayer.modal.Stakeholder;
import com.tmj.tms.master.datalayer.modal.VehicleType;
import com.tmj.tms.master.datalayer.service.StakeholderService;
import com.tmj.tms.master.datalayer.service.StakeholderTypeService;
import com.tmj.tms.master.datalayer.service.VehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("fleet/vehicleTracking")
public class VehicleTrackingController {

    private final StakeholderService stakeholderService;
    private final StakeholderTypeService stakeholderTypeService;
    private final VehicleTrackingControllerManager vehicleTrackingControllerManager;
    private final VehicleTypeService vehicleTypeService;
    private final HttpSession httpSession;

    @Autowired
    public VehicleTrackingController(StakeholderService stakeholderService,
                                     StakeholderTypeService stakeholderTypeService,
                                     VehicleTrackingControllerManager vehicleTrackingControllerManager,
                                     VehicleTypeService vehicleTypeService,
                                     HttpSession httpSession) {
        this.stakeholderService = stakeholderService;
        this.stakeholderTypeService = stakeholderTypeService;
        this.vehicleTrackingControllerManager = vehicleTrackingControllerManager;
        this.vehicleTypeService = vehicleTypeService;
        this.httpSession = httpSession;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@vehicleTracking_VIEW')")
    public String getPage(Model model) {
        this.pageLoad(model);
        return "fleet/vehicleTracking";
    }

    @RequestMapping(value = "/searchVehicle", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_fleet@vehicleTracking_VIEW')")
    public @ResponseBody
    List<VehicleTrackingResponse> searchVehicle(@ModelAttribute VehicleTrackingSearch vehicleTrackingSearch) {
        return this.vehicleTrackingControllerManager.searchVehicle(vehicleTrackingSearch);
    }

    @RequestMapping(value = "/findTransporter", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@vehicleTracking_VIEW')")
    public
    @ResponseBody
    List<Stakeholder> findTransporter(@RequestParam("q") String searchParam, HttpServletRequest httpServletRequest) {
        return this.stakeholderService.getStakeholderDetails(searchParam,
                this.stakeholderTypeService.findByStakeholderTypeCodeAndStatus("TRANSPORT_COMPANY", MasterDataStatus.APPROVED.getStatusSeq()).getStakeholderTypeSeq(),
                Integer.parseInt(httpServletRequest.getSession().getAttribute("companyProfileSeq").toString()),
                MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findCustomer", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@vehicleAssignment_VIEW')")
    public
    @ResponseBody
    List<Stakeholder> findCustomer(@RequestParam("q") String searchParam) {
        return this.stakeholderService.getStakeholderDetails(searchParam,
                this.stakeholderTypeService.findByStakeholderTypeCodeAndStatus("CUSTOMER", MasterDataStatus.APPROVED.getStatusSeq()).getStakeholderTypeSeq(),
                Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString()),
                MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findVehicleType", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@vehicleTracking_VIEW')")
    public
    @ResponseBody
    List<VehicleType> findVehicleType(@RequestParam("q") String searchParam) {
        return this.vehicleTypeService.findByVehicleTypeNameStartsWithIgnoreCaseAndStatus(searchParam, MasterDataStatus.APPROVED.getStatusSeq());
    }

    private void pageLoad(Model model) {
        model.addAttribute("statusList", VehicleTrackingStatus.values());
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}

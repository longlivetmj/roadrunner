package com.tmj.tms.fleet.presentationlayer.controller;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.fleet.bussinesslayer.manager.VehicleManagementControllerManager;
import com.tmj.tms.fleet.datalayer.modal.Vehicle;
import com.tmj.tms.fleet.datalayer.modal.VehicleManufacturer;
import com.tmj.tms.fleet.datalayer.modal.VehicleModal;
import com.tmj.tms.fleet.datalayer.modal.auxiliary.VehicleSearch;
import com.tmj.tms.fleet.datalayer.service.*;
import com.tmj.tms.fleet.utility.FuelType;
import com.tmj.tms.fleet.utility.PaymentMode;
import com.tmj.tms.fleet.utility.VehicleActivation;
import com.tmj.tms.master.datalayer.modal.*;
import com.tmj.tms.master.datalayer.service.*;
import com.tmj.tms.utility.NDaysBefore;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("fleet/vehicleManagement")
public class VehicleManagementController {

    private final VehicleTypeService vehicleTypeService;
    private final StakeholderService stakeholderService;
    private final StakeholderTypeService stakeholderTypeService;
    private final VehicleManufacturerService vehicleManufacturerService;
    private final CountryService countryService;
    private final TyreSizeService tyreSizeService;
    private final EmployeeService employeeService;
    private final EmployeeDesignationService employeeDesignationService;
    private final VehicleModalService vehicleModalService;
    private final GpsServiceProviderService gpsServiceProviderService;
    private final FinalDestinationService finalDestinationService;
    private final VehicleManagementControllerManager vehicleManagementControllerManager;
    private final VehicleService vehicleService;

    @Autowired
    public VehicleManagementController(VehicleTypeService vehicleTypeService,
                                       StakeholderService stakeholderService,
                                       StakeholderTypeService stakeholderTypeService,
                                       VehicleManufacturerService vehicleManufacturerService,
                                       CountryService countryService,
                                       TyreSizeService tyreSizeService,
                                       EmployeeService employeeService,
                                       EmployeeDesignationService employeeDesignationService,
                                       VehicleModalService vehicleModalService,
                                       GpsServiceProviderService gpsServiceProviderService,
                                       FinalDestinationService finalDestinationService,
                                       VehicleManagementControllerManager vehicleManagementControllerManager,
                                       VehicleService vehicleService) {
        this.vehicleTypeService = vehicleTypeService;
        this.stakeholderService = stakeholderService;
        this.stakeholderTypeService = stakeholderTypeService;
        this.vehicleManufacturerService = vehicleManufacturerService;
        this.countryService = countryService;
        this.tyreSizeService = tyreSizeService;
        this.employeeService = employeeService;
        this.employeeDesignationService = employeeDesignationService;
        this.vehicleModalService = vehicleModalService;
        this.gpsServiceProviderService = gpsServiceProviderService;
        this.finalDestinationService = finalDestinationService;
        this.vehicleManagementControllerManager = vehicleManagementControllerManager;
        this.vehicleService = vehicleService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@vehicleManagement_VIEW')")
    public String getPage(Model model, HttpServletRequest httpServletRequest) {
        this.pageLoad(model, httpServletRequest);
        return "fleet/vehicleManagement";
    }

    @RequestMapping(value = "/saveVehicle", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_fleet@vehicleManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject saveVehicle(@ModelAttribute Vehicle vehicle, Principal principal) {
        return this.vehicleManagementControllerManager.saveVehicle(vehicle, principal);
    }

    @RequestMapping(value = "/updateVehicle", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_fleet@vehicleManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateVehicle(@ModelAttribute Vehicle vehicle) {
        return this.vehicleManagementControllerManager.updateVehicle(vehicle);
    }

    @RequestMapping(value = "/searchVehicle", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_fleet@vehicleManagement_VIEW')")
    public String searchVehicle(@ModelAttribute VehicleSearch vehicleSearch,
                                Model model) {
        model.addAttribute("vehicleList", this.vehicleManagementControllerManager.searchVehicle(vehicleSearch));
        return "fleet/content/vehicleData";
    }

    @RequestMapping(value = "/findTransporter", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@vehicleManagement_VIEW')")
    public
    @ResponseBody
    List<Stakeholder> findTransporter(@RequestParam("q") String searchParam, HttpServletRequest httpServletRequest) {
        return this.stakeholderService.getStakeholderDetails(searchParam,
                this.stakeholderTypeService.findByStakeholderTypeCodeAndStatus("TRANSPORT_COMPANY", MasterDataStatus.APPROVED.getStatusSeq()).getStakeholderTypeSeq(),
                Integer.parseInt(httpServletRequest.getSession().getAttribute("companyProfileSeq").toString()),
                MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findDriver", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@vehicleManagement_VIEW')")
    public
    @ResponseBody
    List<Employee> findDriver(@RequestParam("q") String searchParam,
                              @RequestParam("s") Integer stakeholderSeq,
                              HttpServletRequest httpServletRequest) {
        return this.employeeService.findByEmployeeNameContainingIgnoreCaseAndStatusAndEmployeeDesignationSeqAndCompanyProfileSeqAndStakeholderSeq(
                searchParam,
                MasterDataStatus.APPROVED.getStatusSeq(),
                this.employeeDesignationService.findByDesignation("DRIVER").getEmployeeDesignationSeq(),
                Integer.parseInt(httpServletRequest.getSession().getAttribute("companyProfileSeq").toString()),
                stakeholderSeq);
    }

    @RequestMapping(value = "/findHelper", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@vehicleManagement_VIEW')")
    public
    @ResponseBody
    List<Employee> findHelper(@RequestParam("q") String searchParam,
                              @RequestParam("s") Integer stakeholderSeq,
                              HttpServletRequest httpServletRequest) {
        return this.employeeService.findByEmployeeNameContainingIgnoreCaseAndStatusAndEmployeeDesignationSeqAndCompanyProfileSeqAndStakeholderSeq(
                searchParam,
                MasterDataStatus.APPROVED.getStatusSeq(),
                this.employeeDesignationService.findByDesignation("HELPER").getEmployeeDesignationSeq(),
                Integer.parseInt(httpServletRequest.getSession().getAttribute("companyProfileSeq").toString()),
                stakeholderSeq);
    }

    @RequestMapping(value = "/findVehicleManufacturer", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@vehicleManagement_VIEW')")
    public
    @ResponseBody
    List<VehicleManufacturer> findVehicleManufacturer(@RequestParam("q") String searchParam) {
        return this.vehicleManufacturerService.findByManufacturerNameContainingIgnoreCaseAndStatus(searchParam, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findCountry", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@vehicleManagement_VIEW')")
    public
    @ResponseBody
    List<Country> findCountry(@RequestParam("q") String searchParam) {
        return this.countryService.findByCountryNameContainingIgnoreCaseAndStatus(searchParam, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findVehicleModal", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@vehicleManagement_VIEW')")
    public
    @ResponseBody
    List<VehicleModal> findVehicleModal(@RequestParam("q") String searchParam,
                                        @RequestParam("s") Integer vehicleManufacturerSeq) {
        return this.vehicleModalService.findByModalNameContainingIgnoreCaseAndVehicleManufacturerSeqAndStatus(searchParam,
                vehicleManufacturerSeq, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findFinalDestination", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@vehicleManagement_VIEW')")
    public
    @ResponseBody
    List<FinalDestination> findFinalDestination(@RequestParam("q") String searchParam, HttpServletRequest httpServletRequest) {
        return this.finalDestinationService.findByDestinationContainingIgnoreCaseAndStatusAndCompanyProfileSeq(searchParam,
                MasterDataStatus.APPROVED.getStatusSeq(), Integer.parseInt(httpServletRequest.getSession().getAttribute("companyProfileSeq").toString()));
    }

    @RequestMapping(value = "/findByVehicleSeq/{vehicleSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@vehicleManagement_VIEW')")
    public
    @ResponseBody
    Vehicle findByVehicleSeq(@PathVariable("vehicleSeq") Integer vehicleSeq) {
        return this.vehicleService.findOne(vehicleSeq, EntityGraphUtils.fromName("Vehicle.createVehicle"));
    }

    private void pageLoad(Model model, HttpServletRequest httpServletRequest) {
        Integer companyProfileSeq = Integer.parseInt(httpServletRequest.getSession().getAttribute("companyProfileSeq").toString());
        String[] statusList = {"ROLE_fleet@vehicleManagement_APPROVE", "ROLE_fleet@vehicleManagement_DELETE"};
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransaction(Arrays.asList(statusList)));
        model.addAttribute("tyreSizeList", this.tyreSizeService.findByStatus(MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("vehicleTypeList", this.vehicleTypeService.findByStatus(MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("fuelTypeList", FuelType.values());
        model.addAttribute("paymentModeList", PaymentMode.values());
        model.addAttribute("vehicleActivationList", VehicleActivation.values());
        model.addAttribute("gpsServiceProviderList", this.gpsServiceProviderService.findByCompanyProfileSeqAndStatus(companyProfileSeq, MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("fromDate", new NDaysBefore().getDateNDaysBeforeCurrentDate(30));
        model.addAttribute("toDate", new NDaysBefore().getDateNDaysAfterCurrentDate(1));
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}

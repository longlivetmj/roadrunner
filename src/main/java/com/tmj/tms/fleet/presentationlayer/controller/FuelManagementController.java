package com.tmj.tms.fleet.presentationlayer.controller;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.datalayer.service.CompanyProfileService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.fleet.bussinesslayer.manager.FuelManagementControllerManager;
import com.tmj.tms.fleet.datalayer.modal.FuelTypeVariant;
import com.tmj.tms.fleet.datalayer.modal.Vehicle;
import com.tmj.tms.fleet.datalayer.modal.VehicleFuel;
import com.tmj.tms.fleet.datalayer.modal.auxiliary.FuelSearch;
import com.tmj.tms.fleet.datalayer.service.FuelTypeVariantService;
import com.tmj.tms.fleet.datalayer.service.VehicleFuelService;
import com.tmj.tms.fleet.datalayer.service.VehicleService;
import com.tmj.tms.fleet.utility.FuelType;
import com.tmj.tms.master.datalayer.modal.Employee;
import com.tmj.tms.master.datalayer.modal.Stakeholder;
import com.tmj.tms.master.datalayer.service.EmployeeDesignationService;
import com.tmj.tms.master.datalayer.service.EmployeeService;
import com.tmj.tms.master.datalayer.service.StakeholderService;
import com.tmj.tms.master.datalayer.service.StakeholderTypeService;
import com.tmj.tms.master.utility.StakeholderCashType;
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
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("fleet/fuelManagement")
public class FuelManagementController {

    private final StakeholderService stakeholderService;
    private final StakeholderTypeService stakeholderTypeService;
    private final EmployeeService employeeService;
    private final EmployeeDesignationService employeeDesignationService;
    private final FuelManagementControllerManager fuelManagementControllerManager;
    private final HttpSession httpSession;
    private final CompanyProfileService companyProfileService;
    private final VehicleService vehicleService;
    private final FuelTypeVariantService fuelTypeVariantService;
    private final VehicleFuelService vehicleFuelService;

    @Autowired
    public FuelManagementController(StakeholderService stakeholderService,
                                    StakeholderTypeService stakeholderTypeService,
                                    EmployeeService employeeService,
                                    EmployeeDesignationService employeeDesignationService,
                                    FuelManagementControllerManager fuelManagementControllerManager,
                                    HttpSession httpSession,
                                    CompanyProfileService companyProfileService,
                                    VehicleService vehicleService,
                                    FuelTypeVariantService fuelTypeVariantService,
                                    VehicleFuelService vehicleFuelService) {
        this.stakeholderService = stakeholderService;
        this.stakeholderTypeService = stakeholderTypeService;
        this.employeeService = employeeService;
        this.employeeDesignationService = employeeDesignationService;
        this.fuelManagementControllerManager = fuelManagementControllerManager;
        this.httpSession = httpSession;
        this.companyProfileService = companyProfileService;
        this.vehicleService = vehicleService;
        this.fuelTypeVariantService = fuelTypeVariantService;
        this.vehicleFuelService = vehicleFuelService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@fuelManagement_VIEW')")
    public String getPage(Model model) {
        this.pageLoad(model);
        return "fleet/fuelManagement";
    }

    @RequestMapping(value = "/saveFuel", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_fleet@fuelManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject saveVehicleFuel(@ModelAttribute VehicleFuel vehicleFuel) {
        return this.fuelManagementControllerManager.saveVehicleFuel(vehicleFuel);
    }

    @RequestMapping(value = "/updateFuel", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_fleet@fuelManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateVehicleFuel(@ModelAttribute VehicleFuel vehicleFuel) {
        return this.fuelManagementControllerManager.updateVehicleFuel(vehicleFuel);
    }

    @RequestMapping(value = "/searchFuel", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_fleet@fuelManagement_VIEW')")
    public String searchFuelData(@ModelAttribute FuelSearch fuelSearch,
                                 Model model) {
        model.addAttribute("vehicleFuelList", this.fuelManagementControllerManager.searchFuelData(fuelSearch));
        return "fleet/content/fuelData";
    }

    @RequestMapping(value = "/deleteByVehicleFuelSeq", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_fleet@fuelManagement_DELETE')")
    public
    @ResponseBody
    ResponseObject deleteByVehicleFuelSeq(@RequestParam("vehicleFuelSeq") Integer vehicleFuelSeq) {
        return this.fuelManagementControllerManager.delete(vehicleFuelSeq);
    }

    @RequestMapping(value = "/findTransporter", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@fuelManagement_VIEW')")
    public
    @ResponseBody
    List<Stakeholder> findTransporter(@RequestParam("q") String searchParam, HttpServletRequest httpServletRequest) {
        return this.stakeholderService.getStakeholderDetails(searchParam,
                this.stakeholderTypeService.findByStakeholderTypeCodeAndStatus("TRANSPORT_COMPANY", MasterDataStatus.APPROVED.getStatusSeq()).getStakeholderTypeSeq(),
                Integer.parseInt(httpServletRequest.getSession().getAttribute("companyProfileSeq").toString()),
                MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findSupplier", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@fuelManagement_VIEW')")
    public
    @ResponseBody
    List<Stakeholder> findSupplier(@RequestParam("q") String searchParam, HttpServletRequest httpServletRequest) {
        return this.stakeholderService.getStakeholderDetails(searchParam,
                this.stakeholderTypeService.findByStakeholderTypeCodeAndStatus("SUPPLIER", MasterDataStatus.APPROVED.getStatusSeq()).getStakeholderTypeSeq(),
                Integer.parseInt(httpServletRequest.getSession().getAttribute("companyProfileSeq").toString()),
                MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findDriver", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@fuelManagement_VIEW')")
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

    @RequestMapping(value = "/getVehicleListByTransporterSeq/{transporterSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@fuelManagement_VIEW')")
    public
    @ResponseBody
    List<Vehicle> getVehicleListByTransporterSeq(@PathVariable("transporterSeq") Integer transporterSeq) {
        return this.vehicleService.findByStakeholderSeqAndStatus(transporterSeq, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/getDriverListByTransporterSeq/{transporterSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@fuelManagement_VIEW')")
    public
    @ResponseBody
    List<Employee> getDriverListByTransporterSeq(@PathVariable("transporterSeq") Integer transporterSeq) {
        return this.employeeService.findByStatusAndEmployeeDesignationSeqAndCompanyProfileSeqAndStakeholderSeq(
                MasterDataStatus.APPROVED.getStatusSeq(),
                this.employeeDesignationService.findByDesignation("DRIVER").getEmployeeDesignationSeq(),
                Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString()),
                transporterSeq);
    }

    @RequestMapping(value = "/getVehicle/{vehicleSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@fuelManagement_VIEW')")
    public
    @ResponseBody
    Vehicle getVehicle(@PathVariable("vehicleSeq") Integer vehicleSeq) {
        return this.vehicleService.findOne(vehicleSeq);
    }

    @RequestMapping(value = "/getFuelVariantList/{vehicleSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@fuelManagement_VIEW')")
    public
    @ResponseBody
    List<FuelTypeVariant> getFuelVariantList(@PathVariable("vehicleSeq") Integer vehicleSeq) {
        Vehicle vehicle = this.vehicleService.findOne(vehicleSeq);
        return this.fuelTypeVariantService.findByCountrySeqAndFuelTypeAndStatus(vehicle.getStakeholder().getAddressBook().getCountrySeq(), vehicle.getFuelType(), MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findFuelTypeVariant/{fuelTypeVariantSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@fuelManagement_VIEW')")
    public
    @ResponseBody
    FuelTypeVariant findFuelTypeVariant(@PathVariable("fuelTypeVariantSeq") Integer fuelTypeVariantSeq) {
        return this.fuelTypeVariantService.findOne(fuelTypeVariantSeq);
    }

    @RequestMapping(value = "/findByVehicleFuelSeq/{vehicleFuelSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@vehicleManagement_VIEW')")
    public
    @ResponseBody
    VehicleFuel findByVehicleFuelSeq(@PathVariable("vehicleFuelSeq") Integer vehicleFuelSeq) {
        return this.vehicleFuelService.findOne(vehicleFuelSeq, EntityGraphUtils.fromName("VehicleFuel.create"));
    }

    private void pageLoad(Model model) {
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        CompanyProfile companyProfile = this.companyProfileService.findByCompanyProfileSeq(companyProfileSeq);
        VehicleFuel vehicleFuel = new VehicleFuel();
        vehicleFuel.setStakeholder(this.stakeholderService.findOne(companyProfile.getDefaultTransporterSeq()));
        model.addAttribute("vehicleFuel", vehicleFuel);
        model.addAttribute("statusList", MasterDataStatus.values());
        model.addAttribute("fuelTypeList", FuelType.values());
        model.addAttribute("fuelTypeVariantList", this.fuelTypeVariantService.findAll());
        model.addAttribute("stakeholderCashTypeList", StakeholderCashType.values());
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

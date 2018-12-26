package com.tmj.tms.fleet.presentationlayer.controller;


import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.datalayer.service.CompanyProfileService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.fleet.bussinesslayer.manager.ServiceAndMaintenanceControllerManager;
import com.tmj.tms.fleet.datalayer.modal.ServiceAndMaintenance;
import com.tmj.tms.fleet.datalayer.modal.ServiceAndMaintenanceLine;
import com.tmj.tms.fleet.datalayer.modal.Vehicle;
import com.tmj.tms.fleet.datalayer.modal.auxiliary.ServiceAndMaintenanceSearch;
import com.tmj.tms.fleet.datalayer.service.ServiceAndMaintenanceService;
import com.tmj.tms.fleet.datalayer.service.VehicleService;
import com.tmj.tms.fleet.utility.ActionType;
import com.tmj.tms.fleet.utility.DurationType;
import com.tmj.tms.master.datalayer.modal.*;
import com.tmj.tms.master.datalayer.service.*;
import com.tmj.tms.master.utility.StakeholderCashType;
import com.tmj.tms.utility.MultipleDateEditor;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("fleet/serviceAndMaintenance")
public class ServiceAndMaintenanceController {

    private final HttpSession httpSession;
    private final CompanyProfileService companyProfileService;
    private final StakeholderService stakeholderService;
    private final CurrencyService currencyService;
    private final UnitService unitService;
    private final StakeholderTypeService stakeholderTypeService;
    private final EmployeeDesignationService employeeDesignationService;
    private final EmployeeService employeeService;
    private final VehicleService vehicleService;
    private final ItemService itemService;
    private final ServiceAndMaintenanceService serviceAndMaintenanceService;
    private final ServiceAndMaintenanceControllerManager serviceAndMaintenanceControllerManager;

    @Autowired
    public ServiceAndMaintenanceController(HttpSession httpSession,
                                           CompanyProfileService companyProfileService,
                                           StakeholderService stakeholderService,
                                           CurrencyService currencyService,
                                           UnitService unitService,
                                           StakeholderTypeService stakeholderTypeService,
                                           EmployeeDesignationService employeeDesignationService,
                                           EmployeeService employeeService,
                                           VehicleService vehicleService,
                                           ItemService itemService,
                                           ServiceAndMaintenanceService serviceAndMaintenanceService,
                                           ServiceAndMaintenanceControllerManager serviceAndMaintenanceControllerManager) {
        this.httpSession = httpSession;
        this.companyProfileService = companyProfileService;
        this.stakeholderService = stakeholderService;
        this.currencyService = currencyService;
        this.unitService = unitService;
        this.stakeholderTypeService = stakeholderTypeService;
        this.employeeDesignationService = employeeDesignationService;
        this.employeeService = employeeService;
        this.vehicleService = vehicleService;
        this.itemService = itemService;
        this.serviceAndMaintenanceService = serviceAndMaintenanceService;
        this.serviceAndMaintenanceControllerManager = serviceAndMaintenanceControllerManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@serviceAndMaintenance_VIEW')")
    public String getPage(Model model) {
        this.pageLoad(model);
        return "fleet/serviceAndMaintenance";
    }

    @RequestMapping(value = "/saveServiceAndMaintenance", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_fleet@serviceAndMaintenance_CREATE')")
    public
    @ResponseBody
    ResponseObject saveRecord(@ModelAttribute ServiceAndMaintenance serviceAndMaintenance) {
        return this.serviceAndMaintenanceControllerManager.saveRecord(serviceAndMaintenance);
    }

    @RequestMapping(value = "/updateServiceAndMaintenance", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_fleet@serviceAndMaintenance_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateRecord(@ModelAttribute ServiceAndMaintenance serviceAndMaintenance) {
        return this.serviceAndMaintenanceControllerManager.updateRecord(serviceAndMaintenance);
    }

    @RequestMapping(value = "/searchServiceAndMaintenance", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_fleet@serviceAndMaintenance_VIEW')")
    public String searchRecord(@ModelAttribute ServiceAndMaintenanceSearch serviceAndMaintenanceSearch,
                               Model model) {
        model.addAttribute("serviceAndMaintenanceList", this.serviceAndMaintenanceControllerManager.searchRecord(serviceAndMaintenanceSearch));
        return "fleet/content/serviceAndMaintenanceData";
    }

    @RequestMapping(value = "/findTransporter", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@serviceAndMaintenance_VIEW')")
    public
    @ResponseBody
    List<Stakeholder> findTransporter(@RequestParam("q") String searchParam, HttpServletRequest httpServletRequest) {
        return this.stakeholderService.getStakeholderDetails(searchParam,
                this.stakeholderTypeService.findByStakeholderTypeCodeAndStatus("TRANSPORT_COMPANY", MasterDataStatus.APPROVED.getStatusSeq()).getStakeholderTypeSeq(),
                Integer.parseInt(httpServletRequest.getSession().getAttribute("companyProfileSeq").toString()),
                MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findSupplier", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@serviceAndMaintenance_VIEW')")
    public
    @ResponseBody
    List<Stakeholder> findSupplier(@RequestParam("q") String searchParam, HttpServletRequest httpServletRequest) {
        return this.stakeholderService.getStakeholderDetails(searchParam,
                this.stakeholderTypeService.findByStakeholderTypeCodeAndStatus("SUPPLIER", MasterDataStatus.APPROVED.getStatusSeq()).getStakeholderTypeSeq(),
                Integer.parseInt(httpServletRequest.getSession().getAttribute("companyProfileSeq").toString()),
                MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findDriver", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@serviceAndMaintenance_VIEW')")
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
    @PreAuthorize("hasRole('ROLE_fleet@serviceAndMaintenance_VIEW')")
    public
    @ResponseBody
    List<Vehicle> getVehicleListByTransporterSeq(@PathVariable("transporterSeq") Integer transporterSeq) {
        return this.vehicleService.findByStakeholderSeqAndStatus(transporterSeq, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findByServiceAndMaintenanceSeq/{serviceAndMaintenanceSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@serviceAndMaintenance_VIEW')")
    public
    @ResponseBody
    ServiceAndMaintenance findByServiceAndMaintenanceSeq(@PathVariable("serviceAndMaintenanceSeq") Integer serviceAndMaintenanceSeq) {
        return this.serviceAndMaintenanceService.findOne(serviceAndMaintenanceSeq, EntityGraphUtils.fromName("ServiceAndMaintenance.create"));
    }

    @RequestMapping(value = "/getDriverListByTransporterSeq/{transporterSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@serviceAndMaintenance_VIEW')")
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
    @PreAuthorize("hasRole('ROLE_fleet@serviceAndMaintenance_VIEW')")
    public
    @ResponseBody
    Vehicle getVehicle(@PathVariable("vehicleSeq") Integer vehicleSeq) {
        return this.vehicleService.findOne(vehicleSeq);
    }

    @RequestMapping(value = "/findCurrency", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@serviceAndMaintenance_VIEW')")
    public
    @ResponseBody
    List<Currency> findCurrency(@RequestParam("q") String searchParam) {
        return this.currencyService.findByCurrencyCodeStartsWithIgnoreCaseAndStatus(searchParam, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findUnit", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@serviceAndMaintenance_VIEW')")
    @ResponseBody
    public List<Unit> findUnit(@RequestParam("q") String searchParam) {
        return this.unitService.findByUnitNameStartsWithIgnoreCaseAndStatus(searchParam, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findItem", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@serviceAndMaintenance_VIEW')")
    @ResponseBody
    public List<Item> findItem(@RequestParam("q") String searchParam) {
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        return this.itemService.findByItemNameContainingIgnoreCaseAndStatusAndCompanyProfileSeq(searchParam, MasterDataStatus.APPROVED.getStatusSeq(), companyProfileSeq);
    }

    @RequestMapping(value = "/findByItemSeq/{itemSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@serviceAndMaintenance_VIEW')")
    public
    @ResponseBody
    Item findByDeliveryTypeSeq(@PathVariable("itemSeq") Integer itemSeq) {
        return this.itemService.findOne(itemSeq);
    }

    private void pageLoad(Model model) {
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        CompanyProfile companyProfile = this.companyProfileService.findByCompanyProfileSeq(companyProfileSeq);
        ServiceAndMaintenance serviceAndMaintenance = new ServiceAndMaintenance();
        List<ServiceAndMaintenanceLine> serviceAndMaintenanceLineList = new ArrayList<>();
        ServiceAndMaintenanceLine serviceAndMaintenanceLine = new ServiceAndMaintenanceLine();
        serviceAndMaintenanceLine.setCurrency(this.currencyService.findOne(companyProfile.getLocalCurrencySeq()));
        serviceAndMaintenanceLine.setUnit(this.unitService.findByUnitName("UNIT"));
        serviceAndMaintenanceLineList.add(serviceAndMaintenanceLine);
        serviceAndMaintenance.setServiceAndMaintenanceLines(serviceAndMaintenanceLineList);
        serviceAndMaintenance.setStakeholder(this.stakeholderService.findOne(companyProfile.getDefaultTransporterSeq()));
        model.addAttribute("serviceAndMaintenance", serviceAndMaintenance);
        model.addAttribute("stakeholderCashTypeList", StakeholderCashType.values());
        model.addAttribute("durationTypeList", DurationType.values());
        model.addAttribute("actionTypeList", ActionType.values());
        model.addAttribute("fromDate", new NDaysBefore().getDateNDaysBeforeCurrentDate(30));
        model.addAttribute("toDate", new NDaysBefore().getDateNDaysAfterCurrentDate(1));
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransactions("ROLE_fleet@serviceAndMaintenance_APPROVE"));
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new MultipleDateEditor());
    }
}

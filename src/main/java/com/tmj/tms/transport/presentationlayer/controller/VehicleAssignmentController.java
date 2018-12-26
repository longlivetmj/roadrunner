package com.tmj.tms.transport.presentationlayer.controller;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.datalayer.service.CompanyProfileService;
import com.tmj.tms.config.datalayer.service.ModuleService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.fleet.datalayer.modal.Vehicle;
import com.tmj.tms.fleet.datalayer.service.VehicleService;
import com.tmj.tms.home.datalayer.model.TempEmployeeStatistics;
import com.tmj.tms.home.datalayer.service.TempEmployeeStatisticsService;
import com.tmj.tms.master.datalayer.modal.Employee;
import com.tmj.tms.master.datalayer.modal.FinalDestination;
import com.tmj.tms.master.datalayer.modal.Stakeholder;
import com.tmj.tms.master.datalayer.modal.VehicleType;
import com.tmj.tms.master.datalayer.service.*;
import com.tmj.tms.transport.bussinesslayer.manager.VehicleAssignmentControllerManager;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.transport.datalayer.modal.TransportBookingVehicle;
import com.tmj.tms.transport.datalayer.modal.auxiliary.VehicleAssignmentSearch;
import com.tmj.tms.transport.datalayer.service.TransportBookingService;
import com.tmj.tms.transport.datalayer.service.TransportBookingStatusService;
import com.tmj.tms.transport.datalayer.service.TransportBookingVehicleService;
import com.tmj.tms.transport.utility.DateFilterType;
import com.tmj.tms.transport.utility.ViaLocationFormatter;
import com.tmj.tms.utility.NDaysBefore;
import com.tmj.tms.utility.ResponseObject;
import com.tmj.tms.utility.YesOrNo;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/transport/vehicleAssignment")
public class VehicleAssignmentController {

    private final ModuleService moduleService;
    private final HttpSession httpSession;
    private final TransportBookingStatusService transportBookingStatusService;
    private final FinalDestinationService finalDestinationService;
    private final StakeholderService stakeholderService;
    private final StakeholderTypeService stakeholderTypeService;
    private final VehicleTypeService vehicleTypeService;
    private final VehicleAssignmentControllerManager vehicleAssignmentControllerManager;
    private final TransportBookingService transportBookingService;
    private final ViaLocationFormatter viaLocationFormatter;
    private final CompanyProfileService companyProfileService;
    private final VehicleService vehicleService;
    private final EmployeeService employeeService;
    private final EmployeeDesignationService employeeDesignationService;
    private final TransportBookingVehicleService transportBookingVehicleService;
    private final TempEmployeeStatisticsService tempEmployeeStatisticsService;

    @Autowired
    public VehicleAssignmentController(ModuleService moduleService,
                                       HttpSession httpSession,
                                       TransportBookingStatusService transportBookingStatusService,
                                       FinalDestinationService finalDestinationService,
                                       StakeholderService stakeholderService,
                                       StakeholderTypeService stakeholderTypeService,
                                       VehicleTypeService vehicleTypeService,
                                       VehicleAssignmentControllerManager vehicleAssignmentControllerManager,
                                       TransportBookingService transportBookingService,
                                       ViaLocationFormatter viaLocationFormatter,
                                       CompanyProfileService companyProfileService,
                                       VehicleService vehicleService,
                                       EmployeeService employeeService,
                                       EmployeeDesignationService employeeDesignationService,
                                       TransportBookingVehicleService transportBookingVehicleService,
                                       TempEmployeeStatisticsService tempEmployeeStatisticsService) {
        this.moduleService = moduleService;
        this.httpSession = httpSession;
        this.transportBookingStatusService = transportBookingStatusService;
        this.finalDestinationService = finalDestinationService;
        this.stakeholderService = stakeholderService;
        this.stakeholderTypeService = stakeholderTypeService;
        this.vehicleTypeService = vehicleTypeService;
        this.vehicleAssignmentControllerManager = vehicleAssignmentControllerManager;
        this.transportBookingService = transportBookingService;
        this.viaLocationFormatter = viaLocationFormatter;
        this.companyProfileService = companyProfileService;
        this.vehicleService = vehicleService;
        this.employeeService = employeeService;
        this.employeeDesignationService = employeeDesignationService;
        this.transportBookingVehicleService = transportBookingVehicleService;
        this.tempEmployeeStatisticsService = tempEmployeeStatisticsService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@vehicleAssignment_VIEW')")
    public String getPage(Model model) {
        this.pageLoad(model);
        return "transport/vehicleAssignment";
    }

    @RequestMapping(value = "/findBookings", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_transport@vehicleAssignment_VIEW')")
    public String findBookings(@ModelAttribute VehicleAssignmentSearch vehicleAssignmentSearch,
                               Model model) {
        model.addAttribute("bookingList", this.vehicleAssignmentControllerManager.searchBooking(vehicleAssignmentSearch));
        return "transport/content/vehicleAssignmentData";
    }

    @RequestMapping(value = "/loadAssignmentPanel", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_transport@vehicleAssignment_VIEW')")
    public String loadAssignmentPanel(@RequestParam(value = "bookingSeq") Integer bookingSeq,
                                      Model model) {
        this.pageLoad(model);
        TransportBooking transportBooking = this.transportBookingService.findOne(bookingSeq, EntityGraphUtils.fromName("TransportBooking.vehicleAssignment"));
        transportBooking.setViaLocationString(this.viaLocationFormatter.getViaLocationAsString(transportBooking.getTransportBookingViaLocationList()));
        CompanyProfile companyProfile = this.companyProfileService.findByCompanyProfileSeq(transportBooking.getCompanyProfileSeq());
        model.addAttribute("booking", transportBooking);
        model.addAttribute("defaultTransporter", this.stakeholderService.findOne(companyProfile.getDefaultTransporterSeq(), EntityGraphUtils.fromName("Stakeholder.filtering")));
        return "transport/content/assignVehicleData";
    }

    @RequestMapping(value = "/assignVehicle", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_transport@vehicleAssignment_CREATE')")
    public
    @ResponseBody
    ResponseObject assignVehicle(@ModelAttribute TransportBookingVehicle transportBookingVehicle) {
        return this.vehicleAssignmentControllerManager.assignVehicle(transportBookingVehicle);
    }

    @RequestMapping(value = "/removeVehicle", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_transport@vehicleAssignment_DELETE')")
    public
    @ResponseBody
    ResponseObject removeVehicle(@ModelAttribute TransportBookingVehicle transportBookingVehicle) {
        return this.vehicleAssignmentControllerManager.removeVehicle(transportBookingVehicle);
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

    @RequestMapping(value = "/findFinalDestination", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@vehicleAssignment_VIEW')")
    public
    @ResponseBody
    List<FinalDestination> findFinalDestination(@RequestParam("q") String searchParam) {
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        return this.finalDestinationService.findByDestinationContainingIgnoreCaseAndStatusAndCompanyProfileSeq(searchParam,
                MasterDataStatus.APPROVED.getStatusSeq(),
                companyProfileSeq);
    }

    @RequestMapping(value = "/findVehicleType", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@vehicleAssignment_VIEW')")
    public
    @ResponseBody
    List<VehicleType> findVehicleType(@RequestParam("q") String searchParam) {
        return this.vehicleTypeService.findByVehicleTypeNameStartsWithIgnoreCaseAndStatus(searchParam, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findTransporter", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@vehicleAssignment_VIEW')")
    public
    @ResponseBody
    List<Stakeholder> findTransporter(@RequestParam("q") String searchParam, HttpServletRequest httpServletRequest) {
        return this.stakeholderService.getStakeholderDetails(searchParam,
                this.stakeholderTypeService.findByStakeholderTypeCodeAndStatus("TRANSPORT_COMPANY", MasterDataStatus.APPROVED.getStatusSeq()).getStakeholderTypeSeq(),
                Integer.parseInt(httpServletRequest.getSession().getAttribute("companyProfileSeq").toString()),
                MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/getVehicleListByTransporterSeq/{transporterSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@vehicleAssignment_VIEW')")
    public
    @ResponseBody
    List<Vehicle> getVehicleListByTransporterSeq(@PathVariable("transporterSeq") Integer transporterSeq) {
        return this.vehicleService.findByStakeholderSeqAndStatus(transporterSeq, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/getDriverListByTransporterSeq/{transporterSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@vehicleAssignment_VIEW')")
    public
    @ResponseBody
    List<Employee> getDriverListByTransporterSeq(@PathVariable("transporterSeq") Integer transporterSeq) {
        return this.employeeService.findByStatusAndEmployeeDesignationSeqAndCompanyProfileSeqAndStakeholderSeq(
                MasterDataStatus.APPROVED.getStatusSeq(),
                this.employeeDesignationService.findByDesignation("DRIVER").getEmployeeDesignationSeq(),
                Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString()),
                transporterSeq);
    }

    @RequestMapping(value = "/getHelperListByTransporterSeq/{transporterSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@vehicleAssignment_VIEW')")
    public
    @ResponseBody
    List<Employee> getHelperListByTransporterSeq(@PathVariable("transporterSeq") Integer transporterSeq) {
        return this.employeeService.findByStatusAndEmployeeDesignationSeqAndCompanyProfileSeqAndStakeholderSeq(
                MasterDataStatus.APPROVED.getStatusSeq(),
                this.employeeDesignationService.findByDesignation("HELPER").getEmployeeDesignationSeq(),
                Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString()),
                transporterSeq);
    }

    @RequestMapping(value = "/findHelper/{transportBookingSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@vehicleAssignment_VIEW')")
    public
    @ResponseBody
    List<Employee> findHelper(@PathVariable("transportBookingSeq") Integer transportBookingSeq,
                              @RequestParam("q") String searchParam) {
        return this.vehicleAssignmentControllerManager.findHelper(transportBookingSeq, searchParam, Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString()));
    }

    @RequestMapping(value = "/findDriver/{transportBookingSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@vehicleAssignment_VIEW')")
    public
    @ResponseBody
    List<Employee> findDriver(@PathVariable("transportBookingSeq") Integer transportBookingSeq,
                              @RequestParam("q") String searchParam) {
        return this.vehicleAssignmentControllerManager.findDriver(transportBookingSeq, searchParam, Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString()));
    }

    @RequestMapping(value = "/changeDriver", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_transport@vehicleAssignment_UPDATE')")
    public
    @ResponseBody
    ResponseObject changeDriver(@RequestParam(value = "id") Integer transportBookingSeq,
                                @RequestParam(value = "value") Integer employeeSeq) {
        return this.vehicleAssignmentControllerManager.changeDriver(transportBookingSeq, employeeSeq);
    }

    @RequestMapping(value = "/changeHelper", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_transport@vehicleAssignment_UPDATE')")
    public
    @ResponseBody
    ResponseObject changeHelper(@RequestParam(value = "id") Integer transportBookingSeq,
                                @RequestParam(value = "value") Integer employeeSeq) {
        return this.vehicleAssignmentControllerManager.changeHelper(transportBookingSeq, employeeSeq);
    }

    @RequestMapping(value = "/findByTransportBookingVehicleSeq/{transportBookingVehicleSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@vehicleAssignment_VIEW')")
    public
    @ResponseBody
    TransportBookingVehicle findByTransportBookingVehicleSeq(@PathVariable("transportBookingVehicleSeq") Integer transportBookingVehicleSeq) {
        return this.transportBookingVehicleService.findOne(transportBookingVehicleSeq, EntityGraphUtils.fromName("TransportBookingVehicle.assignVehicle"));
    }

    private void pageLoad(Model model) {
        Integer moduleSeq = this.moduleService.findByModuleCode("TRANSPORT").getModuleSeq();
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        Calendar calendar = Calendar.getInstance();
        Integer year = calendar.get(Calendar.YEAR);
        Integer month = calendar.get(Calendar.MONTH);

        List<TempEmployeeStatistics> driverList = this.tempEmployeeStatisticsService.findByCompanyProfileSeqAndEmployeeDesignationSeqAndYearAndMonthOrderByScore(companyProfileSeq,
                this.employeeDesignationService.findByDesignation("DRIVER").getEmployeeDesignationSeq(), year, month);
        List<TempEmployeeStatistics> helperList = this.tempEmployeeStatisticsService.findByCompanyProfileSeqAndEmployeeDesignationSeqAndYearAndMonthOrderByScore(companyProfileSeq,
                this.employeeDesignationService.findByDesignation("HELPER").getEmployeeDesignationSeq(), year, month);
        model.addAttribute("departmentList", this.httpSession.getAttribute("userDepartmentList" + moduleSeq));
        model.addAttribute("statusList", this.transportBookingStatusService.findAllByStatusAndVehicleAssignOrderByCurrentStatus(MasterDataStatus.APPROVED.getStatusSeq(), YesOrNo.Yes.getYesOrNoSeq()));
        model.addAttribute("moduleSeq", moduleSeq);
        model.addAttribute("driverList", driverList);
        model.addAttribute("helperList", helperList);
        model.addAttribute("companyProfileSeq", companyProfileSeq);
        model.addAttribute("endDate", new Date());
        model.addAttribute("startDate", new NDaysBefore().getDateNDaysBeforeCurrentDate(10));
        model.addAttribute("dateFilterTypeList", DateFilterType.values());
        model.addAttribute("defaultFilterType", DateFilterType.ARRIVING_DATE.getDateFilterType());
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}

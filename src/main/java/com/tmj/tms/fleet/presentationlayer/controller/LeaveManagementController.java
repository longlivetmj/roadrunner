package com.tmj.tms.fleet.presentationlayer.controller;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.datalayer.service.CompanyProfileService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.fleet.bussinesslayer.manager.LeaveManagementControllerManager;
import com.tmj.tms.fleet.datalayer.modal.Leave;
import com.tmj.tms.fleet.datalayer.modal.auxiliary.LeaveSearch;
import com.tmj.tms.fleet.datalayer.service.LeaveService;
import com.tmj.tms.fleet.datalayer.service.LeaveTypeService;
import com.tmj.tms.master.datalayer.modal.Employee;
import com.tmj.tms.master.datalayer.service.EmployeeDesignationService;
import com.tmj.tms.master.datalayer.service.EmployeeService;
import com.tmj.tms.utility.DateFormatter;
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
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("fleet/leaveManagement")
public class LeaveManagementController {

    private final HttpSession httpSession;
    private final EmployeeDesignationService employeeDesignationService;
    private final EmployeeService employeeService;
    private final CompanyProfileService companyProfileService;
    private final LeaveTypeService leaveTypeService;
    private final LeaveService leaveService;
    private final LeaveManagementControllerManager leaveManagementControllerManager;

    @Autowired
    public LeaveManagementController(HttpSession httpSession,
                                     EmployeeDesignationService employeeDesignationService,
                                     EmployeeService employeeService,
                                     CompanyProfileService companyProfileService,
                                     LeaveTypeService leaveTypeService,
                                     LeaveService leaveService,
                                     LeaveManagementControllerManager leaveManagementControllerManager) {
        this.httpSession = httpSession;
        this.employeeDesignationService = employeeDesignationService;
        this.employeeService = employeeService;
        this.companyProfileService = companyProfileService;
        this.leaveTypeService = leaveTypeService;
        this.leaveService = leaveService;
        this.leaveManagementControllerManager = leaveManagementControllerManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@leaveManagement_VIEW')")
    public String getPage(Model model) {
        this.pageLoad(model);
        return "fleet/leaveManagement";
    }

    @RequestMapping(value = "/saveLeave", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_fleet@leaveManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject saveLeave(@Valid @ModelAttribute Leave leave) {
        return this.leaveManagementControllerManager.saveLeave(leave);
    }

    @RequestMapping(value = "/updateLeave", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_fleet@leaveManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateLeave(@Valid @ModelAttribute Leave leave) {
        return this.leaveManagementControllerManager.updateLeave(leave);
    }

    @RequestMapping(value = "/deleteByLeaveSeq", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_fleet@leaveManagement_DELETE')")
    public
    @ResponseBody
    ResponseObject deleteLeave(@RequestParam("leaveSeq") Integer leaveSeq) {
        return this.leaveManagementControllerManager.deleteLeave(leaveSeq);
    }

    @RequestMapping(value = "/searchLeaveData", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_fleet@leaveManagement_VIEW')")
    public String searchEmployeeData(@ModelAttribute LeaveSearch leaveSearch,
                                     Model model) {
        model.addAttribute("leaveList", this.leaveManagementControllerManager.searchLeaves(leaveSearch));
        return "fleet/content/leaveData";
    }

    @RequestMapping(value = "/findByLeaveSeq/{leaveSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@leaveManagement_VIEW')")
    public
    @ResponseBody
    Leave findByLeaveSeq(@PathVariable("leaveSeq") Integer leaveSeq) {
        return this.leaveService.findOne(leaveSeq, EntityGraphUtils.fromName("leave.createLeave"));
    }

    @RequestMapping(value = "/findByDesignationSeq/{designationSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@leaveManagement_VIEW')")
    public
    @ResponseBody
    List<Employee> getDriverListByTransporterSeq(@PathVariable("designationSeq") Integer designationSeq) {
        Integer companyProfileSeq = Integer.parseInt(httpSession.getAttribute("companyProfileSeq").toString());
        CompanyProfile companyProfile = this.companyProfileService.findByCompanyProfileSeq(companyProfileSeq);
        return this.employeeService.findByStatusAndEmployeeDesignationSeqAndCompanyProfileSeqAndStakeholderSeq(
                MasterDataStatus.APPROVED.getStatusSeq(),
                designationSeq,
                Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString()),
                companyProfile.getDefaultTransporterSeq());
    }

    private void pageLoad(Model model) {
        Integer companyProfileSeq = Integer.parseInt(httpSession.getAttribute("companyProfileSeq").toString());
        model.addAttribute("designationList", this.employeeDesignationService.findAll());
        model.addAttribute("leaveTypeList", this.leaveTypeService.findByStatus(MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("monthList", Month.values());
        model.addAttribute("currentYear", Calendar.getInstance().get(Calendar.YEAR));
        model.addAttribute("currentMonth", Calendar.getInstance().get(Calendar.MONTH));
        model.addAttribute("startDate", DateFormatter.getStartOfLastMonth());
        model.addAttribute("endDate", DateFormatter.getEndOfLastMonth());
        model.addAttribute("companyProfileSeq", companyProfileSeq);
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransactions("ROLE_fleet@leaveManagement_VIEW"));
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}

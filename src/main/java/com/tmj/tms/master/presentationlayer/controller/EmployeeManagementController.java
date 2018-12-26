package com.tmj.tms.master.presentationlayer.controller;

import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.businesslayer.manager.EmployeeManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.Country;
import com.tmj.tms.master.datalayer.modal.Employee;
import com.tmj.tms.master.datalayer.modal.Stakeholder;
import com.tmj.tms.master.datalayer.modal.auxiliary.EmployeeSearch;
import com.tmj.tms.master.datalayer.service.*;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/master/employeeManagement")
public class EmployeeManagementController {

    private final CountryService countryService;
    private final EmployeeDesignationService employeeDesignationService;
    private final EmployeeService employeeService;
    private final EmployeeManagementControllerManager employeeManagementControllerManager;
    private final StakeholderService stakeholderService;
    private final StakeholderTypeService stakeholderTypeService;

    @Autowired
    public EmployeeManagementController(CountryService countryService,
                                        EmployeeDesignationService employeeDesignationService,
                                        EmployeeService employeeService,
                                        EmployeeManagementControllerManager employeeManagementControllerManager,
                                        StakeholderService stakeholderService,
                                        StakeholderTypeService stakeholderTypeService) {
        this.countryService = countryService;
        this.employeeDesignationService = employeeDesignationService;
        this.employeeService = employeeService;
        this.employeeManagementControllerManager = employeeManagementControllerManager;
        this.stakeholderService = stakeholderService;
        this.stakeholderTypeService = stakeholderTypeService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@employeeManagement_VIEW')")
    public String getPage(Model model) {
        this.pageLoad(model);
        return "master/employeeManagement";
    }

    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@employeeManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject addEmployee(@Valid @ModelAttribute Employee employee) {
        return this.employeeManagementControllerManager.saveEmployee(employee);
    }

    @RequestMapping(value = "/updateEmployee", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@employeeManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateEmployee(@Valid @ModelAttribute Employee employee) {
        return this.employeeManagementControllerManager.updateEmployee(employee);
    }

    @RequestMapping(value = "/findByEmployeeSeq/{employeeSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@employeeManagement_VIEW')")
    public
    @ResponseBody
    Employee findByEmployeeSeq(@PathVariable("employeeSeq") Integer employeeSeq) {
        return this.employeeService.findOne(employeeSeq);
    }

    @RequestMapping(value = "/searchEmployeeData", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@employeeManagement_VIEW')")
    public String searchEmployeeData(@ModelAttribute EmployeeSearch employeeSearch,
                                     Model model) {
        List<Employee> employees = this.employeeManagementControllerManager.searchEmployee(employeeSearch);
        model.addAttribute("employeeListDB", employees);
        return "master/content/employeeData";
    }

    @RequestMapping(value = "/deleteByEmployeeSeq", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@employeeManagement_DELETE')")
    public
    @ResponseBody
    ResponseObject deleteEmployeeByEmployeeSeq(@RequestParam("employeeSeq") Integer employeeSeq) {
        return this.employeeManagementControllerManager.deleteEmployee(employeeSeq);
    }

    @RequestMapping(value = "/findCountry", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@employeeManagement_VIEW')")
    public
    @ResponseBody
    List<Country> findCountry(@RequestParam("q") String searchParam) {
        return this.countryService.findByCountryNameStartsWithIgnoreCaseAndStatus(searchParam, MasterDataStatus.APPROVED.getStatusSeq());
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

    public Model pageLoad(Model model) {
        model.addAttribute("designationList", this.employeeDesignationService.findAll());
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransactions("ROLE_master@employeeManagement_APPROVE"));
        return model;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}

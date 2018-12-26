package com.tmj.tms.fleet.presentationlayer.controller;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.datalayer.service.CompanyProfileService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.fleet.bussinesslayer.manager.PayrollConfigControllerManager;
import com.tmj.tms.fleet.datalayer.modal.Payroll;
import com.tmj.tms.fleet.datalayer.service.PayrollChargeConfigService;
import com.tmj.tms.fleet.datalayer.service.PayrollService;
import com.tmj.tms.fleet.utility.PayrollChargeType;
import com.tmj.tms.master.datalayer.modal.Employee;
import com.tmj.tms.master.datalayer.service.EmployeeDesignationService;
import com.tmj.tms.master.datalayer.service.EmployeeService;
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
@RequestMapping("fleet/payrollConfig")
public class PayrollConfigController {

    private final HttpSession httpSession;
    private final CompanyProfileService companyProfileService;
    private final EmployeeService employeeService;
    private final EmployeeDesignationService employeeDesignationService;
    private final PayrollChargeConfigService payrollChargeConfigService;
    private final PayrollConfigControllerManager payrollConfigControllerManager;
    private final PayrollService payrollService;

    @Autowired
    public PayrollConfigController(HttpSession httpSession,
                                   CompanyProfileService companyProfileService,
                                   EmployeeService employeeService,
                                   EmployeeDesignationService employeeDesignationService,
                                   PayrollChargeConfigService payrollChargeConfigService,
                                   PayrollConfigControllerManager payrollConfigControllerManager,
                                   PayrollService payrollService) {
        this.httpSession = httpSession;
        this.companyProfileService = companyProfileService;
        this.employeeService = employeeService;
        this.employeeDesignationService = employeeDesignationService;
        this.payrollChargeConfigService = payrollChargeConfigService;
        this.payrollConfigControllerManager = payrollConfigControllerManager;
        this.payrollService = payrollService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@payrollConfig_VIEW')")
    public String getPage(Model model) {
        this.pageLoad(model);
        return "fleet/payrollConfig";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_fleet@payrollConfig_CREATE')")
    public
    @ResponseBody
    ResponseObject save(@Valid @ModelAttribute Payroll payroll) {
        return this.payrollConfigControllerManager.save(payroll);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_fleet@payrollConfig_UPDATE')")
    public
    @ResponseBody
    ResponseObject update(@Valid @ModelAttribute Payroll payroll) {
        return this.payrollConfigControllerManager.update(payroll);
    }

    @RequestMapping(value = "/findByDesignationSeq/{designationSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@payrollConfig_VIEW')")
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

    @RequestMapping(value = "/searchPayrollData", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_fleet@payrollConfig_VIEW')")
    public String searchPayrollData(@RequestParam(value = "employeeName", required = false) String employeeName,
                                    @RequestParam(value = "status", required = false) Integer status,
                                    @RequestParam(value = "employeeDesignationSeq", required = false) Integer employeeDesignationSeq,
                                    Model model) {
        List<Payroll> payrollList = this.payrollConfigControllerManager.search(employeeName, status, employeeDesignationSeq);
        model.addAttribute("payrollList", payrollList);
        return "fleet/content/payrollData";
    }

    @RequestMapping(value = "/findByPayrollSeq/{payrollSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@payrollConfig_VIEW')")
    public
    @ResponseBody
    Payroll findByEmployeeSeq(@PathVariable("payrollSeq") Integer payrollSeq) {
        return this.payrollService.findOne(payrollSeq, EntityGraphUtils.fromName("Payroll.create"));
    }

    private void pageLoad(Model model) {
        Integer companyProfileSeq = Integer.parseInt(httpSession.getAttribute("companyProfileSeq").toString());
        model.addAttribute("designationList", this.employeeDesignationService.findAll());
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransactions("ROLE_fleet@payrollConfig_VIEW"));
        model.addAttribute("allowanceList", this.payrollChargeConfigService.findByCompanyProfileSeqAndPayrollChargeTypeAndStatus(companyProfileSeq, PayrollChargeType.ALLOWANCE.getPayrollChargeType(), MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("deductionList", this.payrollChargeConfigService.findByCompanyProfileSeqAndPayrollChargeTypeAndStatus(companyProfileSeq, PayrollChargeType.DEDUCTION.getPayrollChargeType(), MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("commissionList", this.payrollChargeConfigService.findByCompanyProfileSeqAndPayrollChargeTypeAndStatus(companyProfileSeq, PayrollChargeType.COMMISSION.getPayrollChargeType(), MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("companyContributionList", this.payrollChargeConfigService.findByCompanyProfileSeqAndPayrollChargeTypeAndStatus(companyProfileSeq, PayrollChargeType.COMPANY_CONTRIBUTION.getPayrollChargeType(), MasterDataStatus.APPROVED.getStatusSeq()));
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}

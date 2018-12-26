package com.tmj.tms.fleet.presentationlayer.controller;

import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.datalayer.modal.DocumentLink;
import com.tmj.tms.config.datalayer.modal.Module;
import com.tmj.tms.config.datalayer.service.*;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.fleet.bussinesslayer.manager.SalaryAdvanceControllerManager;
import com.tmj.tms.fleet.datalayer.modal.SalaryAdvance;
import com.tmj.tms.fleet.datalayer.modal.auxiliary.SalarySearch;
import com.tmj.tms.fleet.datalayer.service.PayrollChargeConfigService;
import com.tmj.tms.fleet.datalayer.service.SalaryAdvanceService;
import com.tmj.tms.fleet.utility.PayrollChargeType;
import com.tmj.tms.master.datalayer.modal.Employee;
import com.tmj.tms.master.datalayer.service.EmployeeDesignationService;
import com.tmj.tms.master.datalayer.service.EmployeeService;
import com.tmj.tms.utility.DateFormatter;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("fleet/salaryAdvance")
public class SalaryAdvanceController {

    private final HttpSession httpSession;
    private final CompanyProfileService companyProfileService;
    private final EmployeeService employeeService;
    private final EmployeeDesignationService employeeDesignationService;
    private final PayrollChargeConfigService payrollChargeConfigService;
    private final SalaryAdvanceControllerManager salaryAdvanceControllerManager;
    private final SalaryAdvanceService salaryAdvanceService;
    private final ReportService reportService;
    private final DocumentLinkService documentLinkService;
    private final ModuleService moduleService;
    private final ReportUploadService reportUploadService;

    @Autowired
    public SalaryAdvanceController(HttpSession httpSession,
                                   CompanyProfileService companyProfileService,
                                   EmployeeService employeeService,
                                   EmployeeDesignationService employeeDesignationService,
                                   PayrollChargeConfigService payrollChargeConfigService,
                                   SalaryAdvanceControllerManager salaryAdvanceControllerManager,
                                   SalaryAdvanceService salaryAdvanceService,
                                   ReportService reportService,
                                   DocumentLinkService documentLinkService,
                                   ModuleService moduleService,
                                   ReportUploadService reportUploadService) {
        this.httpSession = httpSession;
        this.companyProfileService = companyProfileService;
        this.employeeService = employeeService;
        this.employeeDesignationService = employeeDesignationService;
        this.payrollChargeConfigService = payrollChargeConfigService;
        this.salaryAdvanceControllerManager = salaryAdvanceControllerManager;
        this.salaryAdvanceService = salaryAdvanceService;
        this.reportService = reportService;
        this.documentLinkService = documentLinkService;
        this.moduleService = moduleService;
        this.reportUploadService = reportUploadService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@salaryAdvance_VIEW')")
    public String getPage(Model model) {
        this.pageLoad(model);
        return "fleet/salaryAdvance";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_fleet@salaryAdvance_CREATE')")
    public
    @ResponseBody
    ResponseObject save(@Valid @ModelAttribute SalarySearch salarySearch) {
        return this.salaryAdvanceControllerManager.save(salarySearch);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_fleet@salaryAdvance_DELETE')")
    public
    @ResponseBody
    ResponseObject delete(@RequestParam(value = "salaryAdvanceSeq") Integer salaryAdvanceSeq) {
        return this.salaryAdvanceControllerManager.delete(salaryAdvanceSeq);
    }

    @RequestMapping(value = "/findByDesignationSeq/{designationSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@salaryAdvance_VIEW')")
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

    @RequestMapping(value = "/searchSalary", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_fleet@salaryAdvance_VIEW')")
    public String searchSalaryData(@ModelAttribute SalarySearch salarySearch,
                                   Model model) {
        model.addAttribute("salary", this.salaryAdvanceControllerManager.findSalary(salarySearch));
        return "fleet/content/salaryAdvanceDetail";
    }

    @RequestMapping(value = "/searchSalaryAdvance", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_fleet@salaryAdvance_VIEW')")
    public String searchSalaryAdvance(@ModelAttribute SalarySearch salarySearch,
                                      Model model) {
        model.addAttribute("salaryAdvanceList", this.salaryAdvanceControllerManager.search(salarySearch));
        return "fleet/content/salaryAdvanceData";
    }

    @RequestMapping(value = "/findBySalaryAdvanceSeq/{salaryAdvanceSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@salaryAdvance_VIEW')")
    public
    @ResponseBody
    SalaryAdvance findSalaryAdvance(@PathVariable("salaryAdvanceSeq") Integer salaryAdvanceSeq) {
        SalaryAdvance salaryAdvance = this.salaryAdvanceService.findOne(salaryAdvanceSeq);
        salaryAdvance.setYearMonth(salaryAdvance.getSalaryYear() + "/" + DateFormatter.getMonth(salaryAdvance.getSalaryMonth()));
        return salaryAdvance;
    }

    @RequestMapping(value = "/generateSalaryAdvanceReport", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@salaryAdvance_VIEW')")
    public
    @ResponseBody
    Integer processReport(@RequestParam("salaryAdvanceSeq") Integer salaryAdvanceSeq,
                          @RequestParam("reportSeq") Integer reportSeq,
                          @RequestParam(value = "pdf", required = false) Integer pdf,
                          @RequestParam(value = "rtf", required = false) Integer rtf,
                          @RequestParam(value = "xls", required = false) Integer xls,
                          HttpServletResponse httpServletResponse,
                          HttpServletRequest httpServletRequest,
                          Principal principal) {
        return this.salaryAdvanceControllerManager.getSalaryAdvanceReport(salaryAdvanceSeq, reportSeq, pdf, rtf, xls, httpServletRequest, httpServletResponse, principal);
    }

    @RequestMapping(value = "/getFile/{reportUploadSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@salaryAdvance_VIEW')")
    public ResponseEntity<byte[]> getLogo(@PathVariable Integer reportUploadSeq) throws IOException {
        return this.reportUploadService.findAndDownload(reportUploadSeq);
    }

    private void pageLoad(Model model) {
        Integer companyProfileSeq = Integer.parseInt(httpSession.getAttribute("companyProfileSeq").toString());
        Module module = this.moduleService.findByModuleCode("FLEET");
        DocumentLink documentLink = this.documentLinkService.findByLinkName("salaryAdvance");
        model.addAttribute("designationList", this.employeeDesignationService.findAll());
        model.addAttribute("monthList", Month.values());
        model.addAttribute("currentYear", Calendar.getInstance().get(Calendar.YEAR));
        model.addAttribute("currentMonth", Calendar.getInstance().get(Calendar.MONTH));
        model.addAttribute("startDate", DateFormatter.getStartOfLastMonth());
        model.addAttribute("endDate", DateFormatter.getEndOfLastMonth());
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransactions("ROLE_fleet@salaryAdvance_VIEW"));
        model.addAttribute("allowanceList", this.payrollChargeConfigService.findByCompanyProfileSeqAndPayrollChargeTypeAndStatus(companyProfileSeq, PayrollChargeType.ALLOWANCE.getPayrollChargeType(), MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("deductionList", this.payrollChargeConfigService.findByCompanyProfileSeqAndPayrollChargeTypeAndStatus(companyProfileSeq, PayrollChargeType.DEDUCTION.getPayrollChargeType(), MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("commissionList", this.payrollChargeConfigService.findByCompanyProfileSeqAndPayrollChargeTypeAndStatus(companyProfileSeq, PayrollChargeType.COMMISSION.getPayrollChargeType(), MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("reportList", this.reportService.findByDocumentLinkSeqAndModuleSeqAndCompanyProfileSeqAndStatusOrderByReportName(documentLink.getDocumentLinkSeq(), module.getModuleSeq(), companyProfileSeq, MasterDataStatus.APPROVED.getStatusSeq()));
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}

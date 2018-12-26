package com.tmj.tms.fleet.presentationlayer.controller;

import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.datalayer.modal.DocumentLink;
import com.tmj.tms.config.datalayer.modal.Module;
import com.tmj.tms.config.datalayer.service.*;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.fleet.bussinesslayer.manager.EmployeeSalaryControllerManager;
import com.tmj.tms.fleet.datalayer.modal.Salary;
import com.tmj.tms.fleet.datalayer.modal.auxiliary.SalarySearch;
import com.tmj.tms.fleet.datalayer.service.PayrollChargeConfigService;
import com.tmj.tms.fleet.datalayer.service.SalaryService;
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
@RequestMapping("fleet/employeeSalary")
public class EmployeeSalaryController {

    private final HttpSession httpSession;
    private final CompanyProfileService companyProfileService;
    private final EmployeeService employeeService;
    private final EmployeeDesignationService employeeDesignationService;
    private final PayrollChargeConfigService payrollChargeConfigService;
    private final EmployeeSalaryControllerManager employeeSalaryControllerManager;
    private final SalaryService salaryService;
    private final ReportUploadService reportUploadService;
    private final ModuleService moduleService;
    private final DocumentLinkService documentLinkService;
    private final ReportService reportService;

    @Autowired
    public EmployeeSalaryController(HttpSession httpSession,
                                    CompanyProfileService companyProfileService,
                                    EmployeeService employeeService,
                                    EmployeeDesignationService employeeDesignationService,
                                    PayrollChargeConfigService payrollChargeConfigService,
                                    EmployeeSalaryControllerManager employeeSalaryControllerManager,
                                    SalaryService salaryService,
                                    ReportUploadService reportUploadService,
                                    ModuleService moduleService,
                                    DocumentLinkService documentLinkService,
                                    ReportService reportService) {
        this.httpSession = httpSession;
        this.companyProfileService = companyProfileService;
        this.employeeService = employeeService;
        this.employeeDesignationService = employeeDesignationService;
        this.payrollChargeConfigService = payrollChargeConfigService;
        this.employeeSalaryControllerManager = employeeSalaryControllerManager;
        this.salaryService = salaryService;
        this.reportUploadService = reportUploadService;
        this.moduleService = moduleService;
        this.documentLinkService = documentLinkService;
        this.reportService = reportService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@employeeSalary_VIEW')")
    public String getPage(Model model) {
        this.pageLoad(model);
        return "fleet/employeeSalary";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_fleet@employeeSalary_CREATE')")
    public
    @ResponseBody
    ResponseObject save(@Valid @ModelAttribute SalarySearch salarySearch) {
        return this.employeeSalaryControllerManager.save(salarySearch);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_fleet@employeeSalary_UPDATE')")
    public
    @ResponseBody
    ResponseObject update(@Valid @ModelAttribute Salary salary) {
        return this.employeeSalaryControllerManager.update(salary);
    }

    @RequestMapping(value = "/findByDesignationSeq/{designationSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@employeeSalary_VIEW')")
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
    @PreAuthorize("hasRole('ROLE_fleet@employeeSalary_VIEW')")
    public String searchSalaryData(@ModelAttribute SalarySearch salarySearch,
                                   Model model) {
        model.addAttribute("salary", this.employeeSalaryControllerManager.findSalary(salarySearch));
        return "fleet/content/employeeSalaryDetail";
    }

    @RequestMapping(value = "/calculate", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_fleet@employeeSalary_VIEW')")
    public
    @ResponseBody
    Salary searchSalaryData(@ModelAttribute SalarySearch salarySearch) {
        return this.employeeSalaryControllerManager.calculate(salarySearch);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_fleet@employeeSalary_DELETE')")
    public
    @ResponseBody
    ResponseObject delete(@RequestParam(value = "salarySeq") Integer salarySeq) {
        return this.employeeSalaryControllerManager.delete(salarySeq);
    }

    @RequestMapping(value = "/searchSalaryData", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_fleet@employeeSalary_VIEW')")
    public String searchSalaryHistroryData(@ModelAttribute SalarySearch salarySearch,
                                           Model model) {
        model.addAttribute("salaryList", this.employeeSalaryControllerManager.search(salarySearch));
        return "fleet/content/employeeSalaryData";
    }

    @RequestMapping(value = "/findBySalarySeq/{salarySeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@employeeSalary_VIEW')")
    public
    @ResponseBody
    Salary findSalary(@PathVariable("salarySeq") Integer salarySeq) {
        Salary salary = this.salaryService.findOne(salarySeq);
        salary.setYearMonth(salary.getSalaryYear() + "/" + DateFormatter.getMonth(salary.getSalaryMonth()));
        return salary;
    }

    @RequestMapping(value = "/generateSalaryReport", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@employeeSalary_VIEW')")
    public
    @ResponseBody
    Integer processReport(@RequestParam("salarySeq") Integer salarySeq,
                          @RequestParam("reportSeq") Integer reportSeq,
                          @RequestParam(value = "pdf", required = false) Integer pdf,
                          @RequestParam(value = "rtf", required = false) Integer rtf,
                          @RequestParam(value = "xls", required = false) Integer xls,
                          HttpServletResponse httpServletResponse,
                          HttpServletRequest httpServletRequest,
                          Principal principal) {
        return this.employeeSalaryControllerManager.getSalaryReport(salarySeq, reportSeq, pdf, rtf, xls, httpServletRequest, httpServletResponse, principal);
    }

    @RequestMapping(value = "/getFile/{reportUploadSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_fleet@employeeSalary_VIEW')")
    public ResponseEntity<byte[]> getLogo(@PathVariable Integer reportUploadSeq) throws IOException {
        return this.reportUploadService.findAndDownload(reportUploadSeq);
    }

    private void pageLoad(Model model) {
        Integer companyProfileSeq = Integer.parseInt(httpSession.getAttribute("companyProfileSeq").toString());
        Module module = this.moduleService.findByModuleCode("FLEET");
        DocumentLink documentLink = this.documentLinkService.findByLinkName("employeeSalary");
        model.addAttribute("designationList", this.employeeDesignationService.findAll());
        model.addAttribute("monthList", Month.values());
        model.addAttribute("currentYear", Calendar.getInstance().get(Calendar.YEAR));
        model.addAttribute("currentMonth", Calendar.getInstance().get(Calendar.MONTH));
        model.addAttribute("startDate", DateFormatter.getStartOfLastMonth());
        model.addAttribute("endDate", DateFormatter.getEndOfLastMonth());
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransactions("ROLE_fleet@employeeSalary_VIEW"));
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

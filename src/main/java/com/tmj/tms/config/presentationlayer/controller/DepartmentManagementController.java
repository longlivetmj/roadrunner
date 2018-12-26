package com.tmj.tms.config.presentationlayer.controller;

import com.tmj.tms.config.businesslayer.manager.DepartmentManagementControllerManager;
import com.tmj.tms.config.datalayer.modal.Department;
import com.tmj.tms.config.datalayer.modal.DepartmentCharge;
import com.tmj.tms.config.datalayer.modal.DepartmentChargeAux;
import com.tmj.tms.config.datalayer.service.DepartmentChargeService;
import com.tmj.tms.config.datalayer.service.DepartmentService;
import com.tmj.tms.config.datalayer.service.ModuleService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.utility.ChargeType;
import com.tmj.tms.finance.utility.ReferenceType;
import com.tmj.tms.master.datalayer.service.ChargeService;
import com.tmj.tms.master.datalayer.service.CurrencyService;
import com.tmj.tms.master.datalayer.service.UnitService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/config/departmentManagement")
public class DepartmentManagementController {

    private final UnitService unitService;
    private final ModuleService moduleService;
    private final ChargeService chargeService;
    private final CurrencyService currencyService;
    private final DepartmentService departmentService;
    private final DepartmentChargeService departmentChargeService;
    private final DepartmentManagementControllerManager departmentManagementControllerManager;

    @Autowired
    public DepartmentManagementController(UnitService unitService,
                                          ModuleService moduleService,
                                          ChargeService chargeService,
                                          CurrencyService currencyService,
                                          DepartmentService departmentService,
                                          DepartmentChargeService departmentChargeService,
                                          DepartmentManagementControllerManager departmentManagementControllerManager) {
        this.unitService = unitService;
        this.moduleService = moduleService;
        this.chargeService = chargeService;
        this.currencyService = currencyService;
        this.departmentService = departmentService;
        this.departmentChargeService = departmentChargeService;
        this.departmentManagementControllerManager = departmentManagementControllerManager;

    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_config@departmentManagement_VIEW')")
    public String getPage(Model model, HttpServletRequest request, Principal principal) {
        this.pageLoad(model, request, principal);
        return "config/departmentManagement";
    }

    @RequestMapping(value = "/createDepartment", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@departmentManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject createDepartment(@Valid @ModelAttribute Department department, HttpServletRequest request,
                                    Principal principal) {
        return this.departmentManagementControllerManager.saveDepartment(department, principal, request);
    }

    @RequestMapping(value = "/updateDepartment", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@departmentManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateDepartment(@Valid @ModelAttribute Department department,
                                    Principal principal) {
        return this.departmentManagementControllerManager.updateDepartment(department, principal);
    }

    @RequestMapping(value = "/deleteByDepartmentSeq", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@departmentManagement_DELETE')")
    public
    @ResponseBody
    ResponseObject deleteDepartmentByDepartmentSeq(@RequestParam("departmentSeq") Integer departmentSeq,
                                                   Principal principal) {
        return this.departmentManagementControllerManager.deleteDepartment(departmentSeq, principal);
    }

    @RequestMapping(value = "/searchDepartmentData", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@departmentManagement_VIEW')")
    public String searchDepartmentData(@RequestParam(value = "departmentCode", required = false) String departmentCode,
                                       @RequestParam(value = "departmentName", required = false) String departmentName,
                                       @RequestParam(value = "prifix", required = false) String prifix,
                                       Model model) {
        List<Department> departmentList = this.departmentManagementControllerManager.searchDepartment(departmentCode, departmentName, prifix);
        model.addAttribute("departmentListDB", departmentList);
        return "config/content/departmentData";
    }

    @RequestMapping(value = "/findByDepartmentSeq/{departmentSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_config@departmentManagement_VIEW')")
    public
    @ResponseBody
    Department findByAgentNetworkSeq(@PathVariable("departmentSeq") Integer departmentSeq) {
        return this.departmentService.findOne(departmentSeq);
    }

    @RequestMapping(value = "/getDepartmentChargesData", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@departmentManagement_VIEW')")
    public String getDepartmentCharges(@RequestParam("moduleSeq") Integer moduleSeq,
                                       @RequestParam("departmentSeq") Integer departmentSeq,
                                       HttpServletRequest request,
                                       Model model) {
        List<DepartmentCharge> departmentCharges;
        if (departmentSeq != null) {
            departmentCharges = this.departmentChargeService.findByModuleSeqAndDepartmentSeq(moduleSeq, departmentSeq);
        } else {
            departmentCharges = this.departmentChargeService.findByModuleSeq(moduleSeq);
        }
        if (departmentCharges.size() == 0) {
            departmentCharges.add(new DepartmentCharge());
        }
        Integer companyProfileSeq = Integer.parseInt(request.getSession().getAttribute("companyProfileSeq").toString());
        model.addAttribute("departmentCharges", departmentCharges);
        model.addAttribute("chargeTypes", this.chargeService.findByCompanyProfileSeqAndStatus(companyProfileSeq, MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("currencyList", this.currencyService.findAll());
        model.addAttribute("unitList", this.unitService.findAll());
        model.addAttribute("chargeTypeList", ChargeType.values());
        model.addAttribute("referenceTypeList", ReferenceType.values());
        return "config/content/departmentChargesData";
    }

    @RequestMapping(value = "/findDepartmentsByModule/{moduleSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_config@departmentManagement_VIEW')")
    public
    @ResponseBody
    List<Department> findDepartmentsByModule(@PathVariable("moduleSeq") Integer moduleSeq) {
        return this.departmentService.findByModuleSeq(moduleSeq);
    }

    @RequestMapping(value = "/findChargesByModule/{moduleSeq}")
    @PreAuthorize("hasRole('ROLE_config@departmentManagement_VIEW')")
    public
    @ResponseBody
    List<DepartmentCharge> findChargesByModuleSeq(@PathVariable("moduleSeq") Integer moduleSeq) {
        return this.departmentChargeService.findByModuleSeq(moduleSeq);
    }

    @RequestMapping(value = "/saveDepartmentCharges", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@departmentManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject createDepartmentCharge(@ModelAttribute DepartmentChargeAux departmentCharges, Principal principal) {
        return this.departmentManagementControllerManager.saveDepartmentCharges(departmentCharges, principal);
    }

    public Model pageLoad(Model model, HttpServletRequest request, Principal principal) {
        Integer companyProfileSeq = Integer.parseInt(request.getSession().getAttribute("companyProfileSeq").toString());
        model.addAttribute("moduleList", this.moduleService.getModuleListByCompanySeqAndUsernameAndFinanceEnabled(companyProfileSeq, principal.getName(), 1));
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransactions("ROLE_config@departmentManagement_APPROVE"));
        return model;
    }

}


package com.tmj.tms.master.presentationlayer.controller;

import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.businesslayer.manager.BranchManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.Bank;
import com.tmj.tms.master.datalayer.modal.Branch;
import com.tmj.tms.master.datalayer.modal.Country;
import com.tmj.tms.master.datalayer.service.BankService;
import com.tmj.tms.master.datalayer.service.BranchService;
import com.tmj.tms.master.datalayer.service.CountryService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/master/branchManagement")
public class BranchManagementController {

    private final BranchService branchService;
    private final CountryService countryService;
    private final BankService bankService;
    private final BranchManagementControllerManager branchManagementControllerManager;

    @Autowired
    public BranchManagementController(BranchService branchService,
                                      CountryService countryService,
                                      BankService bankService,
                                      BranchManagementControllerManager branchManagementControllerManager) {
        this.branchService = branchService;
        this.countryService = countryService;
        this.bankService = bankService;
        this.branchManagementControllerManager = branchManagementControllerManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@branchManagement_VIEW')")
    public String getPage(Model model, HttpServletRequest request) {
        this.pageLoad(model, request);
        return "master/branchManagement";
    }

    @RequestMapping(value = "/addBranch", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@branchManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject addBranch(@Valid @ModelAttribute Branch branch) {
        return this.branchManagementControllerManager.saveBranch(branch);
    }


    @RequestMapping(value = "/updateBranch", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@branchManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateBranch(@Valid @ModelAttribute Branch branch) {
        return this.branchManagementControllerManager.updateBranch(branch);
    }

    @RequestMapping(value = "/findBranchByBranchSeq/{branchSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@branchManagement_VIEW')")
    public
    @ResponseBody
    Branch findBranchByBranchSeq(@PathVariable("branchSeq") Integer branchSeq) {
        return this.branchService.findOne(branchSeq);
    }

    @RequestMapping(value = "/searchBranchData", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@branchManagement_VIEW')")
    public String searchBranchData(@RequestParam(value = "bankName", required = false) String bankName,
                                   @RequestParam(value = "branchCode", required = false) String branchCode,
                                   @RequestParam(value = "branchName", required = false) String branchName,
                                   Model model) {
        List<Branch> branches = this.branchManagementControllerManager.searchBranch(branchCode, branchName, bankName);
        model.addAttribute("branchListDB", branches);
        return "master/content/branchData";
    }


    @RequestMapping(value = "/getBranchDetails/{branchSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@branchManagement_VIEW')")
    @ResponseBody
    public Branch getBranchDetails(@PathVariable("branchSeq") Integer branchSeq) {
        return this.branchService.findOne(branchSeq);
    }

    @RequestMapping(value = "/deleteByBranchSeq", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@branchManagement_DELETE')")
    public
    @ResponseBody
    ResponseObject deleteBranchByBranchSeq(@RequestParam("branchSeq") Integer branchSeq) {
        return this.branchManagementControllerManager.deleteBranch(branchSeq);
    }

    @RequestMapping(value = "/findCountry", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@branchManagement_VIEW')")
    public
    @ResponseBody
    List<Country> findCountry(@RequestParam("q") String searchParam) {
        return this.countryService.findByCountryNameStartsWithIgnoreCaseAndStatus(searchParam, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findBank", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@branchManagement_VIEW')")
    public
    @ResponseBody
    List<Bank> findBank(@RequestParam("q") String searchParam) {
        return this.bankService.findByBankNameStartsWithIgnoreCaseAndStatus(searchParam, MasterDataStatus.APPROVED.getStatusSeq());
    }

    private void pageLoad(Model model, HttpServletRequest request) {
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransactions("ROLE_master@branchManagement_APPROVE"));
        model.addAttribute("companyProfileSeq", request.getSession().getAttribute("companyProfileSeq").toString());
    }

}

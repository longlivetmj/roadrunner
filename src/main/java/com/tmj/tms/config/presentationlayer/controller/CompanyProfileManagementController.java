package com.tmj.tms.config.presentationlayer.controller;

import com.tmj.tms.config.businesslayer.manager.CompanyProfileManagementControllerManager;
import com.tmj.tms.config.datalayer.modal.CompanyModule;
import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.datalayer.modal.Module;
import com.tmj.tms.config.datalayer.service.CompanyModuleService;
import com.tmj.tms.config.datalayer.service.CompanyProfileService;
import com.tmj.tms.config.datalayer.service.ModuleService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "config/companyProfileManagement")
public class CompanyProfileManagementController {

    private final CompanyProfileService companyProfileService;
    private final ModuleService moduleService;
    private final CompanyModuleService companyModuleService;
    private final CompanyProfileManagementControllerManager companyProfileManagementControllerManager;

    @Autowired
    public CompanyProfileManagementController(CompanyProfileService companyProfileService,
                                              ModuleService moduleService,
                                              CompanyProfileManagementControllerManager companyProfileManagementControllerManager,
                                              CompanyModuleService companyModuleService) {
        this.companyProfileService = companyProfileService;
        this.companyProfileManagementControllerManager = companyProfileManagementControllerManager;
        this.companyModuleService = companyModuleService;
        this.moduleService = moduleService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getPage(Model model, Principal principal) {
        this.prepareModel(model, principal);
        return "config/companyProfileManagement";
    }

    private Model prepareModel(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("companyProfiles", this.companyProfileService.findAll());
        model.addAttribute("modules", this.moduleService.findAll());
        model.addAttribute("companyModules", this.companyModuleService.findAll());
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransactions("ROLE_config@freightLineManagement_APPROVE"));
        return model;
    }

    @RequestMapping(value = "/createCompanyProfile", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@companyProfileManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject createCompany(@RequestPart(value = "data") CompanyProfile companyProfile,
                                 @RequestPart(value = "companyLogo", required = false) MultipartFile multipartFile,
                                 Principal principal) {
        return this.companyProfileManagementControllerManager.createCompany(companyProfile, multipartFile, principal);
    }

    @RequestMapping(value = "/updateCompanyProfile", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@companyProfileManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateCompany(@RequestPart(value = "data") CompanyProfile companyProfile,
                                 @RequestPart(value = "companyLogo", required = false) MultipartFile multipartFile,

                                 Principal principal) {
        return this.companyProfileManagementControllerManager.updateCompany(companyProfile, multipartFile, principal);
    }

    @RequestMapping(value = "/deleteCompanyProfile", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@companyProfileManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject deleteCompanyProfile(@RequestParam("companyProfileSeq") Integer companyProfileSeq, Principal principal) {
        return this.companyProfileManagementControllerManager.deleteCompanyProfile(companyProfileSeq, principal);
    }

    @RequestMapping(value = "/removeModules", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@companyProfileManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject removeModules(@RequestParam(value = "companyToRemove") Integer companyProfileSeq,
                                 @RequestParam(value = "companyModulesToRemove") List<Integer> moduleSeqList,
                                 Principal principal) {
        return this.companyProfileManagementControllerManager.removeCompanyModules(companyProfileSeq, moduleSeqList, principal);
    }

    @RequestMapping(value = "/assignModules", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@companyProfileManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject assignModules(@RequestParam(value = "companyToAssign") Integer companyProfileSeq,
                                 @RequestParam(value = "companyModules") List<Integer> moduleSeqList,
                                 Principal principal) {

        return this.companyProfileManagementControllerManager.createCompanyModules(companyProfileSeq, moduleSeqList, principal);
    }

    /*
        Look-up/ Search Methods
     */

    @RequestMapping(value = "/findByCompanyProfileSeq/{companyProfileSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_config@companyProfileManagement_VIEW')")
    public
    @ResponseBody
    CompanyProfile findCompanyProfile(@PathVariable("companyProfileSeq") Integer companyProfileSeq) {
        return this.companyProfileService.findByCompanyProfileSeq(companyProfileSeq);
    }

    @RequestMapping(value = "/searchCompanyProfiles", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_config@companyProfileManagement_VIEW')")
    public String searchCompanyProfiles(@RequestParam(value = "companyName") String companyName, Model model) {
        model.addAttribute("companyProfiles", this.companyProfileService.findByCompanyNameContainingIgnoreCase(companyName));
        return "config/content/companyProfileData";
    }

    @RequestMapping(value = "/findCompanyModulesByCompanySeq/{companyProfileSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_config@companyProfileManagement_VIEW')")
    public
    @ResponseBody
    List<CompanyModule> findCompanyModulesByCompanySeq(@PathVariable("companyProfileSeq") Integer companyProfileSeq) {
        return this.companyModuleService.findByCompanyProfileSeq(companyProfileSeq);
    }

    @RequestMapping(value = "/findAllModules", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_config@companyProfileManagement_VIEW')")
    public
    @ResponseBody
    List<Module> findAllModules() {
        return this.moduleService.findAll();
    }

    @RequestMapping(value = "/getModuleListByCompanyProfileSeq/{companySeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_config@companyProfileManagement_VIEW')")
    public
    @ResponseBody
    List<Module> getModuleListByCompanySeq(@PathVariable("companySeq") Integer companySeq) {
        return this.moduleService.getModuleListByCompanySeq(companySeq);
    }
}

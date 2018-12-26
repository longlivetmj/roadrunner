package com.tmj.tms.master.presentationlayer.controller;

import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.businesslayer.manager.PackageTypeManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.PackageType;
import com.tmj.tms.master.datalayer.service.PackageTypeService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/master/packageTypeManagement")
public class PackageTypeManagementController {

    private final PackageTypeManagementControllerManager packageTypeManagementControllerManager;
    private final PackageTypeService packageTypeService;

    @Autowired
    public PackageTypeManagementController(PackageTypeManagementControllerManager packageTypeManagementControllerManager,
                                           PackageTypeService packageTypeService) {
        this.packageTypeManagementControllerManager = packageTypeManagementControllerManager;
        this.packageTypeService = packageTypeService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@packageTypeManagement_VIEW')")
    public String getPage(Model model) {
        this.pageLoad(model);
        return "master/packageTypeManagement";
    }

    @RequestMapping(value = "/createPackageType", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@packageTypeManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject createPackageType(@ModelAttribute PackageType packageType, Principal principal) {
        return this.packageTypeManagementControllerManager.savePackageType(packageType, principal);
    }

    @RequestMapping(value = "/updatePackageType", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@packageTypeManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject updatePackageType(@ModelAttribute PackageType packageType,
                                     Principal principal) {
        return this.packageTypeManagementControllerManager.updatePackageType(packageType, principal);
    }

    @RequestMapping(value = "/searchPackageType", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@packageTypeManagement_VIEW')")
    public String searchPackageType(@RequestParam(value = "packageTypeName", required = false, defaultValue = "") String packageTypeName,
                                    @RequestParam(value = "packageTypeCode", required = false, defaultValue = "") String packageTypeCode,
                                    @RequestParam(value = "description", required = false, defaultValue = "") String description,
                                    Model model) {
        model.addAttribute("packageTypeManagementList", this.packageTypeManagementControllerManager.searchPackageType(packageTypeName,
                packageTypeCode,
                description));
        return "master/content/packageTypeData";
    }

    @RequestMapping(value = "/findByPackageTypeSeq/{packageTypeSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@packageTypeManagement_VIEW')")
    public
    @ResponseBody
    PackageType findPackageTypeByPackageTypeSeq(@PathVariable("packageTypeSeq") Integer packageTypeSeq) {
        return this.packageTypeService.findOne(packageTypeSeq);
    }

    @RequestMapping(value = "/deleteByPackageTypeSeq", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@packageTypeManagement_DELETE')")
    public
    @ResponseBody
    ResponseObject deletePackageTypeByPackageTypeSeq(@RequestParam("packageTypeSeq") Integer packageTypeSeq,
                                                     Principal principal) {
        return this.packageTypeManagementControllerManager.deletePackageType(packageTypeSeq, principal);
    }

    private void pageLoad(Model model) {
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransactions("ROLE_master@packageTypeManagement_APPROVE"));
    }
}

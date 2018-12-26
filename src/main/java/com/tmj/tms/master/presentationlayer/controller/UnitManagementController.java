package com.tmj.tms.master.presentationlayer.controller;

import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.businesslayer.manager.UnitManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.Unit;
import com.tmj.tms.master.datalayer.service.UnitService;
import com.tmj.tms.master.utility.UnitCategory;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping(value = "/master/unitManagement")
public class UnitManagementController {

    private final UnitManagementControllerManager unitManagementControllerManager;
    private final UnitService unitService;

    @Autowired
    public UnitManagementController(UnitManagementControllerManager unitManagementControllerManager,
                                    UnitService unitService) {
        this.unitManagementControllerManager = unitManagementControllerManager;
        this.unitService = unitService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getPage(Model model) {
        this.pageLoad(model);
        return "master/unitManagement";
    }

    @RequestMapping(value = "/createUnit", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@unitManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject createUnit(@ModelAttribute Unit unit,
                              Principal principal) {
        return this.unitManagementControllerManager.createUnit(unit, principal);
    }

    @RequestMapping(value = "/updateUnit", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@unitManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateUnit(@ModelAttribute Unit unit,
                              Principal principal) {
        return this.unitManagementControllerManager.updateDeliveryType(unit, principal);
    }

    @RequestMapping(value = "/searchUnit", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@unitManagement_VIEW')")
    public String searchUnit(@RequestParam(value = "unitName") String unitName,
                             @RequestParam(value = "unitCode") String unitCode,
                             @RequestParam(value = "usedFor", required = false) String usedFor,
                             Model model) {
        model.addAttribute("unitList", this.unitManagementControllerManager.searchUnit(unitName, unitCode, usedFor));
        return "master/content/unitData";
    }

    @RequestMapping(value = "/findByUnitSeq/{unitSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@unitManagement_VIEW')")
    public
    @ResponseBody
    Unit findByDeliveryTypeSeq(@PathVariable("unitSeq") Integer unitSeq) {
        return this.unitService.findOne(unitSeq);
    }

    @RequestMapping(value = "/deleteByUnitSeq", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@unitManagement_DELETE')")
    public
    @ResponseBody
    ResponseObject deleteUnitByUnitSeq(@RequestParam("unitSeq") Integer unitSeq,
                                       Principal principal) {
        return this.unitManagementControllerManager.deleteUnit(unitSeq, principal.getName());
    }

    private void pageLoad(Model model) {
        model.addAttribute("usedForList", UnitCategory.values());
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransactions("ROLE_master@unitManagement_APPROVE"));
    }
}

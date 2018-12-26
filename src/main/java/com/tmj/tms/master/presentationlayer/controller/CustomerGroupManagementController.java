package com.tmj.tms.master.presentationlayer.controller;

import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.businesslayer.manager.CustomerGroupManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.CustomerGroup;
import com.tmj.tms.master.datalayer.service.CustomerGroupService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/master/customerGroupManagement")
public class CustomerGroupManagementController {

    private final CustomerGroupService customerGroupService;
    private final CustomerGroupManagementControllerManager customerGroupManagementControllerManager;

    public CustomerGroupManagementController(CustomerGroupService customerGroupService,
                                             CustomerGroupManagementControllerManager customerGroupManagementControllerManager) {
        this.customerGroupService = customerGroupService;
        this.customerGroupManagementControllerManager = customerGroupManagementControllerManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@customerGroupManagement_VIEW')")
    public String getPage(Model model,
                          HttpServletRequest request) {
        this.pageLoad(model, request);
        return "master/customerGroupManagement";
    }

    @RequestMapping(value = "/addCustomerGroup", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@customerGroupManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject addCustomerGroup(@Valid @ModelAttribute CustomerGroup customerGroup) {
        return this.customerGroupManagementControllerManager.saveCustomerGroup(customerGroup);
    }

    @RequestMapping(value = "/updateCustomerGroup", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@customerGroupManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateCustomerGroup(@Valid @ModelAttribute CustomerGroup customerGroup) {
        return this.customerGroupManagementControllerManager.updateCustomerGroup(customerGroup);
    }

    @RequestMapping(value = "/searchCustomerGroupData", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@customerGroupManagement_VIEW')")
    public String searchCustomerGroup(
            @RequestParam(value = "customerGroupCode", required = false) String customerGroupCode,
            @RequestParam(value = "customerGroupName") String customerGroupName,
            Model model) {
        model.addAttribute("customerGroupListDB", this.customerGroupManagementControllerManager.searchCustomerGroup(customerGroupCode, customerGroupName));
        return "master/content/customerGroupData";
    }

    @RequestMapping(value = "/getCustomerGroupDetails/{customerGroupSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@customerGroupManagement_VIEW')")
    @ResponseBody
    public CustomerGroup getCustomerGroupDetails(@PathVariable("customerGroupSeq") Integer customerGroupSeq) {
        return this.customerGroupService.findOne(customerGroupSeq);
    }

    @RequestMapping(value = "/findCustomerGroupByCustomerGroupSeq/{customerGroupSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@customerGroupManagement_VIEW')")
    public
    @ResponseBody
    CustomerGroup findCustomerGroupByCustomerGroupSeq(@PathVariable("customerGroupSeq") Integer customerGroupSeq) {
        return this.customerGroupService.findOne(customerGroupSeq);
    }

    @RequestMapping(value = "/deleteByCustomerGroupSeq", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@customerGroupManagement_DELETE')")
    public
    @ResponseBody
    ResponseObject deleteByCustomerGroupByCustomerGroupSeq(@RequestParam("customerGroupSeq") Integer customerGroupSeq) {
        return this.customerGroupManagementControllerManager.deleteCustomerGroup(customerGroupSeq);
    }

    public Model pageLoad(Model model, HttpServletRequest request) {
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransactions("ROLE_master@customerGroupManagement_APPROVE"));
        model.addAttribute("companyProfileSeq", request.getSession().getAttribute("companyProfileSeq").toString());
        return model;
    }
}

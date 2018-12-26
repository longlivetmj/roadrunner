package com.tmj.tms.master.presentationlayer.controller;

import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.businesslayer.manager.SalesPersonManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.Country;
import com.tmj.tms.master.datalayer.modal.SalesPerson;
import com.tmj.tms.master.datalayer.modal.Stakeholder;
import com.tmj.tms.master.datalayer.service.CountryService;
import com.tmj.tms.master.datalayer.service.SalesPersonService;
import com.tmj.tms.master.datalayer.service.StakeholderService;
import com.tmj.tms.master.datalayer.service.StakeholderTypeService;
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
@RequestMapping("/master/salesPersonManagement")
public class SalesPersonManagementController {

    private final SalesPersonService salesPersonService;
    private final CountryService countryService;
    private final SalesPersonManagementControllerManager salesPersonManagementControllerManager;
    private final StakeholderService stakeholderService;
    private final StakeholderTypeService stakeholderTypeService;

    @Autowired
    public SalesPersonManagementController(SalesPersonService salesPersonService,
                                           CountryService countryService,
                                           SalesPersonManagementControllerManager salesPersonManagementControllerManager,
                                           StakeholderService stakeholderService,
                                           StakeholderTypeService stakeholderTypeService) {
        this.salesPersonService = salesPersonService;
        this.countryService = countryService;
        this.salesPersonManagementControllerManager = salesPersonManagementControllerManager;
        this.stakeholderService = stakeholderService;
        this.stakeholderTypeService = stakeholderTypeService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@salesPersonManagement_VIEW')")
    public String getPage(Model model, HttpServletRequest request) {
        this.pageLoad(model, request);
        return "master/salesPersonManagement";
    }

    @RequestMapping(value = "/createSalesPerson", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@salesPersonManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject createSalesPerson(@Valid @ModelAttribute SalesPerson salesPerson) {
        return this.salesPersonManagementControllerManager.saveSalesPerson(salesPerson);
    }

    @RequestMapping(value = "/updateSalesPerson", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@salesPersonManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateSalesPerson(@Valid @ModelAttribute SalesPerson salesPerson) {
        return this.salesPersonManagementControllerManager.updateSalesPerson(salesPerson);
    }

    @RequestMapping(value = "/searchSalesPersonData", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@salesPersonManagement_VIEW')")
    public String searchSalesPersonData(@RequestParam(value = "salesPersonCode", required = false) String salesPersonCode,
                                        @RequestParam(value = "salesPersonName", required = false) String salesPersonName,
                                        @RequestParam(value = "stakeholderSeq") Integer stakeholderSeq,
                                        HttpServletRequest request,
                                        Model model) {
        List<SalesPerson> salesPersonList = this.salesPersonManagementControllerManager.searchSalesPerson(salesPersonCode, salesPersonName, stakeholderSeq,request);
        model.addAttribute("salesPersonListDB", salesPersonList);
        return "master/content/salesPersonData";
    }

    @RequestMapping(value = "/deleteBySalesPersonSeq", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@salesPersonManagement_DELETE')")
    public
    @ResponseBody
    ResponseObject deleteBySalesPersonBySalesPersonSeq(@RequestParam("salesPersonSeq") Integer salesPersonSeq) {
        return this.salesPersonManagementControllerManager.deleteSalesPerson(salesPersonSeq);
    }

    @RequestMapping(value = "/getSalesPersonDetails/{salesPersonSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@salesPersonManagement_VIEW')")
    @ResponseBody
    public SalesPerson getSalesPersonDetails(@PathVariable("salesPersonSeq") Integer salesPersonSeq) {
        return this.salesPersonService.findOne(salesPersonSeq);
    }

    @RequestMapping(value = "/findStakeholder", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@salesPersonManagement_VIEW')")
    public
    @ResponseBody
    List<Stakeholder> findStakeholders(@RequestParam("q") String searchParam, HttpServletRequest request) {
        return this.stakeholderService.getStakeholderDetails(searchParam,
                this.stakeholderTypeService.findByStakeholderTypeCodeAndStatus("SP_COMPANY", MasterDataStatus.APPROVED.getStatusSeq()).getStakeholderTypeSeq(),
                Integer.parseInt(request.getSession().getAttribute("companyProfileSeq").toString()),
                MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findCountry", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@salesPersonManagement_VIEW')")
    public
    @ResponseBody
    List<Country> findCountry(@RequestParam("q") String searchParam) {
        return this.countryService.findByCountryNameStartsWithIgnoreCaseAndStatus(searchParam, MasterDataStatus.APPROVED.getStatusSeq());
    }

    public Model pageLoad(Model model, HttpServletRequest request) {
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransactions("ROLE_master@salesPersonManagement_APPROVE"));
        model.addAttribute("companyProfileSeq", request.getSession().getAttribute("companyProfileSeq").toString());
        return model;
    }
}

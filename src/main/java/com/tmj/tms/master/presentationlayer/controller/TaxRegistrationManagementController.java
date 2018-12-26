package com.tmj.tms.master.presentationlayer.controller;

import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.businesslayer.manager.TaxRegistrationManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.Country;
import com.tmj.tms.master.datalayer.modal.TaxRegistration;
import com.tmj.tms.master.datalayer.service.CountryService;
import com.tmj.tms.master.datalayer.service.TaxRegistrationService;
import com.tmj.tms.master.datalayer.service.TaxTypeService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/master/taxRegistrationManagement")
public class TaxRegistrationManagementController {

    private final CountryService countryService;
    private final TaxTypeService taxTypeService;
    private final TaxRegistrationManagementControllerManager taxRegistrationManagementControllerManager;
    private final TaxRegistrationService taxRegistrationService;

    @Autowired
    public TaxRegistrationManagementController(CountryService countryService,
                                               TaxTypeService taxTypeService,
                                               TaxRegistrationManagementControllerManager taxRegistrationManagementControllerManager,
                                               TaxRegistrationService taxRegistrationService) {
        this.countryService = countryService;
        this.taxTypeService = taxTypeService;
        this.taxRegistrationManagementControllerManager = taxRegistrationManagementControllerManager;
        this.taxRegistrationService = taxRegistrationService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@taxRegistrationManagement_VIEW')")
    public String getPage(Model model, HttpServletRequest request) {
        this.pageLoad(model, request);
        return "master/taxRegistrationManagement";
    }

    @RequestMapping(value = "/createTaxRegistration", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@taxRegistrationManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject createTaxRegistration(@ModelAttribute TaxRegistration taxRegistration) {
        return this.taxRegistrationManagementControllerManager.createTaxRegistration(taxRegistration);
    }

    @RequestMapping(value = "/updateTaxRegistration", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@taxRegistrationManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateTaxRegistration(@ModelAttribute TaxRegistration taxRegistration) {
        return this.taxRegistrationManagementControllerManager.updateTaxRegistration(taxRegistration);
    }

    @RequestMapping(value = "/searchTaxRegistration", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@taxRegistrationManagement_VIEW')")
    public String searchTaxRegistration(@RequestParam(value = "taxName", required = false, defaultValue = "") String taxName,
                                        @RequestParam(value = "remarks", required = false, defaultValue = "") String remarks,
                                        @RequestParam(value = "countrySeq") Integer countrySeq,
                                        HttpServletRequest request,
                                        Model model) {
        Integer customerProfileSeq = Integer.parseInt(request.getSession().getAttribute("companyProfileSeq").toString());
        model.addAttribute("taxRegistrationList", this.taxRegistrationManagementControllerManager.searchTaxRegistration(taxName, remarks, countrySeq, customerProfileSeq));
        return "master/content/taxRegistrationData";
    }

    @RequestMapping(value = "/findByTaxRegistrationSeq/{taxRegistrationSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@taxRegistrationManagement_VIEW')")
    public
    @ResponseBody
    TaxRegistration findTaxRegistrationByTaxRegistrationSeq(@PathVariable("taxRegistrationSeq") Integer taxRegistrationSeq) {
        return this.taxRegistrationService.findOne(taxRegistrationSeq);
    }

    @RequestMapping(value = "/deleteByTaxRegistrationSeq", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@taxRegistrationManagement_DELETE')")
    public
    @ResponseBody
    ResponseObject deleteTaxRegistrationByTaxRegistrationSeq(@RequestParam("taxRegistrationSeq") Integer taxRegistrationSeq) {
        return this.taxRegistrationManagementControllerManager.deleteTaxRegistration(taxRegistrationSeq);
    }

    @RequestMapping(value = "/findCountry", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@taxRegistrationManagement_VIEW')")
    public
    @ResponseBody
    List<Country> findCountry(@RequestParam("q") String searchParam) {
        return this.countryService.findByCountryNameStartsWithIgnoreCaseAndStatus(searchParam, 2);
    }

    private void pageLoad(Model model, HttpServletRequest request) {
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransactions("ROLE_master@taxRegistrationManagement_APPROVE"));
        model.addAttribute("taxTypeList", this.taxTypeService.findByStatus(2));
        model.addAttribute("companyProfileSeq", request.getSession().getAttribute("companyProfileSeq").toString());
    }
}

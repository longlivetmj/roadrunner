package com.tmj.tms.master.presentationlayer.controller;

import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.businesslayer.manager.CountryManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.Country;
import com.tmj.tms.master.datalayer.modal.Currency;
import com.tmj.tms.master.datalayer.modal.Region;
import com.tmj.tms.master.datalayer.service.CountryService;
import com.tmj.tms.master.datalayer.service.CurrencyService;
import com.tmj.tms.master.datalayer.service.RegionService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/master/countryManagement")
public class CountryManagementController {

    private final CountryManagementControllerManager countryManagementControllerManager;
    private final CurrencyService currencyService;
    private final RegionService regionService;
    private final CountryService countryService;

    @Autowired
    public CountryManagementController(CountryManagementControllerManager countryManagementControllerManager,
                                       CurrencyService currencyService,
                                       RegionService regionService,
                                       CountryService countryService) {
        this.countryManagementControllerManager = countryManagementControllerManager;
        this.currencyService = currencyService;
        this.regionService = regionService;
        this.countryService = countryService;
    }

    @PreAuthorize("hasRole('ROLE_master@countryManagement_VIEW')")
    @RequestMapping(method = RequestMethod.GET)
    public String getPage(Model model) {
        this.pageLoad(model);
        return "master/countryManagement";
    }

    @RequestMapping(value = "/updateCountry", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@countryManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateCountry(@ModelAttribute Country country) {
        return this.countryManagementControllerManager.updateCountry(country);
    }

    @RequestMapping(value = "/deleteByCountrySeq", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@countryManagement_DELETE')")
    public
    @ResponseBody
    ResponseObject deleteCountryByCountrySeq(@RequestParam("countrySeq") Integer countrySeq) {
        return this.countryManagementControllerManager.deleteCountry(countrySeq);
    }

    @RequestMapping(value = "/searchCountry", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@countryManagement_VIEW')")
    public String searchCountry(@RequestParam(value = "countryName") String countryName,
                                @RequestParam(value = "countryCode") String countryCode,
                                Model model) {
        model.addAttribute("countryList", this.countryManagementControllerManager.searchCountry(countryName, countryCode));
        return "master/content/countryData";
    }

    @RequestMapping(value = "/findRegion", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@countryManagement_VIEW')")
    public
    @ResponseBody
    List<Region> findRegion(@RequestParam("q") String searchParam) {
        return this.regionService.findByRegionNameStartsWithIgnoreCaseAndStatus(searchParam, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findCurrency", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@countryManagement_VIEW')")
    public
    @ResponseBody
    List<Currency> findCurrency(@RequestParam("q") String searchParam) {
        return this.currencyService.findByCurrencyNameStartsWithIgnoreCaseAndStatus(searchParam, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findCountryByCountrySeq/{countrySeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@countryManagement_VIEW')")
    public
    @ResponseBody
    Country findCountryByCountrySeq(@PathVariable("countrySeq") Integer countrySeq) {
        return this.countryService.findOne(countrySeq);
    }

    @RequestMapping(value = "/getCountryDetails/{countrySeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@countryManagement_VIEW')")
    @ResponseBody
    public Country getCountryDetails(@PathVariable("countrySeq") Integer countrySeq) {
        return this.countryService.findOne(countrySeq);
    }

    private void pageLoad(Model model) {
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransactions("ROLE_master@countryManagement_APPROVE"));
    }
}

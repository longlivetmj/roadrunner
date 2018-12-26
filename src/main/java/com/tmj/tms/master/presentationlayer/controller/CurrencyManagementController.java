package com.tmj.tms.master.presentationlayer.controller;

import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.businesslayer.manager.CurrencyManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.Currency;
import com.tmj.tms.master.datalayer.service.CurrencyService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/master/currencyManagement")
public class CurrencyManagementController {

    private final CurrencyManagementControllerManager currencyManagementControllerManager;
    private final CurrencyService currencyService;

    @Autowired
    public CurrencyManagementController(CurrencyManagementControllerManager currencyManagementControllerManager,
                                        CurrencyService currencyService) {
        this.currencyManagementControllerManager = currencyManagementControllerManager;
        this.currencyService = currencyService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@currencyManagement_VIEW')")
    public String getPage(Model model) {
        this.pageLoad(model);
        return "master/currencyManagement";
    }

    @RequestMapping(value = "/createCurrency", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@currencyManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject createCurrency(@ModelAttribute Currency currency) {
        return this.currencyManagementControllerManager.saveCurrency(currency);
    }

    @RequestMapping(value = "/updateCurrency", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@currencyManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateCurrency(@ModelAttribute Currency currency) {
        return this.currencyManagementControllerManager.updateCurrency(currency);
    }

    @RequestMapping(value = "/searchCurrency", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@currencyManagement_VIEW')")
    public String searchCurrency(@RequestParam(value = "currencyCode", required = false) String currencyCode,
                                 @RequestParam(value = "currencyName", required = false) String currencyName,
                                 Model model) {
        List<Currency> currencyList = this.currencyManagementControllerManager.searchCurrencyByCurrencyCodeAndCurrencyName(currencyCode,
                currencyName);
        model.addAttribute("currencyManagementList", currencyList);
        return "master/content/currencyData";
    }

    @RequestMapping(value = "/deleteByCurrencySeq", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@currencyManagement_DELETE')")
    public
    @ResponseBody
    ResponseObject deleteCurrencyByCurrencySeq(@RequestParam("currencySeq") Integer currencySeq) {
        return this.currencyManagementControllerManager.deleteCurrency(currencySeq);
    }

    @RequestMapping(value = "/getCurrencyDetails/{currencySeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@currencyManagement_VIEW')")
    public
    @ResponseBody
    Currency getCurrencyDetails(@PathVariable("currencySeq") Integer currencySeq) {
        return this.currencyService.findOne(currencySeq);
    }

    private void pageLoad(Model model) {
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransactions("ROLE_master@currencyManagement_APPROVE"));
    }

}

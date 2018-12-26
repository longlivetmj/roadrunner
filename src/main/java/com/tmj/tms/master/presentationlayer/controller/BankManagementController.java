package com.tmj.tms.master.presentationlayer.controller;

import com.tmj.tms.master.businesslayer.manager.BankManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.Bank;
import com.tmj.tms.master.datalayer.service.BankService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/master/bankManagement")
public class BankManagementController {

    private final BankManagementControllerManager bankManagementControllerManager;
    private final BankService bankService;

    @Autowired
    public BankManagementController(BankManagementControllerManager bankManagementControllerManager,
                                    BankService bankService) {
        this.bankManagementControllerManager = bankManagementControllerManager;
        this.bankService = bankService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@bankManagement_VIEW')")
    public String getPage(Model model) {
        this.pageLoad(model);
        return "master/bankManagement";
    }

    @RequestMapping(value = "/createBank", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@bankManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject createBank(@ModelAttribute Bank bank) {
        return this.bankManagementControllerManager.createBank(bank);
    }

    @RequestMapping(value = "/updateBank", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@bankManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateBank(@ModelAttribute Bank bank) {
        return this.bankManagementControllerManager.updateBank(bank);
    }

    @RequestMapping(value = "/searchBank", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@bankManagement_VIEW')")
    public String searchBank(@RequestParam(value = "bankName") String bankName,
                             @RequestParam(value = "bankCode") String bankCode,
                             Model model) {
        model.addAttribute("bankList", this.bankManagementControllerManager.searchBank(bankName, bankCode));
        return "master/content/bankData";
    }

    @RequestMapping(value = "/findBankByBankSeq/{bankSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@bankManagement_VIEW')")
    public
    @ResponseBody
    Bank findBankByBankSeq(@PathVariable("bankSeq") Integer bankSeq) {
        return this.bankService.findOne(bankSeq);
    }

    @RequestMapping(value = "/getBankDetails/{bankSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@bankManagement_VIEW')")
    @ResponseBody
    public Bank getBankDetails(@PathVariable("bankSeq") Integer bankSeq) {
        return this.bankService.findOne(bankSeq);
    }

    @RequestMapping(value = "/deleteByBankSeq", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@bankManagement_DELETE')")
    public
    @ResponseBody
    ResponseObject deleteBankByBankSeq(@RequestParam("bankSeq") Integer bankSeq) {
        return this.bankManagementControllerManager.deleteBank(bankSeq);
    }

    private void pageLoad(Model model) {
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransactions("ROLE_master@chargeManagement_APPROVE"));
    }

}

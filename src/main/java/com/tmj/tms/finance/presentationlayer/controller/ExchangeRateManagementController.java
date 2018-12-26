package com.tmj.tms.finance.presentationlayer.controller;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.datalayer.service.CompanyProfileService;
import com.tmj.tms.config.datalayer.service.ModuleService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.bussinesslayer.manager.ExchangeRateManagementControllerManager;
import com.tmj.tms.finance.datalayer.modal.ExchangeRate;
import com.tmj.tms.finance.datalayer.modal.auxiliary.ExchangeRateSearchAux;
import com.tmj.tms.finance.datalayer.service.ExchangeRateService;
import com.tmj.tms.finance.utility.ExchangeRateSourceType;
import com.tmj.tms.master.datalayer.service.BankService;
import com.tmj.tms.master.datalayer.service.CurrencyService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/finance/exchangeRateManagement")
public class ExchangeRateManagementController {

    private final CurrencyService currencyService;
    private final BankService bankService;
    private final ExchangeRateManagementControllerManager exchangeRateManagementControllerManager;
    private final ModuleService moduleService;
    private final ExchangeRateService exchangeRateService;
    private final CompanyProfileService companyProfileService;
    private final HttpSession httpSession;

    @Autowired
    public ExchangeRateManagementController(CurrencyService currencyService,
                                            BankService bankService,
                                            ExchangeRateManagementControllerManager exchangeRateManagementControllerManager,
                                            ModuleService moduleService,
                                            ExchangeRateService exchangeRateService,
                                            CompanyProfileService companyProfileService,
                                            HttpSession httpSession) {
        this.currencyService = currencyService;
        this.bankService = bankService;
        this.exchangeRateManagementControllerManager = exchangeRateManagementControllerManager;
        this.moduleService = moduleService;
        this.exchangeRateService = exchangeRateService;
        this.companyProfileService = companyProfileService;
        this.httpSession = httpSession;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@exchangeRateManagement_VIEW')")
    public String getPage(Model model, Principal principal) {
        this.pageLoad(model, principal);
        return "finance/exchangeRateManagement";
    }

    @RequestMapping(value = "/createExchangeRate", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@exchangeRateManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject createExchangeRate(@ModelAttribute ExchangeRate exchangeRate) {
        return this.exchangeRateManagementControllerManager.saveExchangeRate(exchangeRate);
    }

    @RequestMapping(value = "/updateExchangeRate", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@exchangeRateManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateExchangeRate(@ModelAttribute ExchangeRate exchangeRate) {
        return this.exchangeRateManagementControllerManager.updateExchangeRate(exchangeRate);
    }

    @RequestMapping(value = "/deleteExchangeRate", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@exchangeRateManagement_DELETE')")
    public
    @ResponseBody
    ResponseObject deleteExchangeRate(@RequestParam("exchangeRateSeq") Integer exchangeRateSeq) {
        return this.exchangeRateManagementControllerManager.deleteExchangeRate(exchangeRateSeq);
    }

    @RequestMapping(value = "/searchExchangeRateData", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@exchangeRateManagement_VIEW')")
    public String searchExchangeRateData(@ModelAttribute ExchangeRateSearchAux exchangeRateSearchAux,
                                         Model model) {
        List<ExchangeRate> exchangeList = this.exchangeRateManagementControllerManager.searchExchangeRate(exchangeRateSearchAux);
        model.addAttribute("exchangeRateList", exchangeList);
        return "finance/content/exchangeRateData";
    }

    @RequestMapping(value = "/findByExchangeRateSeq/{exchangeRateSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@exchangeRateManagement_VIEW')")
    public
    @ResponseBody
    ExchangeRate findByExchangeRateSeq(@PathVariable("exchangeRateSeq") Integer exchangeRateSeq) {
        return this.exchangeRateService.findOne(exchangeRateSeq, EntityGraphUtils.fromName("ExchangeRate.search"));
    }

    public Model pageLoad(Model model, Principal principal) {
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        CompanyProfile companyProfile = this.companyProfileService.findOne(companyProfileSeq);
        model.addAttribute("sourceTypeList", ExchangeRateSourceType.values());
        model.addAttribute("defaultCurrencySeq", companyProfile.getLocalCurrencySeq());
        model.addAttribute("currencyList", this.currencyService.findByStatus(MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("bankList", this.bankService.findByStatus(MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("moduleList", this.moduleService.getModuleListByCompanySeqAndUsernameAndFinanceEnabled(companyProfileSeq, principal.getName(), 1));
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransactions("ROLE_finance@exchangeRateManagement_APPROVE"));
        return model;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}

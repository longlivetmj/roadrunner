package com.tmj.tms.master.presentationlayer.controller;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.businesslayer.manager.StakeholderManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.*;
import com.tmj.tms.master.datalayer.service.*;
import com.tmj.tms.master.utility.StakeholderCashType;
import com.tmj.tms.master.utility.StakeholderCreditType;
import com.tmj.tms.master.utility.StakeholderSvatType;
import com.tmj.tms.utility.NDaysBefore;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("master/stakeholderManagement")
public class StakeholderManagementController {

    private final CustomerGroupService customerGroupService;
    private final CountryService countryService;
    private final CurrencyService currencyService;
    private final TaxTypeService taxTypeService;
    private final BankService bankService;
    private final StakeholderTypeService stakeholderTypeService;
    private final StakeholderManagementControllerManager stakeholderManagementControllerManager;
    private final StakeholderService stakeholderService;
    private final SalesPersonService salesPersonService;
    private final StakeholderTypeMappingService stakeholderTypeMappingService;

    @Autowired
    public StakeholderManagementController(CustomerGroupService customerGroupService,
                                           CountryService countryService,
                                           CurrencyService currencyService,
                                           TaxTypeService taxTypeService,
                                           BankService bankService,
                                           StakeholderTypeService stakeholderTypeService,
                                           StakeholderManagementControllerManager stakeholderManagementControllerManager,
                                           StakeholderService stakeholderService,
                                           SalesPersonService salesPersonService,
                                           StakeholderTypeMappingService stakeholderTypeMappingService) {
        this.customerGroupService = customerGroupService;
        this.countryService = countryService;
        this.currencyService = currencyService;
        this.taxTypeService = taxTypeService;
        this.bankService = bankService;
        this.stakeholderTypeService = stakeholderTypeService;
        this.stakeholderManagementControllerManager = stakeholderManagementControllerManager;
        this.stakeholderService = stakeholderService;
        this.salesPersonService = salesPersonService;
        this.stakeholderTypeMappingService = stakeholderTypeMappingService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@stakeholderManagement_VIEW')")
    public String getPage(Model model) {
        this.pageLoad(model);
        return "master/stakeholderManagement";
    }

    @RequestMapping(value = "/createStakeholder", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@stakeholderManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject createStakeholder(@ModelAttribute Stakeholder stakeholder,
                                     HttpServletRequest request,
                                     Principal principal) {
        return this.stakeholderManagementControllerManager.saveStakeholder(stakeholder, request, principal);
    }

    @RequestMapping(value = "/updateStakeholder", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@stakeholderManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateStakeholder(@ModelAttribute Stakeholder stakeholder,
                                     Principal principal) {
        return this.stakeholderManagementControllerManager.updateStakeholder(stakeholder, principal);
    }

    @RequestMapping(value = "/searchStakeholder", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@stakeholderManagement_VIEW')")
    public String searchStakeholder(@RequestParam(value = "stakeholderSeq", required = false) String stakeholderSeq,
                                    @RequestParam(value = "stakeholderTypeSeq", required = false) Integer stakeholderTypeSeq,
                                    @RequestParam(value = "stakeholderName", required = false) String stakeholderName,
                                    @RequestParam(value = "countrySeq", required = false, defaultValue = "-1") Integer countrySeq,
                                    @RequestParam(value = "status", required = false) Integer status,
                                    @RequestParam(value = "stakeholderGroupSeq", required = false) Integer stakeholderGroupSeq,
                                    @RequestParam(value = "startDate", required = false) Date startDate,
                                    @RequestParam(value = "endDate", required = false) Date endDate,
                                    Model model) {
        model.addAttribute("stakeholderList", this.stakeholderManagementControllerManager.searchStakeholder(stakeholderSeq,
                stakeholderTypeSeq,
                stakeholderName,
                countrySeq,
                status,
                stakeholderGroupSeq,
                startDate,
                endDate));
        return "master/content/stakeholderData";
    }

    @RequestMapping(value = "/findByStakeholderSeq/{stakeholderSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@stakeholderManagement_VIEW')")
    public
    @ResponseBody
    Stakeholder findStakeholderByStakeholderSeq(@PathVariable("stakeholderSeq") Integer stakeholderSeq) {
        return this.stakeholderService.findOne(stakeholderSeq, EntityGraphUtils.fromName("Stakeholder.sendEmailStakeholderDetail"));
    }


    @RequestMapping(value = "/findStakeholderTypeListByStakeholderSeq/{stakeholderSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@stakeholderManagement_VIEW')")
    public
    @ResponseBody
    List<StakeholderTypeMapping> findStakeholderTypeListByStakeholderSeq(@PathVariable("stakeholderSeq") Integer stakeholderSeq) {
        return this.stakeholderTypeMappingService.findByStakeholderSeq(stakeholderSeq);
    }


    @RequestMapping(value = "/deleteByStakeholderSeq", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@stakeholderManagement_DELETE')")
    public
    @ResponseBody
    ResponseObject deleteStakeholderByStakeholderSeq(@RequestParam("stakeholderSeq") Integer stakeholderSeq,
                                                     Principal principal) {
        return this.stakeholderManagementControllerManager.deleteStakeholder(stakeholderSeq, principal);
    }

    @RequestMapping(value = "/findCountry", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@stakeholderManagement_VIEW')")
    public
    @ResponseBody
    List<Country> findCountry(@RequestParam("q") String searchParam) {
        return this.countryService.findByCountryNameStartsWithIgnoreCaseAndStatus(searchParam, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findStakeHolderGroup", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@stakeholderManagement_VIEW')")
    public
    @ResponseBody
    List<CustomerGroup> findStakeHolderGroup(@RequestParam("q") String searchParam) {
        return this.customerGroupService.findByCustomerGroupNameStartsWithIgnoreCaseAndStatus(searchParam, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findCurrency", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@stakeholderManagement_VIEW')")
    public
    @ResponseBody
    List<Currency> findCurrency(@RequestParam("q") String searchParam) {
        return this.currencyService.findByCurrencyNameStartsWithIgnoreCaseAndStatus(searchParam, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findSalesPerson", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@stakeholderManagement_VIEW')")
    public
    @ResponseBody
    List<SalesPerson> findSalesPerson(@RequestParam("q") String searchParam) {
        return this.salesPersonService.findBySalesPersonNameStartsWithIgnoreCaseAndStatus(searchParam, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findBank", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@stakeholderManagement_VIEW')")
    public
    @ResponseBody
    List<Bank> findBank(@RequestParam("q") String searchParam) {
        return this.bankService.findByBankNameStartsWithIgnoreCaseAndStatus(searchParam, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findStakeholderType", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@stakeholderManagement_VIEW')")
    public
    @ResponseBody
    List<StakeholderType> findStakeholderType(@RequestParam("q") String searchParam) {
        return this.stakeholderTypeService.findByStakeholderTypeNameStartsWithIgnoreCaseAndStatus(searchParam, MasterDataStatus.APPROVED.getStatusSeq());
    }

    private void pageLoad(Model model) {
        Integer status = MasterDataStatus.APPROVED.getStatusSeq();
        String[] statusList = {"ROLE_master@stakeholderManagement_APPROVE", "ROLE_master@stakeholderManagement_DELETE"};
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransaction(Arrays.asList(statusList)));
        model.addAttribute("svalStakeholderTypeList", StakeholderSvatType.values());
        model.addAttribute("stakeholderTypeList", this.stakeholderTypeService.findByStatus(status));
        model.addAttribute("customerGroupList", this.customerGroupService.findByStatus(status));
        model.addAttribute("customerGroupList", this.customerGroupService.findByStatus(status));
        model.addAttribute("stakeholderCashTypeList", StakeholderCashType.values());
        model.addAttribute("stakeholderCreditTypeList", StakeholderCreditType.values());
        model.addAttribute("TaxTypeList", this.taxTypeService.findByStatus(status));
        model.addAttribute("fromDate", new NDaysBefore().getDateNDaysBeforeCurrentDate(30));
        model.addAttribute("toDate", new NDaysBefore().getDateNDaysAfterCurrentDate(1));
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}

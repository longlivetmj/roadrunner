package com.tmj.tms.finance.presentationlayer.controller;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.tmj.tms.config.datalayer.modal.CompanyModule;
import com.tmj.tms.config.datalayer.modal.Department;
import com.tmj.tms.config.datalayer.modal.DocumentLink;
import com.tmj.tms.config.datalayer.modal.Report;
import com.tmj.tms.config.datalayer.service.*;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.bussinesslayer.manager.ExpenseVoucherControllerManager;
import com.tmj.tms.finance.datalayer.modal.ExchangeRate;
import com.tmj.tms.finance.datalayer.modal.ExpenseVoucher;
import com.tmj.tms.finance.datalayer.modal.FinancialCharge;
import com.tmj.tms.finance.datalayer.modal.auxiliary.ExpenseVoucherSearchAux;
import com.tmj.tms.finance.datalayer.service.ExchangeRateService;
import com.tmj.tms.finance.datalayer.service.ExpenseVoucherService;
import com.tmj.tms.finance.datalayer.service.FinancialChargeService;
import com.tmj.tms.finance.utility.ExchangeRateSourceType;
import com.tmj.tms.finance.utility.ExpenseVoucherSearchFromAux;
import com.tmj.tms.finance.utility.TargetType;
import com.tmj.tms.master.datalayer.modal.AddressBook;
import com.tmj.tms.master.datalayer.modal.Currency;
import com.tmj.tms.master.datalayer.modal.Stakeholder;
import com.tmj.tms.master.datalayer.service.AddressBookService;
import com.tmj.tms.master.datalayer.service.BankService;
import com.tmj.tms.master.datalayer.service.StakeholderService;
import com.tmj.tms.master.datalayer.service.StakeholderTypeService;
import com.tmj.tms.master.utility.AddressBookUtils;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.transport.datalayer.modal.TransportBookingStatus;
import com.tmj.tms.transport.datalayer.service.TransportBookingService;
import com.tmj.tms.transport.datalayer.service.TransportBookingStatusService;
import com.tmj.tms.utility.NDaysBefore;
import com.tmj.tms.utility.ResponseObject;
import com.tmj.tms.utility.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/finance/expenseVoucher")
public class ExpenseVoucherController {
    private final ModuleService moduleService;
    private final BankService bankService;
    private final StakeholderService stakeholderService;
    private final StakeholderTypeService stakeholderTypeService;
    private final ReportUploadService reportUploadService;
    private final ExchangeRateService exchangeRateService;
    private final FinancialChargeService financialChargeService;
    private final AddressBookService addressBookService;
    private final ReportService reportService;
    private final ExpenseVoucherControllerManager expenseVoucherControllerManager;
    private final CompanyModuleService companyModuleService;
    private final TransportBookingService transportBookingService;
    private final TransportBookingStatusService transportBookingStatusService;
    private final DocumentLinkService documentLinkService;
    private final ExpenseVoucherService expenseVoucherService;
    private final HttpSession httpSession;

    @Autowired
    public ExpenseVoucherController(ModuleService moduleService,
                                    BankService bankService,
                                    StakeholderService stakeholderService,
                                    StakeholderTypeService stakeholderTypeService,
                                    ReportUploadService reportUploadService,
                                    ExchangeRateService exchangeRateService,
                                    FinancialChargeService financialChargeService,
                                    AddressBookService addressBookService,
                                    ReportService reportService,
                                    ExpenseVoucherControllerManager expenseVoucherControllerManager,
                                    CompanyModuleService companyModuleService,
                                    TransportBookingService transportBookingService,
                                    TransportBookingStatusService transportBookingStatusService,
                                    DocumentLinkService documentLinkService,
                                    ExpenseVoucherService expenseVoucherService,
                                    HttpSession httpSession) {
        this.moduleService = moduleService;
        this.bankService = bankService;
        this.stakeholderService = stakeholderService;
        this.stakeholderTypeService = stakeholderTypeService;
        this.reportUploadService = reportUploadService;
        this.exchangeRateService = exchangeRateService;
        this.financialChargeService = financialChargeService;
        this.addressBookService = addressBookService;
        this.reportService = reportService;
        this.expenseVoucherControllerManager = expenseVoucherControllerManager;
        this.companyModuleService = companyModuleService;
        this.transportBookingService = transportBookingService;
        this.transportBookingStatusService = transportBookingStatusService;
        this.documentLinkService = documentLinkService;
        this.expenseVoucherService = expenseVoucherService;
        this.httpSession = httpSession;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@expenseVoucher_VIEW')")
    public String getPage(Model model, Principal principal) {
        this.pageLoad(model, principal);
        return "finance/expenseVoucher";
    }

    @RequestMapping(params = "financialChargeSeq", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@expenseVoucher_VIEW')")
    public String loadPageFromHouseBlSeq(@RequestParam("financialChargeSeq") Integer financialChargeSeq,
                                         Model model, Principal principal) {
        this.pageLoad(model, principal);
        FinancialCharge financialCharge = this.financialChargeService.findByFinancialChargeSeqAndStatusNot(financialChargeSeq, MasterDataStatus.DELETED.getStatusSeq());
        this.initializeFromFinancialChargeSeq(financialCharge, model);
        return "finance/expenseVoucher";
    }

    @RequestMapping(value = "/calculate", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@expenseVoucher_CREATE')")
    public
    @ResponseBody
    ResponseObject calculate(@ModelAttribute ExpenseVoucher expenseVoucher, HttpServletRequest request) {
        return this.expenseVoucherControllerManager.calculate(expenseVoucher, request);
    }

    @RequestMapping(value = "/createExpenseVoucher", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@expenseVoucher_CREATE')")
    public
    @ResponseBody
    ResponseObject createExpenseVoucher(@ModelAttribute ExpenseVoucher expenseVoucher, HttpServletRequest request) {
        return this.expenseVoucherControllerManager.createExpenseVoucher(expenseVoucher, request);
    }


    @RequestMapping(value = "/searchExchangeRateBankData", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@expenseVoucher_VIEW')")
    public String searchExchangeRateBankData(@RequestParam(value = "moduleSeq") Integer moduleSeq,
                                             @RequestParam(value = "exchangeRateSourceType") Integer exchangeRateSourceType,
                                             @RequestParam(value = "sourceBankSeq") Integer sourceBankSeq,
                                             Model model) {
        model.addAttribute("exchangeRateBankList", this.expenseVoucherControllerManager.searchExchangeRateBank(moduleSeq, exchangeRateSourceType, sourceBankSeq));
        return "finance/content/expenseVoucherExchangeRateBankData";
    }

    @RequestMapping(value = "/searchExchangeRate", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@expenseVoucher_VIEW')")
    public String searchExchangeRate(@RequestParam(value = "exchangeRateSeq", required = false) Integer exchangeRateSeq,
                                     Model model) {
        model.addAttribute("exchangeRateDetailList", this.expenseVoucherControllerManager.searchExchangeRate(exchangeRateSeq));
        return "finance/content/commonExchangeRateData";
    }

    @RequestMapping(value = "/searchExpenseVoucherTransportBooking", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@expenseVoucher_VIEW')")
    public String searchExpenseVoucherHouseAwb(@RequestParam(value = "moduleSeq") Integer moduleSeq,
                                               @RequestParam(value = "targetType") Integer targetType,
                                               @RequestParam(value = "transportBookingSeq") Integer transportBookingSeq,
                                               Model model) {
        model.addAttribute("financialCharge", this.expenseVoucherControllerManager.searchTransportBookingChargesForExpenseVoucher(moduleSeq, targetType, transportBookingSeq));
        return "finance/content/expenseVoucherTransportBookingData";
    }

    @RequestMapping(value = "/searchExpenseVoucherFinancialChargeData", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@expenseVoucher_VIEW')")
    public String searchExpenseVoucherFinancialChargeData(@RequestParam(value = "moduleSeq") Integer moduleSeq,
                                                          @RequestParam(value = "financialChargeSeq") Integer financialChargeSeq,
                                                          @RequestParam(value = "exchangeRateSeq") Integer exchangeRateSeq,
                                                          @RequestParam(value = "baseCurrencySeq") Integer baseCurrencySeq,
                                                          Model model) {
        model.addAttribute("financialChargeDetailList", this.expenseVoucherControllerManager.searchExpenseVoucherFinancialCharge(moduleSeq, financialChargeSeq, exchangeRateSeq, baseCurrencySeq));
        model.addAttribute("taxRegistrationVatList", this.expenseVoucherControllerManager.getVatTaxList());
        model.addAttribute("taxRegistrationOtherList", this.expenseVoucherControllerManager.getOtherTaxList());
        return "finance/content/expenseVoucherFinancialChargeData";
    }

    @RequestMapping(value = "/getCurrency/{exchangeRateSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@expenseVoucher_VIEW')")
    public
    @ResponseBody
    List<Currency> getCurrency(@PathVariable("exchangeRateSeq") Integer exchangeRateSeq) {
        return this.expenseVoucherControllerManager.getCurrency(exchangeRateSeq);
    }

    @RequestMapping(value = "/findByExchangeRateSeq/{exchangeRateSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@expenseVoucher_VIEW')")
    public
    @ResponseBody
    ExchangeRate findByExchangeRateSeq(@PathVariable("exchangeRateSeq") Integer exchangeRateSeq) {
        return this.exchangeRateService.findOne(exchangeRateSeq);
    }

    @RequestMapping(value = "/findStakeholder", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@expenseVoucher_VIEW')")
    public
    @ResponseBody
    List<Stakeholder> findStakeholder(@RequestParam("q") String searchParam) {
        return this.stakeholderService.getStakeholderDetails(searchParam,
                this.stakeholderTypeService.findByStakeholderTypeCodeAndStatus("TRANSPORT_COMPANY", MasterDataStatus.APPROVED.getStatusSeq()).getStakeholderTypeSeq(),
                Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString()),
                MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/getStakeholder/{stakeholderSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@expenseVoucher_VIEW')")
    public
    @ResponseBody
    Stakeholder getStakeholder(@PathVariable("stakeholderSeq") Integer stakeholderSeq) {
        return this.stakeholderService.findOne(stakeholderSeq);
    }

    @RequestMapping(value = "/findTransportBooking", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@expenseVoucher_VIEW')")
    public
    @ResponseBody
    List<TransportBooking> findTransportBooking(@RequestParam("q") Integer transportBookingSeq,
                                                @RequestParam("s") Integer moduleSeq,
                                                @RequestParam("r") Integer targetType) {
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        if (targetType.equals(TargetType.TRANSPORT_JOB.getTargetType())) {
            List<Integer> statusSeqList = this.transportBookingStatusService.findByInvoiceable(YesOrNo.Yes.getYesOrNoSeq()).stream().map(TransportBookingStatus::getCurrentStatus).collect(Collectors.toList());
            return this.transportBookingService.findByTransportBookingSeqAndCompanyProfileSeqAndModuleSeqAndCurrentStatusIn(transportBookingSeq, companyProfileSeq,
                    moduleSeq, statusSeqList, EntityGraphUtils.fromName("TransportBooking.expenseVoucher"));
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/getAddressBookByStakeholderSeq/{stakeholderSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@expenseVoucher_VIEW')")
    public
    @ResponseBody
    AddressBook getAddressBookByStakeholderSeq(@PathVariable("stakeholderSeq") Integer stakeholderSeq) {
        Stakeholder dbStakeholder = this.stakeholderService.findOne(stakeholderSeq);
        return this.addressBookService.findOne(dbStakeholder.getAddressBookSeq());
    }

    @RequestMapping(value = "/getReportList/{moduleSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@expenseVoucher_VIEW')")
    public
    @ResponseBody
    List<Report> getReportList(@PathVariable("moduleSeq") Integer moduleSeq) {
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        DocumentLink documentLink = this.documentLinkService.findByLinkName("expenseVoucher");
        return this.reportService.findByDocumentLinkSeqAndModuleSeqAndCompanyProfileSeqAndStatusOrderByReportName(documentLink.getDocumentLinkSeq(), moduleSeq, companyProfileSeq, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/generateExpenseVoucherReport", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@expenseVoucher_VIEW')")
    public
    @ResponseBody
    Integer processReport(@RequestParam("expenseVoucherSeq") Integer expenseVoucherSeq,
                          @RequestParam("reportSeq") Integer reportSeq,
                          @RequestParam(value = "pdf", required = false) Integer pdf,
                          @RequestParam(value = "rtf", required = false) Integer rtf,
                          @RequestParam(value = "xls", required = false) Integer xls,
                          HttpServletResponse httpServletResponse,
                          HttpServletRequest httpServletRequest,
                          Principal principal) {
        return this.expenseVoucherControllerManager.getExpenseVoucherReport(expenseVoucherSeq, reportSeq, pdf, rtf, xls, httpServletRequest, httpServletResponse, principal);
    }

    @RequestMapping(value = "/getFile/{reportUploadSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@expenseVoucher_VIEW')")
    public ResponseEntity<byte[]> getLogo(@PathVariable Integer reportUploadSeq) throws IOException {
        return this.reportUploadService.findAndDownload(reportUploadSeq);
    }

    @RequestMapping(value = "/deleteByExpenseVoucherSeq", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@expenseVoucher_VIEW')")
    public
    @ResponseBody
    ResponseObject deleteByExpenseVoucherSeq(@RequestParam("expenseVoucherSeq") Integer expenseVoucherSeq) {
        return this.expenseVoucherControllerManager.deleteExpenseVoucher(expenseVoucherSeq);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/findModuleDepartments/{moduleSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@expenseVoucher_VIEW')")
    public
    @ResponseBody
    List<Department> findModuleDepartments(@PathVariable("moduleSeq") Integer moduleSeq) {
        return (List<Department>) this.httpSession.getAttribute("userDepartmentList" + moduleSeq);
    }

    @RequestMapping(value = "/searchExpenseVoucher", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@expenseVoucher_VIEW')")
    public String searchExpenseVoucher(@ModelAttribute ExpenseVoucherSearchFromAux expenseVoucherSearchFromAux,
                                       Principal principal,
                                       Model model) {
        model.addAttribute("expenseVoucherSearchList", this.expenseVoucherControllerManager.searchExpenseVoucher(expenseVoucherSearchFromAux, principal));
        return "finance/content/expenseVoucherSearchData";
    }

    @RequestMapping(value = "/finalExpenseVoucher", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@expenseVoucher_VIEW')")
    public String finalExpenseVoucher(@RequestParam("expenseVoucherSeq") Integer expenseVoucherSeq,
                                      Model model) {
        ExpenseVoucher expenseVoucher = this.expenseVoucherService.findOne(expenseVoucherSeq);
        if (expenseVoucher.getTargetType().equals(TargetType.TRANSPORT_JOB.getTargetType())) {
            expenseVoucher.setTransportBooking(this.transportBookingService.findOne(expenseVoucher.getReferenceSeq()));
        }
        model.addAttribute("expenseVoucher", expenseVoucher);
        return "finance/content/expenseVoucherFinal";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    public Model pageLoad(Model model, Principal principal) {
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        String username = principal.getName();
        model.addAttribute("sourceTypeList", ExchangeRateSourceType.values());
        model.addAttribute("bankList", bankService.findByStatus(MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("targetTypeList", TargetType.values());
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransaction(Collections.singletonList("ROLE_finance@expenseVoucher_APPROVE")));
        model.addAttribute("startDate", new NDaysBefore().getDateNDaysBeforeCurrentDate(7));
        model.addAttribute("endDate", new NDaysBefore().getDateNDaysAfterCurrentDate(1));
        model.addAttribute("moduleList", this.moduleService.getModuleListByCompanySeqAndUsernameAndFinanceEnabled(companyProfileSeq, username, 1));
        return model;
    }

    private void initializeFromFinancialChargeSeq(FinancialCharge financialCharge, Model model) {
        Integer transport = this.moduleService.findByModuleCode("TRANSPORT").getModuleSeq();
        Integer companyProfileSeq = financialCharge.getCompanyProfileSeq();
        ExpenseVoucherSearchAux expenseVoucherSearchAux = new ExpenseVoucherSearchAux();
        if (financialCharge.getModuleSeq().equals(transport)) {
            TransportBooking transportBooking = this.transportBookingService.findOne(financialCharge.getReferenceSeq());
            Stakeholder supplier = transportBooking.getTransportBookingVehicleList().get(0).getTransportCompany();
            expenseVoucherSearchAux.setModuleSeq(financialCharge.getModuleSeq());
            expenseVoucherSearchAux.setTargetType(financialCharge.getTargetType());
            expenseVoucherSearchAux.setTransportBookingSeq(transportBooking.getTransportBookingSeq());
            expenseVoucherSearchAux.setReferenceSeq(financialCharge.getReferenceSeq());
            expenseVoucherSearchAux.setReferenceType(financialCharge.getReferenceType());
            if (supplier.getStatus().equals(MasterDataStatus.APPROVED.getStatusSeq())) {
                expenseVoucherSearchAux.setExpenseVoucherPartySeq(supplier.getStakeholderSeq());
                expenseVoucherSearchAux.setExpenseVoucherParty(supplier);
                expenseVoucherSearchAux.setStakeholderAddress(AddressBookUtils.cleanAddressBook(supplier.getAddressBook()));
            }
            expenseVoucherSearchAux.setExchangeRateSourceType(ExchangeRateSourceType.BANK.getExchangeRateSourceType());
            CompanyModule companyModule = this.companyModuleService.findByCompanyProfileSeqAndModuleSeq(companyProfileSeq, financialCharge.getModuleSeq());
            expenseVoucherSearchAux.setSourceBankSeq(companyModule.getDefaultBankSeq());
            expenseVoucherSearchAux.setTransportBooking(transportBooking);
        }
        model.addAttribute("expenseVoucherSearchAux", expenseVoucherSearchAux);
    }

}

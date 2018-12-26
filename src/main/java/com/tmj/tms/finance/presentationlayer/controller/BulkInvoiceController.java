package com.tmj.tms.finance.presentationlayer.controller;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.tmj.tms.config.datalayer.modal.*;
import com.tmj.tms.config.datalayer.service.*;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.bussinesslayer.manager.BulkInvoiceControllerManager;
import com.tmj.tms.finance.datalayer.modal.BulkInvoice;
import com.tmj.tms.finance.datalayer.modal.ExchangeRate;
import com.tmj.tms.finance.datalayer.modal.auxiliary.BulkInvoiceSearchAux;
import com.tmj.tms.finance.datalayer.service.BulkInvoiceService;
import com.tmj.tms.finance.datalayer.service.ExchangeRateService;
import com.tmj.tms.finance.utility.BulkInvoiceSearchFromAux;
import com.tmj.tms.finance.utility.ExchangeRateSourceType;
import com.tmj.tms.finance.utility.TargetType;
import com.tmj.tms.fleet.datalayer.modal.Vehicle;
import com.tmj.tms.fleet.datalayer.service.VehicleService;
import com.tmj.tms.master.datalayer.modal.AddressBook;
import com.tmj.tms.master.datalayer.modal.Currency;
import com.tmj.tms.master.datalayer.modal.FinalDestination;
import com.tmj.tms.master.datalayer.modal.Stakeholder;
import com.tmj.tms.master.datalayer.service.*;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.transport.datalayer.modal.TransportBookingStatus;
import com.tmj.tms.transport.datalayer.service.TransportBookingService;
import com.tmj.tms.transport.datalayer.service.TransportBookingStatusService;
import com.tmj.tms.transport.utility.DateFilterType;
import com.tmj.tms.utility.DateFormatter;
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
@RequestMapping("/finance/bulkInvoice")
public class BulkInvoiceController {
    private final ModuleService moduleService;
    private final BankService bankService;
    private final StakeholderService stakeholderService;
    private final StakeholderTypeService stakeholderTypeService;
    private final ReportUploadService reportUploadService;
    private final ExchangeRateService exchangeRateService;
    private final AddressBookService addressBookService;
    private final ReportService reportService;
    private final BulkInvoiceControllerManager bulkInvoiceControllerManager;
    private final CompanyModuleService companyModuleService;
    private final TransportBookingService transportBookingService;
    private final TransportBookingStatusService transportBookingStatusService;
    private final DocumentLinkService documentLinkService;
    private final BulkInvoiceService bulkInvoiceService;
    private final HttpSession httpSession;
    private final CompanyProfileService companyProfileService;
    private final VehicleService vehicleService;
    private final FinalDestinationService finalDestinationService;

    @Autowired
    public BulkInvoiceController(ModuleService moduleService,
                                 BankService bankService,
                                 StakeholderService stakeholderService,
                                 StakeholderTypeService stakeholderTypeService,
                                 ReportUploadService reportUploadService,
                                 ExchangeRateService exchangeRateService,
                                 AddressBookService addressBookService,
                                 ReportService reportService,
                                 BulkInvoiceControllerManager bulkInvoiceControllerManager,
                                 CompanyModuleService companyModuleService,
                                 TransportBookingService transportBookingService,
                                 TransportBookingStatusService transportBookingStatusService,
                                 DocumentLinkService documentLinkService,
                                 BulkInvoiceService bulkInvoiceService,
                                 HttpSession httpSession,
                                 CompanyProfileService companyProfileService,
                                 VehicleService vehicleService,
                                 FinalDestinationService finalDestinationService) {
        this.moduleService = moduleService;
        this.bankService = bankService;
        this.stakeholderService = stakeholderService;
        this.stakeholderTypeService = stakeholderTypeService;
        this.reportUploadService = reportUploadService;
        this.exchangeRateService = exchangeRateService;
        this.addressBookService = addressBookService;
        this.reportService = reportService;
        this.bulkInvoiceControllerManager = bulkInvoiceControllerManager;
        this.companyModuleService = companyModuleService;
        this.transportBookingService = transportBookingService;
        this.transportBookingStatusService = transportBookingStatusService;
        this.documentLinkService = documentLinkService;
        this.bulkInvoiceService = bulkInvoiceService;
        this.httpSession = httpSession;
        this.companyProfileService = companyProfileService;
        this.vehicleService = vehicleService;
        this.finalDestinationService = finalDestinationService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@bulkInvoice_VIEW')")
    public String getPage(Model model, Principal principal) {
        this.pageLoad(model, principal);
        return "finance/bulkInvoice";
    }

    @RequestMapping(value = "/calculate", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@bulkInvoice_CREATE')")
    public
    @ResponseBody
    ResponseObject calculate(@ModelAttribute BulkInvoiceSearchAux bulkInvoiceSearchAux, HttpServletRequest request) {
        return this.bulkInvoiceControllerManager.calculate(bulkInvoiceSearchAux, request);
    }

    @RequestMapping(value = "/createBulkInvoice", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@bulkInvoice_CREATE')")
    public
    @ResponseBody
    ResponseObject createBulkInvoice(@ModelAttribute BulkInvoiceSearchAux bulkInvoiceSearchAux, HttpServletRequest request) {
        return this.bulkInvoiceControllerManager.createBulkInvoice(bulkInvoiceSearchAux, request);
    }


    @RequestMapping(value = "/searchExchangeRateBankData", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@bulkInvoice_VIEW')")
    public String searchExchangeRateBankData(@RequestParam(value = "moduleSeq") Integer moduleSeq,
                                             @RequestParam(value = "exchangeRateSourceType") Integer exchangeRateSourceType,
                                             @RequestParam(value = "sourceBankSeq") Integer sourceBankSeq,
                                             Model model) {
        model.addAttribute("exchangeRateBankList", this.bulkInvoiceControllerManager.searchExchangeRateBank(moduleSeq, exchangeRateSourceType, sourceBankSeq));
        return "finance/content/localInvoiceExchangeRateBankData";
    }

    @RequestMapping(value = "/searchExchangeRate", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@bulkInvoice_VIEW')")
    public String searchExchangeRate(@RequestParam(value = "exchangeRateSeq", required = false) Integer exchangeRateSeq,
                                     Model model) {
        model.addAttribute("exchangeRateDetailList", this.bulkInvoiceControllerManager.searchExchangeRate(exchangeRateSeq));
        return "finance/content/commonExchangeRateData";
    }

    @RequestMapping(value = "/searchBulkInvoiceTransportBooking", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@bulkInvoice_VIEW')")
    public String searchBulkInvoiceTransportBooking(@ModelAttribute BulkInvoiceSearchAux bulkInvoiceSearchAux,
                                                    Model model) {
        model.addAttribute("financialChargeList", this.bulkInvoiceControllerManager.searchTransportBookingChargesForBulkInvoice(bulkInvoiceSearchAux));
        model.addAttribute("bulkInvoiceSearchAux", bulkInvoiceSearchAux);
        return "finance/content/bulkInvoiceTransportBookingData";
    }

    @RequestMapping(value = "/findCustomer", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@bulkInvoice_VIEW')")
    public
    @ResponseBody
    List<Stakeholder> findCustomer(@RequestParam("q") String searchParam) {
        return this.stakeholderService.getStakeholderDetails(searchParam,
                this.stakeholderTypeService.findByStakeholderTypeCodeAndStatus("CUSTOMER", MasterDataStatus.APPROVED.getStatusSeq()).getStakeholderTypeSeq(),
                Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString()),
                MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findShipper", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@bulkInvoice_VIEW')")
    public
    @ResponseBody
    List<Stakeholder> findShipper(@RequestParam("q") String searchParam) {
        return this.stakeholderService.getStakeholderDetails(searchParam,
                this.stakeholderTypeService.findByStakeholderTypeCodeAndStatus("SHIPPER", MasterDataStatus.APPROVED.getStatusSeq()).getStakeholderTypeSeq(),
                Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString()),
                MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findVehicle", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@vehicleAssignment_VIEW')")
    public
    @ResponseBody
    List<Vehicle> findVehicle(@RequestParam("q") String searchParam) {
        return this.vehicleService.findByVehicleNoContainingIgnoreCaseAndStatusAndCompanyProfileSeq(searchParam,
                MasterDataStatus.APPROVED.getStatusSeq(), Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString()));
    }

    @RequestMapping(value = "/loadSummedCharge", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@bulkInvoice_VIEW')")
    public String loadSummedCharge(@ModelAttribute BulkInvoiceSearchAux bulkInvoiceSearchAux,
                                   Model model) {
        model.addAttribute("summedChargeList", this.bulkInvoiceControllerManager.searchBulkInvoiceFinancialCharge(bulkInvoiceSearchAux));
        model.addAttribute("taxRegistrationVatList", this.bulkInvoiceControllerManager.getVatTaxList());
        model.addAttribute("taxRegistrationOtherList", this.bulkInvoiceControllerManager.getOtherTaxList());
        return "finance/content/bulkInvoiceFinancialChargeData";
    }

    @RequestMapping(value = "/getCurrency/{exchangeRateSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@bulkInvoice_VIEW')")
    public
    @ResponseBody
    List<Currency> getCurrency(@PathVariable("exchangeRateSeq") Integer exchangeRateSeq) {
        return this.bulkInvoiceControllerManager.getCurrency(exchangeRateSeq);
    }

    @RequestMapping(value = "/findByExchangeRateSeq/{exchangeRateSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@bulkInvoice_VIEW')")
    public
    @ResponseBody
    ExchangeRate findByExchangeRateSeq(@PathVariable("exchangeRateSeq") Integer exchangeRateSeq) {
        return this.exchangeRateService.findOne(exchangeRateSeq);
    }

    @RequestMapping(value = "/findStakeholder", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@bulkInvoice_VIEW')")
    public
    @ResponseBody
    List<Stakeholder> findStakeholder(@RequestParam("q") String searchParam) {
        return this.stakeholderService.getStakeholderDetails(searchParam,
                this.stakeholderTypeService.findByStakeholderTypeCodeAndStatus("CUSTOMER", MasterDataStatus.APPROVED.getStatusSeq()).getStakeholderTypeSeq(),
                Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString()),
                MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/getStakeholder/{stakeholderSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@bulkInvoice_VIEW')")
    public
    @ResponseBody
    Stakeholder getStakeholder(@PathVariable("stakeholderSeq") Integer stakeholderSeq) {
        return this.stakeholderService.findOne(stakeholderSeq);
    }

    @RequestMapping(value = "/findTransportBooking", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@bulkInvoice_VIEW')")
    public
    @ResponseBody
    List<TransportBooking> findTransportBooking(@RequestParam("q") Integer transportBookingSeq,
                                                @RequestParam("s") Integer moduleSeq,
                                                @RequestParam("r") Integer targetType) {
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        List<TransportBooking> transportBookingList = null;
        if (targetType.equals(TargetType.TRANSPORT_JOB.getTargetType())) {
            List<Integer> statusSeqList = this.transportBookingStatusService.findByInvoiceable(YesOrNo.Yes.getYesOrNoSeq()).stream().map(TransportBookingStatus::getCurrentStatus).collect(Collectors.toList());
            transportBookingList = this.transportBookingService.findByTransportBookingSeqAndCompanyProfileSeqAndModuleSeqAndCurrentStatusIn(transportBookingSeq, companyProfileSeq,
                    moduleSeq, statusSeqList, EntityGraphUtils.fromName("TransportBooking.bulkInvoice"));
        }
        return transportBookingList;
    }

    @RequestMapping(value = "/getAddressBookByStakeholderSeq/{stakeholderSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@bulkInvoice_VIEW')")
    public
    @ResponseBody
    AddressBook getAddressBookByStakeholderSeq(@PathVariable("stakeholderSeq") Integer stakeholderSeq) {
        Stakeholder dbStakeholder = this.stakeholderService.findOne(stakeholderSeq);
        return this.addressBookService.findOne(dbStakeholder.getAddressBookSeq());
    }

    @RequestMapping(value = "/getReportList/{moduleSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@bulkInvoice_VIEW')")
    public
    @ResponseBody
    List<Report> getReportList(@PathVariable("moduleSeq") Integer moduleSeq) {
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        DocumentLink documentLink = this.documentLinkService.findByLinkName("bulkInvoice");
        return this.reportService.findByDocumentLinkSeqAndModuleSeqAndCompanyProfileSeqAndStatusOrderByReportName(documentLink.getDocumentLinkSeq(), moduleSeq, companyProfileSeq, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/generateBulkInvoiceReport", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@bulkInvoice_VIEW')")
    public
    @ResponseBody
    Integer processReport(@RequestParam("bulkInvoiceSeq") Integer bulkInvoiceSeq,
                          @RequestParam("reportSeq") Integer reportSeq,
                          @RequestParam(value = "pdf", required = false) Integer pdf,
                          @RequestParam(value = "rtf", required = false) Integer rtf,
                          @RequestParam(value = "xls", required = false) Integer xls,
                          HttpServletResponse httpServletResponse,
                          HttpServletRequest httpServletRequest,
                          Principal principal) {
        return this.bulkInvoiceControllerManager.getBulkInvoiceReport(bulkInvoiceSeq, reportSeq, pdf, rtf, xls, httpServletRequest, httpServletResponse, principal);
    }

    @RequestMapping(value = "/getFile/{reportUploadSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@bulkInvoice_VIEW')")
    public ResponseEntity<byte[]> getLogo(@PathVariable Integer reportUploadSeq) throws IOException {
        return this.reportUploadService.findAndDownload(reportUploadSeq);
    }

    @RequestMapping(value = "/deleteByBulkInvoiceSeq", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@bulkInvoice_VIEW')")
    public
    @ResponseBody
    ResponseObject deleteByBulkInvoiceSeq(@RequestParam("bulkInvoiceSeq") Integer bulkInvoiceSeq) {
        return this.bulkInvoiceControllerManager.deleteBulkInvoice(bulkInvoiceSeq);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/findModuleDepartments/{moduleSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@bulkInvoice_VIEW')")
    public
    @ResponseBody
    List<Department> findModuleDepartments(@PathVariable("moduleSeq") Integer moduleSeq) {
        return (List<Department>) this.httpSession.getAttribute("userDepartmentList" + moduleSeq);
    }

    @RequestMapping(value = "/searchBulkInvoice", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@bulkInvoice_VIEW')")
    public String searchBulkInvoice(@ModelAttribute BulkInvoiceSearchFromAux bulkInvoiceSearchFromAux,
                                    Principal principal,
                                    Model model) {
        model.addAttribute("bulkInvoiceSearchList", this.bulkInvoiceControllerManager.searchBulkInvoice(bulkInvoiceSearchFromAux, principal));
        return "finance/content/bulkInvoiceSearchData";
    }

    @RequestMapping(value = "/finalBulkInvoice", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@bulkInvoice_VIEW')")
    public String finalBulkInvoice(@RequestParam("bulkInvoiceSeq") Integer bulkInvoiceSeq,
                                   Model model) {
        BulkInvoice bulkInvoice = this.bulkInvoiceService.findOne(bulkInvoiceSeq);
        model.addAttribute("bulkInvoice", bulkInvoice);
        return "finance/content/bulkInvoiceFinal";
    }

    @RequestMapping(value = "/findFinalDestination", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@bulkInvoice_VIEW')")
    public
    @ResponseBody
    List<FinalDestination> findFinalDestination(@RequestParam("q") String searchParam) {
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        return this.finalDestinationService.findByDestinationContainingIgnoreCaseAndStatusAndCompanyProfileSeq(searchParam,
                MasterDataStatus.APPROVED.getStatusSeq(),
                companyProfileSeq);
    }

    @RequestMapping(value = "/changeChargeableKm", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@bulkInvoice_VIEW')")
    public
    @ResponseBody
    ResponseObject changeCustomerReferenceNo(@RequestParam(value = "id") Integer transportBookingSeq,
                                             @RequestParam(value = "value") Double chargeableKm) {
        return this.bulkInvoiceControllerManager.changeChargeableKm(transportBookingSeq, chargeableKm);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    public Model pageLoad(Model model, Principal principal) {
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        Integer transport = this.moduleService.findByModuleCode("TRANSPORT").getModuleSeq();
        CompanyProfile companyProfile = this.companyProfileService.findByCompanyProfileSeq(companyProfileSeq);
        String username = principal.getName();
        model.addAttribute("sourceTypeList", ExchangeRateSourceType.values());
        model.addAttribute("bankList", bankService.findByStatus(MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("targetTypeList", TargetType.values());
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransaction(Collections.singletonList("ROLE_finance@bulkInvoice_APPROVE")));
        model.addAttribute("startDate", DateFormatter.getStartOfLastMonth());
        model.addAttribute("endDate", DateFormatter.getEndOfLastMonth());
        model.addAttribute("moduleList", this.moduleService.getModuleListByCompanySeqAndUsernameAndFinanceEnabled(companyProfileSeq, username, 1));
        model.addAttribute("dateFilterTypeList", DateFilterType.values());
        model.addAttribute("departmentList", this.httpSession.getAttribute("userDepartmentList" + transport));
        BulkInvoiceSearchAux bulkInvoiceSearchAux = new BulkInvoiceSearchAux();
        bulkInvoiceSearchAux.setModuleSeq(transport);
        bulkInvoiceSearchAux.setExchangeRateSourceType(ExchangeRateSourceType.BANK.getExchangeRateSourceType());
        bulkInvoiceSearchAux.setTargetType(TargetType.TRANSPORT_JOB.getTargetType());
        bulkInvoiceSearchAux.setCurrencySeq(companyProfile.getLocalCurrencySeq());
        CompanyModule companyModule = this.companyModuleService.findByCompanyProfileSeqAndModuleSeq(companyProfileSeq, transport);
        bulkInvoiceSearchAux.setSourceBankSeq(companyModule.getDefaultBankSeq());
        model.addAttribute("bulkInvoiceSearchAux", bulkInvoiceSearchAux);
        return model;
    }
}

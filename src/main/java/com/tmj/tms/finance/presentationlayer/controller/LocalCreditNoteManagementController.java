package com.tmj.tms.finance.presentationlayer.controller;

import com.tmj.tms.config.datalayer.modal.DocumentLink;
import com.tmj.tms.config.datalayer.service.DocumentLinkService;
import com.tmj.tms.config.datalayer.service.ReportService;
import com.tmj.tms.config.datalayer.service.UploadedDocumentService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.bussinesslayer.manager.LocalCreditNoteManagementControllerManager;
import com.tmj.tms.finance.datalayer.modal.ExpenseVoucher;
import com.tmj.tms.finance.datalayer.modal.LocalCreditNoteHeader;
import com.tmj.tms.finance.datalayer.modal.LocalInvoice;
import com.tmj.tms.finance.datalayer.modal.auxiliary.LocalCreditNoteFinanceChargeDetailForExpVoucher;
import com.tmj.tms.finance.datalayer.modal.auxiliary.LocalCreditNoteFinancialChargeDetail;
import com.tmj.tms.finance.datalayer.service.ExpenseVoucherService;
import com.tmj.tms.finance.datalayer.service.LocalCreditNoteHeaderService;
import com.tmj.tms.finance.datalayer.service.LocalInvoiceService;
import com.tmj.tms.finance.utility.InvoiceType;
import com.tmj.tms.master.datalayer.service.TaxRegistrationService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/finance/localCreditNoteManagement")
public class LocalCreditNoteManagementController {

    private final LocalCreditNoteManagementControllerManager localCreditNoteManagementControllerManager;
    private final LocalInvoiceService localInvoiceService;
    private final TaxRegistrationService taxRegistrationService;
    private final ExpenseVoucherService expenseVoucherService;
    private final LocalCreditNoteHeaderService localCreditNoteHeaderService;
    private final ReportService reportService;
    private final UploadedDocumentService uploadedDocumentService;
    private final DocumentLinkService documentLinkService;

    @Autowired
    public LocalCreditNoteManagementController(LocalCreditNoteManagementControllerManager localCreditNoteManagementControllerManager,
                                               LocalInvoiceService localInvoiceService,
                                               TaxRegistrationService taxRegistrationService,
                                               ExpenseVoucherService expenseVoucherService,
                                               LocalCreditNoteHeaderService localCreditNoteHeaderService,
                                               ReportService reportService,
                                               UploadedDocumentService uploadedDocumentService,
                                               DocumentLinkService documentLinkService) {
        this.localCreditNoteManagementControllerManager = localCreditNoteManagementControllerManager;
        this.localInvoiceService = localInvoiceService;
        this.taxRegistrationService = taxRegistrationService;
        this.expenseVoucherService = expenseVoucherService;
        this.localCreditNoteHeaderService = localCreditNoteHeaderService;
        this.reportService = reportService;
        this.uploadedDocumentService = uploadedDocumentService;
        this.documentLinkService = documentLinkService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@localCreditNoteManagement_VIEW')")
    public String getPage(Model model, HttpServletRequest request) {
        this.pageLoad(model, request);
        return "finance/localCreditNoteManagement";
    }

    @RequestMapping(params = "localCreditNoteHeaderSeq", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@localCreditNoteManagement_VIEW')")
    public String viewPage(@RequestParam(value = "localCreditNoteHeaderSeq") Integer localCreditNoteHeaderSeq,
                           HttpServletRequest request,
                           Model model) {
        model = this.pageLoad(model, request);
        LocalCreditNoteHeader localCreditNoteHeader = this.localCreditNoteHeaderService.findOne(localCreditNoteHeaderSeq);
        model.addAttribute("localCreditNote", localCreditNoteHeader);
        if (localCreditNoteHeader.getLocalInvoiceSeq() != null) {
            List<LocalCreditNoteFinancialChargeDetail> localCreditNoteFinancialChargeDetailList = this.localCreditNoteManagementControllerManager.findLocalCreditNoteChargeDetailForLocalInvoice(localCreditNoteHeader.getLocalInvoiceSeq(), localCreditNoteHeaderSeq);
            model.addAttribute("financialChargeDetailList", localCreditNoteFinancialChargeDetailList);
            model.addAttribute("financialChargeSummaryDetail", this.localCreditNoteManagementControllerManager.getLocalInvoiceTotalCreditNoteSummaryDetails(localCreditNoteFinancialChargeDetailList, localCreditNoteHeaderSeq));
        } else {
            List<LocalCreditNoteFinanceChargeDetailForExpVoucher> localCreditNoteFinanceChargeDetailForExpVoucherList = this.localCreditNoteManagementControllerManager.findLocalCreditNoteChargeDetailForExpenseVoucher(localCreditNoteHeader.getExpenseVoucherSeq(), localCreditNoteHeaderSeq);
            model.addAttribute("financialChargeDetailList", localCreditNoteFinanceChargeDetailForExpVoucherList);
            model.addAttribute("financialChargeSummaryDetail", this.localCreditNoteManagementControllerManager.getLocalInvoiceTotalCreditNoteSummaryDetailsForExpenseVoucher(localCreditNoteFinanceChargeDetailForExpVoucherList, localCreditNoteHeaderSeq));
        }

        return "finance/localCreditNoteManagement";
    }

    @RequestMapping(value = "/createLocalCreditNote", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@localCreditNoteManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject createLocalCreditNote(@ModelAttribute LocalCreditNoteHeader localCreditNoteHeader,
                                         HttpServletRequest request) {
        return this.localCreditNoteManagementControllerManager.saveLocalCreditNote(localCreditNoteHeader, request);
    }

    @RequestMapping(value = "/updateLocalCreditNote", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@localCreditNoteManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateLocalCreditNote(@ModelAttribute LocalCreditNoteHeader localCreditNoteHeader) {
//        return this.localCreditNoteManagementControllerManager.updateLocalCreditNote(localCreditNoteHeader);
        return null;
    }

    @RequestMapping(value = "/deleteLocalCreditNote", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@localCreditNoteManagement_DELETE')")
    public
    @ResponseBody
    ResponseObject deleteLocalCreditNote(@RequestParam("localCreditNoteHeaderSeq") Integer localCreditNoteHeaderSeq,
                                         HttpServletRequest request) {
        return this.localCreditNoteManagementControllerManager.deleteLocalCreditNote(localCreditNoteHeaderSeq, request);
    }

    @RequestMapping(value = "/findInvoiceList", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@localCreditNoteManagement_VIEW')")
    public
    @ResponseBody
    List<LocalInvoice> findInvoiceList(@RequestParam("q") String searchParam,
                                          @RequestParam("s") Integer invoiceTypeSeq,
                                          @RequestParam("r") Integer moduleSeq,
                                          HttpServletRequest request) {
        return this.localCreditNoteManagementControllerManager.findInvoiceList(searchParam, invoiceTypeSeq, moduleSeq, request);
    }

    @RequestMapping(value = "/findExpenseVoucherList", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@localCreditNoteManagement_VIEW')")
    public
    @ResponseBody
    List<ExpenseVoucher> findExpenseVoucherList(@RequestParam("q") String searchParam,
                                                   @RequestParam("s") Integer invoiceTypeSeq,
                                                   @RequestParam("r") Integer moduleSeq,
                                                   HttpServletRequest request) {
        return this.localCreditNoteManagementControllerManager.findExpenseVoucherList(searchParam, invoiceTypeSeq, moduleSeq, request);
    }

    @RequestMapping(value = "/findBasicDetails", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@localCreditNoteManagement_VIEW')")
    public String findBasicDetails(Model model,
                                   @RequestParam(value = "localInvoiceSeq", required = false) Integer localInvoiceSeq,
                                   @RequestParam(value = "expenseVoucherSeq", required = false) Integer expenseVoucherSeq,
                                   @RequestParam(value = "localCreditNoteHeaderSeq", required = false) Integer localCreditNoteHeaderSeq) {
        if (localInvoiceSeq != null) {
            LocalInvoice localInvoice = this.localInvoiceService.findOne(localInvoiceSeq);
            model.addAttribute("localCreditNoteBasicDetails", localInvoice);
            model.addAttribute("creditNoteStatus", "LI");
            model.addAttribute("departmentSeq", localInvoice.getDepartmentSeq());
            if (localCreditNoteHeaderSeq != null) {
                model.addAttribute("localCreditNote", this.localCreditNoteHeaderService.findOne(localCreditNoteHeaderSeq));
            }
        } else if (expenseVoucherSeq != null) {
            ExpenseVoucher expenseVoucher = this.expenseVoucherService.findOne(expenseVoucherSeq);
            model.addAttribute("localCreditNoteBasicDetails", expenseVoucher);
            model.addAttribute("creditNoteStatus", "EV");
            model.addAttribute("departmentSeq", expenseVoucher.getDepartmentSeq());
            if (localCreditNoteHeaderSeq != null) {
                model.addAttribute("localCreditNote", this.localCreditNoteHeaderService.findOne(localCreditNoteHeaderSeq));
            }
        }
        return "finance/content/localCreditNoteBasicData";
    }

    @RequestMapping(value = "/findFinancialChargeDetail", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@localCreditNoteManagement_VIEW')")
    public String findFinancialChargeDetail(Model model,
                                            @RequestParam(value = "localInvoiceSeq", required = false) Integer localInvoiceSeq,
                                            @RequestParam(value = "expenseVoucherSeq", required = false) Integer expenseVoucherSeq,
                                            @RequestParam(value = "localCreditNoteHeaderSeq", required = false) Integer localCreditNoteHeaderSeq,
                                            HttpServletRequest request) {
        Integer companyProfileSeq = Integer.parseInt(request.getSession().getAttribute("companyProfileSeq").toString());
        String pageName = null;
        if (localInvoiceSeq != null) {
            List<LocalCreditNoteFinancialChargeDetail> localCreditNoteFinancialChargeDetailList = this.localCreditNoteManagementControllerManager.findLocalCreditNoteChargeDetailForLocalInvoice(localInvoiceSeq, localCreditNoteHeaderSeq);
            model.addAttribute("financialChargeDetailList", localCreditNoteFinancialChargeDetailList);
            model.addAttribute("financialChargeSummaryDetail", this.localCreditNoteManagementControllerManager.getLocalInvoiceTotalCreditNoteSummaryDetails(localCreditNoteFinancialChargeDetailList.stream().filter(i -> i.getLocalInvoiceFinChrMapSeq() != null).collect(Collectors.toList()), localCreditNoteHeaderSeq));
            pageName = "finance/content/localCreditNoteLocalInvoiceFCData";
        } else if (expenseVoucherSeq != null) {
            List<LocalCreditNoteFinanceChargeDetailForExpVoucher> localCreditNoteFinanceChargeDetailForExpVouchers = this.localCreditNoteManagementControllerManager.findLocalCreditNoteChargeDetailForExpenseVoucher(expenseVoucherSeq, localCreditNoteHeaderSeq);
            model.addAttribute("financialChargeDetailList", localCreditNoteFinanceChargeDetailForExpVouchers);
            model.addAttribute("financialChargeSummaryDetail", this.localCreditNoteManagementControllerManager.getLocalInvoiceTotalCreditNoteSummaryDetailsForExpenseVoucher(localCreditNoteFinanceChargeDetailForExpVouchers.stream().filter(i -> i.getExpenseVouFinChrMapSeq() != null).collect(Collectors.toList()), localCreditNoteHeaderSeq));
            pageName = "finance/content/localCreditNoteExpenseVoucherFCData";
        }
        model.addAttribute("taxRegistrationVatList", this.taxRegistrationService.findByCompanyProfileSeqAndTaxTypeSeqAndStatus(companyProfileSeq, 1, MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("taxRegistrationOtherList", this.taxRegistrationService.findByCompanyProfileSeqAndTaxTypeSeqAndStatus(companyProfileSeq, 2, MasterDataStatus.APPROVED.getStatusSeq()));
        return pageName;
    }

    @RequestMapping(value = "/loadSummaryDetails", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@localCreditNoteManagement_VIEW')")
    public
    @ResponseBody
    ResponseObject loadSummaryDetails(@ModelAttribute LocalCreditNoteHeader localCreditNoteHeader,
                                      HttpServletRequest request) {
        return this.localCreditNoteManagementControllerManager.loadSummaryDetails(localCreditNoteHeader, request);
    }

    @RequestMapping(value = "/findByLocalInvoiceSeq/{localInvoiceSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@localCreditNoteManagement_VIEW')")
    public
    @ResponseBody
    LocalCreditNoteHeader findByLocalInvoiceSeq(@PathVariable("localInvoiceSeq") Integer localInvoiceSeq) {
        return this.localCreditNoteHeaderService.findByLocalInvoiceSeqAndStatusIsNotIn(localInvoiceSeq, MasterDataStatus.DELETED.getStatusSeq());
    }

    @RequestMapping(value = "/findByExpenseVoucherSeq/{expenseVoucherSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@localCreditNoteManagement_VIEW')")
    public
    @ResponseBody
    LocalCreditNoteHeader findByExpenseVoucherSeq(@PathVariable("expenseVoucherSeq") Integer expenseVoucherSeq) {
        return this.localCreditNoteHeaderService.findByExpenseVoucherSeqAndStatusIsNotIn(expenseVoucherSeq, MasterDataStatus.DELETED.getStatusSeq());
    }

    @RequestMapping(value = "/generateLocalCreditNoteReport", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@localCreditNoteManagement_VIEW')")
    public
    @ResponseBody
    Integer processReport(@RequestParam("localCreditNoteHeaderSeq") Integer localCreditNoteHeaderSeq,
                          @RequestParam("reportName") String reportName, HttpServletRequest httpServletRequest,
                          @RequestParam(value = "pdf", required = false) Integer pdf,
                          @RequestParam(value = "rtf", required = false) Integer rtf,
                          @RequestParam(value = "xls", required = false) Integer xls,
                          HttpServletResponse httpServletResponse) {
        return this.localCreditNoteManagementControllerManager.getReport(localCreditNoteHeaderSeq,
                reportName,
                pdf,
                rtf,
                xls,
                httpServletRequest,
                httpServletResponse);
    }

    @RequestMapping(value = "/getFile/{uploadDocumentSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@localCreditNoteManagement_VIEW')")
    public ResponseEntity<byte[]> getLogo(@PathVariable Integer uploadDocumentSeq) throws IOException {
        return this.uploadedDocumentService.findAndDownload(uploadDocumentSeq);
    }

    public Model pageLoad(Model model, HttpServletRequest request) {
        Integer companyProfileSeq = Integer.parseInt(request.getSession().getAttribute("companyProfileSeq").toString());
        String[] statusList = {"ROLE_finance@localCreditNoteManagement_APPROVE"};
        model.addAttribute("invoiceTypeList", InvoiceType.values());
        model.addAttribute("moduleList", request.getSession().getAttribute("userModuleList"));
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransaction(Arrays.asList(statusList)));
        DocumentLink documentLink = this.documentLinkService.findByLinkName("localCreditNoteManagement");
        model.addAttribute("reportList", this.reportService.findByStatusAndDocumentLinkSeqAndCompanyProfileSeq(MasterDataStatus.APPROVED.getStatusSeq(), documentLink.getDocumentLinkSeq(), companyProfileSeq));
        return model;
    }
}

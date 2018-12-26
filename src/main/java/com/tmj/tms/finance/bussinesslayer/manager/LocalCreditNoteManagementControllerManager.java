package com.tmj.tms.finance.bussinesslayer.manager;

import com.tmj.tms.finance.datalayer.modal.ExpenseVoucher;
import com.tmj.tms.finance.datalayer.modal.LocalCreditNoteHeader;
import com.tmj.tms.finance.datalayer.modal.LocalInvoice;
import com.tmj.tms.finance.datalayer.modal.auxiliary.LocalCreditNoteFinanceChargeDetailForExpVoucher;
import com.tmj.tms.finance.datalayer.modal.auxiliary.LocalCreditNoteFinancialChargeDetail;
import com.tmj.tms.finance.datalayer.modal.auxiliary.LocalCreditNoteSummaryDetail;
import com.tmj.tms.utility.ResponseObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface LocalCreditNoteManagementControllerManager {

    ResponseObject saveLocalCreditNote(LocalCreditNoteHeader localCreditNoteHeader, HttpServletRequest request);

    ResponseObject deleteLocalCreditNote(Integer localCreditNoteHeaderSeq, HttpServletRequest request);

    //page loader summary for local invoice
    LocalCreditNoteSummaryDetail getLocalInvoiceTotalCreditNoteSummaryDetails(List<LocalCreditNoteFinancialChargeDetail> localCreditNoteFinancialChargeDetailList, Integer localCreditNoteHeaderSeq);

    //page loader summary for expense voucher
    LocalCreditNoteSummaryDetail getLocalInvoiceTotalCreditNoteSummaryDetailsForExpenseVoucher(List<LocalCreditNoteFinanceChargeDetailForExpVoucher> localCreditNoteFinanceChargeDetailForExpVouchers, Integer localCreditNoteHeaderSeq);

    List<LocalCreditNoteFinancialChargeDetail> findLocalCreditNoteChargeDetailForLocalInvoice(Integer localInvoiceSeq, Integer localCreditNoteHeaderSeq);

    List<LocalCreditNoteFinanceChargeDetailForExpVoucher> findLocalCreditNoteChargeDetailForExpenseVoucher(Integer expenseVoucherSeq, Integer localCreditNoteHeaderSeq);

    ResponseObject loadSummaryDetails(LocalCreditNoteHeader localCreditNoteHeader, HttpServletRequest request);

    Integer getReport(Integer localCreditNoteHeaderSeq, String reportName, Integer pdf, Integer rtf, Integer xls, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    List<LocalInvoice> findInvoiceList(String searchParam, Integer invoiceTypeSeq, Integer moduleSeq, HttpServletRequest request);

    List<ExpenseVoucher> findExpenseVoucherList(String searchParam, Integer invoiceTypeSeq, Integer moduleSeq, HttpServletRequest request);
}

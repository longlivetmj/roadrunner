package com.tmj.tms.finance.bussinesslayer.managerimpl;

import com.querydsl.core.BooleanBuilder;
import com.tmj.tms.finance.bussinesslayer.manager.LocalCreditNoteSearchControllerManager;
import com.tmj.tms.finance.datalayer.modal.LocalCreditNoteHeader;
import com.tmj.tms.finance.datalayer.modal.auxiliary.LocalCreditNoteSearchAux;
import com.tmj.tms.finance.datalayer.service.ExpenseVoucherService;
import com.tmj.tms.finance.datalayer.service.LocalCreditNoteHeaderService;
import com.tmj.tms.finance.datalayer.service.LocalInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocalCreditNoteSearchControllerManagerImpl implements LocalCreditNoteSearchControllerManager {

    private final LocalCreditNoteHeaderService localCreditNoteHeaderService;
    private final LocalInvoiceService localInvoiceService;
    private final ExpenseVoucherService expenseVoucherService;

    @Autowired
    public LocalCreditNoteSearchControllerManagerImpl(LocalCreditNoteHeaderService localCreditNoteHeaderService, LocalInvoiceService localInvoiceService, ExpenseVoucherService expenseVoucherService) {
        this.localCreditNoteHeaderService = localCreditNoteHeaderService;
        this.localInvoiceService = localInvoiceService;
        this.expenseVoucherService = expenseVoucherService;
    }

    @Override
    public List<LocalCreditNoteHeader> searchLocalCreditNoteDetail(LocalCreditNoteSearchAux localCreditNoteSearchAux,
                                                                   HttpServletRequest request) {
        List<LocalCreditNoteHeader> localCreditNoteHeaderList = new ArrayList<>();
        try {
            /*QLocalCreditNoteHeader localCreditNoteHeader = QLocalCreditNoteHeader.localCreditNoteHeader;
            BooleanBuilder builder = new BooleanBuilder();

            if (!creditDebitNo.isEmpty()) {
                builder.and(localCreditNoteHeader.localCreditNoteNo.like("%" + creditDebitNo + "%"));
            }
            if (!expenseVoucherNo.isEmpty()) {
                builder.and(localCreditNoteHeader.expenseVoucher.expenseVoucherNo.like("%" + expenseVoucherNo + "%"));
            }
            if (!localInvoiceNo.isEmpty()) {
                builder.and(localCreditNoteHeader.localInvoice.localInvoiceNo.like("%" + localInvoiceNo + "%"));
            }

            if (invoiceTypeSeq != -1) {
                builder.and(localCreditNoteHeader.invoiceTypeSeq.eq(invoiceTypeSeq));
            }
            if (status != -1) {
                builder.and(localCreditNoteHeader.status.eq(status));
            } else {
                builder.and(localCreditNoteHeader.status.in(MasterDataStatus.getCommonStatusSeqList()));
            }
            if (startDate != null && endDate != null) {
                builder.and(localCreditNoteHeader.createdDate.between(startDate, endDate));
            }
            builder.and(localCreditNoteHeader.companyProfileSeq.eq(Integer.parseInt(request.getSession().getAttribute("companyProfileSeq").toString())));
            localCreditNoteHeaderList = (List<LocalCreditNoteHeader>) this.localCreditNoteHeaderService.findAll(builder);
            for (LocalCreditNoteHeader creditNoteHeader : localCreditNoteHeaderList) {
                if (creditNoteHeader.getLocalInvoiceSeq() != null) {
                    LocalInvoice localInvoice = this.localInvoiceService.findOne(creditNoteHeader.getLocalInvoiceSeq());
                    creditNoteHeader.setLocalInvoice(localInvoice);
                } else if (creditNoteHeader.getExpenseVoucherSeq() != null) {
                    ExpenseVoucher expenseVoucher = this.expenseVoucherService.findOne(creditNoteHeader.getExpenseVoucherSeq());
                    creditNoteHeader.setExpenseVoucher(expenseVoucher);
                }
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        return localCreditNoteHeaderList;
    }
}

package com.tmj.tms.finance.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphQueryDslPredicateExecutor;
import com.tmj.tms.finance.datalayer.modal.LocalCreditNoteHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LocalCreditNoteHeaderService extends JpaRepository<LocalCreditNoteHeader, Integer>, JpaEntityGraphQueryDslPredicateExecutor<LocalCreditNoteHeader> {

    LocalCreditNoteHeader findByLocalInvoiceSeqAndStatusIsNotIn(Integer localInvoiceSeq, Integer statusSeq);

    LocalCreditNoteHeader findByExpenseVoucherSeqAndStatusIsNotIn(Integer localInvoiceSeq, Integer statusSeq);

    List<LocalCreditNoteHeader> findByLocalInvoiceSeqAndStatusNotIn(Integer localInvoiceSeq, Integer status);

    List<LocalCreditNoteHeader> findByExpenseVoucherSeqAndStatusNotIn(Integer expenseVoucherSeq, Integer status);

    List<LocalCreditNoteHeader> findByFinanceIntegrationAndInvoiceTypeSeq(Integer financeIntegrationStatus, Integer invoiceType);

    List<LocalCreditNoteHeader> findByLocalInvoiceSeqInAndInvoiceTypeSeqAndStatusNot(List<Integer> localInvoiceSeqList, Integer invoiceTypeSeq, Integer status);

    List<LocalCreditNoteHeader> findByCreatedDateIsAfterAndStatusNot(Date createdDate, Integer status);

}

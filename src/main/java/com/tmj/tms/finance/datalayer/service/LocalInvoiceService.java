package com.tmj.tms.finance.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.finance.datalayer.modal.LocalInvoice;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalInvoiceService extends JpaEntityGraphRepository<LocalInvoice, Integer>, QueryDslPredicateExecutor<LocalInvoice> {
    List<LocalInvoice> findByFinancialChargeSeqAndStatusNot(Integer financialChargeSeq, Integer statusSeq);

    List<LocalInvoice> findByCompanyProfileSeqAndStatusAndModuleSeqAndReferenceSeq(Integer companyProfileSeq, Integer statusSeq, Integer moduleSeq, Integer transportBookingSeq);

    List<LocalInvoice> findByCompanyProfileSeqAndFinanceIntegrationIsNull(Integer companyProfileSeq);

    List<LocalInvoice> findFirst100ByCompanyProfileSeqAndFinanceIntegrationIsNull(Integer companyProfileSeq);

    List<LocalInvoice> findFirst100ByCompanyProfileSeqAndFinanceIntegrationIsNullAndStatusOrderByLocalInvoiceNo(Integer companyProfileSeq, Integer statusSeq);

    List<LocalInvoice> findByCompanyProfileSeqAndFinanceIntegrationIsNullAndStatusOrderByLocalInvoiceNo(Integer companyProfileSeq, Integer statusSeq);
}

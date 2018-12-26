package com.tmj.tms.finance.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.finance.datalayer.modal.BulkInvoice;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BulkInvoiceService extends JpaEntityGraphRepository<BulkInvoice, Integer>, QueryDslPredicateExecutor<BulkInvoice> {

    List<BulkInvoice> findByCompanyProfileSeqAndFinanceIntegrationIsNull(Integer companyProfileSeq);

    List<BulkInvoice> findByCompanyProfileSeqAndFinanceIntegrationIsNullAndStatus(Integer companyProfileSeq, Integer statusSeq);
}

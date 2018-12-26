package com.tmj.tms.finance.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.finance.datalayer.modal.LocalInvoiceChargeDetail;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalInvoiceChargeDetailService extends JpaEntityGraphRepository<LocalInvoiceChargeDetail, Integer>, QueryDslPredicateExecutor<LocalInvoiceChargeDetail> {
}

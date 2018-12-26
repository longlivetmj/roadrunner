package com.tmj.tms.finance.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.finance.datalayer.modal.ExpenseVoucherChargeDetail;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseVoucherChargeDetailService extends JpaEntityGraphRepository<ExpenseVoucherChargeDetail, Integer>, QueryDslPredicateExecutor<ExpenseVoucherChargeDetail> {
}

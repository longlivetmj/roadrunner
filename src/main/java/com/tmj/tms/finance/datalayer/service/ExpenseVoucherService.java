package com.tmj.tms.finance.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.finance.datalayer.modal.ExpenseVoucher;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseVoucherService extends JpaEntityGraphRepository<ExpenseVoucher, Integer>, QueryDslPredicateExecutor<ExpenseVoucher> {
    List<ExpenseVoucher> findByFinancialChargeSeqAndStatusNot(Integer financialChargeSeq, Integer statusSeq);

    List<ExpenseVoucher> findByCompanyProfileSeqAndStatusAndModuleSeqAndReferenceSeq(Integer companyProfileSeq, Integer statusSeq, Integer moduleSeq, Integer transportBookingSeq);

    List<ExpenseVoucher> findFirst100ByCompanyProfileSeqAndFinanceIntegrationIsNullAndStatusOrderByExpenseVoucherNo(Integer companyProfileSeq, Integer statusSeq);
}

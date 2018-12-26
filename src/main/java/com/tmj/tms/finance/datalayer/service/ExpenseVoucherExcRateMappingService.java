package com.tmj.tms.finance.datalayer.service;

import com.tmj.tms.finance.datalayer.modal.ExpenseVoucherExcRateMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseVoucherExcRateMappingService extends JpaRepository<ExpenseVoucherExcRateMapping, Integer> {

    List<ExpenseVoucherExcRateMapping> findByExpenseVoucherSeq(Integer expenseVoucherSeq);

    ExpenseVoucherExcRateMapping findByExpenseVoucherSeqAndExchangeRateDetailCurrencySeq(Integer expenseVoucherSeq, Integer currencySeq);

    ExpenseVoucherExcRateMapping findByExpenseVoucherSeqAndCurrencySeq(Integer expenseVoucherSeq, Integer currencySeq);

}

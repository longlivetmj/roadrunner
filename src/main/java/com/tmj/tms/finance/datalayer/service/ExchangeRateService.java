package com.tmj.tms.finance.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphQueryDslPredicateExecutor;
import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.finance.datalayer.modal.ExchangeRate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeRateService extends JpaEntityGraphRepository<ExchangeRate, Integer>, JpaEntityGraphQueryDslPredicateExecutor<ExchangeRate> {

    List<ExchangeRate> findDistinctByBankSeqAndModuleSeqAndCompanyProfileSeqAndStatusOrderByExchangeRateSeqAsc(Integer bankSeq, Integer moduleSeq, Integer companyProfileSeq, Integer status);

    List<ExchangeRate> findByStatus(Integer status);

    ExchangeRate findFirstByStatusAndCompanyProfileSeq(Integer statusSeq, Integer companyProfileSeq);
}

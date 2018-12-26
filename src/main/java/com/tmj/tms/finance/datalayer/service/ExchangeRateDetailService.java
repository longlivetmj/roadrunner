package com.tmj.tms.finance.datalayer.service;

import com.tmj.tms.finance.datalayer.modal.ExchangeRateDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ExchangeRateDetailService extends JpaRepository<ExchangeRateDetail, Integer> {

    List<ExchangeRateDetail> findByStatus(Integer status);

    List<ExchangeRateDetail> findByStatusIn(Collection<Integer> statusSeqList);

    ExchangeRateDetail findByExchangeRateSeqAndCurrencySeq(Integer exchangeRateSeq, Integer currencySeq);

    List<ExchangeRateDetail> findByExchangeRateSeq(Integer exchangeRateSeq);
}

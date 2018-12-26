package com.tmj.tms.finance.datalayer.service;

import com.tmj.tms.finance.datalayer.modal.LocalInvoiceExcRateMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalInvoiceExcRateMappingService extends JpaRepository<LocalInvoiceExcRateMapping, Integer> {
    List<LocalInvoiceExcRateMapping> findByLocalInvoiceSeq(Integer localInvoiceSeq);

    LocalInvoiceExcRateMapping findByLocalInvoiceSeqAndExchangeRateDetailCurrencySeq(Integer localInvoiceSeq, Integer currencySeq);

    LocalInvoiceExcRateMapping findByLocalInvoiceSeqAndCurrencySeq(Integer localInvoiceSeq, Integer currencySeq);

}

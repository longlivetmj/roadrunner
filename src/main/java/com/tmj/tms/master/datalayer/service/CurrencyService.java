package com.tmj.tms.master.datalayer.service;

import com.tmj.tms.master.datalayer.modal.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CurrencyService extends JpaRepository<Currency, Integer> {


    List<Currency> findByCurrencyCodeStartsWithIgnoreCaseAndStatus(String currencyCode, Integer status);

    List<Currency> findByCurrencyNameStartsWithIgnoreCaseAndStatus(String currencyName, Integer status);

    Currency findByCurrencyCodeIgnoreCase(String currencyCode);

    List<Currency> findByStatus(Integer status);

    List<Currency> findByStatusIn(Collection<Integer> status);

    List<Currency> findByCurrencyCodeContainingIgnoreCaseAndCurrencyNameContainingIgnoreCase(String currencyCode, String currencyName);

    List<Currency> findByCurrencySeqIn(Collection<Integer> currecnySeq);

    Currency findByCurrencySeq(Integer currencySeq);
}

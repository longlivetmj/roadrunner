package com.tmj.tms.master.datalayer.service;

import com.tmj.tms.master.datalayer.modal.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BankService extends JpaRepository<Bank, Integer> {

    List<Bank> findByBankNameContainingIgnoreCaseAndStatusIn(String bankName, Collection<Integer> statusSeqList);

    List<Bank> findByBankNameStartsWithIgnoreCaseAndStatus(String bankName, Integer statusSeqList);

    List<Bank> findByBankCodeContainingIgnoreCaseAndStatusIn(String bankCode, Collection<Integer> statusSeqList);

    List<Bank> findByBankNameContainingIgnoreCaseAndBankCodeContainingIgnoreCaseAndStatusIn(String bankName, String bankCode, Collection<Integer> statusSeqList);

    List<Bank> findByStatusIn(Collection<Integer> statusSeqList);

    List<Bank> findByStatus(Integer status);
}

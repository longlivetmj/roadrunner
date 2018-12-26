package com.tmj.tms.finance.datalayer.service;

import com.tmj.tms.finance.datalayer.modal.FinancialChargeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface FinancialChargeDetailService extends JpaRepository<FinancialChargeDetail, Integer>, QueryDslPredicateExecutor {

    List<FinancialChargeDetail> findByFinancialChargeSeqAndChargeTypeAndLiStatusAndStatusNot(Integer financialChargeSeq, Integer chargeType, Integer yesOrNoSeq, Integer statusSeq);

    List<FinancialChargeDetail> findByFinancialChargeDetailSeqIn(List<Integer> financialChargeDetailSeqList);

    FinancialChargeDetail findByFinancialChargeSeqAndChargeTypeAndChargeSeq(Integer referenceSeq, Integer chargeType, Integer chargeSeq);

    List<FinancialChargeDetail> findByLiStatusNotAndEvStatusNotAndStatusNotAndFinancialChargeDetailSeqIn(Integer statusSeq, Integer statusSeq1, Integer statusSeq2, List<Integer> intFinancialChargeDetailSeqList);

    List<FinancialChargeDetail> findByLcnStatusAndStatusNotAndFinancialChargeDetailSeqIn(Integer statusSeq, Integer statusSeq1, List<Integer> financialChargeDetailSeqList);
}
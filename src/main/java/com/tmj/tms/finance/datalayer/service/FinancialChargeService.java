package com.tmj.tms.finance.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.finance.datalayer.modal.FinancialCharge;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialChargeService extends JpaEntityGraphRepository<FinancialCharge, Integer>, QueryDslPredicateExecutor<FinancialCharge> {

    FinancialCharge findByTargetTypeAndReferenceTypeAndReferenceSeqAndModuleSeqAndStatusNot(Integer targetType, Integer referenceType,
                                                                                            Integer referenceSeq, Integer transport, Integer statusSeq);

    FinancialCharge findByFinancialChargeSeqAndStatusNot(Integer financialChargeSeq, Integer statusSeq);

    List<FinancialCharge> findByStatusNot(Integer statusSeq);
}
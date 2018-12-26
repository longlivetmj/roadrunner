package com.tmj.tms.finance.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.finance.datalayer.modal.RateMaster;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateMasterService extends JpaEntityGraphRepository<RateMaster, Integer>, QueryDslPredicateExecutor<RateMaster> {

    List<RateMaster> findByStakeholderSeqAndStatus(Integer stakeholderSeq, Integer statusSeq);

    List<RateMaster> findByStakeholderSeqAndStatusAndChargeType(Integer stakeholderSeq, Integer statusSeq, Integer chargeType);

    List<RateMaster> findByStakeholderSeqAndStatusAndChargeTypeAndChargeSeq(Integer stakeholderSeq, Integer statusSeq, Integer chargeType, Integer chargeSeq);
}

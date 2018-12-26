package com.tmj.tms.master.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphQueryDslPredicateExecutor;
import com.tmj.tms.master.datalayer.modal.StakeholderTypeMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface StakeholderTypeMappingService extends JpaRepository<StakeholderTypeMapping, Integer>, JpaEntityGraphQueryDslPredicateExecutor<StakeholderTypeMapping> {

    List<StakeholderTypeMapping> findByStakeholderTypeSeqAndStatus(Integer stakeholderTypeSeq, Integer status);

    List<StakeholderTypeMapping> findByStakeholderCompanyProfileSeqAndStakeholderTypeSeqInAndStatus(Integer companyProfileSeq, Collection<Integer> stakeholderTypeSeqList, Integer status);

    List<StakeholderTypeMapping> findByStakeholderSeq(Integer stakeholderSeq);

    List<StakeholderTypeMapping> findByStakeholderStakeholderNameStartsWithIgnoreCaseAndStakeholderTypeSeqInAndStatus(String stakeholderName, Collection<Integer> stakeholderTypeSeqList, Integer status);

}

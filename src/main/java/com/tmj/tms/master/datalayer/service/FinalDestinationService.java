package com.tmj.tms.master.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphQueryDslPredicateExecutor;
import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.master.datalayer.modal.FinalDestination;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface FinalDestinationService extends JpaEntityGraphRepository<FinalDestination, Integer>, JpaEntityGraphQueryDslPredicateExecutor<FinalDestination> {

    List<FinalDestination> findByStatus(Integer status);

    List<FinalDestination> findByDestinationContainingIgnoreCaseAndStatusAndCompanyProfileSeq(String searchString, Integer status, Integer companyProfileSeq);

    List<FinalDestination> findByStatusIn(Collection<Integer> statusSeqList);

    List<FinalDestination> findByFinalDestinationSeqIn(List<Integer> arrivals);
}

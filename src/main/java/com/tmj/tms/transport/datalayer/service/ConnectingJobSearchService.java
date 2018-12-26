package com.tmj.tms.transport.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphQueryDslPredicateExecutor;
import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.transport.datalayer.modal.auxiliary.ConnectingJobSearch;

import java.util.Date;
import java.util.List;

public interface ConnectingJobSearchService extends JpaEntityGraphRepository<ConnectingJobSearch, Integer>, JpaEntityGraphQueryDslPredicateExecutor<ConnectingJobSearch> {

    List<ConnectingJobSearch> findByRequestedArrivalTimeAfterAndRequestedArrivalTimeBeforeAndCurrentStatusIn(Date requestedArrivalTime, Date date, List<Integer> statusList);

}

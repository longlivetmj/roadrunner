package com.tmj.tms.transport.utility.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphQueryDslPredicateExecutor;
import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.transport.datalayer.modal.auxiliary.TransportBookingFeedbackAux;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportBookingFeedbackAuxService extends JpaEntityGraphRepository<TransportBookingFeedbackAux, Integer>, JpaEntityGraphQueryDslPredicateExecutor<TransportBookingFeedbackAux> {
}

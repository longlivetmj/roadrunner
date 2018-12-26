package com.tmj.tms.transport.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphQueryDslPredicateExecutor;
import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.transport.datalayer.modal.TransportBookingFeedback;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportBookingFeedbackService extends JpaEntityGraphRepository<TransportBookingFeedback, Integer>, JpaEntityGraphQueryDslPredicateExecutor<TransportBookingFeedback> {
}

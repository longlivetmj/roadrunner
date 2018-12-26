package com.tmj.tms.transport.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphQueryDslPredicateExecutor;
import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.transport.datalayer.modal.TransportBulkBooking;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportBulkBookingService extends JpaEntityGraphRepository<TransportBulkBooking, Integer>, JpaEntityGraphQueryDslPredicateExecutor<TransportBulkBooking> {
}

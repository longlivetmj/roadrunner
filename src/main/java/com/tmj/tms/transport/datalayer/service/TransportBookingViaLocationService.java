package com.tmj.tms.transport.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphQueryDslPredicateExecutor;
import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.transport.datalayer.modal.TransportBookingViaLocation;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportBookingViaLocationService extends JpaEntityGraphRepository<TransportBookingViaLocation, Integer>, JpaEntityGraphQueryDslPredicateExecutor<TransportBookingViaLocation> {

    List<TransportBookingViaLocation> findByTransportBookingSeqAndStatusOrderByRequestedArrivalTime(Integer transportBookingSeq, Integer statusSeq, EntityGraph entityGraph);

    List<TransportBookingViaLocation> findByTransportBookingSeqAndStatus(Integer transportBookingSeq, Integer statusSeq);

}

package com.tmj.tms.transport.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphQueryDslPredicateExecutor;
import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransportBookingService extends JpaEntityGraphRepository<TransportBooking, Integer>, JpaEntityGraphQueryDslPredicateExecutor<TransportBooking> {

    List<TransportBooking> findByTransportBookingSeqAndCompanyProfileSeqAndModuleSeqAndCurrentStatusIn(Integer transportBookingSeq, Integer companyProfileSeq, Integer moduleSeq, List<Integer> statusSeqList, EntityGraph entityGraph);

    List<TransportBooking> findByEstimatedKmIsNull();

    List<TransportBooking> findFirst100ByCurrentStatusAndTransportBookingFeedback_ChargesCalculatedIsNull(Integer status);

    List<TransportBooking> findFirst100ByCurrentStatusAndTransportBookingFeedback_ChargeableKmIsNullAndRequestedArrivalTimeGreaterThan(Integer status, Date date);

    List<TransportBooking> findFirst200ByCurrentStatusAndTransportBookingFeedback_CargoInHandKmIsNull(Integer status);

    List<TransportBooking> findByTransportBookingSeqGreaterThanAndTransportBookingFeedback_PlacementKm(Integer transportBookingSeq, Double placementKm);
}

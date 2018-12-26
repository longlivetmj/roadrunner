package com.tmj.tms.transport.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphQueryDslPredicateExecutor;
import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.transport.datalayer.modal.TransportBookingVehicle;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportBookingVehicleService extends JpaEntityGraphRepository<TransportBookingVehicle, Integer>, JpaEntityGraphQueryDslPredicateExecutor<TransportBookingVehicle> {

    TransportBookingVehicle findByTransportBookingSeqAndStatus(Integer transportBookingSeq, Integer status);

}

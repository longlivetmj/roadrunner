package com.tmj.tms.fleet.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphQueryDslPredicateExecutor;
import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.fleet.datalayer.modal.VehicleModal;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleModalService extends JpaEntityGraphRepository<VehicleModal, Integer>, JpaEntityGraphQueryDslPredicateExecutor<VehicleModal> {

    List<VehicleModal> findByModalNameStartsWithIgnoreCaseAndVehicleManufacturerSeqAndStatus(String searchString, Integer vehicleManufacturerSeq, Integer status);

    List<VehicleModal> findByModalNameContainingIgnoreCaseAndVehicleManufacturerSeqAndStatus(String searchParam, Integer vehicleManufacturerSeq, Integer statusSeq);
}

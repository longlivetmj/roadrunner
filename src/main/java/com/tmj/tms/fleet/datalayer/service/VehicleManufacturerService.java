package com.tmj.tms.fleet.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphQueryDslPredicateExecutor;
import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.fleet.datalayer.modal.VehicleManufacturer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleManufacturerService extends JpaEntityGraphRepository<VehicleManufacturer, Integer>, JpaEntityGraphQueryDslPredicateExecutor<VehicleManufacturer> {

    List<VehicleManufacturer> findByManufacturerNameStartsWithIgnoreCaseAndStatus(String searchParam, Integer statusSeq);

    List<VehicleManufacturer> findByManufacturerNameContainingIgnoreCaseAndStatus(String searchParam, Integer statusSeq);
}

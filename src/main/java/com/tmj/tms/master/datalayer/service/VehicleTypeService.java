package com.tmj.tms.master.datalayer.service;

import com.tmj.tms.master.datalayer.modal.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleTypeService extends JpaRepository<VehicleType, Integer> {

    List<VehicleType> findByVehicleTypeNameStartsWithIgnoreCaseAndStatus(String searchParam, Integer statusSeq);

    List<VehicleType> findByVehicleTypeNameContainingIgnoreCaseAndStatus(String searchParam, Integer statusSeq);

    List<VehicleType> findByStatus(Integer statusSeq);
}

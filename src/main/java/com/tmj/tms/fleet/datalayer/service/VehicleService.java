package com.tmj.tms.fleet.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphQueryDslPredicateExecutor;
import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.fleet.datalayer.modal.Vehicle;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleService extends JpaEntityGraphRepository<Vehicle, Integer>, JpaEntityGraphQueryDslPredicateExecutor<Vehicle> {

    List<Vehicle> findByStakeholderSeqAndStatus(Integer stakeholderSeq, Integer status);

    List<Vehicle> findByVehicleNoContainingIgnoreCaseAndStatusAndCompanyProfileSeq(String vehicleNo, Integer status, Integer companyProfileSeq);

    List<Vehicle> findByCompanyProfileSeqAndFinanceIntegrationIsNullAndStakeholderSeqAndStatus(Integer companyProfileSeq, Integer defaultTransporterSeq, Integer statusSeq);

    Vehicle findByGpsTerminalKey(String gpsTerminalKey);
}

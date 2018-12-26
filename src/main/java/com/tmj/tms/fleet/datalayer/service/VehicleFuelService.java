package com.tmj.tms.fleet.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphQueryDslPredicateExecutor;
import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.fleet.datalayer.modal.VehicleFuel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleFuelService extends JpaEntityGraphRepository<VehicleFuel, Integer>, JpaEntityGraphQueryDslPredicateExecutor<VehicleFuel> {

    List<VehicleFuel> findFirst100ByCompanyProfileSeqAndFinanceIntegrationIsNullAndStatus(Integer companyProfileSeq, Integer statusSeq);

    List<VehicleFuel> findByCompanyProfileSeqAndStatus(Integer companyProfileSeq, Integer statusSeq);
}

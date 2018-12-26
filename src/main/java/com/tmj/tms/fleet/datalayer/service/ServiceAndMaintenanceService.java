package com.tmj.tms.fleet.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphQueryDslPredicateExecutor;
import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.fleet.datalayer.modal.ServiceAndMaintenance;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceAndMaintenanceService extends JpaEntityGraphRepository<ServiceAndMaintenance, Integer>, JpaEntityGraphQueryDslPredicateExecutor<ServiceAndMaintenance> {

    List<ServiceAndMaintenance> findFirst100ByCompanyProfileSeqAndFinanceIntegrationIsNullAndStatus(Integer companyProfileSeq, Integer statusSeq);

    List<ServiceAndMaintenance> findByCompanyProfileSeqAndStatus(Integer companyProfileSeq, Integer statusSeq);
}

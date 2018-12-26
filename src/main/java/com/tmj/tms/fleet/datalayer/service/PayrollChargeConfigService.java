package com.tmj.tms.fleet.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphQueryDslPredicateExecutor;
import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.fleet.datalayer.modal.PayrollChargeConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayrollChargeConfigService extends JpaEntityGraphRepository<PayrollChargeConfig, Integer>, JpaEntityGraphQueryDslPredicateExecutor<PayrollChargeConfig> {
    List<PayrollChargeConfig> findByCompanyProfileSeqAndPayrollChargeTypeAndStatus(Integer companyProfileSeq, Integer payrollChargeType, Integer statusSeq);

    PayrollChargeConfig findByCompanyProfileSeqAndPayrollChargeTypeAndChargeSeqAndStatus(Integer companyProfileSeq, Integer payrollChargeType, Integer allowanceSeq, Integer statusSeq);
}

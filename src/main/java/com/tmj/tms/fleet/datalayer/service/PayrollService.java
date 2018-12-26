package com.tmj.tms.fleet.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphQueryDslPredicateExecutor;
import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.fleet.datalayer.modal.Payroll;
import org.springframework.stereotype.Repository;

@Repository
public interface PayrollService extends JpaEntityGraphRepository<Payroll, Integer>, JpaEntityGraphQueryDslPredicateExecutor<Payroll> {

    Payroll findByEmployeeSeqAndStatus(Integer employeeSeq, Integer statusSeq);

}

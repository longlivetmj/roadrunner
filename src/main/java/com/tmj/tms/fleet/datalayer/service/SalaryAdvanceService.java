package com.tmj.tms.fleet.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphQueryDslPredicateExecutor;
import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.fleet.datalayer.modal.SalaryAdvance;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryAdvanceService extends JpaEntityGraphRepository<SalaryAdvance, Integer>, JpaEntityGraphQueryDslPredicateExecutor<SalaryAdvance> {

    List<SalaryAdvance> findByEmployeeSeqAndSalaryYearAndSalaryMonthAndStatus(Integer employeeSeq, Integer salaryYear, Integer salaryMonth, Integer status);

    List<SalaryAdvance> findFirst100ByCompanyProfileSeqAndFinanceIntegrationIsNullAndStatus(Integer companyProfileSeq, Integer statusSeq);

    List<SalaryAdvance> findByCompanyProfileSeqAndStatus(Integer companyProfileSeq, Integer statusSeq);
}

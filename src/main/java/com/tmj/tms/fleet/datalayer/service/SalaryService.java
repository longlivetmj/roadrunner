package com.tmj.tms.fleet.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphQueryDslPredicateExecutor;
import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.fleet.datalayer.modal.Salary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryService extends JpaEntityGraphRepository<Salary, Integer>, JpaEntityGraphQueryDslPredicateExecutor<Salary> {

    Salary findByEmployeeSeqAndSalaryYearAndSalaryMonthAndStatus(Integer employeeSeq, Integer year, Integer month, Integer status);

    List<Salary> findFirst100ByCompanyProfileSeqAndFinanceIntegrationIsNullAndStatus(Integer companyProfileSeq, Integer statusSeq);

    List<Salary> findByCompanyProfileSeqAndStatus(Integer companyProfileSeq, Integer statusSeq);
}

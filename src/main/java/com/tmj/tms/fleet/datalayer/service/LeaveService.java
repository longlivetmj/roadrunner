package com.tmj.tms.fleet.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphQueryDslPredicateExecutor;
import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.fleet.datalayer.modal.Leave;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LeaveService extends JpaEntityGraphRepository<Leave, Integer>, JpaEntityGraphQueryDslPredicateExecutor<Leave> {

    List<Leave> findByEmployeeSeqAndStartDateBetweenAndStatusNot(Integer employeeSeq, Date startDate, Date endDate, Integer statusSeq);

    List<Leave> findByEmployeeSeqAndEndDateBetweenAndStatusNot(Integer employeeSeq, Date startDate, Date endDate, Integer statusSeq);

    List<Leave> findByEmployeeSeqAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndStatusNot(Integer employeeSeq, Date startDate, Date endDate, Integer statusSeq);

    List<Leave> findByEmployeeSeqAndLeaveYearAndLeaveMonthAndStatusNot(Integer employeeSeq, Integer salaryYear, Integer salaryMonth, Integer statusSeq);
}

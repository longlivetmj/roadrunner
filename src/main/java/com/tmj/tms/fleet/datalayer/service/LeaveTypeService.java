package com.tmj.tms.fleet.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphQueryDslPredicateExecutor;
import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.fleet.datalayer.modal.LeaveType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveTypeService extends JpaEntityGraphRepository<LeaveType, Integer>, JpaEntityGraphQueryDslPredicateExecutor<LeaveType> {

    List<LeaveType> findByStatus(Integer statusSeq);
}

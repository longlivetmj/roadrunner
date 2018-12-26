package com.tmj.tms.config.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphQueryDslPredicateExecutor;
import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.config.datalayer.modal.DepartmentCharge;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface DepartmentChargeService extends JpaEntityGraphRepository<DepartmentCharge, Integer>, JpaEntityGraphQueryDslPredicateExecutor<DepartmentCharge> {

    List<DepartmentCharge> findByModuleSeq(Integer moduleSeq);

    List<DepartmentCharge> findByModuleSeqAndStatusNot(Integer moduleSeq, Integer status);

    List<DepartmentCharge> findByModuleSeqAndDepartmentSeq(Integer moduleSeq, Integer departmentSeq);

    List<DepartmentCharge> findByModuleSeqAndDepartmentSeqAndReferenceTypeOrderByDefaultOrderAsc(Integer moduleSeq, Integer departmentSeq, Integer documentTypeSeq);

    List<DepartmentCharge> findByModuleSeqAndChargeSeqNotInAndStatusNot(Integer moduleSeq, Collection<Integer> chargeSeqList, Integer status);
}

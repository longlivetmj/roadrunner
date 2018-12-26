package com.tmj.tms.master.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphQueryDslPredicateExecutor;
import com.tmj.tms.master.datalayer.modal.SalesPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface SalesPersonService extends JpaRepository<SalesPerson, Integer>, JpaEntityGraphQueryDslPredicateExecutor<SalesPerson> {

    List<SalesPerson> findByStatus(Integer status);

    List<SalesPerson> findByCompanyProfileSeqAndStatus(Integer companyProfileSeq, Integer status);

    List<SalesPerson> findByStatusIn(Collection<Integer> statusSeqList);

    List<SalesPerson> findBySalesPersonNameStartsWithIgnoreCaseAndStatus(String salesPersonName, Integer status);

    List<SalesPerson> findBySalesPersonCodeContainingIgnoreCaseAndSalesPersonNameContainingIgnoreCaseAndStatusIn(String salesPersonCode, String salesPersonName, Collection<Integer> status);

    List<SalesPerson> findBySalesPersonCodeContainingIgnoreCaseAndSalesPersonNameContainingIgnoreCaseAndStakeholderSeqAndStatusIn(String salesPersonCode, String salesPersonName, Integer stakeholderSeq, Collection<Integer> status);
}

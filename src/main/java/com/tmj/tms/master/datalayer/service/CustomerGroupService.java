package com.tmj.tms.master.datalayer.service;

import com.tmj.tms.master.datalayer.modal.CustomerGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CustomerGroupService extends JpaRepository<CustomerGroup, Integer> {

    CustomerGroup findByCustomerGroupNameIgnoreCase(String customerGroupName);

    List<CustomerGroup> findByCustomerGroupCodeContainingIgnoreCaseAndStatusIn(String customerGroupCode, Collection<Integer> statusSeqList);

    List<CustomerGroup> findByCustomerGroupNameStartsWithIgnoreCaseAndStatus(String customerGroupName, Integer status);

    List<CustomerGroup> findByCustomerGroupNameContainingIgnoreCaseAndStatusIn(String customerGroupName, Collection<Integer> statusSeqList);

    List<CustomerGroup> findByCustomerGroupCodeContainingIgnoreCaseAndCustomerGroupNameContainingIgnoreCaseAndStatusIn(String customerGroupCode, String customerGroupName, Collection<Integer> statusSeqList);

    List<CustomerGroup> findByStatusIn(Collection<Integer> statusSeqList);

    List<CustomerGroup> findByStatus(Integer status);

    CustomerGroup findByCustomerGroupCodeAndStatus(String customerGroupCode, Integer status);

}

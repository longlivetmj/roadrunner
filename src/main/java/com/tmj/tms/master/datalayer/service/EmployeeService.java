package com.tmj.tms.master.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphQueryDslPredicateExecutor;
import com.tmj.tms.master.datalayer.modal.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeService extends JpaRepository<Employee, Integer>, JpaEntityGraphQueryDslPredicateExecutor<Employee> {

    List<Employee> findByEmployeeNameContainingIgnoreCase(String employeeName);

    List<Employee> findByStatus(Integer status);

    List<Employee> findByEmployeeDesignationSeq(Integer employeeDesignationSeq);

    List<Employee> findByEmployeeNameContainingIgnoreCaseAndStatus(String employeeName, Integer status);

    List<Employee> findByEmployeeNameContainingIgnoreCaseAndEmployeeDesignationSeq(String employeeName, Integer employeeDesignationSeq);

    List<Employee> findByStatusAndEmployeeDesignationSeq(Integer status, Integer employeeDesignationSeq);

    List<Employee> findByEmployeeNameContainingIgnoreCaseAndStatusAndEmployeeDesignationSeq(String employeeName, Integer status, Integer employeeDesignationSeq);

    List<Employee> findByEmployeeNameContainingIgnoreCaseAndStatusAndEmployeeDesignationSeqAndCompanyProfileSeqAndStakeholderSeq(String employeeName, Integer status,
                                                                                                                                 Integer employeeDesignationSeq,
                                                                                                                                 Integer companyProfileSeq,
                                                                                                                                 Integer stakeholderSeq);

    List<Employee> findByStatusAndEmployeeDesignationSeqAndCompanyProfileSeqAndStakeholderSeq(Integer statusSeq, Integer driver,
                                                                                              Integer companyProfileSeq, Integer transporterSeq);

    List<Employee> findByStatusAndCompanyProfileSeqAndStakeholderSeq(Integer statusSeq, Integer companyProfileSeq, Integer defaultTransporterSeq);

    List<Employee> findByStatusAndEmployeeDesignationSeqAndCompanyProfileSeqAndStakeholderSeqAndEmployeeNameContainingIgnoreCase(Integer statusSeq, Integer helper,
                                                                                                                                 Integer companyProfileSeq, Integer transportCompanySeq, String searchParam);

    List<Employee> findByCompanyProfileSeqAndFinanceIntegrationIsNullAndStakeholderSeqAndStatus(Integer companyProfileSeq, Integer defaultTransporterSeq, Integer statusSeq);
}

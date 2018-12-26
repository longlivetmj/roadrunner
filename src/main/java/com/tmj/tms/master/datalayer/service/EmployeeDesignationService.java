package com.tmj.tms.master.datalayer.service;

import com.tmj.tms.master.datalayer.modal.EmployeeDesignation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDesignationService extends JpaRepository<EmployeeDesignation, Integer> {

    List<EmployeeDesignation> findByStatusNot(Integer status);

    EmployeeDesignation findByDesignation(String designation);
}

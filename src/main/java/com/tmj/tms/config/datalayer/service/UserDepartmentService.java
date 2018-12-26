package com.tmj.tms.config.datalayer.service;

import com.tmj.tms.config.datalayer.modal.Department;
import com.tmj.tms.config.datalayer.modal.UserDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDepartmentService extends JpaRepository<UserDepartment, Integer> {

    List<UserDepartment> findByUserSeqAndStatusAndDepartment_ModuleSeq(Integer userSeq, Integer status, Integer moduleSeq);

    UserDepartment findByUserSeqAndDepartmentSeqAndStatusAndDepartment_ModuleSeq(Integer userSeq, Integer departmentSeq, Integer status, Integer moduleSeq);
}

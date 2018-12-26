package com.tmj.tms.config.businesslayer.manager;

import com.tmj.tms.config.datalayer.modal.Department;
import com.tmj.tms.config.datalayer.modal.DepartmentChargeAux;
import com.tmj.tms.utility.ResponseObject;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

public interface DepartmentManagementControllerManager {

    ResponseObject saveDepartment(Department department, Principal principal, HttpServletRequest request);

    ResponseObject updateDepartment(Department department, Principal principal);

    ResponseObject deleteDepartment(Integer departmentSeq, Principal principal);

    List<Department> searchDepartment(String departmentCode, String departmentName, String prifix);

    ResponseObject saveDepartmentCharges(DepartmentChargeAux departmentCharges, Principal principal);
}

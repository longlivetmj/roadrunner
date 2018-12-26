package com.tmj.tms.master.businesslayer.manager;

import com.tmj.tms.master.datalayer.modal.Employee;
import com.tmj.tms.master.datalayer.modal.auxiliary.EmployeeSearch;
import com.tmj.tms.utility.ResponseObject;

import java.util.List;

public interface EmployeeManagementControllerManager {

    ResponseObject saveEmployee(Employee employee);

    ResponseObject updateEmployee(Employee employee);

    ResponseObject deleteEmployee(Integer employeeSeq);

    List<Employee> searchEmployee(EmployeeSearch employeeSearch);
}

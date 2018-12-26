package com.tmj.tms.master.businesslayer.managerImpl;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.querydsl.core.BooleanBuilder;
import com.tmj.tms.master.businesslayer.manager.EmployeeManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.Employee;
import com.tmj.tms.master.datalayer.modal.QEmployee;
import com.tmj.tms.master.datalayer.modal.auxiliary.EmployeeSearch;
import com.tmj.tms.master.datalayer.service.EmployeeService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class EmployeeManagementControllerManagerImpl implements EmployeeManagementControllerManager {

    private final EmployeeService employeeService;
    private final HttpSession httpSession;

    @Autowired
    public EmployeeManagementControllerManagerImpl(EmployeeService employeeService,
                                                   HttpSession httpSession) {
        this.employeeService = employeeService;
        this.httpSession = httpSession;
    }

    @Override
    public ResponseObject saveEmployee(Employee employee) {
        Integer companyProfileSeq = Integer.parseInt(httpSession.getAttribute("companyProfileSeq").toString());
        employee.setCompanyProfileSeq(companyProfileSeq);
        employee = this.employeeService.save(employee);
        ResponseObject responseObject = new ResponseObject("Employee Saved Successfully", true);
        responseObject.setObject(employee);
        return responseObject;
    }

    @Override
    public ResponseObject updateEmployee(Employee employee) {
        ResponseObject responseObject;
        Integer companyProfileSeq = Integer.parseInt(httpSession.getAttribute("companyProfileSeq").toString());
        employee.setCompanyProfileSeq(companyProfileSeq);
        Employee dbEmployee = this.employeeService.findOne(employee.getEmployeeSeq());
        if (dbEmployee != null) {
            if (!dbEmployee.equals(employee)) {
                employee = this.employeeService.save(employee);
                responseObject = new ResponseObject("Employee Updated Successfully", true);
                responseObject.setObject(employee);
            } else {
                responseObject = new ResponseObject("No Amendments Found", false);
            }
        } else {
            responseObject = new ResponseObject("Employee Not Found!!", false);
        }
        return responseObject;
    }

    @Override
    public ResponseObject deleteEmployee(Integer employeeSeq) {
        Employee dbEmployee = this.employeeService.findOne(employeeSeq);
        dbEmployee.setStatus(0);
        ResponseObject responseObject = new ResponseObject("Employee Deleted Successfully", true);
        dbEmployee = this.employeeService.save(dbEmployee);
        responseObject.setObject(dbEmployee);
        return responseObject;
    }

    @Override
    public List<Employee> searchEmployee(EmployeeSearch employeeSearch) {
        List<Employee> employeeList = null;
        try {
            Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
            BooleanBuilder builder = new BooleanBuilder();
            QEmployee employee = QEmployee.employee;
            builder.and(employee.companyProfileSeq.eq(companyProfileSeq));
            if (employeeSearch.getEmployeeName() != null && employeeSearch.getEmployeeName().length() > 0) {
                builder.and(employee.employeeName.containsIgnoreCase(employeeSearch.getEmployeeName()));
            }
            if (employeeSearch.getStatus() != null) {
                builder.and(employee.status.eq(employeeSearch.getStatus()));
            }
            if (employeeSearch.getEmployeeDesignationSeq() != null) {
                builder.and(employee.employeeDesignationSeq.eq(employeeSearch.getEmployeeDesignationSeq()));
            }
            if (employeeSearch.getStakeholderSeq() != null) {
                builder.and(employee.stakeholderSeq.eq(employeeSearch.getStakeholderSeq()));
            }
            employeeList = (List<Employee>) this.employeeService.findAll(builder, EntityGraphUtils.fromName("Employee.default"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeList;
    }

}

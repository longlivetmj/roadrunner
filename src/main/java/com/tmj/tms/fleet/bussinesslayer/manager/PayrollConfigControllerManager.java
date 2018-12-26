package com.tmj.tms.fleet.bussinesslayer.manager;

import com.tmj.tms.fleet.datalayer.modal.Payroll;
import com.tmj.tms.utility.ResponseObject;

import java.util.List;

public interface PayrollConfigControllerManager {

    ResponseObject save(Payroll payroll);

    ResponseObject update(Payroll payroll);

    List<Payroll> search(String employeeName, Integer status, Integer employeeDesignationSeq);

    ResponseObject validate(Payroll payroll);
}

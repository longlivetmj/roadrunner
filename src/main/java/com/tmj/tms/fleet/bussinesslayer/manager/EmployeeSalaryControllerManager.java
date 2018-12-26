package com.tmj.tms.fleet.bussinesslayer.manager;

import com.tmj.tms.finance.datalayer.modal.FinancialChargeDetail;
import com.tmj.tms.fleet.datalayer.modal.Salary;
import com.tmj.tms.fleet.datalayer.modal.auxiliary.SalarySearch;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.utility.ResponseObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

public interface EmployeeSalaryControllerManager {

    ResponseObject save(SalarySearch salarySearch);

    ResponseObject update(Salary salary);

    List<Salary> search(SalarySearch salarySearch);

    Salary findSalary(SalarySearch salarySearch);

    List<TransportBooking> getTransportBookingList(Salary salary);

    List<FinancialChargeDetail> getFinancialChargeDetailList(List<TransportBooking> transportBookingList, Salary salary);

    Salary calculate(SalarySearch salarySearch);

    ResponseObject delete(Integer salarySeq);

    Integer getSalaryReport(Integer salarySeq, Integer reportSeq, Integer pdf, Integer rtf, Integer xls,
                            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Principal principal);
}

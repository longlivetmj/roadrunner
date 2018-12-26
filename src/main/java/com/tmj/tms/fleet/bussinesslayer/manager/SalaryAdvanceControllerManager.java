package com.tmj.tms.fleet.bussinesslayer.manager;

import com.tmj.tms.fleet.datalayer.modal.SalaryAdvance;
import com.tmj.tms.fleet.datalayer.modal.auxiliary.SalarySearch;
import com.tmj.tms.utility.ResponseObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

public interface SalaryAdvanceControllerManager {

    ResponseObject save(SalarySearch salarySearch);

    Object findSalary(SalarySearch salarySearch);

    List<SalaryAdvance> search(SalarySearch salarySearch);

    ResponseObject delete(Integer salaryAdvanceSeq);

    Integer getSalaryAdvanceReport(Integer salaryAdvanceSeq, Integer reportSeq, Integer pdf, Integer rtf, Integer xls,
                                   HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Principal principal);
}

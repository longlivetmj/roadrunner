package com.tmj.tms.fleet.bussinesslayer.managerimpl;

import com.querydsl.core.BooleanBuilder;
import com.tmj.tms.config.datalayer.service.ReportUploadService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.fleet.bussinesslayer.manager.EmployeeSalaryControllerManager;
import com.tmj.tms.fleet.bussinesslayer.manager.SalaryAdvanceControllerManager;
import com.tmj.tms.fleet.datalayer.modal.QSalaryAdvance;
import com.tmj.tms.fleet.datalayer.modal.Salary;
import com.tmj.tms.fleet.datalayer.modal.SalaryAdvance;
import com.tmj.tms.fleet.datalayer.modal.auxiliary.SalarySearch;
import com.tmj.tms.fleet.datalayer.service.SalaryAdvanceService;
import com.tmj.tms.fleet.datalayer.service.SalaryService;
import com.tmj.tms.utility.DateFormatter;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SalaryAdvanceControllerManagerImpl implements SalaryAdvanceControllerManager {

    private final EmployeeSalaryControllerManager employeeSalaryControllerManager;
    private final SalaryAdvanceService salaryAdvanceService;
    private final HttpSession httpSession;
    private final SalaryService salaryService;
    private final ReportUploadService reportUploadService;

    @Autowired
    public SalaryAdvanceControllerManagerImpl(EmployeeSalaryControllerManager employeeSalaryControllerManager,
                                              SalaryAdvanceService salaryAdvanceService,
                                              HttpSession httpSession,
                                              SalaryService salaryService,
                                              ReportUploadService reportUploadService) {
        this.employeeSalaryControllerManager = employeeSalaryControllerManager;
        this.salaryAdvanceService = salaryAdvanceService;
        this.httpSession = httpSession;
        this.salaryService = salaryService;
        this.reportUploadService = reportUploadService;
    }

    @Override
    public ResponseObject save(SalarySearch salarySearch) {
        ResponseObject responseObject = new ResponseObject();
        Salary dbSalary = this.salaryService.findByEmployeeSeqAndSalaryYearAndSalaryMonthAndStatus(salarySearch.getEmployeeSeq(),
                salarySearch.getSalaryYear(), salarySearch.getSalaryMonth(), MasterDataStatus.APPROVED.getStatusSeq());
        if (dbSalary == null) {
            Salary salary = this.employeeSalaryControllerManager.findSalary(salarySearch);
            if (salary.getNetPay() > salarySearch.getSalaryAdvance()) {
                SalaryAdvance salaryAdvance = new SalaryAdvance();
                salaryAdvance.setPayrollSeq(salary.getPayrollSeq());
                salaryAdvance.setCompanyProfileSeq(salary.getCompanyProfileSeq());
                salaryAdvance.setPayrollSeq(salary.getPayrollSeq());
                salaryAdvance.setEmployeeSeq(salary.getEmployeeSeq());
                salaryAdvance.setEmployeeDesignationSeq(salary.getEmployeeDesignationSeq());
                salaryAdvance.setNetPay(salary.getNetPay());
                salaryAdvance.setSalaryAdvance(salarySearch.getSalaryAdvance());
                salaryAdvance.setRemarks(salarySearch.getRemarks());
                salaryAdvance.setSalaryYear(salary.getSalaryYear());
                salaryAdvance.setSalaryMonth(salary.getSalaryMonth());
                salaryAdvance.setStartDate(salary.getStartDate());
                salaryAdvance.setEndDate(salary.getEndDate());
                salaryAdvance.setCurrencySeq(salary.getCurrencySeq());
                salaryAdvance.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
                salaryAdvance = this.salaryAdvanceService.save(salaryAdvance);
                responseObject.setMessage("Salary Advance Successfully Saved");
                responseObject.setStatus(true);
                responseObject.setObject(salaryAdvance);
            } else {
                responseObject.setMessage("Advance should be smaller than Net Pay");
                responseObject.setStatus(false);
            }
        } else {
            responseObject.setMessage("Salary has been already Calculated");
            responseObject.setStatus(false);
        }
        return responseObject;
    }

    @Override
    public Object findSalary(SalarySearch salarySearch) {
        return this.employeeSalaryControllerManager.findSalary(salarySearch);
    }

    @Override
    public List<SalaryAdvance> search(SalarySearch salarySearch) {
        List<SalaryAdvance> salaryAdvanceList = null;
        try {
            Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
            BooleanBuilder builder = new BooleanBuilder();
            QSalaryAdvance salaryAdvance = QSalaryAdvance.salaryAdvance1;
            if (salarySearch.getEmployeeSeq() != null) {
                builder.and(salaryAdvance.employeeSeq.eq(salarySearch.getEmployeeSeq()));
            }
            if (salarySearch.getEmployeeDesignationSeq() != null) {
                builder.and(salaryAdvance.employeeDesignationSeq.eq(salarySearch.getEmployeeDesignationSeq()));
            }
            if (salarySearch.getSalaryYear() != null) {
                builder.and(salaryAdvance.salaryYear.eq(salarySearch.getSalaryYear()));
            }
            if (salarySearch.getSalaryMonth() != null) {
                builder.and(salaryAdvance.salaryMonth.eq(salarySearch.getSalaryMonth()));
            }
            builder.and(salaryAdvance.companyProfileSeq.eq(companyProfileSeq));
            salaryAdvanceList = (List<SalaryAdvance>) this.salaryAdvanceService.findAll(builder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salaryAdvanceList;
    }

    @Override
    public ResponseObject delete(Integer salaryAdvanceSeq) {
        SalaryAdvance salaryAdvance = this.salaryAdvanceService.findOne(salaryAdvanceSeq);
        salaryAdvance.setStatus(MasterDataStatus.DELETED.getStatusSeq());
        this.salaryAdvanceService.save(salaryAdvance);
        return new ResponseObject("Successfully Deleted", true);
    }

    @Override
    public Integer getSalaryAdvanceReport(Integer salaryAdvanceSeq, Integer reportSeq, Integer pdf, Integer rtf, Integer xls,
                                          HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Principal principal) {
        Integer reportUploadSeq = null;
        String moduleName = "fleet";
        try {
            DateFormatter dateFormatter = new DateFormatter();
            SalaryAdvance salaryAdvance = this.salaryAdvanceService.findOne(salaryAdvanceSeq);
            Map<String, Object> param = new HashMap<>();
            param.put("SALARY_ADVANCE", salaryAdvance.getSalaryAdvance());
            param.put("NET_PAY", salaryAdvance.getNetPay());
            param.put("EMPLOYEE_NAME", salaryAdvance.getEmployee().getEmployeeName());
            param.put("DESIGNATION", salaryAdvance.getEmployeeDesignation().getDescription());
            param.put("REMARKS", salaryAdvance.getRemarks());
            param.put("YEAR_MONTH", salaryAdvance.getSalaryYear() + "/" + DateFormatter.getMonth(salaryAdvance.getSalaryMonth()));
            param.put("CREATED_DATE", dateFormatter.returnLongFormattedDateTime(salaryAdvance.getCreatedDate()));
            param.put("CREATED_BY", salaryAdvance.getCreatedBy());
            String fileName = salaryAdvance.getEmployee().getNicNo() + "-" + salaryAdvance.getSalaryYear() + "" + salaryAdvance.getSalaryMonth();
            if (pdf != null && pdf == 1) {
                reportUploadSeq = this.reportUploadService.saveReport(param,
                        moduleName,
                        reportSeq,
                        fileName,
                        ".pdf",
                        "application/pdf",
                        httpServletRequest);
            }
            if (rtf != null && rtf == 1) {
                reportUploadSeq = this.reportUploadService.saveReport(param,
                        moduleName,
                        reportSeq,
                        fileName,
                        ".rtf",
                        "application/rtf",
                        httpServletRequest);
            }
            if (xls != null && xls == 1) {
                reportUploadSeq = this.reportUploadService.saveReport(param,
                        moduleName,
                        reportSeq,
                        fileName,
                        ".xlsx",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                        httpServletRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reportUploadSeq;
    }

}

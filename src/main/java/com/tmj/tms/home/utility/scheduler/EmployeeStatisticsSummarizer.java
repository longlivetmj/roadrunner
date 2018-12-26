package com.tmj.tms.home.utility.scheduler;

import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.datalayer.service.CompanyProfileService;
import com.tmj.tms.config.datalayer.service.ModuleService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.fleet.bussinesslayer.manager.EmployeeSalaryControllerManager;
import com.tmj.tms.fleet.datalayer.modal.Salary;
import com.tmj.tms.fleet.datalayer.modal.SalaryCommission;
import com.tmj.tms.fleet.datalayer.modal.SalaryCommissionDetail;
import com.tmj.tms.fleet.datalayer.modal.auxiliary.SalarySearch;
import com.tmj.tms.home.datalayer.model.TempEmployeeStatistics;
import com.tmj.tms.home.datalayer.service.TempEmployeeStatisticsService;
import com.tmj.tms.master.datalayer.modal.Employee;
import com.tmj.tms.master.datalayer.modal.EmployeeDesignation;
import com.tmj.tms.master.datalayer.service.EmployeeDesignationService;
import com.tmj.tms.master.datalayer.service.EmployeeService;
import com.tmj.tms.utility.DateFormatter;
import com.tmj.tms.utility.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Profile(value = "prod")
public class EmployeeStatisticsSummarizer {

    private final ModuleService moduleService;
    private final CompanyProfileService companyProfileService;
    private final EmployeeService employeeService;
    private final EmployeeDesignationService employeeDesignationService;
    private final EmployeeSalaryControllerManager employeeSalaryControllerManager;
    private final TempEmployeeStatisticsService tempEmployeeStatisticsService;

    @Autowired
    public EmployeeStatisticsSummarizer(ModuleService moduleService,
                                        CompanyProfileService companyProfileService,
                                        EmployeeService employeeService,
                                        EmployeeDesignationService employeeDesignationService,
                                        EmployeeSalaryControllerManager employeeSalaryControllerManager,
                                        TempEmployeeStatisticsService tempEmployeeStatisticsService) {
        this.moduleService = moduleService;
        this.companyProfileService = companyProfileService;
        this.employeeService = employeeService;
        this.employeeDesignationService = employeeDesignationService;
        this.employeeSalaryControllerManager = employeeSalaryControllerManager;
        this.tempEmployeeStatisticsService = tempEmployeeStatisticsService;
    }

    @Scheduled(fixedRate = 60000 * 15)
    @Transactional(timeout = 500000000)
    public void updateEmployeeStatistics() {
        List<CompanyProfile> companyProfileList = this.companyProfileService.findByStatusNot(MasterDataStatus.DELETED.getStatusSeq());
        Integer moduleSeq = this.moduleService.findByModuleCode("TRANSPORT").getModuleSeq();
        EmployeeDesignation driver = this.employeeDesignationService.findByDesignation("DRIVER");
        EmployeeDesignation helper = this.employeeDesignationService.findByDesignation("HELPER");
        Calendar calendar = Calendar.getInstance();
        for (CompanyProfile companyProfile : companyProfileList) {
            List<Employee> driverList = this.employeeService.findByStatusAndEmployeeDesignationSeqAndCompanyProfileSeqAndStakeholderSeq(MasterDataStatus.APPROVED.getStatusSeq(),
                    driver.getEmployeeDesignationSeq(), companyProfile.getCompanyProfileSeq(), companyProfile.getDefaultTransporterSeq());
            List<TempEmployeeStatistics> driverStatisticsList = new ArrayList<>();
            for (Employee employee : driverList) {
                try {
                    SalarySearch salarySearch = this.initSalarySearch(employee, driver.getEmployeeDesignationSeq(), calendar);
                    Salary salary = this.employeeSalaryControllerManager.findSalary(salarySearch);
                    TempEmployeeStatistics tempEmployeeStatistics = this.init(salary, employee, companyProfile, calendar, moduleSeq);
                    driverStatisticsList.add(tempEmployeeStatistics);
                } catch (Exception e) {
                    System.out.println(">>>>>>>>>>>>>>Driver>>>>" + employee.getEmployeeSeq());
                }
            }
            Double meanValue = driverStatisticsList.stream().mapToDouble(TempEmployeeStatistics::getScore).sum() / driverStatisticsList.size();
            List<Double> values = driverStatisticsList.stream().map(i -> i.getScore().doubleValue()).collect(Collectors.toList());
            this.saveOrUpdate(driverStatisticsList, meanValue, values);
        }
        for (CompanyProfile companyProfile : companyProfileList) {
            List<Employee> helperList = this.employeeService.findByStatusAndEmployeeDesignationSeqAndCompanyProfileSeqAndStakeholderSeq(MasterDataStatus.APPROVED.getStatusSeq(),
                    helper.getEmployeeDesignationSeq(), companyProfile.getCompanyProfileSeq(), companyProfile.getDefaultTransporterSeq());
            List<TempEmployeeStatistics> helperStatisticsList = new ArrayList<>();
            for (Employee employee : helperList) {
                try {
                    try {
                        SalarySearch salarySearch = this.initSalarySearch(employee, helper.getEmployeeDesignationSeq(), calendar);
                        Salary salary = this.employeeSalaryControllerManager.findSalary(salarySearch);
                        TempEmployeeStatistics tempEmployeeStatistics = this.init(salary, employee, companyProfile, calendar, moduleSeq);
                        helperStatisticsList.add(tempEmployeeStatistics);
                    } catch (Exception e) {
                        System.out.println(">>>>>>>>>>>>>>Helper>>>>" + employee.getEmployeeSeq());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Double meanValue = helperStatisticsList.stream().mapToDouble(TempEmployeeStatistics::getScore).sum() / helperStatisticsList.size();
            List<Double> values = helperStatisticsList.stream().map(i -> i.getScore().doubleValue()).collect(Collectors.toList());
            this.saveOrUpdate(helperStatisticsList, meanValue, values);
        }
    }

    private void saveOrUpdate(List<TempEmployeeStatistics> driverStatisticsList, Double meanValue, List<Double> values) {
        List<TempEmployeeStatistics> saveList = new ArrayList<>();
        List<TempEmployeeStatistics> dbDataList = this.tempEmployeeStatisticsService.findAll();
        for (TempEmployeeStatistics tempEmployeeStatistics : dbDataList) {
            Employee employee = this.employeeService.findOne(tempEmployeeStatistics.getEmployeeSeq());
            if (!employee.getStatus().equals(MasterDataStatus.APPROVED.getStatusSeq())) {
                this.tempEmployeeStatisticsService.delete(tempEmployeeStatistics);
            }
        }
        for (TempEmployeeStatistics tempEmployeeStatistics : driverStatisticsList) {
            if (tempEmployeeStatistics.getScore() != null) {
                tempEmployeeStatistics.setMean(meanValue);
                tempEmployeeStatistics.setzScore(Statistics.zScore(tempEmployeeStatistics.getScore(), meanValue, Statistics.stddev(meanValue, values.stream().mapToDouble(Double::doubleValue).toArray())));
                tempEmployeeStatistics.setLastUpdatedDate(new Date());
                TempEmployeeStatistics dbStatistics = this.tempEmployeeStatisticsService.findByEmployeeSeqAndYearAndMonthAndModuleSeq(tempEmployeeStatistics.getEmployeeSeq(), tempEmployeeStatistics.getYear(), tempEmployeeStatistics.getMonth(), tempEmployeeStatistics.getModuleSeq());
                if (dbStatistics != null) {
                    tempEmployeeStatistics.setTempEmployeeStatisticsSeq(dbStatistics.getTempEmployeeStatisticsSeq());
                }
                saveList.add(tempEmployeeStatistics);
            }
        }
        this.tempEmployeeStatisticsService.save(saveList);
    }

    private SalarySearch initSalarySearch(Employee employee, Integer employeeDesignationSeq, Calendar calendar) {
        SalarySearch salarySearch = new SalarySearch();
        salarySearch.setEmployeeSeq(employee.getEmployeeSeq());
        salarySearch.setEmployeeDesignationSeq(employeeDesignationSeq);
        salarySearch.setSalaryYear(calendar.get(Calendar.YEAR));
        salarySearch.setSalaryMonth(calendar.get(Calendar.MONTH) + 1);
        salarySearch.setStartDate(DateFormatter.getStartOfThisMonth());
        salarySearch.setEndDate(DateFormatter.getEndOfThisMonth());
        return salarySearch;
    }

    private TempEmployeeStatistics init(Salary salary, Employee employee, CompanyProfile companyProfile, Calendar calendar, Integer moduleSeq) {
        TempEmployeeStatistics tempEmployeeStatistics = new TempEmployeeStatistics();
        tempEmployeeStatistics.setEmployeeSeq(employee.getEmployeeSeq());
        tempEmployeeStatistics.setEmployeeDesignationSeq(employee.getEmployeeDesignationSeq());
        tempEmployeeStatistics.setCompanyProfileSeq(companyProfile.getCompanyProfileSeq());
        tempEmployeeStatistics.setModuleSeq(moduleSeq);
        tempEmployeeStatistics.setYear(calendar.get(Calendar.YEAR));
        tempEmployeeStatistics.setMonth(calendar.get(Calendar.MONTH));
        tempEmployeeStatistics.setAttendance(salary.getAttendance());
        tempEmployeeStatistics.setTotalCommission(salary.getTotalCommission());
        Set<Integer> transportBookingSeqList = new HashSet<>();
        Double totalKm = 0.0;
        if (salary.getSalaryCommissionList() != null) {
            for (SalaryCommission salaryCommission : salary.getSalaryCommissionList()) {
                for (SalaryCommissionDetail salaryCommissionDetail : salaryCommission.getSalaryCommissionDetailList()) {
                    if (!transportBookingSeqList.contains(salaryCommissionDetail.getReferenceSeq())) {
                        totalKm = totalKm + salaryCommissionDetail.getTotalKm();
                    }
                    transportBookingSeqList.add(salaryCommissionDetail.getReferenceSeq());
                }
            }

            tempEmployeeStatistics.setNoOfJobs(transportBookingSeqList.size());
            tempEmployeeStatistics.setTotalKm(totalKm);
            tempEmployeeStatistics.setScore(this.scoreCalculator(tempEmployeeStatistics));
        } else {
            tempEmployeeStatistics.setNoOfJobs(0);
            tempEmployeeStatistics.setTotalKm(0.0);
            tempEmployeeStatistics.setScore(0);
        }
        return tempEmployeeStatistics;
    }

    private Integer scoreCalculator(TempEmployeeStatistics tempEmployeeStatistics) {
        Integer score;
        Double perDayCommission = 1.0;
        if (tempEmployeeStatistics.getTotalCommission() != null && tempEmployeeStatistics.getAttendance() != null) {
            perDayCommission = tempEmployeeStatistics.getTotalCommission() / tempEmployeeStatistics.getAttendance();
        }
        Double perJobKm = 1.0;
        if (tempEmployeeStatistics.getTotalKm() != null && tempEmployeeStatistics.getNoOfJobs() != null && tempEmployeeStatistics.getTotalKm() != 0 && tempEmployeeStatistics.getNoOfJobs() != 0) {
            perJobKm = tempEmployeeStatistics.getTotalKm() / tempEmployeeStatistics.getNoOfJobs();
        }
        score = new Double(perDayCommission * perJobKm).intValue();
        return score;
    }

}

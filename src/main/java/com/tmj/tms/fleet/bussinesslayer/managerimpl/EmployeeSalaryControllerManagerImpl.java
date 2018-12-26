package com.tmj.tms.fleet.bussinesslayer.managerimpl;


import com.querydsl.core.BooleanBuilder;
import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.datalayer.modal.Module;
import com.tmj.tms.config.datalayer.service.CompanyProfileService;
import com.tmj.tms.config.datalayer.service.ModuleService;
import com.tmj.tms.config.datalayer.service.ReportUploadService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.datalayer.modal.ExchangeRate;
import com.tmj.tms.finance.datalayer.modal.FinancialCharge;
import com.tmj.tms.finance.datalayer.modal.FinancialChargeDetail;
import com.tmj.tms.finance.datalayer.service.ExchangeRateService;
import com.tmj.tms.finance.datalayer.service.FinancialChargeDetailService;
import com.tmj.tms.finance.datalayer.service.FinancialChargeService;
import com.tmj.tms.finance.utility.ChargeType;
import com.tmj.tms.finance.utility.ExchangeRateConversion;
import com.tmj.tms.finance.utility.ReferenceType;
import com.tmj.tms.finance.utility.TargetType;
import com.tmj.tms.fleet.bussinesslayer.manager.EmployeeSalaryControllerManager;
import com.tmj.tms.fleet.datalayer.modal.*;
import com.tmj.tms.fleet.datalayer.modal.auxiliary.SalarySearch;
import com.tmj.tms.fleet.datalayer.service.LeaveService;
import com.tmj.tms.fleet.datalayer.service.PayrollService;
import com.tmj.tms.fleet.datalayer.service.SalaryAdvanceService;
import com.tmj.tms.fleet.datalayer.service.SalaryService;
import com.tmj.tms.fleet.utility.CalculationType;
import com.tmj.tms.fleet.utility.PayrollChargeType;
import com.tmj.tms.master.datalayer.modal.Employee;
import com.tmj.tms.master.datalayer.service.EmployeeService;
import com.tmj.tms.master.utility.AddressBookUtils;
import com.tmj.tms.transport.datalayer.modal.QTransportBooking;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.transport.datalayer.modal.TransportBookingStatus;
import com.tmj.tms.transport.datalayer.service.TransportBookingService;
import com.tmj.tms.transport.datalayer.service.TransportBookingStatusService;
import com.tmj.tms.transport.utility.ViaLocationFormatter;
import com.tmj.tms.utility.DateFormatter;
import com.tmj.tms.utility.ResponseObject;
import com.tmj.tms.utility.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeSalaryControllerManagerImpl implements EmployeeSalaryControllerManager {

    private final EmployeeService employeeService;
    private final PayrollService payrollService;
    private final TransportBookingService transportBookingService;
    private final TransportBookingStatusService transportBookingStatusService;
    private final FinancialChargeService financialChargeService;
    private final ExchangeRateConversion exchangeRateConversion;
    private final CompanyProfileService companyProfileService;
    private final ExchangeRateService exchangeRateService;
    private final ModuleService moduleService;
    private final ViaLocationFormatter viaLocationFormatter;
    private final SalaryService salaryService;
    private final LeaveService leaveService;
    private final SalaryAdvanceService salaryAdvanceService;
    private final FinancialChargeDetailService financialChargeDetailService;
    private final HttpSession httpSession;
    private final ReportUploadService reportUploadService;

    @Autowired
    public EmployeeSalaryControllerManagerImpl(EmployeeService employeeService,
                                               PayrollService payrollService,
                                               TransportBookingService transportBookingService,
                                               TransportBookingStatusService transportBookingStatusService,
                                               FinancialChargeService financialChargeService,
                                               ExchangeRateConversion exchangeRateConversion,
                                               CompanyProfileService companyProfileService,
                                               ExchangeRateService exchangeRateService,
                                               ModuleService moduleService,
                                               ViaLocationFormatter viaLocationFormatter,
                                               SalaryService salaryService,
                                               LeaveService leaveService,
                                               SalaryAdvanceService salaryAdvanceService,
                                               FinancialChargeDetailService financialChargeDetailService,
                                               HttpSession httpSession,
                                               ReportUploadService reportUploadService) {
        this.employeeService = employeeService;
        this.payrollService = payrollService;
        this.transportBookingService = transportBookingService;
        this.transportBookingStatusService = transportBookingStatusService;
        this.financialChargeService = financialChargeService;
        this.exchangeRateConversion = exchangeRateConversion;
        this.companyProfileService = companyProfileService;
        this.exchangeRateService = exchangeRateService;
        this.moduleService = moduleService;
        this.viaLocationFormatter = viaLocationFormatter;
        this.salaryService = salaryService;
        this.leaveService = leaveService;
        this.salaryAdvanceService = salaryAdvanceService;
        this.financialChargeDetailService = financialChargeDetailService;
        this.httpSession = httpSession;
        this.reportUploadService = reportUploadService;
    }

    @Override
    public ResponseObject save(SalarySearch salarySearch) {
        Salary salary = this.calculate(salarySearch);
        salary.setRemarks(salarySearch.getRemarks());
        salary = this.salaryService.save(salary);
        for (SalaryCommission salaryCommission : salary.getSalaryCommissionList()) {
            for (SalaryCommissionDetail salaryCommissionDetail : salaryCommission.getSalaryCommissionDetailList()) {
                FinancialChargeDetail financialChargeDetail = this.financialChargeDetailService.findOne(salaryCommissionDetail.getFinancialChargeDetailSeq());
                financialChargeDetail.setEvStatus(MasterDataStatus.APPROVED.getStatusSeq());
                this.financialChargeDetailService.save(financialChargeDetail);
            }
        }
        ResponseObject responseObject = new ResponseObject("Salary Saved Successfully", true);
        responseObject.setObject(salary);
        return responseObject;
    }

    @Override
    public ResponseObject update(Salary salary) {
        return null;
    }

    @Override
    public List<Salary> search(SalarySearch salarySearch) {
        List<Salary> salaryList = null;
        try {
            Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
            BooleanBuilder builder = new BooleanBuilder();
            QSalary salary = QSalary.salary;
            if (salarySearch.getEmployeeSeq() != null) {
                builder.and(salary.employeeSeq.eq(salarySearch.getEmployeeSeq()));
            }
            if (salarySearch.getEmployeeDesignationSeq() != null) {
                builder.and(salary.employeeDesignationSeq.eq(salarySearch.getEmployeeDesignationSeq()));
            }
            if (salarySearch.getSalaryYear() != null) {
                builder.and(salary.salaryYear.eq(salarySearch.getSalaryYear()));
            }
            if (salarySearch.getSalaryMonth() != null) {
                builder.and(salary.salaryMonth.eq(salarySearch.getSalaryMonth()));
            }
            builder.and(salary.companyProfileSeq.eq(companyProfileSeq));
            salaryList = (List<Salary>) this.salaryService.findAll(builder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salaryList;
    }

    @Override
    public Salary findSalary(SalarySearch salarySearch) {
        Salary salary = new Salary();
        Salary dbSalary = this.salaryService.findByEmployeeSeqAndSalaryYearAndSalaryMonthAndStatus(salarySearch.getEmployeeSeq(), salarySearch.getSalaryYear(),
                salarySearch.getSalaryMonth(), MasterDataStatus.APPROVED.getStatusSeq());
        if (dbSalary == null) {
            salary.setStartDate(salarySearch.getStartDate());
            salary.setEndDate(salarySearch.getEndDate());
            salary.setSalaryMonth(salarySearch.getSalaryMonth());
            salary.setSalaryYear(salarySearch.getSalaryYear());
            Employee employee = this.employeeService.findOne(salarySearch.getEmployeeSeq());
            salary.setEmployeeSeq(employee.getEmployeeSeq());
            salary.setEmployee(employee);
            salary.setEmployeeDesignation(employee.getEmployeeDesignation());
            salary.setEmployeeDesignationSeq(employee.getEmployeeDesignationSeq());
            Payroll payroll = this.payrollService.findByEmployeeSeqAndStatus(salarySearch.getEmployeeSeq(), MasterDataStatus.APPROVED.getStatusSeq());
            salary.setPayroll(payroll);
            salary.setPayrollSeq(payroll.getPayrollSeq());
            salary.setBasicSalary(payroll.getBasicSalary());
            salary.setCompanyProfileSeq(payroll.getCompanyProfileSeq());
            salary.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
            CompanyProfile companyProfile = this.companyProfileService.findByCompanyProfileSeq(payroll.getCompanyProfileSeq());
            salary.setCurrencySeq(companyProfile.getLocalCurrencySeq());
            Double totalAllowance = 0.0;

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, salary.getSalaryYear());
            calendar.set(Calendar.MONTH, salary.getSalaryMonth() - 1);
            Integer noOfDays;
            Calendar monthEnd = Calendar.getInstance();
            monthEnd.set(Calendar.YEAR, salary.getSalaryYear());
            monthEnd.set(Calendar.MONTH, salary.getSalaryMonth() - 1);
            monthEnd.set(Calendar.DATE, monthEnd.getActualMaximum(Calendar.DATE));
            if (DateFormatter.getEndOfTheDay(monthEnd.getTime()).before(new Date())) {
                noOfDays = calendar.getActualMaximum(Calendar.DATE);
            } else {
                noOfDays = calendar.get(Calendar.DAY_OF_MONTH);
            }
            List<Leave> leaveList = this.leaveService.findByEmployeeSeqAndLeaveYearAndLeaveMonthAndStatusNot(salary.getEmployeeSeq(), salary.getSalaryYear(), salary.getSalaryMonth(), MasterDataStatus.DELETED.getStatusSeq());
            Integer noOfLeaves = leaveList.stream().mapToInt(Leave::getNoOfDays).sum();
            salary.setAttendance(noOfDays - noOfLeaves);
            salary.setDaysOfMonth(noOfDays);
            if (payroll.getPayrollAllowanceList() != null && payroll.getPayrollAllowanceList().size() > 0) {
                List<SalaryAllowance> salaryAllowanceList = new ArrayList<>();
                for (PayrollAllowance payrollAllowance : payroll.getPayrollAllowanceList()) {
                    SalaryAllowance salaryAllowance = new SalaryAllowance();
                    salaryAllowance.setPayrollAllowanceSeq(payrollAllowance.getPayrollAllowanceSeq());
                    salaryAllowance.setCalculationType(payrollAllowance.getPayrollChargeConfig().getCalculationType());
                    salaryAllowance.setChargeSeq(payrollAllowance.getChargeSeq());
                    salaryAllowance.setCharge(payrollAllowance.getCharge());
                    salaryAllowance.setChargeValue(payrollAllowance.getPayrollChargeConfig().getChargeValue());
                    salaryAllowance.setMultiplyValue(this.setMultiplyValue(salaryAllowance.getCalculationType(), PayrollChargeType.ALLOWANCE.getPayrollChargeType(),
                            salary.getBasicSalary(), salary, payrollAllowance.getPayrollChargeConfig().getThreshold()));
                    if (salaryAllowance.getMultiplyValue() > 0) {
                        salaryAllowance.setAllowance(salaryAllowance.getMultiplyValue() * salaryAllowance.getChargeValue());
                        salaryAllowance.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
                        salaryAllowanceList.add(salaryAllowance);
                        totalAllowance = totalAllowance + salaryAllowance.getAllowance();
                    }
                }
                salary.setSalaryAllowanceList(salaryAllowanceList);
            }
            salary.setTotalAllowance(totalAllowance);

            Double totalDeduction = 0.0;
            if (payroll.getPayrollDeductionList() != null && payroll.getPayrollDeductionList().size() > 0) {
                List<SalaryDeduction> salaryDeductionList = new ArrayList<>();
                for (PayrollDeduction payrollDeduction : payroll.getPayrollDeductionList()) {
                    SalaryDeduction salaryDeduction = new SalaryDeduction();
                    salaryDeduction.setPayrollDeductionSeq(payrollDeduction.getPayrollDeductionSeq());
                    salaryDeduction.setCalculationType(payrollDeduction.getPayrollChargeConfig().getCalculationType());
                    salaryDeduction.setChargeSeq(payrollDeduction.getChargeSeq());
                    salaryDeduction.setCharge(payrollDeduction.getCharge());
                    salaryDeduction.setChargeValue(payrollDeduction.getPayrollChargeConfig().getChargeValue());
                    salaryDeduction.setMultiplyValue(this.setMultiplyValue(salaryDeduction.getCalculationType(), PayrollChargeType.DEDUCTION.getPayrollChargeType(),
                            salary.getBasicSalary(), salary, payrollDeduction.getPayrollChargeConfig().getThreshold()));
                    salaryDeduction.setDeduction(salaryDeduction.getMultiplyValue() * salaryDeduction.getChargeValue());
                    salaryDeduction.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
                    if(salaryDeduction.getMultiplyValue() > 0){
                        salaryDeductionList.add(salaryDeduction);
                        totalDeduction = totalDeduction + salaryDeduction.getDeduction();
                    }
                }
                salary.setSalaryDeductionList(salaryDeductionList);
            }
            salary.setTotalDeduction(totalDeduction);

            Double companyContribution = 0.0;
            if (payroll.getPayrollCompanyContributionList() != null && payroll.getPayrollCompanyContributionList().size() > 0) {
                List<SalaryCompanyContribution> salaryCompanyContributionList = new ArrayList<>();
                for (PayrollCompanyContribution payrollCompanyContribution : payroll.getPayrollCompanyContributionList()) {
                    SalaryCompanyContribution salaryCompanyContribution = new SalaryCompanyContribution();
                    salaryCompanyContribution.setPayrollCompanyContributionSeq(payrollCompanyContribution.getPayrollCompanyContributionSeq());
                    salaryCompanyContribution.setCalculationType(payrollCompanyContribution.getPayrollChargeConfig().getCalculationType());
                    salaryCompanyContribution.setChargeSeq(payrollCompanyContribution.getChargeSeq());
                    salaryCompanyContribution.setCharge(payrollCompanyContribution.getCharge());
                    salaryCompanyContribution.setChargeValue(payrollCompanyContribution.getPayrollChargeConfig().getChargeValue());
                    salaryCompanyContribution.setMultiplyValue(this.setMultiplyValue(salaryCompanyContribution.getCalculationType(), PayrollChargeType.COMPANY_CONTRIBUTION.getPayrollChargeType(),
                            salary.getBasicSalary(), salary, payrollCompanyContribution.getPayrollChargeConfig().getThreshold()));
                    salaryCompanyContribution.setCompanyContribution(salaryCompanyContribution.getMultiplyValue() * salaryCompanyContribution.getChargeValue());
                    salaryCompanyContribution.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
                    salaryCompanyContributionList.add(salaryCompanyContribution);
                    companyContribution = companyContribution + salaryCompanyContribution.getCompanyContribution();
                }
                salary.setSalaryCompanyContributionList(salaryCompanyContributionList);
            }
            salary.setTotalCompanyContribution(companyContribution);

            Double totalCommission = 0.0;
            List<TransportBooking> transportBookingList = this.getTransportBookingList(salary);
            if (transportBookingList != null && transportBookingList.size() > 0) {
                Module transport = this.moduleService.findByModuleCode("TRANSPORT");
                List<FinancialChargeDetail> financialChargeDetailList = this.getFinancialChargeDetailList(transportBookingList, salary);
                if (payroll.getPayrollCommissionList() != null && payroll.getPayrollCommissionList().size() > 0) {
                    List<SalaryCommission> salaryCommissionList = new ArrayList<>();
                    for (PayrollCommission payrollCommission : payroll.getPayrollCommissionList()) {
                        SalaryCommission salaryCommission = new SalaryCommission();
                        salaryCommission.setPayrollCommissionSeq(payrollCommission.getPayrollCommissionSeq());
                        salaryCommission.setCalculationType(payrollCommission.getPayrollChargeConfig().getCalculationType());
                        salaryCommission.setChargeSeq(payrollCommission.getChargeSeq());
                        salaryCommission.setCharge(payrollCommission.getCharge());
                        salaryCommission.setChargeValue(payrollCommission.getPayrollChargeConfig().getChargeValue());
                        salaryCommission.setMultiplyValue(this.setMultiplyValue(salaryCommission.getCalculationType(), PayrollChargeType.COMMISSION.getPayrollChargeType(),
                                salary.getBasicSalary(), salary, payrollCommission.getPayrollChargeConfig().getThreshold()));
                        List<FinancialChargeDetail> commissionChargeList = financialChargeDetailList.parallelStream().filter(x -> x.getChargeSeq().equals(salaryCommission.getChargeSeq()) && x.getEvStatus().equals(MasterDataStatus.OPEN.getStatusSeq())).collect(Collectors.toList());
                        if (commissionChargeList != null && commissionChargeList.size() > 0) {
                            Double commission = commissionChargeList.parallelStream().mapToDouble(FinancialChargeDetail::getAmount).sum();
                            salaryCommission.setCommission(commission);
                            List<SalaryCommissionDetail> salaryCommissionDetailList = new ArrayList<>();
                            for (FinancialChargeDetail financialChargeDetail : commissionChargeList) {
                                SalaryCommissionDetail salaryCommissionDetail = new SalaryCommissionDetail();
                                FinancialCharge financialCharge = this.financialChargeService.findOne(financialChargeDetail.getFinancialChargeSeq());
                                salaryCommissionDetail.setModuleSeq(financialCharge.getModuleSeq());
                                salaryCommissionDetail.setModule(financialCharge.getModule());
                                salaryCommissionDetail.setFinancialChargeDetailSeq(financialChargeDetail.getFinancialChargeDetailSeq());
                                salaryCommissionDetail.setReferenceTypeDescription(financialCharge.getReferenceTypeDescription());
                                salaryCommissionDetail.setTargetType(financialCharge.getTargetType());
                                salaryCommissionDetail.setReferenceType(financialCharge.getReferenceType());
                                salaryCommissionDetail.setReferenceSeq(financialCharge.getReferenceSeq());
                                salaryCommissionDetail.setReferenceNo(financialCharge.getReferenceNo());
                                salaryCommissionDetail.setAmount(financialChargeDetail.getAmount());
                                salaryCommissionDetail.setTotalKm(financialChargeDetail.getQuantity());
                                salaryCommissionDetail.setRate(financialChargeDetail.getChargeValue());
                                salaryCommissionDetail.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
                                if (financialCharge.getModuleSeq().equals(transport.getModuleSeq())) {
                                    TransportBooking booking = this.transportBookingService.findOne(financialCharge.getReferenceSeq());
                                    booking.setViaLocationString(this.viaLocationFormatter.getViaLocationAsString(booking.getTransportBookingViaLocationList()));
                                    salaryCommissionDetail.setTransportBooking(booking);
                                }
                                salaryCommissionDetailList.add(salaryCommissionDetail);
                            }
                            salaryCommission.setSalaryCommissionDetailList(salaryCommissionDetailList);
                        }
                        salaryCommission.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
                        if (salaryCommission.getCommission() != null && salaryCommission.getCommission() > 0) {
                            salaryCommissionList.add(salaryCommission);
                            totalCommission = totalCommission + salaryCommission.getCommission();
                        }
                    }
                    salary.setSalaryCommissionList(salaryCommissionList);
                }
            }
            salary.setTotalCommission(totalCommission);
            List<SalaryAdvance> salaryAdvanceList = this.salaryAdvanceService.findByEmployeeSeqAndSalaryYearAndSalaryMonthAndStatus(salary.getEmployeeSeq(),
                    salary.getSalaryYear(), salary.getSalaryMonth(), MasterDataStatus.APPROVED.getStatusSeq());
            salary.setSalaryAdvanceList(salaryAdvanceList);
            if (salaryAdvanceList != null) {
                salary.setTotalSalaryAdvance(salaryAdvanceList.stream().mapToDouble(SalaryAdvance::getSalaryAdvance).sum());
            } else {
                salary.setTotalSalaryAdvance(0.0);
            }
            Double netPay = salary.getBasicSalary() + salary.getTotalAllowance() + salary.getTotalCommission() - salary.getTotalDeduction() - salary.getTotalSalaryAdvance();
            salary.setNetPay(netPay);
        }
        return salary;
    }

    private Double setMultiplyValue(Integer calculationType, Integer payrollChargeType, Double percentageValue, Salary salary, Double threshold) {
        Double multiplyValue;
        Integer noOfLeaves = salary.getDaysOfMonth() - salary.getAttendance();
        if (calculationType.equals(CalculationType.DAILY_FIXED.getCalculationType())) {
            if (payrollChargeType.equals(PayrollChargeType.DEDUCTION.getPayrollChargeType())) {
                if (threshold != null && (noOfLeaves > threshold)) {
                    multiplyValue = noOfLeaves - threshold; // No of Leave deduction
                } else {
                    multiplyValue = 0.0;
                }
            } else {
                multiplyValue = salary.getAttendance().doubleValue(); // No of Days worked
            }
        } else if (calculationType.equals(CalculationType.PERCENTAGE.getCalculationType())) {
            multiplyValue = percentageValue * 0.01;
        } else if (calculationType.equals(CalculationType.MONTHLY_FIXED.getCalculationType())) {
            if (payrollChargeType.equals(PayrollChargeType.ALLOWANCE.getPayrollChargeType()) && threshold != null && threshold.equals(0.0)) {
                if (noOfLeaves.equals(0)) {
                    multiplyValue = 1.0;
                } else {
                    multiplyValue = 0.0;
                }
            } else {
                multiplyValue = 1.0;
            }
        } else {
            multiplyValue = 1.0;
        }
        return multiplyValue;
    }

    @Override
    public List<TransportBooking> getTransportBookingList(Salary salary) {
        List<TransportBooking> transportBookingList = null;
        try {
            QTransportBooking transportBooking = QTransportBooking.transportBooking;
            BooleanBuilder builder = new BooleanBuilder();
            builder.and(transportBooking.companyProfileSeq.eq(salary.getCompanyProfileSeq()));
            builder.and(transportBooking.transportBookingVehicleList.isNotEmpty().and(transportBooking.transportBookingVehicleList.any().transportCompanySeq.eq(salary.getEmployee().getStakeholderSeq())));
            if (salary.getEmployeeDesignation().getDesignation().equals("DRIVER")) {
                builder.and(transportBooking.transportBookingVehicleList.isNotEmpty().and(transportBooking.transportBookingVehicleList.any().driverSeq.eq(salary.getEmployeeSeq())));
            }
            if (salary.getEmployeeDesignation().getDesignation().equals("HELPER")) {
                builder.and(transportBooking.transportBookingVehicleList.isNotEmpty().and(transportBooking.transportBookingVehicleList.any().helperSeq.eq(salary.getEmployeeSeq())));
            }
            builder.and(transportBooking.transportBookingFeedback.departedFromDelivery.between(salary.getStartDate(), DateFormatter.getEndOfTheDay(salary.getEndDate())));

            List<TransportBookingStatus> transportBookingStatusList = this.transportBookingStatusService.findByInvoiceable(YesOrNo.Yes.getYesOrNoSeq());
            List<Integer> bookingStatusSeqList = transportBookingStatusList.stream().map(TransportBookingStatus::getCurrentStatus).collect(Collectors.toList());
            builder.and(transportBooking.currentStatus.in(bookingStatusSeqList));
            transportBookingList = (List<TransportBooking>) this.transportBookingService.findAll(builder);
            transportBookingList = transportBookingList.stream().sorted(Comparator.comparing(TransportBooking::getRequestedArrivalTime)).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transportBookingList;
    }

    @Override
    public List<FinancialChargeDetail> getFinancialChargeDetailList(List<TransportBooking> transportBookingList, Salary salary) {
        List<FinancialChargeDetail> financialChargeDetailList = new ArrayList<>();
        try {
            List<Integer> chargeSeqList = new ArrayList<>();
            for (PayrollCommission payrollCommission : salary.getPayroll().getPayrollCommissionList()) {
                chargeSeqList.add(payrollCommission.getChargeSeq());
            }

            for (TransportBooking transportBooking : transportBookingList) {
                FinancialCharge financialCharge = this.financialChargeService.findByTargetTypeAndReferenceTypeAndReferenceSeqAndModuleSeqAndStatusNot(TargetType.TRANSPORT_JOB.getTargetType(), ReferenceType.TRANSPORT_BOOKING.getReferenceType(), transportBooking.getTransportBookingSeq(), transportBooking.getModuleSeq(), MasterDataStatus.DELETED.getStatusSeq());
                if (financialCharge != null) {
                    List<FinancialChargeDetail> tempList = financialCharge.getFinancialChargeDetails()
                            .stream().filter(x ->
                                    x.getChargeType().equals(ChargeType.COST.getChargeType()) &&
                                            x.getEvStatus().equals(MasterDataStatus.OPEN.getStatusSeq()) &&
                                            chargeSeqList.contains(x.getChargeSeq())
                            ).collect(Collectors.toList());
                    if (tempList != null && tempList.size() > 0) {
                        financialChargeDetailList.addAll(tempList);
                    }
                }
            }
            ExchangeRate exchangeRate = this.exchangeRateService.findFirstByStatusAndCompanyProfileSeq(MasterDataStatus.APPROVED.getStatusSeq(), salary.getCompanyProfileSeq());
            CompanyProfile companyProfile = this.companyProfileService.findByCompanyProfileSeq(salary.getCompanyProfileSeq());
            financialChargeDetailList = this.exchangeRateConversion.dynamicConversion(financialChargeDetailList, exchangeRate.getExchangeRateSeq(), companyProfile.getLocalCurrencySeq());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return financialChargeDetailList;
    }

    @Override
    public Salary calculate(SalarySearch salarySearch) {
        Salary salary = this.findSalary(salarySearch);
        List<SalaryAllowance> salaryAllowanceList = salary.getSalaryAllowanceList();
        if (salaryAllowanceList != null && salaryAllowanceList.size() > 0) {
            List<SalaryAllowance> removeList = new ArrayList<>();
            for (SalaryAllowance salaryAllowance : salaryAllowanceList) {
                if (!salarySearch.getSalaryAllowanceChargeSeq().contains(salaryAllowance.getChargeSeq())) {
                    removeList.add(salaryAllowance);
                }
            }
            if (removeList.size() > 0) {
                salaryAllowanceList.removeAll(removeList);
                salary.setSalaryAllowanceList(salaryAllowanceList);
                Double totalAllowance = 0.0;
                for (SalaryAllowance salaryAllowance : salaryAllowanceList) {
                    totalAllowance = totalAllowance + salaryAllowance.getAllowance();
                }
                salary.setTotalAllowance(totalAllowance);
            }
        }

        List<SalaryCommission> salaryCommissionList = salary.getSalaryCommissionList();
        if (salaryCommissionList != null && salaryCommissionList.size() > 0) {
            List<SalaryCommission> removeList = new ArrayList<>();
            for (SalaryCommission salaryCommission : salaryCommissionList) {
                if (!salarySearch.getSalaryCommissionChargeSeq().contains(salaryCommission.getChargeSeq())) {
                    removeList.add(salaryCommission);
                }
            }
            if (removeList.size() > 0) {
                salaryCommissionList.removeAll(removeList);
                salary.setSalaryCommissionList(salaryCommissionList);
                Double totalCommission = 0.0;
                for (SalaryCommission salaryCommission : salaryCommissionList) {
                    totalCommission = totalCommission + salaryCommission.getCommission();
                }
                salary.setTotalCommission(totalCommission);
            }
        }

        List<SalaryDeduction> salaryDeductionList = salary.getSalaryDeductionList();
        if (salaryDeductionList != null && salaryDeductionList.size() > 0) {
            List<SalaryDeduction> removeList = new ArrayList<>();
            for (SalaryDeduction salaryDeduction : salaryDeductionList) {
                if (!salarySearch.getSalaryDeductionChargeSeq().contains(salaryDeduction.getChargeSeq())) {
                    removeList.add(salaryDeduction);
                }
            }
            if (removeList.size() > 0) {
                salaryDeductionList.removeAll(removeList);
                salary.setSalaryDeductionList(salaryDeductionList);
                Double totalDeduction = 0.0;
                for (SalaryDeduction salaryDeduction : salaryDeductionList) {
                    totalDeduction = totalDeduction + salaryDeduction.getDeduction();
                }
                salary.setTotalDeduction(totalDeduction);
            }
        }

        List<SalaryCompanyContribution> salaryCompanyContributionList = salary.getSalaryCompanyContributionList();
        if (salaryCompanyContributionList != null && salaryCompanyContributionList.size() > 0) {
            List<SalaryCompanyContribution> removeList = new ArrayList<>();
            for (SalaryCompanyContribution salaryCompanyContribution : salaryCompanyContributionList) {
                if (!salarySearch.getSalaryCompanyContributionChargeSeq().contains(salaryCompanyContribution.getChargeSeq())) {
                    removeList.add(salaryCompanyContribution);
                }
            }
            if (removeList.size() > 0) {
                salaryCompanyContributionList.removeAll(removeList);
                salary.setSalaryCompanyContributionList(salaryCompanyContributionList);
                Double totalCompanyContribution = 0.0;
                for (SalaryCompanyContribution salaryCompanyContribution : salaryCompanyContributionList) {
                    totalCompanyContribution = totalCompanyContribution + salaryCompanyContribution.getCompanyContribution();
                }
                salary.setTotalCompanyContribution(totalCompanyContribution);
            }
        }

        Double netPay = salary.getBasicSalary() + salary.getTotalAllowance() + salary.getTotalCommission() - salary.getTotalDeduction() - salary.getTotalSalaryAdvance();
        salary.setNetPay(netPay);
        return salary;
    }

    @Override
    public ResponseObject delete(Integer salarySeq) {
        Salary dbSalary = this.salaryService.findOne(salarySeq);
        dbSalary.setStatus(MasterDataStatus.DELETED.getStatusSeq());
        for (SalaryCommission salaryCommission : dbSalary.getSalaryCommissionList()) {
            for (SalaryCommissionDetail salaryCommissionDetail : salaryCommission.getSalaryCommissionDetailList()) {
                FinancialChargeDetail financialChargeDetail = this.financialChargeDetailService.findOne(salaryCommissionDetail.getFinancialChargeDetailSeq());
                financialChargeDetail.setEvStatus(MasterDataStatus.OPEN.getStatusSeq());
                this.financialChargeDetailService.save(financialChargeDetail);
            }
        }
        this.salaryService.save(dbSalary);
        return new ResponseObject("Successfully Deleted", true);
    }

    @Override
    public Integer getSalaryReport(Integer salarySeq, Integer reportSeq, Integer pdf, Integer rtf, Integer xls,
                                   HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Principal principal) {
        Integer reportUploadSeq = null;
        String moduleName = "fleet";
        try {
            DateFormatter dateFormatter = new DateFormatter();
            Salary salary = this.salaryService.findOne(salarySeq);
            Map<String, Object> param = new HashMap<>();
            param.put("SALARY_SEQ", salary.getSalarySeq());
            param.put("BASIC_SALARY", salary.getBasicSalary());
            param.put("SALARY_ADVANCE", salary.getTotalSalaryAdvance());
            param.put("ALLOWANCE", salary.getTotalAllowance());
            param.put("COMMISSION", salary.getTotalCommission());
            param.put("DEDUCTION", salary.getTotalDeduction());
            param.put("COMPANY_CONTRIBUTION", salary.getTotalCompanyContribution());
            param.put("ATTENDANCE", salary.getAttendance());
            param.put("NET_PAY", salary.getNetPay());
            param.put("EMPLOYEE_NAME", salary.getEmployee().getEmployeeName());
            param.put("EMPLOYEE_ADDRESS", AddressBookUtils.addressBookForInvoice(salary.getEmployee().getAddressBook()));
            param.put("DESIGNATION", salary.getEmployeeDesignation().getDescription());
            param.put("REMARKS", salary.getRemarks());
            param.put("YEAR_MONTH", salary.getSalaryYear() + "/" + DateFormatter.getMonth(salary.getSalaryMonth()));
            param.put("CREATED_DATE", dateFormatter.returnLongFormattedDateTime(salary.getCreatedDate()));
            param.put("CREATED_BY", salary.getCreatedBy());
            param.put("PRINTED_BY", principal.getName());
            param.put("PRINTED_DATE", dateFormatter.returnLongFormattedDateTime(new Date()));
            param.put("START_DATE", dateFormatter.returnSortFormattedDate(salary.getStartDate()));
            param.put("END_DATE", dateFormatter.returnSortFormattedDate(salary.getEndDate()));
            String fileName = salary.getEmployee().getNicNo() + "-" + salary.getSalaryYear() + "" + salary.getSalaryMonth();
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

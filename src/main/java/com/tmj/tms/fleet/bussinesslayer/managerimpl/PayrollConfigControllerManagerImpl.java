package com.tmj.tms.fleet.bussinesslayer.managerimpl;

import com.querydsl.core.BooleanBuilder;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.fleet.bussinesslayer.manager.PayrollConfigControllerManager;
import com.tmj.tms.fleet.datalayer.modal.*;
import com.tmj.tms.fleet.datalayer.service.PayrollChargeConfigService;
import com.tmj.tms.fleet.datalayer.service.PayrollService;
import com.tmj.tms.fleet.utility.PayrollChargeType;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class PayrollConfigControllerManagerImpl implements PayrollConfigControllerManager {

    private final PayrollService payrollService;
    private final HttpSession httpSession;
    private final PayrollChargeConfigService payrollChargeConfigService;

    @Autowired
    public PayrollConfigControllerManagerImpl(PayrollService payrollService,
                                              HttpSession httpSession,
                                              PayrollChargeConfigService payrollChargeConfigService) {
        this.payrollService = payrollService;
        this.httpSession = httpSession;
        this.payrollChargeConfigService = payrollChargeConfigService;
    }

    @Override
    public ResponseObject save(Payroll payroll) {
        Integer companyProfileSeq = Integer.parseInt(httpSession.getAttribute("companyProfileSeq").toString());
        ResponseObject responseObject = this.validate(payroll);
        if (responseObject.isStatus()) {
            payroll.setCompanyProfileSeq(companyProfileSeq);
            payroll.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
            this.initPayroll(payroll);
            payroll = this.payrollService.save(payroll);
            responseObject = new ResponseObject("Payroll Saved Successfully", true);
            responseObject.setObject(payroll);
        }
        return responseObject;
    }

    @Override
    public ResponseObject update(Payroll payroll) {
        Payroll payrollDB = this.payrollService.findOne(payroll.getPayrollSeq());
        ResponseObject responseObject;
        if (payrollDB != null) {
            payrollDB.setAllowanceSeqList(payroll.getAllowanceSeqList());
            payrollDB.setCommissionSeqList(payroll.getCommissionSeqList());
            payrollDB.setCompanyContributionSeqList(payroll.getCompanyContributionSeqList());
            payrollDB.setDeductionSeqList(payroll.getDeductionSeqList());
            this.initPayroll(payrollDB);
            payrollDB.setBasicSalary(payroll.getBasicSalary());
            payrollDB = this.payrollService.save(payrollDB);
            responseObject = new ResponseObject("Payroll Updated Successfully", true);
            responseObject.setObject(payrollDB);
        } else {
            responseObject = new ResponseObject("Payroll not found", false);
        }
        return responseObject;
    }

    @Override
    public List<Payroll> search(String employeeName, Integer status, Integer employeeDesignationSeq) {
        List<Payroll> payrollList = null;
        try {
            Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
            BooleanBuilder builder = new BooleanBuilder();
            QPayroll payroll = QPayroll.payroll;
            if (!employeeName.isEmpty()) {
                builder.and(payroll.employee.employeeName.eq(employeeName));
            }
            if (status != -1) {
                builder.and(payroll.employee.status.eq(status));
            } else {
                builder.and(payroll.employee.status.in(MasterDataStatus.OPEN.getStatusSeq(), MasterDataStatus.APPROVED.getStatusSeq()));
            }
            if (employeeDesignationSeq != -1) {
                builder.and(payroll.employee.employeeDesignationSeq.eq(employeeDesignationSeq));
            }
            builder.and(payroll.companyProfileSeq.eq(companyProfileSeq));
            payrollList = (List<Payroll>) this.payrollService.findAll(builder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return payrollList;
    }

    private void initPayroll(Payroll payroll) {
        if (payroll.getAllowanceSeqList() != null) {
            List<PayrollAllowance> payrollAllowanceList = new ArrayList<>();
            for (Integer allowanceSeq : payroll.getAllowanceSeqList()) {
                PayrollChargeConfig payrollChargeConfig = this.payrollChargeConfigService.findByCompanyProfileSeqAndPayrollChargeTypeAndChargeSeqAndStatus(payroll.getCompanyProfileSeq(), PayrollChargeType.ALLOWANCE.getPayrollChargeType(), allowanceSeq, MasterDataStatus.APPROVED.getStatusSeq());
                if (payrollChargeConfig != null) {
                    PayrollAllowance payrollAllowance = new PayrollAllowance();
                    payrollAllowance.setPayrollSeq(payroll.getPayrollSeq());
                    payrollAllowance.setChargeSeq(allowanceSeq);
                    payrollAllowance.setPayrollSeq(payroll.getPayrollSeq());
                    payrollAllowance.setPayrollChargeConfigSeq(payrollChargeConfig.getPayrollChargeConfigSeq());
                    payrollAllowance.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
                    payrollAllowanceList.add(payrollAllowance);
                }
            }
            if (payrollAllowanceList.size() > 0) {
                if (payroll.getPayrollAllowanceList() != null) {
                    payroll.getPayrollAllowanceList().clear();
                    payroll.getPayrollAllowanceList().addAll(payrollAllowanceList);
                } else {
                    payroll.setPayrollAllowanceList(payrollAllowanceList);
                }
            }
        }

        if (payroll.getDeductionSeqList() != null) {
            List<PayrollDeduction> payrollDeductionList = new ArrayList<>();
            for (Integer deductionSeq : payroll.getDeductionSeqList()) {
                PayrollChargeConfig payrollChargeConfig = this.payrollChargeConfigService.findByCompanyProfileSeqAndPayrollChargeTypeAndChargeSeqAndStatus(payroll.getCompanyProfileSeq(), PayrollChargeType.DEDUCTION.getPayrollChargeType(), deductionSeq, MasterDataStatus.APPROVED.getStatusSeq());
                if (payrollChargeConfig != null) {
                    PayrollDeduction payrollDeduction = new PayrollDeduction();
                    payrollDeduction.setPayrollSeq(payroll.getPayrollSeq());
                    payrollDeduction.setChargeSeq(deductionSeq);
                    payrollDeduction.setPayrollSeq(payroll.getPayrollSeq());
                    payrollDeduction.setPayrollChargeConfigSeq(payrollChargeConfig.getPayrollChargeConfigSeq());
                    payrollDeduction.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
                    payrollDeductionList.add(payrollDeduction);
                }
            }
            if (payrollDeductionList.size() > 0) {
                if (payroll.getPayrollDeductionList() != null) {
                    payroll.getPayrollDeductionList().clear();
                    payroll.getPayrollDeductionList().addAll(payrollDeductionList);
                } else {
                    payroll.setPayrollDeductionList(payrollDeductionList);
                }
            }
        }

        if (payroll.getCommissionSeqList() != null) {
            List<PayrollCommission> payrollCommissionList = new ArrayList<>();
            for (Integer commissionSeq : payroll.getCommissionSeqList()) {
                PayrollChargeConfig payrollChargeConfig = this.payrollChargeConfigService.findByCompanyProfileSeqAndPayrollChargeTypeAndChargeSeqAndStatus(payroll.getCompanyProfileSeq(), PayrollChargeType.COMMISSION.getPayrollChargeType(), commissionSeq, MasterDataStatus.APPROVED.getStatusSeq());
                if (payrollChargeConfig != null) {
                    PayrollCommission payrollCommission = new PayrollCommission();
                    payrollCommission.setPayrollSeq(payroll.getPayrollSeq());
                    payrollCommission.setPayrollChargeConfigSeq(payrollChargeConfig.getPayrollChargeConfigSeq());
                    payrollCommission.setChargeSeq(commissionSeq);
                    payrollCommission.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
                    payrollCommissionList.add(payrollCommission);
                }
            }
            if (payrollCommissionList.size() > 0) {
                if (payroll.getPayrollCommissionList() != null) {
                    payroll.getPayrollCommissionList().clear();
                    payroll.getPayrollCommissionList().addAll(payrollCommissionList);
                } else {
                    payroll.setPayrollCommissionList(payrollCommissionList);
                }
            }
        }

        if (payroll.getCompanyContributionSeqList() != null) {
            List<PayrollCompanyContribution> payrollCompanyContributionList = new ArrayList<>();
            for (Integer companyContributionSeq : payroll.getCompanyContributionSeqList()) {
                PayrollChargeConfig payrollChargeConfig = this.payrollChargeConfigService.findByCompanyProfileSeqAndPayrollChargeTypeAndChargeSeqAndStatus(payroll.getCompanyProfileSeq(), PayrollChargeType.COMPANY_CONTRIBUTION.getPayrollChargeType(), companyContributionSeq, MasterDataStatus.APPROVED.getStatusSeq());
                if (payrollChargeConfig != null) {
                    PayrollCompanyContribution payrollCompanyContribution = new PayrollCompanyContribution();
                    payrollCompanyContribution.setPayrollSeq(payroll.getPayrollSeq());
                    payrollCompanyContribution.setPayrollChargeConfigSeq(payrollChargeConfig.getPayrollChargeConfigSeq());
                    payrollCompanyContribution.setChargeSeq(companyContributionSeq);
                    payrollCompanyContribution.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
                    payrollCompanyContributionList.add(payrollCompanyContribution);
                }
            }
            if (payrollCompanyContributionList.size() > 0) {
                if (payroll.getPayrollCompanyContributionList() != null) {
                    payroll.getPayrollCompanyContributionList().clear();
                    payroll.getPayrollCompanyContributionList().addAll(payrollCompanyContributionList);
                } else {
                    payroll.setPayrollCompanyContributionList(payrollCompanyContributionList);
                }
            }
        }
    }

    @Override
    public ResponseObject validate(Payroll payroll) {
        ResponseObject responseObject = new ResponseObject(true, "");
        Payroll payrollDB = this.payrollService.findByEmployeeSeqAndStatus(payroll.getEmployeeSeq(), MasterDataStatus.APPROVED.getStatusSeq());
        if (payrollDB != null) {
            responseObject.setStatus(false);
            responseObject.setMessage("Payroll has been already Configured!!");
        }
        return responseObject;
    }
}

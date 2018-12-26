package com.tmj.tms.finance.zoho.schedule;

import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.datalayer.service.CompanyProfileService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.datalayer.modal.ChartOfAccount;
import com.tmj.tms.finance.datalayer.modal.ZohoIntegration;
import com.tmj.tms.finance.datalayer.service.ChartOfAccountService;
import com.tmj.tms.finance.zoho.api.ExpenseApi;
import com.tmj.tms.finance.zoho.model.Expense;
import com.tmj.tms.finance.zoho.util.ZohoAccessTokenGenerator;
import com.tmj.tms.fleet.datalayer.modal.Salary;
import com.tmj.tms.fleet.datalayer.modal.SalaryAdvance;
import com.tmj.tms.fleet.datalayer.service.SalaryAdvanceService;
import com.tmj.tms.fleet.datalayer.service.SalaryService;
import com.tmj.tms.utility.DateFormatter;
import com.tmj.tms.utility.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Profile(value = "disabled")
public class SalaryIntegration {

    private final CompanyProfileService companyProfileService;
    private final ZohoAccessTokenGenerator zohoAccessTokenGenerator;
    private final ExpenseApi expenseApi;
    private final ChartOfAccountService chartOfAccountService;
    private final SalaryService salaryService;
    private final SalaryAdvanceService salaryAdvanceService;

    @Autowired
    public SalaryIntegration(CompanyProfileService companyProfileService,
                             ZohoAccessTokenGenerator zohoAccessTokenGenerator,
                             ExpenseApi expenseApi,
                             ChartOfAccountService chartOfAccountService,
                             SalaryService salaryService,
                             SalaryAdvanceService salaryAdvanceService) {
        this.companyProfileService = companyProfileService;
        this.zohoAccessTokenGenerator = zohoAccessTokenGenerator;
        this.expenseApi = expenseApi;
        this.chartOfAccountService = chartOfAccountService;
        this.salaryService = salaryService;
        this.salaryAdvanceService = salaryAdvanceService;
    }

    @Scheduled(fixedRate = 60000 * 15)
    @Transactional
    public void insertOrUpdateExpense() {
        DateFormatter dateFormatter = new DateFormatter();
        List<CompanyProfile> companyProfileList = this.companyProfileService.findByStatusNot(MasterDataStatus.DELETED.getStatusSeq());
        for (CompanyProfile companyProfile : companyProfileList) {
            try {
                ZohoIntegration zohoIntegration = this.zohoAccessTokenGenerator.tokenUpdater(companyProfile.getCompanyProfileSeq());
                List<Salary> salaryList = this.salaryService.findFirst100ByCompanyProfileSeqAndFinanceIntegrationIsNullAndStatus(companyProfile.getCompanyProfileSeq(), MasterDataStatus.APPROVED.getStatusSeq());
                for (Salary salary : salaryList) {
                    try {
                        Expense expense = this.initFromSalary(salary, dateFormatter);
                        if (salary.getFinanceIntegrationKey() == null) {
                            expense = this.expenseApi.create(expense, zohoIntegration);
                            if (expense != null) {
                                salary.setFinanceIntegrationKey(expense.getExpenseId());
                                salary.setFinanceIntegration(YesOrNo.Yes.getYesOrNoSeq());
                                this.salaryService.save(salary);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>Salary>>>>>>>>" + salary.getSalarySeq());
                    }
                }

                List<SalaryAdvance> salaryAdvanceList = this.salaryAdvanceService.findFirst100ByCompanyProfileSeqAndFinanceIntegrationIsNullAndStatus(companyProfile.getCompanyProfileSeq(), MasterDataStatus.APPROVED.getStatusSeq());
                for (SalaryAdvance salaryAdvance : salaryAdvanceList) {
                    try {
                        Expense expense = this.initFromSalaryAdvance(salaryAdvance, dateFormatter);
                        if (salaryAdvance.getFinanceIntegrationKey() == null) {
                            expense = this.expenseApi.create(expense, zohoIntegration);
                            if (expense != null) {
                                salaryAdvance.setFinanceIntegrationKey(expense.getExpenseId());
                                salaryAdvance.setFinanceIntegration(YesOrNo.Yes.getYesOrNoSeq());
                                this.salaryAdvanceService.save(salaryAdvance);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>SalaryAdvance>>>>>>>>" + salaryAdvance.getSalaryAdvanceSeq());
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Expense initFromSalaryAdvance(SalaryAdvance salaryAdvance, DateFormatter dateFormatter) {
        Expense expense = new Expense();
        ChartOfAccount chartOfAccount = this.chartOfAccountService.findByCompanyProfileSeqAndAccountTypeAndStatus(salaryAdvance.getCompanyProfileSeq(), "salary_advance", MasterDataStatus.APPROVED.getStatusSeq());
        ChartOfAccount bank = this.chartOfAccountService.findByCompanyProfileSeqAndAccountTypeAndStatus(salaryAdvance.getCompanyProfileSeq(), "amila_bank", MasterDataStatus.APPROVED.getStatusSeq());
        expense.setAccountId(chartOfAccount.getAccountId());
        expense.setVendorId(salaryAdvance.getEmployee().getFinanceIntegrationKey());
        expense.setReferenceNumber(salaryAdvance.getSalaryYear() + "-" + DateFormatter.getMonth(salaryAdvance.getSalaryMonth()));
        expense.setDate(dateFormatter.returnSortFormattedDate(DateFormatter.getLastDayOfMonth(salaryAdvance.getSalaryYear(), salaryAdvance.getSalaryMonth())));
        expense.setDescription(salaryAdvance.getRemarks());
        expense.setAmount(salaryAdvance.getSalaryAdvance());
        expense.setExchangeRate(1);
        expense.setPaidThroughAccountId(bank.getAccountId());
        return expense;
    }

    private Expense initFromSalary(Salary salary, DateFormatter dateFormatter) {
        Expense expense = new Expense();
        ChartOfAccount chartOfAccount = this.chartOfAccountService.findByCompanyProfileSeqAndAccountTypeAndStatus(salary.getCompanyProfileSeq(), "salaries", MasterDataStatus.APPROVED.getStatusSeq());
        ChartOfAccount bank = this.chartOfAccountService.findByCompanyProfileSeqAndAccountTypeAndStatus(salary.getCompanyProfileSeq(), "amila_bank", MasterDataStatus.APPROVED.getStatusSeq());
        expense.setAccountId(chartOfAccount.getAccountId());
        expense.setVendorId(salary.getEmployee().getFinanceIntegrationKey());
        expense.setReferenceNumber(salary.getSalaryYear() + "-" + DateFormatter.getMonth(salary.getSalaryMonth()));
        expense.setDate(dateFormatter.returnSortFormattedDate(DateFormatter.getLastDayOfMonth(salary.getSalaryYear(), salary.getSalaryMonth())));
        expense.setDescription(salary.getRemarks());
        expense.setAmount(salary.getNetPay());
        expense.setExchangeRate(1);
        expense.setPaidThroughAccountId(bank.getAccountId());
        /*List<ExpenseLineItem> lineItemList = new ArrayList<>();
        if (salary.getBasicSalary() > 0) {
            ExpenseLineItem expenseLineItem = new ExpenseLineItem();
            expenseLineItem.setAccountId(chartOfAccount.getAccountId());
            expenseLineItem.setDescription("Basic Salary");
            expenseLineItem.setAmount(salary.getBasicSalary());
            lineItemList.add(expenseLineItem);
        }

        if (salary.getTotalSalaryAdvance() > 0) {
            ExpenseLineItem expenseLineItem = new ExpenseLineItem();
            expenseLineItem.setAccountId(chartOfAccount.getAccountId());
            expenseLineItem.setDescription("Salary Advance");
            expenseLineItem.setAmount(-salary.getTotalSalaryAdvance());
            lineItemList.add(expenseLineItem);
        }

        if (salary.getTotalDeduction() > 0) {
            ExpenseLineItem expenseLineItem = new ExpenseLineItem();
            expenseLineItem.setAccountId(chartOfAccount.getAccountId());
            expenseLineItem.setDescription("Deductions");
            expenseLineItem.setAmount(-salary.getTotalDeduction());
            lineItemList.add(expenseLineItem);
        }

        if (salary.getTotalCommission() > 0) {
            ExpenseLineItem expenseLineItem = new ExpenseLineItem();
            expenseLineItem.setAccountId(chartOfAccount.getAccountId());
            expenseLineItem.setDescription("Total Commissions");
            expenseLineItem.setAmount(salary.getTotalCommission());
            lineItemList.add(expenseLineItem);
        }
        if (salary.getTotalAllowance() > 0) {
            ExpenseLineItem expenseLineItem = new ExpenseLineItem();
            expenseLineItem.setAccountId(chartOfAccount.getAccountId());
            expenseLineItem.setDescription("Total Allowance");
            expenseLineItem.setAmount(salary.getTotalAllowance());
            lineItemList.add(expenseLineItem);
        }
        expense.setLineItems(lineItemList);*/
        return expense;
    }

}

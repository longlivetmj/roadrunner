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
import com.tmj.tms.fleet.datalayer.modal.ServiceAndMaintenance;
import com.tmj.tms.fleet.datalayer.modal.VehicleFuel;
import com.tmj.tms.fleet.datalayer.service.ServiceAndMaintenanceService;
import com.tmj.tms.fleet.datalayer.service.VehicleFuelService;
import com.tmj.tms.utility.DateFormatter;
import com.tmj.tms.utility.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Profile(value = "prod")
public class FuelIntegration {

    private final CompanyProfileService companyProfileService;
    private final ExpenseApi expenseApi;
    private final VehicleFuelService vehicleFuelService;
    private final ServiceAndMaintenanceService serviceAndMaintenanceService;
    private final ZohoAccessTokenGenerator zohoAccessTokenGenerator;
    private final ChartOfAccountService chartOfAccountService;

    @Autowired
    public FuelIntegration(CompanyProfileService companyProfileService,
                           ExpenseApi expenseApi,
                           VehicleFuelService vehicleFuelService,
                           ServiceAndMaintenanceService serviceAndMaintenanceService,
                           ZohoAccessTokenGenerator zohoAccessTokenGenerator,
                           ChartOfAccountService chartOfAccountService) {
        this.companyProfileService = companyProfileService;
        this.expenseApi = expenseApi;
        this.vehicleFuelService = vehicleFuelService;
        this.serviceAndMaintenanceService = serviceAndMaintenanceService;
        this.zohoAccessTokenGenerator = zohoAccessTokenGenerator;
        this.chartOfAccountService = chartOfAccountService;
    }

    @Scheduled(fixedRate = 60000 * 15)
    @Transactional
    public void insertOrUpdateExpense() {
        DateFormatter dateFormatter = new DateFormatter();
        List<CompanyProfile> companyProfileList = this.companyProfileService.findByStatusNot(MasterDataStatus.DELETED.getStatusSeq());
        for (CompanyProfile companyProfile : companyProfileList) {
            try {
                ZohoIntegration zohoIntegration = this.zohoAccessTokenGenerator.tokenUpdater(companyProfile.getCompanyProfileSeq());
                List<VehicleFuel> vehicleFuelList = this.vehicleFuelService.findFirst100ByCompanyProfileSeqAndFinanceIntegrationIsNullAndStatus(companyProfile.getCompanyProfileSeq(), MasterDataStatus.APPROVED.getStatusSeq());
                for (VehicleFuel vehicleFuel : vehicleFuelList) {
                    try {
                        Expense expense = this.initFromVehicleFuel(vehicleFuel, dateFormatter);
                        if (vehicleFuel.getFinanceIntegrationKey() == null) {
                            expense = this.expenseApi.create(expense, zohoIntegration);
                            if (expense != null) {
                                vehicleFuel.setFinanceIntegrationKey(expense.getExpenseId());
                                vehicleFuel.setFinanceIntegration(YesOrNo.Yes.getYesOrNoSeq());
                                this.vehicleFuelService.save(vehicleFuel);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(">>>>>>>>>VehicleFuel>>>>>>>>" + vehicleFuel.getVehicleFuelSeq());
                    }
                }

                List<ServiceAndMaintenance> serviceAndMaintenanceList = this.serviceAndMaintenanceService.findFirst100ByCompanyProfileSeqAndFinanceIntegrationIsNullAndStatus(companyProfile.getCompanyProfileSeq(), MasterDataStatus.APPROVED.getStatusSeq());
                for (ServiceAndMaintenance serviceAndMaintenance : serviceAndMaintenanceList) {
                    try {
                        Expense expense = this.initFromServiceAndMaintenance(serviceAndMaintenance, dateFormatter);
                        if (serviceAndMaintenance.getFinanceIntegrationKey() == null) {
                            expense = this.expenseApi.create(expense, zohoIntegration);
                            if (expense != null) {
                                serviceAndMaintenance.setFinanceIntegrationKey(expense.getExpenseId());
                                serviceAndMaintenance.setFinanceIntegration(YesOrNo.Yes.getYesOrNoSeq());
                                this.serviceAndMaintenanceService.save(serviceAndMaintenance);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(">>>>>>>>>ServiceAndMaintenance>>>>>>>>" + serviceAndMaintenance.getServiceAndMaintenanceSeq());
                    }
                }

            } catch (Exception e) {
                System.out.println(">>>>>>>>>ServiceAndMaintenance Error>>>>>>>>");
            }
        }
    }

    private Expense initFromServiceAndMaintenance(ServiceAndMaintenance serviceAndMaintenance, DateFormatter dateFormatter) {
        Expense expense = new Expense();
        ChartOfAccount chartOfAccount = this.chartOfAccountService.findByCompanyProfileSeqAndAccountTypeAndStatus(serviceAndMaintenance.getCompanyProfileSeq(), "maintenance", MasterDataStatus.APPROVED.getStatusSeq());
        ChartOfAccount bank = this.chartOfAccountService.findByCompanyProfileSeqAndAccountTypeAndStatus(serviceAndMaintenance.getCompanyProfileSeq(), "amila_bank", MasterDataStatus.APPROVED.getStatusSeq());
        expense.setAccountId(chartOfAccount.getAccountId());
        expense.setVendorId(serviceAndMaintenance.getSupplier().getFinanceIntegrationKey());
        expense.setReferenceNumber(serviceAndMaintenance.getVehicle().getVehicleNo());
        expense.setDate(dateFormatter.returnSortFormattedDate(serviceAndMaintenance.getTransactionDate()));
        expense.setDescription(serviceAndMaintenance.getRemarks());
        expense.setAmount(serviceAndMaintenance.getTotalAmount());
        expense.setExchangeRate(1);
        expense.setPaidThroughAccountId(bank.getAccountId());
        expense.setCustomerId(serviceAndMaintenance.getVehicle().getFinanceIntegrationKey());
        expense.setIsBillable(false);
        return expense;
    }

    private Expense initFromVehicleFuel(VehicleFuel vehicleFuel, DateFormatter dateFormatter) {
        Expense expense = new Expense();
        ChartOfAccount chartOfAccount = this.chartOfAccountService.findByCompanyProfileSeqAndAccountTypeAndStatus(vehicleFuel.getCompanyProfileSeq(), "fuelCost", MasterDataStatus.APPROVED.getStatusSeq());
        ChartOfAccount bank = this.chartOfAccountService.findByCompanyProfileSeqAndAccountTypeAndStatus(vehicleFuel.getCompanyProfileSeq(), "amila_bank", MasterDataStatus.APPROVED.getStatusSeq());
        expense.setAccountId(chartOfAccount.getAccountId());
        expense.setVendorId(vehicleFuel.getSupplier().getFinanceIntegrationKey());
        expense.setReferenceNumber(vehicleFuel.getVehicle().getVehicleNo());
        expense.setDate(dateFormatter.returnSortFormattedDate(vehicleFuel.getTransactionDate()));
        expense.setDescription(vehicleFuel.getRemarks());
        expense.setAmount(vehicleFuel.getAmount());
        expense.setExchangeRate(1);
        expense.setPaidThroughAccountId(bank.getAccountId());
        expense.setCustomerId(vehicleFuel.getVehicle().getFinanceIntegrationKey());
        expense.setIsBillable(false);
        return expense;
    }
}

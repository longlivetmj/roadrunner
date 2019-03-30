package com.tmj.tms.home.utility.scheduler;

import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.datalayer.service.CompanyProfileService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.fleet.datalayer.modal.Salary;
import com.tmj.tms.fleet.datalayer.modal.SalaryAdvance;
import com.tmj.tms.fleet.datalayer.modal.ServiceAndMaintenance;
import com.tmj.tms.fleet.datalayer.modal.VehicleFuel;
import com.tmj.tms.fleet.datalayer.service.SalaryAdvanceService;
import com.tmj.tms.fleet.datalayer.service.SalaryService;
import com.tmj.tms.fleet.datalayer.service.ServiceAndMaintenanceService;
import com.tmj.tms.fleet.datalayer.service.VehicleFuelService;
import com.tmj.tms.fleet.utility.ActionType;
import com.tmj.tms.home.datalayer.model.TempOtherCost;
import com.tmj.tms.home.datalayer.service.TempOtherCostService;
import com.tmj.tms.home.utility.CostType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile(value = "prod")
public class OtherCostSummarizer {

    private final CompanyProfileService companyProfileService;
    private final VehicleFuelService vehicleFuelService;
    private final SalaryService salaryService;
    private final SalaryAdvanceService salaryAdvanceService;
    private final ServiceAndMaintenanceService serviceAndMaintenanceService;
    private final TempOtherCostService tempOtherCostService;

    @Autowired
    public OtherCostSummarizer(CompanyProfileService companyProfileService,
                               VehicleFuelService vehicleFuelService,
                               SalaryService salaryService,
                               SalaryAdvanceService salaryAdvanceService,
                               ServiceAndMaintenanceService serviceAndMaintenanceService,
                               TempOtherCostService tempOtherCostService) {
        this.companyProfileService = companyProfileService;
        this.vehicleFuelService = vehicleFuelService;
        this.salaryService = salaryService;
        this.salaryAdvanceService = salaryAdvanceService;
        this.serviceAndMaintenanceService = serviceAndMaintenanceService;
        this.tempOtherCostService = tempOtherCostService;
    }

    @Scheduled(fixedRate = 60000 * 60)
    @Transactional(timeout = 500000000)
    public void updateCostDetail() {
        List<CompanyProfile> companyProfileList = this.companyProfileService.findByStatusNot(MasterDataStatus.DELETED.getStatusSeq());
        for (CompanyProfile companyProfile : companyProfileList) {
            try {
                List<TempOtherCost> tempOtherCostList = new ArrayList<>();
                List<Salary> salaryList = this.salaryService.findByCompanyProfileSeqAndStatus(companyProfile.getCompanyProfileSeq(), MasterDataStatus.APPROVED.getStatusSeq());
                for (Salary salary : salaryList) {
                    try {
                        if (this.tempOtherCostService.findByCostTypeAndReferenceSeq(CostType.SALARY.getCostType(), salary.getSalarySeq()) == null) {
                            TempOtherCost tempOtherCost = new TempOtherCost();
                            tempOtherCost.setAmount(salary.getNetPay());
                            tempOtherCost.setReferenceSeq(salary.getSalarySeq());
                            tempOtherCost.setCompanyProfileSeq(companyProfile.getCompanyProfileSeq());
                            tempOtherCost.setTransactionDate(salary.getCreatedDate());
                            tempOtherCost.setCostType(CostType.SALARY.getCostType());
                            tempOtherCostList.add(tempOtherCost);
                        }
                    } catch (Exception e) {
                        System.out.println("Other Cost error");
                    }

                }
                List<SalaryAdvance> salaryAdvanceList = this.salaryAdvanceService.findByCompanyProfileSeqAndStatus(companyProfile.getCompanyProfileSeq(), MasterDataStatus.APPROVED.getStatusSeq());
                for (SalaryAdvance salaryAdvance : salaryAdvanceList) {
                    try {
                        if (this.tempOtherCostService.findByCostTypeAndReferenceSeq(CostType.SALARY_ADVANCE.getCostType(), salaryAdvance.getSalaryAdvanceSeq()) == null) {
                            TempOtherCost tempOtherCost = new TempOtherCost();
                            tempOtherCost.setAmount(salaryAdvance.getNetPay());
                            tempOtherCost.setReferenceSeq(salaryAdvance.getSalaryAdvanceSeq());
                            tempOtherCost.setCompanyProfileSeq(companyProfile.getCompanyProfileSeq());
                            tempOtherCost.setTransactionDate(salaryAdvance.getCreatedDate());
                            tempOtherCost.setCostType(CostType.SALARY_ADVANCE.getCostType());
                            tempOtherCostList.add(tempOtherCost);
                        }
                    } catch (Exception e) {
                        System.out.println("Other Cost error");
                    }
                }
                List<VehicleFuel> vehicleFuelList = this.vehicleFuelService.findByCompanyProfileSeqAndStatus(companyProfile.getCompanyProfileSeq(), MasterDataStatus.APPROVED.getStatusSeq());
                for (VehicleFuel vehicleFuel : vehicleFuelList) {
                    try {
                        if (this.tempOtherCostService.findByCostTypeAndReferenceSeq(CostType.FUEL.getCostType(), vehicleFuel.getVehicleFuelSeq()) == null) {
                            TempOtherCost tempOtherCost = new TempOtherCost();
                            tempOtherCost.setAmount(vehicleFuel.getAmount());
                            tempOtherCost.setReferenceSeq(vehicleFuel.getVehicleFuelSeq());
                            tempOtherCost.setCompanyProfileSeq(companyProfile.getCompanyProfileSeq());
                            tempOtherCost.setTransactionDate(vehicleFuel.getTransactionDate());
                            tempOtherCost.setCostType(CostType.FUEL.getCostType());
                            tempOtherCostList.add(tempOtherCost);
                        }
                    } catch (Exception e) {
                        System.out.println("Other Cost error");
                    }
                }
                List<ServiceAndMaintenance> serviceAndMaintenanceList = this.serviceAndMaintenanceService.findByCompanyProfileSeqAndStatus(companyProfile.getCompanyProfileSeq(), MasterDataStatus.APPROVED.getStatusSeq());
                for (ServiceAndMaintenance serviceAndMaintenance : serviceAndMaintenanceList) {
                    try {
                        if (serviceAndMaintenance.getActionType().equals(ActionType.SERVICE.getActionType())) {
                            if (this.tempOtherCostService.findByCostTypeAndReferenceSeq(CostType.SERVICE.getCostType(), serviceAndMaintenance.getServiceAndMaintenanceSeq()) == null) {
                                TempOtherCost tempOtherCost = new TempOtherCost();
                                tempOtherCost.setAmount(serviceAndMaintenance.getAmount());
                                tempOtherCost.setReferenceSeq(serviceAndMaintenance.getServiceAndMaintenanceSeq());
                                tempOtherCost.setCompanyProfileSeq(companyProfile.getCompanyProfileSeq());
                                tempOtherCost.setTransactionDate(serviceAndMaintenance.getTransactionDate());
                                tempOtherCost.setCostType(CostType.SERVICE.getCostType());
                                tempOtherCostList.add(tempOtherCost);
                            }
                        } else if (serviceAndMaintenance.getActionType().equals(ActionType.MAINTENANCE.getActionType())) {
                            if (this.tempOtherCostService.findByCostTypeAndReferenceSeq(CostType.MAINTENANCE.getCostType(), serviceAndMaintenance.getServiceAndMaintenanceSeq()) == null) {
                                TempOtherCost tempOtherCost = new TempOtherCost();
                                tempOtherCost.setAmount(serviceAndMaintenance.getAmount());
                                tempOtherCost.setReferenceSeq(serviceAndMaintenance.getServiceAndMaintenanceSeq());
                                tempOtherCost.setCompanyProfileSeq(companyProfile.getCompanyProfileSeq());
                                tempOtherCost.setTransactionDate(serviceAndMaintenance.getTransactionDate());
                                tempOtherCost.setCostType(CostType.MAINTENANCE.getCostType());
                                tempOtherCostList.add(tempOtherCost);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Other Cost error");
                    }
                }
                this.tempOtherCostService.save(tempOtherCostList);
                System.out.println(">>>>>>>>>>>>Cost Summation Finished >>>>>>>>>..");
            } catch (Exception e) {
                System.out.println("Cost Summation error");
            }
        }
    }
}

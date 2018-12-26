package com.tmj.tms.home.utility.scheduler;

import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.datalayer.service.CompanyProfileService;
import com.tmj.tms.config.datalayer.service.ModuleService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.datalayer.modal.ExpenseVoucher;
import com.tmj.tms.finance.datalayer.modal.FinancialCharge;
import com.tmj.tms.finance.datalayer.modal.FinancialChargeDetail;
import com.tmj.tms.finance.datalayer.modal.LocalInvoice;
import com.tmj.tms.finance.datalayer.service.ExpenseVoucherService;
import com.tmj.tms.finance.datalayer.service.FinancialChargeService;
import com.tmj.tms.finance.datalayer.service.LocalInvoiceService;
import com.tmj.tms.finance.utility.ChargeType;
import com.tmj.tms.finance.utility.ReferenceType;
import com.tmj.tms.finance.utility.TargetType;
import com.tmj.tms.home.datalayer.model.TempRevenueAndCost;
import com.tmj.tms.home.datalayer.service.TempRevenueAndCostService;
import com.tmj.tms.transport.datalayer.modal.Job;
import com.tmj.tms.transport.datalayer.modal.TransportBookingVehicle;
import com.tmj.tms.transport.datalayer.modal.auxiliary.TransportBookingStatusUpdateAux;
import com.tmj.tms.transport.datalayer.service.JobService;
import com.tmj.tms.transport.datalayer.service.TransportBookingVehicleService;
import com.tmj.tms.transport.utility.BookingStatus;
import com.tmj.tms.transport.utility.service.TransportBookingStatusUpdateAuxService;
import com.tmj.tms.utility.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile(value = "prod")
public class RevenueAndCostSummarizer {

    private final FinancialChargeService financialChargeService;
    private final TempRevenueAndCostService tempRevenueAndCostService;
    private final ModuleService moduleService;
    private final CompanyProfileService companyProfileService;
    private final TransportBookingStatusUpdateAuxService transportBookingStatusUpdateAuxService;
    private final LocalInvoiceService localInvoiceService;
    private final ExpenseVoucherService expenseVoucherService;
    private final JobService jobService;
    private final TransportBookingVehicleService transportBookingVehicleService;

    @Autowired
    public RevenueAndCostSummarizer(FinancialChargeService financialChargeService,
                                    TempRevenueAndCostService tempRevenueAndCostService,
                                    ModuleService moduleService,
                                    CompanyProfileService companyProfileService,
                                    TransportBookingStatusUpdateAuxService transportBookingStatusUpdateAuxService,
                                    LocalInvoiceService localInvoiceService,
                                    ExpenseVoucherService expenseVoucherService,
                                    JobService jobService,
                                    TransportBookingVehicleService transportBookingVehicleService) {
        this.financialChargeService = financialChargeService;
        this.tempRevenueAndCostService = tempRevenueAndCostService;
        this.moduleService = moduleService;
        this.companyProfileService = companyProfileService;
        this.transportBookingStatusUpdateAuxService = transportBookingStatusUpdateAuxService;
        this.localInvoiceService = localInvoiceService;
        this.expenseVoucherService = expenseVoucherService;
        this.jobService = jobService;
        this.transportBookingVehicleService = transportBookingVehicleService;
    }


    @Scheduled(fixedRate = 60000 * 15)
    @Transactional(timeout = 500000000)
    public void updateRevenueAndCostDetail() {
        List<CompanyProfile> companyProfileList = this.companyProfileService.findByStatusNot(MasterDataStatus.DELETED.getStatusSeq());
        Integer moduleSeq = this.moduleService.findByModuleCode("TRANSPORT").getModuleSeq();
        for (CompanyProfile companyProfile : companyProfileList) {
            try {
                List<Job> jobList = this.jobService.findTop100ByCompanyProfileSeqAndModuleSeqAndUpdateFlag(companyProfile.getCompanyProfileSeq(), moduleSeq, YesOrNo.NO.getYesOrNoSeq());
                if (jobList != null && jobList.size() > 0) {
                    List<Integer> referenceSeqList = jobList.stream().map(Job::getReferenceSeq).collect(Collectors.toList());
                    List<Integer> statusList = new ArrayList<>();
                    statusList.add(BookingStatus.CANCELLED.getCurrentStatus());
                    statusList.add(BookingStatus.REJECTED.getCurrentStatus());
                    List<TransportBookingStatusUpdateAux> transportBookingList = this.transportBookingStatusUpdateAuxService.findByCompanyProfileSeqAndCurrentStatusNotInAndTransportBookingSeqIn(companyProfile.getCompanyProfileSeq(), statusList, referenceSeqList);
                    if (transportBookingList.size() > 0) {
                        for (TransportBookingStatusUpdateAux transportBookingStatusUpdateAux : transportBookingList) {
                            FinancialCharge financialCharge = this.financialChargeService.findByTargetTypeAndReferenceTypeAndReferenceSeqAndModuleSeqAndStatusNot(TargetType.TRANSPORT_JOB.getTargetType(),
                                    ReferenceType.TRANSPORT_BOOKING.getReferenceType(), transportBookingStatusUpdateAux.getTransportBookingSeq(), moduleSeq, MasterDataStatus.DELETED.getStatusSeq());
                            Double revenue = 0.0;
                            Double cost = 0.0;

                            Integer invoiceCount = 0;
                            Integer expenseVoucherCount = 0;

                            Double invoiceValue = 0.0;
                            Double expenseVoucherValue = 0.0;

                            Integer invoicePartySeq;
                            Integer expenseVoucherPartySeq = null;

                            if (financialCharge != null) {
                                List<FinancialChargeDetail> financialChargeDetailList = financialCharge.getFinancialChargeDetails();
                                revenue = financialChargeDetailList.stream().filter(x -> x.getChargeType().equals(ChargeType.REVENUE.getChargeType())).map(FinancialChargeDetail::getAmount).mapToDouble(Double::doubleValue).sum();
                                cost = financialChargeDetailList.stream().filter(x -> x.getChargeType().equals(ChargeType.COST.getChargeType())).map(FinancialChargeDetail::getAmount).mapToDouble(Double::doubleValue).sum();
                            }

                            List<LocalInvoice> localInvoiceList = this.localInvoiceService.findByCompanyProfileSeqAndStatusAndModuleSeqAndReferenceSeq(companyProfile.getCompanyProfileSeq(), MasterDataStatus.APPROVED.getStatusSeq(), moduleSeq, transportBookingStatusUpdateAux.getTransportBookingSeq());
                            if (localInvoiceList != null && localInvoiceList.size() > 0) {
                                invoiceCount = localInvoiceList.size();
                                invoiceValue = localInvoiceList.stream().map(LocalInvoice::getFinalTotalAmount).mapToDouble(Double::doubleValue).sum();
                                invoicePartySeq = localInvoiceList.get(0).getStakeholderSeq();
                            } else {
                                invoicePartySeq = transportBookingStatusUpdateAux.getInvoiceCustomerSeq();
                            }
                            TempRevenueAndCost revenueObject = this.tempRevenueAndCostService.findByReferenceSeqAndModuleSeqAndCompanyProfileSeqAndChargeType(transportBookingStatusUpdateAux.getTransportBookingSeq(), moduleSeq, companyProfile.getCompanyProfileSeq(), ChargeType.REVENUE.getChargeType());
                            if (revenueObject == null) {
                                revenueObject = new TempRevenueAndCost();
                            }
                            revenueObject.setChargeType(ChargeType.REVENUE.getChargeType());
                            revenueObject.setCompanyProfileSeq(companyProfile.getCompanyProfileSeq());
                            revenueObject.setReferenceSeq(transportBookingStatusUpdateAux.getTransportBookingSeq());
                            revenueObject.setModuleSeq(moduleSeq);
                            revenueObject.setAmount(revenue);
                            revenueObject.setTransactionDate(transportBookingStatusUpdateAux.getRequestedArrivalTime());
                            revenueObject.setCreatedDate(new Date());
                            revenueObject.setFinanceDocumentCount(invoiceCount);
                            revenueObject.setFinanceValue(invoiceValue);
                            revenueObject.setStakeholderSeq(invoicePartySeq);
                            revenueObject.setPickupLocationSeq(transportBookingStatusUpdateAux.getPickupLocationSeq());
                            revenueObject.setDeliveryLocationSeq(transportBookingStatusUpdateAux.getDeliveryLocationSeq());
                            revenueObject.setCurrentStatus(transportBookingStatusUpdateAux.getCurrentStatus());
                            this.tempRevenueAndCostService.save(revenueObject);

                            List<ExpenseVoucher> expenseVoucherList = this.expenseVoucherService.findByCompanyProfileSeqAndStatusAndModuleSeqAndReferenceSeq(companyProfile.getCompanyProfileSeq(), MasterDataStatus.APPROVED.getStatusSeq(), moduleSeq, transportBookingStatusUpdateAux.getTransportBookingSeq());
                            if (expenseVoucherList != null && expenseVoucherList.size() > 0) {
                                expenseVoucherCount = expenseVoucherList.size();
                                expenseVoucherValue = expenseVoucherList.stream().map(ExpenseVoucher::getFinalTotalAmount).mapToDouble(Double::doubleValue).sum();
                                expenseVoucherPartySeq = expenseVoucherList.get(0).getStakeholderSeq();
                            } else {
                                TransportBookingVehicle transportBookingVehicle = this.transportBookingVehicleService.findByTransportBookingSeqAndStatus(transportBookingStatusUpdateAux.getTransportBookingSeq(), MasterDataStatus.APPROVED.getStatusSeq());
                                if (transportBookingVehicle != null) {
                                    expenseVoucherPartySeq = transportBookingVehicle.getTransportCompanySeq();
                                }
                            }

                            TempRevenueAndCost costObject = this.tempRevenueAndCostService.findByReferenceSeqAndModuleSeqAndCompanyProfileSeqAndChargeType(transportBookingStatusUpdateAux.getTransportBookingSeq(), moduleSeq, companyProfile.getCompanyProfileSeq(), ChargeType.COST.getChargeType());
                            if (costObject == null) {
                                costObject = new TempRevenueAndCost();
                            }
                            costObject.setChargeType(ChargeType.COST.getChargeType());
                            costObject.setCompanyProfileSeq(companyProfile.getCompanyProfileSeq());
                            costObject.setReferenceSeq(transportBookingStatusUpdateAux.getTransportBookingSeq());
                            costObject.setModuleSeq(moduleSeq);
                            costObject.setAmount(cost);
                            costObject.setTransactionDate(transportBookingStatusUpdateAux.getRequestedArrivalTime());
                            costObject.setCreatedDate(new Date());
                            costObject.setFinanceDocumentCount(expenseVoucherCount);
                            costObject.setFinanceValue(expenseVoucherValue);
                            costObject.setStakeholderSeq(expenseVoucherPartySeq);
                            costObject.setPickupLocationSeq(transportBookingStatusUpdateAux.getPickupLocationSeq());
                            costObject.setDeliveryLocationSeq(transportBookingStatusUpdateAux.getDeliveryLocationSeq());
                            costObject.setCurrentStatus(transportBookingStatusUpdateAux.getCurrentStatus());
                            this.tempRevenueAndCostService.save(costObject);
                        }
                    }
                    for (Job job : jobList) {
                        job.setUpdateFlag(YesOrNo.Yes.getYesOrNoSeq());
                        this.jobService.save(job);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

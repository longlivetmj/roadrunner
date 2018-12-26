package com.tmj.tms.transport.utility.scheduler;

import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.datalayer.service.CompanyProfileService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.datalayer.modal.*;
import com.tmj.tms.finance.datalayer.service.FinancialChargeDetailService;
import com.tmj.tms.finance.datalayer.service.FinancialChargeService;
import com.tmj.tms.finance.datalayer.service.RateMasterService;
import com.tmj.tms.finance.utility.ChargeType;
import com.tmj.tms.finance.utility.ReferenceType;
import com.tmj.tms.finance.utility.TargetType;
import com.tmj.tms.fleet.datalayer.modal.Vehicle;
import com.tmj.tms.fleet.datalayer.modal.VehicleVehicleType;
import com.tmj.tms.fleet.datalayer.service.VehicleService;
import com.tmj.tms.master.datalayer.modal.Unit;
import com.tmj.tms.master.datalayer.service.UnitService;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.transport.datalayer.modal.TransportBookingFeedback;
import com.tmj.tms.transport.datalayer.modal.TransportBookingVehicle;
import com.tmj.tms.transport.datalayer.service.TransportBookingFeedbackService;
import com.tmj.tms.transport.datalayer.service.TransportBookingService;
import com.tmj.tms.transport.datalayer.service.TransportBookingVehicleService;
import com.tmj.tms.transport.utility.BookingStatus;
import com.tmj.tms.utility.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile(value = "disable")
public class LoadingUnloadingRepick {

    private final TransportBookingService transportBookingService;
    private final TransportBookingFeedbackService transportBookingFeedbackService;
    private final RateMasterService rateMasterService;
    private final FinancialChargeService financialChargeService;
    private final TransportBookingVehicleService transportBookingVehicleService;
    private final CompanyProfileService companyProfileService;
    private final FinancialChargeDetailService financialChargeDetailService;
    private final UnitService unitService;
    private final VehicleService vehicleService;

    @Autowired
    public LoadingUnloadingRepick(TransportBookingService transportBookingService,
                                  TransportBookingFeedbackService transportBookingFeedbackService,
                                  RateMasterService rateMasterService,
                                  FinancialChargeService financialChargeService,
                                  TransportBookingVehicleService transportBookingVehicleService,
                                  CompanyProfileService companyProfileService,
                                  FinancialChargeDetailService financialChargeDetailService,
                                  UnitService unitService,
                                  VehicleService vehicleService) {
        this.transportBookingService = transportBookingService;
        this.transportBookingFeedbackService = transportBookingFeedbackService;
        this.rateMasterService = rateMasterService;
        this.financialChargeService = financialChargeService;
        this.transportBookingVehicleService = transportBookingVehicleService;
        this.companyProfileService = companyProfileService;
        this.financialChargeDetailService = financialChargeDetailService;
        this.unitService = unitService;
        this.vehicleService = vehicleService;
    }

//    @Scheduled(fixedRate = 60000 * 5)
    @Transactional
    public void repickLoadingUnlaoding() {
        try {
            System.out.println(">>>>>>>>>>>>>repickLoadingUnlaoding");
            CompanyProfile companyProfile = this.companyProfileService.findByCompanyProfileSeq(2);
            List<TransportBooking> transportBookingList = this.transportBookingService.findFirst100ByCurrentStatusAndTransportBookingFeedback_ChargesCalculatedIsNull(BookingStatus.JOB_COMPLETED.getCurrentStatus());
            for (TransportBooking transportBooking : transportBookingList) {
                try {
                    FinancialCharge financialChargeDb = this.financialChargeService.findByTargetTypeAndReferenceTypeAndReferenceSeqAndModuleSeqAndStatusNot(TargetType.TRANSPORT_JOB.getTargetType(), ReferenceType.TRANSPORT_BOOKING.getReferenceType(),
                            transportBooking.getTransportBookingSeq(), transportBooking.getModuleSeq(), MasterDataStatus.DELETED.getStatusSeq());
                    if (financialChargeDb != null) {
                        TransportBookingFeedback transportBookingFeedback = this.transportBookingFeedbackService.findOne(transportBooking.getTransportBookingSeq());
                        List<RateMaster> rateMasterList = this.rateMasterService.findByStakeholderSeqAndStatus(transportBooking.getInvoiceCustomerSeq(), MasterDataStatus.APPROVED.getStatusSeq());
                        for (RateMaster rateMaster : rateMasterList) {
                            TransportBookingVehicle transportBookingVehicle = transportBookingVehicleService.findByTransportBookingSeqAndStatus(transportBooking.getTransportBookingSeq(), MasterDataStatus.APPROVED.getStatusSeq());
                            if (rateMaster.getChargeType().equals(ChargeType.COST.getChargeType()) && transportBookingVehicle.getHelperSeq() != null &&
                                    transportBookingVehicle.getTransportCompanySeq().equals(companyProfile.getDefaultTransporterSeq())) {
                                boolean okToCalculate = false;
                                if (rateMaster.getRateMasterStakeholderList() != null && rateMaster.getRateMasterStakeholderList().size() > 0) {
                                    for (RateMasterStakeholder rateMasterStakeholder : rateMaster.getRateMasterStakeholderList()) {
                                        if (rateMasterStakeholder.getStakeholderSeq().equals(transportBooking.getShipperSeq())) {
                                            okToCalculate = true;
                                            break;
                                        }
                                    }
                                } else {
                                    okToCalculate = true;
                                }

                                if (okToCalculate) {
                                    Unit unit = this.unitService.findByUnitName("UNIT");
                                    Double chargeValue = 0.0;
                                    Double quantity = 1.0;
                                    List<Integer> vehicleTypeSeqList = new ArrayList<>();
                                    Vehicle vehicle = this.vehicleService.findOne(transportBookingVehicle.getVehicleSeq());
                                    for (VehicleVehicleType vehicleVehicleType : vehicle.getVehicleVehicleTypeList()) {
                                        vehicleTypeSeqList.add(vehicleVehicleType.getVehicleTypeSeq());
                                    }
                                    if (rateMaster.getChargeSeq().equals(9)) {
                                        FinancialChargeDetail unloadingCleaner = this.financialChargeDetailService.findByFinancialChargeSeqAndChargeTypeAndChargeSeq(financialChargeDb.getFinancialChargeSeq(), ChargeType.COST.getChargeType(), rateMaster.getChargeSeq()); //unloading cleaner
                                        if (unloadingCleaner == null) {
                                            unloadingCleaner = new FinancialChargeDetail();
                                            RateMasterDetail rateMasterDetail = rateMaster.getRateMasterDetailList().stream().filter(x -> vehicleTypeSeqList.contains(x.getTypeSeq())
                                                    && x.getPickupLocationSeq() != null && x.getDeliveryLocationSeq() != null).findFirst().orElse(null);
                                            if (rateMasterDetail == null) {
                                                rateMasterDetail = rateMaster.getRateMasterDetailList().stream().filter(x -> transportBooking.getVehicleTypeSeq().equals(x.getTypeSeq())).findFirst().orElse(null);
                                            }
                                            if (rateMasterDetail != null) {
                                                chargeValue = rateMasterDetail.getRateValue();
                                                unloadingCleaner.setChargeSeq(rateMaster.getChargeSeq());
                                                unloadingCleaner.setUnitSeq(unit.getUnitSeq());
                                                unloadingCleaner.setChargeType(rateMaster.getChargeType());
                                                unloadingCleaner.setCurrencySeq(rateMaster.getCurrencySeq());
                                                unloadingCleaner.setLiStatus(MasterDataStatus.OPEN.getStatusSeq());
                                                unloadingCleaner.setEvStatus(MasterDataStatus.OPEN.getStatusSeq());
                                                unloadingCleaner.setStatus(MasterDataStatus.OPEN.getStatusSeq());
                                                unloadingCleaner.setChargeValue(chargeValue);
                                                unloadingCleaner.setQuantity(quantity);
                                                unloadingCleaner.setAmount(chargeValue);
                                                financialChargeDb.getFinancialChargeDetails().add(unloadingCleaner);
                                            }
                                        }
                                    }

                                    if (rateMaster.getChargeSeq().equals(10)) {
                                        FinancialChargeDetail loadingCleaner = this.financialChargeDetailService.findByFinancialChargeSeqAndChargeTypeAndChargeSeq(financialChargeDb.getFinancialChargeSeq(), ChargeType.COST.getChargeType(), rateMaster.getChargeSeq()); //loading cleaner
                                        if (loadingCleaner == null) {
                                            loadingCleaner = new FinancialChargeDetail();
                                            RateMasterDetail rateMasterDetail = rateMaster.getRateMasterDetailList().stream().filter(x -> vehicleTypeSeqList.contains(x.getTypeSeq())
                                                    && x.getPickupLocationSeq() != null && x.getDeliveryLocationSeq() != null).findFirst().orElse(null);
                                            if (rateMasterDetail == null) {
                                                rateMasterDetail = rateMaster.getRateMasterDetailList().stream().filter(x -> transportBooking.getVehicleTypeSeq().equals(x.getTypeSeq())).findFirst().orElse(null);
                                            }
                                            if (rateMasterDetail != null) {
                                                chargeValue = rateMasterDetail.getRateValue();
                                                loadingCleaner.setChargeSeq(rateMaster.getChargeSeq());
                                                loadingCleaner.setUnitSeq(unit.getUnitSeq());
                                                loadingCleaner.setChargeType(rateMaster.getChargeType());
                                                loadingCleaner.setCurrencySeq(rateMaster.getCurrencySeq());
                                                loadingCleaner.setLiStatus(MasterDataStatus.OPEN.getStatusSeq());
                                                loadingCleaner.setEvStatus(MasterDataStatus.OPEN.getStatusSeq());
                                                loadingCleaner.setStatus(MasterDataStatus.OPEN.getStatusSeq());
                                                loadingCleaner.setChargeValue(chargeValue);
                                                loadingCleaner.setQuantity(quantity);
                                                loadingCleaner.setAmount(chargeValue);
                                                financialChargeDb.getFinancialChargeDetails().add(loadingCleaner);
                                            }
                                            this.financialChargeService.save(financialChargeDb);
                                        }
                                    }

                                }
                            }
                        }
                        transportBookingFeedback.setChargesCalculated(YesOrNo.Yes.getYesOrNoSeq());
                        this.transportBookingFeedbackService.save(transportBookingFeedback);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error in " + transportBooking.getTransportBookingSeq());
                }
            }
            System.out.println(">>>>>>>>>>>>>repickLoadingUnlaoding Finished");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

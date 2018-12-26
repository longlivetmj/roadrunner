package com.tmj.tms.finance.utility;

import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.datalayer.service.CompanyProfileService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.datalayer.modal.*;
import com.tmj.tms.finance.datalayer.service.FinancialChargeService;
import com.tmj.tms.finance.datalayer.service.RateMasterService;
import com.tmj.tms.fleet.datalayer.modal.Vehicle;
import com.tmj.tms.fleet.datalayer.modal.VehicleVehicleType;
import com.tmj.tms.fleet.datalayer.service.VehicleService;
import com.tmj.tms.master.datalayer.modal.Charge;
import com.tmj.tms.master.datalayer.modal.Unit;
import com.tmj.tms.master.datalayer.service.ChargeService;
import com.tmj.tms.master.datalayer.service.UnitService;
import com.tmj.tms.transport.datalayer.modal.Job;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.transport.datalayer.modal.TransportBookingFeedback;
import com.tmj.tms.transport.datalayer.modal.TransportBookingVehicle;
import com.tmj.tms.transport.datalayer.service.JobService;
import com.tmj.tms.transport.datalayer.service.TransportBookingFeedbackService;
import com.tmj.tms.transport.datalayer.service.TransportBookingService;
import com.tmj.tms.transport.datalayer.service.TransportBookingVehicleService;
import com.tmj.tms.transport.utility.DistanceCalculator;
import com.tmj.tms.transport.utility.service.DistanceCalculatorService;
import com.tmj.tms.utility.DateFormatter;
import com.tmj.tms.utility.NumberFormatter;
import com.tmj.tms.utility.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ChargesCalculator {

    private final RateMasterService rateMasterService;
    private final UnitService unitService;
    private final TransportBookingService transportBookingService;
    private final FinancialChargeService financialChargeService;
    private final CompanyProfileService companyProfileService;
    private final TransportBookingVehicleService transportBookingVehicleService;
    private final TransportBookingFeedbackService transportBookingFeedbackService;
    private final VehicleService vehicleService;
    private final ChargeService chargeService;
    private final DistanceCalculator distanceCalculator;
    private final JobService jobService;
    private final DistanceCalculatorService distanceCalculatorService;

    @Autowired
    public ChargesCalculator(RateMasterService rateMasterService, UnitService unitService,
                             TransportBookingService transportBookingService,
                             FinancialChargeService financialChargeService,
                             CompanyProfileService companyProfileService,
                             TransportBookingVehicleService transportBookingVehicleService,
                             TransportBookingFeedbackService transportBookingFeedbackService,
                             VehicleService vehicleService,
                             ChargeService chargeService,
                             DistanceCalculator distanceCalculator,
                             JobService jobService,
                             DistanceCalculatorService distanceCalculatorService) {
        this.rateMasterService = rateMasterService;
        this.unitService = unitService;
        this.transportBookingService = transportBookingService;
        this.financialChargeService = financialChargeService;
        this.companyProfileService = companyProfileService;
        this.transportBookingVehicleService = transportBookingVehicleService;
        this.transportBookingFeedbackService = transportBookingFeedbackService;
        this.vehicleService = vehicleService;
        this.chargeService = chargeService;
        this.distanceCalculator = distanceCalculator;
        this.jobService = jobService;
        this.distanceCalculatorService = distanceCalculatorService;
    }

    public void revenueCostChargesCalculator(Integer transportBookingSeq) {
        List<FinancialChargeDetail> financialChargeDetailList = new ArrayList<>();
        try {
            TransportBooking transportBooking = this.transportBookingService.findOne(transportBookingSeq);
            Charge tripCommissionHelper = this.chargeService.findByChargeNameAndCompanyProfileSeqAndStatus("TRIP COMMISSION - HELPER", transportBooking.getCompanyProfileSeq(), MasterDataStatus.APPROVED.getStatusSeq());
            Charge tripCommissionDriverWH = this.chargeService.findByChargeNameAndCompanyProfileSeqAndStatus("TRIP COMMISSION - DRIVER(WH)", transportBooking.getCompanyProfileSeq(), MasterDataStatus.APPROVED.getStatusSeq());
            Charge loadingCleaner = this.chargeService.findByChargeNameAndCompanyProfileSeqAndStatus("LOADING - CLEANER", transportBooking.getCompanyProfileSeq(), MasterDataStatus.APPROVED.getStatusSeq());
            Charge unloadingCleaner = this.chargeService.findByChargeNameAndCompanyProfileSeqAndStatus("UNLOADING - CLEANER", transportBooking.getCompanyProfileSeq(), MasterDataStatus.APPROVED.getStatusSeq());
            CompanyProfile companyProfile = this.companyProfileService.findByCompanyProfileSeq(transportBooking.getCompanyProfileSeq());
            FinancialCharge financialChargeDb = this.financialChargeService.findByTargetTypeAndReferenceTypeAndReferenceSeqAndModuleSeqAndStatusNot(TargetType.TRANSPORT_JOB.getTargetType(), ReferenceType.TRANSPORT_BOOKING.getReferenceType(), transportBookingSeq, transportBooking.getModuleSeq(), MasterDataStatus.DELETED.getStatusSeq());
            TransportBookingFeedback transportBookingFeedback = this.transportBookingFeedbackService.findOne(transportBookingSeq);
            if (financialChargeDb == null && transportBookingFeedback != null) {
                List<RateMaster> rateMasterList = this.rateMasterService.findByStakeholderSeqAndStatus(transportBooking.getInvoiceCustomerSeq(), MasterDataStatus.APPROVED.getStatusSeq());
                for (RateMaster rateMaster : rateMasterList) {
                    try {
                        TransportBookingVehicle transportBookingVehicle = transportBookingVehicleService.findByTransportBookingSeqAndStatus(transportBookingSeq, MasterDataStatus.APPROVED.getStatusSeq());
                        if (rateMaster.getChargeType().equals(ChargeType.REVENUE.getChargeType()) ||
                                (rateMaster.getChargeType().equals(ChargeType.COST.getChargeType()) &&
                                        transportBookingVehicle.getTransportCompanySeq().equals(companyProfile.getDefaultTransporterSeq()))) {
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
                                FinancialChargeDetail financialChargeDetail = new FinancialChargeDetail();
                                Unit unit;
                                Double chargeValue = 0.0;
                                Double quantity;
                                if (rateMaster.getMultiplyType().equals(MultiplyType.FIXED_RATE.getMultiplyType())) {
                                    unit = this.unitService.findByUnitName("UNIT");
                                    quantity = 1.0;
                                } else if (rateMaster.getMultiplyType().equals(MultiplyType.PER_VOLUME.getMultiplyType())) {
                                    unit = this.unitService.findOne(transportBooking.getUovSeq());
                                    quantity = transportBooking.getVolume();
                                } else if (rateMaster.getMultiplyType().equals(MultiplyType.PER_KM.getMultiplyType())) {
                                    unit = this.unitService.findByUnitName("KM");
                                    if (rateMaster.getChargeType().equals(ChargeType.REVENUE.getChargeType())) {
                                        quantity = transportBookingFeedback.getChargeableKm();
                                    } else {
                                        if (transportBookingFeedback.getCargoInHandKm() != null) {
                                            quantity = transportBookingFeedback.getCargoInHandKm() + transportBookingFeedback.getPlacementKm();
                                        } else {
                                            quantity = transportBooking.getEstimatedKm() + transportBookingFeedback.getPlacementKm();
                                        }
                                    }
                                } else if (rateMaster.getMultiplyType().equals(MultiplyType.PER_WEIGHT.getMultiplyType())) {
                                    unit = this.unitService.findOne(transportBooking.getUowSeq());
                                    quantity = transportBooking.getWeight();
                                } else if (rateMaster.getMultiplyType().equals(MultiplyType.PER_CBM.getMultiplyType())) {
                                    unit = this.unitService.findByUnitName("CBM");
                                    quantity = transportBooking.getCbm();
                                } else if (rateMaster.getMultiplyType().equals(MultiplyType.ADDITIONAL_HOUR.getMultiplyType())) {
                                    unit = this.unitService.findByUnitName("HOURS");
                                    Long hours = DateFormatter.getDateDiff(transportBookingFeedback.getArrivedAtPickup(),
                                            transportBookingFeedback.getDepartedFromDelivery(), TimeUnit.HOURS);
                                    quantity = hours.doubleValue();
                                } else if (rateMaster.getMultiplyType().equals(MultiplyType.PER_NIGHT.getMultiplyType())) {
                                    unit = this.unitService.findByUnitName("NIGHTS");
                                    if (transportBookingFeedback.getDepartedFromDelivery().before(DateFormatter.getEndOfTheDay(transportBookingFeedback.getArrivedAtPickup()))) {
                                        quantity = 0.0;
                                    } else {
                                        Long days = DateFormatter.getDateDiff(DateFormatter.getStartOfTheDay(transportBookingFeedback.getArrivedAtPickup()),
                                                DateFormatter.getStartOfTheDay(transportBookingFeedback.getDepartedFromDelivery()), TimeUnit.DAYS);
                                        quantity = days.doubleValue();
                                    }
                                } else {
                                    unit = this.unitService.findByUnitName("UNIT");
                                    quantity = 1.0;
                                }
                                RateMasterDetail rateMasterDetail;
                                List<Integer> vehicleTypeSeqList = new ArrayList<>();
                                if (rateMaster.getChargeType().equals(ChargeType.REVENUE.getChargeType())) {
                                    vehicleTypeSeqList.add(transportBooking.getVehicleTypeSeq());
                                } else {
                                    Vehicle vehicle = this.vehicleService.findOne(transportBookingVehicle.getVehicleSeq());
                                    for (VehicleVehicleType vehicleVehicleType : vehicle.getVehicleVehicleTypeList()) {
                                        vehicleTypeSeqList.add(vehicleVehicleType.getVehicleTypeSeq());
                                    }
                                }
                                if (rateMaster.getMultiplyType().equals(MultiplyType.FIXED_RATE.getMultiplyType())) {
                                    Integer pick = transportBooking.getPickupLocationSeq();
                                    Integer delivery = transportBooking.getDeliveryLocationSeq();
                                    rateMasterDetail = rateMaster.getRateMasterDetailList().stream().filter(x -> vehicleTypeSeqList.contains(x.getTypeSeq())
                                            && x.getPickupLocationSeq() != null && x.getDeliveryLocationSeq() != null
                                            && x.getPickupLocationSeq().equals(pick)
                                            && x.getDeliveryLocationSeq().equals(delivery)).findFirst().orElse(null);
                                    if (rateMasterDetail == null) {
                                        rateMasterDetail = rateMaster.getRateMasterDetailList().stream().filter(x -> vehicleTypeSeqList.contains(x.getTypeSeq())
                                                && x.getPickupLocationSeq() != null && x.getDeliveryLocationSeq() != null
                                                && x.getPickupLocationSeq().equals(delivery)
                                                && x.getDeliveryLocationSeq().equals(pick)).findFirst().orElse(null);
                                    }
                                    if (rateMasterDetail == null) {
                                        rateMasterDetail = rateMaster.getRateMasterDetailList().stream().filter(x -> vehicleTypeSeqList.contains(x.getTypeSeq())
                                                && x.getPickupLocationSeq() != null && x.getDeliveryLocationSeq() != null).findFirst().orElse(null);
                                        if (rateMasterDetail == null) {
                                            rateMasterDetail = rateMaster.getRateMasterDetailList().stream().filter(x -> transportBooking.getVehicleTypeSeq().equals(x.getTypeSeq())).findFirst().orElse(null);
                                        }
                                    }
                                } else {
                                    rateMasterDetail = rateMaster.getRateMasterDetailList().stream().filter(x -> vehicleTypeSeqList.contains(x.getTypeSeq())).findFirst().orElse(null);
                                }
                                if (rateMasterDetail != null) {
                                    if (rateMasterDetail.getRateValue() != null && rateMasterDetail.getRateValue() > 0) {
                                        chargeValue = rateMasterDetail.getRateValue();
                                    }
                                    if (quantity != null && quantity > rateMaster.getThresholdValue()) {
                                        if (rateMaster.getMultiplyType().equals(MultiplyType.ADDITIONAL_HOUR.getMultiplyType())) {
                                            financialChargeDetail.setQuantity(quantity - rateMaster.getThresholdValue());
                                        } else {
                                            financialChargeDetail.setQuantity(quantity);
                                        }
                                    } else {
                                        if (rateMaster.getMultiplyType().equals(MultiplyType.ADDITIONAL_HOUR.getMultiplyType())) {
                                            financialChargeDetail.setQuantity(0.0);
                                        } else {
                                            financialChargeDetail.setQuantity(rateMaster.getThresholdValue());
                                        }
                                    }

                                    if ((chargeValue * financialChargeDetail.getQuantity()) > rateMaster.getMinimumCharge()) {
                                        financialChargeDetail.setQuantity(NumberFormatter.round(financialChargeDetail.getQuantity(), 0));
                                        financialChargeDetail.setChargeValue(NumberFormatter.round(chargeValue, 2));
                                        financialChargeDetail.setAmount(financialChargeDetail.getChargeValue() * financialChargeDetail.getQuantity());
                                    } else {
                                        financialChargeDetail.setChargeValue(rateMaster.getMinimumCharge());
                                        financialChargeDetail.setQuantity(1.0);
                                        financialChargeDetail.setAmount(rateMaster.getMinimumCharge());
                                    }

                                    financialChargeDetail.setChargeSeq(rateMaster.getChargeSeq());
                                    financialChargeDetail.setUnitSeq(unit.getUnitSeq());
                                    financialChargeDetail.setChargeType(rateMaster.getChargeType());
                                    financialChargeDetail.setCurrencySeq(rateMaster.getCurrencySeq());
                                    financialChargeDetail.setLiStatus(MasterDataStatus.OPEN.getStatusSeq());
                                    financialChargeDetail.setEvStatus(MasterDataStatus.OPEN.getStatusSeq());
                                    financialChargeDetail.setStatus(MasterDataStatus.OPEN.getStatusSeq());
                                    if (financialChargeDetail.getQuantity() > 0 && financialChargeDetail.getAmount() > 0) {
                                        if (rateMaster.getChargeType().equals(ChargeType.REVENUE.getChargeType())) {
                                            financialChargeDetailList.add(financialChargeDetail);
                                        } else {
                                            if (rateMaster.getChargeSeq().equals(tripCommissionHelper.getChargeSeq()) || rateMaster.getChargeSeq().equals(tripCommissionDriverWH.getChargeSeq())) {
                                                if (rateMaster.getChargeSeq().equals(tripCommissionHelper.getChargeSeq()) && transportBookingVehicle.getHelperSeq() != null) {
                                                    financialChargeDetailList.add(financialChargeDetail);
                                                } else if (rateMaster.getChargeSeq().equals(tripCommissionDriverWH.getChargeSeq()) && transportBookingVehicle.getHelperSeq() == null) {
                                                    financialChargeDetailList.add(financialChargeDetail);
                                                }
                                            } else {
                                                if (rateMaster.getChargeSeq().equals(loadingCleaner.getChargeSeq())
                                                        || rateMaster.getChargeSeq().equals(unloadingCleaner.getChargeSeq())) {
                                                    if (transportBookingVehicle.getHelperSeq() != null) {
                                                        financialChargeDetailList.add(financialChargeDetail);
                                                    }
                                                } else {
                                                    financialChargeDetailList.add(financialChargeDetail);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Error in Charges Calculator " + transportBookingSeq + "  ChargeSeq" + rateMaster.getChargeSeq());
                    }
                }

                if (financialChargeDetailList.size() > 0) {
                    FinancialCharge financialCharge = new FinancialCharge();
                    financialCharge.setCompanyProfileSeq(transportBooking.getCompanyProfileSeq());
                    financialCharge.setModuleSeq(transportBooking.getModuleSeq());
                    financialCharge.setTargetType(TargetType.TRANSPORT_JOB.getTargetType());
                    financialCharge.setReferenceType(ReferenceType.TRANSPORT_BOOKING.getReferenceType());
                    financialCharge.setReferenceSeq(transportBookingSeq);
                    financialCharge.setReferenceNo(transportBooking.getBookingNo());
                    financialCharge.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
                    financialCharge.setFinancialChargeDetails(financialChargeDetailList);
                    this.financialChargeService.save(financialCharge);
                    Job job = this.jobService.findByModuleSeqAndCompanyProfileSeqAndReferenceSeq(financialCharge.getModuleSeq(), financialCharge.getCompanyProfileSeq(), financialCharge.getReferenceSeq());
                    job.setUpdateFlag(YesOrNo.NO.getYesOrNoSeq());
                    this.jobService.save(job);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in Charges Calculator " + transportBookingSeq);
        }
    }

    public Double parposeRateCalculator(Integer transportBookingSeq) {
        Double proposeRate = null;
        try {
            TransportBooking transportBooking = this.transportBookingService.findOne(transportBookingSeq);
            Charge transportCharge = this.chargeService.findByChargeNameAndCompanyProfileSeqAndStatus("TRANSPORT CHARGE", transportBooking.getCompanyProfileSeq(), MasterDataStatus.APPROVED.getStatusSeq());
            List<RateMaster> rateMasterList = this.rateMasterService.findByStakeholderSeqAndStatusAndChargeTypeAndChargeSeq(transportBooking.getInvoiceCustomerSeq(), MasterDataStatus.APPROVED.getStatusSeq(), ChargeType.REVENUE.getChargeType(), transportCharge.getChargeSeq());
            boolean okToCalculate = false;
            for (RateMaster rateMaster : rateMasterList) {
                if (rateMaster.getRateMasterStakeholderList() != null && rateMaster.getRateMasterStakeholderList().size() > 0) {
                    for (RateMasterStakeholder rateMasterStakeholder : rateMaster.getRateMasterStakeholderList()) {
                        if (rateMasterStakeholder.getStakeholderSeq().equals(transportBooking.getShipperSeq())) {
                            okToCalculate = true;
                            break;
                        }
                    }
                    if (okToCalculate) {
                        FinancialChargeDetail financialChargeDetail = new FinancialChargeDetail();
                        Double chargeValue = 0.0;
                        Double quantity;
                        if (rateMaster.getMultiplyType().equals(MultiplyType.FIXED_RATE.getMultiplyType())) {
                            quantity = 1.0;
                        } else if (rateMaster.getMultiplyType().equals(MultiplyType.PER_VOLUME.getMultiplyType())) {
                            quantity = transportBooking.getVolume();
                        } else if (rateMaster.getMultiplyType().equals(MultiplyType.PER_KM.getMultiplyType())) {
                            quantity = this.distanceCalculator.calculateChargeableDistance(transportBookingSeq);
                        } else if (rateMaster.getMultiplyType().equals(MultiplyType.PER_WEIGHT.getMultiplyType())) {
                            quantity = transportBooking.getWeight();
                        } else if (rateMaster.getMultiplyType().equals(MultiplyType.PER_CBM.getMultiplyType())) {
                            quantity = transportBooking.getCbm();
                        } else {
                            quantity = 1.0;
                        }
                        RateMasterDetail rateMasterDetail;
                        List<Integer> vehicleTypeSeqList = new ArrayList<>();
                        if (rateMaster.getChargeType().equals(ChargeType.REVENUE.getChargeType())) {
                            vehicleTypeSeqList.add(transportBooking.getVehicleTypeSeq());
                        }
                        if (rateMaster.getMultiplyType().equals(MultiplyType.FIXED_RATE.getMultiplyType())) {
                            Integer pick = transportBooking.getPickupLocationSeq();
                            Integer delivery = transportBooking.getDeliveryLocationSeq();
                            rateMasterDetail = rateMaster.getRateMasterDetailList().stream().filter(x -> vehicleTypeSeqList.contains(x.getTypeSeq())
                                    && x.getPickupLocationSeq() != null && x.getDeliveryLocationSeq() != null
                                    && x.getPickupLocationSeq().equals(pick)
                                    && x.getDeliveryLocationSeq().equals(delivery)).findFirst().orElse(null);
                            if (rateMasterDetail == null) {
                                rateMasterDetail = rateMaster.getRateMasterDetailList().stream().filter(x -> vehicleTypeSeqList.contains(x.getTypeSeq())
                                        && x.getPickupLocationSeq() != null && x.getDeliveryLocationSeq() != null
                                        && x.getPickupLocationSeq().equals(delivery)
                                        && x.getDeliveryLocationSeq().equals(pick)).findFirst().orElse(null);
                            }
                            if (rateMasterDetail == null) {
                                rateMasterDetail = rateMaster.getRateMasterDetailList().stream().filter(x -> vehicleTypeSeqList.contains(x.getTypeSeq())).findFirst().orElse(null);
                            }
                        } else {
                            rateMasterDetail = rateMaster.getRateMasterDetailList().stream().filter(x -> vehicleTypeSeqList.contains(x.getTypeSeq())).findFirst().orElse(null);
                        }
                        if (rateMasterDetail != null) {
                            if (rateMasterDetail.getRateValue() != null && rateMasterDetail.getRateValue() > 0) {
                                chargeValue = rateMasterDetail.getRateValue();
                            }
                            if (quantity != null && quantity > rateMaster.getThresholdValue()) {
                                if (rateMaster.getMultiplyType().equals(MultiplyType.ADDITIONAL_HOUR.getMultiplyType())) {
                                    financialChargeDetail.setQuantity(quantity - rateMaster.getThresholdValue());
                                } else {
                                    financialChargeDetail.setQuantity(quantity);
                                }
                            } else {
                                if (rateMaster.getMultiplyType().equals(MultiplyType.ADDITIONAL_HOUR.getMultiplyType())) {
                                    financialChargeDetail.setQuantity(0.0);
                                } else {
                                    financialChargeDetail.setQuantity(rateMaster.getThresholdValue());
                                }
                            }

                            if ((chargeValue * financialChargeDetail.getQuantity()) > rateMaster.getMinimumCharge()) {
                                financialChargeDetail.setQuantity(NumberFormatter.round(financialChargeDetail.getQuantity(), 0));
                                financialChargeDetail.setChargeValue(NumberFormatter.round(chargeValue, 2));
                                financialChargeDetail.setAmount(financialChargeDetail.getChargeValue() * financialChargeDetail.getQuantity());
                            } else {
                                financialChargeDetail.setChargeValue(rateMaster.getMinimumCharge());
                                financialChargeDetail.setQuantity(1.0);
                                financialChargeDetail.setAmount(rateMaster.getMinimumCharge());
                            }

                            proposeRate = financialChargeDetail.getAmount();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return proposeRate;
    }

    public void chargeFixer(TransportBooking transportBooking) {
        try {
            TransportBookingFeedback transportBookingFeedback = this.transportBookingFeedbackService.findOne(transportBooking.getTransportBookingSeq());
            if (transportBookingFeedback != null) {
                transportBookingFeedback.setPlacementKm(this.distanceCalculatorService.calculatePlacementDistance(transportBookingFeedback.getTransportBookingSeq()));
                if (transportBookingFeedback.getPlacementKm() != null &&
                        transportBookingFeedback.getPlacementKm() > 0 &&
                        transportBookingFeedback.getChargeableKm() != null &&
                        transportBookingFeedback.getChargeableKm() > 0) {
                    this.transportBookingFeedbackService.save(transportBookingFeedback);
                    FinancialCharge financialCharge = this.financialChargeService.findByTargetTypeAndReferenceTypeAndReferenceSeqAndModuleSeqAndStatusNot(
                            TargetType.TRANSPORT_JOB.getTargetType(), ReferenceType.TRANSPORT_BOOKING.getReferenceType(), transportBooking.getTransportBookingSeq(), transportBooking.getModuleSeq(), MasterDataStatus.DELETED.getStatusSeq());
                    if (financialCharge != null) {
                        for (FinancialChargeDetail financialChargeDetail : financialCharge.getFinancialChargeDetails()) {
                            if (financialChargeDetail.getChargeSeq().equals(15) ||
                                    financialChargeDetail.getChargeSeq().equals(16) ||
                                    financialChargeDetail.getChargeSeq().equals(21)) {
                                financialChargeDetail.setQuantity(transportBooking.getEstimatedKm() + transportBookingFeedback.getPlacementKm());
                                financialChargeDetail.setAmount(financialChargeDetail.getChargeValue() * financialChargeDetail.getQuantity());
                            }
                        }
                        this.financialChargeService.save(financialCharge);
                        System.out.println("Placement Repick for " + transportBooking.getTransportBookingSeq());
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error Placement Repick in " + transportBooking.getTransportBookingSeq());
        }
    }

}

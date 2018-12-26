package com.tmj.tms.transport.bussinesslayer.managerimpl;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.datalayer.modal.RateMaster;
import com.tmj.tms.finance.datalayer.modal.RateMasterDetail;
import com.tmj.tms.finance.datalayer.service.RateMasterService;
import com.tmj.tms.finance.utility.ChargeType;
import com.tmj.tms.finance.utility.ChargesCalculator;
import com.tmj.tms.finance.utility.RateType;
import com.tmj.tms.master.datalayer.modal.Stakeholder;
import com.tmj.tms.master.datalayer.modal.VehicleType;
import com.tmj.tms.master.datalayer.service.StakeholderService;
import com.tmj.tms.master.datalayer.service.VehicleTypeService;
import com.tmj.tms.transport.bussinesslayer.manager.TransportBookingManagementControllerManager;
import com.tmj.tms.transport.datalayer.modal.Job;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.transport.datalayer.modal.TransportBookingViaLocation;
import com.tmj.tms.transport.datalayer.modal.auxiliary.TransportBookingViaLocationAux;
import com.tmj.tms.transport.datalayer.service.JobService;
import com.tmj.tms.transport.datalayer.service.TransportBookingService;
import com.tmj.tms.transport.datalayer.service.TransportBookingViaLocationService;
import com.tmj.tms.transport.utility.BookingStatus;
import com.tmj.tms.transport.utility.DistanceAndDuration;
import com.tmj.tms.transport.utility.JobNoGenerator;
import com.tmj.tms.transport.utility.StatusChecker;
import com.tmj.tms.transport.utility.service.DistanceCalculatorService;
import com.tmj.tms.utility.ErrorMessageCreator;
import com.tmj.tms.utility.JobReferenceType;
import com.tmj.tms.utility.ResponseObject;
import com.tmj.tms.utility.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransportBookingManagementControllerManagerImpl implements TransportBookingManagementControllerManager {

    private final StatusChecker statusChecker;
    private final JobNoGenerator jobNoGenerator;
    private final TransportBookingService transportBookingService;
    private final JobService jobService;
    private final TransportBookingViaLocationService transportBookingViaLocationService;
    private final StakeholderService stakeholderService;
    private final DistanceCalculatorService distanceCalculatorService;
    private final RateMasterService rateMasterService;
    private final VehicleTypeService vehicleTypeService;
    private final ChargesCalculator chargesCalculator;

    @Autowired
    public TransportBookingManagementControllerManagerImpl(StatusChecker statusChecker,
                                                           JobNoGenerator jobNoGenerator,
                                                           TransportBookingService transportBookingService,
                                                           JobService jobService,
                                                           TransportBookingViaLocationService transportBookingViaLocationService,
                                                           StakeholderService stakeholderService,
                                                           DistanceCalculatorService distanceCalculatorService,
                                                           RateMasterService rateMasterService,
                                                           VehicleTypeService vehicleTypeService,
                                                           ChargesCalculator chargesCalculator) {
        this.statusChecker = statusChecker;
        this.jobNoGenerator = jobNoGenerator;
        this.transportBookingService = transportBookingService;
        this.jobService = jobService;
        this.transportBookingViaLocationService = transportBookingViaLocationService;
        this.stakeholderService = stakeholderService;
        this.distanceCalculatorService = distanceCalculatorService;
        this.rateMasterService = rateMasterService;
        this.vehicleTypeService = vehicleTypeService;
        this.chargesCalculator = chargesCalculator;
    }

    @Override
    public ResponseObject saveTransportBooking(TransportBooking transportBooking) {
        ResponseObject responseObject = this.validateTransportBooking(transportBooking);
        if (responseObject.isStatus()) {
            Stakeholder customer = this.stakeholderService.findOne(transportBooking.getCustomerSeq(), EntityGraphUtils.fromName("Stakeholder.filtering"));
            transportBooking.setPrefix(customer.getStakeholderCode());
            String jobNo = this.jobNoGenerator.generateJobNo(transportBooking.getCompanyProfileSeq(), transportBooking.getDepartmentSeq());
            Job job = new Job();
            job.setReferenceType(JobReferenceType.TRANSPORT.getReferenceType());
            job.setCompanyProfileSeq(transportBooking.getCompanyProfileSeq());
            job.setModuleSeq(transportBooking.getModuleSeq());
            job.setDepartmentSeq(transportBooking.getDepartmentSeq());
            job.setJobNo(jobNo);
            job.setStatus(MasterDataStatus.OPEN.getStatusSeq());
            job.setUpdateFlag(YesOrNo.NO.getYesOrNoSeq());
            job = this.jobService.save(job);
            transportBooking.setJobSeq(job.getJobSeq());

            DistanceAndDuration distanceAndDuration = this.distanceCalculatorService.calculateDistance(transportBooking.getRequestedArrivalTime(),
                    true, null,
                    transportBooking.getPickupLocationSeq(), null, transportBooking.getDeliveryLocationSeq());
            transportBooking.setEstimatedKm(distanceAndDuration.getDistance());
            transportBooking.setHumanReadableEta(distanceAndDuration.getDuration());
            transportBooking = this.transportBookingService.save(transportBooking);
            job.setReferenceSeq(transportBooking.getTransportBookingSeq());
            this.jobService.save(job);
            Double proposeRate = this.chargesCalculator.parposeRateCalculator(transportBooking.getTransportBookingSeq());
            transportBooking.setProposedTransportCharge(proposeRate);
            this.transportBookingService.save(transportBooking);
            responseObject = new ResponseObject("Transport Booking Saved Successfully", true);
            responseObject.setObject(transportBooking);
        }
        return responseObject;
    }

    @Override
    public ResponseObject updateTransportBooking(TransportBooking transportBooking) {
        ResponseObject responseObject = this.validateTransportBooking(transportBooking);
        if (responseObject.isStatus()) {
            List<Integer> deliveryList = new ArrayList<>();
            List<TransportBookingViaLocation> transportBookingViaLocationList = this.transportBookingViaLocationService.findByTransportBookingSeqAndStatusOrderByRequestedArrivalTime(transportBooking.getTransportBookingSeq(), MasterDataStatus.APPROVED.getStatusSeq(), EntityGraphUtils.fromName("TransportBookingViaLocation.createViaLocation"));
            for (TransportBookingViaLocation transportBookingViaLocation : transportBookingViaLocationList) {
                deliveryList.add(transportBookingViaLocation.getViaLocationSeq());
            }
            DistanceAndDuration distanceAndDuration = this.distanceCalculatorService.calculateDistance(transportBooking.getRequestedArrivalTime(),
                    true, null,
                    transportBooking.getPickupLocationSeq(), deliveryList, transportBooking.getDeliveryLocationSeq());
            transportBooking.setEstimatedKm(distanceAndDuration.getDistance());
            transportBooking.setHumanReadableEta(distanceAndDuration.getDuration());
            Double proposeRate = this.chargesCalculator.parposeRateCalculator(transportBooking.getTransportBookingSeq());
            transportBooking.setProposedTransportCharge(proposeRate);
            transportBooking = this.transportBookingService.save(transportBooking);
            responseObject = new ResponseObject("Transport Booking Saved Successfully", true);
            responseObject.setObject(transportBooking);
        }
        return responseObject;
    }

    @Override
    public ResponseObject addViaLocations(TransportBookingViaLocationAux transportBookingViaLocationAux) {
        List<TransportBookingViaLocation> bookingViaLocationList = transportBookingViaLocationAux.getBookingViaLocations();
        for (TransportBookingViaLocation transportBookingViaLocation : bookingViaLocationList) {
            transportBookingViaLocation.setTransportBookingSeq(transportBookingViaLocationAux.getTransportBookingSeq());
            transportBookingViaLocation.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
        }
        ResponseObject responseObject = this.validateTransportBookingViaLocations(bookingViaLocationList);
        if (responseObject.isStatus()) {
            bookingViaLocationList = this.transportBookingViaLocationService.save(bookingViaLocationList);
            responseObject = new ResponseObject("Via Locations saved Successfully", true);
            responseObject.setObject(bookingViaLocationList);
        }
        TransportBooking transportBooking = this.transportBookingService.findOne(transportBookingViaLocationAux.getTransportBookingSeq());
        List<Integer> deliveryList = new ArrayList<>();
        List<TransportBookingViaLocation> transportBookingViaLocationList = this.transportBookingViaLocationService.findByTransportBookingSeqAndStatusOrderByRequestedArrivalTime(transportBooking.getTransportBookingSeq(), MasterDataStatus.APPROVED.getStatusSeq(), EntityGraphUtils.fromName("TransportBookingViaLocation.default"));
        for (TransportBookingViaLocation transportBookingViaLocation : transportBookingViaLocationList) {
            deliveryList.add(transportBookingViaLocation.getViaLocationSeq());
        }
        DistanceAndDuration distanceAndDuration = this.distanceCalculatorService.calculateDistance(transportBooking.getRequestedArrivalTime(),
                true, null,
                transportBooking.getPickupLocationSeq(), deliveryList, transportBooking.getDeliveryLocationSeq());
        transportBooking.setEstimatedKm(distanceAndDuration.getDistance());
        transportBooking.setHumanReadableEta(distanceAndDuration.getDuration());
        Double proposeRate = this.chargesCalculator.parposeRateCalculator(transportBooking.getTransportBookingSeq());
        transportBooking.setProposedTransportCharge(proposeRate);
        this.transportBookingService.save(transportBooking);
        return responseObject;
    }

    @Override
    public ResponseObject addViaLocationsIgnoreValidation(TransportBookingViaLocationAux transportBookingViaLocationAux) {
        ResponseObject responseObject;
        List<TransportBookingViaLocation> bookingViaLocationList = transportBookingViaLocationAux.getBookingViaLocations();
        for (TransportBookingViaLocation transportBookingViaLocation : bookingViaLocationList) {
            transportBookingViaLocation.setTransportBookingSeq(transportBookingViaLocationAux.getTransportBookingSeq());
            transportBookingViaLocation.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
        }
        bookingViaLocationList = this.transportBookingViaLocationService.save(bookingViaLocationList);
        responseObject = new ResponseObject("Via Locations saved Successfully", true);
        responseObject.setObject(bookingViaLocationList);

        TransportBooking transportBooking = this.transportBookingService.findOne(transportBookingViaLocationAux.getTransportBookingSeq());
        List<Integer> deliveryList = new ArrayList<>();
        List<TransportBookingViaLocation> transportBookingViaLocationList = this.transportBookingViaLocationService.findByTransportBookingSeqAndStatusOrderByRequestedArrivalTime(transportBooking.getTransportBookingSeq(), MasterDataStatus.APPROVED.getStatusSeq(), EntityGraphUtils.fromName("TransportBookingViaLocation.default"));
        for (TransportBookingViaLocation transportBookingViaLocation : transportBookingViaLocationList) {
            deliveryList.add(transportBookingViaLocation.getViaLocationSeq());
        }
        DistanceAndDuration distanceAndDuration = this.distanceCalculatorService.calculateDistance(transportBooking.getRequestedArrivalTime(),
                true, null,
                transportBooking.getPickupLocationSeq(), deliveryList, transportBooking.getDeliveryLocationSeq());
        transportBooking.setEstimatedKm(distanceAndDuration.getDistance());
        transportBooking.setHumanReadableEta(distanceAndDuration.getDuration());
        Double proposeRate = this.chargesCalculator.parposeRateCalculator(transportBooking.getTransportBookingSeq());
        transportBooking.setProposedTransportCharge(proposeRate);
        this.transportBookingService.save(transportBooking);
        return responseObject;
    }

    @Override
    public ResponseObject updateViaLocations(TransportBookingViaLocationAux transportBookingViaLocationAux) {
        ResponseObject responseObject;
        List<TransportBookingViaLocation> bookingViaLocationList = transportBookingViaLocationAux.getBookingViaLocations();
        for (TransportBookingViaLocation transportBookingViaLocation : bookingViaLocationList) {
            transportBookingViaLocation.setTransportBookingSeq(transportBookingViaLocationAux.getTransportBookingSeq());
            transportBookingViaLocation.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
        }
        List<TransportBookingViaLocation> dbBookingViaLocationList = this.transportBookingViaLocationService.findByTransportBookingSeqAndStatus(
                transportBookingViaLocationAux.getTransportBookingSeq(), MasterDataStatus.APPROVED.getStatusSeq());

        List<TransportBookingViaLocation> savingList = new ArrayList<>();

        //New Record
        for (TransportBookingViaLocation frontViaLocation : bookingViaLocationList) {
            if (frontViaLocation.getTransportBookingViaLocationSeq() == null) {
                savingList.add(frontViaLocation);
            }
        }

        //Existing Record Update
        for (TransportBookingViaLocation frontViaLocation : bookingViaLocationList) {
            for (TransportBookingViaLocation dbViaLocation : dbBookingViaLocationList) {
                if (dbViaLocation.getTransportBookingViaLocationSeq().equals(frontViaLocation.getTransportBookingViaLocationSeq())) {
                    if (!dbViaLocation.equals(frontViaLocation)) {
                        savingList.add(frontViaLocation);
                    } else {
                        savingList.add(dbViaLocation);
                    }
                    break;
                }
            }
        }

        //Existing Records Delete
        for (TransportBookingViaLocation dbBookingViaLocation : dbBookingViaLocationList) {
            boolean recordExists = false;
            for (TransportBookingViaLocation frontViaLocation : bookingViaLocationList) {
                if (dbBookingViaLocation.getTransportBookingViaLocationSeq().equals(frontViaLocation.getTransportBookingViaLocationSeq())) {
                    recordExists = true;
                    break;
                }
            }
            if (!recordExists) {
                dbBookingViaLocation.setStatus(MasterDataStatus.DELETED.getStatusSeq());
                savingList.add(dbBookingViaLocation);
            }
        }
        if (savingList.size() > 0 && !dbBookingViaLocationList.equals(savingList)) {
            responseObject = this.validateTransportBookingViaLocations(savingList);
            if (responseObject.isStatus()) {
                savingList = this.transportBookingViaLocationService.save(savingList);
                TransportBooking transportBooking = this.transportBookingService.findOne(transportBookingViaLocationAux.getTransportBookingSeq());
                Double proposeRate = this.chargesCalculator.parposeRateCalculator(transportBooking.getTransportBookingSeq());
                transportBooking.setProposedTransportCharge(proposeRate);
                responseObject = new ResponseObject("Via Locations saved Updated", true);
                responseObject.setObject(savingList);
            }
        } else {
            responseObject = new ResponseObject("No Amendments Found !!", false);
        }
        return responseObject;
    }

    public ResponseObject validateTransportBooking(TransportBooking transportBooking) {
        ResponseObject responseObject = this.statusChecker.canUpdate(transportBooking);
        if (responseObject.isStatus()) {
            Set<ConstraintViolation<TransportBooking>> errors = Validation.buildDefaultValidatorFactory().getValidator().validate(transportBooking);
            if (errors.size() > 0) {
                String errorMessage = ErrorMessageCreator.errorsInRow((HashSet<?>) errors);
                responseObject.setMessage(errorMessage);
                responseObject.setStatus(false);
            } else {
                List<String> errorList = new ArrayList<>();
                if (!transportBooking.getRequestedArrivalTime().before(transportBooking.getRequestedDeliveryTime())) {
                    errorList.add("Delivery time should be Greater than Arrival time");
                }
                if (errorList.size() > 0) {
                    responseObject.setMessage(errorList.stream().collect(Collectors.joining(",")));
                    responseObject.setStatus(false);
                } else {
                    responseObject.setStatus(true);
                    responseObject.setMessage("No Errors");
                }
            }
        }
        return responseObject;
    }

    public ResponseObject validateTransportBookingViaLocations(List<TransportBookingViaLocation> transportBookingViaLocationList) {
        TransportBooking transportBooking = this.transportBookingService.findOne(transportBookingViaLocationList.get(0).getTransportBookingSeq());
        ResponseObject responseObject = this.statusChecker.canUpdate(transportBooking);
        if (responseObject.isStatus()) {
            for (TransportBookingViaLocation transportBookingViaLocation : transportBookingViaLocationList) {
                Set<ConstraintViolation<TransportBookingViaLocation>> errors = Validation.buildDefaultValidatorFactory().getValidator().validate(transportBookingViaLocation);
                if (errors.size() > 0) {
                    String errorMessage = ErrorMessageCreator.errorsInRow((HashSet<?>) errors);
                    responseObject.setMessage(errorMessage);
                    responseObject.setStatus(false);
                    break;
                } else {
                    List<String> errorList = new ArrayList<>();
                    if (!transportBooking.getRequestedArrivalTime().before(transportBookingViaLocation.getRequestedArrivalTime())) {
                        errorList.add("Via Location Arrival time should be Greater than Pickup Arrival time");
                    }
                    if (!transportBookingViaLocation.getRequestedArrivalTime().before(transportBooking.getRequestedDeliveryTime())) {
                        errorList.add("Via Location Arrival time should be Smaller than Delivery time");
                    }
                    if (errorList.size() > 0) {
                        responseObject.setMessage(errorList.stream().collect(Collectors.joining(",")));
                        responseObject.setStatus(false);
                        break;
                    } else {
                        responseObject.setStatus(true);
                        responseObject.setMessage("No Errors");
                    }
                }
            }
        }
        return responseObject;
    }

    @Override
    public TransportBooking copyBooking(Integer bookingSeq) {
        TransportBooking transportBooking = this.transportBookingService.findOne(bookingSeq, EntityGraphUtils.fromName("TransportBooking.createBooking"));
        transportBooking.setCurrentStatus(BookingStatus.ACCEPTED.getCurrentStatus());
        transportBooking.setTransportBookingSeq(null);
        transportBooking.setJobSeq(null);
        transportBooking.setCreatedDate(null);
        transportBooking.setCreatedBy(null);
        transportBooking.setLastModifiedBy(null);
        transportBooking.setLastModifiedDate(null);
        return transportBooking;
    }

    @Override
    public ResponseObject deleteBooking(Integer bookingSeq) {
        TransportBooking transportBooking = this.transportBookingService.findOne(bookingSeq);
        ResponseObject responseObject = this.statusChecker.canDelete(transportBooking);
        if (responseObject.isStatus()) {
            transportBooking.setCurrentStatus(BookingStatus.CANCELLED.getCurrentStatus());
            transportBooking = this.transportBookingService.save(transportBooking);
            responseObject = new ResponseObject("Booking Deleted Successfully", true);
        } else {
            responseObject = new ResponseObject("Cannot Delete Booking!!", false);
            responseObject.setObject(transportBooking);
        }
        responseObject.setObject(transportBooking);
        return responseObject;
    }

    @Override
    public List<VehicleType> findByStakeholderSeq(Integer customerSeq) {
        Set<VehicleType> vehicleTypeSet = new HashSet<>();
        try {
            List<RateMaster> rateMasterList = this.rateMasterService.findByStakeholderSeqAndStatusAndChargeType(customerSeq, MasterDataStatus.APPROVED.getStatusSeq(), ChargeType.REVENUE.getChargeType());
            for (RateMaster rateMaster : rateMasterList) {
                for (RateMasterDetail rateMasterDetail : rateMaster.getRateMasterDetailList()) {
                    if (rateMasterDetail.getRateType().equals(RateType.VEHICLE_TYPE.getRateType())) {
                        vehicleTypeSet.add(this.vehicleTypeService.findOne(rateMasterDetail.getTypeSeq()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>(vehicleTypeSet);
    }

}

package com.tmj.tms.transport.bussinesslayer.managerimpl;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.querydsl.core.BooleanBuilder;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.fleet.datalayer.modal.Vehicle;
import com.tmj.tms.fleet.datalayer.service.VehicleService;
import com.tmj.tms.master.datalayer.modal.Employee;
import com.tmj.tms.master.datalayer.service.EmployeeDesignationService;
import com.tmj.tms.master.datalayer.service.EmployeeService;
import com.tmj.tms.transport.bussinesslayer.manager.VehicleAssignmentControllerManager;
import com.tmj.tms.transport.datalayer.modal.QTransportBooking;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.transport.datalayer.modal.TransportBookingStatus;
import com.tmj.tms.transport.datalayer.modal.TransportBookingVehicle;
import com.tmj.tms.transport.datalayer.modal.auxiliary.VehicleAssignmentSearch;
import com.tmj.tms.transport.datalayer.service.TransportBookingService;
import com.tmj.tms.transport.datalayer.service.TransportBookingStatusService;
import com.tmj.tms.transport.datalayer.service.TransportBookingVehicleService;
import com.tmj.tms.transport.utility.BooleanBuilderDuplicateRemover;
import com.tmj.tms.transport.utility.ViaLocationFormatter;
import com.tmj.tms.transport.utility.service.StatusUpdaterService;
import com.tmj.tms.utility.DateFormatter;
import com.tmj.tms.utility.ResponseObject;
import com.tmj.tms.utility.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleAssignmentControllerManagerImpl implements VehicleAssignmentControllerManager {

    private final TransportBookingService transportBookingService;
    private final ViaLocationFormatter viaLocationFormatter;
    private final TransportBookingStatusService transportBookingStatusService;
    private final VehicleService vehicleService;
    private final BooleanBuilderDuplicateRemover booleanBuilderDuplicateRemover;
    private final TransportBookingVehicleService transportBookingVehicleService;
    private final StatusUpdaterService statusUpdaterService;
    private final EmployeeService employeeService;
    private final EmployeeDesignationService employeeDesignationService;

    @Autowired
    public VehicleAssignmentControllerManagerImpl(TransportBookingService transportBookingService,
                                                  ViaLocationFormatter viaLocationFormatter,
                                                  TransportBookingStatusService transportBookingStatusService,
                                                  VehicleService vehicleService,
                                                  BooleanBuilderDuplicateRemover booleanBuilderDuplicateRemover,
                                                  TransportBookingVehicleService transportBookingVehicleService,
                                                  StatusUpdaterService statusUpdaterService,
                                                  EmployeeService employeeService,
                                                  EmployeeDesignationService employeeDesignationService) {
        this.transportBookingService = transportBookingService;
        this.viaLocationFormatter = viaLocationFormatter;
        this.transportBookingStatusService = transportBookingStatusService;
        this.vehicleService = vehicleService;
        this.booleanBuilderDuplicateRemover = booleanBuilderDuplicateRemover;
        this.transportBookingVehicleService = transportBookingVehicleService;
        this.statusUpdaterService = statusUpdaterService;
        this.employeeService = employeeService;
        this.employeeDesignationService = employeeDesignationService;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TransportBooking> searchBooking(VehicleAssignmentSearch vehicleAssignmentSearch) {
        List<TransportBooking> transportBookingList = null;
        try {
            QTransportBooking transportBooking = QTransportBooking.transportBooking;
            BooleanBuilder builder = new BooleanBuilder();
            builder = this.booleanBuilderDuplicateRemover.transportBooking(builder, transportBooking, vehicleAssignmentSearch.getTransportBookingSeq());
            if (vehicleAssignmentSearch.getTransportBookingSeq() == null) {
                if (vehicleAssignmentSearch.getCustomerSeq() != null) {
                    builder.and(transportBooking.customerSeq.eq(vehicleAssignmentSearch.getCustomerSeq()));
                }
                if (!vehicleAssignmentSearch.getCurrentStatus().equals(-1)) {
                    builder.and(transportBooking.currentStatus.eq(vehicleAssignmentSearch.getCurrentStatus()));
                } else {
                    List<TransportBookingStatus> transportBookingStatusList = this.transportBookingStatusService.findAllByStatusAndVehicleAssignOrderByCurrentStatus(MasterDataStatus.APPROVED.getStatusSeq(), YesOrNo.Yes.getYesOrNoSeq());
                    List<Integer> bookingStatusSeqList = transportBookingStatusList.stream().map(TransportBookingStatus::getCurrentStatus).collect(Collectors.toList());
                    builder.and(transportBooking.currentStatus.in(bookingStatusSeqList));
                }
                if (vehicleAssignmentSearch.getPickupLocationSeq() != null) {
                    builder.and(transportBooking.pickupLocationSeq.eq(vehicleAssignmentSearch.getPickupLocationSeq()));
                }
                if (vehicleAssignmentSearch.getDeliveryLocationSeq() != null) {
                    builder.and(transportBooking.deliveryLocationSeq.eq(vehicleAssignmentSearch.getDeliveryLocationSeq()));
                }
                if (vehicleAssignmentSearch.getVehicleTypeSeq() != null) {
                    builder.and(transportBooking.vehicleTypeSeq.eq(vehicleAssignmentSearch.getVehicleTypeSeq()));
                }
                if (vehicleAssignmentSearch.getJobNo() != null) {
                    builder.and(transportBooking.job.jobNo.containsIgnoreCase(vehicleAssignmentSearch.getJobNo()));
                }
                if (vehicleAssignmentSearch.getCustomerRefNo() != null) {
                    builder.and(transportBooking.customerReferenceNo.containsIgnoreCase(vehicleAssignmentSearch.getCustomerRefNo()));
                }
                if (vehicleAssignmentSearch.getStartDate() != null && vehicleAssignmentSearch.getEndDate() != null) {
                    builder = this.booleanBuilderDuplicateRemover.dateFilterType(builder, transportBooking,
                            vehicleAssignmentSearch.getDateFilterType(),
                            vehicleAssignmentSearch.getStartDate(), DateFormatter.getEndOfTheDay(vehicleAssignmentSearch.getEndDate()));
                }
                transportBookingList = (List<TransportBooking>) this.transportBookingService.findAll(builder, EntityGraphUtils.fromName("TransportBooking.vehicleAssignment"));
            } else {
                transportBookingList = (List<TransportBooking>) this.transportBookingService.findAll(builder, EntityGraphUtils.fromName("TransportBooking.vehicleAssignment"));
            }

            for (TransportBooking booking : transportBookingList) {
                booking.setViaLocationString(this.viaLocationFormatter.getViaLocationAsString(booking.getTransportBookingViaLocationList()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transportBookingList;
    }

    @Override
    public ResponseObject assignVehicle(TransportBookingVehicle transportBookingVehicle) {
        ResponseObject responseObject = this.validateVehicleAssignment(transportBookingVehicle);
        if (responseObject.isStatus()) {
            TransportBookingVehicle transportBookingVehicleDB = this.transportBookingVehicleService.findByTransportBookingSeqAndStatus(transportBookingVehicle.getTransportBookingSeq(), MasterDataStatus.APPROVED.getStatusSeq());
            if (transportBookingVehicleDB != null) {
                transportBookingVehicleDB.setTransportCompanySeq(transportBookingVehicle.getTransportCompanySeq());
                transportBookingVehicleDB.setVehicleSeq(transportBookingVehicle.getVehicleSeq());
                transportBookingVehicleDB.setDriverSeq(transportBookingVehicle.getDriverSeq());
                transportBookingVehicleDB.setHelperSeq(transportBookingVehicle.getHelperSeq());
                transportBookingVehicleDB.setRemarks(transportBookingVehicle.getRemarks().trim());
                transportBookingVehicleDB = this.transportBookingVehicleService.save(transportBookingVehicleDB);
                responseObject.setObject(transportBookingVehicleDB);
            } else {
                transportBookingVehicle.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
                transportBookingVehicle = this.transportBookingVehicleService.save(transportBookingVehicle);
                responseObject.setObject(transportBookingVehicle);
            }
            responseObject.setStatus(true);
            responseObject.setMessage("Vehicle Assignment Successful!!");
            this.statusUpdaterService.vehicleAssigned(transportBookingVehicle.getTransportBookingSeq());
        }
        return responseObject;
    }

    @Override
    public ResponseObject removeVehicle(TransportBookingVehicle transportBookingVehicle) {
        ResponseObject responseObject = this.validateRemoveVehicleAssignment(transportBookingVehicle);
        if (responseObject.isStatus()) {
            TransportBookingVehicle transportBookingVehicleDB = this.transportBookingVehicleService.findByTransportBookingSeqAndStatus(transportBookingVehicle.getTransportBookingSeq(), MasterDataStatus.APPROVED.getStatusSeq());
            if (transportBookingVehicleDB != null) {
                transportBookingVehicleDB.setStatus(MasterDataStatus.DELETED.getStatusSeq());
                this.transportBookingVehicleService.save(transportBookingVehicleDB);
                responseObject.setStatus(true);
                responseObject.setMessage("Vehicle Removed Successful!!");
                responseObject.setObject(transportBookingVehicle);
                this.statusUpdaterService.vehicleRemoved(transportBookingVehicle.getTransportBookingSeq());
            } else {
                responseObject.setStatus(false);
                responseObject.setMessage("Vehicle not Assigned yet!!");
            }
        }
        return responseObject;
    }

    @Override
    public ResponseObject validateRemoveVehicleAssignment(TransportBookingVehicle transportBookingVehicle) {
        ResponseObject responseObject = new ResponseObject();
        TransportBooking transportBooking = this.transportBookingService.findOne(transportBookingVehicle.getTransportBookingSeq());
        boolean validStatus = false;
        List<TransportBookingStatus> transportBookingStatusList = this.transportBookingStatusService.findAllByStatusAndVehicleAssignOrderByCurrentStatus(MasterDataStatus.APPROVED.getStatusSeq(), YesOrNo.Yes.getYesOrNoSeq());
        for (TransportBookingStatus transportBookingStatus : transportBookingStatusList) {
            if (transportBooking.getCurrentStatus().equals(transportBookingStatus.getCurrentStatus())) {
                validStatus = true;
                break;
            }
        }
        if (validStatus) {
            responseObject.setStatus(true);
        } else {
            responseObject.setStatus(false);
            responseObject.setMessage("Cannot remove Vehicle!!");
        }
        return responseObject;
    }

    @Override
    public List<Employee> findHelper(Integer transportBookingSeq, String searchParam, Integer companyProfileSeq) {
        List<Employee> employeeList = new ArrayList<>();
        TransportBookingVehicle transportBookingVehicle = this.transportBookingVehicleService.findByTransportBookingSeqAndStatus(transportBookingSeq, MasterDataStatus.APPROVED.getStatusSeq());
        if (transportBookingVehicle != null) {
            employeeList = this.employeeService.findByStatusAndEmployeeDesignationSeqAndCompanyProfileSeqAndStakeholderSeqAndEmployeeNameContainingIgnoreCase(
                    MasterDataStatus.APPROVED.getStatusSeq(),
                    this.employeeDesignationService.findByDesignation("HELPER").getEmployeeDesignationSeq(),
                    companyProfileSeq,
                    transportBookingVehicle.getTransportCompanySeq(),
                    searchParam);
        }
        return employeeList;
    }

    @Override
    public List<Employee> findDriver(Integer transportBookingSeq, String searchParam, Integer companyProfileSeq) {
        List<Employee> employeeList = new ArrayList<>();
        TransportBookingVehicle transportBookingVehicle = this.transportBookingVehicleService.findByTransportBookingSeqAndStatus(transportBookingSeq, MasterDataStatus.APPROVED.getStatusSeq());
        if (transportBookingVehicle != null) {
            employeeList = this.employeeService.findByStatusAndEmployeeDesignationSeqAndCompanyProfileSeqAndStakeholderSeqAndEmployeeNameContainingIgnoreCase(
                    MasterDataStatus.APPROVED.getStatusSeq(),
                    this.employeeDesignationService.findByDesignation("DRIVER").getEmployeeDesignationSeq(),
                    companyProfileSeq,
                    transportBookingVehicle.getTransportCompanySeq(),
                    searchParam);
        }
        return employeeList;
    }

    @Override
    public ResponseObject changeDriver(Integer transportBookingSeq, Integer employeeSeq) {
        ResponseObject responseObject = new ResponseObject();
        TransportBookingVehicle transportBookingVehicle = this.transportBookingVehicleService.findByTransportBookingSeqAndStatus(transportBookingSeq, MasterDataStatus.APPROVED.getStatusSeq());
        if (transportBookingVehicle != null) {
            transportBookingVehicle.setDriverSeq(employeeSeq);
            this.transportBookingVehicleService.save(transportBookingVehicle);
            responseObject.setStatus(true);
            responseObject.setObject(this.employeeService.findOne(employeeSeq));
        } else {
            responseObject.setStatus(false);
            responseObject.setMessage("No vehicle assigned yet !!");
        }
        return responseObject;
    }

    @Override
    public ResponseObject changeHelper(Integer transportBookingSeq, Integer employeeSeq) {
        ResponseObject responseObject = new ResponseObject();
        TransportBookingVehicle transportBookingVehicle = this.transportBookingVehicleService.findByTransportBookingSeqAndStatus(transportBookingSeq, MasterDataStatus.APPROVED.getStatusSeq());
        if (transportBookingVehicle != null) {
            transportBookingVehicle.setHelperSeq(employeeSeq);
            this.transportBookingVehicleService.save(transportBookingVehicle);
            responseObject.setStatus(true);
            responseObject.setObject(this.employeeService.findOne(employeeSeq));
        } else {
            responseObject.setStatus(false);
            responseObject.setMessage("No Vehicle assigned yet !!");
        }
        return responseObject;
    }

    @Override
    public ResponseObject validateVehicleAssignment(TransportBookingVehicle transportBookingVehicle) {
        ResponseObject responseObject = new ResponseObject();
        TransportBooking transportBooking = this.transportBookingService.findOne(transportBookingVehicle.getTransportBookingSeq());
        boolean validStatus = false;
        List<TransportBookingStatus> transportBookingStatusList = this.transportBookingStatusService.findAllByStatusAndVehicleAssignOrderByCurrentStatus(MasterDataStatus.APPROVED.getStatusSeq(), YesOrNo.Yes.getYesOrNoSeq());
        for (TransportBookingStatus transportBookingStatus : transportBookingStatusList) {
            if (transportBooking.getCurrentStatus().equals(transportBookingStatus.getCurrentStatus())) {
                validStatus = true;
                break;
            }
        }
        if (validStatus) {
            Vehicle vehicle = this.vehicleService.findOne(transportBookingVehicle.getVehicleSeq());
            if (transportBooking.getVehicleType().getMaxCBM() > vehicle.getVehicleVehicleTypeList().get(0).getVehicleType().getMaxCBM()) {
                responseObject.setStatus(false);
                responseObject.setMessage("Cannot assign a smaller vehicle!!");
            } else {
                responseObject.setStatus(true);
            }
        } else {
            responseObject.setStatus(false);
            responseObject.setMessage("Cannot change the Vehicle. Please Remove and assign again!!");
        }
        return responseObject;
    }
}

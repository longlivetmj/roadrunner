package com.tmj.tms.home.businesslayer.managerImpl;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.querydsl.core.BooleanBuilder;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.fleet.datalayer.modal.QVehicle;
import com.tmj.tms.fleet.datalayer.modal.Vehicle;
import com.tmj.tms.fleet.datalayer.modal.auxiliary.JobAssignedVehicles;
import com.tmj.tms.fleet.datalayer.modal.auxiliary.QJobAssignedVehicles;
import com.tmj.tms.fleet.datalayer.modal.auxiliary.VehicleTrackingSearch;
import com.tmj.tms.fleet.datalayer.service.JobAssignedVehiclesService;
import com.tmj.tms.fleet.datalayer.service.VehicleService;
import com.tmj.tms.fleet.integration.auxiliary.VehicleTrackingResponse;
import com.tmj.tms.fleet.utility.VehicleTrackingStatus;
import com.tmj.tms.home.businesslayer.manager.VehicleTrackingControllerManager;
import com.tmj.tms.transport.datalayer.service.TransportBookingService;
import com.tmj.tms.transport.utility.BookingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleTrackingControllerManagerImpl implements VehicleTrackingControllerManager {

    private final VehicleService vehicleService;
    private final HttpSession httpSession;
    private final TransportBookingService transportBookingService;
    private final JobAssignedVehiclesService jobAssignedVehiclesService;

    @Autowired
    public VehicleTrackingControllerManagerImpl(VehicleService vehicleService,
                                                HttpSession httpSession,
                                                TransportBookingService transportBookingService,
                                                JobAssignedVehiclesService jobAssignedVehiclesService) {
        this.vehicleService = vehicleService;
        this.httpSession = httpSession;
        this.transportBookingService = transportBookingService;
        this.jobAssignedVehiclesService = jobAssignedVehiclesService;
    }

    @Override
    public List<VehicleTrackingResponse> searchVehicle(VehicleTrackingSearch vehicleTrackingSearch) {
        List<VehicleTrackingResponse> vehicleTrackingResponseList = new ArrayList<>();
        try {
            Integer companyProfileSeq = Integer.parseInt(httpSession.getAttribute("companyProfileSeq").toString());
            QVehicle vehicle = QVehicle.vehicle;
            BooleanBuilder builder = new BooleanBuilder();
            builder.and(vehicle.companyProfileSeq.eq(companyProfileSeq));
            if (vehicleTrackingSearch.getVehicleNo() != null) {
                builder.and(vehicle.vehicleNo.containsIgnoreCase(vehicleTrackingSearch.getVehicleNo()));
            }
            if (vehicleTrackingSearch.getTransporterSeq() != null) {
                builder.and(vehicle.stakeholderSeq.eq(vehicleTrackingSearch.getTransporterSeq()));
            }
            if (vehicleTrackingSearch.getVehicleTypeSeq() != null) {
                builder.and(vehicle.vehicleVehicleTypeList.any().vehicleTypeSeq.eq(vehicleTrackingSearch.getVehicleTypeSeq()));
            }
            builder.and(vehicle.status.eq(MasterDataStatus.APPROVED.getStatusSeq()));
            builder.and(vehicle.gpsTerminalKey.isNotEmpty());
            builder.and(vehicle.gpsTerminalKey.isNotNull());
            List<Vehicle> vehicleList = (List<Vehicle>) this.vehicleService.findAll(builder, EntityGraphUtils.fromName("Vehicle.default"));
            QJobAssignedVehicles jobAssignedVehicles = QJobAssignedVehicles.jobAssignedVehicles;
            BooleanBuilder jaBuilder = new BooleanBuilder();
            if (vehicleTrackingSearch.getCustomerSeq() != null) {
                jaBuilder.and(jobAssignedVehicles.customerSeq.eq(vehicleTrackingSearch.getCustomerSeq()));
            }
            if (vehicleTrackingSearch.getStatus().equals(VehicleTrackingStatus.ALL_VEHICLES.getTrackingType())) {
                for (Vehicle filteredVehicle : vehicleList) {
                    VehicleTrackingResponse vehicleTrackingResponse = new VehicleTrackingResponse();
                    vehicleTrackingResponse.setGpsTerminalKey(filteredVehicle.getGpsTerminalKey());
                    vehicleTrackingResponse.setVehicleNo(filteredVehicle.getVehicleNo());
                    jaBuilder.and(jobAssignedVehicles.vehicleSeq.eq(filteredVehicle.getVehicleSeq()));
                    List<JobAssignedVehicles> jobAssignedVehiclesServiceAll = (List<JobAssignedVehicles>) this.jobAssignedVehiclesService.findAll(jaBuilder);
                    if (jobAssignedVehiclesServiceAll.size() > 0) {
                        vehicleTrackingResponse.setMessage(this.generateMessage(jobAssignedVehiclesServiceAll.get(0)));
                    } else {
                        vehicleTrackingResponse.setMessage("No Job Found");
                    }
                    vehicleTrackingResponseList.add(vehicleTrackingResponse);
                }
            } else if (vehicleTrackingSearch.getStatus().equals(VehicleTrackingStatus.CARGO_IN_HAND.getTrackingType())) {
                for (Vehicle filteredVehicle : vehicleList) {
                    VehicleTrackingResponse vehicleTrackingResponse = new VehicleTrackingResponse();
                    vehicleTrackingResponse.setGpsTerminalKey(filteredVehicle.getGpsTerminalKey());
                    vehicleTrackingResponse.setVehicleNo(filteredVehicle.getVehicleNo());
                    jaBuilder.and(jobAssignedVehicles.vehicleSeq.eq(filteredVehicle.getVehicleSeq()));
                    jaBuilder.and(jobAssignedVehicles.currentStatus.eq(BookingStatus.DEPARTED_FROM_PICKUP.getCurrentStatus()));
                    List<JobAssignedVehicles> jobAssignedVehiclesServiceAll = (List<JobAssignedVehicles>) this.jobAssignedVehiclesService.findAll(jaBuilder);
                    if (jobAssignedVehiclesServiceAll.size() > 0) {
                        vehicleTrackingResponse.setMessage(this.generateMessage(jobAssignedVehiclesServiceAll.get(0)));
                        vehicleTrackingResponseList.add(vehicleTrackingResponse);
                    }
                }
            } else if (vehicleTrackingSearch.getStatus().equals(VehicleTrackingStatus.ARRIVED_AT_PICKUP.getTrackingType())) {
                for (Vehicle filteredVehicle : vehicleList) {
                    VehicleTrackingResponse vehicleTrackingResponse = new VehicleTrackingResponse();
                    vehicleTrackingResponse.setGpsTerminalKey(filteredVehicle.getGpsTerminalKey());
                    vehicleTrackingResponse.setVehicleNo(filteredVehicle.getVehicleNo());
                    jaBuilder.and(jobAssignedVehicles.vehicleSeq.eq(filteredVehicle.getVehicleSeq()));
                    jaBuilder.and(jobAssignedVehicles.currentStatus.eq(BookingStatus.ARRIVED_AT_PICKUP.getCurrentStatus()));
                    List<JobAssignedVehicles> jobAssignedVehiclesServiceAll = (List<JobAssignedVehicles>) this.jobAssignedVehiclesService.findAll(jaBuilder);
                    if (jobAssignedVehiclesServiceAll.size() > 0) {
                        vehicleTrackingResponse.setMessage(this.generateMessage(jobAssignedVehiclesServiceAll.get(0)));
                        vehicleTrackingResponseList.add(vehicleTrackingResponse);
                    }
                }
            } else if (vehicleTrackingSearch.getStatus().equals(VehicleTrackingStatus.ARRIVED_AT_DELIVERY.getTrackingType())) {
                for (Vehicle filteredVehicle : vehicleList) {
                    VehicleTrackingResponse vehicleTrackingResponse = new VehicleTrackingResponse();
                    vehicleTrackingResponse.setGpsTerminalKey(filteredVehicle.getGpsTerminalKey());
                    vehicleTrackingResponse.setVehicleNo(filteredVehicle.getVehicleNo());
                    jaBuilder.and(jobAssignedVehicles.vehicleSeq.eq(filteredVehicle.getVehicleSeq()));
                    jaBuilder.and(jobAssignedVehicles.currentStatus.eq(BookingStatus.ARRIVED_AT_DELIVERY.getCurrentStatus()));
                    List<JobAssignedVehicles> jobAssignedVehiclesServiceAll = (List<JobAssignedVehicles>) this.jobAssignedVehiclesService.findAll(jaBuilder);
                    if (jobAssignedVehiclesServiceAll.size() > 0) {
                        vehicleTrackingResponse.setMessage(this.generateMessage(jobAssignedVehiclesServiceAll.get(0)));
                        vehicleTrackingResponseList.add(vehicleTrackingResponse);
                    }
                }
            } else if (vehicleTrackingSearch.getStatus().equals(VehicleTrackingStatus.NO_JOB_VEHICLES.getTrackingType())) {
                for (Vehicle filteredVehicle : vehicleList) {
                    VehicleTrackingResponse vehicleTrackingResponse = new VehicleTrackingResponse();
                    vehicleTrackingResponse.setGpsTerminalKey(filteredVehicle.getGpsTerminalKey());
                    vehicleTrackingResponse.setVehicleNo(filteredVehicle.getVehicleNo());
                    jaBuilder.and(jobAssignedVehicles.vehicleSeq.eq(filteredVehicle.getVehicleSeq()));
                    List<JobAssignedVehicles> jobAssignedVehiclesServiceAll = (List<JobAssignedVehicles>) this.jobAssignedVehiclesService.findAll(jaBuilder);
                    if (jobAssignedVehiclesServiceAll.size() == 0) {
                        vehicleTrackingResponseList.add(vehicleTrackingResponse);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vehicleTrackingResponseList;
    }

    private String generateMessage(JobAssignedVehicles jobAssignedVehicles) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Request ID: ").append(jobAssignedVehicles.getTransportBooking().getBookingNo()).append("<br/>");
        stringBuilder.append("Vehicle No: ").append(jobAssignedVehicles.getTransportBooking().getTransportBookingVehicleList().get(0).getVehicle().getVehicleNo()).append("<br/>");
        stringBuilder.append("Vehicle No: ").append(jobAssignedVehicles.getTransportBooking().getTransportBookingVehicleList().get(0).getDriver().getEmployeeName()).append("<br/>");
        stringBuilder.append("Customer: ").append(jobAssignedVehicles.getTransportBooking().getCustomer().getStakeholderName()).append("<br/>");
        stringBuilder.append("Pick-up Location: ").append(jobAssignedVehicles.getTransportBooking().getPickupLocation().getDestination()).append("<br/>");
        stringBuilder.append("Delivery Location: ").append(jobAssignedVehicles.getTransportBooking().getDeliveryLocation().getDestination()).append("<br/>");
        return stringBuilder.toString();
    }
}
